CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_CUST_DR IS

	-- ==============================================================
	-- Author  : ZBW
	-- Created : 2013-05-04
	-- Purpose : ÿ��ƾ֤����
	-- ==============================================================

	-----------------------------------------------------------------
	-- ƾ֤������������ϸ
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
																		 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
																		 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
																		 ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

END PKG_STL_VCH_DAILY_CUST_DR;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_CUST_DR IS

	-----------------------------------------------------------------
	-- ƾ֤������������ϸ
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
																		 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
																		 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
																		 ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
	 IS
	BEGIN
	
		--1)Ӫҵ��Ԥ�տͻ�  Ԥ�տͻ��ֽ�
		------��Ԥ�յ��ļ���������ͳ�ƣ�Ԥ�յ��ĸ��ʽΪ���ֽ�Ԥ�յ���������Ϊ��ר�߿ͻ�
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
						 P_PERIOD, --�ڼ�
						 PKG_STL_VCH_COMMON.CUST_DR_CH, --ҵ�񳡾�
						 DEP.CUSTOMER_CODE, --�ͻ�����
						 DEP.CUSTOMER_NAME, --�ͻ�����
						 DEP.CUSTOMER_TYPE, --�ͻ�����
						 DEP.CREATE_ORG_CODE, --ʼ�����ű���
						 DEP.CREATE_ORG_NAME, --ʼ����������
						 DEP.CREATE_ORG_CODE, --���ﲿ�ű���
						 DEP.CREATE_ORG_NAME, --���ﲿ������
						 '', --�˵���
						 DEP.DEPOSIT_RECEIVED_NO, --���ݱ��
						 DEP.ACCOUNT_DATE, --�������
						 DEP.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__US, --��������
						 DEP.BILL_TYPE, --����������
						 DEP.AMOUNT, --���
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PKG_STL_COMMON.PRODUCT_CODE_FLF
				FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DEP
			 WHERE DEP.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---֧����ʽΪ�ֽ�
						 AND DEP.TRANSPORT_TYPE =
						 PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
						 AND DEP.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND DEP.ACCOUNT_DATE < P_END_DATE;
	
		COMMIT;
		--2)Ӫҵ��Ԥ�տͻ�  Ԥ�տͻ�����
		------��Ԥ�յ��ļ���������ͳ�ƣ�Ԥ�յ��ĸ��ʽΪ��
		------���п�����㡢֧Ʊ������֧����Ԥ�յ���������Ϊ��ר�߿ͻ�
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
						 P_PERIOD, --�ڼ�
						 PKG_STL_VCH_COMMON.CUST_DR_CD, --ҵ�񳡾�
						 DEP.CUSTOMER_CODE, --�ͻ�����
						 DEP.CUSTOMER_NAME, --�ͻ�����
						 DEP.CUSTOMER_TYPE, --�ͻ�����
						 DEP.CREATE_ORG_CODE, --ʼ�����ű���
						 DEP.CREATE_ORG_NAME, --ʼ����������
						 DEP.CREATE_ORG_CODE, --���ﲿ�ű���
						 DEP.CREATE_ORG_NAME, --���ﲿ������
						 '', --�˵���
						 DEP.DEPOSIT_RECEIVED_NO, --���ݱ��
						 DEP.ACCOUNT_DATE, --�������
						 DEP.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__US, --��������
						 DEP.BILL_TYPE, --����������
						 DEP.AMOUNT, --���
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
							PKG_STL_COMMON.PAYMENT_TYPE_ONLINE) ---֧����ʽΪ���п�����㡢֧Ʊ������֧��
						 AND DEP.TRANSPORT_TYPE =
						 PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
						 AND DEP.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND DEP.ACCOUNT_DATE < P_END_DATE;
		COMMIT;
		--3)Ӫҵ��Ԥ�տͻ�  Ԥ�տͻ���Ӧ�յ����˷�δǩ��
		/* ���������ļ���������ͳ�ƣ�
    �����жϺ���ʱ��Ӧ�յ���ǩ��״̬Ϊδǩ�գ�
    Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�
    ����ƫ�ߴ���Ӧ�յ������˵������Ӧ�գ�
    Ԥ�յ���������Ϊ��ר�߿ͻ���ȡ������ϸ�Ľ�������ʱ���ɵĸ���Ҳͳ��*/
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
						 P_PERIOD, --�ڼ�
						 PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_NPOD, --ҵ�񳡾�
						 RE.CUSTOMER_CODE, --�ͻ�����
						 RE.CUSTOMER_NAME, --�ͻ�����
						 RE.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_ORIG_ORG_CODE��RE.ORIG_ORG_CODE), --�������ű���
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_ORIG_ORG_NAME��RE.ORIG_ORG_NAME), --ʼ����������
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_DEST_ORG_CODE��RE.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_DEST_ORG_NAME��RE.DEST_ORG_NAME), --���ﲿ������
						 RE.WAYBILL_NO, --�˵���
						 RE.RECEIVABLE_NO, --���ݱ��
						 WO.ACCOUNT_DATE, --�������
						 RE.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
						 RE.BILL_TYPE, --����������
						 WO.AMOUNT, --���
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
						 PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --��Դ���˵�
						 AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
																				PKG_STL_COMMON.POD__POD_TYPE__POD,
																				1,
																				PKG_STL_COMMON.POD__POD_TYPE__UPD,
																				-1)),
														 0) POD
										FROM STL.T_STL_POD T
									 WHERE T.WAYBILL_NO = RE.WAYBILL_NO
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --����ʱδǩ��
						 AND RE.BILL_TYPE IN
						 (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
									PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
									PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�
                                                                                                                                                                                                   ����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
						 AND DR.TRANSPORT_TYPE =
						 PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
						
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;
		COMMIT;
		--4)Ӫҵ��Ԥ�տͻ�  Ԥ�տͻ���Ӧ�յ����˷���ǩ��
		/* ���������ļ���������ͳ�ƣ�
    �����жϺ���ʱ��Ӧ�յ���ǩ��״̬Ϊ��ǩ�գ�
    Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ�գ�
    Ԥ�յ���������Ϊ��ר�߿ͻ���ȡ������ϸ�Ľ�������ʱ���ɵĸ���Ҳͳ��*/
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
						 P_PERIOD, --�ڼ�
						 PKG_STL_VCH_COMMON.CUST_DR_DEST_RCV_POD, --ҵ�񳡾�
						 RE.CUSTOMER_CODE, --�ͻ�����
						 RE.CUSTOMER_NAME, --�ͻ�����
						 RE.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_ORIG_ORG_CODE��RE.ORIG_ORG_CODE), --�������ű���
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_ORIG_ORG_NAME��RE.ORIG_ORG_NAME), --ʼ����������
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_DEST_ORG_CODE��RE.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_DEST_ORG_NAME��RE.DEST_ORG_NAME),
						 DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
										'N/A',
										RE.SOURCE_BILL_NO,
										RE.WAYBILL_NO), --�˵���
						 RE.RECEIVABLE_NO, --���ݱ��
						 WO.ACCOUNT_DATE, --�������
						 RE.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
						 RE.BILL_TYPE, --����������
						 WO.AMOUNT, --���
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
						 PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --��Դ���˵�
						 AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
																				PKG_STL_COMMON.POD__POD_TYPE__POD,
																				1,
																				PKG_STL_COMMON.POD__POD_TYPE__UPD,
																				-1)),
														 0) POD
										FROM STL.T_STL_POD T
									 WHERE T.WAYBILL_NO = RE.WAYBILL_NO
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --����ʱδǩ��
						 AND RE.BILL_TYPE IN
						 (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
									PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
									PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) /*   Ӧ�յ��ĵ���������Ϊ����Ӧ�ա�
                                                                                                                                                                                                   ����ƫ�ߴ���Ӧ�յ������˵������Ӧ��*/
						 AND DR.TRANSPORT_TYPE =
						 PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
						
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;
		COMMIT;
		--5)Ӫҵ��Ԥ�տͻ�  Ԥ�տͻ���Ӧ��ʼ���˷�δǩ��
		/* ���������ļ���������ͳ�ƣ�
    �����жϺ���ʱ��Ӧ�յ���ǩ��״̬Ϊδǩ�գ�
    Ӧ�յ��ĵ���������Ϊʼ��Ӧ�գ�
    Ԥ�յ���������Ϊ��ר�߿ͻ���ȡ������ϸ�Ľ�������ʱ���ɵĸ���Ҳͳ��*/
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
						 P_PERIOD, --�ڼ�
						 PKG_STL_VCH_COMMON.CUST_DR_ORIG_RCV_NPOD, --ҵ�񳡾�
						 RE.CUSTOMER_CODE, --�ͻ�����
						 RE.CUSTOMER_NAME, --�ͻ�����
						 RE.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_ORIG_ORG_CODE��RE.ORIG_ORG_CODE), --�������ű���
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_ORIG_ORG_NAME��RE.ORIG_ORG_NAME), --ʼ����������
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_DEST_ORG_CODE��RE.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_DEST_ORG_NAME��RE.DEST_ORG_NAME),
						 DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
										'N/A',
										RE.SOURCE_BILL_NO,
										RE.WAYBILL_NO), --�˵���
						 RE.RECEIVABLE_NO, --���ݱ��
						 WO.ACCOUNT_DATE, --�������
						 RE.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
						 RE.BILL_TYPE, --����������
						 WO.AMOUNT, --���
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
						 PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --��Դ���˵�
						 AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
																				PKG_STL_COMMON.POD__POD_TYPE__POD,
																				1,
																				PKG_STL_COMMON.POD__POD_TYPE__UPD,
																				-1)),
														 0) POD
										FROM STL.T_STL_POD T
									 WHERE T.WAYBILL_NO = RE.WAYBILL_NO
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --����ʱδǩ��
						 AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN ---Ӧ�յ��ĵ���������Ϊʼ��Ӧ��
						 AND DR.TRANSPORT_TYPE =
						 PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
						
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;
		COMMIT;
		--6)Ӫҵ��Ԥ�տͻ�  Ԥ�տͻ���Ӧ��ʼ���˷���ǩ��
		/* ���������ļ���������ͳ�ƣ�
    �����жϺ���ʱ��Ӧ�յ���ǩ��״̬Ϊ��ǩ�գ�
    Ӧ�յ��ĵ���������Ϊʼ��Ӧ�գ�
    Ԥ�յ���������Ϊ��ר�߿ͻ���ȡ������ϸ�Ľ�������ʱ���ɵĸ���Ҳͳ��*/
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
						 P_PERIOD, --�ڼ�
						 PKG_STL_VCH_COMMON.CUST_DR_ORIG_RCV_POD, --ҵ�񳡾�
						 RE.CUSTOMER_CODE, --�ͻ�����
						 RE.CUSTOMER_NAME, --�ͻ�����
						 RE.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_ORIG_ORG_CODE��RE.ORIG_ORG_CODE), --�������ű���
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_ORIG_ORG_NAME��RE.ORIG_ORG_NAME), --ʼ����������
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_DEST_ORG_CODE��RE.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(RE.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RE.EXPRESS_DEST_ORG_NAME��RE.DEST_ORG_NAME),
						 DECODE(NVL(RE.WAYBILL_NO, 'N/A'),
										'N/A',
										RE.SOURCE_BILL_NO,
										RE.WAYBILL_NO), --�˵���
						 RE.RECEIVABLE_NO, --���ݱ��
						 WO.ACCOUNT_DATE, --�������
						 RE.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
						 RE.BILL_TYPE, --����������
						 WO.AMOUNT, --���
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
						 PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --��Դ���˵�
						 AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
																				PKG_STL_COMMON.POD__POD_TYPE__POD,
																				1,
																				PKG_STL_COMMON.POD__POD_TYPE__UPD,
																				-1)),
														 0) POD
										FROM STL.T_STL_POD T
									 WHERE T.WAYBILL_NO = RE.WAYBILL_NO
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --����ʱ��ǩ��
						 AND RE.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN ---Ӧ�յ��ĵ���������Ϊʼ��Ӧ��
						 AND DR.TRANSPORT_TYPE =
						 PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
						
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;
		COMMIT;
		--7)Ӫҵ��Ԥ�տͻ�  ʼ����Ԥ�ո�������
		/* ������ļ���������ͳ�ƣ���������ĸ�����ϸ����������Ϊ�� Ԥ�յ���
    Ԥ�յ���������Ϊ��ר�߿ͻ���ͳ�ƶ�Ӧ�ĸ����ϸ���֮��*/
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
						 P_PERIOD, --�ڼ�
						 PKG_STL_VCH_COMMON.CUST_DR_ORIG_PAY_APPLY, --ҵ�񳡾�
						 DR.CUSTOMER_CODE, --�ͻ�����
						 DR.CUSTOMER_NAME, --�ͻ�����
						 DR.CUSTOMER_TYPE, --�ͻ�����
						 DR.CREATE_ORG_CODE, --ʼ�����ű���
						 DR.CREATE_ORG_NAME, --ʼ����������
						 DR.CREATE_ORG_CODE, --���ﲿ�ű���
						 DR.CREATE_ORG_NAME, --���ﲿ������
						 DECODE(NVL(PAY_D.WAYBILL_NO, 'N/A'),
										'N/A',
										PAY_D.SOURCE_BILL_NO,
										PAY_D.WAYBILL_NO), --�˵���
						 DR.DEPOSIT_RECEIVED_NO, --���ݱ��
						 PAY.ACCOUNT_DATE, --�������
						 PAY.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__FK, --��������
						 PAY.BILL_TYPE, --����������
						 PAY_D.PAY_AMOUNT *
						 DECODE(PAY.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1), --���
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
						 PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LC --��������Ϊר�߿ͻ�
						 AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --Ԥ�յ�Ϊ�Ǻ쵥
						 AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND PAY.ACCOUNT_DATE < P_END_DATE;
	
		COMMIT;
	
		RETURN TRUE;
	
	EXCEPTION
		WHEN OTHERS THEN
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'FUNC_PROCESS_DAILY_DETAIL',
																				'Ԥ�տͻ���������ϸ' || '�쳣');
		
			--����ʧ�ܱ�־
			RETURN FALSE;
	END;

END PKG_STL_VCH_DAILY_CUST_DR;
/
