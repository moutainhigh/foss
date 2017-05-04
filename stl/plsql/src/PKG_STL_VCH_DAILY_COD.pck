CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_COD IS

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

END PKG_STL_VCH_DAILY_COD;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_COD IS

	-----------------------------------------------------------------
	-- ƾ֤������������ϸ
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
																		 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
																		 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
																		 ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
	 IS
	BEGIN
	
		--1. Ӧ�����ջ����Ӧ�յ����˷���ǩ��
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
						 PKG_STL_VCH_COMMON.COD_DEST_RCV_POD, --ҵ�񳡾�:Ӧ�����ջ����Ӧ�յ����˷���ǩ��
						 WO.CUSTOMER_CODE, --�ͻ�����
						 PAY.CUSTOMER_NAME�� --�ͻ�����
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
										PAY.WAYBILL_NO), --PAY.WAYBILL_NO, --�˵���
						 WO.WRITEOFF_BILL_NO,
						 WO.ACCOUNT_DATE, --�������
						 PAY.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --Ӧ����
						 PAY.BILL_TYPE, --Ӧ�յ���������
						 WO.AMOUNT, --���
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
					ON WO.BEGIN_NO = RCV.RECEIVABLE_NO --Ӧ�յ�
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
				JOIN STL.T_STL_BILL_PAYABLE PAY
					ON WO.END_NO = PAY.PAYABLE_NO --Ӧ����
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
						 AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --���ջ���
						 AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST --����Ӧ��
						 AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
																				PKG_STL_COMMON.POD__POD_TYPE__POD,
																				1,
																				PKG_STL_COMMON.POD__POD_TYPE__UPD,
																				-1)),
														 0) POD
										FROM STL.T_STL_POD T
									 WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;
	
		--2. Ӧ�����ջ����Ӧ�յ����˷�δǩ��         
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
						 PKG_STL_VCH_COMMON.COD_DEST_RCV_NPOD, --ҵ�񳡾�:Ӧ�����ջ����Ӧ�յ����˷�δǩ��  
						 WO.CUSTOMER_CODE, --�ͻ�����
						 PAY.CUSTOMER_NAME�� --�ͻ�����
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
										PAY.WAYBILL_NO), --PAY.WAYBILL_NO, --�˵���
						 WO.WRITEOFF_BILL_NO,
						 WO.ACCOUNT_DATE, --�������
						 PAY.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --Ӧ����
						 PAY.BILL_TYPE, --Ӧ�յ���������
						 WO.AMOUNT, --���
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
					ON WO.BEGIN_NO = RCV.RECEIVABLE_NO --Ӧ�յ�
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
				JOIN STL.T_STL_BILL_PAYABLE PAY
					ON WO.END_NO = PAY.PAYABLE_NO --Ӧ����
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
						 AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --���ջ���
						 AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST --����Ӧ��
						 AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
																				PKG_STL_COMMON.POD__POD_TYPE__POD,
																				1,
																				PKG_STL_COMMON.POD__POD_TYPE__UPD,
																				-1)),
														 0) POD
										FROM STL.T_STL_POD T
									 WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;
	
		--3. Ӧ�����ջ����Ӧ��ʼ���˷���ǩ��
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
						 PKG_STL_VCH_COMMON.COD_ORIG_RCV_POD, --ҵ�񳡾�:Ӧ�����ջ����Ӧ��ʼ���˷���ǩ��
						 WO.CUSTOMER_CODE, --�ͻ�����
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
										PAY.WAYBILL_NO), --PAY.WAYBILL_NO, --�˵���
						 WO.WRITEOFF_BILL_NO,
						 WO.ACCOUNT_DATE, --�������
						 PAY.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --Ӧ����
						 PAY.BILL_TYPE, --Ӧ�յ���������
						 WO.AMOUNT, --���
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
					ON WO.BEGIN_NO = RCV.RECEIVABLE_NO --Ӧ�յ�
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
				JOIN STL.T_STL_BILL_PAYABLE PAY
					ON WO.END_NO = PAY.PAYABLE_NO
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
						 AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --���ջ���
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
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;
	
		--4. Ӧ�����ջ����Ӧ��ʼ���˷�δǩ��
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
						 PKG_STL_VCH_COMMON.COD_ORIG_RCV_NPOD, --ҵ�񳡾�:Ӧ�����ջ����Ӧ��ʼ���˷�δǩ��
						 WO.CUSTOMER_CODE, --�ͻ�����
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
										PAY.WAYBILL_NO), --PAY.WAYBILL_NO, --�˵���
						 WO.WRITEOFF_BILL_NO,
						 WO.ACCOUNT_DATE, --�������
						 PAY.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --Ӧ����
						 PAY.BILL_TYPE, --Ӧ�յ���������
						 WO.AMOUNT, --���
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
					ON WO.BEGIN_NO = RCV.RECEIVABLE_NO --Ӧ�յ�
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
				JOIN STL.T_STL_BILL_PAYABLE PAY
					ON WO.END_NO = PAY.PAYABLE_NO
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
						 AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --���ջ���
						 AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��Ӧ��
						 AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
																				PKG_STL_COMMON.POD__POD_TYPE__POD,
																				1,
																				PKG_STL_COMMON.POD__POD_TYPE__UPD,
																				-1)),
														 0) POD
										FROM STL.T_STL_POD T
									 WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;
	
		--5. ������ջ����ֽ�δǩ��
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
			SELECT SYS_GUID(),
						 P_PERIOD,
						 PKG_STL_VCH_COMMON.COD_UR_CH_NPOD, --ҵ�񳡾���������ջ����ֽ�δǩ��
						 RCV.CUSTOMER_CODE,
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
										RCV.WAYBILL_NO), --RCV.WAYBILL_NO,
						 WO.WRITEOFF_BILL_NO,
						 WO.ACCOUNT_DATE, --��������
						 RCV.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --Ӧ�յ�
						 RCV.BILL_TYPE, --��������
						 WO.AMOUNT,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 RCV.PRODUCT_CODE
				FROM STL.T_STL_BILL_WRITEOFF WO --������
				JOIN STL.T_STL_BILL_REPAYMENT RPMT
					ON RPMT.REPAYMENT_NO = WO.BEGIN_NO --���
				JOIN STL.T_STL_BILL_RECEIVABLE RCV
					ON RCV.RECEIVABLE_NO = WO.END_NO --Ӧ�յ�
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
			 WHERE RCV.IS_RED_BACK = RPMT.IS_RED_BACK
						 AND
						 WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --�����Ӧ��
						 AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_COD --���ջ���Ӧ��
						 AND RPMT.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH --�ֽ�
						 AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
																				PKG_STL_COMMON.POD__POD_TYPE__POD,
																				1,
																				PKG_STL_COMMON.POD__POD_TYPE__UPD,
																				-1)),
														 0) POD
										FROM STL.T_STL_POD T
									 WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;
	
		--6. ������ջ�������δǩ��
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
			SELECT SYS_GUID(),
						 P_PERIOD,
						 PKG_STL_VCH_COMMON.COD_UR_CD_NPOD, --ҵ�񳡾���������ջ�������δǩ��
						 RCV.CUSTOMER_CODE,
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
										RCV.WAYBILL_NO), --RCV.WAYBILL_NO,
						 WO.WRITEOFF_BILL_NO,
						 WO.ACCOUNT_DATE, --��������
						 RCV.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --Ӧ�յ�
						 RCV.BILL_TYPE, --��������
						 WO.AMOUNT,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 RCV.PRODUCT_CODE
				FROM STL.T_STL_BILL_WRITEOFF WO --������
				JOIN STL.T_STL_BILL_REPAYMENT RPMT
					ON RPMT.REPAYMENT_NO = WO.BEGIN_NO --���
				JOIN STL.T_STL_BILL_RECEIVABLE RCV
					ON RCV.RECEIVABLE_NO = WO.END_NO --Ӧ�յ�
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
			 WHERE RCV.IS_RED_BACK = RPMT.IS_RED_BACK
						 AND
						 WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --�����Ӧ��
						 AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_COD --���ջ���Ӧ��
						 AND RPMT.PAYMENT_TYPE IN
						 (PKG_STL_COMMON.PAYMENT_TYPE_CARD,
									PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
									PKG_STL_COMMON.PAYMENT_TYPE_NOTE,
									PKG_STL_COMMON.PAYMENT_TYPE_ONLINE) --���п�����㡢֧Ʊ������֧��
						 AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
																				PKG_STL_COMMON.POD__POD_TYPE__POD,
																				1,
																				PKG_STL_COMMON.POD__POD_TYPE__UPD,
																				-1)),
														 0) POD
										FROM STL.T_STL_POD T
									 WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;
	
		--7. Ӧ�����ջ����СƱӦ��
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
						 PKG_STL_VCH_COMMON.COD_WO_OR_RCV, --ҵ�񳡾�:Ӧ�����ջ����СƱӦ��
						 T3.CUSTOMER_CODE, --�ͻ�����
						 T3.CUSTOMER_NAME,
						 T3.CUSTOMER_TYPE,
						 T2.ORIG_ORG_CODE, --�������ű���
						 T2.ORIG_ORG_NAME, --���ﲿ��
						 T2.DEST_ORG_CODE, --�������ű���
						 T2.DEST_ORG_NAME, --���ﲿ��
						 DECODE(NVL(T2.WAYBILL_NO, 'N/A'),
										'N/A',
										T2.SOURCE_BILL_NO,
										T2.WAYBILL_NO), --T2.WAYBILL_NO, --�˵���
						 T2.RECEIVABLE_NO,
						 T1.ACCOUNT_DATE,
						 T1.ACCOUNT_DATE,
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --Ӧ��
						 T2.BILL_TYPE, -- СƱ
						 T1.AMOUNT, --���
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
              DECODE(T2.PRODUCT_CODE,
										NULL,
										PKG_STL_COMMON.PRODUCT_CODE_FLF��T2.PRODUCT_CODE)
						 --��Ʒ����,Ĭ�Ͼ�׼����
				FROM STL.T_STL_BILL_WRITEOFF   T1,
						 STL.T_STL_BILL_RECEIVABLE T2,
						 STL.T_STL_BILL_PAYABLE    T3
			 WHERE T1.BEGIN_NO = T2.RECEIVABLE_NO
						 AND T1.END_NO = T3.PAYABLE_NO
						 AND T2.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND T3.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND T3.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --���ջ���
						 AND T2.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_REVENUE -- СƱӦ��
						 AND
						 T1.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP -- Ӧ�ճ�Ӧ��
						 AND T1.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND T1.ACCOUNT_DATE < P_END_DATE;
	
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

END PKG_STL_VCH_DAILY_COD;
/
