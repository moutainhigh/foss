CREATE OR REPLACE PACKAGE STV.PKG_STL_VCH_MONTHLY IS

	-- ==============================================================
	-- Author  : ZHUWEI
	-- Created : 2012-12-07 14:29:42
	-- Purpose : 每月凭证入口
	-- ==============================================================

	-----------------------------------------------------------------
	-- 每月凭证汇总入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_MONTHLY(P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
																P_END_DATE   DATE -- 结束时间 2012-12-22
																) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

	-----------------------------------------------------------------
	-- 始发月报表入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NRFONO(P_PERIOD     VARCHAR2,
															 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
															 P_END_DATE   DATE -- 结束时间 2012-12-22
															 ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

	FUNCTION FUNC_VOUCHER_NRFOSO(P_PERIOD     VARCHAR2,
															 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
															 P_END_DATE   DATE -- 结束时间 2012-12-22
															 ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败    

	FUNCTION FUNC_VOUCHER_NRFONT(P_PERIOD     VARCHAR2,
															 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
															 P_END_DATE   DATE -- 结束时间 2012-12-22
															 ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败      

	FUNCTION FUNC_VOUCHER_NRFOST(P_PERIOD     VARCHAR2,
															 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
															 P_END_DATE   DATE -- 结束时间 2012-12-22
															 ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败                                                                          

	-----------------------------------------------------------------
	-- 到达月报表入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NRFDO(P_PERIOD     VARCHAR2,
															P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
															P_END_DATE   DATE -- 结束时间 2012-12-22
															) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

	FUNCTION FUNC_VOUCHER_NRFDT(P_PERIOD     VARCHAR2,
															P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
															P_END_DATE   DATE -- 结束时间 2012-12-22
															) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败                            

	-----------------------------------------------------------------
	-- 偏线月报表入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NPLR(P_PERIOD     VARCHAR2,
														 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
														 P_END_DATE   DATE -- 结束时间 2012-12-22
														 ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

	-----------------------------------------------------------------
	-- 空运月报表入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NAFR(P_PERIOD     VARCHAR2,
														 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
														 P_END_DATE   DATE -- 结束时间 2012-12-22
														 ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

	-----------------------------------------------------------------
	-- 始发到达往来月报表入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NRFI(P_PERIOD     VARCHAR2,
														 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
														 P_END_DATE   DATE -- 结束时间 2012-12-22
														 ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

	-----------------------------------------------------------------
	-- 始发偏线往来月报表入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NPLI(P_PERIOD     VARCHAR2,
														 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
														 P_END_DATE   DATE -- 结束时间 2012-12-22
														 ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

	-----------------------------------------------------------------
	-- 始发空运往来月报表入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NAFI(P_PERIOD     VARCHAR2,
														 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
														 P_END_DATE   DATE -- 结束时间 2012-12-22
														 ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

	-----------------------------------------------------------------
	-- 退代收货款报表入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_RETURN_COD(P_PERIOD     VARCHAR2,
																	 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
																	 P_END_DATE   DATE -- 结束时间 2012-12-22
																	 ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

	-----------------------------------------------------------------
	-- 落地配月报表
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_LDD(P_PERIOD     VARCHAR2,
														P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
														P_END_DATE   DATE -- 结束时间 2012-12-22
														) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

	-----------------------------------------------------------------
	-- 落地配往来月报表
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_LDI(P_PERIOD     VARCHAR2,
														P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
														P_END_DATE   DATE -- 结束时间 2012-12-22
														) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

	-----------------------------------------------------------------
	-- 营业部核销月报表
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_LWO(P_PERIOD     VARCHAR2,
														P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
														P_END_DATE   DATE -- 结束时间 2012-12-22
														) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_MONTHLY;
/
CREATE OR REPLACE PACKAGE BODY STV.PKG_STL_VCH_MONTHLY IS

	-- ==============================================================
	-- Author  : ZHUWEI
	-- Created : 2012-12-07 14:29:42
	-- Purpose : 每月凭证入口
	-- ==============================================================

	-----------------------------------------------------------------
	-- 每月凭证入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_MONTHLY(P_BEGIN_DATE DATE, -- 开始时间 2012-12-21(包含)
																P_END_DATE   DATE -- 结束时间 2012-12-22(不包含)
																) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
	 IS
	
		V_START_PERIOD VARCHAR2(50); --开始期间
		V_END_PERIOD   VARCHAR2(50); --结束期间
	
	BEGIN
	
		--开始期间和结束期间
		V_START_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
		V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');
	
		-- 删除需要重新统计的月汇总报表
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_NMVS T WHERE T.PERIOD >= :BEGIN_PERIOD AND T.PERIOD < :END_PERIOD'
			USING V_START_PERIOD, V_END_PERIOD;
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_NMVSI T WHERE T.PERIOD >= :BEGIN_PERIOD AND T.PERIOD < :END_PERIOD'
			USING V_START_PERIOD, V_END_PERIOD;
	
		--统计月汇总数据
		INSERT INTO STV.T_STL_NMVS
			(ID,
			 PERIOD,
			 BUSINESS_CASE,
			 PRODUCT_CODE,
			 CUSTOMER_CODE,
			 CUSTOMER_NAME,
			 CUSTOMER_TYPE,
			 ORIG_ORG_CODE,
			 ORIG_ORG_NAME,
			 DEST_ORG_CODE,
			 DEST_ORG_NAME,
			 AMOUNT,
			 AMOUNT_FRT,
			 AMOUNT_PUP,
			 AMOUNT_DEL,
			 AMOUNT_PKG,
			 AMOUNT_DV,
			 AMOUNT_COD,
			 AMOUNT_OT,
			 VOUCHER_BEGIN_TIME,
			 VOUCHER_END_TIME)
			SELECT SYS_GUID(),
						 T.PERIOD,
						 T.BUSINESS_CASE,
						 T.PRODUCT_CODE,
						 T.CUSTOMER_CODE,
						 T.CUSTOMER_NAME,
						 T.CUSTOMER_TYPE,
						 T.ORIG_ORG_CODE,
						 T.ORIG_ORG_NAME,
						 T.DEST_ORG_CODE,
						 T.DEST_ORG_NAME,
						 SUM(T.AMOUNT),
						 SUM(T.AMOUNT_FRT),
						 SUM(T.AMOUNT_PUP),
						 SUM(T.AMOUNT_DEL),
						 SUM(T.AMOUNT_PKG),
						 SUM(T.AMOUNT_DV),
						 SUM(T.AMOUNT_COD),
						 SUM(T.AMOUNT_OT),
						 P_BEGIN_DATE,
						 P_END_DATE
				FROM STV.T_STL_NDVS T
			 WHERE T.PERIOD >= V_START_PERIOD
						 AND T.PERIOD < V_END_PERIOD
						 AND T.AMOUNT <> 0
			 GROUP BY T.PERIOD,
								T.BUSINESS_CASE,
								T.PRODUCT_CODE,
								T.CUSTOMER_CODE,
								T.CUSTOMER_NAME,
								T.CUSTOMER_TYPE,
								T.ORIG_ORG_CODE,
								T.ORIG_ORG_NAME,
								T.DEST_ORG_CODE,
								T.DEST_ORG_NAME;
	
		--统计往来月汇总数据
		INSERT INTO STV.T_STL_NMVSI
			(ID,
			 PERIOD,
			 BUSINESS_CASE,
			 PRODUCT_CODE,
			 CUSTOMER_CODE,
			 CUSTOMER_NAME,
			 CUSTOMER_TYPE,
			 CREDIT_ORG_CODE,
			 CREDIT_ORG_NAME,
			 DEBIT_ORG_CODE,
			 DEBIT_ORG_NAME,
			 CREDIT_INVOICE_MARK,
			 DEBIT_INVOICE_MARK,
			 CREDIT_ORG_TYPE,
			 DEBIT_ORG_TYPE,
			 AMOUNT,
			 VOUCHER_BEGIN_TIME,
			 VOUCHER_END_TIME)
			SELECT SYS_GUID(),
						 T.PERIOD,
						 T.BUSINESS_CASE,
						 T.PRODUCT_CODE,
						 T.CUSTOMER_CODE,
						 T.CUSTOMER_NAME,
						 T.CUSTOMER_TYPE,
						 T.CREDIT_ORG_CODE,
						 T.CREDIT_ORG_NAME,
						 T.DEBIT_ORG_CODE,
						 T.DEBIT_ORG_NAME,
						 T.CREDIT_INVOICE_MARK,
						 T.DEBIT_INVOICE_MARK,
						 T.CREDIT_ORG_TYPE,
						 T.DEBIT_ORG_TYPE,
						 SUM(T.AMOUNT),
						 P_BEGIN_DATE,
						 P_END_DATE
				FROM STV.T_STL_NDVSI T
			 WHERE T.PERIOD >= V_START_PERIOD
						 AND T.PERIOD < V_END_PERIOD
						 AND T.AMOUNT <> 0
			 GROUP BY T.PERIOD,
								T.BUSINESS_CASE,
								T.PRODUCT_CODE,
								T.CUSTOMER_CODE,
								T.CUSTOMER_NAME,
								T.CUSTOMER_TYPE,
								T.CREDIT_ORG_CODE,
								T.CREDIT_ORG_NAME,
								T.DEBIT_ORG_CODE,
								T.DEBIT_ORG_NAME,
								T.CREDIT_INVOICE_MARK,
								T.DEBIT_INVOICE_MARK,
								T.CREDIT_ORG_TYPE,
								T.DEBIT_ORG_TYPE;
	
		COMMIT;
	
		RETURN TRUE;
	
	EXCEPTION
		WHEN OTHERS THEN
			--插入错误日志表
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_START_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_MONTHLY',
																				'凭证生成月汇总程序异常:' || SQLERRM);
		
			--返回失败标志
			RETURN FALSE;
		
	END FUNC_VOUCHER_MONTHLY;

	-----------------------------------------------------------------
	-- 01普通始发月报表入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NRFONO(P_PERIOD     VARCHAR2,
															 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
															 P_END_DATE   DATE -- 结束时间 2012-12-22
															 ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
	 IS
	
		V_BEGIN_PERIOD VARCHAR2(50);
		V_END_PERIOD   VARCHAR2(50);
	
	BEGIN
	
		V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
		V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_NRFONO T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
			USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;
	
		INSERT INTO STV.T_STL_MVR_NRFONO
			(ID,
			 PERIOD,
			 PRODUCT_CODE,
			 CUSTOMER_CODE,
			 CUSTOMER_NAME,
			 CUSTOMER_TYPE,
			 ORIG_ORG_CODE,
			 ORIG_ORG_NAME,
			 DEST_ORG_CODE,
			 DEST_ORG_NAME,
			 VOUCHER_BEGIN_TIME,
			 VOUCHER_END_TIME,
			 ALPWR_WO_DEST_RCVO_NPOD, --【1】
			 ALPWR_WO_DEST_RCVO_POD, --【2】
			 AL_DR_WO_DEST_RCVO_NPOD, --【3】
			 AL_DR_WO_DEST_RCVO_POD, --【4】
			 COD_PAY_WO_DEST_RCVO_NPOD, --【5】
			 COD_PAY_WO_DEST_RCVO_POD, --【6】
			 COD_PAY_WO_ORIG_RCVO_NPOD, --【7】
			 COD_PAY_WO_ORIG_RCVO_POD, --【8】
			 CUST_DRO_PAY_APPLY, --【9】
			 CUST_DRO_WO_DEST_RCVO_NPOD, --【10】
			 CUST_DRO_WO_DEST_RCVO_POD, --【11】
			 CUST_DRO_WO_ORIG_RCVO_NPOD, --【12】
			 CUST_DRO_WO_ORIG_RCVO_POD, --【13】
			 CUST_DRO_WO_ORIG_RCVT_NPOD, --【14】
			 CUST_DRO_WO_ORIG_RCVT_POD, --【15】
			 CUST_DRT_WO_DEST_RCVO_NPOD, --【16】
			 CUST_DRT_WO_DEST_RCVO_POD, --【17】
			 CUST_DRT_WO_ORIG_RCVO_NPOD, --【18】
			 CUST_DRT_WO_ORIG_RCVO_POD, --【19】
			 CUST_DR_OCD, --【20】
			 CUST_DR_OCH, --【21】
			 DEO_CD, --【22】
			 DEO_CH, --【23】
			 OTH_PAY_WO_DEST_RCVO_NPOD, --【24】
			 OTH_PAY_WO_DEST_RCVO_POD, --【25】
			 PL_COST_WO_DEST_RCVO_NPOD, --【26】
			 PL_COST_WO_DEST_RCVO_POD, --【27】
			 PL_DR_WO_DEST_RCVO_NPOD, --【28】
			 PL_DR_WO_DEST_RCVO_POD, --【29】
			 PODO_CASH_COLLECTED_COD, --【30】
			 PODO_CASH_COLLECTED_DEL, --【31】
			 PODO_CASH_COLLECTED_DV, --【32】
			 PODO_CASH_COLLECTED_FRT, --【33】
			 PODO_CASH_COLLECTED_OT, --【34】
			 PODO_CASH_COLLECTED_PKG, --【35】
			 PODO_CASH_COLLECTED_PUP, --【36】
			 PODO_DEST_RCV_NWO_COD, --【37】
			 PODO_DEST_RCV_NWO_DEL, --【38】
			 PODO_DEST_RCV_NWO_DV, --【39】
			 PODO_DEST_RCV_NWO_FRT, --【40】
			 PODO_DEST_RCV_NWO_OT, --【41】
			 PODO_DEST_RCV_NWO_PKG, --【42】
			 PODO_DEST_RCV_NWO_PUP, --【43】
			 PODO_DEST_RCV_WO_COD, --【44】
			 PODO_DEST_RCV_WO_DEL, --【45】
			 PODO_DEST_RCV_WO_DV, --【46】
			 PODO_DEST_RCV_WO_FRT, --【47】
			 PODO_DEST_RCV_WO_OT, --【48】
			 PODO_DEST_RCV_WO_PKG, --【49】
			 PODO_DEST_RCV_WO_PUP, --【50】
			 PODO_ORIG_RCV_NWO_COD, --【51】
			 PODO_ORIG_RCV_NWO_DEL, --【52】
			 PODO_ORIG_RCV_NWO_DV, --【53】
			 PODO_ORIG_RCV_NWO_FRT, --【54】
			 PODO_ORIG_RCV_NWO_OT, --【55】
			 PODO_ORIG_RCV_NWO_PKG, --【56】
			 PODO_ORIG_RCV_NWO_PUP, --【57】
			 PODO_ORIG_RCV_WO_COD, --【58】
			 PODO_ORIG_RCV_WO_DEL, --【59】
			 PODO_ORIG_RCV_WO_DV, --【60】
			 PODO_ORIG_RCV_WO_FRT, --【61】
			 PODO_ORIG_RCV_WO_OT, --【62】
			 PODO_ORIG_RCV_WO_PKG, --【63】
			 PODO_ORIG_RCV_WO_PUP, --【64】
			 UPDO_CASH_COLLECTED_COD, --【65】
			 UPDO_CASH_COLLECTED_DEL, --【66】
			 UPDO_CASH_COLLECTED_DV, --【67】
			 UPDO_CASH_COLLECTED_FRT, --【68】
			 UPDO_CASH_COLLECTED_OT, --【69】
			 UPDO_CASH_COLLECTED_PKG, --【70】
			 UPDO_CASH_COLLECTED_PUP, --【71】
			 UPDO_DEST_RCV_NWO_COD, --【72】
			 UPDO_DEST_RCV_NWO_DEL, --【73】
			 UPDO_DEST_RCV_NWO_DV, --【74】
			 UPDO_DEST_RCV_NWO_FRT, --【75】
			 UPDO_DEST_RCV_NWO_OT, --【76】
			 UPDO_DEST_RCV_NWO_PKG, --【77】
			 UPDO_DEST_RCV_NWO_PUP, --【78】
			 UPDO_DEST_RCV_WO_COD, --【79】
			 UPDO_DEST_RCV_WO_DEL, --【80】
			 UPDO_DEST_RCV_WO_DV, --【81】
			 UPDO_DEST_RCV_WO_FRT, --【82】
			 UPDO_DEST_RCV_WO_OT, --【83】
			 UPDO_DEST_RCV_WO_PKG, --【84】
			 UPDO_DEST_RCV_WO_PUP, --【85】
			 UPDO_ORIG_RCV_NWO_COD, --【86】
			 UPDO_ORIG_RCV_NWO_DEL, --【87】
			 UPDO_ORIG_RCV_NWO_DV, --【88】
			 UPDO_ORIG_RCV_NWO_FRT, --【89】
			 UPDO_ORIG_RCV_NWO_OT, --【90】
			 UPDO_ORIG_RCV_NWO_PKG, --【91】
			 UPDO_ORIG_RCV_NWO_PUP, --【92】
			 UPDO_ORIG_RCV_WO_COD, --【93】
			 UPDO_ORIG_RCV_WO_DEL, --【94】
			 UPDO_ORIG_RCV_WO_DV, --【95】
			 UPDO_ORIG_RCV_WO_FRT, --【96】
			 UPDO_ORIG_RCV_WO_OT, --【97】
			 UPDO_ORIG_RCV_WO_PKG, --【98】
			 UPDO_ORIG_RCV_WO_PUP, --【99】
			 URO_DEST_CD_NPOD, --【100】
			 URO_DEST_CD_POD, --【101】
			 URO_DEST_CH_NPOD, --【102】
			 URO_DEST_CH_POD, --【103】
			 URO_ORIG_CD_NPOD, --【104】
			 URO_ORIG_CD_POD, --【105】
			 URO_ORIG_CH_NPOD, --【106】
			 URO_ORIG_CH_POD, --【107】
			 POP_WO_DRO_POD, -- 【108】
			 POP_WO_DRO_NPOD --【109】
			 )
			SELECT SYS_GUID(),
						 P_PERIOD,
						 PRODUCT_CODE,
						 CUSTOMER_CODE,
						 CUSTOMER_NAME,
						 CUSTOMER_TYPE,
						 ORIG_ORG_CODE,
						 ORIG_ORG_NAME,
						 DEST_ORG_CODE,
						 DEST_ORG_NAME,
						 P_BEGIN_DATE,
						 P_END_DATE,
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.ALPWR_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) ALPWR_WO_DEST_RCVO_NPOD, --应付到达代理/落地配代理成本冲01应收到付运费未签收【1】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.ALPWR_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) ALPWR_WO_DEST_RCVO_POD, --应付到达代理/落地配代理成本冲01应收到付运费已签收【2】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AL_DR_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) AL_DR_WO_DEST_RCVO_NPOD, --预收空运/落地配代理冲01应收到付运费未签收【3】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AL_DR_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) AL_DR_WO_DEST_RCVO_POD, --预收空运/落地配代理冲01应收到付运费已签收【4】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) COD_PAY_WO_DEST_RCVO_NPOD, --应付代收货款冲01应收到付运费未签收【5】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) COD_PAY_WO_DEST_RCVO_POD, --应付代收货款冲01应收到付运费已签收【6】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) COD_PAY_WO_ORIG_RCVO_NPOD, --应付代收货款冲01应收始发运费未签收【7】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) COD_PAY_WO_ORIG_RCVO_POD, --应付代收货款冲01应收始发运费已签收【8】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_PAY_APPLY, --01始发退预收付款申请【9】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_DEST_RCVO_NPOD, --01预收客户冲01应收到付运费未签收【10】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_DEST_RCVO_POD, --01预收客户冲01应收到付运费已签收【11】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_ORIG_RCVO_NPOD, --01预收客户冲01应收始发运费未签收【12】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_ORIG_RCVO_POD, --01预收客户冲01应收始发运费已签收【13】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_NPODD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_ORIG_RCVT_NPOD, --01预收客户冲02应收始发运费未签收【14】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_PODD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_ORIG_RCVT_POD, --01预收客户冲02应收始发运费已签收【15】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_DEST_RCVO_NPOD, --02预收客户冲01应收到付运费未签收【16】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_DEST_RCVO_POD, --02预收客户冲01应收到付运费已签收【17】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_ORIG_RCVO_NPOD, --02预收客户冲01应收始发运费未签收【18】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_ORIG_RCVO_POD, --02预收客户冲01应收始发运费已签收【19】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DR_OCD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DR_OCD, --预收客户银行【20】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DR_OCH,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DR_OCH, --预收客户现金【21】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.DEO_CD,
														NVL(AMOUNT, 0),
														0)),
								 0) DEO_CD, --开单银行卡【22】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.DEO_CH,
														NVL(AMOUNT, 0),
														0)),
								 0) DEO_CH, --开单现金【23】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OTH_PAY_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) OTH_PAY_WO_DEST_RCVO_NPOD, --其他应付冲01应收到付运费未签收【24】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OTH_PAY_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) OTH_PAY_WO_DEST_RCVO_POD, --其他应付冲01应收到付运费已签收【25】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_WO_DEST_RCVO_NPOD, --应付偏线代理成本冲01应收到付运费未签收【26】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_WO_DEST_RCVO_POD, --应付偏线代理成本冲01应收到付运费已签收【27】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_DR_WO_DEST_RCVO_NPOD, --预收偏线代理冲01应收到付运费未签收【28】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_DR_WO_DEST_RCVO_POD, --预收偏线代理冲01应收到付运费已签收【29】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_CASH_COLLECTED,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) PODO_CASH_COLLECTED_COD, --签收时现付已收款金额_代收货款手续费【30】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_CASH_COLLECTED,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) PODO_CASH_COLLECTED_DEL, --签收时现付已收款金额_送货费【31】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_CASH_COLLECTED,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) PODO_CASH_COLLECTED_DV, --签收时现付已收款金额_保价费【32】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_CASH_COLLECTED,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) PODO_CASH_COLLECTED_FRT, --签收时现付已收款金额_公布价运费【33】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_CASH_COLLECTED,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) PODO_CASH_COLLECTED_OT, --签收时现付已收款金额_其它费用【34】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_CASH_COLLECTED,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) PODO_CASH_COLLECTED_PKG, --签收时现付已收款金额_包装费【35】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_CASH_COLLECTED,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) PODO_CASH_COLLECTED_PUP, --签收时现付已收款金额_接货费【36】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_NWO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) PODO_DEST_RCV_NWO_COD, --签收时到达应收未核销金额_代收货款手续费【37】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_NWO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) PODO_DEST_RCV_NWO_DEL, --签收时到达应收未核销金额_送货费【38】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_NWO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) PODO_DEST_RCV_NWO_DV, --签收时到达应收未核销金额_保价费【39】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_NWO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) PODO_DEST_RCV_NWO_FRT, --签收时到达应收未核销金额_公布价运费【40】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_NWO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) PODO_DEST_RCV_NWO_OT, --签收时到达应收未核销金额_其它费用【41】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_NWO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) PODO_DEST_RCV_NWO_PKG, --签收时到达应收未核销金额_包装费【42】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_NWO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) PODO_DEST_RCV_NWO_PUP, --签收时到达应收未核销金额_接货费【43】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_WO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) PODO_DEST_RCV_WO_COD, --签收时到达应收已核销金额_代收货款手续费【44】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_WO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) PODO_DEST_RCV_WO_DEL, --签收时到达应收已核销金额_送货费【45】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_WO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) PODO_DEST_RCV_WO_DV, --签收时到达应收已核销金额_保价费【46】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_WO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) PODO_DEST_RCV_WO_FRT, --签收时到达应收已核销金额_公布价运费【47】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_WO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) PODO_DEST_RCV_WO_OT, --签收时到达应收已核销金额_其它费用【48】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_WO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) PODO_DEST_RCV_WO_PKG, --签收时到达应收已核销金额_包装费【49】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_WO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) PODO_DEST_RCV_WO_PUP, --签收时到达应收已核销金额_接货费【50】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_NWO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) PODO_ORIG_RCV_NWO_COD, --签收时始发应收未核销金额_代收货款手续费【51】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_NWO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) PODO_ORIG_RCV_NWO_DEL, --签收时始发应收未核销金额_送货费【52】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_NWO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) PODO_ORIG_RCV_NWO_DV, --签收时始发应收未核销金额_保价费【53】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_NWO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) PODO_ORIG_RCV_NWO_FRT, --签收时始发应收未核销金额_公布价运费【54】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_NWO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) PODO_ORIG_RCV_NWO_OT, --签收时始发应收未核销金额_其它费用【55】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_NWO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) PODO_ORIG_RCV_NWO_PKG, --签收时始发应收未核销金额_包装费【56】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_NWO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) PODO_ORIG_RCV_NWO_PUP, --签收时始发应收未核销金额_接货费【57】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_WO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) PODO_ORIG_RCV_WO_COD, --签收时始发应收已核销金额_代收货款手续费【58】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_WO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) PODO_ORIG_RCV_WO_DEL, --签收时始发应收已核销金额_送货费【59】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_WO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) PODO_ORIG_RCV_WO_DV, --签收时始发应收已核销金额_保价费【60】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_WO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) PODO_ORIG_RCV_WO_FRT, --签收时始发应收已核销金额_公布价运费【61】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_WO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) PODO_ORIG_RCV_WO_OT, --签收时始发应收已核销金额_其它费用【62】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_WO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) PODO_ORIG_RCV_WO_PKG, --签收时始发应收已核销金额_包装费【63】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_WO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) PODO_ORIG_RCV_WO_PUP, --签收时始发应收已核销金额_接货费【64】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_CASH_COLLECTED,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) UPDO_CASH_COLLECTED_COD, --反签收时现付已收款金额_代收货款手续费【65】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_CASH_COLLECTED,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) UPDO_CASH_COLLECTED_DEL, --反签收时现付已收款金额_送货费【66】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_CASH_COLLECTED,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) UPDO_CASH_COLLECTED_DV, --反签收时现付已收款金额_保价费【67】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_CASH_COLLECTED,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) UPDO_CASH_COLLECTED_FRT, --反签收时现付已收款金额_公布价运费【68】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_CASH_COLLECTED,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) UPDO_CASH_COLLECTED_OT, --反签收时现付已收款金额_其它费用【69】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_CASH_COLLECTED,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) UPDO_CASH_COLLECTED_PKG, --反签收时现付已收款金额_包装费【70】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_CASH_COLLECTED,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) UPDO_CASH_COLLECTED_PUP, --反签收时现付已收款金额_接货费【71】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_NWO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) UPDO_DEST_RCV_NWO_COD, --反签收时到达应收未核销金额_代收货款手续费【72】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_NWO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) UPDO_DEST_RCV_NWO_DEL, --反签收时到达应收未核销金额_送货费【73】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_NWO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) UPDO_DEST_RCV_NWO_DV, --反签收时到达应收未核销金额_保价费【74】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_NWO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) UPDO_DEST_RCV_NWO_FRT, --反签收时到达应收未核销金额_公布价运费【75】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_NWO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) UPDO_DEST_RCV_NWO_OT, --反签收时到达应收未核销金额_其它费用【76】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_NWO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) UPDO_DEST_RCV_NWO_PKG, --反签收时到达应收未核销金额_包装费【77】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_NWO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) UPDO_DEST_RCV_NWO_PUP, --反签收时到达应收未核销金额_接货费【78】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_WO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) UPDO_DEST_RCV_WO_COD, --反签收时到达应收已核销金额_代收货款手续费【79】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_WO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) UPDO_DEST_RCV_WO_DEL, --反签收时到达应收已核销金额_送货费【80】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_WO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) UPDO_DEST_RCV_WO_DV, --反签收时到达应收已核销金额_保价费【81】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_WO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) UPDO_DEST_RCV_WO_FRT, --反签收时到达应收已核销金额_公布价运费【82】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_WO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) UPDO_DEST_RCV_WO_OT, --反签收时到达应收已核销金额_其它费用【83】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_WO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) UPDO_DEST_RCV_WO_PKG, --反签收时到达应收已核销金额_包装费【84】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_WO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) UPDO_DEST_RCV_WO_PUP, --反签收时到达应收已核销金额_接货费【85】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_NWO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) UPDO_ORIG_RCV_NWO_COD, --反签收时始发应收未核销金额_代收货款手续费【86】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_NWO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) UPDO_ORIG_RCV_NWO_DEL, --反签收时始发应收未核销金额_送货费【87】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_NWO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) UPDO_ORIG_RCV_NWO_DV, --反签收时始发应收未核销金额_保价费【88】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_NWO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) UPDO_ORIG_RCV_NWO_FRT, --反签收时始发应收未核销金额_公布价运费【89】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_NWO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) UPDO_ORIG_RCV_NWO_OT, --反签收时始发应收未核销金额_其它费用【90】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_NWO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) UPDO_ORIG_RCV_NWO_PKG, --反签收时始发应收未核销金额_包装费【91】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_NWO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) UPDO_ORIG_RCV_NWO_PUP, --反签收时始发应收未核销金额_接货费【92】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_WO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) UPDO_ORIG_RCV_WO_COD, --反签收时始发应收已核销金额_代收货款手续费【93】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_WO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) UPDO_ORIG_RCV_WO_DEL, --反签收时始发应收已核销金额_送货费【94】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_WO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) UPDO_ORIG_RCV_WO_DV, --反签收时始发应收已核销金额_保价费【95】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_WO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) UPDO_ORIG_RCV_WO_FRT, --反签收时始发应收已核销金额_公布价运费【96】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_WO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) UPDO_ORIG_RCV_WO_OT, --反签收时始发应收已核销金额_其它费用【97】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_WO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) UPDO_ORIG_RCV_WO_PKG, --反签收时始发应收已核销金额_包装费【98】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_WO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) UPDO_ORIG_RCV_WO_PUP, --反签收时始发应收已核销金额_接货费【99】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_DEST_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_DEST_CD_NPOD, --还款银行未签收【100】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_DEST_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_DEST_CD_POD, --还款银行已签收【101】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_DEST_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_DEST_CH_NPOD, --还款现金未签收【102】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_DEST_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_DEST_CH_POD, --还款现金已签收【103】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_ORIG_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_ORIG_CD_NPOD, --还款银行未签收【104】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_ORIG_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_ORIG_CD_POD, --还款银行已签收【105】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_ORIG_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_ORIG_CH_NPOD, --还款现金未签收【106】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_ORIG_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_ORIG_CH_POD, --还款现金已签收【107】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.POP_WO_DRO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) POP_WO_DRO_POD, --其他应付冲01应收到付运费已签收【108】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.POP_WO_DRO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) POP_WO_DRO_NPOD --其他应付冲01应收到付运费未签收【109】
				FROM STV.T_STL_NMVS T
			 WHERE T.PERIOD >= V_BEGIN_PERIOD
						 AND T.PERIOD < V_END_PERIOD
						 AND T.AMOUNT <> 0
						 AND T.BUSINESS_CASE IN
						 (PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVO_POD,
									PKG_STL_VCH_COMMON.DEO_CH,
									PKG_STL_VCH_COMMON.URO_ORIG_CD_POD,
									PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVO_NPOD,
									PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_NPOD,
									PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_POD,
									PKG_STL_VCH_COMMON.DEO_CD,
									PKG_STL_VCH_COMMON.PODO_DEST_RCV_WO,
									PKG_STL_VCH_COMMON.URO_DEST_CD_NPOD,
									PKG_STL_VCH_COMMON.URO_DEST_CD_POD,
									PKG_STL_VCH_COMMON.PODO_ORIG_RCV_NWO,
									PKG_STL_VCH_COMMON.UPDO_DEST_RCV_WO,
									PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_NWO,
									PKG_STL_VCH_COMMON.URO_ORIG_CH_NPOD,
									PKG_STL_VCH_COMMON.CUST_DRO_PAY_APPLY,
									PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVO_NPOD,
									PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVO_POD,
									PKG_STL_VCH_COMMON.OTH_PAY_WO_DEST_RCVO_NPOD,
									PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVO_POD,
									PKG_STL_VCH_COMMON.PODO_CASH_COLLECTED,
									PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_WO,
									PKG_STL_VCH_COMMON.URO_ORIG_CD_NPOD,
									PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVO_NPOD,
									PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVO_POD,
									PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_POD,
									PKG_STL_VCH_COMMON.CUST_DR_OCD,
									PKG_STL_VCH_COMMON.OTH_PAY_WO_DEST_RCVO_POD,
									PKG_STL_VCH_COMMON.UPDO_CASH_COLLECTED,
									PKG_STL_VCH_COMMON.AL_DR_WO_DEST_RCVO_NPOD,
									PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_NPOD,
									PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_PODD,
									PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_POD,
									PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVO_NPOD,
									PKG_STL_VCH_COMMON.UPDO_DEST_RCV_NWO,
									PKG_STL_VCH_COMMON.URO_DEST_CH_POD,
									PKG_STL_VCH_COMMON.URO_ORIG_CH_POD,
									PKG_STL_VCH_COMMON.ALPWR_WO_DEST_RCVO_POD,
									PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_NPODD,
									PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_NPOD,
									PKG_STL_VCH_COMMON.ALPWR_WO_DEST_RCVO_NPOD,
									PKG_STL_VCH_COMMON.AL_DR_WO_DEST_RCVO_POD,
									PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_POD,
									PKG_STL_VCH_COMMON.CUST_DR_OCH,
									PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_NPOD,
									PKG_STL_VCH_COMMON.PODO_DEST_RCV_NWO,
									PKG_STL_VCH_COMMON.PODO_ORIG_RCV_WO,
									PKG_STL_VCH_COMMON.URO_DEST_CH_NPOD,
									PKG_STL_VCH_COMMON.POP_WO_DRO_POD,
									PKG_STL_VCH_COMMON.POP_WO_DRO_NPOD)
			 GROUP BY PRODUCT_CODE,
								CUSTOMER_CODE,
								CUSTOMER_NAME,
								CUSTOMER_TYPE,
								ORIG_ORG_CODE,
								ORIG_ORG_NAME,
								DEST_ORG_CODE,
								DEST_ORG_NAME;
	
		COMMIT;
	
		RETURN TRUE;
	
	EXCEPTION
		WHEN OTHERS THEN
			--插入错误日志表
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NRFONO',
																				'凭证生成01普通始发报表程序异常:' || SQLERRM);
		
			--返回失败标志
			RETURN FALSE;
		
	END FUNC_VOUCHER_NRFONO;

	-----------------------------------------------------------------
	-- 01特殊始发月报表入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NRFOSO(P_PERIOD     VARCHAR2,
															 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
															 P_END_DATE   DATE -- 结束时间 2012-12-22
															 ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
	 IS
	
		V_BEGIN_PERIOD VARCHAR2(50);
		V_END_PERIOD   VARCHAR2(50);
	
	BEGIN
	
		V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
		V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_NRFOSO T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
			USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;
	
		INSERT INTO STV.T_STL_MVR_NRFOSO
			(ID,
			 PERIOD,
			 PRODUCT_CODE,
			 CUSTOMER_CODE,
			 CUSTOMER_NAME,
			 CUSTOMER_TYPE,
			 ORIG_ORG_CODE,
			 ORIG_ORG_NAME,
			 DEST_ORG_CODE,
			 DEST_ORG_NAME,
			 VOUCHER_BEGIN_TIME,
			 VOUCHER_END_TIME,
			 AC_CASH, --【1】
			 AC_ORIG_RCV_NWO, --【2】
			 AC_ORIG_RCV_WO, --【3】
			 BD_WO_DEST_RCVO_POD, --【4】
			 BD_WO_ORIG_RCVO_POD, --【5】
			 CLAIM_DESTO_INCOME, --【6】
			 CLAIM_DESTO_WO_DEST_RCVO_NPOD, --【7】
			 CLAIM_DESTO_WO_DEST_RCVO_POD, --【8】
			 CLAIM_DESTT_WO_DEST_RCVO_NPOD, --【9】
			 CLAIM_DESTT_WO_DEST_RCVO_POD, --【10】
			 CLAIM_ORIGO_COST, --【11】
			 CLAIM_ORIGO_INCOME, --【12】
			 CLAIM_ORIGO_ORIG_RCVO_NPOD, --【13】
			 CLAIM_ORIGO_PAY_APPLY, --【14】
			 CLAIM_ORIGO_WO_ORIG_RCVO_POD, --【15】
			 CLAIM_ORIGO_WO_ORIG_RCVT_NPOD, --【16】
			 CLAIM_ORIGO_WO_ORIG_RCVT_POD, --【17】
			 CLAIM_ORIGT_ORIG_RCVO_POD, --【18】
			 CLAIM_ORIGT_WO_ORIG_RCVO_NPOD, --【19】
			 CPO_ORIG_PAY_APPLY, --【20】
			 EX_DEST_RCVO_POD, --【21】
			 EX_ORIG_RCVO_POD, --【22】
			 OR_BAD_WO_RCVO, --【23】
			 OR_CD_PBIO, --【24】
			 OR_CD_UR_RCVO, --【25】
			 OR_CH_PBIO, --【26】
			 OR_CH_UR_RCVO, --【27】
			 OR_CLAIM_PAYO_WO_RCVO, --【28】
			 OR_CLAIM_PAYO_WO_RCVT, --【29】
			 OR_CLAIM_PAYT_WO_RCVO, --【30】
			 OR_COD_PAY_WO_RCVO, --【31】
			 OR_CUST_DRO_WO_RCVO, --【32】
			 OR_CUST_DRO_WO_RCVT, --【33】
			 OR_CUST_DRT_WO_RCVO, --【34】
			 OR_EX_WO_RCVO, --【35】
			 OR_RCVO_PBI, --【36】
			 RD_DESTO_DEST_RCVO_POD, --【37】
			 RD_DESTO_INCOME, --【38】
			 RD_DESTO_WO_DEST_RCVO_NPOD, --【39】
			 RD_DESTT_WO_DEST_RCVO_NPOD, --【40】
			 RD_DESTT_WO_DEST_RCVO_POD, --【41】
			 RD_ORIGO_COST, --【42】
			 RD_ORIGO_INCOME, --【43】
			 RD_ORIGO_PAY_APPLY, --【44】
			 RD_ORIGO_WO_ORIG_RCVO_NPOD, --【45】
			 RD_ORIGO_WO_ORIG_RCVO_POD, --【46】
			 RD_ORIGO_WO_ORIG_RCVT_NPOD, --【47】
			 RD_ORIGO_WO_ORIG_RCVT_POD, --【48】
			 RD_ORIGT_WO_ORIG_RCVO_NPOD, --【49】
			 RD_ORIGT_WO_ORIG_RCVO_POD, --【50】
			 SFO_PAY_APPLY --【51】
			 )
			SELECT SYS_GUID(),
						 P_PERIOD,
						 PRODUCT_CODE,
						 CUSTOMER_CODE,
						 CUSTOMER_NAME,
						 CUSTOMER_TYPE,
						 ORIG_ORG_CODE,
						 ORIG_ORG_NAME,
						 DEST_ORG_CODE,
						 DEST_ORG_NAME,
						 P_BEGIN_DATE,
						 P_END_DATE,
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AC_CASHO,
														NVL(AMOUNT, 0),
														0)),
								 0) AC_CASH, --开单且为现金银行卡【1】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AC_ORIG_RCV_NWOO,
														NVL(AMOUNT, 0),
														0)),
								 0) AC_ORIG_RCV_NWO, --开单且为月结临时欠款网上支付未核销【2】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AC_ORIG_RCV_WOO,
														NVL(AMOUNT, 0),
														0)),
								 0) AC_ORIG_RCV_WO, --开单且为月结临时欠款网上支付已核销【3】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.BD_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) BD_WO_DEST_RCVO_POD, --坏账冲01应收到付运费已签收【4】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.BD_WO_ORIG_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) BD_WO_ORIG_RCVO_POD, --坏账冲01应收始发运费已签收【5】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTO_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTO_INCOME, --01理赔冲收入【6】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTO_WO_DEST_RCVO_NPOD, --01理赔冲01到达应收未签收【7】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTO_WO_DEST_RCVO_POD, --01理赔冲到01达应收已签收【8】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTT_WO_DEST_RCVO_NPOD, --02理赔冲01到达应收未签收【9】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTT_WO_DEST_RCVO_POD, --02理赔冲01到达应收已签收【10】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGO_COST,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGO_COST, --01理赔入成本【11】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGO_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGO_INCOME, --01理赔冲收入【12】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGO_ORIG_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGO_ORIG_RCVO_NPOD, --01理赔冲01始发应收未签收【13】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGO_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGO_PAY_APPLY, --01理赔付款申请【14】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGO_WO_ORIG_RCVO_POD, --01理赔冲01始发应收已签收【15】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGO_WO_ORIG_RCVT_NPOD, --01理赔冲02始发应收未签收【16】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGO_WO_ORIG_RCVT_POD, --01理赔冲02始发应收已签收【17】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGT_ORIG_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGT_ORIG_RCVO_POD, --02理赔冲01始发应收已签收【18】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGT_WO_ORIG_RCVO_NPOD, --02理赔冲01始发应收未签收【19】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CPO_ORIG_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CPO_ORIG_PAY_APPLY, --01始发服务补救付款申请【20】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.EX_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) EX_DEST_RCVO_POD, --01应收到付运费已签收【21】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.EX_ORIG_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) EX_ORIG_RCVO_POD, --01应收始发运费已签收【22】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_BAD_WO_RCVO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_BAD_WO_RCVO, --坏账损失冲01小票应收【23】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CD_PBIO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CD_PBIO, --01小票银行主营业务收入【24】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CD_UR_RCVO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CD_UR_RCVO, --还款银行冲01小票应收【25】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CH_PBIO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CH_PBIO, --01小票现金主营业务收入【26】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CH_UR_RCVO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CH_UR_RCVO, --还款现金冲01小票应收【27】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CLAIM_PAYO_WO_RCVO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CLAIM_PAYO_WO_RCVO, --01应付理赔冲01小票应收【28】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CLAIM_PAYO_WO_RCVTP,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CLAIM_PAYO_WO_RCVT, --01应付理赔冲02小票应收【29】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CLAIM_PAYT_WO_RCVO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CLAIM_PAYT_WO_RCVO, --02应付理赔冲01小票应收【30】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_COD_PAY_WO_RCVO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_COD_PAY_WO_RCVO, --应付代收货款冲01小票应收【31】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CUST_DRO_WO_RCVO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CUST_DRO_WO_RCVO, --01预收客户冲01小票应收【32】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CUST_DRO_WO_RCVTD,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CUST_DRO_WO_RCVT, --01预收客户冲02小票应收【33】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CUST_DRT_WO_RCVO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CUST_DRT_WO_RCVO, --02预收客户冲01小票应收【34】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_EX_WO_RCVO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_EX_WO_RCVO, --异常冲收入冲01小票应收【35】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_RCVO_PBI,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_RCVO_PBI, --01小票应收主营业务收入【36】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_DEST_RCVO_POD, --01退运费冲01到达应收已签收【37】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_INCOME, --01退运费冲收入【38】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_WO_DEST_RCVO_NPOD, --01退运费冲01到达应收未签收【39】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTT_WO_DEST_RCVO_NPOD, --02退运费冲01到达应收未签收【40】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTT_WO_DEST_RCVO_POD, --02退运费冲01到达应收已签收【41】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGO_COST,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGO_COST, --01退运费入成本【42】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGO_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGO_INCOME, --01退运费冲收入【43】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGO_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGO_PAY_APPLY, --01退运费付款申请【44】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGO_WO_ORIG_RCVO_NPOD, --01退运费冲01始发应收未签收【45】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGO_WO_ORIG_RCVO_POD, --01退运费冲01始发应收已签收【46】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGO_WO_ORIG_RCVT_NPOD, --01退运费冲02始发应收未签收【47】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGO_WO_ORIG_RCVT_POD, --01退运费冲02始发应收已签收【48】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGT_WO_ORIG_RCVO_NPOD, --02退运费冲01始发应收未签收【49】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGT_WO_ORIG_RCVO_POD, --02退运费冲01始发应收已签收【50】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.SFO_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) SFO_PAY_APPLY --01装卸费付款申请【51】
				FROM STV.T_STL_NMVS T
			 WHERE T.PERIOD >= V_BEGIN_PERIOD
						 AND T.PERIOD < V_END_PERIOD
						 AND T.AMOUNT <> 0
						 AND T.BUSINESS_CASE IN
						 (PKG_STL_VCH_COMMON.BD_WO_DEST_RCVO_POD,
									PKG_STL_VCH_COMMON.CLAIM_DESTO_INCOME,
									PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_POD,
									PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_NPODP,
									PKG_STL_VCH_COMMON.CLAIM_ORIGT_ORIG_RCVO_POD,
									PKG_STL_VCH_COMMON.EX_ORIG_RCVO_POD,
									PKG_STL_VCH_COMMON.OR_CD_UR_RCVO,
									PKG_STL_VCH_COMMON.OR_CH_UR_RCVO,
									PKG_STL_VCH_COMMON.OR_CLAIM_PAYT_WO_RCVO,
									PKG_STL_VCH_COMMON.AC_CASHO,
									PKG_STL_VCH_COMMON.AC_ORIG_RCV_NWOO,
									PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_NPOD,
									PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVO_POD,
									PKG_STL_VCH_COMMON.OR_BAD_WO_RCVO,
									PKG_STL_VCH_COMMON.OR_COD_PAY_WO_RCVO,
									PKG_STL_VCH_COMMON.RD_ORIGO_COST,
									PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_POD,
									PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_NPOD,
									PKG_STL_VCH_COMMON.EX_DEST_RCVO_POD,
									PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_PODP,
									PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_NPOD,
									PKG_STL_VCH_COMMON.SFO_PAY_APPLY,
									PKG_STL_VCH_COMMON.CLAIM_ORIGO_COST,
									PKG_STL_VCH_COMMON.CLAIM_ORIGO_ORIG_RCVO_NPOD,
									PKG_STL_VCH_COMMON.CLAIM_ORIGO_PAY_APPLY,
									PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVO_NPOD,
									PKG_STL_VCH_COMMON.OR_CLAIM_PAYO_WO_RCVO,
									PKG_STL_VCH_COMMON.OR_CUST_DRT_WO_RCVO,
									PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVO_POD,
									PKG_STL_VCH_COMMON.RD_DESTO_INCOME,
									PKG_STL_VCH_COMMON.AC_ORIG_RCV_WOO,
									PKG_STL_VCH_COMMON.OR_CUST_DRO_WO_RCVO,
									PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVO_NPOD,
									PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_POD,
									PKG_STL_VCH_COMMON.CLAIM_ORIGO_INCOME,
									PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_PODP,
									PKG_STL_VCH_COMMON.CPO_ORIG_PAY_APPLY,
									PKG_STL_VCH_COMMON.OR_CH_PBIO,
									PKG_STL_VCH_COMMON.OR_CUST_DRO_WO_RCVTD,
									PKG_STL_VCH_COMMON.OR_RCVO_PBI,
									PKG_STL_VCH_COMMON.RD_ORIGO_PAY_APPLY,
									PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVO_POD,
									PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_NPODP,
									PKG_STL_VCH_COMMON.OR_CLAIM_PAYO_WO_RCVTP,
									PKG_STL_VCH_COMMON.OR_EX_WO_RCVO,
									PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVO_NPOD,
									PKG_STL_VCH_COMMON.BD_WO_ORIG_RCVO_POD,
									PKG_STL_VCH_COMMON.OR_CD_PBIO,
									PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_NPOD,
									PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_POD,
									PKG_STL_VCH_COMMON.RD_ORIGO_INCOME)
			 GROUP BY PRODUCT_CODE,
								CUSTOMER_CODE,
								CUSTOMER_NAME,
								CUSTOMER_TYPE,
								ORIG_ORG_CODE,
								ORIG_ORG_NAME,
								DEST_ORG_CODE,
								DEST_ORG_NAME;
	
		COMMIT;
	
		RETURN TRUE;
	
	EXCEPTION
		WHEN OTHERS THEN
			--插入错误日志表
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NRFOSO',
																				'凭证生成01特殊始发报表程序异常:' || SQLERRM);
		
			--返回失败标志
			RETURN FALSE;
		
	END FUNC_VOUCHER_NRFOSO;

	-----------------------------------------------------------------
	-- 02普通始发月报表入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NRFONT(P_PERIOD     VARCHAR2,
															 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
															 P_END_DATE   DATE -- 结束时间 2012-12-22
															 ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
	 IS
	
		V_BEGIN_PERIOD VARCHAR2(50);
		V_END_PERIOD   VARCHAR2(50);
	
	BEGIN
	
		V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
		V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_NRFONT T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
			USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;
	
		INSERT INTO STV.T_STL_MVR_NRFONT
			(ID,
			 PERIOD,
			 PRODUCT_CODE,
			 CUSTOMER_CODE,
			 CUSTOMER_NAME,
			 CUSTOMER_TYPE,
			 ORIG_ORG_CODE,
			 ORIG_ORG_NAME,
			 DEST_ORG_CODE,
			 DEST_ORG_NAME,
			 VOUCHER_BEGIN_TIME,
			 VOUCHER_END_TIME,
			 ALPWR_WO_DEST_RCVT_NPOD, --【1】
			 ALPWR_WO_DEST_RCVT_POD, --【2】
			 AL_DR_WO_DEST_RCVT_NPOD, --【3】
			 AL_DR_WO_DEST_RCVT_POD, --【4】
			 COD_PAY_WO_DEST_RCVT_NPOD, --【5】
			 COD_PAY_WO_DEST_RCVT_POD, --【6】
			 COD_PAY_WO_ORIG_RCVT_NPOD, --【7】
			 COD_PAY_WO_ORIG_RCVT_POD, --【8】
			 CUST_DRO_PAY_APPLY, --【9】
			 CUST_DRO_WO_DEST_RCVT_NPOD, --【10】
			 CUST_DRO_WO_DEST_RCVT_POD, --【11】
			 CUST_DRO_WO_ORIG_RCVT_NPOD, --【12】
			 CUST_DRO_WO_ORIG_RCVT_POD, --【13】
			 CUST_DRT_PAY_APPLY, --【14】
			 CUST_DRT_WO_DEST_RCVT_NPOD, --【15】
			 CUST_DRT_WO_DEST_RCVT_POD, --【16】
			 CUST_DRT_WO_ORIG_RCVO_NPOD, --【17】
			 CUST_DRT_WO_ORIG_RCVO_POD, --【18】
			 CUST_DRT_WO_ORIG_RCVT_NPOD, --【19】
			 CUST_DRT_WO_ORIG_RCVT_POD, --【20】
			 CUST_DR_OCD, --【21】
			 CUST_DR_OCH, --【22】
			 CUST_DR_TCD, --【23】
			 CUST_DR_TCH, --【24】
			 DEO_CD, --【25】
			 DEO_CH, --【26】
			 DET_CD, --【27】
			 DET_CH, --【28】
			 OTH_PAY_WO_DEST_RCVT_NPOD, --【29】
			 OTH_PAY_WO_DEST_RCVT_POD, --【30】
			 PL_COST_WO_DEST_RCVT_NPOD, --【31】
			 PL_COST_WO_DEST_RCVT_POD, --【32】
			 PL_DR_WO_DEST_RCVT_NPOD, --【33】
			 PL_DR_WO_DEST_RCVT_POD, --【34】
			 PODT_CASH_COLLECTED_COD, --【35】
			 PODT_CASH_COLLECTED_DEL, --【36】
			 PODT_CASH_COLLECTED_DV, --【37】
			 PODT_CASH_COLLECTED_FRT, --【38】
			 PODT_CASH_COLLECTED_OT, --【39】
			 PODT_CASH_COLLECTED_PKG, --【40】
			 PODT_CASH_COLLECTED_PUP, --【41】
			 PODT_DEST_RCV_NWO_COD, --【42】
			 PODT_DEST_RCV_NWO_DEL, --【43】
			 PODT_DEST_RCV_NWO_DV, --【44】
			 PODT_DEST_RCV_NWO_FRT, --【45】
			 PODT_DEST_RCV_NWO_OT, --【46】
			 PODT_DEST_RCV_NWO_PKG, --【47】
			 PODT_DEST_RCV_NWO_PUP, --【48】
			 PODT_DEST_RCV_WO_COD, --【49】
			 PODT_DEST_RCV_WO_DEL, --【50】
			 PODT_DEST_RCV_WO_DV, --【51】
			 PODT_DEST_RCV_WO_FRT, --【52】
			 PODT_DEST_RCV_WO_OT, --【53】
			 PODT_DEST_RCV_WO_PKG, --【54】
			 PODT_DEST_RCV_WO_PUP, --【55】
			 PODT_ORIG_RCV_NWO_COD, --【56】
			 PODT_ORIG_RCV_NWO_DEL, --【57】
			 PODT_ORIG_RCV_NWO_DV, --【58】
			 PODT_ORIG_RCV_NWO_FRT, --【59】
			 PODT_ORIG_RCV_NWO_OT, --【60】
			 PODT_ORIG_RCV_NWO_PKG, --【61】
			 PODT_ORIG_RCV_NWO_PUP, --【62】
			 PODT_ORIG_RCV_WO_COD, --【63】
			 PODT_ORIG_RCV_WO_DEL, --【64】
			 PODT_ORIG_RCV_WO_DV, --【65】
			 PODT_ORIG_RCV_WO_FRT, --【66】
			 PODT_ORIG_RCV_WO_OT, --【67】
			 PODT_ORIG_RCV_WO_PKG, --【68】
			 PODT_ORIG_RCV_WO_PUP, --【69】
			 UPDT_CASH_COLLECTED_COD, --【70】
			 UPDT_CASH_COLLECTED_DEL, --【71】
			 UPDT_CASH_COLLECTED_DV, --【72】
			 UPDT_CASH_COLLECTED_FRT, --【73】
			 UPDT_CASH_COLLECTED_OT, --【74】
			 UPDT_CASH_COLLECTED_PKG, --【75】
			 UPDT_CASH_COLLECTED_PUP, --【76】
			 UPDT_DEST_RCV_NWO_COD, --【77】
			 UPDT_DEST_RCV_NWO_DEL, --【78】
			 UPDT_DEST_RCV_NWO_DV, --【79】
			 UPDT_DEST_RCV_NWO_FRT, --【80】
			 UPDT_DEST_RCV_NWO_OT, --【81】
			 UPDT_DEST_RCV_NWO_PKG, --【82】
			 UPDT_DEST_RCV_NWO_PUP, --【83】
			 UPDT_DEST_RCV_WO_COD, --【84】
			 UPDT_DEST_RCV_WO_DEL, --【85】
			 UPDT_DEST_RCV_WO_DV, --【86】
			 UPDT_DEST_RCV_WO_FRT, --【87】
			 UPDT_DEST_RCV_WO_OT, --【88】
			 UPDT_DEST_RCV_WO_PKG, --【89】
			 UPDT_DEST_RCV_WO_PUP, --【90】
			 UPDT_ORIG_RCV_NWO_COD, --【91】
			 UPDT_ORIG_RCV_NWO_DEL, --【92】
			 UPDT_ORIG_RCV_NWO_DV, --【93】
			 UPDT_ORIG_RCV_NWO_FRT, --【94】
			 UPDT_ORIG_RCV_NWO_OT, --【95】
			 UPDT_ORIG_RCV_NWO_PKG, --【96】
			 UPDT_ORIG_RCV_NWO_PUP, --【97】
			 UPDT_ORIG_RCV_WO_COD, --【98】
			 UPDT_ORIG_RCV_WO_DEL, --【99】
			 UPDT_ORIG_RCV_WO_DV, --【100】
			 UPDT_ORIG_RCV_WO_FRT, --【101】
			 UPDT_ORIG_RCV_WO_OT, --【102】
			 UPDT_ORIG_RCV_WO_PKG, --【103】
			 UPDT_ORIG_RCV_WO_PUP, --【104】
			 URO_ORIG_CD_NPOD, --【105】
			 URO_ORIG_CD_POD, --【106】
			 URO_ORIG_CH_NPOD, --【107】
			 URO_ORIG_CH_POD, --【108】
			 URT_DEST_CD_NPOD, --【109】
			 URT_DEST_CD_POD, --【110】
			 URT_DEST_CH_NPOD, --【111】
			 URT_DEST_CH_POD, --【112】
			 URT_ORIG_CD_NPOD, --【113】
			 URT_ORIG_CD_POD, --【114】
			 URT_ORIG_CH_NPOD, --【115】
			 URT_ORIG_CH_POD, --【116】
			 POP_WO_DRT_POD, --【117】
			 POP_WO_DRT_NPOD --【118】
			 )
			SELECT SYS_GUID(),
						 P_PERIOD,
						 PRODUCT_CODE,
						 CUSTOMER_CODE,
						 CUSTOMER_NAME,
						 CUSTOMER_TYPE,
						 ORIG_ORG_CODE,
						 ORIG_ORG_NAME,
						 DEST_ORG_CODE,
						 DEST_ORG_NAME,
						 P_BEGIN_DATE,
						 P_END_DATE,
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.ALPWR_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) ALPWR_WO_DEST_RCVT_NPOD, --应付到达代理/落地配代理成本冲02应收到付运费未签收【1】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.ALPWR_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) ALPWR_WO_DEST_RCVT_POD, --应付到达代理/落地配代理成本冲02应收到付运费已签收【2】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AL_DR_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) AL_DR_WO_DEST_RCVT_NPOD, --预收空运/落地配代理冲02应收到付运费未签收【3】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AL_DR_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) AL_DR_WO_DEST_RCVT_POD, --预收空运/落地配代理冲02应收到付运费已签收【4】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) COD_PAY_WO_DEST_RCVT_NPOD, --应付代收货款冲02应收到付运费未签收【5】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) COD_PAY_WO_DEST_RCVT_POD, --应付代收货款冲02应收到付运费已签收【6】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) COD_PAY_WO_ORIG_RCVT_NPOD, --应付代收货款冲02应收始发运费未签收【7】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) COD_PAY_WO_ORIG_RCVT_POD, --应付代收货款冲02应收始发运费已签收【8】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_PAY_APPLY, --01始发退预收付款申请【9】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_DEST_RCVT_NPOD, --01预收客户冲02应收到付运费未签收【10】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_DEST_RCVT_POD, --01预收客户冲02应收到付运费已签收【11】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_ORIG_RCVT_NPOD, --01预收客户冲02应收始发运费未签收【12】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_ORIG_RCVT_POD, --01预收客户冲02应收始发运费已签收【13】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_PAY_APPLY, --02始发退预收付款申请【14】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_DEST_RCVT_NPOD, --02预收客户冲02应收到付运费未签收【15】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_DEST_RCVT_POD, --02预收客户冲02应收到付运费已签收【16】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_NPODD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_ORIG_RCVO_NPOD, --02预收客户冲01应收始发运费未签收【17】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_PODD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_ORIG_RCVO_POD, --02预收客户冲01应收始发运费已签收【18】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_ORIG_RCVT_NPOD, --02预收客户冲02应收始发运费未签收【19】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_ORIG_RCVT_POD, --02预收客户冲02应收始发运费已签收【20】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DR_OCD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DR_OCD, --预收客户银行【21】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DR_OCH,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DR_OCH, --预收客户现金【22】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DR_TCD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DR_TCD, --预收客户银行【23】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DR_TCH,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DR_TCH, --预收客户现金【24】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.DEO_CD,
														NVL(AMOUNT, 0),
														0)),
								 0) DEO_CD, --开单银行卡【25】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.DEO_CH,
														NVL(AMOUNT, 0),
														0)),
								 0) DEO_CH, --开单现金【26】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.DET_CD,
														NVL(AMOUNT, 0),
														0)),
								 0) DET_CD, --开单银行卡【27】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.DET_CH,
														NVL(AMOUNT, 0),
														0)),
								 0) DET_CH, --开单现金【28】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OTH_PAY_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) OTH_PAY_WO_DEST_RCVT_NPOD, --其他应付冲02应收到付运费未签收【29】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OTH_PAY_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) OTH_PAY_WO_DEST_RCVT_POD, --其他应付冲02应收到付运费已签收【30】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_WO_DEST_RCVT_NPOD, --应付偏线代理成本冲02应收到付运费未签收【31】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_WO_DEST_RCVT_POD, --应付偏线代理成本冲02应收到付运费已签收【32】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_DR_WO_DEST_RCVT_NPOD, --预收偏线代理冲02应收到付运费未签收【33】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_DR_WO_DEST_RCVT_POD, --预收偏线代理冲02应收到付运费已签收【34】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_CASH_COLLECTED,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) PODT_CASH_COLLECTED_COD, --签收时现付已收款金额_代收货款手续费【35】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_CASH_COLLECTED,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) PODT_CASH_COLLECTED_DEL, --签收时现付已收款金额_送货费【36】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_CASH_COLLECTED,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) PODT_CASH_COLLECTED_DV, --签收时现付已收款金额_保价费【37】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_CASH_COLLECTED,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) PODT_CASH_COLLECTED_FRT, --签收时现付已收款金额_公布价运费【38】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_CASH_COLLECTED,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) PODT_CASH_COLLECTED_OT, --签收时现付已收款金额_其它费用【39】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_CASH_COLLECTED,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) PODT_CASH_COLLECTED_PKG, --签收时现付已收款金额_包装费【40】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_CASH_COLLECTED,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) PODT_CASH_COLLECTED_PUP, --签收时现付已收款金额_接货费【41】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_NWO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) PODT_DEST_RCV_NWO_COD, --签收时到达应收未核销金额_代收货款手续费【42】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_NWO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) PODT_DEST_RCV_NWO_DEL, --签收时到达应收未核销金额_送货费【43】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_NWO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) PODT_DEST_RCV_NWO_DV, --签收时到达应收未核销金额_保价费【44】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_NWO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) PODT_DEST_RCV_NWO_FRT, --签收时到达应收未核销金额_公布价运费【45】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_NWO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) PODT_DEST_RCV_NWO_OT, --签收时到达应收未核销金额_其它费用【46】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_NWO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) PODT_DEST_RCV_NWO_PKG, --签收时到达应收未核销金额_包装费【47】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_NWO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) PODT_DEST_RCV_NWO_PUP, --签收时到达应收未核销金额_接货费【48】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_WO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) PODT_DEST_RCV_WO_COD, --签收时到达应收已核销金额_代收货款手续费【49】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_WO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) PODT_DEST_RCV_WO_DEL, --签收时到达应收已核销金额_送货费【50】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_WO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) PODT_DEST_RCV_WO_DV, --签收时到达应收已核销金额_保价费【51】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_WO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) PODT_DEST_RCV_WO_FRT, --签收时到达应收已核销金额_公布价运费【52】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_WO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) PODT_DEST_RCV_WO_OT, --签收时到达应收已核销金额_其它费用【53】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_WO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) PODT_DEST_RCV_WO_PKG, --签收时到达应收已核销金额_包装费【54】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_WO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) PODT_DEST_RCV_WO_PUP, --签收时到达应收已核销金额_接货费【55】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_NWO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) PODT_ORIG_RCV_NWO_COD, --签收时始发应收未核销金额_代收货款手续费【56】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_NWO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) PODT_ORIG_RCV_NWO_DEL, --签收时始发应收未核销金额_送货费【57】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_NWO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) PODT_ORIG_RCV_NWO_DV, --签收时始发应收未核销金额_保价费【58】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_NWO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) PODT_ORIG_RCV_NWO_FRT, --签收时始发应收未核销金额_公布价运费【59】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_NWO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) PODT_ORIG_RCV_NWO_OT, --签收时始发应收未核销金额_其它费用【60】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_NWO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) PODT_ORIG_RCV_NWO_PKG, --签收时始发应收未核销金额_包装费【61】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_NWO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) PODT_ORIG_RCV_NWO_PUP, --签收时始发应收未核销金额_接货费【62】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_WO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) PODT_ORIG_RCV_WO_COD, --签收时始发应收已核销金额_代收货款手续费【63】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_WO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) PODT_ORIG_RCV_WO_DEL, --签收时始发应收已核销金额_送货费【64】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_WO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) PODT_ORIG_RCV_WO_DV, --签收时始发应收已核销金额_保价费【65】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_WO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) PODT_ORIG_RCV_WO_FRT, --签收时始发应收已核销金额_公布价运费【66】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_WO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) PODT_ORIG_RCV_WO_OT, --签收时始发应收已核销金额_其它费用【67】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_WO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) PODT_ORIG_RCV_WO_PKG, --签收时始发应收已核销金额_包装费【68】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_WO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) PODT_ORIG_RCV_WO_PUP, --签收时始发应收已核销金额_接货费【69】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_CASH_COLLECTED,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) UPDT_CASH_COLLECTED_COD, --反签收时现付已收款金额_代收货款手续费【70】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_CASH_COLLECTED,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) UPDT_CASH_COLLECTED_DEL, --反签收时现付已收款金额_送货费【71】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_CASH_COLLECTED,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) UPDT_CASH_COLLECTED_DV, --反签收时现付已收款金额_保价费【72】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_CASH_COLLECTED,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) UPDT_CASH_COLLECTED_FRT, --反签收时现付已收款金额_公布价运费【73】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_CASH_COLLECTED,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) UPDT_CASH_COLLECTED_OT, --反签收时现付已收款金额_其它费用【74】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_CASH_COLLECTED,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) UPDT_CASH_COLLECTED_PKG, --反签收时现付已收款金额_包装费【75】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_CASH_COLLECTED,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) UPDT_CASH_COLLECTED_PUP, --反签收时现付已收款金额_接货费【76】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_NWO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) UPDT_DEST_RCV_NWO_COD, --反签收时到达应收未核销金额_代收货款手续费【77】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_NWO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) UPDT_DEST_RCV_NWO_DEL, --反签收时到达应收未核销金额_送货费【78】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_NWO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) UPDT_DEST_RCV_NWO_DV, --反签收时到达应收未核销金额_保价费【79】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_NWO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) UPDT_DEST_RCV_NWO_FRT, --反签收时到达应收未核销金额_公布价运费【80】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_NWO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) UPDT_DEST_RCV_NWO_OT, --反签收时到达应收未核销金额_其它费用【81】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_NWO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) UPDT_DEST_RCV_NWO_PKG, --反签收时到达应收未核销金额_包装费【82】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_NWO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) UPDT_DEST_RCV_NWO_PUP, --反签收时到达应收未核销金额_接货费【83】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_WO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) UPDT_DEST_RCV_WO_COD, --反签收时到达应收已核销金额_代收货款手续费【84】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_WO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) UPDT_DEST_RCV_WO_DEL, --反签收时到达应收已核销金额_送货费【85】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_WO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) UPDT_DEST_RCV_WO_DV, --反签收时到达应收已核销金额_保价费【86】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_WO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) UPDT_DEST_RCV_WO_FRT, --反签收时到达应收已核销金额_公布价运费【87】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_WO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) UPDT_DEST_RCV_WO_OT, --反签收时到达应收已核销金额_其它费用【88】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_WO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) UPDT_DEST_RCV_WO_PKG, --反签收时到达应收已核销金额_包装费【89】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_WO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) UPDT_DEST_RCV_WO_PUP, --反签收时到达应收已核销金额_接货费【90】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_NWO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) UPDT_ORIG_RCV_NWO_COD, --反签收时始发应收未核销金额_代收货款手续费【91】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_NWO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) UPDT_ORIG_RCV_NWO_DEL, --反签收时始发应收未核销金额_送货费【92】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_NWO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) UPDT_ORIG_RCV_NWO_DV, --反签收时始发应收未核销金额_保价费【93】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_NWO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) UPDT_ORIG_RCV_NWO_FRT, --反签收时始发应收未核销金额_公布价运费【94】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_NWO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) UPDT_ORIG_RCV_NWO_OT, --反签收时始发应收未核销金额_其它费用【95】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_NWO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) UPDT_ORIG_RCV_NWO_PKG, --反签收时始发应收未核销金额_包装费【96】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_NWO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) UPDT_ORIG_RCV_NWO_PUP, --反签收时始发应收未核销金额_接货费【97】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_WO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) UPDT_ORIG_RCV_WO_COD, --反签收时始发应收已核销金额_代收货款手续费【98】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_WO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) UPDT_ORIG_RCV_WO_DEL, --反签收时始发应收已核销金额_送货费【99】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_WO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) UPDT_ORIG_RCV_WO_DV, --反签收时始发应收已核销金额_保价费【100】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_WO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) UPDT_ORIG_RCV_WO_FRT, --反签收时始发应收已核销金额_公布价运费【101】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_WO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) UPDT_ORIG_RCV_WO_OT, --反签收时始发应收已核销金额_其它费用【102】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_WO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) UPDT_ORIG_RCV_WO_PKG, --反签收时始发应收已核销金额_包装费【103】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_WO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) UPDT_ORIG_RCV_WO_PUP, --反签收时始发应收已核销金额_接货费【104】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_ORIG_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_ORIG_CD_NPOD, --还款银行未签收【105】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_ORIG_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_ORIG_CD_POD, --还款银行已签收【106】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_ORIG_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_ORIG_CH_NPOD, --还款现金未签收【107】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_ORIG_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_ORIG_CH_POD, --还款现金已签收【108】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URT_DEST_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URT_DEST_CD_NPOD, --还款银行未签收【109】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URT_DEST_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URT_DEST_CD_POD, --还款银行已签收【110】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URT_DEST_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URT_DEST_CH_NPOD, --还款现金未签收【111】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URT_DEST_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URT_DEST_CH_POD, --还款现金已签收【112】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URT_ORIG_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URT_ORIG_CD_NPOD, --还款银行未签收【113】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URT_ORIG_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URT_ORIG_CD_POD, --还款银行已签收【114】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URT_ORIG_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URT_ORIG_CH_NPOD, --还款现金未签收【115】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URT_ORIG_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URT_ORIG_CH_POD, --还款现金已签收【116】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.POP_WO_DRT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) POP_WO_DRT_POD, --还款现金已签收【117】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.POP_WO_DRT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) POP_WO_DRT_NPOD --还款现金已签收【118】
				FROM STV.T_STL_NMVS T
			 WHERE T.PERIOD >= V_BEGIN_PERIOD
						 AND T.PERIOD < V_END_PERIOD
						 AND T.AMOUNT <> 0
						 AND T.BUSINESS_CASE IN
						 (PKG_STL_VCH_COMMON.AL_DR_WO_DEST_RCVT_NPOD,
									PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVT_POD,
									PKG_STL_VCH_COMMON.DEO_CH,
									PKG_STL_VCH_COMMON.PODT_CASH_COLLECTED,
									PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_NWO,
									PKG_STL_VCH_COMMON.URO_ORIG_CD_POD,
									PKG_STL_VCH_COMMON.URT_DEST_CD_POD,
									PKG_STL_VCH_COMMON.URT_ORIG_CH_NPOD,
									PKG_STL_VCH_COMMON.URT_ORIG_CH_POD,
									PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_NPODD,
									PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_PODD,
									PKG_STL_VCH_COMMON.DEO_CD,
									PKG_STL_VCH_COMMON.DET_CH,
									PKG_STL_VCH_COMMON.PODT_ORIG_RCV_NWO,
									PKG_STL_VCH_COMMON.UPDT_CASH_COLLECTED,
									PKG_STL_VCH_COMMON.URT_DEST_CH_NPOD,
									PKG_STL_VCH_COMMON.CUST_DRT_PAY_APPLY,
									PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVT_NPOD,
									PKG_STL_VCH_COMMON.DET_CD,
									PKG_STL_VCH_COMMON.PODT_DEST_RCV_NWO,
									PKG_STL_VCH_COMMON.PODT_ORIG_RCV_WO,
									PKG_STL_VCH_COMMON.URO_ORIG_CH_NPOD,
									PKG_STL_VCH_COMMON.URT_DEST_CH_POD,
									PKG_STL_VCH_COMMON.URT_ORIG_CD_NPOD,
									PKG_STL_VCH_COMMON.ALPWR_WO_DEST_RCVT_POD,
									PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVT_POD,
									PKG_STL_VCH_COMMON.CUST_DRO_PAY_APPLY,
									PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_NPOD,
									PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVT_POD,
									PKG_STL_VCH_COMMON.URO_ORIG_CD_NPOD,
									PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVT_NPOD,
									PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_POD,
									PKG_STL_VCH_COMMON.CUST_DR_OCD,
									PKG_STL_VCH_COMMON.OTH_PAY_WO_DEST_RCVT_POD,
									PKG_STL_VCH_COMMON.UPDT_DEST_RCV_WO,
									PKG_STL_VCH_COMMON.URT_ORIG_CD_POD,
									PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_POD,
									PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_POD,
									PKG_STL_VCH_COMMON.CUST_DR_TCH,
									PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_POD,
									PKG_STL_VCH_COMMON.UPDT_DEST_RCV_NWO,
									PKG_STL_VCH_COMMON.URO_ORIG_CH_POD,
									PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_NPOD,
									PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_NPOD,
									PKG_STL_VCH_COMMON.OTH_PAY_WO_DEST_RCVT_NPOD,
									PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_WO,
									PKG_STL_VCH_COMMON.URT_DEST_CD_NPOD,
									PKG_STL_VCH_COMMON.ALPWR_WO_DEST_RCVT_NPOD,
									PKG_STL_VCH_COMMON.AL_DR_WO_DEST_RCVT_POD,
									PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVT_NPOD,
									PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVT_POD,
									PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_NPOD,
									PKG_STL_VCH_COMMON.CUST_DR_OCH,
									PKG_STL_VCH_COMMON.CUST_DR_TCD,
									PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVT_NPOD,
									PKG_STL_VCH_COMMON.PODT_DEST_RCV_WO,
									PKG_STL_VCH_COMMON.POP_WO_DRT_POD,
									PKG_STL_VCH_COMMON.POP_WO_DRT_NPOD)
			 GROUP BY PRODUCT_CODE,
								CUSTOMER_CODE,
								CUSTOMER_NAME,
								CUSTOMER_TYPE,
								ORIG_ORG_CODE,
								ORIG_ORG_NAME,
								DEST_ORG_CODE,
								DEST_ORG_NAME;
	
		COMMIT;
	
		RETURN TRUE;
	
	EXCEPTION
		WHEN OTHERS THEN
			--插入错误日志表
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NRFONT',
																				'凭证生成02普通始发报表程序异常:' || SQLERRM);
		
			--返回失败标志
			RETURN FALSE;
		
	END FUNC_VOUCHER_NRFONT;

	-----------------------------------------------------------------
	-- 02特殊始发月报表入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NRFOST(P_PERIOD     VARCHAR2,
															 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
															 P_END_DATE   DATE -- 结束时间 2012-12-22
															 ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
	 IS
	
		V_BEGIN_PERIOD VARCHAR2(50);
		V_END_PERIOD   VARCHAR2(50);
	
	BEGIN
	
		V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
		V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_NRFOST T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
			USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;
	
		INSERT INTO STV.T_STL_MVR_NRFOST
			(ID,
			 PERIOD,
			 PRODUCT_CODE,
			 CUSTOMER_CODE,
			 CUSTOMER_NAME,
			 CUSTOMER_TYPE,
			 ORIG_ORG_CODE,
			 ORIG_ORG_NAME,
			 DEST_ORG_CODE,
			 DEST_ORG_NAME,
			 VOUCHER_BEGIN_TIME,
			 VOUCHER_END_TIME,
			 AC_CASH, --【1】
			 AC_ORIG_RCV_NWO, --【2】
			 AC_ORIG_RCV_WO, --【3】
			 BD_WO_DEST_RCVT_POD, --【4】
			 BD_WO_ORIG_RCVT_POD, --【5】
			 CLAIM_DESTO_WO_DEST_RCVT_NPOD, --【6】
			 CLAIM_DESTO_WO_DEST_RCVT_POD, --【7】
			 CLAIM_DESTT_INCOME, --【8】
			 CLAIM_DESTT_WO_DEST_RCVT_NPOD, --【9】
			 CLAIM_DESTT_WO_DEST_RCVT_POD, --【10】
			 CLAIM_ORIGO_PAY_APPLY, --【11】
			 CLAIM_ORIGO_WO_ORIG_RCVT_NPOD, --【12】
			 CLAIM_ORIGO_WO_ORIG_RCVT_POD, --【13】
			 CLAIM_ORIGT_COST, --【14】
			 CLAIM_ORIGT_INCOME, --【15】
			 CLAIM_ORIGT_ORIG_RCVO_POD, --【16】
			 CLAIM_ORIGT_PAY_APPLY, --【17】
			 CLAIM_ORIGT_WO_ORIG_RCVO_NPOD, --【18】
			 CLAIM_ORIGT_WO_ORIG_RCVT_NPOD, --【19】
			 CLAIM_ORIGT_WO_ORIG_RCVT_POD, --【20】
			 CPO_ORIG_PAY_APPLY, --【21】
			 CPT_ORIG_PAY_APPLY, --【22】
			 EX_DEST_RCVT_POD, --【23】
			 EX_ORIG_RCVT_POD, --【24】
			 OR_BAD_WO_RCVT, --【25】
			 OR_CD_AC, --【26】
			 OR_CD_BANK_INT, --【27】
			 OR_CD_OPAY, --【28】
			 OR_CD_OTHER, --【29】
			 OR_CD_PBIO, --【30】
			 OR_CD_PBIT, --【31】
			 OR_CD_UR_RCVO, --【32】
			 OR_CD_UR_RCVT, --【33】
			 OR_CH_AC, --【34】
			 OR_CH_OPAY, --【35】
			 OR_CH_OTHER, --【36】
			 OR_CH_PBIO, --【37】
			 OR_CH_PBIT, --【38】
			 OR_CH_SI, --【39】
			 OR_CH_UR_RCVO, --【40】
			 OR_CH_UR_RCVT, --【41】
			 OR_CLAIM_PAYO_WO_RCVT, --【42】
			 OR_CLAIM_PAYT_WO_RCVO, --【43】
			 OR_CLAIM_PAYT_WO_RCVT, --【44】
			 OR_COD_PAY_WO_RCVT, --【45】
			 OR_CUST_DRO_WO_RCVT, --【46】
			 OR_CUST_DRT_WO_RCVO, --【47】
			 OR_CUST_DRT_WO_RCVT, --【48】
			 OR_EX_WO_RCVT, --【49】
			 OR_RCVT_PBI, --【50】
			 RD_DESTO_DEST_RCVT_POD, --【51】
			 RD_DESTO_WO_DEST_RCVT_NPOD, --【52】
			 RD_DESTT_INCOME, --【53】
			 RD_DESTT_WO_DEST_RCVT_NPOD, --【54】
			 RD_DESTT_WO_DEST_RCVT_POD, --【55】
			 RD_ORIGO_PAY_APPLY, --【56】
			 RD_ORIGO_WO_ORIG_RCVT_NPOD, --【57】
			 RD_ORIGO_WO_ORIG_RCVT_POD, --【58】
			 RD_ORIGT_COST, --【59】
			 RD_ORIGT_INCOME, --【60】
			 RD_ORIGT_PAY_APPLY, --【61】
			 RD_ORIGT_WO_ORIG_RCVO_NPOD, --【62】
			 RD_ORIGT_WO_ORIG_RCVO_POD, --【63】
			 RD_ORIGT_WO_ORIG_RCVT_NPOD, --【64】
			 RD_ORIGT_WO_ORIG_RCVT_POD, --【65】
			 SFO_PAY_APPLY, --【66】
			 SFT_PAY_APPLY, --【67】
			 OR_CH_RENT_INCOME, --【68】
			 OR_CH_BANK_INT, --【69】
			 OR_CD_RENT_INCOME --【70】
			 )
			SELECT SYS_GUID(),
						 P_PERIOD,
						 PRODUCT_CODE,
						 CUSTOMER_CODE,
						 CUSTOMER_NAME,
						 CUSTOMER_TYPE,
						 ORIG_ORG_CODE,
						 ORIG_ORG_NAME,
						 DEST_ORG_CODE,
						 DEST_ORG_NAME,
						 P_BEGIN_DATE,
						 P_END_DATE,
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AC_CASHT,
														NVL(AMOUNT, 0),
														0)),
								 0) AC_CASH, --开单且为现金银行卡【1】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AC_ORIG_RCV_NWOT,
														NVL(AMOUNT, 0),
														0)),
								 0) AC_ORIG_RCV_NWO, --开单且为月结临时欠款网上支付未核销【2】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AC_ORIG_RCV_WOT,
														NVL(AMOUNT, 0),
														0)),
								 0) AC_ORIG_RCV_WO, --开单且为月结临时欠款网上支付已核销【3】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.BD_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) BD_WO_DEST_RCVT_POD, --坏账冲02应收到付运费已签收【4】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.BD_WO_ORIG_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) BD_WO_ORIG_RCVT_POD, --坏账冲02应收始发运费已签收【5】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTO_WO_DEST_RCVT_NPOD, --01理赔冲02到达应收未签收【6】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTO_WO_DEST_RCVT_POD, --01理赔冲02到达应收已签收【7】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTT_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTT_INCOME, --02理赔冲收入【8】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTT_WO_DEST_RCVT_NPOD, --02理赔冲02到达应收未签收【9】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTT_WO_DEST_RCVT_POD, --02理赔冲02到达应收已签收【10】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGO_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGO_PAY_APPLY, --01理赔付款申请【11】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGO_WO_ORIG_RCVT_NPOD, --01理赔冲02始发应收未签收【12】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGO_WO_ORIG_RCVT_POD, --01理赔冲02始发应收已签收【13】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGT_COST,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGT_COST, --02理赔入成本【14】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGT_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGT_INCOME, --02理赔冲收入【15】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGT_ORIG_RCVO_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGT_ORIG_RCVO_POD, --02理赔冲01始发应收已签收【16】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGT_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGT_PAY_APPLY, --02理赔付款申请【17】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVO_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGT_WO_ORIG_RCVO_NPOD, --02理赔冲01始发应收未签收【18】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGT_WO_ORIG_RCVT_NPOD, --02理赔冲02始发应收未签收【19】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGT_WO_ORIG_RCVT_POD, --02理赔冲02始发应收已签收【20】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CPO_ORIG_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CPO_ORIG_PAY_APPLY, --01始发服务补救付款申请【21】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CPT_ORIG_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CPT_ORIG_PAY_APPLY, --02始发服务补救付款申请【22】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.EX_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) EX_DEST_RCVT_POD, --02应收到付运费已签收【23】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.EX_ORIG_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) EX_ORIG_RCVT_POD, --02应收始发运费已签收【24】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_BAD_WO_RCVT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_BAD_WO_RCVT, --坏账损失冲02小票应收【25】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CD_AC,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CD_AC, --小票银行之事故赔款 【26】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CD_BANK_INT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CD_BANK_INT, --小票银行之收银员卡利息【27】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CD_OPAY,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CD_OPAY, --小票银行之客户多付运费或盘点长款金额 【28】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CD_OTHER,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CD_OTHER, --小票银行之其他【29】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CD_PBIO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CD_PBIO, --01小票银行主营业务收入【30】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CD_PBIT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CD_PBIT, --02小票银行主营业务收入【31】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CD_UR_RCVO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CD_UR_RCVO, --还款银行冲01小票应收【32】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CD_UR_RCVT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CD_UR_RCVT, --还款银行冲02小票应收【33】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CH_AC,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CH_AC, --小票现金之事故赔款 【34】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CH_OPAY,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CH_OPAY, --小票现金之客户多付运费或盘点长款金额 【35】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CH_OTHER,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CH_OTHER, --小票现金之其他【36】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CH_PBIO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CH_PBIO, --01小票现金主营业务收入【37】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CH_PBIT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CH_PBIT, --02小票现金主营业务收入【38】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CH_SI,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CH_SI, --小票现金之变卖废品收入【39】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CH_UR_RCVO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CH_UR_RCVO, --还款现金冲01小票应收【40】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CH_UR_RCVT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CH_UR_RCVT, --还款现金冲02小票应收【41】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CLAIM_PAYO_WO_RCVT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CLAIM_PAYO_WO_RCVT, --01应付理赔冲02小票应收【42】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CLAIM_PAYT_WO_RCVOP,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CLAIM_PAYT_WO_RCVO, --02应付理赔冲01小票应收【43】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CLAIM_PAYT_WO_RCVT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CLAIM_PAYT_WO_RCVT, --02应付理赔冲02小票应收【44】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_COD_PAY_WO_RCVT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_COD_PAY_WO_RCVT, --应付代收货款冲02小票应收【45】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CUST_DRO_WO_RCVT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CUST_DRO_WO_RCVT, --01预收客户冲02小票应收【46】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CUST_DRT_WO_RCVOD,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CUST_DRT_WO_RCVO, --02预收客户冲01小票应收【47】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CUST_DRT_WO_RCVT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CUST_DRT_WO_RCVT, --02预收客户冲02小票应收【48】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_EX_WO_RCVT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_EX_WO_RCVT, --异常冲收入冲02小票应收【49】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_RCVT_PBI,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_RCVT_PBI, --02小票应收主营业务收入【50】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_DEST_RCVT_POD, --01退运费冲02到达应收已签收【51】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_WO_DEST_RCVT_NPOD, --01退运费冲02到达应收未签收【52】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTT_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTT_INCOME, --02退运费冲收入【53】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTT_WO_DEST_RCVT_NPOD, --02退运费冲02到达应收未签收【54】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTT_WO_DEST_RCVT_POD, --02退运费冲02到达应收已签收【55】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGO_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGO_PAY_APPLY, --01退运费付款申请【56】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGO_WO_ORIG_RCVT_NPOD, --01退运费冲02始发应收未签收【57】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGO_WO_ORIG_RCVT_POD, --01退运费冲02始发应收已签收【58】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGT_COST,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGT_COST, --02退运费入成本【59】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGT_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGT_INCOME, --02退运费冲收入【60】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGT_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGT_PAY_APPLY, --02退运费付款申请【61】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGT_WO_ORIG_RCVO_NPOD, --02退运费冲01始发应收未签收【62】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGT_WO_ORIG_RCVO_POD, --02退运费冲01始发应收已签收【63】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGT_WO_ORIG_RCVT_NPOD, --02退运费冲02始发应收未签收【64】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGT_WO_ORIG_RCVT_POD, --02退运费冲02始发应收已签收【65】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.SFO_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) SFO_PAY_APPLY, --01装卸费付款申请【66】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.SFT_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) SFT_PAY_APPLY, --02装卸费付款申请【67】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CH_RENT_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CH_RENT_INCOME, --小票现金之富余仓库出租收入【68】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CH_BANK_INT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CH_BANK_INT, --小票现金之收银员卡利息【69】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CD_RENT_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CD_RENT_INCOME --小票银行之富余仓库出租收入【70】
				FROM STV.T_STL_NMVS T
			 WHERE T.PERIOD >= V_BEGIN_PERIOD
						 AND T.PERIOD < V_END_PERIOD
						 AND T.AMOUNT <> 0
						 AND T.BUSINESS_CASE IN
						 (PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_POD,
									PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_NPOD,
									PKG_STL_VCH_COMMON.CLAIM_ORIGT_ORIG_RCVO_PODP,
									PKG_STL_VCH_COMMON.OR_CD_AC,
									PKG_STL_VCH_COMMON.OR_CD_PBIT,
									PKG_STL_VCH_COMMON.OR_CD_UR_RCVO,
									PKG_STL_VCH_COMMON.OR_CH_PBIT,
									PKG_STL_VCH_COMMON.OR_CH_UR_RCVO,
									PKG_STL_VCH_COMMON.OR_CLAIM_PAYT_WO_RCVOP,
									PKG_STL_VCH_COMMON.RD_DESTT_INCOME,
									PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVT_POD,
									PKG_STL_VCH_COMMON.AC_CASHT,
									PKG_STL_VCH_COMMON.AC_ORIG_RCV_NWOT,
									PKG_STL_VCH_COMMON.BD_WO_ORIG_RCVT_POD,
									PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVT_NPOD,
									PKG_STL_VCH_COMMON.EX_ORIG_RCVT_POD,
									PKG_STL_VCH_COMMON.OR_CD_OTHER,
									PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVT_POD,
									PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_NPOD,
									PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_PODP,
									PKG_STL_VCH_COMMON.SFT_PAY_APPLY,
									PKG_STL_VCH_COMMON.CLAIM_ORIGT_PAY_APPLY,
									PKG_STL_VCH_COMMON.CPT_ORIG_PAY_APPLY,
									PKG_STL_VCH_COMMON.OR_CD_UR_RCVT,
									PKG_STL_VCH_COMMON.OR_CH_AC,
									PKG_STL_VCH_COMMON.OR_CH_UR_RCVT,
									PKG_STL_VCH_COMMON.OR_CLAIM_PAYT_WO_RCVT,
									PKG_STL_VCH_COMMON.OR_CUST_DRT_WO_RCVT,
									PKG_STL_VCH_COMMON.OR_RCVT_PBI,
									PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_POD,
									PKG_STL_VCH_COMMON.RD_ORIGT_COST,
									PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_NPODP,
									PKG_STL_VCH_COMMON.SFO_PAY_APPLY,
									PKG_STL_VCH_COMMON.CLAIM_ORIGO_PAY_APPLY,
									PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVO_NPODP,
									PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVT_POD,
									PKG_STL_VCH_COMMON.OR_CD_BANK_INT,
									PKG_STL_VCH_COMMON.OR_CH_OPAY,
									PKG_STL_VCH_COMMON.OR_CUST_DRT_WO_RCVOD,
									PKG_STL_VCH_COMMON.RD_ORIGT_INCOME,
									PKG_STL_VCH_COMMON.AC_ORIG_RCV_WOT,
									PKG_STL_VCH_COMMON.BD_WO_DEST_RCVT_POD,
									PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_POD,
									PKG_STL_VCH_COMMON.CLAIM_DESTT_INCOME,
									PKG_STL_VCH_COMMON.OR_COD_PAY_WO_RCVT,
									PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVT_NPOD,
									PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_POD,
									PKG_STL_VCH_COMMON.CLAIM_ORIGT_INCOME,
									PKG_STL_VCH_COMMON.CPO_ORIG_PAY_APPLY,
									PKG_STL_VCH_COMMON.EX_DEST_RCVT_POD,
									PKG_STL_VCH_COMMON.OR_BAD_WO_RCVT,
									PKG_STL_VCH_COMMON.OR_CH_PBIO,
									PKG_STL_VCH_COMMON.OR_CH_SI,
									PKG_STL_VCH_COMMON.OR_CUST_DRO_WO_RCVT,
									PKG_STL_VCH_COMMON.OR_EX_WO_RCVT,
									PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVT_NPOD,
									PKG_STL_VCH_COMMON.RD_ORIGO_PAY_APPLY,
									PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_NPOD,
									PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_NPOD,
									PKG_STL_VCH_COMMON.OR_CH_OTHER,
									PKG_STL_VCH_COMMON.OR_CLAIM_PAYO_WO_RCVT,
									PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_POD,
									PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_NPOD,
									PKG_STL_VCH_COMMON.CLAIM_ORIGT_COST,
									PKG_STL_VCH_COMMON.OR_CD_OPAY,
									PKG_STL_VCH_COMMON.OR_CD_PBIO,
									PKG_STL_VCH_COMMON.RD_ORIGT_PAY_APPLY,
									PKG_STL_VCH_COMMON.OR_CH_RENT_INCOME,
									PKG_STL_VCH_COMMON.OR_CH_BANK_INT,
									PKG_STL_VCH_COMMON.OR_CD_RENT_INCOME)
			 GROUP BY PRODUCT_CODE,
								CUSTOMER_CODE,
								CUSTOMER_NAME,
								CUSTOMER_TYPE,
								ORIG_ORG_CODE,
								ORIG_ORG_NAME,
								DEST_ORG_CODE,
								DEST_ORG_NAME;
	
		COMMIT;
	
		RETURN TRUE;
	
	EXCEPTION
		WHEN OTHERS THEN
			--插入错误日志表
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NRFOST',
																				'凭证生成02特殊始发报表程序异常:' || SQLERRM);
		
			--返回失败标志
			RETURN FALSE;
		
	END FUNC_VOUCHER_NRFOST;

	-----------------------------------------------------------------
	-- 01到达月报表入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NRFDO(P_PERIOD     VARCHAR2,
															P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
															P_END_DATE   DATE -- 结束时间 2012-12-22
															) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
	 IS
	
		V_BEGIN_PERIOD VARCHAR2(50);
		V_END_PERIOD   VARCHAR2(50);
	
	BEGIN
	
		V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
		V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_NRFDO T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
			USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;
	
		INSERT INTO STV.T_STL_MVR_NRFDO
			(ID,
			 PERIOD,
			 PRODUCT_CODE,
			 CUSTOMER_CODE,
			 CUSTOMER_NAME,
			 CUSTOMER_TYPE,
			 ORIG_ORG_CODE,
			 ORIG_ORG_NAME,
			 DEST_ORG_CODE,
			 DEST_ORG_NAME,
			 VOUCHER_BEGIN_TIME,
			 VOUCHER_END_TIME,
			 CLAIM_DESTO_COST, --【1】
			 CLAIM_DESTO_INCOME, --【2】
			 CLAIM_DESTO_PAY_APPLY, --【3】
			 CLAIM_DESTO_WO_DEST_RCVO_NPOD, --【4】
			 CLAIM_DESTO_WO_DEST_RCVO_POD, --【5】
			 CLAIM_DESTO_WO_DEST_RCVT_NPOD, --【6】
			 CLAIM_DESTO_WO_DEST_RCVT_POD, --【7】
			 CPO_DEST_PAY_APPLY, --【8】
			 CUST_DRO_WO_DEST_RCVO_NPOD, --【9】
			 CUST_DRO_WO_DEST_RCVO_POD, --【10】
			 CUST_DRO_WO_DEST_RCVT_NPOD, --【11】
			 CUST_DRO_WO_DEST_RCVT_POD, --【12】
			 RD_DESTO_COST, --【13】
			 RD_DESTO_DEST_RCVO_POD, --【14】
			 RD_DESTO_DEST_RCVT_POD, --【15】
			 RD_DESTO_INCOME, --【16】
			 RD_DESTO_PAY_APPLY, --【17】
			 RD_DESTO_WO_DEST_RCVO_NPOD, --【18】
			 RD_DESTO_WO_DEST_RCVT_NPOD --【19】
			 )
			SELECT SYS_GUID(),
						 P_PERIOD,
						 T.PRODUCT_CODE,
						 T.CUSTOMER_CODE,
						 T.CUSTOMER_NAME,
						 T.CUSTOMER_TYPE,
						 T.ORIG_ORG_CODE,
						 T.ORIG_ORG_NAME,
						 T.DEST_ORG_CODE,
						 T.DEST_ORG_NAME,
						 P_BEGIN_DATE,
						 P_END_DATE,
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTO_COST,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTO_COST, --01理赔入成本【1】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTO_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTO_INCOME, --01理赔冲收入【2】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTO_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTO_PAY_APPLY, --01理赔付款申请【3】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTO_WO_DEST_RCVO_NPOD, --01理赔冲01到达应收未签收【4】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTO_WO_DEST_RCVO_POD, --01理赔冲到01达应收已签收【5】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTO_WO_DEST_RCVT_NPOD, --01理赔冲02到达应收未签收【6】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTO_WO_DEST_RCVT_POD, --01理赔冲02到达应收已签收【7】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CPO_DEST_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CPO_DEST_PAY_APPLY, --01到达服务补救付款申请【8】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_NPODD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_DEST_RCVO_NPOD, --01预收客户冲01应收到付运费未签收【9】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_PODD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_DEST_RCVO_POD, --01预收客户冲01应收到付运费已签收【10】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_NPODD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_DEST_RCVT_NPOD, --01预收客户冲02应收到付运费未签收【11】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_PODD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_DEST_RCVT_POD, --01预收客户冲02应收到付运费已签收【12】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_COST,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_COST, --01退运费入成本【13】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVO_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_DEST_RCVO_POD, --01退运费冲01到达应收已签收【14】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVT_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_DEST_RCVT_POD, --01退运费冲02到达应收已签收【15】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_INCOME, --01退运费冲收入【16】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_PAY_APPLY, --01退运费付款申请【17】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVO_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_WO_DEST_RCVO_NPOD, --01退运费冲01到达应收未签收【18】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVT_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_WO_DEST_RCVT_NPOD --01退运费冲02到达应收未签收【19】
				FROM STV.T_STL_NMVS T
			 WHERE T.PERIOD >= V_BEGIN_PERIOD
						 AND T.PERIOD < V_END_PERIOD
						 AND T.AMOUNT <> 0
						 AND T.PRODUCT_CODE IN (PKG_STL_COMMON.PRODUCT_CODE_FLF, --精准卡航
																		PKG_STL_COMMON.PRODUCT_CODE_FSF, --精准城运
																		PKG_STL_COMMON.PRODUCT_CODE_LRF, --精准汽运(长途)
																		PKG_STL_COMMON.PRODUCT_CODE_SRF, --精准汽运(短途)
																		PKG_STL_COMMON.PRODUCT_CODE_WVH, --整车
																		PKG_STL_COMMON.PRODUCT_CODE_PKG --经济快递
																		)
						 AND T.BUSINESS_CASE IN
						 (PKG_STL_VCH_COMMON.CLAIM_DESTO_INCOME,
									PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_PODP,
									PKG_STL_VCH_COMMON.CLAIM_DESTO_COST,
									PKG_STL_VCH_COMMON.CLAIM_DESTO_PAY_APPLY,
									PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVT_PODP,
									PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_NPODP,
									PKG_STL_VCH_COMMON.RD_DESTO_COST,
									PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVO_PODP,
									PKG_STL_VCH_COMMON.RD_DESTO_INCOME,
									PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_PODP,
									PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_PODD,
									PKG_STL_VCH_COMMON.RD_DESTO_PAY_APPLY,
									PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVO_NPODP,
									PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_NPODD,
									PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVT_NPODP,
									PKG_STL_VCH_COMMON.CPO_DEST_PAY_APPLY,
									PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_NPODP,
									PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_PODD,
									PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_NPODD)
			 GROUP BY T.PRODUCT_CODE,
								T.CUSTOMER_CODE,
								T.CUSTOMER_NAME,
								T.CUSTOMER_TYPE,
								T.ORIG_ORG_CODE,
								T.ORIG_ORG_NAME,
								T.DEST_ORG_CODE,
								T.DEST_ORG_NAME;
	
		COMMIT;
	
		RETURN TRUE;
	
	EXCEPTION
		WHEN OTHERS THEN
			--插入错误日志表
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NRFDO',
																				'凭证生成01到达月报表程序异常:' || SQLERRM);
		
			--返回失败标志
			RETURN FALSE;
		
	END FUNC_VOUCHER_NRFDO;

	-----------------------------------------------------------------
	-- 02到达月报表入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NRFDT(P_PERIOD     VARCHAR2,
															P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
															P_END_DATE   DATE -- 结束时间 2012-12-22
															) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
	 IS
	
		V_BEGIN_PERIOD VARCHAR2(50);
		V_END_PERIOD   VARCHAR2(50);
	
	BEGIN
	
		V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
		V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_NRFDT T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
			USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;
	
		INSERT INTO STV.T_STL_MVR_NRFDT
			(ID,
			 PERIOD,
			 PRODUCT_CODE,
			 CUSTOMER_CODE,
			 CUSTOMER_NAME,
			 CUSTOMER_TYPE,
			 ORIG_ORG_CODE,
			 ORIG_ORG_NAME,
			 DEST_ORG_CODE,
			 DEST_ORG_NAME,
			 VOUCHER_BEGIN_TIME,
			 VOUCHER_END_TIME,
			 CLAIM_DESTO_PAY_APPLY, --【1】
			 CLAIM_DESTT_COST, --【2】
			 CLAIM_DESTT_INCOME, --【3】
			 CLAIM_DESTT_PAY_APPLY, --【4】
			 CLAIM_DESTT_WO_DEST_RCVO_NPOD, --【5】
			 CLAIM_DESTT_WO_DEST_RCVO_POD, --【6】
			 CLAIM_DESTT_WO_DEST_RCVT_NPOD, --【7】
			 CLAIM_DESTT_WO_DEST_RCVT_POD, --【8】
			 COD_UR_CD_NPOD, --【9】
			 COD_UR_CH_NPOD, --【10】
			 CPO_DEST_PAY_APPLY, --【11】
			 CPT_DEST_PAY_APPLY, --【12】
			 CUST_DRT_WO_DEST_RCVO_NPOD, --【13】
			 CUST_DRT_WO_DEST_RCVO_POD, --【14】
			 CUST_DRT_WO_DEST_RCVT_NPOD, --【15】
			 CUST_DRT_WO_DEST_RCVT_POD, --【16】
			 RD_DESTO_PAY_APPLY, --【17】
			 RD_DESTT_COST, --【18】
			 RD_DESTT_INCOME, --【19】
			 RD_DESTT_PAY_APPLY, --【20】
			 RD_DESTT_WO_DEST_RCVO_NPOD, --【21】
			 RD_DESTT_WO_DEST_RCVO_POD, --【22】
			 RD_DESTT_WO_DEST_RCVT_NPOD, --【23】
			 RD_DESTT_WO_DEST_RCVT_POD, --【24】
			 URO_DEST_CD_NPOD, --【25】
			 URO_DEST_CD_POD, --【26】
			 URO_DEST_CH_NPOD, --【27】
			 URO_DEST_CH_POD, --【28】
			 URT_DEST_CD_NPOD, --【29】
			 URT_DEST_CD_POD, --【30】
			 URT_DEST_CH_NPOD, --【31】
			 URT_DEST_CH_POD --【32】
			 )
			SELECT SYS_GUID(),
						 P_PERIOD,
						 T.PRODUCT_CODE,
						 T.CUSTOMER_CODE,
						 T.CUSTOMER_NAME,
						 T.CUSTOMER_TYPE,
						 T.ORIG_ORG_CODE,
						 T.ORIG_ORG_NAME,
						 T.DEST_ORG_CODE,
						 T.DEST_ORG_NAME,
						 P_BEGIN_DATE,
						 P_END_DATE,
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTO_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTO_PAY_APPLY, --01理赔付款申请【1】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTT_COST,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTT_COST, --02理赔入成本【2】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTT_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTT_INCOME, --02理赔冲收入【3】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTT_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTT_PAY_APPLY, --02理赔付款申请【4】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTT_WO_DEST_RCVO_NPOD, --02理赔冲01到达应收未签收【5】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTT_WO_DEST_RCVO_POD, --02理赔冲01到达应收已签收【6】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTT_WO_DEST_RCVT_NPOD, --02理赔冲02到达应收未签收【7】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTT_WO_DEST_RCVT_POD, --02理赔冲02到达应收已签收【8】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.COD_UR_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) COD_UR_CD_NPOD, --还款代收货款银行未签收【9】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.COD_UR_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) COD_UR_CH_NPOD, --还款代收货款现金未签收【10】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CPO_DEST_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CPO_DEST_PAY_APPLY, --01到达服务补救付款申请【11】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CPT_DEST_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CPT_DEST_PAY_APPLY, --02到达服务补救付款申请【12】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_NPODD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_DEST_RCVO_NPOD, --02预收客户冲01应收到付运费未签收【13】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_PODD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_DEST_RCVO_POD, --02预收客户冲01应收到付运费已签收【14】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_NPODD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_DEST_RCVT_NPOD, --02预收客户冲02应收到付运费未签收【15】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_PODD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_DEST_RCVT_POD, --02预收客户冲02应收到付运费已签收【16】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_PAY_APPLY, --01退运费付款申请【17】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTT_COST,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTT_COST, --02退运费入成本【18】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTT_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTT_INCOME, --02退运费冲收入【19】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTT_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTT_PAY_APPLY, --02退运费付款申请【20】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTT_WO_DEST_RCVO_NPOD, --02退运费冲01到达应收未签收【21】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTT_WO_DEST_RCVO_POD, --02退运费冲01到达应收已签收【22】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTT_WO_DEST_RCVT_NPOD, --02退运费冲02到达应收未签收【23】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTT_WO_DEST_RCVT_POD, --02退运费冲02到达应收已签收【24】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_DEST_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_DEST_CD_NPOD, --还款银行未签收【25】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_DEST_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_DEST_CD_POD, --还款银行已签收【26】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_DEST_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_DEST_CH_NPOD, --还款现金未签收【27】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_DEST_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_DEST_CH_POD, --还款现金已签收【28】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URT_DEST_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URT_DEST_CD_NPOD, --还款银行未签收【29】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URT_DEST_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URT_DEST_CD_POD, --还款银行已签收【30】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URT_DEST_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URT_DEST_CH_NPOD, --还款现金未签收【31】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URT_DEST_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URT_DEST_CH_POD --还款现金已签收【32】
				FROM STV.T_STL_NMVS T
			 WHERE T.PERIOD >= V_BEGIN_PERIOD
						 AND T.PERIOD < V_END_PERIOD
						 AND T.AMOUNT <> 0
						 AND T.PRODUCT_CODE IN (PKG_STL_COMMON.PRODUCT_CODE_FLF, --精准卡航
																		PKG_STL_COMMON.PRODUCT_CODE_FSF, --精准城运
																		PKG_STL_COMMON.PRODUCT_CODE_LRF, --精准汽运(长途)
																		PKG_STL_COMMON.PRODUCT_CODE_SRF, --精准汽运(短途)
																		PKG_STL_COMMON.PRODUCT_CODE_WVH, --整车
																		PKG_STL_COMMON.PRODUCT_CODE_PKG --经济快递
																		)
						 AND T.BUSINESS_CASE IN
						 (PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_PODP,
									PKG_STL_VCH_COMMON.RD_DESTT_INCOME,
									PKG_STL_VCH_COMMON.URT_DEST_CD_POD,
									PKG_STL_VCH_COMMON.CLAIM_DESTO_PAY_APPLY,
									PKG_STL_VCH_COMMON.CLAIM_DESTT_PAY_APPLY,
									PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_NPODP,
									PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_NPODP,
									PKG_STL_VCH_COMMON.URO_DEST_CD_NPOD,
									PKG_STL_VCH_COMMON.URO_DEST_CD_POD,
									PKG_STL_VCH_COMMON.URT_DEST_CH_NPOD,
									PKG_STL_VCH_COMMON.CLAIM_DESTT_COST,
									PKG_STL_VCH_COMMON.RD_DESTT_PAY_APPLY,
									PKG_STL_VCH_COMMON.URT_DEST_CH_POD,
									PKG_STL_VCH_COMMON.CLAIM_DESTT_INCOME,
									PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_PODD,
									PKG_STL_VCH_COMMON.RD_DESTO_PAY_APPLY,
									PKG_STL_VCH_COMMON.RD_DESTT_COST,
									PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_PODP,
									PKG_STL_VCH_COMMON.CPT_DEST_PAY_APPLY,
									PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_PODD,
									PKG_STL_VCH_COMMON.URO_DEST_CH_POD,
									PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_NPODP,
									PKG_STL_VCH_COMMON.CPO_DEST_PAY_APPLY,
									PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_NPODD,
									PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_NPODD,
									PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_PODP,
									PKG_STL_VCH_COMMON.URT_DEST_CD_NPOD,
									PKG_STL_VCH_COMMON.COD_UR_CD_NPOD,
									PKG_STL_VCH_COMMON.COD_UR_CH_NPOD,
									PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_NPODP,
									PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_PODP,
									PKG_STL_VCH_COMMON.URO_DEST_CH_NPOD)
			 GROUP BY T.PRODUCT_CODE,
								T.CUSTOMER_CODE,
								T.CUSTOMER_NAME,
								T.CUSTOMER_TYPE,
								T.ORIG_ORG_CODE,
								T.ORIG_ORG_NAME,
								T.DEST_ORG_CODE,
								T.DEST_ORG_NAME;
	
		COMMIT;
	
		RETURN TRUE;
	
	EXCEPTION
		WHEN OTHERS THEN
			--插入错误日志表
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NRFDT',
																				'凭证生成02到达月报表程序异常:' || SQLERRM);
		
			--返回失败标志
			RETURN FALSE;
		
	END FUNC_VOUCHER_NRFDT;

	-----------------------------------------------------------------
	-- 偏线月报表入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NPLR(P_PERIOD     VARCHAR2,
														 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
														 P_END_DATE   DATE -- 结束时间 2012-12-22
														 ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
	 IS
	
		V_BEGIN_PERIOD VARCHAR2(50);
		V_END_PERIOD   VARCHAR2(50);
	
	BEGIN
	
		V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
		V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_NPLR T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
			USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;
	
		INSERT INTO STV.T_STL_MVR_NPLR
			(ID,
			 PERIOD,
			 PRODUCT_CODE,
			 CUSTOMER_CODE,
			 CUSTOMER_NAME,
			 CUSTOMER_TYPE,
			 ORIG_ORG_CODE,
			 ORIG_ORG_NAME,
			 DEST_ORG_CODE,
			 DEST_ORG_NAME,
			 VOUCHER_BEGIN_TIME,
			 VOUCHER_END_TIME,
			 PL_COST_CONFIRM, --【1】
			 PL_COST_ENTRY, --【2】
			 PL_COST_NCONFIRM, --【3】
			 PL_COST_PAY_APPLY, --【4】
			 PL_COST_WO_DEST_RCVO_NPOD, --【5】
			 PL_COST_WO_DEST_RCVO_POD, --【6】
			 PL_COST_WO_DEST_RCVT_NPOD, --【7】
			 PL_COST_WO_DEST_RCVT_POD, --【8】
			 PL_DR_CD, --【9】
			 PL_DR_CH, --【10】
			 PL_DR_PAY_APPLY, --【11】
			 PL_DR_WO_DEST_RCVO_NPOD, --【12】
			 PL_DR_WO_DEST_RCVO_POD, --【13】
			 PL_DR_WO_DEST_RCVT_NPOD, --【14】
			 PL_DR_WO_DEST_RCVT_POD, --【15】
			 UROP_DEST_CD_NPOD, --【16】
			 UROP_DEST_CD_POD, --【17】
			 UROP_DEST_CH_NPOD, --【18】
			 UROP_DEST_CH_POD, --【19】
			 URTP_DEST_CD_NPOD, --【20】
			 URTP_DEST_CD_POD, --【21】
			 URTP_DEST_CH_NPOD, --【22】
			 URTP_DEST_CH_POD, --【23】
			 PL_COST_OP_CRM, --【24】
			 PL_DR_WO_OR, --【25】
			 POR_ENTRY, --【26】
			 UR_POR_CH, --【27】
			 UR_POR_CD, --【28】
			 POP_WO_DRO_POD, --【29】
			 POP_WO_DRO_NPOD, --【30】
			 POP_WO_DRT_POD, --【31】
			 POP_WO_DRT_NPOD, --【32】
			 POCP_WO_OR --【33】
			 )
			SELECT SYS_GUID(),
						 P_PERIOD,
						 T.PRODUCT_CODE,
						 T.CUSTOMER_CODE,
						 T.CUSTOMER_NAME,
						 T.CUSTOMER_TYPE,
						 T.ORIG_ORG_CODE,
						 T.ORIG_ORG_NAME,
						 T.DEST_ORG_CODE,
						 T.DEST_ORG_NAME,
						 P_BEGIN_DATE,
						 P_END_DATE,
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_CONFIRM,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_CONFIRM, --偏线代理成本确认【1】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_ENTRY,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_ENTRY, --外发单录入【2】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_NCONFIRM,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_NCONFIRM, --偏线代理成本反确认【3】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_PAY_APPLY, --偏线代理成本付款申请【4】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_WO_DEST_RCVO_NPOD, --应付偏线代理成本冲01应收到付运费未签收【5】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_WO_DEST_RCVO_POD, --应付偏线代理成本冲01应收到付运费已签收【6】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_WO_DEST_RCVT_NPOD, --应付偏线代理成本冲02应收到付运费未签收【7】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_WO_DEST_RCVT_POD, --应付偏线代理成本冲02应收到付运费已签收【8】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_DR_CD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_DR_CD, --预收偏线代理银行【9】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_DR_CH,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_DR_CH, --预收偏线代理现金【10】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_DR_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_DR_PAY_APPLY, --偏线退预收付款申请【11】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVO_NPODD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_DR_WO_DEST_RCVO_NPOD, --预收偏线代理冲01应收到付运费未签收【12】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVO_PODD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_DR_WO_DEST_RCVO_POD, --预收偏线代理冲01应收到付运费已签收【13】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVT_NPODD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_DR_WO_DEST_RCVT_NPOD, --预收偏线代理冲02应收到付运费未签收【14】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVT_PODD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_DR_WO_DEST_RCVT_POD, --预收偏线代理冲02应收到付运费已签收【15】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UROP_DEST_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) UROP_DEST_CD_NPOD, --还款银行未签收【16】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UROP_DEST_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) UROP_DEST_CD_POD, --还款银行已签收【17】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UROP_DEST_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) UROP_DEST_CH_NPOD, --还款现金未签收【18】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UROP_DEST_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) UROP_DEST_CH_POD, --还款现金已签收【19】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URTP_DEST_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URTP_DEST_CD_NPOD, --还款银行未签收【20】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URTP_DEST_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URTP_DEST_CD_POD, --还款银行已签收【21】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URTP_DEST_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URTP_DEST_CH_NPOD, --还款现金未签收【22】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URTP_DEST_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URTP_DEST_CH_POD, --还款现金已签收【23】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_OP_CRM,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_OP_CRM, --其它应付成本确认【24】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_DR_WO_OR,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_DR_WO_OR, --预收偏线代理冲其他应收【25】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.POR_ENTRY,
														NVL(AMOUNT, 0),
														0)),
								 0) POR_ENTRY, --偏线其他应收录入【26】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UR_POR_CH,
														NVL(AMOUNT, 0),
														0)),
								 0) UR_POR_CH, --还款偏线其他应收现金【27】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UR_POR_CD,
														NVL(AMOUNT, 0),
														0)),
								 0) UR_POR_CD, --还款偏线其他应收银行【28】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.POP_WO_DRO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) POP_WO_DRO_POD, --其他应付冲01应收到付运费已签收【29】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.POP_WO_DRO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) POP_WO_DRO_NPOD, --其他应付冲01应收到付运费未签收【30】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.POP_WO_DRT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) POP_WO_DRT_POD, --其他应付冲02应收到付运费已签收【31】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.POP_WO_DRT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) POP_WO_DRT_NPOD, --其他应付冲02应收到付运费未签收【32】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.POCP_WO_OR,
														NVL(AMOUNT, 0),
														0)),
								 0) POCP_WO_OR --成本应付其他应付冲其他应收【33】
				FROM STV.T_STL_NMVS T
			 WHERE T.PERIOD >= V_BEGIN_PERIOD
						 AND T.PERIOD < V_END_PERIOD
						 AND T.AMOUNT <> 0
						 AND T.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PLF -- 偏线
						 AND T.BUSINESS_CASE IN
						 (PKG_STL_VCH_COMMON.PL_COST_ENTRY,
									PKG_STL_VCH_COMMON.UROP_DEST_CH_POD,
									PKG_STL_VCH_COMMON.PL_DR_CH,
									PKG_STL_VCH_COMMON.UROP_DEST_CH_NPOD,
									PKG_STL_VCH_COMMON.URTP_DEST_CD_NPOD,
									PKG_STL_VCH_COMMON.PL_COST_PAY_APPLY,
									PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_NPODP,
									PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVO_PODD,
									PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVT_PODD,
									PKG_STL_VCH_COMMON.URTP_DEST_CD_POD,
									PKG_STL_VCH_COMMON.URTP_DEST_CH_NPOD,
									PKG_STL_VCH_COMMON.PL_COST_NCONFIRM,
									PKG_STL_VCH_COMMON.UROP_DEST_CD_NPOD,
									PKG_STL_VCH_COMMON.PL_COST_CONFIRM,
									PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_PODP,
									PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_PODP,
									PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVO_NPODD,
									PKG_STL_VCH_COMMON.URTP_DEST_CH_POD,
									PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_NPODP,
									PKG_STL_VCH_COMMON.PL_DR_CD,
									PKG_STL_VCH_COMMON.PL_DR_PAY_APPLY,
									PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVT_NPODD,
									PKG_STL_VCH_COMMON.UROP_DEST_CD_POD,
									PKG_STL_VCH_COMMON.PL_COST_OP_CRM,
									PKG_STL_VCH_COMMON.PL_DR_WO_OR,
									PKG_STL_VCH_COMMON.POR_ENTRY,
									PKG_STL_VCH_COMMON.UR_POR_CH,
									PKG_STL_VCH_COMMON.UR_POR_CD,
									PKG_STL_VCH_COMMON.POP_WO_DRO_POD,
									PKG_STL_VCH_COMMON.POP_WO_DRO_NPOD,
									PKG_STL_VCH_COMMON.POP_WO_DRT_POD,
									PKG_STL_VCH_COMMON.POP_WO_DRT_NPOD,
									PKG_STL_VCH_COMMON.POCP_WO_OR)
			 GROUP BY T.PRODUCT_CODE,
								T.CUSTOMER_CODE,
								T.CUSTOMER_NAME,
								T.CUSTOMER_TYPE,
								T.ORIG_ORG_CODE,
								T.ORIG_ORG_NAME,
								T.DEST_ORG_CODE,
								T.DEST_ORG_NAME;
	
		COMMIT;
	
		RETURN TRUE;
	
	EXCEPTION
		WHEN OTHERS THEN
			--插入错误日志表
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NPLR',
																				'凭证生成偏线月报表程序异常:' || SQLERRM);
		
			--返回失败标志
			RETURN FALSE;
		
	END FUNC_VOUCHER_NPLR;

	-----------------------------------------------------------------
	-- 空运月报表入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NAFR(P_PERIOD     VARCHAR2,
														 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
														 P_END_DATE   DATE -- 结束时间 2012-12-22
														 ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
	 IS
	
		V_BEGIN_PERIOD VARCHAR2(50);
		V_END_PERIOD   VARCHAR2(50);
	
	BEGIN
	
		V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
		V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_NAFR T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
			USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;
	
		INSERT INTO STV.T_STL_MVR_NAFR
			(ID,
			 PERIOD,
			 PRODUCT_CODE,
			 CUSTOMER_CODE,
			 CUSTOMER_NAME,
			 CUSTOMER_TYPE,
			 ORIG_ORG_CODE,
			 ORIG_ORG_NAME,
			 DEST_ORG_CODE,
			 DEST_ORG_NAME,
			 VOUCHER_BEGIN_TIME,
			 VOUCHER_END_TIME,
			 AIR_COD_CD_UR_NPOD, --【1】
			 AIR_COD_CD_UR_POD, --【2】
			 AIR_COD_CH_UR_NPOD, --【3】
			 AIR_COD_CH_UR_POD, --【4】
			 AIR_COD_COST_WO_COD_RCV_NPOD, --【5】
			 AIR_COD_DPAY_WO_COD_RCV_POD, --【6】
			 AIR_COD_OPAY_WO_COD_RCV_NPOD, --【7】
			 AIR_COD_OTH_PAY_WO_COD_RCV_POD, --【8】
			 AIR_COD_POD_WO, --【9】
			 AIR_COD_UPD_WO, --【10】
			 AIR_COST_DEST_AGENCY_PAY_CFRM, --【11】
			 AIR_COST_DEST_AGENCY_PAY_GEN, --【12】
			 AIR_COST_DEST_AGENCY_PAY_NCFRM, --【13】
			 AIR_COST_FEE_CONFIRM, --【14】
			 AIR_COST_ORIG_AGENCY_PAY_CFRM, --【15】
			 AIR_COST_OTH_PAY_COST_CFRM, --【16】
			 AIR_COST_PAY_APPLY, --【17】
			 AIR_DR_CD, --【18】
			 AIR_DR_CH, --【19】
			 AIR_DR_PAY_APPLY, --【20】
			 AIR_DR_WO_COD_RCV_NPOD, --【21】
			 AIR_DR_WO_COD_RCV_POD, --【22】
			 AIR_DR_WO_DEST_RCVO_NPOD, --【23】
			 AIR_DR_WO_DEST_RCVO_POD, --【24】
			 AIR_DR_WO_DEST_RCVT_NPOD, --【25】
			 AIR_DR_WO_DEST_RCVT_POD, --【26】
			 AIR_DR_WO_OTH_RCV, --【27】
			 AOR_CD_UR, --【28】
			 AOR_CH_UR, --【29】
			 AOR_ENTRY, --【30】
			 APT_AIR_COMPANY, --【31】
			 APT_WO_AIR_PAY, --【32】
			 APT_WO_OTH_PAY, --【33】
			 APWR_COST_WO_DEST_RCVO_NPOD, --【34】
			 APWR_COST_WO_DEST_RCVO_POD, --【35】
			 APWR_COST_WO_DEST_RCVT_NPOD, --【36】
			 APWR_COST_WO_DEST_RCVT_POD, --【37】
			 APWR_OTH_PAY_WO_DEST_RCVO_NPOD, --【38】
			 APWR_OTH_PAY_WO_DEST_RCVO_POD, --【39】
			 APWR_OTH_PAY_WO_DEST_RCVT_NPOD, --【40】
			 APWR_OTH_PAY_WO_DEST_RCVT_POD, --【41】
			 APWR_OTH_PAY_WO_OTH_RCV, --【42】
			 BWOR, --【43】
			 UROA_DEST_CD_NPOD, --【44】
			 UROA_DEST_CD_POD, --【45】
			 UROA_DEST_CH_NPOD, --【46】
			 UROA_DEST_CH_POD, --【47】
			 URTA_DEST_CD_NPOD, --【48】
			 URTA_DEST_CD_POD, --【49】
			 URTA_DEST_CH_NPOD, --【50】
			 URTA_DEST_CH_POD --【51】
			 )
			SELECT SYS_GUID(),
						 P_PERIOD,
						 T.PRODUCT_CODE,
						 T.CUSTOMER_CODE,
						 T.CUSTOMER_NAME,
						 T.CUSTOMER_TYPE,
						 T.ORIG_ORG_CODE,
						 T.ORIG_ORG_NAME,
						 T.DEST_ORG_CODE,
						 T.DEST_ORG_NAME,
						 P_BEGIN_DATE,
						 P_END_DATE,
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COD_CD_UR_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COD_CD_UR_NPOD, --空运还款代收货款银行未签收【1】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COD_CD_UR_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COD_CD_UR_POD, --空运还款代收货款银行已签收【2】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COD_CH_UR_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COD_CH_UR_NPOD, --空运还款代收货款现金未签收【3】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COD_CH_UR_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COD_CH_UR_POD, --空运还款代收货款现金已签收【4】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COD_COST_WO_COD_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COD_COST_WO_COD_RCV_NPOD, --空运到达代理应付冲应收代收货款未签收【5】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COD_DPAY_WO_COD_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COD_DPAY_WO_COD_RCV_POD, --空运到达代理应付冲应收代收货款已签收【6】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COD_OPAY_WO_COD_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COD_OPAY_WO_COD_RCV_NPOD, --空运其他应付冲应收代收货款未签收【7】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COD_OTH_PAY_WO_COD_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COD_OTH_PAY_WO_COD_RCV_POD, --空运其他应付冲应收代收货款已签收【8】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COD_POD_WO,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COD_POD_WO, --空运签收时已核销代收货款【9】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COD_UPD_WO,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COD_UPD_WO, --空运反签收时已核销代收货款【10】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COST_DEST_AGENCY_PAY_CFRM,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COST_DEST_AGENCY_PAY_CFRM, --空运到达代理应付成本确认【11】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COST_DEST_AGENCY_PAY_GEN,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COST_DEST_AGENCY_PAY_GEN, --空运到达代理应付生成【12】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COST_DEST_AGENCY_PAY_NCFRM,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COST_DEST_AGENCY_PAY_NCFRM, --空运到达代理应付成本反确认【13】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COST_FEE_CONFIRM,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COST_FEE_CONFIRM, --空运航空公司运费确认【14】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COST_ORIG_AGENCY_PAY_CFRM,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COST_ORIG_AGENCY_PAY_CFRM, --空运出发代理应付确认【15】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COST_OTH_PAY_COST_CFRM,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COST_OTH_PAY_COST_CFRM, --其它应付成本确认【16】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COST_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COST_PAY_APPLY, --空运成本付款申请【17】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_DR_CD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_DR_CD, --预收空运代理银行【18】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_DR_CH,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_DR_CH, --预收空运代理现金【19】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_DR_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_DR_PAY_APPLY, --空运退预收付款申请【20】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_DR_WO_COD_RCV_NPOD, --预收空运代理冲应收代收货款未签收【21】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_DR_WO_COD_RCV_POD, --预收空运代理冲应收代收货款已签收【22】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_DR_WO_DEST_RCVO_NPOD, --预收空运代理冲01应收到付运费未签收【23】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_DR_WO_DEST_RCVO_POD, --预收空运代理冲01应收到付运费已签收【24】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_DR_WO_DEST_RCVT_NPOD, --预收空运代理冲02应收到付运费未签收【25】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_DR_WO_DEST_RCVT_POD, --预收空运代理冲02应收到付运费已签收【26】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_DR_WO_OTH_RCV,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_DR_WO_OTH_RCV, --预收空运代理冲其他应收【27】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AOR_CD_UR,
														NVL(AMOUNT, 0),
														0)),
								 0) AOR_CD_UR, --还款空运其他应收银行【28】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AOR_CH_UR,
														NVL(AMOUNT, 0),
														0)),
								 0) AOR_CH_UR, --还款空运其他应收现金【29】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AOR_ENTRY,
														NVL(AMOUNT, 0),
														0)),
								 0) AOR_ENTRY, --空运其他应收录入【30】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.APT_AIR_COMPANY,
														NVL(AMOUNT, 0),
														0)),
								 0) APT_AIR_COMPANY, --预付航空公司【31】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.APT_WO_AIR_PAY,
														NVL(AMOUNT, 0),
														0)),
								 0) APT_WO_AIR_PAY, --预付冲应付航空公司【32】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.APT_WO_OTH_PAY,
														NVL(AMOUNT, 0),
														0)),
								 0) APT_WO_OTH_PAY, --预付冲其他应付【33】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) APWR_COST_WO_DEST_RCVO_NPOD, --应付到达代理成本冲01应收到付运费未签收【34】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) APWR_COST_WO_DEST_RCVO_POD, --应付到达代理成本冲01应收到付运费已签收【35】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) APWR_COST_WO_DEST_RCVT_NPOD, --应付到达代理成本冲02应收到付运费未签收【36】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) APWR_COST_WO_DEST_RCVT_POD, --应付到达代理成本冲02应收到付运费已签收【37】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) APWR_OTH_PAY_WO_DEST_RCVO_NPOD, --其他应付冲01应收到付运费未签收【38】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) APWR_OTH_PAY_WO_DEST_RCVO_POD, --其他应付冲01应收到付运费已签收【39】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) APWR_OTH_PAY_WO_DEST_RCVT_NPOD, --其他应付冲02应收到付运费未签收【40】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) APWR_OTH_PAY_WO_DEST_RCVT_POD, --其他应付冲02应收到付运费已签收【41】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_OTH_RCV,
														NVL(AMOUNT, 0),
														0)),
								 0) APWR_OTH_PAY_WO_OTH_RCV, --其他应付冲其他应收【42】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.BWOR,
														NVL(AMOUNT, 0),
														0)),
								 0) BWOR, --坏账冲其他应收【43】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UROA_DEST_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) UROA_DEST_CD_NPOD, --还款银行未签收【44】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UROA_DEST_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) UROA_DEST_CD_POD, --还款银行已签收【45】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UROA_DEST_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) UROA_DEST_CH_NPOD, --还款现金未签收【46】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UROA_DEST_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) UROA_DEST_CH_POD, --还款现金已签收【47】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URTA_DEST_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URTA_DEST_CD_NPOD, --还款银行未签收【48】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URTA_DEST_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URTA_DEST_CD_POD, --还款银行已签收【49】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URTA_DEST_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URTA_DEST_CH_NPOD, --还款现金未签收【50】
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URTA_DEST_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URTA_DEST_CH_POD --还款现金已签收【51】
				FROM STV.T_STL_NMVS T
			 WHERE T.PERIOD >= V_BEGIN_PERIOD
						 AND T.PERIOD < V_END_PERIOD
						 AND T.AMOUNT <> 0
						 AND T.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_AF --空运
						 AND T.BUSINESS_CASE IN
						 (PKG_STL_VCH_COMMON.AIR_COST_OTH_PAY_COST_CFRM,
									PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVT_POD,
									PKG_STL_VCH_COMMON.APT_AIR_COMPANY,
									PKG_STL_VCH_COMMON.APT_WO_AIR_PAY,
									PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_POD,
									PKG_STL_VCH_COMMON.AIR_COD_CD_UR_POD,
									PKG_STL_VCH_COMMON.AIR_DR_WO_OTH_RCV,
									PKG_STL_VCH_COMMON.AOR_ENTRY,
									PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_NPOD,
									PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_POD,
									PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_POD,
									PKG_STL_VCH_COMMON.UROA_DEST_CH_NPOD,
									PKG_STL_VCH_COMMON.URTA_DEST_CH_POD,
									PKG_STL_VCH_COMMON.AIR_COD_OTH_PAY_WO_COD_RCV_POD,
									PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVO_NPOD,
									PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_NPOD,
									PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_POD,
									PKG_STL_VCH_COMMON.URTA_DEST_CH_NPOD,
									PKG_STL_VCH_COMMON.AIR_COD_OPAY_WO_COD_RCV_NPOD,
									PKG_STL_VCH_COMMON.AOR_CH_UR,
									PKG_STL_VCH_COMMON.APT_WO_OTH_PAY,
									PKG_STL_VCH_COMMON.UROA_DEST_CD_NPOD,
									PKG_STL_VCH_COMMON.UROA_DEST_CD_POD,
									PKG_STL_VCH_COMMON.AIR_COD_CH_UR_NPOD,
									PKG_STL_VCH_COMMON.AIR_COST_DEST_AGENCY_PAY_NCFRM,
									PKG_STL_VCH_COMMON.AIR_COST_ORIG_AGENCY_PAY_CFRM,
									PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVT_NPOD,
									PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_NPOD,
									PKG_STL_VCH_COMMON.AIR_COD_CD_UR_NPOD,
									PKG_STL_VCH_COMMON.AIR_COST_DEST_AGENCY_PAY_CFRM,
									PKG_STL_VCH_COMMON.AIR_DR_CD,
									PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVO_POD,
									PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_NPOD,
									PKG_STL_VCH_COMMON.AIR_COD_CH_UR_POD,
									PKG_STL_VCH_COMMON.AIR_COD_COST_WO_COD_RCV_NPOD,
									PKG_STL_VCH_COMMON.AIR_COD_POD_WO,
									PKG_STL_VCH_COMMON.AIR_COST_DEST_AGENCY_PAY_GEN,
									PKG_STL_VCH_COMMON.AIR_DR_CH,
									PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_POD,
									PKG_STL_VCH_COMMON.BWOR,
									PKG_STL_VCH_COMMON.UROA_DEST_CH_POD,
									PKG_STL_VCH_COMMON.URTA_DEST_CD_POD,
									PKG_STL_VCH_COMMON.AIR_COD_DPAY_WO_COD_RCV_POD,
									PKG_STL_VCH_COMMON.AIR_COD_UPD_WO,
									PKG_STL_VCH_COMMON.AIR_COST_FEE_CONFIRM,
									PKG_STL_VCH_COMMON.AIR_COST_PAY_APPLY,
									PKG_STL_VCH_COMMON.AIR_DR_PAY_APPLY,
									PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_NPOD,
									PKG_STL_VCH_COMMON.AOR_CD_UR,
									PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_OTH_RCV,
									PKG_STL_VCH_COMMON.URTA_DEST_CD_NPOD)
			 GROUP BY T.PRODUCT_CODE,
								T.CUSTOMER_CODE,
								T.CUSTOMER_NAME,
								T.CUSTOMER_TYPE,
								T.ORIG_ORG_CODE,
								T.ORIG_ORG_NAME,
								T.DEST_ORG_CODE,
								T.DEST_ORG_NAME;
	
		COMMIT;
	
		RETURN TRUE;
	
	EXCEPTION
		WHEN OTHERS THEN
			--插入错误日志表
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NAFR',
																				'凭证生成空运月报表程序异常:' || SQLERRM);
		
			--返回失败标志
			RETURN FALSE;
		
	END FUNC_VOUCHER_NAFR;

	-----------------------------------------------------------------
	-- 始发到达往来月报表入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NRFI(P_PERIOD     VARCHAR2,
														 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
														 P_END_DATE   DATE -- 结束时间 2012-12-22
														 ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
	 IS
	
		V_BEGIN_PERIOD VARCHAR2(50);
		V_END_PERIOD   VARCHAR2(50);
	
	BEGIN
	
		V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
		V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_NRFI T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
			USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;
	
		--始发
		INSERT INTO STV.T_STL_MVR_NRFI
			(ID,
			 PERIOD,
			 ORG_CODE,
			 ORG_NAME,
			 ORG_TYPE,
			 INVOICE_MARK,
			 VOUCHER_BEGIN_TIME,
			 VOUCHER_END_TIME,
			 CLAIM_DESTO_INCOME, --【1】
			 CLAIM_DESTO_PAY_APPLY, --【2】
			 CLAIM_DESTO_WO_DEST_RCVO_NPOD, --【3】
			 CLAIM_DESTO_WO_DEST_RCVO_POD, --【4】
			 CLAIM_DESTO_WO_DEST_RCVT_NPOD, --【5】
			 CLAIM_DESTO_WO_DEST_RCVT_POD, --【6】
			 CLAIM_DESTT_INCOME, --【7】
			 CLAIM_DESTT_WO_DEST_RCVO_NPOD, --【8】
			 CLAIM_DESTT_WO_DEST_RCVO_POD, --【9】
			 CLAIM_DESTT_WO_DEST_RCVT_NPOD, --【10】
			 CLAIM_DESTT_WO_DEST_RCVT_POD, --【11】
			 CLAIM_ORIGO_PAY_APPLY, --【12】
			 CLAIM_ORIGO_WO_ORIG_RCVT_NPOD, --【13】
			 CLAIM_ORIGO_WO_ORIG_RCVT_POD, --【14】
			 CLAIM_ORIGT_ORIG_RCVO_POD, --【15】
			 CLAIM_ORIGT_WO_ORIG_RCVO_NPOD, --【16】
			 COD_PAY_WO_DEST_RCVO_NPOD, --【17】
			 COD_PAY_WO_DEST_RCVO_POD, --【18】
			 COD_PAY_WO_DEST_RCVT_NPOD, --【19】
			 COD_PAY_WO_DEST_RCVT_POD, --【20】
			 COD_PAY_WO_ORIG_RCVO_NPOD, --【21】
			 COD_PAY_WO_ORIG_RCVO_POD, --【22】
			 COD_PAY_WO_ORIG_RCVT_NPOD, --【23】
			 COD_PAY_WO_ORIG_RCVT_POD, --【24】
			 COD_PAY_WO_OTH_RCVO, --【25】
			 COD_PAY_WO_OTH_RCVT, --【26】
			 COD_UR_CD_NPOD, --【27】
			 COD_UR_CH_NPOD, --【28】
			 CPO_DEST_PAY_APPLY, --【29】
			 CPO_ORIG_PAY_APPLY, --【30】
			 CUST_DRO_PAY_APPLY, --【31】
			 CUST_DRO_WO_DEST_RCVO_NPOD, --【32】
			 CUST_DRO_WO_DEST_RCVO_POD, --【33】
			 CUST_DRO_WO_DEST_RCVT_NPOD, --【34】
			 CUST_DRO_WO_DEST_RCVT_POD, --【35】
			 CUST_DRO_WO_ORIG_RCVT_NPOD, --【36】
			 CUST_DRO_WO_ORIG_RCVT_POD, --【37】
			 CUST_DRT_WO_DEST_RCVO_NPOD, --【38】
			 CUST_DRT_WO_DEST_RCVO_POD, --【39】
			 CUST_DRT_WO_DEST_RCVT_NPOD, --【40】
			 CUST_DRT_WO_DEST_RCVT_POD, --【41】
			 CUST_DRT_WO_ORIG_RCVO_NPOD, --【42】
			 CUST_DRT_WO_ORIG_RCVO_POD, --【43】
			 CUST_DR_OCD, --【44】
			 CUST_DR_OCH, --【45】
			 DEO_CD, --【46】
			 DEO_CH, --【47】
			 OR_CD_PBIO, --【48】
			 OR_CD_UR_RCVO, --【49】
			 OR_CH_PBIO, --【50】
			 OR_CH_UR_RCVO, --【51】
			 OR_CLAIM_PAYO_WO_RCVT, --【52】
			 OR_CLAIM_PAYT_WO_RCVO, --【53】
			 OR_CUST_DRO_WO_RCVT, --【54】
			 OR_CUST_DRT_WO_RCVO, --【55】
			 RD_DESTO_DEST_RCVO_POD, --【56】
			 RD_DESTO_DEST_RCVT_POD, --【57】
			 RD_DESTO_INCOME, --【58】
			 RD_DESTO_PAY_APPLY, --【59】
			 RD_DESTO_WO_DEST_RCVO_NPOD, --【60】
			 RD_DESTO_WO_DEST_RCVT_NPOD, --【61】
			 RD_DESTT_INCOME, --【62】
			 RD_DESTT_WO_DEST_RCVO_NPOD, --【63】
			 RD_DESTT_WO_DEST_RCVO_POD, --【64】
			 RD_DESTT_WO_DEST_RCVT_NPOD, --【65】
			 RD_DESTT_WO_DEST_RCVT_POD, --【66】
			 RD_ORIGO_PAY_APPLY, --【67】
			 RD_ORIGO_WO_ORIG_RCVT_NPOD, --【68】
			 RD_ORIGO_WO_ORIG_RCVT_POD, --【69】
			 RD_ORIGT_WO_ORIG_RCVO_NPOD, --【70】
			 RD_ORIGT_WO_ORIG_RCVO_POD, --【71】
			 SFO_PAY_APPLY, --【72】
			 URO_DEST_CD_NPOD, --【73】
			 URO_DEST_CD_POD, --【74】
			 URO_DEST_CH_NPOD, --【75】
			 URO_DEST_CH_POD, --【76】
			 URO_ORIG_CD_NPOD, --【77】
			 URO_ORIG_CD_POD, --【78】
			 URO_ORIG_CH_NPOD, --【79】
			 URO_ORIG_CH_POD, --【80】
			 URT_DEST_CD_NPOD, --【81】
			 URT_DEST_CD_POD, --【82】
			 URT_DEST_CH_NPOD, --【83】
			 URT_DEST_CH_POD --【84】
			 )
			SELECT SYS_GUID(),
						 P_PERIOD,
						 ORG_CODE,
						 ORG_NAME,
						 ORG_TYPE,
						 INVOICE_MARK,
						 P_BEGIN_DATE VOUCHER_BEGIN_TIME,
						 P_END_DATE VOUCHER_END_TIME,
						 SUM(CLAIM_DESTO_INCOME), -- 【1】
						 SUM(CLAIM_DESTO_PAY_APPLY), -- 【2】
						 SUM(CLAIM_DESTO_WO_DEST_RCVO_NPOD), -- 【3】
						 SUM(CLAIM_DESTO_WO_DEST_RCVO_POD), -- 【4】
						 SUM(CLAIM_DESTO_WO_DEST_RCVT_NPOD), -- 【5】
						 SUM(CLAIM_DESTO_WO_DEST_RCVT_POD), -- 【6】
						 SUM(CLAIM_DESTT_INCOME), -- 【7】
						 SUM(CLAIM_DESTT_WO_DEST_RCVO_NPOD), -- 【8】
						 SUM(CLAIM_DESTT_WO_DEST_RCVO_POD), -- 【9】
						 SUM(CLAIM_DESTT_WO_DEST_RCVT_NPOD), -- 【10】
						 SUM(CLAIM_DESTT_WO_DEST_RCVT_POD), -- 【11】
						 SUM(CLAIM_ORIGO_PAY_APPLY), -- 【12】
						 SUM(CLAIM_ORIGO_WO_ORIG_RCVT_NPOD), -- 【13】
						 SUM(CLAIM_ORIGO_WO_ORIG_RCVT_POD), -- 【14】
						 SUM(CLAIM_ORIGT_ORIG_RCVO_POD), -- 【15】
						 SUM(CLAIM_ORIGT_WO_ORIG_RCVO_NPOD), -- 【16】
						 SUM(COD_PAY_WO_DEST_RCVO_NPOD), -- 【17】
						 SUM(COD_PAY_WO_DEST_RCVO_POD), -- 【18】
						 SUM(COD_PAY_WO_DEST_RCVT_NPOD), -- 【19】
						 SUM(COD_PAY_WO_DEST_RCVT_POD), -- 【20】
						 SUM(COD_PAY_WO_ORIG_RCVO_NPOD), -- 【21】
						 SUM(COD_PAY_WO_ORIG_RCVO_POD), -- 【22】
						 SUM(COD_PAY_WO_ORIG_RCVT_NPOD), -- 【23】
						 SUM(COD_PAY_WO_ORIG_RCVT_POD), -- 【24】
						 SUM(COD_PAY_WO_OTH_RCVO), -- 【25】
						 SUM(COD_PAY_WO_OTH_RCVT), -- 【26】
						 SUM(COD_UR_CD_NPOD), -- 【27】
						 SUM(COD_UR_CH_NPOD), -- 【28】
						 SUM(CPO_DEST_PAY_APPLY), -- 【29】
						 SUM(CPO_ORIG_PAY_APPLY), -- 【30】
						 SUM(CUST_DRO_PAY_APPLY), -- 【31】
						 SUM(CUST_DRO_WO_DEST_RCVO_NPOD), -- 【32】
						 SUM(CUST_DRO_WO_DEST_RCVO_POD), -- 【33】
						 SUM(CUST_DRO_WO_DEST_RCVT_NPOD), -- 【34】
						 SUM(CUST_DRO_WO_DEST_RCVT_POD), -- 【35】
						 SUM(CUST_DRO_WO_ORIG_RCVT_NPOD), -- 【36】
						 SUM(CUST_DRO_WO_ORIG_RCVT_POD), -- 【37】
						 SUM(CUST_DRT_WO_DEST_RCVO_NPOD), -- 【38】
						 SUM(CUST_DRT_WO_DEST_RCVO_POD), -- 【39】
						 SUM(CUST_DRT_WO_DEST_RCVT_NPOD), -- 【40】
						 SUM(CUST_DRT_WO_DEST_RCVT_POD), -- 【41】
						 SUM(CUST_DRT_WO_ORIG_RCVO_NPOD), -- 【42】
						 SUM(CUST_DRT_WO_ORIG_RCVO_POD), -- 【43】
						 SUM(CUST_DR_OCD), -- 【44】
						 SUM(CUST_DR_OCH), -- 【45】
						 SUM(DEO_CD), -- 【46】
						 SUM(DEO_CH), -- 【47】
						 SUM(OR_CD_PBIO), -- 【48】
						 SUM(OR_CD_UR_RCVO), -- 【49】
						 SUM(OR_CH_PBIO), -- 【50】
						 SUM(OR_CH_UR_RCVO), -- 【51】
						 SUM(OR_CLAIM_PAYO_WO_RCVT), -- 【52】
						 SUM(OR_CLAIM_PAYT_WO_RCVO), -- 【53】
						 SUM(OR_CUST_DRO_WO_RCVT), -- 【54】
						 SUM(OR_CUST_DRT_WO_RCVO), -- 【55】
						 SUM(RD_DESTO_DEST_RCVO_POD), -- 【56】
						 SUM(RD_DESTO_DEST_RCVT_POD), -- 【57】
						 SUM(RD_DESTO_INCOME), -- 【58】
						 SUM(RD_DESTO_PAY_APPLY), -- 【59】
						 SUM(RD_DESTO_WO_DEST_RCVO_NPOD), -- 【60】
						 SUM(RD_DESTO_WO_DEST_RCVT_NPOD), -- 【61】
						 SUM(RD_DESTT_INCOME), -- 【62】
						 SUM(RD_DESTT_WO_DEST_RCVO_NPOD), -- 【63】
						 SUM(RD_DESTT_WO_DEST_RCVO_POD), -- 【64】
						 SUM(RD_DESTT_WO_DEST_RCVT_NPOD), -- 【65】
						 SUM(RD_DESTT_WO_DEST_RCVT_POD), -- 【66】
						 SUM(RD_ORIGO_PAY_APPLY), -- 【67】
						 SUM(RD_ORIGO_WO_ORIG_RCVT_NPOD), -- 【68】
						 SUM(RD_ORIGO_WO_ORIG_RCVT_POD), -- 【69】
						 SUM(RD_ORIGT_WO_ORIG_RCVO_NPOD), -- 【70】
						 SUM(RD_ORIGT_WO_ORIG_RCVO_POD), -- 【71】
						 SUM(SFO_PAY_APPLY), -- 【72】
						 SUM(URO_DEST_CD_NPOD), -- 【73】
						 SUM(URO_DEST_CD_POD), -- 【74】
						 SUM(URO_DEST_CH_NPOD), -- 【75】
						 SUM(URO_DEST_CH_POD), -- 【76】
						 SUM(URO_ORIG_CD_NPOD), -- 【77】
						 SUM(URO_ORIG_CD_POD), -- 【78】
						 SUM(URO_ORIG_CH_NPOD), -- 【79】
						 SUM(URO_ORIG_CH_POD), -- 【80】
						 SUM(URT_DEST_CD_NPOD), -- 【81】
						 SUM(URT_DEST_CD_POD), -- 【82】
						 SUM(URT_DEST_CH_NPOD), -- 【83】
						 SUM(URT_DEST_CH_POD) -- 【84】
				FROM (SELECT T.CREDIT_ORG_CODE ORG_CODE,
										 T.CREDIT_ORG_NAME ORG_NAME,
										 T.CREDIT_ORG_TYPE ORG_TYPE, --借方部门类型
										 T.CREDIT_INVOICE_MARK INVOICE_MARK,
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTO_INCOME,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTO_INCOME, --01理赔冲收入【1】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTO_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTO_PAY_APPLY, --01理赔付款申请【2】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTO_WO_DEST_RCVO_NPOD, --01理赔冲01到达应收未签收【3】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTO_WO_DEST_RCVO_POD, --01理赔冲到01达应收已签收【4】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTO_WO_DEST_RCVT_NPOD, --01理赔冲02到达应收未签收【5】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTO_WO_DEST_RCVT_POD, --01理赔冲02到达应收已签收【6】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTT_INCOME,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTT_INCOME, --02理赔冲收入【7】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTT_WO_DEST_RCVO_NPOD, --02理赔冲01到达应收未签收【8】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTT_WO_DEST_RCVO_POD, --02理赔冲01到达应收已签收【9】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTT_WO_DEST_RCVT_NPOD, --02理赔冲02到达应收未签收【10】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTT_WO_DEST_RCVT_POD, --02理赔冲02到达应收已签收【11】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_ORIGO_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_ORIGO_PAY_APPLY, --01理赔付款申请【12】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_ORIGO_WO_ORIG_RCVT_NPOD, --01理赔冲02始发应收未签收【13】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_ORIGO_WO_ORIG_RCVT_POD, --01理赔冲02始发应收已签收【14】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_ORIGT_ORIG_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_ORIGT_ORIG_RCVO_POD, --02理赔冲01始发应收已签收【15】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_ORIGT_WO_ORIG_RCVO_NPOD, --02理赔冲01始发应收未签收【16】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_DEST_RCVO_NPOD, --应付代收货款冲01应收到付运费未签收【17】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_DEST_RCVO_POD, --应付代收货款冲01应收到付运费已签收【18】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_DEST_RCVT_NPOD, --应付代收货款冲02应收到付运费未签收【19】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_DEST_RCVT_POD, --应付代收货款冲02应收到付运费已签收【20】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_ORIG_RCVO_NPOD, --应付代收货款冲01应收始发运费未签收【21】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_ORIG_RCVO_POD, --应付代收货款冲01应收始发运费已签收【22】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_ORIG_RCVT_NPOD, --应付代收货款冲02应收始发运费未签收【23】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_ORIG_RCVT_POD, --应付代收货款冲02应收始发运费已签收【24】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_OTH_RCVO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_OTH_RCVO, --应付代收货款冲01小票应收【25】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_OTH_RCVT,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_OTH_RCVT, --应付代收货款冲02小票应收【26】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_UR_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_UR_CD_NPOD, --还款代收货款银行未签收【27】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_UR_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_UR_CH_NPOD, --还款代收货款现金未签收【28】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CPO_DEST_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CPO_DEST_PAY_APPLY, --01到达服务补救付款申请【29】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CPO_ORIG_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CPO_ORIG_PAY_APPLY, --01始发服务补救付款申请【30】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_PAY_APPLY, --01始发退预收付款申请【31】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_WO_DEST_RCVO_NPOD, --01预收客户冲01应收到付运费未签收【32】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_WO_DEST_RCVO_POD, --01预收客户冲01应收到付运费已签收【33】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_WO_DEST_RCVT_NPOD, --01预收客户冲02应收到付运费未签收【34】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_WO_DEST_RCVT_POD, --01预收客户冲02应收到付运费已签收【35】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_WO_ORIG_RCVT_NPOD, --01预收客户冲02应收始发运费未签收【36】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_WO_ORIG_RCVT_POD, --01预收客户冲02应收始发运费已签收【37】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRT_WO_DEST_RCVO_NPOD, --02预收客户冲01应收到付运费未签收【38】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRT_WO_DEST_RCVO_POD, --02预收客户冲01应收到付运费已签收【39】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRT_WO_DEST_RCVT_NPOD, --02预收客户冲02应收到付运费未签收【40】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRT_WO_DEST_RCVT_POD, --02预收客户冲02应收到付运费已签收【41】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRT_WO_ORIG_RCVO_NPOD, --02预收客户冲01应收始发运费未签收【42】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRT_WO_ORIG_RCVO_POD, --02预收客户冲01应收始发运费已签收【43】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DR_OCD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DR_OCD, --预收客户银行【44】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DR_OCH,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DR_OCH, --预收客户现金【45】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.DEO_CD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) DEO_CD, --开单银行卡【46】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.DEO_CH,
																		NVL(AMOUNT, 0),
																		0)),
												 0) DEO_CH, --开单现金【47】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CD_PBIO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CD_PBIO, --01小票银行主营业务收入【48】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CD_UR_RCVO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CD_UR_RCVO, --还款银行冲01小票应收【49】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CH_PBIO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CH_PBIO, --01小票现金主营业务收入【50】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CH_UR_RCVO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CH_UR_RCVO, --还款现金冲01小票应收【51】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CLAIM_PAYO_WO_RCVT,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CLAIM_PAYO_WO_RCVT, --01应付理赔冲02小票应收【52】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CLAIM_PAYT_WO_RCVO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CLAIM_PAYT_WO_RCVO, --02应付理赔冲01小票应收【53】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CUST_DRO_WO_RCVT,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CUST_DRO_WO_RCVT, --01预收客户冲02小票应收【54】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CUST_DRT_WO_RCVO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CUST_DRT_WO_RCVO, --02预收客户冲01小票应收【55】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTO_DEST_RCVO_POD, --01退运费冲01到达应收已签收【56】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTO_DEST_RCVT_POD, --01退运费冲02到达应收已签收【57】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTO_INCOME,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTO_INCOME, --01退运费冲收入【58】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTO_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTO_PAY_APPLY, --01退运费付款申请【59】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTO_WO_DEST_RCVO_NPOD, --01退运费冲01到达应收未签收【60】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTO_WO_DEST_RCVT_NPOD, --01退运费冲02到达应收未签收【61】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTT_INCOME,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTT_INCOME, --02退运费冲收入【62】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTT_WO_DEST_RCVO_NPOD, --02退运费冲01到达应收未签收【63】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTT_WO_DEST_RCVO_POD, --02退运费冲01到达应收已签收【64】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTT_WO_DEST_RCVT_NPOD, --02退运费冲02到达应收未签收【65】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTT_WO_DEST_RCVT_POD, --02退运费冲02到达应收已签收【66】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_ORIGO_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_ORIGO_PAY_APPLY, --01退运费付款申请【67】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_ORIGO_WO_ORIG_RCVT_NPOD, --01退运费冲02始发应收未签收【68】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_ORIGO_WO_ORIG_RCVT_POD, --01退运费冲02始发应收已签收【69】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_ORIGT_WO_ORIG_RCVO_NPOD, --02退运费冲01始发应收未签收【70】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_ORIGT_WO_ORIG_RCVO_POD, --02退运费冲01始发应收已签收【71】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.SFO_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) SFO_PAY_APPLY, --01装卸费付款申请【72】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_DEST_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_DEST_CD_NPOD, --还款银行未签收【73】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_DEST_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_DEST_CD_POD, --还款银行已签收【74】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_DEST_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_DEST_CH_NPOD, --还款现金未签收【75】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_DEST_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_DEST_CH_POD, --还款现金已签收【76】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_ORIG_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_ORIG_CD_NPOD, --还款银行未签收【77】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_ORIG_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_ORIG_CD_POD, --还款银行已签收【78】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_ORIG_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_ORIG_CH_NPOD, --还款现金未签收【79】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_ORIG_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_ORIG_CH_POD, --还款现金已签收【80】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URT_DEST_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URT_DEST_CD_NPOD, --还款银行未签收【81】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URT_DEST_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URT_DEST_CD_POD, --还款银行已签收【82】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URT_DEST_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URT_DEST_CH_NPOD, --还款现金未签收【83】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URT_DEST_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URT_DEST_CH_POD --还款现金已签收【84】
								FROM STV.T_STL_NMVSI T
							 WHERE T.PERIOD >= V_BEGIN_PERIOD
										 AND T.PERIOD < V_END_PERIOD
										 AND T.AMOUNT <> 0
										 AND T.BUSINESS_CASE IN
										 (PKG_STL_VCH_COMMON.CLAIM_DESTO_INCOME,
													PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_POD,
													PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_POD,
													PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_NPOD,
													PKG_STL_VCH_COMMON.CLAIM_ORIGT_ORIG_RCVO_POD,
													PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVO_POD,
													PKG_STL_VCH_COMMON.DEO_CH,
													PKG_STL_VCH_COMMON.OR_CD_UR_RCVO,
													PKG_STL_VCH_COMMON.OR_CH_UR_RCVO,
													PKG_STL_VCH_COMMON.OR_CLAIM_PAYT_WO_RCVO,
													PKG_STL_VCH_COMMON.RD_DESTT_INCOME,
													PKG_STL_VCH_COMMON.URO_ORIG_CD_POD,
													PKG_STL_VCH_COMMON.URT_DEST_CD_POD,
													PKG_STL_VCH_COMMON.CLAIM_DESTO_PAY_APPLY,
													PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_NPOD,
													PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVO_NPOD,
													PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_NPOD,
													PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_POD,
													PKG_STL_VCH_COMMON.DEO_CD,
													PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVT_POD,
													PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_NPOD,
													PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_POD,
													PKG_STL_VCH_COMMON.URO_DEST_CD_NPOD,
													PKG_STL_VCH_COMMON.URO_DEST_CD_POD,
													PKG_STL_VCH_COMMON.URT_DEST_CH_NPOD,
													PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_NPOD,
													PKG_STL_VCH_COMMON.COD_PAY_WO_OTH_RCVO,
													PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_POD,
													PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_NPOD,
													PKG_STL_VCH_COMMON.SFO_PAY_APPLY,
													PKG_STL_VCH_COMMON.URO_ORIG_CH_NPOD,
													PKG_STL_VCH_COMMON.URT_DEST_CH_POD,
													PKG_STL_VCH_COMMON.CLAIM_ORIGO_PAY_APPLY,
													PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVO_NPOD,
													PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVT_POD,
													PKG_STL_VCH_COMMON.CUST_DRO_PAY_APPLY,
													PKG_STL_VCH_COMMON.OR_CUST_DRT_WO_RCVO,
													PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVO_POD,
													PKG_STL_VCH_COMMON.RD_DESTO_INCOME,
													PKG_STL_VCH_COMMON.URO_ORIG_CD_NPOD,
													PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_POD,
													PKG_STL_VCH_COMMON.CLAIM_DESTT_INCOME,
													PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVO_NPOD,
													PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVO_POD,
													PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVT_NPOD,
													PKG_STL_VCH_COMMON.COD_PAY_WO_OTH_RCVT,
													PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_POD,
													PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_POD,
													PKG_STL_VCH_COMMON.CUST_DR_OCD,
													PKG_STL_VCH_COMMON.RD_DESTO_PAY_APPLY,
													PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVO_NPOD,
													PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_POD,
													PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_POD,
													PKG_STL_VCH_COMMON.CPO_ORIG_PAY_APPLY,
													PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_NPOD,
													PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_POD,
													PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_POD,
													PKG_STL_VCH_COMMON.OR_CH_PBIO,
													PKG_STL_VCH_COMMON.OR_CUST_DRO_WO_RCVT,
													PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVT_NPOD,
													PKG_STL_VCH_COMMON.RD_ORIGO_PAY_APPLY,
													PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_NPOD,
													PKG_STL_VCH_COMMON.URO_DEST_CH_POD,
													PKG_STL_VCH_COMMON.URO_ORIG_CH_POD,
													PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_NPOD,
													PKG_STL_VCH_COMMON.CPO_DEST_PAY_APPLY,
													PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_NPOD,
													PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_NPOD,
													PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_NPOD,
													PKG_STL_VCH_COMMON.OR_CLAIM_PAYO_WO_RCVT,
													PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_POD,
													PKG_STL_VCH_COMMON.URT_DEST_CD_NPOD,
													PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_NPOD,
													PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVT_NPOD,
													PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVT_POD,
													PKG_STL_VCH_COMMON.COD_UR_CD_NPOD,
													PKG_STL_VCH_COMMON.COD_UR_CH_NPOD,
													PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_POD,
													PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_NPOD,
													PKG_STL_VCH_COMMON.CUST_DR_OCH,
													PKG_STL_VCH_COMMON.OR_CD_PBIO,
													PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_NPOD,
													PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_POD,
													PKG_STL_VCH_COMMON.URO_DEST_CH_NPOD)
							 GROUP BY T.CREDIT_ORG_CODE,
												T.CREDIT_ORG_NAME,
												T.CREDIT_INVOICE_MARK,
												T.CREDIT_ORG_TYPE
							
							UNION ALL
							
							SELECT T.DEBIT_ORG_CODE ORG_CODE,
										 T.DEBIT_ORG_NAME ORG_NAME,
										 T.DEBIT_ORG_TYPE ORG_TYPE, --贷方部门类型
										 T.DEBIT_INVOICE_MARK INVOICE_MARK,
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTO_INCOME,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTO_INCOME, --01理赔冲收入【1】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTO_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTO_PAY_APPLY, --01理赔付款申请【2】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTO_WO_DEST_RCVO_NPOD, --01理赔冲01到达应收未签收【3】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTO_WO_DEST_RCVO_POD, --01理赔冲到01达应收已签收【4】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTO_WO_DEST_RCVT_NPOD, --01理赔冲02到达应收未签收【5】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTO_WO_DEST_RCVT_POD, --01理赔冲02到达应收已签收【6】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTT_INCOME,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTT_INCOME, --02理赔冲收入【7】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTT_WO_DEST_RCVO_NPOD, --02理赔冲01到达应收未签收【8】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTT_WO_DEST_RCVO_POD, --02理赔冲01到达应收已签收【9】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTT_WO_DEST_RCVT_NPOD, --02理赔冲02到达应收未签收【10】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTT_WO_DEST_RCVT_POD, --02理赔冲02到达应收已签收【11】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_ORIGO_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_ORIGO_PAY_APPLY, --01理赔付款申请【12】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_ORIGO_WO_ORIG_RCVT_NPOD, --01理赔冲02始发应收未签收【13】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_ORIGO_WO_ORIG_RCVT_POD, --01理赔冲02始发应收已签收【14】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_ORIGT_ORIG_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_ORIGT_ORIG_RCVO_POD, --02理赔冲01始发应收已签收【15】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_ORIGT_WO_ORIG_RCVO_NPOD, --02理赔冲01始发应收未签收【16】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_DEST_RCVO_NPOD, --应付代收货款冲01应收到付运费未签收【17】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_DEST_RCVO_POD, --应付代收货款冲01应收到付运费已签收【18】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_DEST_RCVT_NPOD, --应付代收货款冲02应收到付运费未签收【19】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_DEST_RCVT_POD, --应付代收货款冲02应收到付运费已签收【20】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_ORIG_RCVO_NPOD, --应付代收货款冲01应收始发运费未签收【21】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_ORIG_RCVO_POD, --应付代收货款冲01应收始发运费已签收【22】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_ORIG_RCVT_NPOD, --应付代收货款冲02应收始发运费未签收【23】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_ORIG_RCVT_POD, --应付代收货款冲02应收始发运费已签收【24】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_OTH_RCVO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_OTH_RCVO, --应付代收货款冲01小票应收【25】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_OTH_RCVT,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_OTH_RCVT, --应付代收货款冲02小票应收【26】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_UR_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_UR_CD_NPOD, --还款代收货款银行未签收【27】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_UR_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_UR_CH_NPOD, --还款代收货款现金未签收【28】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CPO_DEST_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CPO_DEST_PAY_APPLY, --01到达服务补救付款申请【29】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CPO_ORIG_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CPO_ORIG_PAY_APPLY, --01始发服务补救付款申请【30】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_PAY_APPLY, --01始发退预收付款申请【31】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_WO_DEST_RCVO_NPOD, --01预收客户冲01应收到付运费未签收【32】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_WO_DEST_RCVO_POD, --01预收客户冲01应收到付运费已签收【33】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_WO_DEST_RCVT_NPOD, --01预收客户冲02应收到付运费未签收【34】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_WO_DEST_RCVT_POD, --01预收客户冲02应收到付运费已签收【35】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_WO_ORIG_RCVT_NPOD, --01预收客户冲02应收始发运费未签收【36】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_WO_ORIG_RCVT_POD, --01预收客户冲02应收始发运费已签收【37】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRT_WO_DEST_RCVO_NPOD, --02预收客户冲01应收到付运费未签收【38】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRT_WO_DEST_RCVO_POD, --02预收客户冲01应收到付运费已签收【39】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRT_WO_DEST_RCVT_NPOD, --02预收客户冲02应收到付运费未签收【40】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRT_WO_DEST_RCVT_POD, --02预收客户冲02应收到付运费已签收【41】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRT_WO_ORIG_RCVO_NPOD, --02预收客户冲01应收始发运费未签收【42】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRT_WO_ORIG_RCVO_POD, --02预收客户冲01应收始发运费已签收【43】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DR_OCD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DR_OCD, --预收客户银行【44】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DR_OCH,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DR_OCH, --预收客户现金【45】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.DEO_CD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) DEO_CD, --开单银行卡【46】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.DEO_CH,
																		NVL(AMOUNT, 0),
																		0)),
												 0) DEO_CH, --开单现金【47】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CD_PBIO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CD_PBIO, --01小票银行主营业务收入【48】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CD_UR_RCVO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CD_UR_RCVO, --还款银行冲01小票应收【49】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CH_PBIO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CH_PBIO, --01小票现金主营业务收入【50】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CH_UR_RCVO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CH_UR_RCVO, --还款现金冲01小票应收【51】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CLAIM_PAYO_WO_RCVT,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CLAIM_PAYO_WO_RCVT, --01应付理赔冲02小票应收【52】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CLAIM_PAYT_WO_RCVO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CLAIM_PAYT_WO_RCVO, --02应付理赔冲01小票应收【53】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CUST_DRO_WO_RCVT,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CUST_DRO_WO_RCVT, --01预收客户冲02小票应收【54】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CUST_DRT_WO_RCVO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CUST_DRT_WO_RCVO, --02预收客户冲01小票应收【55】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTO_DEST_RCVO_POD, --01退运费冲01到达应收已签收【56】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTO_DEST_RCVT_POD, --01退运费冲02到达应收已签收【57】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTO_INCOME,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTO_INCOME, --01退运费冲收入【58】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTO_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTO_PAY_APPLY, --01退运费付款申请【59】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTO_WO_DEST_RCVO_NPOD, --01退运费冲01到达应收未签收【60】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTO_WO_DEST_RCVT_NPOD, --01退运费冲02到达应收未签收【61】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTT_INCOME,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTT_INCOME, --02退运费冲收入【62】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTT_WO_DEST_RCVO_NPOD, --02退运费冲01到达应收未签收【63】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTT_WO_DEST_RCVO_POD, --02退运费冲01到达应收已签收【64】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTT_WO_DEST_RCVT_NPOD, --02退运费冲02到达应收未签收【65】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTT_WO_DEST_RCVT_POD, --02退运费冲02到达应收已签收【66】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_ORIGO_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_ORIGO_PAY_APPLY, --01退运费付款申请【67】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_ORIGO_WO_ORIG_RCVT_NPOD, --01退运费冲02始发应收未签收【68】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_ORIGO_WO_ORIG_RCVT_POD, --01退运费冲02始发应收已签收【69】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_ORIGT_WO_ORIG_RCVO_NPOD, --02退运费冲01始发应收未签收【70】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_ORIGT_WO_ORIG_RCVO_POD, --02退运费冲01始发应收已签收【71】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.SFO_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) SFO_PAY_APPLY, --01装卸费付款申请【72】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_DEST_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_DEST_CD_NPOD, --还款银行未签收【73】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_DEST_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_DEST_CD_POD, --还款银行已签收【74】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_DEST_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_DEST_CH_NPOD, --还款现金未签收【75】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_DEST_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_DEST_CH_POD, --还款现金已签收【76】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_ORIG_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_ORIG_CD_NPOD, --还款银行未签收【77】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_ORIG_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_ORIG_CD_POD, --还款银行已签收【78】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_ORIG_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_ORIG_CH_NPOD, --还款现金未签收【79】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_ORIG_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_ORIG_CH_POD, --还款现金已签收【80】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URT_DEST_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URT_DEST_CD_NPOD, --还款银行未签收【81】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URT_DEST_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URT_DEST_CD_POD, --还款银行已签收【82】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URT_DEST_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URT_DEST_CH_NPOD, --还款现金未签收【83】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URT_DEST_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URT_DEST_CH_POD --还款现金已签收【84】
								FROM STV.T_STL_NMVSI T
							 WHERE T.PERIOD >= V_BEGIN_PERIOD
										 AND T.PERIOD < V_END_PERIOD
										 AND T.AMOUNT <> 0
										 AND T.BUSINESS_CASE IN
										 (PKG_STL_VCH_COMMON.CLAIM_DESTO_INCOME,
													PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_POD,
													PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_POD,
													PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_NPOD,
													PKG_STL_VCH_COMMON.CLAIM_ORIGT_ORIG_RCVO_POD,
													PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVO_POD,
													PKG_STL_VCH_COMMON.DEO_CH,
													PKG_STL_VCH_COMMON.OR_CD_UR_RCVO,
													PKG_STL_VCH_COMMON.OR_CH_UR_RCVO,
													PKG_STL_VCH_COMMON.OR_CLAIM_PAYT_WO_RCVO,
													PKG_STL_VCH_COMMON.RD_DESTT_INCOME,
													PKG_STL_VCH_COMMON.URO_ORIG_CD_POD,
													PKG_STL_VCH_COMMON.URT_DEST_CD_POD,
													PKG_STL_VCH_COMMON.CLAIM_DESTO_PAY_APPLY,
													PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_NPOD,
													PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVO_NPOD,
													PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_NPOD,
													PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_POD,
													PKG_STL_VCH_COMMON.DEO_CD,
													PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVT_POD,
													PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_NPOD,
													PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_POD,
													PKG_STL_VCH_COMMON.URO_DEST_CD_NPOD,
													PKG_STL_VCH_COMMON.URO_DEST_CD_POD,
													PKG_STL_VCH_COMMON.URT_DEST_CH_NPOD,
													PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_NPOD,
													PKG_STL_VCH_COMMON.COD_PAY_WO_OTH_RCVO,
													PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_POD,
													PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_NPOD,
													PKG_STL_VCH_COMMON.SFO_PAY_APPLY,
													PKG_STL_VCH_COMMON.URO_ORIG_CH_NPOD,
													PKG_STL_VCH_COMMON.URT_DEST_CH_POD,
													PKG_STL_VCH_COMMON.CLAIM_ORIGO_PAY_APPLY,
													PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVO_NPOD,
													PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVT_POD,
													PKG_STL_VCH_COMMON.CUST_DRO_PAY_APPLY,
													PKG_STL_VCH_COMMON.OR_CUST_DRT_WO_RCVO,
													PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVO_POD,
													PKG_STL_VCH_COMMON.RD_DESTO_INCOME,
													PKG_STL_VCH_COMMON.URO_ORIG_CD_NPOD,
													PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_POD,
													PKG_STL_VCH_COMMON.CLAIM_DESTT_INCOME,
													PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVO_NPOD,
													PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVO_POD,
													PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVT_NPOD,
													PKG_STL_VCH_COMMON.COD_PAY_WO_OTH_RCVT,
													PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_POD,
													PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_POD,
													PKG_STL_VCH_COMMON.CUST_DR_OCD,
													PKG_STL_VCH_COMMON.RD_DESTO_PAY_APPLY,
													PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVO_NPOD,
													PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_POD,
													PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_POD,
													PKG_STL_VCH_COMMON.CPO_ORIG_PAY_APPLY,
													PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_NPOD,
													PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_POD,
													PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_POD,
													PKG_STL_VCH_COMMON.OR_CH_PBIO,
													PKG_STL_VCH_COMMON.OR_CUST_DRO_WO_RCVT,
													PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVT_NPOD,
													PKG_STL_VCH_COMMON.RD_ORIGO_PAY_APPLY,
													PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_NPOD,
													PKG_STL_VCH_COMMON.URO_DEST_CH_POD,
													PKG_STL_VCH_COMMON.URO_ORIG_CH_POD,
													PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_NPOD,
													PKG_STL_VCH_COMMON.CPO_DEST_PAY_APPLY,
													PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_NPOD,
													PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_NPOD,
													PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_NPOD,
													PKG_STL_VCH_COMMON.OR_CLAIM_PAYO_WO_RCVT,
													PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_POD,
													PKG_STL_VCH_COMMON.URT_DEST_CD_NPOD,
													PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_NPOD,
													PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVT_NPOD,
													PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVT_POD,
													PKG_STL_VCH_COMMON.COD_UR_CD_NPOD,
													PKG_STL_VCH_COMMON.COD_UR_CH_NPOD,
													PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_POD,
													PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_NPOD,
													PKG_STL_VCH_COMMON.CUST_DR_OCH,
													PKG_STL_VCH_COMMON.OR_CD_PBIO,
													PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_NPOD,
													PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_POD,
													PKG_STL_VCH_COMMON.URO_DEST_CH_NPOD)
							 GROUP BY T.DEBIT_ORG_CODE,
												T.DEBIT_ORG_NAME,
												T.DEBIT_INVOICE_MARK,
												T.DEBIT_ORG_TYPE)
			 GROUP BY ORG_CODE, ORG_NAME, INVOICE_MARK, ORG_TYPE;
	
		COMMIT;
	
		RETURN TRUE;
	
	EXCEPTION
		WHEN OTHERS THEN
			--插入错误日志表
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NRFI',
																				'凭证生成始发专线往来月报表程序异常:' || SQLERRM);
		
			--返回失败标志
			RETURN FALSE;
		
	END FUNC_VOUCHER_NRFI;

	-----------------------------------------------------------------
	-- 始发偏线往来月报表入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NPLI(P_PERIOD     VARCHAR2,
														 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
														 P_END_DATE   DATE -- 结束时间 2012-12-22
														 ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
	 IS
	
		V_BEGIN_PERIOD VARCHAR2(50);
		V_END_PERIOD   VARCHAR2(50);
	
	BEGIN
	
		V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
		V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_NPLI T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
			USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;
	
		--始发
		INSERT INTO STV.T_STL_MVR_NPLI
			(ID,
			 PERIOD,
			 PRODUCT_CODE,
			 CUSTOMER_CODE,
			 CUSTOMER_NAME,
			 CUSTOMER_TYPE,
			 ORG_CODE,
			 ORG_NAME,
			 ORG_TYPE,
			 INVOICE_MARK,
			 VOUCHER_BEGIN_TIME,
			 VOUCHER_END_TIME,
			 PL_COST_WO_DEST_RCVO_NPOD, --【1】
			 PL_COST_WO_DEST_RCVO_POD, --【2】
			 PL_COST_WO_DEST_RCVT_NPOD, --【3】
			 PL_COST_WO_DEST_RCVT_POD, --【4】
			 PL_DR_WO_DEST_RCVO_NPOD, --【5】
			 PL_DR_WO_DEST_RCVO_POD, --【6】
			 PL_DR_WO_DEST_RCVT_NPOD, --【7】
			 PL_DR_WO_DEST_RCVT_POD, --【8】
			 UROP_DEST_CD_NPOD, --【9】
			 UROP_DEST_CD_POD, --【10】
			 UROP_DEST_CH_NPOD, --【11】
			 UROP_DEST_CH_POD, --【12】
			 URTP_DEST_CD_NPOD, --【13】
			 URTP_DEST_CD_POD, --【14】
			 URTP_DEST_CH_NPOD, --【15】
			 URTP_DEST_CH_POD, --【16】
			 POP_WO_DRO_POD, --【17】
			 POP_WO_DRO_NPOD, --【18】
			 POP_WO_DRT_POD, --【19】
			 POP_WO_DRT_NPOD --【20】
			 )
			SELECT SYS_GUID(),
						 P_PERIOD,
						 PRODUCT_CODE,
						 CUSTOMER_CODE,
						 CUSTOMER_NAME,
						 CUSTOMER_TYPE,
						 ORG_CODE,
						 ORG_NAME,
						 ORG_TYPE,
						 INVOICE_MARK,
						 P_BEGIN_DATE,
						 P_END_DATE,
						 SUM(PL_COST_WO_DEST_RCVO_NPOD), --【1】
						 SUM(PL_COST_WO_DEST_RCVO_POD), --【2】
						 SUM(PL_COST_WO_DEST_RCVT_NPOD), --【3】
						 SUM(PL_COST_WO_DEST_RCVT_POD), --【4】
						 SUM(PL_DR_WO_DEST_RCVO_NPOD), --【5】
						 SUM(PL_DR_WO_DEST_RCVO_POD), --【6】
						 SUM(PL_DR_WO_DEST_RCVT_NPOD), --【7】
						 SUM(PL_DR_WO_DEST_RCVT_POD), --【8】
						 SUM(UROP_DEST_CD_NPOD), --【9】
						 SUM(UROP_DEST_CD_POD), --【10】
						 SUM(UROP_DEST_CH_NPOD), --【11】
						 SUM(UROP_DEST_CH_POD), --【12】
						 SUM(URTP_DEST_CD_NPOD), --【13】
						 SUM(URTP_DEST_CD_POD), --【14】
						 SUM(URTP_DEST_CH_NPOD), --【15】
						 SUM(URTP_DEST_CH_POD), --【16】
						 SUM(POP_WO_DRO_POD), --【17】
						 SUM(POP_WO_DRO_NPOD), --【18】
						 SUM(POP_WO_DRT_POD), --【19】
						 SUM(POP_WO_DRT_NPOD) --【20】
				FROM (SELECT T.PRODUCT_CODE,
										 T.CUSTOMER_CODE,
										 T.CUSTOMER_NAME,
										 T.CUSTOMER_TYPE,
										 T.CREDIT_ORG_CODE ORG_CODE,
										 T.CREDIT_ORG_NAME ORG_NAME,
										 T.CREDIT_ORG_TYPE ORG_TYPE,
										 T.CREDIT_INVOICE_MARK INVOICE_MARK,
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_COST_WO_DEST_RCVO_NPOD, --应付偏线代理成本冲01应收到付运费未签收【1】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_COST_WO_DEST_RCVO_POD, --应付偏线代理成本冲01应收到付运费已签收【2】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_COST_WO_DEST_RCVT_NPOD, --应付偏线代理成本冲02应收到付运费未签收【3】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_COST_WO_DEST_RCVT_POD, --应付偏线代理成本冲02应收到付运费已签收【4】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_DR_WO_DEST_RCVO_NPOD, --预收偏线代理冲01应收到付运费未签收【5】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_DR_WO_DEST_RCVO_POD, --预收偏线代理冲01应收到付运费已签收【6】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_DR_WO_DEST_RCVT_NPOD, --预收偏线代理冲02应收到付运费未签收【7】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_DR_WO_DEST_RCVT_POD, --预收偏线代理冲02应收到付运费已签收【8】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROP_DEST_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROP_DEST_CD_NPOD, --还款银行未签收【9】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROP_DEST_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROP_DEST_CD_POD, --还款银行已签收【10】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROP_DEST_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROP_DEST_CH_NPOD, --还款现金未签收【11】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROP_DEST_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROP_DEST_CH_POD, --还款现金已签收【12】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTP_DEST_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTP_DEST_CD_NPOD, --还款银行未签收【13】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTP_DEST_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTP_DEST_CD_POD, --还款银行已签收【14】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTP_DEST_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTP_DEST_CH_NPOD, --还款现金未签收【15】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTP_DEST_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTP_DEST_CH_POD, --还款现金已签收【16】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.POP_WO_DRO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) POP_WO_DRO_POD, --其他应付冲01应收到付运费已签收【17】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.POP_WO_DRO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) POP_WO_DRO_NPOD, --其他应付冲01应收到付运费未签收【18】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.POP_WO_DRT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) POP_WO_DRT_POD, --其他应付冲02应收到付运费已签收【19】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.POP_WO_DRT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) POP_WO_DRT_NPOD --其他应付冲02应收到付运费未签收【20】
								FROM STV.T_STL_NMVSI T
							 WHERE T.PERIOD >= V_BEGIN_PERIOD
										 AND T.PERIOD < V_END_PERIOD
										 AND T.AMOUNT <> 0
										 AND T.BUSINESS_CASE IN
										 (PKG_STL_VCH_COMMON.UROP_DEST_CH_POD,
													PKG_STL_VCH_COMMON.UROP_DEST_CH_NPOD,
													PKG_STL_VCH_COMMON.URTP_DEST_CD_NPOD,
													PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_NPOD,
													PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVO_POD,
													PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVT_POD,
													PKG_STL_VCH_COMMON.URTP_DEST_CD_POD,
													PKG_STL_VCH_COMMON.URTP_DEST_CH_NPOD,
													PKG_STL_VCH_COMMON.UROP_DEST_CD_NPOD,
													PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_POD,
													PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_POD,
													PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVO_NPOD,
													PKG_STL_VCH_COMMON.URTP_DEST_CH_POD,
													PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_NPOD,
													PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVT_NPOD,
													PKG_STL_VCH_COMMON.UROP_DEST_CD_POD,
													PKG_STL_VCH_COMMON.POP_WO_DRO_POD,
													PKG_STL_VCH_COMMON.POP_WO_DRO_NPOD,
													PKG_STL_VCH_COMMON.POP_WO_DRT_POD,
													PKG_STL_VCH_COMMON.POP_WO_DRT_NPOD)
							 GROUP BY T.PRODUCT_CODE,
												T.CUSTOMER_CODE,
												T.CUSTOMER_NAME,
												T.CUSTOMER_TYPE,
												T.CREDIT_ORG_CODE,
												T.CREDIT_ORG_NAME,
												T.CREDIT_INVOICE_MARK,
												T.CREDIT_ORG_TYPE
							UNION ALL
							SELECT T.PRODUCT_CODE,
										 T.CUSTOMER_CODE,
										 T.CUSTOMER_NAME,
										 T.CUSTOMER_TYPE,
										 T.DEBIT_ORG_CODE ORG_CODE,
										 T.DEBIT_ORG_NAME ORG_NAME,
										 T.DEBIT_ORG_TYPE ORG_TYPE,
										 T.DEBIT_INVOICE_MARK INVOICE_MARK,
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_COST_WO_DEST_RCVO_NPOD, --应付偏线代理成本冲01应收到付运费未签收【1】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_COST_WO_DEST_RCVO_POD, --应付偏线代理成本冲01应收到付运费已签收【2】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_COST_WO_DEST_RCVT_NPOD, --应付偏线代理成本冲02应收到付运费未签收【3】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_COST_WO_DEST_RCVT_POD, --应付偏线代理成本冲02应收到付运费已签收【4】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_DR_WO_DEST_RCVO_NPOD, --预收偏线代理冲01应收到付运费未签收【5】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_DR_WO_DEST_RCVO_POD, --预收偏线代理冲01应收到付运费已签收【6】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_DR_WO_DEST_RCVT_NPOD, --预收偏线代理冲02应收到付运费未签收【7】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_DR_WO_DEST_RCVT_POD, --预收偏线代理冲02应收到付运费已签收【8】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROP_DEST_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROP_DEST_CD_NPOD, --还款银行未签收【9】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROP_DEST_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROP_DEST_CD_POD, --还款银行已签收【10】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROP_DEST_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROP_DEST_CH_NPOD, --还款现金未签收【11】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROP_DEST_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROP_DEST_CH_POD, --还款现金已签收【12】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTP_DEST_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTP_DEST_CD_NPOD, --还款银行未签收【13】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTP_DEST_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTP_DEST_CD_POD, --还款银行已签收【14】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTP_DEST_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTP_DEST_CH_NPOD, --还款现金未签收【15】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTP_DEST_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTP_DEST_CH_POD, --还款现金已签收【16】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.POP_WO_DRO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) POP_WO_DRO_POD, --其他应付冲01应收到付运费已签收【17】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.POP_WO_DRO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) POP_WO_DRO_NPOD, --其他应付冲01应收到付运费未签收【18】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.POP_WO_DRT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) POP_WO_DRT_POD, --其他应付冲02应收到付运费已签收【19】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.POP_WO_DRT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) POP_WO_DRT_NPOD --其他应付冲02应收到付运费未签收【20】
								FROM STV.T_STL_NMVSI T
							 WHERE T.PERIOD >= V_BEGIN_PERIOD
										 AND T.PERIOD < V_END_PERIOD
										 AND T.AMOUNT <> 0
										 AND T.BUSINESS_CASE IN
										 (PKG_STL_VCH_COMMON.UROP_DEST_CH_POD,
													PKG_STL_VCH_COMMON.UROP_DEST_CH_NPOD,
													PKG_STL_VCH_COMMON.URTP_DEST_CD_NPOD,
													PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_NPOD,
													PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVO_POD,
													PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVT_POD,
													PKG_STL_VCH_COMMON.URTP_DEST_CD_POD,
													PKG_STL_VCH_COMMON.URTP_DEST_CH_NPOD,
													PKG_STL_VCH_COMMON.UROP_DEST_CD_NPOD,
													PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_POD,
													PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_POD,
													PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVO_NPOD,
													PKG_STL_VCH_COMMON.URTP_DEST_CH_POD,
													PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_NPOD,
													PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVT_NPOD,
													PKG_STL_VCH_COMMON.UROP_DEST_CD_POD,
													PKG_STL_VCH_COMMON.POP_WO_DRO_POD,
													PKG_STL_VCH_COMMON.POP_WO_DRO_NPOD,
													PKG_STL_VCH_COMMON.POP_WO_DRT_POD,
													PKG_STL_VCH_COMMON.POP_WO_DRT_NPOD)
							 GROUP BY T.PRODUCT_CODE,
												T.CUSTOMER_CODE,
												T.CUSTOMER_NAME,
												T.CUSTOMER_TYPE,
												T.DEBIT_ORG_CODE,
												T.DEBIT_ORG_NAME,
												T.DEBIT_INVOICE_MARK,
												T.DEBIT_ORG_TYPE)
			 GROUP BY PRODUCT_CODE,
								CUSTOMER_CODE,
								CUSTOMER_NAME,
								CUSTOMER_TYPE,
								ORG_CODE,
								ORG_NAME,
								INVOICE_MARK,
								ORG_TYPE;
	
		COMMIT;
	
		RETURN TRUE;
	
	EXCEPTION
		WHEN OTHERS THEN
			--插入错误日志表
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NPLI',
																				'凭证生成始发偏线往来月报表程序异常:' || SQLERRM);
		
			--返回失败标志
			RETURN FALSE;
		
	END FUNC_VOUCHER_NPLI;

	-----------------------------------------------------------------
	-- 始发空运往来月报表入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NAFI(P_PERIOD     VARCHAR2,
														 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
														 P_END_DATE   DATE -- 结束时间 2012-12-22
														 ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
	 IS
	
		V_BEGIN_PERIOD VARCHAR2(50);
		V_END_PERIOD   VARCHAR2(50);
	
	BEGIN
	
		V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
		V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_NAFI T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
			USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;
	
		INSERT INTO STV.T_STL_MVR_NAFI
			(ID,
			 PERIOD,
			 PRODUCT_CODE,
			 CUSTOMER_CODE,
			 CUSTOMER_NAME,
			 CUSTOMER_TYPE,
			 ORG_CODE,
			 ORG_NAME,
			 ORG_TYPE,
			 INVOICE_MARK,
			 VOUCHER_BEGIN_TIME,
			 VOUCHER_END_TIME,
			 AIR_COD_CD_UR_POD, --【1】
			 AIR_COD_CH_UR_POD, --【2】
			 AIR_COD_DPAY_WO_COD_RCV_POD, --【3】
			 AIR_COD_OTH_PAY_WO_COD_RCV_POD, --【4】
			 AIR_COD_POD_NWO, --【5】
			 AIR_COD_POD_WO, --【6】
			 AIR_COD_UPD_NWO, --【7】
			 AIR_COD_UPD_WO, --【8】
			 AIR_DR_WO_COD_RCV_POD, --【9】
			 AIR_DR_WO_DEST_RCVO_NPOD, --【10】
			 AIR_DR_WO_DEST_RCVO_POD, --【11】
			 AIR_DR_WO_DEST_RCVT_NPOD, --【12】
			 AIR_DR_WO_DEST_RCVT_POD, --【13】
			 APWR_COST_WO_DEST_RCVO_NPOD, --【14】
			 APWR_COST_WO_DEST_RCVO_POD, --【15】
			 APWR_COST_WO_DEST_RCVT_NPOD, --【16】
			 APWR_COST_WO_DEST_RCVT_POD, --【17】
			 APWR_OTH_PAY_WO_DEST_RCVO_NPOD, --【18】
			 APWR_OTH_PAY_WO_DEST_RCVO_POD, --【19】
			 APWR_OTH_PAY_WO_DEST_RCVT_NPOD, --【20】
			 APWR_OTH_PAY_WO_DEST_RCVT_POD, --【21】
			 UROA_DEST_CD_NPOD, --【22】
			 UROA_DEST_CD_POD, --【23】
			 UROA_DEST_CH_NPOD, --【24】
			 UROA_DEST_CH_POD, --【25】
			 URTA_DEST_CD_NPOD, --【26】
			 URTA_DEST_CD_POD, --【27】
			 URTA_DEST_CH_NPOD, --【28】
			 URTA_DEST_CH_POD --【29】
			 )
			SELECT SYS_GUID(),
						 P_PERIOD,
						 PRODUCT_CODE,
						 CUSTOMER_CODE,
						 CUSTOMER_NAME,
						 CUSTOMER_TYPE,
						 ORG_CODE,
						 ORG_NAME,
						 ORG_TYPE,
						 INVOICE_MARK,
						 P_BEGIN_DATE,
						 P_END_DATE,
						 SUM(AIR_COD_CD_UR_POD), --【1】
						 SUM(AIR_COD_CH_UR_POD), --【2】
						 SUM(AIR_COD_DPAY_WO_COD_RCV_POD), --【3】
						 SUM(AIR_COD_OTH_PAY_WO_COD_RCV_POD), --【4】
						 SUM(AIR_COD_POD_NWO), --【5】
						 SUM(AIR_COD_POD_WO), --【6】
						 SUM(AIR_COD_UPD_NWO), --【7】
						 SUM(AIR_COD_UPD_WO), --【8】
						 SUM(AIR_DR_WO_COD_RCV_POD), --【9】
						 SUM(AIR_DR_WO_DEST_RCVO_NPOD), --【10】
						 SUM(AIR_DR_WO_DEST_RCVO_POD), --【11】
						 SUM(AIR_DR_WO_DEST_RCVT_NPOD), --【12】
						 SUM(AIR_DR_WO_DEST_RCVT_POD), --【13】
						 SUM(APWR_COST_WO_DEST_RCVO_NPOD), --【14】
						 SUM(APWR_COST_WO_DEST_RCVO_POD), --【15】
						 SUM(APWR_COST_WO_DEST_RCVT_NPOD), --【16】
						 SUM(APWR_COST_WO_DEST_RCVT_POD), --【17】
						 SUM(APWR_OTH_PAY_WO_DEST_RCVO_NPOD), --【18】
						 SUM(APWR_OTH_PAY_WO_DEST_RCVO_POD), --【19】
						 SUM(APWR_OTH_PAY_WO_DEST_RCVT_NPOD), --【20】
						 SUM(APWR_OTH_PAY_WO_DEST_RCVT_POD), --【21】
						 SUM(UROA_DEST_CD_NPOD), --【22】
						 SUM(UROA_DEST_CD_POD), --【23】
						 SUM(UROA_DEST_CH_NPOD), --【24】
						 SUM(UROA_DEST_CH_POD), --【25】
						 SUM(URTA_DEST_CD_NPOD), --【26】
						 SUM(URTA_DEST_CD_POD), --【27】
						 SUM(URTA_DEST_CH_NPOD), --【28】
						 SUM(URTA_DEST_CH_POD) --【29】
				FROM (SELECT T.PRODUCT_CODE,
										 T.CUSTOMER_CODE,
										 T.CUSTOMER_NAME,
										 T.CUSTOMER_TYPE,
										 T.CREDIT_ORG_CODE ORG_CODE,
										 T.CREDIT_ORG_NAME ORG_NAME,
										 T.CREDIT_ORG_TYPE ORG_TYPE,
										 T.CREDIT_INVOICE_MARK INVOICE_MARK,
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_CD_UR_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_CD_UR_POD, --空运还款代收货款银行已签收【1】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_CH_UR_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_CH_UR_POD, --空运还款代收货款现金已签收【2】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_DPAY_WO_COD_RCV_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_DPAY_WO_COD_RCV_POD, --空运到达代理应付冲应收代收货款已签收【3】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_OTH_PAY_WO_COD_RCV_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_OTH_PAY_WO_COD_RCV_POD, --空运其他应付冲应收代收货款已签收【4】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_POD_NWO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_POD_NWO, --空运签收时未核销代收货款【5】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_POD_WO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_POD_WO, --空运签收时已核销代收货款【6】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_UPD_NWO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_UPD_NWO, --空运反签收时未核销代收货款【7】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_UPD_WO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_UPD_WO, --空运反签收时已核销代收货款【8】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_DR_WO_COD_RCV_POD, --预收空运代理冲应收代收货款已签收【9】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_DR_WO_DEST_RCVO_NPOD, --预收空运代理冲01应收到付运费未签收【10】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_DR_WO_DEST_RCVO_POD, --预收空运代理冲01应收到付运费已签收【11】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_DR_WO_DEST_RCVT_NPOD, --预收空运代理冲02应收到付运费未签收【12】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_DR_WO_DEST_RCVT_POD, --预收空运代理冲02应收到付运费已签收【13】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_COST_WO_DEST_RCVO_NPOD, --应付到达代理成本冲01应收到付运费未签收【14】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_COST_WO_DEST_RCVO_POD, --应付到达代理成本冲01应收到付运费已签收【15】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_COST_WO_DEST_RCVT_NPOD, --应付到达代理成本冲02应收到付运费未签收【16】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_COST_WO_DEST_RCVT_POD, --应付到达代理成本冲02应收到付运费已签收【17】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_OTH_PAY_WO_DEST_RCVO_NPOD, --其他应付冲01应收到付运费未签收【18】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_OTH_PAY_WO_DEST_RCVO_POD, --其他应付冲01应收到付运费已签收【19】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_OTH_PAY_WO_DEST_RCVT_NPOD, --其他应付冲02应收到付运费未签收【20】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_OTH_PAY_WO_DEST_RCVT_POD, --其他应付冲02应收到付运费已签收【21】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROA_DEST_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROA_DEST_CD_NPOD, --还款银行未签收【22】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROA_DEST_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROA_DEST_CD_POD, --还款银行已签收【23】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROA_DEST_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROA_DEST_CH_NPOD, --还款现金未签收【24】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROA_DEST_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROA_DEST_CH_POD, --还款现金已签收【25】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTA_DEST_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTA_DEST_CD_NPOD, --还款银行未签收【26】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTA_DEST_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTA_DEST_CD_POD, --还款银行已签收【27】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTA_DEST_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTA_DEST_CH_NPOD, --还款现金未签收【28】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTA_DEST_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTA_DEST_CH_POD --还款现金已签收【29】
								FROM STV.T_STL_NMVSI T
							 WHERE T.PERIOD >= V_BEGIN_PERIOD
										 AND T.PERIOD < V_END_PERIOD
										 AND T.AMOUNT <> 0
										 AND T.BUSINESS_CASE IN
										 (PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVT_POD,
													PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_POD,
													PKG_STL_VCH_COMMON.AIR_COD_CD_UR_POD,
													PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_NPOD,
													PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_POD,
													PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_POD,
													PKG_STL_VCH_COMMON.UROA_DEST_CH_NPOD,
													PKG_STL_VCH_COMMON.URTA_DEST_CH_POD,
													PKG_STL_VCH_COMMON.AIR_COD_OTH_PAY_WO_COD_RCV_POD,
													PKG_STL_VCH_COMMON.AIR_COD_POD_NWO,
													PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVO_NPOD,
													PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_NPOD,
													PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_POD,
													PKG_STL_VCH_COMMON.URTA_DEST_CH_NPOD,
													PKG_STL_VCH_COMMON.UROA_DEST_CD_NPOD,
													PKG_STL_VCH_COMMON.UROA_DEST_CD_POD,
													PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVT_NPOD,
													PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_NPOD,
													PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVO_POD,
													PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_NPOD,
													PKG_STL_VCH_COMMON.AIR_COD_CH_UR_POD,
													PKG_STL_VCH_COMMON.AIR_COD_POD_WO,
													PKG_STL_VCH_COMMON.AIR_COD_UPD_NWO,
													PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_POD,
													PKG_STL_VCH_COMMON.UROA_DEST_CH_POD,
													PKG_STL_VCH_COMMON.URTA_DEST_CD_POD,
													PKG_STL_VCH_COMMON.AIR_COD_DPAY_WO_COD_RCV_POD,
													PKG_STL_VCH_COMMON.AIR_COD_UPD_WO,
													PKG_STL_VCH_COMMON.URTA_DEST_CD_NPOD)
							 GROUP BY T.PRODUCT_CODE,
												T.CUSTOMER_CODE,
												T.CUSTOMER_NAME,
												T.CUSTOMER_TYPE,
												T.CREDIT_ORG_CODE,
												T.CREDIT_ORG_NAME,
												T.CREDIT_INVOICE_MARK,
												T.CREDIT_ORG_TYPE
							UNION ALL
							SELECT T.PRODUCT_CODE,
										 T.CUSTOMER_CODE,
										 T.CUSTOMER_NAME,
										 T.CUSTOMER_TYPE,
										 T.DEBIT_ORG_CODE ORG_CODE,
										 T.DEBIT_ORG_NAME ORG_NAME,
										 T.DEBIT_ORG_TYPE ORG_TYPE,
										 T.DEBIT_INVOICE_MARK INVOICE_MARK,
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_CD_UR_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_CD_UR_POD, --空运还款代收货款银行已签收【1】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_CH_UR_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_CH_UR_POD, --空运还款代收货款现金已签收【2】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_DPAY_WO_COD_RCV_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_DPAY_WO_COD_RCV_POD, --空运到达代理应付冲应收代收货款已签收【3】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_OTH_PAY_WO_COD_RCV_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_OTH_PAY_WO_COD_RCV_POD, --空运其他应付冲应收代收货款已签收【4】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_POD_NWO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_POD_NWO, --空运签收时未核销代收货款【5】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_POD_WO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_POD_WO, --空运签收时已核销代收货款【6】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_UPD_NWO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_UPD_NWO, --空运反签收时未核销代收货款【7】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_UPD_WO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_UPD_WO, --空运反签收时已核销代收货款【8】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_DR_WO_COD_RCV_POD, --预收空运代理冲应收代收货款已签收【9】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_DR_WO_DEST_RCVO_NPOD, --预收空运代理冲01应收到付运费未签收【10】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_DR_WO_DEST_RCVO_POD, --预收空运代理冲01应收到付运费已签收【11】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_DR_WO_DEST_RCVT_NPOD, --预收空运代理冲02应收到付运费未签收【12】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_DR_WO_DEST_RCVT_POD, --预收空运代理冲02应收到付运费已签收【13】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_COST_WO_DEST_RCVO_NPOD, --应付到达代理成本冲01应收到付运费未签收【14】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_COST_WO_DEST_RCVO_POD, --应付到达代理成本冲01应收到付运费已签收【15】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_COST_WO_DEST_RCVT_NPOD, --应付到达代理成本冲02应收到付运费未签收【16】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_COST_WO_DEST_RCVT_POD, --应付到达代理成本冲02应收到付运费已签收【17】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_OTH_PAY_WO_DEST_RCVO_NPOD, --其他应付冲01应收到付运费未签收【18】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_OTH_PAY_WO_DEST_RCVO_POD, --其他应付冲01应收到付运费已签收【19】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_OTH_PAY_WO_DEST_RCVT_NPOD, --其他应付冲02应收到付运费未签收【20】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_OTH_PAY_WO_DEST_RCVT_POD, --其他应付冲02应收到付运费已签收【21】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROA_DEST_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROA_DEST_CD_NPOD, --还款银行未签收【22】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROA_DEST_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROA_DEST_CD_POD, --还款银行已签收【23】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROA_DEST_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROA_DEST_CH_NPOD, --还款现金未签收【24】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROA_DEST_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROA_DEST_CH_POD, --还款现金已签收【25】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTA_DEST_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTA_DEST_CD_NPOD, --还款银行未签收【26】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTA_DEST_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTA_DEST_CD_POD, --还款银行已签收【27】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTA_DEST_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTA_DEST_CH_NPOD, --还款现金未签收【28】
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTA_DEST_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTA_DEST_CH_POD --还款现金已签收【29】
								FROM STV.T_STL_NMVSI T
							 WHERE T.PERIOD >= V_BEGIN_PERIOD
										 AND T.PERIOD < V_END_PERIOD
										 AND T.AMOUNT <> 0
										 AND T.BUSINESS_CASE IN
										 (PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVT_POD,
													PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_POD,
													PKG_STL_VCH_COMMON.AIR_COD_CD_UR_POD,
													PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_NPOD,
													PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_POD,
													PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_POD,
													PKG_STL_VCH_COMMON.UROA_DEST_CH_NPOD,
													PKG_STL_VCH_COMMON.URTA_DEST_CH_POD,
													PKG_STL_VCH_COMMON.AIR_COD_OTH_PAY_WO_COD_RCV_POD,
													PKG_STL_VCH_COMMON.AIR_COD_POD_NWO,
													PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVO_NPOD,
													PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_NPOD,
													PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_POD,
													PKG_STL_VCH_COMMON.URTA_DEST_CH_NPOD,
													PKG_STL_VCH_COMMON.UROA_DEST_CD_NPOD,
													PKG_STL_VCH_COMMON.UROA_DEST_CD_POD,
													PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVT_NPOD,
													PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_NPOD,
													PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVO_POD,
													PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_NPOD,
													PKG_STL_VCH_COMMON.AIR_COD_CH_UR_POD,
													PKG_STL_VCH_COMMON.AIR_COD_POD_WO,
													PKG_STL_VCH_COMMON.AIR_COD_UPD_NWO,
													PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_POD,
													PKG_STL_VCH_COMMON.UROA_DEST_CH_POD,
													PKG_STL_VCH_COMMON.URTA_DEST_CD_POD,
													PKG_STL_VCH_COMMON.AIR_COD_DPAY_WO_COD_RCV_POD,
													PKG_STL_VCH_COMMON.AIR_COD_UPD_WO,
													PKG_STL_VCH_COMMON.URTA_DEST_CD_NPOD)
							 GROUP BY T.PRODUCT_CODE,
												T.CUSTOMER_CODE,
												T.CUSTOMER_NAME,
												T.CUSTOMER_TYPE,
												T.DEBIT_ORG_CODE,
												T.DEBIT_ORG_NAME,
												T.DEBIT_INVOICE_MARK,
												T.DEBIT_ORG_TYPE)
			 GROUP BY PRODUCT_CODE,
								CUSTOMER_CODE,
								CUSTOMER_NAME,
								CUSTOMER_TYPE,
								ORG_CODE,
								ORG_NAME,
								INVOICE_MARK,
								ORG_TYPE;
	
		COMMIT;
	
		RETURN TRUE;
	
	EXCEPTION
		WHEN OTHERS THEN
			--插入错误日志表
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NAFI',
																				'凭证生成始发空运往来月报表程序异常:' || SQLERRM);
		
			--返回失败标志
			RETURN FALSE;
		
	END FUNC_VOUCHER_NAFI;

	-----------------------------------------------------------------
	-- 退代收货款报表入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_RETURN_COD(P_PERIOD     VARCHAR2,
																	 P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
																	 P_END_DATE   DATE -- 结束时间 2012-12-22
																	 ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
	 IS
	
		V_RESULT BOOLEAN;
	
	BEGIN
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_DVR_RETURN_COD T WHERE T.PAYMENT_DATE >= :P_BEGIN_DATE AND T.PAYMENT_DATE <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
			USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;
	
		V_RESULT := PKG_STL_VCH_DAILY_RETURN_COD.FUNC_PROCESS_MONTHLY_SUMMARY(P_PERIOD,
																																					P_BEGIN_DATE,
																																					P_END_DATE);
	
		COMMIT;
	
		RETURN V_RESULT;
	
	EXCEPTION
		WHEN OTHERS THEN
			--插入错误日志表
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				P_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_RETURN_COD',
																				'凭证生成代收货款报表程序异常:' || SQLERRM);
		
			--返回失败标志
			RETURN FALSE;
		
	END FUNC_VOUCHER_RETURN_COD;

	-----------------------------------------------------------------
	-- 落地配月报表入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_LDD(P_PERIOD     VARCHAR2,
														P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
														P_END_DATE   DATE -- 结束时间 2012-12-22
														) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
	 IS
	
		V_BEGIN_PERIOD VARCHAR2(50);
		V_END_PERIOD   VARCHAR2(50);
	
	BEGIN
	
		V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
		V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_LDD T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
			USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;
	
		INSERT INTO STV.T_STL_MVR_LDD
			(ID,
			 PERIOD,
			 PRODUCT_CODE,
			 CUSTOMER_CODE,
			 CUSTOMER_NAME,
			 CUSTOMER_TYPE,
			 ORIG_ORG_CODE,
			 ORIG_ORG_NAME,
			 DEST_ORG_CODE,
			 DEST_ORG_NAME,
			 VOUCHER_BEGIN_TIME,
			 VOUCHER_END_TIME,
			 LAND_COST_AGENCY_GEN,
			 LAND_COST_AGENCY_CFM,
			 LAND_COST_AGENCY_NCFM,
			 LAND_COST_OTHER_CONFIRM,
			 LAND_COST_PAY_APPLY,
			 LAND_OTH_ENTRY,
			 LAND_OTH_RCV_CH,
			 LAND_OTH_RCV_CD,
			 LAND_COD_CH_POD,
			 LAND_COD_CD_POD,
			 LAND_COD_POD_WO_COD,
			 LAND_COD_NPOD_WO_COD,
			 LAND_COD_CH_NPOD,
			 LAND_COD_CD_NPOD,
			 LAND_COD_WO_AGENCY_PAY_POD,
			 LAND_COD_WO_OTH_PAY_COD,
			 LAND_COD_WO_AGENCY_PAY_NPOD,
			 LAND_COD_WO_OTH_NPOD,
			 LAND_BDR_WO_OTH_RCV,
			 LAND_UR_DEST_CH_NPOD,
			 LAND_UR_DEST_CD_NPOD,
			 LAND_UR_DEST_CH_POD,
			 LAND_UR_DEST_CD_POD,
			 LAND_PR_AGENCY_WO_DEST_RCV_POD,
			 LAND_PR_AGENCY_WO_DEST_RCV_NP,
			 LAND_PR_OT_WO_DEST_RCV_POD,
			 LAND_PR_OTH_WO_DEST_RCV_NPOD,
			 LAND_PR_OTH_WO_OTH_RCV,
			 LAND_DR_DEST_RCV_POD,
			 LAND_DR_DEST_RCV_NPOD,
			 LAND_DR_CH,
			 LAND_DR_CD,
			 LAND_DR_WO_OTHER_RCV,
			 LAND_DR_WO_COD_RCV_POD,
			 LAND_DR_WO_COD_RCV_NPOD,
			 LAND_DR_PAY_APPLY,
			 LAND_APT_COM,
			 LAND_APT_WO_COM_PAY,
			 LAND_APT_WO_OTH_PAY)
			SELECT SYS_GUID(),
						 P_PERIOD,
						 T.PRODUCT_CODE,
						 T.CUSTOMER_CODE,
						 T.CUSTOMER_NAME,
						 T.CUSTOMER_TYPE,
						 T.ORIG_ORG_CODE,
						 T.ORIG_ORG_NAME,
						 T.DEST_ORG_CODE,
						 T.DEST_ORG_NAME,
						 P_BEGIN_DATE,
						 P_END_DATE,
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COST_AGENCY_GEN,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COST_AGENCY_GEN, --落地配代理应付生成
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COST_AGENCY_CFM,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COST_AGENCY_CFM, --落地配代理应付成本确认
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COST_AGENCY_NCFM,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COST_AGENCY_NCFM, --落地配代理应付成本反确认
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COST_OTHER_CONFIRM,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COST_OTHER_CONFIRM, --其它应付成本确认
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COST_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COST_PAY_APPLY, --落地配成本付款申请
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_OTH_ENTRY,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_OTH_ENTRY, --落地配其他应收录入
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_OTH_RCV_CH,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_OTH_RCV_CH, --还款落地配其他应收现金
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_OTH_RCV_CD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_OTH_RCV_CD, --还款落地配其他应收银行
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_CH_POD, --落地配还款代收货款现金已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_CD_POD, --落地配还款代收货款银行已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_POD_WO_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_POD_WO_COD, --落地配签收时已核销代收货款
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_NPOD_WO_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_NPOD_WO_COD, --落地配反签收时已核销代收货款
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_CH_NPOD, --落地配还款代收货款现金未签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_CD_NPOD, --落地配还款代收货款银行未签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_WO_AGENCY_PAY_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_WO_AGENCY_PAY_POD, --落地配应付冲应收代收货款已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_WO_OTH_PAY_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_WO_OTH_PAY_COD, --落地配其他应付冲应收代收货款已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_WO_AGENCY_PAY_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_WO_AGENCY_PAY_NPOD, --落地配到达代理应付冲应收代收货款未签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_WO_OTH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_WO_OTH_NPOD, --落地配其他应付冲应收代收货款未签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_BDR_WO_OTH_RCV,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_BDR_WO_OTH_RCV, --坏账冲其他应收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_UR_DEST_CH_NPOD, --还款现金未签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_UR_DEST_CD_NPOD, --还款银行未签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_UR_DEST_CH_POD, --还款现金已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_UR_DEST_CD_POD, --还款银行已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_AGENCY_WO_DEST_RCV_POD, --应付代理成本冲应收到付运费已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_NP,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_AGENCY_WO_DEST_RCV_NP, --应付到达代理成本冲应收到付运费未签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_OT_WO_DEST_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_OT_WO_DEST_RCV_POD, --其他应付冲应收到付运费已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_DEST_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_OTH_WO_DEST_RCV_NPOD, --其他应付冲应收到付运费未签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_OTH_RCV,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_OTH_WO_OTH_RCV, --其他应付冲其他应收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_DEST_RCV_POD, --预收落地配代理冲应收到付运费已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_DEST_RCV_NPOD, --预收落地配代理冲应收到付运费未签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_CH,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_CH, --预收落地配代理现金
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_CD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_CD, --预收落地配代理银行
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_WO_OTHER_RCV,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_WO_OTHER_RCV, --预收落地配代理冲其他应收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_WO_COD_RCV_POD, --预收落地配代理冲应收代收货款已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_WO_COD_RCV_NPOD, --预收落地配代理冲应收代收货款未签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_PAY_APPLY, --落地配退预收付款申请
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_APT_COM,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_APT_COM, --预付落地配公司
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_APT_WO_COM_PAY,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_APT_WO_COM_PAY, --预付冲应付落地配公司
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_APT_WO_OTH_PAY,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_APT_WO_OTH_PAY --预付冲其他应付
				FROM STV.T_STL_MVS T
			 WHERE T.PERIOD >= V_BEGIN_PERIOD
						 AND T.PERIOD < V_END_PERIOD
						 AND T.AMOUNT <> 0
						 AND T.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG --落地配
						 AND T.BUSINESS_CASE IN
						 (PKG_STL_VCH_COMMON.LAND_COST_AGENCY_GEN,
									PKG_STL_VCH_COMMON.LAND_COST_AGENCY_CFM,
									PKG_STL_VCH_COMMON.LAND_COST_AGENCY_NCFM,
									PKG_STL_VCH_COMMON.LAND_COST_OTHER_CONFIRM,
									PKG_STL_VCH_COMMON.LAND_COST_PAY_APPLY,
									PKG_STL_VCH_COMMON.LAND_OTH_ENTRY,
									PKG_STL_VCH_COMMON.LAND_OTH_RCV_CH,
									PKG_STL_VCH_COMMON.LAND_OTH_RCV_CD,
									PKG_STL_VCH_COMMON.LAND_COD_CH_POD,
									PKG_STL_VCH_COMMON.LAND_COD_CD_POD,
									PKG_STL_VCH_COMMON.LAND_COD_POD_WO_COD,
									PKG_STL_VCH_COMMON.LAND_COD_NPOD_WO_COD,
									PKG_STL_VCH_COMMON.LAND_COD_CH_NPOD,
									PKG_STL_VCH_COMMON.LAND_COD_CD_NPOD,
									PKG_STL_VCH_COMMON.LAND_COD_WO_AGENCY_PAY_POD,
									PKG_STL_VCH_COMMON.LAND_COD_WO_OTH_PAY_COD,
									PKG_STL_VCH_COMMON.LAND_COD_WO_AGENCY_PAY_NPOD,
									PKG_STL_VCH_COMMON.LAND_COD_WO_OTH_NPOD,
									PKG_STL_VCH_COMMON.LAND_BDR_WO_OTH_RCV,
									PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_NPOD,
									PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_NPOD,
									PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_POD,
									PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_POD,
									PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_POD,
									PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_NP,
									PKG_STL_VCH_COMMON.LAND_PR_OT_WO_DEST_RCV_POD,
									PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_DEST_RCV_NPOD,
									PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_OTH_RCV,
									PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_POD,
									PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_NPOD,
									PKG_STL_VCH_COMMON.LAND_DR_CH,
									PKG_STL_VCH_COMMON.LAND_DR_CD,
									PKG_STL_VCH_COMMON.LAND_DR_WO_OTHER_RCV,
									PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_POD,
									PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_NPOD,
									PKG_STL_VCH_COMMON.LAND_DR_PAY_APPLY,
									PKG_STL_VCH_COMMON.LAND_APT_COM,
									PKG_STL_VCH_COMMON.LAND_APT_WO_COM_PAY,
									PKG_STL_VCH_COMMON.LAND_APT_WO_OTH_PAY)
			 GROUP BY T.PRODUCT_CODE,
								T.CUSTOMER_CODE,
								T.CUSTOMER_NAME,
								T.CUSTOMER_TYPE,
								T.ORIG_ORG_CODE,
								T.ORIG_ORG_NAME,
								T.DEST_ORG_CODE,
								T.DEST_ORG_NAME;
		COMMIT;
	
		RETURN TRUE;
	
	EXCEPTION
		WHEN OTHERS THEN
			--插入错误日志表
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_AFR',
																				'凭证生成落地配月报表程序异常:' || SQLERRM);
		
			--返回失败标志
			RETURN FALSE;
		
	END FUNC_VOUCHER_LDD;

	-----------------------------------------------------------------
	-- 落地配往来月报表入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_LDI(P_PERIOD     VARCHAR2,
														P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
														P_END_DATE   DATE -- 结束时间 2012-12-22
														) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
	 IS
	
		V_BEGIN_PERIOD VARCHAR2(50);
		V_END_PERIOD   VARCHAR2(50);
	
	BEGIN
	
		V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
		V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_LDI T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
			USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;
	
		INSERT INTO STV.T_STL_MVR_LDI
			(ID,
			 PERIOD,
			 PRODUCT_CODE,
			 CUSTOMER_CODE,
			 CUSTOMER_NAME,
			 CUSTOMER_TYPE,
			 ORG_CODE,
			 ORG_NAME,
			 ORG_TYPE,
			 VOUCHER_BEGIN_TIME,
			 VOUCHER_END_TIME,
			 LAND_COD_CH_POD,
			 LAND_COD_CD_POD,
			 LAND_COD_POD_NWO_COD,
			 LAND_COD_NPOD_NWO_COD,
			 LAND_COD_POD_WO_COD,
			 LAND_COD_NPOD_WO_COD,
			 LAND_COD_WO_AGENCY_PAY_POD,
			 LAND_COD_WO_OTH_PAY_COD,
			 LAND_UR_DEST_CH_NPOD,
			 LAND_UR_DEST_CD_NPOD,
			 LAND_UR_DEST_CH_POD,
			 LAND_UR_DEST_CD_POD,
			 LAND_PR_AGENCY_WO_DEST_RCV_POD,
			 LAND_PR_AGENCY_WO_DEST_RCV_NP,
			 LAND_PR_OT_WO_DEST_RCV_POD,
			 LAND_PR_OTH_WO_DEST_RCV_NPOD,
			 LAND_DR_DEST_RCV_POD,
			 LAND_DR_DEST_RCV_NPOD,
			 LAND_DR_WO_COD_RCV_POD)
			SELECT SYS_GUID(),
						 P_PERIOD,
						 T.PRODUCT_CODE,
						 T.CUSTOMER_CODE,
						 T.CUSTOMER_NAME,
						 T.CUSTOMER_TYPE,
						 T.ORIG_ORG_CODE,
						 T.ORIG_ORG_NAME,
						 PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG,
						 P_BEGIN_DATE,
						 P_END_DATE,
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_CH_POD, --落地配还款代收货款现金已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_CD_POD, --落地配还款代收货款银行已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_POD_NWO_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_POD_NWO_COD, --落地配签收时未核销代收货款
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_NPOD_NWO_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_NPOD_NWO_COD, --落地配反签收时未核销代收货款
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_POD_WO_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_POD_WO_COD, --落地配签收时已核销代收货款
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_NPOD_WO_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_NPOD_WO_COD, --落地配反签收时已核销代收货款
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_WO_AGENCY_PAY_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_WO_AGENCY_PAY_POD, --落地配到达代理应付冲应收代收货款已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_WO_OTH_PAY_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_WO_OTH_PAY_COD, --落地配其他应付冲应收代收货款已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_UR_DEST_CH_NPOD, --还款现金未签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_UR_DEST_CD_NPOD, --还款银行未签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_UR_DEST_CH_POD, --还款现金已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_UR_DEST_CD_POD, --还款银行已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_AGENCY_WO_DEST_RCV_POD, --应付到达代理成本冲应收到付运费已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_NP,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_AGENCY_WO_DEST_RCV_NP, --应付到达代理成本冲应收到付运费未签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_OT_WO_DEST_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_OT_WO_DEST_RCV_POD, --其他应付冲应收到付运费已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_DEST_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_OTH_WO_DEST_RCV_NPOD, --其他应付冲应收到付运费未签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_DEST_RCV_POD, --预收落地配代理冲应收到付运费已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_DEST_RCV_NPOD, --预收落地配代理冲应收到付运费未签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_WO_COD_RCV_POD --预收落地配代理冲应收代收货款已签收
				FROM STV.T_STL_MVS T
			 WHERE T.PERIOD >= V_BEGIN_PERIOD
						 AND T.PERIOD < V_END_PERIOD
						 AND T.AMOUNT <> 0
						 AND T.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
						 AND T.BUSINESS_CASE IN
						 (PKG_STL_VCH_COMMON.LAND_COD_CH_POD,
									PKG_STL_VCH_COMMON.LAND_COD_POD_NWO_COD,
									PKG_STL_VCH_COMMON.LAND_COD_CD_POD,
									PKG_STL_VCH_COMMON.LAND_COD_NPOD_NWO_COD,
									PKG_STL_VCH_COMMON.LAND_COD_POD_WO_COD,
									PKG_STL_VCH_COMMON.LAND_COD_NPOD_WO_COD,
									PKG_STL_VCH_COMMON.LAND_COD_WO_AGENCY_PAY_POD,
									PKG_STL_VCH_COMMON.LAND_COD_WO_OTH_PAY_COD,
									PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_NPOD,
									PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_NPOD,
									PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_POD,
									PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_POD,
									PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_POD,
									PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_NP,
									PKG_STL_VCH_COMMON.LAND_PR_OT_WO_DEST_RCV_POD,
									PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_DEST_RCV_NPOD,
									PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_POD,
									PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_NPOD,
									PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_POD)
			 GROUP BY T.PRODUCT_CODE,
								T.CUSTOMER_CODE,
								T.CUSTOMER_NAME,
								T.CUSTOMER_TYPE,
								T.ORIG_ORG_CODE,
								T.ORIG_ORG_NAME;
	
		INSERT INTO STV.T_STL_MVR_LDI
			(ID,
			 PERIOD,
			 PRODUCT_CODE,
			 CUSTOMER_CODE,
			 CUSTOMER_NAME,
			 CUSTOMER_TYPE,
			 ORG_CODE,
			 ORG_NAME,
			 ORG_TYPE,
			 VOUCHER_BEGIN_TIME,
			 VOUCHER_END_TIME,
			 LAND_COD_CH_POD,
			 LAND_COD_CD_POD,
			 LAND_COD_POD_NWO_COD,
			 LAND_COD_NPOD_NWO_COD,
			 LAND_COD_POD_WO_COD,
			 LAND_COD_NPOD_WO_COD,
			 LAND_COD_WO_AGENCY_PAY_POD,
			 LAND_COD_WO_OTH_PAY_COD,
			 LAND_UR_DEST_CH_NPOD,
			 LAND_UR_DEST_CD_NPOD,
			 LAND_UR_DEST_CH_POD,
			 LAND_UR_DEST_CD_POD,
			 LAND_PR_AGENCY_WO_DEST_RCV_POD,
			 LAND_PR_AGENCY_WO_DEST_RCV_NP,
			 LAND_PR_OT_WO_DEST_RCV_POD,
			 LAND_PR_OTH_WO_DEST_RCV_NPOD,
			 LAND_DR_DEST_RCV_POD,
			 LAND_DR_DEST_RCV_NPOD,
			 LAND_DR_WO_COD_RCV_POD)
			SELECT SYS_GUID(),
						 P_PERIOD,
						 T.PRODUCT_CODE,
						 T.CUSTOMER_CODE,
						 T.CUSTOMER_NAME,
						 T.CUSTOMER_TYPE,
						 T.DEST_ORG_CODE,
						 T.DEST_ORG_NAME,
						 PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_PKG,
						 P_BEGIN_DATE,
						 P_END_DATE,
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_CH_POD, --落地配还款代收货款现金已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_CD_POD, --落地配还款代收货款银行已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_POD_NWO_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_POD_NWO_COD, --落地配签收时未核销代收货款
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_NPOD_NWO_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_NPOD_NWO_COD, --落地配反签收时未核销代收货款
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_POD_WO_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_POD_WO_COD, --落地配签收时已核销代收货款
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_NPOD_WO_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_NPOD_WO_COD, --落地配反签收时已核销代收货款
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_WO_AGENCY_PAY_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_WO_AGENCY_PAY_POD, --落地配到达代理应付冲应收代收货款已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_WO_OTH_PAY_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_WO_OTH_PAY_COD, --落地配其他应付冲应收代收货款已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_UR_DEST_CH_NPOD, --还款现金未签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_UR_DEST_CD_NPOD, --还款银行未签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_UR_DEST_CH_POD, --还款现金已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_UR_DEST_CD_POD, --还款银行已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_AGENCY_WO_DEST_RCV_POD, --应付到达代理成本冲应收到付运费已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_NP,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_AGENCY_WO_DEST_RCV_NP, --应付到达代理成本冲应收到付运费未签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_OT_WO_DEST_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_OT_WO_DEST_RCV_POD, --其他应付冲应收到付运费已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_DEST_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_OTH_WO_DEST_RCV_NPOD, --其他应付冲应收到付运费未签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_DEST_RCV_POD, --预收落地配代理冲应收到付运费已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_DEST_RCV_NPOD, --预收落地配代理冲应收到付运费未签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_WO_COD_RCV_POD --预收落地配代理冲应收代收货款已签收
				FROM STV.T_STL_MVS T
			 WHERE T.PERIOD >= V_BEGIN_PERIOD
						 AND T.PERIOD < V_END_PERIOD
						 AND T.AMOUNT <> 0
						 AND T.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG
						 AND T.BUSINESS_CASE IN
						 (PKG_STL_VCH_COMMON.LAND_COD_CH_POD,
									PKG_STL_VCH_COMMON.LAND_COD_POD_NWO_COD,
									PKG_STL_VCH_COMMON.LAND_COD_CD_POD,
									PKG_STL_VCH_COMMON.LAND_COD_NPOD_NWO_COD,
									PKG_STL_VCH_COMMON.LAND_COD_POD_WO_COD,
									PKG_STL_VCH_COMMON.LAND_COD_NPOD_WO_COD,
									PKG_STL_VCH_COMMON.LAND_COD_WO_AGENCY_PAY_POD,
									PKG_STL_VCH_COMMON.LAND_COD_WO_OTH_PAY_COD,
									PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_NPOD,
									PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_NPOD,
									PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_POD,
									PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_POD,
									PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_POD,
									PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_NP,
									PKG_STL_VCH_COMMON.LAND_PR_OT_WO_DEST_RCV_POD,
									PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_DEST_RCV_NPOD,
									PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_POD,
									PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_NPOD,
									PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_POD)
			 GROUP BY T.PRODUCT_CODE,
								T.CUSTOMER_CODE,
								T.CUSTOMER_NAME,
								T.CUSTOMER_TYPE,
								T.DEST_ORG_CODE,
								T.DEST_ORG_NAME;
	
		COMMIT;
	
		RETURN TRUE;
	
	EXCEPTION
		WHEN OTHERS THEN
			--插入错误日志表
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_AFI',
																				'凭证生成落地配往来月报表程序异常:' || SQLERRM);
		
			--返回失败标志
			RETURN FALSE;
		
	END FUNC_VOUCHER_LDI;

	-----------------------------------------------------------------
	-- 营业部核销月报表入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_LWO(P_PERIOD     VARCHAR2,
														P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
														P_END_DATE   DATE -- 结束时间 2012-12-22
														) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
	 IS
	
		V_BEGIN_PERIOD VARCHAR2(50);
		V_END_PERIOD   VARCHAR2(50);
	
	BEGIN
	
		V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
		V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_LWO T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
			USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;
	
		INSERT INTO STV.T_STL_MVR_LWO
			(ID,
			 PERIOD,
			 PRODUCT_CODE,
			 CUSTOMER_CODE,
			 CUSTOMER_NAME,
			 CUSTOMER_TYPE,
			 ORIG_ORG_CODE,
			 ORIG_ORG_NAME,
			 DEST_ORG_CODE,
			 DEST_ORG_NAME,
			 VOUCHER_BEGIN_TIME,
			 VOUCHER_END_TIME,
			 CUST_DR_ORIG_LAND_RCV_NPOD,
			 CUST_DR_ORIG_LAND_RCV_POD,
			 CLAIM_WO_ORIG_LAND_RCV_POD,
			 CLAIM_WO_ORIG_LAND_RCV_NPOD,
			 OR_LAND_RCV_WO_CLAIM_PAY,
			 OR_LAND_RCV_WO_CUST_DR,
			 REFUND_WO_ORIG_LAND_RCV_POD,
			 REFUND_WO_ORIG_LAND_RCV_NPOD,
			 LAND_CLAIM_WO_ORIG_RCV_POD,
			 LAND_CLAIM_WO_ORIG_RCV_NPOD,
			 OR_RCV_WO_LAND_CLAIM_PAY,
			 LAND_REFUND_WO_ORIG_RCV_POD,
			 LAND_REFUND_WO_ORIG_RCV_NPOD)
			SELECT SYS_GUID(),
						 P_PERIOD,
						 PRODUCT_CODE,
						 CUSTOMER_CODE,
						 CUSTOMER_NAME,
						 CUSTOMER_TYPE,
						 ORIG_ORG_CODE,
						 ORIG_ORG_NAME,
						 DEST_ORG_CODE,
						 DEST_ORG_NAME,
						 P_BEGIN_DATE,
						 P_END_DATE,
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DR_ORIG_LAND_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DR_ORIG_LAND_RCV_NPOD, --预收客户冲快递应收始发运费未签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DR_ORIG_LAND_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DR_ORIG_LAND_RCV_POD, --预收客户冲快递应收始发运费已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_WO_ORIG_LAND_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_WO_ORIG_LAND_RCV_POD, --理赔冲快递始发应收已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_WO_ORIG_LAND_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_WO_ORIG_LAND_RCV_NPOD, --理赔冲快递始发应收未签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_LAND_RCV_WO_CLAIM_PAY,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_LAND_RCV_WO_CLAIM_PAY, --应付理赔冲快递小票应收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_LAND_RCV_WO_CUST_DR,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_LAND_RCV_WO_CUST_DR, --预收客户冲快递小票应收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.REFUND_WO_ORIG_LAND_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) REFUND_WO_ORIG_LAND_RCV_POD, --退运费冲快递始发应收已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.REFUND_WO_ORIG_LAND_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) REFUND_WO_ORIG_LAND_RCV_NPOD, --退运费冲快递始发应收未签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_CLAIM_WO_ORIG_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_CLAIM_WO_ORIG_RCV_POD, --快递理赔冲始发应收已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_CLAIM_WO_ORIG_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_CLAIM_WO_ORIG_RCV_NPOD, --快递理赔冲始发应收未签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_RCV_WO_LAND_CLAIM_PAY,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_RCV_WO_LAND_CLAIM_PAY, --快递应付理赔冲小票应收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_REFUND_WO_ORIG_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_REFUND_WO_ORIG_RCV_POD, --快递退运费冲始发应收已签收
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_REFUND_WO_ORIG_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_REFUND_WO_ORIG_RCV_NPOD --快递退运费冲始发应收未签收
				FROM STV.T_STL_MVS T
			 WHERE T.PERIOD >= V_BEGIN_PERIOD
						 AND T.PERIOD < V_END_PERIOD
						 AND T.AMOUNT <> 0
						 AND T.BUSINESS_CASE IN
						 (PKG_STL_VCH_COMMON.CUST_DR_ORIG_LAND_RCV_NPOD,
									PKG_STL_VCH_COMMON.CUST_DR_ORIG_LAND_RCV_POD,
									PKG_STL_VCH_COMMON.CLAIM_WO_ORIG_LAND_RCV_POD,
									PKG_STL_VCH_COMMON.CLAIM_WO_ORIG_LAND_RCV_NPOD,
									PKG_STL_VCH_COMMON.OR_LAND_RCV_WO_CLAIM_PAY,
									PKG_STL_VCH_COMMON.OR_LAND_RCV_WO_CUST_DR,
									PKG_STL_VCH_COMMON.REFUND_WO_ORIG_LAND_RCV_POD,
									PKG_STL_VCH_COMMON.REFUND_WO_ORIG_LAND_RCV_NPOD,
									PKG_STL_VCH_COMMON.LAND_CLAIM_WO_ORIG_RCV_POD,
									PKG_STL_VCH_COMMON.LAND_CLAIM_WO_ORIG_RCV_NPOD,
									PKG_STL_VCH_COMMON.OR_RCV_WO_LAND_CLAIM_PAY,
									PKG_STL_VCH_COMMON.LAND_REFUND_WO_ORIG_RCV_POD,
									PKG_STL_VCH_COMMON.LAND_REFUND_WO_ORIG_RCV_NPOD)
			 GROUP BY PRODUCT_CODE,
								CUSTOMER_CODE,
								CUSTOMER_NAME,
								CUSTOMER_TYPE,
								ORIG_ORG_CODE,
								ORIG_ORG_NAME,
								DEST_ORG_CODE,
								DEST_ORG_NAME;
	
		COMMIT;
	
		RETURN TRUE;
	
	EXCEPTION
		WHEN OTHERS THEN
			--插入错误日志表
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_LWO',
																				'凭证生成营业部核销月报表程序异常:' || SQLERRM);
		
			--返回失败标志
			RETURN FALSE;
		
	END FUNC_VOUCHER_LWO;

END PKG_STL_VCH_MONTHLY;
/
