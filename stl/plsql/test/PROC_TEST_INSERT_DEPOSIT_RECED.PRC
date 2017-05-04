CREATE OR REPLACE PROCEDURE PROC_TEST_INSERT_DEPOSIT_RECED(P_DEPOSIT_RECEIVED_NO     VARCHAR2, --  Ԥ�յ���
																															P_CUSTOMER_CODE           VARCHAR2, --  �ͻ�����
																															P_CUSTOMER_NAME           VARCHAR2, --  �ͻ�����
																															P_AMOUNT                  NUMBER, --  ���
																															P_VERIFY_AMOUNT           NUMBER, --  �Ѻ������
																															P_UNVERIFY_AMOUNT         NUMBER, --  δ�������
																															P_CREATE_ORG_CODE         VARCHAR2, --  ¼�벿�ű���
																															P_CREATE_ORG_NAME         VARCHAR2, --  ¼�벿������
																															P_COLLECTION_ORG_CODE     VARCHAR2, --  �տ�ű���
																															P_COLLECTION_ORG_NAME     VARCHAR2, --  �տ������
																															P_COLLECTION_COMPANY_CODE VARCHAR2, --  �տ�������ӹ�˾����
																															P_COLLECTION_COMPANY_NAME VARCHAR2, --  �տ�������ӹ�˾����
																															P_GENERATING_ORG_CODE     VARCHAR2, --  ���벿�ű���
																															P_GENERATING_ORG_NAME     VARCHAR2, --  ���벿������
																															P_GENERATING_COMPANY_CODE VARCHAR2, --  ���벿�������ӹ�˾����
																															P_GENERATING_COMPANY_NAME VARCHAR2, --  ���벿�������ӹ�˾����
																															P_WORKFLOW_NO             VARCHAR2, --  ��������
																															P_REFUND_STATUS           VARCHAR2, --  �˿�״̬
																															P_REMIT_NO                VARCHAR2, --  �����
																															P_REMITER_NAME            VARCHAR2, --  ���������
																															P_REMIT_ORG_CODE          VARCHAR2, --  ����������ű���
																															P_REMIT_ORG_NAME          VARCHAR2, --  ���������������
																															P_BILL_TYPE               VARCHAR2, --  ��������
																															P_STATUS                  VARCHAR2, --  ����״̬
																															P_ACTIVE                  CHAR, --  �Ƿ���Ч
																															P_IS_RED_BACK             CHAR, --  �Ƿ�쵥
																															P_PAYMENT_TYPE            VARCHAR2, --  ֧����ʽ
																															P_BUSINESS_DATE           DATE, --  ҵ������
																															P_ACCOUNT_DATE            DATE, --  ��������
																															P_CREATE_TIME             DATE, --  ����ʱ��
																															P_CASH_CONFIRM_TIME       DATE, --  ����ȷ��ʱ��
																															P_IS_INIT                 CHAR, --  �Ƿ��ʼ��
																															P_STATEMENT_BILL_NO       VARCHAR2, --  ���˵���
																															P_PAYMENT_AMOUNT          NUMBER, --  ������
																															P_TRANSPORT_TYPE          VARCHAR2, --  ��������
																															P_PAYMENT_NO              VARCHAR2 --  �����
																															) IS
BEGIN

	INSERT INTO STL.T_STL_BILL_DEPOSIT_RECEIVED
		(ID, --  ID
		 DEPOSIT_RECEIVED_NO, --  Ԥ�յ���
		 CUSTOMER_CODE, --  �ͻ�����
		 CUSTOMER_NAME, --  �ͻ�����
		 CURRENCY_CODE, --  ����
		 AMOUNT, --  ���
		 VERIFY_AMOUNT, --  �Ѻ������
		 UNVERIFY_AMOUNT, --  δ�������
		 CREATE_ORG_CODE, --  ¼�벿�ű���
		 CREATE_ORG_NAME, --  ¼�벿������
		 COLLECTION_ORG_CODE, --  �տ�ű���
		 COLLECTION_ORG_NAME, --  �տ������
		 COLLECTION_COMPANY_CODE, --  �տ�������ӹ�˾����
		 COLLECTION_COMPANY_NAME, --  �տ�������ӹ�˾����
		 GENERATING_ORG_CODE, --  ���벿�ű���
		 GENERATING_ORG_NAME, --  ���벿������
		 GENERATING_COMPANY_CODE, --  ���벿�������ӹ�˾����
		 GENERATING_COMPANY_NAME, --  ���벿�������ӹ�˾����
		 WORKFLOW_NO, --  ��������
		 REFUND_STATUS, --  �˿�״̬
		 REMIT_NO, --  �����
		 REMITER_NAME, --  ���������
		 REMIT_ORG_CODE, --  ����������ű���
		 REMIT_ORG_NAME, --  ���������������
		 BILL_TYPE, --  ��������
		 STATUS, --  ����״̬
		 ACTIVE, --  �Ƿ���Ч
		 IS_RED_BACK, --  �Ƿ�쵥
		 PAYMENT_TYPE, --  ֧����ʽ
		 CREATE_USER_CODE, --  �Ƶ��˹���
		 CREATE_USER_NAME, --  �Ƶ�������
		 AUDIT_USER_CODE, --  ����˹���
		 AUDIT_USER_NAME, --  ���������
		 MODIFY_USER_CODE, --  �޸��˹���
		 MODIFY_USER_NAME, --  �޸�������
		 DISABLE_USER_CODE, --  �����˹���
		 DISABLE_USER_NAME, --  ����������
		 CASH_CONFIRM_USER_CODE, --  ����ȷ���˹���
		 CASH_CONFIRM_USER_NAME, --  ����ȷ��������
		 BUSINESS_DATE, --  ҵ������
		 ACCOUNT_DATE, --  ��������
		 CREATE_TIME, --  ����ʱ��
		 MODIFY_TIME, --  �޸�ʱ��
		 DISABLE_TIME, --  ����ʱ��
		 CASH_CONFIRM_TIME, --  ����ȷ��ʱ��
		 IS_INIT, --  �Ƿ��ʼ��
		 STATEMENT_BILL_NO, --  ���˵���
		 NOTES, --  ��ע
		 VERSION_NO, --  �汾��
		 PAYMENT_AMOUNT, --  ������
		 PAYMENT_NOTES, --  ���ע
		 TRANSPORT_TYPE, --  ��������
		 PAYMENT_NO --  �����

		 )
	VALUES
		(SYS_GUID(), --  ID
		 P_DEPOSIT_RECEIVED_NO, --  Ԥ�յ���
		 P_CUSTOMER_CODE, --  �ͻ�����
		 P_CUSTOMER_NAME, --  �ͻ�����
		 'RMB', --  ����
		 P_AMOUNT, --  ���
		 P_VERIFY_AMOUNT, --  �Ѻ������
		 P_UNVERIFY_AMOUNT, --  δ�������
		 P_CREATE_ORG_CODE, --  ¼�벿�ű���
		 P_CREATE_ORG_NAME, --  ¼�벿������
		 P_COLLECTION_ORG_CODE, --  �տ�ű���
		 P_COLLECTION_ORG_NAME, --  �տ������
		 P_COLLECTION_COMPANY_CODE, --  �տ�������ӹ�˾����
		 P_COLLECTION_COMPANY_NAME, --  �տ�������ӹ�˾����
		 P_GENERATING_ORG_CODE, --  ���벿�ű���
		 P_GENERATING_ORG_NAME, --  ���벿������
		 P_GENERATING_COMPANY_CODE, --  ���벿�������ӹ�˾����
		 P_GENERATING_COMPANY_NAME, --  ���벿�������ӹ�˾����
		 P_WORKFLOW_NO, --  ��������
		 P_REFUND_STATUS, --  �˿�״̬
		 P_REMIT_NO, --  �����
		 P_REMITER_NAME, --  ���������
		 P_REMIT_ORG_CODE, --  ����������ű���
		 P_REMIT_ORG_NAME, --  ���������������
		 P_BILL_TYPE, --  ��������
		 P_STATUS, --  ����״̬
		 P_ACTIVE, --  �Ƿ���Ч
		 P_IS_RED_BACK, --  �Ƿ�쵥
		 P_PAYMENT_TYPE, --  ֧����ʽ
		 '000123', --  �Ƶ��˹���
		 '����', --  �Ƶ�������
		 '000123', --  ����˹���
		 '����', --  ���������
		 NULL, --  �޸��˹���
		 NULL, --  �޸�������
		 NULL, --  �����˹���
		 NULL, --  ����������
		 NULL, --  ����ȷ���˹���
		 NULL, --  ����ȷ��������
		 P_BUSINESS_DATE, --  ҵ������
		 P_ACCOUNT_DATE, --  ��������
		 P_CREATE_TIME, --  ����ʱ��
		 NULL, --  �޸�ʱ��
		 NULL, --  ����ʱ��
		 P_CASH_CONFIRM_TIME, --  ����ȷ��ʱ��
		 P_IS_INIT, --  �Ƿ��ʼ��
		 P_STATEMENT_BILL_NO, --  ���˵���
		 '', --  ��ע
		 1, --  �汾��
		 P_PAYMENT_AMOUNT, --  ������
		 '', --  ���ע
		 P_TRANSPORT_TYPE, --  ��������
		 P_PAYMENT_NO --  �����
		 );
	COMMIT;

END PROC_TEST_INSERT_DEPOSIT_RECED;
/
