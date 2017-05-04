CREATE OR REPLACE PACKAGE STV.PKG_STL_VCH_DAILY_AIR_DR IS

  -- ==============================================================
  -- Author  : ZBW
  -- Created : 2013-11-06
  -- Purpose : 每日凭证预收空运代理
  -- ==============================================================

  -----------------------------------------------------------------
  -- 预收空运代理处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_DAILY_AIR_DR;
/
CREATE OR REPLACE PACKAGE BODY STV.PKG_STL_VCH_DAILY_AIR_DR IS

  -----------------------------------------------------------------
  -- 预收空运代理处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期YYYYMMDD，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN -- 返回结果 TRUE:成功，FALSE:失败
   IS
  BEGIN
  
    --01) 预收空运代理现金
    --02) 预收空运代理银行
    --03) 预收空运代理冲01应收到付运费已签收
    --04) 预收空运代理冲02应收到付运费已签收
    --05) 预收空运代理冲01应收到付运费未签收
    --06) 预收空运代理冲02应收到付运费未签收
    --07) 预收空运代理冲其他应收
    --08) 预收空运代理冲应收代收货款已签收
    --09) 预收空运代理冲应收代收货款未签收
    --10) 空运退预收付款申请
    --11) 往来 预收空运代理冲01应收到付运费已签收
    --12) 往来 预收空运代理冲02应收到付运费已签收
    --13) 往来 预收空运代理冲01应收到付运费未签收
    --14) 往来 预收空运代理冲02应收到付运费未签收
    --15) 往来 预收空运代理冲应收代收货款已签收
  
    --01) 预收空运代理现金
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
             PKG_STL_VCH_COMMON.AIR_DR_CH,
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --始发部门编码
             DR.GENERATING_ORG_NAME, --始发部门名称
             DR.GENERATING_ORG_CODE, --到达部门编码
             DR.GENERATING_ORG_NAME, --到达部门名称
             '', --运单号
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
             PKG_STL_COMMON.PRODUCT_CODE_AF --产品类型
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DR
       WHERE DR.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH --现金
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA --空运代理
         AND DR.ACCOUNT_DATE >= P_BEGIN_DATE
         AND DR.ACCOUNT_DATE < P_END_DATE --预收02
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --02) 预收空运代理银行
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
             PKG_STL_VCH_COMMON.AIR_DR_CD, --业务场景
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --始发部门编码
             DR.GENERATING_ORG_NAME, --始发部门名称
             DR.GENERATING_ORG_CODE, --到达部门编码
             DR.GENERATING_ORG_NAME, --到达部门名称
             '', --运单号
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
             PKG_STL_COMMON.PRODUCT_CODE_AF --产品类型
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DR
       WHERE DR.PAYMENT_TYPE IN (PKG_STL_COMMON.PAYMENT_TYPE_CARD, --银行卡
                                 PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER, --电汇
                                 PKG_STL_COMMON.PAYMENT_TYPE_NOTE, --支票
                                 PKG_STL_COMMON.PAYMENT_TYPE_ONLINE --网上支付
                                 )
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA --空运代理
         AND DR.ACCOUNT_DATE >= P_BEGIN_DATE
         AND DR.ACCOUNT_DATE < P_END_DATE --预收02
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --03) 预收空运代理冲01应收到付运费已签收
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
             PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVO_POD,
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --始发部门编码
             DR.GENERATING_ORG_NAME, --始发部门名称
             DR.GENERATING_ORG_CODE, --到达部门编码
             DR.GENERATING_ORG_NAME, --到达部门名称
             '', --运单号
             DR.DEPOSIT_RECEIVED_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             DR.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --单据类型
             DR.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_AF --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
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
            --PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL)--落地配到达代理应收 
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA
            --PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LS)--落地配代理
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --04) 预收空运代理冲02应收到付运费已签收
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
             PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVT_POD,
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --始发部门编码
             DR.GENERATING_ORG_NAME, --始发部门名称
             DR.GENERATING_ORG_CODE, --到达部门编码
             DR.GENERATING_ORG_NAME, --到达部门名称
             '', --运单号
             DR.DEPOSIT_RECEIVED_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             DR.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --单据类型
             DR.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_AF --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
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
            --PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL)--落地配到达代理应收 
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA
            --PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LS)--落地配代理
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --05) 预收空运代理冲01应收到付运费未签收
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
             PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVO_NPOD,
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --始发部门编码
             DR.GENERATING_ORG_NAME, --始发部门名称
             DR.GENERATING_ORG_CODE, --到达部门编码
             DR.GENERATING_ORG_NAME, --到达部门名称
             '', --运单号
             DR.DEPOSIT_RECEIVED_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             DR.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --单据类型
             DR.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_AF --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
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
            --PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL)--落地配到达代理应收 
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA
            --PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LS)--落地配代理
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --06) 预收空运代理冲02应收到付运费未签收
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
             PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVT_NPOD,
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --始发部门编码
             DR.GENERATING_ORG_NAME, --始发部门名称
             DR.GENERATING_ORG_CODE, --到达部门编码
             DR.GENERATING_ORG_NAME, --到达部门名称
             '', --运单号
             DR.DEPOSIT_RECEIVED_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             DR.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --单据类型
             DR.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_AF --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
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
            --PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL)--落地配到达代理应收 
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA
            --PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LS)--落地配代理
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --07) 预收空运代理冲其他应收
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
             PKG_STL_VCH_COMMON.AIR_DR_WO_OTH_RCV, --业务场景
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --始发部门编码
             DR.GENERATING_ORG_NAME, --始发部门名称
             DR.GENERATING_ORG_CODE, --到达部门编码
             DR.GENERATING_ORG_NAME, --到达部门名称
             '', --运单号
             DR.DEPOSIT_RECEIVED_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             DR.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --单据类型
             DR.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_AF --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AR --空运其他应收
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA --空运代理
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --预收02 应收02
         AND DR.Invoice_Mark = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND RCV.Invoice_Mark = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --08) 预收空运代理冲应收代收货款已签收
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
             PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_POD, --业务场景
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --始发部门编码
             DR.GENERATING_ORG_NAME, --始发部门名称
             DR.GENERATING_ORG_CODE, --到达部门编码
             DR.GENERATING_ORG_NAME, --到达部门名称
             '', --运单号
             DR.DEPOSIT_RECEIVED_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             DR.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --单据类型
             DR.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_AF --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
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
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_COD, --代收货款应收
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC) --空运代理代收货款应收
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA --空运代理
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --09) 预收空运代理冲应收代收货款未签收
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
             PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_NPOD, --业务场景
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --始发部门编码
             DR.GENERATING_ORG_NAME, --始发部门名称
             DR.GENERATING_ORG_CODE, --到达部门编码
             DR.GENERATING_ORG_NAME, --到达部门名称
             '', --运单号
             DR.DEPOSIT_RECEIVED_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             DR.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --单据类型
             DR.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_AF --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
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
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_COD, --代收货款应收
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC) --空运代理代收货款应收
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA --空运代理
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --10) 空运退预收付款申请
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
             PKG_STL_VCH_COMMON.AIR_DR_PAY_APPLY, --业务场景
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --始发部门编码
             DR.GENERATING_ORG_NAME, --始发部门名称
             DR.GENERATING_ORG_CODE, --到达部门编码
             DR.GENERATING_ORG_NAME, --到达部门名称
             '', --运单号
             DR.DEPOSIT_RECEIVED_NO, --单据编号
             PT.ACCOUNT_DATE, --会计日期
             DR.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --单据类型
             DR.BILL_TYPE, --单据子类型
             DP.PAY_AMOUNT *
             DECODE(PT.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1), --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_AF --产品类型
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DR
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON DP.SOURCE_BILL_NO = DR.DEPOSIT_RECEIVED_NO
        JOIN STL.T_STL_BILL_PAYMENT PT
          ON PT.PAYMENT_NO = DP.PAYMENT_NO
       WHERE DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA --空运代理
         AND DR.IS_RED_BACK = PKG_STL_COMMON.NO --非红单
         AND PT.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PT.ACCOUNT_DATE < P_END_DATE --预收02
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --11) 往来 预收空运代理冲01应收到付运费已签收
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
             PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVO_POD,
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
             DR.GENERATING_ORG_CODE, --预收部门编码
             DR.GENERATING_ORG_NAME, --预收部门名称
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
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
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
            --PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL)--落地配到达代理应收 
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA
            --PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LS)--落地配代理
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --12) 往来 预收空运代理冲02应收到付运费已签收
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
             PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVT_POD,
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
             DR.GENERATING_ORG_CODE, --预收部门编码
             DR.GENERATING_ORG_NAME, --预收部门名称
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
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
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
            --PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL)--落地配到达代理应收 
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA
            --PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LS)--落地配代理
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --13) 往来 预收空运代理冲01应收到付运费未签收
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
             PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVO_NPOD,
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
             DR.GENERATING_ORG_CODE, --预收部门编码
             DR.GENERATING_ORG_NAME, --预收部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --到达部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
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
            --PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL)--落地配到达代理应收 
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA
            --PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LS)--落地配代理
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收01
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --14) 往来 预收空运代理冲02应收到付运费未签收
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
             PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVT_NPOD,
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
             DR.GENERATING_ORG_CODE, --预收部门编码
             DR.GENERATING_ORG_NAME, --预收部门名称
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
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
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
            --PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL)--落地配到达代理应收 
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA
            --PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LS)--落地配代理
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收02
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --15) 往来 预收空运代理冲应收代收货款已签收
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
             PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_POD, --业务场景
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
             PKG_STL_COMMON.PRODUCT_CODE_AF, --产品类型
             DR.GENERATING_ORG_CODE, --预收部门编码
             DR.GENERATING_ORG_NAME, --预收部门名称
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
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
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
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_COD, --代收货款应收
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC) --空运代理代收货款应收
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA --空运代理
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
                                        '预收空运代理处理日明细' || '异常');
    
      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_AIR_DR;
/
