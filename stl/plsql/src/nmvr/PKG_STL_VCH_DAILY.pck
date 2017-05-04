CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY IS

	-- ==============================================================
	-- Author  : ZHUWEI
	-- Created : 2012-12-07 14:29:42
	-- Purpose : ÿ��ƾ֤���
	-- ==============================================================

	-----------------------------------------------------------------
	-- ÿ��ƾ֤���
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_DAILY(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
															P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
															P_END_DATE   DATE -- ����ʱ�� 2012-12-22
															) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

END PKG_STL_VCH_DAILY;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY IS

	-- ==============================================================
	-- Author  : ZHUWEI
	-- Created : 2012-12-07 14:29:42
	-- Purpose : ÿ��ƾ֤���
	-- ==============================================================

	-----------------------------------------------------------------
	-- ÿ��ƾ֤���
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_DAILY(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
															P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
															P_END_DATE   DATE -- ����ʱ�� 2012-12-22
															) RETURN BOOLEAN IS
	
		V_RESULT BOOLEAN := TRUE;
	
	BEGIN
	
		--ɾ������ƾ֤������ϸ
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_NDVD T WHERE T.PERIOD = :P_PERIOD'
			USING P_PERIOD;
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_NDVDI T WHERE T.PERIOD = :P_PERIOD'
			USING P_PERIOD;
	
		--ɾ������ƾ֤�ջ���
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_NDVS T WHERE T.PERIOD = :P_PERIOD'
			USING P_PERIOD;
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_NDVSI T WHERE T.PERIOD = :P_PERIOD'
			USING P_PERIOD;
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_DVD_RETURN_COD T WHERE T.PERIOD = :P_PERIOD'
			USING P_PERIOD;
	
		--�����˵���01��
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_DEO.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																	P_BEGIN_DATE,
																																	P_END_DATE);
			COMMIT;
		END IF;
	
		--�����˵���02��
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_DET.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																	P_BEGIN_DATE,
																																	P_END_DATE);
			COMMIT;
		END IF;
	
		--�����˵����˷ѣ��½���ʱǷ������֧������01��
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_URO_ORIG.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																			 P_BEGIN_DATE,
																																			 P_END_DATE);
			COMMIT;
		END IF;
	
		--�����˵����˷ѣ��½���ʱǷ������֧������02��
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_URT_ORIG.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																			 P_BEGIN_DATE,
																																			 P_END_DATE);
			COMMIT;
		END IF;
	
		--�����˵����˷ѣ���������01��
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_URO_DEST.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																			 P_BEGIN_DATE,
																																			 P_END_DATE);
			COMMIT;
		END IF;
	
		--�����˵����˷ѣ���������02��
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_URT_DEST.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																			 P_BEGIN_DATE,
																																			 P_END_DATE);
			COMMIT;
		END IF;
	
		--ƫ��_�����˵����˷ѣ���������01��
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_UROP_DEST.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																				P_BEGIN_DATE,
																																				P_END_DATE);
			COMMIT;
		END IF;
	
		--ƫ��_�����˵����˷ѣ���������02��
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_URTP_DEST.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																				P_BEGIN_DATE,
																																				P_END_DATE);
			COMMIT;
		END IF;
	
		--����_�����˵����˷ѣ���������01��
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_UROA_DEST.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																				P_BEGIN_DATE,
																																				P_END_DATE);
			COMMIT;
		END IF;
	
		--����_�����˵����˷ѣ���������02��
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_URTA_DEST.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																				P_BEGIN_DATE,
																																				P_END_DATE);
			COMMIT;
		END IF;
	
		--ǩ���˵���01��
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_PODO.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																	 P_BEGIN_DATE,
																																	 P_END_DATE);
			COMMIT;
		END IF;
	
		--ǩ���˵���02��
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_PODT.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																	 P_BEGIN_DATE,
																																	 P_END_DATE);
			COMMIT;
		END IF;
	
		--��ǩ���˵���01��
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_UPDO.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																	 P_BEGIN_DATE,
																																	 P_END_DATE);
			COMMIT;
		END IF;
	
		--��ǩ���˵���02��
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_UPDT.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																	 P_BEGIN_DATE,
																																	 P_END_DATE);
			COMMIT;
		END IF;
	
		--Ԥ�տͻ�
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_CUST_DR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																			P_BEGIN_DATE,
																																			P_END_DATE);
			COMMIT;
		END IF;
	
		--���ջ���
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_COD.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																	P_BEGIN_DATE,
																																	P_END_DATE);
			COMMIT;
		END IF;
	
		--ƫ�ߴ���ɱ�
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_PL_COST.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																			P_BEGIN_DATE,
																																			P_END_DATE);
			COMMIT;
		END IF;
	
		--Ԥ��ƫ�ߴ���
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_PL_DR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																		P_BEGIN_DATE,
																																		P_END_DATE);
			COMMIT;
		END IF;
	
		--Ԥ�տ���/��������
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_AL_DR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																		P_BEGIN_DATE,
																																		P_END_DATE);
			COMMIT;
		END IF;
	
		--����/�����Ӧ����Ӧ��
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_ALPWR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																		P_BEGIN_DATE,
																																		P_END_DATE);
			COMMIT;
		END IF;
	
		--����
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_CLAIM.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																		P_BEGIN_DATE,
																																		P_END_DATE);
			COMMIT;
		END IF;
	
		--�쳣������
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_EX.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																 P_BEGIN_DATE,
																																 P_END_DATE);
			COMMIT;
		END IF;
	
		--������ʧ
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_BD.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																 P_BEGIN_DATE,
																																 P_END_DATE);
			COMMIT;
		END IF;
	
		--СƱ
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_OR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																 P_BEGIN_DATE,
																																 P_END_DATE);
			COMMIT;
		END IF;
	
		--������Υ��Ʒ��ȫƱ����
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_AC.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																 P_BEGIN_DATE,
																																 P_END_DATE);
			COMMIT;
		END IF;
	
		--���˷�
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_RD.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																 P_BEGIN_DATE,
																																 P_END_DATE);
			COMMIT;
		END IF;
	
		--װж��
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_SF.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																 P_BEGIN_DATE,
																																 P_END_DATE);
			COMMIT;
		END IF;
	
		--���񲹾�
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_CP.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																 P_BEGIN_DATE,
																																 P_END_DATE);
			COMMIT;
		END IF;
	
		--���˳ɱ�
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_AIR_COST.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																			 P_BEGIN_DATE,
																																			 P_END_DATE);
			COMMIT;
		END IF;
	
		--��������Ӧ��
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_AOR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																	P_BEGIN_DATE,
																																	P_END_DATE);
			COMMIT;
		END IF;
	
		--����Ӧ����Ӧ��
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_APWR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																	 P_BEGIN_DATE,
																																	 P_END_DATE);
			COMMIT;
		END IF;
	
		--���˴��ջ���
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_AIR_COD.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																			P_BEGIN_DATE,
																																			P_END_DATE);
			COMMIT;
		END IF;
	
		--Ԥ�տ��˴���
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_AIR_DR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																		 P_BEGIN_DATE,
																																		 P_END_DATE);
			COMMIT;
		END IF;
	
		--Ԥ��
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_APT.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																	P_BEGIN_DATE,
																																	P_END_DATE);
			COMMIT;
		END IF;
	
		--���˳�����Ӧ��
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_BWOR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																	 P_BEGIN_DATE,
																																	 P_END_DATE);
			COMMIT;
		END IF;
	
		--ƫ������Ӧ��
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_POR.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																	P_BEGIN_DATE,
																																	P_END_DATE);
			COMMIT;
		END IF;
	
		--ƫ��Ӧ�ճ�Ӧ��
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_PO_RWP.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																		 P_BEGIN_DATE,
																																		 P_END_DATE);
			COMMIT;
		END IF;
	
		--�˴��ջ����
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_RETURN_COD.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																				 P_BEGIN_DATE,
																																				 P_END_DATE);
			COMMIT;
		END IF;
	
		----------------------------------------
		--����䴦��
		----------------------------------------
	
		--����¼���
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
	
		--Ӫҵ�������±�����ϸ
		IF V_RESULT THEN
			V_RESULT := PKG_STL_VCH_DAILY_LAND_WO.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
																																			P_BEGIN_DATE,
																																			P_END_DATE);
			COMMIT;
		END IF;
	
		--�����ջ���
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
	
		--���������ջ���
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
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_DAILY',
																				'ƾ֤�����մ�������쳣:' || SQLERRM);
		
			--����ʧ�ܱ�־
			RETURN FALSE;
		
	END FUNC_VOUCHER_DAILY;

END PKG_STL_VCH_DAILY;
/
