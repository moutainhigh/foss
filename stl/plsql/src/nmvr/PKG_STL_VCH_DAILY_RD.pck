CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_RD IS

  -- ==============================================================
  -- Author  : ZBW
  -- Created : 2013-11-06
  -- Purpose : 每日凭证退运费
  -- ==============================================================

  -----------------------------------------------------------------
  -- 退运费处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_DAILY_RD;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_RD IS

  -----------------------------------------------------------------
  -- 退运费处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd,例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN -- 返回结果 true:成功,false:失败
   IS
  BEGIN
     --1）退运费--出发部门申请--01退运费冲收入
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
             PKG_STL_VCH_COMMON.RD_ORIGO_INCOME, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
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
             AND PAY.ACCOUNT_DATE < P_END_DATE
             AND pay.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
             ;

    --2）退运费--出发部门申请--01退运费入成本
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
             PKG_STL_VCH_COMMON.RD_ORIGO_COST, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
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
             AND ABS(PAY.AMOUNT) - (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
             NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
             NVL(WAYBILL.COD_AMOUNT, 0)) > 0
             AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PAY.ACCOUNT_DATE < P_END_DATE
             AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;

    --3）退运费--出发部门申请--01退运费付款申请
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
             PKG_STL_VCH_COMMON.RD_ORIGO_PAY_APPLY, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PMT.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
             --金额取数有问题,2013-8-5 黄小波修订
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
             AND DP.Source_Bill_No = PAY.Payable_No
             AND DP.PAY_AMOUNT > 0
             AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PMT.ACCOUNT_DATE < P_END_DATE
             AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --非红单
             AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
     --4）退运费--出发部门申请--02退运费冲收入
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
             PKG_STL_VCH_COMMON.RD_ORIGT_INCOME, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
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
             AND PAY.ACCOUNT_DATE < P_END_DATE
             AND pay.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
             ;

    --5）退运费--出发部门申请--02退运费入成本
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
             PKG_STL_VCH_COMMON.RD_ORIGT_COST, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
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
             AND ABS(PAY.AMOUNT) - (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
             NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
             NVL(WAYBILL.COD_AMOUNT, 0)) > 0
             AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PAY.ACCOUNT_DATE < P_END_DATE
             AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;

    --6）退运费--出发部门申请--02退运费付款申请
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
             PKG_STL_VCH_COMMON.RD_ORIGT_PAY_APPLY, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PMT.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
               --金额取数有问题,2013-8-5 黄小波修订
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
             AND DP.Source_Bill_No = PAY.Payable_No
             AND DP.PAY_AMOUNT > 0
             AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PMT.ACCOUNT_DATE < P_END_DATE
             AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --非红单
             AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
      --7）退运费--出发部门申请--01退运费冲01始发应收已签收
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVO_POD,--01退运费冲01始发应收已签收
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --始发部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --始发部门名称
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --到达部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --到达部门名称
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--运单号
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--应付单
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.Account_Date) = 1 --已签收      
       AND　RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应收01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应付01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND  --应付退运费
        AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --始发部门
        ;

       --8）退运费--出发部门申请--01退运费冲02始发应收已签收 /***********
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_PODP,--01退运费冲02始发应收已签收
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--运单号
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应付单
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.Account_Date) = 1 --已签收
       AND　RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应收02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应付01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
       AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --始发部门
        ;
      --8--1）退运费--出发部门申请--01退运费冲02始发应收已签收 /***********
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_POD,--01退运费冲02始发应收已签收
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --始发部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --始发部门名称
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --到达部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --到达部门名称
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--运单号
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--应收单
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.Account_Date) = 1 --已签收
       AND　RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应收02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应付01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
       AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --始发部门
        ;
      --9）退运费--出发部门申请--02退运费冲01始发应收已签收 /***************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_PODP,--02退运费冲01始发应收已签收
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--运单号
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应收单
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收
       AND　RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应收01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应付02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
       AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --始发部门
        ;
       --9--1）退运费--出发部门申请--02退运费冲01始发应收已签收 /***************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_POD,--02退运费冲01始发应收已签收
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --始发部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --始发部门名称
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --到达部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --到达部门名称
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--运单号
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--应收单
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收
       AND　RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应收01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应付02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
       AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --始发部门
       ;
      
      --10）退运费--出发部门申请--02退运费冲02始发应收已签收
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVT_POD,--02退运费冲02始发应收已签收
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --始发部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --始发部门名称
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --到达部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --到达部门名称
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--运单号
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--应付单
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收
       AND　RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO        --应收02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应付02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE--始发部门
        ;
      --11）退运费--出发部门申请--01退运费冲01始发应收未签收
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVO_NPOD,--01退运费冲01始发应收未签收
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--运单号
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应付单
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收
       AND　RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应收01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应付01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --始发部门
        ;
       --12）退运费--出发部门申请--01退运费冲02始发应收未签收 /********************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_NPODP,--01退运费冲02始发应收未签收
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--运单号
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应付单
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收
       AND　RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应收02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应付01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE =PAY.ORIG_ORG_CODE --始发部门
        ;
        
       --12--1）退运费--出发部门申请--01退运费冲02始发应收未签收 /********************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_NPOD,--01退运费冲02始发应收未签收
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --始发部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --始发部门名称
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --到达部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --到达部门名称
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--运单号
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--应收单
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收
       AND　RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应收02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应付01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE =PAY.ORIG_ORG_CODE --始发部门
        ;
      --13）退运费--出发部门申请--02退运费冲01始发应收未签收 /*******************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_NPODP,--02退运费冲01始发应收未签收
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--运单号
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应付单
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收       
       AND　RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应收01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应付02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
       AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --始发部门
        ;
      
      --13--1）退运费--出发部门申请--02退运费冲01始发应收未签收 /*******************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_NPOD,--02退运费冲01始发应收未签收
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --始发部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --始发部门名称
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --到达部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --到达部门名称
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--运单号
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--应收单
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收       
       AND　RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应收01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应付02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
       AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --始发部门
        ;
      --14）退运费--出发部门申请--02退运费冲02始发应收已签收
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVT_NPOD,--02退运费冲02始发应收已签收
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --始发部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --始发部门名称
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --到达部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --到达部门名称
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--运单号
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--应付单
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收      
       AND　RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO        --应收02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应付02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --始发部门
        ;

     --15）退运费--到达部门申请--01退运费冲收入
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
             PKG_STL_VCH_COMMON.RD_DESTO_INCOME, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
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
             AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
             AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PAY.ACCOUNT_DATE < P_END_DATE
             AND pay.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE ;

    --16）退运费--到达部门申请--01退运费入成本
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
             PKG_STL_VCH_COMMON.RD_DESTO_COST, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
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
             AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
             AND ABS(PAY.AMOUNT) - (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
             NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
             NVL(WAYBILL.COD_AMOUNT, 0)) > 0
             AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PAY.ACCOUNT_DATE < P_END_DATE
             AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;

    --17）退运费--到达门申请--01退运费付款申请
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
             PKG_STL_VCH_COMMON.RD_DESTO_PAY_APPLY, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PMT.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
               --金额取数有问题,2013-8-5 黄小波修订
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
             AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达
              AND DP.Source_Bill_No = PAY.PAYABLE_NO
             AND DP.PAY_AMOUNT > 0
             AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PMT.ACCOUNT_DATE < P_END_DATE
             AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --非红单
             AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
     --18）退运费--到达部门申请--02退运费冲收入
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
             PKG_STL_VCH_COMMON.RD_DESTT_INCOME, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
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
             AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
             AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PAY.ACCOUNT_DATE < P_END_DATE
             AND pay.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
             ;

    --19）退运费--到达部门申请--02退运费入成本
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
             PKG_STL_VCH_COMMON.RD_DESTT_COST, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
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
             AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
             AND ABS(PAY.AMOUNT) - (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
             NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
             NVL(WAYBILL.COD_AMOUNT, 0)) > 0
             AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PAY.ACCOUNT_DATE < P_END_DATE
             AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;

    --20）退运费--到达部门申请--02退运费付款申请
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
             PKG_STL_VCH_COMMON.RD_DESTT_PAY_APPLY, --业务场景
             PAY.CUSTOMER_CODE, --客户编码
             PAY.CUSTOMER_NAME, --客户名称
             PAY.CUSTOMER_TYPE, --客户类型
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
             NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --运单号
             PAY.PAYABLE_NO, --单据编号
             PMT.ACCOUNT_DATE, --记账日期
             PAY.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PAY.BILL_TYPE, --单据子类型
               --金额取数有问题,2013-8-5 黄小波修订
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
             AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达
             AND DP.SOURCE_BILL_NO = PAY.PAYABLE_NO
             AND DP.PAY_AMOUNT > 0
             AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PMT.ACCOUNT_DATE < P_END_DATE
             AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --非红单
             AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;

      --21）退运费--到达部门申请--01退运费冲01始发应收已签收 /**************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVO_POD ,--01退运费冲01始发应收已签收
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --始发部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --始发部门名称
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --到达部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --到达部门名称
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--运单号
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--应收单
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收       
       AND　RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--到达应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--到达偏线代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达落地配代理应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应收01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应付01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND  --应付退运费
       AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
       ;
       
      --21--1）退运费--到达部门申请--01退运费冲01始发应收已签收 /**************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVO_PODP,--01退运费冲01始发应收已签收
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--运单号
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应付单
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收       
       AND　RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--到达应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--到达偏线代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达落地配代理应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应收01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应付01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND  --应付退运费
       AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
       ;

       --22）退运费--出发部门申请--01退运费冲02到达应收已签收/*********************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVT_PODP,--01退运费冲02到达应收已签收
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--运单号
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应付单
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收      
       AND　RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--到达应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--到达偏线代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达落地配代理应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应收01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应付01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
       AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
        ;
      
       --22--1）退运费--出发部门申请--01退运费冲02到达应收已签收/*********************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVT_POD,--01退运费冲02到达应收已签收
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --始发部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --始发部门名称
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --到达部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --到达部门名称
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--运单号
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--应收单
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收      
       AND　RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--到达应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--到达偏线代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达落地配代理应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应收01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应付01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
       AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
        ;
      --23）退运费--到达部门申请--02退运费冲01到达应收已签收 /*******************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_PODP,--02退运费冲01到达应收已签收
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--运单号
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应付单
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
     INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收      
       AND　RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--到达应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--到达偏线代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达落地配代理应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应收01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应付02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
        ;
      
      --23--1）退运费--到达部门申请--02退运费冲01到达应收已签收 /*******************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_POD,--02退运费冲01到达应收已签收
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --始发部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --始发部门名称
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --到达部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --到达部门名称
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--运单号
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--应收单
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
     INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收      
       AND　RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--到达应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--到达偏线代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达落地配代理应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应收01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应付02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
        ;
      --24）退运费--到达部门申请--02退运费冲02到达应收已签收 /****************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_POD,--02退运费冲02到达应收已签收
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --始发部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --始发部门名称
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --到达部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --到达部门名称
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--运单号
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--应付单
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收       
       AND　RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--到达应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--到达偏线代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达落地配代理应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO        --应收02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应付02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
        ;
      --24--1）退运费--到达部门申请--02退运费冲02到达应收已签收 /****************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_PODP,--02退运费冲02到达应收已签收
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--运单号
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应付单
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收       
       AND　RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--到达应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--到达偏线代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达落地配代理应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO        --应收02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应付02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
        ;
      --25）退运费--到达部门申请--01退运费冲01到达应收未签收 /**************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVO_NPODP,--01退运费冲01到达应收未签收
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--运单号
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应付单
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收      
       AND　RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--到达应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--到达偏线代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达落地配代理应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应收01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应付01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
        ;
        
      --25--1）退运费--到达部门申请--01退运费冲01到达应收未签收 /**************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVO_NPOD,--01退运费冲01到达应收未签收
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --始发部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --始发部门名称
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --到达部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --到达部门名称
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--运单号
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--应收单
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收      
       AND　RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--到达应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--到达偏线代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达落地配代理应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应收01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应付01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
        ;
       --26）退运费--到达部门申请--01退运费冲02到达应收未签收 /******************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVT_NPODP,--01退运费冲02到达应收未签收
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--运单号
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应付单
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收      
       AND　RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--到达应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--到达偏线代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达落地配代理应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应收02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应付01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
        ;
       --26--1）退运费--到达部门申请--01退运费冲02到达应收未签收 /******************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVT_NPOD,--01退运费冲02到达应收未签收
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --始发部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --始发部门名称
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --到达部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --到达部门名称
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--运单号
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--应付单
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收      
       AND　RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--到达应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--到达偏线代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达落地配代理应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应收02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应付01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
        ;
      --27）退运费--到达部门申请--02退运费冲01到达应收未签收 /*************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_NPODP,--02退运费冲01到达应收未签收
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--运单号
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应付单
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收      
       AND　RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--到达应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--到达偏线代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达落地配代理应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应收01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应付02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE =PAY.DEST_ORG_CODE --到达部门
        ;
        
        --27--1）退运费--到达部门申请--02退运费冲01到达应收未签收 /*************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_NPOD,--02退运费冲01到达应收未签收
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --始发部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --始发部门名称
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --到达部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --到达部门名称
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--运单号
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--应付单
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收      
       AND　RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--到达应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--到达偏线代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达落地配代理应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应收01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应付02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE =PAY.DEST_ORG_CODE --到达部门
        ;
      --28）退运费--出到达门申请--02退运费冲02到达应收未签收 /********************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_NPODP,--02退运费冲02到达应收未签收
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
      DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--运单号
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应付单
      PAY.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      PAY.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收      
       AND　RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--到达应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--到达偏线代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达落地配代理应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO        --应收02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应付02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
        ;
      
      --28--1）退运费--出到达门申请--02退运费冲02到达应收未签收 /********************
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
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_NPOD,--02退运费冲02到达应收未签收
      RCV.CUSTOMER_CODE,
      RCV.CUSTOMER_NAME,
      RCV.CUSTOMER_TYPE,
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --始发部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --始发部门名称
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --到达部门编码
      DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --到达部门名称
      NVL(RCV.WAYBILL_NO,RCV.SOURCE_BILL_NO) ,--运单号
      RCV.RECEIVABLE_NO,
      WO.ACCOUNT_DATE,
      RCV.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YS,--应收单
      RCV.BILL_TYPE,
      WO.AMOUNT,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      RCV.PRODUCT_CODE --产品
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收      
       AND　RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--到达应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--到达偏线代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达落地配代理应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO        --应收02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应付02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
        ;
        
      --29）退运费 出发部门申请 01退运费付款申请--往来
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE
       )
       SELECT
       SYS_GUID(),
       P_PERIOD,
       PKG_STL_VCH_COMMON.RD_ORIGO_PAY_APPLY, --01退运费付款申请
       PAY.CUSTOMER_CODE,
       PAY.CUSTOMER_NAME,
       PAY.CUSTOMER_TYPE,
       NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO),
       PAY.PAYABLE_NO,
       PMT.ACCOUNT_DATE,
       PAY.BUSINESS_DATE,
       PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应付单,
       PAY.BILL_TYPE,
       DP.PAY_AMOUNT*DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1),
       PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --始发部门编码
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --始发部门名称
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --始发部门编码
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --始发部门名称
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST
        FROM STL.T_STL_BILL_PAYABLE PAY --应付单
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO --付款单明细
        LEFT JOIN STL.T_STL_BILL_PAYMENT PMT
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --付款单
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --退运费
             AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --始发
              AND DP.Source_Bill_No = PAY.PAYABLE_NO
             AND DP.PAY_AMOUNT > 0
             AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PMT.ACCOUNT_DATE < P_END_DATE
             AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --非红单
             AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;

      --30）退运费 出发部门申请 01退运费冲02始发应收已签收--往来 
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_POD,
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--运单号
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,
      PAY.BILL_TYPE,
      WO.AMOUNT,
      PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收      
       AND　RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应收02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应付01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
       AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --始发部门
       ;


      --31）退运费 出发部门申请 02退运费冲01始发应收已签收--往来 
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_POD,--02冲01
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--运单号
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,
      PAY.BILL_TYPE,
      WO.AMOUNT,
      PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收      
       AND　RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应收01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应付02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --始发部门
        ;
     --32）退运费 出发部门申请 01退运费冲02始发应收未签收--往来 
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_NPOD,
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--运单号
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,
      PAY.BILL_TYPE,
      WO.AMOUNT,
      PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收      
       AND　RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应收02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应付01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
       AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --始发部门
       ;

       --33）退运费 出发部门申请 02退运费冲01始发应收未签收--往来 
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE
       )
      SELECT
      SYS_GUID(),
      P_PERIOD,
      PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_NPOD,--02冲01
      PAY.CUSTOMER_CODE,
      PAY.CUSTOMER_NAME,
      PAY.CUSTOMER_TYPE,
      NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO) ,--运单号
      PAY.PAYABLE_NO,
      WO.ACCOUNT_DATE,
      PAY.BUSINESS_DATE,
      PKG_STL_COMMON.BILL_PARENT_TYPE__YF,
      PAY.BILL_TYPE,
      WO.AMOUNT,
      PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE
                          ) = 0 --未签收      
       AND　RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应收01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应付02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --出发部门
        ;
     --34）退运费 到达部门申请 01退运费冲收入 --往来
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
       SELECT
       SYS_GUID(),
       P_PERIOD,
       PKG_STL_VCH_COMMON.RD_DESTO_INCOME, --01退运费冲收入
       PAY.CUSTOMER_CODE,
       PAY.CUSTOMER_NAME,
       PAY.CUSTOMER_TYPE,
       NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO),
       PAY.PAYABLE_NO,
       PAY.ACCOUNT_DATE,
       PAY.BUSINESS_DATE,
       PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应付单,
       PAY.BILL_TYPE,
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
       PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --始发部门编码
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --始发部门名称
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST
       FROM STL.T_STL_BILL_PAYABLE PAY --应付单
        JOIN PKP.T_SRV_WAYBILL WAYBILL
          ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
             AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----运单
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --退运费类型应付单
             AND PAY.PAYABLE_ORG_CODE = WAYBILL.LAST_LOAD_ORG_CODE --到达部门
             AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PAY.ACCOUNT_DATE < P_END_DATE
             AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE
             AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;

    --35）退运费 到达部门申请 01退运费付款申请 --往来
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
       SELECT
       SYS_GUID(),
       P_PERIOD,
       PKG_STL_VCH_COMMON.RD_DESTO_PAY_APPLY, --01退运费申请
       PAY.CUSTOMER_CODE,
       PAY.CUSTOMER_NAME,
       PAY.CUSTOMER_TYPE,
       NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO),
       PAY.PAYABLE_NO,
       PMT.ACCOUNT_DATE,
       PAY.BUSINESS_DATE,
       PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应付单,
       PAY.BILL_TYPE,
       DP.PAY_AMOUNT*DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1),
       PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST
      FROM STL.T_STL_BILL_PAYABLE PAY --应付单
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO --付款单明细
        LEFT JOIN STL.T_STL_BILL_PAYMENT PMT
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --付款单
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --退运费
             AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
             AND DP.SOURCE_BILL_NO = PAY.PAYABLE_NO
             AND DP.PAY_AMOUNT > 0
             AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PMT.ACCOUNT_DATE < P_END_DATE
             AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE
             AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --非红单
             AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;

      --36）退运费 到达部门申请 02退运费冲收入 --往来
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
       SELECT
       SYS_GUID(),
       P_PERIOD,
       PKG_STL_VCH_COMMON.RD_DESTT_INCOME, --01退运费冲收入
       PAY.CUSTOMER_CODE,
       PAY.CUSTOMER_NAME,
       PAY.CUSTOMER_TYPE,
       NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO),
       PAY.PAYABLE_NO,
       PAY.ACCOUNT_DATE,
       PAY.BUSINESS_DATE,
       PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应付单,
       PAY.BILL_TYPE,
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
             END,
       PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --始发部门编码
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --始发部门名称
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST
       FROM STL.T_STL_BILL_PAYABLE PAY --应付单
        JOIN PKP.T_SRV_WAYBILL WAYBILL
          ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
             AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----运单
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --退运费类型应付单
             AND PAY.PAYABLE_ORG_CODE = WAYBILL.LAST_LOAD_ORG_CODE --到达部门
             AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PAY.ACCOUNT_DATE < P_END_DATE
             AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE
             AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;

     --37）退运费 到达部门申请 01退运费冲01到达应收已签收 --往来 
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
       SELECT
       SYS_GUID(),
       P_PERIOD,
       PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVO_POD, --01退运费冲01到达应收已签收
       PAY.CUSTOMER_CODE,
       PAY.CUSTOMER_NAME,
       PAY.CUSTOMER_TYPE,
       NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO),
       PAY.PAYABLE_NO,
       WO.ACCOUNT_DATE,
       PAY.BUSINESS_DATE,
       PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应付单,
       PAY.BILL_TYPE,
       WO.AMOUNT,
       PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --始发部门编码
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --始发部门名称
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收       
       AND　RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--到达应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--到达偏线代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达落地配代理应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应收01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应付01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
       AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
        ;
     --38）退运费 到达部门申请 02退运费冲01到达应收已签收 --往来
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
       SELECT
       SYS_GUID(),
       P_PERIOD,
       PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_POD, --02退运费冲01到达应收已签收
       PAY.CUSTOMER_CODE,
       PAY.CUSTOMER_NAME,
       PAY.CUSTOMER_TYPE,
       NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO),
       PAY.PAYABLE_NO,
       WO.ACCOUNT_DATE,
       PAY.BUSINESS_DATE,
       PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应付单,
       PAY.BILL_TYPE,
       WO.AMOUNT,
       PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --始发部门编码
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --始发部门名称
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收       
       AND　RCV.BILL_TYPE IN  (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--到达应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--到达偏线代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达落地配代理应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应收01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO        --应付02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
        ;

     --39）退运费 到达部门申请 01退运费冲01到达应收未签收 --往来 
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
       SELECT
       SYS_GUID(),
       P_PERIOD,
       PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVO_NPOD, --01退运费冲01到达应收未签收
       PAY.CUSTOMER_CODE,
       PAY.CUSTOMER_NAME,
       PAY.CUSTOMER_TYPE,
       NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO),
       PAY.PAYABLE_NO,
       WO.ACCOUNT_DATE,
       PAY.BUSINESS_DATE,
       PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应付单,
       PAY.BILL_TYPE,
       WO.AMOUNT,
       PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --始发部门编码
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --始发部门名称
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收      
       AND　RCV.BILL_TYPE IN  (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--到达应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--到达偏线代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达落地配代理应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应收01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应付01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
        ;

     --40）退运费 到达部门申请 02退运费冲01到达应收未签收 --往来
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE
       )
       SELECT
       SYS_GUID(),
       P_PERIOD,
       PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_NPOD, --02退运费冲01到达应收未签收
       PAY.CUSTOMER_CODE,
       PAY.CUSTOMER_NAME,
       PAY.CUSTOMER_TYPE,
       NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO),
       PAY.PAYABLE_NO,
       WO.ACCOUNT_DATE,
       PAY.BUSINESS_DATE,
       PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应付单,
       PAY.BILL_TYPE,
       WO.AMOUNT,
       PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --始发部门编码
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --始发部门名称
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收      
       AND　RCV.BILL_TYPE IN  (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--到达应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--到达偏线代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达落地配代理应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应收01
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO        --应付02
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
        ;
      --41）退运费 到达部门申请 01退运费冲02到达应收已签收 --往来
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
       SELECT
       SYS_GUID(),
       P_PERIOD,
       PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVT_POD, --01退运费冲02到达应收已签收
       PAY.CUSTOMER_CODE,
       PAY.CUSTOMER_NAME,
       PAY.CUSTOMER_TYPE,
       NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO),
       PAY.PAYABLE_NO,
       WO.ACCOUNT_DATE,
       PAY.BUSINESS_DATE,
       PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应付单,
       PAY.BILL_TYPE,
       WO.AMOUNT,
       PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --始发部门编码
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --始发部门名称
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --始发部门编码
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --始发部门名称
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收      
       AND　RCV.BILL_TYPE  IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--到达应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--到达偏线代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达落地配代理应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应收02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应付01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
        ;

     --42）退运费 到达部门申请 02退运费冲02到达应收已签收 --往来
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
       SELECT
       SYS_GUID(),
       P_PERIOD,
       PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_POD, --02退运费冲02到达应收已签收
       PAY.CUSTOMER_CODE,
       PAY.CUSTOMER_NAME,
       PAY.CUSTOMER_TYPE,
       NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO),
       PAY.PAYABLE_NO,
       WO.ACCOUNT_DATE,
       PAY.BUSINESS_DATE,
       PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应付单,
       PAY.BILL_TYPE,
       WO.AMOUNT,
       PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --始发部门编码
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --始发部门名称
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --已签收      
       AND　RCV.BILL_TYPE IN  (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--到达应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--到达偏线代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达落地配代理应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应收02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应付01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
        ;

     --43）退运费 到达部门申请 01退运费冲02到达应收未签收 --往来
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
       SELECT
       SYS_GUID(),
       P_PERIOD,
       PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVT_NPOD, --01退运费冲02到达应收未签收
       PAY.CUSTOMER_CODE,
       PAY.CUSTOMER_NAME,
       PAY.CUSTOMER_TYPE,
       NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO),
       PAY.PAYABLE_NO,
       WO.ACCOUNT_DATE,
       PAY.BUSINESS_DATE,
       PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应付单,
       PAY.BILL_TYPE,
       WO.AMOUNT,
       PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --始发部门编码
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --始发部门名称
       PKG_STL_COMMON.INVOICE_MARK_ONE,
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --始发部门编码
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --始发部门名称
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --未签收      
       AND　RCV.BILL_TYPE IN  (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--到达应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--到达偏线代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达落地配代理应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应收02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE         --应付01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
        ;

     --44）退运费 到达部门申请 02退运费冲02到达应收未签收 --往来
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
       DEBIT_ORG_CODE,
       DEBIT_ORG_NAME,
       DEBIT_INVOICE_MARK,
       CREDIT_ORG_TYPE,
       DEBIT_ORG_TYPE)
       SELECT
       SYS_GUID(),
       P_PERIOD,
       PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_NPOD, --02退运费冲02到达应收未签收
       PAY.CUSTOMER_CODE,
       PAY.CUSTOMER_NAME,
       PAY.CUSTOMER_TYPE,
       NVL(PAY.WAYBILL_NO,PAY.SOURCE_BILL_NO),
       PAY.PAYABLE_NO,
       WO.ACCOUNT_DATE,
       PAY.BUSINESS_DATE,
       PKG_STL_COMMON.BILL_PARENT_TYPE__YF,--应付单,
       PAY.BILL_TYPE,
       WO.AMOUNT,
       PAY.PRODUCT_CODE,
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --到达部门编码
       DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --到达部门名称
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --始发部门编码
       DECODE(RCV.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --始发部门名称
       PKG_STL_COMMON.INVOICE_MARK_TWO,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
       PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
      FROM STL.T_STL_BILL_WRITEOFF WO
      INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO = WO.BEGIN_NO AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      INNER JOIN STL.T_STL_BILL_PAYABLE PAY ON PAY.PAYABLE_NO = WO.END_NO AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO
      WHERE
        WO.ACCOUNT_DATE >= P_BEGIN_DATE
       AND WO.ACCOUNT_DATE <P_END_DATE
       AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
       AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                        PKG_STL_COMMON.POD__POD_TYPE__POD,
                                        1,
                                        PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                        -1)),
                             0) POD
                    FROM STL.T_STL_POD T
                   WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                         AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --已签收      
       AND　RCV.BILL_TYPE IN  (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,--到达应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,--到达偏线代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
       PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --到达落地配代理应收
       AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应收02
       AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO         --应付01
       AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --应付退运费
        AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --到达部门
        ;
    RETURN TRUE;

  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        '退运费处理日明细' || '异常');

      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_RD;
/
