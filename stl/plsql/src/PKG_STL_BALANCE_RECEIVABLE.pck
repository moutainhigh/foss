CREATE OR REPLACE PACKAGE PKG_STL_BALANCE_RECEIVABLE IS

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

END PKG_STL_BALANCE_RECEIVABLE;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_BALANCE_RECEIVABLE IS

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
      RAISE_APPLICATION_ERROR('-20002', '结算应收，发生额发生异常');
    END IF;
  
    --计算核销额
    V_RESULT := FUNC_DAILY_DETAIL_WRITEOFF(P_ORG_CODE,
                                           P_BEGIN_DATE,
                                           P_END_DATE,
                                           P_PERIOD,
                                           P_BALANCE_TYPE);
  
    --发生错误返回
    IF V_RESULT = FALSE THEN
      RAISE_APPLICATION_ERROR('-20002', '结算应收，核销发生异常');
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
                                        P_ORG_CODE || '-应收期末结账调度');
    
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
  
    --异常签收记录相关
    CURSOR CUR_STOCK_EX IS
      SELECT distinct t.waybill_no, t.create_time
        FROM STL.T_STL_OUT_STOCK_EXCEPTION T
       inner join stl.t_stl_bill_receivable bill
          on bill.waybill_no = t.waybill_no
      --始发应收
       WHERE bill.bill_type = Pkg_Stl_Common.RECEIVABLE_BILL_TYPE_ORIGIN
            --非红单
         and is_red_back = PKG_STL_COMMON.NO
            --应收部门
         and bill.receivable_org_code = P_ORG_CODE
            --创建时间
         and t.create_time >= P_BEGIN_DATE
         and t.create_time < P_END_DATE;
  
    V_STOCK_EX_ROW STl.T_STL_OUT_STOCK_EXCEPTION%ROWTYPE;
  
    --签收记录相关  
    CURSOR CUR_POD IS
      SELECT pod.waybill_no, pod.pod_type, pod.pod_date
        FROM Stv.t_Stl_Pod_Rec pod
       WHERE pod.receivable_org_code = P_ORG_CODE;
  
    V_POD_ROW STV.t_Stl_Pod_Rec%ROWTYPE;
  
  BEGIN
    --获得记账期间
    V_PERIOD := P_PERIOD;
  
    --非运单 
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
       invoice_mark --发票标记   
       )
      SELECT SYS_GUID() AS ID, --业务ID
             V_PERIOD AS CHECK_OUT_PERIOD, -- 结账期间
             NVL(BILL.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE, --客户编码
             DECODE(BILL.PRODUCT_CODE,
              PKG_STL_COMMON.PRODUCT_CODE_PKG,
              DECODE(BILL.receivable_org_code,
                     BILL.ORIG_ORG_CODE,
                     BILL.Express_Orig_Org_Code,
                     BILL.DEST_ORG_CODE,
                     BILL.Express_Dest_Org_Code，BILL.receivable_org_code),
              BILL.receivable_org_code) AS CHECK_OUT_ORG_CODE, -- 应收组织
             NVL(BILL.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型
             0 AS BEGIN_AMOUNT, --期初金额
             BILL.AMOUNT AS PRODUCE_AMOUNT, --发生额
             0 AS WRITEOFF_AMOUNT, --核销金额
             0 AS END_AMOUNT, --期末金额
             --如果单据类型为偏线时，使用运单号，其他不变 
             case
               when BILL.BILL_TYPE =
                    PKG_STL_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(bill.waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(bill.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --来源单号 
             SOURCE_BILL_TYPE AS SOURCE_BILL_TYPE, --来源单据类别
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE, --币别
             PKG_STL_COMMON.ACTIVE AS ACTIVE, --有效性
             P_BALANCE_TYPE AS STL_TYPE, --结算类型
             BILL.BILL_TYPE AS BILL_TYPE, --单据类型
             P_BEGIN_DATE AS BALANCE_TIME, --结账业务日期
             SYSDATE AS CREATE_TIME, --结账时间
             bill.account_date as business_date,
             bill.payment_type as payment_type,
             bill.invoice_mark as invoice_mark --发票标记          
        FROM STL.T_STL_BILL_RECEIVABLE BILL
      --目前按照记账日期 
       WHERE BILL.Account_Date >= P_BEGIN_DATE
         AND BILL.Account_Date < P_END_DATE
            --应收组织
         AND BILL.RECEIVABLE_ORG_CODE = P_ORG_CODE
            --单据类型（空运其他应收、小票应收 ,落地配其他应收）
         and bill.bill_type in
             (pkg_stl_common.RECEIVABLE_BILL_TYPE_AR,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_REVENUE,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_LR,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_PR --偏线其它应收
                            );
    commit;
  
    --来源类别：运单
    --单据类型：始发应收
    --有异常签收
    --也算：已签收的应收
    FOR V_STOCK_EX_ROW IN CUR_STOCK_EX LOOP
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
         invoice_mark -- 发票标记        
         )
        SELECT SYS_GUID() AS ID,
               V_PERIOD AS CHECK_OUT_PERIOD,
               NVL(CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
               CHECK_OUT_ORG_CODE,
               0 AS BEGIN_AMOUNT,
               --应收总金额 - 累计核销金额
               --已签收 取正 
               RCV_AMOUNT - WO_AMOUNT as PRODUCE_AMOUNT,
               0 AS WRITEOFF_AMOUNT,
               0 AS END_AMOUNT,
               NVL(PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型
               --如果单据类型为偏线时，使用运单号，其他不变 
               case
                 when BILL_TYPE = PKG_STL_common.RECEIVABLE_BILL_TYPE_PARTIAL then
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
               ACCOUNT_DATE as business_date,
               payment_type as payment_type,
               invoice_mark as invoice_mark--发票标记             
          FROM (SELECT rcv.CUSTOMER_CODE, --客户编码
                       --使用出发部门
                       DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE) as CHECK_OUT_ORG_CODE, --应收组织
                       RCV.CUSTOMER_TYPE, --客户类型
                       RCV.WAYBILL_NO, --运单号
                       rcv.SOURCE_BILL_NO, --来源单号
                       rcv.SOURCE_BILL_TYPE, --来源单据类别
                       RCV.RECEIVABLE_NO, --单据编号
                       RCV.ACCOUNT_DATE, --会计日期
                       RCV.BILL_TYPE, --单据子类型
                       rcv.payment_type,
                       RCV.AMOUNT RCV_AMOUNT, --金额
                       NVL((SELECT SUM(WO.AMOUNT)
                             FROM STL.T_STL_BILL_WRITEOFF WO
                            WHERE (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
                                  WO.END_NO = RCV.RECEIVABLE_NO)
                              AND WO.ACCOUNT_DATE <=
                                  V_STOCK_EX_ROW.create_time),
                           0) WO_AMOUNT,
                       RCV.PRODUCT_CODE, --产品类型 
                       rcv.invoice_mark as invoice_mark--发票标记                  
                  FROM STL.T_STL_BILL_RECEIVABLE RCV
                --出发部门
                 where rcv.receivable_org_code = p_org_code
                   and RCV.ID IN
                       (SELECT ID
                          FROM (SELECT ID, IS_RED_BACK, BILL_TYPE
                                  FROM (SELECT *
                                          FROM STL.T_STL_BILL_RECEIVABLE T
                                         WHERE T.WAYBILL_NO =
                                               V_STOCK_EX_ROW.WAYBILL_NO
                                           AND T.SOURCE_BILL_TYPE =
                                               PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W
                                           AND T.ACCOUNT_DATE <
                                               V_STOCK_EX_ROW.create_time
                                              --出发运费
                                           AND T.BILL_TYPE =
                                               pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN
                                         ORDER BY T.ACCOUNT_DATE DESC,
                                                  T.CREATE_TIME  DESC,
                                                  t.IS_RED_BACK  asc)
                                 WHERE ROWNUM <= 1)
                         WHERE IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
                              --出发运费
                           AND bill_type =
                               pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN))
         WHERE RCV_AMOUNT <> WO_AMOUNT
           AND RCV_AMOUNT <> 0;
      commit;
    end loop;
  
    --运单类
    --签收未核销（应收总额-累计核销额） 作为发生额
    --反签收 未核销  （应收总额-累计核销额） × -1 作为发生额
    --出发运费
    FOR V_POD_ROW IN CUR_POD LOOP
    
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
         invoice_mark --发票标记        
         )
        SELECT SYS_GUID() AS ID,
               V_PERIOD AS CHECK_OUT_PERIOD,
               NVL(CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
               CHECK_OUT_ORG_CODE,
               0 AS BEGIN_AMOUNT,
               --应收总金额 - 累计核销金额
               --已签收 取整
               case
                 when V_POD_ROW.POD_TYPE = Pkg_Stl_Common.POD__POD_TYPE__POD then
                  RCV_AMOUNT - WO_AMOUNT
                 else
                 --反签收 取反
                  (RCV_AMOUNT - WO_AMOUNT) * -1
               end as PRODUCE_AMOUNT,
               
               0 AS WRITEOFF_AMOUNT,
               0 AS END_AMOUNT,
               NVL(PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型
               --如果单据类型为偏线时，使用运单号，其他不变 
               case
                 when BILL_TYPE = PKG_STL_common.RECEIVABLE_BILL_TYPE_PARTIAL then
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
               ACCOUNT_DATE as business_date,
               payment_type as payment_type,
               invoice_mark as invoice_mark--发票标记           
          FROM (SELECT rcv.CUSTOMER_CODE, --客户编码
                       --使用出发部门
                       DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE) as CHECK_OUT_ORG_CODE, --应收组织
                       RCV.CUSTOMER_TYPE, --客户类型
                       RCV.WAYBILL_NO, --运单号
                       rcv.SOURCE_BILL_NO, --来源单号
                       rcv.SOURCE_BILL_TYPE, --来源单据类别
                       RCV.RECEIVABLE_NO, --单据编号
                       RCV.ACCOUNT_DATE, --会计日期
                       RCV.BILL_TYPE, --单据子类型
                       rcv.payment_type,
                       RCV.AMOUNT RCV_AMOUNT, --金额
                       NVL((SELECT SUM(WO.AMOUNT)
                             FROM STL.T_STL_BILL_WRITEOFF WO
                            WHERE (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
                                  WO.END_NO = RCV.RECEIVABLE_NO)
                              AND WO.ACCOUNT_DATE <= V_POD_ROW.Pod_Date),
                           0) WO_AMOUNT,
                       RCV.PRODUCT_CODE, --产品类型 
                       rcv.invoice_mark --发票标记
                  FROM STL.T_STL_BILL_RECEIVABLE RCV
                --出发部门
                 where rcv.receivable_org_code = p_org_code
                   and RCV.ID IN
                       (SELECT ID
                          FROM (SELECT ID, IS_RED_BACK, BILL_TYPE
                                  FROM (SELECT *
                                          FROM STL.T_STL_BILL_RECEIVABLE T
                                         WHERE T.WAYBILL_NO =
                                               V_POD_ROW.WAYBILL_NO
                                           AND T.SOURCE_BILL_TYPE =
                                               PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W
                                           AND T.ACCOUNT_DATE <
                                               V_POD_ROW.POD_DATE
                                              --出发运费
                                           AND T.BILL_TYPE =
                                               pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN
                                         ORDER BY T.ACCOUNT_DATE DESC,
                                                  T.CREATE_TIME  DESC,
                                                  t.IS_RED_BACK  asc)
                                 WHERE ROWNUM <= 1)
                         WHERE IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
                              --出发运费
                           AND bill_type =
                               pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN))
         WHERE RCV_AMOUNT <> WO_AMOUNT
           AND RCV_AMOUNT <> 0;
    
      commit;
    
      --运单类
      --签收未核销（应收总额-累计核销额） 作为发生额
      --反签收 未核销  （应收总额-累计核销额） × -1 作为发生额
      --到达 - 到付运费     
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
               --应收总金额 - 累计核销金额
               --已签收 取整
               case
                 when V_POD_ROW.POD_TYPE = Pkg_Stl_Common.POD__POD_TYPE__POD then
                  RCV_AMOUNT - WO_AMOUNT
                 else
                 --反签收 取反
                  (RCV_AMOUNT - WO_AMOUNT) * -1
               end as PRODUCE_AMOUNT,
               
               0 AS WRITEOFF_AMOUNT,
               0 AS END_AMOUNT,
               NVL(PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型
               --如果单据类型为偏线时，使用运单号，其他不变 
               case
                 when BILL_TYPE =
                      PKG_STL_common.RECEIVABLE_BILL_TYPE_PARTIAL then
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
               ACCOUNT_DATE as business_date,
               payment_type as payment_type,
               invoice_mark as invoice_mark--发票标记             
          FROM (SELECT rcv.CUSTOMER_CODE, --客户编码
                       DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.Express_Dest_Org_Code，RCV.dest_org_code) as CHECK_OUT_ORG_CODE, --应收组织
                       RCV.CUSTOMER_TYPE, --客户类型
                       RCV.WAYBILL_NO, --运单号
                       rcv.SOURCE_BILL_NO, --来源单号
                       rcv.SOURCE_BILL_TYPE, --来源单据类别
                       RCV.RECEIVABLE_NO, --单据编号
                       RCV.ACCOUNT_DATE, --会计日期
                       RCV.BILL_TYPE, --单据子类型
                       rcv.payment_type,
                       RCV.AMOUNT RCV_AMOUNT, --金额
                       NVL((SELECT SUM(WO.AMOUNT)
                             FROM STL.T_STL_BILL_WRITEOFF WO
                            WHERE (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
                                  WO.END_NO = RCV.RECEIVABLE_NO)
                              AND WO.ACCOUNT_DATE <= V_POD_ROW.Pod_Date),
                           0) WO_AMOUNT,
                       RCV.PRODUCT_CODE, --产品类型 
                       rcv.invoice_mark --发票标记
                  FROM STL.T_STL_BILL_RECEIVABLE RCV
                --到达部门
                 where rcv.receivable_org_code = p_org_code
                   and RCV.ID IN
                       (SELECT ID
                          FROM (SELECT ID, IS_RED_BACK, BILL_TYPE
                                  FROM (SELECT *
                                          FROM STL.T_STL_BILL_RECEIVABLE T
                                         WHERE T.WAYBILL_NO =
                                               V_POD_ROW.WAYBILL_NO
                                           AND T.SOURCE_BILL_TYPE =
                                               PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W
                                           AND T.ACCOUNT_DATE <
                                               V_POD_ROW.POD_DATE
                                           AND T.BILL_TYPE in
                                              --到付运费
                                               (pkg_stl_common.RECEIVABLE_BILL_TYPE_DEST,
                                                pkg_stl_common.RECEIVABLE_BILL_TYPE_AA,
                                                pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL,
                                                pkg_stl_common.RECEIVABLE_BILL_TYPE_DL)
                                         ORDER BY T.ACCOUNT_DATE DESC,
                                                  T.CREATE_TIME  DESC,
                                                  t.IS_RED_BACK  asc)
                                 WHERE ROWNUM <= 1)
                         WHERE IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
                              --到付运费
                           AND bill_type in
                               (pkg_stl_common.RECEIVABLE_BILL_TYPE_DEST,
                                pkg_stl_common.RECEIVABLE_BILL_TYPE_AA,
                                pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL,
                                pkg_stl_common.RECEIVABLE_BILL_TYPE_DL)))
         WHERE RCV_AMOUNT <> WO_AMOUNT
           AND RCV_AMOUNT <> 0;
    
      commit;
    
      --运单类
      --签收未核销（应收总额-累计核销额） 作为发生额
      --反签收 未核销  （应收总额-累计核销额） × -1 作为发生额
      --到达 - 空运代收货款     
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
         invoice_mark --发票标记        
         )
        SELECT SYS_GUID() AS ID,
               V_PERIOD AS CHECK_OUT_PERIOD,
               NVL(CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
               CHECK_OUT_ORG_CODE AS CHECK_OUT_ORG_CODE,
               0 AS BEGIN_AMOUNT,
               --应收总金额 - 累计核销金额
               --已签收 取整
               case
                 when V_POD_ROW.POD_TYPE = Pkg_Stl_Common.POD__POD_TYPE__POD then
                  RCV_AMOUNT - WO_AMOUNT
                 else
                 --反签收 取反
                  (RCV_AMOUNT - WO_AMOUNT) * -1
               end as PRODUCE_AMOUNT,
               
               0 AS WRITEOFF_AMOUNT,
               0 AS END_AMOUNT,
               NVL(PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型
               --如果单据类型为偏线时，使用运单号，其他不变 
               case
                 when BILL_TYPE =
                      PKG_STL_common.RECEIVABLE_BILL_TYPE_PARTIAL then
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
               ACCOUNT_DATE as business_date,
               payment_type as payment_type,
               invoice_mark as invoice_mark--发票标记              
          FROM (SELECT rcv.CUSTOMER_CODE, --客户编码
                       DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.Express_Dest_Org_Code，RCV.dest_org_code) as CHECK_OUT_ORG_CODE, --应收组织
                       RCV.CUSTOMER_TYPE, --客户类型
                       RCV.WAYBILL_NO, --运单号
                       rcv.SOURCE_BILL_NO, --来源单号
                       rcv.SOURCE_BILL_TYPE, --来源单据类别
                       RCV.RECEIVABLE_NO, --单据编号
                       RCV.ACCOUNT_DATE, --会计日期
                       RCV.BILL_TYPE, --单据子类型
                       rcv.payment_type,
                       RCV.AMOUNT RCV_AMOUNT, --金额
                       NVL((SELECT SUM(WO.AMOUNT)
                             FROM STL.T_STL_BILL_WRITEOFF WO
                            WHERE (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
                                  WO.END_NO = RCV.RECEIVABLE_NO)
                              AND WO.ACCOUNT_DATE <= V_POD_ROW.Pod_Date),
                           0) WO_AMOUNT,
                       RCV.PRODUCT_CODE, --产品类型
                       rcv.invoice_mark --发票标记 
                  FROM STL.T_STL_BILL_RECEIVABLE RCV
                --按到达部门
                 where rcv.receivable_org_code = p_org_code
                   and RCV.ID IN
                       (SELECT ID
                          FROM (SELECT ID, IS_RED_BACK, BILL_TYPE
                                  FROM (SELECT *
                                          FROM STL.T_STL_BILL_RECEIVABLE T
                                         WHERE T.WAYBILL_NO =
                                               V_POD_ROW.WAYBILL_NO
                                           AND T.SOURCE_BILL_TYPE =
                                               PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W
                                           AND T.ACCOUNT_DATE <
                                               V_POD_ROW.POD_DATE
                                              --空运代收货款
                                           AND T.BILL_TYPE in
                                               (pkg_stl_common.RECEIVABLE_BILL_TYPE_AAC,
                                                pkg_stl_common.RECEIVABLE_BILL_TYPE_COD,
                                                pkg_stl_common.RECEIVABLE_BILL_TYPE_DLC)
                                              --空运产品
                                           and t.product_code IN(
                                               pkg_stl_common.PRODUCT_CODE_AF,
                                               pkg_stl_common.PRODUCT_CODE_PKG)
                                         ORDER BY T.ACCOUNT_DATE DESC,
                                                  T.CREATE_TIME  DESC,
                                                  t.IS_RED_BACK  asc)
                                 WHERE ROWNUM <= 1)
                         WHERE IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
                              --空运代收货款
                           AND BILL_TYPE in
                               (pkg_stl_common.RECEIVABLE_BILL_TYPE_AAC,
                                pkg_stl_common.RECEIVABLE_BILL_TYPE_COD,
                                pkg_stl_common.RECEIVABLE_BILL_TYPE_DLC)
                              --空运产品

                           and product_code in (pkg_stl_common.PRODUCT_CODE_AF,
                                                pkg_stl_common.PRODUCT_CODE_PKG)))
         WHERE RCV_AMOUNT <> WO_AMOUNT
           AND RCV_AMOUNT <> 0;
    
      commit;
    end loop;
  
    RETURN TRUE;
  
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE - 1 / (24 * 60 * 60),
                                        'PKG_STL_Balance_RECEIVABLE.func_daily_detail_produce',
                                        P_ORG_CODE || '-应收期末结账核算发生额');
    
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
  
    --非运单
    --获得应收冲应付的核销记录 
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
       invoice_mark --发票标记
     
       )
      SELECT SYS_GUID() AS ID,
             V_PERIOD AS CHECK_OUT_PERIOD,
             NVL(b.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
              DECODE(B.PRODUCT_CODE,
                     PKG_STL_COMMON.PRODUCT_CODE_PKG,
              DECODE(B.receivable_org_code,
                     B.ORIG_ORG_CODE,
                     B.Express_Orig_Org_Code,
                     B.DEST_ORG_CODE,
                     B.Express_Dest_Org_Code，B.receivable_org_code),
              B.receivable_org_code) AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SBW.AMOUNT AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(B.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型
             --如果单据类型为偏线时，使用运单号，其他不变 
             case
               when b.BILL_TYPE =
                    PKG_STL_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(b.waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(b.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --来源单号 
             NVL(B.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --结算类型
             B.BILL_TYPE AS BILL_TYPE, -- 单据子类型
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             b.account_date as business_date,
             b.payment_type as payment_type,
             b.invoice_mark as invoice_mark--发票标记           
        FROM STL.T_STL_BILL_WRITEOFF SBW
      --关联应收单
       INNER JOIN STL.T_STL_BILL_RECEIVABLE B
          ON B.RECEIVABLE_NO = SBW.BEGIN_No --起始应收单号  
            --非红单
         and b.is_red_back = PKG_STL_COMMON.NO
       WHERE SBW.ORG_CODE = P_ORG_CODE --核销部门
         AND SBW.ACCOUNT_DATE >= P_BEGIN_DATE --核销日期
         AND SBW.ACCOUNT_DATE < P_END_DATE
            --非运单
            --单据类型（空运其他应收、小票应收 ）
         and b.bill_type in
             (pkg_stl_common.RECEIVABLE_BILL_TYPE_AR,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_REVENUE,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_LR,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_PR --偏线其它应收
              );
    commit;
  
    --获得预收冲应收、还款冲应收、理赔冲应收等应收单作为endbill进行核销
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
       BILL_TYPE, -- 结算单据类型
       CURRENCY_CODE, --币别
       ACTIVE, --是否有效
       BALANCE_TIME, --结账日期
       CREATE_TIME,
       business_date,
       payment_type,
       invoice_mark --发票标记   
       )
      SELECT SYS_GUID() AS ID,
             V_PERIOD AS CHECK_OUT_PERIOD,
             NVL(b.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             DECODE(B.PRODUCT_CODE,
                     PKG_STL_COMMON.PRODUCT_CODE_PKG,
              DECODE(B.receivable_org_code,
                     B.ORIG_ORG_CODE,
                     B.Express_Orig_Org_Code,
                     B.DEST_ORG_CODE,
                     B.Express_Dest_Org_Code，B.receivable_org_code),
              B.receivable_org_code) AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SBW.AMOUNT AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(B.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型
             --如果单据类型为偏线时，使用运单号，其他不变 
             case
               when b.BILL_TYPE =
                    PKG_STL_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(b.waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(b.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --来源单号 
             NVL(B.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --结算类型
             B.BILL_TYPE AS BILL_TYPE,
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             b.account_date as business_date,
             b.payment_type as payment_type,
             b.invoice_mark as invoice_mark--发票标记            
        FROM STL.T_STL_BILL_WRITEOFF SBW
      --关联应收单
       INNER JOIN STL.T_STL_BILL_RECEIVABLE B
          ON B.RECEIVABLE_NO = SBW.END_No --起始应收单号 --辅助单号 
            --非红单
         and b.is_red_back = PKG_STL_COMMON.NO
       WHERE SBW.ORG_CODE = P_ORG_CODE --核销部门
         AND SBW.ACCOUNT_DATE >= P_BEGIN_DATE --核销日期
         AND SBW.ACCOUNT_DATE < P_END_DATE
            --非运单
            --单据类型（空运其他应收、小票应收 ）
         and b.bill_type in
             (pkg_stl_common.RECEIVABLE_BILL_TYPE_AR,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_REVENUE,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_LR,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_PR --偏线其它应收
              );
    commit;
  
    --运单类
    --核销已签收 （还款额进入核销额）
    --正常签收
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
       invoice_mark --发票标记
      
       )
      SELECT SYS_GUID() AS ID,
             V_PERIOD AS CHECK_OUT_PERIOD,
             NVL(b.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             --如果始发应收，始发出发部门
             case
               when b.bill_type = pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN then
                DECODE(B.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,B.EXPRESS_ORIG_ORG_CODE，B.ORIG_ORG_CODE)
               else
                DECODE(B.PRODUCT_CODE,
                     PKG_STL_COMMON.PRODUCT_CODE_PKG,
                     DECODE(B.receivable_org_code,
                     B.ORIG_ORG_CODE,
                     B.Express_Orig_Org_Code,
                     B.DEST_ORG_CODE,
                     B.Express_Dest_Org_Code，B.receivable_org_code),
                     B.receivable_org_code)
             end AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SBW.AMOUNT AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(B.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型
             --如果单据类型为偏线时，使用运单号，其他不变 
             case
               when b.BILL_TYPE =
                    PKG_STL_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(b.waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(b.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --来源单号 
             NVL(B.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --结算类型
             B.BILL_TYPE AS BILL_TYPE, -- 单据子类型
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             b.account_date as business_date,
             b.payment_type as payment_type,
             b.invoice_mark as invoice_mark--发票标记            
        FROM STL.T_STL_BILL_WRITEOFF sbw
       inner JOIN STL.T_STL_BILL_RECEIVABLE b
          ON b.RECEIVABLE_NO = sbw.BEGIN_NO
         AND b.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = b.WAYBILL_NO
                 AND T.POD_DATE < sbw.ACCOUNT_DATE) = 1 --已签收
         AND sbw.ACCOUNT_DATE >= P_BEGIN_DATE
         and sbw.ACCOUNT_DATE < P_END_DATE
            --按核销组织过滤
         and SBW.ORG_CODE = p_org_code
            --单据类型（始发运费、到达运费、空运到付、偏线到付、空运代收货款、落地配）
         and b.bill_type in
             (pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DEST,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_AA,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_AAC,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DL,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DLC
              );
    commit;
  
    --作为辅方
    --正常签收
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
       invoice_mark --发票标记    
       )
      SELECT SYS_GUID() AS ID,
             V_PERIOD AS CHECK_OUT_PERIOD,
             NVL(b.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             --如果始发应收，始发出发部门
             case
               when b.bill_type = pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN then
                DECODE(B.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,B.EXPRESS_ORIG_ORG_CODE，B.ORIG_ORG_CODE)
               else
                DECODE(B.PRODUCT_CODE,
                     PKG_STL_COMMON.PRODUCT_CODE_PKG,
                     DECODE(B.receivable_org_code,
                     B.ORIG_ORG_CODE,
                     B.Express_Orig_Org_Code,
                     B.DEST_ORG_CODE,
                     B.Express_Dest_Org_Code，B.receivable_org_code),
                     B.receivable_org_code)
             end AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SBW.AMOUNT AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(B.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型
             --如果单据类型为偏线时，使用运单号，其他不变 
             case
               when b.BILL_TYPE =
                    PKG_STL_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(b.waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(b.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --来源单号 
             NVL(B.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --结算类型
             B.BILL_TYPE AS BILL_TYPE, -- 单据子类型
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             b.account_date as business_date,
             b.payment_type as payment_type,
             b.invoice_mark as invoice_mark --发票标记            
        FROM STL.T_STL_BILL_WRITEOFF sbw
        JOIN STL.T_STL_BILL_RECEIVABLE b
          ON b.RECEIVABLE_NO = sbw.END_NO
         AND b.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = b.WAYBILL_NO
                 AND T.POD_DATE < sbw.ACCOUNT_DATE) = 1 --已签收
         AND sbw.ACCOUNT_DATE >= P_BEGIN_DATE
         and sbw.ACCOUNT_DATE < P_END_DATE
            --按核销组织过滤
         and SBW.ORG_CODE = p_org_code
            --单据类型（始发运费、到达运费、空运到付、偏线到付、空运代收货款）
         and b.bill_type in
             (pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DEST,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_AA,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_AAC,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DL,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DLC);
    commit;
  
    --异常签收已核销
    --始发应收
    --运单作为主方
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
             NVL(b.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             --如果始发应收，始发出发部门
             case
               when b.bill_type = pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN then
                DECODE(B.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,B.EXPRESS_ORIG_ORG_CODE，B.ORIG_ORG_CODE)
               else
                DECODE(B.PRODUCT_CODE,
                     PKG_STL_COMMON.PRODUCT_CODE_PKG,
                     DECODE(B.receivable_org_code,
                     B.ORIG_ORG_CODE,
                     B.Express_Orig_Org_Code,
                     B.DEST_ORG_CODE,
                     B.Express_Dest_Org_Code，B.receivable_org_code),
                     B.receivable_org_code)
             end AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SBW.AMOUNT AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(B.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型
             --如果单据类型为偏线时，使用运单号，其他不变 
             case
               when b.BILL_TYPE =
                    PKG_STL_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(b.waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(b.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --来源单号 
             NVL(B.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --结算类型
             B.BILL_TYPE AS BILL_TYPE, -- 单据子类型
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             b.account_date as business_date,
             b.payment_type as payment_type,
             b.invoice_mark as invoice_mark--发票标记            
        FROM STL.T_STL_BILL_WRITEOFF sbw
       inner JOIN STL.T_STL_BILL_RECEIVABLE b
          ON b.RECEIVABLE_NO = sbw.BEGIN_NO
         AND b.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE exists
       (SELECT 1
                FROM STL.t_Stl_Out_Stock_Exception T
               WHERE T.WAYBILL_NO = b.WAYBILL_NO
                 AND T.Create_Time < sbw.ACCOUNT_DATE) --存在异常签收记录
         AND sbw.ACCOUNT_DATE >= P_BEGIN_DATE
         and sbw.ACCOUNT_DATE < P_END_DATE
            --按核销组织过滤
         and SBW.ORG_CODE = p_org_code
            --单据类型（始发运费）
         and b.bill_type in (pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN);
    commit;
  
    --运单作为辅方
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
             NVL(b.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             --如果始发应收，始发出发部门
             case
               when b.bill_type = pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN then
                 DECODE(B.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,B.EXPRESS_ORIG_ORG_CODE，B.ORIG_ORG_CODE)
               else
                DECODE(B.PRODUCT_CODE,
                     PKG_STL_COMMON.PRODUCT_CODE_PKG,
                     DECODE(B.receivable_org_code,
                     B.ORIG_ORG_CODE,
                     B.Express_Orig_Org_Code,
                     B.DEST_ORG_CODE,
                     B.Express_Dest_Org_Code，B.receivable_org_code),
                     B.receivable_org_code)
             end AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SBW.AMOUNT AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(B.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --产品类型
             --如果单据类型为偏线时，使用运单号，其他不变 
             case
               when b.BILL_TYPE =
                    PKG_STL_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(b.waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(b.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --来源单号 
             NVL(B.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --结算类型
             B.BILL_TYPE AS BILL_TYPE, -- 单据子类型
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             b.account_date as business_date,
             b.payment_type as payment_type,
             b.invoice_mark as invoice_mark --发票标记          
        FROM STL.T_STL_BILL_WRITEOFF sbw
       inner JOIN STL.T_STL_BILL_RECEIVABLE b
          ON b.RECEIVABLE_NO = sbw.end_no
         AND b.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE exists
       (SELECT 1
                FROM STL.t_Stl_Out_Stock_Exception T
               WHERE T.WAYBILL_NO = b.WAYBILL_NO
                 AND T.Create_Time < sbw.ACCOUNT_DATE) --存在异常签收记录
         AND sbw.ACCOUNT_DATE >= P_BEGIN_DATE
         and sbw.ACCOUNT_DATE < P_END_DATE
            --按核销组织过滤
         and SBW.ORG_CODE = p_org_code
            --单据类型（始发运费）
         and b.bill_type in (pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN);
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
                                        'PKG_STL_Balance_RECEIVABLE.func_daily_detail_writeoff',
                                        P_ORG_CODE || '-应收期末结账核算核销额');
      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_BALANCE_RECEIVABLE;
/
