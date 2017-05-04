CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_APT IS

	-- ==============================================================
	-- Author  : ZBW
	-- Created : 2013-11-06
	-- Purpose : 每日凭证预付
	-- ==============================================================

	-----------------------------------------------------------------
	-- 预付处理日明细
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
																		 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
																		 P_END_DATE   DATE -- 结束时间 2012-12-22
																		 ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_DAILY_APT;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_APT IS

	-----------------------------------------------------------------
	-- 预付处理日明细
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
																		 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
																		 P_END_DATE   DATE -- 结束时间 2012-12-22
																		 ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
	 IS
	BEGIN
	
		--1)预付航空公司  
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
						 PKG_STL_VCH_COMMON.APT_AIR_COMPANY, --业务场景
						 AP.CUSTOMER_CODE, --客户编码
						 AP.CUSTOMER_NAME, --客户名称
						 AP.CUSTOMER_TYPE, --客户类型
						 AP.APPLY_ORG_CODE, --始发部门编码 
						 AP.APPLY_ORG_NAME, --始发部门名称
						 AP.APPLY_ORG_CODE, --到达部门编码
						 AP.APPLY_ORG_NAME, --到达部门名称
						 NULL, --运单号
						 AP.ADVANCES_NO, --单据编号
						 AP.ACCOUNT_DATE, --会计日期
						 AP.BUSINESS_DATE, --业务日期
						 PKG_STL_COMMON.BILL_PARENT_TYPE__UF, --单据类型
						 AP.BILL_TYPE, --单据子类型
						 AP.AMOUNT, --金额
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PKG_STL_COMMON.PRODUCT_CODE_AF --产品类型
				FROM STL.T_STL_BILL_ADVANCED_PAYMENT AP
			 WHERE AP.BILL_TYPE = PKG_STL_COMMON.ADVANCED_PAYMENT_BILL_TYPE_AIR ---空运
						 AND
						 AP.AUDIT_STATUS = PKG_STL_COMMON.ADVANCED_PAY_AUDIT_STATUS_AA
						 AND AP.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
						 AND AP.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND AP.ACCOUNT_DATE < P_END_DATE;
	
		--2)预付冲应付航空公司 
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
						 PKG_STL_VCH_COMMON.APT_WO_AIR_PAY, --业务场景
						 AP.CUSTOMER_CODE, --客户编码
						 AP.CUSTOMER_NAME, --客户名称
						 AP.CUSTOMER_TYPE, --客户类型
						 AP.APPLY_ORG_CODE, --始发部门编码 
						 AP.APPLY_ORG_NAME, --始发部门名称
						 AP.APPLY_ORG_CODE, --到达部门编码
						 AP.APPLY_ORG_NAME, --到达部门名称
						 NULL, --运单号
						 AP.ADVANCES_NO, --单据编号
						 WO.ACCOUNT_DATE, --会计日期
						 AP.BUSINESS_DATE, --业务日期
						 PKG_STL_COMMON.BILL_PARENT_TYPE__UF, --单据类型
						 AP.BILL_TYPE, --单据子类型
						 WO.AMOUNT, --金额
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PKG_STL_COMMON.PRODUCT_CODE_AF --产品类型
				FROM STL.T_STL_BILL_WRITEOFF WO
				JOIN STL.T_STL_BILL_ADVANCED_PAYMENT AP
					ON AP.ADVANCES_NO = WO.BEGIN_NO
						 AND AP.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
				JOIN STL.T_STL_BILL_PAYABLE PAY
					ON PAY.PAYABLE_NO = WO.END_NO
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_AP -- 预付冲应付
						 AND
						 AP.BILL_TYPE = PKG_STL_COMMON.ADVANCED_PAYMENT_BILL_TYPE_AIR --空运
						 AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR --应付单为航空运费
						 AND AP.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
						 AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;
	
		--3)预付冲应付空运其他应付 
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
						 PKG_STL_VCH_COMMON.APT_WO_OTH_PAY, --业务场景
						 AP.CUSTOMER_CODE, --客户编码
						 AP.CUSTOMER_NAME, --客户名称
						 AP.CUSTOMER_TYPE, --客户类型
						 AP.APPLY_ORG_CODE, --始发部门编码 
						 AP.APPLY_ORG_NAME, --始发部门名称
						 AP.APPLY_ORG_CODE, --到达部门编码
						 AP.APPLY_ORG_NAME, --到达部门名称
						 NULL, --运单号
						 AP.ADVANCES_NO, --单据编号
						 WO.ACCOUNT_DATE, --会计日期
						 AP.BUSINESS_DATE, --业务日期
						 PKG_STL_COMMON.BILL_PARENT_TYPE__UF, --单据类型
						 AP.BILL_TYPE, --单据子类型
						 WO.AMOUNT, --金额
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PKG_STL_COMMON.PRODUCT_CODE_AF --产品类型
				FROM STL.T_STL_BILL_WRITEOFF WO
				JOIN STL.T_STL_BILL_ADVANCED_PAYMENT AP
					ON AP.ADVANCES_NO = WO.BEGIN_NO
						 AND AP.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
				JOIN STL.T_STL_BILL_PAYABLE PAY
					ON PAY.PAYABLE_NO = WO.END_NO
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_AP -- 预付冲应付
						 AND
						 AP.BILL_TYPE = PKG_STL_COMMON.ADVANCED_PAYMENT_BILL_TYPE_AIR --空运
						 AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER --应付单为航空其他运费
						 AND AP.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
						 AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
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
																				'预付处理日明细' || '异常');
		
			--返回失败标志
			RETURN FALSE;
	END;

END PKG_STL_VCH_DAILY_APT;
/
