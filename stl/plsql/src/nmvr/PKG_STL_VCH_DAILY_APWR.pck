CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_APWR IS

  -- ==============================================================
  -- Author  : ZBW
  -- Created : 2013-11-06
  -- Purpose : 每日凭证空运应付冲应收
  -- ==============================================================

  -----------------------------------------------------------------
  -- 空运应付冲应收处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_DAILY_APWR;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_APWR IS

  -----------------------------------------------------------------
  -- 空运应付冲应收处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS
  BEGIN
  
    --01) 应付到达代理成本冲01应收到付运费已签收
    --02) 应付到达代理成本冲01应收到付运费未签收
    --03) 应付到达代理成本冲02应收到付运费已签收
    --04) 应付到达代理成本冲02应收到付运费未签收
    --05) 其他应付冲01应收到付运费已签收
    --06) 其他应付冲01应收到付运费未签收
    --07) 其他应付冲02应收到付运费已签收
    --08) 其他应付冲02应收到付运费未签收
    --09) 其他应付冲其他应收
    --10) 往来 应付到达代理成本冲01应收到付运费已签收
    --11) 往来 应付到达代理成本冲01应收到付运费未签收
    --12) 往来 应付到达代理成本冲02应收到付运费已签收
    --13) 往来 应付到达代理成本冲02应收到付运费未签收
    --14) 往来 其他应付冲01应收到付运费已签收
    --15) 往来 其他应付冲01应收到付运费未签收
    --16) 往来 其他应付冲02应收到付运费已签收
    --17) 往来 其他应付冲02应收到付运费未签收
  
    --01) 应付到达代理成本冲01应收到付运费已签收
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
             PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_POD, --业务场景
             PA.CUSTOMER_CODE, --客户编码
             PA.CUSTOMER_NAME, --客户名称
             PA.CUSTOMER_TYPE, --客户类型
             PA.ORIG_ORG_CODE, --始发部门编码
             PA.ORIG_ORG_NAME, --始发部门名称
             PA.DEST_ORG_CODE, --到达部门编码
             PA.DEST_ORG_NAME, --到达部门名称
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --运单号
             PA.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             PA.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PA.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_AF) --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --核销时已签收
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --到达应收
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --空运到达代理应收
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --02) 应付到达代理成本冲01应收到付运费未签收
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
             PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_NPOD, --业务场景
             PA.CUSTOMER_CODE, --客户编码
             PA.CUSTOMER_NAME, --客户名称
             PA.CUSTOMER_TYPE, --客户类型
             PA.ORIG_ORG_CODE, --始发部门编码
             PA.ORIG_ORG_NAME, --始发部门名称
             PA.DEST_ORG_CODE, --到达部门编码
             PA.DEST_ORG_NAME, --到达部门名称
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --运单号
             PA.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             PA.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PA.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_AF) --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --核销时未签收
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --到达应收
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --空运到达代理应收 
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --03) 应付到达代理成本冲02应收到付运费已签收
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
             PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_POD,
             PA.CUSTOMER_CODE, --客户编码
             PA.CUSTOMER_NAME, --客户名称
             PA.CUSTOMER_TYPE, --客户类型
             PA.ORIG_ORG_CODE, --始发部门编码
             PA.ORIG_ORG_NAME, --始发部门名称
             PA.DEST_ORG_CODE, --到达部门编码
             PA.DEST_ORG_NAME, --到达部门名称
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --运单号
             PA.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             PA.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PA.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_AF) --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --核销时已签收
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --到达应收
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --空运到达代理应收 
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --04) 应付到达代理成本冲02应收到付运费未签收
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
             PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_NPOD,
             PA.CUSTOMER_CODE, --客户编码
             PA.CUSTOMER_NAME, --客户名称
             PA.CUSTOMER_TYPE, --客户类型
             PA.ORIG_ORG_CODE, --始发部门编码
             PA.ORIG_ORG_NAME, --始发部门名称
             PA.DEST_ORG_CODE, --到达部门编码
             PA.DEST_ORG_NAME, --到达部门名称
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --运单号
             PA.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             PA.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PA.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_AF) --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --核销时未签收
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --到达应收
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --空运到达代理应收
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --05) 其他应付冲01应收到付运费已签收
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
             PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_POD, --业务场景
             PA.CUSTOMER_CODE, --客户编码
             PA.CUSTOMER_NAME, --客户名称
             PA.CUSTOMER_TYPE, --客户类型
             PA.ORIG_ORG_CODE, --始发部门编码
             PA.ORIG_ORG_NAME, --始发部门名称
             PA.DEST_ORG_CODE, --到达部门编码
             PA.DEST_ORG_NAME, --到达部门名称
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --运单号
             PA.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             PA.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PA.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PA.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --核销时已签收
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --到达应收
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --空运到达代理应收  
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --06) 其他应付冲01应收到付运费未签收
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
             PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_NPOD, --业务场景
             PA.CUSTOMER_CODE, --客户编码
             PA.CUSTOMER_NAME, --客户名称
             PA.CUSTOMER_TYPE, --客户类型
             PA.ORIG_ORG_CODE, --始发部门编码
             PA.ORIG_ORG_NAME, --始发部门名称
             PA.DEST_ORG_CODE, --到达部门编码
             PA.DEST_ORG_NAME, --到达部门名称
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --运单号
             PA.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             PA.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PA.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PA.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --核销时未签收
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --到达应收
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --空运到达代理应收 
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --07) 其他应付冲02应收到付运费已签收
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
             PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_POD, --业务场景
             PA.CUSTOMER_CODE, --客户编码
             PA.CUSTOMER_NAME, --客户名称
             PA.CUSTOMER_TYPE, --客户类型
             PA.ORIG_ORG_CODE, --始发部门编码
             PA.ORIG_ORG_NAME, --始发部门名称
             PA.DEST_ORG_CODE, --到达部门编码
             PA.DEST_ORG_NAME, --到达部门名称
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --运单号
             PA.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             PA.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PA.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PA.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --核销时已签收
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --到达应收
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --空运到达代理应收 
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --08) 其他应付冲02应收到付运费未签收
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
             PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_NPOD, --业务场景
             PA.CUSTOMER_CODE, --客户编码
             PA.CUSTOMER_NAME, --客户名称
             PA.CUSTOMER_TYPE, --客户类型
             PA.ORIG_ORG_CODE, --始发部门编码
             PA.ORIG_ORG_NAME, --始发部门名称
             PA.DEST_ORG_CODE, --到达部门编码
             PA.DEST_ORG_NAME, --到达部门名称
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --运单号
             PA.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             PA.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PA.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PA.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --核销时未签收
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --到达应收
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --空运到达代理应收  
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --09) 其他应付冲其他应收
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
             PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_OTH_RCV,
             PA.CUSTOMER_CODE, --客户编码
             PA.CUSTOMER_NAME, --客户名称
             PA.CUSTOMER_TYPE, --客户类型
             PA.ORIG_ORG_CODE, --始发部门编码
             PA.ORIG_ORG_NAME, --始发部门名称
             PA.DEST_ORG_CODE, --到达部门编码
             PA.DEST_ORG_NAME, --到达部门名称
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --运单号
             PA.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             PA.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PA.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_AF --产品类型空运
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AR --空运其他应收
         AND PA.BILL_TYPE IN
             (PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER, --空运其他应付
              PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR, --航空公司运费
              PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_ORIGINAL, --空运出发代理应付
              PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY) --空运到达代理应付
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PA.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --应付02 应收02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --10) 往来 应付到达代理成本冲01应收到付运费已签收
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
             PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_POD, --业务场景
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --运单号
             RCV.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RCV.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             RCV.PRODUCT_CODE, --产品类型
             PA.DEST_ORG_CODE, --应付部门编码
             PA.DEST_ORG_NAME, --应付部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             RCV.ORIG_ORG_CODE, --始发部门编码
             RCV.ORIG_ORG_NAME, --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --核销时已签收
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --到达应收
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --空运到达代理应收
            --PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL 落地配到达代理应收  
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY
            --(PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY, --空运到达代理应付
            --PKG_STL_COMMON.PAYABLE_BILL_TYPE_LANDSTOWAGE) --落地配代理应付
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --11) 往来 应付到达代理成本冲01应收到付运费未签收
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
             PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_NPOD, --业务场景
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --运单号
             RCV.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RCV.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             RCV.PRODUCT_CODE, --产品类型
             PA.DEST_ORG_CODE, --应付部门编码
             PA.DEST_ORG_NAME, --应付部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             RCV.ORIG_ORG_CODE, --始发部门编码
             RCV.ORIG_ORG_NAME, --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --核销时未签收
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --到达应收
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --空运到达代理应收
            --PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL 落地配到达代理应收  
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY
            --(PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY, --空运到达代理应付
            --PKG_STL_COMMON.PAYABLE_BILL_TYPE_LANDSTOWAGE) --落地配代理应付
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --12) 往来 应付到达代理成本冲02应收到付运费已签收
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
             PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_POD, --业务场景
             PA.CUSTOMER_CODE, --客户编码
             PA.CUSTOMER_NAME, --客户名称
             PA.CUSTOMER_TYPE, --客户类型
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --运单号
             PA.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             PA.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PA.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_AF), --产品类型
             PA.DEST_ORG_CODE, --应付部门编码
             PA.DEST_ORG_NAME, --应付部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             RCV.ORIG_ORG_CODE, --始发部门编码
             RCV.ORIG_ORG_NAME, --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --核销时已签收
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --到达应收
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --空运到达代理应收
            --PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL 落地配到达代理应收  
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY
            --(PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY, --空运到达代理应付
            --PKG_STL_COMMON.PAYABLE_BILL_TYPE_LANDSTOWAGE) --落地配代理应付
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --13) 往来 应付到达代理成本冲02应收到付运费未签收
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
             PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_NPOD, --业务场景
             PA.CUSTOMER_CODE, --客户编码
             PA.CUSTOMER_NAME, --客户名称
             PA.CUSTOMER_TYPE, --客户类型
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --运单号
             PA.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             PA.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PA.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_AF), --产品类型
             PA.DEST_ORG_CODE, --应付部门编码
             PA.DEST_ORG_NAME, --应付部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             RCV.ORIG_ORG_CODE, --始发部门编码
             RCV.ORIG_ORG_NAME, --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --核销时未签收
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --到达应收
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --空运到达代理应收
            --PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL 落地配到达代理应收  
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY
            --(PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY, --空运到达代理应付
            --PKG_STL_COMMON.PAYABLE_BILL_TYPE_LANDSTOWAGE) --落地配代理应付
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --14) 往来 其他应付冲01应收到付运费已签收
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
             PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_POD, --业务场景
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --运单号
             RCV.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RCV.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             RCV.PRODUCT_CODE, --产品类型
             PA.DEST_ORG_CODE, --应付部门编码
             PA.DEST_ORG_NAME, --应付部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             RCV.ORIG_ORG_CODE, --始发部门编码
             RCV.ORIG_ORG_NAME, --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --核销时已签收
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --到达应收
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --空运到达代理应收
            --PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --落地配到达代理应收  
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER
            --(PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER, --空运其他应付
            --PKG_STL_COMMON.PAYABLE_BILLTYPE_LAND_OTHER) --落地配其它应付 
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --15) 往来 其他应付冲01应收到付运费未签收
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
             PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_NPOD, --业务场景
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --运单号
             RCV.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RCV.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             RCV.PRODUCT_CODE, --产品类型
             PA.DEST_ORG_CODE, --应付部门编码
             PA.DEST_ORG_NAME, --应付部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             RCV.ORIG_ORG_CODE, --始发部门编码
             RCV.ORIG_ORG_NAME, --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --核销时未签收
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --到达应收
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --空运到达代理应收
            --PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --落地配到达代理应收  
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER
            --(PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER, --空运其他应付
            --PKG_STL_COMMON.PAYABLE_BILLTYPE_LAND_OTHER) --落地配其它应付 
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --16) 往来 其他应付冲02应收到付运费已签收
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
             PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_POD, --业务场景
             PA.CUSTOMER_CODE, --客户编码
             PA.CUSTOMER_NAME, --客户名称
             PA.CUSTOMER_TYPE, --客户类型
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --运单号
             PA.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             PA.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PA.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_AF), --产品类型
             PA.DEST_ORG_CODE, --应付部门编码
             PA.DEST_ORG_NAME, --应付部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             RCV.ORIG_ORG_CODE, --始发部门编码
             RCV.ORIG_ORG_NAME, --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --核销时已签收
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --到达应收
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --空运到达代理应收
            --PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --落地配到达代理应收  
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER
            --(PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER, --空运其他应付
            --PKG_STL_COMMON.PAYABLE_BILLTYPE_LAND_OTHER) --落地配其它应付 
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --17) 往来 其他应付冲02应收到付运费未签收
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
             PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_NPOD, --业务场景
             PA.CUSTOMER_CODE, --客户编码
             PA.CUSTOMER_NAME, --客户名称
             PA.CUSTOMER_TYPE, --客户类型
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --运单号
             PA.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             PA.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PA.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_AF), --产品类型
             PA.DEST_ORG_CODE, --应付部门编码
             PA.DEST_ORG_NAME, --应付部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             RCV.ORIG_ORG_CODE, --始发部门编码
             RCV.ORIG_ORG_NAME, --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --核销时未签收
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --到达应收
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --空运到达代理应收
            --PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --落地配到达代理应收  
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER
            --(PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER, --空运其他应付
            --PKG_STL_COMMON.PAYABLE_BILLTYPE_LAND_OTHER) --落地配其它应付 
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    RETURN TRUE;
  
  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        '空运应付冲应收处理日明细' || '异常');
    
      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_APWR;
/
