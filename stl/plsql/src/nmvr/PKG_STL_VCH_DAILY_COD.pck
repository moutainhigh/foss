﻿CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_COD IS

  -- ==============================================================
  -- Author  : ZBW
  -- Created : 2013-11-06
  -- Purpose : 每日凭证代收货款
  -- ==============================================================

  -----------------------------------------------------------------
  -- 代收货款处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_DAILY_COD;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_COD IS

  -----------------------------------------------------------------
  -- 代收货款处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期YYYYMMDD,例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN -- 返回结果 TRUE:成功,FALSE:失败
   IS
  BEGIN
  
    --01) 应付代收货款冲01应收到付运费未签收
    --02) 应付代收货款冲02应收到付运费未签收
    --03) 应付代收货款冲01应收始发运费未签收
    --04) 应付代收货款冲02应收始发运费未签收
    --05) 应付代收货款冲01应收到付运费已签收
    --06) 应付代收货款冲02应收到付运费已签收
    --07) 应付代收货款冲01应收始发运费已签收
    --08) 应付代收货款冲02应收始发运费已签收
    --09) 还款代收货款现金未签收
    --10) 还款代收货款银行未签收
    --11) 往来 应付代收货款冲01应收到付运费未签收
    --12) 往来 应付代收货款冲02应收到付运费未签收
    --13) 往来 应付代收货款冲01应收始发运费未签收
    --14) 往来 应付代收货款冲02应收始发运费未签收
    --15) 往来 应付代收货款冲01应收到付运费已签收
    --16) 往来 应付代收货款冲02应收到付运费已签收
    --17) 往来 应付代收货款冲01应收始发运费已签收
    --18) 往来 应付代收货款冲02应收始发运费已签收
    --19) 往来 还款代收货款现金未签收
    --20) 往来 还款代收货款银行未签收
    --21) 往来 应付代收货款冲01小票应收
    --22) 往来 应付代收货款冲02小票应收
  
    --01) 应付代收货款冲01应收到付运费未签收
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
             PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVO_NPOD,
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --始发部门编码 
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --始发部门名称
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --运单号
             RCV.RECEIVABLE_NO,
             WO.ACCOUNT_DATE, --会计日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应付单
             RCV.BILL_TYPE, --应收单单据类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品编码
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON WO.BEGIN_NO = RCV.RECEIVABLE_NO --应收单
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO --应付单
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --代收货款
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST --到达应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE -- 应收01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --02) 应付代收货款冲02应收到付运费未签收
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
             PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVT_NPOD,
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --始发部门编码 
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --始发部门名称
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --运单号
             RCV.RECEIVABLE_NO,
             WO.ACCOUNT_DATE, --会计日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应付单
             RCV.BILL_TYPE, --应收单单据类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品编码
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON WO.BEGIN_NO = RCV.RECEIVABLE_NO --应收单
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO --应付单
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --代收货款
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST --到达应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE -- 应收02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --03) 应付代收货款冲01应收始发运费未签收
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
             PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVO_NPOD,
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --始发部门编码 
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --始发部门名称
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --运单号
             RCV.RECEIVABLE_NO,
             WO.ACCOUNT_DATE, --会计日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应付单
             RCV.BILL_TYPE, --应收单单据类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品编码
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON WO.BEGIN_NO = RCV.RECEIVABLE_NO --应收单
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO --应付单
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --代收货款
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE -- 应收01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --04) 应付代收货款冲02应收始发运费未签收
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
             PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVT_NPOD,
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --始发部门编码 
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --始发部门名称
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --运单号
             RCV.RECEIVABLE_NO,
             WO.ACCOUNT_DATE, --会计日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应付单
             RCV.BILL_TYPE, --应收单单据类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品编码
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON WO.BEGIN_NO = RCV.RECEIVABLE_NO --应收单
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO --应付单
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --代收货款
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE -- 应收02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --05) 应付代收货款冲01应收到付运费已签收
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
             PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVO_POD,
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --始发部门编码 
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --始发部门名称
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --运单号
             RCV.RECEIVABLE_NO,
             WO.ACCOUNT_DATE, --会计日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应付单
             RCV.BILL_TYPE, --应收单单据类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品编码
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON WO.BEGIN_NO = RCV.RECEIVABLE_NO --应收单
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO --应付单
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --代收货款
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST --到达应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE -- 应收01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --06) 应付代收货款冲02应收到付运费已签收
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
             PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVT_POD,
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --始发部门编码 
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --始发部门名称
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --运单号
             RCV.RECEIVABLE_NO,
             WO.ACCOUNT_DATE, --会计日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应付单
             RCV.BILL_TYPE, --应收单单据类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品编码
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON WO.BEGIN_NO = RCV.RECEIVABLE_NO --应收单
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO --应付单
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --代收货款
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST --到达应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE -- 应收02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --07) 应付代收货款冲01应收始发运费已签收
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
             PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVO_POD,
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --始发部门编码 
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --始发部门名称
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --运单号
             RCV.RECEIVABLE_NO,
             WO.ACCOUNT_DATE, --会计日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应付单
             RCV.BILL_TYPE, --应收单单据类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品编码
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON WO.BEGIN_NO = RCV.RECEIVABLE_NO --应收单
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO --应付单
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --代收货款
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE -- 应收01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --08) 应付代收货款冲02应收始发运费已签收
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
             PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVT_POD,
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --始发部门编码 
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --始发部门名称
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --运单号
             RCV.RECEIVABLE_NO,
             WO.ACCOUNT_DATE, --会计日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应付单
             RCV.BILL_TYPE, --应收单单据类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品编码
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON WO.BEGIN_NO = RCV.RECEIVABLE_NO --应收单
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO --应付单
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --代收货款
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE -- 应收02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --09) 还款代收货款现金未签收
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
      SELECT SYS_GUID(),
             P_PERIOD,
             PKG_STL_VCH_COMMON.COD_UR_CH_NPOD,
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --始发部门编码 
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --始发部门名称
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --运单号
             RCV.RECEIVABLE_NO,
             WO.ACCOUNT_DATE, --会计日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应付单
             RCV.BILL_TYPE, --应收单单据类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品编码
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO --还款单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO --应收单
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RCV.IS_RED_BACK = RPMT.IS_RED_BACK
         AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_COD --代收货款应收
         AND RPMT.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH --现金
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --10) 还款代收货款银行卡未签收
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
      SELECT SYS_GUID(),
             P_PERIOD,
             PKG_STL_VCH_COMMON.COD_UR_CD_NPOD, --业务场景：还款代收货款银行未签收
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --始发部门编码 
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --始发部门名称
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --运单号
             RCV.RECEIVABLE_NO,
             WO.ACCOUNT_DATE, --会计日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应付单
             RCV.BILL_TYPE, --应收单单据类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品编码
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO --还款单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO --应收单
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RCV.IS_RED_BACK = RPMT.IS_RED_BACK
         AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_COD --代收货款应收
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
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --11) 往来 应付代收货款冲01应收到付运费未签收
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
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE)
      SELECT SYS_GUID,
             P_PERIOD,
             PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVO_NPOD,
             WO.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型  
             DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PAY.SOURCE_BILL_NO,
                    PAY.WAYBILL_NO), --运单号
             WO.WRITEOFF_BILL_NO,
             WO.ACCOUNT_DATE, --会计日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --应付单
             PAY.BILL_TYPE, --应收单单据类型
             WO.AMOUNT, --金额
             PAY.PRODUCT_CODE, --产品编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --始发部门编码 
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --出发部门编码 
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON WO.BEGIN_NO = RCV.RECEIVABLE_NO --应收单
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO --应付单
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --代收货款
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST --到达应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE -- 应收01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --12) 往来 应付代收货款冲02应收到付运费未签收
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
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE)
      SELECT SYS_GUID,
             P_PERIOD,
             PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVT_NPOD,
             WO.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型  
             DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PAY.SOURCE_BILL_NO,
                    PAY.WAYBILL_NO), --PAY.WAYBILL_NO, --运单号
             WO.WRITEOFF_BILL_NO,
             WO.ACCOUNT_DATE, --会计日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --应付单
             PAY.BILL_TYPE, --应收单单据类型
             WO.AMOUNT, --金额
             PAY.PRODUCT_CODE, --产品编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --始发部门编码 
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --出发部门编码 
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON WO.BEGIN_NO = RCV.RECEIVABLE_NO --应收单
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO --应付单
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --代收货款
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST --到达应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE -- 应收02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --13) 往来 应付代收货款冲01应收始发运费未签收
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
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE)
      SELECT SYS_GUID,
             P_PERIOD,
             PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVO_NPOD,
             WO.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型  
             DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PAY.SOURCE_BILL_NO,
                    PAY.WAYBILL_NO), --PAY.WAYBILL_NO, --运单号
             WO.WRITEOFF_BILL_NO,
             WO.ACCOUNT_DATE, --会计日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --应付单
             PAY.BILL_TYPE, --应收单单据类型
             WO.AMOUNT, --金额
             PAY.PRODUCT_CODE, --产品编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --始发部门编码 
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --出发部门编码 
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON WO.BEGIN_NO = RCV.RECEIVABLE_NO --应收单
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO --应付单
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --代收货款
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE -- 应收01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --14) 往来 应付代收货款冲02应收始发运费未签收
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
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE)
      SELECT SYS_GUID,
             P_PERIOD,
             PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVT_NPOD,
             WO.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型  
             DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PAY.SOURCE_BILL_NO,
                    PAY.WAYBILL_NO), --PAY.WAYBILL_NO, --运单号
             WO.WRITEOFF_BILL_NO,
             WO.ACCOUNT_DATE, --会计日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --应付单
             PAY.BILL_TYPE, --应收单单据类型
             WO.AMOUNT, --金额
             PAY.PRODUCT_CODE, --产品编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --始发部门编码 
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --出发部门编码 
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON WO.BEGIN_NO = RCV.RECEIVABLE_NO --应收单
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO --应付单
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --代收货款
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE -- 应收02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --15) 往来 应付代收货款冲01应收到付运费已签收
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
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE)
      SELECT SYS_GUID,
             P_PERIOD,
             PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVO_POD,
             WO.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型  
             DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PAY.SOURCE_BILL_NO,
                    PAY.WAYBILL_NO), --PAY.WAYBILL_NO, --运单号
             WO.WRITEOFF_BILL_NO,
             WO.ACCOUNT_DATE, --会计日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --应付单
             PAY.BILL_TYPE, --应收单单据类型
             WO.AMOUNT, --金额
             PAY.PRODUCT_CODE, --产品编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --始发部门编码 
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --出发部门编码 
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON WO.BEGIN_NO = RCV.RECEIVABLE_NO --应收单
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO --应付单
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --代收货款
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST --到达应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE -- 应收01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --16) 往来 应付代收货款冲02应收到付运费已签收
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
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE)
      SELECT SYS_GUID,
             P_PERIOD,
             PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVT_POD,
             WO.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型  
             DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PAY.SOURCE_BILL_NO,
                    PAY.WAYBILL_NO), --PAY.WAYBILL_NO, --运单号
             WO.WRITEOFF_BILL_NO,
             WO.ACCOUNT_DATE, --会计日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --应付单
             PAY.BILL_TYPE, --应收单单据类型
             WO.AMOUNT, --金额
             PAY.PRODUCT_CODE, --产品编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --始发部门编码 
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --出发部门编码 
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON WO.BEGIN_NO = RCV.RECEIVABLE_NO --应收单
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO --应付单
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --代收货款
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST --到达应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE -- 应收02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --17) 往来 应付代收货款冲01应收始发运费已签收
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
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE)
      SELECT SYS_GUID,
             P_PERIOD,
             PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVO_POD,
             WO.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型  
             DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PAY.SOURCE_BILL_NO,
                    PAY.WAYBILL_NO), --PAY.WAYBILL_NO, --运单号
             WO.WRITEOFF_BILL_NO,
             WO.ACCOUNT_DATE, --会计日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --应付单
             PAY.BILL_TYPE, --应收单单据类型
             WO.AMOUNT, --金额
             PAY.PRODUCT_CODE, --产品编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --始发部门编码 
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --出发部门编码 
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON WO.BEGIN_NO = RCV.RECEIVABLE_NO --应收单
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO --应付单
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --代收货款
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE -- 应收01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --18) 往来 应付代收货款冲02应收始发运费已签收
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
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE)
      SELECT SYS_GUID,
             P_PERIOD,
             PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVT_POD,
             WO.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型  
             DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PAY.SOURCE_BILL_NO,
                    PAY.WAYBILL_NO), --PAY.WAYBILL_NO, --运单号
             WO.WRITEOFF_BILL_NO,
             WO.ACCOUNT_DATE, --会计日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --应付单
             PAY.BILL_TYPE, --应收单单据类型
             WO.AMOUNT, --金额
             PAY.PRODUCT_CODE, --产品编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --始发部门编码 
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --出发部门编码 
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON WO.BEGIN_NO = RCV.RECEIVABLE_NO --应收单
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO --应付单
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --代收货款
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE -- 应收02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --19) 往来 还款代收货款现金未签收
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
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE)
      SELECT SYS_GUID(),
             P_PERIOD,
             PKG_STL_VCH_COMMON.COD_UR_CH_NPOD,
             RCV.CUSTOMER_CODE,
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --RCV.WAYBILL_NO,
             WO.WRITEOFF_BILL_NO,
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应收单
             RCV.BILL_TYPE, --单据类型
             WO.AMOUNT,
             RCV.PRODUCT_CODE,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --到达部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --始发部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO --还款单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO --应收单
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RCV.IS_RED_BACK = RPMT.IS_RED_BACK
         AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_COD --代收货款应收
         AND RPMT.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH --现金
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --20) 往来 还款代收货款银行未签收
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
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE)
      SELECT SYS_GUID(),
             P_PERIOD,
             PKG_STL_VCH_COMMON.COD_UR_CD_NPOD,
             RCV.CUSTOMER_CODE,
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --RCV.WAYBILL_NO,
             WO.WRITEOFF_BILL_NO,
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应收单
             RCV.BILL_TYPE, --单据类型
             WO.AMOUNT,
             RCV.PRODUCT_CODE,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --到达部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --始发部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO --还款单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO --应收单
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RCV.IS_RED_BACK = RPMT.IS_RED_BACK
         AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_COD --代收货款应收
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
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --21) 往来 应付代收货款冲01小票应收
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
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --期间
             PKG_STL_VCH_COMMON.COD_PAY_WO_OTH_RCVO, --业务场景:应付代收货款冲小票应收
             T3.CUSTOMER_CODE, --客户编码
             T3.CUSTOMER_NAME,
             T3.CUSTOMER_TYPE,
             DECODE(NVL(T2.WAYBILL_NO, 'N/A'),
                    'N/A',
                    T2.SOURCE_BILL_NO,
                    T2.WAYBILL_NO), --T2.WAYBILL_NO, --运单号
             T2.RECEIVABLE_NO,
             T1.ACCOUNT_DATE,
             T1.ACCOUNT_DATE,
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应收
             T2.BILL_TYPE, -- 小票
             T1.AMOUNT, --金额
             DECODE(T2.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    T2.PRODUCT_CODE), --产品编码,默认精准卡航
             DECODE(T3.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    T3.EXPRESS_ORIG_ORG_CODE,
                    T3.ORIG_ORG_CODE), --应付单出发部门编码
             DECODE(T3.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    T3.EXPRESS_ORIG_ORG_NAME,
                    T3.ORIG_ORG_NAME), --应付单出发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             T2.RECEIVABLE_ORG_CODE, --小票收入部门
             T2.RECEIVABLE_ORG_NAME, --小票收入部门
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF   T1,
             STL.T_STL_BILL_RECEIVABLE T2,
             STL.T_STL_BILL_PAYABLE    T3
       WHERE T1.BEGIN_NO = T2.RECEIVABLE_NO
         AND T1.END_NO = T3.PAYABLE_NO
         AND T2.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND T3.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND T3.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --代收货款
         AND T2.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE -- 小票应收
         AND T1.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP -- 应收冲应付
         AND T1.ACCOUNT_DATE >= P_BEGIN_DATE
         AND T1.ACCOUNT_DATE < P_END_DATE --小票应收01
         AND T2.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --22) 往来 应付代收货款冲02小票应收
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
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --期间
             PKG_STL_VCH_COMMON.COD_PAY_WO_OTH_RCVT, --业务场景:应付代收货款冲小票应收
             T3.CUSTOMER_CODE, --客户编码
             T3.CUSTOMER_NAME,
             T3.CUSTOMER_TYPE,
             DECODE(NVL(T2.WAYBILL_NO, 'N/A'),
                    'N/A',
                    T2.SOURCE_BILL_NO,
                    T2.WAYBILL_NO), --T2.WAYBILL_NO, --运单号
             T2.RECEIVABLE_NO,
             T1.ACCOUNT_DATE,
             T1.ACCOUNT_DATE,
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应收
             T2.BILL_TYPE, -- 小票
             T1.AMOUNT, --金额
             DECODE(T2.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    T2.PRODUCT_CODE), --产品编码,默认精准卡航
             DECODE(T3.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    T3.EXPRESS_ORIG_ORG_CODE,
                    T3.ORIG_ORG_CODE), --应付单出发部门编码
             DECODE(T3.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    T3.EXPRESS_ORIG_ORG_NAME,
                    T3.ORIG_ORG_NAME), --应付单出发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             T2.RECEIVABLE_ORG_CODE, --小票收入部门
             T2.RECEIVABLE_ORG_NAME, --小票收入部门
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF   T1,
             STL.T_STL_BILL_RECEIVABLE T2,
             STL.T_STL_BILL_PAYABLE    T3
       WHERE T1.BEGIN_NO = T2.RECEIVABLE_NO
         AND T1.END_NO = T3.PAYABLE_NO
         AND T2.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND T3.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND T3.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --代收货款
         AND T2.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE -- 小票应收
         AND T1.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP -- 应收冲应付
         AND T1.ACCOUNT_DATE >= P_BEGIN_DATE
         AND T1.ACCOUNT_DATE < P_END_DATE --小票应收02
         AND T2.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    RETURN TRUE;
  
  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        '代收货款处理日明细' || '异常');
    
      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_COD;
/
