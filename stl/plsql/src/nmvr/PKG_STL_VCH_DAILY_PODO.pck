CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_PODO IS

	-- ==============================================================
	-- Author  : ZBW
	-- Created : 2013-11-06
	-- Purpose : ÿ��ƾ֤ǩ���˵���01��
	-- ==============================================================

	-----------------------------------------------------------------
	-- ǩ���˵���01����������ϸ
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
																		 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
																		 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
																		 ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

END PKG_STL_VCH_DAILY_PODO;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_PODO IS

	-----------------------------------------------------------------
	-- ǩ���˵���01����������ϸ
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
																		 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
																		 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
																		 ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
	 IS
	BEGIN
	
		--1)ǩ���˵���01������ǩ��ʱ�ָ����տ���
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
						 PKG_STL_VCH_COMMON.PODO_CASH_COLLECTED, --ҵ�񳡾�
						 CASH.CUSTOMER_CODE, --�ͻ�����
						 CASH.CUSTOMER_NAME, --�ͻ�����
						 CASH.CUSTOMER_TYPE, --�ͻ�����
						 DECODE(CASH.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										CASH.EXPRESS_ORIG_ORG_CODE,
										CASH.ORIG_ORG_CODE), --ʼ�����ű���
						 DECODE(CASH.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										CASH.EXPRESS_ORIG_ORG_NAME,
										CASH.ORIG_ORG_NAME), --ʼ����������
						 DECODE(CASH.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										CASH.EXPRESS_DEST_ORG_CODE,
										CASH.DEST_ORG_CODE), --���ﲿ�ű���
						 DECODE(CASH.PRODUCT_CODE,
										PKG_STL_COMMON.PRODUCT_CODE_PKG,
										CASH.EXPRESS_DEST_ORG_NAME,
										CASH.DEST_ORG_NAME), --���ﲿ������
						 CASH.WAYBILL_NO, --�˵���
						 CASH.CASH_COLLECTION_NO, --���ݱ��
						 CASH.ACCOUNT_DATE, --�������
						 CASH.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__XS, --��������
						 CASH.BILL_TYPE, --����������
						 CASH.AMOUNT, --���
						 CASH.TRANSPORT_FEE, --�˷�
						 CASH.PICKUP_FEE, --�ӻ���
						 CASH.DELIVERY_GOODS_FEE, --�ͻ���
						 CASH.PACKAGING_FEE, --��װ��
						 CASH.INSURANCE_FEE, --���۷�
						 CASH.COD_FEE, --���ջ���������
						 CASH.OTHER_FEE, --������
						 CASH.PRODUCT_CODE --��Ʒ����
				FROM STL.T_STL_POD POD --ǩ�ռ�¼��
				JOIN STL.T_STL_BILL_CASH_COLLECTION CASH
					ON CASH.WAYBILL_NO = POD.WAYBILL_NO --�ֽ��տ
			 WHERE POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__POD --ǩ��
						 AND CASH.SOURCE_BILL_TYPE =
						 PKG_STL_COMMON.CASH_COLL_SOURCE_BILL_TYPE_W --��Դ���˵�
						 AND POD.POD_DATE >= P_BEGIN_DATE
						 AND POD.POD_DATE < P_END_DATE
						 AND CASH.ACCOUNT_DATE <= POD.POD_DATE
						 AND CASH.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE; --�տ�С�ڵ���ǩ��ʱ��   
	
		--2)ǩ���˵���01������ǩ��ʱʼ��Ӧ���Ѻ������ 
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
						 PKG_STL_VCH_COMMON.PODO_ORIG_RCV_WO, --ҵ�񳡾�
						 CUSTOMER_CODE, --�ͻ�����
						 CUSTOMER_NAME, --�ͻ�����
						 CUSTOMER_TYPE, --�ͻ�����
						 ORIG_ORG_CODE, --ʼ�����ű��� 
						 ORIG_ORG_NAME, --ʼ����������
						 DEST_ORG_CODE, --���ﲿ�ű���
						 DEST_ORG_NAME, --���ﲿ������
						 WAYBILL_NO, --�˵���
						 RECEIVABLE_NO, --���ݱ��
						 ACCOUNT_DATE, --�������
						 BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
						 BILL_TYPE, --����������
						 WO_AMOUNT, --���
						 ROUND(TRANSPORT_FEE * WO_AMOUNT / RCV_AMOUNT), --�˷�
						 ROUND(PICKUP_FEE * WO_AMOUNT / RCV_AMOUNT), --�ӻ���
						 ROUND(DELIVERY_GOODS_FEE * WO_AMOUNT / RCV_AMOUNT), --�ͻ���
						 ROUND(PACKAGING_FEE * WO_AMOUNT / RCV_AMOUNT), --��װ��
						 ROUND(INSURANCE_FEE * WO_AMOUNT / RCV_AMOUNT), --���۷�
						 ROUND(COD_FEE * WO_AMOUNT / RCV_AMOUNT), --���ջ���������
						 WO_AMOUNT - (ROUND(TRANSPORT_FEE * WO_AMOUNT / RCV_AMOUNT) +
						 ROUND(PICKUP_FEE * WO_AMOUNT / RCV_AMOUNT) +
						 ROUND(DELIVERY_GOODS_FEE * WO_AMOUNT / RCV_AMOUNT) +
						 ROUND(PACKAGING_FEE * WO_AMOUNT / RCV_AMOUNT) +
						 ROUND(INSURANCE_FEE * WO_AMOUNT / RCV_AMOUNT) +
						 ROUND(COD_FEE * WO_AMOUNT / RCV_AMOUNT)), --������
						 PRODUCT_CODE --��Ʒ���� 
				FROM (SELECT RCV.CUSTOMER_CODE, --�ͻ�����
										 RCV.CUSTOMER_NAME, --�ͻ�����
										 RCV.CUSTOMER_TYPE, --�ͻ�����
										 DECODE(RCV.PRODUCT_CODE,
														PKG_STL_COMMON.PRODUCT_CODE_PKG,
														RCV.EXPRESS_ORIG_ORG_CODE��RCV.ORIG_ORG_CODE) ORIG_ORG_CODE, --ʼ�����ű��� 
										 DECODE(RCV.PRODUCT_CODE,
														PKG_STL_COMMON.PRODUCT_CODE_PKG,
														RCV.EXPRESS_ORIG_ORG_NAME��RCV.ORIG_ORG_NAME) ORIG_ORG_NAME, --ʼ����������
										 DECODE(RCV.PRODUCT_CODE,
														PKG_STL_COMMON.PRODUCT_CODE_PKG,
														RCV.EXPRESS_DEST_ORG_CODE��RCV.DEST_ORG_CODE) DEST_ORG_CODE, --���ﲿ�ű���
										 DECODE(RCV.PRODUCT_CODE,
														PKG_STL_COMMON.PRODUCT_CODE_PKG,
														RCV.EXPRESS_DEST_ORG_NAME��RCV.DEST_ORG_NAME) DEST_ORG_NAME, --���ﲿ������
										 RCV.WAYBILL_NO, --�˵���
										 RCV.RECEIVABLE_NO RECEIVABLE_NO, --���ݱ��
										 MAX(RCV.ACCOUNT_DATE) ACCOUNT_DATE, --�������
										 MIN(RCV.BUSINESS_DATE) BUSINESS_DATE, --ҵ������
										 RCV.BILL_TYPE, --����������
										 MAX(RCV.AMOUNT) RCV_AMOUNT, --���
										 SUM(WO.AMOUNT) WO_AMOUNT,
										 MAX(RCV.TRANSPORT_FEE) TRANSPORT_FEE, --�˷�
										 MAX(RCV.PICKUP_FEE) PICKUP_FEE, --�ӻ���
										 MAX(RCV.DELIVERY_GOODS_FEE) DELIVERY_GOODS_FEE, --�ͻ���
										 MAX(RCV.PACKAGING_FEE) PACKAGING_FEE, --��װ��
										 MAX(RCV.INSURANCE_FEE) INSURANCE_FEE, --���۷�
										 MAX(RCV.COD_FEE) COD_FEE, --���ջ���������
										 MAX(RCV.OTHER_FEE) OTHER_FEE, --������
										 RCV.PRODUCT_CODE --��Ʒ����
								FROM STL.T_STL_POD POD
								JOIN STL.T_STL_BILL_RECEIVABLE RCV
									ON RCV.WAYBILL_NO = POD.WAYBILL_NO
										 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
								JOIN STL.T_STL_BILL_WRITEOFF WO
									ON (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
										 WO.END_NO = RCV.RECEIVABLE_NO)
							 WHERE POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__POD --ǩ��
										 AND RCV.SOURCE_BILL_TYPE =
										 PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --��Դ���˵�
										 AND RCV.BILL_TYPE =
										 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN --ʼ��
										 AND WO.ACCOUNT_DATE <= POD.POD_DATE --����ʱ����С���ڻ���ʱ��
										 AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE -- ��Ʊ���Ϊ01
										 AND POD.POD_DATE >= P_BEGIN_DATE
										 AND POD.POD_DATE < P_END_DATE
							 GROUP BY RCV.CUSTOMER_CODE, --�ͻ�����
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
												RCV.WAYBILL_NO, --�˵���
												RCV.RECEIVABLE_NO, --���ݱ��
												RCV.BILL_TYPE, --����������
												RCV.PRODUCT_CODE, --��Ʒ����
												POD.ID)
			 WHERE WO_AMOUNT <> 0
						 AND RCV_AMOUNT <> 0;
	
		--3)ǩ���˵���01������ǩ��ʱʼ��Ӧ��δ�������
		DECLARE
		
			CURSOR CUR_POD IS
				SELECT *
					FROM STL.T_STL_POD POD
				 WHERE POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__POD
							 AND POD.POD_DATE >= P_BEGIN_DATE
							 AND POD.POD_DATE < P_END_DATE;
		
			V_POD_ROW STL.T_STL_POD%ROWTYPE;
		
		BEGIN
		
			FOR V_POD_ROW IN CUR_POD LOOP
			
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
								 PKG_STL_VCH_COMMON.PODO_ORIG_RCV_NWO, --ҵ�񳡾�
								 CUSTOMER_CODE, --�ͻ�����
								 CUSTOMER_NAME, --�ͻ�����
								 CUSTOMER_TYPE, --�ͻ�����
								 ORIG_ORG_CODE, --ʼ�����ű��� 
								 ORIG_ORG_NAME, --ʼ����������
								 DEST_ORG_CODE, --���ﲿ�ű���
								 DEST_ORG_NAME, --���ﲿ������
								 WAYBILL_NO, --�˵���
								 RECEIVABLE_NO, --���ݱ��
								 ACCOUNT_DATE, --�������
								 BUSINESS_DATE, --ҵ������
								 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
								 BILL_TYPE, --����������
								 RCV_AMOUNT - WO_AMOUNT, --���
								 TRANSPORT_FEE -
								 ROUND(TRANSPORT_FEE * WO_AMOUNT / RCV_AMOUNT), --�˷�
								 PICKUP_FEE - ROUND(PICKUP_FEE * WO_AMOUNT / RCV_AMOUNT), --�ӻ���
								 DELIVERY_GOODS_FEE -
								 ROUND(DELIVERY_GOODS_FEE * WO_AMOUNT / RCV_AMOUNT), --�ͻ���
								 PACKAGING_FEE -
								 ROUND(PACKAGING_FEE * WO_AMOUNT / RCV_AMOUNT), --��װ��
								 INSURANCE_FEE -
								 ROUND(INSURANCE_FEE * WO_AMOUNT / RCV_AMOUNT), --���۷�
								 COD_FEE - ROUND(COD_FEE * WO_AMOUNT / RCV_AMOUNT), --���ջ���������
								 RCV_AMOUNT - WO_AMOUNT -
								 (TRANSPORT_FEE -
								 ROUND(TRANSPORT_FEE * WO_AMOUNT / RCV_AMOUNT) +
								 PICKUP_FEE - ROUND(PICKUP_FEE * WO_AMOUNT / RCV_AMOUNT) +
								 DELIVERY_GOODS_FEE -
								 ROUND(DELIVERY_GOODS_FEE * WO_AMOUNT / RCV_AMOUNT) +
								 PACKAGING_FEE -
								 ROUND(PACKAGING_FEE * WO_AMOUNT / RCV_AMOUNT) +
								 INSURANCE_FEE -
								 ROUND(INSURANCE_FEE * WO_AMOUNT / RCV_AMOUNT) + COD_FEE -
								 ROUND(COD_FEE * WO_AMOUNT / RCV_AMOUNT)), --������
								 PRODUCT_CODE --��Ʒ���� 
						FROM (SELECT RCV.CUSTOMER_CODE, --�ͻ�����
												 RCV.CUSTOMER_NAME, --�ͻ�����
												 RCV.CUSTOMER_TYPE, --�ͻ�����
												 DECODE(RCV.PRODUCT_CODE,
																PKG_STL_COMMON.PRODUCT_CODE_PKG,
																RCV.EXPRESS_ORIG_ORG_CODE��RCV.ORIG_ORG_CODE) ORIG_ORG_CODE, --ʼ�����ű��� 
												 DECODE(RCV.PRODUCT_CODE,
																PKG_STL_COMMON.PRODUCT_CODE_PKG,
																RCV.EXPRESS_ORIG_ORG_NAME��RCV.ORIG_ORG_NAME) ORIG_ORG_NAME, --ʼ����������
												 DECODE(RCV.PRODUCT_CODE,
																PKG_STL_COMMON.PRODUCT_CODE_PKG,
																RCV.EXPRESS_DEST_ORG_CODE��RCV.DEST_ORG_CODE) DEST_ORG_CODE, --���ﲿ�ű���
												 DECODE(RCV.PRODUCT_CODE,
																PKG_STL_COMMON.PRODUCT_CODE_PKG,
																RCV.EXPRESS_DEST_ORG_NAME��RCV.DEST_ORG_NAME) DEST_ORG_NAME, --���ﲿ������
												 RCV.WAYBILL_NO, --�˵���
												 RCV.RECEIVABLE_NO, --���ݱ��
												 RCV.ACCOUNT_DATE, --�������
												 RCV.BUSINESS_DATE, --ҵ������
												 RCV.BILL_TYPE, --����������
												 RCV.AMOUNT RCV_AMOUNT, --���
												 NVL((SELECT SUM(WO.AMOUNT)
															 FROM STL.T_STL_BILL_WRITEOFF WO
															WHERE (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
																		WO.END_NO = RCV.RECEIVABLE_NO)
																		AND
																		WO.ACCOUNT_DATE <= V_POD_ROW.POD_DATE),
														 0) WO_AMOUNT,
												 RCV.TRANSPORT_FEE, --�˷�
												 RCV.PICKUP_FEE, --�ӻ���
												 RCV.DELIVERY_GOODS_FEE, --�ͻ���
												 RCV.PACKAGING_FEE, --��װ��
												 RCV.INSURANCE_FEE, --���۷�
												 RCV.COD_FEE, --���ջ���������
												 RCV.OTHER_FEE, --������
												 RCV.PRODUCT_CODE --��Ʒ���� 
										FROM STL.T_STL_BILL_RECEIVABLE RCV
									 WHERE RCV.ID IN
												 (SELECT ID
														FROM (SELECT ID, IS_RED_BACK, INVOICE_MARK
																		FROM (SELECT *
																						FROM STL.T_STL_BILL_RECEIVABLE T
																					 WHERE T.WAYBILL_NO =
																								 V_POD_ROW.WAYBILL_NO
																								 AND
																								 T.SOURCE_BILL_TYPE =
																								 PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W
																								 AND T.ACCOUNT_DATE <=
																								 V_POD_ROW.POD_DATE
																								 AND
																								 T.BILL_TYPE =
																								 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_ORIGIN
																					 ORDER BY T.ACCOUNT_DATE DESC,
																										T.CREATE_TIME  DESC,
																										T.IS_RED_BACK  ASC)
																	 WHERE ROWNUM <= 1)
													 WHERE IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
																 AND INVOICE_MARK =
																 PKG_STL_COMMON.INVOICE_MARK_ONE))
					 WHERE RCV_AMOUNT <> WO_AMOUNT
								 AND RCV_AMOUNT <> 0;
			
			END LOOP;
		END;
	
		--4)ǩ���˵���01������ǩ��ʱ����Ӧ���Ѻ������
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
						 PKG_STL_VCH_COMMON.PODO_DEST_RCV_WO, --ҵ�񳡾�
						 CUSTOMER_CODE, --�ͻ�����
						 CUSTOMER_NAME, --�ͻ�����
						 CUSTOMER_TYPE, --�ͻ�����
						 ORIG_ORG_CODE, --ʼ�����ű��� 
						 ORIG_ORG_NAME, --ʼ����������
						 DEST_ORG_CODE, --���ﲿ�ű���
						 DEST_ORG_NAME, --���ﲿ������
						 WAYBILL_NO, --�˵���
						 RECEIVABLE_NO, --���ݱ��
						 ACCOUNT_DATE, --�������
						 BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
						 BILL_TYPE, --����������
						 WO_AMOUNT, --���
						 ROUND(TRANSPORT_FEE * WO_AMOUNT / RCV_AMOUNT), --�˷�
						 ROUND(PICKUP_FEE * WO_AMOUNT / RCV_AMOUNT), --�ӻ���
						 ROUND(DELIVERY_GOODS_FEE * WO_AMOUNT / RCV_AMOUNT), --�ͻ���
						 ROUND(PACKAGING_FEE * WO_AMOUNT / RCV_AMOUNT), --��װ��
						 ROUND(INSURANCE_FEE * WO_AMOUNT / RCV_AMOUNT), --���۷�
						 ROUND(COD_FEE * WO_AMOUNT / RCV_AMOUNT), --���ջ���������
						 WO_AMOUNT - (ROUND(TRANSPORT_FEE * WO_AMOUNT / RCV_AMOUNT) +
						 ROUND(PICKUP_FEE * WO_AMOUNT / RCV_AMOUNT) +
						 ROUND(DELIVERY_GOODS_FEE * WO_AMOUNT / RCV_AMOUNT) +
						 ROUND(PACKAGING_FEE * WO_AMOUNT / RCV_AMOUNT) +
						 ROUND(INSURANCE_FEE * WO_AMOUNT / RCV_AMOUNT) +
						 ROUND(COD_FEE * WO_AMOUNT / RCV_AMOUNT)), --������
						 PRODUCT_CODE --��Ʒ���� 
				FROM (SELECT RCV.CUSTOMER_CODE, --�ͻ�����
										 RCV.CUSTOMER_NAME, --�ͻ�����
										 RCV.CUSTOMER_TYPE, --�ͻ�����
										 DECODE(RCV.PRODUCT_CODE,
														PKG_STL_COMMON.PRODUCT_CODE_PKG,
														RCV.EXPRESS_ORIG_ORG_CODE��RCV.ORIG_ORG_CODE) ORIG_ORG_CODE, --ʼ�����ű��� 
										 DECODE(RCV.PRODUCT_CODE,
														PKG_STL_COMMON.PRODUCT_CODE_PKG,
														RCV.EXPRESS_ORIG_ORG_NAME��RCV.ORIG_ORG_NAME) ORIG_ORG_NAME, --ʼ����������
										 DECODE(RCV.PRODUCT_CODE,
														PKG_STL_COMMON.PRODUCT_CODE_PKG,
														RCV.EXPRESS_DEST_ORG_CODE��RCV.DEST_ORG_CODE) DEST_ORG_CODE, --���ﲿ�ű���
										 DECODE(RCV.PRODUCT_CODE,
														PKG_STL_COMMON.PRODUCT_CODE_PKG,
														RCV.EXPRESS_DEST_ORG_NAME��RCV.DEST_ORG_NAME) DEST_ORG_NAME, --���ﲿ������
										 RCV.WAYBILL_NO, --�˵���
										 RCV.RECEIVABLE_NO, --���ݱ��
										 MAX(RCV.ACCOUNT_DATE) ACCOUNT_DATE, --�������
										 MIN(RCV.BUSINESS_DATE) BUSINESS_DATE, --ҵ������
										 RCV.BILL_TYPE, --����������
										 MAX(RCV.AMOUNT) RCV_AMOUNT, --���
										 SUM(WO.AMOUNT) WO_AMOUNT,
										 MAX(RCV.TRANSPORT_FEE) TRANSPORT_FEE, --�˷�
										 MAX(RCV.PICKUP_FEE) PICKUP_FEE, --�ӻ���
										 MAX(RCV.DELIVERY_GOODS_FEE) DELIVERY_GOODS_FEE, --�ͻ���
										 MAX(RCV.PACKAGING_FEE) PACKAGING_FEE, --��װ��
										 MAX(RCV.INSURANCE_FEE) INSURANCE_FEE, --���۷�
										 MAX(RCV.COD_FEE) COD_FEE, --���ջ���������
										 MAX(RCV.OTHER_FEE) OTHER_FEE, --������
										 RCV.PRODUCT_CODE --��Ʒ����
								FROM STL.T_STL_POD POD
								JOIN STL.T_STL_BILL_RECEIVABLE RCV
									ON RCV.WAYBILL_NO = POD.WAYBILL_NO
										 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
								JOIN STL.T_STL_BILL_WRITEOFF WO
									ON (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
										 WO.END_NO = RCV.RECEIVABLE_NO)
							 WHERE POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__POD --ǩ��
										 AND RCV.SOURCE_BILL_TYPE =
										 PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --��Դ���˵�
										 AND RCV.BILL_TYPE IN
										 (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
													PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
													PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA) --����Ӧ�ա�ƫ�ߵ���Ӧ�ա����˵���Ӧ��
										 AND WO.ACCOUNT_DATE <= POD.POD_DATE --����ʱ����С����ʱ��
										 AND RCV.INVOICE_MARK = PKG_STL_COMMON.INVOICE_MARK_ONE
										 AND POD.POD_DATE >= P_BEGIN_DATE
										 AND POD.POD_DATE < P_END_DATE
							 GROUP BY RCV.CUSTOMER_CODE, --�ͻ�����
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
												RCV.WAYBILL_NO, --�˵���
												RCV.RECEIVABLE_NO, --���ݱ��
												RCV.BILL_TYPE, --����������
												RCV.PRODUCT_CODE, --��Ʒ����
												POD.ID)
			 WHERE WO_AMOUNT <> 0
						 AND RCV_AMOUNT <> 0;
	
		--5)ǩ���˵���01������ǩ��ʱ����Ӧ��δ�������
		DECLARE
		
			CURSOR CUR_POD IS
				SELECT *
					FROM STL.T_STL_POD POD
				 WHERE POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__POD
							 AND POD.POD_DATE >= P_BEGIN_DATE
							 AND POD.POD_DATE < P_END_DATE;
		
			V_POD_ROW STL.T_STL_POD%ROWTYPE;
		
		BEGIN
		
			FOR V_POD_ROW IN CUR_POD LOOP
			
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
								 PKG_STL_VCH_COMMON.PODO_DEST_RCV_NWO, --ҵ�񳡾�
								 CUSTOMER_CODE, --�ͻ�����
								 CUSTOMER_NAME, --�ͻ�����
								 CUSTOMER_TYPE, --�ͻ�����
								 ORIG_ORG_CODE, --ʼ�����ű��� 
								 ORIG_ORG_NAME, --ʼ����������
								 DEST_ORG_CODE, --���ﲿ�ű���
								 DEST_ORG_NAME, --���ﲿ������
								 WAYBILL_NO, --�˵���
								 RECEIVABLE_NO, --���ݱ��
								 ACCOUNT_DATE, --�������
								 BUSINESS_DATE, --ҵ������
								 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --��������
								 BILL_TYPE, --����������
								 RCV_AMOUNT - WO_AMOUNT, --���
								 TRANSPORT_FEE -
								 ROUND(TRANSPORT_FEE * WO_AMOUNT / RCV_AMOUNT), --�˷�
								 PICKUP_FEE - ROUND(PICKUP_FEE * WO_AMOUNT / RCV_AMOUNT), --�ӻ���
								 DELIVERY_GOODS_FEE -
								 ROUND(DELIVERY_GOODS_FEE * WO_AMOUNT / RCV_AMOUNT), --�ͻ���
								 PACKAGING_FEE -
								 ROUND(PACKAGING_FEE * WO_AMOUNT / RCV_AMOUNT), --��װ��
								 INSURANCE_FEE -
								 ROUND(INSURANCE_FEE * WO_AMOUNT / RCV_AMOUNT), --���۷�
								 COD_FEE - ROUND(COD_FEE * WO_AMOUNT / RCV_AMOUNT), --���ջ���������
								 RCV_AMOUNT - WO_AMOUNT -
								 (TRANSPORT_FEE -
								 ROUND(TRANSPORT_FEE * WO_AMOUNT / RCV_AMOUNT) +
								 PICKUP_FEE - ROUND(PICKUP_FEE * WO_AMOUNT / RCV_AMOUNT) +
								 DELIVERY_GOODS_FEE -
								 ROUND(DELIVERY_GOODS_FEE * WO_AMOUNT / RCV_AMOUNT) +
								 PACKAGING_FEE -
								 ROUND(PACKAGING_FEE * WO_AMOUNT / RCV_AMOUNT) +
								 INSURANCE_FEE -
								 ROUND(INSURANCE_FEE * WO_AMOUNT / RCV_AMOUNT) + COD_FEE -
								 ROUND(COD_FEE * WO_AMOUNT / RCV_AMOUNT)), --������
								 PRODUCT_CODE --��Ʒ���� 
						FROM (SELECT RCV.CUSTOMER_CODE, --�ͻ�����
												 RCV.CUSTOMER_NAME, --�ͻ�����
												 RCV.CUSTOMER_TYPE, --�ͻ�����
												 DECODE(RCV.PRODUCT_CODE,
																PKG_STL_COMMON.PRODUCT_CODE_PKG,
																RCV.EXPRESS_ORIG_ORG_CODE��RCV.ORIG_ORG_CODE) ORIG_ORG_CODE, --ʼ�����ű��� 
												 DECODE(RCV.PRODUCT_CODE,
																PKG_STL_COMMON.PRODUCT_CODE_PKG,
																RCV.EXPRESS_ORIG_ORG_NAME��RCV.ORIG_ORG_NAME) ORIG_ORG_NAME, --ʼ����������
												 DECODE(RCV.PRODUCT_CODE,
																PKG_STL_COMMON.PRODUCT_CODE_PKG,
																RCV.EXPRESS_DEST_ORG_CODE��RCV.DEST_ORG_CODE) DEST_ORG_CODE, --���ﲿ�ű���
												 DECODE(RCV.PRODUCT_CODE,
																PKG_STL_COMMON.PRODUCT_CODE_PKG,
																RCV.EXPRESS_DEST_ORG_NAME��RCV.DEST_ORG_NAME) DEST_ORG_NAME, --���ﲿ������
												 RCV.WAYBILL_NO, --�˵���
												 RCV.RECEIVABLE_NO, --���ݱ��
												 RCV.ACCOUNT_DATE, --�������
												 RCV.BUSINESS_DATE, --ҵ������
												 RCV.BILL_TYPE, --����������
												 RCV.AMOUNT RCV_AMOUNT, --���
												 NVL((SELECT SUM(WO.AMOUNT)
															 FROM STL.T_STL_BILL_WRITEOFF WO
															WHERE (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
																		WO.END_NO = RCV.RECEIVABLE_NO)
																		AND
																		WO.ACCOUNT_DATE <= V_POD_ROW.POD_DATE),
														 0) WO_AMOUNT,
												 RCV.TRANSPORT_FEE, --�˷�
												 RCV.PICKUP_FEE, --�ӻ���
												 RCV.DELIVERY_GOODS_FEE, --�ͻ���
												 RCV.PACKAGING_FEE, --��װ��
												 RCV.INSURANCE_FEE, --���۷�
												 RCV.COD_FEE, --���ջ���������
												 RCV.OTHER_FEE, --������
												 RCV.PRODUCT_CODE --��Ʒ���� 
										FROM STL.T_STL_BILL_RECEIVABLE RCV
									 WHERE RCV.ID IN
												 (SELECT ID
														FROM (SELECT ID, IS_RED_BACK, INVOICE_MARK
																		FROM (SELECT *
																						FROM STL.T_STL_BILL_RECEIVABLE T
																					 WHERE T.WAYBILL_NO =
																								 V_POD_ROW.WAYBILL_NO
																								 AND
																								 T.SOURCE_BILL_TYPE =
																								 PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W
																								 AND T.ACCOUNT_DATE <=
																								 V_POD_ROW.POD_DATE
																								 AND
																								 T.BILL_TYPE IN
																								 (PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_DEST,
																									PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_PARTIAL,
																									PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AA)
																					 ORDER BY T.ACCOUNT_DATE DESC,
																										T.CREATE_TIME  DESC,
																										T.IS_RED_BACK  ASC)
																	 WHERE ROWNUM <= 1)
													 WHERE IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
																 AND INVOICE_MARK =
																 PKG_STL_COMMON.INVOICE_MARK_ONE))
					 WHERE RCV_AMOUNT <> WO_AMOUNT
								 AND RCV_AMOUNT <> 0;
			
			END LOOP;
		
		END;
	
		RETURN TRUE;
	
	EXCEPTION
		WHEN OTHERS THEN
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				P_BEGIN_DATE,
																				P_END_DATE,
																				'FUNC_PROCESS_DAILY_DETAIL',
																				'ǩ���˵���01����������ϸ' || '�쳣');
		
			--����ʧ�ܱ�־
			RETURN FALSE;
	END;

END PKG_STL_VCH_DAILY_PODO;
/
