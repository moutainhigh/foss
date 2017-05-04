CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_CUST_DR IS

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

END PKG_STL_VCH_DAILY_CUST_DR;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_CUST_DR IS

	-----------------------------------------------------------------
	-- 凭证开单处理日明细
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
																		 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
																		 P_END_DATE   DATE -- 结束时间 2012-12-22
																		 ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
	 IS
	BEGIN
	
		--1)营业部预收客户  预收客户现金
		------按预收单的记账日期来统计，预收单的付款方式为：现金，预收单运输类型为：专线客户
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
						 PKG_STL_VCH_COMMON.CUST_DR_CH, --业务场景
						 DEP.CUSTOMER_CODE, --客户编码
						 DEP.CUSTOMER_NAME, --客户名称
						 DEP.CUSTOMER_TYPE, --客户类型
						 DEP.CREATE_ORG_CODE, --始发部门编码
						 DEP.CREATE_ORG_NAME, --始发部门名称
						 DEP.CREATE_ORG_CODE, --到达部门编码
						 DEP.CREATE_ORG_NAME, --到达部门名称
						 '', --运单号
						 DEP.DEPOSIT_RECEIVED_NO, --单据编号
						 DEP.ACCOUNT_DATE, --会计日期
						 DEP.BUSINESS_DATE, --业务日期
						 PKG_STL_COMMON.BILL_PARENT_TYPE__US, --单据类型
						 DEP.BILL_TYPE, --单据子类型
						 DEP.AMOUNT, --金额
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PKG_STL_COMMON.PRODUCT_CODE_FLF
				FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DEP
			 WHERE DEP.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---支付方式为现金
						 AND DEP.TRANSPORT_TYPE =
						 PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
						 AND DEP.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND DEP.ACCOUNT_DATE < P_END_DATE;
	
		COMMIT;
		--2)营业部预收客户  预收客户银行
		------按预收单的记账日期来统计，预收单的付款方式为：
		------银行卡、电汇、支票、网上支付，预收单运输类型为：专线客户
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
						 PKG_STL_VCH_COMMON.CUST_DR_CD, --业务场景
						 DEP.CUSTOMER_CODE, --客户编码
						 DEP.CUSTOMER_NAME, --客户名称
						 DEP.CUSTOMER_TYPE, --客户类型
						 DEP.CREATE_ORG_CODE, --始发部门编码
						 DEP.CREATE_ORG_NAME, --始发部门名称
						 DEP.CREATE_ORG_CODE, --到达部门编码
						 DEP.CREATE_ORG_NAME, --到达部门名称
						 '', --运单号
						 DEP.DEPOSIT_RECEIVED_NO, --单据编号
						 DEP.ACCOUNT_DATE, --会计日期
						 DEP.BUSINESS_DATE, --业务日期
						 PKG_STL_COMMON.BILL_PARENT_TYPE__US, --单据类型
						 DEP.BILL_TYPE, --单据子类型
						 DEP.AMOUNT, --金额
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PKG_STL_COMMON.PRODUCT_CODE_FLF
				FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DEP
			 WHERE DEP.PAYMENT_TYPE IN
						 (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
							PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
							PKG_STL_COMMON.PAYMENT_TYPE_NOTE,
							PKG_STL_COMMON.PAYMENT_TYPE_ONLINE) ---支付方式为银行卡、电汇、支票、网上支付
						 AND DEP.TRANSPORT_TYPE =
						 PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
						 AND DEP.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND DEP.ACCOUNT_DATE < P_END_DATE;
		COMMIT;
		--3)营业部预收客户  预收客户冲应收到付运费未签收
		/* 按核销单的记账日期来统计，
    关联判断核销时点应收单的签收状态为未签收，
    应收单的单据子类型为到达应收、
    到达偏线代理应收单、空运到达代理应收，
    预收单运输类型为：专线客户，取核销明细的金额，反核销时生成的负数也统计*/
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
						 PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_NPOD, --业务场景
						 RE.CUSTOMER_CODE, --客户编码
						 RE.CUSTOMER_NAME, --客户名称
						 RE.CUSTOMER_TYPE, --客户类型
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_ORIG_ORG_CODE，RE.ORIG_ORG_CODE), --出发部门编码
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_ORIG_ORG_NAME，RE.ORIG_ORG_NAME), --始发部门名称
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_DEST_ORG_CODE，RE.DEST_ORG_CODE), --到达部门编码
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_DEST_ORG_NAME，RE.DEST_ORG_NAME), --到达部门名称
						 RE.WAYBILL_NO, --运单号
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
										PKG_STL_COMMON.PRODUCT_CODE_FLF,
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
						 AND RE.BILL_TYPE IN
						 (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
									PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
									PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收、
                                                                                                                                                                                                   到达偏线代理应收单、空运到达代理应收*/
						 AND DR.TRANSPORT_TYPE =
						 PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
						
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;
		COMMIT;
		--4)营业部预收客户  预收客户冲应收到付运费已签收
		/* 按核销单的记账日期来统计，
    关联判断核销时点应收单的签收状态为已签收，
    应收单的单据子类型为到达应收、到达偏线代理应收单、空运到达代理应收，
    预收单运输类型为：专线客户，取核销明细的金额，反核销时生成的负数也统计*/
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
						 PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_POD, --业务场景
						 RE.CUSTOMER_CODE, --客户编码
						 RE.CUSTOMER_NAME, --客户名称
						 RE.CUSTOMER_TYPE, --客户类型
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_ORIG_ORG_CODE，RE.ORIG_ORG_CODE), --出发部门编码
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_ORIG_ORG_NAME，RE.ORIG_ORG_NAME), --始发部门名称
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_DEST_ORG_CODE，RE.DEST_ORG_CODE), --到达部门编码
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_DEST_ORG_NAME，RE.DEST_ORG_NAME),
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
										PKG_STL_COMMON.PRODUCT_CODE_FLF,
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
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --还款时未签收
						 AND RE.BILL_TYPE IN
						 (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
									PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
									PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   应收单的单据子类型为到达应收、
                                                                                                                                                                                                   到达偏线代理应收单、空运到达代理应收*/
						 AND DR.TRANSPORT_TYPE =
						 PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
						
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;
		COMMIT;
		--5)营业部预收客户  预收客户冲应收始发运费未签收
		/* 按核销单的记账日期来统计，
    关联判断核销时点应收单的签收状态为未签收，
    应收单的单据子类型为始发应收，
    预收单运输类型为：专线客户，取核销明细的金额，反核销时生成的负数也统计*/
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
						 PKG_STL_VCH_COMMON.CUST_DR_ORIG_RCV_NPOD, --业务场景
						 RE.CUSTOMER_CODE, --客户编码
						 RE.CUSTOMER_NAME, --客户名称
						 RE.CUSTOMER_TYPE, --客户类型
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_ORIG_ORG_CODE，RE.ORIG_ORG_CODE), --出发部门编码
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_ORIG_ORG_NAME，RE.ORIG_ORG_NAME), --始发部门名称
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_DEST_ORG_CODE，RE.DEST_ORG_CODE), --到达部门编码
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_DEST_ORG_NAME，RE.DEST_ORG_NAME),
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
										PKG_STL_COMMON.PRODUCT_CODE_FLF,
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
						
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;
		COMMIT;
		--6)营业部预收客户  预收客户冲应收始发运费已签收
		/* 按核销单的记账日期来统计，
    关联判断核销时点应收单的签收状态为已签收，
    应收单的单据子类型为始发应收，
    预收单运输类型为：专线客户，取核销明细的金额，反核销时生成的负数也统计*/
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
						 PKG_STL_VCH_COMMON.CUST_DR_ORIG_RCV_POD, --业务场景
						 RE.CUSTOMER_CODE, --客户编码
						 RE.CUSTOMER_NAME, --客户名称
						 RE.CUSTOMER_TYPE, --客户类型
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_ORIG_ORG_CODE，RE.ORIG_ORG_CODE), --出发部门编码
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_ORIG_ORG_NAME，RE.ORIG_ORG_NAME), --始发部门名称
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_DEST_ORG_CODE，RE.DEST_ORG_CODE), --到达部门编码
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_DEST_ORG_NAME，RE.DEST_ORG_NAME),
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
										PKG_STL_COMMON.PRODUCT_CODE_FLF,
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
						
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;
		COMMIT;
		--7)营业部预收客户  始发退预收付款申请
		/* 按付款单的记账日期来统计，关联里面的付款明细单据子类型为： 预收单，
    预收单运输类型为：专线客户，统计对应的付款单明细金额之和*/
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
						 PKG_STL_VCH_COMMON.CUST_DR_ORIG_PAY_APPLY, --业务场景
						 DR.CUSTOMER_CODE, --客户编码
						 DR.CUSTOMER_NAME, --客户名称
						 DR.CUSTOMER_TYPE, --客户类型
						 DR.CREATE_ORG_CODE, --始发部门编码
						 DR.CREATE_ORG_NAME, --始发部门名称
						 DR.CREATE_ORG_CODE, --到达部门编码
						 DR.CREATE_ORG_NAME, --到达部门名称
						 DECODE(NVL(PAY_D.WAYBILL_NO, 'N/A'),
										'N/A',
										PAY_D.SOURCE_BILL_NO,
										PAY_D.WAYBILL_NO), --运单号
						 DR.DEPOSIT_RECEIVED_NO, --单据编号
						 PAY.ACCOUNT_DATE, --会计日期
						 PAY.BUSINESS_DATE, --业务日期
						 PKG_STL_COMMON.BILL_PARENT_TYPE__FK, --单据类型
						 PAY.BILL_TYPE, --单据子类型
						 PAY_D.PAY_AMOUNT *
						 DECODE(PAY.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1), --金额
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PKG_STL_COMMON.PRODUCT_CODE_FLF
				FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DR
				JOIN STL.T_STL_BILL_PAYMENT_D PAY_D
					ON PAY_D.SOURCE_BILL_NO = DR.DEPOSIT_RECEIVED_NO
			
				JOIN STL.T_STL_BILL_PAYMENT PAY
					ON PAY.PAYMENT_NO = PAY_D.PAYMENT_NO
			 WHERE DR.TRANSPORT_TYPE =
						 PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --运输类型为专线客户
						 AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --预收单为非红单
						 AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND PAY.ACCOUNT_DATE < P_END_DATE;
	
		COMMIT;
	
		RETURN TRUE;
	
	EXCEPTION
		WHEN OTHERS THEN
			--插入错误日志表
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'FUNC_PROCESS_DAILY_DETAIL',
																				'预收客户处理日明细' || '异常');
		
			--返回失败标志
			RETURN FALSE;
	END;

END PKG_STL_VCH_DAILY_CUST_DR;
/
