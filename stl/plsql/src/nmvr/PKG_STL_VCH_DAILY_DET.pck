CREATE OR REPLACE PACKAGE STV.PKG_STL_VCH_DAILY_DET IS

  -- ==============================================================
  -- Author  : ZBW
  -- Created : 2013-11-06
  -- Purpose : 每日凭证开单运单【02】
  -- ==============================================================

  -----------------------------------------------------------------
  -- 开单运单【02】处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_DAILY_DET;
/
CREATE OR REPLACE PACKAGE BODY STV.PKG_STL_VCH_DAILY_DET IS

  -----------------------------------------------------------------
  -- 开单运单【02】处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS
  BEGIN
  
    --1)开单现金
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
             PKG_STL_VCH_COMMON.DET_CH, --业务场景
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
             NVL(CASH.WAYBILL_NO, CASH.SOURCE_BILL_NO), --运单号
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
       WHERE CASH.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---支付方式为现金
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE
         AND CASH.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --3)开单银行卡
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
             PKG_STL_VCH_COMMON.DET_CD, --业务场景
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
             NVL(CASH.WAYBILL_NO, CASH.SOURCE_BILL_NO), --运单号
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
       WHERE CASH.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CARD ---支付方式为银行卡
         AND CASH.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
         AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CASH.ACCOUNT_DATE < P_END_DATE
         AND CASH.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    RETURN TRUE;
  
  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        '开单运单【02】处理日明细' || '异常');
    
      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_DET;
/
