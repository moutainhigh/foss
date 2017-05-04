CREATE OR REPLACE PACKAGE PKG_STL_BALANCE_PAYABLE IS

  -- Author  : 000123-foss-huangxiaobo
  -- Created : 2012/11/19 14:36:51
  -- Purpose : 应付期末结算

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
  --计算每日发生额度
  ------------------------------------------------------------------
  FUNCTION FUNC_DAILY_DETAIL_PRODUCE(P_ORG_CODE     STRING, --组织编码
                                     P_BEGIN_DATE   DATE, --起始日期
                                     P_END_DATE     DATE, --结束日期
                                     P_PERIOD       STRING, --结账期间
                                     P_BALANCE_TYPE IN STRING -- 核算类别
                                     ) RETURN BOOLEAN;

  ------------------------------------------------------------------
  --计算每日核销金额
  ------------------------------------------------------------------
  FUNCTION FUNC_DAILY_DETAIL_WRITEOFF(P_ORG_CODE     STRING, --组织编码
                                      P_BEGIN_DATE   DATE, --起始日期
                                      P_END_DATE     DATE, --结束日期
                                      P_PERIOD       STRING, --结账期间
                                      P_BALANCE_TYPE IN STRING -- 核算类别
                                      ) RETURN BOOLEAN;

END PKG_STL_BALANCE_PAYABLE;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_BALANCE_PAYABLE IS

  --Author:000123-foss-huangxiaobo
  --Created:2012-11-19

  ------------------------------------------------------------------
  --应付期末结账（应收期末结账总入口）
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
  
    --计算每日发生额
    V_RESULT := FUNC_DAILY_DETAIL_PRODUCE(P_ORG_CODE,
                                          P_BEGIN_DATE,
                                          P_END_DATE,
                                          P_PERIOD,
                                          P_BALANCE_TYPE);
  
    --发生错误返回
    IF V_RESULT = FALSE THEN
      --返回异常
      RAISE_APPLICATION_ERROR('-20002', ' 结算应付,每日发生额时发生异常！');
    END IF;
  
    --计算每日核销额
    V_RESULT := FUNC_DAILY_DETAIL_WRITEOFF(P_ORG_CODE,
                                           P_BEGIN_DATE,
                                           P_END_DATE,
                                           P_PERIOD,
                                           P_BALANCE_TYPE);
  
    --发生错误返回
    IF V_RESULT = FALSE THEN
      --返回异常
      RAISE_APPLICATION_ERROR('-20002', ' 结算应付,每日核销时发生异常！');
    END IF;
  
    RETURN V_RESULT;
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'pkg_stl_balance_payable.func_count_balance',
                                        P_ORG_CODE || '-应付期末结账调度');
      --返回失败标志
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
  
    --1) 发生——应付其它结账类型（不含劳务费）
    INSERT INTO STV.T_STL_DAILY_BALANCE_TMP
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
       BUSINESS_DATE,
       PAYMENT_TYPE,
       invoice_mark--发票标记    
       )
      SELECT SYS_GUID() AS ID,
             V_PERIOD AS CHECK_OUT_PERIOD,
             NVL(BILL.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             DECODE(BILL.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(BILL.PAYABLE_ORG_CODE,
                           BILL.ORIG_ORG_CODE,
                           BILL.Express_Orig_Org_Code,
                           BILL.DEST_ORG_CODE,
                           BILL.Express_Dest_Org_Code，BILL.PAYABLE_ORG_CODE),
                    BILL.PAYABLE_ORG_CODE) AS PAYABLE_ORG_CODE, --落地配需要转换
             NVL(BILL.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE,
             0 AS BEGIN_AMOUNT,
             NVL(BILL.AMOUNT, 0) AS PRODUCE_AMOUNT,
             0 AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             -- 偏线代理成本(以运单为准，否则使用来源单号)
             case
               when bill_type =
                    PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE then
                NVL(BILL.WAYBILL_NO, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(BILL.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --来源单号
             NVL(SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE, --来源单据类别
             BILL.CURRENCY_CODE AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BALANCE_TYPE AS STL_TYPE,
             BILL.BILL_TYPE AS BILL_TYPE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             BILL.BUSINESS_DATE AS BUSINESS_DATE,
             PKG_STL_COMMON.NULL_VALUE AS PAYMENT_TYPE,
             bill.invoice_mark --发票标记            
        FROM STL.T_STL_BILL_PAYABLE BILL
       WHERE BILL.ACCOUNT_DATE >= P_BEGIN_DATE
         AND BILL.ACCOUNT_DATE < P_END_DATE
         AND BILL.PAYABLE_ORG_CODE = P_ORG_CODE
         AND BILL.BILL_TYPE IN (PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR, -- 航空公司运费
                                PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_ORIGINAL, -- 空运出发代理应付
                                PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY, -- 空运到达代理应付
                                PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER, -- 空运其他应付
                                PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE, -- 偏线代理成本
                                PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM, -- 理赔应付
                                PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND, -- 退运费应付
                                PKG_STL_COMMON.PAYABLE_BILL_TYPE_COMPENSATION, -- 服务补救应付
                                PKG_STL_COMMON.PAYABLE_BILL_TYPE_LANDSTOWAGE, --落地配外发成本
                                PKG_STL_COMMON.PAYABLE_BILLTYPE_LAND_OTHER, --落地配其他应付
                                PKG_STL_COMMON.PAYABLE_BILL_TYPE_PO --偏线其它应付
                                );
    commit;
  
    --2） 发生——空运代收货款(
    INSERT INTO STV.T_STL_DAILY_BALANCE_TMP
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
       BUSINESS_DATE,
       PAYMENT_TYPE,
       invoice_mark--发票标记   
       )
      SELECT SYS_GUID() AS ID,
             V_PERIOD AS CHECK_OUT_PERIOD,
             NVL(PAY.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PAY.PAYABLE_ORG_CODE,
                           PAY.ORIG_ORG_CODE,
                           PAY.Express_Orig_Org_Code,
                           PAY.DEST_ORG_CODE,
                           PAY.Express_Dest_Org_Code，PAY.PAYABLE_ORG_CODE),
                    PAY.PAYABLE_ORG_CODE) AS PAYABLE_ORG_CODE, --落地配需要转换
             NVL(PAY.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE,
             0 AS BEGIN_AMOUNT,
             PAY.AMOUNT *
             DECODE(POD.POD_TYPE, PKG_STL_COMMON.POD__POD_TYPE__POD, 1, -1),
             0 AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(PAY.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO, --来源单号
             NVL(PAY.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE, --来源单据类别
             --币别
             PAY.Currency_Code         AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE     AS ACTIVE,
             P_BALANCE_TYPE            AS STL_TYPE,
             PAY.BILL_TYPE             AS BILL_TYPE,
             P_BEGIN_DATE              AS BALANCE_TIME,
             SYSDATE                   AS CREATE_TIME,
             PAY.BUSINESS_DATE         AS BUSINESS_DATE,
             PKG_STL_COMMON.NULL_VALUE AS PAYMENT_TYPE,
             pay.invoice_mark as invoice_mark--发票标记             
        FROM STL.T_STL_POD POD
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.WAYBILL_NO = POD.WAYBILL_NO
       WHERE POD.POD_TYPE IN
             (PKG_STL_COMMON.POD__POD_TYPE__POD,
              PKG_STL_COMMON.POD__POD_TYPE__UPD)
         AND POD.POD_DATE >= P_BEGIN_DATE
         AND POD.POD_DATE < P_END_DATE
         AND PAY.ACCOUNT_DATE < POD.POD_DATE
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC
         AND PAY.PRODUCT_CODE IN
             (PKG_STL_COMMON.PRODUCT_CODE_AF,
              PKG_STL_COMMON.PRODUCT_CODE_PKG) --落地配
         AND PAY.PAYABLE_ORG_CODE = P_ORG_CODE;
    commit;
  
    --3） 发生——专线应付代收货款
    INSERT INTO STV.T_STL_DAILY_BALANCE_TMP
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
       BUSINESS_DATE,
       PAYMENT_TYPE,
       invoice_mark--发票标记     
       )
      SELECT SYS_GUID() AS ID,
             V_PERIOD AS CHECK_OUT_PERIOD,
             NVL(RCV.DELIVERY_CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             RCV.ORIG_ORG_CODE AS PAYABLE_ORG_CODE,
             NVL(RCV.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE,
             0 AS BEGIN_AMOUNT,
             WO.AMOUNT AS PRODUCE_AMOUNT,
             0 AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(RCV.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO, --来源单号
             NVL(RCV.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE, --来源单据类别
             RCV.CURRENCY_CODE AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BALANCE_TYPE AS STL_TYPE,
             PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC AS BILL_TYPE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             RCV.BUSINESS_DATE AS BUSINESS_DATE,
             PKG_STL_COMMON.NULL_VALUE AS PAYMENT_TYPE,
             rcv.invoice_mark as invoice--发票标记           
        FROM STL.T_STL_BILL_WRITEOFF WO
      --还款单
       INNER JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
         AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      --应收单
       INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      --还款冲应收
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_COD
            --还款日期
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND RCV.PRODUCT_CODE <> PKG_STL_COMMON.PRODUCT_CODE_PKG
            --核销组织
         AND wo.org_code = P_ORG_CODE;
  
    COMMIT;
  
    RETURN TRUE;
  
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'pkg_stl_balance_payable.FUNC_DAILY_DETAIL_PRODUCE',
                                        P_ORG_CODE || '-应付期末结账核算发生额');
    
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
  
    --1) 核销——应付其它结账类型——付款
    INSERT INTO STV.T_STL_DAILY_BALANCE_TMP
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
       BUSINESS_DATE,
       PAYMENT_TYPE,
       invoice_mark--发票标记        
       )
      SELECT SYS_GUID() AS ID,
             V_PERIOD AS CHECK_OUT_PERIOD,
             NVL(PAY.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             --2013-8-12 统一使用应付部门
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PAY.PAYABLE_ORG_CODE,
                           PAY.ORIG_ORG_CODE,
                           PAY.Express_Orig_Org_Code,
                           PAY.DEST_ORG_CODE,
                           PAY.Express_Dest_Org_Code，PAY.PAYABLE_ORG_CODE),
                    PAY.PAYABLE_ORG_CODE) AS CHECK_OUT_ORG_CODE, --落地配需要转换
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             --红单取反，非红单去正
             PMTD.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1),
             0 AS END_AMOUNT,
             NVL(PAY.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型
             -- 偏线代理成本(以运单为准，否则使用来源单号)
             case
               when pay.bill_type =
                    PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE then
                NVL(pay.WAYBILL_NO, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(pay.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --来源单号
             NVL(PAY.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --结算类型
             PAY.BILL_TYPE AS BILL_TYPE, -- 单据子类型
             PAY.CURRENCY_CODE AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             PAY.BUSINESS_DATE AS BUSINESS_DATE,
             PKG_STL_COMMON.NULL_VALUE AS PAYMENT_TYPE,
             pay.invoice_mark as invoice_mark --发票标记             
      --付款单关联应付单
        FROM STL.T_STL_BILL_PAYMENT PMT
        JOIN STL.T_STL_BILL_PAYMENT_D PMTD
          ON PMTD.PAYMENT_NO = PMT.PAYMENT_NO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = PMTD.SOURCE_BILL_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE PMT.PAYMENT_ORG_CODE = P_ORG_CODE
         AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE --核销日期
         AND PMT.ACCOUNT_DATE < P_END_DATE
         AND PAY.BILL_TYPE IN (PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR, -- 航空公司运费
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_ORIGINAL, -- 空运出发代理应付
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY, -- 空运到达代理应付
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER, -- 空运其他应付
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE, -- 偏线代理成本
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM, -- 理赔应付
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND, -- 退运费应付
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_COMPENSATION, --服务补救
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_LANDSTOWAGE, --落地配外发成本
                               PKG_STL_COMMON.PAYABLE_BILLTYPE_LAND_OTHER, --落地配其他应付
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_PO --偏线其它应付
                               );
    commit;
  
    --2) 核销——应付其它结账类型——冲销(应收冲应付,预付冲应付)
    INSERT INTO STV.T_STL_DAILY_BALANCE_TMP
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
       BILL_TYPE, -- 结算单据类型
       CURRENCY_CODE, --币别
       ACTIVE, --是否有效
       BALANCE_TIME, --结账日期
       CREATE_TIME,
       BUSINESS_DATE,
       PAYMENT_TYPE,       
       invoice_mark --发票标记    
       )    
      SELECT SYS_GUID() AS ID,
             V_PERIOD AS CHECK_OUT_PERIOD,
             NVL(PAY.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             --2013-8-12 统一使用应付部门
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PAY.PAYABLE_ORG_CODE,
                           PAY.ORIG_ORG_CODE,
                           PAY.Express_Orig_Org_Code,
                           PAY.DEST_ORG_CODE,
                           PAY.Express_Dest_Org_Code，PAY.PAYABLE_ORG_CODE),
                    PAY.PAYABLE_ORG_CODE) AS CHECK_OUT_ORG_CODE, --落地配需要转换
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             WO.AMOUNT AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(PAY.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型
             -- 偏线代理成本(以运单为准，否则使用来源单号)
             case
               when pay.bill_type =
                    PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE then
                NVL(pay.WAYBILL_NO, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(pay.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --来源单号
             NVL(PAY.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --结算类型
             PAY.BILL_TYPE AS BILL_TYPE, -- 单据子类型
             PAY.CURRENCY_CODE AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             PAY.BUSINESS_DATE AS BUSINESS_DATE,
             PKG_STL_COMMON.NULL_VALUE AS PAYMENT_TYPE,
             pay.invoice_mark as invoice_mark--发票标记           
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --辅助单号
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.ORG_CODE = P_ORG_CODE --核销部门
         AND WO.WRITEOFF_TYPE IN
             (PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP,
              PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_AP) --应收冲应付、预付冲应付
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE --核销日期
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PAY.BILL_TYPE IN (PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR, -- 航空公司运费
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_ORIGINAL, -- 空运出发代理应付
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY, -- 空运到达代理应付
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER, -- 空运其他应付
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE, -- 偏线代理成本
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM, -- 理赔应付
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND, -- 退运费应付
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_COMPENSATION, --服务补救应付
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_LANDSTOWAGE, --落地配外发成本
                               PKG_STL_COMMON.PAYABLE_BILLTYPE_LAND_OTHER, --落地配其他应付
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_PO --偏线其它应付
                               );
  
    commit;
  
    --3) 核销——应付代收货款（空运、专线）
    INSERT INTO STV.T_STL_DAILY_BALANCE_TMP
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
       BILL_TYPE, -- 结算单据类型
       CURRENCY_CODE, --币别
       ACTIVE, --是否有效
       BALANCE_TIME, --结账日期
       CREATE_TIME,
       BUSINESS_DATE,
       PAYMENT_TYPE,
       invoice_mark--发票标记    
       )
      SELECT SYS_GUID() AS ID,
             V_PERIOD AS CHECK_OUT_PERIOD,
             NVL(PAY.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             --2013-8-12统一使用应付部门
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PAY.PAYABLE_ORG_CODE,
                           PAY.ORIG_ORG_CODE,
                           PAY.Express_Orig_Org_Code,
                           PAY.DEST_ORG_CODE,
                           PAY.Express_Dest_Org_Code，PAY.PAYABLE_ORG_CODE),
                    PAY.PAYABLE_ORG_CODE) AS CHECK_OUT_ORG_CODE, --落地配需要转换
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             WO.AMOUNT AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(PAY.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型
             NVL(PAY.WAYBILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO, --来源单号
             NVL(PAY.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --结算类型
             PAY.BILL_TYPE AS BILL_TYPE, -- 单据子类型
             PAY.CURRENCY_CODE AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             PAY.BUSINESS_DATE AS BUSINESS_DATE,
             PKG_STL_COMMON.NULL_VALUE AS PAYMENT_TYPE,
             pay.invoice_mark as invoice_mark--发票标记            
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE IN
             (PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP,
              PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_AP,
              PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_PP)
         AND WO.ORG_CODE = P_ORG_CODE
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    COMMIT;
  
    --默认返回成功；
    RETURN TRUE;
  
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_BALANCE_PAYABLE.FUNC_DAILY_DETAIL_WRITEOFF',
                                        P_ORG_CODE || '-应付期末结账核算核销发生额');
      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_BALANCE_PAYABLE;
/
