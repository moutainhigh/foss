CREATE OR REPLACE PROCEDURE PROC_TEST_INSERT_WRITEOFF(WRITEOFF_BATCH_NO  VARCHAR2, --�������κ�
																											WRITEOFF_BILL_NO   IN VARCHAR2, --��������
																											AMOUNT             NUMBER, --�������
																											WRITEOFF_TYPE      VARCHAR2, --��������
																											BEGIN_WAYBILL_NO   VARCHAR2, --��ʼ��Դ�˵���
                                                      BEGIN_NO           VARCHAR2, --��ʼ����
																											BEGIN_ACCOUNT_DATE DATE, --��ʼ��������
                                                      END_WAYBILL_NO     VARCHAR2, --������Դ�˵���
																											END_NO             VARCHAR2, --��������
																											END_ACCOUNT_DATE   DATE, --������������
																											ACTIVE             CHAR, --�Ƿ���Ч
																											IS_RED_BACK        CHAR, --�Ƿ�쵥
																											WRITEOFF_TIME      DATE, --����ʱ��
																											ACCOUNT_DATE       DATE, --��������
																											CUSTOMER_CODE      VARCHAR2, --�ͻ�����
																											CUSTOMER_NAME      VARCHAR2, --�ͻ�����
																											ORG_CODE           VARCHAR2, --���ű���
																											ORG_NAME           VARCHAR2, --��������
																											VERSION_NO         NUMBER) IS
BEGIN
	INSERT INTO STL.T_STL_BILL_WRITEOFF
		(ID,
		 WRITEOFF_BATCH_NO,
		 WRITEOFF_BILL_NO,
		 BEGIN_WAYBILL_NO,
		 BEGIN_NO,
		 BEGIN_ACCOUNT_DATE,
		 END_WAYBILL_NO,
		 END_NO,
		 END_ACCOUNT_DATE,
		 STATEMENT_BILL_NO,
		 CREATE_TYPE,
		 WRITEOFF_TYPE,
		 ORG_CODE,
		 ORG_NAME,
		 CUSTOMER_CODE,
		 CUSTOMER_NAME,
		 AMOUNT,
		 ACTIVE,
		 IS_RED_BACK,
		 CREATE_USER_CODE,
		 CREATE_USER_NAME,
		 RED_IMPACT_USER_CODE,
		 RED_IMPACT_USER_NAME,
		 WRITEOFF_TIME,
		 ACCOUNT_DATE,
		 IS_INIT,
		 VERSION_NO)
	VALUES
		(SYS_GUID(), --ID
		 WRITEOFF_BATCH_NO, --�������κ�
		 WRITEOFF_BILL_NO, --��������
		 BEGIN_WAYBILL_NO, --��ʼ��Դ�˵���
		 BEGIN_NO, --Ӧ�յ���
		 BEGIN_ACCOUNT_DATE, --��ʼ�������� 
		 END_WAYBILL_NO, --�����˵���
		 END_NO, --������Դ����
		 END_ACCOUNT_DATE, --������Դ�˵���
		 '',
		 'M', --������ʽ
		 WRITEOFF_TYPE, --��������
		 ORG_CODE, --���ű���
		 ORG_NAME, --��������
		 CUSTOMER_CODE, --�ͻ�����
		 CUSTOMER_NAME, --�ͻ�����
		 AMOUNT, --���
		 active, --�Ƿ���Ч
		 IS_RED_BACK, --�Ƿ�쵥 
		 '000000',
		 '�ͻ�1',
		 '',
		 '',
		 WRITEOFF_TIME, --����ʱ��
		 ACCOUNT_DATE, --����ʱ��
		 'N',
		 VERSION_NO --�汾��
		 );
	COMMIT;

END PROC_TEST_INSERT_WRITEOFF;
/
