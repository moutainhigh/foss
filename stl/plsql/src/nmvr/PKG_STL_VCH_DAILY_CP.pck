CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_CP IS

  -- ==============================================================
  -- Author  : ZBW
  -- Created : 2013-11-06
  -- Purpose : 每日凭证服务补救
  -- ==============================================================

  -----------------------------------------------------------------
  -- 服务补救处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_DAILY_CP;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_CP IS

  -----------------------------------------------------------------
  -- 服务补救处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd,例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN -- 返回结果 true:成功,false:失败
   IS
  BEGIN
    --1) 01始发服务补救付款申请
    --2) 02始发服务补救付款申请
    --3) 01到达服务补救付款申请
    --4) 02到达服务补救付款申请
    --5) 往来 01始发服务补救付款申请
    --6) 往来 01到达服务补救付款申请
  
    --1) 01始发服务补救付款申请
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
             PKG_STL_VCH_COMMON.CPO_ORIG_PAY_APPLY,
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --始发部门编码 
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --始发部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --到达部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PAY.SOURCE_BILL_NO,
                    PAY.WAYBILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PMT.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             DP.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.YES, -1, 1), --金额,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_PAYABLE PAY --应付单
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO --付款单明细
        LEFT JOIN STL.T_STL_BILL_PAYMENT PMT
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --付款单
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_COMPENSATION --服务补救
         AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --始发
         AND DP.PAY_AMOUNT > 0
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --非红单
         AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PMT.ACCOUNT_DATE < P_END_DATE --应付01
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --2) 02始发服务补救付款申请
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
             PKG_STL_VCH_COMMON.CPT_ORIG_PAY_APPLY,
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --始发部门编码 
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --始发部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --到达部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PAY.SOURCE_BILL_NO,
                    PAY.WAYBILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PMT.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             DP.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.YES, -1, 1), --金额,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_PAYABLE PAY --应付单
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO --付款单明细
        LEFT JOIN STL.T_STL_BILL_PAYMENT PMT
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --付款单
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_COMPENSATION --服务补救
         AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --始发
         AND DP.PAY_AMOUNT > 0
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --非红单
         AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PMT.ACCOUNT_DATE < P_END_DATE --应付02
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --3) 01到达服务补救付款申请
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
             PKG_STL_VCH_COMMON.CPO_DEST_PAY_APPLY,
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --始发部门编码 
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --始发部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --到达部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PAY.SOURCE_BILL_NO,
                    PAY.WAYBILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PMT.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             DP.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.YES, -1, 1), --金额,   
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_PAYABLE PAY --应付单
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO --付款单明细
        LEFT JOIN STL.T_STL_BILL_PAYMENT PMT
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --付款单
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_COMPENSATION --服务补救
         AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达
         AND DP.PAY_AMOUNT > 0
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO --非红单
         AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PMT.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE --应付01
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --4) 02到达服务补救付款申请
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
             PKG_STL_VCH_COMMON.CPT_DEST_PAY_APPLY,
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --始发部门编码 
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --始发部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --到达部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PAY.SOURCE_BILL_NO,
                    PAY.WAYBILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PMT.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             DP.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.YES, -1, 1), --金额,   
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_PAYABLE PAY --应付单
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO --付款单明细
        LEFT JOIN STL.T_STL_BILL_PAYMENT PMT
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --付款单
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_COMPENSATION --服务补救
         AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达
            -- AND PMT.PAYMENT_NO=PAY.PAYMENT_NO 2013-08-01 modified by zbw
         AND DP.PAY_AMOUNT > 0
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO --非红单
         AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PMT.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE --应付02
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --5) 往来 01始发服务补救付款申请
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
             PKG_STL_VCH_COMMON.CPO_ORIG_PAY_APPLY,
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PAY.SOURCE_BILL_NO,
                    PAY.WAYBILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PMT.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             DP.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.YES, -1, 1), --金额,
             PAY.PRODUCT_CODE, --产品类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --始发部门编码 
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --始发部门编码 
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_PAYABLE PAY --应付单
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO --付款单明细
        LEFT JOIN STL.T_STL_BILL_PAYMENT PMT
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --付款单
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_COMPENSATION --服务补救
         AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --始发
            -- AND PMT.PAYMENT_NO=PAY.PAYMENT_NO 2013-08-01 modified by zbw
         AND DP.PAY_AMOUNT > 0
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --非红单
         AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PMT.ACCOUNT_DATE < P_END_DATE --应付01
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --6) 往来 01到达服务补救付款申请
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
             PKG_STL_VCH_COMMON.CPO_DEST_PAY_APPLY,
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PAY.SOURCE_BILL_NO,
                    PAY.WAYBILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PMT.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             DP.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.YES, -1, 1), --金额
             PAY.PRODUCT_CODE, --产品类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --到达部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --到达部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --到达部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --到达部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST
        FROM STL.T_STL_BILL_PAYABLE PAY --应付单
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO --付款单明细
        LEFT JOIN STL.T_STL_BILL_PAYMENT PMT
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --付款单
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_COMPENSATION --服务补救
         AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达
            -- AND PMT.PAYMENT_NO=PAY.PAYMENT_NO 2013-08-01 modified by zbw
         AND DP.PAY_AMOUNT > 0
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO --非红单
         AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PMT.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE --应付01
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    RETURN TRUE;
  
  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        '服务补救处理日明细' || '异常');
    
      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_CP;
/
