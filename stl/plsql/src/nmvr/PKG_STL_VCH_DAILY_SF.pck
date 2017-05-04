CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_SF IS

  -- ==============================================================
  -- Author  : ZBW
  -- Created : 2013-11-06
  -- Purpose : 每日凭证装卸费
  -- ==============================================================

  -----------------------------------------------------------------
  -- 装卸费处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_DAILY_SF;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_SF IS

  -----------------------------------------------------------------
  -- 装卸费处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS
  BEGIN
    --1)01装卸费付款申请
    ---按付款单的记账日期来统计，关联里面的应付单单据子类型为劳务费，对应的付款单的明细金额之和
    INSERT INTO STV.T_STL_NDVD
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       ORIG_ORG_CODE,
       ORIG_ORG_NAME,
       DEST_ORG_CODE,
       DEST_ORG_NAME,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --期间
             PKG_STL_VCH_COMMON.SFO_PAY_APPLY, --业务场景
             PA.CUSTOMER_CODE, --客户编码
             PA.CUSTOMER_NAME, --客户名称
             PA.CUSTOMER_TYPE, --客户类型
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_ORIG_ORG_CODE，PA.ORIG_ORG_CODE), --始发部门编码
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_ORIG_ORG_NAME，PA.ORIG_ORG_NAME), --始发部门名称
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_DEST_ORG_CODE，PA.DEST_ORG_CODE), --到达部门编码
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_DEST_ORG_NAME，PA.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --运单号
             PA.PAYABLE_NO, --单据编号
             PAY.ACCOUNT_DATE, --会计日期
             PA.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON. BILL_PARENT_TYPE__YF, --单据类型
             PA.BILL_TYPE, --单据子类型

             --BUG-42069
             PAY_D.PAY_AMOUNT * DECODE(PAY.IS_RED_BACK,
                                       PKG_STL_COMMON.IS_RED_BACK_YES,
                                       -1,
                                       PKG_STL_COMMON.IS_RED_BACK_NO,
                                       1), --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PA.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_PAYMENT PAY
        JOIN STL.T_STL_BILL_PAYMENT_D PAY_D
          ON PAY.PAYMENT_NO = PAY_D.PAYMENT_NO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = PAY_D.SOURCE_BILL_NO
             AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO

       WHERE PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_SERVICE_FEE --劳务费
             AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PAY.ACCOUNT_DATE < P_END_DATE
             AND PA.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;

      --2)02装卸费付款申请
      INSERT INTO STV.T_STL_NDVD
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       ORIG_ORG_CODE,
       ORIG_ORG_NAME,
       DEST_ORG_CODE,
       DEST_ORG_NAME,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       AMOUNT_FRT,
       AMOUNT_PUP,
       AMOUNT_DEL,
       AMOUNT_PKG,
       AMOUNT_DV,
       AMOUNT_COD,
       AMOUNT_OT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --期间
             PKG_STL_VCH_COMMON.SFT_PAY_APPLY, --业务场景
             PA.CUSTOMER_CODE, --客户编码
             PA.CUSTOMER_NAME, --客户名称
             PA.CUSTOMER_TYPE, --客户类型
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_ORIG_ORG_CODE，PA.ORIG_ORG_CODE), --始发部门编码
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_ORIG_ORG_NAME，PA.ORIG_ORG_NAME), --始发部门名称
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_DEST_ORG_CODE，PA.DEST_ORG_CODE), --到达部门编码
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_DEST_ORG_NAME，PA.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --运单号
             PA.PAYABLE_NO, --单据编号
             PAY.ACCOUNT_DATE, --会计日期
             PA.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON. BILL_PARENT_TYPE__YF, --单据类型
             PA.BILL_TYPE, --单据子类型

             --BUG-42069
             PAY_D.PAY_AMOUNT * DECODE(PAY.IS_RED_BACK,
                                       PKG_STL_COMMON.IS_RED_BACK_YES,
                                       -1,
                                       PKG_STL_COMMON.IS_RED_BACK_NO,
                                       1), --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PA.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_PAYMENT PAY
        JOIN STL.T_STL_BILL_PAYMENT_D PAY_D
          ON PAY.PAYMENT_NO = PAY_D.PAYMENT_NO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = PAY_D.SOURCE_BILL_NO
             AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO

       WHERE PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_SERVICE_FEE --劳务费
             AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PAY.ACCOUNT_DATE < P_END_DATE
             AND PA.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;

       --3)01装卸费付款申请  往来
       INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --期间
             PKG_STL_VCH_COMMON.SFO_PAY_APPLY, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
              DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --运单号
             PA.PAYABLE_NO,
             PAY.ACCOUNT_DATE,
             PA.BUSINESS_DATE,
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF,
             PA.BILL_TYPE,
             PAY_D.PAY_AMOUNT*DECODE(PAY.IS_RED_BACK,
                                       PKG_STL_COMMON.IS_RED_BACK_YES,
                                       -1,
                                       PKG_STL_COMMON.IS_RED_BACK_NO,
                                       1),
             PA.PRODUCT_CODE,
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_ORIG_ORG_CODE，PA.ORIG_ORG_CODE), --始发部门编码
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_ORIG_ORG_NAME，PA.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_ORIG_ORG_CODE，PA.ORIG_ORG_CODE), --始发部门编码
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_ORIG_ORG_NAME，PA.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_PAYMENT PAY
        JOIN STL.T_STL_BILL_PAYMENT_D PAY_D
          ON PAY.PAYMENT_NO = PAY_D.PAYMENT_NO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = PAY_D.SOURCE_BILL_NO
             AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_SERVICE_FEE --劳务费
             AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PAY.ACCOUNT_DATE < P_END_DATE
             AND PA.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;

    RETURN TRUE;

  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        '装卸费处理日明细' || '异常');

      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_SF;
/
