CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_SF IS

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

END PKG_STL_VCH_DAILY_SF;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_SF IS

	-----------------------------------------------------------------
	-- װж��
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
																		 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
																		 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
																		 ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
	 IS
	BEGIN
		--1)װж�Ѹ�������  
		---������ļ���������ͳ�ƣ����������Ӧ��������������Ϊ����ѣ���Ӧ�ĸ������ϸ���֮��
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
						 PKG_STL_VCH_COMMON.SF_PAY_APPLY, --ҵ�񳡾�
						 PAY.CUSTOMER_CODE, --�ͻ�����
						 PAY.CUSTOMER_NAME, --�ͻ�����
						 PAY.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(PA.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PA.EXPRESS_ORIG_ORG_CODE��PA.ORIG_ORG_CODE), --ʼ�����ű��� 
						 DECODE(PA.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PA.EXPRESS_ORIG_ORG_NAME��PA.ORIG_ORG_NAME), --ʼ����������
						 DECODE(PA.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PA.EXPRESS_DEST_ORG_CODE��PA.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(PA.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PA.EXPRESS_DEST_ORG_NAME��PA.DEST_ORG_NAME), --���ﲿ������
						 DECODE(NVL(PA.WAYBILL_NO, 'N/A'),
										'N/A',
										PA.SOURCE_BILL_NO,
										PA.WAYBILL_NO), --�˵���
						 PAY.PAYMENT_NO, --���ݱ��
						 PAY.ACCOUNT_DATE, --�������
						 PAY.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON. BILL_PARENT_TYPE__FK, --��������
						 PAY.BILL_TYPE, --����������
						 
						 --BUG-42069
						 PAY_D.PAY_AMOUNT * DECODE(PAY.IS_RED_BACK,
																			 PKG_STL_COMMON.IS_RED_BACK_YES,
																			 -1,
																			 PKG_STL_COMMON.IS_RED_BACK_NO,
																			 1), --���
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PA.PRODUCT_CODE --��Ʒ����
				FROM STL.T_STL_BILL_PAYMENT PAY
				JOIN STL.T_STL_BILL_PAYMENT_D PAY_D
					ON PAY.PAYMENT_NO = PAY_D.PAYMENT_NO
				JOIN STL.T_STL_BILL_PAYABLE PA
					ON PA.PAYABLE_NO = PAY_D.SOURCE_BILL_NO
						 AND PA.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
			
			 WHERE PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_SERVICE_FEE --�����
						 AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND PAY.ACCOUNT_DATE < P_END_DATE;
		RETURN TRUE;
	
	EXCEPTION
		WHEN OTHERS THEN
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'FUNC_PROCESS_DAILY_DETAIL',
																				'ƾ֤װж�Ѵ�������ϸ' || '�쳣');
		
			--����ʧ�ܱ�־
			RETURN FALSE;
	END;

END PKG_STL_VCH_DAILY_SF;
/
