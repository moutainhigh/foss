CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_PL_COST IS

	-- ==============================================================
	-- Author  : ZBW
	-- Created : 2013-11-06
	-- Purpose : ÿ��ƾ֤ƫ�ߴ���ɱ�
	-- ==============================================================

	-----------------------------------------------------------------
	-- ƫ�ߴ���ɱ���������ϸ
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
																		 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
																		 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
																		 ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

END PKG_STL_VCH_DAILY_PL_COST;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_PL_COST IS

	-----------------------------------------------------------------
	-- ƫ�ߴ���ɱ���������ϸ
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����YYYYMMDD������20121221
																		 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
																		 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
																		 ) RETURN BOOLEAN -- ���ؽ�� TRUE:�ɹ���FALSE:ʧ��
	 IS
	BEGIN
	
		--01) Ӧ��ƫ�ߴ���ɱ���01Ӧ�յ����˷���ǩ��
		--02) Ӧ��ƫ�ߴ���ɱ���01Ӧ�յ����˷�δǩ��
		--03) Ӧ��ƫ�ߴ���ɱ���02Ӧ�յ����˷���ǩ��
		--04) Ӧ��ƫ�ߴ���ɱ���02Ӧ�յ����˷�δǩ��
		--05) �ⷢ��¼��
		--06) ƫ�ߴ���ɱ�ȷ��
		--07) ƫ�ߴ���ɱ���ȷ��
		--08) ƫ�ߴ���ɱ���������
		--09) ���� Ӧ��ƫ�ߴ���ɱ���01Ӧ�յ����˷���ǩ��
		--10) ���� Ӧ��ƫ�ߴ���ɱ���01Ӧ�յ����˷�δǩ��
		--11) ���� Ӧ��ƫ�ߴ���ɱ���02Ӧ�յ����˷���ǩ��
		--12) ���� Ӧ��ƫ�ߴ���ɱ���02Ӧ�յ����˷�δǩ��
		--13) P Ӧ��ƫ�ߴ���ɱ���01Ӧ�յ����˷���ǩ��
		--14) P Ӧ��ƫ�ߴ���ɱ���01Ӧ�յ����˷�δǩ��
		--15) P Ӧ��ƫ�ߴ���ɱ���02Ӧ�յ����˷���ǩ��
		--16) P Ӧ��ƫ�ߴ���ɱ���02Ӧ�յ����˷�δǩ��
		--17) ����Ӧ���ɱ�ȷ��
	
		--01) Ӧ��ƫ�ߴ���ɱ���01Ӧ�յ����˷���ǩ��
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
						 PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_POD,
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_CODE,
										RCV.ORIG_ORG_CODE), --�������ű��� 
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_NAME,
										RCV.ORIG_ORG_NAME), --ʼ����������
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_DEST_ORG_CODE,
										RCV.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_DEST_ORG_NAME,
										RCV.DEST_ORG_NAME), --���ﲿ������
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
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --��ǩ��
						 AND WO.AMOUNT <> 0
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��01
						 AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
	
		--02) Ӧ��ƫ�ߴ���ɱ���01Ӧ�յ����˷�δǩ��
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
						 PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_NPOD,
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_CODE,
										RCV.ORIG_ORG_CODE), --�������ű��� 
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_NAME,
										RCV.ORIG_ORG_NAME), --ʼ����������
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_DEST_ORG_CODE,
										RCV.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_DEST_ORG_NAME,
										RCV.DEST_ORG_NAME), --���ﲿ������
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
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --δǩ��
						 AND WO.AMOUNT <> 0
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��01
						 AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
	
		--03) Ӧ��ƫ�ߴ���ɱ���02Ӧ�յ����˷���ǩ��
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
						 PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_POD, --ҵ�񳡾������������ļ���������ͳ�ƣ�ȡ����������ϸ��������ϸ�е�Ӧ�յ�����������Ϊʼ��Ӧ��
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_CODE,
										RCV.ORIG_ORG_CODE), --�������ű��� 
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_NAME,
										RCV.ORIG_ORG_NAME), --ʼ����������
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_DEST_ORG_CODE,
										RCV.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_DEST_ORG_NAME,
										RCV.DEST_ORG_NAME), --���ﲿ������
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
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --��ǩ��
						 AND WO.AMOUNT <> 0
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��02
						 AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
	
		--04) Ӧ��ƫ�ߴ���ɱ���02Ӧ�յ����˷�δǩ��
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
						 PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_NPOD,
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_CODE,
										RCV.ORIG_ORG_CODE), --�������ű��� 
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_NAME,
										RCV.ORIG_ORG_NAME), --ʼ����������
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_DEST_ORG_CODE,
										RCV.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_DEST_ORG_NAME,
										RCV.DEST_ORG_NAME), --���ﲿ������
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
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --δǩ��
						 AND WO.AMOUNT <> 0
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��02
						 AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
		--05) �ⷢ��¼��
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
						 PKG_STL_VCH_COMMON.PL_COST_ENTRY,
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
	
		--06) ƫ�ߴ���ɱ�ȷ��
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
						 PKG_STL_VCH_COMMON.PL_COST_CONFIRM,
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
		--07) ƫ�ߴ���ɱ���ȷ��
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
						 PKG_STL_VCH_COMMON.PL_COST_NCONFIRM,
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
	
		--08) ƫ�ߴ���ɱ���������
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
						 PKG_STL_VCH_COMMON.PL_COST_PAY_APPLY,
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
			 WHERE PAY.BILL_TYPE IN
						 (PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE,
							PKG_STL_COMMON.PAYABLE_BILL_TYPE_PO) --ƫ�ߴ���ɱ���ƫ������Ӧ��
						 AND DP.PAY_AMOUNT <> 0
						--AND PMT.PAYMENT_NO = PAY.PAYMENT_NO  2013-08-01 MODIFIED BY ZBW
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --�Ǻ쵥
						 AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND PMT.ACCOUNT_DATE < P_END_DATE;
	
		--09) ���� Ӧ��ƫ�ߴ���ɱ���01Ӧ�յ����˷���ǩ��
	
		INSERT INTO STV.T_STL_NDVDI
			(ID,
			 PERIOD,
			 BUSINESS_CASE,
			 CUSTOMER_CODE,
			 CUSTOMER_NAME,
			 CUSTOMER_TYPE,
			 WAYBILL_NO,
			 BILL_NO,
			 ACCOUNT_DATE,
			 BUSINESS_DATE,
			 BILL_PARENT_TYPE,
			 BILL_TYPE,
			 AMOUNT,
			 PRODUCT_CODE,
			 CREDIT_ORG_CODE,
			 CREDIT_ORG_NAME,
			 CREDIT_INVOICE_MARK,
			 CREDIT_ORG_TYPE,
			 DEBIT_ORG_CODE,
			 DEBIT_ORG_NAME,
			 DEBIT_INVOICE_MARK,
			 DEBIT_ORG_TYPE)
			SELECT SYS_GUID(), --ID
						 P_PERIOD, --�ڼ�
						 PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_POD,
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --�˵���
						 RCV.RECEIVABLE_NO, --���ݱ��
						 WO.ACCOUNT_DATE, --��������
						 RCV.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
						 RCV.BILL_TYPE, --����������
						 WO.AMOUNT,
						 RCV.PRODUCT_CODE, --��Ʒ����
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_CODE,
										PAY.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_NAME,
										PAY.DEST_ORG_NAME), --���ﲿ������
						 PKG_STL_COMMON.INVOICE_MARK_TWO,
						 PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_CODE,
										RCV.ORIG_ORG_CODE), --�������ű��� 
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_NAME,
										RCV.ORIG_ORG_NAME), --ʼ����������
						 PKG_STL_COMMON.INVOICE_MARK_ONE,
						 PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
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
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --��ǩ��
						 AND WO.AMOUNT <> 0
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��01
						 AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
	
		--10) ���� Ӧ��ƫ�ߴ���ɱ���01Ӧ�յ����˷�δǩ��
		INSERT INTO STV.T_STL_NDVDI
			(ID,
			 PERIOD,
			 BUSINESS_CASE,
			 CUSTOMER_CODE,
			 CUSTOMER_NAME,
			 CUSTOMER_TYPE,
			 WAYBILL_NO,
			 BILL_NO,
			 ACCOUNT_DATE,
			 BUSINESS_DATE,
			 BILL_PARENT_TYPE,
			 BILL_TYPE,
			 AMOUNT,
			 PRODUCT_CODE,
			 CREDIT_ORG_CODE,
			 CREDIT_ORG_NAME,
			 CREDIT_INVOICE_MARK,
			 CREDIT_ORG_TYPE,
			 DEBIT_ORG_CODE,
			 DEBIT_ORG_NAME,
			 DEBIT_INVOICE_MARK,
			 DEBIT_ORG_TYPE)
			SELECT SYS_GUID(), --ID
						 P_PERIOD, --�ڼ�
						 PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_NPOD,
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --�˵���
						 RCV.RECEIVABLE_NO, --���ݱ��
						 WO.ACCOUNT_DATE, --��������
						 RCV.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
						 RCV.BILL_TYPE, --����������
						 WO.AMOUNT,
						 RCV.PRODUCT_CODE, --��Ʒ����
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_CODE,
										PAY.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_NAME,
										PAY.DEST_ORG_NAME), --���ﲿ������
						 PKG_STL_COMMON.INVOICE_MARK_TWO,
						 PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_CODE,
										RCV.ORIG_ORG_CODE), --�������ű��� 
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_NAME,
										RCV.ORIG_ORG_NAME), --ʼ����������
						 PKG_STL_COMMON.INVOICE_MARK_ONE,
						 PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
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
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --δǩ��
						 AND WO.AMOUNT <> 0
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��01
						 AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
	
		--11) ���� Ӧ��ƫ�ߴ���ɱ���02Ӧ�յ����˷���ǩ��
		INSERT INTO STV.T_STL_NDVDI
			(ID,
			 PERIOD,
			 BUSINESS_CASE,
			 CUSTOMER_CODE,
			 CUSTOMER_NAME,
			 CUSTOMER_TYPE,
			 WAYBILL_NO,
			 BILL_NO,
			 ACCOUNT_DATE,
			 BUSINESS_DATE,
			 BILL_PARENT_TYPE,
			 BILL_TYPE,
			 AMOUNT,
			 PRODUCT_CODE,
			 CREDIT_ORG_CODE,
			 CREDIT_ORG_NAME,
			 CREDIT_INVOICE_MARK,
			 CREDIT_ORG_TYPE,
			 DEBIT_ORG_CODE,
			 DEBIT_ORG_NAME,
			 DEBIT_INVOICE_MARK,
			 DEBIT_ORG_TYPE)
			SELECT SYS_GUID(), --ID
						 P_PERIOD, --�ڼ�
						 PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_POD,
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --�˵���
						 RCV.RECEIVABLE_NO, --���ݱ��
						 WO.ACCOUNT_DATE, --��������
						 RCV.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
						 RCV.BILL_TYPE, --����������
						 WO.AMOUNT,
						 RCV.PRODUCT_CODE, --��Ʒ����
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_CODE,
										PAY.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_NAME,
										PAY.DEST_ORG_NAME), --���ﲿ������
						 PKG_STL_COMMON.INVOICE_MARK_TWO,
						 PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_CODE,
										RCV.ORIG_ORG_CODE), --�������ű��� 
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_NAME,
										RCV.ORIG_ORG_NAME), --ʼ����������
						 PKG_STL_COMMON.INVOICE_MARK_TWO,
						 PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
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
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --��ǩ��
						 AND WO.AMOUNT <> 0
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��02
						 AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
	
		--12) ���� Ӧ��ƫ�ߴ���ɱ���02Ӧ�յ����˷�δǩ��
		INSERT INTO STV.T_STL_NDVDI
			(ID,
			 PERIOD,
			 BUSINESS_CASE,
			 CUSTOMER_CODE,
			 CUSTOMER_NAME,
			 CUSTOMER_TYPE,
			 WAYBILL_NO,
			 BILL_NO,
			 ACCOUNT_DATE,
			 BUSINESS_DATE,
			 BILL_PARENT_TYPE,
			 BILL_TYPE,
			 AMOUNT,
			 PRODUCT_CODE,
			 CREDIT_ORG_CODE,
			 CREDIT_ORG_NAME,
			 CREDIT_INVOICE_MARK,
			 CREDIT_ORG_TYPE,
			 DEBIT_ORG_CODE,
			 DEBIT_ORG_NAME,
			 DEBIT_INVOICE_MARK,
			 DEBIT_ORG_TYPE)
			SELECT SYS_GUID(), --ID
						 P_PERIOD, --�ڼ�
						 PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_NPOD,
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --�˵���
						 RCV.RECEIVABLE_NO, --���ݱ��
						 WO.ACCOUNT_DATE, --��������
						 RCV.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
						 RCV.BILL_TYPE, --����������
						 WO.AMOUNT,
						 RCV.PRODUCT_CODE, --��Ʒ����
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_CODE,
										PAY.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_NAME,
										PAY.DEST_ORG_NAME), --���ﲿ������
						 PKG_STL_COMMON.INVOICE_MARK_TWO,
						 PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_DEST,
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_CODE,
										RCV.ORIG_ORG_CODE), --�������ű��� 
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_NAME,
										RCV.ORIG_ORG_NAME), --ʼ����������
						 PKG_STL_COMMON.INVOICE_MARK_TWO,
						 PKG_STL_VCH_COMMON.VOUCHER_ORG_TYPE_ORIG
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
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --δǩ��
						 AND WO.AMOUNT <> 0
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��02
						 AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
	
		--13) P Ӧ��ƫ�ߴ���ɱ���01Ӧ�յ����˷���ǩ��
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
						 PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_PODP,
						 PAY.CUSTOMER_CODE, --�ͻ�����
						 PAY.CUSTOMER_NAME, --�ͻ�����
						 PAY.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_ORIG_ORG_CODE,
										PAY.ORIG_ORG_CODE), --�������ű��� 
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_ORIG_ORG_NAME,
										PAY.ORIG_ORG_NAME), --ʼ����������
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_CODE,
										PAY.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_NAME,
										PAY.DEST_ORG_NAME), --���ﲿ������
						 NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
						 PAY.PAYABLE_NO, --���ݱ��
						 WO.ACCOUNT_DATE, --��������
						 PAY.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
						 PAY.BILL_TYPE, --����������
						 WO.AMOUNT,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PAY.PRODUCT_CODE --��Ʒ����
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
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --��ǩ��
						 AND WO.AMOUNT <> 0
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��01
						 AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
	
		--14) P Ӧ��ƫ�ߴ���ɱ���01Ӧ�յ����˷�δǩ��
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
						 PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVO_NPODP,
						 PAY.CUSTOMER_CODE, --�ͻ�����
						 PAY.CUSTOMER_NAME, --�ͻ�����
						 PAY.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_ORIG_ORG_CODE,
										PAY.ORIG_ORG_CODE), --�������ű��� 
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_ORIG_ORG_NAME,
										PAY.ORIG_ORG_NAME), --ʼ����������
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_CODE,
										PAY.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_NAME,
										PAY.DEST_ORG_NAME), --���ﲿ������
						 NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
						 PAY.PAYABLE_NO, --���ݱ��
						 WO.ACCOUNT_DATE, --��������
						 PAY.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
						 PAY.BILL_TYPE, --����������
						 WO.AMOUNT,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PAY.PRODUCT_CODE --��Ʒ����
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
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --δǩ��
						 AND WO.AMOUNT <> 0
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��01
						 AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE;
	
		--15) P Ӧ��ƫ�ߴ���ɱ���02Ӧ�յ����˷���ǩ��
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
						 PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_PODP, --ҵ�񳡾������������ļ���������ͳ�ƣ�ȡ����������ϸ��������ϸ�е�Ӧ�յ�����������Ϊʼ��Ӧ��
						 PAY.CUSTOMER_CODE, --�ͻ�����
						 PAY.CUSTOMER_NAME, --�ͻ�����
						 PAY.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_ORIG_ORG_CODE,
										PAY.ORIG_ORG_CODE), --�������ű��� 
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_ORIG_ORG_NAME,
										PAY.ORIG_ORG_NAME), --ʼ����������
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_CODE,
										PAY.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_NAME,
										PAY.DEST_ORG_NAME), --���ﲿ������
						 NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
						 PAY.PAYABLE_NO, --���ݱ��
						 WO.ACCOUNT_DATE, --��������
						 PAY.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
						 PAY.BILL_TYPE, --����������
						 WO.AMOUNT,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PAY.PRODUCT_CODE --��Ʒ����
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
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --��ǩ��
						 AND WO.AMOUNT <> 0
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��02
						 AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
	
		--16) P Ӧ��ƫ�ߴ���ɱ���02Ӧ�յ����˷�δǩ��
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
						 PKG_STL_VCH_COMMON.PL_COST_WO_DEST_RCVT_NPODP,
						 PAY.CUSTOMER_CODE, --�ͻ�����
						 PAY.CUSTOMER_NAME, --�ͻ�����
						 PAY.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_ORIG_ORG_CODE,
										PAY.ORIG_ORG_CODE), --�������ű��� 
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_ORIG_ORG_NAME,
										PAY.ORIG_ORG_NAME), --ʼ����������
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_CODE,
										PAY.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_NAME,
										PAY.DEST_ORG_NAME), --���ﲿ������
						 NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
						 PAY.PAYABLE_NO, --���ݱ��
						 WO.ACCOUNT_DATE, --��������
						 PAY.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
						 PAY.BILL_TYPE, --����������
						 WO.AMOUNT,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PAY.PRODUCT_CODE --��Ʒ����
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
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --δǩ��
						 AND WO.AMOUNT <> 0
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE --Ӧ��02
						 AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
	
		--17)����Ӧ���ɱ�ȷ��
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
						 PKG_STL_VCH_COMMON.PL_COST_OP_CRM,
						 PAY.CUSTOMER_CODE, --�ͻ�����
						 PAY.CUSTOMER_NAME, --�ͻ�����
						 PAY.CUSTOMER_TYPE, --�ͻ�����
						 PAY.ORIG_ORG_CODE, --ʼ�����ű��� 
						 PAY.ORIG_ORG_NAME, --ʼ����������
						 PAY.DEST_ORG_CODE, --���ﲿ�ű���
						 PAY.DEST_ORG_NAME, --���ﲿ������
						 NVL(PAY.WAYBILL_NO, PAY.SOURCE_BILL_NO), --�˵���
						 PAY.PAYABLE_NO, --���ݱ��
						 PAY.ACCOUNT_DATE, --�������
						 PAY.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
						 PAY.BILL_TYPE, --����������
						 PAY.AMOUNT, --���
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 NVL(PAY.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_PLF) --��Ʒ����
				FROM STL.T_STL_BILL_PAYABLE PAY
			 WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_PO --ƫ������Ӧ��
						 AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND PAY.ACCOUNT_DATE < P_END_DATE --Ӧ��02
						 AND PAY.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_TWO;
	
		RETURN TRUE;
	
	EXCEPTION
		WHEN OTHERS THEN
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'FUNC_PROCESS_DAILY_DETAIL',
																				'ƫ�ߴ���ɱ���������ϸ' || '�쳣');
		
			--����ʧ�ܱ�־
			RETURN FALSE;
	END;

END PKG_STL_VCH_DAILY_PL_COST;
/
