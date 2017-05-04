CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_LAND_WO IS

  -- ==============================================================
  -- Author  : DDW
  -- Created : 2013-09-23
  -- Purpose : 营业部核销月报表明细
  -- ==============================================================

  -----------------------------------------------------------------
  -- 营业部核销月报表处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_DAILY_LAND_WO;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_LAND_WO IS

  -----------------------------------------------------------------
  -- 营业部核销月报表处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS
  BEGIN
    --营业部预收客户冲销快递应收
  
    --1)营业部预收客户  预收客户冲应收始发运费未签收
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
             PKG_STL_VCH_COMMON.CUST_DR_ORIG_LAND_RCV_NPOD, --业务场景,预收客户冲快递应收始发运费未签收
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DR.CREATE_ORG_CODE, --营业部门编码
             DR.CREATE_ORG_NAME, --营业部门名称
             RE.EXPRESS_ORIG_ORG_CODE, --快递部门编码
             RE.EXPRESS_ORIG_ORG_NAME, --快递部门名称
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
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
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
         AND RE.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
    --2)营业部预收客户  预收客户冲应收始发运费已签收
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
             PKG_STL_VCH_COMMON.CUST_DR_ORIG_LAND_RCV_POD, --业务场景,预收客户冲快递应收始发运费已签收
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DR.CREATE_ORG_CODE, --营业部门编码
             DR.CREATE_ORG_NAME, --营业部门名称
             RE.EXPRESS_ORIG_ORG_CODE, --快递部门编码
             RE.EXPRESS_ORIG_ORG_NAME, --快递部门名称,
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
             DECODE(RE.PRODUCT_CODE,
                    NULL,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
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
         AND RE.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
    --营业部理赔冲销快递应收
  
    --3) 理赔-出发部门申请-理赔冲始发应收已签收
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
             PKG_STL_VCH_COMMON.CLAIM_WO_ORIG_LAND_RCV_POD, --业务场景,理赔冲快递始发应收已签收
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             PAY.PAYABLE_ORG_CODE, --营业部门编码
             PAY.PAYABLE_ORG_NAME, --营业部门名称
             RCV.EXPRESS_ORIG_ORG_CODE, --快递部门编码
             RCV.EXPRESS_ORIG_ORG_NAME, --快递部门名称
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --运单号
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
         AND RCV.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
    --4) 理赔-出发部门申请-理赔冲始发应收未签收
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
             PKG_STL_VCH_COMMON.CLAIM_WO_ORIG_LAND_RCV_NPOD, --业务场景,理赔冲快递始发应收未签收
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             PAY.PAYABLE_ORG_CODE, --营业部门编码
             PAY.PAYABLE_ORG_NAME, --营业部门名称
             RCV.EXPRESS_ORIG_ORG_CODE, --快递部门编码
             RCV.EXPRESS_ORIG_ORG_NAME, --快递部门名称
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --运单号
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
         AND RCV.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
    --营业部冲销快递小票应收
  
    --5)小票应收核销  应付理赔冲小票应收
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
             PKG_STL_VCH_COMMON.OR_LAND_RCV_WO_CLAIM_PAY, --业务场景,应付理赔冲快递小票应收
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             PA.PAYABLE_ORG_CODE, --营业部门编码
             PA.PAYABLE_ORG_NAME, --营业部门名称
             RE.EXPRESS_ORIG_ORG_CODE, --快递部门编码
             RE.EXPRESS_ORIG_ORG_NAME, --快递部门名称
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
         AND RE.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
    --6)小票应收核销  预收客户冲小票应收
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
             PKG_STL_VCH_COMMON.OR_LAND_RCV_WO_CUST_DR, --业务场景,预收客户冲快递小票应收
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             DR.CREATE_ORG_CODE, --营业部门编码
             DR.CREATE_ORG_NAME, --营业部门名称
             RE.EXPRESS_ORIG_ORG_CODE, --快递部门编码
             RE.EXPRESS_ORIG_ORG_NAME, --快递部门名称
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
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
         AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RE
          ON RE.RECEIVABLE_NO = WO.END_NO
         AND RE.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
         AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE --来源于小票
         AND RE.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
    --营业部退运费冲销快递应收
  
    --7) 退运费-出发部门申请-退运费冲始发应收已签收
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
             PKG_STL_VCH_COMMON.REFUND_WO_ORIG_LAND_RCV_POD, --业务场景,退运费冲快递始发应收已签收
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             PAY.PAYABLE_ORG_CODE, --营业部门编码
             PAY.PAYABLE_ORG_NAME, --营业部门名称
             RCV.EXPRESS_ORIG_ORG_CODE, --快递部门编码
             RCV.EXPRESS_ORIG_ORG_NAME, --快递部门名称
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --运单号
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
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --退运费
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --始发
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
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
         AND RCV.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
    --8) 退运费-出发部门申请-退运费冲始发应收未签收
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
             PKG_STL_VCH_COMMON.REFUND_WO_ORIG_LAND_RCV_NPOD, --业务场景,退运费冲快递始发应收未签收
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             PAY.PAYABLE_ORG_CODE, --营业部门编码
             PAY.PAYABLE_ORG_NAME, --营业部门名称
             RCV.EXPRESS_ORIG_ORG_CODE, --快递部门编码
             RCV.EXPRESS_ORIG_ORG_NAME, --快递部门名称
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --运单号
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
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --退运费
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --始发
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
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
         AND RCV.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
    --快递理赔冲销营业部应收
  
    --9) 理赔-出发部门申请-理赔冲始发应收已签收
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
             PKG_STL_VCH_COMMON.LAND_CLAIM_WO_ORIG_RCV_POD, --业务场景,快递理赔冲始发应收已签收
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             RCV.ORIG_ORG_CODE, --营业部门编码
             RCV.ORIG_ORG_NAME, --营业部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PAY.PAYABLE_ORG_CODE,
                           PAY.ORIG_ORG_CODE,
                           PAY.EXPRESS_ORIG_ORG_CODE,
                           PAY.DEST_ORG_CODE,
                           PAY.EXPRESS_DEST_ORG_CODE，PAY.PAYABLE_ORG_CODE),
                    PAY.PAYABLE_ORG_CODE) AS DEST_ORG_CODE, --快递部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PAY.PAYABLE_ORG_NAME,
                           PAY.ORIG_ORG_NAME,
                           PAY.EXPRESS_ORIG_ORG_NAME,
                           PAY.DEST_ORG_NAME,
                           PAY.EXPRESS_DEST_ORG_NAME，PAY.PAYABLE_ORG_NAME),
                    PAY.PAYABLE_ORG_NAME) AS DEST_ORG_NAME, --快递部门名称
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --运单号
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
         AND PAY.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
    --10) 理赔-出发部门申请-理赔冲始发应收未签收
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
             PKG_STL_VCH_COMMON.LAND_CLAIM_WO_ORIG_RCV_NPOD, --业务场景,快递理赔冲始发应收未签收
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             RCV.ORIG_ORG_CODE, --营业部门编码
             RCV.ORIG_ORG_NAME, --营业部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PAY.PAYABLE_ORG_CODE,
                           PAY.ORIG_ORG_CODE,
                           PAY.EXPRESS_ORIG_ORG_CODE,
                           PAY.DEST_ORG_CODE,
                           PAY.EXPRESS_DEST_ORG_CODE，PAY.PAYABLE_ORG_CODE),
                    PAY.PAYABLE_ORG_CODE) AS DEST_ORG_CODE, --快递部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PAY.PAYABLE_ORG_NAME,
                           PAY.ORIG_ORG_NAME,
                           PAY.EXPRESS_ORIG_ORG_NAME,
                           PAY.DEST_ORG_NAME,
                           PAY.EXPRESS_DEST_ORG_NAME，PAY.PAYABLE_ORG_NAME),
                    PAY.PAYABLE_ORG_NAME) AS DEST_ORG_NAME, --快递部门名称
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --运单号
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
         AND PAY.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
    --快递冲销营业部应收小票
  
    --11)小票应收核销  应付理赔冲小票应收
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
             PKG_STL_VCH_COMMON.OR_RCV_WO_LAND_CLAIM_PAY, --业务场景,快递应付理赔冲小票应收
             RE.CUSTOMER_CODE, --客户编码
             RE.CUSTOMER_NAME, --客户名称
             RE.CUSTOMER_TYPE, --客户类型
             RE.ORIG_ORG_CODE, --营业部门编码
             RE.ORIG_ORG_NAME, --营业部门名称
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PA.PAYABLE_ORG_CODE,
                           PA.ORIG_ORG_CODE,
                           PA.EXPRESS_ORIG_ORG_CODE,
                           PA.DEST_ORG_CODE,
                           PA.EXPRESS_DEST_ORG_CODE，PA.PAYABLE_ORG_CODE),
                    PA.PAYABLE_ORG_CODE) AS DEST_ORG_CODE, --快递部门编码
             DECODE(PA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PA.PAYABLE_ORG_NAME,
                           PA.ORIG_ORG_NAME,
                           PA.EXPRESS_ORIG_ORG_NAME,
                           PA.DEST_ORG_NAME,
                           PA.EXPRESS_DEST_ORG_NAME，PA.PAYABLE_ORG_NAME),
                    PA.PAYABLE_ORG_NAME) AS DEST_ORG_NAME, --快递部门名称
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
         AND PA.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
    --快递退运费冲销营业部应收
  
    --12) 退运费-出发部门申请-退运费冲始发应收已签收
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
             PKG_STL_VCH_COMMON.LAND_REFUND_WO_ORIG_RCV_POD, --业务场景,快递退运费冲始发应收已签收
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             RCV.ORIG_ORG_CODE, --营业部门编码
             RCV.ORIG_ORG_NAME, --营业部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PAY.PAYABLE_ORG_CODE,
                           PAY.ORIG_ORG_CODE,
                           PAY.EXPRESS_ORIG_ORG_CODE,
                           PAY.DEST_ORG_CODE,
                           PAY.EXPRESS_DEST_ORG_CODE，PAY.PAYABLE_ORG_CODE),
                    PAY.PAYABLE_ORG_CODE) AS DEST_ORG_CODE, --快递部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PAY.PAYABLE_ORG_NAME,
                           PAY.ORIG_ORG_NAME,
                           PAY.EXPRESS_ORIG_ORG_NAME,
                           PAY.DEST_ORG_NAME,
                           PAY.EXPRESS_DEST_ORG_NAME，PAY.PAYABLE_ORG_NAME),
                    PAY.PAYABLE_ORG_NAME) AS DEST_ORG_NAME, --快递部门名称
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --运单号
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
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --退运费
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --始发
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
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
         AND PAY.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
    --13) 退运费-出发部门申请-退运费冲始发应收未签收
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
             PKG_STL_VCH_COMMON.LAND_REFUND_WO_ORIG_RCV_NPOD, --业务场景,快递退运费冲始发应收未签收
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             RCV.ORIG_ORG_CODE, --营业部门编码
             RCV.ORIG_ORG_NAME, --营业部门名称
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PAY.PAYABLE_ORG_CODE,
                           PAY.ORIG_ORG_CODE,
                           PAY.EXPRESS_ORIG_ORG_CODE,
                           PAY.DEST_ORG_CODE,
                           PAY.EXPRESS_DEST_ORG_CODE，PAY.PAYABLE_ORG_CODE),
                    PAY.PAYABLE_ORG_CODE) AS DEST_ORG_CODE, --快递部门编码
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PAY.PAYABLE_ORG_NAME,
                           PAY.ORIG_ORG_NAME,
                           PAY.EXPRESS_ORIG_ORG_NAME,
                           PAY.DEST_ORG_NAME,
                           PAY.EXPRESS_DEST_ORG_NAME，PAY.PAYABLE_ORG_NAME),
                    PAY.PAYABLE_ORG_NAME) AS DEST_ORG_NAME, --快递部门名称
             DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
                    'N/A',
                    RCV.SOURCE_BILL_NO,
                    RCV.WAYBILL_NO), --运单号
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
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND --退运费
         AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --始发
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发应收
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
         AND PAY.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
    COMMIT;
  
    RETURN TRUE;
  
  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        '营业部核销月报表处理日明细' || '异常');
    
      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_LAND_WO;
/
