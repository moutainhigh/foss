CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_DE IS

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

END PKG_STL_VCH_DAILY_DE;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_DE IS

	-----------------------------------------------------------------
	-- ƾ֤������������ϸ
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
																		 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
																		 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
																		 ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
	 IS
	BEGIN
	
		--1)�����ֽ�  ����������ͳ���˵��ֽ��տ�Ľ��Ҹ��ʽΪ�ֽ�
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
						 PKG_STL_VCH_COMMON.DE_CH, --ҵ�񳡾�
						 CASH.CUSTOMER_CODE, --�ͻ�����
						 CASH.CUSTOMER_NAME, --�ͻ�����
						 CASH.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(CASH.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										CASH.EXPRESS_ORIG_ORG_CODE��CASH.ORIG_ORG_CODE), --ʼ�����ű��� 
						 DECODE(CASH.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										CASH.EXPRESS_ORIG_ORG_NAME��CASH.ORIG_ORG_NAME), --ʼ����������
						 DECODE(CASH.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										CASH.EXPRESS_DEST_ORG_CODE��CASH.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(CASH.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										CASH.EXPRESS_DEST_ORG_NAME��CASH.DEST_ORG_NAME), --���ﲿ������
						 DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
										'N/A',
										CASH.SOURCE_BILL_NO,
										CASH.WAYBILL_NO), --�˵���
						 CASH.CASH_COLLECTION_NO, --���ݱ��
						 CASH.ACCOUNT_DATE, --�������
						 CASH.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --��������
						 CASH.BILL_TYPE, --����������
						 CASH.AMOUNT, --���
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 CASH.PRODUCT_CODE --��Ʒ����
				FROM STL.T_STL_BILL_CASH_COLLECTION CASH
			 WHERE CASH.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH ---֧����ʽΪ�ֽ�
						 AND CASH.SOURCE_BILL_TYPE =
						 PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --��Դ���˵�
						 AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND CASH.ACCOUNT_DATE < P_END_DATE;
	
		--1)�������п�  ����������ͳ���˵��ֽ��տ�Ľ��Ҹ��ʽΪ���п�
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
						 PKG_STL_VCH_COMMON.DE_CD, --ҵ�񳡾�
						 CASH.CUSTOMER_CODE, --�ͻ�����
						 CASH.CUSTOMER_NAME, --�ͻ�����
						 CASH.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(CASH.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										CASH.EXPRESS_ORIG_ORG_CODE��CASH.ORIG_ORG_CODE), --ʼ�����ű��� 
						 DECODE(CASH.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										CASH.EXPRESS_ORIG_ORG_NAME��CASH.ORIG_ORG_NAME), --ʼ����������
						 DECODE(CASH.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										CASH.EXPRESS_DEST_ORG_CODE��CASH.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(CASH.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										CASH.EXPRESS_DEST_ORG_NAME��CASH.DEST_ORG_NAME), --���ﲿ������
						 DECODE(NVL(CASH.WAYBILL_NO, 'N/A'),
										'N/A',
										CASH.SOURCE_BILL_NO,
										CASH.WAYBILL_NO), --�˵���
						 CASH.CASH_COLLECTION_NO, --���ݱ��
						 CASH.ACCOUNT_DATE, --�������
						 CASH.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --��������
						 CASH.BILL_TYPE, --����������
						 CASH.AMOUNT, --���
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 CASH.PRODUCT_CODE --��Ʒ����
				FROM STL.T_STL_BILL_CASH_COLLECTION CASH
			 WHERE CASH.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CARD ---֧����ʽΪ���п�
						 AND CASH.SOURCE_BILL_TYPE =
						 PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --��Դ���˵�
						 AND CASH.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND CASH.ACCOUNT_DATE < P_END_DATE;
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

END PKG_STL_VCH_DAILY_DE;
/
