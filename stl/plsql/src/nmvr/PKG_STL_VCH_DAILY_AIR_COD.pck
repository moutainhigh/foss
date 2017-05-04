CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_AIR_COD IS

  -- ==============================================================
  -- Author  : ZBW
  -- Created : 2013-11-06
  -- Purpose : 每日凭证空运代收货款
  -- ==============================================================

  -----------------------------------------------------------------
  -- 空运代收货款处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_DAILY_AIR_COD;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_AIR_COD IS

  -----------------------------------------------------------------
  -- 空运代收货款处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS
  BEGIN
  
    --01) 空运还款代收货款现金已签收
    --02) 空运还款代收货款银行已签收
    --03) 空运签收时已核销代收货款
    --04) 空运反签收时已核销代收货款
    --05) 空运还款代收货款现金未签收
    --06) 空运还款代收货款银行未签收
    --07) 空运到达代理应付冲应收代收货款已签收
    --08) 空运其他应付冲应收代收货款已签收
    --09) 空运到达代理应付冲应收代收货款未签收
    --10) 空运其他应付冲应收代收货款未签收
    --11) 往来 空运签收时未核销代收货款
    --12) 往来 空运反签收时未核销代收货款
    --13) 往来 空运还款代收货款现金已签收
    --14) 往来 空运还款代收货款银行已签收
    --15) 往来 空运签收时已核销代收货款
    --16) 往来 空运反签收时已核销代收货款
    --17) 往来 空运到达代理应付冲应收代收货款已签收
    --18) 往来 空运其他应付冲应收代收货款已签收
  
    --01) 空运还款代收货款现金已签收
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
             PKG_STL_VCH_COMMON.AIR_COD_CH_UR_POD, --业务场景
             RCV.CUSTOMER_CODE,
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             RCV.ORIG_ORG_CODE,
             RCV.ORIG_ORG_NAME, --始发部门名称
             RCV.DEST_ORG_CODE,
             RCV.DEST_ORG_NAME, --到达部门名称
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --运单号 --RCV.WAYBILL_NO,
             RCV.RECEIVABLE_NO,
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应收单
             RCV.BILL_TYPE, --单据类型
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
         AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --还款单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --应收单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --空运代理代收货款应收
         AND RPMT.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH --现金
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
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --02) 空运还款代收货款银行已签收
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
             PKG_STL_VCH_COMMON.AIR_COD_CD_UR_POD, --业务场景 
             RCV.CUSTOMER_CODE,
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             RCV.ORIG_ORG_CODE,
             RCV.ORIG_ORG_NAME, --始发部门名称
             RCV.DEST_ORG_CODE,
             RCV.DEST_ORG_NAME, --到达部门名称
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --RCV.WAYBILL_NO,
             RCV.RECEIVABLE_NO,
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应收单
             RCV.BILL_TYPE, --单据类型
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
         AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --还款单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --应收单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --空运代理代收货款应收
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
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --03) 空运签收时已核销代收货款
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
             PKG_STL_VCH_COMMON.AIR_COD_POD_WO, --业务场景
             RCV.CUSTOMER_CODE,
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             RCV.ORIG_ORG_CODE,
             RCV.ORIG_ORG_NAME, --始发部门名称
             RCV.DEST_ORG_CODE,
             RCV.DEST_ORG_NAME, --到达部门名称
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --RCV.WAYBILL_NO,
             RCV.RECEIVABLE_NO,
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应收单
             RCV.BILL_TYPE, --单据类型          
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE
        FROM STL.T_STL_POD POD
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.WAYBILL_NO = POD.WAYBILL_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_WRITEOFF WO
          ON (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
             WO.END_NO = RCV.RECEIVABLE_NO)
       WHERE POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__POD --签收
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --空运代理代收货款应收
         AND WO.ACCOUNT_DATE < POD.POD_DATE --签收时间＞核销单的记账时间
         AND POD.POD_DATE >= P_BEGIN_DATE
         AND POD.POD_DATE < P_END_DATE;
  
    --04) 空运反签收时已核销代收货款
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
             PKG_STL_VCH_COMMON.AIR_COD_UPD_WO, --业务场景
             RCV.CUSTOMER_CODE,
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             RCV.ORIG_ORG_CODE,
             RCV.ORIG_ORG_NAME, --始发部门名称
             RCV.DEST_ORG_CODE,
             RCV.DEST_ORG_NAME, --到达部门名称
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), 
             RCV.RECEIVABLE_NO,
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应收单
             RCV.BILL_TYPE, --单据类型          
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE
        FROM STL.T_STL_POD POD
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.WAYBILL_NO = POD.WAYBILL_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_WRITEOFF WO
          ON (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
             WO.END_NO = RCV.RECEIVABLE_NO)
       WHERE POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__UPD --反签收
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --空运代理代收货款应收
         AND WO.ACCOUNT_DATE < POD.POD_DATE --签收时间＞核销单的记账时间
         AND POD.POD_DATE >= P_BEGIN_DATE
         AND POD.POD_DATE < P_END_DATE;
  
    --05) 空运还款代收货款现金未签收
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
             PKG_STL_VCH_COMMON.AIR_COD_CH_UR_NPOD, --业务场景
             RCV.CUSTOMER_CODE,
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             RCV.ORIG_ORG_CODE,
             RCV.ORIG_ORG_NAME, --始发部门名称
             RCV.DEST_ORG_CODE,
             RCV.DEST_ORG_NAME, --到达部门名称
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --RCV.WAYBILL_NO,
             RCV.RECEIVABLE_NO,
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应收单
             RCV.BILL_TYPE, --单据类型
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
         AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --还款单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --应收单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收    
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --空运代理代收货款应收
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
  
    --06) 空运还款代收货款银行未签收
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
             PKG_STL_VCH_COMMON.AIR_COD_CD_UR_NPOD, --业务场景
             RCV.CUSTOMER_CODE,
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             RCV.ORIG_ORG_CODE,
             RCV.ORIG_ORG_NAME, --始发部门名称
             RCV.DEST_ORG_CODE,
             RCV.DEST_ORG_NAME, --到达部门名称
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --RCV.WAYBILL_NO,
             RCV.RECEIVABLE_NO,
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应收单
             RCV.BILL_TYPE, --单据类型
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
         AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --还款单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --应收单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收      
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --空运代理代收货款应收
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
  
    --07) 空运到达代理应付冲应收代收货款已签收
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
             PKG_STL_VCH_COMMON.AIR_COD_DPAY_WO_COD_RCV_POD,
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             RCV.ORIG_ORG_CODE, --出发部门编码
             RCV.ORIG_ORG_NAME, --始发部门名称
             RCV.DEST_ORG_CODE, --到达部门编码
             RCV.DEST_ORG_NAME, --到达部门名称
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --RCV.WAYBILL_NO, --运单号
             RCV.RECEIVABLE_NO,
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应收单
             RCV.BILL_TYPE, --单据类型
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
          ON WO.BEGIN_NO = RCV.RECEIVABLE_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --应收单
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --应付单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY --空运到达代理应付
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --空运代理代收货款应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应付02 应收02
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --08) 空运其他应付冲应收代收货款已签收
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
             PKG_STL_VCH_COMMON.AIR_COD_OTH_PAY_WO_COD_RCV_POD,
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             RCV.ORIG_ORG_CODE, --出发部门编码
             RCV.ORIG_ORG_NAME, --始发部门名称
             RCV.DEST_ORG_CODE, --到达部门编码
             RCV.DEST_ORG_NAME, --到达部门名称
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --RCV.WAYBILL_NO, --运单号
             RCV.RECEIVABLE_NO,
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应收单
             RCV.BILL_TYPE, --单据类型
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
          ON WO.BEGIN_NO = RCV.RECEIVABLE_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --应收单
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --应付单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER --空运其它应付
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --空运代理代收货款应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应付02 应收02
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --09) 空运到达代理应付冲应收代收货款未签收
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
             PKG_STL_VCH_COMMON.AIR_COD_COST_WO_COD_RCV_NPOD,
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             RCV.ORIG_ORG_CODE, --出发部门编码
             RCV.ORIG_ORG_NAME, --始发部门名称
             RCV.DEST_ORG_CODE, --到达部门编码
             RCV.DEST_ORG_NAME, --到达部门名称
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO),
             RCV.RECEIVABLE_NO,
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应收单
             RCV.BILL_TYPE, --单据类型
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
          ON WO.BEGIN_NO = RCV.RECEIVABLE_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --应收单
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --应付单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY --空运到达代理应付
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --空运代理代收货款应收
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
         AND WO.ACCOUNT_DATE < P_END_DATE --应付02 应收02
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --10) 空运其他应付冲应收代收货款未签收
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
             PKG_STL_VCH_COMMON.AIR_COD_OPAY_WO_COD_RCV_NPOD,
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             RCV.ORIG_ORG_CODE, --出发部门编码
             RCV.ORIG_ORG_NAME, --始发部门名称
             RCV.DEST_ORG_CODE, --到达部门编码
             RCV.DEST_ORG_NAME, --到达部门名称
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --RCV.WAYBILL_NO, --运单号
             RCV.RECEIVABLE_NO,
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应收单
             RCV.BILL_TYPE, --单据类型
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
          ON WO.BEGIN_NO = RCV.RECEIVABLE_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --应收单
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --应付单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER --空运其它应付
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --应收单的单据子类型为代收货款应收（产品类型为：精准空运）、空运代理代收货款应收
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
         AND WO.ACCOUNT_DATE < P_END_DATE --应付02 应收02
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --11) 往来 空运签收时未核销代收货款
    DECLARE
    
      CURSOR CUR_POD IS
        SELECT *
          FROM STL.T_STL_POD POD
         WHERE POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__POD
           AND POD.POD_DATE >= P_BEGIN_DATE
           AND POD.POD_DATE < P_END_DATE;
    
      V_POD_ROW STL.T_STL_POD%ROWTYPE;
    
    BEGIN
      FOR V_POD_ROW IN CUR_POD LOOP
      
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
                 PKG_STL_VCH_COMMON.AIR_COD_POD_NWO,
                 RCV.CUSTOMER_CODE,
                 RCV.CUSTOMER_NAME, --客户名称
                 RCV.CUSTOMER_TYPE, --客户类型
                 NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO),
                 RCV.RECEIVABLE_NO,
                 RCV.ACCOUNT_DATE, --记账日期
                 RCV.BUSINESS_DATE, --业务日期
                 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应收单
                 RCV.BILL_TYPE, --单据类型          
                 RCV.AMOUNT -
                 NVL((SELECT SUM(WO.AMOUNT)
                       FROM STL.T_STL_BILL_WRITEOFF WO
                      WHERE (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
                            WO.END_NO = RCV.RECEIVABLE_NO)
                        AND WO.ACCOUNT_DATE < V_POD_ROW.POD_DATE),
                     0), --金额
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
                        RCV.ORIG_ORG_CODE), --出发部门编码
                 DECODE(RCV.PRODUCT_CODE,
                        PKG_STL_COMMON.PRODUCT_CODE_PKG,
                        RCV.EXPRESS_ORIG_ORG_NAME,
                        RCV.ORIG_ORG_NAME), --始发部门名称
                 PKG_STL_COMMON.INVOICE_MARK_TWO,
                 PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
            FROM STL.T_STL_BILL_RECEIVABLE RCV
           WHERE RCV.ID IN
                 (SELECT ID
                    FROM (SELECT *
                            FROM STL.T_STL_BILL_RECEIVABLE T
                           WHERE T.WAYBILL_NO = V_POD_ROW.WAYBILL_NO
                             AND T.BILL_TYPE =
                                 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --空运代理代收货款应收
                             AND T.ACCOUNT_DATE < V_POD_ROW.POD_DATE
                           ORDER BY T.ACCOUNT_DATE DESC,
                                    T.CREATE_TIME  DESC,
                                    T.IS_RED_BACK  ASC)
                   WHERE ROWNUM <= 1)
             AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --取最新一条应收单，如何是红单，就过滤掉
             AND RCV.AMOUNT <> 0
             AND RCV.AMOUNT <>
                 NVL((SELECT SUM(WO.AMOUNT)
                       FROM STL.T_STL_BILL_WRITEOFF WO
                      WHERE (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
                            WO.END_NO = RCV.RECEIVABLE_NO)
                        AND WO.ACCOUNT_DATE < V_POD_ROW.POD_DATE),
                     0);
      END LOOP;
    END;
  
    --12) 往来 空运反签收时未核销代收货款
    DECLARE
      CURSOR CUR_UPD IS
        SELECT *
          FROM STL.T_STL_POD POD
         WHERE POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__UPD
           AND POD.POD_DATE >= P_BEGIN_DATE
           AND POD.POD_DATE < P_END_DATE;
    
      V_POD_ROW STL.T_STL_POD%ROWTYPE;
    
    BEGIN
      FOR V_POD_ROW IN CUR_UPD LOOP
      
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
                 PKG_STL_VCH_COMMON.AIR_COD_UPD_NWO,
                 RCV.CUSTOMER_CODE,
                 RCV.CUSTOMER_NAME, --客户名称
                 RCV.CUSTOMER_TYPE, --客户类型
                 NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO),
                 RCV.RECEIVABLE_NO,
                 RCV.ACCOUNT_DATE, --记账日期
                 RCV.BUSINESS_DATE, --业务日期
                 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应收单
                 RCV.BILL_TYPE, --单据类型          
                 RCV.AMOUNT -
                 NVL((SELECT SUM(WO.AMOUNT)
                       FROM STL.T_STL_BILL_WRITEOFF WO
                      WHERE (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
                            WO.END_NO = RCV.RECEIVABLE_NO)
                        AND WO.ACCOUNT_DATE < V_POD_ROW.POD_DATE),
                     0),
                 RCV.PRODUCT_CODE,
                 DECODE(RCV.PRODUCT_CODE,
                        PKG_STL_COMMON.PRODUCT_CODE_PKG,
                        RCV.EXPRESS_ORIG_ORG_CODE,
                        RCV.ORIG_ORG_CODE), --始发部门编码
                 DECODE(RCV.PRODUCT_CODE,
                        PKG_STL_COMMON.PRODUCT_CODE_PKG,
                        RCV.EXPRESS_ORIG_ORG_NAME,
                        RCV.ORIG_ORG_NAME), --始发部门名称
                 PKG_STL_COMMON.INVOICE_MARK_TWO,
                 PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
                 DECODE(RCV.PRODUCT_CODE,
                        PKG_STL_COMMON.PRODUCT_CODE_PKG,
                        RCV.EXPRESS_DEST_ORG_CODE,
                        RCV.DEST_ORG_CODE), --到达部门编码
                 DECODE(RCV.PRODUCT_CODE,
                        PKG_STL_COMMON.PRODUCT_CODE_PKG,
                        RCV.EXPRESS_DEST_ORG_NAME,
                        RCV.DEST_ORG_NAME), --到达部门名称
                 PKG_STL_COMMON.INVOICE_MARK_TWO,
                 PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST
            FROM STL.T_STL_BILL_RECEIVABLE RCV
           WHERE RCV.ID IN
                 (SELECT ID
                    FROM (SELECT *
                            FROM STL.T_STL_BILL_RECEIVABLE T
                           WHERE T.WAYBILL_NO = V_POD_ROW.WAYBILL_NO
                             AND T.BILL_TYPE =
                                 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --空运代理代收货款应收
                             AND T.ACCOUNT_DATE < V_POD_ROW.POD_DATE
                           ORDER BY T.ACCOUNT_DATE DESC,
                                    T.CREATE_TIME  DESC,
                                    T.IS_RED_BACK  ASC)
                   WHERE ROWNUM <= 1)
             AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --取最新一条应收单，如何是红单，就过滤掉 
             AND RCV.AMOUNT <> 0
             AND RCV.AMOUNT -
                 NVL((SELECT SUM(WO.AMOUNT)
                       FROM STL.T_STL_BILL_WRITEOFF WO
                      WHERE (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
                            WO.END_NO = RCV.RECEIVABLE_NO)
                        AND WO.ACCOUNT_DATE < V_POD_ROW.POD_DATE),
                     0) <> 0;
      
      END LOOP;
    END;
  
    --13) 往来 空运还款代收货款现金已签收
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
             PKG_STL_VCH_COMMON.AIR_COD_CH_UR_POD, --业务场景
             RCV.CUSTOMER_CODE,
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --运单号 --RCV.WAYBILL_NO,
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
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --到达部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
         AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --还款单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --应收单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --空运代理代收货款应收
         AND RPMT.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH --现金
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --14) 往来 空运还款代收货款银行已签收
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
             PKG_STL_VCH_COMMON.AIR_COD_CD_UR_POD, --业务场景 
             RCV.CUSTOMER_CODE,
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --RCV.WAYBILL_NO,
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
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --到达部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
         AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --还款单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --应收单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --空运代理代收货款应收
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
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --15) 往来 空运签收时已核销代收货款
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
             PKG_STL_VCH_COMMON.AIR_COD_POD_WO, --业务场景
             RCV.CUSTOMER_CODE,
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --RCV.WAYBILL_NO,
             RCV.RECEIVABLE_NO,
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
        FROM STL.T_STL_POD POD
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.WAYBILL_NO = POD.WAYBILL_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_WRITEOFF WO
          ON (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
             WO.END_NO = RCV.RECEIVABLE_NO)
       WHERE POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__POD --签收
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --空运代理代收货款应收
         AND WO.ACCOUNT_DATE < POD.POD_DATE --签收时间＞核销单的记账时间
         AND POD.POD_DATE >= P_BEGIN_DATE
         AND POD.POD_DATE < P_END_DATE;
  
    --16) 往来 空运反签收时已核销代收货款
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
             PKG_STL_VCH_COMMON.AIR_COD_UPD_WO, --业务场景
             RCV.CUSTOMER_CODE,
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             RCV.WAYBILL_NO,
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --RCV.RECEIVABLE_NO,
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应收单
             RCV.BILL_TYPE, --单据类型          
             WO.AMOUNT,
             RCV.PRODUCT_CODE,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --始发部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --到达部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST
        FROM STL.T_STL_POD POD
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.WAYBILL_NO = POD.WAYBILL_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_WRITEOFF WO
          ON (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
             WO.END_NO = RCV.RECEIVABLE_NO)
       WHERE POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__UPD --反签收
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --空运代理代收货款应收
         AND WO.ACCOUNT_DATE < POD.POD_DATE --签收时间＞核销单的记账时间
         AND POD.POD_DATE >= P_BEGIN_DATE
         AND POD.POD_DATE < P_END_DATE;
  
    --17) 往来 空运到达代理应付冲应收代收货款已签收
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
             PKG_STL_VCH_COMMON.AIR_COD_DPAY_WO_COD_RCV_POD,
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --RCV.WAYBILL_NO, --运单号
             WO.WRITEOFF_BILL_NO,
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应收单
             RCV.BILL_TYPE, --单据类型
             WO.AMOUNT, --金额
             RCV.PRODUCT_CODE, --产品编码
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
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --到达部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON WO.BEGIN_NO = RCV.RECEIVABLE_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --应收单
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --应付单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY --空运到达代理应付
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --空运代理代收货款应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应付02 应收02
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --18) 往来 空运其他应付冲应收代收货款已签收
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
             PKG_STL_VCH_COMMON.AIR_COD_OTH_PAY_WO_COD_RCV_POD,
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --RCV.WAYBILL_NO, --运单号
             WO.WRITEOFF_BILL_NO,
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --应收单
             RCV.BILL_TYPE, --单据类型
             WO.AMOUNT, --金额
             RCV.PRODUCT_CODE, --产品编码
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
                    RCV.EXPRESS_DEST_ORG_CODE,
                    RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,
                    RCV.DEST_ORG_NAME), --到达部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON WO.BEGIN_NO = RCV.RECEIVABLE_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --应收单
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --应付单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER --空运其它应付
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --空运代理代收货款应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应付02 应收02
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    RETURN TRUE;
  
  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        '空运代收货款处理日明细' || '异常');
    
      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_AIR_COD;
/
