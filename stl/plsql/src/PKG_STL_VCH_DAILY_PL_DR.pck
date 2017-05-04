CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_PL_DR IS

  -- ==============================================================
  -- Author  : ZBW
  -- Created : 2013-05-04
  -- Purpose : 每日凭证开单
  -- ==============================================================

  -----------------------------------------------------------------
  -- 凭证开单处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_DAILY_PL_DR;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_PL_DR IS

  -----------------------------------------------------------------
  -- 凭证开单处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS
  BEGIN
    --1)预收偏线代理冲应收到付运费已签收
    INSERT INTO STV.T_STL_DVD
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
             PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_POD, --业务场景
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             RCV.ORIG_ORG_CODE, --始发部门编码 
             RCV.ORIG_ORG_NAME, --始发部门名称
             RCV.DEST_ORG_CODE, --到达部门编码
             RCV.DEST_ORG_NAME, --到达部门名称
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
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL) --到达应收、到达偏线代理应收单
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__PA --偏线代理
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --核销时已签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --2)预收偏线代理冲应收到付运费未签收
    INSERT INTO STV.T_STL_DVD
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
             PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCV_NPOD, --业务场景
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             RCV.ORIG_ORG_CODE, --始发部门编码 
             RCV.ORIG_ORG_NAME, --始发部门名称
             RCV.DEST_ORG_CODE, --到达部门编码
             RCV.DEST_ORG_NAME, --到达部门名称
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
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL) --到达应收、到达偏线代理应收单
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__PA --偏线代理
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --核销时未签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --3)预收偏线代理现金
    INSERT INTO STV.T_STL_DVD
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
             PKG_STL_VCH_COMMON.PL_DR_CH, --业务场景
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.CREATE_ORG_CODE, --始发部门编码 
             DR.CREATE_ORG_NAME, --始发部门名称
             DR.CREATE_ORG_CODE, --到达部门编码
             DR.CREATE_ORG_NAME, --到达部门名称
             DR.DEPOSIT_RECEIVED_NO, --运单号
             DR.DEPOSIT_RECEIVED_NO, --单据编号
             DR.ACCOUNT_DATE, --会计日期
             DR.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --单据类型
             DR.BILL_TYPE, --单据子类型
             DR.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_PLF --产品类型
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DR
       WHERE DR.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH --现金
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__PA --偏线代理
         AND DR.ACCOUNT_DATE >= P_BEGIN_DATE
         AND DR.ACCOUNT_DATE < P_END_DATE;
  
    --4)预收偏线代理银行
    INSERT INTO STV.T_STL_DVD
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
             PKG_STL_VCH_COMMON.PL_DR_CD, --业务场景
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.CREATE_ORG_CODE, --始发部门编码 
             DR.CREATE_ORG_NAME, --始发部门名称
             DR.CREATE_ORG_CODE, --到达部门编码
             DR.CREATE_ORG_NAME, --到达部门名称
             DR.DEPOSIT_RECEIVED_NO, --运单号
             DR.DEPOSIT_RECEIVED_NO, --单据编号
             DR.ACCOUNT_DATE, --会计日期
             DR.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --单据类型
             DR.BILL_TYPE, --单据子类型
             DR.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_PLF --产品类型
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DR
       WHERE DR.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
              PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
              PKG_STL_COMMON.PAYMENT_TYPE_NOTE,
              PKG_STL_COMMON.PAYMENT_TYPE_ONLINE) --现金
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__PA --偏线代理
         AND DR.ACCOUNT_DATE >= P_BEGIN_DATE
         AND DR.ACCOUNT_DATE < P_END_DATE;
  
    --5) 偏线退预收付款申请
    INSERT INTO STV.T_STL_DVD
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
             PKG_STL_VCH_COMMON.PL_DR_PAY_APPLY, --业务场景
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.CREATE_ORG_CODE, --始发部门编码 
             DR.CREATE_ORG_NAME, --始发部门名称
             DR.CREATE_ORG_CODE, --到达部门编码
             DR.CREATE_ORG_NAME, --到达部门名称
             DR.DEPOSIT_RECEIVED_NO, --运单号
             DR.DEPOSIT_RECEIVED_NO, --单据编号
             DR.ACCOUNT_DATE, --会计日期
             DR.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --单据类型
             DR.BILL_TYPE, --单据子类型
             DP.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.YES, -1, 1), --金额, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_PLF --产品类型
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DR --预收单
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON DR.DEPOSIT_RECEIVED_NO = DP.SOURCE_BILL_NO --付款单明细
        LEFT JOIN STL.T_STL_BILL_PAYMENT PMT
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --付款单
       WHERE DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__PA --偏线代理
            -- AND PMT.PAYMENT_NO = DR.PAYMENT_NO 2013-08-01 modified by zbw
             AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --非红单
         AND DP.PAY_AMOUNT > 0
         AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PMT.ACCOUNT_DATE < P_END_DATE;
  
    RETURN TRUE;
  
  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        '凭证开单处理日明细' || '异常');
    
      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_PL_DR;
/
