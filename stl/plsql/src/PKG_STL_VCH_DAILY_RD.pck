CREATE OR REPLACE PACKAGE STV.PKG_STL_VCH_DAILY_RD IS

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

END PKG_STL_VCH_DAILY_RD;
/
CREATE OR REPLACE PACKAGE BODY STV.PKG_STL_VCH_DAILY_RD IS

  -----------------------------------------------------------------
  -- 凭证开单处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS
  BEGIN

    --1）退运费--出发部门申请--退运费冲收入
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
             PKG_STL_VCH_COMMON.RD_ORIG_WO_INCOME, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE，PAY.ORIG_ORG_CODE), --始发部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME，PAY.ORIG_ORG_NAME), --始发部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE，PAY.DEST_ORG_CODE), --到达部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME，PAY.DEST_ORG_NAME), --到达部门名称
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
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --退运费类型应付单
         AND PAY.PAYABLE_ORG_CODE = WAYBILL.RECEIVE_ORG_CODE --始发部门
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE;

    --2）退运费--出发部门申请--退运费入成本
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
             PKG_STL_VCH_COMMON.RD_ORIG_COST, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE，PAY.ORIG_ORG_CODE), --始发部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME，PAY.ORIG_ORG_NAME), --始发部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE，PAY.DEST_ORG_CODE), --到达部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME，PAY.DEST_ORG_NAME), --到达部门名称
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
             END,
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
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --退运费类型应付单
         AND PAY.PAYABLE_ORG_CODE = WAYBILL.RECEIVE_ORG_CODE --始发部门
         AND ABS(PAY.AMOUNT) -
             (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
             NVL(WAYBILL.PRE_PAY_AMOUNT, 0) - NVL(WAYBILL.COD_AMOUNT, 0)) > 0
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE;

    --3）退运费--出发部门申请--退运费付款申请
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
             PKG_STL_VCH_COMMON.RD_ORIG_PAY_APPLY, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE，PAY.ORIG_ORG_CODE), --始发部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME，PAY.ORIG_ORG_NAME), --始发部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE，PAY.DEST_ORG_CODE), --到达部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME，PAY.DEST_ORG_NAME), --到达部门名称
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PAY.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             --金额取数有问题，2013-8-5 黄小波修订
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
        FROM STL.T_STL_BILL_PAYABLE PAY --应付单
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO --付款单明细
        LEFT JOIN STL.T_STL_BILL_PAYMENT PMT
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --付款单
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --退运费
         AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --始发
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --非红单
         AND DP.PAY_AMOUNT > 0
         AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PMT.ACCOUNT_DATE < P_END_DATE;

    --4）退运费--到达部门申请--退运费冲收入
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
             PKG_STL_VCH_COMMON.RD_DEST_WO_INCOME, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE，PAY.ORIG_ORG_CODE), --始发部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME，PAY.ORIG_ORG_NAME), --始发部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE，PAY.DEST_ORG_CODE), --到达部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME，PAY.DEST_ORG_NAME), --到达部门名称
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
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --退运费类型应付单
         AND PAY.PAYABLE_ORG_CODE = WAYBILL.LAST_LOAD_ORG_CODE --到达部门
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
    -- BUG-42080

    --5）退运费--到达部门申请--退运费入成本
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
             PKG_STL_VCH_COMMON.RD_DEST_COST, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE，PAY.ORIG_ORG_CODE), --始发部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME，PAY.ORIG_ORG_NAME), --始发部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE，PAY.DEST_ORG_CODE), --到达部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME，PAY.DEST_ORG_NAME), --到达部门名称
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
             END,
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
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --退运费类型应付单
         AND PAY.PAYABLE_ORG_CODE = WAYBILL.LAST_LOAD_ORG_CODE --到达部门
         AND ABS(PAY.AMOUNT) -
             (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
             NVL(WAYBILL.PRE_PAY_AMOUNT, 0) - NVL(WAYBILL.COD_AMOUNT, 0)) > 0
         AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PAY.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
    -- issue-3303

    --6）退运费--到达部门申请--退运费付款申请
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
             PKG_STL_VCH_COMMON.RD_DEST_PAY_APPLY, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE，PAY.ORIG_ORG_CODE), --始发部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME，PAY.ORIG_ORG_NAME), --始发部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE，PAY.DEST_ORG_CODE), --到达部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME，PAY.DEST_ORG_NAME), --到达部门名称
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PAY.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             --金额取数有问题，2013-8-5 黄小波修订
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
        FROM STL.T_STL_BILL_PAYABLE PAY --应付单
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO --付款单明细
        LEFT JOIN STL.T_STL_BILL_PAYMENT PMT
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --付款单
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --退运费
         AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --非红单
         AND DP.PAY_AMOUNT > 0
         AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
         AND PMT.ACCOUNT_DATE < P_END_DATE
         AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
    -- issue-3303

    --7)退运费——出发部门申请——退运费冲始发应收已签收
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
             PKG_STL_VCH_COMMON.RD_WO_ORIG_RCV_POD, --业务场景
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE), --始发部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME，RCV.ORIG_ORG_NAME), --始发部门名称
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE，RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME),
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --运单号
             RCV.RECEIVABLE_NO, --单据编号
             RCV.ACCOUNT_DATE, --记账日期
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
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --退运费
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --冲销时已签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;

    --8)退运费——出发部门申请——退运费冲始发应收未签收
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
             PKG_STL_VCH_COMMON.RD_WO_ORIG_RCV_NPOD, --业务场景
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE), --始发部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME，RCV.ORIG_ORG_NAME), --始发部门名称
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE，RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME),
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --运单号
             RCV.RECEIVABLE_NO, --单据编号
             RCV.ACCOUNT_DATE, --记账日期
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
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --退运费
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --冲销时已签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;

    --9)退运费——出发部门申请——退运费冲始发应收已签收
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
             PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_POD, --业务场景
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE), --始发部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME，RCV.ORIG_ORG_NAME), --始发部门名称
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE，RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME),
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --运单号
             RCV.RECEIVABLE_NO, --单据编号
             RCV.ACCOUNT_DATE, --记账日期
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
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --退运费
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --冲销时已签收
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;

    --10)退运费——出发部门申请——退运费冲始发应收未签收
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
             PKG_STL_VCH_COMMON.RD_WO_DEST_RCV_NPOD, --业务场景
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE), --始发部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME，RCV.ORIG_ORG_NAME), --始发部门名称
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE，RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME),
             NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --运单号
             RCV.RECEIVABLE_NO, --单据编号
             RCV.ACCOUNT_DATE, --记账日期
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
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP
         AND RCV.BILL_TYPE IN
             (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --退运费
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --冲销时已签收
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
                                        '凭证开单处理日明细' || '异常');

      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_RD;
/
