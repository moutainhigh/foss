CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_AL_DR IS

  -- ==============================================================
  -- Author  : ZBW
  -- Created : 2013-11-06
  -- Purpose : 每日凭证预收空运/落地配代理
  -- ==============================================================

  -----------------------------------------------------------------
  -- 预收空运/落地配代理处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_DAILY_AL_DR;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_AL_DR IS

  -----------------------------------------------------------------
  -- 预收空运/落地配代理处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS
  BEGIN
    --1) 预收空运/落地配代理冲01应收到付运费已签收
    --2) 预收空运/落地配代理冲01应收到付运费未签收
    --3) 预收空运/落地配代理冲02应收到付运费已签收
    --4) 预收空运/落地配代理冲02应收到付运费未签收
  
    --1) 预收空运/落地配代理冲01应收到付运费已签收
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
             PKG_STL_VCH_COMMON.AL_DR_WO_DEST_RCVO_POD,
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
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --运单号
             RCV.RECEIVABLE_NO, --单据编号
             RCV.ACCOUNT_DATE, --会计日期
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
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达应收、空运到达代理应收、到达落地配代理应收
         AND DR.TRANSPORT_TYPE in
             (PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA,
              PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LS) --空运代理、到达落地配代理应收
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
         AND WO.ACCOUNT_DATE < P_END_DATE --应收01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --2) 预收空运/落地配代理冲01应收到付运费未签收
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
             PKG_STL_VCH_COMMON.AL_DR_WO_DEST_RCVO_NPOD,
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
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --运单号
             RCV.RECEIVABLE_NO, --单据编号
             RCV.ACCOUNT_DATE, --会计日期
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
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达应收、空运到达代理应收、到达落地配代理应收
         AND DR.TRANSPORT_TYPE in
             (PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA,
              PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LS) --空运代理、到达落地配代理应收
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
         AND WO.ACCOUNT_DATE < P_END_DATE --应收01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --3) 预收空运/落地配代理冲02应收到付运费已签收
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
             PKG_STL_VCH_COMMON.AL_DR_WO_DEST_RCVT_POD,
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
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --运单号
             RCV.RECEIVABLE_NO, --单据编号
             RCV.ACCOUNT_DATE, --会计日期
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
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达应收、空运到达代理应收、到达落地配代理应收
         AND DR.TRANSPORT_TYPE in
             (PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA,
              PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LS) --空运代理,落地配代理
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
         AND WO.ACCOUNT_DATE < P_END_DATE --应收02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --4) 预收空运/落地配代理冲02应收到付运费未签收
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
             PKG_STL_VCH_COMMON.AL_DR_WO_DEST_RCVT_NPOD,
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
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --运单号
             RCV.RECEIVABLE_NO, --单据编号
             RCV.ACCOUNT_DATE, --会计日期
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
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达应收、空运到达代理应收、到达落地配代理应收
         AND DR.TRANSPORT_TYPE in
             (PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA,
              PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LS) --空运代理、到达落地配代理应收
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
                                        '预收空运/落地配代理处理日明细' || '异常');
    
      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_AL_DR;
/
