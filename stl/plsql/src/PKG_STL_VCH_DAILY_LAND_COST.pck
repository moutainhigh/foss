CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_LAND_COST IS

  -- ==============================================================
  -- Author  : MJQ
  -- Created : 2013-07-25
  -- Purpose : 每日凭证开单
  -- ==============================================================

  -----------------------------------------------------------------
  -- 凭证开单处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_DAILY_LAND_COST;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_LAND_COST IS

  -----------------------------------------------------------------
  -- 凭证开单处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS
  BEGIN
    --1)落地配代理应付生成
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
             PKG_STL_VCH_COMMON.LAND_COST_AGENCY_GEN, --业务场景
             PA.CUSTOMER_CODE, --客户编码
             PA.CUSTOMER_NAME, --客户名称
             PA.CUSTOMER_TYPE, --客户类型
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_ORIG_ORG_CODE，PA.ORIG_ORG_CODE), --始发部门编码
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_ORIG_ORG_NAME，PA.ORIG_ORG_NAME), --始发部门名称
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_DEST_ORG_CODE，PA.DEST_ORG_CODE), --到达部门编码
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_DEST_ORG_NAME，PA.DEST_ORG_NAME), --到达部门名称
             decode(nvl(PA.waybill_no, 'N/A'), 'N/A', pa.source_bill_no, pa.waybill_no), --运单号
             PA.PAYABLE_NO, --单据编号
             PA.ACCOUNT_DATE, --会计日期
             PA.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PA.BILL_TYPE, --单据子类型
             PA.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_PKG) --产品类型
        FROM STL.T_STL_BILL_PAYABLE PA
       WHERE PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_LANDSTOWAGE --落地配外发成本
             AND PA.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PA.ACCOUNT_DATE < P_END_DATE;

    --2)落地配代理应付成本确认：按签收表中的签收时间来统计，取关联运单生成的空运到达代理应付单金额
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
             PKG_STL_VCH_COMMON.LAND_COST_AGENCY_CFM, --业务场景
             PA.CUSTOMER_CODE, --客户编码
             PA.CUSTOMER_NAME, --客户名称
             PA.CUSTOMER_TYPE, --客户类型
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_ORIG_ORG_CODE，PA.ORIG_ORG_CODE), --始发部门编码
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_ORIG_ORG_NAME，PA.ORIG_ORG_NAME), --始发部门名称
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_DEST_ORG_CODE，PA.DEST_ORG_CODE), --到达部门编码
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_DEST_ORG_NAME，PA.DEST_ORG_NAME), --到达部门名称
             decode(nvl(PA.waybill_no, 'N/A'), 'N/A', pa.source_bill_no, pa.waybill_no), --运单号
             PA.PAYABLE_NO, --单据编号
             PA.ACCOUNT_DATE, --会计日期
             PA.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PA.BILL_TYPE, --单据子类型
             PA.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_PKG) --产品类型
        FROM STL.T_STL_BILL_PAYABLE PA
        JOIN STL.T_STL_POD POD
          ON POD.WAYBILL_NO = PA.WAYBILL_NO
             AND POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__POD
       WHERE POD.POD_DATE >= P_BEGIN_DATE
             AND POD.POD_DATE < P_END_DATE
             AND PA.ACCOUNT_DATE < POD.POD_DATE
             AND
             PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_LANDSTOWAGE; --落地配外发成本

    --3)落地配代理应付成本反确认：按签收表中的反签收时间来统计，取关联运单生成的空运到达代理应付单金额
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
             PKG_STL_VCH_COMMON.LAND_COST_AGENCY_NCFM, --业务场景
             PA.CUSTOMER_CODE, --客户编码
             PA.CUSTOMER_NAME, --客户名称
             PA.CUSTOMER_TYPE, --客户类型
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_ORIG_ORG_CODE，PA.ORIG_ORG_CODE), --始发部门编码
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_ORIG_ORG_NAME，PA.ORIG_ORG_NAME), --始发部门名称
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_DEST_ORG_CODE，PA.DEST_ORG_CODE), --到达部门编码
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_DEST_ORG_NAME，PA.DEST_ORG_NAME), --到达部门名称
             decode(nvl(PA.waybill_no, 'N/A'), 'N/A', pa.source_bill_no, pa.waybill_no), --运单号
             PA.PAYABLE_NO, --单据编号
             PA.ACCOUNT_DATE, --会计日期
             PA.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PA.BILL_TYPE, --单据子类型
             PA.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_PKG) --产品类型
        FROM STL.T_STL_BILL_PAYABLE PA
        JOIN STL.T_STL_POD POD
          ON POD.WAYBILL_NO = PA.WAYBILL_NO
             AND POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__UPD
       WHERE POD.POD_DATE >= P_BEGIN_DATE
             AND POD.POD_DATE < P_END_DATE
             AND PA.ACCOUNT_DATE < POD.POD_DATE
             AND
             PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_LANDSTOWAGE; --落地配外发成本

    --4)其它应付成本确认：按空运其他应付单的记账日期来统计，取空运其他应付单的金额
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
             PKG_STL_VCH_COMMON.LAND_COST_OTHER_CONFIRM, --业务场景
             PA.CUSTOMER_CODE, --客户编码
             PA.CUSTOMER_NAME, --客户名称
             PA.CUSTOMER_TYPE, --客户类型
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_ORIG_ORG_CODE，PA.ORIG_ORG_CODE), --始发部门编码
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_ORIG_ORG_NAME，PA.ORIG_ORG_NAME), --始发部门名称
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_DEST_ORG_CODE，PA.DEST_ORG_CODE), --到达部门编码
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_DEST_ORG_NAME，PA.DEST_ORG_NAME), --到达部门名称
             decode(nvl(PA.waybill_no, 'N/A'), 'N/A', PA.source_bill_no, PA.waybill_no), --运单号
             PA.Payable_No, --单据编号
             PA.ACCOUNT_DATE, --会计日期
             PA.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PA.BILL_TYPE, --单据子类型
             PA.AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_PKG) --产品类型
        FROM STL.T_STL_BILL_PAYABLE PA
       WHERE PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILLTYPE_LAND_OTHER --落地配其他应付
             AND PA.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PA.ACCOUNT_DATE < P_END_DATE;

    --5）落地配成本付款申请
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
             PKG_STL_VCH_COMMON.LAND_COST_PAY_APPLY, --业务场景
             PA.CUSTOMER_CODE, --客户编码
             PA.CUSTOMER_NAME, --客户名称
             PA.CUSTOMER_TYPE, --客户类型
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_ORIG_ORG_CODE，PA.ORIG_ORG_CODE), --始发部门编码
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_ORIG_ORG_NAME，PA.ORIG_ORG_NAME), --始发部门名称
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_DEST_ORG_CODE，PA.DEST_ORG_CODE), --到达部门编码
             DECODE(PA.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,PA.EXPRESS_DEST_ORG_NAME，PA.DEST_ORG_NAME), --到达部门名称
             decode(nvl(PA.waybill_no, 'N/A'), 'N/A', PA.source_bill_no, PA.waybill_no), --运单号
             PA.PAYABLE_NO, --单据编号
             PA.ACCOUNT_DATE, --会计日期
             PA.BUSINESS_DATE, --业务日期
             PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
             PA.BILL_TYPE, --单据子类型
             DECODE(PMT.IS_RED_BACK,
                    PKG_STL_COMMON.IS_RED_BACK_YES,
                    -1,
                    PKG_STL_COMMON.IS_RED_BACK_NO,
                    1) * PMTD.PAY_AMOUNT, --金额
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_PKG) --产品类型
        FROM STL.T_STL_BILL_PAYMENT PMT
        JOIN STL.T_STL_BILL_PAYMENT_D PMTD
          ON PMTD.PAYMENT_NO = PMT.PAYMENT_NO
        JOIN STL.T_STL_BILL_PAYABLE PA
          ON PMTD.SOURCE_BILL_NO = PA.PAYABLE_NO
       WHERE PA.BILL_TYPE IN (PKG_STL_COMMON.PAYABLE_BILL_TYPE_LANDSTOWAGE, --落地配外发成本
                               PKG_STL_COMMON.PAYABLE_BILLTYPE_LAND_OTHER --落地配其他应付
                               )
             AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PMT.ACCOUNT_DATE < P_END_DATE
             AND PMTD.PAY_AMOUNT <> 0;

    RETURN TRUE;

  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        '凭证开单处理日明细LAND_COST' || '异常');

      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_LAND_COST;
/
