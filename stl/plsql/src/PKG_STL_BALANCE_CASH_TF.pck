CREATE OR REPLACE PACKAGE STV.PKG_STL_BALANCE_CASH_TF IS
  --现金期末结账入口
  FUNCTION FUNC_COUNT_BALANCE(P_ORG_CODE     STRING, --组织编码
                              P_BEGIN_DATE   DATE, --起始日期
                              P_END_DATE     DATE, --结束日期
                              P_PERIOD       STRING, --结账期间
                              P_BALANCE_TYPE STRING --结账类型
                              ) RETURN BOOLEAN;
  --现金期末结账发生
  FUNCTION FUNC_DAILY_DETAIL_PRODUCE(P_ORG_CODE     STRING, --组织编码
                                     P_BEGIN_DATE   DATE, --起始日期
                                     P_END_DATE     DATE, --结束日期
                                     P_PERIOD       STRING, --结账期间
                                     P_BALANCE_TYPE IN STRING -- 核算类别
                                     ) RETURN BOOLEAN;
  --现金期末结账核销
  FUNCTION FUNC_DAILY_DETAIL_WRITEOFF(P_ORG_CODE     STRING, --组织编码
                                      P_BEGIN_DATE   DATE, --起始日期
                                      P_END_DATE     DATE, --结束日期
                                      P_PERIOD       STRING, --期间
                                      P_BALANCE_TYPE IN STRING -- 核算类别
                                      ) RETURN BOOLEAN;
END;
/
CREATE OR REPLACE PACKAGE BODY STV.PKG_STL_BALANCE_CASH_TF IS
  --现金期末结账入口
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
      RAISE_APPLICATION_ERROR('-20002',
                              '结算预收-中转--现金，发生额发生异常');
    END IF;

    V_RESULT := FUNC_DAILY_DETAIL_WRITEOFF(P_ORG_CODE,
                                           P_BEGIN_DATE,
                                           P_END_DATE,
                                           P_PERIOD,
                                           P_BALANCE_TYPE);
    --发生错误返回
    IF V_RESULT = FALSE THEN
      RAISE_APPLICATION_ERROR('-20002',
                              '结算预收-中转--现金，核销额发生异常');
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
                                        'pkg_stl_balance_cash_tf.func_count_balance',
                                        P_ORG_CODE || '-现金-中转期末结账调度');

      --返回成功标志
      RETURN FALSE;
  END;

  --现金期末结账发生
  FUNCTION FUNC_DAILY_DETAIL_PRODUCE(P_ORG_CODE     STRING, --组织编码
                                     P_BEGIN_DATE   DATE, --起始日期
                                     P_END_DATE     DATE, --结束日期
                                     P_PERIOD       STRING, --结账期间
                                     P_BALANCE_TYPE IN STRING -- 核算类别
                                     ) RETURN BOOLEAN IS
  BEGIN
    --计算发生额
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
       INVOICE_MARK --发票标记
       )
      SELECT SYS_GUID() AS ID,
             P_PERIOD AS CHECK_OUT_PERIOD,
             CA.CUSTOMER_CODE AS CUSTOMER_CODE,
             DECODE(CA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    NVL(CA.EXPRESS_ORIG_ORG_CODE, CA.GENERATING_ORG_CODE),
                    CA.GENERATING_ORG_CODE) AS CHECK_OUT_ORG_CODE, --收入部门
             CA.PRODUCT_CODE AS PRODUCT_CODE,
             0 AS BEGIN_AMOUNT,
             CA.AMOUNT AS PRODUCE_AMOUNT,
             0 AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(CA.SOURCE_BILL_NO, CA.WAYBILL_NO) AS SOURE_BILL_NO,
             CA.SOURCE_BILL_TYPE AS SOURE_BILL_TYPE,
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BALANCE_TYPE AS STL_TYPE,
             CA.BILL_TYPE AS BILL_TYPE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             CA.ACCOUNT_DATE AS BUSSINESS_DATE,
             CA.PAYMENT_TYPE AS PAYMENT_TYPE,
             CA.INVOICE_MARK AS INVOICE_MARK --发票标记
        FROM STL.T_STL_BILL_CASH_COLLECTION CA
       WHERE CA.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CA.ACCOUNT_DATE < P_END_DATE
         AND CA.GENERATING_ORG_CODE = P_ORG_CODE
         AND CA.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.CASH_COLL_SOURCE_BILL_TYPE_W;
    COMMIT;
    RETURN TRUE;
    --捕捉异常
  EXCEPTION

    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE - 1 / (24 * 60 * 60),
                                        'pkg_stl_balance_cash_tf.FUNC_DAILY_DETAIL_PRODUCE',
                                        P_ORG_CODE || '-现金-中转期末结账核算发生额');

      --返回失败标志
      RETURN FALSE;

  END;
  --现金期末结账核销
  FUNCTION FUNC_DAILY_DETAIL_WRITEOFF(P_ORG_CODE     STRING, --组织编码
                                      P_BEGIN_DATE   DATE, --起始日期
                                      P_END_DATE     DATE, --结束日期
                                      P_PERIOD       STRING, --期间
                                      P_BALANCE_TYPE IN STRING -- 核算类别
                                      ) RETURN BOOLEAN IS
  --存放当天签收相关现金收款单
  CURSOR POD_CORSOR IS SELECT DISTINCT CA.SOURCE_BILL_NO FROM STL.T_STL_BILL_CASH_COLLECTION CA
  INNER JOIN STL.T_STL_POD POD ON POD.WAYBILL_NO = CA.SOURCE_BILL_NO
  WHERE POD.POD_TYPE IN (Pkg_Stl_Common.POD__POD_TYPE__POD,PKG_STL_COMMON.POD__POD_TYPE__UPD)
  AND POD.POD_DATE>= P_BEGIN_DATE
  AND POD.POD_DATE < P_END_DATE
  AND CA.SOURCE_BILL_TYPE = PKG_STL_COMMON.CASH_COLL_SOURCE_BILL_TYPE_W;
  --异常签收游标
  CURSOR EX_CORSOR IS SELECT DISTINCT CA.SOURCE_BILL_NO FROM STL.T_STL_BILL_CASH_COLLECTION CA
  INNER JOIN STL.T_STL_OUT_STOCK_EXCEPTION EX ON EX.WAYBILL_NO = CA.SOURCE_BILL_NO
  WHERE EX.CREATE_TIME >= P_BEGIN_DATE
  AND EX.CREATE_TIME < P_END_DATE
  AND CA.SOURCE_BILL_TYPE = PKG_STL_COMMON.CASH_COLL_SOURCE_BILL_TYPE_W;

  CASH_COLLECTION_ROW STL.T_STL_BILL_CASH_COLLECTION%ROWTYPE;
  BEGIN
    FOR CASH_COLLECTION_ROW IN POD_CORSOR LOOP
    --计算核销金额(签收反签收)
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
       INVOICE_MARK --发票标记
       )
      SELECT SYS_GUID() AS ID,
             P_PERIOD AS CHECK_OUT_PERIOD,
             CA.CUSTOMER_CODE AS CUSTOMER_CODE,
             DECODE(CA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    NVL(CA.EXPRESS_ORIG_ORG_CODE, CA.GENERATING_ORG_CODE),
                    CA.GENERATING_ORG_CODE) AS CHECK_OUT_ORG_CODE, --收入部门
             CA.PRODUCT_CODE AS PRODUCT_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
              CA.AMOUNT * (CASE
               WHEN SPOD.RS > 0 THEN
                1
               WHEN SPOD.RS <0 THEN
               -1
               ELSE
                0
             END) AS WRITEOFF_AMOUNT, --签收取正数，反签收取负数
             0 AS END_AMOUNT,
             NVL(CA.SOURCE_BILL_NO, CA.WAYBILL_NO) AS SOURE_BILL_NO,
             CA.SOURCE_BILL_TYPE AS SOURE_BILL_TYPE,
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BALANCE_TYPE AS STL_TYPE,
             CA.BILL_TYPE AS BILL_TYPE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             CA.ACCOUNT_DATE AS BUSSINESS_DATE,
             CA.PAYMENT_TYPE AS PAYMENT_TYPE,
             CA.INVOICE_MARK --发票标记
        FROM STL.T_STL_BILL_CASH_COLLECTION CA
       INNER JOIN (SELECT SUM(DECODE(PP.POD_TYPE,
                                     PKG_STL_COMMON.POD__POD_TYPE__POD,
                                     1,
                                     PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                     -1,
                                     0)) RS,
                          PP.WAYBILL_NO
                     FROM STL.T_STL_POD PP
                    WHERE PP.POD_DATE < P_END_DATE
                    AND PP.POD_DATE>= P_BEGIN_DATE
                    AND PP.POD_TYPE IN (Pkg_Stl_Common.POD__POD_TYPE__POD,PKG_STL_COMMON.POD__POD_TYPE__UPD)
                    GROUP BY PP.WAYBILL_NO) SPOD
          ON CA.WAYBILL_NO = SPOD.WAYBILL_NO
       WHERE CA.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.CASH_COLL_SOURCE_BILL_TYPE_W
         AND CA.GENERATING_ORG_CODE = P_ORG_CODE
         AND CA.ID IN (
         SELECT ID FROM (SELECT ID,CO.IS_RED_BACK FROM STL.T_STL_BILL_CASH_COLLECTION CO WHERE
         CO.SOURCE_BILL_NO = CASH_COLLECTION_ROW.SOURCE_BILL_NO
         AND CO.ACCOUNT_DATE <= P_END_DATE
         AND CO.SOURCE_BILL_TYPE = PKG_STL_COMMON.CASH_COLL_SOURCE_BILL_TYPE_W
         ORDER BY CO.ACCOUNT_DATE DESC,CO.CREATE_TIME DESC
          ) WHERE ROWNUM <=1 AND IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         );
     END LOOP;
    COMMIT;

    FOR CASH_COLLECTION_ROW IN EX_CORSOR LOOP
    --计算核销金额（丢货、弃货、违禁品）
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
       INVOICE_MARK --发票标记
       )
      SELECT SYS_GUID() AS ID,
             P_PERIOD AS CHECK_OUT_PERIOD,
             CA.CUSTOMER_CODE AS CUSTOMER_CODE,
             DECODE(CA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    NVL(CA.EXPRESS_ORIG_ORG_CODE, CA.GENERATING_ORG_CODE),
                    CA.GENERATING_ORG_CODE) AS CHECK_OUT_ORG_CODE, --收入部门
             CA.PRODUCT_CODE AS PRODUCT_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             CA.AMOUNT AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(CA.SOURCE_BILL_NO, CA.WAYBILL_NO) AS SOURE_BILL_NO,
             CA.SOURCE_BILL_TYPE AS SOURE_BILL_TYPE,
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BALANCE_TYPE AS STL_TYPE,
             CA.BILL_TYPE AS BILL_TYPE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             CA.ACCOUNT_DATE AS BUSSINESS_DATE,
             CA.PAYMENT_TYPE AS PAYMENT_TYPE,
             CA.INVOICE_MARK AS INVOICE_MARK --发票标记
        FROM STL.T_STL_BILL_CASH_COLLECTION CA
       INNER JOIN STL.T_STL_OUT_STOCK_EXCEPTION EX
          ON EX.WAYBILL_NO = CA.WAYBILL_NO
       WHERE  CA.GENERATING_ORG_CODE = P_ORG_CODE
         AND CA.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.CASH_COLL_SOURCE_BILL_TYPE_W
         AND EX.CREATE_TIME >= P_BEGIN_DATE
         AND EX.CREATE_TIME <P_END_DATE
         --取最新的一条现金收款单的金额
         AND CA.ID IN (
          SELECT ID FROM (SELECT ID,CO.IS_RED_BACK FROM STL.T_STL_BILL_CASH_COLLECTION CO WHERE
         CO.SOURCE_BILL_NO = CASH_COLLECTION_ROW.SOURCE_BILL_NO
         AND CO.ACCOUNT_DATE <= P_END_DATE
         AND CO.SOURCE_BILL_TYPE = PKG_STL_COMMON.CASH_COLL_SOURCE_BILL_TYPE_W
         ORDER BY CO.ACCOUNT_DATE DESC,CO.CREATE_TIME DESC
          ) WHERE ROWNUM <=1 AND IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         );
    END LOOP;
    COMMIT;
    RETURN TRUE;
    --捕捉异常
  EXCEPTION

    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE - 1 / (24 * 60 * 60),
                                        'pkg_stl_balance_cash_tf.FUNC_DAILY_DETAIL_WRITEOFF',
                                        P_ORG_CODE || '-现金-中转期末结账核核销生额');

      --返回失败标志
      RETURN FALSE;
  END;
END;
/
