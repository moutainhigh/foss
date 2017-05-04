CREATE OR REPLACE PACKAGE PKG_STL_BALANCE_DEPOSIT_TF IS

  -- Author  : 000123-foss-huangxiaobo
  -- Created : 2012/11/19 14:36:51
  -- Purpose : 应收期末结算

  ------------------------------------------------------------------
  --应收期末结账总入口
  ------------------------------------------------------------------
  FUNCTION FUNC_COUNT_BALANCE(P_ORG_CODE     STRING, --组织编码
                              P_BEGIN_DATE   DATE, --起始日期
                              P_END_DATE     DATE, --结束日期
                              P_PERIOD       STRING, --结账期间
                              P_BALANCE_TYPE STRING --结账类型
                              ) RETURN BOOLEAN;

  ------------------------------------------------------------------
  --计算本期发生额度
  ------------------------------------------------------------------
  FUNCTION FUNC_DAILY_DETAIL_PRODUCE(P_ORG_CODE     STRING, --组织编码
                                     P_BEGIN_DATE   DATE, --起始日期
                                     P_END_DATE     DATE, --结束日期
                                     P_PERIOD       STRING, --结账期间
                                     P_BALANCE_TYPE IN STRING -- 核算类别
                                     ) RETURN BOOLEAN;

  ------------------------------------------------------------------
  --计算本次核销金额
  ------------------------------------------------------------------
  FUNCTION FUNC_DAILY_DETAIL_WRITEOFF(P_ORG_CODE     STRING, --组织编码
                                      P_BEGIN_DATE   DATE, --起始日期
                                      P_END_DATE     DATE, --结束日期
                                      P_PERIOD       STRING, --结账期间
                                      P_BALANCE_TYPE IN STRING -- 核算类别
                                      ) RETURN BOOLEAN;

END PKG_STL_BALANCE_DEPOSIT_TF;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_BALANCE_DEPOSIT_TF IS

  --Author:000123-foss-huangxiaobo
  --Created:2012-11-19

  ------------------------------------------------------------------
  --应收期末结账（应收期末结账总入口）
  ------------------------------------------------------------------
  FUNCTION FUNC_COUNT_BALANCE(P_ORG_CODE     STRING, --组织编码
                              P_BEGIN_DATE   DATE, --起始日期
                              P_END_DATE     DATE, --结束日期
                              P_PERIOD       STRING, --结账期间
                              P_BALANCE_TYPE STRING --结账类型
                              ) RETURN BOOLEAN IS
    --执行结果
    V_RESULT BOOLEAN;
  
  BEGIN
  
    --计算发生额
    V_RESULT := FUNC_DAILY_DETAIL_PRODUCE(P_ORG_CODE,
                                          P_BEGIN_DATE,
                                          P_END_DATE,
                                          P_PERIOD,
                                          P_BALANCE_TYPE);
  
    --发生错误返回
    IF V_RESULT = FALSE THEN
      RAISE_APPLICATION_ERROR('-20002', '结算预收-中转，发生额发生异常');
    END IF;
  
    --计算核销额
    V_RESULT := FUNC_DAILY_DETAIL_WRITEOFF(P_ORG_CODE,
                                           P_BEGIN_DATE,
                                           P_END_DATE,
                                           P_PERIOD,
                                           P_BALANCE_TYPE);
  
    --发生错误返回
    IF V_RESULT = FALSE THEN
      RAISE_APPLICATION_ERROR('-20002', '结算预收-中转，核销发生异常');
    END IF;
  
    RETURN V_RESULT;
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE - 1 / (24 * 60 * 60),
                                        'PKG_STL_Balance_RECEIVABLE.func_count_balance',
                                        P_ORG_CODE || '-预收-中转期末结账调度');
    
      --返回成功标志
      RETURN FALSE;
    
  END;
  ------------------------------------------------------------------
  --计算制定期间的发生额度
  ------------------------------------------------------------------
  FUNCTION FUNC_DAILY_DETAIL_PRODUCE(P_ORG_CODE     STRING, --组织编码
                                     P_BEGIN_DATE   DATE, --起始日期
                                     P_END_DATE     DATE, --结束日期
                                     P_PERIOD       STRING, --结账期间
                                     P_BALANCE_TYPE IN STRING -- 核算类别
                                     ) RETURN BOOLEAN IS
  
    --结账期间
    V_PERIOD STRING(8);
  BEGIN
    --获得记账期间
    V_PERIOD := P_PERIOD;
  
    --作为主方
    --还款未签收
    --将还款金额写入发生额
    INSERT INTO STV.T_STL_DAILY_BALANCE_tmp
    --ID,期间,组织
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
       CHECK_OUT_ORG_CODE,
       PRODUCT_CODE,
       BEGIN_AMOUNT, --期初
       PRODUCE_AMOUNT, --本期发生额
       WRITEOFF_AMOUNT, --核销金额
       END_AMOUNT, --期末余额
       SOURCE_BILL_NO, --来源单号
       SOURCE_BILL_TYPE, --来源单据类别
       CURRENCY_CODE, --币别
       ACTIVE, --是否有效
       STL_TYPE, -- 结算类型
       BILL_TYPE, --单据子类型
       BALANCE_TIME, --结账日期
       CREATE_TIME,
       business_date,
       payment_type,
       invoice_mark--发票标记    
       )
      SELECT SYS_GUID() AS ID, --业务ID
             V_PERIOD AS CHECK_OUT_PERIOD, -- 结账期间
             NVL(RCV.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE, --客户编码
             --如果为出发应收，挂账部门为出发部门
             case
               when rcv.bill_type =
                    Pkg_Stl_Common.RECEIVABLE_BILL_TYPE_ORIGIN then
                 DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE)
               else
                DECODE(RCV.PRODUCT_CODE,
                     PKG_STL_COMMON.PRODUCT_CODE_PKG,
                     DECODE(RCV.receivable_org_code,
                     RCV.ORIG_ORG_CODE,
                     RCV.Express_Orig_Org_Code,
                     RCV.DEST_ORG_CODE,
                     RCV.Express_Dest_Org_Code，RCV.receivable_org_code),
                     RCV.receivable_org_code)
             end AS CHECK_OUT_ORG_CODE, -- 应收组织
             NVL(RCV.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型
             0 AS BEGIN_AMOUNT, --期初金额
             WO.Amount AS PRODUCE_AMOUNT, --发生额
             0 AS WRITEOFF_AMOUNT, --核销金额
             0 AS END_AMOUNT, --期末金额
             --偏线，以运单为准
             case
               when bill_type = pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --来源单号            
             RCV.SOURCE_BILL_TYPE AS SOURCE_BILL_TYPE, --来源单据类别
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE, --币别
             PKG_STL_COMMON.ACTIVE AS ACTIVE, --有效性
             P_BALANCE_TYPE AS STL_TYPE, --结算类型
             RCV.BILL_TYPE AS BILL_TYPE, --单据类型
             P_BEGIN_DATE AS BALANCE_TIME, --结账业务日期
             SYSDATE AS CREATE_TIME, --结账时间
             --明确记账日期为业务日期
             RCV.Account_Date as business_date,
             RCV.payment_type as payment_type,
             RCV.Invoice_Mark as invoice_mark--发票标记      
        FROM STL.T_STL_BILL_WRITEOFF WO
       INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV
      --作为主方
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      
       WHERE RCV.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE <= WO.ACCOUNT_DATE
                 and T.POD_TYPE in
                     (PKG_STL_COMMON.POD__POD_TYPE__POD,
                      PKG_STL_COMMON.POD__POD_TYPE__UPD)) = 0
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
            --指定核销
         and wo.ORG_CODE = p_org_code
            --单据类型（始发运费、到达运费、空运到付、偏线到付、空运代收货款,落地配）
         and RCV.bill_type in
             (pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DEST,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_AA,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_AAC,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DL,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DLC);

    commit;
  
    --作为辅方
    --还款未签收
    --将还款金额写入发生额
    INSERT INTO STV.T_STL_DAILY_BALANCE_tmp
    --ID,期间,组织
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
       CHECK_OUT_ORG_CODE,
       PRODUCT_CODE,
       BEGIN_AMOUNT, --期初
       PRODUCE_AMOUNT, --本期发生额
       WRITEOFF_AMOUNT, --核销金额
       END_AMOUNT, --期末余额
       SOURCE_BILL_NO, --来源单号
       SOURCE_BILL_TYPE, --来源单据类别
       CURRENCY_CODE, --币别
       ACTIVE, --是否有效
       STL_TYPE, -- 结算类型
       BILL_TYPE, --单据子类型
       BALANCE_TIME, --结账日期
       CREATE_TIME,
       business_date,
       payment_type,
       invoice_mark--发票标记    
        )
      SELECT SYS_GUID() AS ID, --业务ID
             V_PERIOD AS CHECK_OUT_PERIOD, -- 结账期间
             NVL(RCV.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE, --客户编码
             --如果为出发应收，挂账部门为出发部门
             case
               when rcv.bill_type =
                    Pkg_Stl_Common.RECEIVABLE_BILL_TYPE_ORIGIN then
                 DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE)
               else
                DECODE(RCV.PRODUCT_CODE,
                     PKG_STL_COMMON.PRODUCT_CODE_PKG,
                     DECODE(RCV.receivable_org_code,
                     RCV.ORIG_ORG_CODE,
                     RCV.Express_Orig_Org_Code,
                     RCV.DEST_ORG_CODE,
                     RCV.Express_Dest_Org_Code，RCV.receivable_org_code),
                     RCV.receivable_org_code)
             end AS CHECK_OUT_ORG_CODE, -- 应收组织
             NVL(RCV.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型
             0 AS BEGIN_AMOUNT, --期初金额
             WO.Amount AS PRODUCE_AMOUNT, --发生额
             0 AS WRITEOFF_AMOUNT, --核销金额
             0 AS END_AMOUNT, --期末金额
             --偏线，以运单为准
             case
               when bill_type = pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --来源单号            
             RCV.SOURCE_BILL_TYPE AS SOURCE_BILL_TYPE, --来源单据类别
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE, --币别
             PKG_STL_COMMON.ACTIVE AS ACTIVE, --有效性
             P_BALANCE_TYPE AS STL_TYPE, --结算类型
             RCV.BILL_TYPE AS BILL_TYPE, --单据类型
             P_BEGIN_DATE AS BALANCE_TIME, --结账业务日期
             SYSDATE AS CREATE_TIME, --结账时间
             --明确记账日期为业务日期
             RCV.Account_Date as business_date,
             RCV.payment_type as payment_type,
             rcv.invoice_mark as invoice_mark--发票标记          
        FROM STL.T_STL_BILL_WRITEOFF WO
       INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV
      --作为辅方
          ON (RCV.RECEIVABLE_NO = WO.END_NO)
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      
       WHERE RCV.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE <= WO.ACCOUNT_DATE
                 and T.POD_TYPE in
                     (PKG_STL_COMMON.POD__POD_TYPE__POD,
                      PKG_STL_COMMON.POD__POD_TYPE__UPD)) = 0
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
            --指定核销
         and wo.ORG_CODE = p_org_code
            --单据类型（始发运费、到达运费、空运到付、偏线到付、空运代收货款,落地配）
         and RCV.bill_type in
             (pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DEST,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_AA,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_AAC,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DL,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DLC);

    commit;
  
    --作为主方
    --还款同时签收
    --将还款金额写入发生额
    INSERT INTO STV.T_STL_DAILY_BALANCE_tmp
    --ID,期间,组织
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
       CHECK_OUT_ORG_CODE,
       PRODUCT_CODE,
       BEGIN_AMOUNT, --期初
       PRODUCE_AMOUNT, --本期发生额
       WRITEOFF_AMOUNT, --核销金额
       END_AMOUNT, --期末余额
       SOURCE_BILL_NO, --来源单号
       SOURCE_BILL_TYPE, --来源单据类别
       CURRENCY_CODE, --币别
       ACTIVE, --是否有效
       STL_TYPE, -- 结算类型
       BILL_TYPE, --单据子类型
       BALANCE_TIME, --结账日期
       CREATE_TIME,
       business_date,
       payment_type,
       invoice_mark--发票标记
       )
      SELECT SYS_GUID() AS ID, --业务ID
             V_PERIOD AS CHECK_OUT_PERIOD, -- 结账期间
             NVL(RCV.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE, --客户编码
             --如果为出发应收，挂账部门为出发部门
             case
               when rcv.bill_type =
                    Pkg_Stl_Common.RECEIVABLE_BILL_TYPE_ORIGIN then
                    DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE)
               else
                     DECODE(RCV.PRODUCT_CODE,
                     PKG_STL_COMMON.PRODUCT_CODE_PKG,
                     DECODE(RCV.receivable_org_code,
                     RCV.ORIG_ORG_CODE,
                     RCV.Express_Orig_Org_Code,
                     RCV.DEST_ORG_CODE,
                     RCV.Express_Dest_Org_Code，RCV.receivable_org_code),
                     RCV.receivable_org_code)
             end AS CHECK_OUT_ORG_CODE, -- 应收组织
             NVL(RCV.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型
             0 AS BEGIN_AMOUNT, --期初金额
             WO.Amount AS PRODUCE_AMOUNT, --发生额
             0 AS WRITEOFF_AMOUNT, --核销金额
             0 AS END_AMOUNT, --期末金额
             --偏线，以运单为准
             case
               when bill_type = pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --来源单号            
             RCV.SOURCE_BILL_TYPE AS SOURCE_BILL_TYPE, --来源单据类别
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE, --币别
             PKG_STL_COMMON.ACTIVE AS ACTIVE, --有效性
             P_BALANCE_TYPE AS STL_TYPE, --结算类型
             RCV.BILL_TYPE AS BILL_TYPE, --单据类型
             P_BEGIN_DATE AS BALANCE_TIME, --结账业务日期
             SYSDATE AS CREATE_TIME, --结账时间
             --明确记账日期为业务日期
             RCV.Account_Date as business_date,
             RCV.payment_type as payment_type,
             rcv.invoice_mark as invoice_mark--发票标记            
        FROM STL.T_STL_BILL_WRITEOFF WO
       INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV
      --作为主方
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      
       WHERE RCV.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                    --签收时间与核销时间相同
                 AND T.POD_DATE = WO.ACCOUNT_DATE
                 and T.POD_TYPE in
                     (PKG_STL_COMMON.POD__POD_TYPE__POD,
                      --签收
                      PKG_STL_COMMON.POD__POD_TYPE__UPD)) = 1
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
            --指定核销
         and wo.ORG_CODE = p_org_code
            --单据类型（始发运费、到达运费、空运到付、偏线到付、空运代收货款）
         and RCV.bill_type in
             (pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DEST,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_AA,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_AAC,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DL,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DLC);

    commit;
  
    --作为辅方
    --还款同时签收
    --将还款金额写入发生额
    INSERT INTO STV.T_STL_DAILY_BALANCE_tmp
    --ID,期间,组织
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
       CHECK_OUT_ORG_CODE,
       PRODUCT_CODE,
       BEGIN_AMOUNT, --期初
       PRODUCE_AMOUNT, --本期发生额
       WRITEOFF_AMOUNT, --核销金额
       END_AMOUNT, --期末余额
       SOURCE_BILL_NO, --来源单号
       SOURCE_BILL_TYPE, --来源单据类别
       CURRENCY_CODE, --币别
       ACTIVE, --是否有效
       STL_TYPE, -- 结算类型
       BILL_TYPE, --单据子类型
       BALANCE_TIME, --结账日期
       CREATE_TIME,
       business_date,
       payment_type,
       invoice_mark--发票标记      
       )
      SELECT SYS_GUID() AS ID, --业务ID
             V_PERIOD AS CHECK_OUT_PERIOD, -- 结账期间
             NVL(RCV.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE, --客户编码
             --如果为出发应收，挂账部门为出发部门
             case
               when rcv.bill_type =
                    Pkg_Stl_Common.RECEIVABLE_BILL_TYPE_ORIGIN then
                    DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE)
               else
                     DECODE(RCV.PRODUCT_CODE,
                     PKG_STL_COMMON.PRODUCT_CODE_PKG,
                     DECODE(RCV.receivable_org_code,
                     RCV.ORIG_ORG_CODE,
                     RCV.Express_Orig_Org_Code,
                     RCV.DEST_ORG_CODE,
                     RCV.Express_Dest_Org_Code，RCV.receivable_org_code),
                     RCV.receivable_org_code)
             end AS CHECK_OUT_ORG_CODE, -- 应收组织
             NVL(RCV.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型
             0 AS BEGIN_AMOUNT, --期初金额
             WO.Amount AS PRODUCE_AMOUNT, --发生额
             0 AS WRITEOFF_AMOUNT, --核销金额
             0 AS END_AMOUNT, --期末金额
             --偏线，以运单为准
             case
               when bill_type = pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --来源单号            
             RCV.SOURCE_BILL_TYPE AS SOURCE_BILL_TYPE, --来源单据类别
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE, --币别
             PKG_STL_COMMON.ACTIVE AS ACTIVE, --有效性
             P_BALANCE_TYPE AS STL_TYPE, --结算类型
             RCV.BILL_TYPE AS BILL_TYPE, --单据类型
             P_BEGIN_DATE AS BALANCE_TIME, --结账业务日期
             SYSDATE AS CREATE_TIME, --结账时间
             --明确记账日期为业务日期
             RCV.Account_Date as business_date,
             RCV.payment_type as payment_type,
             rcv.invoice_mark as invoice_mark--发票标记           
        FROM STL.T_STL_BILL_WRITEOFF WO
       INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV
      --作为辅方
          ON (RCV.RECEIVABLE_NO = WO.END_NO)
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      
       WHERE RCV.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                    --签收时间与核销时间相同
                 AND T.POD_DATE = WO.ACCOUNT_DATE
                 and T.POD_TYPE in
                     (PKG_STL_COMMON.POD__POD_TYPE__POD,
                      --等于签收
                      PKG_STL_COMMON.POD__POD_TYPE__UPD)) = 1
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
            --指定核销
         and wo.ORG_CODE = p_org_code
            --单据类型（始发运费、到达运费、空运到付、偏线到付、空运代收货款）
         and RCV.bill_type in
             (pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DEST,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_AA,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_AAC,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DL,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DLC);
    commit;
  
    RETURN TRUE;
  
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE - 1 / (24 * 60 * 60),
                                        'PKG_STL_BALANCE_DEPOSIT_TF.func_daily_detail_produce',
                                        P_ORG_CODE || '-预收-中转期末结账核算发生额');
    
      --返回失败标志
      RETURN FALSE;
    
  END;

  ------------------------------------------------------------------
  --计算本次核销金额
  ------------------------------------------------------------------
  FUNCTION FUNC_DAILY_DETAIL_WRITEOFF(P_ORG_CODE     STRING, --组织编码
                                      P_BEGIN_DATE   DATE, --起始日期
                                      P_END_DATE     DATE, --结束日期
                                      P_PERIOD       STRING, --期间
                                      P_BALANCE_TYPE IN STRING -- 核算类别
                                      ) RETURN BOOLEAN IS
    --结账期间
    V_PERIOD STRING(8);
  
  BEGIN
    --获得记账期间
    V_PERIOD := P_PERIOD;
  
    --运单类
    --作为主方
    --签收已核销，计入核销额 
    --反签收已核销 ，取反计入核销额    
    INSERT INTO STV.T_STL_DAILY_BALANCE_tmp
    --ID,期间,组织
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
       CHECK_OUT_ORG_CODE,
       BEGIN_AMOUNT,
       PRODUCE_AMOUNT,
       WRITEOFF_AMOUNT,
       END_AMOUNT,
       PRODUCT_CODE,
       SOURCE_BILL_NO,
       SOURCE_BILL_TYPE,
       STL_TYPE, --结算类型
       BILL_TYPE, --结算类型
       CURRENCY_CODE, --币别
       ACTIVE, --是否有效
       BALANCE_TIME, --结账日期
       CREATE_TIME,
       business_date,
       payment_type,
       invoice_mark--发票标记   
       )
      SELECT SYS_GUID() AS ID,
             V_PERIOD AS CHECK_OUT_PERIOD,
             NVL(CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             CHECK_OUT_ORG_CODE AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             --签收已还款
             case
               when pod_Type = Pkg_Stl_Common.POD__POD_TYPE__POD then
                WO_AMOUNT
               else
                WO_AMOUNT * -1
             end AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型 
             --偏线，以运单为准
             case
               when bill_type = pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --来源单号 
             NVL(SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --结算类型
             BILL_TYPE AS BILL_TYPE, -- 单据子类型
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             --明确记账日期为业务日期 
             account_date as business_date,
             payment_type as payment_type,
             invoice_mark as invoice_mark--发票标记          
        FROM (SELECT RCV.CUSTOMER_CODE, --客户编码  
                     --如果为出发应收，挂账部门为出发部门
                     case
                       when rcv.bill_type =
                            Pkg_Stl_Common.RECEIVABLE_BILL_TYPE_ORIGIN then
                       DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE)
               else
                     DECODE(RCV.PRODUCT_CODE,
                     PKG_STL_COMMON.PRODUCT_CODE_PKG,
                     DECODE(RCV.receivable_org_code,
                     RCV.ORIG_ORG_CODE,
                     RCV.Express_Orig_Org_Code,
                     RCV.DEST_ORG_CODE,
                     RCV.Express_Dest_Org_Code，RCV.receivable_org_code),
                     RCV.receivable_org_code)
                     end AS CHECK_OUT_ORG_CODE, -- 应收组织
                     RCV.WAYBILL_NO, --运单号 
                     MIN(RCV.ACCOUNT_DATE) as ACCOUNT_DATE, --会计日期 
                     RCV.BILL_TYPE, --单据子类型
                     MIN(RCV.AMOUNT) RCV_AMOUNT, --应收额  
                     pod.pod_type as pod_type, --签收类型              
                     SUM(WO.AMOUNT) as WO_AMOUNT, --核销额
                     rcv.payment_type as payment_type, --付款方式
                     rcv.SOURCE_BILL_TYPE as SOURCE_BILL_TYPE, --单据类型
                     rcv.SOURCE_BILL_NO as SOURCE_BILL_NO, --来源单号
                     RCV.PRODUCT_CODE, --产品类型
                     rcv.invoice_mark as invoice_mark --发票标记
                FROM STL.T_STL_POD POD
               inner JOIN STL.T_STL_BILL_RECEIVABLE RCV
                  ON RCV.WAYBILL_NO = POD.WAYBILL_NO
                 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
               inner JOIN STL.T_STL_BILL_WRITEOFF WO
              --作为主方
                  ON (WO.BEGIN_NO = RCV.RECEIVABLE_NO)
               WHERE RCV.SOURCE_BILL_TYPE =
                     PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
                    --指定单据类型
                    --单据类型（始发运费、到达运费、空运到付、偏线到付、空运代收货款）
                 and RCV.bill_type in
                     (pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN,
                      pkg_stl_common.RECEIVABLE_BILL_TYPE_DEST,
                      pkg_stl_common.RECEIVABLE_BILL_TYPE_AA,
                      pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL,
                      pkg_stl_common.RECEIVABLE_BILL_TYPE_AAC,
                      pkg_stl_common.RECEIVABLE_BILL_TYPE_DL,
                      pkg_stl_common.RECEIVABLE_BILL_TYPE_DLC)
                 AND WO.ACCOUNT_DATE <= POD.POD_DATE --核销时间于小反签收时间
                 AND POD.POD_DATE >= P_BEGIN_DATE
                 AND POD.POD_DATE < P_END_DATE
                    --只要签收与反签收的数据
                 and pod.pod_Type in
                     (PKG_STL_COMMON.POD__POD_TYPE__POD,
                      PKG_STL_COMMON.POD__POD_TYPE__UPD)
                    --指定核销
                 and wo.ORG_CODE = p_org_code
               GROUP BY RCV.CUSTOMER_CODE, --客户编码
                        DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE),
                        DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,
                             DECODE(RCV.receivable_org_code,RCV.ORIG_ORG_CODE, RCV.Express_Orig_Org_Code,
                                                            RCV.DEST_ORG_CODE,RCV.Express_Dest_Org_Code，
                                                            RCV.receivable_org_code),RCV.receivable_org_code),
                        RCV.SOURCE_BILL_TYPE,
                        RCV.WAYBILL_NO, --运单号
                        rcv.SOURCE_BILL_NO,
                        RCV.payment_type,
                        pod.pod_Type,
                        RCV.BILL_TYPE, --单据子类型
                        RCV.PRODUCT_CODE, --产品类型      
                        rcv.invoice_mark --发票标记       
              )
       WHERE WO_AMOUNT != 0
         AND RCV_AMOUNT != 0;
    commit;
  
    --运单类
    --作为作为辅方
    --签收已核销，计入核销额 
    --反签收已核销 ，取反计入核销额    
    INSERT INTO STV.T_STL_DAILY_BALANCE_tmp
    --ID,期间,组织
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
       CHECK_OUT_ORG_CODE,
       BEGIN_AMOUNT,
       PRODUCE_AMOUNT,
       WRITEOFF_AMOUNT,
       END_AMOUNT,
       PRODUCT_CODE,
       SOURCE_BILL_NO,
       SOURCE_BILL_TYPE,
       STL_TYPE, --结算类型
       BILL_TYPE, --结算类型
       CURRENCY_CODE, --币别
       ACTIVE, --是否有效
       BALANCE_TIME, --结账日期
       CREATE_TIME,
       business_date,
       payment_type,
       invoice_mark--发票标记     
       )
      SELECT SYS_GUID() AS ID,
             V_PERIOD AS CHECK_OUT_PERIOD,
             NVL(CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             CHECK_OUT_ORG_CODE AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             --签收已还款
             case
               when pod_Type = Pkg_Stl_Common.POD__POD_TYPE__POD then
                WO_AMOUNT
               else
                WO_AMOUNT * -1
             end AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型 
             --偏线，以运单为准
             case
               when bill_type = pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --来源单号 
             NVL(SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --结算类型
             BILL_TYPE AS BILL_TYPE, -- 单据子类型
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             --明确记账日期为业务日期 
             account_date as business_date,
             payment_type as payment_type,
             invoice_mark as invoice_mark--发票标记           
        FROM (SELECT RCV.CUSTOMER_CODE, --客户编码  
                     --如果为出发应收，挂账部门为出发部门
                     case
                       when rcv.bill_type =
                            Pkg_Stl_Common.RECEIVABLE_BILL_TYPE_ORIGIN then
                        DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE)
               else
                     DECODE(RCV.PRODUCT_CODE,
                     PKG_STL_COMMON.PRODUCT_CODE_PKG,
                     DECODE(RCV.receivable_org_code,
                     RCV.ORIG_ORG_CODE,
                     RCV.Express_Orig_Org_Code,
                     RCV.DEST_ORG_CODE,
                     RCV.Express_Dest_Org_Code，RCV.receivable_org_code),
                     RCV.receivable_org_code)
                     end AS CHECK_OUT_ORG_CODE, -- 应收组织
                     RCV.WAYBILL_NO, --运单号 
                     MIN(RCV.ACCOUNT_DATE) as ACCOUNT_DATE, --会计日期 
                     RCV.BILL_TYPE, --单据子类型
                     MIN(RCV.AMOUNT) RCV_AMOUNT, --应收额  
                     pod.pod_type as pod_type, --签收类型              
                     SUM(WO.AMOUNT) as WO_AMOUNT, --核销额
                     rcv.payment_type as payment_type, --付款方式
                     rcv.SOURCE_BILL_TYPE as SOURCE_BILL_TYPE, --单据类型
                     rcv.SOURCE_BILL_NO as SOURCE_BILL_NO, --来源单号
                     RCV.PRODUCT_CODE, --产品类型
                     rcv.invoice_mark --发票标记
                FROM STL.T_STL_POD POD
               inner JOIN STL.T_STL_BILL_RECEIVABLE RCV
                  ON RCV.WAYBILL_NO = POD.WAYBILL_NO
                 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
               inner JOIN STL.T_STL_BILL_WRITEOFF WO
              --作为辅方
                  ON (WO.END_NO = RCV.RECEIVABLE_NO)
               WHERE RCV.SOURCE_BILL_TYPE =
                     PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
                    --指定单据类型
                    --单据类型（始发运费、到达运费、空运到付、偏线到付、空运代收货款）
                 and RCV.bill_type in
                     (pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN,
                      pkg_stl_common.RECEIVABLE_BILL_TYPE_DEST,
                      pkg_stl_common.RECEIVABLE_BILL_TYPE_AA,
                      pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL,
                      pkg_stl_common.RECEIVABLE_BILL_TYPE_AAC,
                      pkg_stl_common.RECEIVABLE_BILL_TYPE_DL,
                      pkg_stl_common.RECEIVABLE_BILL_TYPE_DLC)
                 AND WO.ACCOUNT_DATE <= POD.POD_DATE --核销时间于小反签收时间
                 AND POD.POD_DATE >= P_BEGIN_DATE
                 AND POD.POD_DATE < P_END_DATE
                    --只要签收与反签收的数据
                 and pod.pod_Type in
                     (PKG_STL_COMMON.POD__POD_TYPE__POD,
                      PKG_STL_COMMON.POD__POD_TYPE__UPD)
                    --指定核销
                 and wo.ORG_CODE = p_org_code
               GROUP BY RCV.CUSTOMER_CODE, --客户编码
                        DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE),
                        DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,
                             DECODE(RCV.receivable_org_code,RCV.ORIG_ORG_CODE, RCV.Express_Orig_Org_Code,
                                                            RCV.DEST_ORG_CODE,RCV.Express_Dest_Org_Code，
                                                            RCV.receivable_org_code),RCV.receivable_org_code),
                        RCV.SOURCE_BILL_TYPE,
                        RCV.WAYBILL_NO, --运单号
                        rcv.SOURCE_BILL_NO,
                        RCV.payment_type,
                        pod.pod_Type,
                        RCV.BILL_TYPE, --单据子类型
                        RCV.PRODUCT_CODE, --产品类型  
                        rcv.invoice_mark --发票标记          
              )
       WHERE WO_AMOUNT != 0
         AND RCV_AMOUNT != 0;
    commit;
  
    --默认返回成功；
    RETURN TRUE;
  
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE - 1 / (24 * 60 * 60),
                                        'PKG_STL_BALANCE_DEPOSIT_TF.func_daily_detail_writeoff',
                                        P_ORG_CODE || '-预收-中转期末结账核算核销');
      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_BALANCE_DEPOSIT_TF;
/
