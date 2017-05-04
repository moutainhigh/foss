CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY IS

	-- ==============================================================
	-- Author  : ZHUWEI
	-- Created : 2012-12-07 14:29:42
	-- Purpose : 每日凭证入口
	-- ==============================================================

	-----------------------------------------------------------------
	-- 每日凭证入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_DAILY(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
															P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
															P_END_DATE   DATE -- 结束时间 2012-12-22
															) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_DAILY;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY IS

	-- ==============================================================
	-- Author  : ZHUWEI
	-- Created : 2012-12-07 14:29:42
	-- Purpose : 每日凭证入口
	-- ==============================================================

	-----------------------------------------------------------------
	-- 每日凭证入口
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_DAILY(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
															P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
															P_END_DATE   DATE -- 结束时间 2012-12-22
															) RETURN BOOLEAN IS
	
		V_RESULT BOOLEAN := TRUE;
	
	BEGIN
	
		--删除当天凭证报表明细
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_NDVD T WHERE T.PERIOD = :P_PERIOD'
			USING P_PERIOD;
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_NDVDI T WHERE T.PERIOD = :P_PERIOD'
			USING P_PERIOD;
	
		--删除当天凭证日汇总
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_NDVS T WHERE T.PERIOD = :P_PERIOD'
			USING P_PERIOD;
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_NDVSI T WHERE T.PERIOD = :P_PERIOD'
			USING P_PERIOD;
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_DVD_RETURN_COD T WHERE T.PERIOD = :P_PERIOD'
			USING P_PERIOD;
	
		--开单运单【01】
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_DEO.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																	P_BEGIN_DATE,
																																	P_END_DATE);
			COMMIT;
		END IF;
	
		--开单运单【02】
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_DET.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																	P_BEGIN_DATE,
																																	P_END_DATE);
			COMMIT;
		END IF;
	
		--还款运单总运费（月结临时欠款网上支付）【01】
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_URO_ORIG.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																			 P_BEGIN_DATE,
																																			 P_END_DATE);
			COMMIT;
		END IF;
	
		--还款运单总运费（月结临时欠款网上支付）【02】
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_URT_ORIG.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																			 P_BEGIN_DATE,
																																			 P_END_DATE);
			COMMIT;
		END IF;
	
		--还款运单总运费（到付）【01】
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_URO_DEST.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																			 P_BEGIN_DATE,
																																			 P_END_DATE);
			COMMIT;
		END IF;
	
		--还款运单总运费（到付）【02】
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_URT_DEST.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																			 P_BEGIN_DATE,
																																			 P_END_DATE);
			COMMIT;
		END IF;
	
		--偏线_还款运单总运费（到付）【01】
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_UROP_DEST.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																				P_BEGIN_DATE,
																																				P_END_DATE);
			COMMIT;
		END IF;
	
		--偏线_还款运单总运费（到付）【02】
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_URTP_DEST.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																				P_BEGIN_DATE,
																																				P_END_DATE);
			COMMIT;
		END IF;
	
		--空运_还款运单总运费（到付）【01】
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_UROA_DEST.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																				P_BEGIN_DATE,
																																				P_END_DATE);
			COMMIT;
		END IF;
	
		--空运_还款运单总运费（到付）【02】
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_URTA_DEST.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																				P_BEGIN_DATE,
																																				P_END_DATE);
			COMMIT;
		END IF;
	
		--签收运单【01】
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_PODO.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																	 P_BEGIN_DATE,
																																	 P_END_DATE);
			COMMIT;
		END IF;
	
		--签收运单【02】
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_PODT.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																	 P_BEGIN_DATE,
																																	 P_END_DATE);
			COMMIT;
		END IF;
	
		--反签收运单【01】
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_UPDO.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																	 P_BEGIN_DATE,
																																	 P_END_DATE);
			COMMIT;
		END IF;
	
		--反签收运单【02】
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_UPDT.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																	 P_BEGIN_DATE,
																																	 P_END_DATE);
			COMMIT;
		END IF;
	
		--预收客户
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_CUST_DR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																			P_BEGIN_DATE,
																																			P_END_DATE);
			COMMIT;
		END IF;
	
		--代收货款
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_COD.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																	P_BEGIN_DATE,
																																	P_END_DATE);
			COMMIT;
		END IF;
	
		--偏线代理成本
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_PL_COST.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																			P_BEGIN_DATE,
																																			P_END_DATE);
			COMMIT;
		END IF;
	
		--预收偏线代理
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_PL_DR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																		P_BEGIN_DATE,
																																		P_END_DATE);
			COMMIT;
		END IF;
	
		--预收空运/落地配代理
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_AL_DR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																		P_BEGIN_DATE,
																																		P_END_DATE);
			COMMIT;
		END IF;
	
		--空运/落地配应付冲应收
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_ALPWR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																		P_BEGIN_DATE,
																																		P_END_DATE);
			COMMIT;
		END IF;
	
		--理赔
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_CLAIM.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																		P_BEGIN_DATE,
																																		P_END_DATE);
			COMMIT;
		END IF;
	
		--异常冲收入
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_EX.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																 P_BEGIN_DATE,
																																 P_END_DATE);
			COMMIT;
		END IF;
	
		--坏账损失
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_BD.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																 P_BEGIN_DATE,
																																 P_END_DATE);
			COMMIT;
		END IF;
	
		--小票
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_OR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																 P_BEGIN_DATE,
																																 P_END_DATE);
			COMMIT;
		END IF;
	
		--弃货、违禁品、全票丢货
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_AC.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																 P_BEGIN_DATE,
																																 P_END_DATE);
			COMMIT;
		END IF;
	
		--退运费
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_RD.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																 P_BEGIN_DATE,
																																 P_END_DATE);
			COMMIT;
		END IF;
	
		--装卸费
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_SF.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																 P_BEGIN_DATE,
																																 P_END_DATE);
			COMMIT;
		END IF;
	
		--服务补救
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_CP.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																 P_BEGIN_DATE,
																																 P_END_DATE);
			COMMIT;
		END IF;
	
		--空运成本
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_AIR_COST.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																			 P_BEGIN_DATE,
																																			 P_END_DATE);
			COMMIT;
		END IF;
	
		--空运其他应收
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_AOR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																	P_BEGIN_DATE,
																																	P_END_DATE);
			COMMIT;
		END IF;
	
		--空运应付冲应收
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_APWR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																	 P_BEGIN_DATE,
																																	 P_END_DATE);
			COMMIT;
		END IF;
	
		--空运代收货款
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_AIR_COD.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																			P_BEGIN_DATE,
																																			P_END_DATE);
			COMMIT;
		END IF;
	
		--预收空运代理
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_AIR_DR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																		 P_BEGIN_DATE,
																																		 P_END_DATE);
			COMMIT;
		END IF;
	
		--预付
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_APT.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																	P_BEGIN_DATE,
																																	P_END_DATE);
			COMMIT;
		END IF;
	
		--坏账冲其他应收
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_BWOR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																	 P_BEGIN_DATE,
																																	 P_END_DATE);
			COMMIT;
		END IF;
	
		--偏线其他应收
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_POR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																	P_BEGIN_DATE,
																																	P_END_DATE);
			COMMIT;
		END IF;
	
		--偏线应收冲应付
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_PO_RWP.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																		 P_BEGIN_DATE,
																																		 P_END_DATE);
			COMMIT;
		END IF;
	
		--退代收货款报表
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_RETURN_COD.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																				 P_BEGIN_DATE,
																																				 P_END_DATE);
			COMMIT;
		END IF;
	
		----------------------------------------
		--落地配处理
		----------------------------------------
	
		--快递新加列
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_LAND_BDR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																			 P_BEGIN_DATE,
																																			 P_END_DATE);
			COMMIT;
		END IF;
	
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_LAND_COD.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																			 P_BEGIN_DATE,
																																			 P_END_DATE);
			COMMIT;
		END IF;
	
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_LAND_COST.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																				P_BEGIN_DATE,
																																				P_END_DATE);
			COMMIT;
		END IF;
	
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_LAND_DR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																			P_BEGIN_DATE,
																																			P_END_DATE);
			COMMIT;
		END IF;
	
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_LAND_OTH.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																			 P_BEGIN_DATE,
																																			 P_END_DATE);
			COMMIT;
		END IF;
	
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_LAND_PR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																			P_BEGIN_DATE,
																																			P_END_DATE);
			COMMIT;
		END IF;
	
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_LAND_UR_DEST.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																					 P_BEGIN_DATE,
																																					 P_END_DATE);
			COMMIT;
		END IF;
	
		--营业部核销月报表明细
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_LAND_WO.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																			P_BEGIN_DATE,
																																			P_END_DATE);
			COMMIT;
		END IF;
	
		--计算日汇总
		INSERT INTO STV.T_STL_NDVS
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
			 AMOUNT,
			 AMOUNT_FRT,
			 AMOUNT_PUP,
			 AMOUNT_DEL,
			 AMOUNT_PKG,
			 AMOUNT_DV,
			 AMOUNT_COD,
			 AMOUNT_OT,
			 PRODUCT_CODE)
			SELECT SYS_GUID(),
						 P_PERIOD,
						 T.BUSINESS_CASE,
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
						 T.PRODUCT_CODE
				FROM STV.T_STL_NDVD T
			 WHERE T.PERIOD = P_PERIOD
			 GROUP BY T.BUSINESS_CASE,
								T.CUSTOMER_CODE,
								T.CUSTOMER_NAME,
								T.CUSTOMER_TYPE,
								T.ORIG_ORG_CODE,
								T.ORIG_ORG_NAME,
								T.DEST_ORG_CODE,
								T.DEST_ORG_NAME,
								T.PRODUCT_CODE;
	
		--计算往来日汇总
		INSERT INTO STV.T_STL_NDVSI
			(ID,
			 PERIOD,
			 BUSINESS_CASE,
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
			 PRODUCT_CODE)
			SELECT SYS_GUID(),
						 P_PERIOD,
						 BUSINESS_CASE,
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
						 SUM(T.AMOUNT),
						 T.PRODUCT_CODE
				FROM STV.T_STL_NDVDI T
			 WHERE T.PERIOD = P_PERIOD
			 GROUP BY BUSINESS_CASE,
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
								T.PRODUCT_CODE;
	
		COMMIT;
	
		RETURN V_RESULT;
	
	EXCEPTION
		WHEN OTHERS THEN
			DBMS_OUTPUT.PUT_LINE('EXCEPTION');
			--插入错误日志表
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_DAILY',
																				'凭证生成日处理程序异常:' || SQLERRM);
		
			--返回失败标志
			RETURN FALSE;
		
	END FUNC_VOUCHER_DAILY;

END PKG_STL_VCH_DAILY;
/
