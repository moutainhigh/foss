CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_BD IS

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

END PKG_STL_VCH_DAILY_BD;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_BD IS

	-----------------------------------------------------------------
	-- ƾ֤������������ϸ
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
																		 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
																		 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
																		 ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
	 IS
	BEGIN
	
		--1) ������ʧ-���˳�Ӧ��ʼ���˷���ǩ��
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
						 PKG_STL_VCH_COMMON.BD_ORIG_RCV_POD, --ҵ�񳡾�
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_CODE��RCV.ORIG_ORG_CODE), --ʼ�����ű��� 
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_NAME��RCV.ORIG_ORG_NAME), --ʼ����������
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_DEST_ORG_CODE��RCV.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_DEST_ORG_NAME��RCV.DEST_ORG_NAME), --���ﲿ������
						 DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
										'N/A',
										RCV.SOURCE_BILL_NO,
										RCV.WAYBILL_NO), --�˵���
						 RCV.RECEIVABLE_NO, --���ݱ��
						 WO.ACCOUNT_DATE, --��������
						 RCV.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
						 RCV.BILL_TYPE, --����������
						 WO.AMOUNT,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 RCV.PRODUCT_CODE --��Ʒ����
				FROM STL.T_STL_BILL_WRITEOFF WO --������
				JOIN STL.T_STL_BILL_RECEIVABLE RCV
					ON RCV.RECEIVABLE_NO = WO.END_NO --Ӧ�յ�
				JOIN STL.T_STL_BILL_BAD_ACCOUNT ACC
					ON ACC.BAD_DEBAT_BILL_NO = WO.BEGIN_NO --���˵�
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_BR --���˳�Ӧ��
						 AND
						 ACC.BILL_TYPE = PKG_STL_COMMON.BAD_ACCOUNT_BILL_TYPE_BADDEDTS --������ʧ
						 AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
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
	
		--2) ������ʧ-���˳�Ӧ�յ����˷���ǩ��
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
						 PKG_STL_VCH_COMMON.BD_DEST_RCV_POD, --ҵ�񳡾�
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_CODE��RCV.ORIG_ORG_CODE), --ʼ�����ű��� 
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_NAME��RCV.ORIG_ORG_NAME), --ʼ����������
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_DEST_ORG_CODE��RCV.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_DEST_ORG_NAME��RCV.DEST_ORG_NAME), --���ﲿ������
						 DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
										'N/A',
										RCV.SOURCE_BILL_NO,
										RCV.WAYBILL_NO), --�˵���
						 RCV.RECEIVABLE_NO, --���ݱ��
						 WO.ACCOUNT_DATE, --��������
						 RCV.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
						 RCV.BILL_TYPE, --����������
						 WO.AMOUNT,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 RCV.PRODUCT_CODE --��Ʒ����
				FROM STL.T_STL_BILL_WRITEOFF WO --������
				JOIN STL.T_STL_BILL_RECEIVABLE RCV
					ON RCV.RECEIVABLE_NO = WO.END_NO --Ӧ�յ�
				JOIN STL.T_STL_BILL_BAD_ACCOUNT ACC
					ON ACC.BAD_DEBAT_BILL_NO = WO.BEGIN_NO --���˵�
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_BR --���˳�Ӧ��
						 AND
						 ACC.BILL_TYPE = PKG_STL_COMMON.BAD_ACCOUNT_BILL_TYPE_BADDEDTS --������ʧ
						 AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
																	 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
																	 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,
																	 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL -- ������������Ӧ��
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
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'FUNC_PROCESS_DAILY_DETAIL',
																				'ƾ֤������������ϸ' || '�쳣');
		
			--����ʧ�ܱ�־
			RETURN FALSE;
	END;

END PKG_STL_VCH_DAILY_BD;
/
