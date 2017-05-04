CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_AIR_PR IS

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

END PKG_STL_VCH_DAILY_AIR_PR;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_AIR_PR IS

	-----------------------------------------------------------------
	-- 凭证开单处理日明细
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
																		 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
																		 P_END_DATE   DATE -- 结束时间 2012-12-22
																		 ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
	 IS
	BEGIN
		--1)应付到达代理成本冲应收到付运费已签收:
		--按核销单的记账日期来统计，关联判断核销时点应收单的签收状态为已签收，应收单的单据子类型为到达应收
		--（产品类型为：精准空运）、空运到达代理应收，应付单的单据子类型为：空运到达代理应付，取核销明细的
		--金额，反核销时生成的负数也统计
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
						 PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_POD, --业务场景
						 RCV.CUSTOMER_CODE, --客户编码
						 RCV.CUSTOMER_NAME, --客户名称
						 RCV.CUSTOMER_TYPE, --客户类型
						 RCV.ORIG_ORG_CODE, --始发部门编码
						 RCV.ORIG_ORG_NAME, --始发部门名称
						 RCV.DEST_ORG_CODE, --到达部门编码
						 RCV.DEST_ORG_NAME, --到达部门名称
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
				JOIN STL.T_STL_BILL_PAYABLE PA ON PA.PAYABLE_NO = WO.END_NO
																					AND PA.IS_RED_BACK =
																					PKG_STL_COMMON.IS_RED_BACK_NO
				JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO =
																							WO.BEGIN_NO
																							AND
																							RCV.IS_RED_BACK =
																							PKG_STL_COMMON.IS_RED_BACK_NO
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
						 AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
																				PKG_STL_COMMON.POD__POD_TYPE__POD,
																				1,
																				PKG_STL_COMMON.POD__POD_TYPE__UPD,
																				-1)),
														 0) POD
										FROM STL.T_STL_POD T
									 WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --核销时已签收
						 AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --到达应收
							PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL)--落地配到达代理应收  
						 AND
						 PA.BILL_TYPE IN( PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY, --空运到达代理应付
						                  PKG_STL_COMMON.PAYABLE_BILL_TYPE_LANDSTOWAGE)--落地配代理应付
             AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;

		--2)应付到达代理成本冲应收到付运费未签收:
		--按核销单的记账日期来统计，关联判断核销时点应收单的签收状态为未签收，应收单的单据子类型为到达应收
		--（产品类型为：精准空运）、空运到达代理应收，应付单的单据子类型为：空运到达代理应付，取核销明细的
		--金额，反核销时生成的负数也统计
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
						 PKG_STL_VCH_COMMON.AIR_PR_AGENCY_WO_DEST_RCV_NPOD, --业务场景
						 RCV.CUSTOMER_CODE, --客户编码
						 RCV.CUSTOMER_NAME, --客户名称
						 RCV.CUSTOMER_TYPE, --客户类型
						 RCV.ORIG_ORG_CODE, --始发部门编码
						 RCV.ORIG_ORG_NAME, --始发部门名称
						 RCV.DEST_ORG_CODE, --到达部门编码
						 RCV.DEST_ORG_NAME, --到达部门名称
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
				JOIN STL.T_STL_BILL_PAYABLE PA ON PA.PAYABLE_NO = WO.END_NO
																					AND PA.IS_RED_BACK =
																					PKG_STL_COMMON.IS_RED_BACK_NO
				JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO =
																							WO.BEGIN_NO
																							AND
																							RCV.IS_RED_BACK =
																							PKG_STL_COMMON.IS_RED_BACK_NO
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
						 AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
																				PKG_STL_COMMON.POD__POD_TYPE__POD,
																				1,
																				PKG_STL_COMMON.POD__POD_TYPE__UPD,
																				-1)),
														 0) POD
										FROM STL.T_STL_POD T
									 WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --核销时未签收
						 AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --到达应收
							PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL)--落地配到达代理应收  
						 AND
						 PA.BILL_TYPE IN( PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY, --空运到达代理应付
						                  PKG_STL_COMMON.PAYABLE_BILL_TYPE_LANDSTOWAGE)--落地配代理应付
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;

		--3）其他应付冲应收到付运费已签收：
		--按核销单的记账日期来统计，关联判断核销时点应收单的签收状态为已签收，应收单的单据子类型为到达
		--应收（产品类型为：精准空运）、空运到达代理应收，应付单的单据子类型为：空运其他应付，取核销明
		--细的金额，反核销时生成的负数也统计
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
						 PKG_STL_VCH_COMMON.AIR_PR_OT_WO_DEST_RCV_POD, --业务场景
						 RCV.CUSTOMER_CODE, --客户编码
						 RCV.CUSTOMER_NAME, --客户名称
						 RCV.CUSTOMER_TYPE, --客户类型
						 RCV.ORIG_ORG_CODE, --始发部门编码
						 RCV.ORIG_ORG_NAME, --始发部门名称
						 RCV.DEST_ORG_CODE, --到达部门编码
						 RCV.DEST_ORG_NAME, --到达部门名称
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
				JOIN STL.T_STL_BILL_PAYABLE PA ON PA.PAYABLE_NO = WO.END_NO
																					AND PA.IS_RED_BACK =
																					PKG_STL_COMMON.IS_RED_BACK_NO
				JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO =
																							WO.BEGIN_NO
																							AND
																							RCV.IS_RED_BACK =
																							PKG_STL_COMMON.IS_RED_BACK_NO
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
						 AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
																				PKG_STL_COMMON.POD__POD_TYPE__POD,
																				1,
																				PKG_STL_COMMON.POD__POD_TYPE__UPD,
																				-1)),
														 0) POD
										FROM STL.T_STL_POD T
									 WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --核销时已签收
						 AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --到达应收
							PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--空运到达代理应收
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL)--落地配到达代理应收  
						 AND PA.BILL_TYPE IN( PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER,--空运其他应付
                                  PKG_STL_COMMON.PAYABLE_BILLTYPE_LAND_OTHER)--落地配其它应付 
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;

		--4）其他应付冲应收到付运费未签收：
		--按核销单的记账日期来统计，关联判断核销时点应收单的签收状态为未签收，应收单的单据子类型为到达
		--应收（产品类型为：精准空运）、空运到达代理应收，应付单的单据子类型为：空运其他应付，取核销明
		--细的金额，反核销时生成的负数也统计
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
						 PKG_STL_VCH_COMMON.AIR_PR_OTH_WO_DEST_RCV_NPOD, --业务场景
						 RCV.CUSTOMER_CODE, --客户编码
						 RCV.CUSTOMER_NAME, --客户名称
						 RCV.CUSTOMER_TYPE, --客户类型
						 RCV.ORIG_ORG_CODE, --始发部门编码
						 RCV.ORIG_ORG_NAME, --始发部门名称
						 RCV.DEST_ORG_CODE, --到达部门编码
						 RCV.DEST_ORG_NAME, --到达部门名称
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
				JOIN STL.T_STL_BILL_PAYABLE PA ON PA.PAYABLE_NO = WO.END_NO
																					AND PA.IS_RED_BACK =
																					PKG_STL_COMMON.IS_RED_BACK_NO
				JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO =
																							WO.BEGIN_NO
																							AND
																							RCV.IS_RED_BACK =
																							PKG_STL_COMMON.IS_RED_BACK_NO
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
						 AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
																				PKG_STL_COMMON.POD__POD_TYPE__POD,
																				1,
																				PKG_STL_COMMON.POD__POD_TYPE__UPD,
																				-1)),
														 0) POD
										FROM STL.T_STL_POD T
									 WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --核销时未签收
						 AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --到达应收
							PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA, --空运到达代理应收
              PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL)--落地配到达代理应收  
						 AND PA.BILL_TYPE IN( PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER,--空运其他应付
                                  PKG_STL_COMMON.PAYABLE_BILLTYPE_LAND_OTHER)--落地配其它应付 
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;

		--5)其他应付冲其他应收
		--按核销单的记账日期来统计，核销类型为：应付冲应收，其中应收单的单据子类型为空运其他应收，
		--应付单的单据子类型为：空运其他应付，取核销明细的金额，反核销时生成的负数也统计
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
						 PKG_STL_VCH_COMMON.AIR_PR_OTH_WO_OTH_RCV, --业务场景
						 RCV.CUSTOMER_CODE, --客户编码
						 RCV.CUSTOMER_NAME, --客户名称
						 RCV.CUSTOMER_TYPE, --客户类型
						 RCV.ORIG_ORG_CODE, --始发部门编码
						 RCV.ORIG_ORG_NAME, --始发部门名称
						 RCV.DEST_ORG_CODE, --到达部门编码
						 RCV.DEST_ORG_NAME, --到达部门名称
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
				JOIN STL.T_STL_BILL_PAYABLE PA ON PA.PAYABLE_NO = WO.END_NO
																					AND PA.IS_RED_BACK =
																					PKG_STL_COMMON.IS_RED_BACK_NO
				JOIN STL.T_STL_BILL_RECEIVABLE RCV ON RCV.RECEIVABLE_NO =
																							WO.BEGIN_NO
																							AND
																							RCV.IS_RED_BACK =
																							PKG_STL_COMMON.IS_RED_BACK_NO
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --应收冲应付
						 AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AR --空运其他应收
						 AND PA.BILL_TYPE IN (PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER,
							PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY,
							PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_ORIGINAL,
							PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR)
						--空运其他应付  空运到达代理应付 航空公司运费 空运出发代理应付
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

END PKG_STL_VCH_DAILY_AIR_PR;
/
