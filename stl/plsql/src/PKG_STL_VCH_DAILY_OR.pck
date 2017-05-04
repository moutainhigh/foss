CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_OR IS

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

END PKG_STL_VCH_DAILY_OR;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_OR IS

  -----------------------------------------------------------------
  -- 凭证小票处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS
  BEGIN
    --1)小票录入现金  小票现金之事故赔款  
    /*            按现金收款单的记账日期来统计，
    取现金收款单的金额，现金收款单的来源为小票，
    现金收款单的付款方式为现金，关联小票收款类别为：事故私了赔款 */
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
             PKG_STL_VCH_COMMON.OR_CH_AC, --业务场景
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE，CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME，CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE，CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME，CASH.DEST_ORG_NAME), --到达部门名称
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
         AND ORE.BUSINESS_DATE = cash.business_date
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE;
  
    --2)小票录入现金  小票现金之变卖废品收入  
    /*            按现金收款单的记账日期来统计，取现金收款单的金额，
    现金收款单的来源为小票，现金收款单的付款方式为现金，
    关联小票收款类别为：变卖废品收入 */
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
             PKG_STL_VCH_COMMON.OR_CH_SI, --业务场景
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE，CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME，CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE，CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME，CASH.DEST_ORG_NAME), --到达部门名称
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
         AND ORE.BUSINESS_DATE = cash.business_date
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE;
  
    --3)小票录入现金  小票现金之客户多付运费或盘点长款金额  
    /*            按现金收款单的记账日期来统计，
    取现金收款单的金额，现金收款单的来源为小票，
    现金收款单的付款方式为现金，关联小票收款类别为：
    客户多付运费（不要款项）、盘点长款金额  */
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
             PKG_STL_VCH_COMMON.OR_CH_OPAY, --业务场景
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE，CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME，CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE，CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME，CASH.DEST_ORG_NAME), --到达部门名称
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
         AND ORE.BUSINESS_DATE = cash.business_date
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE;
  
    --4)小票录入现金  小票现金之其他  
    /*            按现金收款单的记账日期来统计，
    取现金收款单的金额，现金收款单的来源为小票，
    现金收款单的付款方式为现金，
    关联小票收款类别为：数据字典中除客户多付运费（不要款项）、
    盘点长款金额 、收银员卡利息 、变卖废品收入、 事故私了赔款之外的非主营收入类型    */
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
             PKG_STL_VCH_COMMON.OR_CH_OTHER, --业务场景
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE，CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME，CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE，CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME，CASH.DEST_ORG_NAME), --到达部门名称
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
         AND ORE.BUSINESS_DATE = cash.business_date
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE;
  
    --5)小票录入现金  小票现金主营业务收入  
    /*           按现金收款单的记账日期来统计，
    取现金收款单的金额，现金收款单的来源为小票，
    现金收款单的付款方式为现金，关联小票收款类别为：
    数据字典中的主营收入类型    */
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
             PKG_STL_VCH_COMMON.OR_CH_MBI, --业务场景
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE，CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME，CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE，CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME，CASH.DEST_ORG_NAME), --到达部门名称
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
            --- and ORE.ACTIVE=PKG_STL_COMMON.YES
         AND ORE.BUSINESS_DATE = cash.business_date
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE;
  
    --1)小票录入银行  小票银行之事故赔款  
    /*            按现金收款单的记账日期来统计，
    取现金收款单的金额，现金收款单的来源为小票，
    现金收款单的付款方式为银行卡、电汇、支票、网上支付，关联小票收款类别为：事故私了赔款 */
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
             PKG_STL_VCH_COMMON.OR_CD_AC, --业务场景
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE，CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME，CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE，CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME，CASH.DEST_ORG_NAME), --到达部门名称
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
         AND ORE.BUSINESS_DATE = cash.business_date
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE;
  
    --7)小票录入银行  小票银行之收银员卡利息  
    /*           按现金收款单的记账日期来统计，取现金收款单的金额，现金收款单的来源为小票，
    现金收款单的付款方式为银行卡、电汇、支票、网上支付，关联小票收款类别为：收银员卡利息  */
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
             PKG_STL_VCH_COMMON.OR_CD_BANK_IT, --业务场景
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE，CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME，CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE，CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME，CASH.DEST_ORG_NAME), --到达部门名称
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
         AND ORE.BUSINESS_DATE = cash.business_date
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE;
  
    --8)小票录入银行  小票银行之客户多付运费或盘点长款金额  
    /*            按现金收款单的记账日期来统计，
    取现金收款单的金额，现金收款单的来源为小票，
    现金收款单的付款方式为现金，关联小票收款类别为：
    客户多付运费（不要款项）、盘点长款金额  */
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
             PKG_STL_VCH_COMMON.OR_CD_OPAY, --业务场景
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE，CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME，CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE，CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME，CASH.DEST_ORG_NAME), --到达部门名称
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
         AND ORE.BUSINESS_DATE = cash.business_date
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE;
  
    --9)小票录入银行  小票银行之其他  
    /*            按现金收款单的记账日期来统计，
    取现金收款单的金额，现金收款单的来源为小票，
    现金收款单的付款方式为现金，
    关联小票收款类别为：数据字典中除客户多付运费（不要款项）、
    盘点长款金额 、收银员卡利息 、变卖废品收入、 事故私了赔款之外的非主营收入类型    */
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
             PKG_STL_VCH_COMMON.OR_CD_OTHER, --业务场景
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE，CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME，CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE，CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME，CASH.DEST_ORG_NAME), --到达部门名称
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
         AND ORE.BUSINESS_DATE = cash.business_date
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE;
  
    --10)小票录入银行  小票银行主营业务收入  
    /*           按现金收款单的记账日期来统计，
    取现金收款单的金额，现金收款单的来源为小票，
    现金收款单的付款方式为现金，关联小票收款类别为：
    数据字典中的主营收入类型    */
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
             PKG_STL_VCH_COMMON.OR_CD_MBI, --业务场景
             CASH.CUSTOMER_CODE, --客户编码
             CASH.CUSTOMER_NAME, --客户名称
             CASH.CUSTOMER_TYPE, --客户类型
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_CODE，CASH.ORIG_ORG_CODE), --始发部门编码 
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_ORIG_ORG_NAME，CASH.ORIG_ORG_NAME), --始发部门名称
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_CODE，CASH.DEST_ORG_CODE), --到达部门编码
             DECODE(CASH.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    CASH.EXPRESS_DEST_ORG_NAME，CASH.DEST_ORG_NAME), --到达部门名称
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
         AND ORE.BUSINESS_DATE = cash.business_date
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE;
  
    --11)小票录入应收  小票应收主营业务收入  
    /*           按应收单的记账日期来统计，取应收单的金额，
    应收单的单据子类型为小票应收，关联小票收款类别为：数据字典中的主营收入类型   */
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
             PKG_STL_VCH_COMMON.OR_RCV_MBI, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE，RE.ORIG_ORG_CODE), --始发部门编码 
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME，RE.ORIG_ORG_NAME), --始发部门名称
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE，RE.DEST_ORG_CODE), --到达部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME，RE.DEST_ORG_NAME), --到达部门名称
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
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
            --AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND ORE.INCOME_CATEGORIES IN
             (SELECT T.VALUE_CODE
                FROM BSE.T_BAS_DATA_DICTIONARY_VALUE T
               WHERE T.TERMS_CODE = 'BILL_RECEIVABLE__COLLECTION_TYPE'
                 AND T.EXTATTRIBUTE1 = 'PRIMARY') --主营收入类型  
         AND ORE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND ORE.BUSINESS_DATE = re.business_date
         AND RE.ACCOUNT_DATE >= P_BEGIN_DATE
         AND RE.ACCOUNT_DATE < P_END_DATE;
  
    --12)小票应收核销  还款现金冲小票应收  
    /*           小票对应的现金方式的还款单金额，
    按还款单的记账日期来统计，
    还款单对应的应收单明细为小票应收类型，
    作废还款单生成的负数也统计   */
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
             PKG_STL_VCH_COMMON.OR_RCV_WO_UR_CH, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE，RE.ORIG_ORG_CODE), --始发部门编码 
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME，RE.ORIG_ORG_NAME), --始发部门名称
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE，RE.DEST_ORG_CODE), --到达部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME，RE.DEST_ORG_NAME), --到达部门名称
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
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --13)小票应收核销  还款银行冲小票应收 
    /*           小票对应的银行卡、电汇、支票、网上支付方式的还款单金额，
    按还款单的记账日期来统计，
    还款单对应的应收单明细为小票应收类型，
    作废还款单生成的负数也统计   */
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
             PKG_STL_VCH_COMMON.OR_RCV_WO_UR_CD, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE，RE.ORIG_ORG_CODE), --始发部门编码 
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME，RE.ORIG_ORG_NAME), --始发部门名称
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE，RE.DEST_ORG_CODE), --到达部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME，RE.DEST_ORG_NAME), --到达部门名称
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
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --14)小票应收核销  应付代收货款冲小票应收 
    /*           按核销单的记账日期来统计，
    应收单的单据子类型为小票应收，
    取核销明细的金额，
    应付单的单据子类型为：应付代收货款，
    反核销时生成的负数也统计   */
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
             PKG_STL_VCH_COMMON.OR_RCV_WO_COD_PAY, --业务场景
             PA.CUSTOMER_CODE, --客户编码
             PA.CUSTOMER_NAME, --客户名称
             PA.CUSTOMER_TYPE, --客户类型
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_ORIG_ORG_CODE，PA.ORIG_ORG_CODE), --始发部门编码 
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_ORIG_ORG_NAME，PA.ORIG_ORG_NAME), --始发部门名称
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_DEST_ORG_CODE，PA.DEST_ORG_CODE), --到达部门编码
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PA.EXPRESS_DEST_ORG_NAME，PA.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
                    'N/A',
                    PA.SOURCE_BILL_NO,
                    PA.WAYBILL_NO), --运单号
             WO.WRITEOFF_BILL_NO, --单据编号
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
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC ----- 应付代收货款
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --15)小票应收核销  应付理赔冲小票应收 
    /*           按核销单的记账日期来统计，
    取核销单的明细金额，关联明细中的应收单单据子类型为小票应收，
    应付单的单据子类型为：理赔应付，反核销时生成的负数也统计   */
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
             PKG_STL_VCH_COMMON.OR_RCV_WO_CLAIM_PAY, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE，RE.ORIG_ORG_CODE), --始发部门编码 
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME，RE.ORIG_ORG_NAME), --始发部门名称
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE，RE.DEST_ORG_CODE), --到达部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME，RE.DEST_ORG_NAME), --到达部门名称
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
            --  AND RE.SOURCE_BILL_TYPE =
            ---   PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_R --来源于小票
         AND PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM ----- 应付理赔
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --16)小票应收核销  预收客户冲小票应收 
    /*           按核销单的记账日期来统计，
    核销类型为预收冲应收，应收单的单据子类型为小票应收，
    取核销明细的金额，反核销时生成的负数也统计  */
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
             PKG_STL_VCH_COMMON.OR_RCV_WO_CUST_DR, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE，RE.ORIG_ORG_CODE), --始发部门编码 
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME，RE.ORIG_ORG_NAME), --始发部门名称
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE，RE.DEST_ORG_CODE), --到达部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME，RE.DEST_ORG_NAME), --到达部门名称
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
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE --来源于小票
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --17)小票应收核销  坏账之保险理赔冲小票应收 
    /*           按核销单的记账日期来统计，
    核销类型为坏账冲应收，且坏账单的冲账方式为：保险理赔，
    应收单的单据子类型为小票应收，取核销明细的金额，
    反核销时生成的负数也统计   */
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
             PKG_STL_VCH_COMMON.OR_RCV_WO_BD_DEBT, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE，RE.ORIG_ORG_CODE), --始发部门编码 
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME，RE.ORIG_ORG_NAME), --始发部门名称
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE，RE.DEST_ORG_CODE), --到达部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME，RE.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
             WO.WRITEOFF_BILL_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             WO.WRITEOFF_TIME, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             BA.BILL_TYPE, --单据子类型
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
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --18)小票应收核销  坏账之坏账损失冲小票应收 
    /*          按核销单的记账日期来统计，
    核销类型为坏账冲应收，且坏账单的冲账方式为：坏账损失，
    应收单的单据子类型为小票应收，取核销明细的金额，
    反核销时生成的负数也统计   */
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
             PKG_STL_VCH_COMMON.OR_RCV_WO_BD_INCOME, --业务场景
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_CODE，RE.ORIG_ORG_CODE), --始发部门编码 
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_ORIG_ORG_NAME，RE.ORIG_ORG_NAME), --始发部门名称
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_CODE，RE.DEST_ORG_CODE), --到达部门编码
             DECODE(RE.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RE.EXPRESS_DEST_ORG_NAME，RE.DEST_ORG_NAME), --到达部门名称
             DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RE.SOURCE_BILL_NO,
                    RE.WAYBILL_NO), --运单号
             WO.WRITEOFF_BILL_NO, --单据编号
             WO.ACCOUNT_DATE, --会计日期
             WO.WRITEOFF_TIME, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             BA.BILL_TYPE, --单据子类型
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
         AND WO.ACCOUNT_DATE < P_END_DATE;
    RETURN TRUE;
  
  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        '凭证小票处理日明细' || '异常');
    
      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_OR;
/
