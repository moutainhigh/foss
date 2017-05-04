CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_LAND_DR IS

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

END PKG_STL_VCH_DAILY_LAND_DR;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_LAND_DR IS

  -----------------------------------------------------------------
  -- 凭证开单处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS
  BEGIN
    --1)预收落地配代理现金:按预收单的记账日期来统计，预收单的付款方式为：现金，预收单运输类型为：落地配代理
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
             PKG_STL_VCH_COMMON.LAND_DR_CH, --业务场景
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.CREATE_ORG_CODE, --始发部门编码
             DR.CREATE_ORG_NAME, --始发部门名称
             DR.CREATE_ORG_CODE, --到达部门编码
             DR.CREATE_ORG_NAME, --到达部门名称
             DR.DEPOSIT_RECEIVED_NO, --运单号
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
             PKG_STL_COMMON.PRODUCT_CODE_PKG --产品类型
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DR
       WHERE DR.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH --现金
             AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LS --落地配代理
             AND DR.ACCOUNT_DATE >= P_BEGIN_DATE
             AND DR.ACCOUNT_DATE < P_END_DATE;

    --2) 预收落地配代理银行:按预收单的记账日期来统计，预收单的付款方式为：银行卡、电汇、支票、网上支付，预收单运输类型为：落地配代理
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
             PKG_STL_VCH_COMMON.LAND_DR_CD, --业务场景
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.CREATE_ORG_CODE, --始发部门编码
             DR.CREATE_ORG_NAME, --始发部门名称
             DR.CREATE_ORG_CODE, --到达部门编码
             DR.CREATE_ORG_NAME, --到达部门名称
             DR.DEPOSIT_RECEIVED_NO, --运单号
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
             PKG_STL_COMMON.PRODUCT_CODE_PKG --产品类型
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DR
       WHERE DR.PAYMENT_TYPE IN (PKG_STL_COMMON.PAYMENT_TYPE_CARD, --银行卡
                                 PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER, --电汇
                                 PKG_STL_COMMON.PAYMENT_TYPE_NOTE, --支票
                                 PKG_STL_COMMON.PAYMENT_TYPE_ONLINE --网上支付
                                 )
             AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LS --落地配代理
             AND DR.ACCOUNT_DATE >= P_BEGIN_DATE
             AND DR.ACCOUNT_DATE < P_END_DATE;

    --3)预收落地配代理冲应收到付运费已签收:
    --按核销单的记账日期来统计，核销类型为：预收冲应收，关联判断核销时点应收单的签收状态为已签收，
    --应收单的单据子类型为应收、落地配代理应收，预收单运输类型为：落地配代理，
    --取核销明细的金额，反核销时生成的负数也统计
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
             PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_POD, --业务场景
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE) , --始发部门编码
             DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_NAME，RCV.ORIG_ORG_NAME), --始发部门名称
             DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_DEST_ORG_CODE，RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME), --到达部门名称
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
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品类型
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
                  PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --落地配代理应收
             AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LS --落地配代理
             AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
             AND WO.ACCOUNT_DATE < P_END_DATE;

    --4)预收落地配代理冲应收到付运费未签收
    --按核销单的记账日期来统计，核销类型为：预收冲应收，关联判断核销时点应收单的签收状态为未签收，
    --应收单的单据子类型为到达应收、落地配到达代理应收，预收单运输类型为：落地配代理，取核销明细的金
    --额，反核销时生成的负数也统计
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
             PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_NPOD, --业务场景
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE) , --始发部门编码
             DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_NAME，RCV.ORIG_ORG_NAME), --始发部门名称
             DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_DEST_ORG_CODE，RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME), --到达部门名称
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
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_PKG --产品类型
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
                  PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL) --落地配代理应收
             AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LS --落地配代理
             AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
             AND WO.ACCOUNT_DATE < P_END_DATE;

    --5）预收落地配代理冲其他应收
    --按核销单的记账日期来统计，核销类型为：预收冲应收，应收单的单据子类型为落地配其他应收，
    --预收单运输类型为：落地配代理，取核销明细的金额，反核销时生成的负数也统计
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
             PKG_STL_VCH_COMMON.LAND_DR_WO_OTHER_RCV, --业务场景
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE) , --始发部门编码
             DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_NAME，RCV.ORIG_ORG_NAME), --始发部门名称
             DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_DEST_ORG_CODE，RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME), --到达部门名称
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
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_PKG --产品类型
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
          ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
             AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
        JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
             AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --预收冲应收
             AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_LR --落地配其他应收
             AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LS --落地配代理
             AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
             AND WO.ACCOUNT_DATE < P_END_DATE;

    --6)预收落地配代理冲应收代收货款已签收
    --按核销单的记账日期来统计，核销类型为：预收冲应收，关联判断核销时点应收单的签收状态为已签收，
    --应收单的单据子类型为代收货款应收（产品类型为：包裹）、落地配代理代收货款应收，预收单运输类型为：落地配代理，
    --取核销明细的金额，反核销时生成的负数也统计
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
             PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_POD, --业务场景
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE) , --始发部门编码
             DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_NAME，RCV.ORIG_ORG_NAME), --始发部门名称
             DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_DEST_ORG_CODE，RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME), --到达部门名称
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
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_PKG --产品类型
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
                  PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DLC) --落地配代理代收货款应收
             AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LS --落地配代理
             AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
             AND WO.ACCOUNT_DATE < P_END_DATE;

    --7)预收落地配代理冲应收代收货款未签收  
    --按核销单的记账日期来统计，核销类型为：预收冲应收，关联判断核销时点应收单的签收状态为未签收，
    --应收单的单据子类型为代收货款应收（产品类型为：精准落地配）、落地配代理代收货款应收，预收单运输
    --类型为：落地配代理，取核销明细的金额，反核销时生成的负数也统计
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
             PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_NPOD, --业务场景
             RCV.CUSTOMER_CODE, --客户编码
             RCV.CUSTOMER_NAME, --客户名称
             RCV.CUSTOMER_TYPE, --客户类型
             DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE) , --始发部门编码
             DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_NAME，RCV.ORIG_ORG_NAME), --始发部门名称
             DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_DEST_ORG_CODE，RCV.DEST_ORG_CODE), --到达部门编码
             DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME), --到达部门名称
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
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             RCV.PRODUCT_CODE --产品类型
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
                  PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DLC) --落地配代理代收货款应收单
             AND DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LS --落地配代理
             AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
             AND WO.ACCOUNT_DATE < P_END_DATE;

    --8）落地配退预收付款申请： 
    --按付款单的记账日期来统计，关联里面的付款明细单据子类型为：预收单，预收单运输类型为：落地配代理，
    --统计对应的付款单明细金额之和
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
             PKG_STL_VCH_COMMON.LAND_DR_PAY_APPLY, --业务场景
             DR.CUSTOMER_CODE, --客户编码
             DR.CUSTOMER_NAME, --客户名称
             DR.CUSTOMER_TYPE, --客户类型
             DR.CREATE_ORG_CODE, --始发部门编码
             DR.CREATE_ORG_NAME, --始发部门名称
             DR.CREATE_ORG_CODE, --到达部门编码
             DR.CREATE_ORG_NAME, --到达部门名称
             DR.DEPOSIT_RECEIVED_NO, --运单号
             DR.DEPOSIT_RECEIVED_NO, --单据编号
             DR.ACCOUNT_DATE, --会计日期
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
             PKG_STL_COMMON.PRODUCT_CODE_PKG --产品类型
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DR
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON DP.SOURCE_BILL_NO = DR.DEPOSIT_RECEIVED_NO
        JOIN STL.T_STL_BILL_PAYMENT PT
          ON PT.PAYMENT_NO = DP.PAYMENT_NO
       WHERE DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LS --落地配代理
             AND PT.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PT.ACCOUNT_DATE < P_END_DATE;

    RETURN TRUE;

  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        '凭证开单处理日明细LAND_DR' || '异常');

      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_LAND_DR;
/
