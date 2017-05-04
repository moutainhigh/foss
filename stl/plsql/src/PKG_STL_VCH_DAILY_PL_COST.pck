CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_PL_COST IS

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

END PKG_STL_VCH_DAILY_PL_COST;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_PL_COST IS

	-----------------------------------------------------------------
	-- ƾ֤������������ϸ
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
																		 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
																		 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
																		 ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
	 IS
	BEGIN
	
		--1) ƫ�ߴ���ɱ�--Ӧ��ƫ�ߴ���ɱ���Ӧ�յ����˷���ǩ��  
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
						 PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_POD, --ҵ�񳡾������������ļ���������ͳ�ƣ�ȡ����������ϸ��������ϸ�е�Ӧ�յ�����������Ϊʼ��Ӧ��
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 RCV.ORIG_ORG_CODE, --ʼ�����ű��� 
						 RCV.ORIG_ORG_NAME, --ʼ����������
						 RCV.DEST_ORG_CODE, --���ﲿ�ű���
						 RCV.DEST_ORG_NAME, --���ﲿ������
						 NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --�˵���
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
					ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�
				JOIN STL.T_STL_BILL_PAYABLE PAY
					ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
						 AND
						 PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE --ƫ�ߴ���ɱ�
						 AND RCV.BILL_TYPE IN
						 (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
									PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ�
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
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;
	
		--2) ƫ�ߴ���ɱ�--Ӧ��ƫ�ߴ���ɱ���Ӧ�յ����˷�δǩ��  
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
						 PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCV_NPOD, --ҵ�񳡾������������ļ���������ͳ�ƣ�ȡ����������ϸ��������ϸ�е�Ӧ�յ�����������Ϊʼ��Ӧ��
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 RCV.ORIG_ORG_CODE, --ʼ�����ű��� 
						 RCV.ORIG_ORG_NAME, --ʼ����������
						 RCV.DEST_ORG_CODE, --���ﲿ�ű���
						 RCV.DEST_ORG_NAME, --���ﲿ������
						 NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --�˵���
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
					ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�
				JOIN STL.T_STL_BILL_PAYABLE PAY
					ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
						 AND
						 PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE --ƫ�ߴ���ɱ�
						 AND RCV.BILL_TYPE IN
						 (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
									PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ�
						 AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
																				PKG_STL_COMMON.POD__POD_TYPE__POD,
																				1,
																				PKG_STL_COMMON.POD__POD_TYPE__UPD,
																				-1)),
														 0) POD
										FROM STL.T_STL_POD T
									 WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
						 AND WO.AMOUNT <> 0
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;
	
		--3) ƫ�ߴ���ɱ�--�ⷢ��¼��
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
						 PKG_STL_VCH_COMMON.PL_COST_VECH, --ҵ�񳡾������������ļ���������ͳ�ƣ�ȡ����������ϸ��������ϸ�е�Ӧ�յ�����������Ϊʼ��Ӧ��
						 PAY.CUSTOMER_CODE, --�ͻ�����
						 PAY.CUSTOMER_NAME, --�ͻ�����
						 PAY.CUSTOMER_TYPE, --�ͻ�����
						 PAY.ORIG_ORG_CODE, --ʼ�����ű��� 
						 PAY.ORIG_ORG_NAME, --ʼ����������
						 PAY.DEST_ORG_CODE, --���ﲿ�ű���
						 PAY.DEST_ORG_NAME, --���ﲿ������
						 DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
										'N/A',
										PAY.SOURCE_BILL_NO,
										PAY.WAYBILL_NO), --�˵���
						 PAY.PAYABLE_NO, --���ݱ��
						 PAY.ACCOUNT_DATE, --��������
						 PAY.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
						 PAY.BILL_TYPE, --����������
						 PAY.AMOUNT,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PAY.PRODUCT_CODE --��Ʒ����
				FROM STL.T_STL_BILL_PAYABLE PAY
			 WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE --ƫ�ߴ���ɱ�
						 AND PAY.AMOUNT <> 0
						 AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND PAY.ACCOUNT_DATE < P_END_DATE;
	
		--4) ƫ�ߴ���ɱ�--ƫ�ߴ���ɱ�ȷ��
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
						 PKG_STL_VCH_COMMON.PL_COST_CONFIRM, --ҵ�񳡾������������ļ���������ͳ�ƣ�ȡ����������ϸ��������ϸ�е�Ӧ�յ�����������Ϊʼ��Ӧ��
						 PAY.CUSTOMER_CODE, --�ͻ�����
						 PAY.CUSTOMER_NAME, --�ͻ�����
						 PAY.CUSTOMER_TYPE, --�ͻ�����
						 PAY.ORIG_ORG_CODE, --ʼ�����ű��� 
						 PAY.ORIG_ORG_NAME, --ʼ����������
						 PAY.DEST_ORG_CODE, --���ﲿ�ű���
						 PAY.DEST_ORG_NAME, --���ﲿ������
						 DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
										'N/A',
										PAY.SOURCE_BILL_NO,
										PAY.WAYBILL_NO), --�˵���
						 PAY.PAYABLE_NO, --���ݱ��
						 PAY.ACCOUNT_DATE, --��������
						 PAY.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
						 PAY.BILL_TYPE, --����������
						 PAY.AMOUNT,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PAY.PRODUCT_CODE --��Ʒ����
				FROM STL.T_STL_BILL_PAYABLE PAY
				JOIN STL.T_STL_POD T
					ON T.WAYBILL_NO = PAY.WAYBILL_NO
			 WHERE T.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__POD --ǩ������
						 AND
						 PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE --ƫ�ߴ���ɱ�
						 AND PAY.AMOUNT <> 0
						 AND PAY.ACCOUNT_DATE < T.POD_DATE
						 AND T.POD_DATE >= P_BEGIN_DATE
						 AND T.POD_DATE < P_END_DATE;
	
		--5) ƫ�ߴ���ɱ�--ƫ�ߴ���ɱ���ȷ��
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
						 PKG_STL_VCH_COMMON.PL_COST_NOT_CONFIRM, --ҵ�񳡾������������ļ���������ͳ�ƣ�ȡ����������ϸ��������ϸ�е�Ӧ�յ�����������Ϊʼ��Ӧ��
						 PAY.CUSTOMER_CODE, --�ͻ�����
						 PAY.CUSTOMER_NAME, --�ͻ�����
						 PAY.CUSTOMER_TYPE, --�ͻ�����
						 PAY.ORIG_ORG_CODE, --ʼ�����ű��� 
						 PAY.ORIG_ORG_NAME, --ʼ����������
						 PAY.DEST_ORG_CODE, --���ﲿ�ű���
						 PAY.DEST_ORG_NAME, --���ﲿ������
						 DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
										'N/A',
										PAY.SOURCE_BILL_NO,
										PAY.WAYBILL_NO), --�˵���
						 PAY.PAYABLE_NO, --���ݱ��
						 PAY.ACCOUNT_DATE, --��������
						 PAY.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
						 PAY.BILL_TYPE, --����������
						 PAY.AMOUNT,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PAY.PRODUCT_CODE --��Ʒ����
				FROM STL.T_STL_BILL_PAYABLE PAY
				JOIN STL.T_STL_POD T
					ON T.WAYBILL_NO = PAY.WAYBILL_NO
			 WHERE T.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__UPD --��ǩ������
						 AND
						 PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE --ƫ�ߴ���ɱ�
						 AND PAY.AMOUNT <> 0
						 AND PAY.ACCOUNT_DATE < T.POD_DATE
						 AND T.POD_DATE >= P_BEGIN_DATE
						 AND T.POD_DATE < P_END_DATE;
	
		--6) ƫ�ߴ���ɱ�--ƫ�ߴ���ɱ���������
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
						 PKG_STL_VCH_COMMON.PL_COST_PAY_APPLY, --ҵ�񳡾������������ļ���������ͳ�ƣ�ȡ����������ϸ��������ϸ�е�Ӧ�յ�����������Ϊʼ��Ӧ��
						 PAY.CUSTOMER_CODE, --�ͻ�����
						 PAY.CUSTOMER_NAME, --�ͻ�����
						 PAY.CUSTOMER_TYPE, --�ͻ�����
						 PAY.ORIG_ORG_CODE, --ʼ�����ű��� 
						 PAY.ORIG_ORG_NAME, --ʼ����������
						 PAY.DEST_ORG_CODE, --���ﲿ�ű���
						 PAY.DEST_ORG_NAME, --���ﲿ������
						 DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
										'N/A',
										PAY.SOURCE_BILL_NO,
										PAY.WAYBILL_NO), --�˵���
						 PAY.PAYABLE_NO, --���ݱ��
						 PAY.ACCOUNT_DATE, --��������
						 PAY.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
						 PAY.BILL_TYPE, --����������
						 DP.PAY_AMOUNT * DECODE(PMT.IS_RED_BACK,
																		PKG_STL_COMMON.IS_RED_BACK_YES,
																		-1,
																		PKG_STL_COMMON.IS_RED_BACK_NO,
																		1),
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
			 WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE --ƫ�ߴ���ɱ�
						 AND DP.PAY_AMOUNT <> 0
						--AND PMT.PAYMENT_NO = PAY.PAYMENT_NO  2013-08-01 modified by zbw
             					 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --�Ǻ쵥
						 AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND PMT.ACCOUNT_DATE < P_END_DATE;
	
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

END PKG_STL_VCH_DAILY_PL_COST;
/
