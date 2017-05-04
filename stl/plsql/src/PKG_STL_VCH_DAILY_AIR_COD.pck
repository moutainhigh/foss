CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_AIR_COD IS

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

END PKG_STL_VCH_DAILY_AIR_COD;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_AIR_COD IS

	-----------------------------------------------------------------
	-- ƾ֤������������ϸ
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
																		 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
																		 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
																		 ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
	 IS
	BEGIN
		--1. ���˻�����ջ����ֽ���ǩ��  
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
						 PKG_STL_VCH_COMMON.AIR_COD_CH_POD, --ҵ�񳡾�
						 RCV.CUSTOMER_CODE,
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 RCV.ORIG_ORG_CODE,
						 RCV.ORIG_ORG_NAME, --ʼ����������
						 RCV.DEST_ORG_CODE,
						 RCV.DEST_ORG_NAME, --���ﲿ������
						 NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --�˵��� --RCV.WAYBILL_NO,
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
					ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
						 AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --���
				JOIN STL.T_STL_BILL_RECEIVABLE RCV
					ON RCV.RECEIVABLE_NO = WO.END_NO
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --Ӧ�յ�
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --�����Ӧ��
						 AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --���˴�����ջ���Ӧ��
						 AND RPMT.PAYMENT_TYPE = PKG_STL_COMMON.PAYMENT_TYPE_CASH --�ֽ�
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
	
		--2. ���˻�����ջ���������ǩ��  
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
						 PKG_STL_VCH_COMMON.AIR_COD_CD_POD, --ҵ�񳡾� 
						 RCV.CUSTOMER_CODE,
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 RCV.ORIG_ORG_CODE,
						 RCV.ORIG_ORG_NAME, --ʼ����������
						 RCV.DEST_ORG_CODE,
						 RCV.DEST_ORG_NAME, --���ﲿ������
						 NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --RCV.WAYBILL_NO,
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
					ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
						 AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --���
				JOIN STL.T_STL_BILL_RECEIVABLE RCV
					ON RCV.RECEIVABLE_NO = WO.END_NO
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --Ӧ�յ�
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --�����Ӧ��
						 AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --���˴�����ջ���Ӧ��
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
												 AND T.POD_DATE < WO.ACCOUNT_DATE) = 1
						 AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND WO.ACCOUNT_DATE < P_END_DATE;
	
		--3. ����ǩ��ʱ�Ѻ������ջ��� 
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
						 PKG_STL_VCH_COMMON.AIR_COD_POD_WO_COD, --ҵ�񳡾�
						 RCV.CUSTOMER_CODE,
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 RCV.ORIG_ORG_CODE,
						 RCV.ORIG_ORG_NAME, --ʼ����������
						 RCV.DEST_ORG_CODE,
						 RCV.DEST_ORG_NAME, --���ﲿ������
						 NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --RCV.WAYBILL_NO,
						 RCV.RECEIVABLE_NO,
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
				FROM STL.T_STL_POD POD
				JOIN STL.T_STL_BILL_RECEIVABLE RCV
					ON RCV.WAYBILL_NO = POD.WAYBILL_NO
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
				JOIN STL.T_STL_BILL_WRITEOFF WO
					ON (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
						 WO.END_NO = RCV.RECEIVABLE_NO)
			 WHERE POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__POD --ǩ��
						 AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --���˴�����ջ���Ӧ��
						 AND WO.ACCOUNT_DATE < POD.POD_DATE --ǩ��ʱ�䣾�������ļ���ʱ��
						 AND POD.POD_DATE >= P_BEGIN_DATE
						 AND POD.POD_DATE < P_END_DATE;
	
		--4. ���˷�ǩ��ʱ�Ѻ������ջ���  
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
						 PKG_STL_VCH_COMMON.AIR_COD_NPOD_WO_COD, --ҵ�񳡾�
						 RCV.CUSTOMER_CODE,
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 RCV.ORIG_ORG_CODE,
						 RCV.ORIG_ORG_NAME, --ʼ����������
						 RCV.DEST_ORG_CODE,
						 RCV.DEST_ORG_NAME, --���ﲿ������
						 RCV.WAYBILL_NO,
						 NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --RCV.RECEIVABLE_NO,
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
				FROM STL.T_STL_POD POD
				JOIN STL.T_STL_BILL_RECEIVABLE RCV
					ON RCV.WAYBILL_NO = POD.WAYBILL_NO
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
				JOIN STL.T_STL_BILL_WRITEOFF WO
					ON (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
						 WO.END_NO = RCV.RECEIVABLE_NO)
			 WHERE POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__UPD --��ǩ��
						 AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --���˴�����ջ���Ӧ��
						 AND WO.ACCOUNT_DATE < POD.POD_DATE --ǩ��ʱ�䣾�������ļ���ʱ��
						 AND POD.POD_DATE >= P_BEGIN_DATE
						 AND POD.POD_DATE < P_END_DATE;
	
		--5. ����ǩ��ʱδ�������ջ��� 
		/**
    ��ǩ�ձ��е�ǩ��ʱ����ͳ�ƣ�ȡ�����˵����ɵģ�Ӧ�յ�����ȥ��Ӧ���������ۼƽ���
    Ӧ�յ��ĵ���������Ϊ�����ջ���Ӧ�գ���Ʒ����Ϊ����׼���ˣ������˴�����ջ���Ӧ�գ���ǩ��ʱ�䣾�������ļ���ʱ��
    */
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
								 PKG_STL_VCH_COMMON.AIR_COD_POD_NWO_COD, --ҵ�񳡾�
								 RCV.CUSTOMER_CODE,
								 RCV.CUSTOMER_NAME, --�ͻ�����
								 RCV.CUSTOMER_TYPE, --�ͻ�����
								 RCV.ORIG_ORG_CODE,
								 RCV.ORIG_ORG_NAME, --ʼ����������
								 RCV.DEST_ORG_CODE,
								 RCV.DEST_ORG_NAME, --���ﲿ������
								 NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --RCV.WAYBILL_NO,
								 RCV.RECEIVABLE_NO,
								 RCV.ACCOUNT_DATE, --��������
								 RCV.BUSINESS_DATE, --ҵ������
								 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --Ӧ�յ�
								 RCV.BILL_TYPE, --��������          
								 RCV.AMOUNT -
								 NVL((SELECT SUM(WO.AMOUNT)
											 FROM STL.T_STL_BILL_WRITEOFF WO
											WHERE (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
														WO.END_NO = RCV.RECEIVABLE_NO)
														AND WO.ACCOUNT_DATE < V_POD_ROW.POD_DATE),
										 0),
								 0,
								 0,
								 0,
								 0,
								 0,
								 0,
								 0,
								 RCV.PRODUCT_CODE
						FROM STL.T_STL_BILL_RECEIVABLE RCV
					 WHERE RCV.ID IN
								 (SELECT ID
										FROM (SELECT *
														FROM STL.T_STL_BILL_RECEIVABLE T
													 WHERE T.WAYBILL_NO = V_POD_ROW.WAYBILL_NO
																 AND T.BILL_TYPE =
																 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --���˴�����ջ���Ӧ��
																 AND T.ACCOUNT_DATE < V_POD_ROW.POD_DATE
													 ORDER BY T.ACCOUNT_DATE DESC,
																		T.CREATE_TIME  DESC,
																		T.IS_RED_BACK  ASC)
									 WHERE ROWNUM <= 1)
								 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --ȡ����һ��Ӧ�յ�������Ǻ쵥���͹��˵�
								 AND RCV.AMOUNT <> 0
								 AND RCV.AMOUNT <>
								 NVL((SELECT SUM(WO.AMOUNT)
													 FROM STL.T_STL_BILL_WRITEOFF WO
													WHERE (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
																WO.END_NO = RCV.RECEIVABLE_NO)
																AND WO.ACCOUNT_DATE < V_POD_ROW.POD_DATE),
												 0);
			
			END LOOP;
		END;
	
		--6. ���˷�ǩ��ʱδ�������ջ���  
		DECLARE
		
			CURSOR CUR_UPD IS
				SELECT *
					FROM STL.T_STL_POD POD
				 WHERE POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__UPD
							 AND POD.POD_DATE >= P_BEGIN_DATE
							 AND POD.POD_DATE < P_END_DATE;
		
			V_POD_ROW STL.T_STL_POD%ROWTYPE;
		
		BEGIN
			FOR V_POD_ROW IN CUR_UPD LOOP
			
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
								 PKG_STL_VCH_COMMON.AIR_COD_NPOD_NWO_COD, --ҵ�񳡾�
								 RCV.CUSTOMER_CODE,
								 RCV.CUSTOMER_NAME, --�ͻ�����
								 RCV.CUSTOMER_TYPE, --�ͻ�����
								 RCV.ORIG_ORG_CODE,
								 RCV.ORIG_ORG_NAME, --ʼ����������
								 RCV.DEST_ORG_CODE,
								 RCV.DEST_ORG_NAME, --���ﲿ������
								 NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --RCV.WAYBILL_NO,
								 RCV.RECEIVABLE_NO,
								 RCV.ACCOUNT_DATE, --��������
								 RCV.BUSINESS_DATE, --ҵ������
								 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --Ӧ�յ�
								 RCV.BILL_TYPE, --��������          
								 RCV.AMOUNT -
								 NVL((SELECT SUM(WO.AMOUNT)
											 FROM STL.T_STL_BILL_WRITEOFF WO
											WHERE (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
														WO.END_NO = RCV.RECEIVABLE_NO)
														AND WO.ACCOUNT_DATE < V_POD_ROW.POD_DATE),
										 0),
								 0,
								 0,
								 0,
								 0,
								 0,
								 0,
								 0,
								 RCV.PRODUCT_CODE
						FROM STL.T_STL_BILL_RECEIVABLE RCV
					
					 WHERE RCV.ID IN
								 (SELECT ID
										FROM (SELECT *
														FROM STL.T_STL_BILL_RECEIVABLE T
													 WHERE T.WAYBILL_NO = V_POD_ROW.WAYBILL_NO
																 AND T.BILL_TYPE =
																 PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --���˴�����ջ���Ӧ��
																 AND T.ACCOUNT_DATE < V_POD_ROW.POD_DATE
													 ORDER BY T.ACCOUNT_DATE DESC,
																		T.CREATE_TIME  DESC,
																		T.IS_RED_BACK  ASC)
									 WHERE ROWNUM <= 1)
								 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --ȡ����һ��Ӧ�յ�������Ǻ쵥���͹��˵� 
								 AND RCV.AMOUNT <> 0
								 AND RCV.AMOUNT -
								 NVL((SELECT SUM(WO.AMOUNT)
													 FROM STL.T_STL_BILL_WRITEOFF WO
													WHERE (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
																WO.END_NO = RCV.RECEIVABLE_NO)
																AND WO.ACCOUNT_DATE < V_POD_ROW.POD_DATE),
												 0) <> 0;
			
			END LOOP;
		END;
	
		--7. ���˻�����ջ����ֽ�δǩ��  
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
						 PKG_STL_VCH_COMMON.AIR_COD_CH_NPOD, --ҵ�񳡾�
						 RCV.CUSTOMER_CODE,
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 RCV.ORIG_ORG_CODE,
						 RCV.ORIG_ORG_NAME, --ʼ����������
						 RCV.DEST_ORG_CODE,
						 RCV.DEST_ORG_NAME, --���ﲿ������
						 NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --RCV.WAYBILL_NO,
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
					ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
						 AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --���
				JOIN STL.T_STL_BILL_RECEIVABLE RCV
					ON RCV.RECEIVABLE_NO = WO.END_NO
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --Ӧ�յ�
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --�����Ӧ��    
						 AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --���˴�����ջ���Ӧ��
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
	
		--8. ���˻�����ջ�������δǩ��  
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
						 PKG_STL_VCH_COMMON.AIR_COD_CD_NPOD, --ҵ�񳡾�
						 RCV.CUSTOMER_CODE,
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 RCV.ORIG_ORG_CODE,
						 RCV.ORIG_ORG_NAME, --ʼ����������
						 RCV.DEST_ORG_CODE,
						 RCV.DEST_ORG_NAME, --���ﲿ������
						 NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --RCV.WAYBILL_NO,
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
					ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
						 AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --���
				JOIN STL.T_STL_BILL_RECEIVABLE RCV
					ON RCV.RECEIVABLE_NO = WO.END_NO
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --Ӧ�յ�
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR --�����Ӧ��      
						 AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --���˴�����ջ���Ӧ��
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
	
		--9. ���˵������Ӧ����Ӧ�մ��ջ�����ǩ�� 
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
						 PKG_STL_VCH_COMMON.AIR_COD_WO_AGENCY_PAY_POD, --ҵ�񳡾�:����Ӧ���������ɱ���Ӧ�մ��ջ��δǩ�գ�
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 RCV.ORIG_ORG_CODE, --�������ű���
						 RCV.ORIG_ORG_NAME, --ʼ����������
						 RCV.DEST_ORG_CODE, --���ﲿ�ű���
						 RCV.DEST_ORG_NAME, --���ﲿ������
						 NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --RCV.WAYBILL_NO, --�˵���
						 WO.WRITEOFF_BILL_NO,
						 WO.ACCOUNT_DATE, --��������
						 RCV.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --Ӧ�յ�
						 RCV.BILL_TYPE, --��������
						 WO.AMOUNT, --���
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
					ON WO.BEGIN_NO = RCV.RECEIVABLE_NO
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --Ӧ�յ�
				JOIN STL.T_STL_BILL_PAYABLE PAY
					ON WO.END_NO = PAY.PAYABLE_NO
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --Ӧ����
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
						 AND
						 PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY --���˵������Ӧ��
						 AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --���˴�����ջ���Ӧ��
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
	
		--10.��������Ӧ����Ӧ�մ��ջ�����ǩ�� 
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
						 PKG_STL_VCH_COMMON.AIR_COD_WO_OTH_PAY_COD, --ҵ�񳡾�:����Ӧ���������ɱ���Ӧ�մ��ջ��δǩ�գ�
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 RCV.ORIG_ORG_CODE, --�������ű���
						 RCV.ORIG_ORG_NAME, --ʼ����������
						 RCV.DEST_ORG_CODE, --���ﲿ�ű���
						 RCV.DEST_ORG_NAME, --���ﲿ������
						 NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --RCV.WAYBILL_NO, --�˵���
						 WO.WRITEOFF_BILL_NO,
						 WO.ACCOUNT_DATE, --��������
						 RCV.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --Ӧ�յ�
						 RCV.BILL_TYPE, --��������
						 WO.AMOUNT, --���
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
					ON WO.BEGIN_NO = RCV.RECEIVABLE_NO
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --Ӧ�յ�
				JOIN STL.T_STL_BILL_PAYABLE PAY
					ON WO.END_NO = PAY.PAYABLE_NO
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --Ӧ����
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
						 AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER --��������Ӧ��
						 AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --���˴�����ջ���Ӧ��
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
	
		--11.���˵������Ӧ����Ӧ�մ��ջ���δǩ�� 
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
						 PKG_STL_VCH_COMMON.AIR_COD_WO_AGENCY_PAY_NPOD, --ҵ�񳡾�:����Ӧ���������ɱ���Ӧ�մ��ջ��δǩ�գ�
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 RCV.ORIG_ORG_CODE, --�������ű���
						 RCV.ORIG_ORG_NAME, --ʼ����������
						 RCV.DEST_ORG_CODE, --���ﲿ�ű���
						 RCV.DEST_ORG_NAME, --���ﲿ������
						 NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --RCV.WAYBILL_NO, --�˵���
						 WO.WRITEOFF_BILL_NO,
						 WO.ACCOUNT_DATE, --��������
						 RCV.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --Ӧ�յ�
						 RCV.BILL_TYPE, --��������
						 WO.AMOUNT, --���
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
					ON WO.BEGIN_NO = RCV.RECEIVABLE_NO
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --Ӧ�յ�
				JOIN STL.T_STL_BILL_PAYABLE PAY
					ON WO.END_NO = PAY.PAYABLE_NO
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --Ӧ����
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
						 AND
						 PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY --���˵������Ӧ��
						 AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --���˴�����ջ���Ӧ��
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
	
		--12.��������Ӧ����Ӧ�մ��ջ���δǩ��
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
						 PKG_STL_VCH_COMMON.AIR_COD_WO_OTH_NPOD, --ҵ�񳡾�:����Ӧ���������ɱ���Ӧ�մ��ջ��δǩ�գ�
						 RCV.CUSTOMER_CODE, --�ͻ�����
						 RCV.CUSTOMER_NAME, --�ͻ�����
						 RCV.CUSTOMER_TYPE, --�ͻ�����
						 RCV.ORIG_ORG_CODE, --�������ű���
						 RCV.ORIG_ORG_NAME, --ʼ����������
						 RCV.DEST_ORG_CODE, --���ﲿ�ű���
						 RCV.DEST_ORG_NAME, --���ﲿ������
						 NVL(RCV.WAYBILL_NO, RCV.SOURCE_BILL_NO), --RCV.WAYBILL_NO, --�˵���
						 WO.WRITEOFF_BILL_NO,
						 WO.ACCOUNT_DATE, --��������
						 RCV.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YS, --Ӧ�յ�
						 RCV.BILL_TYPE, --��������
						 WO.AMOUNT, --���
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
					ON WO.BEGIN_NO = RCV.RECEIVABLE_NO
						 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --Ӧ�յ�
				JOIN STL.T_STL_BILL_PAYABLE PAY
					ON WO.END_NO = PAY.PAYABLE_NO
						 AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO --Ӧ����
			 WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP --Ӧ�ճ�Ӧ��
						 AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER --��������Ӧ��
						 AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_AAC --Ӧ�յ��ĵ���������Ϊ���ջ���Ӧ�գ���Ʒ����Ϊ����׼���ˣ������˴�����ջ���Ӧ��
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

END PKG_STL_VCH_DAILY_AIR_COD;
/
