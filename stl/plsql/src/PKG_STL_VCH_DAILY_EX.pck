CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_EX IS

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

END PKG_STL_VCH_DAILY_EX;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_EX IS

	-----------------------------------------------------------------
	-- 凭证开单处理日明细
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
																		 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
																		 P_END_DATE   DATE -- 结束时间 2012-12-22
																		 ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
	 IS
	BEGIN
	
		--1) 异常冲收入-应收始发运费已签收
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
						 PKG_STL_VCH_COMMON.EX_ORIG_RCV_POD, --业务场景
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
										RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME), --到达部门名称
						 DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
										'N/A',
										RCV.SOURCE_BILL_NO,
										RCV.WAYBILL_NO), --运单号
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
				FROM STL.T_STL_BILL_WRITEOFF WO --核销单
				JOIN STL.T_STL_BILL_RECEIVABLE RCV
					ON RCV.RECEIVABLE_NO = WO.END_NO --应收单
				JOIN STL.T_STL_BILL_BAD_ACCOUNT ACC
					ON ACC.BAD_DEBAT_BILL_NO = WO.BEGIN_NO --坏账单
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_BR --坏账冲应收
						 AND
						 ACC.BILL_TYPE = PKG_STL_COMMON.BAD_ACCOUNT_BILL_TYPE_INCOME --保险理赔
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
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;
	
		--2) 异常冲收入-应收到达运费已签收
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
						 PKG_STL_VCH_COMMON.EX_DEST_RCV_POD, --业务场景
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
										RCV.EXPRESS_DEST_ORG_NAME，RCV.DEST_ORG_NAME), --到达部门名称
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
					ON RCV.RECEIVABLE_NO = WO.END_NO --应收单
				JOIN STL.T_STL_BILL_BAD_ACCOUNT ACC
					ON ACC.BAD_DEBAT_BILL_NO = WO.BEGIN_NO --坏账单
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_BR --坏账冲应收
						 AND
						 ACC.BILL_TYPE = PKG_STL_COMMON.BAD_ACCOUNT_BILL_TYPE_INCOME --保险理赔
						 AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
																	 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
																	 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,
																	 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL -- 到达落地配
																	 )
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

END PKG_STL_VCH_DAILY_EX;
/
