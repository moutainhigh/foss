CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_UPD IS

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

END PKG_STL_VCH_DAILY_UPD;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_UPD IS

	-----------------------------------------------------------------
	-- 凭证开单处理日明细
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
																		 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
																		 P_END_DATE   DATE -- 结束时间 2012-12-22
																		 ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
	 IS
	BEGIN
	
		--1)反签收运单——反签收时现付已收款金额
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
						 PKG_STL_VCH_COMMON.UPD_CASH_COLLECTED, --业务场景
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
						 CASH.WAYBILL_NO, --运单号
						 CASH.CASH_COLLECTION_NO, --单据编号
						 CASH.ACCOUNT_DATE, --会计日期
						 CASH.BUSINESS_DATE, --业务日期
						 PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --单据类型
						 CASH.BILL_TYPE, --单据子类型
						 CASH.AMOUNT, --金额
						 CASH.TRANSPORT_FEE, --运费
						 CASH.PICKUP_FEE, --接货费
						 CASH.DELIVERY_GOODS_FEE, --送货费
						 CASH.PACKAGING_FEE, --包装费
						 CASH.INSURANCE_FEE, --保价费
						 CASH.COD_FEE, --代收货款手续费
						 CASH.OTHER_FEE, --其它费
						 CASH.PRODUCT_CODE --产品类型
				FROM STL.T_STL_POD POD --签收记录表
				JOIN STL.T_STL_BILL_CASH_COLLECTION CASH
					ON CASH.WAYBILL_NO = POD.WAYBILL_NO --现金收款单
			 WHERE POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__UPD --反签收
						 AND CASH.SOURCE_BILL_TYPE =
						 PKG_STL_COMMON.CASH_COLL_SOURCE_BILL_TYPE_W --来源于运单
						 AND POD.POD_DATE >= P_BEGIN_DATE
						 AND POD.POD_DATE < P_END_DATE
						 AND CASH.ACCOUNT_DATE <= POD.POD_DATE; --收款小于反签收时间
	
		--2)反签收运单——反签收时始发应收已核销金额 
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
						 PKG_STL_VCH_COMMON.UPD_ORIG_RCV_WO, --业务场景
						 CUSTOMER_CODE, --客户编码
						 CUSTOMER_NAME, --客户名称
						 CUSTOMER_TYPE, --客户类型
						 ORIG_ORG_CODE, --始发部门编码 
						 ORIG_ORG_NAME, --始发部门名称
						 DEST_ORG_CODE, --到达部门编码
						 DEST_ORG_NAME, --到达部门名称
						 WAYBILL_NO, --运单号
						 RECEIVABLE_NO, --单据编号
						 ACCOUNT_DATE, --会计日期
						 BUSINESS_DATE, --业务日期
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
						 BILL_TYPE, --单据子类型
						 WO_AMOUNT, --金额
						 ROUND(TRANSPORT_FEE * WO_AMOUNT / RCV_AMOUNT), --运费
						 ROUND(PICKUP_FEE * WO_AMOUNT / RCV_AMOUNT), --接货费
						 ROUND(DELIVERY_GOODS_FEE * WO_AMOUNT / RCV_AMOUNT), --送货费
						 ROUND(PACKAGING_FEE * WO_AMOUNT / RCV_AMOUNT), --包装费
						 ROUND(INSURANCE_FEE * WO_AMOUNT / RCV_AMOUNT), --保价费
						 ROUND(COD_FEE * WO_AMOUNT / RCV_AMOUNT), --代收货款手续费
						 WO_AMOUNT - (ROUND(TRANSPORT_FEE * WO_AMOUNT / RCV_AMOUNT) +
						 ROUND(PICKUP_FEE * WO_AMOUNT / RCV_AMOUNT) +
						 ROUND(DELIVERY_GOODS_FEE * WO_AMOUNT / RCV_AMOUNT) +
						 ROUND(PACKAGING_FEE * WO_AMOUNT / RCV_AMOUNT) +
						 ROUND(INSURANCE_FEE * WO_AMOUNT / RCV_AMOUNT) +
						 ROUND(COD_FEE * WO_AMOUNT / RCV_AMOUNT)), --其它费
						 PRODUCT_CODE --产品类型 
				FROM (SELECT RCV.CUSTOMER_CODE, --客户编码
										 RCV.CUSTOMER_NAME, --客户名称
										 RCV.CUSTOMER_TYPE, --客户类型
										 DECODE(RCV.PRODUCT_CODE,
														PKG_STL_COMMON.PRODUCT_CODE_PKG,
														RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE) ORIG_ORG_CODE, --始发部门编码 
										 DECODE(RCV.PRODUCT_CODE,
														PKG_STL_COMMON.PRODUCT_CODE_PKG,
														RCV.EXPRESS_ORIG_ORG_NAME，RCV.ORIG_ORG_NAME) ORIG_ORG_NAME, --始发部门名称
										 DECODE(RCV.PRODUCT_CODE,
														PKG_STL_COMMON.PRODUCT_CODE_PKG,
														RCV.EXPRESS_DEST_ORG_CODE，RCV.DEST_ORG_CODE) DEST_ORG_CODE, --到达部门编码
										 DECODE(RCV.PRODUCT_CODE,
														PKG_STL_COMMON.PRODUCT_CODE_PKG,
														RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME) DEST_ORG_NAME, --到达部门名称
										 RCV.WAYBILL_NO, --运单号
										 RCV.RECEIVABLE_NO RECEIVABLE_NO, --单据编号
										 MAX(RCV.ACCOUNT_DATE) ACCOUNT_DATE, --会计日期
										 MIN(RCV.BUSINESS_DATE) BUSINESS_DATE, --业务日期
										 RCV.BILL_TYPE, --单据子类型
										 MAX(RCV.AMOUNT) RCV_AMOUNT, --金额
										 SUM(WO.AMOUNT) WO_AMOUNT,
										 MAX(RCV.TRANSPORT_FEE) TRANSPORT_FEE, --运费
										 MAX(RCV.PICKUP_FEE) PICKUP_FEE, --接货费
										 MAX(RCV.DELIVERY_GOODS_FEE) DELIVERY_GOODS_FEE, --送货费
										 MAX(RCV.PACKAGING_FEE) PACKAGING_FEE, --包装费
										 MAX(RCV.INSURANCE_FEE) INSURANCE_FEE, --保价费
										 MAX(RCV.COD_FEE) COD_FEE, --代收货款手续费
										 MAX(RCV.OTHER_FEE) OTHER_FEE, --其它费
										 RCV.PRODUCT_CODE --产品类型
								FROM STL.T_STL_POD POD
								JOIN STL.T_STL_BILL_RECEIVABLE RCV
									ON RCV.WAYBILL_NO = POD.WAYBILL_NO
										 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
								JOIN STL.T_STL_BILL_WRITEOFF WO
									ON (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
										 WO.END_NO = RCV.RECEIVABLE_NO)
							 WHERE POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__UPD --反签收
										 AND RCV.SOURCE_BILL_TYPE =
										 PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
										 AND RCV.BILL_TYPE =
										 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --始发
										 AND WO.ACCOUNT_DATE <= POD.POD_DATE --核销时间于小反签收时间
										 AND POD.POD_DATE >= P_BEGIN_DATE
										 AND POD.POD_DATE < P_END_DATE
							 GROUP BY RCV.CUSTOMER_CODE, --客户编码
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
															 RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME), --到达部门名称
												RCV.WAYBILL_NO, --运单号
												RCV.RECEIVABLE_NO, --单据编号
												RCV.BILL_TYPE, --单据子类型
												RCV.PRODUCT_CODE, --产品类型
												POD.ID)
			 WHERE WO_AMOUNT <> 0
						 AND RCV_AMOUNT <> 0;
	
		--3)反签收运单——反签收时始发应收未核销金额
		DECLARE
		
			CURSOR CUR_POD IS
				SELECT *
					FROM STL.T_STL_POD POD
				 WHERE POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__UPD
							 AND POD.POD_DATE >= P_BEGIN_DATE
							 AND POD.POD_DATE < P_END_DATE;
		
			V_POD_ROW STL.T_STL_POD%ROWTYPE;
		
		BEGIN
		
			FOR V_POD_ROW IN CUR_POD LOOP
			
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
								 PKG_STL_VCH_COMMON.UPD_ORIG_RCV_NWO, --业务场景
								 CUSTOMER_CODE, --客户编码
								 CUSTOMER_NAME, --客户名称
								 CUSTOMER_TYPE, --客户类型
								 ORIG_ORG_CODE, --始发部门编码 
								 ORIG_ORG_NAME, --始发部门名称
								 DEST_ORG_CODE, --到达部门编码
								 DEST_ORG_NAME, --到达部门名称
								 WAYBILL_NO, --运单号
								 RECEIVABLE_NO, --单据编号
								 ACCOUNT_DATE, --会计日期
								 BUSINESS_DATE, --业务日期
								 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
								 BILL_TYPE, --单据子类型
								 RCV_AMOUNT - WO_AMOUNT, --金额
								 TRANSPORT_FEE -
								 ROUND(TRANSPORT_FEE * WO_AMOUNT / RCV_AMOUNT), --运费
								 PICKUP_FEE - ROUND(PICKUP_FEE * WO_AMOUNT / RCV_AMOUNT), --接货费
								 DELIVERY_GOODS_FEE -
								 ROUND(DELIVERY_GOODS_FEE * WO_AMOUNT / RCV_AMOUNT), --送货费
								 PACKAGING_FEE -
								 ROUND(PACKAGING_FEE * WO_AMOUNT / RCV_AMOUNT), --包装费
								 INSURANCE_FEE -
								 ROUND(INSURANCE_FEE * WO_AMOUNT / RCV_AMOUNT), --保价费
								 COD_FEE - ROUND(COD_FEE * WO_AMOUNT / RCV_AMOUNT), --代收货款手续费
								 RCV_AMOUNT - WO_AMOUNT -
								 (TRANSPORT_FEE -
								 ROUND(TRANSPORT_FEE * WO_AMOUNT / RCV_AMOUNT) +
								 PICKUP_FEE - ROUND(PICKUP_FEE * WO_AMOUNT / RCV_AMOUNT) +
								 DELIVERY_GOODS_FEE -
								 ROUND(DELIVERY_GOODS_FEE * WO_AMOUNT / RCV_AMOUNT) +
								 PACKAGING_FEE -
								 ROUND(PACKAGING_FEE * WO_AMOUNT / RCV_AMOUNT) +
								 INSURANCE_FEE -
								 ROUND(INSURANCE_FEE * WO_AMOUNT / RCV_AMOUNT) + COD_FEE -
								 ROUND(COD_FEE * WO_AMOUNT / RCV_AMOUNT)), --其它费
								 PRODUCT_CODE --产品类型 
						FROM (SELECT RCV.CUSTOMER_CODE, --客户编码
												 RCV.CUSTOMER_NAME, --客户名称
												 RCV.CUSTOMER_TYPE, --客户类型
												 DECODE(RCV.PRODUCT_CODE,
																PKG_STL_COMMON.PRODUCT_CODE_PKG,
																RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE) ORIG_ORG_CODE, --始发部门编码 
												 DECODE(RCV.PRODUCT_CODE,
																PKG_STL_COMMON.PRODUCT_CODE_PKG,
																RCV.EXPRESS_ORIG_ORG_NAME，RCV.ORIG_ORG_NAME) ORIG_ORG_NAME, --始发部门名称
												 DECODE(RCV.PRODUCT_CODE,
																PKG_STL_COMMON.PRODUCT_CODE_PKG,
																RCV.EXPRESS_DEST_ORG_CODE，RCV.DEST_ORG_CODE) DEST_ORG_CODE, --到达部门编码
												 DECODE(RCV.PRODUCT_CODE,
																PKG_STL_COMMON.PRODUCT_CODE_PKG,
																RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME) DEST_ORG_NAME, --到达部门名称
												 RCV.WAYBILL_NO, --运单号
												 RCV.RECEIVABLE_NO, --单据编号
												 RCV.ACCOUNT_DATE, --会计日期
												 RCV.BUSINESS_DATE, --业务日期
												 RCV.BILL_TYPE, --单据子类型
												 RCV.AMOUNT RCV_AMOUNT, --金额
												 NVL((SELECT SUM(WO.AMOUNT)
															 FROM STL.T_STL_BILL_WRITEOFF WO
															WHERE (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
																		WO.END_NO = RCV.RECEIVABLE_NO)
																		AND
																		WO.ACCOUNT_DATE <= V_POD_ROW.POD_DATE),
														 0) WO_AMOUNT,
												 RCV.TRANSPORT_FEE, --运费
												 RCV.PICKUP_FEE, --接货费
												 RCV.DELIVERY_GOODS_FEE, --送货费
												 RCV.PACKAGING_FEE, --包装费
												 RCV.INSURANCE_FEE, --保价费
												 RCV.COD_FEE, --代收货款手续费
												 RCV.OTHER_FEE, --其它费
												 RCV.PRODUCT_CODE --产品类型 
										FROM STL.T_STL_BILL_RECEIVABLE RCV
									 WHERE RCV.ID IN
												 (SELECT ID
														FROM (SELECT ID, IS_RED_BACK, BILL_TYPE
																		FROM (SELECT *
																						FROM STL.T_STL_BILL_RECEIVABLE T
																					 WHERE T.WAYBILL_NO =
																								 V_POD_ROW.WAYBILL_NO
																								 AND
																								 T.SOURCE_BILL_TYPE =
																								 PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W
																								 AND T.ACCOUNT_DATE <=
																								 V_POD_ROW.POD_DATE
																								 AND
																								 T.BILL_TYPE =
																								 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN
																					 ORDER BY T.ACCOUNT_DATE DESC,
																										T.CREATE_TIME  DESC,
																										T.IS_RED_BACK  ASC)
																	 WHERE ROWNUM <= 1)
													 WHERE IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
																 AND
																 BILL_TYPE =
																 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN))
					 WHERE RCV_AMOUNT <> WO_AMOUNT
								 AND RCV_AMOUNT <> 0;
			
			END LOOP;
		END;
	
		--4)反签收运单——反签收时到达应收已核销金额
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
						 PKG_STL_VCH_COMMON.UPD_DEST_RCV_WO, --业务场景
						 CUSTOMER_CODE, --客户编码
						 CUSTOMER_NAME, --客户名称
						 CUSTOMER_TYPE, --客户类型
						 ORIG_ORG_CODE, --始发部门编码 
						 ORIG_ORG_NAME, --始发部门名称
						 DEST_ORG_CODE, --到达部门编码
						 DEST_ORG_NAME, --到达部门名称
						 WAYBILL_NO, --运单号
						 RECEIVABLE_NO, --单据编号
						 ACCOUNT_DATE, --会计日期
						 BUSINESS_DATE, --业务日期
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
						 BILL_TYPE, --单据子类型
						 WO_AMOUNT, --金额
						 ROUND(TRANSPORT_FEE * WO_AMOUNT / RCV_AMOUNT), --运费
						 ROUND(PICKUP_FEE * WO_AMOUNT / RCV_AMOUNT), --接货费
						 ROUND(DELIVERY_GOODS_FEE * WO_AMOUNT / RCV_AMOUNT), --送货费
						 ROUND(PACKAGING_FEE * WO_AMOUNT / RCV_AMOUNT), --包装费
						 ROUND(INSURANCE_FEE * WO_AMOUNT / RCV_AMOUNT), --保价费
						 ROUND(COD_FEE * WO_AMOUNT / RCV_AMOUNT), --代收货款手续费
						 WO_AMOUNT - (ROUND(TRANSPORT_FEE * WO_AMOUNT / RCV_AMOUNT) +
						 ROUND(PICKUP_FEE * WO_AMOUNT / RCV_AMOUNT) +
						 ROUND(DELIVERY_GOODS_FEE * WO_AMOUNT / RCV_AMOUNT) +
						 ROUND(PACKAGING_FEE * WO_AMOUNT / RCV_AMOUNT) +
						 ROUND(INSURANCE_FEE * WO_AMOUNT / RCV_AMOUNT) +
						 ROUND(COD_FEE * WO_AMOUNT / RCV_AMOUNT)), --其它费
						 PRODUCT_CODE --产品类型 
				FROM (SELECT RCV.CUSTOMER_CODE, --客户编码
										 RCV.CUSTOMER_NAME, --客户名称
										 RCV.CUSTOMER_TYPE, --客户类型
										 DECODE(RCV.PRODUCT_CODE,
														PKG_STL_COMMON.PRODUCT_CODE_PKG,
														RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE) ORIG_ORG_CODE, --始发部门编码 
										 DECODE(RCV.PRODUCT_CODE,
														PKG_STL_COMMON.PRODUCT_CODE_PKG,
														RCV.EXPRESS_ORIG_ORG_NAME，RCV.ORIG_ORG_NAME) ORIG_ORG_NAME, --始发部门名称
										 DECODE(RCV.PRODUCT_CODE,
														PKG_STL_COMMON.PRODUCT_CODE_PKG,
														RCV.EXPRESS_DEST_ORG_CODE，RCV.DEST_ORG_CODE) DEST_ORG_CODE, --到达部门编码
										 DECODE(RCV.PRODUCT_CODE,
														PKG_STL_COMMON.PRODUCT_CODE_PKG,
														RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME) DEST_ORG_NAME, --到达部门名称
										 RCV.WAYBILL_NO, --运单号
										 RCV.RECEIVABLE_NO, --单据编号
										 MAX(RCV.ACCOUNT_DATE) ACCOUNT_DATE, --会计日期
										 MIN(RCV.BUSINESS_DATE) BUSINESS_DATE, --业务日期
										 RCV.BILL_TYPE, --单据子类型
										 MAX(RCV.AMOUNT) RCV_AMOUNT, --金额
										 SUM(WO.AMOUNT) WO_AMOUNT,
										 MAX(RCV.TRANSPORT_FEE) TRANSPORT_FEE, --运费
										 MAX(RCV.PICKUP_FEE) PICKUP_FEE, --接货费
										 MAX(RCV.DELIVERY_GOODS_FEE) DELIVERY_GOODS_FEE, --送货费
										 MAX(RCV.PACKAGING_FEE) PACKAGING_FEE, --包装费
										 MAX(RCV.INSURANCE_FEE) INSURANCE_FEE, --保价费
										 MAX(RCV.COD_FEE) COD_FEE, --代收货款手续费
										 MAX(RCV.OTHER_FEE) OTHER_FEE, --其它费
										 RCV.PRODUCT_CODE --产品类型
								FROM STL.T_STL_POD POD
								JOIN STL.T_STL_BILL_RECEIVABLE RCV
									ON RCV.WAYBILL_NO = POD.WAYBILL_NO
										 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
								JOIN STL.T_STL_BILL_WRITEOFF WO
									ON (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
										 WO.END_NO = RCV.RECEIVABLE_NO)
							 WHERE POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__UPD --签收
										 AND RCV.SOURCE_BILL_TYPE =
										 PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --来源于运单
										 AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
																					 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
																					 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,
																					 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL --落地配到达应收类型
																					 ) --到达应收、偏线到达应收、空运到达应收
										 AND WO.ACCOUNT_DATE <= POD.POD_DATE --核销时间于小反签收时间
										 AND POD.POD_DATE >= P_BEGIN_DATE
										 AND POD.POD_DATE < P_END_DATE
							 GROUP BY RCV.CUSTOMER_CODE, --客户编码
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
															 RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME), --到达部门名称
												RCV.WAYBILL_NO, --运单号
												RCV.RECEIVABLE_NO, --单据编号
												RCV.BILL_TYPE, --单据子类型
												RCV.PRODUCT_CODE, --产品类型
												POD.ID)
			 WHERE WO_AMOUNT <> 0
						 AND RCV_AMOUNT <> 0;
	
		--5)反签收运单——反签收时到达应收未核销金额
		DECLARE
		
			CURSOR CUR_POD IS
				SELECT *
					FROM STL.T_STL_POD POD
				 WHERE POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__UPD
							 AND POD.POD_DATE >= P_BEGIN_DATE
							 AND POD.POD_DATE < P_END_DATE;
		
			V_POD_ROW STL.T_STL_POD%ROWTYPE;
		
		BEGIN
		
			FOR V_POD_ROW IN CUR_POD LOOP
			
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
								 PKG_STL_VCH_COMMON.UPD_DEST_RCV_NWO, --业务场景
								 CUSTOMER_CODE, --客户编码
								 CUSTOMER_NAME, --客户名称
								 CUSTOMER_TYPE, --客户类型
								 ORIG_ORG_CODE, --始发部门编码 
								 ORIG_ORG_NAME, --始发部门名称
								 DEST_ORG_CODE, --到达部门编码
								 DEST_ORG_NAME, --到达部门名称
								 WAYBILL_NO, --运单号
								 RECEIVABLE_NO, --单据编号
								 ACCOUNT_DATE, --会计日期
								 BUSINESS_DATE, --业务日期
								 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
								 BILL_TYPE, --单据子类型
								 RCV_AMOUNT - WO_AMOUNT, --金额
								 TRANSPORT_FEE -
								 ROUND(TRANSPORT_FEE * WO_AMOUNT / RCV_AMOUNT), --运费
								 PICKUP_FEE - ROUND(PICKUP_FEE * WO_AMOUNT / RCV_AMOUNT), --接货费
								 DELIVERY_GOODS_FEE -
								 ROUND(DELIVERY_GOODS_FEE * WO_AMOUNT / RCV_AMOUNT), --送货费
								 PACKAGING_FEE -
								 ROUND(PACKAGING_FEE * WO_AMOUNT / RCV_AMOUNT), --包装费
								 INSURANCE_FEE -
								 ROUND(INSURANCE_FEE * WO_AMOUNT / RCV_AMOUNT), --保价费
								 COD_FEE - ROUND(COD_FEE * WO_AMOUNT / RCV_AMOUNT), --代收货款手续费
								 RCV_AMOUNT - WO_AMOUNT -
								 (TRANSPORT_FEE -
								 ROUND(TRANSPORT_FEE * WO_AMOUNT / RCV_AMOUNT) +
								 PICKUP_FEE - ROUND(PICKUP_FEE * WO_AMOUNT / RCV_AMOUNT) +
								 DELIVERY_GOODS_FEE -
								 ROUND(DELIVERY_GOODS_FEE * WO_AMOUNT / RCV_AMOUNT) +
								 PACKAGING_FEE -
								 ROUND(PACKAGING_FEE * WO_AMOUNT / RCV_AMOUNT) +
								 INSURANCE_FEE -
								 ROUND(INSURANCE_FEE * WO_AMOUNT / RCV_AMOUNT) + COD_FEE -
								 ROUND(COD_FEE * WO_AMOUNT / RCV_AMOUNT)), --其它费
								 PRODUCT_CODE --产品类型 
						FROM (SELECT RCV.CUSTOMER_CODE, --客户编码
												 RCV.CUSTOMER_NAME, --客户名称
												 RCV.CUSTOMER_TYPE, --客户类型
												 DECODE(RCV.PRODUCT_CODE,
																PKG_STL_COMMON.PRODUCT_CODE_PKG,
																RCV.EXPRESS_ORIG_ORG_CODE，RCV.ORIG_ORG_CODE) ORIG_ORG_CODE, --始发部门编码 
												 DECODE(RCV.PRODUCT_CODE,
																PKG_STL_COMMON.PRODUCT_CODE_PKG,
																RCV.EXPRESS_ORIG_ORG_NAME，RCV.ORIG_ORG_NAME) ORIG_ORG_NAME, --始发部门名称
												 DECODE(RCV.PRODUCT_CODE,
																PKG_STL_COMMON.PRODUCT_CODE_PKG,
																RCV.EXPRESS_DEST_ORG_CODE，RCV.DEST_ORG_CODE) DEST_ORG_CODE, --到达部门编码
												 DECODE(RCV.PRODUCT_CODE,
																PKG_STL_COMMON.PRODUCT_CODE_PKG,
																RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME) DEST_ORG_NAME, --到达部门名称
												 RCV.WAYBILL_NO, --运单号
												 RCV.RECEIVABLE_NO, --单据编号
												 RCV.ACCOUNT_DATE, --会计日期
												 RCV.BUSINESS_DATE, --业务日期
												 RCV.BILL_TYPE, --单据子类型
												 RCV.AMOUNT RCV_AMOUNT, --金额
												 NVL((SELECT SUM(WO.AMOUNT)
															 FROM STL.T_STL_BILL_WRITEOFF WO
															WHERE (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
																		WO.END_NO = RCV.RECEIVABLE_NO)
																		AND
																		WO.ACCOUNT_DATE <= V_POD_ROW.POD_DATE),
														 0) WO_AMOUNT,
												 RCV.TRANSPORT_FEE, --运费
												 RCV.PICKUP_FEE, --接货费
												 RCV.DELIVERY_GOODS_FEE, --送货费
												 RCV.PACKAGING_FEE, --包装费
												 RCV.INSURANCE_FEE, --保价费
												 RCV.COD_FEE, --代收货款手续费
												 RCV.OTHER_FEE, --其它费
												 RCV.PRODUCT_CODE --产品类型 
										FROM STL.T_STL_BILL_RECEIVABLE RCV
									 WHERE RCV.ID IN
												 (SELECT ID
														FROM (SELECT ID, IS_RED_BACK, BILL_TYPE
																		FROM (SELECT *
																						FROM STL.T_STL_BILL_RECEIVABLE T
																					 WHERE T.WAYBILL_NO =
																								 V_POD_ROW.WAYBILL_NO
																								 AND
																								 T.SOURCE_BILL_TYPE =
																								 PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W
																								 AND T.ACCOUNT_DATE <=
																								 V_POD_ROW.POD_DATE
																								 AND T.BILL_TYPE IN
																								 (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
																											PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
																											PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,
																											PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL --落地配到达应收类型
																											)
																					 ORDER BY T.ACCOUNT_DATE DESC,
																										T.CREATE_TIME  DESC,
																										T.IS_RED_BACK  ASC)
																	 WHERE ROWNUM <= 1)
													 WHERE IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
																 AND BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
																									 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
																									 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,
																									 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL --落地配到达应收类型
																									 )))
					 WHERE RCV_AMOUNT <> WO_AMOUNT
								 AND RCV_AMOUNT <> 0;
			
			END LOOP;
		END;
	
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

END PKG_STL_VCH_DAILY_UPD;
/
