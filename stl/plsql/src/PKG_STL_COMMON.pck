CREATE OR REPLACE PACKAGE PKG_STL_COMMON IS
  -- Author  : ZHUWEI
  -- Created : 2012-11-19 11:06:22
  -- Purpose : �����Լ�ͨ�ú���

  --========================== ͨ�ó������� ================================
  MAX_PAGE_ROW_SIZE NUMBER := 1000; --���ҳ�����������������룬����ÿ�β���������
  DEFAULT_USER_CODE VARCHAR2(20) := 'STL'; --�����̨�û�����
  DEFAULT_BILL_NO   VARCHAR2(20) := 'N/A'; -- Ĭ�ϵ���

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
  * CURRENCY_CODE ���ұ���
  */
  CURRENCY_CODE_RMB VARCHAR2(20) := 'RMB'; --�����

  /**
  * ��ֵ������ո�
  */
  NULL_VALUE VARCHAR2(20) := 'N/A';

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

  /**
  * BILL_PARENT_TYPE ��������
  */
  BILL_PARENT_TYPE__XS VARCHAR2(20) := 'XS'; -- �ֽ��տ
  BILL_PARENT_TYPE__YS VARCHAR2(20) := 'YS'; -- Ӧ�յ�
  BILL_PARENT_TYPE__YF VARCHAR2(20) := 'YF'; -- Ӧ����
  BILL_PARENT_TYPE__HK VARCHAR2(20) := 'HK'; -- ���
  BILL_PARENT_TYPE__US VARCHAR2(20) := 'US'; -- Ԥ�յ�
  BILL_PARENT_TYPE__UF VARCHAR2(20) := 'UF'; -- Ԥ����
  BILL_PARENT_TYPE__DZ VARCHAR2(20) := 'DZ'; -- ���˵�
  BILL_PARENT_TYPE__FK VARCHAR2(20) := 'FK'; -- ���
  BILL_PARENT_TYPE__XP VARCHAR2(20) := 'XP'; -- СƱ��
  BILL_PARENT_TYPE__HZ VARCHAR2(20) := 'HZ'; -- ���˵�

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
  RECEIVABLE_BILL_TYPE_AA      VARCHAR2(20) := 'AA'; -- ���˵������Ӧ��
  RECEIVABLE_BILL_TYPE_AAC     VARCHAR2(20) := 'AAC'; --  ���˴�����ջ���Ӧ��
  RECEIVABLE_BILL_TYPE_DL    VARCHAR2(20) := 'DL'; -- ������������Ӧ��
  RECEIVABLE_BILL_TYPE_DLC   VARCHAR2(20) := 'DLC'; -- ����������ջ���Ӧ�յ�
  RECEIVABLE_BILL_TYPE_LR    VARCHAR2(20) := 'LR'; -- ���������Ӧ��
  /**
  * SOURCE_BILL_TYPE ��Դ����������
  */
  RECEIVABLE_SOURCE_BILL_TYPE_W VARCHAR2(20) := 'W'; -- �˵�
  RECEIVABLE_SOURCE_BILL_TYPE_R VARCHAR2(20) := 'R'; -- СƱ
  RECEIVABLE_SOURCE_BILL_TYPE_M VARCHAR2(20) := 'M'; -- �˹�¼��
  RECEIVABLE_SOURCE_BILL_TYPE_E VARCHAR2(20) := 'E'; -- �쳣����
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
  RECEIVABLE_COLLECTION_TYPE_W   VARCHAR2(20) := 'W'; -- �ִ���
  RECEIVABLE_COLLECTION_TYPE_PD  VARCHAR2(20) := 'PD'; -- ���������
  RECEIVABLE_COLLECTION_TYPE_D   VARCHAR2(20) := 'D'; -- �����ͻ���
  RECEIVABLE_COLLECTION_TYPE_C   VARCHAR2(20) := 'C'; -- ��Ա����
  RECEIVABLE_COLLECTION_TYPE_P   VARCHAR2(20) := 'P'; -- ��װ��
  RECEIVABLE_COLLECTION_TYPE_F   VARCHAR2(20) := 'F'; -- �ſշ�
  RECEIVABLE_COLLECTION_TYPE_R   VARCHAR2(20) := 'R'; -- ����Ʒ
  RECEIVABLE_COLLECTION_TYPE_E   VARCHAR2(20) := 'E'; -- ����Ԥ�տ�
  RECEIVABLE_COLLECTION_TYPE_RFC VARCHAR2(20) := 'RFC'; -- ���ķ�
  RECEIVABLE_COLLECTION_TYPE_DU  VARCHAR2(20) := 'DU'; -- �ͻ���¥��
  RECEIVABLE_COLLECTION_TYPE_POS VARCHAR2(20) := 'POS'; -- POS��������
  RECEIVABLE_COLLECTION_TYPE_O   VARCHAR2(20) := 'O'; -- ����
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
  PAYABLE_BILL_TYPE_AIR_ORIGINAL VARCHAR2(20) := 'AAO'; -- ���˳�������Ӧ��
  PAYABLE_BILL_TYPE_AIR_DELIVERY VARCHAR2(20) := 'AAD'; -- ���˵������Ӧ��
  PAYABLE_BILL_TYPE_AIR_OTHER    VARCHAR2(20) := 'AO'; -- ��������Ӧ��
  PAYABLE_BILL_TYPE_PARTIAL_LINE VARCHAR2(20) := 'PL'; -- ƫ�ߴ���ɱ�
  PAYABLE_BILL_TYPE_SERVICE_FEE  VARCHAR2(20) := 'SF'; -- �����Ӧ��
  PAYABLE_BILL_TYPE_CLAIM        VARCHAR2(20) := 'C'; -- ����Ӧ��
  PAYABLE_BILL_TYPE_REFUND       VARCHAR2(20) := 'R'; -- ���˷�Ӧ��
  PAYABLE_BILL_TYPE_COMPENSATION VARCHAR2(20) := 'CP'; -- ���񲹾�Ӧ��
  PAYABLE_BILL_TYPE_LANDSTOWAGE  VARCHAR2(20) := 'LD'; -- ������ⷢ�ɱ�
  PAYABLE_BILLTYPE_LAND_OTHER VARCHAR2(20) := 'LDO'; -- ���������Ӧ��
  /**
  * SOURCE_BILL_TYPE ��Դ����������
  */
  PAYABLE_SOURCE_BILL_TYPE_W  VARCHAR2(20) := 'W'; -- �˵�
  PAYABLE_SOURCE_BILL_TYPE_RS VARCHAR2(20) := 'RS'; -- �������õ�
  PAYABLE_SOURCE_BILL_TYPE_AW VARCHAR2(20) := 'AW'; -- ��������
  PAYABLE_SOURCE_BILL_TYPE_TP VARCHAR2(20) := 'TP'; -- ��ת����嵥
  PAYABLE_SOURCE_BILL_TYPE_AP VARCHAR2(20) := 'AP'; -- �ϴ�Ʊ�嵥
  PAYABLE_SOURCE_BILL_TYPE_PL VARCHAR2(20) := 'PL'; -- �ⷢ��
  PAYABLE_SOURCE_BILL_TYPE_E  VARCHAR2(20) := 'E'; -- �쳣����
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
  DEPOSIT_RCVD_STATUS_SUBMIT  VARCHAR2(20) := 'S'; -- ���ύ
  DEPOSIT_RCVD_STATUS_CONFIRM VARCHAR2(20) := 'C'; -- ����ȷ��
  DEPOSIT_RCVD_STATUS_SC      VARCHAR2(20) := 'SC'; -- �ͻ����˵�ȷ��
  /**
  * ��������
  */
  DEPOSIT_RCVD__TRANS_TYPE__LC VARCHAR2(20) := 'LC'; -- ר�߿ͻ�
  DEPOSIT_RCVD__TRANS_TYPE__PA VARCHAR2(20) := 'PA'; -- ƫ�ߴ���
  DEPOSIT_RCVD__TRANS_TYPE__AA VARCHAR2(20) := 'AA'; -- ���˴���
  DEPOSIT_RCVD__TRANS_TYPE__LS VARCHAR2(20) := 'LS'; -- ��������

  -- ==================== Ԥ���� BILL_ADVANCED_PAYMENT ====================
  /**
  * BILL_TYPE Ԥ��������������
  */
  ADVANCED_PAYMENT_BILL_TYPE_AIR VARCHAR2(20) := 'A'; --����

  /**
  * AUDIT_STATUS ����״̬
  */
  ADVANCED_PAY_AUDIT_STATUS_NA VARCHAR2(20) := 'NA'; -- δ����
  ADVANCED_PAY_AUDIT_STATUS_AG VARCHAR2(20) := 'AG'; -- ������
  ADVANCED_PAY_AUDIT_STATUS_AA VARCHAR2(20) := 'AA'; -- ����ͨ��
  ADVANCED_PAY_AUDIT_STATUS_AD VARCHAR2(20) := 'AD'; -- ������ͨ��

  -- ==================== ��� BILL_REPAYMENT ====================
  /**
  * BILL_TYPE ��������
  */
  REPAYMENT_BILL_TYPE_REPAYMENT VARCHAR2(20) := 'HK'; -- ����
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
  * SOURCE_BILL_TYPE ��Դ��������
  */
  REPAYMENT_SOURCE_BILL_TYPE_W   VARCHAR2(20) := 'W'; -- �˷�
  REPAYMENT_SOURCE_BILL_TYPE_FC  VARCHAR2(20) := 'FC'; -- ������
  REPAYMENT_SOURCE_BILL_TYPE_COD VARCHAR2(20) := 'COD'; -- ���ջ���
  REPAYMENT_SOURCE_BILL_TYPE_R   VARCHAR2(20) := 'R'; -- СƱ
  REPAYMENT_SOURCE_BILL_TYPE_PL  VARCHAR2(20) := 'PL'; -- �ⷢ��

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
  COD_COD_TYPE_RETURN_1_DAY     VARCHAR2(20) := 'R1'; -- ������
  COD_COD_TYPE_RETURN_3_DAY     VARCHAR2(20) := 'R3'; -- ������
  COD_COD_TYPE_RETURN_APPROVE   VARCHAR2(20) := 'RA'; -- �����
  COD_COD_TYPE_RETURN_3_APPROVE VARCHAR2(20) := 'R3RA'; -- �����˺������
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
  --POD__POD_TYPE__BILLING VARCHAR2(20) := 'BILL'; -- ����
  POD__POD_TYPE__POD VARCHAR2(20) := 'POD'; -- ǩ��
  POD__POD_TYPE__UPD VARCHAR2(20) := 'UPD'; -- ��ǩ��

  -- ==================== �쳣���� OUT_STOCK_EXCEPTION ====================
  /**
  * EXCEPTION_TYPE �쳣����
  */
  OUT_STOCK_EXCEPTION_TYPE_LG VARCHAR2(20) := 'LG'; -- ����
  OUT_STOCK_EXCEPTION_TYPE_GG VARCHAR2(20) := 'GG'; -- ����
  OUT_STOCK_EXCEPTION_TYPE_CG VARCHAR2(20) := 'CG'; -- Υ��Ʒ

  /**
  * BILL_TYPE ���˵���������
  */
  BAD_ACCOUNT_BILL_TYPE_BADDEDTS VARCHAR2(20) := 'BADDEBTS'; --������ʧ
  BAD_ACCOUNT_BILL_TYPE_INCOME   VARCHAR2(20) := 'INCOME'; --��������

  /**
  * PRODUCT_CODE ��Ʒ����
  */
  PRODUCT_CODE_FLF VARCHAR2(20) := 'FLF'; --��׼����
  PRODUCT_CODE_FSF VARCHAR2(20) := 'FSF'; --��׼����
  PRODUCT_CODE_LRF VARCHAR2(20) := 'LRF'; --��׼����(��;)
  PRODUCT_CODE_SRF VARCHAR2(20) := 'SRF'; --��׼����(��;)
  PRODUCT_CODE_WVH VARCHAR2(20) := 'WVH'; --������������
  PRODUCT_CODE_AF  VARCHAR2(20) := 'AF'; --��׼����
  PRODUCT_CODE_PLF VARCHAR2(20) := 'PLF'; --����ƫ��
  PRODUCT_CODE_PKG VARCHAR2(20) := 'PACKAGE'; --��ݰ���

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

  ------------------------------------------------------------------
  -- ͨ����֯�����ȡ��֯��˱��루���棩
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_ORG_UNIFIED_CODE(P_ORG_CODE VARCHAR2) RETURN VARCHAR2 RESULT_CACHE;

  ------------------------------------------------------------------
  -- ͨ����֯�����ȡ��֯���ƣ����棩
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_ORG_NAME(P_ORG_CODE VARCHAR2) RETURN VARCHAR2 RESULT_CACHE;

  ------------------------------------------------------------------
  -- ͨ���ͻ������ȡ�ͻ����ƣ����棩
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_CUSTOMER_NAME(P_CUST_CODE VARCHAR2) RETURN VARCHAR2 RESULT_CACHE;

  ------------------------------------------------------------------
  -- �����ȡ��(���ֻ���)
  ------------------------------------------------------------------
  FUNCTION FUNC_AMOUNT_SPLIT_PART(P_FEE           NUMBER,
                                  P_VERIFY_AMOUNT NUMBER,
                                  P_AMOUNT        NUMBER) RETURN NUMBER;

  ------------------------------------------------------------------
  -- �����ȡ��(ȫ������)
  ------------------------------------------------------------------
  FUNCTION FUNC_AMOUNT_SPLIT_ALL(P_FEE           NUMBER,
                                 P_VERIFY_AMOUNT NUMBER,
                                 P_AMOUNT        NUMBER) RETURN NUMBER;

END PKG_STL_COMMON;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_COMMON IS

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

  ------------------------------------------------------------------
  -- ͨ����֯�����ȡ��֯��˱��루���棩
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_ORG_UNIFIED_CODE(P_ORG_CODE VARCHAR2) RETURN VARCHAR2 RESULT_CACHE IS
    V_UNIFIED_CODE BSE.T_BAS_ORG.UNIFIED_CODE%TYPE;
    V_COUNT        NUMBER;
  BEGIN
    SELECT COUNT(T.ID)
      INTO V_COUNT
      FROM BSE.T_BAS_ORG T
     WHERE T.ACTIVE = PKG_STL_COMMON.ACTIVE
           AND T.CODE = P_ORG_CODE;

    IF V_COUNT > 0 THEN
      SELECT T.UNIFIED_CODE
        INTO V_UNIFIED_CODE
        FROM BSE.T_BAS_ORG T
       WHERE T.ACTIVE = PKG_STL_COMMON.ACTIVE
             AND T.CODE = P_ORG_CODE;
    ELSE
      V_UNIFIED_CODE := '';
    END IF;

    RETURN V_UNIFIED_CODE;
  END;
  ------------------------------------------------------------------
  -- ͨ����֯�����ȡ��֯���ƣ����棩
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_ORG_NAME(P_ORG_CODE VARCHAR2) RETURN VARCHAR2 RESULT_CACHE IS
    V_ORG_NAME BSE.T_BAS_ORG.NAME%TYPE;
    V_COUNT    NUMBER;
  BEGIN

    SELECT COUNT(T.ID)
      INTO V_COUNT
      FROM BSE.T_BAS_ORG T
     WHERE T.ACTIVE = PKG_STL_COMMON.ACTIVE
           AND T.CODE = P_ORG_CODE;

    IF V_COUNT > 0 THEN
      SELECT T.NAME
        INTO V_ORG_NAME
        FROM BSE.T_BAS_ORG T
       WHERE T.ACTIVE = PKG_STL_COMMON.ACTIVE
             AND T.CODE = P_ORG_CODE;
    ELSE
      V_ORG_NAME := '';
    END IF;

    RETURN V_ORG_NAME;
  END;

  ------------------------------------------------------------------
  -- ͨ���ͻ������ȡ�ͻ�(ɢ��)���ƣ����棩
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_CUSTOMER_NAME(P_CUST_CODE VARCHAR2) RETURN VARCHAR2 RESULT_CACHE IS
    V_CUST_NAME BSE.T_BAS_CUSTOMER.NAME%TYPE;
    V_COUNT     NUMBER;
  BEGIN
    SELECT COUNT(T.ID)
      INTO V_COUNT
      FROM BSE.T_BAS_CUSTOMER T
     WHERE T.ACTIVE = PKG_STL_COMMON.ACTIVE
           AND T.CODE = P_CUST_CODE;

    IF V_COUNT > 0 THEN
      SELECT T.NAME
        INTO V_CUST_NAME
        FROM BSE.T_BAS_CUSTOMER T
       WHERE T.ACTIVE = PKG_STL_COMMON.ACTIVE
             AND T.CODE = P_CUST_CODE;
    ELSE
      V_CUST_NAME := '';
      /*SELECT COUNT(NT.ID)
        INTO V_COUNT
        FROM BSE.T_BAS_NONFIXED_CUSTOMER NT
       WHERE NT.ACTIVE = PKG_STL_COMMON.ACTIVE
             AND NT.CUSTCODE = P_CUST_CODE;
      IF V_COUNT > 0 THEN
        SELECT NT.CUSTNAME
          INTO V_CUST_NAME
          FROM BSE.T_BAS_NONFIXED_CUSTOMER NT
         WHERE NT.ACTIVE = PKG_STL_COMMON.ACTIVE
               AND NT.CUSTCODE = P_CUST_CODE;
      ELSE
        V_CUST_NAME := '';
      END IF;*/
    END IF;

    RETURN V_CUST_NAME;
  END;

  ------------------------------------------------------------------
  -- �����ȡ��(���ֻ���)
  ------------------------------------------------------------------
  FUNCTION FUNC_AMOUNT_SPLIT_PART(P_FEE           NUMBER, --����
                                  P_VERIFY_AMOUNT NUMBER, --���λ�����
                                  P_AMOUNT        NUMBER --�ܽ��
                                  ) RETURN NUMBER IS
    V_PRECISION NUMBER := 100; --��ȷ��100
  BEGIN
    RETURN ROUND(P_FEE * P_VERIFY_AMOUNT / (P_AMOUNT * V_PRECISION)) * V_PRECISION;
  END;

  ------------------------------------------------------------------
  -- �����ȡ��(ȫ������)
  ------------------------------------------------------------------
  FUNCTION FUNC_AMOUNT_SPLIT_ALL(P_FEE           NUMBER, --����
                                 P_VERIFY_AMOUNT NUMBER, --�ۼ��ѻ����
                                 P_AMOUNT        NUMBER --�ܽ��
                                 ) RETURN NUMBER IS
    V_PRECISION NUMBER := 100; --��ȷ��100
  BEGIN
    RETURN P_FEE - ROUND(P_VERIFY_AMOUNT * P_FEE /
                         (P_AMOUNT * V_PRECISION)) * V_PRECISION;
  END;

END PKG_STL_COMMON;
/
