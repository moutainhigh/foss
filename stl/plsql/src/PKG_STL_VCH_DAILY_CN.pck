CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_CN IS

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

END PKG_STL_VCH_DAILY_CN;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_CN IS

	-----------------------------------------------------------------
	-- ƾ֤������������ϸ
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
																		 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
																		 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
																		 ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
	 IS
	BEGIN
	
		--1) ���񲹾�-ʼ�����񲹾ȸ�������
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
						 PKG_STL_VCH_COMMON.CN_ORIG_PAY_APPLY, --ҵ�񳡾� ��ʼ��
						 PAY.CUSTOMER_CODE, --�ͻ�����
						 PAY.CUSTOMER_NAME, --�ͻ�����
						 PAY.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_ORIG_ORG_CODE��PAY.ORIG_ORG_CODE), --ʼ�����ű��� 
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_ORIG_ORG_NAME��PAY.ORIG_ORG_NAME), --ʼ����������
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_CODE��PAY.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_NAME��PAY.DEST_ORG_NAME), --���ﲿ������
						 DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
										'N/A',
										PAY.SOURCE_BILL_NO,
										PAY.WAYBILL_NO), --�˵���
						 PAY.PAYABLE_NO, --���ݱ��
						 PAY.ACCOUNT_DATE, --��������
						 PAY.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
						 PAY.BILL_TYPE, --����������
						 DP.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.YES, -1, 1), --���,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PAY.PRODUCT_CODE --��Ʒ����
				FROM STL.T_STL_BILL_PAYABLE PAY --Ӧ����
				JOIN STL.T_STL_BILL_PAYMENT_D DP
					ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO --�����ϸ
				LEFT JOIN STL.T_STL_BILL_PAYMENT PMT
					ON DP.PAYMENT_NO = PMT.PAYMENT_NO --���
			 WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_COMPENSATION --���񲹾�
						 AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --ʼ��
						-- AND PMT.PAYMENT_NO=PAY.PAYMENT_NO 2013-08-01 modified by zbw
						 AND DP.PAY_AMOUNT > 0
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --�Ǻ쵥
						 AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND PMT.ACCOUNT_DATE < P_END_DATE;
	
		--2) ���񲹾�-������񲹾ȸ�������
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
						 PKG_STL_VCH_COMMON.CN_DEST_PAY_APPLY, --ҵ�񳡾� ������
						 PAY.CUSTOMER_CODE, --�ͻ�����
						 PAY.CUSTOMER_NAME, --�ͻ�����
						 PAY.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_ORIG_ORG_CODE��PAY.ORIG_ORG_CODE), --ʼ�����ű��� 
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_ORIG_ORG_NAME��PAY.ORIG_ORG_NAME), --ʼ����������
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_CODE��PAY.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_NAME��PAY.DEST_ORG_NAME), --���ﲿ������
						 DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
										'N/A',
										PAY.SOURCE_BILL_NO,
										PAY.WAYBILL_NO), --�˵���
						 PAY.PAYABLE_NO, --���ݱ��
						 PAY.ACCOUNT_DATE, --��������
						 PAY.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
						 PAY.BILL_TYPE, --����������
						 DP.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.YES, -1, 1), --���,   
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PAY.PRODUCT_CODE --��Ʒ����
				FROM STL.T_STL_BILL_PAYABLE PAY --Ӧ����
				JOIN STL.T_STL_BILL_PAYMENT_D DP
					ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO --�����ϸ
				LEFT JOIN STL.T_STL_BILL_PAYMENT PMT
					ON DP.PAYMENT_NO = PMT.PAYMENT_NO --���
			 WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_COMPENSATION --���񲹾�
						 AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --����
						-- AND PMT.PAYMENT_NO=PAY.PAYMENT_NO 2013-08-01 modified by zbw
						 AND DP.PAY_AMOUNT > 0
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.NO --�Ǻ쵥
						 AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND PMT.ACCOUNT_DATE < P_END_DATE
						 AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
		-- issue-3303
	
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

END PKG_STL_VCH_DAILY_CN;
/
