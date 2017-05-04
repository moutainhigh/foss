CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_CLAIM IS

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

END PKG_STL_VCH_DAILY_CLAIM;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_CLAIM IS

	-----------------------------------------------------------------
	-- ƾ֤������������ϸ
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd,����20121221
																		 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
																		 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
																		 ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ�,false:ʧ��
	 IS
	BEGIN

		--1) ����-������������-���������
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
						 PKG_STL_VCH_COMMON.CLAIM_ORIG_WO_INCOME, --ҵ�񳡾�
						 PAY.CUSTOMER_CODE, --�ͻ�����
						 PAY.CUSTOMER_NAME, --�ͻ�����
						 PAY.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
						 DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
										'N/A',
										PAY.SOURCE_BILL_NO,
										PAY.WAYBILL_NO), --�˵���
						 PAY.PAYABLE_NO, --���ݱ��
						 PAY.ACCOUNT_DATE, --�������
						 PAY.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
						 PAY.BILL_TYPE, --����������
						 CASE
							 WHEN PAY.AMOUNT < 0 THEN
								LEAST(ABS(PAY.AMOUNT),
											NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
											NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
											NVL(WAYBILL.COD_AMOUNT, 0)) * (-1)
							 ELSE
								LEAST(ABS(PAY.AMOUNT),
											NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
											NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
											NVL(WAYBILL.COD_AMOUNT, 0))
						 END, --���˷ѡ�����������ȡС
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PAY.PRODUCT_CODE --��Ʒ����
				FROM STL.T_STL_BILL_PAYABLE PAY --Ӧ����
				JOIN PKP.T_SRV_WAYBILL WAYBILL
					ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
						 AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----�˵�
			 WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --��������Ӧ����
						 AND PAY.PAYABLE_ORG_CODE = WAYBILL.RECEIVE_ORG_CODE --ʼ��
						 AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND PAY.ACCOUNT_DATE < P_END_DATE;

		--2) ����-������������-������ɱ�
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
						 PKG_STL_VCH_COMMON.CLAIM_ORIG_COST, --ҵ�񳡾�
						 PAY.CUSTOMER_CODE, --�ͻ�����
						 PAY.CUSTOMER_NAME, --�ͻ�����
						 PAY.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
						 DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
										'N/A',
										PAY.SOURCE_BILL_NO,
										PAY.WAYBILL_NO), --�˵���
						 PAY.PAYABLE_NO, --���ݱ��
						 PAY.ACCOUNT_DATE, --�������
						 PAY.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
						 PAY.BILL_TYPE, --����������
						 CASE
							 WHEN PAY.AMOUNT < 0 THEN
								(ABS(PAY.AMOUNT) - (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
								NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
								NVL(WAYBILL.COD_AMOUNT, 0))) * (-1)
							 ELSE
								ABS(PAY.AMOUNT) -
								(NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
								 NVL(WAYBILL.PRE_PAY_AMOUNT, 0) - NVL(WAYBILL.COD_AMOUNT, 0))
						 END, --�������ȥ���˷�,������С��0,ֵΪ0
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PAY.PRODUCT_CODE --��Ʒ����
				FROM STL.T_STL_BILL_PAYABLE PAY --Ӧ����
				JOIN PKP.T_SRV_WAYBILL WAYBILL
					ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
						 AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----�˵�
			 WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --��������Ӧ����
						 AND PAY.PAYABLE_ORG_CODE = WAYBILL.RECEIVE_ORG_CODE --ʼ��
						 AND ABS(PAY.AMOUNT) - (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
						 NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
						 NVL(WAYBILL.COD_AMOUNT, 0)) > 0
						 AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND PAY.ACCOUNT_DATE < P_END_DATE;

		--3) ����-������������-�����ʼ��Ӧ����ǩ��
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
						 PKG_STL_VCH_COMMON.CLAIM_WO_ORIG_RCV_POD, --ҵ�񳡾������������ļ���������ͳ��,ȡ����������ϸ���,������ϸ�е�Ӧ�յ�����������Ϊʼ��Ӧ��
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --���ﲿ������
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
					ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ���
				JOIN STL.T_STL_BILL_PAYABLE PAY
					ON PAY.PAYABLE_NO = WO.END_NO --Ӧ������
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
						 AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
						 AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --ʼ��
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
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;

		--4) ����-������������-�����ʼ��Ӧ��δǩ��
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
						 PKG_STL_VCH_COMMON.CLAIM_WO_ORIG_RCV_NPOD, --ҵ�񳡾������������ļ���������ͳ��,ȡ����������ϸ���,������ϸ�е�Ӧ�յ�����������Ϊʼ��Ӧ��
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_CODE,RCV.ORIG_ORG_CODE), --ʼ�����ű���
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_ORIG_ORG_NAME,RCV.ORIG_ORG_NAME), --ʼ����������
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_DEST_ORG_CODE,RCV.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										RCV.EXPRESS_DEST_ORG_NAME,RCV.DEST_ORG_NAME), --���ﲿ������
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
					ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ���
				JOIN STL.T_STL_BILL_PAYABLE PAY
					ON PAY.PAYABLE_NO = WO.END_NO --Ӧ������
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
						 AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
						 AND PAY.PAYABLE_ORG_CODE = RCV.ORIG_ORG_CODE --ʼ��
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
						 AND WO.AMOUNT <> 0
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;

		--5) ����-������������-���⸶������
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
						 PKG_STL_VCH_COMMON.CLAIM_ORIG_PAY_APPLY, --ҵ�񳡾�
						 PAY.CUSTOMER_CODE, --�ͻ�����
						 PAY.CUSTOMER_NAME, --�ͻ�����
						 PAY.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
						 DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
										'N/A',
										PAY.SOURCE_BILL_NO,
										PAY.WAYBILL_NO), --�˵���
						 PAY.PAYABLE_NO, --���ݱ��
						 PAY.ACCOUNT_DATE, --��������
						 PAY.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
						 PAY.BILL_TYPE, --����������
						 --���ȡ��������,2013-8-5 ��С���޶�
             DP.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1),
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_PAYMENT PMT --���
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --�����ϸ
        JOIN STL.T_STL_BILL_PAYABLE PAY --Ӧ����
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO
             AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
             AND PAY.PAYABLE_ORG_CODE = PAY.ORIG_ORG_CODE --ʼ��
            -- AND PMT.PAYMENT_NO = PAY.PAYMENT_NO 2013-08-01 modified by zbw
             AND DP.PAY_AMOUNT > 0
             AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PMT.ACCOUNT_DATE < P_END_DATE;

		--6) ����-���ﲿ������-���������
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
						 PKG_STL_VCH_COMMON.CLAIM_DEST_WO_INCOME, --ҵ�񳡾�
						 PAY.CUSTOMER_CODE, --�ͻ�����
						 PAY.CUSTOMER_NAME, --�ͻ�����
						 PAY.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
						 DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
										'N/A',
										PAY.SOURCE_BILL_NO,
										PAY.WAYBILL_NO), --�˵���
						 PAY.PAYABLE_NO, --���ݱ��
						 PAY.ACCOUNT_DATE, --�������
						 PAY.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
						 PAY.BILL_TYPE, --����������
						 CASE
							 WHEN PAY.AMOUNT < 0 THEN
								LEAST(ABS(PAY.AMOUNT),
											NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
											NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
											NVL(WAYBILL.COD_AMOUNT, 0)) * (-1)
							 ELSE
								LEAST(ABS(PAY.AMOUNT),
											NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
											NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
											NVL(WAYBILL.COD_AMOUNT, 0))
						 END, --���˷ѡ�����������ȡС
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PAY.PRODUCT_CODE --��Ʒ����
				FROM STL.T_STL_BILL_PAYABLE PAY --Ӧ����
				JOIN PKP.T_SRV_WAYBILL WAYBILL
					ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
						 AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----�˵�
			 WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --��������Ӧ����
						 AND PAY.PAYABLE_ORG_CODE = WAYBILL.LAST_LOAD_ORG_CODE --����
						 AND WAYBILL.LAST_LOAD_ORG_CODE <> WAYBILL.RECEIVE_ORG_CODE --ISSUE-3132
						 AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND PAY.ACCOUNT_DATE < P_END_DATE
						 AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
		-- issue-3303

		--7) ����-���ﲿ������-������ɱ�
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
						 PKG_STL_VCH_COMMON.CLAIM_DEST_COST, --ҵ�񳡾�
						 PAY.CUSTOMER_CODE, --�ͻ�����
						 PAY.CUSTOMER_NAME, --�ͻ�����
						 PAY.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
						 DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
										'N/A',
										PAY.SOURCE_BILL_NO,
										PAY.WAYBILL_NO), --�˵���
						 PAY.PAYABLE_NO, --���ݱ��
						 PAY.ACCOUNT_DATE, --�������
						 PAY.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
						 PAY.BILL_TYPE, --����������
						 CASE
							 WHEN PAY.AMOUNT < 0 THEN
								(ABS(PAY.AMOUNT) - (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
								NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
								NVL(WAYBILL.COD_AMOUNT, 0))) * (-1)
							 ELSE
								ABS(PAY.AMOUNT) -
								(NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
								 NVL(WAYBILL.PRE_PAY_AMOUNT, 0) - NVL(WAYBILL.COD_AMOUNT, 0))
						 END, --�������ȥ���˷�,������С��0,ֵΪ0
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PAY.PRODUCT_CODE --��Ʒ����
				FROM STL.T_STL_BILL_PAYABLE PAY --Ӧ����
				JOIN PKP.T_SRV_WAYBILL WAYBILL
					ON PAY.WAYBILL_NO = WAYBILL.WAYBILL_NO
						 AND WAYBILL.ACTIVE = PKG_STL_COMMON.ACTIVE ----�˵�
			 WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --��������Ӧ����
						 AND PAY.PAYABLE_ORG_CODE = WAYBILL.LAST_LOAD_ORG_CODE --����
						 AND ABS(PAY.AMOUNT) - (NVL(WAYBILL.TO_PAY_AMOUNT, 0) +
						 NVL(WAYBILL.PRE_PAY_AMOUNT, 0) -
						 NVL(WAYBILL.COD_AMOUNT, 0)) > 0
						 AND WAYBILL.RECEIVE_ORG_CODE <> WAYBILL.LAST_LOAD_ORG_CODE --BUG-40092
						 AND PAY.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND PAY.ACCOUNT_DATE < P_END_DATE
						 AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
		-- issue-3303

		--8) ����-���ﲿ������-����嵽��Ӧ����ǩ��
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
						 PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_POD, --ҵ�񳡾������������ļ���������ͳ��,ȡ����������ϸ���,������ϸ�е�Ӧ�յ�����������Ϊʼ��Ӧ��
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										DECODE(PAY.PAYABLE_ORG_CODE,
                    PAY.ORIG_ORG_CODE,
                    PAY.Express_Orig_Org_Code,
                    PAY.DEST_ORG_CODE,
                    PAY.Express_Dest_Org_Code,PAY.PAYABLE_ORG_CODE),RCV.ORIG_ORG_CODE), --ʼ�����ű���         
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										DECODE(PAY.PAYABLE_ORG_CODE,
                    PAY.ORIG_ORG_CODE,
                    PAY.Express_Orig_Org_Name,
                    PAY.DEST_ORG_CODE,
                    PAY.Express_Dest_Org_Name,PAY.PAYABLE_ORG_NAME),RCV.ORIG_ORG_NAME), --ʼ����������
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										DECODE(PAY.PAYABLE_ORG_CODE,
                    PAY.ORIG_ORG_CODE,
                    PAY.Express_Orig_Org_Code,
                    PAY.DEST_ORG_CODE,
                    PAY.Express_Dest_Org_Code,PAY.PAYABLE_ORG_CODE),RCV.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										DECODE(PAY.PAYABLE_ORG_CODE,
                    PAY.ORIG_ORG_CODE,
                    PAY.Express_Orig_Org_Name,
                    PAY.DEST_ORG_CODE,
                    PAY.Express_Dest_Org_Name,PAY.PAYABLE_ORG_NAME),RCV.DEST_ORG_NAME), --���ﲿ������
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
					ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�
				JOIN STL.T_STL_BILL_PAYABLE PAY
					ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
						 AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
						 AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����
						 AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
																	 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
																	 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,
																	 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL --����䵽��Ӧ������
																	 ) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��
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
						 AND WO.ACCOUNT_DATE < P_END_DATE
						 AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
		-- issue-3303

		--9) ����-���ﲿ������-����嵽��Ӧ��δǩ��
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
						 PKG_STL_VCH_COMMON.CLAIM_WO_DEST_RCV_NPOD, --ҵ�񳡾������������ļ���������ͳ��,ȡ����������ϸ���,������ϸ�е�Ӧ�յ�����������Ϊʼ��Ӧ��
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										DECODE(PAY.PAYABLE_ORG_CODE,
                    PAY.ORIG_ORG_CODE,
                    PAY.Express_Orig_Org_Code,
                    PAY.DEST_ORG_CODE,
                    PAY.Express_Dest_Org_Code,PAY.PAYABLE_ORG_CODE),RCV.ORIG_ORG_CODE), --ʼ�����ű���         
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										DECODE(PAY.PAYABLE_ORG_CODE,
                    PAY.ORIG_ORG_CODE,
                    PAY.Express_Orig_Org_Name,
                    PAY.DEST_ORG_CODE,
                    PAY.Express_Dest_Org_Name,PAY.PAYABLE_ORG_NAME),RCV.ORIG_ORG_NAME), --ʼ����������
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										DECODE(PAY.PAYABLE_ORG_CODE,
                    PAY.ORIG_ORG_CODE,
                    PAY.Express_Orig_Org_Code,
                    PAY.DEST_ORG_CODE,
                    PAY.Express_Dest_Org_Code,PAY.PAYABLE_ORG_CODE),RCV.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(RCV.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										DECODE(PAY.PAYABLE_ORG_CODE,
                    PAY.ORIG_ORG_CODE,
                    PAY.Express_Orig_Org_Name,
                    PAY.DEST_ORG_CODE,
                    PAY.Express_Dest_Org_Name,PAY.PAYABLE_ORG_NAME),RCV.DEST_ORG_NAME), --���ﲿ������
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
					ON RCV.RECEIVABLE_NO = WO.BEGIN_NO --Ӧ�յ�
				JOIN STL.T_STL_BILL_PAYABLE PAY
					ON PAY.PAYABLE_NO = WO.END_NO --Ӧ����
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
						 AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
						 AND PAY.PAYABLE_ORG_CODE = RCV.DEST_ORG_CODE --����
						 AND RCV.BILL_TYPE IN (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
																	 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
																	 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,
																	 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL --����䵽��Ӧ������
																	 ) --����Ӧ�ա�����ƫ�ߴ���Ӧ�յ������˵������Ӧ��
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
						 AND WO.ACCOUNT_DATE < P_END_DATE
						 AND PAY.DEST_ORG_CODE <> PAY.ORIG_ORG_CODE;
		-- issue-3303

		--10) ����-���ﲿ������-���⸶������
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
						 PKG_STL_VCH_COMMON.CLAIM_DEST_PAY_APPLY, --ҵ�񳡾�
						 PAY.CUSTOMER_CODE, --�ͻ�����
						 PAY.CUSTOMER_NAME, --�ͻ�����
						 PAY.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE), --ʼ�����ű���
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME), --ʼ����������
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_CODE,PAY.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(PAY.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										PAY.EXPRESS_DEST_ORG_NAME,PAY.DEST_ORG_NAME), --���ﲿ������
						 DECODE(NVL(PAY.WAYBILL_NO, 'N/A'),
										'N/A',
										PAY.SOURCE_BILL_NO,
										PAY.WAYBILL_NO), --�˵���
						 PAY.PAYABLE_NO, --���ݱ��
						 PAY.ACCOUNT_DATE, --��������
						 PAY.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
						 PAY.BILL_TYPE, --����������
						 --���ȡ��������,2013-8-5 ��С���޶�
             DP.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1),
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PAY.PRODUCT_CODE --��Ʒ����
        FROM STL.T_STL_BILL_PAYMENT PMT --���
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON DP.PAYMENT_NO = PMT.PAYMENT_NO --�����ϸ
        JOIN STL.T_STL_BILL_PAYABLE PAY --Ӧ����
          ON PAY.PAYABLE_NO = DP.SOURCE_BILL_NO
             AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM --����
             AND PAY.PAYABLE_ORG_CODE = PAY.DEST_ORG_CODE --����
             AND PAY.ORIG_ORG_CODE <> PAY.DEST_ORG_CODE --BUG-40092
            -- AND PMT.PAYMENT_NO = PAY.PAYMENT_NO 2013-08-01 modified by zbw
             AND DP.PAY_AMOUNT > 0
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

END PKG_STL_VCH_DAILY_CLAIM;
/
