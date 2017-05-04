CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_CLAIM IS

  -- ==============================================================
  -- Author  : ZBW
  -- Created : 2013-11-06
  -- Purpose : 每日凭证理赔
  -- ==============================================================

  -----------------------------------------------------------------
  -- 理赔处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_DAILY_CLAIM;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_CLAIM IS

  -----------------------------------------------------------------
  -- 理赔处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS
  BEGIN
  
    --1) 理赔-出发部门申请-01理赔冲收入
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
             PKG_STL_VCH_COMMON.CLAIM_ORIGO_INCOME, --业务场景
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PAY.ACCOUNT_DATE, --会计日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             CASE
               WHEN PAY.AMOUNT < 0 THEN
                LEAST(ABS(PAY.AMOUNT),
                      NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                      NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                      NVL(WAYBILL.COD_AMOUNT, 0)) * (-1)
               ELSE
                LEAST(ABS(PAY.AMOUNT),
                      NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                      NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                      NVL(WAYBILL.COD_AMOUNT, 0))
             END, --总运费、理赔金额两者取小
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_PAYABLE PAY --应付单
        JOIN PKP.T_SRV_WAYBILL WAYBILL
          ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
         AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----运单
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔类型应付单
         AND PAY.PAYABLE_ORG_CODE = WAYBILL.RECEIVE_ORG_CODE --始发
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --2) 理赔-出发部门申请-01理赔入成本
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
             PKG_STL_VCH_COMMON.CLAIM_ORIGO_COST, --业务场景
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PAY.ACCOUNT_DATE, --会计日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             CASE
               WHEN PAY.AMOUNT < 0 THEN
                (ABS(PAY.AMOUNT) - (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                NVL(WAYBILL.COD_AMOUNT, 0))) * (-1)
               ELSE
                ABS(PAY.AMOUNT) -
                (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                 NVL(WAYBILL.PRE_PAY_AMOUNT, 0) - NVL(WAYBILL.COD_AMOUNT, 0))
             END, --理赔金额减去总运费,如果结果小于0,值为0
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_PAYABLE PAY --应付单
        JOIN PKP.T_SRV_WAYBILL WAYBILL
          ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
         AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----运单
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔类型应付单
         AND PAY.PAYABLE_ORG_CODE = WAYBILL.RECEIVE_ORG_CODE --始发
         AND ABS(PAY.AMOUNT) -
             (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
             NVL(WAYBILL.PRE_PAY_AMOUNT, 0) - NVL(WAYBILL.COD_AMOUNT, 0)) > 0
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
  
    --3) 理赔-出发部门申请-02理赔冲收入
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
             PKG_STL_VCH_COMMON.CLAIM_ORIGT_INCOME, --业务场景
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PAY.ACCOUNT_DATE, --会计日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             CASE
               WHEN PAY.AMOUNT < 0 THEN
                LEAST(ABS(PAY.AMOUNT),
                      NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                      NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                      NVL(WAYBILL.COD_AMOUNT, 0)) * (-1)
               ELSE
                LEAST(ABS(PAY.AMOUNT),
                      NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                      NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                      NVL(WAYBILL.COD_AMOUNT, 0))
             END, --总运费、理赔金额两者取小
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_PAYABLE PAY --应付单
        JOIN PKP.T_SRV_WAYBILL WAYBILL
          ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
         AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----运单
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔类型应付单
         AND PAY.PAYABLE_ORG_CODE = WAYBILL.RECEIVE_ORG_CODE --始发
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --4) 理赔-出发部门申请-02理赔入成本
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
             PKG_STL_VCH_COMMON.CLAIM_ORIGT_COST, --业务场景
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PAY.ACCOUNT_DATE, --会计日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             CASE
               WHEN PAY.AMOUNT < 0 THEN
                (ABS(PAY.AMOUNT) - (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                NVL(WAYBILL.COD_AMOUNT, 0))) * (-1)
               ELSE
                ABS(PAY.AMOUNT) -
                (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                 NVL(WAYBILL.PRE_PAY_AMOUNT, 0) - NVL(WAYBILL.COD_AMOUNT, 0))
             END, --理赔金额减去总运费,如果结果小于0,值为0
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_PAYABLE PAY --应付单
        JOIN PKP.T_SRV_WAYBILL WAYBILL
          ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
         AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----运单
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔类型应付单
         AND PAY.PAYABLE_ORG_CODE = WAYBILL.RECEIVE_ORG_CODE --始发
         AND ABS(PAY.AMOUNT) -
             (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
             NVL(WAYBILL.PRE_PAY_AMOUNT, 0) - NVL(WAYBILL.COD_AMOUNT, 0)) > 0
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
  
    --5) 理赔-出发部门申请-01理赔冲01始发应收已签收
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
             PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVO_POD, --业务场景：按核销单的记账日期来统计,取核销单的明细金额,关联明细中的应收单单据子类型为始发应收
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
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RCV.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单号
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单号
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --始发
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --6) 理赔-出发部门申请-01理赔冲02始发应收已签收
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
             PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_POD, --业务场景：按核销单的记账日期来统计,取核销单的明细金额,关联明细中的应收单单据子类型为始发应收
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
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RCV.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单号
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单号
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --始发
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --6I) 理赔-出发部门申请-01理赔冲02始发应收已签收
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --期间
             PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_POD, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --借方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --借方部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --贷方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --贷方部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单号
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单号
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --始发
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --7) 理赔-出发部门申请-02理赔冲01始发应收已签收
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
             PKG_STL_VCH_COMMON.CLAIM_ORIGT_ORIG_RCVO_POD,
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
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RCV.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单号
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单号
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --始发
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --7I) 理赔-出发部门申请-02理赔冲01始发应收已签收
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --期间
             PKG_STL_VCH_COMMON.CLAIM_ORIGT_ORIG_RCVO_POD, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --借方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --借方部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --贷方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --贷方部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单号
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单号
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --始发
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --8) 理赔-出发部门申请-01理赔冲01始发应收未签收
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
             PKG_STL_VCH_COMMON.CLAIM_ORIGO_ORIG_RCVO_NPOD,
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.Payable_No, --单据编号
             WO.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单号
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单号
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --始发
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --9) 理赔-出发部门申请-01理赔冲02始发应收未签收
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
             PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_NPOD,
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
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RCV.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单号
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单号
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --始发
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --9I) 理赔-出发部门申请-01理赔冲02始发应收未签收
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --期间
             PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_NPOD,
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --借方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --借方部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --贷方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --贷方部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单号
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单号
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --始发
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --10) 理赔-出发部门申请-02理赔冲01始发应收未签收
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
             PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVO_NPOD,
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
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RCV.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单号
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单号
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --始发
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --10I) 理赔-出发部门申请-02理赔冲01始发应收未签收
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --期间
             PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVO_NPOD,
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --借方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --借方部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --贷方部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --贷方部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单号
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单号
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --始发
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --11) 理赔-出发部门申请-01理赔付款申请
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
             PKG_STL_VCH_COMMON.CLAIM_ORIGO_PAY_APPLY, --业务场景
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PAY.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             DP.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1),
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_PAYMENT PMT --付款单
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --付款单明细
        JOIN STL.T_STL_BILL_PAYABLE PAY --应付单
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --始发
         AND DP.PAY_AMOUNT > 0
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PMT.ACCOUNT_DATE < P_END_DATE;
  
    --11I) 理赔-出发部门申请-01理赔付款申请
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --期间
             PKG_STL_VCH_COMMON.CLAIM_ORIGO_PAY_APPLY, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --借方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --借方部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --贷方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --贷方部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PAY.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             DP.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1),
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_PAYMENT PMT --付款单
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --付款单明细
        JOIN STL.T_STL_BILL_PAYABLE PAY --应付单
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --始发
         AND DP.PAY_AMOUNT > 0
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PMT.ACCOUNT_DATE < P_END_DATE;
  
    --12) 理赔-出发部门申请-02理赔付款申请
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
             PKG_STL_VCH_COMMON.CLAIM_ORIGT_PAY_APPLY, --业务场景
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PAY.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             DP.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1),
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_PAYMENT PMT --付款单
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --付款单明细
        JOIN STL.T_STL_BILL_PAYABLE PAY --应付单
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --始发
         AND DP.PAY_AMOUNT > 0
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PMT.ACCOUNT_DATE < P_END_DATE;
  
    --13) 理赔-出发部门申请-02理赔冲02始发应收已签收
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
             PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVT_POD,
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
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RCV.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单号
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单号
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --始发
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --14) 理赔-出发部门申请-02理赔冲02始发应收未签收
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
             PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVT_NPOD,
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.Payable_No, --单据编号
             WO.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单号
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单号
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --始发
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --15) 理赔-到达部门申请-01理赔冲收入
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
             PKG_STL_VCH_COMMON.CLAIM_DESTO_INCOME, --业务场景
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PAY.ACCOUNT_DATE, --会计日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             CASE
               WHEN PAY.AMOUNT < 0 THEN
                LEAST(ABS(PAY.AMOUNT),
                      NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                      NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                      NVL(WAYBILL.COD_AMOUNT, 0)) * (-1)
               ELSE
                LEAST(ABS(PAY.AMOUNT),
                      NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                      NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                      NVL(WAYBILL.COD_AMOUNT, 0))
             END, --总运费、理赔金额两者取小
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_PAYABLE PAY --应付单
        JOIN PKP.T_SRV_WAYBILL WAYBILL
          ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
         AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----运单
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔类型应付单
         AND PAY.PAYABLE_ORG_CODE = WAYBILL.LAST_LOAD_ORG_CODE --到达
         AND WAYBILL.LAST_LOAD_ORG_CODE <> WAYBILL.RECEIVE_ORG_CODE --ISSUE-3132
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --15I) 理赔-到达部门申请-01理赔冲收入
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --期间
             PKG_STL_VCH_COMMON.CLAIM_DESTO_INCOME, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --借方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --借方部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --贷方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --贷方部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PAY.ACCOUNT_DATE, --会计日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             CASE
               WHEN PAY.AMOUNT < 0 THEN
                LEAST(ABS(PAY.AMOUNT),
                      NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                      NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                      NVL(WAYBILL.COD_AMOUNT, 0)) * (-1)
               ELSE
                LEAST(ABS(PAY.AMOUNT),
                      NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                      NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                      NVL(WAYBILL.COD_AMOUNT, 0))
             END, --总运费、理赔金额两者取小
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_PAYABLE PAY --应付单
        JOIN PKP.T_SRV_WAYBILL WAYBILL
          ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
         AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----运单
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔类型应付单
         AND PAY.PAYABLE_ORG_CODE = WAYBILL.LAST_LOAD_ORG_CODE --到达
         AND WAYBILL.LAST_LOAD_ORG_CODE <> WAYBILL.RECEIVE_ORG_CODE --ISSUE-3132
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --16) 理赔-到达部门申请-01理赔入成本
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
             PKG_STL_VCH_COMMON.CLAIM_DESTO_COST, --业务场景
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PAY.ACCOUNT_DATE, --会计日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             CASE
               WHEN PAY.AMOUNT < 0 THEN
                (ABS(PAY.AMOUNT) - (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                NVL(WAYBILL.COD_AMOUNT, 0))) * (-1)
               ELSE
                ABS(PAY.AMOUNT) -
                (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                 NVL(WAYBILL.PRE_PAY_AMOUNT, 0) - NVL(WAYBILL.COD_AMOUNT, 0))
             END, --理赔金额减去总运费,如果结果小于0,值为0
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_PAYABLE PAY --应付单
        JOIN PKP.T_SRV_WAYBILL WAYBILL
          ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
         AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----运单
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔类型应付单
         AND PAY.PAYABLE_ORG_CODE = WAYBILL.LAST_LOAD_ORG_CODE --到达
         AND ABS(PAY.AMOUNT) -
             (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
             NVL(WAYBILL.PRE_PAY_AMOUNT, 0) - NVL(WAYBILL.COD_AMOUNT, 0)) > 0
         AND WAYBILL.RECEIVE_ORG_CODE <> WAYBILL.LAST_LOAD_ORG_CODE
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --17) 理赔-到达部门申请-02理赔冲收入
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
             PKG_STL_VCH_COMMON.CLAIM_DESTT_INCOME, --业务场景
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PAY.ACCOUNT_DATE, --会计日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             CASE
               WHEN PAY.AMOUNT < 0 THEN
                LEAST(ABS(PAY.AMOUNT),
                      NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                      NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                      NVL(WAYBILL.COD_AMOUNT, 0)) * (-1)
               ELSE
                LEAST(ABS(PAY.AMOUNT),
                      NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                      NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                      NVL(WAYBILL.COD_AMOUNT, 0))
             END, --总运费、理赔金额两者取小
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_PAYABLE PAY --应付单
        JOIN PKP.T_SRV_WAYBILL WAYBILL
          ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
         AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----运单
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔类型应付单
         AND PAY.PAYABLE_ORG_CODE = WAYBILL.LAST_LOAD_ORG_CODE --到达
         AND WAYBILL.LAST_LOAD_ORG_CODE <> WAYBILL.RECEIVE_ORG_CODE --ISSUE-3132
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --17I) 理赔-到达部门申请-02理赔冲收入
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --期间
             PKG_STL_VCH_COMMON.CLAIM_DESTT_INCOME, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,
                    PAY.ORIG_ORG_CODE), --借方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,
                    PAY.ORIG_ORG_NAME), --借方部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --贷方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --贷方部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PAY.ACCOUNT_DATE, --会计日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             CASE
               WHEN PAY.AMOUNT < 0 THEN
                LEAST(ABS(PAY.AMOUNT),
                      NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                      NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                      NVL(WAYBILL.COD_AMOUNT, 0)) * (-1)
               ELSE
                LEAST(ABS(PAY.AMOUNT),
                      NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                      NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                      NVL(WAYBILL.COD_AMOUNT, 0))
             END, --总运费、理赔金额两者取小
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_PAYABLE PAY --应付单
        JOIN PKP.T_SRV_WAYBILL WAYBILL
          ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
         AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----运单
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔类型应付单
         AND PAY.PAYABLE_ORG_CODE = WAYBILL.LAST_LOAD_ORG_CODE --到达
         AND WAYBILL.LAST_LOAD_ORG_CODE <> WAYBILL.RECEIVE_ORG_CODE --ISSUE-3132
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --18) 理赔-到达部门申请-01理赔入成本
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
             PKG_STL_VCH_COMMON.CLAIM_DESTT_COST, --业务场景
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PAY.ACCOUNT_DATE, --会计日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             CASE
               WHEN PAY.AMOUNT < 0 THEN
                (ABS(PAY.AMOUNT) - (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
                NVL(WAYBILL.COD_AMOUNT, 0))) * (-1)
               ELSE
                ABS(PAY.AMOUNT) -
                (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
                 NVL(WAYBILL.PRE_PAY_AMOUNT, 0) - NVL(WAYBILL.COD_AMOUNT, 0))
             END, --理赔金额减去总运费,如果结果小于0,值为0
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_PAYABLE PAY --应付单
        JOIN PKP.T_SRV_WAYBILL WAYBILL
          ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
         AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----运单
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔类型应付单
         AND PAY.PAYABLE_ORG_CODE = WAYBILL.LAST_LOAD_ORG_CODE --到达
         AND ABS(PAY.AMOUNT) -
             (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
             NVL(WAYBILL.PRE_PAY_AMOUNT, 0) - NVL(WAYBILL.COD_AMOUNT, 0)) > 0
         AND WAYBILL.RECEIVE_ORG_CODE <> WAYBILL.LAST_LOAD_ORG_CODE
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --19) 理赔-到达部门申请-01理赔冲01到达应收已签收
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
             PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_POD, --业务场景
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
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RCV.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --到达
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --到达应收、到达偏线代理应收单、空运到达代理应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --19I) 理赔-到达部门申请-01理赔冲01到达应收已签收
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --期间
             PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_POD, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --借方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --借方部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --贷方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --贷方部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --到达
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --到达应收、到达偏线代理应收单、空运到达代理应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --20) 理赔-到达部门申请-02理赔冲01到达应收已签收
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
             PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_POD, --业务场景
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
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RCV.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --到达
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --到达应收、到达偏线代理应收单、空运到达代理应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --20I) 理赔-到达部门申请-02理赔冲01到达应收已签收
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --期间
             PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_POD, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --借方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --借方部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --货方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --货方部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --到达
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --到达应收、到达偏线代理应收单、空运到达代理应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --21) 理赔-到达部门申请-01理赔冲02到达应收已签收
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
             PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_POD, --业务场景
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
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RCV.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --到达
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --到达应收、到达偏线代理应收单、空运到达代理应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --21I) 理赔-到达部门申请-01理赔冲02到达应收已签收
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --期间
             PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_POD, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --借方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --借方部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --贷方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --贷方部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --到达
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --到达应收、到达偏线代理应收单、空运到达代理应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --22) 理赔-到达部门申请-02理赔冲02到达应收已签收
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
             PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_POD, --业务场景
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
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RCV.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --到达
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --到达应收、到达偏线代理应收单、空运到达代理应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --22I) 理赔-到达部门申请-02理赔冲02到达应收已签收
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --期间
             PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_POD, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --借方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --借方部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --贷方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --贷方部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --到达
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --到达应收、到达偏线代理应收单、空运到达代理应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --23) 理赔-到达部门申请-01理赔冲01到达应收未签收
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
             PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_NPOD, --业务场景
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
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RCV.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --到达
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --到达应收、到达偏线代理应收单、空运到达代理应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --23I) 理赔-到达部门申请-01理赔冲01到达应收未签收
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --期间
             PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_NPOD, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --借方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --借方部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --贷方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --贷方部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --到达
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --到达应收、到达偏线代理应收单、空运到达代理应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         -- AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --24) 理赔-到达部门申请-02理赔冲01到达应收未签收
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
             PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_NPOD, --业务场景
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
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RCV.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --到达
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --到达应收、到达偏线代理应收单、空运到达代理应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --24I) 理赔-到达部门申请-02理赔冲01到达应收未签收
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --期间
             PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_NPOD, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --借方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --借方部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --贷方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --贷方部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --到达
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --到达应收、到达偏线代理应收单、空运到达代理应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --25) 理赔-到达部门申请-01理赔冲02到达应收未签收
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
             PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_NPOD, --业务场景
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
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RCV.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --到达
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --到达应收、到达偏线代理应收单、空运到达代理应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --25I) 理赔-到达部门申请-01理赔冲02到达应收未签收
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --期间
             PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_NPOD, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --借方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --借方部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --贷方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --贷方部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --到达
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --到达应收、到达偏线代理应收单、空运到达代理应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --26) 理赔-到达部门申请-02理赔冲02到达应收未签收
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
             PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_NPOD, --业务场景
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
             WO.ACCOUNT_DATE, --记账日期
             RCV.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
             RCV.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --到达
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --到达应收、到达偏线代理应收单、空运到达代理应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --26I) 理赔-到达部门申请-02理赔冲02到达应收未签收
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --期间
             PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_NPOD, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --借方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --借方部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,
                    RCV.ORIG_ORG_CODE), --贷方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,
                    RCV.ORIG_ORG_NAME), --贷方部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             WO.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             WO.AMOUNT,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --到达
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --到达应收、到达偏线代理应收单、空运到达代理应收
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
         --AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --27) 理赔-到达部门申请-01理赔付款申请
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
             PKG_STL_VCH_COMMON.CLAIM_DESTO_PAY_APPLY, --业务场景
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PAY.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             DP.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1),
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_PAYMENT PMT --付款单
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --付款单明细
        JOIN STL.T_STL_BILL_PAYABLE PAY --应付单
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达
         AND PAY.ORIG_ORG_CODE <> PAY.DEST_ORG_CODE --BUG-40092
         AND DP.PAY_AMOUNT > 0
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PMT.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --27I) 理赔-到达部门申请-01理赔付款申请
    INSERT INTO STV.T_STL_NDVDI
      (ID,
       PERIOD,
       BUSINESS_CASE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       CUSTOMER_TYPE,
       CREDIT_ORG_CODE,
       CREDIT_ORG_NAME,
       CREDIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       DEBIT_ORG_TYPE,
       WAYBILL_NO,
       BILL_NO,
       ACCOUNT_DATE,
       BUSINESS_DATE,
       BILL_PARENT_TYPE,
       BILL_TYPE,
       AMOUNT,
       PRODUCT_CODE)
      SELECT SYS_GUID(), --ID
             P_PERIOD, --期间
             PKG_STL_VCH_COMMON.CLAIM_DESTO_PAY_APPLY, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --借方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --借方部门名称
             PKG_STL_COMMON.INVOICE_MARK_ONE,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,
                    PAY.DEST_ORG_CODE), --贷方部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,
                    PAY.DEST_ORG_NAME), --贷方部门名称
             PKG_STL_COMMON.INVOICE_MARK_TWO,
             PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PAY.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             DP.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1),
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_PAYMENT PMT --付款单
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --付款单明细
        JOIN STL.T_STL_BILL_PAYABLE PAY --应付单
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达
         AND PAY.ORIG_ORG_CODE <> PAY.DEST_ORG_CODE --BUG-40092
         AND DP.PAY_AMOUNT > 0
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PMT.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --28) 理赔-到达部门申请-02理赔付款申请
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
             PKG_STL_VCH_COMMON.CLAIM_DESTT_PAY_APPLY, --业务场景
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PAY.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             DP.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1),
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型
        FROM STL.T_STL_BILL_PAYMENT PMT --付款单
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --付款单明细
        JOIN STL.T_STL_BILL_PAYABLE PAY --应付单
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔
         AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达
         AND PAY.ORIG_ORG_CODE <> PAY.DEST_ORG_CODE --BUG-40092
         AND DP.PAY_AMOUNT > 0
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PMT.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --6) 理赔-出发部门申请-01理赔冲02始发应收已签收 --01始发特殊                                                                                             
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
             PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_PODP, --业务场景：按核销单的记账日期来统计,取核销单的明细金额,关联明细中的应收单单据子类型为始发应收
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号                                                                                               
             PAY.PAYABLE_NO, --单据编号                                                                                                                      
             WO.ACCOUNT_DATE, --记账日期                                                                                                                     
             PAY.BUSINESS_DATE, --业务日期                                                                                                                   
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型                                                                                                 
             PAY.BILL_TYPE, --单据子类型                                                                                                                     
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型                                                                                                                     
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单                                                                                                             
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单号                                                                                                      
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单号                                                                                                           
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付                                                                        
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔                                                                               
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --始发                                                                                             
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收                                                                       
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --7) 理赔-出发部门申请-02理赔冲01始发应收已签收 --02特殊始发                                                                                             
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
             PKG_STL_VCH_COMMON.CLAIM_ORIGT_ORIG_RCVO_PODP,
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号                                                                                               
             PAY.PAYABLE_NO, --单据编号                                                                                                                      
             WO.ACCOUNT_DATE, --记账日期                                                                                                                     
             PAY.BUSINESS_DATE, --业务日期                                                                                                                   
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型                                                                                                 
             PAY.BILL_TYPE, --单据子类型                                                                                                                     
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型                                                                                                                     
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单                                                                                                             
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单号                                                                                                      
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单号                                                                                                           
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付                                                                        
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔                                                                               
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --始发                                                                                             
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收                                                                       
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --9) 理赔-出发部门申请-01理赔冲02始发应收未签收 --01特殊始发                                                                                             
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
             PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_NPODP,
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号                                                                                               
             PAY.PAYABLE_NO, --单据编号                                                                                                                      
             WO.ACCOUNT_DATE, --记账日期                                                                                                                     
             PAY.BUSINESS_DATE, --业务日期                                                                                                                   
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型                                                                                                 
             PAY.BILL_TYPE, --单据子类型                                                                                                                     
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型                                                                                                                     
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单                                                                                                             
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单号                                                                                                      
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单号                                                                                                           
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付                                                                        
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔                                                                               
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --始发                                                                                             
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收                                                                       
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --10) 理赔-出发部门申请-02理赔冲01始发应收未签收 --02特殊始发                                                                                            
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
             PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVO_NPODP,
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号                                                                                               
             PAY.PAYABLE_NO, --单据编号                                                                                                                      
             WO.ACCOUNT_DATE, --记账日期                                                                                                                     
             PAY.BUSINESS_DATE, --业务日期                                                                                                                   
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型                                                                                                 
             PAY.BILL_TYPE, --单据子类型                                                                                                                     
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型                                                                                                                     
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单                                                                                                             
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单号                                                                                                      
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单号                                                                                                           
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付                                                                        
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔                                                                               
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --始发                                                                                             
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收                                                                       
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    --19) 理赔-到达部门申请-01理赔冲01到达应收已签收 --01达到                                                                                                
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
             PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_PODP, --业务场景                                                                                    
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号                                                                                               
             PAY.PAYABLE_NO, --单据编号                                                                                                                      
             WO.ACCOUNT_DATE, --记账日期                                                                                                                     
             PAY.BUSINESS_DATE, --业务日期                                                                                                                   
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型                                                                                                 
             PAY.BILL_TYPE, --单据子类型                                                                                                                     
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型                                                                                                                     
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单                                                                                                             
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单                                                                                                        
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单                                                                                                             
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付                                                                        
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔                                                                               
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --到达                                                                                             
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --到达应收、到达偏线代理应收单、空运到达代理应收                                                   
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --20) 理赔-到达部门申请-02理赔冲01到达应收已签收 --02到达                                                                                                
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
             PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_PODP, --业务场景                                                                                    
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号                                                                                               
             PAY.PAYABLE_NO, --单据编号                                                                                                                      
             WO.ACCOUNT_DATE, --记账日期                                                                                                                     
             PAY.BUSINESS_DATE, --业务日期                                                                                                                   
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型                                                                                                 
             PAY.BILL_TYPE, --单据子类型                                                                                                                     
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型                                                                                                                     
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单                                                                                                             
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单                                                                                                        
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单                                                                                                             
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付                                                                        
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔                                                                               
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --到达                                                                                             
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --到达应收、到达偏线代理应收单、空运到达代理应收                                                   
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --23) 理赔-到达部门申请-01理赔冲01到达应收未签收                                                                                                         
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
             PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_NPODP, --业务场景                                                                                   
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号                                                                                               
             PAY.PAYABLE_NO, --单据编号                                                                                                                      
             WO.ACCOUNT_DATE, --记账日期                                                                                                                     
             PAY.BUSINESS_DATE, --业务日期                                                                                                                   
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型                                                                                                 
             PAY.BILL_TYPE, --单据子类型                                                                                                                     
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型                                                                                                                     
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单                                                                                                             
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单                                                                                                        
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单                                                                                                             
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付                                                                        
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔                                                                               
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --到达                                                                                             
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --到达应收、到达偏线代理应收单、空运到达代理应收                                                   
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --24) 理赔-到达部门申请-02理赔冲01到达应收未签收                                                                                                         
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
             PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_NPODP, --业务场景                                                                                   
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号                                                                                               
             PAY.PAYABLE_NO, --单据编号                                                                                                                      
             WO.ACCOUNT_DATE, --记账日期                                                                                                                     
             PAY.BUSINESS_DATE, --业务日期                                                                                                                   
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型                                                                                                 
             PAY.BILL_TYPE, --单据子类型                                                                                                                     
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型                                                                                                                     
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单                                                                                                             
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单                                                                                                        
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单                                                                                                             
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付                                                                        
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔                                                                               
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --到达                                                                                             
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --到达应收、到达偏线代理应收单、空运到达代理应收                                                   
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --21) 理赔-到达部门申请-01理赔冲02到达应收已签收                                                                                                         
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
             PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_PODP, --业务场景                                                                                    
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号                                                                                               
             PAY.PAYABLE_NO, --单据编号                                                                                                                      
             WO.ACCOUNT_DATE, --记账日期                                                                                                                     
             PAY.BUSINESS_DATE, --业务日期                                                                                                                   
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型                                                                                                 
             PAY.BILL_TYPE, --单据子类型                                                                                                                     
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型                                                                                                                     
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单                                                                                                             
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单                                                                                                        
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单                                                                                                             
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付                                                                        
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔                                                                               
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --到达                                                                                             
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --到达应收、到达偏线代理应收单、空运到达代理应收                                                   
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --22) 理赔-到达部门申请-02理赔冲02到达应收已签收                                                                                                         
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
             PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_PODP, --业务场景                                                                                    
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号                                                                                               
             PAY.PAYABLE_NO, --单据编号                                                                                                                      
             WO.ACCOUNT_DATE, --记账日期                                                                                                                     
             PAY.BUSINESS_DATE, --业务日期                                                                                                                   
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型                                                                                                 
             PAY.BILL_TYPE, --单据子类型                                                                                                                     
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型                                                                                                                     
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单                                                                                                             
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单                                                                                                        
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单                                                                                                             
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付                                                                        
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔                                                                               
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --到达                                                                                             
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --到达应收、到达偏线代理应收单、空运到达代理应收                                                   
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --25) 理赔-到达部门申请-01理赔冲02到达应收未签收                                                                                                         
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
             PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_NPODP, --业务场景                                                                                   
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号                                                                                               
             PAY.PAYABLE_NO, --单据编号                                                                                                                      
             WO.ACCOUNT_DATE, --记账日期                                                                                                                     
             PAY.BUSINESS_DATE, --业务日期                                                                                                                   
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型                                                                                                 
             PAY.BILL_TYPE, --单据子类型                                                                                                                     
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型                                                                                                                     
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单                                                                                                             
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单                                                                                                        
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单                                                                                                             
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付                                                                        
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔                                                                               
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --到达                                                                                             
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --到达应收、到达偏线代理应收单、空运到达代理应收                                                   
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    --26) 理赔-到达部门申请-02理赔冲02到达应收未签收                                                                                                         
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
             PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_NPODP, --业务场景                                                                                   
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
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号                                                                                               
             PAY.PAYABLE_NO, --单据编号                                                                                                                      
             WO.ACCOUNT_DATE, --记账日期                                                                                                                     
             PAY.BUSINESS_DATE, --业务日期                                                                                                                   
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型                                                                                                 
             PAY.BILL_TYPE, --单据子类型                                                                                                                     
             WO.AMOUNT,
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --产品类型                                                                                                                     
        FROM STL.T_STL_BILL_WRITEOFF WO --核销单                                                                                                             
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --应收单                                                                                                        
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --应付单                                                                                                             
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付                                                                        
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --理赔                                                                               
         AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --到达                                                                                             
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --到达应收、到达偏线代理应收单、空运到达代理应收                                                   
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
         AND WO.AMOUNT <> 0
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
  
    RETURN TRUE;
  
  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        '理赔处理日明细' || '异常');
    
      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_CLAIM;
/
