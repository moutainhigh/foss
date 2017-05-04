CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_CUST_DR IS

  -- ==============================================================
  -- Author  : ZBW
  -- Created : 2013-11-06
  -- Purpose : 每日凭证预收客户
  -- ==============================================================

  -----------------------------------------------------------------
  -- 预收客户处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_DAILY_CUST_DR;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_CUST_DR IS

  -----------------------------------------------------------------
  -- 预收客户处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS
  BEGIN
  
    --1)  预收客户01 预收现金
    --2)  预收客户01 预收银行卡
    --3)  预收客户02 预收现金
    --4)  预收客户02 预收银行卡
    --5)  往来 预收客户01 预收现金
    --6)  往来 预收客户01 预收银行卡
    --7)  01预收客户冲01应收到付运费未签收
    --8)  02预收客户冲01应收到付运费未签收
    --9)  01预收客户冲02应收到付运费未签收
    --10) 02预收客户冲02应收到付运费未签收
    --11) 01预收客户冲01应收到付运费已签收
    --12) 02预收客户冲01应收到付运费已签收
    --13) 01预收客户冲02应收到付运费已签收
    --14) 02预收客户冲02应收到付运费已签收
    --15) 01预收客户冲01应收始发运费未签收
    --16) 02预收客户冲01应收始发运费未签收
    --17) 01预收客户冲02应收始发运费未签收
    --18) 02预收客户冲02应收始发运费未签收
    --19) 01预收客户冲01应收始发运费已签收
    --20) 02预收客户冲01应收始发运费已签收
    --21) 01预收客户冲02应收始发运费已签收
    --22) 02预收客户冲02应收始发运费已签收
    --23) 01始发退预收付款申请
    --24) 02始发退预收付款申请
    --25) 往来 01预收客户冲01应收到付运费未签收
    --26) 往来 02预收客户冲01应收到付运费未签收
    --27) 往来 01预收客户冲02应收到付运费未签收
    --28) 往来 02预收客户冲02应收到付运费未签收
    --29) 往来 01预收客户冲02应收始发运费未签收
    --30) 往来 02预收客户冲01应收始发运费未签收
    --31) 往来 01预收客户冲02应收始发运费已签收
    --32) 往来 02预收客户冲01应收始发运费已签收
    --33) 往来 01预收客户冲01应收到付运费已签收
    --34) 往来 02预收客户冲01应收到付运费已签收
    --35) 往来 01预收客户冲02应收到付运费已签收
    --36) 往来 02预收客户冲02应收到付运费已签收
    --37) 往来 01始发退预收付款申请
    
    --38) 01预收客户冲01应收到付运费未签收
    --39) 02预收客户冲01应收到付运费未签收
    --40) 01预收客户冲01应收到付运费已签收
    --41) 02预收客户冲01应收到付运费已签收
    --42) 01预收客户冲02应收始发运费未签收
    --43) 02预收客户冲01应收始发运费未签收
    --44) 01预收客户冲02应收始发运费已签收
    --45) 02预收客户冲01应收始发运费已签收
    --46) 01预收客户冲02应收到付运费未签收
    --47) 02预收客户冲02应收到付运费未签收
    --48) 01预收客户冲02应收到付运费已签收
    --49) 02预收客户冲02应收到付运费已签收
  
    --1)预收客户01 预收现金
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
             PKG_STL_VCH_COMMON.CUST_DR_OCH, --业务场景
             DEP.CUSTOMER_CODE, --客户编码
             DEP.CUSTOMER_NAME, --客户名称
             DEP.CUSTOMER_TYPE, --客户类型
             DEP.GENERATING_ORG_CODE, --始发部门编码
             DEP.GENERATING_ORG_NAME, --始发部门名称
             DEP.GENERATING_ORG_CODE, --到达部门编码
             DEP.GENERATING_ORG_NAME, --到达部门名称
             '', --运单号
             DEP.DEPOSIT_RECEIVED_NO, --单据编号
             DEP.ACCOUNT_DATE, --会计日期
             DEP.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --单据类型
             DEP.BILL_TYPE, --单据子类型
             DEP.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_FLF
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DEP
       WHERE DEP.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---支付方式为现金
         AND DEP.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND DEP.ACCOUNT_DATE >= P_BEGIN_DATE
         AND DEP.ACCOUNT_DATE < P_END_DATE
         AND DEP.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --2)预收客户01 预收银行卡
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
             PKG_STL_VCH_COMMON.CUST_DR_OCD, --业务场景
             DEP.CUSTOMER_CODE, --客户编码
             DEP.CUSTOMER_NAME, --客户名称
             DEP.CUSTOMER_TYPE, --客户类型
             DEP.GENERATING_ORG_CODE, --始发部门编码
             DEP.GENERATING_ORG_NAME, --始发部门名称
             DEP.GENERATING_ORG_CODE, --到达部门编码
             DEP.GENERATING_ORG_NAME, --到达部门名称
             '', --运单号
             DEP.DEPOSIT_RECEIVED_NO, --单据编号
             DEP.ACCOUNT_DATE, --会计日期
             DEP.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --单据类型
             DEP.BILL_TYPE, --单据子类型
             DEP.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_FLF
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DEP
       WHERE DEP.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
              PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
              PKG_STL_COMMON.PAYMENT_TYPE_NOTE,
              PKG_STL_COMMON.PAYMENT_TYPE_ONLINE) ---支付方式为银行卡、电汇、支票、网上支付
         AND DEP.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND DEP.ACCOUNT_DATE >= P_BEGIN_DATE
         AND DEP.ACCOUNT_DATE < P_END_DATE
         AND DEP.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --3)预收客户02 预收现金
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
             PKG_STL_VCH_COMMON.CUST_DR_TCH, --业务场景
             DEP.CUSTOMER_CODE, --客户编码
             DEP.CUSTOMER_NAME, --客户名称
             DEP.CUSTOMER_TYPE, --客户类型
             DEP.GENERATING_ORG_CODE, --始发部门编码
             DEP.GENERATING_ORG_NAME, --始发部门名称
             DEP.GENERATING_ORG_CODE, --到达部门编码
             DEP.GENERATING_ORG_NAME, --到达部门名称
             '', --运单号
             DEP.DEPOSIT_RECEIVED_NO, --单据编号
             DEP.ACCOUNT_DATE, --会计日期
             DEP.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --单据类型
             DEP.BILL_TYPE, --单据子类型
             DEP.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_FLF
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DEP
       WHERE DEP.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---支付方式为现金
         AND DEP.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND DEP.ACCOUNT_DATE >= P_BEGIN_DATE
         AND DEP.ACCOUNT_DATE < P_END_DATE
         AND DEP.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --4)预收客户02 预收银行卡
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
             PKG_STL_VCH_COMMON.CUST_DR_TCD, --业务场景
             DEP.CUSTOMER_CODE, --客户编码
             DEP.CUSTOMER_NAME, --客户名称
             DEP.CUSTOMER_TYPE, --客户类型
             DEP.GENERATING_ORG_CODE, --始发部门编码
             DEP.GENERATING_ORG_NAME, --始发部门名称
             DEP.GENERATING_ORG_CODE, --到达部门编码
             DEP.GENERATING_ORG_NAME, --到达部门名称
             '', --运单号
             DEP.DEPOSIT_RECEIVED_NO, --单据编号
             DEP.ACCOUNT_DATE, --会计日期
             DEP.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --单据类型
             DEP.BILL_TYPE, --单据子类型
             DEP.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_FLF
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DEP
       WHERE DEP.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
              PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
              PKG_STL_COMMON.PAYMENT_TYPE_NOTE,
              PKG_STL_COMMON.PAYMENT_TYPE_ONLINE) ---支付方式为银行卡、电汇、支票、网上支付
         AND DEP.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND DEP.ACCOUNT_DATE >= P_BEGIN_DATE
         AND DEP.ACCOUNT_DATE < P_END_DATE
         AND DEP.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --5) 往来 预收客户01 预收现金
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
             PKG_STL_VCH_COMMON.CUST_DR_OCH,
             DEP.CUSTOMER_CODE,
             DEP.CUSTOMER_NAME,
             DEP.CUSTOMER_TYPE,
             '', --运单号
             DEP.DEPOSIT_RECEIVED_NO,
             DEP.ACCOUNT_DATE,
             DEP.BUSINESS_DATE,
             PKG_STL_COMMON.BILL_PARENT_TYPE__US,
             DEP.BILL_TYPE,
             DEP.AMOUNT,
             PKG_STL_COMMON.PRODUCT_CODE_FLF,
             DEP.GENERATING_ORG_CODE,
             DEP.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DEP.GENERATING_ORG_CODE,
             DEP.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DEP
       WHERE DEP.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---支付方式为现金
         AND DEP.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND DEP.ACCOUNT_DATE >= P_BEGIN_DATE
         AND DEP.ACCOUNT_DATE < P_END_DATE
         AND DEP.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --6) 往来 预收客户01 预收银行卡
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
             PKG_STL_VCH_COMMON.CUST_DR_OCD,
             DEP.CUSTOMER_CODE,
             DEP.CUSTOMER_NAME,
             DEP.CUSTOMER_TYPE,
             '', --运单号
             DEP.DEPOSIT_RECEIVED_NO,
             DEP.ACCOUNT_DATE,
             DEP.BUSINESS_DATE,
             PKG_STL_COMMON.BILL_PARENT_TYPE__US,
             DEP.BILL_TYPE,
             DEP.AMOUNT,
             PKG_STL_COMMON.PRODUCT_CODE_FLF,
             DEP.GENERATING_ORG_CODE,
             DEP.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DEP.GENERATING_ORG_CODE,
             DEP.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DEP
       WHERE DEP.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
              PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
              PKG_STL_COMMON.PAYMENT_TYPE_NOTE,
              PKG_STL_COMMON.PAYMENT_TYPE_ONLINE) ---支付方式为银行卡、电汇、支票、网上支付
         AND DEP.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND DEP.ACCOUNT_DATE >= P_BEGIN_DATE
         AND DEP.ACCOUNT_DATE < P_END_DATE --预收01
         AND DEP.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --7) 01预收客户冲01应收到付运费未签收
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_NPOD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --始发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --到达部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --到达部门名称
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE)
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --还款时未签收
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收、到达偏线代理应收单、空运到达代理应收*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --预收01 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --8) 02预收客户冲01应收到付运费未签收
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_NPOD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --到达部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --到达部门名称
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE)
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --还款时未签收
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收、到达偏线代理应收单、空运到达代理应收*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --预收02 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --9) 01预收客户冲02应收到付运费未签收
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_NPOD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --到达部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --到达部门名称
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE)
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --还款时未签收
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收、到达偏线代理应收单、空运到达代理应收*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --预收01 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --10) 02预收客户冲02应收到付运费未签收
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_NPOD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --到达部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --到达部门名称
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE)
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --还款时未签收
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收、到达偏线代理应收单、空运到达代理应收*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --预收02 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --11) 01预收客户冲01应收到付运费已签收
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_POD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --到达部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --到达部门名称
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE)
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时已签收
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收、到达偏线代理应收单、空运到达代理应收*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --预收01 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --12) 02预收客户冲01应收到付运费已签收
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_POD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --到达部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --到达部门名称
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE)
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时已签收
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收、  到达偏线代理应收单、空运到达代理应收*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --预收02 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --13) 01预收客户冲02应收到付运费已签收
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_POD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --到达部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --到达部门名称
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE)
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时已签收
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收、到达偏线代理应收单、空运到达代理应收*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --预收01 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --14) 02预收客户冲02应收到付运费已签收
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_POD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --到达部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --到达部门名称
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE)
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时已签收
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收 到达偏线代理应收单、空运到达代理应收*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --预收02 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --15) 01预收客户冲01应收始发运费未签收
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVO_NPOD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --到达部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --到达部门名称
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE)
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --还款时未签收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN ---应收单的单据子类型为始发应收
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --预收01 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --16) 02预收客户冲01应收始发运费未签收
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_NPOD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --到达部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --到达部门名称
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE)
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --还款时未签收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN ---应收单的单据子类型为始发应收
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --预收02 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --17) 01预收客户冲02应收始发运费未签收
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_NPOD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --到达部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --到达部门名称
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE)
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --还款时未签收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN ---应收单的单据子类型为始发应收
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --预收01 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --18) 02预收客户冲02应收始发运费未签收
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVT_NPOD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --到达部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --到达部门名称
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE)
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --还款时未签收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN ---应收单的单据子类型为始发应收
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --预收02 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --19) 01预收客户冲01应收始发运费已签收
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVO_POD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --到达部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --到达部门名称
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE)
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时已签收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN ---应收单的单据子类型为始发应收
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --预收01 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --20) 02预收客户冲01应收始发运费已签收
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_POD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --到达部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --到达部门名称
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE)
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时已签收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN ---应收单的单据子类型为始发应收
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --预收02 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --21) 01预收客户冲02应收始发运费已签收
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_POD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --到达部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --到达部门名称
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE)
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时已签收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN ---应收单的单据子类型为始发应收
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --预收01 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --22) 02预收客户冲02应收始发运费已签收
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVT_POD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE,
                    RE.DEST_ORG_CODE), --到达部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME,
                    RE.DEST_ORG_NAME), --到达部门名称
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE)
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时已签收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN ---应收单的单据子类型为始发应收
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --预收02 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --23) 01始发退预收付款申请
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
             PKG_STL_VCH_COMMON.CUST_DRO_PAY_APPLY, --业务场景
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --始发部门编码
             DR.GENERATING_ORG_NAME, --始发部门名称
             DR.GENERATING_ORG_CODE, --到达部门编码
             DR.GENERATING_ORG_NAME, --到达部门名称
             '', --运单号
             DR.DEPOSIT_RECEIVED_NO, --单据编号
             PAY.ACCOUNT_DATE, --会计日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --单据类型
             DR.BILL_TYPE, --单据子类型
             PAY_D.PAY_AMOUNT *
             DECODE(PAY.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1), --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_FLF
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DR
        JOIN STL.T_STL_BILL_PAYMENT_D PAY_D
          ON PAY_D.SOURCE_BILL_NO = DR.DEPOSIT_RECEIVED_NO
        JOIN STL.T_STL_BILL_PAYMENT PAY
          ON PAY.PAYMENT_NO = PAY_D.PAYMENT_NO
       WHERE DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --预收单为非红单
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE --01预收
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --24) 02始发退预收付款申请
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
             PKG_STL_VCH_COMMON.CUST_DRT_PAY_APPLY, --业务场景
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --始发部门编码
             DR.GENERATING_ORG_NAME, --始发部门名称
             DR.GENERATING_ORG_CODE, --到达部门编码
             DR.GENERATING_ORG_NAME, --到达部门名称
             '', --运单号
             DR.DEPOSIT_RECEIVED_NO, --单据编号
             PAY.ACCOUNT_DATE, --会计日期
             DR.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --单据类型
             DR.BILL_TYPE, --单据子类型
             PAY_D.PAY_AMOUNT *
             DECODE(PAY.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1), --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_FLF
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DR
        JOIN STL.T_STL_BILL_PAYMENT_D PAY_D
          ON PAY_D.SOURCE_BILL_NO = DR.DEPOSIT_RECEIVED_NO
        JOIN STL.T_STL_BILL_PAYMENT PAY
          ON PAY.PAYMENT_NO = PAY_D.PAYMENT_NO
       WHERE DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --预收单为非红单
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE --02预收
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --25) 往来 01预收客户冲01应收到付运费未签收
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_NPOD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE), --产品类型
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --还款时未签收
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收、到达偏线代理应收单、空运到达代理应收*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --预收01 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --26) 往来 02预收客户冲01应收到付运费未签收
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_NPOD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE), --产品类型
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --还款时未签收
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收、到达偏线代理应收单、空运到达代理应收*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --预收02 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --27) 往来 01预收客户冲02应收到付运费未签收
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_NPOD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE), --产品类型
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --还款时未签收
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收、到达偏线代理应收单、空运到达代理应收*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --预收01 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --28) 往来 02预收客户冲02应收到付运费未签收
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_NPOD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE), --产品类型
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --还款时未签收
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收、到达偏线代理应收单、空运到达代理应收*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --预收02 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --29) 往来 01预收客户冲02应收始发运费未签收
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_NPOD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE), --产品类型
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --还款时未签收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN ---应收单的单据子类型为始发应收
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --预收01 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --30) 往来 02预收客户冲01应收始发运费未签收
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_NPOD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE), --产品类型
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --还款时未签收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN ---应收单的单据子类型为始发应收
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --预收02 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --31) 往来 01预收客户冲02应收始发运费已签收
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_POD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE), --产品类型
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时已签收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN ---应收单的单据子类型为始发应收
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --预收01 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --32) 往来 02预收客户冲01应收始发运费已签收
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_POD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE), --产品类型
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时已签收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN ---应收单的单据子类型为始发应收
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --预收02 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --33) 往来 01预收客户冲01应收到付运费已签收
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_POD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE), --产品类型
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时已签收
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收、到达偏线代理应收单、空运到达代理应收*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --预收01 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --34) 往来 02预收客户冲01应收到付运费已签收
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_POD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE), --产品类型
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时已签收
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收、到达偏线代理应收单、空运到达代理应收*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --预收02 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --35) 往来 01预收客户冲02应收到付运费已签收
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_POD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE), --产品类型
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时已签收
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收、到达偏线代理应收单、空运到达代理应收*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --预收01 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --36) 往来 02预收客户冲02应收到付运费已签收
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_POD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             RE.WAYBILL_NO, --运单号
             RE.RECEIVABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_FLF,
                    RE.PRODUCT_CODE), --产品类型
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE,
                    RE.ORIG_ORG_CODE), --出发部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME,
                    RE.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时已签收
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收、到达偏线代理应收单、空运到达代理应收*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --预收02 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --37) 往来 01始发退预收付款申请
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
             PKG_STL_VCH_COMMON.CUST_DRO_PAY_APPLY, --业务场景
             DR.CUSTOMER_CODE,
             DR.CUSTOMER_NAME,
             DR.CUSTOMER_TYPE,
             '', --运单号
             DR.DEPOSIT_RECEIVED_NO,
             DR.ACCOUNT_DATE,
             DR.BUSINESS_DATE,
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --单据类型
             DR.BILL_TYPE, --单据子类型
             PAY_D.PAY_AMOUNT *
             DECODE(PAY.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1), --金额
             PKG_STL_COMMON.PRODUCT_CODE_FLF,
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DR.GENERATING_ORG_CODE,
             DR.GENERATING_ORG_NAME,
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DR
        JOIN STL.T_STL_BILL_PAYMENT_D PAY_D
          ON PAY_D.SOURCE_BILL_NO = DR.DEPOSIT_RECEIVED_NO
        JOIN STL.T_STL_BILL_PAYMENT PAY
          ON PAY.PAYMENT_NO = PAY_D.PAYMENT_NO
       WHERE DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --预收单为非红单
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE --01预收
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
    
    --38) 01预收客户冲01应收到付运费未签收
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_NPODD, --业务场景
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
             PKG_STL_COMMON.PRODUCT_CODE_FLF
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --还款时未签收
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收、到达偏线代理应收单、空运到达代理应收*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --预收01 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
    
    --39) 02预收客户冲01应收到付运费未签收
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_NPODD, --业务场景
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --出发部门编码
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
             PKG_STL_COMMON.PRODUCT_CODE_FLF
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --还款时未签收
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收、到达偏线代理应收单、空运到达代理应收*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --预收02 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
    
    --40) 01预收客户冲01应收到付运费已签收
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_PODD, --业务场景
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --出发部门编码
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
             PKG_STL_COMMON.PRODUCT_CODE_FLF
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时已签收
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收、到达偏线代理应收单、空运到达代理应收*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --预收01 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
    
    --41) 02预收客户冲01应收到付运费已签收
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_PODD, --业务场景
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --出发部门编码
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
             PKG_STL_COMMON.PRODUCT_CODE_FLF
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时已签收
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收、  到达偏线代理应收单、空运到达代理应收*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --预收02 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
    
    --42) 01预收客户冲02应收始发运费未签收
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_NPODD, --业务场景
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --出发部门编码
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
             PKG_STL_COMMON.PRODUCT_CODE_FLF
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --还款时未签收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN ---应收单的单据子类型为始发应收
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --预收01 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
    
    --43) 02预收客户冲01应收始发运费未签收
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_NPODD, --业务场景
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --出发部门编码
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
             PKG_STL_COMMON.PRODUCT_CODE_FLF
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --还款时未签收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN ---应收单的单据子类型为始发应收
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --预收02 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
   
    --44) 01预收客户冲02应收始发运费已签收
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_PODD, --业务场景
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --出发部门编码
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
             PKG_STL_COMMON.PRODUCT_CODE_FLF
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时已签收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN ---应收单的单据子类型为始发应收
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --预收01 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;     
    
    --45) 02预收客户冲01应收始发运费已签收
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_PODD, --业务场景
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --出发部门编码
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
             PKG_STL_COMMON.PRODUCT_CODE_FLF
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时已签收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN ---应收单的单据子类型为始发应收
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --预收02 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
    
    --46) 01预收客户冲02应收到付运费未签收
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_NPODD, --业务场景
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --出发部门编码
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
             PKG_STL_COMMON.PRODUCT_CODE_FLF
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --还款时未签收
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收、到达偏线代理应收单、空运到达代理应收*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --预收01 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
    
    --47) 02预收客户冲02应收到付运费未签收
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_NPODD, --业务场景
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --出发部门编码
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
             PKG_STL_COMMON.PRODUCT_CODE_FLF
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --还款时未签收
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收、到达偏线代理应收单、空运到达代理应收*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --预收02 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
    
    --48) 01预收客户冲02应收到付运费已签收
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
             PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_PODD, --业务场景
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --出发部门编码
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
             PKG_STL_COMMON.PRODUCT_CODE_FLF
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时已签收
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收、到达偏线代理应收单、空运到达代理应收*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --预收01 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
    
    --49) 02预收客户冲02应收到付运费已签收
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
             PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_PODD, --业务场景
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --出发部门编码
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
             PKG_STL_COMMON.PRODUCT_CODE_FLF
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RE.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时已签收
         AND RE.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收 到达偏线代理应收单、空运到达代理应收*/
         AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --预收02 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
        
    RETURN TRUE;
  
  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        '预收客户处理日明细' || '异常');
    
      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_CUST_DR;
/
