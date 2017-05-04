CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_RETURN_COD IS

  -- ==============================================================
  -- Author  : guxinhua
  -- Created : 2013-04-01 16:00:00
  -- Purpose : 每日凭证退代收货款
  -- ==============================================================

  -----------------------------------------------------------------
  -- 凭证退代收货款日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

  -----------------------------------------------------------------
  -- 凭证退代收货款月汇总（按每日方式）
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_MONTHLY_SUMMARY(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                        P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                        P_END_DATE   DATE -- 结束时间 2012-12-22
                                        ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_DAILY_RETURN_COD;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_RETURN_COD IS

  -----------------------------------------------------------------
  -- 凭证退代收货款日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS
  BEGIN
  
    --退代收货款明细
    INSERT INTO STV.T_STL_DVD_RETURN_COD
      (ID, -- ID
       PERIOD, -- 期间
       WAYBILL_NO, -- 运单号
       PAYABLE_ORG_CODE, -- 应付部门编码
       PAYABLE_ORG_NAME, -- 应付部门名称
       PAYABLE_COM_CODE, -- 应付子公司编码
       PAYABLE_COM_NAME, -- 应付子公司名称
       COD_TYPE, -- 代收货款类型
       CUSTOMER_CODE, -- 发货客户编码
       CUSTOMER_TYPE, -- 客户类型
       CUSTOMER_NAME, -- 发货客户名称/代理名称
       PAYEE_NAME, -- 收款人
       PAYEE_ACCOUNT, -- 收款人银行帐号
       BANK_HQ_CODE, -- 开户行编码
       BANK_HQ_NAME, -- 开户行名称
       ACCOUNT_DATE, -- 记账日期
       BUSINESS_DATE, -- 业务日期
       SIGN_DATE, -- 签收日期
       PAYMENT_DATE, -- 付款日期
       --RETURN_ORG, -- 退款部门（固定值）
       --RETURN_COM_ORG, -- 退款子公司（固定值）
       REFUND_PATH, -- 退款路径
       PRODUCT_CODE, -- 运输性质
       COD_AMOUNT -- 应退金额
       )
      SELECT SYS_GUID(),
             P_PERIOD,
             PAY.WAYBILL_NO,
             decode(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    NVL(PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE),
                    PAY.PAYABLE_ORG_CODE), --PAY.PAYABLE_ORG_CODE,
             decode(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    NVL(PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME),
                    PAY.PAYABLE_ORG_NAME), --PAY.PAYABLE_ORG_NAME,
             PAY.PAYABLE_COM_CODE,
             PAY.PAYABLE_COM_NAME,
             COD.COD_TYPE,
             PAY.CUSTOMER_CODE,
             PAY.CUSTOMER_TYPE,
             PAY.CUSTOMER_NAME,
             PAY.PAYEE_NAME,
             PAY.ACCOUNT_NO,
             COD.BANK_HQ_CODE,
             COD.BANK_HQ_NAME,
             WO.ACCOUNT_DATE,
             COD.BUSINESS_DATE,
             PAY.SIGN_DATE,
             WO.ACCOUNT_DATE,
             --NULL,
             --NULL,
             COD.REFUND_PATH,
             PAY.PRODUCT_CODE,
             WO.AMOUNT
      
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_PAYMENT PMT
          ON WO.BEGIN_NO = PMT.PAYMENT_NO --付款单
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO --应付单
         AND PAY.IS_RED_BACK = 'N'
        JOIN STL.T_STL_COD COD
          ON PAY.WAYBILL_ID = COD.WAYBILL_ID
         AND PAY.ACTIVE = COD.ACTIVE
       WHERE PAY.IS_RED_BACK = PMT.IS_RED_BACK
         AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_PP --付款冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --APC
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    RETURN TRUE;
  
  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        '凭证退代收货款日明细' || '异常');
    
      --返回失败标志
      RETURN FALSE;
  END;

  -----------------------------------------------------------------
  -- 凭证退代收货款月汇总（按每日方式）
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_MONTHLY_SUMMARY(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121212
                                        P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                        P_END_DATE   DATE -- 结束时间 2012-12-22
                                        ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS
  BEGIN
  
    INSERT INTO STV.T_STL_DVR_RETURN_COD
      (ID, -- ID
       PAYMENT_DATE, -- 付款日期
       PERIOD, -- 付款所属期间
       PAYABLE_ORG_CODE, -- 应付部门编码
       PAYABLE_ORG_NAME, -- 应付部门名称
       UNIFIED_CODE, -- 组织标杆编码
       PAYABLE_COM_CODE, -- 应付子公司编码
       PAYABLE_COM_NAME, -- 应付子公司名称
       --RETURN_ORG, -- 退款部门（固定值）
       --RETURN_COM_ORG, -- 退款子公司（固定值）
       PRODUCT_CODE, -- 业务类型(即日退/三日退和审核退)
       BANK_ACCOUNT, -- 银行帐号
       REFUND_PATH, -- 付款途径(银企/线下)
       COD_AMOUNT -- 金额
       )
      (SELECT SYS_GUID(),
              TO_DATE(COD.PERIOD, 'yyyyMMdd'),
              P_PERIOD,
              COD.PAYABLE_ORG_CODE,
              MAX(COD.PAYABLE_ORG_NAME),
              NVL2(COD.PAYABLE_ORG_CODE,
                   PKG_STL_COMMON.FUNC_GET_ORG_UNIFIED_CODE(COD.PAYABLE_ORG_CODE),
                   NULL),
              COD.PAYABLE_COM_CODE,
              MAX(COD.PAYABLE_COM_NAME),
              --NULL,
              --NULL,
              PKG_STL_COMMON.COD_COD_TYPE_RETURN_1_DAY,
              NULL,
              COD.REFUND_PATH,
              SUM(COD_AMOUNT)
         FROM STV.T_STL_DVD_RETURN_COD COD
        WHERE --SUBSTR(COD.PERIOD, 1, 6) = P_PERIOD
        COD.ACCOUNT_DATE >= P_BEGIN_DATE
    AND COD.ACCOUNT_DATE < P_END_DATE -- 期间范围
    AND COD.COD_TYPE = PKG_STL_COMMON.COD_COD_TYPE_RETURN_1_DAY
        GROUP BY COD.PERIOD,
                 --COD.PAYMENT_DATE,
                 COD.PAYABLE_ORG_CODE,
                 COD.PAYABLE_COM_CODE,
                 COD.REFUND_PATH) UNION ALL
      (SELECT SYS_GUID(),
              TO_DATE(COD.PERIOD, 'yyyyMMdd'),
              P_PERIOD,
              COD.PAYABLE_ORG_CODE,
              MAX(COD.PAYABLE_ORG_NAME),
              NVL2(COD.PAYABLE_ORG_CODE,
                   PKG_STL_COMMON.FUNC_GET_ORG_UNIFIED_CODE(COD.PAYABLE_ORG_CODE),
                   NULL),
              COD.PAYABLE_COM_CODE,
              MAX(COD.PAYABLE_COM_NAME),
              --NULL,
              --NULL,
              PKG_STL_COMMON.COD_COD_TYPE_RETURN_3_APPROVE,
              NULL,
              COD.REFUND_PATH,
              SUM(COD_AMOUNT)
         FROM STV.T_STL_DVD_RETURN_COD COD
        WHERE --SUBSTR(COD.PERIOD, 1, 6) = P_PERIOD
        COD.ACCOUNT_DATE >= P_BEGIN_DATE
    AND COD.ACCOUNT_DATE < P_END_DATE -- 期间范围
    AND COD.COD_TYPE IN
        (PKG_STL_COMMON.COD_COD_TYPE_RETURN_3_DAY,
         PKG_STL_COMMON.COD_COD_TYPE_RETURN_APPROVE)
        GROUP BY COD.PERIOD,
                 --COD.PAYMENT_DATE,
                 COD.PAYABLE_ORG_CODE,
                 COD.PAYABLE_COM_CODE,
                 COD.REFUND_PATH
       HAVING SUM(COD_AMOUNT) != 0);
  
    RETURN TRUE;
  
  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_MONTHLY_SUMMARY',
                                        '凭证退代收货款月汇总（按每日方式）' || '异常');
    
      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_RETURN_COD;
/
