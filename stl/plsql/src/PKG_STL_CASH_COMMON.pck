CREATE OR REPLACE PACKAGE PKG_STL_CASH_COMMON IS
	-- Author  : ZHUWEI
	-- Created : 2012-11-19 11:06:22
	-- Purpose : �����Լ�ͨ�ú���

  --========================== ͨ�ó������� ================================
  MAX_PAGE_ROW_SIZE NUMBER := 1000; --���ҳ�����������������룬����ÿ�β���������
	DEFAULT_USER_CODE VARCHAR2(20) := 'STL'; --�����̨�û�����
  DEFAULT_BILL_NO VARCHAR2(20) := 'N/A'; -- Ĭ�ϵ���


	-- ==================== ����ͨ�� SETTLEMENT ====================
	/**
  * ��������
  */
	YES VARCHAR2(20) := 'Y';
	NO  VARCHAR2(20) := 'N';
	/**
  * ��Ч/δ��Ч
  */
	ACTIVE   VARCHAR2(20) := 'Y';
	INACTIVE VARCHAR2(20) := 'N';
	/**
  * IS_RED_BACK �Ƿ�쵥
  */
	IS_RED_BACK_YES VARCHAR2(20) := 'Y'; -- ��
	IS_RED_BACK_NO  VARCHAR2(20) := 'N'; -- ��
	/**
  * CREATE_TYPE �������ɷ�ʽ
  */
	CREATE_TYPE_AUTO   VARCHAR2(20) := 'A'; -- ϵͳ����
	CREATE_TYPE_MANUAL VARCHAR2(20) := 'M'; -- �ֹ�����
	/**
  * PAYMENT_TYPE ֧����ʽ
  */
	PAYMENT_TYPE_CASH            VARCHAR2(20) := 'CH'; -- �ֽ�
	PAYMENT_TYPE_CARD            VARCHAR2(20) := 'CD'; -- ���п�
	PAYMENT_TYPE_TELE_TRANSFER   VARCHAR2(20) := 'TT'; -- ���
	PAYMENT_TYPE_NOTE            VARCHAR2(20) := 'NT'; -- ֧Ʊ
	PAYMENT_TYPE_ONLINE          VARCHAR2(20) := 'OL'; -- ����֧��
	PAYMENT_TYPE_CREDIT          VARCHAR2(20) := 'CT'; -- �½�
	PAYMENT_TYPE_DEBT            VARCHAR2(20) := 'DT'; -- ��ʱǷ��
	PAYMENT_TYPE_FREIGHT_COLLECT VARCHAR2(20) := 'FC'; -- ����

	-- ==================== Ӧ�յ� BILL_RECEIVABLE ====================
	/**
  * BILL_TYPE ����������
  */
	RECEIVABLE_BILL_TYPE_ORIGIN  VARCHAR2(20) := 'OR'; -- ʼ��Ӧ��
	RECEIVABLE_BILL_TYPE_DEST    VARCHAR2(20) := 'DR'; -- ����Ӧ��
	RECEIVABLE_BILL_TYPE_COD     VARCHAR2(20) := 'CR'; -- ���ջ���Ӧ��
	RECEIVABLE_BILL_TYPE_AR      VARCHAR2(20) := 'AR'; -- ��������Ӧ��
	RECEIVABLE_BILL_TYPE_REVENUE VARCHAR2(20) := 'RR'; -- СƱӦ��
	RECEIVABLE_BILL_TYPE_PARTIAL VARCHAR2(20) := 'DP'; -- ����ƫ�ߴ���Ӧ�յ�
	RECEIVABLE_BILL_TYPE_AA      VARCHAR2(20) := 'AA'; -- ������ת����Ӧ��
	/**
  * SOURCE_BILL_TYPE ��Դ����������
  */
	RECEIVABLE_SOURCE_BILL_TYPE_W VARCHAR2(20) := 'W'; -- �˵�
	RECEIVABLE_SOURCE_BILL_TYPE_R VARCHAR2(20) := 'R'; -- СƱ
	RECEIVABLE_SOURCE_BILL_TYPE_M VARCHAR2(20) := 'M'; -- �˹�¼��
	/**
  * PAYMENT_TYPE ���ʽ���ο��˵��ж�Ӧ�ĸ��ʽ��
  */
	RECEIVABLE_PAYMENT_TYPE_DEBT VARCHAR2(20) := 'DT'; -- ��Ƿ
	RECEIVABLE_PAYMENT_TYPE_CT   VARCHAR2(20) := 'CT'; -- �½�
	RECEIVABLE_PAYMENT_TYPE_FC   VARCHAR2(20) := 'FC'; -- ����
	RECEIVABLE_PAYMENT_TYPE_OL   VARCHAR2(20) := 'OL'; -- ����֧��
	/**
  * COLLECTION_TYPE �տ����СƱ���տ����
  */
	RECEIVABLE_COLLECTION_TYPE_W  VARCHAR2(20) := 'W'; -- �ִ���
	RECEIVABLE_COLLECTION_TYPE_PD VARCHAR2(20) := 'PD'; -- ���������
	RECEIVABLE_COLLECTION_TYPE_D  VARCHAR2(20) := 'D'; -- �����ͻ���
	RECEIVABLE_COLLECTION_TYPE_C  VARCHAR2(20) := 'C'; -- ��Ա����
	RECEIVABLE_COLLECTION_TYPE_P  VARCHAR2(20) := 'P'; -- ��װ��
	RECEIVABLE_COLLECTION_TYPE_F  VARCHAR2(20) := 'F'; -- �ſշ�
	RECEIVABLE_COLLECTION_TYPE_R  VARCHAR2(20) := 'R'; -- ����Ʒ
	RECEIVABLE_COLLECTION_TYPE_E  VARCHAR2(20) := 'E'; -- ����Ԥ�տ�
	RECEIVABLE_COLLECTION_TYPE_O  VARCHAR2(20) := 'O'; -- ����
	/**
  * APPROVE_STATUS ���״̬
  */
	RECEIVABLE_APPROVE_STATUS_NA VARCHAR2(20) := 'NA'; -- δ���
	RECEIVABLE_APPROVE_STATUS_AA VARCHAR2(20) := 'AA'; -- �����

	-- ==================== Ӧ���� BILL_PAYABLE ====================
	/**
  * BILL_TYPE ����������
  */
	PAYABLE_BILL_TYPE_APC          VARCHAR2(20) := 'APC'; -- Ӧ�����ջ���
	PAYABLE_BILL_TYPE_TRUCK1_FIRST VARCHAR2(20) := 'TF1'; -- ���복�׿�
	PAYABLE_BILL_TYPE_TRUCK1_LAST  VARCHAR2(20) := 'TL1'; -- ���복β��
	PAYABLE_BILL_TYPE_TRUCK2_FIRST VARCHAR2(20) := 'TF2'; -- �����׿�
	PAYABLE_BILL_TYPE_TRUCK2_LAST  VARCHAR2(20) := 'TL2'; -- ����β��
	PAYABLE_BILL_TYPE_AIR          VARCHAR2(20) := 'A'; -- ���չ�˾�˷�
	PAYABLE_BILL_TYPE_AIR_OTHER    VARCHAR2(20) := 'AO'; -- ��������Ӧ��
	PAYABLE_BILL_TYPE_AAD          VARCHAR2(20) := 'AAD'; -- ��������Ӧ��Ӧ��
	PAYABLE_BILL_TYPE_PARTIAL_LINE VARCHAR2(20) := 'PL'; -- ƫ�ߴ���ɱ�
	PAYABLE_BILL_TYPE_SERVICE_FEE  VARCHAR2(20) := 'SF'; -- �����Ӧ��
	PAYABLE_BILL_TYPE_CLAIM        VARCHAR2(20) := 'C'; -- ����Ӧ��
	PAYABLE_BILL_TYPE_REFUND       VARCHAR2(20) := 'R'; -- ���˷�Ӧ��
	PAYABLE_BILL_TYPE_COMPENSATION VARCHAR2(20) := 'CP'; -- ���񲹾�Ӧ��
	/**
  * SOURCE_BILL_TYPE ��Դ����������
  */
	PAYABLE_SOURCE_BILL_TYPE_W  VARCHAR2(20) := 'W'; -- �˵�
	PAYABLE_SOURCE_BILL_TYPE_RS VARCHAR2(20) := 'RS'; -- �������õ�
	PAYABLE_SOURCE_BILL_TYPE_AW VARCHAR2(20) := 'AW'; -- ��������
	PAYABLE_SOURCE_BILL_TYPE_TP VARCHAR2(20) := 'TP'; -- ��ת����嵥
	PAYABLE_SOURCE_BILL_TYPE_AP VARCHAR2(20) := 'AP'; -- �ϴ�Ʊ�嵥
	PAYABLE_SOURCE_BILL_TYPE_PL VARCHAR2(20) := 'PL'; -- �ⷢ��
	/**
  * EFFECTIVE_STATUS ��Ч״̬
  */
	PAYABLE_EFFECTIVE_STATUS_YES VARCHAR2(20) := 'Y'; -- ����Ч
	PAYABLE_EFFECTIVE_STATUS_NO  VARCHAR2(20) := 'N'; -- δ��Ч
	/**
  * PAY_STATUS ֧��״̬
  */
	PAYABLE_PAY_STATUS_YES VARCHAR2(20) := 'Y'; -- ��֧��
	PAYABLE_PAY_STATUS_NO  VARCHAR2(20) := 'N'; -- δ֧��
	/**
  * PAYMENT_STATUS ����״̬
  */
	PAYABLE_PAYMENT_STATUS_NOT_PAY VARCHAR2(20) := 'NP'; -- δ����
	PAYABLE_PAYMENT_STATUS_PAYING  VARCHAR2(20) := 'PG'; -- ������
	PAYABLE_PAYMENT_STATUS_PAID    VARCHAR2(20) := 'PD'; -- �Ѹ���
	/**
  * PAYER_TYPE ���
  */
	PAYABLE_PAYER_TYPE_ORIGIN      VARCHAR2(20) := 'O'; -- ��������
	PAYABLE_PAYER_TYPE_DESTINATION VARCHAR2(20) := 'D'; -- ���︶��
	/**
  * PAYABLE_TYPE Ӧ������
  */
	PAYABLE_PAYABLE_TYPE_FIRST VARCHAR2(20) := 'F'; -- �׿�
	PAYABLE_PAYABLE_TYPE_LAST  VARCHAR2(20) := 'L'; -- β��
	/**
  * APPROVE_STATUS ���״̬
  */
	PAYABLE_APPROVE_STATUS_NA VARCHAR2(20) := 'NA'; -- δ���
	PAYABLE_APPROVE_STATUS_AA VARCHAR2(20) := 'AA'; -- �����

	/**
  * FROZEN_STATUS ����״̬
  */
	PAYABLE_FROZEN_STATUS_F VARCHAR2(20) := 'F'; -- �Ѷ���
	PAYABLE_FROZEN_STATUS_N VARCHAR2(20) := 'N'; -- --δ����

	-- ==================== �ֽ��տ BILL_CASH_COLLECTION ====================
	/**
  * BILL_TYPE ��������
  */
	CASH_COLL_BILL_TYPE_C VARCHAR2(20) := 'C'; -- �ֽ��տ
	/**
  * SOURCE_BILL_TYPE ��Դ����������
  */
	CASH_COLL_SOURCE_BILL_TYPE_W VARCHAR2(20) := 'W'; -- �˵�
	CASH_COLL_SOURCE_BILL_TYPE_R VARCHAR2(20) := 'R'; -- СƱ
	/**
  * STATUS ����״̬
  */
	CASH_COLL_STATUS_SUBMIT  VARCHAR2(20) := 'S'; -- �ύ
	CASH_COLL_STATUS_CONFIRM VARCHAR2(20) := 'C'; -- ȷ������

	-- ==================== Ԥ�յ� BILL_DEPOSIT_RECEIVED ====================
	/**
  * REFUND_STATUS �˿�״̬
  */
	DEPOSIT_RCVD_REFUND_STATUS_RD VARCHAR2(20) := 'RD'; -- ���˿�
	DEPOSIT_RCVD_REFUND_STATUS_NR VARCHAR2(20) := 'NR'; -- δ�˿�
	DEPOSIT_RCVD_REFUND_STATUS_RG VARCHAR2(20) := 'RG'; -- �˿���
	/**
  * STATUS ����״̬
  */
	DEPOSIT_RCVD_STATUS_SS VARCHAR2(20) := 'SS'; -- ���ύ
	DEPOSIT_RCVD_STATUS_CC VARCHAR2(20) := 'CC'; -- ����ȷ��
	DEPOSIT_RCVD_STATUS_SC VARCHAR2(20) := 'SC'; -- �ͻ����˵�ȷ��
	/**
  * ��������
  */
	DEPOSIT_RCVD__TRANS_TYPE__LC VARCHAR2(20) := 'LC'; -- ר�߿ͻ�
	DEPOSIT_RCVD__TRANS_TYPE__PA VARCHAR2(20) := 'PA'; -- ƫ�ߴ���
	DEPOSIT_RCVD__TRANS_TYPE__AA VARCHAR2(20) := 'AA'; -- ���˴���

	-- ==================== Ԥ���� BILL_ADVANCED_PAYMENT ====================
	/**
  * AUDIT_STATUS ����״̬
  */
	ADVANCED_PAY_AUDIT_STATUS_NA VARCHAR2(20) := 'NA'; -- δ����
	ADVANCED_PAY_AUDIT_STATUS_AA VARCHAR2(20) := 'AA'; -- ����ͨ��
	ADVANCED_PAY_AUDIT_STATUS_AD VARCHAR2(20) := 'AD'; -- ������ͨ��

	-- ==================== ��� BILL_REPAYMENT ====================
	/**
  * STATUS ����״̬
  */
	REPAYMENT_STATUS_SUBMIT  VARCHAR2(20) := 'S'; -- ���ύ
	REPAYMENT_STATUS_CONFIRM VARCHAR2(20) := 'C'; -- ��ȷ��
	/**
  * AUDIT_STATUS ���״̬
  */
	REPAYMENT_AUDIT_STATUS_NA VARCHAR2(20) := 'NA'; -- δ���
	REPAYMENT_AUDIT_STATUS_AA VARCHAR2(20) := 'AA'; -- �����
	/**
  * BILL_TYPE ��������
  */
	REPAYMENT_BILL_TYPE_WAYBILL VARCHAR2(20) := 'W'; -- �˷�
	REPAYMENT_BILL_TYPE_FC      VARCHAR2(20) := 'FC'; -- ������
	REPAYMENT_BILL_TYPE_COD     VARCHAR2(20) := 'COD'; -- ���ջ���
	REPAYMENT_BILL_TYPE_REVENUE VARCHAR2(20) := 'R'; -- СƱ
	REPAYMENT_BILL_TYPE_PL      VARCHAR2(20) := 'PL'; -- �ⷢ��

	-- ==================== ��� BILL_PAYMENT ====================
	/**
  * REMIT_STATUS ���״̬
  */
	PAYMENT_REMIT_STATUS_NT VARCHAR2(20) := 'NT'; -- δ���
	PAYMENT_REMIT_STATUS_TG VARCHAR2(20) := 'TG'; -- �����
	PAYMENT_REMIT_STATUS_TD VARCHAR2(20) := 'TD'; -- �ѻ��
	/**
  * SOURCE_BILL_TYPE ��Դ��������
  */
	PAYMENT_SOURCE_BILL_TYPE_YF VARCHAR2(20) := 'YF'; -- Ӧ����
	PAYMENT_SOURCE_BILL_TYPE_YS VARCHAR2(20) := 'YS'; -- Ԥ�յ�
	PAYMENT_SOURCE_BILL_TYPE_DZ VARCHAR2(20) := 'DZ'; -- ���˵�
	PAYMENT_SOURCE_BILL_TYPE_PL VARCHAR2(20) := 'PL'; -- �ⷢ��

	-- ==================== ���˵� STATEMENT_OF_ACCOUNT ====================
	/**
  * CONFIRM_STATUS ȷ��״̬
  */
	STATEMENT_CONFIRM_STATUS_C VARCHAR2(20) := 'C'; -- ��ȷ��
	STATEMENT_CONFIRM_STATUS_N VARCHAR2(20) := 'N'; -- δȷ��
	/**
  * BILL_TYPE ���˵�����
  */
	STATEMENT_BILL_TYPE_CA VARCHAR2(20) := 'CA'; -- �ͻ����˵�
	STATEMENT_BILL_TYPE_AA VARCHAR2(20) := 'AA'; -- ������˵�
	STATEMENT_BILL_TYPE_AF VARCHAR2(20) := 'AF'; -- ���˶��˵�

	-- ==================== ���˵���ϸ STATEMENT_OF_ACCOUNT_D ====================
	/**
  * BILL_PARENT_TYPE ���˵���ϸ���ݴ�����
  */
	STATEMENTD_BILL_PARENT_TYPE_YS VARCHAR2(20) := '10.YS'; -- Ӧ�յ�
	STATEMENTD_BILL_PARENT_TYPE_YF VARCHAR2(20) := '20.YF'; -- Ӧ����
	STATEMENTD_BILL_PARENT_TYPE_US VARCHAR2(20) := '30.US'; -- Ԥ�յ�
	STATEMENTD_BILL_PARENT_TYPE_UF VARCHAR2(20) := '40.UF'; -- Ԥ����

	-- ==================== ������ BILL_WRITEOFF ====================
	/**
  * WRITEOFF_TYPE ������ʽ
  */
	WRITEOFF_WRITEOFF_TYPE_RP VARCHAR2(20) := 'RP'; -- Ӧ�ճ�Ӧ��
	WRITEOFF_WRITEOFF_TYPE_DR VARCHAR2(20) := 'DR'; -- Ԥ�ճ�Ӧ��
	WRITEOFF_WRITEOFF_TYPE_AP VARCHAR2(20) := 'AP'; -- Ԥ����Ӧ��
	WRITEOFF_WRITEOFF_TYPE_RR VARCHAR2(20) := 'RR'; -- �����Ӧ��
	WRITEOFF_WRITEOFF_TYPE_PP VARCHAR2(20) := 'PP'; -- �����Ӧ��
	WRITEOFF_WRITEOFF_TYPE_BR VARCHAR2(20) := 'BR'; -- ���˳�Ӧ��
	WRITEOFF_WRITEOFF_TYPE_PD VARCHAR2(20) := 'PD'; -- �����Ԥ��

	-- ==================== ���ջ��� COD ====================
	/**
  * REFUND_PATH ���ջ����˿�·��
  */
	COD_COD_REFUND_PATH_ONLINE  VARCHAR2(20) := 'ONL';
	COD_COD_REFUND_PATH_OFFLINE VARCHAR2(20) := 'OFFL';
	/**
  * COD_TYPE ���ջ������ͣ��ο��˵��д��ջ����˿����ͣ�
  */
	COD_COD_TYPE_RETURN_1_DAY   VARCHAR2(20) := 'R1'; -- ������
	COD_COD_TYPE_RETURN_3_DAY   VARCHAR2(20) := 'R3'; -- ������
	COD_COD_TYPE_RETURN_APPROVE VARCHAR2(20) := 'RA'; -- �����
	/**
  * AIR_STATUS ���˴��ջ���״̬
  */
	COD_AIR_STATUS_NOT_AUDIT VARCHAR2(20) := 'NA'; -- δ���

	COD_AIR_STATUS_AUDIT_AGREE VARCHAR2(20) := 'AA'; -- �����

	/**
  * PUBLIC_PRIVATE_FLAG �Թ���˽��־
  */
	COD_PUBLIC_PRIVATE_FLAG_C VARCHAR2(20) := 'C'; -- �Թ�
	COD_PUBLIC_PRIVATE_FLAG_R VARCHAR2(20) := 'R'; -- ��˽
	/**
  * STATUS ���ջ���״̬
  */
	COD_STATUS_NOT_RETURN          VARCHAR2(20) := 'NR'; -- δ�˿�
	COD_STATUS_APPROVING           VARCHAR2(20) := 'AG'; -- �����
	COD_STATUS_SHIPPER_FREEZE      VARCHAR2(20) := 'SF'; -- Ӫҵ������
	COD_STATUS_CASHIER_APPROVE     VARCHAR2(20) := 'CA'; -- ����Ա���
	COD_STATUS_FUND_FREEZE         VARCHAR2(20) := 'FF'; -- �ʽ𲿶���
	COD_STATUS_RETURNING           VARCHAR2(20) := 'RG'; -- �˿���
	COD_STATUS_RETURN_FAILURE_APPL VARCHAR2(20) := 'RFA'; -- �˿�ʧ������
	COD_STATUS_NEGATIVE_RTN_SUCC   VARCHAR2(20) := 'NRS'; -- �����ɹ�
	COD_STATUS_RETURN_FAILURE      VARCHAR2(20) := 'RF'; -- �˿�ʧ��
	COD_STATUS_RETURNED            VARCHAR2(20) := 'RD'; -- ���˿�

	-- ==================== ���ջ�����ʷ״̬ COD_LOG ====================
	/**
  * OPERATE_TYPE ��������
  */
	COD_LOG_OPERATE_TYPE_A   VARCHAR2(20) := 'A'; -- ���
	COD_LOG_OPERATE_TYPE_NA  VARCHAR2(20) := 'NA'; -- �����
	COD_LOG_OPERATE_TYPE_F   VARCHAR2(20) := 'F'; -- ����
	COD_LOG_OPERATE_TYPE_D   VARCHAR2(20) := 'D'; -- ����
	COD_LOG_OPERATE_TYPE_CA  VARCHAR2(20) := 'CA'; -- �����˺�
	COD_LOG_OPERATE_TYPE_ER  VARCHAR2(20) := 'ET'; -- �������
	COD_LOG_OPERATE_TYPE_RA  VARCHAR2(20) := 'RA'; -- �������
	COD_LOG_OPERATE_TYPE_RS  VARCHAR2(20) := 'RS'; -- ���ɹ�
	COD_LOG_OPERATE_TYPE_NRS VARCHAR2(20) := 'NRS'; -- �����ɹ�
	COD_LOG_OPERATE_TYPE_RF  VARCHAR2(20) := 'RF'; -- ���ʧ��
	COD_LOG_OPERATE_TYPE_FAP VARCHAR2(20) := 'FAP'; -- ���ʧ�����ͨ��
	COD_LOG_OPERATE_TYPE_FAR VARCHAR2(20) := 'FAR'; -- ���ʧ������˻�
	COD_LOG_OPERATE_TYPE_FF  VARCHAR2(20) := 'FF'; -- �ʽ𲿶���
	COD_LOG_OPERATE_TYPE_FRF VARCHAR2(20) := 'FRF'; -- �ʽ𲿷�����

	-- ==================== �ֽ����뱨����ϸ CASH_COLLECTION_RPT_D ====================
	/**
  * SOURCE_BILL_TYPE ��Դ����������
  */
	CASH_RPT_D_SOURCE_BILL_TYPE_XS VARCHAR2(20) := 'XS'; -- �ֽ��տ
	CASH_RPT_D_SOURCE_BILL_TYPE_US VARCHAR2(20) := 'US'; -- Ԥ�յ�
	CASH_RPT_D_SOURCE_BILL_TYPE_HK VARCHAR2(20) := 'HK'; -- ���

	-- ==================== СƱ���������¼ NOTE_APPL ====================
	/**
  * STATUS ����״̬
  */
	NOTE_APPL_STATUS_SUBMIT VARCHAR2(20) := 'S'; -- ���ύ
	NOTE_APPL_STATUS_D      VARCHAR2(20) := 'D'; -- ���·�
	NOTE_APPL_STATUS_IN     VARCHAR2(20) := 'I'; -- �����
	/**
  * APPROVE_STATUS ����״̬
  */
	NOTE_APPL_APPROVE_STATUS_NA VARCHAR2(20) := 'NA'; -- δ����
	NOTE_APPL_APPROVE_STATUS_RA VARCHAR2(20) := 'RA'; -- ����ͨ��
	NOTE_APPL_APPROVE_STATUS_RD VARCHAR2(20) := 'RD'; -- ������ͨ��
	/**
  * WRITE_OFF_STATUS ����״̬
  */
	NOTE_APPL_WRITEOFF_STATUS_NW VARCHAR2(20) := 'NW'; -- δ����
	NOTE_APPL_WRITEOFF_STATUS_AW VARCHAR2(20) := 'AW'; -- �������
	NOTE_APPL_WRITEOFF_STATUS_WD VARCHAR2(20) := 'WD'; -- �Ѻ���

	-- ==================== СƱ���ݷ������ NOTE_STOCK_IN ====================
	/**
  * ISSUED_TYPE �·���ʽ
  */
	NOTE_STOCK_IN_ISSUED_TYPE_E VARCHAR2(20) := 'E'; -- ���
	NOTE_STOCK_IN_ISSUED_TYPE_I VARCHAR2(20) := 'I'; -- �ڲ�����
	NOTE_STOCK_IN_ISSUED_TYPE_P VARCHAR2(20) := 'P'; -- ����

	-- ==================== СƱ������ϸ NOTE_DETAILS ====================
	/**
  * STATUS ����״̬
  */
	NOTE_DETAILS_STATUS_SUBMIT VARCHAR2(20) := 'S'; -- ���ύ
	NOTE_DETAILS_STATUS_USED   VARCHAR2(20) := 'U'; -- ��ʹ��

	-- ==================== �˵���Ϣ POD_ENTITY ====================
	/**
  * POD_TYPE ǩ��/��ǩ������
  */
	POD__POD_TYPE__POD VARCHAR2(20) := 'POD'; -- ǩ��
	POD__POD_TYPE__UPD VARCHAR2(20) := 'UPD'; -- ��ǩ��

	--============================���ҵ�λ=====================================
	CURRENCY_CODE_RMB VARCHAR2(20) := 'RMB'; --�����

	--=============================��ĩ��������====================================
	--��������
	BALANCE_TYPE_RECEIVABLE VARCHAR(20) := 'R'; -- Ӧ����ĩ����
	BALANCE_TYPE_PAYABLE    VARCHAR(20) := 'P'; -- Ӧ����ĩ����
	BALANCE_TYPE_DEPOSIT    VARCHAR(20) := 'D'; -- Ԥ����ĩ����

	---==========================�������κ�==================================
	BALANCE_BATCH_INITED VARCHAR2(20) := 'INITED'; --��ʼ��û�����κβ���
	BALANCE_BATCH_BEGIN  VARCHAR2(20) := 'BEGIN'; --���ڽ���
	BALANCE_BATCH_END    VARCHAR2(20) := 'END'; --���˽���

	---========================== ƾ֤ҵ�񳡾� ���� ==================================
	DE_ORIG_CR_CASH   VARCHAR2(30) := 'DE_ORIG_CR_CASH'; -- �������ֽ�
	DE_ORIG_CR_CARD   VARCHAR2(30) := 'DE_ORIG_CR_CARD'; -- ���������п���
	DE_ORIG_AR        VARCHAR2(30) := 'DE_ORIG_AR'; -- �������½�/��Ƿ��
	DE_AP_SERVICE_FEE VARCHAR2(30) := 'DE_AP_SERVICE_FEE'; -- �����

	---========================== ƾ֤ҵ�񳡾� ǩ�ջ��� ==================================
	UR_ORIG_CR_CASH_NO_POD VARCHAR2(30) := 'UR_ORIG_CR_CASH_NO_POD'; -- ���δǩ�գ�����Ϊ���½�/Ƿ������ֽ𻹿
	UR_ORIG_CR_CARD_NO_POD VARCHAR2(30) := 'UR_ORIG_CR_CARD_NO_POD'; -- ���δǩ�գ�����Ϊ���½�/Ƿ��������п����
	UR_ORIG_POD_CR         VARCHAR2(30) := 'UR_ORIG_POD_CR'; -- ǩ�գ��ѻ�����Ѻ���������Ϊ�ָ�/�½�/��Ƿ��
	UR_ORIG_POD_AR         VARCHAR2(30) := 'UR_ORIG_POD_AR'; -- ǩ�գ�δ�������Ϊ�½�/��Ƿ��
	UR_ORIG_CR_CASH_POD    VARCHAR2(30) := 'UR_ORIG_CR_CASH_POD'; -- �����ǩ�գ��½�/Ƿ��ǩ�������ֽ𻹿
	UR_ORIG_CR_BANK_POD    VARCHAR2(30) := 'UR_ORIG_CR_BANK_POD'; -- �����ǩ�գ��½�/Ƿ��ǩ���������л��

	---========================== ƾ֤ҵ�񳡾� ǩ�յ��� ==================================
	UR_DEST_AR             VARCHAR2(30) := 'UR_DEST_AR'; -- ������������
	UR_DEST_CR_CASH_NO_POD VARCHAR2(30) := 'UR_DEST_CR_CASH_NO_POD'; -- ���δǩ�գ�������ʵ���ֽ�򻹿��ֽ�
	UR_DEST_POD_CR         VARCHAR2(30) := 'UR_DEST_POD_CR'; -- ǩ�գ��ѻ�����Ѻ�����������ǩ��ǰ����ȡ�ֽ�/���л򻹿��ֽ�/���л��Ѻ�����
	UR_DEST_CR_CASH_AS_POD VARCHAR2(30) := 'UR_DEST_CR_CASH_AS_POD'; -- ǩ�գ�������ͬʱ�յ��ֽ�
	UR_DEST_CR_BANK_NO_POD VARCHAR2(30) := 'UR_DEST_CR_BANK_NO_POD'; -- ���δǩ�գ�������ʵ�����л򻹿����У�
	UR_DEST_CR_BANK_AS_POD VARCHAR2(30) := 'UR_DEST_CR_BANK_AS_POD'; -- ǩ�գ�����ͬʱ�յ����У�
	UR_DEST_AR_POD         VARCHAR2(30) := 'UR_DEST_AR_POD'; -- ǩ��ʱδ�տ������ר�ߵ�����ת�½�/Ƿ���ƫ��ǩ��ʱδ�տ�����ǩ��ʱδ�տ�������
	UR_DEST_CR_CASH_POD    VARCHAR2(30) := 'UR_DEST_CR_CASH_POD'; -- ǩ�պ󻹿��ֽ𣨰��������ר�ߵ�����ת�½�/Ƿ���ƫ��ǩ��ʱδ�տ�����ǩ��ʱδ�տ�3��������Ļ����ֽ�
	UR_DEST_CR_BANK_POD    VARCHAR2(30) := 'UR_DEST_CR_BANK_POD'; -- ǩ�պ󻹿����У����������ר�ߵ�����ת�½�/Ƿ���ƫ��ǩ��ʱδ�տ�����ǩ��ʱδ�տ�3��������Ļ������У�

	---========================== ƾ֤ҵ�񳡾� ���⣨����ר�ߡ�ƫ�ߡ����˲��Ų��������⣩ ==================================
	CLAIM_ORIG_AP_WO_AR_WB VARCHAR2(30) := 'CLAIM_ORIG_AP_WO_AR_WB'; -- ͬһ���ţ�Ӧ�������˵���Ӧ�����˷ѻ������������ȡС������ʼ����������
	CLAIM_ORIG_AP_COST     VARCHAR2(30) := 'CLAIM_ORIG_AP_COST'; -- ͬһ���ţ�������-�˵����˷ѣ���ʼ����������
	CLAIM_ORIG_AP_WO_AR    VARCHAR2(30) := 'CLAIM_ORIG_AP_WO_AR'; -- �����Ӧ��ʼ������ʼ���������루���������ⵥ�ź�Ӧ�յ�����ͬ��ͬ��������жԳ壩
	CLAIM_ORIG_CP          VARCHAR2(30) := 'CLAIM_ORIG_CP'; -- �����Ӧ�պ�Ӧ�������Դ�������ʱʼ�������������֧��
	CLAIM_DEST_AP_WO_AR_WB VARCHAR2(30) := 'CLAIM_DEST_AP_WO_AR_WB'; -- ͬһ���ţ�Ӧ�������˵���Ӧ�����˷ѻ������������ȡС�����ɵ��ﲿ������
	CLAIM_DEST_AP_COST     VARCHAR2(30) := 'CLAIM_DEST_AP_COST'; -- ͬһ���ţ�������-�˵����˷ѣ��ɵ��ﲿ������
	CLAIM_DEST_AP_WO_AR    VARCHAR2(30) := 'CLAIM_DEST_AP_WO_AR'; -- �����Ӧ�յ������ɵ��ﲿ�����루���������ⵥ�ź�Ӧ�յ�����ͬ��ͬ��������жԳ壩
	CLAIM_DEST_CP          VARCHAR2(30) := 'CLAIM_DEST_CP'; -- �����Ӧ�պ�Ӧ�������Դ�������ʱ���ﲿ���������֧��

	---========================== ƾ֤ҵ�񳡾� ���ջ��� ==================================
	COD_DE_AR_AP             VARCHAR2(30) := 'COD_DE_AR_AP'; -- ���������ջ��
	COD_DEST_CR_CASH         VARCHAR2(30) := 'COD_DEST_CR_CASH'; -- ǩ��ǰ��ǩ��ʱ�����ջ������ֽ�
	COD_DEST_CR_BANK         VARCHAR2(30) := 'COD_DEST_CR_BANK'; -- ǩ��ǰ��ǩ��ʱ�����ջ���������
	COD_DEST_AP_WO_AR_POD    VARCHAR2(30) := 'COD_DEST_AP_WO_AR_POD'; -- Ӧ��(����)��Ӧ�գ������˷ѣ�����ǩ�գ�
	COD_DEST_AP_WO_AR_NO_POD VARCHAR2(30) := 'COD_DEST_AP_WO_AR_NO_POD'; -- Ӧ��(����)��Ӧ�գ������˷ѣ���δǩ�գ�
	COD_ORIG_AP_WO_AR_POD    VARCHAR2(30) := 'COD_ORIG_AP_WO_AR_POD'; -- Ӧ�������գ���Ӧ��ʼ������ǩ�գ�
	COD_ORIG_AP_WO_AR_NO_POD VARCHAR2(30) := 'COD_ORIG_AP_WO_AR_NO_POD'; -- Ӧ�������գ���Ӧ��ʼ����δǩ�գ�

	---========================== ƾ֤ҵ�񳡾� Ԥ�տͻ� ==================================
	DR_CR_CASH              VARCHAR2(30) := 'DR_CR_CASH'; -- Ԥ�տͻ��ֽ�
	DR_CR_BANK              VARCHAR2(30) := 'DR_CR_BANK'; -- Ԥ�տͻ�����
	DR_DEST_CR_WO_AR_NO_POD VARCHAR2(30) := 'DR_DEST_CR_WO_AR_NO_POD'; -- Ԥ�տͻ���Ӧ�յ����˷ѣ�δǩ�գ�
	DR_DEST_CR_WO_AR_POD    VARCHAR2(30) := 'DR_DEST_CR_WO_AR_POD'; -- Ԥ�տͻ���Ӧ�յ����˷ѣ���ǩ�գ�
	DR_ORIG_CR_WO_AR_NO_POD VARCHAR2(30) := 'DR_ORIG_CR_WO_AR_NO_POD'; -- Ԥ�տͻ���Ӧ��ʼ����δǩ�գ�
	DR_ORIG_CR_WO_AR_POD    VARCHAR2(30) := 'DR_ORIG_CR_WO_AR_POD'; -- Ԥ�տͻ���Ӧ��ʼ������ǩ�գ�
	DR_ORIG_REFUND_CP       VARCHAR2(30) := 'DR_ORIG_REFUND_CP'; -- ʼ����Ԥ�ո�������

	---========================== ƾ֤ҵ�񳡾� ���˳�Ӧ�� ==================================
	BA_ORIG_AR_NO_POD VARCHAR2(30) := 'BA_ORIG_AR_NO_POD'; -- ���˳�Ӧ��ʼ����δǩ�գ�
	BA_ORIG_AR_POD    VARCHAR2(30) := 'BA_ORIG_AR_POD'; -- ���˳�Ӧ��ʼ������ǩ�գ�
	BA_DEST_AR_NO_POD VARCHAR2(30) := 'BA_DEST_AR_NO_POD'; -- ���˳�Ӧ�յ����˷ѣ�δǩ�գ�
	BA_DEST_AR_POD    VARCHAR2(30) := 'BA_DEST_AR_POD'; -- ���˳�Ӧ�յ����˷ѣ���ǩ�գ�

	---========================== ƾ֤ҵ�񳡾� СƱ ==================================
	OR_NOR_CR_CASH   VARCHAR2(30) := 'OR_NOR_CR_CASH'; -- СƱ�ֽ�Ӫҵ�����룺��Ա���ѡ�����Ʒ������3�����ͣ�
	OR_NOR_CR_BANK   VARCHAR2(30) := 'OR_NOR_CR_BANK'; -- СƱ���У�Ӫҵ�����룺��Ա���ѡ�����Ʒ������3�����ͣ�
	OR_NOR_AR        VARCHAR2(30) := 'OR_NOR_AR'; -- СƱӦ�գ�Ӫҵ�����룺��Ա���ѡ�����Ʒ������3�����ͣ�
	OR_POR_CR_CASH   VARCHAR2(30) := 'OR_POR_CR_CASH'; -- СƱ�ֽ���Ӫҵ�����룺����Ա���ѡ�����Ʒ������3��֮������ͣ�
	OR_POR_CR_BANK   VARCHAR2(30) := 'OR_POR_CR_BANK'; -- СƱ���У���Ӫҵ�����룺����Ա���ѡ�����Ʒ������3��֮������ͣ�
	OR_POR_AR        VARCHAR2(30) := 'OR_POR_AR'; -- СƱӦ�գ���Ӫҵ�����룺����Ա���ѡ�����Ʒ������3��֮������ͣ�
	OR_CR_CASH_WO_AR VARCHAR2(30) := 'OR_CR_CASH_WO_AR'; -- СƱӦ�ջ����ֽ�
	OR_CR_BANK_WO_AR VARCHAR2(30) := 'OR_CR_BANK_WO_AR'; -- СƱӦ�ջ�������
	OR_COD_AP_WO_AR  VARCHAR2(30) := 'OR_COD_AP_WO_AR'; -- Ӧ�������գ���СƱӦ��
	OR_DR_WO_AR      VARCHAR2(30) := 'OR_DR_WO_AR'; -- Ԥ�տͻ���СƱӦ��
	OR_BA_AR         VARCHAR2(30) := 'OR_BA_AR'; -- ���˳�СƱӦ��

	---========================== ƾ֤ҵ�񳡾� ������Υ��Ʒ������ ==================================
	AC_ORIG_AR      VARCHAR2(30) := 'AC_ORIG_AR'; -- Ӧ��ʼ����������Υ��Ʒ��
	AC_ORIG_CR      VARCHAR2(30) := 'AC_ORIG_CR'; -- �ָ����ֽ�/���п�����������Υ��Ʒ��
	AC_ORIG_AR_LOST VARCHAR2(30) := 'AC_ORIG_AR_LOST'; -- Ӧ��ʼ����ȫ��������
	AC_ORIG_CR_LOST VARCHAR2(30) := 'AC_ORIG_CR_LOST'; -- �ָ����ֽ�/���п�����ȫ��������

	---========================== ƾ֤ҵ�񳡾� ���˷� ==================================
	RD_ORIG_AP_WO_AR_WB   VARCHAR2(30) := 'RD_ORIG_AP_WO_AR_WB'; -- ͬһ���ţ����˷��˵���Ӧ�����˷ѻ������˷ѽ�����ȡС������ʼ����������
	RD_ORIG_AP_WO_AR_COST VARCHAR2(30) := 'RD_ORIG_AP_WO_AR_COST'; -- ͬһ���ţ����˷ѽ��-�˵����˷ѣ���ʼ����������
	RD_ORIG_CP            VARCHAR2(30) := 'RD_ORIG_CP'; -- ʼ�����˷Ѹ�������
	RD_DEST_AP_WO_AR_WB   VARCHAR2(30) := 'RD_DEST_AP_WO_AR_WB'; -- ͬһ���ţ����˷��˵���Ӧ�����˷ѻ������˷ѽ�����ȡС�����ɵ��ﲿ������
	RD_DEST_AP_WO_AR_COST VARCHAR2(30) := 'RD_DEST_AP_WO_AR_COST'; -- ͬһ���ţ����˷ѽ��-�˵����˷ѣ��ɵ��ﲿ������
	RD_DEST_CP            VARCHAR2(30) := 'RD_DEST_CP'; -- �������˷Ѹ�������

	---========================== ƾ֤ҵ�񳡾� ���񲹾� ==================================
	CN_ORIG_CP VARCHAR2(30) := 'CN_ORIG_CP'; -- ʼ�����񲹾ȸ�������
	CN_DEST_CP VARCHAR2(30) := 'CN_DEST_CP'; -- ������񲹾ȸ�������

	---========================== ƾ֤ҵ�񳡾� ƫ�ߴ���ɱ� ==================================
	COST_AP_DE                VARCHAR2(30) := 'COST_AP_DE'; -- �ⷢ����¼��
	COST_AP_POD               VARCHAR2(30) := 'COST_AP_POD'; -- ����Ӧ������ɱ����˵�ǩ��
	COST_AP_WO_DEST_AR_POD    VARCHAR2(30) := 'COST_AP_WO_DEST_AR_POD'; -- Ӧ��(�ɱ�)��Ӧ�գ������˷ѣ�����ǩ�գ�
	COST_AP_WO_DEST_AR_NO_POD VARCHAR2(30) := 'COST_AP_WO_DEST_AR_NO_POD'; -- Ӧ��(�ɱ�)��Ӧ�գ������˷ѣ���δǩ�գ�
	COST_CP                   VARCHAR2(30) := 'COST_CP'; -- ʵ��ƫ�ߴ���ɱ�

	---========================== ƾ֤ҵ�񳡾� ����ǩ�� ==================================
	UR_CR_CASH_NO_POD VARCHAR2(30) := 'UR_CR_CASH_NO_POD'; -- ���δǩ�գ��������ֽ�򻹿��ֽ�
	UR_CR_CASH_AS_POD VARCHAR2(30) := 'UR_CR_CASH_AS_POD'; -- ǩ�գ�ͬʱ�������ֽ�
	UR_CR_BANK_NO_POD VARCHAR2(30) := 'UR_CR_BANK_NO_POD'; -- ���δǩ�գ������������л򻹿����У�
	UR_CR_BANK_AS_POD VARCHAR2(30) := 'UR_CR_BANK_AS_POD'; -- ǩ�գ�ͬʱ���������У�
	UR_CR_CASH_POD    VARCHAR2(30) := 'UR_CR_CASH_POD'; -- ǩ�պ󻹿��ֽ𣨵�����
	UR_CR_BANK_POD    VARCHAR2(30) := 'UR_CR_BANK_POD'; -- ǩ�պ󻹿����У�������

	---========================== ƾ֤ҵ�񳡾� ƫ������ ==================================
	CLAIM_AP_WO_AR_WB   VARCHAR2(30) := 'CLAIM_AP_WO_AR_WB'; -- ͬһ���ţ�Ӧ�������˵���Ӧ�����˷ѻ������������ȡС������ƫ�߲�������
	CLAIM_COST_AP       VARCHAR2(30) := 'CLAIM_COST_AP'; -- ͬһ���ţ�������-�˵����˷ѣ���ƫ�߲�������
	CLAIM_AP_WO_DEST_AR VARCHAR2(30) := 'CLAIM_AP_WO_DEST_AR'; -- �����Ӧ�յ�������ƫ�߲������루���������ⵥ�ź�Ӧ�յ�����ͬ��ͬ��������жԳ壩
	CLAIM_CP            VARCHAR2(30) := 'CLAIM_CP'; -- �����Ӧ�պ�Ӧ�������Դ�������ʱƫ�߲����������֧��

	---========================== ƾ֤ҵ�񳡾� Ԥ��ƫ�ߴ��� ==================================
	DR_CASH              VARCHAR2(30) := 'DR_CASH'; -- Ԥ��ƫ�ߴ����ֽ�
	DR_BANK              VARCHAR2(30) := 'DR_BANK'; -- Ԥ��ƫ�ߴ������У�
	DR_WO_DEST_AR_POD    VARCHAR2(30) := 'DR_WO_DEST_AR_POD'; -- Ԥ��ƫ�ߴ����Ӧ�յ����˷ѣ���ǩ�գ�
	DR_WO_DEST_AR_NO_POD VARCHAR2(30) := 'DR_WO_DEST_AR_NO_POD'; -- Ԥ��ƫ�ߴ����Ӧ�յ����˷ѣ�δǩ�գ�
	DR_CP                VARCHAR2(30) := 'DR_CP'; -- ƫ����Ԥ�ո�������

	---========================== ƾ֤ҵ�񳡾� ���˴���ɱ� ==================================
	COST_AIR_AP_DE  VARCHAR2(30) := 'COST_AP_DE'; -- ���˳ɱ�����
	COST_AIR_AP_POD VARCHAR2(30) := 'COST_AP_POD'; -- ���˳ɱ�ȷ��
	COST_AIR_CP     VARCHAR2(30) := 'COST_CP'; -- ���˳ɱ���������

	---========================== ƾ֤ҵ�񳡾� ����Ӧ��Ӧ�� ==================================
	OT_AP_DE        VARCHAR2(30) := 'OT_AP_DE'; -- ����Ӧ���ɱ�����
	OT_CP           VARCHAR2(30) := 'OT_CP'; -- ����Ӧ����������
	OT_AR_COMM_CASH VARCHAR2(30) := 'OT_AR_COMM_CASH'; -- ����Ӧ��-����-�ֽ�
	OT_AR_COMM_BANK VARCHAR2(30) := 'OT_AR_COMM_BANK'; -- ����Ӧ��-����-����
	OT_AR_COMM_DEBT VARCHAR2(30) := 'OT_AR_COMM_DEBT'; -- ����Ӧ��-����-�½�/��Ƿ

	---========================== ƾ֤ҵ�񳡾� ����Ӧ��������Ӧ������Ӧ�գ�����Ӧ�գ� ==================================
	OT_DEST_AP_WO_DEST_AR_POD     VARCHAR2(30) := 'OT_DEST_AP_WO_DEST_AR_POD'; -- Ӧ���������ɱ���Ӧ�յ����˷ѣ���ǩ�գ�
	OT_DEST_AP_WO_DEST_AR_NO_POD  VARCHAR2(30) := 'OT_DEST_AP_WO_DEST_AR_NO_POD'; -- Ӧ���������ɱ���Ӧ�յ����˷ѣ�δǩ�գ�
	OT_DEST_OT_AP_WO_DEST_AR_POD  VARCHAR2(30) := 'OT_DEST_OT_AP_WO_DEST_AR_POD'; -- ����Ӧ����������Ӧ�յ����˷ѣ���ǩ�գ�
	OT_DEST_OT_AP_WO_DEST_AR_NPOD VARCHAR2(30) := 'OT_DEST_OT_AP_WO_DEST_AR_NPOD'; -- ����Ӧ����������Ӧ�յ����˷ѣ�δǩ�գ�
	OT_AP_WO_OT_AR                VARCHAR2(30) := 'OT_AP_WO_OT_AR'; -- ����Ӧ��������Ӧ�գ����������չ�˾���ݶԳ塢���������ݶԳ塢��ת�����ݶԳ塢��������ݶԳ壩

	---========================== ƾ֤ҵ�񳡾� Ԥ�տ��˴��� ==================================
	DR_AA_CASH              VARCHAR2(30) := 'DR_AA_CASH'; -- Ԥ�տ��˴����ֽ�
	DR_AA_BANK              VARCHAR2(30) := 'DR_AA_BANK'; -- Ԥ�տ��˴�������
	DR_AA_WO_DEST_CR_POD    VARCHAR2(30) := 'DR_AA_WO_DEST_CR_POD'; -- Ԥ�տ��˴����Ӧ�յ����˷ѣ���ǩ�գ�
	DR_AA_WO_DEST_CR_NO_POD VARCHAR2(30) := 'DR_AA_WO_DEST_CR_NO_POD'; -- Ԥ�տ��˴����Ӧ�յ����˷ѣ�δǩ�գ�
	DR_AA_WO_OT_AR          VARCHAR2(30) := 'DR_AA_WO_OT_AR'; -- Ԥ�տ��˴��������Ӧ��
	DR_AA_WO_COD_AR_POD     VARCHAR2(30) := 'DR_AA_WO_COD_AR_POD'; -- Ԥ�տ��˴����Ӧ�մ��ջ����ǩ�գ�
	DR_AA_WO_COD_AR_NO_POD  VARCHAR2(30) := 'DR_AA_WO_COD_AR_NO_POD'; -- Ԥ�տ��˴����Ӧ�մ��ջ��δǩ�գ�
	DR_AA_CP                VARCHAR2(30) := 'DR_AA_CP'; -- ������Ԥ�ո�������

	---========================== ƾ֤ҵ�񳡾� �������� ==================================
	CLAIM_AIR_AP_WO_AR_WB   VARCHAR2(30) := 'CLAIM_AIR_AP_WO_AR_WB'; -- ͬһ���ţ�Ӧ�������˵���Ӧ�����˷ѻ������������ȡС�����ɿ��˲�������
	CLAIM_AIR_COST_AP       VARCHAR2(30) := 'CLAIM_AIR_COST_AP'; -- ͬһ���ţ�������-�˵����˷ѣ��ɿ��˲�������
	CLAIM_AIR_AP_WO_DEST_AR VARCHAR2(30) := 'CLAIM_AIR_AP_WO_DEST_AR'; -- �����Ӧ�յ����˷ѣ��ɿ��˲������루���������ⵥ�ź�Ӧ�յ�����ͬ��ͬ��������жԳ壩
	CLAIM_AIR_AP_WO_OT_AR   VARCHAR2(30) := 'CLAIM_AIR_AP_WO_OT_AR'; -- ���������Ӧ�գ��ɿ��˲�������
	CLAIM_AIR_CP            VARCHAR2(30) := 'CLAIM_AIR_CP'; -- �����Ӧ�ջ�����Ӧ�պ�Ӧ�������Դ�������ʱ���˲����������֧��

	---========================== ƾ֤ҵ�񳡾� Ԥ�� ==================================
	ADP_AIR_CO   VARCHAR2(30) := 'ADP_AIR_CO'; -- Ԥ�����չ�˾��
	ADP_WO_AP    VARCHAR2(30) := 'ADP_WO_AP'; -- Ԥ����Ӧ��
	ADP_WO_OT_AP VARCHAR2(30) := 'ADP_WO_OT_AP'; -- Ԥ��������Ӧ��

	---========================== ƾ֤ҵ�񳡾� ���˳�����Ӧ�� ==================================
	BA_WO_OT_AR VARCHAR2(30) := 'BA_WO_OT_AR'; -- ���˳�����Ӧ��

	------------------------------------------------------------------
	-- ���������־
	------------------------------------------------------------------
	PROCEDURE PROC_STL_ERROR_LOG(P_PERIOD           VARCHAR2, --����
															 P_BEGIN_TIME       DATE, --��ʼʱ��
															 P_END_TIME         DATE, --��ֹʱ��
															 P_OPERATE_CONTENT  VARCHAR2, --��������
															 P_NOTES            VARCHAR2, --��ע
															 P_CREATE_USER_CODE VARCHAR2 DEFAULT DEFAULT_USER_CODE, --������
															 P_SQL_ERR_CODE     VARCHAR2 DEFAULT SQLCODE, --������
															 P_SQL_ERR_MSG      VARCHAR2 DEFAULT SQLERRM, --������Ϣ
															 P_CREATE_TIME      DATE DEFAULT SYSDATE);

	---------------------------------------------------------------------
	--��ͼ����
	---------------------------------------------------------------------
	FUNCTION SET_POD_END_DATE(POD_DATE DATE) RETURN DATE;
	FUNCTION GET_POD_END_DATE RETURN DATE;

END PKG_STL_CASH_COMMON;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_CASH_COMMON IS

	V_POD_END_DATE DATE;

	------------------------------------------------------------------
	-- ���������־
	------------------------------------------------------------------
	PROCEDURE PROC_STL_ERROR_LOG(P_PERIOD           VARCHAR2, --����
															 P_BEGIN_TIME       DATE, --��ʼʱ��
															 P_END_TIME         DATE, --��ֹʱ��
															 P_OPERATE_CONTENT  VARCHAR2, --��������
															 P_NOTES            VARCHAR2, --��ע
															 P_CREATE_USER_CODE VARCHAR2 DEFAULT DEFAULT_USER_CODE, --������
															 P_SQL_ERR_CODE     VARCHAR2 DEFAULT SQLCODE, --������
															 P_SQL_ERR_MSG      VARCHAR2 DEFAULT SQLERRM, --������Ϣ
															 P_CREATE_TIME      DATE DEFAULT SYSDATE) AS
		PRAGMA AUTONOMOUS_TRANSACTION; -- ��������
		V_ERROR   VARCHAR2(500);
		V_CALLER  VARCHAR2(50);
		V_LINE_NO NUMBER(9);
		V_LPOS    NUMBER(9);
		V_RPOS    NUMBER(9);
	BEGIN
		V_ERROR  := DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;
		V_LPOS   := INSTR(V_ERROR, '"') + 1;
		V_RPOS   := INSTR(V_ERROR, '"', V_LPOS);
		V_CALLER := SUBSTR(V_ERROR, V_LPOS, V_RPOS - V_LPOS);
	
		V_LPOS    := INSTR(V_ERROR, 'line ') + 5;
		V_RPOS    := INSTR(V_ERROR, CHR(10));
		V_LINE_NO := TO_NUMBER(TRIM(SUBSTR(V_ERROR, V_LPOS, V_RPOS - V_LPOS)));
	
		--DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_STACK);
		--DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_CALL_STACK);
		--DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
	
		-- ������־
		INSERT INTO T_STL_ERROR_LOG
			(ID,
			 CREATE_TIME,
			 CREATE_USER_CODE,
			 OPERATE_TYPE,
			 OPERATE_CONTENT,
			 PERIOD,
			 BEGIN_TIME,
			 END_TIME,
			 SQL_ERR_CODE,
			 SQL_ERR_MSG,
			 SQL_LINE_NO,
			 NOTES)
		VALUES
			(SYS_GUID(),
			 P_CREATE_TIME,
			 P_CREATE_USER_CODE,
			 V_CALLER,
			 P_OPERATE_CONTENT,
			 P_PERIOD,
			 P_BEGIN_TIME,
			 P_END_TIME,
			 P_SQL_ERR_CODE,
			 P_SQL_ERR_MSG,
			 V_LINE_NO,
			 P_NOTES);
	
		COMMIT;
	END;

	FUNCTION SET_POD_END_DATE(POD_DATE DATE) RETURN DATE IS
	BEGIN
		V_POD_END_DATE := POD_DATE;
		RETURN V_POD_END_DATE;
	END;

	FUNCTION GET_POD_END_DATE RETURN DATE IS
	BEGIN
		RETURN V_POD_END_DATE;
	END;

END PKG_STL_CASH_COMMON;
/
