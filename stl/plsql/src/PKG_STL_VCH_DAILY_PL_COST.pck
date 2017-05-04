CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_PL_COST IS

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

END PKG_STL_VCH_DAILY_PL_COST;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_PL_COST IS

	-----------------------------------------------------------------
	-- 凭证开单处理日明细
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
																		 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
																		 P_END_DATE   DATE -- 结束时间 2012-12-22
																		 ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
	 IS
	BEGIN
	
		--1) 偏线代理成本--应付偏线代理成本冲应收到付运费已签收  
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
						 PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_POD, --业务场景：按核销单的记账日期来统计，取核销单的明细金额，关联明细中的应收单单据子类型为始发应收
						 RCV.CUSTOMER_CODE, --客户编码
						 RCV.CUSTOMER_NAME, --客户名称
						 RCV.CUSTOMER_TYPE, --客户类型
						 RCV.ORIG_ORG_CODE, --始发部门编码 
						 RCV.ORIG_ORG_NAME, --始发部门名称
						 RCV.DEST_ORG_CODE, --到达部门编码
						 RCV.DEST_ORG_NAME, --到达部门名称
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
						 AND
						 PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE --偏线代理成本
						 AND RCV.BILL_TYPE IN
						 (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
									PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL) --到达应收、到达偏线代理应收单
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
	
		--2) 偏线代理成本--应付偏线代理成本冲应收到付运费未签收  
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
						 PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_NPOD, --业务场景：按核销单的记账日期来统计，取核销单的明细金额，关联明细中的应收单单据子类型为始发应收
						 RCV.CUSTOMER_CODE, --客户编码
						 RCV.CUSTOMER_NAME, --客户名称
						 RCV.CUSTOMER_TYPE, --客户类型
						 RCV.ORIG_ORG_CODE, --始发部门编码 
						 RCV.ORIG_ORG_NAME, --始发部门名称
						 RCV.DEST_ORG_CODE, --到达部门编码
						 RCV.DEST_ORG_NAME, --到达部门名称
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
						 AND
						 PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE --偏线代理成本
						 AND RCV.BILL_TYPE IN
						 (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
									PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL) --到达应收、到达偏线代理应收单
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
	
		--3) 偏线代理成本--外发单录入
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
						 PKG_STL_VCH_COMMON.PL_COST_VECH, --业务场景：按核销单的记账日期来统计，取核销单的明细金额，关联明细中的应收单单据子类型为始发应收
						 PAY.CUSTOMER_CODE, --客户编码
						 PAY.CUSTOMER_NAME, --客户名称
						 PAY.CUSTOMER_TYPE, --客户类型
						 PAY.ORIG_ORG_CODE, --始发部门编码 
						 PAY.ORIG_ORG_NAME, --始发部门名称
						 PAY.DEST_ORG_CODE, --到达部门编码
						 PAY.DEST_ORG_NAME, --到达部门名称
						 DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
										'N/A',
										PAY.SOURCE_BILL_NO,
										PAY.WAYBILL_NO), --运单号
						 PAY.PAYABLE_NO, --单据编号
						 PAY.ACCOUNT_DATE, --记账日期
						 PAY.BUSINESS_DATE, --业务日期
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
						 PAY.BILL_TYPE, --单据子类型
						 PAY.AMOUNT,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PAY.PRODUCT_CODE --产品类型
				FROM STL.T_STL_BILL_PAYABLE PAY
			 WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE --偏线代理成本
						 AND PAY.AMOUNT <> 0
						 AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND PAY.ACCOUNT_DATE < P_END_DATE;
	
		--4) 偏线代理成本--偏线代理成本确认
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
						 PKG_STL_VCH_COMMON.PL_COST_CONFIRM, --业务场景：按核销单的记账日期来统计，取核销单的明细金额，关联明细中的应收单单据子类型为始发应收
						 PAY.CUSTOMER_CODE, --客户编码
						 PAY.CUSTOMER_NAME, --客户名称
						 PAY.CUSTOMER_TYPE, --客户类型
						 PAY.ORIG_ORG_CODE, --始发部门编码 
						 PAY.ORIG_ORG_NAME, --始发部门名称
						 PAY.DEST_ORG_CODE, --到达部门编码
						 PAY.DEST_ORG_NAME, --到达部门名称
						 DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
										'N/A',
										PAY.SOURCE_BILL_NO,
										PAY.WAYBILL_NO), --运单号
						 PAY.PAYABLE_NO, --单据编号
						 PAY.ACCOUNT_DATE, --记账日期
						 PAY.BUSINESS_DATE, --业务日期
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
						 PAY.BILL_TYPE, --单据子类型
						 PAY.AMOUNT,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PAY.PRODUCT_CODE --产品类型
				FROM STL.T_STL_BILL_PAYABLE PAY
				JOIN STL.T_STL_POD T
					ON T.WAYBILL_NO = PAY.WAYBILL_NO
			 WHERE T.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__POD --签收类型
						 AND
						 PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE --偏线代理成本
						 AND PAY.AMOUNT <> 0
						 AND PAY.ACCOUNT_DATE < T.POD_DATE
						 AND T.POD_DATE >= P_BEGIN_DATE
						 AND T.POD_DATE < P_END_DATE;
	
		--5) 偏线代理成本--偏线代理成本反确认
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
						 PKG_STL_VCH_COMMON.PL_COST_NOT_CONFIRM, --业务场景：按核销单的记账日期来统计，取核销单的明细金额，关联明细中的应收单单据子类型为始发应收
						 PAY.CUSTOMER_CODE, --客户编码
						 PAY.CUSTOMER_NAME, --客户名称
						 PAY.CUSTOMER_TYPE, --客户类型
						 PAY.ORIG_ORG_CODE, --始发部门编码 
						 PAY.ORIG_ORG_NAME, --始发部门名称
						 PAY.DEST_ORG_CODE, --到达部门编码
						 PAY.DEST_ORG_NAME, --到达部门名称
						 DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
										'N/A',
										PAY.SOURCE_BILL_NO,
										PAY.WAYBILL_NO), --运单号
						 PAY.PAYABLE_NO, --单据编号
						 PAY.ACCOUNT_DATE, --记账日期
						 PAY.BUSINESS_DATE, --业务日期
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
						 PAY.BILL_TYPE, --单据子类型
						 PAY.AMOUNT,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PAY.PRODUCT_CODE --产品类型
				FROM STL.T_STL_BILL_PAYABLE PAY
				JOIN STL.T_STL_POD T
					ON T.WAYBILL_NO = PAY.WAYBILL_NO
			 WHERE T.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__UPD --反签收类型
						 AND
						 PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE --偏线代理成本
						 AND PAY.AMOUNT <> 0
						 AND PAY.ACCOUNT_DATE < T.POD_DATE
						 AND T.POD_DATE >= P_BEGIN_DATE
						 AND T.POD_DATE < P_END_DATE;
	
		--6) 偏线代理成本--偏线代理成本付款申请
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
						 PKG_STL_VCH_COMMON.PL_COST_PAY_APPLY, --业务场景：按核销单的记账日期来统计，取核销单的明细金额，关联明细中的应收单单据子类型为始发应收
						 PAY.CUSTOMER_CODE, --客户编码
						 PAY.CUSTOMER_NAME, --客户名称
						 PAY.CUSTOMER_TYPE, --客户类型
						 PAY.ORIG_ORG_CODE, --始发部门编码 
						 PAY.ORIG_ORG_NAME, --始发部门名称
						 PAY.DEST_ORG_CODE, --到达部门编码
						 PAY.DEST_ORG_NAME, --到达部门名称
						 DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
										'N/A',
										PAY.SOURCE_BILL_NO,
										PAY.WAYBILL_NO), --运单号
						 PAY.PAYABLE_NO, --单据编号
						 PAY.ACCOUNT_DATE, --记账日期
						 PAY.BUSINESS_DATE, --业务日期
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --单据类型
						 PAY.BILL_TYPE, --单据子类型
						 DP.PAY_AMOUNT * DECODE(PMT.IS_RED_BACK,
																		PKG_STL_COMMON.IS_RED_BACK_YES,
																		-1,
																		PKG_STL_COMMON.IS_RED_BACK_NO,
																		1),
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
			 WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE --偏线代理成本
						 AND DP.PAY_AMOUNT <> 0
						--AND PMT.PAYMENT_NO = PAY.PAYMENT_NO  2013-08-01 modified by zbw
             					 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --非红单
						 AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND PMT.ACCOUNT_DATE < P_END_DATE;
	
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

END PKG_STL_VCH_DAILY_PL_COST;
/
