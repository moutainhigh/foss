CREATE OR REPLACE PACKAGE STV.PKG_STL_VCH_COMMON IS

	-- ==============================================================
	-- Author  : ZHUWEI
	-- Created : 2012-12-07 14:29:42
	-- Purpose : ƾ֤ͨ�÷���
	-- ==============================================================

	RATIO_PRECISION NUMBER := 4; -- ����

	---========================== ƾ֤�����ֵ� ==================================
	/**
  * VOUCHER_BATCH_STATUS ƾ֤����״̬
  */
	VOUCHER_BATCH_STATUS_PROCESS VARCHAR2(20) := 'PROCESS'; -- ������
	VOUCHER_BATCH_STATUS_SUCCESS VARCHAR2(20) := 'SUCCESS'; -- �ɹ�
	VOUCHER_BATCH_STATUS_FAILURE VARCHAR2(20) := 'FAILURE'; -- ʧ��

	/**
  * VOUCHER_ORG_TYPE ��������
  */
	VOUCHER_ORG_TYPE_ORIG VARCHAR2(20) := 'O'; -- ʼ��
	VOUCHER_ORG_TYPE_DEST VARCHAR2(20) := 'D'; -- ����
	VOUCHER_ORG_TYPE_AIR  VARCHAR2(20) := 'A'; --����
	VOUCHER_ORG_TYPE_PL   VARCHAR2(20) := 'P'; --ƫ��
	VOUCHER_ORG_TYPE_PKG  VARCHAR2(20) := 'G'; --�����

	/**
  * VOUCHER_BUSINESS_TYPE ƾ֤ҵ������
  */
	VOUCHER_BUSINESS_TYPE_DE      VARCHAR2(20) := 'DE'; -- ����
	VOUCHER_BUSINESS_TYPE_UR_ORIG VARCHAR2(20) := 'UR_ORIG'; -- ǩ�ջ���
	VOUCHER_BUSINESS_TYPE_UR_DEST VARCHAR2(20) := 'UR_DEST'; -- ǩ�յ���
	VOUCHER_BUSINESS_TYPE_COD     VARCHAR2(20) := 'COD'; -- ���ջ���
	VOUCHER_BUSINESS_TYPE_OR      VARCHAR2(20) := 'OR'; -- СƱ
	VOUCHER_BUSINESS_TYPE_CLAIM   VARCHAR2(20) := 'CLAIM'; -- ����
	VOUCHER_BUSINESS_TYPE_BA      VARCHAR2(20) := 'BA'; -- ���˳�Ӧ��
	VOUCHER_BUSINESS_TYPE_AC      VARCHAR2(20) := 'AC'; -- ��������
	VOUCHER_BUSINESS_TYPE_RD      VARCHAR2(20) := 'RD'; -- ���˷�
	VOUCHER_BUSINESS_TYPE_CN      VARCHAR2(20) := 'CN'; -- ���񲹾�
	VOUCHER_BUSINESS_TYPE_PL      VARCHAR2(20) := 'PL'; -- ƫ�߳ɱ�
	VOUCHER_BUSINESS_TYPE_AIR     VARCHAR2(20) := 'AIR'; -- ���˳ɱ�
	VOUCHER_BUSINESS_TYPE_DR      VARCHAR2(20) := 'DR'; -- Ԥ��
	VOUCHER_BUSINESS_TYPE_ADP     VARCHAR2(20) := 'ADP'; -- ����Ԥ��
	VOUCHER_BUSINESS_TYPE_OTH     VARCHAR2(20) := 'OT'; -- ����Ӧ��Ӧ��

	VOUCHER_BUSINESS_TYPE_AFI  VARCHAR2(20) := 'AFI'; -- ��������
	VOUCHER_BUSINESS_TYPE_AFR  VARCHAR2(20) := 'AFR'; -- �����±�
	VOUCHER_BUSINESS_TYPE_PLI  VARCHAR2(20) := 'PLI'; -- ƫ������
	VOUCHER_BUSINESS_TYPE_PLR  VARCHAR2(20) := 'PLR'; -- ƫ���±�
	VOUCHER_BUSINESS_TYPE_RFD  VARCHAR2(20) := 'RFD'; -- ר�ߵ���
	VOUCHER_BUSINESS_TYPE_RFI  VARCHAR2(20) := 'RFI'; -- ʼ����������
	VOUCHER_BUSINESS_TYPE_RFO  VARCHAR2(20) := 'RFO'; -- ʼ���±�
	VOUCHER_BUSINESS_TYPE_RCOD VARCHAR2(20) := 'RCOD'; -- �˴��ջ���

	---========================== ƾ֤ҵ�񳡾� ==================================
	DEO_CH                         VARCHAR2(30) := 'DEO_CH'; --�����ֽ�
	DEO_CD                         VARCHAR2(30) := 'DEO_CD'; --�������п�
	DET_CH                         VARCHAR2(30) := 'DET_CH'; --�����ֽ�
	DET_CD                         VARCHAR2(30) := 'DET_CD'; --�������п�
	URO_ORIG_CH_NPOD               VARCHAR2(30) := 'URO_ORIG_CH_NPOD'; --�����ֽ�δǩ��
	URO_ORIG_CD_NPOD               VARCHAR2(30) := 'URO_ORIG_CD_NPOD'; --��������δǩ��
	URO_ORIG_CH_POD                VARCHAR2(30) := 'URO_ORIG_CH_POD'; --�����ֽ���ǩ��
	URO_ORIG_CD_POD                VARCHAR2(30) := 'URO_ORIG_CD_POD'; --����������ǩ��
	URT_ORIG_CH_NPOD               VARCHAR2(30) := 'URT_ORIG_CH_NPOD'; --�����ֽ�δǩ��
	URT_ORIG_CD_NPOD               VARCHAR2(30) := 'URT_ORIG_CD_NPOD'; --��������δǩ��
	URT_ORIG_CH_POD                VARCHAR2(30) := 'URT_ORIG_CH_POD'; --�����ֽ���ǩ��
	URT_ORIG_CD_POD                VARCHAR2(30) := 'URT_ORIG_CD_POD'; --����������ǩ��
	URO_DEST_CH_NPOD               VARCHAR2(30) := 'URO_DEST_CH_NPOD'; --�����ֽ�δǩ��
	URO_DEST_CD_NPOD               VARCHAR2(30) := 'URO_DEST_CD_NPOD'; --��������δǩ��
	URO_DEST_CH_POD                VARCHAR2(30) := 'URO_DEST_CH_POD'; --�����ֽ���ǩ��
	URO_DEST_CD_POD                VARCHAR2(30) := 'URO_DEST_CD_POD'; --����������ǩ��
	URT_DEST_CH_NPOD               VARCHAR2(30) := 'URT_DEST_CH_NPOD'; --�����ֽ�δǩ��
	URT_DEST_CD_NPOD               VARCHAR2(30) := 'URT_DEST_CD_NPOD'; --��������δǩ��
	URT_DEST_CH_POD                VARCHAR2(30) := 'URT_DEST_CH_POD'; --�����ֽ���ǩ��
	URT_DEST_CD_POD                VARCHAR2(30) := 'URT_DEST_CD_POD'; --����������ǩ��
	UROP_DEST_CH_NPOD              VARCHAR2(30) := 'UROP_DEST_CH_NPOD'; --�����ֽ�δǩ��
	UROP_DEST_CD_NPOD              VARCHAR2(30) := 'UROP_DEST_CD_NPOD'; --��������δǩ��
	UROP_DEST_CH_POD               VARCHAR2(30) := 'UROP_DEST_CH_POD'; --�����ֽ���ǩ��
	UROP_DEST_CD_POD               VARCHAR2(30) := 'UROP_DEST_CD_POD'; --����������ǩ��
	URTP_DEST_CH_NPOD              VARCHAR2(30) := 'URTP_DEST_CH_NPOD'; --�����ֽ�δǩ��
	URTP_DEST_CD_NPOD              VARCHAR2(30) := 'URTP_DEST_CD_NPOD'; --��������δǩ��
	URTP_DEST_CH_POD               VARCHAR2(30) := 'URTP_DEST_CH_POD'; --�����ֽ���ǩ��
	URTP_DEST_CD_POD               VARCHAR2(30) := 'URTP_DEST_CD_POD'; --����������ǩ��
	UROA_DEST_CH_NPOD              VARCHAR2(30) := 'UROA_DEST_CH_NPOD'; --�����ֽ�δǩ��
	UROA_DEST_CD_NPOD              VARCHAR2(30) := 'UROA_DEST_CD_NPOD'; --��������δǩ��
	UROA_DEST_CH_POD               VARCHAR2(30) := 'UROA_DEST_CH_POD'; --�����ֽ���ǩ��
	UROA_DEST_CD_POD               VARCHAR2(30) := 'UROA_DEST_CD_POD'; --����������ǩ��
	URTA_DEST_CH_NPOD              VARCHAR2(30) := 'URTA_DEST_CH_NPOD'; --�����ֽ�δǩ��
	URTA_DEST_CD_NPOD              VARCHAR2(30) := 'URTA_DEST_CD_NPOD'; --��������δǩ��
	URTA_DEST_CH_POD               VARCHAR2(30) := 'URTA_DEST_CH_POD'; --�����ֽ���ǩ��
	URTA_DEST_CD_POD               VARCHAR2(30) := 'URTA_DEST_CD_POD'; --����������ǩ��
	PODO_CASH_COLLECTED            VARCHAR2(30) := 'PODO_CASH_COLLECTED'; --ǩ��ʱ�ָ����տ���
	PODO_ORIG_RCV_WO               VARCHAR2(30) := 'PODO_ORIG_RCV_WO'; --ǩ��ʱʼ��Ӧ���Ѻ������
	PODO_ORIG_RCV_NWO              VARCHAR2(30) := 'PODO_ORIG_RCV_NWO'; --ǩ��ʱʼ��Ӧ��δ�������
	PODO_DEST_RCV_WO               VARCHAR2(30) := 'PODO_DEST_RCV_WO'; --ǩ��ʱ����Ӧ���Ѻ������
	PODO_DEST_RCV_NWO              VARCHAR2(30) := 'PODO_DEST_RCV_NWO'; --ǩ��ʱ����Ӧ��δ�������
	PODT_CASH_COLLECTED            VARCHAR2(30) := 'PODT_CASH_COLLECTED'; --ǩ��ʱ�ָ����տ���
	PODT_ORIG_RCV_WO               VARCHAR2(30) := 'PODT_ORIG_RCV_WO'; --ǩ��ʱʼ��Ӧ���Ѻ������
	PODT_ORIG_RCV_NWO              VARCHAR2(30) := 'PODT_ORIG_RCV_NWO'; --ǩ��ʱʼ��Ӧ��δ�������
	PODT_DEST_RCV_WO               VARCHAR2(30) := 'PODT_DEST_RCV_WO'; --ǩ��ʱ����Ӧ���Ѻ������
	PODT_DEST_RCV_NWO              VARCHAR2(30) := 'PODT_DEST_RCV_NWO'; --ǩ��ʱ����Ӧ��δ�������
	UPDO_CASH_COLLECTED            VARCHAR2(30) := 'UPDO_CASH_COLLECTED'; --��ǩ��ʱ�ָ����տ���
	UPDO_ORIG_RCV_WO               VARCHAR2(30) := 'UPDO_ORIG_RCV_WO'; --��ǩ��ʱʼ��Ӧ���Ѻ������
	UPDO_ORIG_RCV_NWO              VARCHAR2(30) := 'UPDO_ORIG_RCV_NWO'; --��ǩ��ʱʼ��Ӧ��δ�������
	UPDO_DEST_RCV_WO               VARCHAR2(30) := 'UPDO_DEST_RCV_WO'; --��ǩ��ʱ����Ӧ���Ѻ������
	UPDO_DEST_RCV_NWO              VARCHAR2(30) := 'UPDO_DEST_RCV_NWO'; --��ǩ��ʱ����Ӧ��δ�������
	UPDT_CASH_COLLECTED            VARCHAR2(30) := 'UPDT_CASH_COLLECTED'; --��ǩ��ʱ�ָ����տ���
	UPDT_ORIG_RCV_WO               VARCHAR2(30) := 'UPDT_ORIG_RCV_WO'; --��ǩ��ʱʼ��Ӧ���Ѻ������
	UPDT_ORIG_RCV_NWO              VARCHAR2(30) := 'UPDT_ORIG_RCV_NWO'; --��ǩ��ʱʼ��Ӧ��δ�������
	UPDT_DEST_RCV_WO               VARCHAR2(30) := 'UPDT_DEST_RCV_WO'; --��ǩ��ʱ����Ӧ���Ѻ������
	UPDT_DEST_RCV_NWO              VARCHAR2(30) := 'UPDT_DEST_RCV_NWO'; --��ǩ��ʱ����Ӧ��δ�������
	CUST_DR_OCH                    VARCHAR2(30) := 'CUST_DR_OCH'; --Ԥ�տͻ��ֽ�
	CUST_DR_OCD                    VARCHAR2(30) := 'CUST_DR_OCD'; --Ԥ�տͻ�����
	CUST_DR_TCH                    VARCHAR2(30) := 'CUST_DR_TCH'; --Ԥ�տͻ��ֽ�
	CUST_DR_TCD                    VARCHAR2(30) := 'CUST_DR_TCD'; --Ԥ�տͻ�����
	CUST_DRO_WO_DEST_RCVO_NPOD     VARCHAR2(30) := 'CUST_DRO_WO_DEST_RCVO_NPOD'; --01Ԥ�տͻ���01Ӧ�յ����˷�δǩ��
	CUST_DRO_WO_DEST_RCVO_NPODD    VARCHAR2(30) := 'CUST_DRO_WO_DEST_RCVO_NPODD'; --01Ԥ�տͻ���01Ӧ�յ����˷�δǩ��
	CUST_DRT_WO_DEST_RCVO_NPOD     VARCHAR2(30) := 'CUST_DRT_WO_DEST_RCVO_NPOD'; --02Ԥ�տͻ���01Ӧ�յ����˷�δǩ��
	CUST_DRT_WO_DEST_RCVO_NPODD    VARCHAR2(30) := 'CUST_DRT_WO_DEST_RCVO_NPODD'; --02Ԥ�տͻ���01Ӧ�յ����˷�δǩ��
	CUST_DRO_WO_DEST_RCVO_POD      VARCHAR2(30) := 'CUST_DRO_WO_DEST_RCVO_POD'; --01Ԥ�տͻ���01Ӧ�յ����˷���ǩ��
	CUST_DRO_WO_DEST_RCVO_PODD     VARCHAR2(30) := 'CUST_DRO_WO_DEST_RCVO_PODD'; --01Ԥ�տͻ���01Ӧ�յ����˷���ǩ��
	CUST_DRT_WO_DEST_RCVO_POD      VARCHAR2(30) := 'CUST_DRT_WO_DEST_RCVO_POD'; --02Ԥ�տͻ���01Ӧ�յ����˷���ǩ��
	CUST_DRT_WO_DEST_RCVO_PODD     VARCHAR2(30) := 'CUST_DRT_WO_DEST_RCVO_PODD'; --02Ԥ�տͻ���01Ӧ�յ����˷���ǩ��
	CUST_DRO_WO_ORIG_RCVO_NPOD     VARCHAR2(30) := 'CUST_DRO_WO_ORIG_RCVO_NPOD'; --01Ԥ�տͻ���01Ӧ��ʼ���˷�δǩ��
	CUST_DRO_WO_ORIG_RCVT_NPOD     VARCHAR2(30) := 'CUST_DRO_WO_ORIG_RCVT_NPOD'; --01Ԥ�տͻ���02Ӧ��ʼ���˷�δǩ��
	CUST_DRO_WO_ORIG_RCVT_NPODD    VARCHAR2(30) := 'CUST_DRO_WO_ORIG_RCVT_NPODD'; --01Ԥ�տͻ���02Ӧ��ʼ���˷�δǩ��
	CUST_DRT_WO_ORIG_RCVO_NPOD     VARCHAR2(30) := 'CUST_DRT_WO_ORIG_RCVO_NPOD'; --02Ԥ�տͻ���01Ӧ��ʼ���˷�δǩ��
	CUST_DRT_WO_ORIG_RCVO_NPODD    VARCHAR2(30) := 'CUST_DRT_WO_ORIG_RCVO_NPODD'; --02Ԥ�տͻ���01Ӧ��ʼ���˷�δǩ��
	CUST_DRO_WO_ORIG_RCVO_POD      VARCHAR2(30) := 'CUST_DRO_WO_ORIG_RCVO_POD'; --01Ԥ�տͻ���01Ӧ��ʼ���˷���ǩ��
	CUST_DRO_WO_ORIG_RCVT_POD      VARCHAR2(30) := 'CUST_DRO_WO_ORIG_RCVT_POD'; --01Ԥ�տͻ���02Ӧ��ʼ���˷���ǩ��
	CUST_DRO_WO_ORIG_RCVT_PODD     VARCHAR2(30) := 'CUST_DRO_WO_ORIG_RCVT_PODD'; --01Ԥ�տͻ���02Ӧ��ʼ���˷���ǩ��
	CUST_DRT_WO_ORIG_RCVO_POD      VARCHAR2(30) := 'CUST_DRT_WO_ORIG_RCVO_POD'; --02Ԥ�տͻ���01Ӧ��ʼ���˷���ǩ��
	CUST_DRT_WO_ORIG_RCVO_PODD     VARCHAR2(30) := 'CUST_DRT_WO_ORIG_RCVO_PODD'; --02Ԥ�տͻ���01Ӧ��ʼ���˷���ǩ��
	CUST_DRO_PAY_APPLY             VARCHAR2(30) := 'CUST_DRO_PAY_APPLY'; --01ʼ����Ԥ�ո�������
	CUST_DRT_PAY_APPLY             VARCHAR2(30) := 'CUST_DRT_PAY_APPLY'; --02ʼ����Ԥ�ո�������
	CUST_DRO_WO_DEST_RCVT_NPOD     VARCHAR2(30) := 'CUST_DRO_WO_DEST_RCVT_NPOD'; --01Ԥ�տͻ���02Ӧ�յ����˷�δǩ��
	CUST_DRO_WO_DEST_RCVT_NPODD    VARCHAR2(30) := 'CUST_DRO_WO_DEST_RCVT_NPODD'; --01Ԥ�տͻ���02Ӧ�յ����˷�δǩ��
	CUST_DRT_WO_DEST_RCVT_NPOD     VARCHAR2(30) := 'CUST_DRT_WO_DEST_RCVT_NPOD'; --02Ԥ�տͻ���02Ӧ�յ����˷�δǩ��
	CUST_DRT_WO_DEST_RCVT_NPODD    VARCHAR2(30) := 'CUST_DRT_WO_DEST_RCVT_NPODD'; --02Ԥ�տͻ���02Ӧ�յ����˷�δǩ��
	CUST_DRO_WO_DEST_RCVT_POD      VARCHAR2(30) := 'CUST_DRO_WO_DEST_RCVT_POD'; --01Ԥ�տͻ���02Ӧ�յ����˷���ǩ��
	CUST_DRO_WO_DEST_RCVT_PODD     VARCHAR2(30) := 'CUST_DRO_WO_DEST_RCVT_PODD'; --01Ԥ�տͻ���02Ӧ�յ����˷���ǩ��
	CUST_DRT_WO_DEST_RCVT_POD      VARCHAR2(30) := 'CUST_DRT_WO_DEST_RCVT_POD'; --02Ԥ�տͻ���02Ӧ�յ����˷���ǩ��
	CUST_DRT_WO_DEST_RCVT_PODD     VARCHAR2(30) := 'CUST_DRT_WO_DEST_RCVT_PODD'; --02Ԥ�տͻ���02Ӧ�յ����˷���ǩ��
	CUST_DRT_WO_ORIG_RCVT_NPOD     VARCHAR2(30) := 'CUST_DRT_WO_ORIG_RCVT_NPOD'; --02Ԥ�տͻ���02Ӧ��ʼ���˷�δǩ��
	CUST_DRT_WO_ORIG_RCVT_POD      VARCHAR2(30) := 'CUST_DRT_WO_ORIG_RCVT_POD'; --02Ԥ�տͻ���02Ӧ��ʼ���˷���ǩ��
	COD_PAY_WO_DEST_RCVO_POD       VARCHAR2(30) := 'COD_PAY_WO_DEST_RCVO_POD'; --Ӧ�����ջ����01Ӧ�յ����˷���ǩ��
	COD_PAY_WO_DEST_RCVO_NPOD      VARCHAR2(30) := 'COD_PAY_WO_DEST_RCVO_NPOD'; --Ӧ�����ջ����01Ӧ�յ����˷�δǩ��
	COD_PAY_WO_ORIG_RCVO_POD       VARCHAR2(30) := 'COD_PAY_WO_ORIG_RCVO_POD'; --Ӧ�����ջ����01Ӧ��ʼ���˷���ǩ��
	COD_PAY_WO_ORIG_RCVO_NPOD      VARCHAR2(30) := 'COD_PAY_WO_ORIG_RCVO_NPOD'; --Ӧ�����ջ����01Ӧ��ʼ���˷�δǩ��
	COD_PAY_WO_DEST_RCVT_POD       VARCHAR2(30) := 'COD_PAY_WO_DEST_RCVT_POD'; --Ӧ�����ջ����02Ӧ�յ����˷���ǩ��
	COD_PAY_WO_DEST_RCVT_NPOD      VARCHAR2(30) := 'COD_PAY_WO_DEST_RCVT_NPOD'; --Ӧ�����ջ����02Ӧ�յ����˷�δǩ��
	COD_PAY_WO_ORIG_RCVT_POD       VARCHAR2(30) := 'COD_PAY_WO_ORIG_RCVT_POD'; --Ӧ�����ջ����02Ӧ��ʼ���˷���ǩ��
	COD_PAY_WO_ORIG_RCVT_NPOD      VARCHAR2(30) := 'COD_PAY_WO_ORIG_RCVT_NPOD'; --Ӧ�����ջ����02Ӧ��ʼ���˷�δǩ��
	COD_UR_CH_NPOD                 VARCHAR2(30) := 'COD_UR_CH_NPOD'; --������ջ����ֽ�δǩ��
	COD_UR_CD_NPOD                 VARCHAR2(30) := 'COD_UR_CD_NPOD'; --������ջ�������δǩ��
	COD_PAY_WO_OTH_RCVO            VARCHAR2(30) := 'COD_PAY_WO_OTH_RCVO'; --Ӧ�����ջ����01СƱӦ��
	COD_PAY_WO_OTH_RCVT            VARCHAR2(30) := 'COD_PAY_WO_OTH_RCVT'; --Ӧ�����ջ����02СƱӦ��
	PL_COST_WO_DEST_RCVO_POD       VARCHAR2(30) := 'PL_COST_WO_DEST_RCVO_POD'; --Ӧ��ƫ�ߴ���ɱ���01Ӧ�յ����˷���ǩ��
	PL_COST_WO_DEST_RCVO_PODP      VARCHAR2(30) := 'PL_COST_WO_DEST_RCVO_PODP'; --Ӧ��ƫ�ߴ���ɱ���01Ӧ�յ����˷���ǩ��
	PL_COST_WO_DEST_RCVO_NPOD      VARCHAR2(30) := 'PL_COST_WO_DEST_RCVO_NPOD'; --Ӧ��ƫ�ߴ���ɱ���01Ӧ�յ����˷�δǩ��
	PL_COST_WO_DEST_RCVO_NPODP     VARCHAR2(30) := 'PL_COST_WO_DEST_RCVO_NPODP'; --Ӧ��ƫ�ߴ���ɱ���01Ӧ�յ����˷�δǩ��
	PL_COST_WO_DEST_RCVT_POD       VARCHAR2(30) := 'PL_COST_WO_DEST_RCVT_POD'; --Ӧ��ƫ�ߴ���ɱ���02Ӧ�յ����˷���ǩ��
	PL_COST_WO_DEST_RCVT_PODP      VARCHAR2(30) := 'PL_COST_WO_DEST_RCVT_PODP'; --Ӧ��ƫ�ߴ���ɱ���02Ӧ�յ����˷���ǩ��
	PL_COST_WO_DEST_RCVT_NPOD      VARCHAR2(30) := 'PL_COST_WO_DEST_RCVT_NPOD'; --Ӧ��ƫ�ߴ���ɱ���02Ӧ�յ����˷�δǩ��
	PL_COST_WO_DEST_RCVT_NPODP     VARCHAR2(30) := 'PL_COST_WO_DEST_RCVT_NPODP'; --Ӧ��ƫ�ߴ���ɱ���02Ӧ�յ����˷�δǩ��
	PL_COST_ENTRY                  VARCHAR2(30) := 'PL_COST_ENTRY'; --�ⷢ��¼��
	PL_COST_CONFIRM                VARCHAR2(30) := 'PL_COST_CONFIRM'; --ƫ�ߴ���ɱ�ȷ��
	PL_COST_NCONFIRM               VARCHAR2(30) := 'PL_COST_NCONFIRM'; --ƫ�ߴ���ɱ���ȷ��
	PL_COST_PAY_APPLY              VARCHAR2(30) := 'PL_COST_PAY_APPLY'; --ƫ�ߴ���ɱ���������
	PL_DR_WO_DEST_RCVO_POD         VARCHAR2(30) := 'PL_DR_WO_DEST_RCVO_POD'; --Ԥ��ƫ�ߴ����01Ӧ�յ����˷���ǩ��
	PL_DR_WO_DEST_RCVO_PODD        VARCHAR2(30) := 'PL_DR_WO_DEST_RCVO_PODD'; --Ԥ��ƫ�ߴ����01Ӧ�յ����˷���ǩ��
	PL_DR_WO_DEST_RCVO_NPOD        VARCHAR2(30) := 'PL_DR_WO_DEST_RCVO_NPOD'; --Ԥ��ƫ�ߴ����01Ӧ�յ����˷�δǩ��
	PL_DR_WO_DEST_RCVO_NPODD       VARCHAR2(30) := 'PL_DR_WO_DEST_RCVO_NPODD'; --Ԥ��ƫ�ߴ����01Ӧ�յ����˷�δǩ��
	PL_DR_WO_DEST_RCVT_POD         VARCHAR2(30) := 'PL_DR_WO_DEST_RCVT_POD'; --Ԥ��ƫ�ߴ����02Ӧ�յ����˷���ǩ��
	PL_DR_WO_DEST_RCVT_PODD        VARCHAR2(30) := 'PL_DR_WO_DEST_RCVT_PODD'; --Ԥ��ƫ�ߴ����02Ӧ�յ����˷���ǩ��
	PL_DR_WO_DEST_RCVT_NPOD        VARCHAR2(30) := 'PL_DR_WO_DEST_RCVT_NPOD'; --Ԥ��ƫ�ߴ����02Ӧ�յ����˷�δǩ��
	PL_DR_WO_DEST_RCVT_NPODD       VARCHAR2(30) := 'PL_DR_WO_DEST_RCVT_NPODD'; --Ԥ��ƫ�ߴ����02Ӧ�յ����˷�δǩ��
	PL_DR_CH                       VARCHAR2(30) := 'PL_DR_CH'; --Ԥ��ƫ�ߴ����ֽ�
	PL_DR_CD                       VARCHAR2(30) := 'PL_DR_CD'; --Ԥ��ƫ�ߴ�������
	PL_DR_PAY_APPLY                VARCHAR2(30) := 'PL_DR_PAY_APPLY'; --ƫ����Ԥ�ո�������
	AL_DR_WO_DEST_RCVO_POD         VARCHAR2(30) := 'AL_DR_WO_DEST_RCVO_POD'; --Ԥ�տ���/���������01Ӧ�յ����˷���ǩ��
	AL_DR_WO_DEST_RCVO_NPOD        VARCHAR2(30) := 'AL_DR_WO_DEST_RCVO_NPOD'; --Ԥ�տ���/���������01Ӧ�յ����˷�δǩ��
	AL_DR_WO_DEST_RCVT_POD         VARCHAR2(30) := 'AL_DR_WO_DEST_RCVT_POD'; --Ԥ�տ���/���������02Ӧ�յ����˷���ǩ��
	AL_DR_WO_DEST_RCVT_NPOD        VARCHAR2(30) := 'AL_DR_WO_DEST_RCVT_NPOD'; --Ԥ�տ���/���������02Ӧ�յ����˷�δǩ��
	ALPWR_WO_DEST_RCVO_POD         VARCHAR2(30) := 'ALPWR_WO_DEST_RCVO_POD'; --Ӧ���������/��������ɱ���01Ӧ�յ����˷���ǩ��
	ALPWR_WO_DEST_RCVO_NPOD        VARCHAR2(30) := 'ALPWR_WO_DEST_RCVO_NPOD'; --Ӧ���������/��������ɱ���01Ӧ�յ����˷�δǩ��
	OTH_PAY_WO_DEST_RCVO_POD       VARCHAR2(30) := 'OTH_PAY_WO_DEST_RCVO_POD'; --����Ӧ����01Ӧ�յ����˷���ǩ��
	OTH_PAY_WO_DEST_RCVO_NPOD      VARCHAR2(30) := 'OTH_PAY_WO_DEST_RCVO_NPOD'; --����Ӧ����01Ӧ�յ����˷�δǩ��
	ALPWR_WO_DEST_RCVT_POD         VARCHAR2(30) := 'ALPWR_WO_DEST_RCVT_POD'; --Ӧ���������/��������ɱ���02Ӧ�յ����˷���ǩ��
	ALPWR_WO_DEST_RCVT_NPOD        VARCHAR2(30) := 'ALPWR_WO_DEST_RCVT_NPOD'; --Ӧ���������/��������ɱ���02Ӧ�յ����˷�δǩ��
	OTH_PAY_WO_DEST_RCVT_POD       VARCHAR2(30) := 'OTH_PAY_WO_DEST_RCVT_POD'; --����Ӧ����02Ӧ�յ����˷���ǩ��
	OTH_PAY_WO_DEST_RCVT_NPOD      VARCHAR2(30) := 'OTH_PAY_WO_DEST_RCVT_NPOD'; --����Ӧ����02Ӧ�յ����˷�δǩ��
	CLAIM_ORIGO_INCOME             VARCHAR2(30) := 'CLAIM_ORIGO_INCOME'; --01���������
	CLAIM_ORIGO_COST               VARCHAR2(30) := 'CLAIM_ORIGO_COST'; --01������ɱ�
	CLAIM_ORIGT_INCOME             VARCHAR2(30) := 'CLAIM_ORIGT_INCOME'; --02���������
	CLAIM_ORIGT_COST               VARCHAR2(30) := 'CLAIM_ORIGT_COST'; --02������ɱ�
	CLAIM_ORIGO_WO_ORIG_RCVO_POD   VARCHAR2(30) := 'CLAIM_ORIGO_WO_ORIG_RCVO_POD'; --01�����01ʼ��Ӧ����ǩ��
	CLAIM_ORIGO_WO_ORIG_RCVT_POD   VARCHAR2(30) := 'CLAIM_ORIGO_WO_ORIG_RCVT_POD'; --01�����02ʼ��Ӧ����ǩ��
	CLAIM_ORIGO_WO_ORIG_RCVT_PODP  VARCHAR2(30) := 'CLAIM_ORIGO_WO_ORIG_RCVT_PODP'; --01�����02ʼ��Ӧ����ǩ��
	CLAIM_ORIGT_ORIG_RCVO_POD      VARCHAR2(30) := 'CLAIM_ORIGT_ORIG_RCVO_POD'; --02�����01ʼ��Ӧ����ǩ��
	CLAIM_ORIGT_ORIG_RCVO_PODP     VARCHAR2(30) := 'CLAIM_ORIGT_ORIG_RCVO_PODP'; --02�����01ʼ��Ӧ����ǩ��
	CLAIM_ORIGO_ORIG_RCVO_NPOD     VARCHAR2(30) := 'CLAIM_ORIGO_ORIG_RCVO_NPOD'; --01�����01ʼ��Ӧ��δǩ��
	CLAIM_ORIGO_WO_ORIG_RCVT_NPOD  VARCHAR2(30) := 'CLAIM_ORIGO_WO_ORIG_RCVT_NPOD'; --01�����02ʼ��Ӧ��δǩ��
	CLAIM_ORIGO_WO_ORIG_RCVT_NPODP VARCHAR2(30) := 'CLAIM_ORIGO_WO_ORIG_RCVT_NPODP'; --01�����02ʼ��Ӧ��δǩ��
	CLAIM_ORIGT_WO_ORIG_RCVO_NPOD  VARCHAR2(30) := 'CLAIM_ORIGT_WO_ORIG_RCVO_NPOD'; --02�����01ʼ��Ӧ��δǩ��
	CLAIM_ORIGT_WO_ORIG_RCVO_NPODP VARCHAR2(30) := 'CLAIM_ORIGT_WO_ORIG_RCVO_NPODP'; --02�����01ʼ��Ӧ��δǩ��
	CLAIM_ORIGO_PAY_APPLY          VARCHAR2(30) := 'CLAIM_ORIGO_PAY_APPLY'; --01���⸶������
	CLAIM_ORIGT_PAY_APPLY          VARCHAR2(30) := 'CLAIM_ORIGT_PAY_APPLY'; --02���⸶������
	CLAIM_ORIGT_WO_ORIG_RCVT_POD   VARCHAR2(30) := 'CLAIM_ORIGT_WO_ORIG_RCVT_POD'; --02�����02ʼ��Ӧ����ǩ��
	CLAIM_ORIGT_WO_ORIG_RCVT_NPOD  VARCHAR2(30) := 'CLAIM_ORIGT_WO_ORIG_RCVT_NPOD'; --02�����02ʼ��Ӧ��δǩ��
	CLAIM_DESTO_INCOME             VARCHAR2(30) := 'CLAIM_DESTO_INCOME'; --01���������
	CLAIM_DESTO_COST               VARCHAR2(30) := 'CLAIM_DESTO_COST'; --01������ɱ�
	CLAIM_DESTT_INCOME             VARCHAR2(30) := 'CLAIM_DESTT_INCOME'; --02���������
	CLAIM_DESTT_COST               VARCHAR2(30) := 'CLAIM_DESTT_COST'; --02������ɱ�
	CLAIM_DESTO_WO_DEST_RCVO_POD   VARCHAR2(30) := 'CLAIM_DESTO_WO_DEST_RCVO_POD'; --01����嵽01��Ӧ����ǩ��
	CLAIM_DESTO_WO_DEST_RCVO_PODP  VARCHAR2(30) := 'CLAIM_DESTO_WO_DEST_RCVO_PODP'; --01����嵽01��Ӧ����ǩ��
	CLAIM_DESTT_WO_DEST_RCVO_POD   VARCHAR2(30) := 'CLAIM_DESTT_WO_DEST_RCVO_POD'; --02�����01����Ӧ����ǩ��
	CLAIM_DESTT_WO_DEST_RCVO_PODP  VARCHAR2(30) := 'CLAIM_DESTT_WO_DEST_RCVO_PODP'; --02�����01����Ӧ����ǩ��
	CLAIM_DESTO_WO_DEST_RCVO_NPOD  VARCHAR2(30) := 'CLAIM_DESTO_WO_DEST_RCVO_NPOD'; --01�����01����Ӧ��δǩ��
	CLAIM_DESTO_WO_DEST_RCVO_NPODP VARCHAR2(30) := 'CLAIM_DESTO_WO_DEST_RCVO_NPODP'; --01�����01����Ӧ��δǩ��
	CLAIM_DESTT_WO_DEST_RCVO_NPOD  VARCHAR2(30) := 'CLAIM_DESTT_WO_DEST_RCVO_NPOD'; --02�����01����Ӧ��δǩ��
	CLAIM_DESTT_WO_DEST_RCVO_NPODP VARCHAR2(30) := 'CLAIM_DESTT_WO_DEST_RCVO_NPODP'; --02�����01����Ӧ��δǩ��
	CLAIM_DESTO_WO_DEST_RCVT_POD   VARCHAR2(30) := 'CLAIM_DESTO_WO_DEST_RCVT_POD'; --01�����02����Ӧ����ǩ��
	CLAIM_DESTO_WO_DEST_RCVT_PODP  VARCHAR2(30) := 'CLAIM_DESTO_WO_DEST_RCVT_PODP'; --01�����02����Ӧ����ǩ��
	CLAIM_DESTT_WO_DEST_RCVT_POD   VARCHAR2(30) := 'CLAIM_DESTT_WO_DEST_RCVT_POD'; --02�����02����Ӧ����ǩ��
	CLAIM_DESTT_WO_DEST_RCVT_PODP  VARCHAR2(30) := 'CLAIM_DESTT_WO_DEST_RCVT_PODP'; --02�����02����Ӧ����ǩ��
	CLAIM_DESTO_WO_DEST_RCVT_NPOD  VARCHAR2(30) := 'CLAIM_DESTO_WO_DEST_RCVT_NPOD'; --01�����02����Ӧ��δǩ��
	CLAIM_DESTO_WO_DEST_RCVT_NPODP VARCHAR2(30) := 'CLAIM_DESTO_WO_DEST_RCVT_NPODP'; --01�����02����Ӧ��δǩ��
	CLAIM_DESTT_WO_DEST_RCVT_NPOD  VARCHAR2(30) := 'CLAIM_DESTT_WO_DEST_RCVT_NPOD'; --02�����02����Ӧ��δǩ��
	CLAIM_DESTT_WO_DEST_RCVT_NPODP VARCHAR2(30) := 'CLAIM_DESTT_WO_DEST_RCVT_NPODP'; --02�����02����Ӧ��δǩ��
	CLAIM_DESTO_PAY_APPLY          VARCHAR2(30) := 'CLAIM_DESTO_PAY_APPLY'; --01���⸶������
	CLAIM_DESTT_PAY_APPLY          VARCHAR2(30) := 'CLAIM_DESTT_PAY_APPLY'; --02���⸶������
	EX_ORIG_RCVO_POD               VARCHAR2(30) := 'EX_ORIG_RCVO_POD'; --01Ӧ��ʼ���˷���ǩ��
	EX_DEST_RCVO_POD               VARCHAR2(30) := 'EX_DEST_RCVO_POD'; --01Ӧ�յ����˷���ǩ��
	EX_ORIG_RCVT_POD               VARCHAR2(30) := 'EX_ORIG_RCVT_POD'; --02Ӧ��ʼ���˷���ǩ��
	EX_DEST_RCVT_POD               VARCHAR2(30) := 'EX_DEST_RCVT_POD'; --02Ӧ�յ����˷���ǩ��
	BD_WO_ORIG_RCVO_POD            VARCHAR2(30) := 'BD_WO_ORIG_RCVO_POD'; --���˳�01Ӧ��ʼ���˷���ǩ��
	BD_WO_DEST_RCVO_POD            VARCHAR2(30) := 'BD_WO_DEST_RCVO_POD'; --���˳�01Ӧ�յ����˷���ǩ��
	BD_WO_ORIG_RCVT_POD            VARCHAR2(30) := 'BD_WO_ORIG_RCVT_POD'; --���˳�02Ӧ��ʼ���˷���ǩ��
	BD_WO_DEST_RCVT_POD            VARCHAR2(30) := 'BD_WO_DEST_RCVT_POD'; --���˳�02Ӧ�յ����˷���ǩ��
	OR_CH_AC                       VARCHAR2(30) := 'OR_CH_AC'; --СƱ�ֽ�֮�¹���� 
	OR_CH_SI                       VARCHAR2(30) := 'OR_CH_SI'; --СƱ�ֽ�֮������Ʒ����
	OR_CH_OPAY                     VARCHAR2(30) := 'OR_CH_OPAY'; --СƱ�ֽ�֮�ͻ��ึ�˷ѻ��̵㳤���� 
	OR_CH_OTHER                    VARCHAR2(30) := 'OR_CH_OTHER'; --СƱ�ֽ�֮����
	OR_CH_PBIO                     VARCHAR2(30) := 'OR_CH_PBIO'; --01СƱ�ֽ���Ӫҵ������
	OR_CH_PBIT                     VARCHAR2(30) := 'OR_CH_PBIT'; --02СƱ�ֽ���Ӫҵ������
	OR_CD_AC                       VARCHAR2(30) := 'OR_CD_AC'; --СƱ����֮�¹���� 
	OR_CD_BANK_INT                 VARCHAR2(30) := 'OR_CD_BANK_INT'; --СƱ����֮����Ա����Ϣ
	OR_CD_OPAY                     VARCHAR2(30) := 'OR_CD_OPAY'; --СƱ����֮�ͻ��ึ�˷ѻ��̵㳤���� 
	OR_CD_OTHER                    VARCHAR2(30) := 'OR_CD_OTHER'; --СƱ����֮����
	OR_CD_PBIO                     VARCHAR2(30) := 'OR_CD_PBIO'; --01СƱ������Ӫҵ������
	OR_CD_PBIT                     VARCHAR2(30) := 'OR_CD_PBIT'; --02СƱ������Ӫҵ������
	OR_RCVO_PBI                    VARCHAR2(30) := 'OR_RCVO_PBI'; --01СƱӦ����Ӫҵ������
	OR_RCVT_PBI                    VARCHAR2(30) := 'OR_RCVT_PBI'; --02СƱӦ����Ӫҵ������
	OR_CH_UR_RCVO                  VARCHAR2(30) := 'OR_CH_UR_RCVO'; --�����ֽ��01СƱӦ��
	OR_CD_UR_RCVO                  VARCHAR2(30) := 'OR_CD_UR_RCVO'; --�������г�01СƱӦ��
	OR_CH_UR_RCVT                  VARCHAR2(30) := 'OR_CH_UR_RCVT'; --�����ֽ��02СƱӦ��
	OR_CD_UR_RCVT                  VARCHAR2(30) := 'OR_CD_UR_RCVT'; --�������г�02СƱӦ��
	OR_COD_PAY_WO_RCVO             VARCHAR2(30) := 'OR_COD_PAY_WO_RCVO'; --Ӧ�����ջ����01СƱӦ��
	OR_COD_PAY_WO_RCVT             VARCHAR2(30) := 'OR_COD_PAY_WO_RCVT'; --Ӧ�����ջ����02СƱӦ��
	OR_CLAIM_PAYO_WO_RCVO          VARCHAR2(30) := 'OR_CLAIM_PAYO_WO_RCVO'; --01Ӧ�������01СƱӦ��
	OR_CLAIM_PAYO_WO_RCVT          VARCHAR2(30) := 'OR_CLAIM_PAYO_WO_RCVT'; --01Ӧ�������02СƱӦ��
	OR_CLAIM_PAYO_WO_RCVTP         VARCHAR2(30) := 'OR_CLAIM_PAYO_WO_RCVTP'; --01Ӧ�������02СƱӦ��
	OR_CLAIM_PAYT_WO_RCVT          VARCHAR2(30) := 'OR_CLAIM_PAYT_WO_RCVT'; --02Ӧ�������02СƱӦ��
	OR_CLAIM_PAYT_WO_RCVO          VARCHAR2(30) := 'OR_CLAIM_PAYT_WO_RCVO'; --02Ӧ�������01СƱӦ��
	OR_CLAIM_PAYT_WO_RCVOP         VARCHAR2(30) := 'OR_CLAIM_PAYT_WO_RCVOP'; --02Ӧ�������01СƱӦ��
	OR_CUST_DRO_WO_RCVO            VARCHAR2(30) := 'OR_CUST_DRO_WO_RCVO'; --01Ԥ�տͻ���01СƱӦ��
	OR_CUST_DRO_WO_RCVT            VARCHAR2(30) := 'OR_CUST_DRO_WO_RCVT'; --01Ԥ�տͻ���02СƱӦ��
	OR_CUST_DRO_WO_RCVTD           VARCHAR2(30) := 'OR_CUST_DRO_WO_RCVTD'; --01Ԥ�տͻ���02СƱӦ��
	OR_CUST_DRT_WO_RCVO            VARCHAR2(30) := 'OR_CUST_DRT_WO_RCVO'; --02Ԥ�տͻ���01СƱӦ��
	OR_CUST_DRT_WO_RCVOD           VARCHAR2(30) := 'OR_CUST_DRT_WO_RCVOD'; --02Ԥ�տͻ���01СƱӦ��
	OR_CUST_DRT_WO_RCVT            VARCHAR2(30) := 'OR_CUST_DRT_WO_RCVT'; --02Ԥ�տͻ���02СƱӦ��
	OR_EX_WO_RCVO                  VARCHAR2(30) := 'OR_EX_WO_RCVO'; --�쳣�������01СƱӦ��
	OR_BAD_WO_RCVO                 VARCHAR2(30) := 'OR_BAD_WO_RCVO'; --������ʧ��01СƱӦ��
	OR_EX_WO_RCVT                  VARCHAR2(30) := 'OR_EX_WO_RCVT'; --�쳣�������02СƱӦ��
	OR_BAD_WO_RCVT                 VARCHAR2(30) := 'OR_BAD_WO_RCVT'; --������ʧ��02СƱӦ��
	AC_ORIG_RCV_NWOO               VARCHAR2(30) := 'AC_ORIG_RCV_NWOO'; --01������Ϊ�½���ʱǷ������֧��δ����
	AC_ORIG_RCV_WOO                VARCHAR2(30) := 'AC_ORIG_RCV_WOO'; --01������Ϊ�½���ʱǷ������֧���Ѻ���
	AC_CASHO                       VARCHAR2(30) := 'AC_CASHO'; --01������Ϊ�ֽ����п�
	AC_ORIG_RCV_NWOT               VARCHAR2(30) := 'AC_ORIG_RCV_NWOT'; --02������Ϊ�½���ʱǷ������֧��δ����
	AC_ORIG_RCV_WOT                VARCHAR2(30) := 'AC_ORIG_RCV_WOT'; --02������Ϊ�½���ʱǷ������֧���Ѻ���
	AC_CASHT                       VARCHAR2(30) := 'AC_CASHT'; --02������Ϊ�ֽ����п�
	RD_ORIGO_INCOME                VARCHAR2(30) := 'RD_ORIGO_INCOME'; --01���˷ѳ�����
	RD_ORIGO_COST                  VARCHAR2(30) := 'RD_ORIGO_COST'; --01���˷���ɱ�
	RD_ORIGO_PAY_APPLY             VARCHAR2(30) := 'RD_ORIGO_PAY_APPLY'; --01���˷Ѹ�������
	RD_ORIGT_INCOME                VARCHAR2(30) := 'RD_ORIGT_INCOME'; --02���˷ѳ�����
	RD_ORIGT_COST                  VARCHAR2(30) := 'RD_ORIGT_COST'; --02���˷���ɱ�
	RD_ORIGT_PAY_APPLY             VARCHAR2(30) := 'RD_ORIGT_PAY_APPLY'; --02���˷Ѹ�������
	RD_ORIGO_WO_ORIG_RCVO_POD      VARCHAR2(30) := 'RD_ORIGO_WO_ORIG_RCVO_POD'; --01���˷ѳ�01ʼ��Ӧ����ǩ��
	RD_ORIGO_WO_ORIG_RCVT_POD      VARCHAR2(30) := 'RD_ORIGO_WO_ORIG_RCVT_POD'; --01���˷ѳ�02ʼ��Ӧ����ǩ��
	RD_ORIGO_WO_ORIG_RCVT_PODP     VARCHAR2(30) := 'RD_ORIGO_WO_ORIG_RCVT_PODP'; --01���˷ѳ�02ʼ��Ӧ����ǩ��
	RD_ORIGT_WO_ORIG_RCVO_POD      VARCHAR2(30) := 'RD_ORIGT_WO_ORIG_RCVO_POD'; --02���˷ѳ�01ʼ��Ӧ����ǩ��
	RD_ORIGT_WO_ORIG_RCVO_PODP     VARCHAR2(30) := 'RD_ORIGT_WO_ORIG_RCVO_PODP'; --02���˷ѳ�01ʼ��Ӧ����ǩ��
	RD_ORIGT_WO_ORIG_RCVT_POD      VARCHAR2(30) := 'RD_ORIGT_WO_ORIG_RCVT_POD'; --02���˷ѳ�02ʼ��Ӧ����ǩ��
	RD_ORIGO_WO_ORIG_RCVO_NPOD     VARCHAR2(30) := 'RD_ORIGO_WO_ORIG_RCVO_NPOD'; --01���˷ѳ�01ʼ��Ӧ��δǩ��
	RD_ORIGO_WO_ORIG_RCVT_NPOD     VARCHAR2(30) := 'RD_ORIGO_WO_ORIG_RCVT_NPOD'; --01���˷ѳ�02ʼ��Ӧ��δǩ��
	RD_ORIGO_WO_ORIG_RCVT_NPODP    VARCHAR2(30) := 'RD_ORIGO_WO_ORIG_RCVT_NPODP'; --01���˷ѳ�02ʼ��Ӧ��δǩ��
	RD_ORIGT_WO_ORIG_RCVO_NPOD     VARCHAR2(30) := 'RD_ORIGT_WO_ORIG_RCVO_NPOD'; --02���˷ѳ�01ʼ��Ӧ��δǩ��
	RD_ORIGT_WO_ORIG_RCVO_NPODP    VARCHAR2(30) := 'RD_ORIGT_WO_ORIG_RCVO_NPODP'; --02���˷ѳ�01ʼ��Ӧ��δǩ��
	RD_ORIGT_WO_ORIG_RCVT_NPOD     VARCHAR2(30) := 'RD_ORIGT_WO_ORIG_RCVT_NPOD'; --02���˷ѳ�02ʼ��Ӧ��δǩ��
	RD_DESTO_INCOME                VARCHAR2(30) := 'RD_DESTO_INCOME'; --01���˷ѳ�����
	RD_DESTO_COST                  VARCHAR2(30) := 'RD_DESTO_COST'; --01���˷���ɱ�
	RD_DESTO_PAY_APPLY             VARCHAR2(30) := 'RD_DESTO_PAY_APPLY'; --01���˷Ѹ�������
	RD_DESTT_INCOME                VARCHAR2(30) := 'RD_DESTT_INCOME'; --02���˷ѳ�����
	RD_DESTT_COST                  VARCHAR2(30) := 'RD_DESTT_COST'; --02���˷���ɱ�
	RD_DESTT_PAY_APPLY             VARCHAR2(30) := 'RD_DESTT_PAY_APPLY'; --02���˷Ѹ�������
	RD_DESTO_DEST_RCVO_POD         VARCHAR2(30) := 'RD_DESTO_DEST_RCVO_POD'; --01���˷ѳ�01����Ӧ����ǩ��
	RD_DESTO_DEST_RCVO_PODP        VARCHAR2(30) := 'RD_DESTO_DEST_RCVO_PODP'; --01���˷ѳ�01����Ӧ����ǩ��
	RD_DESTT_WO_DEST_RCVO_POD      VARCHAR2(30) := 'RD_DESTT_WO_DEST_RCVO_POD'; --02���˷ѳ�01����Ӧ����ǩ��
	RD_DESTT_WO_DEST_RCVO_PODP     VARCHAR2(30) := 'RD_DESTT_WO_DEST_RCVO_PODP'; --02���˷ѳ�01����Ӧ����ǩ��
	RD_DESTO_WO_DEST_RCVO_NPOD     VARCHAR2(30) := 'RD_DESTO_WO_DEST_RCVO_NPOD'; --01���˷ѳ�01����Ӧ��δǩ��
	RD_DESTO_WO_DEST_RCVO_NPODP    VARCHAR2(30) := 'RD_DESTO_WO_DEST_RCVO_NPODP'; --01���˷ѳ�01����Ӧ��δǩ��
	RD_DESTT_WO_DEST_RCVO_NPOD     VARCHAR2(30) := 'RD_DESTT_WO_DEST_RCVO_NPOD'; --02���˷ѳ�01����Ӧ��δǩ��
	RD_DESTT_WO_DEST_RCVO_NPODP    VARCHAR2(30) := 'RD_DESTT_WO_DEST_RCVO_NPODP'; --02���˷ѳ�01����Ӧ��δǩ��
	RD_DESTO_DEST_RCVT_POD         VARCHAR2(30) := 'RD_DESTO_DEST_RCVT_POD'; --01���˷ѳ�02����Ӧ����ǩ��
	RD_DESTO_DEST_RCVT_PODP        VARCHAR2(30) := 'RD_DESTO_DEST_RCVT_PODP'; --01���˷ѳ�02����Ӧ����ǩ��
	RD_DESTT_WO_DEST_RCVT_POD      VARCHAR2(30) := 'RD_DESTT_WO_DEST_RCVT_POD'; --02���˷ѳ�02����Ӧ����ǩ��
	RD_DESTT_WO_DEST_RCVT_PODP     VARCHAR2(30) := 'RD_DESTT_WO_DEST_RCVT_PODP'; --02���˷ѳ�02����Ӧ����ǩ��
	RD_DESTO_WO_DEST_RCVT_NPOD     VARCHAR2(30) := 'RD_DESTO_WO_DEST_RCVT_NPOD'; --01���˷ѳ�02����Ӧ��δǩ��
	RD_DESTO_WO_DEST_RCVT_NPODP    VARCHAR2(30) := 'RD_DESTO_WO_DEST_RCVT_NPODP'; --01���˷ѳ�02����Ӧ��δǩ��
	RD_DESTT_WO_DEST_RCVT_NPOD     VARCHAR2(30) := 'RD_DESTT_WO_DEST_RCVT_NPOD'; --02���˷ѳ�02����Ӧ��δǩ��
	RD_DESTT_WO_DEST_RCVT_NPODP    VARCHAR2(30) := 'RD_DESTT_WO_DEST_RCVT_NPODP'; --02���˷ѳ�02����Ӧ��δǩ��
	SFO_PAY_APPLY                  VARCHAR2(30) := 'SFO_PAY_APPLY'; --01װж�Ѹ�������
	SFT_PAY_APPLY                  VARCHAR2(30) := 'SFT_PAY_APPLY'; --02װж�Ѹ�������
	CPO_ORIG_PAY_APPLY             VARCHAR2(30) := 'CPO_ORIG_PAY_APPLY'; --01ʼ�����񲹾ȸ�������
	CPT_ORIG_PAY_APPLY             VARCHAR2(30) := 'CPT_ORIG_PAY_APPLY'; --02ʼ�����񲹾ȸ�������
	CPO_DEST_PAY_APPLY             VARCHAR2(30) := 'CPO_DEST_PAY_APPLY'; --01������񲹾ȸ�������
	CPT_DEST_PAY_APPLY             VARCHAR2(30) := 'CPT_DEST_PAY_APPLY'; --02������񲹾ȸ�������
	AIR_COST_FEE_CONFIRM           VARCHAR2(30) := 'AIR_COST_FEE_CONFIRM'; --���˺��չ�˾�˷�ȷ��
	AIR_COST_ORIG_AGENCY_PAY_CFRM  VARCHAR2(30) := 'AIR_COST_ORIG_AGENCY_PAY_CFRM'; --���˳�������Ӧ��ȷ��
	AIR_COST_DEST_AGENCY_PAY_GEN   VARCHAR2(30) := 'AIR_COST_DEST_AGENCY_PAY_GEN'; --���˵������Ӧ������
	AIR_COST_DEST_AGENCY_PAY_CFRM  VARCHAR2(30) := 'AIR_COST_DEST_AGENCY_PAY_CFRM'; --���˵������Ӧ���ɱ�ȷ��
	AIR_COST_DEST_AGENCY_PAY_NCFRM VARCHAR2(30) := 'AIR_COST_DEST_AGENCY_PAY_NCFRM'; --���˵������Ӧ���ɱ���ȷ��
	AIR_COST_OTH_PAY_COST_CFRM     VARCHAR2(30) := 'AIR_COST_OTH_PAY_COST_CFRM'; --����Ӧ���ɱ�ȷ��
	AIR_COST_PAY_APPLY             VARCHAR2(30) := 'AIR_COST_PAY_APPLY'; --���˳ɱ���������
	AOR_ENTRY                      VARCHAR2(30) := 'AOR_ENTRY'; --��������Ӧ��¼��
	AOR_CH_UR                      VARCHAR2(30) := 'AOR_CH_UR'; --�����������Ӧ���ֽ�
	AOR_CD_UR                      VARCHAR2(30) := 'AOR_CD_UR'; --�����������Ӧ������
	APWR_COST_WO_DEST_RCVO_POD     VARCHAR2(30) := 'APWR_COST_WO_DEST_RCVO_POD'; --Ӧ���������ɱ���01Ӧ�յ����˷���ǩ��
	APWR_COST_WO_DEST_RCVO_NPOD    VARCHAR2(30) := 'APWR_COST_WO_DEST_RCVO_NPOD'; --Ӧ���������ɱ���01Ӧ�յ����˷�δǩ��
	APWR_OTH_PAY_WO_DEST_RCVO_POD  VARCHAR2(30) := 'APWR_OTH_PAY_WO_DEST_RCVO_POD'; --����Ӧ����01Ӧ�յ����˷���ǩ��
	APWR_OTH_PAY_WO_DEST_RCVO_NPOD VARCHAR2(30) := 'APWR_OTH_PAY_WO_DEST_RCVO_NPOD'; --����Ӧ����01Ӧ�յ����˷�δǩ��
	APWR_COST_WO_DEST_RCVT_POD     VARCHAR2(30) := 'APWR_COST_WO_DEST_RCVT_POD'; --Ӧ���������ɱ���02Ӧ�յ����˷���ǩ��
	APWR_COST_WO_DEST_RCVT_NPOD    VARCHAR2(30) := 'APWR_COST_WO_DEST_RCVT_NPOD'; --Ӧ���������ɱ���02Ӧ�յ����˷�δǩ��
	APWR_OTH_PAY_WO_DEST_RCVT_POD  VARCHAR2(30) := 'APWR_OTH_PAY_WO_DEST_RCVT_POD'; --����Ӧ����02Ӧ�յ����˷���ǩ��
	APWR_OTH_PAY_WO_DEST_RCVT_NPOD VARCHAR2(30) := 'APWR_OTH_PAY_WO_DEST_RCVT_NPOD'; --����Ӧ����02Ӧ�յ����˷�δǩ��
	APWR_OTH_PAY_WO_OTH_RCV        VARCHAR2(30) := 'APWR_OTH_PAY_WO_OTH_RCV'; --����Ӧ��������Ӧ��
	AIR_COD_POD_NWO                VARCHAR2(30) := 'AIR_COD_POD_NWO'; --����ǩ��ʱδ�������ջ���
	AIR_COD_UPD_NWO                VARCHAR2(30) := 'AIR_COD_UPD_NWO'; --���˷�ǩ��ʱδ�������ջ���
	AIR_COD_CH_UR_POD              VARCHAR2(30) := 'AIR_COD_CH_UR_POD'; --���˻�����ջ����ֽ���ǩ��
	AIR_COD_CD_UR_POD              VARCHAR2(30) := 'AIR_COD_CD_UR_POD'; --���˻�����ջ���������ǩ��
	AIR_COD_POD_WO                 VARCHAR2(30) := 'AIR_COD_POD_WO'; --����ǩ��ʱ�Ѻ������ջ���
	AIR_COD_UPD_WO                 VARCHAR2(30) := 'AIR_COD_UPD_WO'; --���˷�ǩ��ʱ�Ѻ������ջ���
	AIR_COD_CH_UR_NPOD             VARCHAR2(30) := 'AIR_COD_CH_UR_NPOD'; --���˻�����ջ����ֽ�δǩ��
	AIR_COD_CD_UR_NPOD             VARCHAR2(30) := 'AIR_COD_CD_UR_NPOD'; --���˻�����ջ�������δǩ��
	AIR_COD_DPAY_WO_COD_RCV_POD    VARCHAR2(30) := 'AIR_COD_DPAY_WO_COD_RCV_POD'; --���˵������Ӧ����Ӧ�մ��ջ�����ǩ��
	AIR_COD_OTH_PAY_WO_COD_RCV_POD VARCHAR2(30) := 'AIR_COD_OTH_PAY_WO_COD_RCV_POD'; --��������Ӧ����Ӧ�մ��ջ�����ǩ��
	AIR_COD_COST_WO_COD_RCV_NPOD   VARCHAR2(30) := 'AIR_COD_COST_WO_COD_RCV_NPOD'; --���˵������Ӧ����Ӧ�մ��ջ���δǩ��
	AIR_COD_OPAY_WO_COD_RCV_NPOD   VARCHAR2(30) := 'AIR_COD_OPAY_WO_COD_RCV_NPOD'; --��������Ӧ����Ӧ�մ��ջ���δǩ��
	AIR_DR_CH                      VARCHAR2(30) := 'AIR_DR_CH'; --Ԥ�տ��˴����ֽ�
	AIR_DR_CD                      VARCHAR2(30) := 'AIR_DR_CD'; --Ԥ�տ��˴�������
	AIR_DR_WO_DEST_RCVO_POD        VARCHAR2(30) := 'AIR_DR_WO_DEST_RCVO_POD'; --Ԥ�տ��˴����01Ӧ�յ����˷���ǩ��
	AIR_DR_WO_DEST_RCVT_POD        VARCHAR2(30) := 'AIR_DR_WO_DEST_RCVT_POD'; --Ԥ�տ��˴����02Ӧ�յ����˷���ǩ��
	AIR_DR_WO_DEST_RCVO_NPOD       VARCHAR2(30) := 'AIR_DR_WO_DEST_RCVO_NPOD'; --Ԥ�տ��˴����01Ӧ�յ����˷�δǩ��
	AIR_DR_WO_DEST_RCVT_NPOD       VARCHAR2(30) := 'AIR_DR_WO_DEST_RCVT_NPOD'; --Ԥ�տ��˴����02Ӧ�յ����˷�δǩ��
	AIR_DR_WO_OTH_RCV              VARCHAR2(30) := 'AIR_DR_WO_OTH_RCV'; --Ԥ�տ��˴��������Ӧ��
	AIR_DR_WO_COD_RCV_POD          VARCHAR2(30) := 'AIR_DR_WO_COD_RCV_POD'; --Ԥ�տ��˴����Ӧ�մ��ջ�����ǩ��
	AIR_DR_WO_COD_RCV_NPOD         VARCHAR2(30) := 'AIR_DR_WO_COD_RCV_NPOD'; --Ԥ�տ��˴����Ӧ�մ��ջ���δǩ��
	AIR_DR_PAY_APPLY               VARCHAR2(30) := 'AIR_DR_PAY_APPLY'; --������Ԥ�ո�������
	APT_AIR_COMPANY                VARCHAR2(30) := 'APT_AIR_COMPANY'; --Ԥ�����չ�˾
	APT_WO_AIR_PAY                 VARCHAR2(30) := 'APT_WO_AIR_PAY'; --Ԥ����Ӧ�����չ�˾
	APT_WO_OTH_PAY                 VARCHAR2(30) := 'APT_WO_OTH_PAY'; --Ԥ��������Ӧ��
	BWOR                           VARCHAR2(30) := 'BWOR'; --���˳�����Ӧ��
	OR_CH_RENT_INCOME              VARCHAR2(30) := 'OR_CH_RENT_INCOME'; --СƱ�ֽ�֮����ֿ��������
	OR_CH_BANK_INT                 VARCHAR2(30) := 'OR_CH_BANK_INT'; --СƱ�ֽ�֮����Ա����Ϣ
	OR_CD_RENT_INCOME              VARCHAR2(30) := 'OR_CD_RENT_INCOME'; --СƱ����֮����ֿ��������

	---------------------------------------------------------------
	--ƫ������Ӧ��Ӧ�������ֶ�
	---------------------------------------------------------------
	PL_DR_WO_OR     VARCHAR2(30) := 'PL_DR_WO_OR'; --Ԥ��ƫ�ߴ��������Ӧ��
	PL_COST_OP_CRM  VARCHAR2(30) := 'PL_COST_OP_CRM'; --����Ӧ���ɱ�ȷ��
	POR_ENTRY       VARCHAR2(30) := 'POR_ENTRY'; --ƫ������Ӧ��¼��             
	UR_POR_CH       VARCHAR2(30) := 'UR_POR_CH'; --����ƫ������Ӧ���ֽ�                  
	UR_POR_CD       VARCHAR2(30) := 'UR_POR_CD'; --����ƫ������Ӧ������             
	POP_WO_DRO_POD  VARCHAR2(30) := 'POP_WO_DRO_POD'; --����Ӧ����01Ӧ�յ����˷���ǩ��             
	POP_WO_DRO_NPOD VARCHAR2(30) := 'POP_WO_DRO_NPOD'; --����Ӧ����01Ӧ�յ����˷�δǩ��             
	POP_WO_DRT_POD  VARCHAR2(30) := 'POP_WO_DRT_POD'; --����Ӧ����02Ӧ�յ����˷���ǩ��             
	POP_WO_DRT_NPOD VARCHAR2(30) := 'POP_WO_DRT_NPOD'; --����Ӧ����02Ӧ�յ����˷�δǩ��             
	POCP_WO_OR      VARCHAR2(30) := 'POCP_WO_OR'; --�ɱ�Ӧ������Ӧ��������Ӧ��  

	---------------------------------------------------------------
	--��������ֶ�
	---------------------------------------------------------------
	LAND_COST_AGENCY_GEN           VARCHAR2(30) := 'LAND_COST_AGENCY_GEN'; --��������Ӧ������
	LAND_COST_AGENCY_CFM           VARCHAR2(30) := 'LAND_COST_AGENCY_CFM'; --����乫˾�˷�ȷ��
	LAND_COST_AGENCY_NCFM          VARCHAR2(30) := 'LAND_COST_AGENCY_NCFM'; --��������Ӧ���ɱ���ȷ��
	LAND_COST_OTHER_CONFIRM        VARCHAR2(30) := 'LAND_COST_OTHER_CONFIRM'; --���������Ӧ���ɱ�ȷ��
	LAND_COST_PAY_APPLY            VARCHAR2(30) := 'LAND_COST_PAY_APPLY'; --�����ɱ���������
	LAND_OTH_ENTRY                 VARCHAR2(30) := 'LAND_OTH_ENTRY'; --���������Ӧ��¼��
	LAND_OTH_RCV_CH                VARCHAR2(30) := 'LAND_OTH_RCV_CH'; --�������������Ӧ���ֽ�
	LAND_OTH_RCV_CD                VARCHAR2(30) := 'LAND_OTH_RCV_CD'; --�������������Ӧ������
	LAND_COD_CH_NPOD               VARCHAR2(30) := 'LAND_COD_CH_NPOD'; --����仹����ջ����ֽ�δǩ��
	LAND_COD_CD_NPOD               VARCHAR2(30) := 'LAND_COD_CD_NPOD'; --����仹����ջ�������δǩ��
	LAND_COD_WO_AGENCY_PAY_NPOD    VARCHAR2(30) := 'LAND_COD_WO_AGENCY_PAY_NPOD'; --����䵽�����Ӧ����Ӧ�մ��ջ���δǩ��
	LAND_PR_OTH_WO_OTH_RCV         VARCHAR2(30) := 'LAND_PR_OTH_WO_OTH_RCV'; --���������Ӧ��������Ӧ��
	LAND_DR_CH                     VARCHAR2(30) := 'LAND_DR_CH'; --Ԥ�����������ֽ�
	LAND_DR_CD                     VARCHAR2(30) := 'LAND_DR_CD'; --Ԥ��������������
	LAND_DR_WO_OTHER_RCV           VARCHAR2(30) := 'LAND_DR_WO_OTHER_RCV'; --�����Ԥ���������������Ӧ��
	LAND_DR_WO_COD_RCV_NPOD        VARCHAR2(30) := 'LAND_DR_WO_COD_RCV_NPOD'; --�����Ԥ�����������Ӧ�մ��ջ���δǩ��
	LAND_DR_PAY_APPLY              VARCHAR2(30) := 'LAND_DR_PAY_APPLY'; --�������Ԥ�ո�������
	LAND_COD_POD_NWO_COD           VARCHAR2(30) := 'LAND_COD_POD_NWO_COD'; --�����ǩ��ʱδ�������ջ���
	LAND_COD_CH_POD                VARCHAR2(30) := 'LAND_COD_CH_POD'; --����仹����ջ����ֽ���ǩ��
	LAND_COD_CD_POD                VARCHAR2(30) := 'LAND_COD_CD_POD'; --����仹����ջ���������ǩ��
	LAND_COD_NPOD_NWO_COD          VARCHAR2(30) := 'LAND_COD_NPOD_NWO_COD'; --����䷴ǩ��ʱδ�������ջ���
	LAND_COD_POD_WO_COD            VARCHAR2(30) := 'LAND_COD_POD_WO_COD'; --�����ǩ��ʱ�Ѻ������ջ���
	LAND_COD_NPOD_WO_COD           VARCHAR2(30) := 'LAND_COD_NPOD_WO_COD'; --����䷴ǩ��ʱ�Ѻ������ջ���
	LAND_COD_WO_AGENCY_PAY_POD     VARCHAR2(30) := 'LAND_COD_WO_AGENCY_PAY_POD'; --����䵽�����Ӧ����Ӧ�մ��ջ�����ǩ��
	LAND_COD_WO_OTH_PAY_COD        VARCHAR2(30) := 'LAND_COD_WO_OTH_PAY_COD'; --���������Ӧ����Ӧ�մ��ջ�����ǩ��
	LAND_PR_AGENCY_WO_DEST_RCV_POD VARCHAR2(30) := 'LAND_PR_AGENCY_WO_DEST_RCV_POD'; --�����Ӧ���������ɱ���Ӧ�յ����˷���ǩ��
	LAND_PR_AGENCY_WO_DEST_RCV_NP  VARCHAR2(30) := 'LAND_PR_AGENCY_WO_DEST_RCV_NP'; --�����Ӧ���������ɱ���Ӧ�յ����˷�δǩ��
	LAND_PR_OT_WO_DEST_RCV_POD     VARCHAR2(30) := 'LAND_PR_OT_WO_DEST_RCV_POD'; --���������Ӧ����Ӧ�յ����˷���ǩ��
	LAND_PR_OTH_WO_DEST_RCV_NPOD   VARCHAR2(30) := 'LAND_PR_OTH_WO_DEST_RCV_NPOD'; --���������Ӧ����Ӧ�յ����˷�δǩ��
	LAND_DR_DEST_RCV_POD           VARCHAR2(30) := 'LAND_DR_DEST_RCV_POD'; --�����Ԥ�����������Ӧ�յ����˷���ǩ��
	LAND_DR_DEST_RCV_NPOD          VARCHAR2(30) := 'LAND_DR_DEST_RCV_NPOD'; --�����Ԥ�����������Ӧ�յ����˷�δǩ��
	LAND_DR_WO_COD_RCV_POD         VARCHAR2(30) := 'LAND_DR_WO_COD_RCV_POD'; --�����Ԥ�����������Ӧ�մ��ջ�����ǩ��
	LAND_UR_DEST_CH_NPOD           VARCHAR2(30) := 'LAND_UR_DEST_CH_NPOD'; --����仹���ֽ�δǩ��
	LAND_UR_DEST_CD_NPOD           VARCHAR2(30) := 'LAND_UR_DEST_CD_NPOD'; --����仹������δǩ��
	LAND_UR_DEST_CH_POD            VARCHAR2(30) := 'LAND_UR_DEST_CH_POD'; --����仹���ֽ���ǩ��
	LAND_UR_DEST_CD_POD            VARCHAR2(30) := 'LAND_UR_DEST_CD_POD'; --����仹��������ǩ��
	LAND_BDR_WO_OTH_RCV            VARCHAR2(30) := 'LAND_BDR_WO_OTH_RCV'; --����仵�˳�����Ӧ��_���˳�����Ӧ��
	LAND_APT_COM                   VARCHAR2(30) := 'LAND_APT_COM'; --�����Ԥ��_Ԥ�����չ�˾
	LAND_APT_WO_COM_PAY            VARCHAR2(30) := 'LAND_APT_WO_COM_PAY'; --�����Ԥ��_Ԥ����Ӧ�����չ�˾
	LAND_APT_WO_OTH_PAY            VARCHAR2(30) := 'LAND_APT_WO_OTH_PAY'; --�����Ԥ��_Ԥ��������Ӧ��
	LAND_COD_WO_OTH_NPOD           VARCHAR2(30) := 'LAND_COD_WO_OTH_NPOD'; --���������Ӧ����Ӧ�մ��ջ���δǩ��

	CUST_DR_ORIG_LAND_RCV_NPOD   VARCHAR2(30) := 'CUST_DR_ORIG_LAND_RCV_NPOD'; --Ԥ�տͻ�����Ӧ��ʼ���˷�δǩ��
	CUST_DR_ORIG_LAND_RCV_POD    VARCHAR2(30) := 'CUST_DR_ORIG_LAND_RCV_POD'; --Ԥ�տͻ�����Ӧ��ʼ���˷���ǩ��
	CLAIM_WO_ORIG_LAND_RCV_POD   VARCHAR2(30) := 'CLAIM_WO_ORIG_LAND_RCV_POD'; --�������ʼ��Ӧ����ǩ��
	CLAIM_WO_ORIG_LAND_RCV_NPOD  VARCHAR2(30) := 'CLAIM_WO_ORIG_LAND_RCV_NPOD'; --�������ʼ��Ӧ��δǩ��
	OR_LAND_RCV_WO_CLAIM_PAY     VARCHAR2(30) := 'OR_LAND_RCV_WO_CLAIM_PAY'; --Ӧ���������СƱӦ��
	OR_LAND_RCV_WO_CUST_DR       VARCHAR2(30) := 'OR_LAND_RCV_WO_CUST_DR'; --Ԥ�տͻ�����СƱӦ��
	REFUND_WO_ORIG_LAND_RCV_POD  VARCHAR2(30) := 'REFUND_WO_ORIG_LAND_RCV_POD'; --���˷ѳ���ʼ��Ӧ����ǩ��
	REFUND_WO_ORIG_LAND_RCV_NPOD VARCHAR2(30) := 'REFUND_WO_ORIG_LAND_RCV_NPOD'; --���˷ѳ���ʼ��Ӧ��δǩ��
	LAND_CLAIM_WO_ORIG_RCV_POD   VARCHAR2(30) := 'LAND_CLAIM_WO_ORIG_RCV_POD'; --��������ʼ��Ӧ����ǩ��
	LAND_CLAIM_WO_ORIG_RCV_NPOD  VARCHAR2(30) := 'LAND_CLAIM_WO_ORIG_RCV_NPOD'; --��������ʼ��Ӧ��δǩ��
	OR_RCV_WO_LAND_CLAIM_PAY     VARCHAR2(30) := 'OR_RCV_WO_LAND_CLAIM_PAY'; --���Ӧ�������СƱӦ��
	LAND_REFUND_WO_ORIG_RCV_POD  VARCHAR2(30) := 'LAND_REFUND_WO_ORIG_RCV_POD'; --������˷ѳ�ʼ��Ӧ����ǩ��
	LAND_REFUND_WO_ORIG_RCV_NPOD VARCHAR2(30) := 'LAND_REFUND_WO_ORIG_RCV_NPOD'; --������˷ѳ�ʼ��Ӧ��δǩ��

	-----------------------------------------------------------------
	-- ƾ֤��ʼ����
	-----------------------------------------------------------------
	PROCEDURE PROC_VOUCHER_PROCESS(P_PERIOD        VARCHAR2, -- ����yyyymm������201212
																 P_BUSINESS_TYPE VARCHAR2 -- ����
																 ); -- ���ؽ�� true:�ɹ���false:ʧ��

	-----------------------------------------------------------------
	-- ƾ֤�ɹ�
	-----------------------------------------------------------------
	PROCEDURE PROC_VOUCHER_SUCCESS(P_PERIOD        VARCHAR2, -- ����yyyymm������201212
																 P_BUSINESS_TYPE VARCHAR2 -- ����
																 ); -- ���ؽ�� true:�ɹ���false:ʧ��

	-----------------------------------------------------------------
	-- ƾ֤ʧ��
	-----------------------------------------------------------------
	PROCEDURE PROC_VOUCHER_FAILURE(P_PERIOD        VARCHAR2, -- ����yyyymm������201212
																 P_BUSINESS_TYPE VARCHAR2 -- ����
																 ); -- ���ؽ�� true:�ɹ���false:ʧ��

	-----------------------------------------------------------------
	-- ƾ֤��ͳ�ƿ�ʼ����
	-----------------------------------------------------------------
	PROCEDURE PROC_RPT_PROCESS(P_PERIOD VARCHAR2 -- ����yyyymm������201212
														 ); -- ���ؽ�� true:�ɹ���false:ʧ��

	-----------------------------------------------------------------
	-- ƾ֤��ͳ�Ƴɹ�����
	-----------------------------------------------------------------
	PROCEDURE PROC_RPT_SUCCESS(P_PERIOD VARCHAR2 -- ����yyyymm������201212
														 ); -- ���ؽ�� true:�ɹ���false:ʧ��

END PKG_STL_VCH_COMMON;
/
CREATE OR REPLACE PACKAGE BODY STV.PKG_STL_VCH_COMMON IS

	-----------------------------------------------------------------
	-- ƾ֤��ʼ��¼
	-----------------------------------------------------------------
	PROCEDURE PROC_VOUCHER_PROCESS(P_PERIOD        VARCHAR2, -- ����yyyymm������201212
																 P_BUSINESS_TYPE VARCHAR2 -- ����
																 ) IS
		PRAGMA AUTONOMOUS_TRANSACTION; -- ��������
		CURSOR V_CUR IS
			SELECT *
				FROM T_STL_VOUCHER_BATCH T
			 WHERE T.PERIOD = P_PERIOD
						 AND T.BUSINESS_TYPE = P_BUSINESS_TYPE
			 ORDER BY T.ID;
		V_REC V_CUR%ROWTYPE;
		VOUCHER_BATCH_ERROR EXCEPTION;
	BEGIN
	
		OPEN V_CUR;
		FETCH V_CUR
			INTO V_REC;
		IF V_CUR%NOTFOUND THEN
			INSERT INTO T_STL_VOUCHER_BATCH
				(ID, PERIOD, STAT_BEGIN_DATE, STATUS, BUSINESS_TYPE)
			VALUES
				(SYS_GUID(),
				 P_PERIOD,
				 SYSDATE,
				 VOUCHER_BATCH_STATUS_PROCESS,
				 P_BUSINESS_TYPE);
		ELSIF V_REC.STATUS IN
					(VOUCHER_BATCH_STATUS_SUCCESS, VOUCHER_BATCH_STATUS_FAILURE) THEN
			UPDATE T_STL_VOUCHER_BATCH T
				 SET T.STAT_BEGIN_DATE = SYSDATE,
						 T.STATUS          = VOUCHER_BATCH_STATUS_PROCESS
			 WHERE T.ID = V_REC.ID;
		ELSE
			CLOSE V_CUR;
			RAISE VOUCHER_BATCH_ERROR;
		END IF;
	
		CLOSE V_CUR;
		COMMIT;
	
	EXCEPTION
		WHEN VOUCHER_BATCH_ERROR THEN
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				NULL,
																				NULL,
																				'PROC_VOUCHER_PROCESS',
																				'ƾ֤��ʼ��¼' || '״̬�쳣');
			RAISE_APPLICATION_ERROR(SQLCODE, '��¼ƾ֤��־����');
		WHEN OTHERS THEN
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				NULL,
																				NULL,
																				'PROC_VOUCHER_PROCESS',
																				'ƾ֤��ʼ��¼' || '״̬�쳣');
	END;

	-----------------------------------------------------------------
	-- ƾ֤�ɹ�
	-----------------------------------------------------------------
	PROCEDURE PROC_VOUCHER_SUCCESS(P_PERIOD        VARCHAR2, -- ����yyyymm������201212
																 P_BUSINESS_TYPE VARCHAR2 -- ����
																 ) IS
		PRAGMA AUTONOMOUS_TRANSACTION; -- ��������
		V_ROWID  ROWID;
		V_STATUS VARCHAR2(20);
	BEGIN
		-- ��ѯ��¼
		SELECT ROWID, T.STATUS
			INTO V_ROWID, V_STATUS
			FROM T_STL_VOUCHER_BATCH T
		 WHERE T.PERIOD = P_PERIOD
					 AND T.BUSINESS_TYPE = P_BUSINESS_TYPE;
	
		UPDATE T_STL_VOUCHER_BATCH T
			 SET T.STAT_END_DATE = SYSDATE,
					 T.STATUS        = VOUCHER_BATCH_STATUS_SUCCESS
		 WHERE T.ROWID = V_ROWID;
	
		COMMIT;
	EXCEPTION
		WHEN OTHERS THEN
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				NULL,
																				NULL,
																				'PROC_VOUCHER_SUCCESS',
																				'ƾ֤�ɹ�' || '״̬�쳣');
	END;

	-----------------------------------------------------------------
	-- ƾ֤ʧ��
	-----------------------------------------------------------------
	PROCEDURE PROC_VOUCHER_FAILURE(P_PERIOD        VARCHAR2, -- ����yyyymm������201212
																 P_BUSINESS_TYPE VARCHAR2 -- ����
																 ) IS
		PRAGMA AUTONOMOUS_TRANSACTION; -- ��������
		V_ROWID  ROWID;
		V_STATUS VARCHAR2(20);
	BEGIN
		-- ��ѯ��¼
		SELECT ROWID, T.STATUS
			INTO V_ROWID, V_STATUS
			FROM T_STL_VOUCHER_BATCH T
		 WHERE T.PERIOD = P_PERIOD
					 AND T.BUSINESS_TYPE = P_BUSINESS_TYPE;
	
		UPDATE T_STL_VOUCHER_BATCH T
			 SET T.STAT_END_DATE = SYSDATE,
					 T.STATUS        = VOUCHER_BATCH_STATUS_FAILURE
		 WHERE T.ROWID = V_ROWID;
	
		COMMIT;
	EXCEPTION
		WHEN OTHERS THEN
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				NULL,
																				NULL,
																				'PROC_VOUCHER_FAILURE',
																				'ƾ֤ʧ��' || '״̬�쳣');
	END;

	-----------------------------------------------------------------
	-- ƾ֤��ͳ�ƿ�ʼ����
	-----------------------------------------------------------------
	PROCEDURE PROC_RPT_PROCESS(P_PERIOD VARCHAR2 -- ����yyyymm������201212
														 ) IS
		PRAGMA AUTONOMOUS_TRANSACTION; -- ��������
		V_COUNT NUMBER;
		RPT_BATCH_ERROR EXCEPTION;
	BEGIN
		SELECT COUNT(T.PERIOD)
			INTO V_COUNT
			FROM STV.T_STL_MVR_BATCH T
		 WHERE T.PERIOD = P_PERIOD;
	
		IF V_COUNT <= 0 THEN
			INSERT INTO STV.T_STL_MVR_BATCH
				(ID, PERIOD, STATISTICS_BEGIN_DATE, ACTIVE)
			VALUES
				(SYS_GUID(), P_PERIOD, SYSDATE, PKG_STL_COMMON.NO);
		ELSIF V_COUNT = 1 THEN
			UPDATE STV.T_STL_MVR_BATCH T
				 SET T.STATISTICS_BEGIN_DATE = SYSDATE,
						 T.STATISTICS_END_DATE   = NULL,
						 T.RFO                   = NULL,
						 T.RFD                   = NULL,
						 T.RFI                   = NULL,
						 T.PLC                   = NULL,
						 T.PLI                   = NULL,
						 T.AFC                   = NULL,
						 T.AFI                   = NULL,
						 T.STATUS                = NULL,
						 T.ACTIVE                = PKG_STL_COMMON.NO,
						 T.RCOD                  = NULL
			 WHERE T.PERIOD = P_PERIOD;
		ELSE
			RAISE RPT_BATCH_ERROR;
		END IF;
	
		COMMIT;
	EXCEPTION
		WHEN OTHERS THEN
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				NULL,
																				NULL,
																				'PROC_RPT_PROCESS',
																				'ƾ֤��ͳ�ƿ�ʼ����' || '״̬�쳣');
	END;

	-----------------------------------------------------------------
	-- ƾ֤��ͳ�Ƴɹ�����
	-----------------------------------------------------------------
	PROCEDURE PROC_RPT_SUCCESS(P_PERIOD VARCHAR2 -- ����yyyymm������201212
														 ) IS
		PRAGMA AUTONOMOUS_TRANSACTION; -- ��������
		V_COUNT NUMBER;
		RPT_BATCH_ERROR EXCEPTION;
	BEGIN
		SELECT COUNT(T.PERIOD)
			INTO V_COUNT
			FROM STV.T_STL_MVR_BATCH T
		 WHERE T.PERIOD = P_PERIOD;
	
		IF V_COUNT = 1 THEN
			UPDATE STV.T_STL_MVR_BATCH T
				 SET T.STATISTICS_END_DATE = SYSDATE,
						 T.RFO                 = PKG_STL_COMMON.YES,
						 T.RFD                 = PKG_STL_COMMON.YES,
						 T.RFI                 = PKG_STL_COMMON.YES,
						 T.PLC                 = PKG_STL_COMMON.YES,
						 T.PLI                 = PKG_STL_COMMON.YES,
						 T.AFC                 = PKG_STL_COMMON.YES,
						 T.AFI                 = PKG_STL_COMMON.YES,
						 T.ACTIVE              = PKG_STL_COMMON.YES,
						 T.RCOD                = PKG_STL_COMMON.YES
			 WHERE T.PERIOD = P_PERIOD;
		ELSE
			RAISE RPT_BATCH_ERROR;
		END IF;
	
		COMMIT;
	EXCEPTION
		WHEN OTHERS THEN
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				NULL,
																				NULL,
																				'PROC_RPT_SUCCESS',
																				'ƾ֤��ͳ�Ƴɹ�����' || '״̬�쳣');
	END;

END PKG_STL_VCH_COMMON;
/
