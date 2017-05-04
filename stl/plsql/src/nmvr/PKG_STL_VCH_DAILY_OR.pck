CREATE OR REPLACE PACKAGE STV.PKG_STL_VCH_DAILY_OR IS

  -- ==============================================================
  -- Author  : ZBW
  -- Created : 2013-11-06
  -- Purpose : 每日凭证小票
  -- ==============================================================

  -----------------------------------------------------------------
  -- 小票处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_DAILY_OR;
/
CREATE OR REPLACE PACKAGE BODY STV.PKG_STL_VCH_DAILY_OR IS

  -----------------------------------------------------------------
  -- 小票处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期YYYYMMDD,例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN -- 返回结果 TRUE:成功,FALSE:失败
   IS
  BEGIN
  
    --01) 小票现金之事故赔款 
    --02) 小票现金之变卖废品收入
    --03) 小票现金之客户多付运费或盘点长款金额 
    --04) 小票现金之其他
    --05) 01小票现金主营业务收入
    --06) 02小票现金主营业务收入
    --07) 小票银行之事故赔款 
    --08) 小票银行之收银员卡利息
    --09) 小票银行之客户多付运费或盘点长款金额 
    --10) 小票银行之其他
    --11) 01小票银行主营业务收入
    --12) 02小票银行主营业务收入
    --13) 01小票应收主营业务收入
    --14) 02小票应收主营业务收入
    --15) 还款现金冲01小票应收
    --16) 还款银行冲01小票应收
    --17) 还款现金冲02小票应收
    --18) 还款银行冲02小票应收
    --19) 应付代收货款冲01小票应收
    --20) 应付代收货款冲02小票应收
    --21) 01应付理赔冲01小票应收
    --22) 01应付理赔冲02小票应收
    --23) 02应付理赔冲02小票应收
    --24) 02应付理赔冲01小票应收
    --25) 01预收客户冲01小票应收
    --26) 01预收客户冲02小票应收
    --27) 02预收客户冲01小票应收
    --28) 02预收客户冲02小票应收
    --29) 异常冲收入冲01小票应收
    --30) 坏账损失冲01小票应收
    --31) 异常冲收入冲02小票应收
    --32) 坏账损失冲02小票应收
    --33) 往来 01小票现金主营业务收入
    --34) 往来 01小票银行主营业务收入
    --35) 往来 还款现金冲01小票应收
    --36) 往来 还款银行冲01小票应收
    --37) 往来 01应付理赔冲02小票应收
    --38) 往来 02应付理赔冲01小票应收
    --39) 往来 01预收客户冲02小票应收
    --40) 往来 02预收客户冲01小票应收
    --41) P 01应付理赔冲02小票应收
    --42) P 02应付理赔冲01小票应收
    --43) D 01预收客户冲02小票应收
    --44) D 02预收客户冲01小票应收
    --45) 小票现金之富余仓库出租收入
    --46) 小票现金之收银员卡利息
    --47) 小票银行之富余仓库出租收入
  
    --01) 小票现金之事故赔款
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
             PKG_STL_VCH_COMMON.OR_CH_AC,
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE,
                    CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME,
                    CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE,
                    CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME,
                    CASH.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --运单号
             CASH.CASH_COLLECTION_NO, --单据编号
             CASH.ACCOUNT_DATE, --会计日期
             CASH.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --单据类型
             CASH.BILL_TYPE, --单据子类型
             CASH.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---支付方式为现金
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
         AND ORE.INCOME_CATEGORIES = 'A' --事故私了赔款 
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE --现金02
         AND CASH.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --02) 小票现金之变卖废品收入
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
             PKG_STL_VCH_COMMON.OR_CH_SI, --业务场景
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE,
                    CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME,
                    CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE,
                    CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME,
                    CASH.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --运单号
             CASH.CASH_COLLECTION_NO, --单据编号
             CASH.ACCOUNT_DATE, --会计日期
             CASH.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --单据类型
             CASH.BILL_TYPE, --单据子类型
             CASH.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---支付方式为现金
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
         AND ORE.INCOME_CATEGORIES = 'R' --变卖废品收入 
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE --现金02
         AND CASH.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --03) 小票现金之客户多付运费或盘点长款金额 
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
             PKG_STL_VCH_COMMON.OR_CH_OPAY, --业务场景
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE,
                    CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME,
                    CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE,
                    CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME,
                    CASH.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --运单号
             CASH.CASH_COLLECTION_NO, --单据编号
             CASH.ACCOUNT_DATE, --会计日期
             CASH.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --单据类型
             CASH.BILL_TYPE, --单据子类型
             CASH.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---支付方式为现金
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
         AND ORE.INCOME_CATEGORIES IN ('B', 'G') --客户多付运费（不要款项）、盘点长款金额  
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE --现金02
         AND CASH.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --04) 小票现金之其他
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
             PKG_STL_VCH_COMMON.OR_CH_OTHER, --业务场景
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE,
                    CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME,
                    CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE,
                    CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME,
                    CASH.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --运单号
             CASH.CASH_COLLECTION_NO, --单据编号
             CASH.ACCOUNT_DATE, --会计日期
             CASH.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --单据类型
             CASH.BILL_TYPE, --单据子类型
             CASH.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---支付方式为现金
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
         AND ORE.INCOME_CATEGORIES = 'O' --其他非主营收入类型  
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE --现金02
         AND CASH.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --05) 01小票现金主营业务收入
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
             PKG_STL_VCH_COMMON.OR_CH_PBIO,
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE,
                    CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME,
                    CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE,
                    CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME,
                    CASH.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --运单号
             CASH.CASH_COLLECTION_NO, --单据编号
             CASH.ACCOUNT_DATE, --会计日期
             CASH.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --单据类型
             CASH.BILL_TYPE, --单据子类型
             CASH.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---支付方式为现金
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
         AND ORE.INCOME_CATEGORIES IN
             (SELECT T.VALUE_CODE
                FROM BSE.T_BAS_DATA_DICTIONARY_VALUE T
               WHERE T.TERMS_CODE = 'BILL_RECEIVABLE__COLLECTION_TYPE'
                 AND T.EXTATTRIBUTE1 = 'PRIMARY') --主营收入类型  
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE --现金01
         AND CASH.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --06) 02小票现金主营业务收入
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
             PKG_STL_VCH_COMMON.OR_CH_PBIT,
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE,
                    CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME,
                    CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE,
                    CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME,
                    CASH.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --运单号
             CASH.CASH_COLLECTION_NO, --单据编号
             CASH.ACCOUNT_DATE, --会计日期
             CASH.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --单据类型
             CASH.BILL_TYPE, --单据子类型
             CASH.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---支付方式为现金
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
         AND ORE.INCOME_CATEGORIES IN
             (SELECT T.VALUE_CODE
                FROM BSE.T_BAS_DATA_DICTIONARY_VALUE T
               WHERE T.TERMS_CODE = 'BILL_RECEIVABLE__COLLECTION_TYPE'
                 AND T.EXTATTRIBUTE1 = 'PRIMARY') --主营收入类型  
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE --现金02
         AND CASH.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --07) 小票银行之事故赔款 
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
             PKG_STL_VCH_COMMON.OR_CD_AC,
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE,
                    CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME,
                    CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE,
                    CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME,
                    CASH.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --运单号
             CASH.CASH_COLLECTION_NO, --单据编号
             CASH.ACCOUNT_DATE, --会计日期
             CASH.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --单据类型
             CASH.BILL_TYPE, --单据子类型
             CASH.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
              PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
              PKG_STL_COMMON.PAYMENT_TYPE_ONLINE,
              PKG_STL_COMMON.PAYMENT_TYPE_NOTE) ---支付方式为银行卡、电汇、支票、网上支付
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
         AND ORE.INCOME_CATEGORIES = 'A' --事故私了赔款 
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE --现金02
         AND CASH.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --08) 小票银行之收银员卡利息
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
             PKG_STL_VCH_COMMON.OR_CD_BANK_INT,
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE,
                    CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME,
                    CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE,
                    CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME,
                    CASH.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --运单号
             CASH.CASH_COLLECTION_NO, --单据编号
             CASH.ACCOUNT_DATE, --会计日期
             CASH.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --单据类型
             CASH.BILL_TYPE, --单据子类型
             CASH.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
              PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
              PKG_STL_COMMON.PAYMENT_TYPE_ONLINE,
              PKG_STL_COMMON.PAYMENT_TYPE_NOTE) ---支付方式为银行卡、电汇、支票、网上支付
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
         AND ORE.INCOME_CATEGORIES = 'H' --收银员卡利息
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE --现金02
         AND CASH.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --09) 小票银行之客户多付运费或盘点长款金额 
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
             PKG_STL_VCH_COMMON.OR_CD_OPAY,
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE,
                    CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME,
                    CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE,
                    CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME,
                    CASH.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --运单号
             CASH.CASH_COLLECTION_NO, --单据编号
             CASH.ACCOUNT_DATE, --会计日期
             CASH.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --单据类型
             CASH.BILL_TYPE, --单据子类型
             CASH.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
              PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
              PKG_STL_COMMON.PAYMENT_TYPE_ONLINE,
              PKG_STL_COMMON.PAYMENT_TYPE_NOTE) ---支付方式为银行卡、电汇、支票、网上支付
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
         AND ORE.INCOME_CATEGORIES IN ('B', 'G') --客户多付运费（不要款项）、盘点长款金额  
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE --现金02
         AND CASH.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --10) 小票银行之其他
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
             PKG_STL_VCH_COMMON.OR_CD_OTHER,
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE,
                    CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME,
                    CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE,
                    CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME,
                    CASH.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --运单号
             CASH.CASH_COLLECTION_NO, --单据编号
             CASH.ACCOUNT_DATE, --会计日期
             CASH.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --单据类型
             CASH.BILL_TYPE, --单据子类型
             CASH.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
              PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
              PKG_STL_COMMON.PAYMENT_TYPE_ONLINE,
              PKG_STL_COMMON.PAYMENT_TYPE_NOTE) ---支付方式为银行卡、电汇、支票、网上支付
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
         AND ORE.INCOME_CATEGORIES = 'O' --其他非主营收入类型  
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE --现金02
         AND CASH.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --11) 01小票银行主营业务收入
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
             PKG_STL_VCH_COMMON.OR_CD_PBIO,
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE,
                    CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME,
                    CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE,
                    CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME,
                    CASH.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --运单号
             CASH.CASH_COLLECTION_NO, --单据编号
             CASH.ACCOUNT_DATE, --会计日期
             CASH.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --单据类型
             CASH.BILL_TYPE, --单据子类型
             CASH.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
              PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
              PKG_STL_COMMON.PAYMENT_TYPE_ONLINE,
              PKG_STL_COMMON.PAYMENT_TYPE_NOTE) ---支付方式为银行卡、电汇、支票、网上支付
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
         AND ORE.INCOME_CATEGORIES IN
             (SELECT T.VALUE_CODE
                FROM BSE.T_BAS_DATA_DICTIONARY_VALUE T
               WHERE T.TERMS_CODE = 'BILL_RECEIVABLE__COLLECTION_TYPE'
                 AND T.EXTATTRIBUTE1 = 'PRIMARY') --主营收入类型  
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE --现金01
         AND CASH.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --12) 02小票银行主营业务收入
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
             PKG_STL_VCH_COMMON.OR_CD_PBIT,
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE,
                    CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME,
                    CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE,
                    CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME,
                    CASH.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --运单号
             CASH.CASH_COLLECTION_NO, --单据编号
             CASH.ACCOUNT_DATE, --会计日期
             CASH.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --单据类型
             CASH.BILL_TYPE, --单据子类型
             CASH.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
              PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
              PKG_STL_COMMON.PAYMENT_TYPE_ONLINE,
              PKG_STL_COMMON.PAYMENT_TYPE_NOTE) ---支付方式为银行卡、电汇、支票、网上支付
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
         AND ORE.INCOME_CATEGORIES IN
             (SELECT T.VALUE_CODE
                FROM BSE.T_BAS_DATA_DICTIONARY_VALUE T
               WHERE T.TERMS_CODE = 'BILL_RECEIVABLE__COLLECTION_TYPE'
                 AND T.EXTATTRIBUTE1 = 'PRIMARY') --主营收入类型  
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE --现金02
         AND CASH.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --13) 01小票应收主营业务收入
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
             PKG_STL_VCH_COMMON.OR_RCVO_PBI,
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
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
             RE.RECEIVABLE_NO, --单据编号
             RE.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             RE.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RE.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_RECEIVABLE RE
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON RE.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R
         AND ORE.INCOME_CATEGORIES IN
             (SELECT T.VALUE_CODE
                FROM BSE.T_BAS_DATA_DICTIONARY_VALUE T
               WHERE T.TERMS_CODE = 'BILL_RECEIVABLE__COLLECTION_TYPE'
                 AND T.EXTATTRIBUTE1 = 'PRIMARY') --主营收入类型  
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RE.ACCOUNT_DATE >= P_BEGIN_DATE
         AND RE.ACCOUNT_DATE < P_END_DATE --应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --14) 02小票应收主营业务收入
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
             PKG_STL_VCH_COMMON.OR_RCVT_PBI,
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
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
             RE.RECEIVABLE_NO, --单据编号
             RE.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             RE.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RE.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_RECEIVABLE RE
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON RE.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R
         AND ORE.INCOME_CATEGORIES IN
             (SELECT T.VALUE_CODE
                FROM BSE.T_BAS_DATA_DICTIONARY_VALUE T
               WHERE T.TERMS_CODE = 'BILL_RECEIVABLE__COLLECTION_TYPE'
                 AND T.EXTATTRIBUTE1 = 'PRIMARY') --主营收入类型  
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RE.ACCOUNT_DATE >= P_BEGIN_DATE
         AND RE.ACCOUNT_DATE < P_END_DATE --应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --15) 还款现金冲01小票应收
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
             PKG_STL_VCH_COMMON.OR_CH_UR_RCVO, --业务场景
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
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
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
             RE.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
         AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收
         AND RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
         AND RPMT.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---支付方式为现金
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --16) 还款银行冲01小票应收
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
             PKG_STL_VCH_COMMON.OR_CD_UR_RCVO,
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
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
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
             RE.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
         AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收
         AND RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
         AND RPMT.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
              PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
              PKG_STL_COMMON.PAYMENT_TYPE_ONLINE,
              PKG_STL_COMMON.PAYMENT_TYPE_NOTE) ---支付方式为银行卡、电汇、支票、网上支付
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --17) 还款现金冲02小票应收
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
             PKG_STL_VCH_COMMON.OR_CH_UR_RCVT, --业务场景
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
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
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
             RE.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
         AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收
         AND RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
         AND RPMT.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---支付方式为现金
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --18) 还款银行冲02小票应收
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
             PKG_STL_VCH_COMMON.OR_CD_UR_RCVT,
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
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
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
             RE.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
         AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收
         AND RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
         AND RPMT.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
              PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
              PKG_STL_COMMON.PAYMENT_TYPE_ONLINE,
              PKG_STL_COMMON.PAYMENT_TYPE_NOTE) ---支付方式为银行卡、电汇、支票、网上支付
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --19) 应付代收货款冲01小票应收
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
             PKG_STL_VCH_COMMON.OR_COD_PAY_WO_RCVO,
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
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
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
             RE.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.BEGIN_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP -- 应收冲应付
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE ---应收单为小票
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC ----- 应付代收货款
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --20) 应付代收货款冲02小票应收
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
             PKG_STL_VCH_COMMON.OR_COD_PAY_WO_RCVT,
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
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
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
             RE.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.BEGIN_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP -- 应收冲应付
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE ---应收单为小票
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC ----- 应付代收货款
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --21) 01应付理赔冲01小票应收
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
             PKG_STL_VCH_COMMON.OR_CLAIM_PAYO_WO_RCVO,
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
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
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
             RE.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.BEGIN_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP -- 应收冲应付
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE ---应收单为小票
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM ----- 应付理赔
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PA.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --应付01 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --22) 01应付理赔冲02小票应收
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
             PKG_STL_VCH_COMMON.OR_CLAIM_PAYO_WO_RCVT,
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
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
             WO.WRITEOFF_BILL_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             WO.WRITEOFF_TIME, --业务日期
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
             RE.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.BEGIN_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP -- 应收冲应付
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE ---应收单为小票
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM ----- 应付理赔
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PA.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --应付01 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --23) 02应付理赔冲02小票应收
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
             PKG_STL_VCH_COMMON.OR_CLAIM_PAYT_WO_RCVT,
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
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
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
             RE.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.BEGIN_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP -- 应收冲应付
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE ---应收单为小票
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM ----- 应付理赔
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PA.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --应付02 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --24) 02应付理赔冲01小票应收
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
             PKG_STL_VCH_COMMON.OR_CLAIM_PAYT_WO_RCVO,
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
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
             WO.WRITEOFF_BILL_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             WO.WRITEOFF_TIME, --业务日期
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
             RE.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.BEGIN_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP -- 应收冲应付
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE ---应收单为小票
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM ----- 应付理赔
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PA.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --应付02 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --25) 01预收客户冲01小票应收
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
             PKG_STL_VCH_COMMON.OR_CUST_DRO_WO_RCVO,
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
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
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
             RE.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE --来源于小票
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --预收01 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --26) 01预收客户冲02小票应收
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
             PKG_STL_VCH_COMMON.OR_CUST_DRO_WO_RCVT,
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
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
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
             RE.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE --来源于小票
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --预收01 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --27) 02预收客户冲01小票应收
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
             PKG_STL_VCH_COMMON.OR_CUST_DRT_WO_RCVO,
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
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
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
             RE.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE --来源于小票
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --预收02 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --28) 02预收客户冲02小票应收
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
             PKG_STL_VCH_COMMON.OR_CUST_DRT_WO_RCVT,
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
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
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
             RE.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE --来源于小票
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --预收02 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --29) 异常冲收入冲01小票应收
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
             PKG_STL_VCH_COMMON.OR_EX_WO_RCVO,
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
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
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
             RE.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_BAD_ACCOUNT BA
          ON BA.BAD_DEBAT_BILL_NO = WO.BEGIN_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_BR -- 坏账冲应付
         AND BA.BILL_TYPE = PKG_STL_COMMON.BAD_ACCOUNT_BILL_TYPE_INCOME ----坏账为保险理赔
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE ----- 应收单为小票
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --30) 坏账损失冲01小票应收
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
             PKG_STL_VCH_COMMON.OR_BAD_WO_RCVO,
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
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
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
             RE.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_BAD_ACCOUNT BA
          ON BA.BAD_DEBAT_BILL_NO = WO.BEGIN_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_BR -- 坏账冲应付
         AND BA.BILL_TYPE = PKG_STL_COMMON.BAD_ACCOUNT_BILL_TYPE_BADDEDTS ----坏账为坏账损失
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE ----- 应收单为小票
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --31) 异常冲收入冲02小票应收
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
             PKG_STL_VCH_COMMON.OR_EX_WO_RCVT,
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
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
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
             RE.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_BAD_ACCOUNT BA
          ON BA.BAD_DEBAT_BILL_NO = WO.BEGIN_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_BR -- 坏账冲应付
         AND BA.BILL_TYPE = PKG_STL_COMMON.BAD_ACCOUNT_BILL_TYPE_INCOME ----坏账为保险理赔
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE ----- 应收单为小票
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --32) 坏账损失冲02小票应收
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
             PKG_STL_VCH_COMMON.OR_BAD_WO_RCVT,
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
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
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
             RE.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_BAD_ACCOUNT BA
          ON BA.BAD_DEBAT_BILL_NO = WO.BEGIN_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_BR -- 坏账冲应付
         AND BA.BILL_TYPE = PKG_STL_COMMON.BAD_ACCOUNT_BILL_TYPE_BADDEDTS ----坏账为坏账损失
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE ----- 应收单为小票
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --33) 往来 01小票现金主营业务收入
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
             PKG_STL_VCH_COMMON.OR_CH_PBIO,
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --运单号
             CASH.CASH_COLLECTION_NO, --单据编号
             CASH.ACCOUNT_DATE, --会计日期
             CASH.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --单据类型
             CASH.BILL_TYPE, --单据子类型
             CASH.AMOUNT, --金额
             CASH.PRODUCT_CODE, --产品类型
             ORE.CREATE_ORG_CODE, -- 小票部门编码
             ORE.CREATE_ORG_NAME, --小票部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             ORE.CREATE_ORG_CODE, -- 小票部门编码
             ORE.CREATE_ORG_NAME, --小票部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---支付方式为现金
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
         AND ORE.INCOME_CATEGORIES IN
             (SELECT T.VALUE_CODE
                FROM BSE.T_BAS_DATA_DICTIONARY_VALUE T
               WHERE T.TERMS_CODE = 'BILL_RECEIVABLE__COLLECTION_TYPE'
                 AND T.EXTATTRIBUTE1 = 'PRIMARY') --主营收入类型  
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE --现金01
         AND CASH.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --34) 往来 01小票银行主营业务收入
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
             PKG_STL_VCH_COMMON.OR_CD_PBIO,
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --运单号
             CASH.CASH_COLLECTION_NO, --单据编号
             CASH.ACCOUNT_DATE, --会计日期
             CASH.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --单据类型
             CASH.BILL_TYPE, --单据子类型
             CASH.AMOUNT, --金额
             CASH.PRODUCT_CODE, --产品类型
             ORE.CREATE_ORG_CODE, -- 小票部门编码
             ORE.CREATE_ORG_NAME, --小票部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             ORE.CREATE_ORG_CODE, -- 小票部门编码
             ORE.CREATE_ORG_NAME, --小票部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
              PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
              PKG_STL_COMMON.PAYMENT_TYPE_ONLINE,
              PKG_STL_COMMON.PAYMENT_TYPE_NOTE) ---支付方式为银行卡、电汇、支票、网上支付
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
         AND ORE.INCOME_CATEGORIES IN
             (SELECT T.VALUE_CODE
                FROM BSE.T_BAS_DATA_DICTIONARY_VALUE T
               WHERE T.TERMS_CODE = 'BILL_RECEIVABLE__COLLECTION_TYPE'
                 AND T.EXTATTRIBUTE1 = 'PRIMARY') --主营收入类型  
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE --现金01
         AND CASH.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --35) 往来 还款现金冲01小票应收
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
             PKG_STL_VCH_COMMON.OR_CH_UR_RCVO, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
             WO.WRITEOFF_BILL_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             RE.PRODUCT_CODE, --产品类型
             RE.RECEIVABLE_ORG_CODE, -- 小票部门编码
             RE.RECEIVABLE_ORG_NAME, --小票部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             RE.RECEIVABLE_ORG_CODE, -- 小票部门编码
             RE.RECEIVABLE_ORG_NAME, --小票部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
         AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收
         AND RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
         AND RPMT.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---支付方式为现金
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --36) 往来 还款银行冲01小票应收
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
             PKG_STL_VCH_COMMON.OR_CD_UR_RCVO, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
             WO.WRITEOFF_BILL_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             RE.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             RE.PRODUCT_CODE, --产品类型
             RE.RECEIVABLE_ORG_CODE, -- 小票部门编码
             RE.RECEIVABLE_ORG_NAME, --小票部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             RE.RECEIVABLE_ORG_CODE, -- 小票部门编码
             RE.RECEIVABLE_ORG_NAME, --小票部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
         AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收
         AND RE.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
         AND RPMT.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
              PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
              PKG_STL_COMMON.PAYMENT_TYPE_ONLINE,
              PKG_STL_COMMON.PAYMENT_TYPE_NOTE) ---支付方式为银行卡、电汇、支票、网上支付
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE --应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --37) 往来 01应付理赔冲02小票应收
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
             PKG_STL_VCH_COMMON.OR_CLAIM_PAYO_WO_RCVT,
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
             WO.WRITEOFF_BILL_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             WO.WRITEOFF_TIME, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             RE.PRODUCT_CODE, --产品类型
             PA.ORIG_ORG_CODE, --应付单始发部门编码 
             PA.ORIG_ORG_NAME, --应付单始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             RE.RECEIVABLE_ORG_CODE, --小票应收单收款部门编码
             RE.RECEIVABLE_ORG_NAME, --小票应收单收款部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.BEGIN_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP -- 应收冲应付
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE ---应收单为小票
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM ----- 应付理赔
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PA.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --应付01 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --38) 往来 02应付理赔冲01小票应收
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
             PKG_STL_VCH_COMMON.OR_CLAIM_PAYT_WO_RCVO,
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
             WO.WRITEOFF_BILL_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             WO.WRITEOFF_TIME, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             RE.PRODUCT_CODE, --产品类型
             PA.ORIG_ORG_CODE, --应付单始发部门编码 
             PA.ORIG_ORG_NAME, --应付单始发部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             RE.RECEIVABLE_ORG_CODE, --小票应收单收款部门编码
             RE.RECEIVABLE_ORG_NAME, --小票应收单收款部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.BEGIN_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP -- 应收冲应付
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE ---应收单为小票
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM ----- 应付理赔
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PA.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --应付02 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --39) 往来 01预收客户冲02小票应收
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
             PKG_STL_VCH_COMMON.OR_CUST_DRO_WO_RCVT,
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
             WO.WRITEOFF_BILL_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             WO.WRITEOFF_TIME, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             RE.PRODUCT_CODE, --产品类型
             DR.GENERATING_ORG_CODE, --预收单对应收入部门编码
             DR.GENERATING_ORG_NAME, --预收单对应收入部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             RE.RECEIVABLE_ORG_CODE, --小票应收单应收部门编码
             RE.RECEIVABLE_ORG_NAME, --小票应收单应收部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE --来源于小票
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --预收01 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --40) 往来 02预收客户冲01小票应收
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
             PKG_STL_VCH_COMMON.OR_CUST_DRT_WO_RCVO,
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
             WO.WRITEOFF_BILL_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             WO.WRITEOFF_TIME, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RE.BILL_TYPE, --单据子类型
             WO.AMOUNT, --金额
             RE.PRODUCT_CODE, --产品类型
             DR.GENERATING_ORG_CODE, --预收单对应收入部门编码
             DR.GENERATING_ORG_NAME, --预收单对应收入部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             RE.RECEIVABLE_ORG_CODE, --小票应收单应收部门编码
             RE.RECEIVABLE_ORG_NAME, --小票应收单应收部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE --来源于小票
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --预收02 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --41) P 01应付理赔冲02小票应收
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
             PKG_STL_VCH_COMMON.OR_CLAIM_PAYO_WO_RCVTP,
             PA.CUSTOMER_CODE, --客户编码
             PA.CUSTOMER_NAME, --客户名称
             PA.CUSTOMER_TYPE, --客户类型
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_ORIG_ORG_CODE,
                    PA.ORIG_ORG_CODE), --始发部门编码 
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_ORIG_ORG_NAME,
                    PA.ORIG_ORG_NAME), --始发部门名称
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_DEST_ORG_CODE,
                    PA.DEST_ORG_CODE), --到达部门编码
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_DEST_ORG_NAME,
                    PA.DEST_ORG_NAME), --到达部门名称
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
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.BEGIN_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP -- 应收冲应付
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE ---应收单为小票
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM ----- 应付理赔
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PA.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --应付01 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --42) P 02应付理赔冲01小票应收
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
             PKG_STL_VCH_COMMON.OR_CLAIM_PAYT_WO_RCVOP,
             PA.CUSTOMER_CODE, --客户编码
             PA.CUSTOMER_NAME, --客户名称
             PA.CUSTOMER_TYPE, --客户类型
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_ORIG_ORG_CODE,
                    PA.ORIG_ORG_CODE), --始发部门编码 
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_ORIG_ORG_NAME,
                    PA.ORIG_ORG_NAME), --始发部门名称
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_DEST_ORG_CODE,
                    PA.DEST_ORG_CODE), --到达部门编码
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_DEST_ORG_NAME,
                    PA.DEST_ORG_NAME), --到达部门名称
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
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.BEGIN_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PA.PAYABLE_NO = WO.END_NO
         AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP -- 应收冲应付
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE ---应收单为小票
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM ----- 应付理赔
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PA.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --应付02 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
    --43) D 01预收客户冲02小票应收
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
             PKG_STL_VCH_COMMON.OR_CUST_DRO_WO_RCVTD,
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --部门编码 
             DR.GENERATING_ORG_NAME, --部门名称
             DR.GENERATING_ORG_CODE, --部门编码 
             DR.GENERATING_ORG_NAME, --部门名称
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
             PKG_STL_COMMON.PRODUCT_CODE_FLF --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE --来源于小票
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE --预收01 应收02
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --44) D 02预收客户冲01小票应收
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
             PKG_STL_VCH_COMMON.OR_CUST_DRT_WO_RCVOD,
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.GENERATING_ORG_CODE, --部门编码 
             DR.GENERATING_ORG_NAME, --部门名称
             DR.GENERATING_ORG_CODE, --部门编码 
             DR.GENERATING_ORG_NAME, --部门名称
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
             PKG_STL_COMMON.PRODUCT_CODE_FLF --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE --来源于小票
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND DR.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO --预收02 应收01
         AND RE.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --45) 小票现金之富余仓库出租收入
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
             PKG_STL_VCH_COMMON.OR_CH_RENT_INCOME, --业务场景
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE,
                    CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME,
                    CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE,
                    CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME,
                    CASH.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --运单号
             CASH.CASH_COLLECTION_NO, --单据编号
             CASH.ACCOUNT_DATE, --会计日期
             CASH.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --单据类型
             CASH.BILL_TYPE, --单据子类型
             CASH.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---支付方式为现金
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
         AND ORE.INCOME_CATEGORIES = 'FC'
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE --现金02
         AND CASH.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --46) 小票现金之收银员卡利息
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
             PKG_STL_VCH_COMMON.OR_CH_BANK_INT, --业务场景
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE,
                    CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME,
                    CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE,
                    CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME,
                    CASH.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --运单号
             CASH.CASH_COLLECTION_NO, --单据编号
             CASH.ACCOUNT_DATE, --会计日期
             CASH.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --单据类型
             CASH.BILL_TYPE, --单据子类型
             CASH.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---支付方式为现金
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
         AND ORE.INCOME_CATEGORIES = 'H' --收银员卡利息
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE --现金02
         AND CASH.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
    --47) 小票银行之富余仓库出租收入
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
             PKG_STL_VCH_COMMON.OR_CD_RENT_INCOME,
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE,
                    CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME,
                    CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE,
                    CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME,
                    CASH.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
                    'N/A',
                    CASH.SOURCE_BILL_NO,
                    CASH.WAYBILL_NO), --运单号
             CASH.CASH_COLLECTION_NO, --单据编号
             CASH.ACCOUNT_DATE, --会计日期
             CASH.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --单据类型
             CASH.BILL_TYPE, --单据子类型
             CASH.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             CASH.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_CASH_COLLECTION CASH
        JOIN STL.T_STL_OTHER_REVENUE ORE
          ON CASH.SOURCE_BILL_NO = ORE.OTHER_REVENUE_NO
       WHERE CASH.PAYMENT_TYPE IN
             (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
              PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
              PKG_STL_COMMON.PAYMENT_TYPE_ONLINE,
              PKG_STL_COMMON.PAYMENT_TYPE_NOTE) ---支付方式为银行卡、电汇、支票、网上支付
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
         AND ORE.INCOME_CATEGORIES = 'FC'
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE --现金02
         AND CASH.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    RETURN TRUE;
  
  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        '小票处理日明细' || '异常');
    
      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_OR;
/
