CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_POR IS

	-- ==============================================================
	-- Author  : ZBW
	-- Created : 2014-02-20
	-- Purpose : 每日凭证偏线其它应收
	-- ==============================================================

	-----------------------------------------------------------------
	-- 坏账冲其他应收处理日明细
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
																		 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
																		 P_END_DATE   DATE -- 结束时间 2012-12-22
																		 ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_DAILY_POR;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_POR IS

	-----------------------------------------------------------------
	-- 偏线其它应收处理日明细
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
																		 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
																		 P_END_DATE   DATE -- 结束时间 2012-12-22
																		 ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
	 IS
	BEGIN
	
		--1) 偏线其它应收录入
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
						 PKG_STL_VCH_COMMON.POR_ENTRY, --业务场景
						 RCV.CUSTOMER_CODE, --客户编码
						 RCV.CUSTOMER_NAME, --客户名称
						 RCV.CUSTOMER_TYPE, --客户类型
						 RCV.ORIG_ORG_CODE, --始发部门编码 
						 RCV.ORIG_ORG_NAME, --始发部门名称
						 RCV.DEST_ORG_CODE, --到达部门编码
						 RCV.DEST_ORG_NAME, --到达部门名称
						 NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --运单号
						 RCV.RECEIVABLE_NO, --单据编号
						 RCV.ACCOUNT_DATE, --记账日期
						 RCV.BUSINESS_DATE, --业务日期
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --单据类型
						 RCV.BILL_TYPE, --单据子类型
						 RCV.AMOUNT,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 RCV.PRODUCT_CODE --产品类型
				FROM STL.T_STL_BILL_RECEIVABLE RCV
			 WHERE RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PR --偏线其它应收
						 AND RCV.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND RCV.ACCOUNT_DATE < P_END_DATE;
	
		--2) 还款偏线其他应收现金
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
						 PKG_STL_VCH_COMMON.UR_POR_CH, --业务场景
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
				FROM STL.T_STL_BILL_WRITEOFF WO
				JOIN STL.T_STL_BILL_REPAYMENT RPMT
					ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
						 AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
				JOIN STL.T_STL_BILL_RECEIVABLE RCV
					ON RCV.RECEIVABLE_NO = WO.END_NO
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收
						 AND RPMT.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH --现金
						 AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PR --偏线其它应收
						 AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;
	
		--3) 还款偏线其他应收银行
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
						 PKG_STL_VCH_COMMON.UR_POR_CD, --业务场景
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
				FROM STL.T_STL_BILL_WRITEOFF WO
				JOIN STL.T_STL_BILL_REPAYMENT RPMT
					ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
						 AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
				JOIN STL.T_STL_BILL_RECEIVABLE RCV
					ON RCV.RECEIVABLE_NO = WO.END_NO
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --还款冲应收
						 AND RPMT.PAYMENT_TYPE IN
						 (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
									PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
									PKG_STL_COMMON.PAYMENT_TYPE_NOTE,
									PKG_STL_COMMON.PAYMENT_TYPE_ONLINE) --银行卡、电汇、支票、网上支付
						 AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PR --偏线其它应收
						 AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
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
																				'偏线其它应收处理日明细' || '异常');
		
			--返回失败标志
			RETURN FALSE;
	END;

END PKG_STL_VCH_DAILY_POR;
/
