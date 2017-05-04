CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_APT IS

	-- ==============================================================
	-- Author  : ZBW
	-- Created : 2013-11-06
	-- Purpose : ÿ��ƾ֤Ԥ��
	-- ==============================================================

	-----------------------------------------------------------------
	-- Ԥ����������ϸ
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
																		 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
																		 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
																		 ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

END PKG_STL_VCH_DAILY_APT;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_APT IS

	-----------------------------------------------------------------
	-- Ԥ����������ϸ
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
																		 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
																		 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
																		 ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
	 IS
	BEGIN
	
		--1)Ԥ�����չ�˾  
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
						 P_PERIOD, --�ڼ�
						 PKG_STL_VCH_COMMON.APT_AIR_COMPANY, --ҵ�񳡾�
						 AP.CUSTOMER_CODE, --�ͻ�����
						 AP.CUSTOMER_NAME, --�ͻ�����
						 AP.CUSTOMER_TYPE, --�ͻ�����
						 AP.APPLY_ORG_CODE, --ʼ�����ű��� 
						 AP.APPLY_ORG_NAME, --ʼ����������
						 AP.APPLY_ORG_CODE, --���ﲿ�ű���
						 AP.APPLY_ORG_NAME, --���ﲿ������
						 NULL, --�˵���
						 AP.ADVANCES_NO, --���ݱ��
						 AP.ACCOUNT_DATE, --�������
						 AP.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__UF, --��������
						 AP.BILL_TYPE, --����������
						 AP.AMOUNT, --���
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PKG_STL_COMMON.PRODUCT_CODE_AF --��Ʒ����
				FROM STL.T_STL_BILL_ADVANCED_PAYMENT AP
			 WHERE AP.BILL_TYPE = PKG_STL_COMMON.ADVANCED_PAYMENT_BILL_TYPE_AIR ---����
						 AND
						 AP.AUDIT_STATUS = PKG_STL_COMMON.ADVANCED_PAY_AUDIT_STATUS_AA
						 AND AP.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
						 AND AP.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND AP.ACCOUNT_DATE < P_END_DATE;
	
		--2)Ԥ����Ӧ�����չ�˾ 
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
						 P_PERIOD, --�ڼ�
						 PKG_STL_VCH_COMMON.APT_WO_AIR_PAY, --ҵ�񳡾�
						 AP.CUSTOMER_CODE, --�ͻ�����
						 AP.CUSTOMER_NAME, --�ͻ�����
						 AP.CUSTOMER_TYPE, --�ͻ�����
						 AP.APPLY_ORG_CODE, --ʼ�����ű��� 
						 AP.APPLY_ORG_NAME, --ʼ����������
						 AP.APPLY_ORG_CODE, --���ﲿ�ű���
						 AP.APPLY_ORG_NAME, --���ﲿ������
						 NULL, --�˵���
						 AP.ADVANCES_NO, --���ݱ��
						 WO.ACCOUNT_DATE, --�������
						 AP.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__UF, --��������
						 AP.BILL_TYPE, --����������
						 WO.AMOUNT, --���
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PKG_STL_COMMON.PRODUCT_CODE_AF --��Ʒ����
				FROM STL.T_STL_BILL_WRITEOFF WO
				JOIN STL.T_STL_BILL_ADVANCED_PAYMENT AP
					ON AP.ADVANCES_NO = WO.BEGIN_NO
						 AND AP.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
				JOIN STL.T_STL_BILL_PAYABLE PAY
					ON PAY.PAYABLE_NO = WO.END_NO
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_AP -- Ԥ����Ӧ��
						 AND
						 AP.BILL_TYPE = PKG_STL_COMMON.ADVANCED_PAYMENT_BILL_TYPE_AIR --����
						 AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR --Ӧ����Ϊ�����˷�
						 AND AP.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
						 AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;
	
		--3)Ԥ����Ӧ����������Ӧ�� 
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
						 P_PERIOD, --�ڼ�
						 PKG_STL_VCH_COMMON.APT_WO_OTH_PAY, --ҵ�񳡾�
						 AP.CUSTOMER_CODE, --�ͻ�����
						 AP.CUSTOMER_NAME, --�ͻ�����
						 AP.CUSTOMER_TYPE, --�ͻ�����
						 AP.APPLY_ORG_CODE, --ʼ�����ű��� 
						 AP.APPLY_ORG_NAME, --ʼ����������
						 AP.APPLY_ORG_CODE, --���ﲿ�ű���
						 AP.APPLY_ORG_NAME, --���ﲿ������
						 NULL, --�˵���
						 AP.ADVANCES_NO, --���ݱ��
						 WO.ACCOUNT_DATE, --�������
						 AP.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__UF, --��������
						 AP.BILL_TYPE, --����������
						 WO.AMOUNT, --���
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PKG_STL_COMMON.PRODUCT_CODE_AF --��Ʒ����
				FROM STL.T_STL_BILL_WRITEOFF WO
				JOIN STL.T_STL_BILL_ADVANCED_PAYMENT AP
					ON AP.ADVANCES_NO = WO.BEGIN_NO
						 AND AP.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
				JOIN STL.T_STL_BILL_PAYABLE PAY
					ON PAY.PAYABLE_NO = WO.END_NO
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_AP -- Ԥ����Ӧ��
						 AND
						 AP.BILL_TYPE = PKG_STL_COMMON.ADVANCED_PAYMENT_BILL_TYPE_AIR --����
						 AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER --Ӧ����Ϊ���������˷�
						 AND AP.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
						 AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO
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
																				'Ԥ����������ϸ' || '�쳣');
		
			--����ʧ�ܱ�־
			RETURN FALSE;
	END;

END PKG_STL_VCH_DAILY_APT;
/
