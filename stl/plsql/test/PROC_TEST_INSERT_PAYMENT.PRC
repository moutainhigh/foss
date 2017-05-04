CREATE OR REPLACE PROCEDURE PROC_TEST_INSERT_PAYMENT(
																											PAYMENT_NO       VARCHAR2,--�����
																											AMOUNT           NUMBER,--���
																											CUSTOMER_CODE    VARCHAR2,--�ͻ���������
																											CUSTOMER_NAME    VARCHAR2,--�ͻ���������
																											SOURCE_BILL_NO   VARCHAR2,--��Դ����
																											SOURCE_BILL_TYPE VARCHAR2,--��Դ��������
																											PAYMENT_ORG_CODE VARCHAR2,--����ű���
																											PAYMENT_ORG_NAME VARCHAR2,--���������
																											BUSINESS_DATE    DATE,--ҵ������
																											ACCOUNT_DATE     DATE,--��������
                                                      ACTIVE char,--�Ƿ���Ч
                                                      IS_RED_BACK char,--�Ƿ�쵥
                                                      PAYMENT_TYPE char,--���ʽ
                                                      BILL_TYPE VARCHAR2--��������
                                                      ) IS 
BEGIN

	INSERT INTO STL.T_STL_BILL_PAYMENT
		(ID,
		 PAYMENT_NO,
		 CURRENCY_CODE,
		 AMOUNT,
		 CUSTOMER_CODE,
		 CUSTOMER_NAME,
		 WORKFLOW_NO,
		 REMIT_STATUS,
		 AUDIT_STATUS,
		 SOURCE_BILL_NO,
		 SOURCE_BILL_TYPE,
		 PAYMENT_ORG_CODE,
		 PAYMENT_ORG_NAME,
		 PAYMENT_COMPANY_CODE,
		 PAYMENT_COMPANY_NAME,
		 ACTIVE,
		 IS_RED_BACK,
		 CREATE_USER_CODE,
		 CREATE_USER_NAME,
		 AUDIT_USER_CODE,
		 AUDIT_USER_NAME,
		 MODIFY_USER_CODE,
		 MODIFY_USER_NAME,
		 DISABLE_USER_CODE,
		 DISABLE_USER_NAME,
		 BUSINESS_DATE,
		 ACCOUNT_DATE,
		 CREATE_TIME,
		 MODIFY_TIME,
		 DISABLE_TIME,
		 BILL_TYPE,
		 IS_INIT,
		 PAYMENT_TYPE,
		 MOBILE_PHONE,
		 ACCOUNT_NO,
		 PAYEE_NAME,
		 ACCOUNT_TYPE,
		 PROVINCE_CODE,
		 CITY_CODE,
		 BANK_HQ_NAME,
		 BANK_BRANCH_NAME,
		 BANK_BRANCH_CODE,
		 AUDIT_OPINION,
		 DISABLE_OPINION,
		 APPLY_WORK_FLOW_NO,
		 NOTES,
		 VERSION_NO,
		 BANK_HQ_CODE,
		 PROVINCE_NAME,
		 CITY_NAME,
		 CREATE_TYPE,
		 BATCH_NO)
	VALUES
		(SYS_GUID(), --ID
		 PAYMENT_NO, --�����
		 'RMB',
		 AMOUNT, --���
		 CUSTOMER_CODE, --�ͻ�/��������
		 CUSTOMER_NAME, --�ͻ�/��������
		 '',
		 'NT',
		 'NA',
		 SOURCE_BILL_NO, --��Դ���ݺ�
		 SOURCE_BILL_TYPE, --��Դ��������
		 PAYMENT_ORG_CODE, --����ű���
		 PAYMENT_ORG_NAME, --���������
		 'TODO',
		 'TODO',
		 ACTIVE,--�Ƿ���Ч
		 IS_RED_BACK,--�Ƿ�쵥
		 '000000',
		 '��������Ա',
		 '',
		 '',
		 '000000',
		 '��������Ա',
		 '',
		 '',
		 BUSINESS_DATE, --ҵ������
		 ACCOUNT_DATE, --��������
		 SYSDATE,
		 SYSDATE,
		 NULL,
		 BILL_TYPE,--��������
		 'N',--�Ƿ��ʼ��
		 payment_type,--���ʽ
		 '',
		 '',
		 '',
		 'C',
		 '',
		 '',
		 '',
		 '',
		 '',
		 '',
		 '',
		 '',
		 '',
		 1,
		 '',
		 '',
		 '',
		 '',
		 '');
	COMMIT;

END PROC_TEST_INSERT_PAYMENT;
/
