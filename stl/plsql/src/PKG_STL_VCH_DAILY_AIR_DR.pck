CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_AIR_DR IS

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

END PKG_STL_VCH_DAILY_AIR_DR;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_AIR_DR IS

	-----------------------------------------------------------------
	-- ƾ֤������������ϸ
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
																		 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
																		 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
																		 ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
	 IS
	BEGIN
		--1)Ԥ�տ��˴����ֽ�:��Ԥ�յ��ļ���������ͳ�ƣ�Ԥ�յ��ĸ��ʽΪ���ֽ�Ԥ�յ���������Ϊ�����˴���
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
						 PKG_STL_VCH_COMMON.AIR_DR_CH, --ҵ�񳡾�
						 DR.CUSTOMER_CODE, --�ͻ�����
						 DR.CUSTOMER_NAME, --�ͻ�����
						 DR.CUSTOMER_TYPE, --�ͻ�����
						 DR.CREATE_ORG_CODE, --ʼ�����ű���
						 DR.CREATE_ORG_NAME, --ʼ����������
						 DR.CREATE_ORG_CODE, --���ﲿ�ű���
						 DR.CREATE_ORG_NAME, --���ﲿ������
						 DR.DEPOSIT_RECEIVED_NO, --�˵���
						 DR.DEPOSIT_RECEIVED_NO, --���ݱ��
						 DR.ACCOUNT_DATE, --�������
						 DR.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__US, --��������
						 DR.BILL_TYPE, --����������
						 DR.AMOUNT, --���
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PKG_STL_COMMON.PRODUCT_CODE_AF --��Ʒ����
				FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DR
			 WHERE DR.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH --�ֽ�
						 AND DR.TRANSPORT_TYPE =
						 PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA --���˴���
						 AND DR.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND DR.ACCOUNT_DATE < P_END_DATE;

		--2)Ԥ�տ��˴�������:��Ԥ�յ��ļ���������ͳ�ƣ�Ԥ�յ��ĸ��ʽΪ�����п�����㡢֧Ʊ������֧����Ԥ�յ���������Ϊ�����˴���
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
						 PKG_STL_VCH_COMMON.AIR_DR_CD, --ҵ�񳡾�
						 DR.CUSTOMER_CODE, --�ͻ�����
						 DR.CUSTOMER_NAME, --�ͻ�����
						 DR.CUSTOMER_TYPE, --�ͻ�����
						 DR.CREATE_ORG_CODE, --ʼ�����ű���
						 DR.CREATE_ORG_NAME, --ʼ����������
						 DR.CREATE_ORG_CODE, --���ﲿ�ű���
						 DR.CREATE_ORG_NAME, --���ﲿ������
						 DR.DEPOSIT_RECEIVED_NO, --�˵���
						 DR.DEPOSIT_RECEIVED_NO, --���ݱ��
						 DR.ACCOUNT_DATE, --�������
						 DR.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__US, --��������
						 DR.BILL_TYPE, --����������
						 DR.AMOUNT, --���
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 PKG_STL_COMMON.PRODUCT_CODE_AF --��Ʒ����
				FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DR
			 WHERE DR.PAYMENT_TYPE IN (PKG_STL_COMMON.PAYMENT_TYPE_CARD, --���п�
																 PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER, --���
																 PKG_STL_COMMON.PAYMENT_TYPE_NOTE, --֧Ʊ
																 PKG_STL_COMMON.PAYMENT_TYPE_ONLINE --����֧��
																 )
						 AND DR.TRANSPORT_TYPE =
						 PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA --���˴���
						 AND DR.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND DR.ACCOUNT_DATE < P_END_DATE;

		--3)Ԥ�տ��˴����Ӧ�յ����˷���ǩ��:
		--���������ļ���������ͳ�ƣ���������Ϊ��Ԥ�ճ�Ӧ�գ������жϺ���ʱ��Ӧ�յ���ǩ��״̬Ϊ��ǩ�գ�
		--Ӧ�յ��ĵ���������Ϊ����Ӧ�ա����˵������Ӧ�գ�Ԥ�յ���������Ϊ�����˴���ȡ������ϸ�Ľ�
		--�������ʱ���ɵĸ���Ҳͳ��
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
						 PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_POD, --ҵ�񳡾�
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 RCV.ORIG_ORG_CODE, --ʼ�����ű���
						 RCV.ORIG_ORG_NAME, --ʼ����������
						 RCV.DEST_ORG_CODE, --���ﲿ�ű���
						 RCV.DEST_ORG_NAME, --���ﲿ������
						 DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
										'N/A',
										RCV.SOURCE_BILL_NO,
										RCV.WAYBILL_NO), --�˵���
						 RCV.RECEIVABLE_NO, --���ݱ��
						 WO.ACCOUNT_DATE, --�������
						 RCV.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
						 RCV.BILL_TYPE, --����������
						 WO.AMOUNT, --���
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 RCV.PRODUCT_CODE --��Ʒ����
				FROM STL.T_STL_BILL_WRITEOFF WO
				JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
					ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
						 AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
				JOIN STL.T_STL_BILL_RECEIVABLE RCV
					ON RCV.RECEIVABLE_NO = WO.END_NO
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --Ԥ�ճ�Ӧ��
						 AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
																				PKG_STL_COMMON.POD__POD_TYPE__POD,
																				1,
																				PKG_STL_COMMON.POD__POD_TYPE__UPD,
																				-1)),
														 0) POD
										FROM STL.T_STL_POD T
									 WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --����ʱ��ǩ��
						 AND RCV.BILL_TYPE IN
						 (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --����Ӧ��
									PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA,--���˵������Ӧ��
                  PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DL)--����䵽�����Ӧ�� 
						 AND DR.TRANSPORT_TYPE IN(
						 PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA ,--���˴���
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__LS)--��������
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;

		--4)Ԥ�տ��˴����Ӧ�յ����˷�δǩ��
		--���������ļ���������ͳ�ƣ���������Ϊ��Ԥ�ճ�Ӧ�գ������жϺ���ʱ��Ӧ�յ���ǩ��״̬Ϊδǩ�գ�
		--Ӧ�յ��ĵ���������Ϊ����Ӧ�ա����˵������Ӧ�գ�Ԥ�յ���������Ϊ�����˴���ȡ������ϸ�Ľ�
		--�������ʱ���ɵĸ���Ҳͳ��
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
						 PKG_STL_VCH_COMMON.AIR_DR_DEST_RCV_NPOD, --ҵ�񳡾�
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 RCV.ORIG_ORG_CODE, --ʼ�����ű���
						 RCV.ORIG_ORG_NAME, --ʼ����������
						 RCV.DEST_ORG_CODE, --���ﲿ�ű���
						 RCV.DEST_ORG_NAME, --���ﲿ������
						 DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
										'N/A',
										RCV.SOURCE_BILL_NO,
										RCV.WAYBILL_NO), --�˵���
						 RCV.RECEIVABLE_NO, --���ݱ��
						 WO.ACCOUNT_DATE, --�������
						 RCV.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
						 RCV.BILL_TYPE, --����������
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
				JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
					ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
						 AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
				JOIN STL.T_STL_BILL_RECEIVABLE RCV
					ON RCV.RECEIVABLE_NO = WO.END_NO
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --Ԥ�ճ�Ӧ��
						 AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
																				PKG_STL_COMMON.POD__POD_TYPE__POD,
																				1,
																				PKG_STL_COMMON.POD__POD_TYPE__UPD,
																				-1)),
														 0) POD
										FROM STL.T_STL_POD T
									 WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --����ʱδǩ��
						 AND RCV.BILL_TYPE IN
						 (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST, --����Ӧ��
									PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --���˵������Ӧ��
						 AND DR.TRANSPORT_TYPE =
						 PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA --���˴���
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;

		--5��Ԥ�տ��˴��������Ӧ��
		--���������ļ���������ͳ�ƣ���������Ϊ��Ԥ�ճ�Ӧ�գ�Ӧ�յ��ĵ���������Ϊ��������Ӧ�գ�
		--Ԥ�յ���������Ϊ�����˴���ȡ������ϸ�Ľ�������ʱ���ɵĸ���Ҳͳ��
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
						 PKG_STL_VCH_COMMON.AIR_DR_WO_OTHER_RCV, --ҵ�񳡾�
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 RCV.ORIG_ORG_CODE, --ʼ�����ű���
						 RCV.ORIG_ORG_NAME, --ʼ����������
						 RCV.DEST_ORG_CODE, --���ﲿ�ű���
						 RCV.DEST_ORG_NAME, --���ﲿ������
						 DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
										'N/A',
										RCV.SOURCE_BILL_NO,
										RCV.WAYBILL_NO), --�˵���
						 RCV.RECEIVABLE_NO, --���ݱ��
						 WO.ACCOUNT_DATE, --�������
						 RCV.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
						 RCV.BILL_TYPE, --����������
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
				JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
					ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
						 AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
				JOIN STL.T_STL_BILL_RECEIVABLE RCV
					ON RCV.RECEIVABLE_NO = WO.END_NO
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --Ԥ�ճ�Ӧ��
						 AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AR --��������Ӧ��
						 AND DR.TRANSPORT_TYPE =
						 PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA --���˴���
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;

		--6)Ԥ�տ��˴����Ӧ�մ��ջ�����ǩ��
		--���������ļ���������ͳ�ƣ���������Ϊ��Ԥ�ճ�Ӧ�գ������жϺ���ʱ��Ӧ�յ���ǩ��״̬Ϊ��ǩ�գ�
		--Ӧ�յ��ĵ���������Ϊ���ջ���Ӧ�գ���Ʒ����Ϊ����׼���ˣ������˴�����ջ���Ӧ�գ�Ԥ�յ�����
		--����Ϊ�����˴���ȡ������ϸ�Ľ�������ʱ���ɵĸ���Ҳͳ��
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
						 PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_POD, --ҵ�񳡾�
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 RCV.ORIG_ORG_CODE, --ʼ�����ű���
						 RCV.ORIG_ORG_NAME, --ʼ����������
						 RCV.DEST_ORG_CODE, --���ﲿ�ű���
						 RCV.DEST_ORG_NAME, --���ﲿ������
						 DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
										'N/A',
										RCV.SOURCE_BILL_NO,
										RCV.WAYBILL_NO), --�˵���
						 RCV.RECEIVABLE_NO, --���ݱ��
						 WO.ACCOUNT_DATE, --�������
						 RCV.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
						 RCV.BILL_TYPE, --����������
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
				JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
					ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
						 AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
				JOIN STL.T_STL_BILL_RECEIVABLE RCV
					ON RCV.RECEIVABLE_NO = WO.END_NO
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --Ԥ�ճ�Ӧ��
						 AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
																				PKG_STL_COMMON.POD__POD_TYPE__POD,
																				1,
																				PKG_STL_COMMON.POD__POD_TYPE__UPD,
																				-1)),
														 0) POD
										FROM STL.T_STL_POD T
									 WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1 --����ʱ��ǩ��
						 AND RCV.BILL_TYPE IN
						 (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_COD, --���ջ���Ӧ��
									PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC) --���˴�����ջ���Ӧ��
						 AND DR.TRANSPORT_TYPE =
						 PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA --���˴���
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;

		--7)Ԥ�տ��˴����Ӧ�մ��ջ���δǩ��
		--���������ļ���������ͳ�ƣ���������Ϊ��Ԥ�ճ�Ӧ�գ������жϺ���ʱ��Ӧ�յ���ǩ��״̬Ϊδǩ�գ�
		--Ӧ�յ��ĵ���������Ϊ���ջ���Ӧ�գ���Ʒ����Ϊ����׼���ˣ������˴�����ջ���Ӧ�գ�Ԥ�յ�����
		--����Ϊ�����˴���ȡ������ϸ�Ľ�������ʱ���ɵĸ���Ҳͳ��
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
						 PKG_STL_VCH_COMMON.AIR_DR_WO_COD_RCV_NPOD, --ҵ�񳡾�
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 RCV.ORIG_ORG_CODE, --ʼ�����ű���
						 RCV.ORIG_ORG_NAME, --ʼ����������
						 RCV.DEST_ORG_CODE, --���ﲿ�ű���
						 RCV.DEST_ORG_NAME, --���ﲿ������
						 DECODE(NVL(RCV.WAYBILL_NO, 'N/A'),
										'N/A',
										RCV.SOURCE_BILL_NO,
										RCV.WAYBILL_NO), --�˵���
						 RCV.RECEIVABLE_NO, --���ݱ��
						 WO.ACCOUNT_DATE, --�������
						 RCV.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
						 RCV.BILL_TYPE, --����������
						 WO.AMOUNT, --���
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 RCV.PRODUCT_CODE --��Ʒ����
				FROM STL.T_STL_BILL_WRITEOFF WO
				JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED DR
					ON DR.DEPOSIT_RECEIVED_NO = WO.BEGIN_NO
						 AND DR.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
				JOIN STL.T_STL_BILL_RECEIVABLE RCV
					ON RCV.RECEIVABLE_NO = WO.END_NO
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR --Ԥ�ճ�Ӧ��
						 AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
																				PKG_STL_COMMON.POD__POD_TYPE__POD,
																				1,
																				PKG_STL_COMMON.POD__POD_TYPE__UPD,
																				-1)),
														 0) POD
										FROM STL.T_STL_POD T
									 WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 0 --����ʱδǩ��
						 AND RCV.BILL_TYPE IN
						 (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_COD, --���ջ���Ӧ��
									PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC) --���˴�����ջ���Ӧ��
						 AND DR.TRANSPORT_TYPE =
						 PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA --���˴���
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;

    --8��������Ԥ�ո������룺
    --������ļ���������ͳ�ƣ���������ĸ�����ϸ����������Ϊ��Ԥ�յ���Ԥ�յ���������Ϊ�����˴���
    --ͳ�ƶ�Ӧ�ĸ����ϸ���֮��
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
             PKG_STL_VCH_COMMON.AIR_DR_PAY_APPLY, --ҵ�񳡾�
             DR.CUSTOMER_CODE, --�ͻ�����
             DR.CUSTOMER_NAME, --�ͻ�����
             DR.CUSTOMER_TYPE, --�ͻ�����
             DR.CREATE_ORG_CODE, --ʼ�����ű���
             DR.CREATE_ORG_NAME, --ʼ����������
             DR.CREATE_ORG_CODE, --���ﲿ�ű���
             DR.CREATE_ORG_NAME, --���ﲿ������
             DR.DEPOSIT_RECEIVED_NO, --�˵���
             DR.DEPOSIT_RECEIVED_NO, --���ݱ��
             DR.ACCOUNT_DATE, --�������
             DR.BUSINESS_DATE, --ҵ������
             PKG_STL_COMMON.BILL_PARENT_TYPE__US, --��������
             DR.BILL_TYPE, --����������
             DP.PAY_AMOUNT *
             DECODE(PT.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1), --���
             0,
             0,
             0,
             0,
             0,
             0,
             0,
             PKG_STL_COMMON.PRODUCT_CODE_AF --��Ʒ����
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED DR
        JOIN STL.T_STL_BILL_PAYMENT_D DP
          ON DP.SOURCE_BILL_NO = DR.DEPOSIT_RECEIVED_NO
        JOIN STL.T_STL_BILL_PAYMENT PT
          ON PT.PAYMENT_NO = DP.PAYMENT_NO
       WHERE DR.TRANSPORT_TYPE =
             PKG_STL_COMMON.DEPOSIT_RCVD__TRANS_TYPE__AA --���˴���
             AND DR.IS_RED_BACK = PKG_STL_COMMON.NO --�Ǻ쵥
             AND PT.ACCOUNT_DATE >= P_BEGIN_DATE
             AND PT.ACCOUNT_DATE < P_END_DATE;

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

END PKG_STL_VCH_DAILY_AIR_DR;
/
