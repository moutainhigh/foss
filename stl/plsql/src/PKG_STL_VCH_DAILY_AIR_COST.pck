CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_AIR_COST IS

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

END PKG_STL_VCH_DAILY_AIR_COST;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_AIR_COST IS

	-----------------------------------------------------------------
	-- ƾ֤������������ϸ
	-----------------------------------------------------------------
	FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
																		 P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
																		 P_END_DATE   DATE -- ����ʱ�� 2012-12-22
																		 ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
	 IS
	BEGIN
		--1)���˺��չ�˾�˷�ȷ��
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
						 PKG_STL_VCH_COMMON.AIR_COST_COM_CONFIRM, --ҵ�񳡾�
						 PA.CUSTOMER_CODE, --�ͻ�����
						 PA.CUSTOMER_NAME, --�ͻ�����
						 PA.CUSTOMER_TYPE, --�ͻ�����
						 PA.ORIG_ORG_CODE, --ʼ�����ű��� 
						 PA.ORIG_ORG_NAME, --ʼ����������
						 PA.DEST_ORG_CODE, --���ﲿ�ű���
						 PA.DEST_ORG_NAME, --���ﲿ������
             decode(nvl(PA.waybill_no, 'N/A'), 'N/A', pa.source_bill_no, pa.waybill_no),--�˵���
						 PA.PAYABLE_NO, --���ݱ��
						 PA.ACCOUNT_DATE, --�������
						 PA.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
						 PA.BILL_TYPE, --����������
						 PA.AMOUNT, --���
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_AF) --��Ʒ����
				FROM STL.T_STL_BILL_PAYABLE PA
			 WHERE PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR --���չ�˾�˷�Ӧ����
						 AND PA.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND PA.ACCOUNT_DATE < P_END_DATE;
	
		--2)���˳�������Ӧ��ȷ��
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
						 PKG_STL_VCH_COMMON.AIR_COST_ORIG_AGENCY_CFM, --ҵ�񳡾�
						 PA.CUSTOMER_CODE, --�ͻ�����
						 PA.CUSTOMER_NAME, --�ͻ�����
						 PA.CUSTOMER_TYPE, --�ͻ�����
						 PA.ORIG_ORG_CODE, --ʼ�����ű��� 
						 PA.ORIG_ORG_NAME, --ʼ����������
						 PA.DEST_ORG_CODE, --���ﲿ�ű���
						 PA.DEST_ORG_NAME, --���ﲿ������
						 decode(nvl(PA.waybill_no, 'N/A'), 'N/A', pa.source_bill_no, pa.waybill_no), --�˵���
						 PA.PAYABLE_NO, --���ݱ��
						 PA.ACCOUNT_DATE, --�������
						 PA.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
						 PA.BILL_TYPE, --����������
						 PA.AMOUNT, --���
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_AF) --��Ʒ����
				FROM STL.T_STL_BILL_PAYABLE PA
			 WHERE PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_ORIGINAL --���˳�������Ӧ����
						 AND PA.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND PA.ACCOUNT_DATE < P_END_DATE;
	
		--3)���˵������Ӧ������
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
						 PKG_STL_VCH_COMMON.AIR_COST_DEST_AGENCY_GEN, --ҵ�񳡾�
						 PA.CUSTOMER_CODE, --�ͻ�����
						 PA.CUSTOMER_NAME, --�ͻ�����
						 PA.CUSTOMER_TYPE, --�ͻ�����
						 PA.ORIG_ORG_CODE, --ʼ�����ű��� 
						 PA.ORIG_ORG_NAME, --ʼ����������
						 PA.DEST_ORG_CODE, --���ﲿ�ű���
						 PA.DEST_ORG_NAME, --���ﲿ������
						 decode(nvl(PA.waybill_no, 'N/A'), 'N/A', pa.source_bill_no, pa.waybill_no), --�˵���
						 PA.PAYABLE_NO, --���ݱ��
						 PA.ACCOUNT_DATE, --�������
						 PA.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
						 PA.BILL_TYPE, --����������
						 PA.AMOUNT, --���
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_AF) --��Ʒ����
				FROM STL.T_STL_BILL_PAYABLE PA
			 WHERE PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY --���˵������Ӧ��
						 AND PA.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND PA.ACCOUNT_DATE < P_END_DATE;
	
		--4)���˵������Ӧ���ɱ�ȷ�ϣ���ǩ�ձ��е�ǩ��ʱ����ͳ�ƣ�ȡ�����˵����ɵĿ��˵������Ӧ�������
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
						 PKG_STL_VCH_COMMON.AIR_COST_DEST_AGENCY_CFM, --ҵ�񳡾�
						 PA.CUSTOMER_CODE, --�ͻ�����
						 PA.CUSTOMER_NAME, --�ͻ�����
						 PA.CUSTOMER_TYPE, --�ͻ�����
						 PA.ORIG_ORG_CODE, --ʼ�����ű��� 
						 PA.ORIG_ORG_NAME, --ʼ����������
						 PA.DEST_ORG_CODE, --���ﲿ�ű���
						 PA.DEST_ORG_NAME, --���ﲿ������
						 decode(nvl(PA.waybill_no, 'N/A'), 'N/A', pa.source_bill_no, pa.waybill_no), --�˵���
						 PA.PAYABLE_NO, --���ݱ��
						 PA.ACCOUNT_DATE, --�������
						 PA.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
						 PA.BILL_TYPE, --����������
						 PA.AMOUNT, --���
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_AF) --��Ʒ����
				FROM STL.T_STL_BILL_PAYABLE PA
				JOIN STL.T_STL_POD POD
					ON POD.WAYBILL_NO = PA.WAYBILL_NO
						 AND POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__POD
			 WHERE POD.POD_DATE >= P_BEGIN_DATE
						 AND POD.POD_DATE < P_END_DATE
						 AND PA.ACCOUNT_DATE < POD.POD_DATE
						 AND
						 PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY; --���˵������Ӧ��
	
		--5)���˵������Ӧ���ɱ���ȷ�ϣ���ǩ�ձ��еķ�ǩ��ʱ����ͳ�ƣ�ȡ�����˵����ɵĿ��˵������Ӧ�������
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
						 PKG_STL_VCH_COMMON.AIR_COST_DEST_AGENCY_NCFM, --ҵ�񳡾�
						 PA.CUSTOMER_CODE, --�ͻ�����
						 PA.CUSTOMER_NAME, --�ͻ�����
						 PA.CUSTOMER_TYPE, --�ͻ�����
						 PA.ORIG_ORG_CODE, --ʼ�����ű��� 
						 PA.ORIG_ORG_NAME, --ʼ����������
						 PA.DEST_ORG_CODE, --���ﲿ�ű���
						 PA.DEST_ORG_NAME, --���ﲿ������
						 decode(nvl(PA.waybill_no, 'N/A'), 'N/A', pa.source_bill_no, pa.waybill_no), --�˵���
						 PA.PAYABLE_NO, --���ݱ��
						 PA.ACCOUNT_DATE, --�������
						 PA.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
						 PA.BILL_TYPE, --����������
						 PA.AMOUNT, --���
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 NVL(PA.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_AF) --��Ʒ����
				FROM STL.T_STL_BILL_PAYABLE PA
				JOIN STL.T_STL_POD POD
					ON POD.WAYBILL_NO = PA.WAYBILL_NO
						 AND POD.POD_TYPE = PKG_STL_COMMON.POD__POD_TYPE__UPD
			 WHERE POD.POD_DATE >= P_BEGIN_DATE
						 AND POD.POD_DATE < P_END_DATE
						 AND PA.ACCOUNT_DATE < POD.POD_DATE
						 AND
						 PA.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY; --���˵������Ӧ��
	
		--6)����Ӧ���ɱ�ȷ�ϣ�����������Ӧ�����ļ���������ͳ�ƣ�ȡ��������Ӧ�����Ľ��
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
						 PKG_STL_VCH_COMMON.AIR_COST_OTHER_CONFIRM, --ҵ�񳡾�
						 PAT.CUSTOMER_CODE, --�ͻ�����
						 PAT.CUSTOMER_NAME, --�ͻ�����
						 PAT.CUSTOMER_TYPE, --�ͻ�����
						 PAT.ORIG_ORG_CODE, --ʼ�����ű��� 
						 PAT.ORIG_ORG_NAME, --ʼ����������
						 PAT.DEST_ORG_CODE, --���ﲿ�ű���
						 PAT.DEST_ORG_NAME, --���ﲿ������
						 decode(nvl(PAT.waybill_no, 'N/A'), 'N/A', pat.source_bill_no, pat.waybill_no), --�˵���
						 PAT.PAYMENT_NO, --���ݱ��
						 PAT.ACCOUNT_DATE, --�������
						 PAT.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__YF, --��������
						 PAT.BILL_TYPE, --����������
						 PAT.AMOUNT, --���
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 NVL(PAT.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_AF) --��Ʒ����
				FROM STL.T_STL_BILL_PAYABLE PAT
			 WHERE PAT.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER --��������Ӧ��
						 AND PAT.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND PAT.ACCOUNT_DATE < P_END_DATE;
	
		--7�����˳ɱ���������
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
						 PKG_STL_VCH_COMMON.AIR_COST_PAY_APPLY, --ҵ�񳡾�
						 PAY.CUSTOMER_CODE, --�ͻ�����
						 PAY.CUSTOMER_NAME, --�ͻ�����
						 PAY.CUSTOMER_TYPE, --�ͻ�����
						 PAY.ORIG_ORG_CODE, --ʼ�����ű��� 
						 PAY.ORIG_ORG_NAME, --ʼ����������
						 PAY.DEST_ORG_CODE, --���ﲿ�ű���
						 PAY.DEST_ORG_NAME, --���ﲿ������
						 decode(nvl(PAY.waybill_no, 'N/A'), 'N/A', pay.source_bill_no, pay.waybill_no), --�˵���
						 PAY.PAYABLE_NO, --���ݱ��
						 PAY.ACCOUNT_DATE, --�������
						 PAY.BUSINESS_DATE, --ҵ������
						 PKG_STL_COMMON.BILL_PARENT_TYPE__FK, --��������
						 PAY.BILL_TYPE, --����������
						 DECODE(PMT.IS_RED_BACK,
										PKG_STL_COMMON.IS_RED_BACK_YES,
										-1,
										PKG_STL_COMMON.IS_RED_BACK_NO,
										1) * PMTD.PAY_AMOUNT, --���
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 0,
						 NVL(PAY.PRODUCT_CODE, PKG_STL_COMMON.PRODUCT_CODE_AF) --��Ʒ����
				FROM STL.T_STL_BILL_PAYMENT PMT
				JOIN STL.T_STL_BILL_PAYMENT_D PMTD
					ON PMTD.PAYMENT_NO = PMT.PAYMENT_NO
				JOIN STL.T_STL_BILL_PAYABLE PAY
					ON PMTD.SOURCE_BILL_NO = PAY.PAYABLE_NO
             AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
			 WHERE PAY.BILL_TYPE IN (PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR, --���չ�˾�˷�
															 PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_ORIGINAL, --���˳�������Ӧ��
															 PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY, --���˵������Ӧ��
															 PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER --��������Ӧ��
															 )
						 AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE
						 AND PMT.ACCOUNT_DATE < P_END_DATE
						 AND PMTD.PAY_AMOUNT <> 0;
	
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

END PKG_STL_VCH_DAILY_AIR_COST;
/
