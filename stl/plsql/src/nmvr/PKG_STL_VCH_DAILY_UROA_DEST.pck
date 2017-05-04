CREATE OR REPLACE PACKAGE STV.PKG_STL_VCH_DAILY_UROA_DEST IS

  -- ==============================================================
  -- Author  : ZBW
  -- Created : 2013-11-06
  -- Purpose : 每日凭证空运_还款运单总运费（到付）【01】
  -- ==============================================================

  -----------------------------------------------------------------
  -- 空运_还款运单总运费（到付）【01】处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_DAILY_UROA_DEST;
/
CREATE OR REPLACE PACKAGE BODY STV.PKG_STL_VCH_DAILY_UROA_DEST IS

  -----------------------------------------------------------------
  -- 空运_还款运单总运费（到付）【01】处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS
  BEGIN
     --1)空运--还款运单总运费（到付）-还款现金未签收
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
             PKG_STL_VCH_COMMON.UROA_DEST_CH_NPOD, --业务场景
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE), --始发部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME，RCV.ORIG_ORG_NAME), --始发部门名称
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE，RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME), --到达部门名称
             RCV.WAYBILL_NO, --运单号
             RCV.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RCV.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
             AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
             AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收
             AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
                                   PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
                                   PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA                                   ) --到达应收、偏线到达应收、空运到达应收
             AND RCV.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
             AND RPMT.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH --现金
             AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --还款时未签收
             AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
             AND WO.ACCOUNT_DATE < P_END_DATE
             AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
             AND RCV.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_AF --精准空运
             AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
             PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA); --到付运费和空运到达代理应收


    --2)空运--还款运单总运费（到付）-还款银行未签收
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
             PKG_STL_VCH_COMMON.UROA_DEST_CD_NPOD, --业务场景
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE), --始发部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME，RCV.ORIG_ORG_NAME), --始发部门名称
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE，RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME), --到达部门名称
             RCV.WAYBILL_NO, --运单号
             RCV.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RCV.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
             AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
             AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收
             AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
                                   PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
                                   PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA                                   ) --到达应收、偏线到达应收、空运到达应收
             AND RCV.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
             AND RPMT.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
                  PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
                  PKG_STL_COMMON.PAYMENT_TYPE_NOTE,
                  PKG_STL_COMMON.PAYMENT_TYPE_ONLINE) --银行卡、电汇、支票、网上支付
             AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --还款时未签收
             AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
             AND WO.ACCOUNT_DATE < P_END_DATE
             AND RCV.INVOICE_MARK=PKG_STL_COMMON.INVOICE_MARK_ONE
             AND RCV.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_AF --精准空运
             AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
             PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA); --到付运费和空运到达代理应收

    --3)空运--还款运单总运费（到付）-还款现金已签收
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
             PKG_STL_VCH_COMMON.UROA_DEST_CH_POD, --业务场景
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE), --始发部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME，RCV.ORIG_ORG_NAME), --始发部门名称
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE，RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME), --到达部门名称
             RCV.WAYBILL_NO, --运单号
             RCV.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RCV.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
             AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
             AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收
             AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
                                   PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
                                   PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA                                   ) --到达应收、偏线到达应收、空运到达应收
             AND RCV.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
             AND RPMT.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH --现金
             AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时已签收
             AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
             AND WO.ACCOUNT_DATE < P_END_DATE
             AND RCV.INVOICE_MARK =PKG_STL_COMMON.INVOICE_MARK_ONE
             AND RCV.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_AF --精准空运
             AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
             PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA); --到付运费和空运到达代理应收


    --4)空运--还款运单总运费（到付）-还款银行已签收
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
             PKG_STL_VCH_COMMON.UROA_DEST_CD_POD, --业务场景
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE), --始发部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME，RCV.ORIG_ORG_NAME), --始发部门名称
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE，RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME), --到达部门名称
             RCV.WAYBILL_NO, --运单号
             RCV.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RCV.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
             AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
             AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收
             AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
                                   PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
                                   PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA                                   ) --到达应收、偏线到达应收、空运到达应收
             AND RCV.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
             AND RPMT.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
                  PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
                  PKG_STL_COMMON.PAYMENT_TYPE_NOTE,
                  PKG_STL_COMMON.PAYMENT_TYPE_ONLINE) --银行卡、电汇、支票、网上支付
             AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时已签收
             AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
             AND WO.ACCOUNT_DATE < P_END_DATE
             AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
             AND RCV.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_AF --精准空运
             AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
             PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA); --到付运费和空运到达代理应收

    --5)空运--还款运单总运费（到付）【01】--还款现金未签收 往来
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
             PKG_STL_VCH_COMMON.UROA_DEST_CH_NPOD, --业务场景
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             RCV.WAYBILL_NO, --运单号
             RCV.RECEIVABLE_NO,--应收单号
             WO.ACCOUNT_DATE,--会计日期
             RCV.BUSINESS_DATE,--业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--应收单
             RCV.BILL_TYPE,
             WO.AMOUNT,
             RCV.PRODUCT_CODE,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE，RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME), --到达部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE), --始发部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME，RCV.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
             AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
             AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收
             AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
                                   PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
                                   PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA                                   ) --到达应收、偏线到达应收、空运到达应收
             AND RCV.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
             AND RPMT.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH --现金
             AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --还款时未签收
             AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
             AND WO.ACCOUNT_DATE < P_END_DATE
             AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
             AND RCV.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_AF --精准空运
             AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
             PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA); --到付运费和空运到达代理应收


      --6)空运--还款运单总运费（到付）【01】--还款银行未签收 往来
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
       DEBIT_ORG_TYPE
       )
       SELECT SYS_GUID(), --ID
             P_PERIOD, --期间
             PKG_STL_VCH_COMMON.UROA_DEST_CD_NPOD, --业务场景
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             RCV.WAYBILL_NO, --运单号
             RCV.RECEIVABLE_NO,--应收单号
             WO.ACCOUNT_DATE,--会计日期
             RCV.BUSINESS_DATE,--业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--应收单
             RCV.BILL_TYPE,
             WO.AMOUNT,
             RCV.PRODUCT_CODE,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE，RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME), --到达部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE), --始发部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME，RCV.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
             AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
             AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收
             AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
                                   PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
                                   PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA                                   ) --到达应收、偏线到达应收、空运到达应收
             AND RCV.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
             AND RPMT.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
                  PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
                  PKG_STL_COMMON.PAYMENT_TYPE_NOTE,
                  PKG_STL_COMMON.PAYMENT_TYPE_ONLINE) --银行卡、电汇、支票、网上支付
             AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --还款时未签收
             AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
             AND WO.ACCOUNT_DATE < P_END_DATE
             AND RCV.INVOICE_MARK=PKG_STL_COMMON.INVOICE_MARK_ONE
             AND RCV.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_AF --精准空运
             AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
             PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA); --到付运费和空运到达代理应收


      --7)空运--还款运单总运费（到付）【01】--还款现金已签收 往来
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
             PKG_STL_VCH_COMMON.UROA_DEST_CH_POD, --业务场景
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             RCV.WAYBILL_NO, --运单号
             RCV.RECEIVABLE_NO,--应收单号
             WO.ACCOUNT_DATE,--会计日期
             RCV.BUSINESS_DATE,--业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--应收单
             RCV.BILL_TYPE,
             WO.AMOUNT,
             RCV.PRODUCT_CODE,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE，RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME), --到达部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE), --始发部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME，RCV.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
             AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
             AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收
             AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
                                   PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
                                   PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA                                   ) --到达应收、偏线到达应收、空运到达应收
             AND RCV.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
             AND RPMT.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH --现金
             AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时已签收
             AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
             AND WO.ACCOUNT_DATE < P_END_DATE
             AND RCV.INVOICE_MARK =PKG_STL_COMMON.INVOICE_MARK_ONE
             AND RCV.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_AF --精准空运
             AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
             PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA); --到付运费和空运到达代理应收


      --8)空运--还款运单总运费（到付）【01】--还款银行已签收 往来
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
             PKG_STL_VCH_COMMON.UROA_DEST_CD_POD, --业务场景
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             RCV.WAYBILL_NO, --运单号
             RCV.RECEIVABLE_NO,--应收单号
             WO.ACCOUNT_DATE,--会计日期
             RCV.BUSINESS_DATE,--业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--应收单
             RCV.BILL_TYPE,
             WO.AMOUNT,
             RCV.PRODUCT_CODE,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE，RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME), --到达部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE), --始发部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME，RCV.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
             AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
             AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收
             AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
                                   PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
                                   PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA                                   ) --到达应收、偏线到达应收、空运到达应收
             AND RCV.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
             AND RPMT.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
                  PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
                  PKG_STL_COMMON.PAYMENT_TYPE_NOTE,
                  PKG_STL_COMMON.PAYMENT_TYPE_ONLINE) --银行卡、电汇、支票、网上支付
             AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时已签收
             AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
             AND WO.ACCOUNT_DATE < P_END_DATE
             AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
             AND RCV.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_AF --精准空运
             AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
             PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA); --到付运费和空运到达代理应收


    RETURN TRUE;

  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        '空运_还款运单总运费（到付）【01】处理日明细' || '异常');

      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_UROA_DEST;
/
