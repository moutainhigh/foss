CREATE OR REPLACE PACKAGE PKG_STL_BALANCE_DEPOSIT_BILL IS

  -- Author  : 000123-foss-huangxiaobo
  -- Created : 2012/12/10 14:36:51
  -- Purpose : 预收期末结算

  ------------------------------------------------------------------
  --预收期末结账总入口
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

END PKG_STL_BALANCE_DEPOSIT_BILL;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_BALANCE_DEPOSIT_BILL IS

  ------------------------------------------------------------------
  --预收期末结账（预收期末结账入口-预收单据）
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
      RAISE_APPLICATION_ERROR('-20002', '结算预收,发生额发生异常');
    END IF;
  
    --计算核销额
    V_RESULT := FUNC_DAILY_DETAIL_WRITEOFF(P_ORG_CODE,
                                           P_BEGIN_DATE,
                                           P_END_DATE,
                                           P_PERIOD,
                                           P_BALANCE_TYPE);
  
    --发生错误返回
    IF V_RESULT = FALSE THEN
      RAISE_APPLICATION_ERROR('-20002', '结算预收,核销发生异常');
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
                                        'pkg_stl_balance_deposit.func_count_balance',
                                        P_ORG_CODE || '预收期末结账调度');
      --返回失败标志
      RETURN FALSE;
  END;

  ------------------------------------------------------------------
  --计算本期发生额度
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
  
    --将本期发生额写入临时表
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
      SELECT SYS_GUID() AS ID,
             V_PERIOD AS CHECK_OUT_PERIOD,
             NVL(BILL.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             BILL.Collection_Org_Code AS CHECK_OUT_ORG_CODE,
             --DDW 2013-06-18 给预收结账添加产品类型
             BILL.TRANSPORT_TYPE AS PRODUCT_CODE,
             0                   AS BEGIN_AMOUNT,
             BILL.AMOUNT         AS PRODUCE_AMOUNT,
             0                   AS WRITEOFF_AMOUNT,
             0                   AS END_AMOUNT,
             --DDW 2013-06-18 给预收结账添加来源单号
             BILL.DEPOSIT_RECEIVED_NO  AS SOURCE_BILL_NO, --来源单号(无值)
             PKG_STL_COMMON.NULL_VALUE AS SOURCE_BILL_TYPE, --来源单据类别(无值)
             BILL.CURRENCY_CODE        AS CURRENCY_CODE, --币别
             PKG_STL_COMMON.ACTIVE     AS ACTIVE,
             P_BALANCE_TYPE            AS STL_TYPE,
             BILL.BILL_TYPE            AS BILL_TYPE,
             P_BEGIN_DATE              AS BALANCE_TIME,
             SYSDATE                   AS CREATE_TIME,
             business_date             as business_date, --业务日期
             bill.payment_type,
             bill.invoice_mark as invoice_mark --发票标记           
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED BILL
      --目前按照记账日期
       WHERE BILL.ACCOUNT_DATE >= P_BEGIN_DATE
         AND BILL.ACCOUNT_DATE < P_END_DATE
            --应收组织
         AND BILL.Collection_Org_Code = P_ORG_CODE; -- 单据类型 
    commit;
    RETURN TRUE;
  
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'pkg_stl_balance_deposit.func_daily_produce',
                                        P_ORG_CODE || '-预收期末结账核算发生额');
    
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

    --预收冲应收  以核销时间为准
  
    --获得预收冲应收的核销金额,并记入临时表
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
              B.Collection_Org_Code AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SBW.AMOUNT AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             --DDW 2013-06-18 给预收结账添加产品类型和来源单号
             B.TRANSPORT_TYPE          AS PRODUCT_CODE, --产品类型
             B.DEPOSIT_RECEIVED_NO     AS SOURCE_BILL_NO,
             PKG_STL_COMMON.NULL_VALUE AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE            AS STL_TYPE, --结算类型
             B.BILL_TYPE               AS BILL_TYPE, -- 单据子类型
             B.CURRENCY_CODE           AS CURRENCY_CODE, --币别
             PKG_STL_COMMON.ACTIVE     AS ACTIVE,
             P_BEGIN_DATE              AS BALANCE_TIME,
             SYSDATE                   AS CREATE_TIME,
             b.business_date           as business_date,
             b.payment_type,
             b.invoice_mark as invoice_mark--发票标记             
        FROM STL.T_STL_BILL_WRITEOFF SBW
      --关联预收单
       INNER JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED B
          ON B.Deposit_Received_No = SBW.Begin_No --起始应收单号
           --非红单
         and b.is_red_back = PKG_STL_COMMON.NO
       WHERE 
             SBW.Writeoff_Type =  PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR
         AND SBW.ORG_CODE = P_ORG_CODE --核销部门
        -- AND SBW.ORG_CODE = B.Collection_Org_Code
         AND SBW.ACCOUNT_DATE >= P_BEGIN_DATE --核销日期
         AND SBW.ACCOUNT_DATE < P_END_DATE; 
    commit;
      
   --获得付款冲预收金额,并记入临时表
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
     --付款冲预收
 select SYS_GUID() AS ID,
        V_PERIOD AS CHECK_OUT_PERIOD,
        NVL(b.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
        B.Collection_Org_Code AS CHECK_OUT_ORG_CODE,
        0 AS BEGIN_AMOUNT,
        0 AS PRODUCE_AMOUNT,
        PAY_D.PAY_AMOUNT * DECODE(PAY.IS_RED_BACK,
                                  PKG_STL_COMMON.IS_RED_BACK_YES,
                                  -1,
                                  PKG_STL_COMMON.IS_RED_BACK_NO,
                                  1) AS WRITEOFF_AMOUNT,
        0 AS END_AMOUNT,
        B.TRANSPORT_TYPE AS PRODUCT_CODE, --产品类型
        B.DEPOSIT_RECEIVED_NO AS SOURCE_BILL_NO,
        PKG_STL_COMMON.NULL_VALUE AS SOURCE_BILL_TYPE,
        P_BALANCE_TYPE AS STL_TYPE, --结算类型
        B.BILL_TYPE AS BILL_TYPE, -- 单据子类型
        B.CURRENCY_CODE AS CURRENCY_CODE, --币别
        PKG_STL_COMMON.ACTIVE AS ACTIVE,
        P_BEGIN_DATE AS BALANCE_TIME,
        SYSDATE AS CREATE_TIME,
        b.business_date as business_date,
        b.payment_type,
        b.invoice_mark as invoice_mark--发票标记      
   FROM STL.T_STL_BILL_PAYMENT PAY
   JOIN STL.T_STL_BILL_PAYMENT_D PAY_D ON PAY.PAYMENT_NO = PAY_D.PAYMENT_NO
   JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED B ON B.Deposit_Received_No =
                                             PAY_D.SOURCE_BILL_NO
                                         AND B.IS_RED_BACK =
                                             PKG_STL_COMMON.IS_RED_BACK_NO
  WHERE PAY.ACCOUNT_DATE >= P_BEGIN_DATE
    AND PAY.ACCOUNT_DATE < P_END_DATE
    and PAY.Payment_Org_Code = B.Collection_Org_Code
    AND B.Collection_Org_Code = P_ORG_CODE;
      
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
                                        P_END_DATE,
                                        'PKG_STL_BALANCE_DEPOSIT',
                                        P_ORG_CODE || '-预收期末结账核算期末余额');
      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_BALANCE_DEPOSIT_BILL;
/
