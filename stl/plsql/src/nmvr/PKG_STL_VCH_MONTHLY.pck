CREATE OR REPLACE PACKAGE STV.PKG_STL_VCH_MONTHLY IS

	-- ==============================================================
	-- Author  : ZHUWEI
	-- Created : 2012-12-07 14:29:42
	-- Purpose : ÿ��ƾ֤���
	-- ==============================================================

	-----------------------------------------------------------------
	-- ÿ��ƾ֤�������
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_MONTHLY(P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
																P_END_DATE   DATE -- ����ʱ�� 2012-12-22
																) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

	-----------------------------------------------------------------
	-- ʼ���±������
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NRFONO(P_PERIOD     VARCHAR2,
															 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
															 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
															 ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

	FUNCTION FUNC_VOUCHER_NRFOSO(P_PERIOD     VARCHAR2,
															 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
															 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
															 ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��    

	FUNCTION FUNC_VOUCHER_NRFONT(P_PERIOD     VARCHAR2,
															 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
															 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
															 ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��      

	FUNCTION FUNC_VOUCHER_NRFOST(P_PERIOD     VARCHAR2,
															 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
															 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
															 ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��                                                                          

	-----------------------------------------------------------------
	-- �����±������
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NRFDO(P_PERIOD     VARCHAR2,
															P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
															P_END_DATE   DATE -- ����ʱ�� 2012-12-22
															) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

	FUNCTION FUNC_VOUCHER_NRFDT(P_PERIOD     VARCHAR2,
															P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
															P_END_DATE   DATE -- ����ʱ�� 2012-12-22
															) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��                            

	-----------------------------------------------------------------
	-- ƫ���±������
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NPLR(P_PERIOD     VARCHAR2,
														 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
														 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
														 ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

	-----------------------------------------------------------------
	-- �����±������
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NAFR(P_PERIOD     VARCHAR2,
														 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
														 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
														 ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

	-----------------------------------------------------------------
	-- ʼ�����������±������
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NRFI(P_PERIOD     VARCHAR2,
														 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
														 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
														 ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

	-----------------------------------------------------------------
	-- ʼ��ƫ�������±������
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NPLI(P_PERIOD     VARCHAR2,
														 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
														 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
														 ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

	-----------------------------------------------------------------
	-- ʼ�����������±������
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NAFI(P_PERIOD     VARCHAR2,
														 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
														 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
														 ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

	-----------------------------------------------------------------
	-- �˴��ջ�������
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_RETURN_COD(P_PERIOD     VARCHAR2,
																	 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
																	 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
																	 ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

	-----------------------------------------------------------------
	-- ������±���
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_LDD(P_PERIOD     VARCHAR2,
														P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
														P_END_DATE   DATE -- ����ʱ�� 2012-12-22
														) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

	-----------------------------------------------------------------
	-- ����������±���
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_LDI(P_PERIOD     VARCHAR2,
														P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
														P_END_DATE   DATE -- ����ʱ�� 2012-12-22
														) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

	-----------------------------------------------------------------
	-- Ӫҵ�������±���
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_LWO(P_PERIOD     VARCHAR2,
														P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
														P_END_DATE   DATE -- ����ʱ�� 2012-12-22
														) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

END PKG_STL_VCH_MONTHLY;
/
CREATE OR REPLACE PACKAGE BODY STV.PKG_STL_VCH_MONTHLY IS

	-- ==============================================================
	-- Author  : ZHUWEI
	-- Created : 2012-12-07 14:29:42
	-- Purpose : ÿ��ƾ֤���
	-- ==============================================================

	-----------------------------------------------------------------
	-- ÿ��ƾ֤���
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_MONTHLY(P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21(����)
																P_END_DATE   DATE -- ����ʱ�� 2012-12-22(������)
																) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
	 IS
	
		V_START_PERIOD VARCHAR2(50); --��ʼ�ڼ�
		V_END_PERIOD   VARCHAR2(50); --�����ڼ�
	
	BEGIN
	
		--��ʼ�ڼ�ͽ����ڼ�
		V_START_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
		V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');
	
		-- ɾ����Ҫ����ͳ�Ƶ��»��ܱ���
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_NMVS T WHERE T.PERIOD >= :BEGIN_PERIOD AND T.PERIOD < :END_PERIOD'
			USING V_START_PERIOD, V_END_PERIOD;
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_NMVSI T WHERE T.PERIOD >= :BEGIN_PERIOD AND T.PERIOD < :END_PERIOD'
			USING V_START_PERIOD, V_END_PERIOD;
	
		--ͳ���»�������
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
	
		--ͳ�������»�������
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
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_START_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_MONTHLY',
																				'ƾ֤�����»��ܳ����쳣:' || SQLERRM);
		
			--����ʧ�ܱ�־
			RETURN FALSE;
		
	END FUNC_VOUCHER_MONTHLY;

	-----------------------------------------------------------------
	-- 01��ͨʼ���±������
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NRFONO(P_PERIOD     VARCHAR2,
															 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
															 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
															 ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
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
			 ALPWR_WO_DEST_RCVO_NPOD, --��1��
			 ALPWR_WO_DEST_RCVO_POD, --��2��
			 AL_DR_WO_DEST_RCVO_NPOD, --��3��
			 AL_DR_WO_DEST_RCVO_POD, --��4��
			 COD_PAY_WO_DEST_RCVO_NPOD, --��5��
			 COD_PAY_WO_DEST_RCVO_POD, --��6��
			 COD_PAY_WO_ORIG_RCVO_NPOD, --��7��
			 COD_PAY_WO_ORIG_RCVO_POD, --��8��
			 CUST_DRO_PAY_APPLY, --��9��
			 CUST_DRO_WO_DEST_RCVO_NPOD, --��10��
			 CUST_DRO_WO_DEST_RCVO_POD, --��11��
			 CUST_DRO_WO_ORIG_RCVO_NPOD, --��12��
			 CUST_DRO_WO_ORIG_RCVO_POD, --��13��
			 CUST_DRO_WO_ORIG_RCVT_NPOD, --��14��
			 CUST_DRO_WO_ORIG_RCVT_POD, --��15��
			 CUST_DRT_WO_DEST_RCVO_NPOD, --��16��
			 CUST_DRT_WO_DEST_RCVO_POD, --��17��
			 CUST_DRT_WO_ORIG_RCVO_NPOD, --��18��
			 CUST_DRT_WO_ORIG_RCVO_POD, --��19��
			 CUST_DR_OCD, --��20��
			 CUST_DR_OCH, --��21��
			 DEO_CD, --��22��
			 DEO_CH, --��23��
			 OTH_PAY_WO_DEST_RCVO_NPOD, --��24��
			 OTH_PAY_WO_DEST_RCVO_POD, --��25��
			 PL_COST_WO_DEST_RCVO_NPOD, --��26��
			 PL_COST_WO_DEST_RCVO_POD, --��27��
			 PL_DR_WO_DEST_RCVO_NPOD, --��28��
			 PL_DR_WO_DEST_RCVO_POD, --��29��
			 PODO_CASH_COLLECTED_COD, --��30��
			 PODO_CASH_COLLECTED_DEL, --��31��
			 PODO_CASH_COLLECTED_DV, --��32��
			 PODO_CASH_COLLECTED_FRT, --��33��
			 PODO_CASH_COLLECTED_OT, --��34��
			 PODO_CASH_COLLECTED_PKG, --��35��
			 PODO_CASH_COLLECTED_PUP, --��36��
			 PODO_DEST_RCV_NWO_COD, --��37��
			 PODO_DEST_RCV_NWO_DEL, --��38��
			 PODO_DEST_RCV_NWO_DV, --��39��
			 PODO_DEST_RCV_NWO_FRT, --��40��
			 PODO_DEST_RCV_NWO_OT, --��41��
			 PODO_DEST_RCV_NWO_PKG, --��42��
			 PODO_DEST_RCV_NWO_PUP, --��43��
			 PODO_DEST_RCV_WO_COD, --��44��
			 PODO_DEST_RCV_WO_DEL, --��45��
			 PODO_DEST_RCV_WO_DV, --��46��
			 PODO_DEST_RCV_WO_FRT, --��47��
			 PODO_DEST_RCV_WO_OT, --��48��
			 PODO_DEST_RCV_WO_PKG, --��49��
			 PODO_DEST_RCV_WO_PUP, --��50��
			 PODO_ORIG_RCV_NWO_COD, --��51��
			 PODO_ORIG_RCV_NWO_DEL, --��52��
			 PODO_ORIG_RCV_NWO_DV, --��53��
			 PODO_ORIG_RCV_NWO_FRT, --��54��
			 PODO_ORIG_RCV_NWO_OT, --��55��
			 PODO_ORIG_RCV_NWO_PKG, --��56��
			 PODO_ORIG_RCV_NWO_PUP, --��57��
			 PODO_ORIG_RCV_WO_COD, --��58��
			 PODO_ORIG_RCV_WO_DEL, --��59��
			 PODO_ORIG_RCV_WO_DV, --��60��
			 PODO_ORIG_RCV_WO_FRT, --��61��
			 PODO_ORIG_RCV_WO_OT, --��62��
			 PODO_ORIG_RCV_WO_PKG, --��63��
			 PODO_ORIG_RCV_WO_PUP, --��64��
			 UPDO_CASH_COLLECTED_COD, --��65��
			 UPDO_CASH_COLLECTED_DEL, --��66��
			 UPDO_CASH_COLLECTED_DV, --��67��
			 UPDO_CASH_COLLECTED_FRT, --��68��
			 UPDO_CASH_COLLECTED_OT, --��69��
			 UPDO_CASH_COLLECTED_PKG, --��70��
			 UPDO_CASH_COLLECTED_PUP, --��71��
			 UPDO_DEST_RCV_NWO_COD, --��72��
			 UPDO_DEST_RCV_NWO_DEL, --��73��
			 UPDO_DEST_RCV_NWO_DV, --��74��
			 UPDO_DEST_RCV_NWO_FRT, --��75��
			 UPDO_DEST_RCV_NWO_OT, --��76��
			 UPDO_DEST_RCV_NWO_PKG, --��77��
			 UPDO_DEST_RCV_NWO_PUP, --��78��
			 UPDO_DEST_RCV_WO_COD, --��79��
			 UPDO_DEST_RCV_WO_DEL, --��80��
			 UPDO_DEST_RCV_WO_DV, --��81��
			 UPDO_DEST_RCV_WO_FRT, --��82��
			 UPDO_DEST_RCV_WO_OT, --��83��
			 UPDO_DEST_RCV_WO_PKG, --��84��
			 UPDO_DEST_RCV_WO_PUP, --��85��
			 UPDO_ORIG_RCV_NWO_COD, --��86��
			 UPDO_ORIG_RCV_NWO_DEL, --��87��
			 UPDO_ORIG_RCV_NWO_DV, --��88��
			 UPDO_ORIG_RCV_NWO_FRT, --��89��
			 UPDO_ORIG_RCV_NWO_OT, --��90��
			 UPDO_ORIG_RCV_NWO_PKG, --��91��
			 UPDO_ORIG_RCV_NWO_PUP, --��92��
			 UPDO_ORIG_RCV_WO_COD, --��93��
			 UPDO_ORIG_RCV_WO_DEL, --��94��
			 UPDO_ORIG_RCV_WO_DV, --��95��
			 UPDO_ORIG_RCV_WO_FRT, --��96��
			 UPDO_ORIG_RCV_WO_OT, --��97��
			 UPDO_ORIG_RCV_WO_PKG, --��98��
			 UPDO_ORIG_RCV_WO_PUP, --��99��
			 URO_DEST_CD_NPOD, --��100��
			 URO_DEST_CD_POD, --��101��
			 URO_DEST_CH_NPOD, --��102��
			 URO_DEST_CH_POD, --��103��
			 URO_ORIG_CD_NPOD, --��104��
			 URO_ORIG_CD_POD, --��105��
			 URO_ORIG_CH_NPOD, --��106��
			 URO_ORIG_CH_POD, --��107��
			 POP_WO_DRO_POD, -- ��108��
			 POP_WO_DRO_NPOD --��109��
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
								 0) ALPWR_WO_DEST_RCVO_NPOD, --Ӧ���������/��������ɱ���01Ӧ�յ����˷�δǩ�ա�1��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.ALPWR_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) ALPWR_WO_DEST_RCVO_POD, --Ӧ���������/��������ɱ���01Ӧ�յ����˷���ǩ�ա�2��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AL_DR_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) AL_DR_WO_DEST_RCVO_NPOD, --Ԥ�տ���/���������01Ӧ�յ����˷�δǩ�ա�3��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AL_DR_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) AL_DR_WO_DEST_RCVO_POD, --Ԥ�տ���/���������01Ӧ�յ����˷���ǩ�ա�4��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) COD_PAY_WO_DEST_RCVO_NPOD, --Ӧ�����ջ����01Ӧ�յ����˷�δǩ�ա�5��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) COD_PAY_WO_DEST_RCVO_POD, --Ӧ�����ջ����01Ӧ�յ����˷���ǩ�ա�6��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) COD_PAY_WO_ORIG_RCVO_NPOD, --Ӧ�����ջ����01Ӧ��ʼ���˷�δǩ�ա�7��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) COD_PAY_WO_ORIG_RCVO_POD, --Ӧ�����ջ����01Ӧ��ʼ���˷���ǩ�ա�8��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_PAY_APPLY, --01ʼ����Ԥ�ո������롾9��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_DEST_RCVO_NPOD, --01Ԥ�տͻ���01Ӧ�յ����˷�δǩ�ա�10��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_DEST_RCVO_POD, --01Ԥ�տͻ���01Ӧ�յ����˷���ǩ�ա�11��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_ORIG_RCVO_NPOD, --01Ԥ�տͻ���01Ӧ��ʼ���˷�δǩ�ա�12��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_ORIG_RCVO_POD, --01Ԥ�տͻ���01Ӧ��ʼ���˷���ǩ�ա�13��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_NPODD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_ORIG_RCVT_NPOD, --01Ԥ�տͻ���02Ӧ��ʼ���˷�δǩ�ա�14��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_PODD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_ORIG_RCVT_POD, --01Ԥ�տͻ���02Ӧ��ʼ���˷���ǩ�ա�15��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_DEST_RCVO_NPOD, --02Ԥ�տͻ���01Ӧ�յ����˷�δǩ�ա�16��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_DEST_RCVO_POD, --02Ԥ�տͻ���01Ӧ�յ����˷���ǩ�ա�17��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_ORIG_RCVO_NPOD, --02Ԥ�տͻ���01Ӧ��ʼ���˷�δǩ�ա�18��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_ORIG_RCVO_POD, --02Ԥ�տͻ���01Ӧ��ʼ���˷���ǩ�ա�19��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DR_OCD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DR_OCD, --Ԥ�տͻ����С�20��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DR_OCH,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DR_OCH, --Ԥ�տͻ��ֽ�21��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.DEO_CD,
														NVL(AMOUNT, 0),
														0)),
								 0) DEO_CD, --�������п���22��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.DEO_CH,
														NVL(AMOUNT, 0),
														0)),
								 0) DEO_CH, --�����ֽ�23��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OTH_PAY_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) OTH_PAY_WO_DEST_RCVO_NPOD, --����Ӧ����01Ӧ�յ����˷�δǩ�ա�24��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OTH_PAY_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) OTH_PAY_WO_DEST_RCVO_POD, --����Ӧ����01Ӧ�յ����˷���ǩ�ա�25��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_WO_DEST_RCVO_NPOD, --Ӧ��ƫ�ߴ���ɱ���01Ӧ�յ����˷�δǩ�ա�26��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_WO_DEST_RCVO_POD, --Ӧ��ƫ�ߴ���ɱ���01Ӧ�յ����˷���ǩ�ա�27��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_DR_WO_DEST_RCVO_NPOD, --Ԥ��ƫ�ߴ����01Ӧ�յ����˷�δǩ�ա�28��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_DR_WO_DEST_RCVO_POD, --Ԥ��ƫ�ߴ����01Ӧ�յ����˷���ǩ�ա�29��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_CASH_COLLECTED,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) PODO_CASH_COLLECTED_COD, --ǩ��ʱ�ָ����տ���_���ջ��������ѡ�30��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_CASH_COLLECTED,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) PODO_CASH_COLLECTED_DEL, --ǩ��ʱ�ָ����տ���_�ͻ��ѡ�31��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_CASH_COLLECTED,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) PODO_CASH_COLLECTED_DV, --ǩ��ʱ�ָ����տ���_���۷ѡ�32��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_CASH_COLLECTED,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) PODO_CASH_COLLECTED_FRT, --ǩ��ʱ�ָ����տ���_�������˷ѡ�33��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_CASH_COLLECTED,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) PODO_CASH_COLLECTED_OT, --ǩ��ʱ�ָ����տ���_�������á�34��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_CASH_COLLECTED,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) PODO_CASH_COLLECTED_PKG, --ǩ��ʱ�ָ����տ���_��װ�ѡ�35��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_CASH_COLLECTED,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) PODO_CASH_COLLECTED_PUP, --ǩ��ʱ�ָ����տ���_�ӻ��ѡ�36��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_NWO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) PODO_DEST_RCV_NWO_COD, --ǩ��ʱ����Ӧ��δ�������_���ջ��������ѡ�37��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_NWO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) PODO_DEST_RCV_NWO_DEL, --ǩ��ʱ����Ӧ��δ�������_�ͻ��ѡ�38��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_NWO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) PODO_DEST_RCV_NWO_DV, --ǩ��ʱ����Ӧ��δ�������_���۷ѡ�39��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_NWO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) PODO_DEST_RCV_NWO_FRT, --ǩ��ʱ����Ӧ��δ�������_�������˷ѡ�40��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_NWO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) PODO_DEST_RCV_NWO_OT, --ǩ��ʱ����Ӧ��δ�������_�������á�41��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_NWO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) PODO_DEST_RCV_NWO_PKG, --ǩ��ʱ����Ӧ��δ�������_��װ�ѡ�42��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_NWO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) PODO_DEST_RCV_NWO_PUP, --ǩ��ʱ����Ӧ��δ�������_�ӻ��ѡ�43��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_WO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) PODO_DEST_RCV_WO_COD, --ǩ��ʱ����Ӧ���Ѻ������_���ջ��������ѡ�44��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_WO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) PODO_DEST_RCV_WO_DEL, --ǩ��ʱ����Ӧ���Ѻ������_�ͻ��ѡ�45��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_WO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) PODO_DEST_RCV_WO_DV, --ǩ��ʱ����Ӧ���Ѻ������_���۷ѡ�46��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_WO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) PODO_DEST_RCV_WO_FRT, --ǩ��ʱ����Ӧ���Ѻ������_�������˷ѡ�47��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_WO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) PODO_DEST_RCV_WO_OT, --ǩ��ʱ����Ӧ���Ѻ������_�������á�48��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_WO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) PODO_DEST_RCV_WO_PKG, --ǩ��ʱ����Ӧ���Ѻ������_��װ�ѡ�49��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_DEST_RCV_WO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) PODO_DEST_RCV_WO_PUP, --ǩ��ʱ����Ӧ���Ѻ������_�ӻ��ѡ�50��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_NWO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) PODO_ORIG_RCV_NWO_COD, --ǩ��ʱʼ��Ӧ��δ�������_���ջ��������ѡ�51��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_NWO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) PODO_ORIG_RCV_NWO_DEL, --ǩ��ʱʼ��Ӧ��δ�������_�ͻ��ѡ�52��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_NWO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) PODO_ORIG_RCV_NWO_DV, --ǩ��ʱʼ��Ӧ��δ�������_���۷ѡ�53��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_NWO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) PODO_ORIG_RCV_NWO_FRT, --ǩ��ʱʼ��Ӧ��δ�������_�������˷ѡ�54��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_NWO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) PODO_ORIG_RCV_NWO_OT, --ǩ��ʱʼ��Ӧ��δ�������_�������á�55��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_NWO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) PODO_ORIG_RCV_NWO_PKG, --ǩ��ʱʼ��Ӧ��δ�������_��װ�ѡ�56��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_NWO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) PODO_ORIG_RCV_NWO_PUP, --ǩ��ʱʼ��Ӧ��δ�������_�ӻ��ѡ�57��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_WO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) PODO_ORIG_RCV_WO_COD, --ǩ��ʱʼ��Ӧ���Ѻ������_���ջ��������ѡ�58��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_WO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) PODO_ORIG_RCV_WO_DEL, --ǩ��ʱʼ��Ӧ���Ѻ������_�ͻ��ѡ�59��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_WO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) PODO_ORIG_RCV_WO_DV, --ǩ��ʱʼ��Ӧ���Ѻ������_���۷ѡ�60��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_WO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) PODO_ORIG_RCV_WO_FRT, --ǩ��ʱʼ��Ӧ���Ѻ������_�������˷ѡ�61��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_WO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) PODO_ORIG_RCV_WO_OT, --ǩ��ʱʼ��Ӧ���Ѻ������_�������á�62��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_WO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) PODO_ORIG_RCV_WO_PKG, --ǩ��ʱʼ��Ӧ���Ѻ������_��װ�ѡ�63��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODO_ORIG_RCV_WO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) PODO_ORIG_RCV_WO_PUP, --ǩ��ʱʼ��Ӧ���Ѻ������_�ӻ��ѡ�64��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_CASH_COLLECTED,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) UPDO_CASH_COLLECTED_COD, --��ǩ��ʱ�ָ����տ���_���ջ��������ѡ�65��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_CASH_COLLECTED,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) UPDO_CASH_COLLECTED_DEL, --��ǩ��ʱ�ָ����տ���_�ͻ��ѡ�66��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_CASH_COLLECTED,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) UPDO_CASH_COLLECTED_DV, --��ǩ��ʱ�ָ����տ���_���۷ѡ�67��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_CASH_COLLECTED,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) UPDO_CASH_COLLECTED_FRT, --��ǩ��ʱ�ָ����տ���_�������˷ѡ�68��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_CASH_COLLECTED,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) UPDO_CASH_COLLECTED_OT, --��ǩ��ʱ�ָ����տ���_�������á�69��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_CASH_COLLECTED,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) UPDO_CASH_COLLECTED_PKG, --��ǩ��ʱ�ָ����տ���_��װ�ѡ�70��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_CASH_COLLECTED,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) UPDO_CASH_COLLECTED_PUP, --��ǩ��ʱ�ָ����տ���_�ӻ��ѡ�71��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_NWO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) UPDO_DEST_RCV_NWO_COD, --��ǩ��ʱ����Ӧ��δ�������_���ջ��������ѡ�72��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_NWO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) UPDO_DEST_RCV_NWO_DEL, --��ǩ��ʱ����Ӧ��δ�������_�ͻ��ѡ�73��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_NWO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) UPDO_DEST_RCV_NWO_DV, --��ǩ��ʱ����Ӧ��δ�������_���۷ѡ�74��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_NWO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) UPDO_DEST_RCV_NWO_FRT, --��ǩ��ʱ����Ӧ��δ�������_�������˷ѡ�75��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_NWO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) UPDO_DEST_RCV_NWO_OT, --��ǩ��ʱ����Ӧ��δ�������_�������á�76��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_NWO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) UPDO_DEST_RCV_NWO_PKG, --��ǩ��ʱ����Ӧ��δ�������_��װ�ѡ�77��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_NWO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) UPDO_DEST_RCV_NWO_PUP, --��ǩ��ʱ����Ӧ��δ�������_�ӻ��ѡ�78��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_WO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) UPDO_DEST_RCV_WO_COD, --��ǩ��ʱ����Ӧ���Ѻ������_���ջ��������ѡ�79��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_WO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) UPDO_DEST_RCV_WO_DEL, --��ǩ��ʱ����Ӧ���Ѻ������_�ͻ��ѡ�80��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_WO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) UPDO_DEST_RCV_WO_DV, --��ǩ��ʱ����Ӧ���Ѻ������_���۷ѡ�81��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_WO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) UPDO_DEST_RCV_WO_FRT, --��ǩ��ʱ����Ӧ���Ѻ������_�������˷ѡ�82��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_WO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) UPDO_DEST_RCV_WO_OT, --��ǩ��ʱ����Ӧ���Ѻ������_�������á�83��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_WO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) UPDO_DEST_RCV_WO_PKG, --��ǩ��ʱ����Ӧ���Ѻ������_��װ�ѡ�84��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_DEST_RCV_WO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) UPDO_DEST_RCV_WO_PUP, --��ǩ��ʱ����Ӧ���Ѻ������_�ӻ��ѡ�85��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_NWO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) UPDO_ORIG_RCV_NWO_COD, --��ǩ��ʱʼ��Ӧ��δ�������_���ջ��������ѡ�86��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_NWO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) UPDO_ORIG_RCV_NWO_DEL, --��ǩ��ʱʼ��Ӧ��δ�������_�ͻ��ѡ�87��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_NWO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) UPDO_ORIG_RCV_NWO_DV, --��ǩ��ʱʼ��Ӧ��δ�������_���۷ѡ�88��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_NWO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) UPDO_ORIG_RCV_NWO_FRT, --��ǩ��ʱʼ��Ӧ��δ�������_�������˷ѡ�89��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_NWO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) UPDO_ORIG_RCV_NWO_OT, --��ǩ��ʱʼ��Ӧ��δ�������_�������á�90��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_NWO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) UPDO_ORIG_RCV_NWO_PKG, --��ǩ��ʱʼ��Ӧ��δ�������_��װ�ѡ�91��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_NWO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) UPDO_ORIG_RCV_NWO_PUP, --��ǩ��ʱʼ��Ӧ��δ�������_�ӻ��ѡ�92��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_WO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) UPDO_ORIG_RCV_WO_COD, --��ǩ��ʱʼ��Ӧ���Ѻ������_���ջ��������ѡ�93��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_WO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) UPDO_ORIG_RCV_WO_DEL, --��ǩ��ʱʼ��Ӧ���Ѻ������_�ͻ��ѡ�94��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_WO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) UPDO_ORIG_RCV_WO_DV, --��ǩ��ʱʼ��Ӧ���Ѻ������_���۷ѡ�95��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_WO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) UPDO_ORIG_RCV_WO_FRT, --��ǩ��ʱʼ��Ӧ���Ѻ������_�������˷ѡ�96��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_WO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) UPDO_ORIG_RCV_WO_OT, --��ǩ��ʱʼ��Ӧ���Ѻ������_�������á�97��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_WO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) UPDO_ORIG_RCV_WO_PKG, --��ǩ��ʱʼ��Ӧ���Ѻ������_��װ�ѡ�98��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDO_ORIG_RCV_WO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) UPDO_ORIG_RCV_WO_PUP, --��ǩ��ʱʼ��Ӧ���Ѻ������_�ӻ��ѡ�99��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_DEST_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_DEST_CD_NPOD, --��������δǩ�ա�100��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_DEST_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_DEST_CD_POD, --����������ǩ�ա�101��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_DEST_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_DEST_CH_NPOD, --�����ֽ�δǩ�ա�102��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_DEST_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_DEST_CH_POD, --�����ֽ���ǩ�ա�103��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_ORIG_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_ORIG_CD_NPOD, --��������δǩ�ա�104��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_ORIG_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_ORIG_CD_POD, --����������ǩ�ա�105��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_ORIG_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_ORIG_CH_NPOD, --�����ֽ�δǩ�ա�106��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_ORIG_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_ORIG_CH_POD, --�����ֽ���ǩ�ա�107��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.POP_WO_DRO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) POP_WO_DRO_POD, --����Ӧ����01Ӧ�յ����˷���ǩ�ա�108��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.POP_WO_DRO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) POP_WO_DRO_NPOD --����Ӧ����01Ӧ�յ����˷�δǩ�ա�109��
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
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NRFONO',
																				'ƾ֤����01��ͨʼ����������쳣:' || SQLERRM);
		
			--����ʧ�ܱ�־
			RETURN FALSE;
		
	END FUNC_VOUCHER_NRFONO;

	-----------------------------------------------------------------
	-- 01����ʼ���±������
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NRFOSO(P_PERIOD     VARCHAR2,
															 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
															 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
															 ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
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
			 AC_CASH, --��1��
			 AC_ORIG_RCV_NWO, --��2��
			 AC_ORIG_RCV_WO, --��3��
			 BD_WO_DEST_RCVO_POD, --��4��
			 BD_WO_ORIG_RCVO_POD, --��5��
			 CLAIM_DESTO_INCOME, --��6��
			 CLAIM_DESTO_WO_DEST_RCVO_NPOD, --��7��
			 CLAIM_DESTO_WO_DEST_RCVO_POD, --��8��
			 CLAIM_DESTT_WO_DEST_RCVO_NPOD, --��9��
			 CLAIM_DESTT_WO_DEST_RCVO_POD, --��10��
			 CLAIM_ORIGO_COST, --��11��
			 CLAIM_ORIGO_INCOME, --��12��
			 CLAIM_ORIGO_ORIG_RCVO_NPOD, --��13��
			 CLAIM_ORIGO_PAY_APPLY, --��14��
			 CLAIM_ORIGO_WO_ORIG_RCVO_POD, --��15��
			 CLAIM_ORIGO_WO_ORIG_RCVT_NPOD, --��16��
			 CLAIM_ORIGO_WO_ORIG_RCVT_POD, --��17��
			 CLAIM_ORIGT_ORIG_RCVO_POD, --��18��
			 CLAIM_ORIGT_WO_ORIG_RCVO_NPOD, --��19��
			 CPO_ORIG_PAY_APPLY, --��20��
			 EX_DEST_RCVO_POD, --��21��
			 EX_ORIG_RCVO_POD, --��22��
			 OR_BAD_WO_RCVO, --��23��
			 OR_CD_PBIO, --��24��
			 OR_CD_UR_RCVO, --��25��
			 OR_CH_PBIO, --��26��
			 OR_CH_UR_RCVO, --��27��
			 OR_CLAIM_PAYO_WO_RCVO, --��28��
			 OR_CLAIM_PAYO_WO_RCVT, --��29��
			 OR_CLAIM_PAYT_WO_RCVO, --��30��
			 OR_COD_PAY_WO_RCVO, --��31��
			 OR_CUST_DRO_WO_RCVO, --��32��
			 OR_CUST_DRO_WO_RCVT, --��33��
			 OR_CUST_DRT_WO_RCVO, --��34��
			 OR_EX_WO_RCVO, --��35��
			 OR_RCVO_PBI, --��36��
			 RD_DESTO_DEST_RCVO_POD, --��37��
			 RD_DESTO_INCOME, --��38��
			 RD_DESTO_WO_DEST_RCVO_NPOD, --��39��
			 RD_DESTT_WO_DEST_RCVO_NPOD, --��40��
			 RD_DESTT_WO_DEST_RCVO_POD, --��41��
			 RD_ORIGO_COST, --��42��
			 RD_ORIGO_INCOME, --��43��
			 RD_ORIGO_PAY_APPLY, --��44��
			 RD_ORIGO_WO_ORIG_RCVO_NPOD, --��45��
			 RD_ORIGO_WO_ORIG_RCVO_POD, --��46��
			 RD_ORIGO_WO_ORIG_RCVT_NPOD, --��47��
			 RD_ORIGO_WO_ORIG_RCVT_POD, --��48��
			 RD_ORIGT_WO_ORIG_RCVO_NPOD, --��49��
			 RD_ORIGT_WO_ORIG_RCVO_POD, --��50��
			 SFO_PAY_APPLY --��51��
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
								 0) AC_CASH, --������Ϊ�ֽ����п���1��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AC_ORIG_RCV_NWOO,
														NVL(AMOUNT, 0),
														0)),
								 0) AC_ORIG_RCV_NWO, --������Ϊ�½���ʱǷ������֧��δ������2��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AC_ORIG_RCV_WOO,
														NVL(AMOUNT, 0),
														0)),
								 0) AC_ORIG_RCV_WO, --������Ϊ�½���ʱǷ������֧���Ѻ�����3��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.BD_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) BD_WO_DEST_RCVO_POD, --���˳�01Ӧ�յ����˷���ǩ�ա�4��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.BD_WO_ORIG_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) BD_WO_ORIG_RCVO_POD, --���˳�01Ӧ��ʼ���˷���ǩ�ա�5��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTO_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTO_INCOME, --01��������롾6��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTO_WO_DEST_RCVO_NPOD, --01�����01����Ӧ��δǩ�ա�7��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTO_WO_DEST_RCVO_POD, --01����嵽01��Ӧ����ǩ�ա�8��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTT_WO_DEST_RCVO_NPOD, --02�����01����Ӧ��δǩ�ա�9��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTT_WO_DEST_RCVO_POD, --02�����01����Ӧ����ǩ�ա�10��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGO_COST,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGO_COST, --01������ɱ���11��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGO_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGO_INCOME, --01��������롾12��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGO_ORIG_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGO_ORIG_RCVO_NPOD, --01�����01ʼ��Ӧ��δǩ�ա�13��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGO_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGO_PAY_APPLY, --01���⸶�����롾14��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGO_WO_ORIG_RCVO_POD, --01�����01ʼ��Ӧ����ǩ�ա�15��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGO_WO_ORIG_RCVT_NPOD, --01�����02ʼ��Ӧ��δǩ�ա�16��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGO_WO_ORIG_RCVT_POD, --01�����02ʼ��Ӧ����ǩ�ա�17��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGT_ORIG_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGT_ORIG_RCVO_POD, --02�����01ʼ��Ӧ����ǩ�ա�18��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGT_WO_ORIG_RCVO_NPOD, --02�����01ʼ��Ӧ��δǩ�ա�19��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CPO_ORIG_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CPO_ORIG_PAY_APPLY, --01ʼ�����񲹾ȸ������롾20��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.EX_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) EX_DEST_RCVO_POD, --01Ӧ�յ����˷���ǩ�ա�21��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.EX_ORIG_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) EX_ORIG_RCVO_POD, --01Ӧ��ʼ���˷���ǩ�ա�22��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_BAD_WO_RCVO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_BAD_WO_RCVO, --������ʧ��01СƱӦ�ա�23��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CD_PBIO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CD_PBIO, --01СƱ������Ӫҵ�����롾24��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CD_UR_RCVO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CD_UR_RCVO, --�������г�01СƱӦ�ա�25��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CH_PBIO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CH_PBIO, --01СƱ�ֽ���Ӫҵ�����롾26��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CH_UR_RCVO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CH_UR_RCVO, --�����ֽ��01СƱӦ�ա�27��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CLAIM_PAYO_WO_RCVO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CLAIM_PAYO_WO_RCVO, --01Ӧ�������01СƱӦ�ա�28��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CLAIM_PAYO_WO_RCVTP,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CLAIM_PAYO_WO_RCVT, --01Ӧ�������02СƱӦ�ա�29��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CLAIM_PAYT_WO_RCVO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CLAIM_PAYT_WO_RCVO, --02Ӧ�������01СƱӦ�ա�30��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_COD_PAY_WO_RCVO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_COD_PAY_WO_RCVO, --Ӧ�����ջ����01СƱӦ�ա�31��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CUST_DRO_WO_RCVO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CUST_DRO_WO_RCVO, --01Ԥ�տͻ���01СƱӦ�ա�32��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CUST_DRO_WO_RCVTD,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CUST_DRO_WO_RCVT, --01Ԥ�տͻ���02СƱӦ�ա�33��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CUST_DRT_WO_RCVO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CUST_DRT_WO_RCVO, --02Ԥ�տͻ���01СƱӦ�ա�34��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_EX_WO_RCVO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_EX_WO_RCVO, --�쳣�������01СƱӦ�ա�35��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_RCVO_PBI,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_RCVO_PBI, --01СƱӦ����Ӫҵ�����롾36��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_DEST_RCVO_POD, --01���˷ѳ�01����Ӧ����ǩ�ա�37��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_INCOME, --01���˷ѳ����롾38��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_WO_DEST_RCVO_NPOD, --01���˷ѳ�01����Ӧ��δǩ�ա�39��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTT_WO_DEST_RCVO_NPOD, --02���˷ѳ�01����Ӧ��δǩ�ա�40��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTT_WO_DEST_RCVO_POD, --02���˷ѳ�01����Ӧ����ǩ�ա�41��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGO_COST,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGO_COST, --01���˷���ɱ���42��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGO_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGO_INCOME, --01���˷ѳ����롾43��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGO_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGO_PAY_APPLY, --01���˷Ѹ������롾44��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGO_WO_ORIG_RCVO_NPOD, --01���˷ѳ�01ʼ��Ӧ��δǩ�ա�45��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGO_WO_ORIG_RCVO_POD, --01���˷ѳ�01ʼ��Ӧ����ǩ�ա�46��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGO_WO_ORIG_RCVT_NPOD, --01���˷ѳ�02ʼ��Ӧ��δǩ�ա�47��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGO_WO_ORIG_RCVT_POD, --01���˷ѳ�02ʼ��Ӧ����ǩ�ա�48��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGT_WO_ORIG_RCVO_NPOD, --02���˷ѳ�01ʼ��Ӧ��δǩ�ա�49��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGT_WO_ORIG_RCVO_POD, --02���˷ѳ�01ʼ��Ӧ����ǩ�ա�50��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.SFO_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) SFO_PAY_APPLY --01װж�Ѹ������롾51��
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
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NRFOSO',
																				'ƾ֤����01����ʼ����������쳣:' || SQLERRM);
		
			--����ʧ�ܱ�־
			RETURN FALSE;
		
	END FUNC_VOUCHER_NRFOSO;

	-----------------------------------------------------------------
	-- 02��ͨʼ���±������
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NRFONT(P_PERIOD     VARCHAR2,
															 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
															 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
															 ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
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
			 ALPWR_WO_DEST_RCVT_NPOD, --��1��
			 ALPWR_WO_DEST_RCVT_POD, --��2��
			 AL_DR_WO_DEST_RCVT_NPOD, --��3��
			 AL_DR_WO_DEST_RCVT_POD, --��4��
			 COD_PAY_WO_DEST_RCVT_NPOD, --��5��
			 COD_PAY_WO_DEST_RCVT_POD, --��6��
			 COD_PAY_WO_ORIG_RCVT_NPOD, --��7��
			 COD_PAY_WO_ORIG_RCVT_POD, --��8��
			 CUST_DRO_PAY_APPLY, --��9��
			 CUST_DRO_WO_DEST_RCVT_NPOD, --��10��
			 CUST_DRO_WO_DEST_RCVT_POD, --��11��
			 CUST_DRO_WO_ORIG_RCVT_NPOD, --��12��
			 CUST_DRO_WO_ORIG_RCVT_POD, --��13��
			 CUST_DRT_PAY_APPLY, --��14��
			 CUST_DRT_WO_DEST_RCVT_NPOD, --��15��
			 CUST_DRT_WO_DEST_RCVT_POD, --��16��
			 CUST_DRT_WO_ORIG_RCVO_NPOD, --��17��
			 CUST_DRT_WO_ORIG_RCVO_POD, --��18��
			 CUST_DRT_WO_ORIG_RCVT_NPOD, --��19��
			 CUST_DRT_WO_ORIG_RCVT_POD, --��20��
			 CUST_DR_OCD, --��21��
			 CUST_DR_OCH, --��22��
			 CUST_DR_TCD, --��23��
			 CUST_DR_TCH, --��24��
			 DEO_CD, --��25��
			 DEO_CH, --��26��
			 DET_CD, --��27��
			 DET_CH, --��28��
			 OTH_PAY_WO_DEST_RCVT_NPOD, --��29��
			 OTH_PAY_WO_DEST_RCVT_POD, --��30��
			 PL_COST_WO_DEST_RCVT_NPOD, --��31��
			 PL_COST_WO_DEST_RCVT_POD, --��32��
			 PL_DR_WO_DEST_RCVT_NPOD, --��33��
			 PL_DR_WO_DEST_RCVT_POD, --��34��
			 PODT_CASH_COLLECTED_COD, --��35��
			 PODT_CASH_COLLECTED_DEL, --��36��
			 PODT_CASH_COLLECTED_DV, --��37��
			 PODT_CASH_COLLECTED_FRT, --��38��
			 PODT_CASH_COLLECTED_OT, --��39��
			 PODT_CASH_COLLECTED_PKG, --��40��
			 PODT_CASH_COLLECTED_PUP, --��41��
			 PODT_DEST_RCV_NWO_COD, --��42��
			 PODT_DEST_RCV_NWO_DEL, --��43��
			 PODT_DEST_RCV_NWO_DV, --��44��
			 PODT_DEST_RCV_NWO_FRT, --��45��
			 PODT_DEST_RCV_NWO_OT, --��46��
			 PODT_DEST_RCV_NWO_PKG, --��47��
			 PODT_DEST_RCV_NWO_PUP, --��48��
			 PODT_DEST_RCV_WO_COD, --��49��
			 PODT_DEST_RCV_WO_DEL, --��50��
			 PODT_DEST_RCV_WO_DV, --��51��
			 PODT_DEST_RCV_WO_FRT, --��52��
			 PODT_DEST_RCV_WO_OT, --��53��
			 PODT_DEST_RCV_WO_PKG, --��54��
			 PODT_DEST_RCV_WO_PUP, --��55��
			 PODT_ORIG_RCV_NWO_COD, --��56��
			 PODT_ORIG_RCV_NWO_DEL, --��57��
			 PODT_ORIG_RCV_NWO_DV, --��58��
			 PODT_ORIG_RCV_NWO_FRT, --��59��
			 PODT_ORIG_RCV_NWO_OT, --��60��
			 PODT_ORIG_RCV_NWO_PKG, --��61��
			 PODT_ORIG_RCV_NWO_PUP, --��62��
			 PODT_ORIG_RCV_WO_COD, --��63��
			 PODT_ORIG_RCV_WO_DEL, --��64��
			 PODT_ORIG_RCV_WO_DV, --��65��
			 PODT_ORIG_RCV_WO_FRT, --��66��
			 PODT_ORIG_RCV_WO_OT, --��67��
			 PODT_ORIG_RCV_WO_PKG, --��68��
			 PODT_ORIG_RCV_WO_PUP, --��69��
			 UPDT_CASH_COLLECTED_COD, --��70��
			 UPDT_CASH_COLLECTED_DEL, --��71��
			 UPDT_CASH_COLLECTED_DV, --��72��
			 UPDT_CASH_COLLECTED_FRT, --��73��
			 UPDT_CASH_COLLECTED_OT, --��74��
			 UPDT_CASH_COLLECTED_PKG, --��75��
			 UPDT_CASH_COLLECTED_PUP, --��76��
			 UPDT_DEST_RCV_NWO_COD, --��77��
			 UPDT_DEST_RCV_NWO_DEL, --��78��
			 UPDT_DEST_RCV_NWO_DV, --��79��
			 UPDT_DEST_RCV_NWO_FRT, --��80��
			 UPDT_DEST_RCV_NWO_OT, --��81��
			 UPDT_DEST_RCV_NWO_PKG, --��82��
			 UPDT_DEST_RCV_NWO_PUP, --��83��
			 UPDT_DEST_RCV_WO_COD, --��84��
			 UPDT_DEST_RCV_WO_DEL, --��85��
			 UPDT_DEST_RCV_WO_DV, --��86��
			 UPDT_DEST_RCV_WO_FRT, --��87��
			 UPDT_DEST_RCV_WO_OT, --��88��
			 UPDT_DEST_RCV_WO_PKG, --��89��
			 UPDT_DEST_RCV_WO_PUP, --��90��
			 UPDT_ORIG_RCV_NWO_COD, --��91��
			 UPDT_ORIG_RCV_NWO_DEL, --��92��
			 UPDT_ORIG_RCV_NWO_DV, --��93��
			 UPDT_ORIG_RCV_NWO_FRT, --��94��
			 UPDT_ORIG_RCV_NWO_OT, --��95��
			 UPDT_ORIG_RCV_NWO_PKG, --��96��
			 UPDT_ORIG_RCV_NWO_PUP, --��97��
			 UPDT_ORIG_RCV_WO_COD, --��98��
			 UPDT_ORIG_RCV_WO_DEL, --��99��
			 UPDT_ORIG_RCV_WO_DV, --��100��
			 UPDT_ORIG_RCV_WO_FRT, --��101��
			 UPDT_ORIG_RCV_WO_OT, --��102��
			 UPDT_ORIG_RCV_WO_PKG, --��103��
			 UPDT_ORIG_RCV_WO_PUP, --��104��
			 URO_ORIG_CD_NPOD, --��105��
			 URO_ORIG_CD_POD, --��106��
			 URO_ORIG_CH_NPOD, --��107��
			 URO_ORIG_CH_POD, --��108��
			 URT_DEST_CD_NPOD, --��109��
			 URT_DEST_CD_POD, --��110��
			 URT_DEST_CH_NPOD, --��111��
			 URT_DEST_CH_POD, --��112��
			 URT_ORIG_CD_NPOD, --��113��
			 URT_ORIG_CD_POD, --��114��
			 URT_ORIG_CH_NPOD, --��115��
			 URT_ORIG_CH_POD, --��116��
			 POP_WO_DRT_POD, --��117��
			 POP_WO_DRT_NPOD --��118��
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
								 0) ALPWR_WO_DEST_RCVT_NPOD, --Ӧ���������/��������ɱ���02Ӧ�յ����˷�δǩ�ա�1��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.ALPWR_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) ALPWR_WO_DEST_RCVT_POD, --Ӧ���������/��������ɱ���02Ӧ�յ����˷���ǩ�ա�2��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AL_DR_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) AL_DR_WO_DEST_RCVT_NPOD, --Ԥ�տ���/���������02Ӧ�յ����˷�δǩ�ա�3��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AL_DR_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) AL_DR_WO_DEST_RCVT_POD, --Ԥ�տ���/���������02Ӧ�յ����˷���ǩ�ա�4��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) COD_PAY_WO_DEST_RCVT_NPOD, --Ӧ�����ջ����02Ӧ�յ����˷�δǩ�ա�5��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) COD_PAY_WO_DEST_RCVT_POD, --Ӧ�����ջ����02Ӧ�յ����˷���ǩ�ա�6��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) COD_PAY_WO_ORIG_RCVT_NPOD, --Ӧ�����ջ����02Ӧ��ʼ���˷�δǩ�ա�7��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) COD_PAY_WO_ORIG_RCVT_POD, --Ӧ�����ջ����02Ӧ��ʼ���˷���ǩ�ա�8��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_PAY_APPLY, --01ʼ����Ԥ�ո������롾9��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_DEST_RCVT_NPOD, --01Ԥ�տͻ���02Ӧ�յ����˷�δǩ�ա�10��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_DEST_RCVT_POD, --01Ԥ�տͻ���02Ӧ�յ����˷���ǩ�ա�11��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_ORIG_RCVT_NPOD, --01Ԥ�տͻ���02Ӧ��ʼ���˷�δǩ�ա�12��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_ORIG_RCVT_POD, --01Ԥ�տͻ���02Ӧ��ʼ���˷���ǩ�ա�13��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_PAY_APPLY, --02ʼ����Ԥ�ո������롾14��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_DEST_RCVT_NPOD, --02Ԥ�տͻ���02Ӧ�յ����˷�δǩ�ա�15��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_DEST_RCVT_POD, --02Ԥ�տͻ���02Ӧ�յ����˷���ǩ�ա�16��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_NPODD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_ORIG_RCVO_NPOD, --02Ԥ�տͻ���01Ӧ��ʼ���˷�δǩ�ա�17��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_PODD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_ORIG_RCVO_POD, --02Ԥ�տͻ���01Ӧ��ʼ���˷���ǩ�ա�18��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_ORIG_RCVT_NPOD, --02Ԥ�տͻ���02Ӧ��ʼ���˷�δǩ�ա�19��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_ORIG_RCVT_POD, --02Ԥ�տͻ���02Ӧ��ʼ���˷���ǩ�ա�20��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DR_OCD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DR_OCD, --Ԥ�տͻ����С�21��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DR_OCH,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DR_OCH, --Ԥ�տͻ��ֽ�22��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DR_TCD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DR_TCD, --Ԥ�տͻ����С�23��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DR_TCH,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DR_TCH, --Ԥ�տͻ��ֽ�24��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.DEO_CD,
														NVL(AMOUNT, 0),
														0)),
								 0) DEO_CD, --�������п���25��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.DEO_CH,
														NVL(AMOUNT, 0),
														0)),
								 0) DEO_CH, --�����ֽ�26��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.DET_CD,
														NVL(AMOUNT, 0),
														0)),
								 0) DET_CD, --�������п���27��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.DET_CH,
														NVL(AMOUNT, 0),
														0)),
								 0) DET_CH, --�����ֽ�28��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OTH_PAY_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) OTH_PAY_WO_DEST_RCVT_NPOD, --����Ӧ����02Ӧ�յ����˷�δǩ�ա�29��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OTH_PAY_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) OTH_PAY_WO_DEST_RCVT_POD, --����Ӧ����02Ӧ�յ����˷���ǩ�ա�30��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_WO_DEST_RCVT_NPOD, --Ӧ��ƫ�ߴ���ɱ���02Ӧ�յ����˷�δǩ�ա�31��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_WO_DEST_RCVT_POD, --Ӧ��ƫ�ߴ���ɱ���02Ӧ�յ����˷���ǩ�ա�32��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_DR_WO_DEST_RCVT_NPOD, --Ԥ��ƫ�ߴ����02Ӧ�յ����˷�δǩ�ա�33��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_DR_WO_DEST_RCVT_POD, --Ԥ��ƫ�ߴ����02Ӧ�յ����˷���ǩ�ա�34��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_CASH_COLLECTED,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) PODT_CASH_COLLECTED_COD, --ǩ��ʱ�ָ����տ���_���ջ��������ѡ�35��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_CASH_COLLECTED,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) PODT_CASH_COLLECTED_DEL, --ǩ��ʱ�ָ����տ���_�ͻ��ѡ�36��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_CASH_COLLECTED,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) PODT_CASH_COLLECTED_DV, --ǩ��ʱ�ָ����տ���_���۷ѡ�37��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_CASH_COLLECTED,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) PODT_CASH_COLLECTED_FRT, --ǩ��ʱ�ָ����տ���_�������˷ѡ�38��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_CASH_COLLECTED,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) PODT_CASH_COLLECTED_OT, --ǩ��ʱ�ָ����տ���_�������á�39��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_CASH_COLLECTED,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) PODT_CASH_COLLECTED_PKG, --ǩ��ʱ�ָ����տ���_��װ�ѡ�40��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_CASH_COLLECTED,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) PODT_CASH_COLLECTED_PUP, --ǩ��ʱ�ָ����տ���_�ӻ��ѡ�41��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_NWO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) PODT_DEST_RCV_NWO_COD, --ǩ��ʱ����Ӧ��δ�������_���ջ��������ѡ�42��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_NWO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) PODT_DEST_RCV_NWO_DEL, --ǩ��ʱ����Ӧ��δ�������_�ͻ��ѡ�43��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_NWO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) PODT_DEST_RCV_NWO_DV, --ǩ��ʱ����Ӧ��δ�������_���۷ѡ�44��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_NWO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) PODT_DEST_RCV_NWO_FRT, --ǩ��ʱ����Ӧ��δ�������_�������˷ѡ�45��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_NWO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) PODT_DEST_RCV_NWO_OT, --ǩ��ʱ����Ӧ��δ�������_�������á�46��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_NWO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) PODT_DEST_RCV_NWO_PKG, --ǩ��ʱ����Ӧ��δ�������_��װ�ѡ�47��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_NWO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) PODT_DEST_RCV_NWO_PUP, --ǩ��ʱ����Ӧ��δ�������_�ӻ��ѡ�48��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_WO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) PODT_DEST_RCV_WO_COD, --ǩ��ʱ����Ӧ���Ѻ������_���ջ��������ѡ�49��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_WO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) PODT_DEST_RCV_WO_DEL, --ǩ��ʱ����Ӧ���Ѻ������_�ͻ��ѡ�50��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_WO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) PODT_DEST_RCV_WO_DV, --ǩ��ʱ����Ӧ���Ѻ������_���۷ѡ�51��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_WO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) PODT_DEST_RCV_WO_FRT, --ǩ��ʱ����Ӧ���Ѻ������_�������˷ѡ�52��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_WO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) PODT_DEST_RCV_WO_OT, --ǩ��ʱ����Ӧ���Ѻ������_�������á�53��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_WO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) PODT_DEST_RCV_WO_PKG, --ǩ��ʱ����Ӧ���Ѻ������_��װ�ѡ�54��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_DEST_RCV_WO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) PODT_DEST_RCV_WO_PUP, --ǩ��ʱ����Ӧ���Ѻ������_�ӻ��ѡ�55��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_NWO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) PODT_ORIG_RCV_NWO_COD, --ǩ��ʱʼ��Ӧ��δ�������_���ջ��������ѡ�56��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_NWO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) PODT_ORIG_RCV_NWO_DEL, --ǩ��ʱʼ��Ӧ��δ�������_�ͻ��ѡ�57��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_NWO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) PODT_ORIG_RCV_NWO_DV, --ǩ��ʱʼ��Ӧ��δ�������_���۷ѡ�58��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_NWO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) PODT_ORIG_RCV_NWO_FRT, --ǩ��ʱʼ��Ӧ��δ�������_�������˷ѡ�59��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_NWO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) PODT_ORIG_RCV_NWO_OT, --ǩ��ʱʼ��Ӧ��δ�������_�������á�60��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_NWO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) PODT_ORIG_RCV_NWO_PKG, --ǩ��ʱʼ��Ӧ��δ�������_��װ�ѡ�61��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_NWO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) PODT_ORIG_RCV_NWO_PUP, --ǩ��ʱʼ��Ӧ��δ�������_�ӻ��ѡ�62��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_WO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) PODT_ORIG_RCV_WO_COD, --ǩ��ʱʼ��Ӧ���Ѻ������_���ջ��������ѡ�63��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_WO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) PODT_ORIG_RCV_WO_DEL, --ǩ��ʱʼ��Ӧ���Ѻ������_�ͻ��ѡ�64��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_WO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) PODT_ORIG_RCV_WO_DV, --ǩ��ʱʼ��Ӧ���Ѻ������_���۷ѡ�65��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_WO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) PODT_ORIG_RCV_WO_FRT, --ǩ��ʱʼ��Ӧ���Ѻ������_�������˷ѡ�66��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_WO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) PODT_ORIG_RCV_WO_OT, --ǩ��ʱʼ��Ӧ���Ѻ������_�������á�67��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_WO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) PODT_ORIG_RCV_WO_PKG, --ǩ��ʱʼ��Ӧ���Ѻ������_��װ�ѡ�68��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PODT_ORIG_RCV_WO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) PODT_ORIG_RCV_WO_PUP, --ǩ��ʱʼ��Ӧ���Ѻ������_�ӻ��ѡ�69��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_CASH_COLLECTED,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) UPDT_CASH_COLLECTED_COD, --��ǩ��ʱ�ָ����տ���_���ջ��������ѡ�70��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_CASH_COLLECTED,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) UPDT_CASH_COLLECTED_DEL, --��ǩ��ʱ�ָ����տ���_�ͻ��ѡ�71��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_CASH_COLLECTED,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) UPDT_CASH_COLLECTED_DV, --��ǩ��ʱ�ָ����տ���_���۷ѡ�72��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_CASH_COLLECTED,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) UPDT_CASH_COLLECTED_FRT, --��ǩ��ʱ�ָ����տ���_�������˷ѡ�73��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_CASH_COLLECTED,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) UPDT_CASH_COLLECTED_OT, --��ǩ��ʱ�ָ����տ���_�������á�74��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_CASH_COLLECTED,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) UPDT_CASH_COLLECTED_PKG, --��ǩ��ʱ�ָ����տ���_��װ�ѡ�75��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_CASH_COLLECTED,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) UPDT_CASH_COLLECTED_PUP, --��ǩ��ʱ�ָ����տ���_�ӻ��ѡ�76��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_NWO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) UPDT_DEST_RCV_NWO_COD, --��ǩ��ʱ����Ӧ��δ�������_���ջ��������ѡ�77��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_NWO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) UPDT_DEST_RCV_NWO_DEL, --��ǩ��ʱ����Ӧ��δ�������_�ͻ��ѡ�78��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_NWO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) UPDT_DEST_RCV_NWO_DV, --��ǩ��ʱ����Ӧ��δ�������_���۷ѡ�79��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_NWO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) UPDT_DEST_RCV_NWO_FRT, --��ǩ��ʱ����Ӧ��δ�������_�������˷ѡ�80��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_NWO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) UPDT_DEST_RCV_NWO_OT, --��ǩ��ʱ����Ӧ��δ�������_�������á�81��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_NWO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) UPDT_DEST_RCV_NWO_PKG, --��ǩ��ʱ����Ӧ��δ�������_��װ�ѡ�82��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_NWO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) UPDT_DEST_RCV_NWO_PUP, --��ǩ��ʱ����Ӧ��δ�������_�ӻ��ѡ�83��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_WO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) UPDT_DEST_RCV_WO_COD, --��ǩ��ʱ����Ӧ���Ѻ������_���ջ��������ѡ�84��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_WO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) UPDT_DEST_RCV_WO_DEL, --��ǩ��ʱ����Ӧ���Ѻ������_�ͻ��ѡ�85��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_WO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) UPDT_DEST_RCV_WO_DV, --��ǩ��ʱ����Ӧ���Ѻ������_���۷ѡ�86��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_WO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) UPDT_DEST_RCV_WO_FRT, --��ǩ��ʱ����Ӧ���Ѻ������_�������˷ѡ�87��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_WO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) UPDT_DEST_RCV_WO_OT, --��ǩ��ʱ����Ӧ���Ѻ������_�������á�88��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_WO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) UPDT_DEST_RCV_WO_PKG, --��ǩ��ʱ����Ӧ���Ѻ������_��װ�ѡ�89��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_DEST_RCV_WO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) UPDT_DEST_RCV_WO_PUP, --��ǩ��ʱ����Ӧ���Ѻ������_�ӻ��ѡ�90��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_NWO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) UPDT_ORIG_RCV_NWO_COD, --��ǩ��ʱʼ��Ӧ��δ�������_���ջ��������ѡ�91��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_NWO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) UPDT_ORIG_RCV_NWO_DEL, --��ǩ��ʱʼ��Ӧ��δ�������_�ͻ��ѡ�92��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_NWO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) UPDT_ORIG_RCV_NWO_DV, --��ǩ��ʱʼ��Ӧ��δ�������_���۷ѡ�93��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_NWO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) UPDT_ORIG_RCV_NWO_FRT, --��ǩ��ʱʼ��Ӧ��δ�������_�������˷ѡ�94��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_NWO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) UPDT_ORIG_RCV_NWO_OT, --��ǩ��ʱʼ��Ӧ��δ�������_�������á�95��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_NWO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) UPDT_ORIG_RCV_NWO_PKG, --��ǩ��ʱʼ��Ӧ��δ�������_��װ�ѡ�96��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_NWO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) UPDT_ORIG_RCV_NWO_PUP, --��ǩ��ʱʼ��Ӧ��δ�������_�ӻ��ѡ�97��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_WO,
														NVL(AMOUNT_COD, 0),
														0)),
								 0) UPDT_ORIG_RCV_WO_COD, --��ǩ��ʱʼ��Ӧ���Ѻ������_���ջ��������ѡ�98��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_WO,
														NVL(AMOUNT_DEL, 0),
														0)),
								 0) UPDT_ORIG_RCV_WO_DEL, --��ǩ��ʱʼ��Ӧ���Ѻ������_�ͻ��ѡ�99��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_WO,
														NVL(AMOUNT_DV, 0),
														0)),
								 0) UPDT_ORIG_RCV_WO_DV, --��ǩ��ʱʼ��Ӧ���Ѻ������_���۷ѡ�100��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_WO,
														NVL(AMOUNT_FRT, 0),
														0)),
								 0) UPDT_ORIG_RCV_WO_FRT, --��ǩ��ʱʼ��Ӧ���Ѻ������_�������˷ѡ�101��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_WO,
														NVL(AMOUNT_OT, 0),
														0)),
								 0) UPDT_ORIG_RCV_WO_OT, --��ǩ��ʱʼ��Ӧ���Ѻ������_�������á�102��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_WO,
														NVL(AMOUNT_PKG, 0),
														0)),
								 0) UPDT_ORIG_RCV_WO_PKG, --��ǩ��ʱʼ��Ӧ���Ѻ������_��װ�ѡ�103��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UPDT_ORIG_RCV_WO,
														NVL(AMOUNT_PUP, 0),
														0)),
								 0) UPDT_ORIG_RCV_WO_PUP, --��ǩ��ʱʼ��Ӧ���Ѻ������_�ӻ��ѡ�104��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_ORIG_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_ORIG_CD_NPOD, --��������δǩ�ա�105��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_ORIG_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_ORIG_CD_POD, --����������ǩ�ա�106��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_ORIG_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_ORIG_CH_NPOD, --�����ֽ�δǩ�ա�107��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_ORIG_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_ORIG_CH_POD, --�����ֽ���ǩ�ա�108��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URT_DEST_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URT_DEST_CD_NPOD, --��������δǩ�ա�109��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URT_DEST_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URT_DEST_CD_POD, --����������ǩ�ա�110��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URT_DEST_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URT_DEST_CH_NPOD, --�����ֽ�δǩ�ա�111��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URT_DEST_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URT_DEST_CH_POD, --�����ֽ���ǩ�ա�112��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URT_ORIG_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URT_ORIG_CD_NPOD, --��������δǩ�ա�113��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URT_ORIG_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URT_ORIG_CD_POD, --����������ǩ�ա�114��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URT_ORIG_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URT_ORIG_CH_NPOD, --�����ֽ�δǩ�ա�115��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URT_ORIG_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URT_ORIG_CH_POD, --�����ֽ���ǩ�ա�116��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.POP_WO_DRT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) POP_WO_DRT_POD, --�����ֽ���ǩ�ա�117��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.POP_WO_DRT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) POP_WO_DRT_NPOD --�����ֽ���ǩ�ա�118��
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
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NRFONT',
																				'ƾ֤����02��ͨʼ����������쳣:' || SQLERRM);
		
			--����ʧ�ܱ�־
			RETURN FALSE;
		
	END FUNC_VOUCHER_NRFONT;

	-----------------------------------------------------------------
	-- 02����ʼ���±������
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NRFOST(P_PERIOD     VARCHAR2,
															 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
															 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
															 ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
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
			 AC_CASH, --��1��
			 AC_ORIG_RCV_NWO, --��2��
			 AC_ORIG_RCV_WO, --��3��
			 BD_WO_DEST_RCVT_POD, --��4��
			 BD_WO_ORIG_RCVT_POD, --��5��
			 CLAIM_DESTO_WO_DEST_RCVT_NPOD, --��6��
			 CLAIM_DESTO_WO_DEST_RCVT_POD, --��7��
			 CLAIM_DESTT_INCOME, --��8��
			 CLAIM_DESTT_WO_DEST_RCVT_NPOD, --��9��
			 CLAIM_DESTT_WO_DEST_RCVT_POD, --��10��
			 CLAIM_ORIGO_PAY_APPLY, --��11��
			 CLAIM_ORIGO_WO_ORIG_RCVT_NPOD, --��12��
			 CLAIM_ORIGO_WO_ORIG_RCVT_POD, --��13��
			 CLAIM_ORIGT_COST, --��14��
			 CLAIM_ORIGT_INCOME, --��15��
			 CLAIM_ORIGT_ORIG_RCVO_POD, --��16��
			 CLAIM_ORIGT_PAY_APPLY, --��17��
			 CLAIM_ORIGT_WO_ORIG_RCVO_NPOD, --��18��
			 CLAIM_ORIGT_WO_ORIG_RCVT_NPOD, --��19��
			 CLAIM_ORIGT_WO_ORIG_RCVT_POD, --��20��
			 CPO_ORIG_PAY_APPLY, --��21��
			 CPT_ORIG_PAY_APPLY, --��22��
			 EX_DEST_RCVT_POD, --��23��
			 EX_ORIG_RCVT_POD, --��24��
			 OR_BAD_WO_RCVT, --��25��
			 OR_CD_AC, --��26��
			 OR_CD_BANK_INT, --��27��
			 OR_CD_OPAY, --��28��
			 OR_CD_OTHER, --��29��
			 OR_CD_PBIO, --��30��
			 OR_CD_PBIT, --��31��
			 OR_CD_UR_RCVO, --��32��
			 OR_CD_UR_RCVT, --��33��
			 OR_CH_AC, --��34��
			 OR_CH_OPAY, --��35��
			 OR_CH_OTHER, --��36��
			 OR_CH_PBIO, --��37��
			 OR_CH_PBIT, --��38��
			 OR_CH_SI, --��39��
			 OR_CH_UR_RCVO, --��40��
			 OR_CH_UR_RCVT, --��41��
			 OR_CLAIM_PAYO_WO_RCVT, --��42��
			 OR_CLAIM_PAYT_WO_RCVO, --��43��
			 OR_CLAIM_PAYT_WO_RCVT, --��44��
			 OR_COD_PAY_WO_RCVT, --��45��
			 OR_CUST_DRO_WO_RCVT, --��46��
			 OR_CUST_DRT_WO_RCVO, --��47��
			 OR_CUST_DRT_WO_RCVT, --��48��
			 OR_EX_WO_RCVT, --��49��
			 OR_RCVT_PBI, --��50��
			 RD_DESTO_DEST_RCVT_POD, --��51��
			 RD_DESTO_WO_DEST_RCVT_NPOD, --��52��
			 RD_DESTT_INCOME, --��53��
			 RD_DESTT_WO_DEST_RCVT_NPOD, --��54��
			 RD_DESTT_WO_DEST_RCVT_POD, --��55��
			 RD_ORIGO_PAY_APPLY, --��56��
			 RD_ORIGO_WO_ORIG_RCVT_NPOD, --��57��
			 RD_ORIGO_WO_ORIG_RCVT_POD, --��58��
			 RD_ORIGT_COST, --��59��
			 RD_ORIGT_INCOME, --��60��
			 RD_ORIGT_PAY_APPLY, --��61��
			 RD_ORIGT_WO_ORIG_RCVO_NPOD, --��62��
			 RD_ORIGT_WO_ORIG_RCVO_POD, --��63��
			 RD_ORIGT_WO_ORIG_RCVT_NPOD, --��64��
			 RD_ORIGT_WO_ORIG_RCVT_POD, --��65��
			 SFO_PAY_APPLY, --��66��
			 SFT_PAY_APPLY, --��67��
			 OR_CH_RENT_INCOME, --��68��
			 OR_CH_BANK_INT, --��69��
			 OR_CD_RENT_INCOME --��70��
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
								 0) AC_CASH, --������Ϊ�ֽ����п���1��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AC_ORIG_RCV_NWOT,
														NVL(AMOUNT, 0),
														0)),
								 0) AC_ORIG_RCV_NWO, --������Ϊ�½���ʱǷ������֧��δ������2��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AC_ORIG_RCV_WOT,
														NVL(AMOUNT, 0),
														0)),
								 0) AC_ORIG_RCV_WO, --������Ϊ�½���ʱǷ������֧���Ѻ�����3��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.BD_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) BD_WO_DEST_RCVT_POD, --���˳�02Ӧ�յ����˷���ǩ�ա�4��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.BD_WO_ORIG_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) BD_WO_ORIG_RCVT_POD, --���˳�02Ӧ��ʼ���˷���ǩ�ա�5��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTO_WO_DEST_RCVT_NPOD, --01�����02����Ӧ��δǩ�ա�6��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTO_WO_DEST_RCVT_POD, --01�����02����Ӧ����ǩ�ա�7��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTT_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTT_INCOME, --02��������롾8��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTT_WO_DEST_RCVT_NPOD, --02�����02����Ӧ��δǩ�ա�9��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTT_WO_DEST_RCVT_POD, --02�����02����Ӧ����ǩ�ա�10��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGO_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGO_PAY_APPLY, --01���⸶�����롾11��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGO_WO_ORIG_RCVT_NPOD, --01�����02ʼ��Ӧ��δǩ�ա�12��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGO_WO_ORIG_RCVT_POD, --01�����02ʼ��Ӧ����ǩ�ա�13��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGT_COST,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGT_COST, --02������ɱ���14��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGT_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGT_INCOME, --02��������롾15��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGT_ORIG_RCVO_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGT_ORIG_RCVO_POD, --02�����01ʼ��Ӧ����ǩ�ա�16��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGT_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGT_PAY_APPLY, --02���⸶�����롾17��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVO_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGT_WO_ORIG_RCVO_NPOD, --02�����01ʼ��Ӧ��δǩ�ա�18��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGT_WO_ORIG_RCVT_NPOD, --02�����02ʼ��Ӧ��δǩ�ա�19��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_ORIGT_WO_ORIG_RCVT_POD, --02�����02ʼ��Ӧ����ǩ�ա�20��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CPO_ORIG_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CPO_ORIG_PAY_APPLY, --01ʼ�����񲹾ȸ������롾21��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CPT_ORIG_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CPT_ORIG_PAY_APPLY, --02ʼ�����񲹾ȸ������롾22��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.EX_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) EX_DEST_RCVT_POD, --02Ӧ�յ����˷���ǩ�ա�23��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.EX_ORIG_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) EX_ORIG_RCVT_POD, --02Ӧ��ʼ���˷���ǩ�ա�24��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_BAD_WO_RCVT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_BAD_WO_RCVT, --������ʧ��02СƱӦ�ա�25��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CD_AC,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CD_AC, --СƱ����֮�¹���� ��26��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CD_BANK_INT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CD_BANK_INT, --СƱ����֮����Ա����Ϣ��27��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CD_OPAY,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CD_OPAY, --СƱ����֮�ͻ��ึ�˷ѻ��̵㳤���� ��28��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CD_OTHER,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CD_OTHER, --СƱ����֮������29��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CD_PBIO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CD_PBIO, --01СƱ������Ӫҵ�����롾30��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CD_PBIT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CD_PBIT, --02СƱ������Ӫҵ�����롾31��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CD_UR_RCVO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CD_UR_RCVO, --�������г�01СƱӦ�ա�32��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CD_UR_RCVT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CD_UR_RCVT, --�������г�02СƱӦ�ա�33��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CH_AC,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CH_AC, --СƱ�ֽ�֮�¹���� ��34��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CH_OPAY,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CH_OPAY, --СƱ�ֽ�֮�ͻ��ึ�˷ѻ��̵㳤���� ��35��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CH_OTHER,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CH_OTHER, --СƱ�ֽ�֮������36��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CH_PBIO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CH_PBIO, --01СƱ�ֽ���Ӫҵ�����롾37��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CH_PBIT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CH_PBIT, --02СƱ�ֽ���Ӫҵ�����롾38��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CH_SI,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CH_SI, --СƱ�ֽ�֮������Ʒ���롾39��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CH_UR_RCVO,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CH_UR_RCVO, --�����ֽ��01СƱӦ�ա�40��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CH_UR_RCVT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CH_UR_RCVT, --�����ֽ��02СƱӦ�ա�41��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CLAIM_PAYO_WO_RCVT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CLAIM_PAYO_WO_RCVT, --01Ӧ�������02СƱӦ�ա�42��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CLAIM_PAYT_WO_RCVOP,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CLAIM_PAYT_WO_RCVO, --02Ӧ�������01СƱӦ�ա�43��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CLAIM_PAYT_WO_RCVT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CLAIM_PAYT_WO_RCVT, --02Ӧ�������02СƱӦ�ա�44��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_COD_PAY_WO_RCVT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_COD_PAY_WO_RCVT, --Ӧ�����ջ����02СƱӦ�ա�45��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CUST_DRO_WO_RCVT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CUST_DRO_WO_RCVT, --01Ԥ�տͻ���02СƱӦ�ա�46��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CUST_DRT_WO_RCVOD,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CUST_DRT_WO_RCVO, --02Ԥ�տͻ���01СƱӦ�ա�47��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CUST_DRT_WO_RCVT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CUST_DRT_WO_RCVT, --02Ԥ�տͻ���02СƱӦ�ա�48��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_EX_WO_RCVT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_EX_WO_RCVT, --�쳣�������02СƱӦ�ա�49��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_RCVT_PBI,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_RCVT_PBI, --02СƱӦ����Ӫҵ�����롾50��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_DEST_RCVT_POD, --01���˷ѳ�02����Ӧ����ǩ�ա�51��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_WO_DEST_RCVT_NPOD, --01���˷ѳ�02����Ӧ��δǩ�ա�52��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTT_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTT_INCOME, --02���˷ѳ����롾53��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTT_WO_DEST_RCVT_NPOD, --02���˷ѳ�02����Ӧ��δǩ�ա�54��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTT_WO_DEST_RCVT_POD, --02���˷ѳ�02����Ӧ����ǩ�ա�55��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGO_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGO_PAY_APPLY, --01���˷Ѹ������롾56��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGO_WO_ORIG_RCVT_NPOD, --01���˷ѳ�02ʼ��Ӧ��δǩ�ա�57��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGO_WO_ORIG_RCVT_POD, --01���˷ѳ�02ʼ��Ӧ����ǩ�ա�58��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGT_COST,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGT_COST, --02���˷���ɱ���59��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGT_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGT_INCOME, --02���˷ѳ����롾60��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGT_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGT_PAY_APPLY, --02���˷Ѹ������롾61��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGT_WO_ORIG_RCVO_NPOD, --02���˷ѳ�01ʼ��Ӧ��δǩ�ա�62��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGT_WO_ORIG_RCVO_POD, --02���˷ѳ�01ʼ��Ӧ����ǩ�ա�63��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGT_WO_ORIG_RCVT_NPOD, --02���˷ѳ�02ʼ��Ӧ��δǩ�ա�64��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_ORIGT_WO_ORIG_RCVT_POD, --02���˷ѳ�02ʼ��Ӧ����ǩ�ա�65��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.SFO_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) SFO_PAY_APPLY, --01װж�Ѹ������롾66��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.SFT_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) SFT_PAY_APPLY, --02װж�Ѹ������롾67��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CH_RENT_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CH_RENT_INCOME, --СƱ�ֽ�֮����ֿ�������롾68��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CH_BANK_INT,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CH_BANK_INT, --СƱ�ֽ�֮����Ա����Ϣ��69��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_CD_RENT_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_CD_RENT_INCOME --СƱ����֮����ֿ�������롾70��
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
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NRFOST',
																				'ƾ֤����02����ʼ����������쳣:' || SQLERRM);
		
			--����ʧ�ܱ�־
			RETURN FALSE;
		
	END FUNC_VOUCHER_NRFOST;

	-----------------------------------------------------------------
	-- 01�����±������
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NRFDO(P_PERIOD     VARCHAR2,
															P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
															P_END_DATE   DATE -- ����ʱ�� 2012-12-22
															) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
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
			 CLAIM_DESTO_COST, --��1��
			 CLAIM_DESTO_INCOME, --��2��
			 CLAIM_DESTO_PAY_APPLY, --��3��
			 CLAIM_DESTO_WO_DEST_RCVO_NPOD, --��4��
			 CLAIM_DESTO_WO_DEST_RCVO_POD, --��5��
			 CLAIM_DESTO_WO_DEST_RCVT_NPOD, --��6��
			 CLAIM_DESTO_WO_DEST_RCVT_POD, --��7��
			 CPO_DEST_PAY_APPLY, --��8��
			 CUST_DRO_WO_DEST_RCVO_NPOD, --��9��
			 CUST_DRO_WO_DEST_RCVO_POD, --��10��
			 CUST_DRO_WO_DEST_RCVT_NPOD, --��11��
			 CUST_DRO_WO_DEST_RCVT_POD, --��12��
			 RD_DESTO_COST, --��13��
			 RD_DESTO_DEST_RCVO_POD, --��14��
			 RD_DESTO_DEST_RCVT_POD, --��15��
			 RD_DESTO_INCOME, --��16��
			 RD_DESTO_PAY_APPLY, --��17��
			 RD_DESTO_WO_DEST_RCVO_NPOD, --��18��
			 RD_DESTO_WO_DEST_RCVT_NPOD --��19��
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
								 0) CLAIM_DESTO_COST, --01������ɱ���1��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTO_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTO_INCOME, --01��������롾2��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTO_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTO_PAY_APPLY, --01���⸶�����롾3��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTO_WO_DEST_RCVO_NPOD, --01�����01����Ӧ��δǩ�ա�4��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTO_WO_DEST_RCVO_POD, --01����嵽01��Ӧ����ǩ�ա�5��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTO_WO_DEST_RCVT_NPOD, --01�����02����Ӧ��δǩ�ա�6��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTO_WO_DEST_RCVT_POD, --01�����02����Ӧ����ǩ�ա�7��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CPO_DEST_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CPO_DEST_PAY_APPLY, --01������񲹾ȸ������롾8��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_NPODD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_DEST_RCVO_NPOD, --01Ԥ�տͻ���01Ӧ�յ����˷�δǩ�ա�9��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_PODD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_DEST_RCVO_POD, --01Ԥ�տͻ���01Ӧ�յ����˷���ǩ�ա�10��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_NPODD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_DEST_RCVT_NPOD, --01Ԥ�տͻ���02Ӧ�յ����˷�δǩ�ա�11��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_PODD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRO_WO_DEST_RCVT_POD, --01Ԥ�տͻ���02Ӧ�յ����˷���ǩ�ա�12��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_COST,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_COST, --01���˷���ɱ���13��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVO_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_DEST_RCVO_POD, --01���˷ѳ�01����Ӧ����ǩ�ա�14��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVT_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_DEST_RCVT_POD, --01���˷ѳ�02����Ӧ����ǩ�ա�15��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_INCOME, --01���˷ѳ����롾16��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_PAY_APPLY, --01���˷Ѹ������롾17��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVO_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_WO_DEST_RCVO_NPOD, --01���˷ѳ�01����Ӧ��δǩ�ա�18��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVT_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_WO_DEST_RCVT_NPOD --01���˷ѳ�02����Ӧ��δǩ�ա�19��
				FROM STV.T_STL_NMVS T
			 WHERE T.PERIOD >= V_BEGIN_PERIOD
						 AND T.PERIOD < V_END_PERIOD
						 AND T.AMOUNT <> 0
						 AND T.PRODUCT_CODE IN (PKG_STL_COMMON.PRODUCT_CODE_FLF, --��׼����
																		PKG_STL_COMMON.PRODUCT_CODE_FSF, --��׼����
																		PKG_STL_COMMON.PRODUCT_CODE_LRF, --��׼����(��;)
																		PKG_STL_COMMON.PRODUCT_CODE_SRF, --��׼����(��;)
																		PKG_STL_COMMON.PRODUCT_CODE_WVH, --����
																		PKG_STL_COMMON.PRODUCT_CODE_PKG --���ÿ��
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
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NRFDO',
																				'ƾ֤����01�����±�������쳣:' || SQLERRM);
		
			--����ʧ�ܱ�־
			RETURN FALSE;
		
	END FUNC_VOUCHER_NRFDO;

	-----------------------------------------------------------------
	-- 02�����±������
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NRFDT(P_PERIOD     VARCHAR2,
															P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
															P_END_DATE   DATE -- ����ʱ�� 2012-12-22
															) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
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
			 CLAIM_DESTO_PAY_APPLY, --��1��
			 CLAIM_DESTT_COST, --��2��
			 CLAIM_DESTT_INCOME, --��3��
			 CLAIM_DESTT_PAY_APPLY, --��4��
			 CLAIM_DESTT_WO_DEST_RCVO_NPOD, --��5��
			 CLAIM_DESTT_WO_DEST_RCVO_POD, --��6��
			 CLAIM_DESTT_WO_DEST_RCVT_NPOD, --��7��
			 CLAIM_DESTT_WO_DEST_RCVT_POD, --��8��
			 COD_UR_CD_NPOD, --��9��
			 COD_UR_CH_NPOD, --��10��
			 CPO_DEST_PAY_APPLY, --��11��
			 CPT_DEST_PAY_APPLY, --��12��
			 CUST_DRT_WO_DEST_RCVO_NPOD, --��13��
			 CUST_DRT_WO_DEST_RCVO_POD, --��14��
			 CUST_DRT_WO_DEST_RCVT_NPOD, --��15��
			 CUST_DRT_WO_DEST_RCVT_POD, --��16��
			 RD_DESTO_PAY_APPLY, --��17��
			 RD_DESTT_COST, --��18��
			 RD_DESTT_INCOME, --��19��
			 RD_DESTT_PAY_APPLY, --��20��
			 RD_DESTT_WO_DEST_RCVO_NPOD, --��21��
			 RD_DESTT_WO_DEST_RCVO_POD, --��22��
			 RD_DESTT_WO_DEST_RCVT_NPOD, --��23��
			 RD_DESTT_WO_DEST_RCVT_POD, --��24��
			 URO_DEST_CD_NPOD, --��25��
			 URO_DEST_CD_POD, --��26��
			 URO_DEST_CH_NPOD, --��27��
			 URO_DEST_CH_POD, --��28��
			 URT_DEST_CD_NPOD, --��29��
			 URT_DEST_CD_POD, --��30��
			 URT_DEST_CH_NPOD, --��31��
			 URT_DEST_CH_POD --��32��
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
								 0) CLAIM_DESTO_PAY_APPLY, --01���⸶�����롾1��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTT_COST,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTT_COST, --02������ɱ���2��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTT_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTT_INCOME, --02��������롾3��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTT_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTT_PAY_APPLY, --02���⸶�����롾4��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTT_WO_DEST_RCVO_NPOD, --02�����01����Ӧ��δǩ�ա�5��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTT_WO_DEST_RCVO_POD, --02�����01����Ӧ����ǩ�ա�6��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTT_WO_DEST_RCVT_NPOD, --02�����02����Ӧ��δǩ�ա�7��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_DESTT_WO_DEST_RCVT_POD, --02�����02����Ӧ����ǩ�ա�8��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.COD_UR_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) COD_UR_CD_NPOD, --������ջ�������δǩ�ա�9��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.COD_UR_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) COD_UR_CH_NPOD, --������ջ����ֽ�δǩ�ա�10��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CPO_DEST_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CPO_DEST_PAY_APPLY, --01������񲹾ȸ������롾11��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CPT_DEST_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) CPT_DEST_PAY_APPLY, --02������񲹾ȸ������롾12��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_NPODD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_DEST_RCVO_NPOD, --02Ԥ�տͻ���01Ӧ�յ����˷�δǩ�ա�13��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_PODD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_DEST_RCVO_POD, --02Ԥ�տͻ���01Ӧ�յ����˷���ǩ�ա�14��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_NPODD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_DEST_RCVT_NPOD, --02Ԥ�տͻ���02Ӧ�յ����˷�δǩ�ա�15��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_PODD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DRT_WO_DEST_RCVT_POD, --02Ԥ�տͻ���02Ӧ�յ����˷���ǩ�ա�16��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTO_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTO_PAY_APPLY, --01���˷Ѹ������롾17��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTT_COST,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTT_COST, --02���˷���ɱ���18��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTT_INCOME,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTT_INCOME, --02���˷ѳ����롾19��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTT_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTT_PAY_APPLY, --02���˷Ѹ������롾20��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTT_WO_DEST_RCVO_NPOD, --02���˷ѳ�01����Ӧ��δǩ�ա�21��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTT_WO_DEST_RCVO_POD, --02���˷ѳ�01����Ӧ����ǩ�ա�22��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTT_WO_DEST_RCVT_NPOD, --02���˷ѳ�02����Ӧ��δǩ�ա�23��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) RD_DESTT_WO_DEST_RCVT_POD, --02���˷ѳ�02����Ӧ����ǩ�ա�24��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_DEST_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_DEST_CD_NPOD, --��������δǩ�ա�25��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_DEST_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_DEST_CD_POD, --����������ǩ�ա�26��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_DEST_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_DEST_CH_NPOD, --�����ֽ�δǩ�ա�27��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URO_DEST_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URO_DEST_CH_POD, --�����ֽ���ǩ�ա�28��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URT_DEST_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URT_DEST_CD_NPOD, --��������δǩ�ա�29��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URT_DEST_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URT_DEST_CD_POD, --����������ǩ�ա�30��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URT_DEST_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URT_DEST_CH_NPOD, --�����ֽ�δǩ�ա�31��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URT_DEST_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URT_DEST_CH_POD --�����ֽ���ǩ�ա�32��
				FROM STV.T_STL_NMVS T
			 WHERE T.PERIOD >= V_BEGIN_PERIOD
						 AND T.PERIOD < V_END_PERIOD
						 AND T.AMOUNT <> 0
						 AND T.PRODUCT_CODE IN (PKG_STL_COMMON.PRODUCT_CODE_FLF, --��׼����
																		PKG_STL_COMMON.PRODUCT_CODE_FSF, --��׼����
																		PKG_STL_COMMON.PRODUCT_CODE_LRF, --��׼����(��;)
																		PKG_STL_COMMON.PRODUCT_CODE_SRF, --��׼����(��;)
																		PKG_STL_COMMON.PRODUCT_CODE_WVH, --����
																		PKG_STL_COMMON.PRODUCT_CODE_PKG --���ÿ��
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
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NRFDT',
																				'ƾ֤����02�����±�������쳣:' || SQLERRM);
		
			--����ʧ�ܱ�־
			RETURN FALSE;
		
	END FUNC_VOUCHER_NRFDT;

	-----------------------------------------------------------------
	-- ƫ���±������
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NPLR(P_PERIOD     VARCHAR2,
														 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
														 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
														 ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
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
			 PL_COST_CONFIRM, --��1��
			 PL_COST_ENTRY, --��2��
			 PL_COST_NCONFIRM, --��3��
			 PL_COST_PAY_APPLY, --��4��
			 PL_COST_WO_DEST_RCVO_NPOD, --��5��
			 PL_COST_WO_DEST_RCVO_POD, --��6��
			 PL_COST_WO_DEST_RCVT_NPOD, --��7��
			 PL_COST_WO_DEST_RCVT_POD, --��8��
			 PL_DR_CD, --��9��
			 PL_DR_CH, --��10��
			 PL_DR_PAY_APPLY, --��11��
			 PL_DR_WO_DEST_RCVO_NPOD, --��12��
			 PL_DR_WO_DEST_RCVO_POD, --��13��
			 PL_DR_WO_DEST_RCVT_NPOD, --��14��
			 PL_DR_WO_DEST_RCVT_POD, --��15��
			 UROP_DEST_CD_NPOD, --��16��
			 UROP_DEST_CD_POD, --��17��
			 UROP_DEST_CH_NPOD, --��18��
			 UROP_DEST_CH_POD, --��19��
			 URTP_DEST_CD_NPOD, --��20��
			 URTP_DEST_CD_POD, --��21��
			 URTP_DEST_CH_NPOD, --��22��
			 URTP_DEST_CH_POD, --��23��
			 PL_COST_OP_CRM, --��24��
			 PL_DR_WO_OR, --��25��
			 POR_ENTRY, --��26��
			 UR_POR_CH, --��27��
			 UR_POR_CD, --��28��
			 POP_WO_DRO_POD, --��29��
			 POP_WO_DRO_NPOD, --��30��
			 POP_WO_DRT_POD, --��31��
			 POP_WO_DRT_NPOD, --��32��
			 POCP_WO_OR --��33��
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
								 0) PL_COST_CONFIRM, --ƫ�ߴ���ɱ�ȷ�ϡ�1��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_ENTRY,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_ENTRY, --�ⷢ��¼�롾2��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_NCONFIRM,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_NCONFIRM, --ƫ�ߴ���ɱ���ȷ�ϡ�3��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_PAY_APPLY, --ƫ�ߴ���ɱ��������롾4��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_WO_DEST_RCVO_NPOD, --Ӧ��ƫ�ߴ���ɱ���01Ӧ�յ����˷�δǩ�ա�5��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_WO_DEST_RCVO_POD, --Ӧ��ƫ�ߴ���ɱ���01Ӧ�յ����˷���ǩ�ա�6��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_NPODP,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_WO_DEST_RCVT_NPOD, --Ӧ��ƫ�ߴ���ɱ���02Ӧ�յ����˷�δǩ�ա�7��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_PODP,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_WO_DEST_RCVT_POD, --Ӧ��ƫ�ߴ���ɱ���02Ӧ�յ����˷���ǩ�ա�8��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_DR_CD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_DR_CD, --Ԥ��ƫ�ߴ������С�9��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_DR_CH,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_DR_CH, --Ԥ��ƫ�ߴ����ֽ�10��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_DR_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_DR_PAY_APPLY, --ƫ����Ԥ�ո������롾11��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVO_NPODD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_DR_WO_DEST_RCVO_NPOD, --Ԥ��ƫ�ߴ����01Ӧ�յ����˷�δǩ�ա�12��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVO_PODD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_DR_WO_DEST_RCVO_POD, --Ԥ��ƫ�ߴ����01Ӧ�յ����˷���ǩ�ա�13��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVT_NPODD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_DR_WO_DEST_RCVT_NPOD, --Ԥ��ƫ�ߴ����02Ӧ�յ����˷�δǩ�ա�14��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVT_PODD,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_DR_WO_DEST_RCVT_POD, --Ԥ��ƫ�ߴ����02Ӧ�յ����˷���ǩ�ա�15��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UROP_DEST_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) UROP_DEST_CD_NPOD, --��������δǩ�ա�16��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UROP_DEST_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) UROP_DEST_CD_POD, --����������ǩ�ա�17��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UROP_DEST_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) UROP_DEST_CH_NPOD, --�����ֽ�δǩ�ա�18��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UROP_DEST_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) UROP_DEST_CH_POD, --�����ֽ���ǩ�ա�19��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URTP_DEST_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URTP_DEST_CD_NPOD, --��������δǩ�ա�20��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URTP_DEST_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URTP_DEST_CD_POD, --����������ǩ�ա�21��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URTP_DEST_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URTP_DEST_CH_NPOD, --�����ֽ�δǩ�ա�22��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URTP_DEST_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URTP_DEST_CH_POD, --�����ֽ���ǩ�ա�23��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_COST_OP_CRM,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_COST_OP_CRM, --����Ӧ���ɱ�ȷ�ϡ�24��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.PL_DR_WO_OR,
														NVL(AMOUNT, 0),
														0)),
								 0) PL_DR_WO_OR, --Ԥ��ƫ�ߴ��������Ӧ�ա�25��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.POR_ENTRY,
														NVL(AMOUNT, 0),
														0)),
								 0) POR_ENTRY, --ƫ������Ӧ��¼�롾26��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UR_POR_CH,
														NVL(AMOUNT, 0),
														0)),
								 0) UR_POR_CH, --����ƫ������Ӧ���ֽ�27��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UR_POR_CD,
														NVL(AMOUNT, 0),
														0)),
								 0) UR_POR_CD, --����ƫ������Ӧ�����С�28��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.POP_WO_DRO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) POP_WO_DRO_POD, --����Ӧ����01Ӧ�յ����˷���ǩ�ա�29��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.POP_WO_DRO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) POP_WO_DRO_NPOD, --����Ӧ����01Ӧ�յ����˷�δǩ�ա�30��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.POP_WO_DRT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) POP_WO_DRT_POD, --����Ӧ����02Ӧ�յ����˷���ǩ�ա�31��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.POP_WO_DRT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) POP_WO_DRT_NPOD, --����Ӧ����02Ӧ�յ����˷�δǩ�ա�32��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.POCP_WO_OR,
														NVL(AMOUNT, 0),
														0)),
								 0) POCP_WO_OR --�ɱ�Ӧ������Ӧ��������Ӧ�ա�33��
				FROM STV.T_STL_NMVS T
			 WHERE T.PERIOD >= V_BEGIN_PERIOD
						 AND T.PERIOD < V_END_PERIOD
						 AND T.AMOUNT <> 0
						 AND T.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PLF -- ƫ��
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
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NPLR',
																				'ƾ֤����ƫ���±�������쳣:' || SQLERRM);
		
			--����ʧ�ܱ�־
			RETURN FALSE;
		
	END FUNC_VOUCHER_NPLR;

	-----------------------------------------------------------------
	-- �����±������
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NAFR(P_PERIOD     VARCHAR2,
														 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
														 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
														 ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
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
			 AIR_COD_CD_UR_NPOD, --��1��
			 AIR_COD_CD_UR_POD, --��2��
			 AIR_COD_CH_UR_NPOD, --��3��
			 AIR_COD_CH_UR_POD, --��4��
			 AIR_COD_COST_WO_COD_RCV_NPOD, --��5��
			 AIR_COD_DPAY_WO_COD_RCV_POD, --��6��
			 AIR_COD_OPAY_WO_COD_RCV_NPOD, --��7��
			 AIR_COD_OTH_PAY_WO_COD_RCV_POD, --��8��
			 AIR_COD_POD_WO, --��9��
			 AIR_COD_UPD_WO, --��10��
			 AIR_COST_DEST_AGENCY_PAY_CFRM, --��11��
			 AIR_COST_DEST_AGENCY_PAY_GEN, --��12��
			 AIR_COST_DEST_AGENCY_PAY_NCFRM, --��13��
			 AIR_COST_FEE_CONFIRM, --��14��
			 AIR_COST_ORIG_AGENCY_PAY_CFRM, --��15��
			 AIR_COST_OTH_PAY_COST_CFRM, --��16��
			 AIR_COST_PAY_APPLY, --��17��
			 AIR_DR_CD, --��18��
			 AIR_DR_CH, --��19��
			 AIR_DR_PAY_APPLY, --��20��
			 AIR_DR_WO_COD_RCV_NPOD, --��21��
			 AIR_DR_WO_COD_RCV_POD, --��22��
			 AIR_DR_WO_DEST_RCVO_NPOD, --��23��
			 AIR_DR_WO_DEST_RCVO_POD, --��24��
			 AIR_DR_WO_DEST_RCVT_NPOD, --��25��
			 AIR_DR_WO_DEST_RCVT_POD, --��26��
			 AIR_DR_WO_OTH_RCV, --��27��
			 AOR_CD_UR, --��28��
			 AOR_CH_UR, --��29��
			 AOR_ENTRY, --��30��
			 APT_AIR_COMPANY, --��31��
			 APT_WO_AIR_PAY, --��32��
			 APT_WO_OTH_PAY, --��33��
			 APWR_COST_WO_DEST_RCVO_NPOD, --��34��
			 APWR_COST_WO_DEST_RCVO_POD, --��35��
			 APWR_COST_WO_DEST_RCVT_NPOD, --��36��
			 APWR_COST_WO_DEST_RCVT_POD, --��37��
			 APWR_OTH_PAY_WO_DEST_RCVO_NPOD, --��38��
			 APWR_OTH_PAY_WO_DEST_RCVO_POD, --��39��
			 APWR_OTH_PAY_WO_DEST_RCVT_NPOD, --��40��
			 APWR_OTH_PAY_WO_DEST_RCVT_POD, --��41��
			 APWR_OTH_PAY_WO_OTH_RCV, --��42��
			 BWOR, --��43��
			 UROA_DEST_CD_NPOD, --��44��
			 UROA_DEST_CD_POD, --��45��
			 UROA_DEST_CH_NPOD, --��46��
			 UROA_DEST_CH_POD, --��47��
			 URTA_DEST_CD_NPOD, --��48��
			 URTA_DEST_CD_POD, --��49��
			 URTA_DEST_CH_NPOD, --��50��
			 URTA_DEST_CH_POD --��51��
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
								 0) AIR_COD_CD_UR_NPOD, --���˻�����ջ�������δǩ�ա�1��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COD_CD_UR_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COD_CD_UR_POD, --���˻�����ջ���������ǩ�ա�2��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COD_CH_UR_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COD_CH_UR_NPOD, --���˻�����ջ����ֽ�δǩ�ա�3��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COD_CH_UR_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COD_CH_UR_POD, --���˻�����ջ����ֽ���ǩ�ա�4��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COD_COST_WO_COD_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COD_COST_WO_COD_RCV_NPOD, --���˵������Ӧ����Ӧ�մ��ջ���δǩ�ա�5��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COD_DPAY_WO_COD_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COD_DPAY_WO_COD_RCV_POD, --���˵������Ӧ����Ӧ�մ��ջ�����ǩ�ա�6��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COD_OPAY_WO_COD_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COD_OPAY_WO_COD_RCV_NPOD, --��������Ӧ����Ӧ�մ��ջ���δǩ�ա�7��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COD_OTH_PAY_WO_COD_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COD_OTH_PAY_WO_COD_RCV_POD, --��������Ӧ����Ӧ�մ��ջ�����ǩ�ա�8��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COD_POD_WO,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COD_POD_WO, --����ǩ��ʱ�Ѻ������ջ��9��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COD_UPD_WO,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COD_UPD_WO, --���˷�ǩ��ʱ�Ѻ������ջ��10��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COST_DEST_AGENCY_PAY_CFRM,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COST_DEST_AGENCY_PAY_CFRM, --���˵������Ӧ���ɱ�ȷ�ϡ�11��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COST_DEST_AGENCY_PAY_GEN,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COST_DEST_AGENCY_PAY_GEN, --���˵������Ӧ�����ɡ�12��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COST_DEST_AGENCY_PAY_NCFRM,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COST_DEST_AGENCY_PAY_NCFRM, --���˵������Ӧ���ɱ���ȷ�ϡ�13��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COST_FEE_CONFIRM,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COST_FEE_CONFIRM, --���˺��չ�˾�˷�ȷ�ϡ�14��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COST_ORIG_AGENCY_PAY_CFRM,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COST_ORIG_AGENCY_PAY_CFRM, --���˳�������Ӧ��ȷ�ϡ�15��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COST_OTH_PAY_COST_CFRM,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COST_OTH_PAY_COST_CFRM, --����Ӧ���ɱ�ȷ�ϡ�16��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_COST_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_COST_PAY_APPLY, --���˳ɱ��������롾17��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_DR_CD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_DR_CD, --Ԥ�տ��˴������С�18��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_DR_CH,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_DR_CH, --Ԥ�տ��˴����ֽ�19��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_DR_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_DR_PAY_APPLY, --������Ԥ�ո������롾20��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_DR_WO_COD_RCV_NPOD, --Ԥ�տ��˴����Ӧ�մ��ջ���δǩ�ա�21��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_DR_WO_COD_RCV_POD, --Ԥ�տ��˴����Ӧ�մ��ջ�����ǩ�ա�22��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_DR_WO_DEST_RCVO_NPOD, --Ԥ�տ��˴����01Ӧ�յ����˷�δǩ�ա�23��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_DR_WO_DEST_RCVO_POD, --Ԥ�տ��˴����01Ӧ�յ����˷���ǩ�ա�24��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_DR_WO_DEST_RCVT_NPOD, --Ԥ�տ��˴����02Ӧ�յ����˷�δǩ�ա�25��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_DR_WO_DEST_RCVT_POD, --Ԥ�տ��˴����02Ӧ�յ����˷���ǩ�ա�26��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AIR_DR_WO_OTH_RCV,
														NVL(AMOUNT, 0),
														0)),
								 0) AIR_DR_WO_OTH_RCV, --Ԥ�տ��˴��������Ӧ�ա�27��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AOR_CD_UR,
														NVL(AMOUNT, 0),
														0)),
								 0) AOR_CD_UR, --�����������Ӧ�����С�28��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AOR_CH_UR,
														NVL(AMOUNT, 0),
														0)),
								 0) AOR_CH_UR, --�����������Ӧ���ֽ�29��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.AOR_ENTRY,
														NVL(AMOUNT, 0),
														0)),
								 0) AOR_ENTRY, --��������Ӧ��¼�롾30��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.APT_AIR_COMPANY,
														NVL(AMOUNT, 0),
														0)),
								 0) APT_AIR_COMPANY, --Ԥ�����չ�˾��31��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.APT_WO_AIR_PAY,
														NVL(AMOUNT, 0),
														0)),
								 0) APT_WO_AIR_PAY, --Ԥ����Ӧ�����չ�˾��32��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.APT_WO_OTH_PAY,
														NVL(AMOUNT, 0),
														0)),
								 0) APT_WO_OTH_PAY, --Ԥ��������Ӧ����33��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) APWR_COST_WO_DEST_RCVO_NPOD, --Ӧ���������ɱ���01Ӧ�յ����˷�δǩ�ա�34��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) APWR_COST_WO_DEST_RCVO_POD, --Ӧ���������ɱ���01Ӧ�յ����˷���ǩ�ա�35��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) APWR_COST_WO_DEST_RCVT_NPOD, --Ӧ���������ɱ���02Ӧ�յ����˷�δǩ�ա�36��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) APWR_COST_WO_DEST_RCVT_POD, --Ӧ���������ɱ���02Ӧ�յ����˷���ǩ�ա�37��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) APWR_OTH_PAY_WO_DEST_RCVO_NPOD, --����Ӧ����01Ӧ�յ����˷�δǩ�ա�38��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) APWR_OTH_PAY_WO_DEST_RCVO_POD, --����Ӧ����01Ӧ�յ����˷���ǩ�ա�39��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) APWR_OTH_PAY_WO_DEST_RCVT_NPOD, --����Ӧ����02Ӧ�յ����˷�δǩ�ա�40��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) APWR_OTH_PAY_WO_DEST_RCVT_POD, --����Ӧ����02Ӧ�յ����˷���ǩ�ա�41��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_OTH_RCV,
														NVL(AMOUNT, 0),
														0)),
								 0) APWR_OTH_PAY_WO_OTH_RCV, --����Ӧ��������Ӧ�ա�42��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.BWOR,
														NVL(AMOUNT, 0),
														0)),
								 0) BWOR, --���˳�����Ӧ�ա�43��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UROA_DEST_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) UROA_DEST_CD_NPOD, --��������δǩ�ա�44��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UROA_DEST_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) UROA_DEST_CD_POD, --����������ǩ�ա�45��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UROA_DEST_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) UROA_DEST_CH_NPOD, --�����ֽ�δǩ�ա�46��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.UROA_DEST_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) UROA_DEST_CH_POD, --�����ֽ���ǩ�ա�47��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URTA_DEST_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URTA_DEST_CD_NPOD, --��������δǩ�ա�48��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URTA_DEST_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URTA_DEST_CD_POD, --����������ǩ�ա�49��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URTA_DEST_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) URTA_DEST_CH_NPOD, --�����ֽ�δǩ�ա�50��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.URTA_DEST_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) URTA_DEST_CH_POD --�����ֽ���ǩ�ա�51��
				FROM STV.T_STL_NMVS T
			 WHERE T.PERIOD >= V_BEGIN_PERIOD
						 AND T.PERIOD < V_END_PERIOD
						 AND T.AMOUNT <> 0
						 AND T.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_AF --����
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
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NAFR',
																				'ƾ֤���ɿ����±�������쳣:' || SQLERRM);
		
			--����ʧ�ܱ�־
			RETURN FALSE;
		
	END FUNC_VOUCHER_NAFR;

	-----------------------------------------------------------------
	-- ʼ�����������±������
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NRFI(P_PERIOD     VARCHAR2,
														 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
														 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
														 ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
	 IS
	
		V_BEGIN_PERIOD VARCHAR2(50);
		V_END_PERIOD   VARCHAR2(50);
	
	BEGIN
	
		V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
		V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_NRFI T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
			USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;
	
		--ʼ��
		INSERT INTO STV.T_STL_MVR_NRFI
			(ID,
			 PERIOD,
			 ORG_CODE,
			 ORG_NAME,
			 ORG_TYPE,
			 INVOICE_MARK,
			 VOUCHER_BEGIN_TIME,
			 VOUCHER_END_TIME,
			 CLAIM_DESTO_INCOME, --��1��
			 CLAIM_DESTO_PAY_APPLY, --��2��
			 CLAIM_DESTO_WO_DEST_RCVO_NPOD, --��3��
			 CLAIM_DESTO_WO_DEST_RCVO_POD, --��4��
			 CLAIM_DESTO_WO_DEST_RCVT_NPOD, --��5��
			 CLAIM_DESTO_WO_DEST_RCVT_POD, --��6��
			 CLAIM_DESTT_INCOME, --��7��
			 CLAIM_DESTT_WO_DEST_RCVO_NPOD, --��8��
			 CLAIM_DESTT_WO_DEST_RCVO_POD, --��9��
			 CLAIM_DESTT_WO_DEST_RCVT_NPOD, --��10��
			 CLAIM_DESTT_WO_DEST_RCVT_POD, --��11��
			 CLAIM_ORIGO_PAY_APPLY, --��12��
			 CLAIM_ORIGO_WO_ORIG_RCVT_NPOD, --��13��
			 CLAIM_ORIGO_WO_ORIG_RCVT_POD, --��14��
			 CLAIM_ORIGT_ORIG_RCVO_POD, --��15��
			 CLAIM_ORIGT_WO_ORIG_RCVO_NPOD, --��16��
			 COD_PAY_WO_DEST_RCVO_NPOD, --��17��
			 COD_PAY_WO_DEST_RCVO_POD, --��18��
			 COD_PAY_WO_DEST_RCVT_NPOD, --��19��
			 COD_PAY_WO_DEST_RCVT_POD, --��20��
			 COD_PAY_WO_ORIG_RCVO_NPOD, --��21��
			 COD_PAY_WO_ORIG_RCVO_POD, --��22��
			 COD_PAY_WO_ORIG_RCVT_NPOD, --��23��
			 COD_PAY_WO_ORIG_RCVT_POD, --��24��
			 COD_PAY_WO_OTH_RCVO, --��25��
			 COD_PAY_WO_OTH_RCVT, --��26��
			 COD_UR_CD_NPOD, --��27��
			 COD_UR_CH_NPOD, --��28��
			 CPO_DEST_PAY_APPLY, --��29��
			 CPO_ORIG_PAY_APPLY, --��30��
			 CUST_DRO_PAY_APPLY, --��31��
			 CUST_DRO_WO_DEST_RCVO_NPOD, --��32��
			 CUST_DRO_WO_DEST_RCVO_POD, --��33��
			 CUST_DRO_WO_DEST_RCVT_NPOD, --��34��
			 CUST_DRO_WO_DEST_RCVT_POD, --��35��
			 CUST_DRO_WO_ORIG_RCVT_NPOD, --��36��
			 CUST_DRO_WO_ORIG_RCVT_POD, --��37��
			 CUST_DRT_WO_DEST_RCVO_NPOD, --��38��
			 CUST_DRT_WO_DEST_RCVO_POD, --��39��
			 CUST_DRT_WO_DEST_RCVT_NPOD, --��40��
			 CUST_DRT_WO_DEST_RCVT_POD, --��41��
			 CUST_DRT_WO_ORIG_RCVO_NPOD, --��42��
			 CUST_DRT_WO_ORIG_RCVO_POD, --��43��
			 CUST_DR_OCD, --��44��
			 CUST_DR_OCH, --��45��
			 DEO_CD, --��46��
			 DEO_CH, --��47��
			 OR_CD_PBIO, --��48��
			 OR_CD_UR_RCVO, --��49��
			 OR_CH_PBIO, --��50��
			 OR_CH_UR_RCVO, --��51��
			 OR_CLAIM_PAYO_WO_RCVT, --��52��
			 OR_CLAIM_PAYT_WO_RCVO, --��53��
			 OR_CUST_DRO_WO_RCVT, --��54��
			 OR_CUST_DRT_WO_RCVO, --��55��
			 RD_DESTO_DEST_RCVO_POD, --��56��
			 RD_DESTO_DEST_RCVT_POD, --��57��
			 RD_DESTO_INCOME, --��58��
			 RD_DESTO_PAY_APPLY, --��59��
			 RD_DESTO_WO_DEST_RCVO_NPOD, --��60��
			 RD_DESTO_WO_DEST_RCVT_NPOD, --��61��
			 RD_DESTT_INCOME, --��62��
			 RD_DESTT_WO_DEST_RCVO_NPOD, --��63��
			 RD_DESTT_WO_DEST_RCVO_POD, --��64��
			 RD_DESTT_WO_DEST_RCVT_NPOD, --��65��
			 RD_DESTT_WO_DEST_RCVT_POD, --��66��
			 RD_ORIGO_PAY_APPLY, --��67��
			 RD_ORIGO_WO_ORIG_RCVT_NPOD, --��68��
			 RD_ORIGO_WO_ORIG_RCVT_POD, --��69��
			 RD_ORIGT_WO_ORIG_RCVO_NPOD, --��70��
			 RD_ORIGT_WO_ORIG_RCVO_POD, --��71��
			 SFO_PAY_APPLY, --��72��
			 URO_DEST_CD_NPOD, --��73��
			 URO_DEST_CD_POD, --��74��
			 URO_DEST_CH_NPOD, --��75��
			 URO_DEST_CH_POD, --��76��
			 URO_ORIG_CD_NPOD, --��77��
			 URO_ORIG_CD_POD, --��78��
			 URO_ORIG_CH_NPOD, --��79��
			 URO_ORIG_CH_POD, --��80��
			 URT_DEST_CD_NPOD, --��81��
			 URT_DEST_CD_POD, --��82��
			 URT_DEST_CH_NPOD, --��83��
			 URT_DEST_CH_POD --��84��
			 )
			SELECT SYS_GUID(),
						 P_PERIOD,
						 ORG_CODE,
						 ORG_NAME,
						 ORG_TYPE,
						 INVOICE_MARK,
						 P_BEGIN_DATE VOUCHER_BEGIN_TIME,
						 P_END_DATE VOUCHER_END_TIME,
						 SUM(CLAIM_DESTO_INCOME), -- ��1��
						 SUM(CLAIM_DESTO_PAY_APPLY), -- ��2��
						 SUM(CLAIM_DESTO_WO_DEST_RCVO_NPOD), -- ��3��
						 SUM(CLAIM_DESTO_WO_DEST_RCVO_POD), -- ��4��
						 SUM(CLAIM_DESTO_WO_DEST_RCVT_NPOD), -- ��5��
						 SUM(CLAIM_DESTO_WO_DEST_RCVT_POD), -- ��6��
						 SUM(CLAIM_DESTT_INCOME), -- ��7��
						 SUM(CLAIM_DESTT_WO_DEST_RCVO_NPOD), -- ��8��
						 SUM(CLAIM_DESTT_WO_DEST_RCVO_POD), -- ��9��
						 SUM(CLAIM_DESTT_WO_DEST_RCVT_NPOD), -- ��10��
						 SUM(CLAIM_DESTT_WO_DEST_RCVT_POD), -- ��11��
						 SUM(CLAIM_ORIGO_PAY_APPLY), -- ��12��
						 SUM(CLAIM_ORIGO_WO_ORIG_RCVT_NPOD), -- ��13��
						 SUM(CLAIM_ORIGO_WO_ORIG_RCVT_POD), -- ��14��
						 SUM(CLAIM_ORIGT_ORIG_RCVO_POD), -- ��15��
						 SUM(CLAIM_ORIGT_WO_ORIG_RCVO_NPOD), -- ��16��
						 SUM(COD_PAY_WO_DEST_RCVO_NPOD), -- ��17��
						 SUM(COD_PAY_WO_DEST_RCVO_POD), -- ��18��
						 SUM(COD_PAY_WO_DEST_RCVT_NPOD), -- ��19��
						 SUM(COD_PAY_WO_DEST_RCVT_POD), -- ��20��
						 SUM(COD_PAY_WO_ORIG_RCVO_NPOD), -- ��21��
						 SUM(COD_PAY_WO_ORIG_RCVO_POD), -- ��22��
						 SUM(COD_PAY_WO_ORIG_RCVT_NPOD), -- ��23��
						 SUM(COD_PAY_WO_ORIG_RCVT_POD), -- ��24��
						 SUM(COD_PAY_WO_OTH_RCVO), -- ��25��
						 SUM(COD_PAY_WO_OTH_RCVT), -- ��26��
						 SUM(COD_UR_CD_NPOD), -- ��27��
						 SUM(COD_UR_CH_NPOD), -- ��28��
						 SUM(CPO_DEST_PAY_APPLY), -- ��29��
						 SUM(CPO_ORIG_PAY_APPLY), -- ��30��
						 SUM(CUST_DRO_PAY_APPLY), -- ��31��
						 SUM(CUST_DRO_WO_DEST_RCVO_NPOD), -- ��32��
						 SUM(CUST_DRO_WO_DEST_RCVO_POD), -- ��33��
						 SUM(CUST_DRO_WO_DEST_RCVT_NPOD), -- ��34��
						 SUM(CUST_DRO_WO_DEST_RCVT_POD), -- ��35��
						 SUM(CUST_DRO_WO_ORIG_RCVT_NPOD), -- ��36��
						 SUM(CUST_DRO_WO_ORIG_RCVT_POD), -- ��37��
						 SUM(CUST_DRT_WO_DEST_RCVO_NPOD), -- ��38��
						 SUM(CUST_DRT_WO_DEST_RCVO_POD), -- ��39��
						 SUM(CUST_DRT_WO_DEST_RCVT_NPOD), -- ��40��
						 SUM(CUST_DRT_WO_DEST_RCVT_POD), -- ��41��
						 SUM(CUST_DRT_WO_ORIG_RCVO_NPOD), -- ��42��
						 SUM(CUST_DRT_WO_ORIG_RCVO_POD), -- ��43��
						 SUM(CUST_DR_OCD), -- ��44��
						 SUM(CUST_DR_OCH), -- ��45��
						 SUM(DEO_CD), -- ��46��
						 SUM(DEO_CH), -- ��47��
						 SUM(OR_CD_PBIO), -- ��48��
						 SUM(OR_CD_UR_RCVO), -- ��49��
						 SUM(OR_CH_PBIO), -- ��50��
						 SUM(OR_CH_UR_RCVO), -- ��51��
						 SUM(OR_CLAIM_PAYO_WO_RCVT), -- ��52��
						 SUM(OR_CLAIM_PAYT_WO_RCVO), -- ��53��
						 SUM(OR_CUST_DRO_WO_RCVT), -- ��54��
						 SUM(OR_CUST_DRT_WO_RCVO), -- ��55��
						 SUM(RD_DESTO_DEST_RCVO_POD), -- ��56��
						 SUM(RD_DESTO_DEST_RCVT_POD), -- ��57��
						 SUM(RD_DESTO_INCOME), -- ��58��
						 SUM(RD_DESTO_PAY_APPLY), -- ��59��
						 SUM(RD_DESTO_WO_DEST_RCVO_NPOD), -- ��60��
						 SUM(RD_DESTO_WO_DEST_RCVT_NPOD), -- ��61��
						 SUM(RD_DESTT_INCOME), -- ��62��
						 SUM(RD_DESTT_WO_DEST_RCVO_NPOD), -- ��63��
						 SUM(RD_DESTT_WO_DEST_RCVO_POD), -- ��64��
						 SUM(RD_DESTT_WO_DEST_RCVT_NPOD), -- ��65��
						 SUM(RD_DESTT_WO_DEST_RCVT_POD), -- ��66��
						 SUM(RD_ORIGO_PAY_APPLY), -- ��67��
						 SUM(RD_ORIGO_WO_ORIG_RCVT_NPOD), -- ��68��
						 SUM(RD_ORIGO_WO_ORIG_RCVT_POD), -- ��69��
						 SUM(RD_ORIGT_WO_ORIG_RCVO_NPOD), -- ��70��
						 SUM(RD_ORIGT_WO_ORIG_RCVO_POD), -- ��71��
						 SUM(SFO_PAY_APPLY), -- ��72��
						 SUM(URO_DEST_CD_NPOD), -- ��73��
						 SUM(URO_DEST_CD_POD), -- ��74��
						 SUM(URO_DEST_CH_NPOD), -- ��75��
						 SUM(URO_DEST_CH_POD), -- ��76��
						 SUM(URO_ORIG_CD_NPOD), -- ��77��
						 SUM(URO_ORIG_CD_POD), -- ��78��
						 SUM(URO_ORIG_CH_NPOD), -- ��79��
						 SUM(URO_ORIG_CH_POD), -- ��80��
						 SUM(URT_DEST_CD_NPOD), -- ��81��
						 SUM(URT_DEST_CD_POD), -- ��82��
						 SUM(URT_DEST_CH_NPOD), -- ��83��
						 SUM(URT_DEST_CH_POD) -- ��84��
				FROM (SELECT T.CREDIT_ORG_CODE ORG_CODE,
										 T.CREDIT_ORG_NAME ORG_NAME,
										 T.CREDIT_ORG_TYPE ORG_TYPE, --�跽��������
										 T.CREDIT_INVOICE_MARK INVOICE_MARK,
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTO_INCOME,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTO_INCOME, --01��������롾1��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTO_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTO_PAY_APPLY, --01���⸶�����롾2��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTO_WO_DEST_RCVO_NPOD, --01�����01����Ӧ��δǩ�ա�3��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTO_WO_DEST_RCVO_POD, --01����嵽01��Ӧ����ǩ�ա�4��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTO_WO_DEST_RCVT_NPOD, --01�����02����Ӧ��δǩ�ա�5��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTO_WO_DEST_RCVT_POD, --01�����02����Ӧ����ǩ�ա�6��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTT_INCOME,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTT_INCOME, --02��������롾7��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTT_WO_DEST_RCVO_NPOD, --02�����01����Ӧ��δǩ�ա�8��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTT_WO_DEST_RCVO_POD, --02�����01����Ӧ����ǩ�ա�9��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTT_WO_DEST_RCVT_NPOD, --02�����02����Ӧ��δǩ�ա�10��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTT_WO_DEST_RCVT_POD, --02�����02����Ӧ����ǩ�ա�11��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_ORIGO_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_ORIGO_PAY_APPLY, --01���⸶�����롾12��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_ORIGO_WO_ORIG_RCVT_NPOD, --01�����02ʼ��Ӧ��δǩ�ա�13��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_ORIGO_WO_ORIG_RCVT_POD, --01�����02ʼ��Ӧ����ǩ�ա�14��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_ORIGT_ORIG_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_ORIGT_ORIG_RCVO_POD, --02�����01ʼ��Ӧ����ǩ�ա�15��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_ORIGT_WO_ORIG_RCVO_NPOD, --02�����01ʼ��Ӧ��δǩ�ա�16��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_DEST_RCVO_NPOD, --Ӧ�����ջ����01Ӧ�յ����˷�δǩ�ա�17��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_DEST_RCVO_POD, --Ӧ�����ջ����01Ӧ�յ����˷���ǩ�ա�18��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_DEST_RCVT_NPOD, --Ӧ�����ջ����02Ӧ�յ����˷�δǩ�ա�19��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_DEST_RCVT_POD, --Ӧ�����ջ����02Ӧ�յ����˷���ǩ�ա�20��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_ORIG_RCVO_NPOD, --Ӧ�����ջ����01Ӧ��ʼ���˷�δǩ�ա�21��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_ORIG_RCVO_POD, --Ӧ�����ջ����01Ӧ��ʼ���˷���ǩ�ա�22��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_ORIG_RCVT_NPOD, --Ӧ�����ջ����02Ӧ��ʼ���˷�δǩ�ա�23��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_ORIG_RCVT_POD, --Ӧ�����ջ����02Ӧ��ʼ���˷���ǩ�ա�24��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_OTH_RCVO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_OTH_RCVO, --Ӧ�����ջ����01СƱӦ�ա�25��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_OTH_RCVT,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_OTH_RCVT, --Ӧ�����ջ����02СƱӦ�ա�26��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_UR_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_UR_CD_NPOD, --������ջ�������δǩ�ա�27��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_UR_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_UR_CH_NPOD, --������ջ����ֽ�δǩ�ա�28��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CPO_DEST_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CPO_DEST_PAY_APPLY, --01������񲹾ȸ������롾29��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CPO_ORIG_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CPO_ORIG_PAY_APPLY, --01ʼ�����񲹾ȸ������롾30��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_PAY_APPLY, --01ʼ����Ԥ�ո������롾31��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_WO_DEST_RCVO_NPOD, --01Ԥ�տͻ���01Ӧ�յ����˷�δǩ�ա�32��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_WO_DEST_RCVO_POD, --01Ԥ�տͻ���01Ӧ�յ����˷���ǩ�ա�33��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_WO_DEST_RCVT_NPOD, --01Ԥ�տͻ���02Ӧ�յ����˷�δǩ�ա�34��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_WO_DEST_RCVT_POD, --01Ԥ�տͻ���02Ӧ�յ����˷���ǩ�ա�35��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_WO_ORIG_RCVT_NPOD, --01Ԥ�տͻ���02Ӧ��ʼ���˷�δǩ�ա�36��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_WO_ORIG_RCVT_POD, --01Ԥ�տͻ���02Ӧ��ʼ���˷���ǩ�ա�37��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRT_WO_DEST_RCVO_NPOD, --02Ԥ�տͻ���01Ӧ�յ����˷�δǩ�ա�38��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRT_WO_DEST_RCVO_POD, --02Ԥ�տͻ���01Ӧ�յ����˷���ǩ�ա�39��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRT_WO_DEST_RCVT_NPOD, --02Ԥ�տͻ���02Ӧ�յ����˷�δǩ�ա�40��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRT_WO_DEST_RCVT_POD, --02Ԥ�տͻ���02Ӧ�յ����˷���ǩ�ա�41��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRT_WO_ORIG_RCVO_NPOD, --02Ԥ�տͻ���01Ӧ��ʼ���˷�δǩ�ա�42��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRT_WO_ORIG_RCVO_POD, --02Ԥ�տͻ���01Ӧ��ʼ���˷���ǩ�ա�43��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DR_OCD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DR_OCD, --Ԥ�տͻ����С�44��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DR_OCH,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DR_OCH, --Ԥ�տͻ��ֽ�45��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.DEO_CD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) DEO_CD, --�������п���46��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.DEO_CH,
																		NVL(AMOUNT, 0),
																		0)),
												 0) DEO_CH, --�����ֽ�47��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CD_PBIO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CD_PBIO, --01СƱ������Ӫҵ�����롾48��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CD_UR_RCVO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CD_UR_RCVO, --�������г�01СƱӦ�ա�49��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CH_PBIO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CH_PBIO, --01СƱ�ֽ���Ӫҵ�����롾50��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CH_UR_RCVO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CH_UR_RCVO, --�����ֽ��01СƱӦ�ա�51��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CLAIM_PAYO_WO_RCVT,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CLAIM_PAYO_WO_RCVT, --01Ӧ�������02СƱӦ�ա�52��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CLAIM_PAYT_WO_RCVO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CLAIM_PAYT_WO_RCVO, --02Ӧ�������01СƱӦ�ա�53��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CUST_DRO_WO_RCVT,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CUST_DRO_WO_RCVT, --01Ԥ�տͻ���02СƱӦ�ա�54��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CUST_DRT_WO_RCVO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CUST_DRT_WO_RCVO, --02Ԥ�տͻ���01СƱӦ�ա�55��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTO_DEST_RCVO_POD, --01���˷ѳ�01����Ӧ����ǩ�ա�56��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTO_DEST_RCVT_POD, --01���˷ѳ�02����Ӧ����ǩ�ա�57��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTO_INCOME,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTO_INCOME, --01���˷ѳ����롾58��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTO_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTO_PAY_APPLY, --01���˷Ѹ������롾59��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTO_WO_DEST_RCVO_NPOD, --01���˷ѳ�01����Ӧ��δǩ�ա�60��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTO_WO_DEST_RCVT_NPOD, --01���˷ѳ�02����Ӧ��δǩ�ա�61��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTT_INCOME,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTT_INCOME, --02���˷ѳ����롾62��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTT_WO_DEST_RCVO_NPOD, --02���˷ѳ�01����Ӧ��δǩ�ա�63��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTT_WO_DEST_RCVO_POD, --02���˷ѳ�01����Ӧ����ǩ�ա�64��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTT_WO_DEST_RCVT_NPOD, --02���˷ѳ�02����Ӧ��δǩ�ա�65��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTT_WO_DEST_RCVT_POD, --02���˷ѳ�02����Ӧ����ǩ�ա�66��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_ORIGO_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_ORIGO_PAY_APPLY, --01���˷Ѹ������롾67��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_ORIGO_WO_ORIG_RCVT_NPOD, --01���˷ѳ�02ʼ��Ӧ��δǩ�ա�68��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_ORIGO_WO_ORIG_RCVT_POD, --01���˷ѳ�02ʼ��Ӧ����ǩ�ա�69��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_ORIGT_WO_ORIG_RCVO_NPOD, --02���˷ѳ�01ʼ��Ӧ��δǩ�ա�70��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_ORIGT_WO_ORIG_RCVO_POD, --02���˷ѳ�01ʼ��Ӧ����ǩ�ա�71��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.SFO_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) SFO_PAY_APPLY, --01װж�Ѹ������롾72��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_DEST_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_DEST_CD_NPOD, --��������δǩ�ա�73��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_DEST_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_DEST_CD_POD, --����������ǩ�ա�74��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_DEST_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_DEST_CH_NPOD, --�����ֽ�δǩ�ա�75��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_DEST_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_DEST_CH_POD, --�����ֽ���ǩ�ա�76��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_ORIG_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_ORIG_CD_NPOD, --��������δǩ�ա�77��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_ORIG_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_ORIG_CD_POD, --����������ǩ�ա�78��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_ORIG_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_ORIG_CH_NPOD, --�����ֽ�δǩ�ա�79��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_ORIG_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_ORIG_CH_POD, --�����ֽ���ǩ�ա�80��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URT_DEST_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URT_DEST_CD_NPOD, --��������δǩ�ա�81��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URT_DEST_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URT_DEST_CD_POD, --����������ǩ�ա�82��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URT_DEST_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URT_DEST_CH_NPOD, --�����ֽ�δǩ�ա�83��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URT_DEST_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URT_DEST_CH_POD --�����ֽ���ǩ�ա�84��
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
										 T.DEBIT_ORG_TYPE ORG_TYPE, --������������
										 T.DEBIT_INVOICE_MARK INVOICE_MARK,
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTO_INCOME,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTO_INCOME, --01��������롾1��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTO_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTO_PAY_APPLY, --01���⸶�����롾2��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTO_WO_DEST_RCVO_NPOD, --01�����01����Ӧ��δǩ�ա�3��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTO_WO_DEST_RCVO_POD, --01����嵽01��Ӧ����ǩ�ա�4��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTO_WO_DEST_RCVT_NPOD, --01�����02����Ӧ��δǩ�ա�5��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTO_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTO_WO_DEST_RCVT_POD, --01�����02����Ӧ����ǩ�ա�6��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTT_INCOME,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTT_INCOME, --02��������롾7��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTT_WO_DEST_RCVO_NPOD, --02�����01����Ӧ��δǩ�ա�8��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTT_WO_DEST_RCVO_POD, --02�����01����Ӧ����ǩ�ա�9��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTT_WO_DEST_RCVT_NPOD, --02�����02����Ӧ��δǩ�ա�10��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_DESTT_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_DESTT_WO_DEST_RCVT_POD, --02�����02����Ӧ����ǩ�ա�11��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_ORIGO_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_ORIGO_PAY_APPLY, --01���⸶�����롾12��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_ORIGO_WO_ORIG_RCVT_NPOD, --01�����02ʼ��Ӧ��δǩ�ա�13��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_ORIGO_WO_ORIG_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_ORIGO_WO_ORIG_RCVT_POD, --01�����02ʼ��Ӧ����ǩ�ա�14��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_ORIGT_ORIG_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_ORIGT_ORIG_RCVO_POD, --02�����01ʼ��Ӧ����ǩ�ա�15��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CLAIM_ORIGT_WO_ORIG_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CLAIM_ORIGT_WO_ORIG_RCVO_NPOD, --02�����01ʼ��Ӧ��δǩ�ա�16��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_DEST_RCVO_NPOD, --Ӧ�����ջ����01Ӧ�յ����˷�δǩ�ա�17��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_DEST_RCVO_POD, --Ӧ�����ջ����01Ӧ�յ����˷���ǩ�ա�18��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_DEST_RCVT_NPOD, --Ӧ�����ջ����02Ӧ�յ����˷�δǩ�ա�19��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_DEST_RCVT_POD, --Ӧ�����ջ����02Ӧ�յ����˷���ǩ�ա�20��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_ORIG_RCVO_NPOD, --Ӧ�����ջ����01Ӧ��ʼ���˷�δǩ�ա�21��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_ORIG_RCVO_POD, --Ӧ�����ջ����01Ӧ��ʼ���˷���ǩ�ա�22��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_ORIG_RCVT_NPOD, --Ӧ�����ջ����02Ӧ��ʼ���˷�δǩ�ա�23��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_ORIG_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_ORIG_RCVT_POD, --Ӧ�����ջ����02Ӧ��ʼ���˷���ǩ�ա�24��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_OTH_RCVO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_OTH_RCVO, --Ӧ�����ջ����01СƱӦ�ա�25��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_PAY_WO_OTH_RCVT,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_PAY_WO_OTH_RCVT, --Ӧ�����ջ����02СƱӦ�ա�26��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_UR_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_UR_CD_NPOD, --������ջ�������δǩ�ա�27��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.COD_UR_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) COD_UR_CH_NPOD, --������ջ����ֽ�δǩ�ա�28��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CPO_DEST_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CPO_DEST_PAY_APPLY, --01������񲹾ȸ������롾29��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CPO_ORIG_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CPO_ORIG_PAY_APPLY, --01ʼ�����񲹾ȸ������롾30��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_PAY_APPLY, --01ʼ����Ԥ�ո������롾31��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_WO_DEST_RCVO_NPOD, --01Ԥ�տͻ���01Ӧ�յ����˷�δǩ�ա�32��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_WO_DEST_RCVO_POD, --01Ԥ�տͻ���01Ӧ�յ����˷���ǩ�ա�33��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_WO_DEST_RCVT_NPOD, --01Ԥ�տͻ���02Ӧ�յ����˷�δǩ�ա�34��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_WO_DEST_RCVT_POD, --01Ԥ�տͻ���02Ӧ�յ����˷���ǩ�ա�35��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_WO_ORIG_RCVT_NPOD, --01Ԥ�տͻ���02Ӧ��ʼ���˷�δǩ�ա�36��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRO_WO_ORIG_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRO_WO_ORIG_RCVT_POD, --01Ԥ�տͻ���02Ӧ��ʼ���˷���ǩ�ա�37��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRT_WO_DEST_RCVO_NPOD, --02Ԥ�տͻ���01Ӧ�յ����˷�δǩ�ա�38��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRT_WO_DEST_RCVO_POD, --02Ԥ�տͻ���01Ӧ�յ����˷���ǩ�ա�39��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRT_WO_DEST_RCVT_NPOD, --02Ԥ�տͻ���02Ӧ�յ����˷�δǩ�ա�40��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRT_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRT_WO_DEST_RCVT_POD, --02Ԥ�տͻ���02Ӧ�յ����˷���ǩ�ա�41��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRT_WO_ORIG_RCVO_NPOD, --02Ԥ�տͻ���01Ӧ��ʼ���˷�δǩ�ա�42��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DRT_WO_ORIG_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DRT_WO_ORIG_RCVO_POD, --02Ԥ�տͻ���01Ӧ��ʼ���˷���ǩ�ա�43��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DR_OCD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DR_OCD, --Ԥ�տͻ����С�44��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.CUST_DR_OCH,
																		NVL(AMOUNT, 0),
																		0)),
												 0) CUST_DR_OCH, --Ԥ�տͻ��ֽ�45��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.DEO_CD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) DEO_CD, --�������п���46��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.DEO_CH,
																		NVL(AMOUNT, 0),
																		0)),
												 0) DEO_CH, --�����ֽ�47��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CD_PBIO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CD_PBIO, --01СƱ������Ӫҵ�����롾48��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CD_UR_RCVO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CD_UR_RCVO, --�������г�01СƱӦ�ա�49��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CH_PBIO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CH_PBIO, --01СƱ�ֽ���Ӫҵ�����롾50��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CH_UR_RCVO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CH_UR_RCVO, --�����ֽ��01СƱӦ�ա�51��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CLAIM_PAYO_WO_RCVT,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CLAIM_PAYO_WO_RCVT, --01Ӧ�������02СƱӦ�ա�52��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CLAIM_PAYT_WO_RCVO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CLAIM_PAYT_WO_RCVO, --02Ӧ�������01СƱӦ�ա�53��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CUST_DRO_WO_RCVT,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CUST_DRO_WO_RCVT, --01Ԥ�տͻ���02СƱӦ�ա�54��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.OR_CUST_DRT_WO_RCVO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) OR_CUST_DRT_WO_RCVO, --02Ԥ�տͻ���01СƱӦ�ա�55��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTO_DEST_RCVO_POD, --01���˷ѳ�01����Ӧ����ǩ�ա�56��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTO_DEST_RCVT_POD, --01���˷ѳ�02����Ӧ����ǩ�ա�57��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTO_INCOME,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTO_INCOME, --01���˷ѳ����롾58��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTO_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTO_PAY_APPLY, --01���˷Ѹ������롾59��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTO_WO_DEST_RCVO_NPOD, --01���˷ѳ�01����Ӧ��δǩ�ա�60��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTO_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTO_WO_DEST_RCVT_NPOD, --01���˷ѳ�02����Ӧ��δǩ�ա�61��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTT_INCOME,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTT_INCOME, --02���˷ѳ����롾62��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTT_WO_DEST_RCVO_NPOD, --02���˷ѳ�01����Ӧ��δǩ�ա�63��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTT_WO_DEST_RCVO_POD, --02���˷ѳ�01����Ӧ����ǩ�ա�64��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTT_WO_DEST_RCVT_NPOD, --02���˷ѳ�02����Ӧ��δǩ�ա�65��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_DESTT_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_DESTT_WO_DEST_RCVT_POD, --02���˷ѳ�02����Ӧ����ǩ�ա�66��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_ORIGO_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_ORIGO_PAY_APPLY, --01���˷Ѹ������롾67��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_ORIGO_WO_ORIG_RCVT_NPOD, --01���˷ѳ�02ʼ��Ӧ��δǩ�ա�68��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_ORIGO_WO_ORIG_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_ORIGO_WO_ORIG_RCVT_POD, --01���˷ѳ�02ʼ��Ӧ����ǩ�ա�69��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_ORIGT_WO_ORIG_RCVO_NPOD, --02���˷ѳ�01ʼ��Ӧ��δǩ�ա�70��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.RD_ORIGT_WO_ORIG_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) RD_ORIGT_WO_ORIG_RCVO_POD, --02���˷ѳ�01ʼ��Ӧ����ǩ�ա�71��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.SFO_PAY_APPLY,
																		NVL(AMOUNT, 0),
																		0)),
												 0) SFO_PAY_APPLY, --01װж�Ѹ������롾72��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_DEST_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_DEST_CD_NPOD, --��������δǩ�ա�73��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_DEST_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_DEST_CD_POD, --����������ǩ�ա�74��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_DEST_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_DEST_CH_NPOD, --�����ֽ�δǩ�ա�75��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_DEST_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_DEST_CH_POD, --�����ֽ���ǩ�ա�76��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_ORIG_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_ORIG_CD_NPOD, --��������δǩ�ա�77��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_ORIG_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_ORIG_CD_POD, --����������ǩ�ա�78��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_ORIG_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_ORIG_CH_NPOD, --�����ֽ�δǩ�ա�79��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URO_ORIG_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URO_ORIG_CH_POD, --�����ֽ���ǩ�ա�80��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URT_DEST_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URT_DEST_CD_NPOD, --��������δǩ�ա�81��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URT_DEST_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URT_DEST_CD_POD, --����������ǩ�ա�82��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URT_DEST_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URT_DEST_CH_NPOD, --�����ֽ�δǩ�ա�83��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URT_DEST_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URT_DEST_CH_POD --�����ֽ���ǩ�ա�84��
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
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NRFI',
																				'ƾ֤����ʼ��ר�������±�������쳣:' || SQLERRM);
		
			--����ʧ�ܱ�־
			RETURN FALSE;
		
	END FUNC_VOUCHER_NRFI;

	-----------------------------------------------------------------
	-- ʼ��ƫ�������±������
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NPLI(P_PERIOD     VARCHAR2,
														 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
														 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
														 ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
	 IS
	
		V_BEGIN_PERIOD VARCHAR2(50);
		V_END_PERIOD   VARCHAR2(50);
	
	BEGIN
	
		V_BEGIN_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
		V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');
	
		EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_NPLI T WHERE T.VOUCHER_BEGIN_TIME >= :P_BEGIN_DATE AND T.VOUCHER_END_TIME <= :P_END_DATE AND T.PERIOD = :P_PERIOD'
			USING P_BEGIN_DATE, P_END_DATE, P_PERIOD;
	
		--ʼ��
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
			 PL_COST_WO_DEST_RCVO_NPOD, --��1��
			 PL_COST_WO_DEST_RCVO_POD, --��2��
			 PL_COST_WO_DEST_RCVT_NPOD, --��3��
			 PL_COST_WO_DEST_RCVT_POD, --��4��
			 PL_DR_WO_DEST_RCVO_NPOD, --��5��
			 PL_DR_WO_DEST_RCVO_POD, --��6��
			 PL_DR_WO_DEST_RCVT_NPOD, --��7��
			 PL_DR_WO_DEST_RCVT_POD, --��8��
			 UROP_DEST_CD_NPOD, --��9��
			 UROP_DEST_CD_POD, --��10��
			 UROP_DEST_CH_NPOD, --��11��
			 UROP_DEST_CH_POD, --��12��
			 URTP_DEST_CD_NPOD, --��13��
			 URTP_DEST_CD_POD, --��14��
			 URTP_DEST_CH_NPOD, --��15��
			 URTP_DEST_CH_POD, --��16��
			 POP_WO_DRO_POD, --��17��
			 POP_WO_DRO_NPOD, --��18��
			 POP_WO_DRT_POD, --��19��
			 POP_WO_DRT_NPOD --��20��
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
						 SUM(PL_COST_WO_DEST_RCVO_NPOD), --��1��
						 SUM(PL_COST_WO_DEST_RCVO_POD), --��2��
						 SUM(PL_COST_WO_DEST_RCVT_NPOD), --��3��
						 SUM(PL_COST_WO_DEST_RCVT_POD), --��4��
						 SUM(PL_DR_WO_DEST_RCVO_NPOD), --��5��
						 SUM(PL_DR_WO_DEST_RCVO_POD), --��6��
						 SUM(PL_DR_WO_DEST_RCVT_NPOD), --��7��
						 SUM(PL_DR_WO_DEST_RCVT_POD), --��8��
						 SUM(UROP_DEST_CD_NPOD), --��9��
						 SUM(UROP_DEST_CD_POD), --��10��
						 SUM(UROP_DEST_CH_NPOD), --��11��
						 SUM(UROP_DEST_CH_POD), --��12��
						 SUM(URTP_DEST_CD_NPOD), --��13��
						 SUM(URTP_DEST_CD_POD), --��14��
						 SUM(URTP_DEST_CH_NPOD), --��15��
						 SUM(URTP_DEST_CH_POD), --��16��
						 SUM(POP_WO_DRO_POD), --��17��
						 SUM(POP_WO_DRO_NPOD), --��18��
						 SUM(POP_WO_DRT_POD), --��19��
						 SUM(POP_WO_DRT_NPOD) --��20��
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
												 0) PL_COST_WO_DEST_RCVO_NPOD, --Ӧ��ƫ�ߴ���ɱ���01Ӧ�յ����˷�δǩ�ա�1��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_COST_WO_DEST_RCVO_POD, --Ӧ��ƫ�ߴ���ɱ���01Ӧ�յ����˷���ǩ�ա�2��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_COST_WO_DEST_RCVT_NPOD, --Ӧ��ƫ�ߴ���ɱ���02Ӧ�յ����˷�δǩ�ա�3��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_COST_WO_DEST_RCVT_POD, --Ӧ��ƫ�ߴ���ɱ���02Ӧ�յ����˷���ǩ�ա�4��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_DR_WO_DEST_RCVO_NPOD, --Ԥ��ƫ�ߴ����01Ӧ�յ����˷�δǩ�ա�5��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_DR_WO_DEST_RCVO_POD, --Ԥ��ƫ�ߴ����01Ӧ�յ����˷���ǩ�ա�6��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_DR_WO_DEST_RCVT_NPOD, --Ԥ��ƫ�ߴ����02Ӧ�յ����˷�δǩ�ա�7��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_DR_WO_DEST_RCVT_POD, --Ԥ��ƫ�ߴ����02Ӧ�յ����˷���ǩ�ա�8��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROP_DEST_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROP_DEST_CD_NPOD, --��������δǩ�ա�9��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROP_DEST_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROP_DEST_CD_POD, --����������ǩ�ա�10��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROP_DEST_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROP_DEST_CH_NPOD, --�����ֽ�δǩ�ա�11��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROP_DEST_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROP_DEST_CH_POD, --�����ֽ���ǩ�ա�12��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTP_DEST_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTP_DEST_CD_NPOD, --��������δǩ�ա�13��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTP_DEST_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTP_DEST_CD_POD, --����������ǩ�ա�14��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTP_DEST_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTP_DEST_CH_NPOD, --�����ֽ�δǩ�ա�15��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTP_DEST_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTP_DEST_CH_POD, --�����ֽ���ǩ�ա�16��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.POP_WO_DRO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) POP_WO_DRO_POD, --����Ӧ����01Ӧ�յ����˷���ǩ�ա�17��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.POP_WO_DRO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) POP_WO_DRO_NPOD, --����Ӧ����01Ӧ�յ����˷�δǩ�ա�18��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.POP_WO_DRT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) POP_WO_DRT_POD, --����Ӧ����02Ӧ�յ����˷���ǩ�ա�19��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.POP_WO_DRT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) POP_WO_DRT_NPOD --����Ӧ����02Ӧ�յ����˷�δǩ�ա�20��
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
												 0) PL_COST_WO_DEST_RCVO_NPOD, --Ӧ��ƫ�ߴ���ɱ���01Ӧ�յ����˷�δǩ�ա�1��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_COST_WO_DEST_RCVO_POD, --Ӧ��ƫ�ߴ���ɱ���01Ӧ�յ����˷���ǩ�ա�2��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_COST_WO_DEST_RCVT_NPOD, --Ӧ��ƫ�ߴ���ɱ���02Ӧ�յ����˷�δǩ�ա�3��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_COST_WO_DEST_RCVT_POD, --Ӧ��ƫ�ߴ���ɱ���02Ӧ�յ����˷���ǩ�ա�4��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_DR_WO_DEST_RCVO_NPOD, --Ԥ��ƫ�ߴ����01Ӧ�յ����˷�δǩ�ա�5��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_DR_WO_DEST_RCVO_POD, --Ԥ��ƫ�ߴ����01Ӧ�յ����˷���ǩ�ա�6��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_DR_WO_DEST_RCVT_NPOD, --Ԥ��ƫ�ߴ����02Ӧ�յ����˷�δǩ�ա�7��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.PL_DR_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) PL_DR_WO_DEST_RCVT_POD, --Ԥ��ƫ�ߴ����02Ӧ�յ����˷���ǩ�ա�8��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROP_DEST_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROP_DEST_CD_NPOD, --��������δǩ�ա�9��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROP_DEST_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROP_DEST_CD_POD, --����������ǩ�ա�10��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROP_DEST_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROP_DEST_CH_NPOD, --�����ֽ�δǩ�ա�11��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROP_DEST_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROP_DEST_CH_POD, --�����ֽ���ǩ�ա�12��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTP_DEST_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTP_DEST_CD_NPOD, --��������δǩ�ա�13��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTP_DEST_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTP_DEST_CD_POD, --����������ǩ�ա�14��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTP_DEST_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTP_DEST_CH_NPOD, --�����ֽ�δǩ�ա�15��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTP_DEST_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTP_DEST_CH_POD, --�����ֽ���ǩ�ա�16��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.POP_WO_DRO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) POP_WO_DRO_POD, --����Ӧ����01Ӧ�յ����˷���ǩ�ա�17��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.POP_WO_DRO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) POP_WO_DRO_NPOD, --����Ӧ����01Ӧ�յ����˷�δǩ�ա�18��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.POP_WO_DRT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) POP_WO_DRT_POD, --����Ӧ����02Ӧ�յ����˷���ǩ�ա�19��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.POP_WO_DRT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) POP_WO_DRT_NPOD --����Ӧ����02Ӧ�յ����˷�δǩ�ա�20��
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
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NPLI',
																				'ƾ֤����ʼ��ƫ�������±�������쳣:' || SQLERRM);
		
			--����ʧ�ܱ�־
			RETURN FALSE;
		
	END FUNC_VOUCHER_NPLI;

	-----------------------------------------------------------------
	-- ʼ�����������±������
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_NAFI(P_PERIOD     VARCHAR2,
														 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
														 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
														 ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
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
			 AIR_COD_CD_UR_POD, --��1��
			 AIR_COD_CH_UR_POD, --��2��
			 AIR_COD_DPAY_WO_COD_RCV_POD, --��3��
			 AIR_COD_OTH_PAY_WO_COD_RCV_POD, --��4��
			 AIR_COD_POD_NWO, --��5��
			 AIR_COD_POD_WO, --��6��
			 AIR_COD_UPD_NWO, --��7��
			 AIR_COD_UPD_WO, --��8��
			 AIR_DR_WO_COD_RCV_POD, --��9��
			 AIR_DR_WO_DEST_RCVO_NPOD, --��10��
			 AIR_DR_WO_DEST_RCVO_POD, --��11��
			 AIR_DR_WO_DEST_RCVT_NPOD, --��12��
			 AIR_DR_WO_DEST_RCVT_POD, --��13��
			 APWR_COST_WO_DEST_RCVO_NPOD, --��14��
			 APWR_COST_WO_DEST_RCVO_POD, --��15��
			 APWR_COST_WO_DEST_RCVT_NPOD, --��16��
			 APWR_COST_WO_DEST_RCVT_POD, --��17��
			 APWR_OTH_PAY_WO_DEST_RCVO_NPOD, --��18��
			 APWR_OTH_PAY_WO_DEST_RCVO_POD, --��19��
			 APWR_OTH_PAY_WO_DEST_RCVT_NPOD, --��20��
			 APWR_OTH_PAY_WO_DEST_RCVT_POD, --��21��
			 UROA_DEST_CD_NPOD, --��22��
			 UROA_DEST_CD_POD, --��23��
			 UROA_DEST_CH_NPOD, --��24��
			 UROA_DEST_CH_POD, --��25��
			 URTA_DEST_CD_NPOD, --��26��
			 URTA_DEST_CD_POD, --��27��
			 URTA_DEST_CH_NPOD, --��28��
			 URTA_DEST_CH_POD --��29��
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
						 SUM(AIR_COD_CD_UR_POD), --��1��
						 SUM(AIR_COD_CH_UR_POD), --��2��
						 SUM(AIR_COD_DPAY_WO_COD_RCV_POD), --��3��
						 SUM(AIR_COD_OTH_PAY_WO_COD_RCV_POD), --��4��
						 SUM(AIR_COD_POD_NWO), --��5��
						 SUM(AIR_COD_POD_WO), --��6��
						 SUM(AIR_COD_UPD_NWO), --��7��
						 SUM(AIR_COD_UPD_WO), --��8��
						 SUM(AIR_DR_WO_COD_RCV_POD), --��9��
						 SUM(AIR_DR_WO_DEST_RCVO_NPOD), --��10��
						 SUM(AIR_DR_WO_DEST_RCVO_POD), --��11��
						 SUM(AIR_DR_WO_DEST_RCVT_NPOD), --��12��
						 SUM(AIR_DR_WO_DEST_RCVT_POD), --��13��
						 SUM(APWR_COST_WO_DEST_RCVO_NPOD), --��14��
						 SUM(APWR_COST_WO_DEST_RCVO_POD), --��15��
						 SUM(APWR_COST_WO_DEST_RCVT_NPOD), --��16��
						 SUM(APWR_COST_WO_DEST_RCVT_POD), --��17��
						 SUM(APWR_OTH_PAY_WO_DEST_RCVO_NPOD), --��18��
						 SUM(APWR_OTH_PAY_WO_DEST_RCVO_POD), --��19��
						 SUM(APWR_OTH_PAY_WO_DEST_RCVT_NPOD), --��20��
						 SUM(APWR_OTH_PAY_WO_DEST_RCVT_POD), --��21��
						 SUM(UROA_DEST_CD_NPOD), --��22��
						 SUM(UROA_DEST_CD_POD), --��23��
						 SUM(UROA_DEST_CH_NPOD), --��24��
						 SUM(UROA_DEST_CH_POD), --��25��
						 SUM(URTA_DEST_CD_NPOD), --��26��
						 SUM(URTA_DEST_CD_POD), --��27��
						 SUM(URTA_DEST_CH_NPOD), --��28��
						 SUM(URTA_DEST_CH_POD) --��29��
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
												 0) AIR_COD_CD_UR_POD, --���˻�����ջ���������ǩ�ա�1��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_CH_UR_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_CH_UR_POD, --���˻�����ջ����ֽ���ǩ�ա�2��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_DPAY_WO_COD_RCV_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_DPAY_WO_COD_RCV_POD, --���˵������Ӧ����Ӧ�մ��ջ�����ǩ�ա�3��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_OTH_PAY_WO_COD_RCV_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_OTH_PAY_WO_COD_RCV_POD, --��������Ӧ����Ӧ�մ��ջ�����ǩ�ա�4��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_POD_NWO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_POD_NWO, --����ǩ��ʱδ�������ջ��5��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_POD_WO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_POD_WO, --����ǩ��ʱ�Ѻ������ջ��6��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_UPD_NWO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_UPD_NWO, --���˷�ǩ��ʱδ�������ջ��7��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_UPD_WO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_UPD_WO, --���˷�ǩ��ʱ�Ѻ������ջ��8��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_DR_WO_COD_RCV_POD, --Ԥ�տ��˴����Ӧ�մ��ջ�����ǩ�ա�9��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_DR_WO_DEST_RCVO_NPOD, --Ԥ�տ��˴����01Ӧ�յ����˷�δǩ�ա�10��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_DR_WO_DEST_RCVO_POD, --Ԥ�տ��˴����01Ӧ�յ����˷���ǩ�ա�11��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_DR_WO_DEST_RCVT_NPOD, --Ԥ�տ��˴����02Ӧ�յ����˷�δǩ�ա�12��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_DR_WO_DEST_RCVT_POD, --Ԥ�տ��˴����02Ӧ�յ����˷���ǩ�ա�13��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_COST_WO_DEST_RCVO_NPOD, --Ӧ���������ɱ���01Ӧ�յ����˷�δǩ�ա�14��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_COST_WO_DEST_RCVO_POD, --Ӧ���������ɱ���01Ӧ�յ����˷���ǩ�ա�15��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_COST_WO_DEST_RCVT_NPOD, --Ӧ���������ɱ���02Ӧ�յ����˷�δǩ�ա�16��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_COST_WO_DEST_RCVT_POD, --Ӧ���������ɱ���02Ӧ�յ����˷���ǩ�ա�17��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_OTH_PAY_WO_DEST_RCVO_NPOD, --����Ӧ����01Ӧ�յ����˷�δǩ�ա�18��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_OTH_PAY_WO_DEST_RCVO_POD, --����Ӧ����01Ӧ�յ����˷���ǩ�ա�19��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_OTH_PAY_WO_DEST_RCVT_NPOD, --����Ӧ����02Ӧ�յ����˷�δǩ�ա�20��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_OTH_PAY_WO_DEST_RCVT_POD, --����Ӧ����02Ӧ�յ����˷���ǩ�ա�21��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROA_DEST_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROA_DEST_CD_NPOD, --��������δǩ�ա�22��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROA_DEST_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROA_DEST_CD_POD, --����������ǩ�ա�23��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROA_DEST_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROA_DEST_CH_NPOD, --�����ֽ�δǩ�ա�24��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROA_DEST_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROA_DEST_CH_POD, --�����ֽ���ǩ�ա�25��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTA_DEST_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTA_DEST_CD_NPOD, --��������δǩ�ա�26��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTA_DEST_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTA_DEST_CD_POD, --����������ǩ�ա�27��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTA_DEST_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTA_DEST_CH_NPOD, --�����ֽ�δǩ�ա�28��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTA_DEST_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTA_DEST_CH_POD --�����ֽ���ǩ�ա�29��
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
												 0) AIR_COD_CD_UR_POD, --���˻�����ջ���������ǩ�ա�1��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_CH_UR_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_CH_UR_POD, --���˻�����ջ����ֽ���ǩ�ա�2��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_DPAY_WO_COD_RCV_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_DPAY_WO_COD_RCV_POD, --���˵������Ӧ����Ӧ�մ��ջ�����ǩ�ա�3��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_OTH_PAY_WO_COD_RCV_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_OTH_PAY_WO_COD_RCV_POD, --��������Ӧ����Ӧ�մ��ջ�����ǩ�ա�4��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_POD_NWO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_POD_NWO, --����ǩ��ʱδ�������ջ��5��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_POD_WO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_POD_WO, --����ǩ��ʱ�Ѻ������ջ��6��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_UPD_NWO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_UPD_NWO, --���˷�ǩ��ʱδ�������ջ��7��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_COD_UPD_WO,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_COD_UPD_WO, --���˷�ǩ��ʱ�Ѻ������ջ��8��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_DR_WO_COD_RCV_POD, --Ԥ�տ��˴����Ӧ�մ��ջ�����ǩ�ա�9��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_DR_WO_DEST_RCVO_NPOD, --Ԥ�տ��˴����01Ӧ�յ����˷�δǩ�ա�10��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_DR_WO_DEST_RCVO_POD, --Ԥ�տ��˴����01Ӧ�յ����˷���ǩ�ա�11��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_DR_WO_DEST_RCVT_NPOD, --Ԥ�տ��˴����02Ӧ�յ����˷�δǩ�ա�12��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.AIR_DR_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) AIR_DR_WO_DEST_RCVT_POD, --Ԥ�տ��˴����02Ӧ�յ����˷���ǩ�ա�13��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_COST_WO_DEST_RCVO_NPOD, --Ӧ���������ɱ���01Ӧ�յ����˷�δǩ�ա�14��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_COST_WO_DEST_RCVO_POD, --Ӧ���������ɱ���01Ӧ�յ����˷���ǩ�ա�15��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_COST_WO_DEST_RCVT_NPOD, --Ӧ���������ɱ���02Ӧ�յ����˷�δǩ�ա�16��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_COST_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_COST_WO_DEST_RCVT_POD, --Ӧ���������ɱ���02Ӧ�յ����˷���ǩ�ա�17��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_OTH_PAY_WO_DEST_RCVO_NPOD, --����Ӧ����01Ӧ�յ����˷�δǩ�ա�18��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVO_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_OTH_PAY_WO_DEST_RCVO_POD, --����Ӧ����01Ӧ�յ����˷���ǩ�ա�19��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_OTH_PAY_WO_DEST_RCVT_NPOD, --����Ӧ����02Ӧ�յ����˷�δǩ�ա�20��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.APWR_OTH_PAY_WO_DEST_RCVT_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) APWR_OTH_PAY_WO_DEST_RCVT_POD, --����Ӧ����02Ӧ�յ����˷���ǩ�ա�21��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROA_DEST_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROA_DEST_CD_NPOD, --��������δǩ�ա�22��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROA_DEST_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROA_DEST_CD_POD, --����������ǩ�ա�23��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROA_DEST_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROA_DEST_CH_NPOD, --�����ֽ�δǩ�ա�24��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.UROA_DEST_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) UROA_DEST_CH_POD, --�����ֽ���ǩ�ա�25��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTA_DEST_CD_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTA_DEST_CD_NPOD, --��������δǩ�ա�26��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTA_DEST_CD_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTA_DEST_CD_POD, --����������ǩ�ա�27��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTA_DEST_CH_NPOD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTA_DEST_CH_NPOD, --�����ֽ�δǩ�ա�28��
										 NVL(SUM(DECODE(T.BUSINESS_CASE,
																		PKG_STL_VCH_COMMON.URTA_DEST_CH_POD,
																		NVL(AMOUNT, 0),
																		0)),
												 0) URTA_DEST_CH_POD --�����ֽ���ǩ�ա�29��
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
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NAFI',
																				'ƾ֤����ʼ�����������±�������쳣:' || SQLERRM);
		
			--����ʧ�ܱ�־
			RETURN FALSE;
		
	END FUNC_VOUCHER_NAFI;

	-----------------------------------------------------------------
	-- �˴��ջ�������
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_RETURN_COD(P_PERIOD     VARCHAR2,
																	 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
																	 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
																	 ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
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
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				P_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_RETURN_COD',
																				'ƾ֤���ɴ��ջ��������쳣:' || SQLERRM);
		
			--����ʧ�ܱ�־
			RETURN FALSE;
		
	END FUNC_VOUCHER_RETURN_COD;

	-----------------------------------------------------------------
	-- ������±������
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_LDD(P_PERIOD     VARCHAR2,
														P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
														P_END_DATE   DATE -- ����ʱ�� 2012-12-22
														) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
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
								 0) LAND_COST_AGENCY_GEN, --��������Ӧ������
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COST_AGENCY_CFM,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COST_AGENCY_CFM, --��������Ӧ���ɱ�ȷ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COST_AGENCY_NCFM,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COST_AGENCY_NCFM, --��������Ӧ���ɱ���ȷ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COST_OTHER_CONFIRM,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COST_OTHER_CONFIRM, --����Ӧ���ɱ�ȷ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COST_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COST_PAY_APPLY, --�����ɱ���������
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_OTH_ENTRY,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_OTH_ENTRY, --���������Ӧ��¼��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_OTH_RCV_CH,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_OTH_RCV_CH, --�������������Ӧ���ֽ�
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_OTH_RCV_CD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_OTH_RCV_CD, --�������������Ӧ������
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_CH_POD, --����仹����ջ����ֽ���ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_CD_POD, --����仹����ջ���������ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_POD_WO_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_POD_WO_COD, --�����ǩ��ʱ�Ѻ������ջ���
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_NPOD_WO_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_NPOD_WO_COD, --����䷴ǩ��ʱ�Ѻ������ջ���
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_CH_NPOD, --����仹����ջ����ֽ�δǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_CD_NPOD, --����仹����ջ�������δǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_WO_AGENCY_PAY_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_WO_AGENCY_PAY_POD, --�����Ӧ����Ӧ�մ��ջ�����ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_WO_OTH_PAY_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_WO_OTH_PAY_COD, --���������Ӧ����Ӧ�մ��ջ�����ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_WO_AGENCY_PAY_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_WO_AGENCY_PAY_NPOD, --����䵽�����Ӧ����Ӧ�մ��ջ���δǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_WO_OTH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_WO_OTH_NPOD, --���������Ӧ����Ӧ�մ��ջ���δǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_BDR_WO_OTH_RCV,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_BDR_WO_OTH_RCV, --���˳�����Ӧ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_UR_DEST_CH_NPOD, --�����ֽ�δǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_UR_DEST_CD_NPOD, --��������δǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_UR_DEST_CH_POD, --�����ֽ���ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_UR_DEST_CD_POD, --����������ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_AGENCY_WO_DEST_RCV_POD, --Ӧ������ɱ���Ӧ�յ����˷���ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_NP,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_AGENCY_WO_DEST_RCV_NP, --Ӧ���������ɱ���Ӧ�յ����˷�δǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_OT_WO_DEST_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_OT_WO_DEST_RCV_POD, --����Ӧ����Ӧ�յ����˷���ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_DEST_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_OTH_WO_DEST_RCV_NPOD, --����Ӧ����Ӧ�յ����˷�δǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_OTH_RCV,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_OTH_WO_OTH_RCV, --����Ӧ��������Ӧ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_DEST_RCV_POD, --Ԥ�����������Ӧ�յ����˷���ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_DEST_RCV_NPOD, --Ԥ�����������Ӧ�յ����˷�δǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_CH,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_CH, --Ԥ�����������ֽ�
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_CD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_CD, --Ԥ��������������
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_WO_OTHER_RCV,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_WO_OTHER_RCV, --Ԥ���������������Ӧ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_WO_COD_RCV_POD, --Ԥ�����������Ӧ�մ��ջ�����ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_WO_COD_RCV_NPOD, --Ԥ�����������Ӧ�մ��ջ���δǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_PAY_APPLY,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_PAY_APPLY, --�������Ԥ�ո�������
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_APT_COM,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_APT_COM, --Ԥ������乫˾
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_APT_WO_COM_PAY,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_APT_WO_COM_PAY, --Ԥ����Ӧ������乫˾
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_APT_WO_OTH_PAY,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_APT_WO_OTH_PAY --Ԥ��������Ӧ��
				FROM STV.T_STL_MVS T
			 WHERE T.PERIOD >= V_BEGIN_PERIOD
						 AND T.PERIOD < V_END_PERIOD
						 AND T.AMOUNT <> 0
						 AND T.PRODUCT_CODE = PKG_STL_COMMON.PRODUCT_CODE_PKG --�����
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
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_AFR',
																				'ƾ֤����������±�������쳣:' || SQLERRM);
		
			--����ʧ�ܱ�־
			RETURN FALSE;
		
	END FUNC_VOUCHER_LDD;

	-----------------------------------------------------------------
	-- ����������±������
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_LDI(P_PERIOD     VARCHAR2,
														P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
														P_END_DATE   DATE -- ����ʱ�� 2012-12-22
														) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
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
								 0) LAND_COD_CH_POD, --����仹����ջ����ֽ���ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_CD_POD, --����仹����ջ���������ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_POD_NWO_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_POD_NWO_COD, --�����ǩ��ʱδ�������ջ���
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_NPOD_NWO_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_NPOD_NWO_COD, --����䷴ǩ��ʱδ�������ջ���
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_POD_WO_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_POD_WO_COD, --�����ǩ��ʱ�Ѻ������ջ���
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_NPOD_WO_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_NPOD_WO_COD, --����䷴ǩ��ʱ�Ѻ������ջ���
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_WO_AGENCY_PAY_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_WO_AGENCY_PAY_POD, --����䵽�����Ӧ����Ӧ�մ��ջ�����ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_WO_OTH_PAY_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_WO_OTH_PAY_COD, --���������Ӧ����Ӧ�մ��ջ�����ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_UR_DEST_CH_NPOD, --�����ֽ�δǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_UR_DEST_CD_NPOD, --��������δǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_UR_DEST_CH_POD, --�����ֽ���ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_UR_DEST_CD_POD, --����������ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_AGENCY_WO_DEST_RCV_POD, --Ӧ���������ɱ���Ӧ�յ����˷���ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_NP,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_AGENCY_WO_DEST_RCV_NP, --Ӧ���������ɱ���Ӧ�յ����˷�δǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_OT_WO_DEST_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_OT_WO_DEST_RCV_POD, --����Ӧ����Ӧ�յ����˷���ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_DEST_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_OTH_WO_DEST_RCV_NPOD, --����Ӧ����Ӧ�յ����˷�δǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_DEST_RCV_POD, --Ԥ�����������Ӧ�յ����˷���ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_DEST_RCV_NPOD, --Ԥ�����������Ӧ�յ����˷�δǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_WO_COD_RCV_POD --Ԥ�����������Ӧ�մ��ջ�����ǩ��
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
								 0) LAND_COD_CH_POD, --����仹����ջ����ֽ���ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_CD_POD, --����仹����ջ���������ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_POD_NWO_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_POD_NWO_COD, --�����ǩ��ʱδ�������ջ���
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_NPOD_NWO_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_NPOD_NWO_COD, --����䷴ǩ��ʱδ�������ջ���
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_POD_WO_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_POD_WO_COD, --�����ǩ��ʱ�Ѻ������ջ���
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_NPOD_WO_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_NPOD_WO_COD, --����䷴ǩ��ʱ�Ѻ������ջ���
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_WO_AGENCY_PAY_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_WO_AGENCY_PAY_POD, --����䵽�����Ӧ����Ӧ�մ��ջ�����ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_COD_WO_OTH_PAY_COD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_COD_WO_OTH_PAY_COD, --���������Ӧ����Ӧ�մ��ջ�����ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_UR_DEST_CH_NPOD, --�����ֽ�δǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_UR_DEST_CD_NPOD, --��������δǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_UR_DEST_CH_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_UR_DEST_CH_POD, --�����ֽ���ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_UR_DEST_CD_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_UR_DEST_CD_POD, --����������ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_AGENCY_WO_DEST_RCV_POD, --Ӧ���������ɱ���Ӧ�յ����˷���ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_AGENCY_WO_DEST_RCV_NP,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_AGENCY_WO_DEST_RCV_NP, --Ӧ���������ɱ���Ӧ�յ����˷�δǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_OT_WO_DEST_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_OT_WO_DEST_RCV_POD, --����Ӧ����Ӧ�յ����˷���ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_PR_OTH_WO_DEST_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_PR_OTH_WO_DEST_RCV_NPOD, --����Ӧ����Ӧ�յ����˷�δǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_DEST_RCV_POD, --Ԥ�����������Ӧ�յ����˷���ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_DEST_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_DEST_RCV_NPOD, --Ԥ�����������Ӧ�յ����˷�δǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_DR_WO_COD_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_DR_WO_COD_RCV_POD --Ԥ�����������Ӧ�մ��ջ�����ǩ��
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
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_AFI',
																				'ƾ֤��������������±�������쳣:' || SQLERRM);
		
			--����ʧ�ܱ�־
			RETURN FALSE;
		
	END FUNC_VOUCHER_LDI;

	-----------------------------------------------------------------
	-- Ӫҵ�������±������
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_LWO(P_PERIOD     VARCHAR2,
														P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
														P_END_DATE   DATE -- ����ʱ�� 2012-12-22
														) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
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
								 0) CUST_DR_ORIG_LAND_RCV_NPOD, --Ԥ�տͻ�����Ӧ��ʼ���˷�δǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CUST_DR_ORIG_LAND_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CUST_DR_ORIG_LAND_RCV_POD, --Ԥ�տͻ�����Ӧ��ʼ���˷���ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_WO_ORIG_LAND_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_WO_ORIG_LAND_RCV_POD, --�������ʼ��Ӧ����ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.CLAIM_WO_ORIG_LAND_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) CLAIM_WO_ORIG_LAND_RCV_NPOD, --�������ʼ��Ӧ��δǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_LAND_RCV_WO_CLAIM_PAY,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_LAND_RCV_WO_CLAIM_PAY, --Ӧ���������СƱӦ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_LAND_RCV_WO_CUST_DR,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_LAND_RCV_WO_CUST_DR, --Ԥ�տͻ�����СƱӦ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.REFUND_WO_ORIG_LAND_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) REFUND_WO_ORIG_LAND_RCV_POD, --���˷ѳ���ʼ��Ӧ����ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.REFUND_WO_ORIG_LAND_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) REFUND_WO_ORIG_LAND_RCV_NPOD, --���˷ѳ���ʼ��Ӧ��δǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_CLAIM_WO_ORIG_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_CLAIM_WO_ORIG_RCV_POD, --��������ʼ��Ӧ����ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_CLAIM_WO_ORIG_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_CLAIM_WO_ORIG_RCV_NPOD, --��������ʼ��Ӧ��δǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.OR_RCV_WO_LAND_CLAIM_PAY,
														NVL(AMOUNT, 0),
														0)),
								 0) OR_RCV_WO_LAND_CLAIM_PAY, --���Ӧ�������СƱӦ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_REFUND_WO_ORIG_RCV_POD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_REFUND_WO_ORIG_RCV_POD, --������˷ѳ�ʼ��Ӧ����ǩ��
						 NVL(SUM(DECODE(T.BUSINESS_CASE,
														PKG_STL_VCH_COMMON.LAND_REFUND_WO_ORIG_RCV_NPOD,
														NVL(AMOUNT, 0),
														0)),
								 0) LAND_REFUND_WO_ORIG_RCV_NPOD --������˷ѳ�ʼ��Ӧ��δǩ��
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
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_BEGIN_PERIOD || '~' ||
																				V_END_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_LWO',
																				'ƾ֤����Ӫҵ�������±�������쳣:' || SQLERRM);
		
			--����ʧ�ܱ�־
			RETURN FALSE;
		
	END FUNC_VOUCHER_LWO;

END PKG_STL_VCH_MONTHLY;
/
