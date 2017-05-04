CREATE OR REPLACE PROCEDURE PROC_TEST_INSERT_BAD_ACCOUNT(P_BAD_DEBAT_BILL_NO varchar2, --  ���˵����
																												 P_APPLY_ORG_CODE varchar2, --  ���벿�ű���
																												 P_APPLY_ORG_NAME varchar2, --  ���벿������
																												 P_APPLY_USER_CODE varchar2, --  �����˹���
																												 P_APPLY_USER_NAME varchar2, --  ����������
																												 P_ERROR_HANDING_NO varchar2, --  ����������
																												 P_RECEIVABLE_NO varchar2, --  Ӧ�յ���
																												 P_WAYBILL_NO varchar2, --  �˵���
																												 P_SOURCE_BILL_TYPE varchar2, --  ��Դ��������
																												 P_ORIG_ORG_CODE varchar2, --  �������ű���
																												 P_ORIG_ORG_NAME varchar2, --  ������������
																												 P_DEST_ORG_CODE varchar2, --  �ջ����ű���
																												 P_DEST_ORG_NAME varchar2, --  �ջ���������
																												 P_FCUSTOMER_CODE varchar2, --  �����ͻ�����
																												 P_FCUSTOMER_NAME varchar2, --  �����ͻ�����
																												 P_ARRV_REGION_CODE varchar2, --  Ŀ��վ
																												 P_RECEIVE_CUSTOMER_CODE varchar2, --  �ջ��ͻ�����
																												 P_RECEIVE_CUSTOMER_NAME varchar2, --  �ջ��ͻ�����
																												 P_TOTAL_FEE number, --  ���˷�
																												 P_PAYMENT_TYPE varchar2, --  �տʽ
																												 P_BAD_AMOUNT number, --  ���˽��
																												 P_AUDIT_STATUS varchar2, --  ���״̬
																												 P_VERIFY_STATUS CHAR --  �Ƿ����
																												 ) IS
BEGIN

	INSERT INTO STL.T_STL_BILL_BAD_ACCOUNT
		(ID, --  ID
		 BAD_DEBAT_BILL_NO, --  ���˵����
		 APPLY_ORG_CODE, --  ���벿�ű���
		 APPLY_ORG_NAME, --  ���벿������
		 APPLY_USER_CODE, --  �����˹���
		 APPLY_USER_NAME, --  ����������
		 ERROR_HANDING_NO, --  ����������
		 APPLY_REASON, --  ��������
		 RECEIVABLE_NO, --  Ӧ�յ���
		 WAYBILL_NO, --  �˵���
		 SOURCE_BILL_TYPE, --  ��Դ��������
		 ORIG_ORG_CODE, --  �������ű���
		 ORIG_ORG_NAME, --  ������������
		 DEST_ORG_CODE, --  �ջ����ű���
		 DEST_ORG_NAME, --  �ջ���������
		 FCUSTOMER_CODE, --  �����ͻ�����
		 FCUSTOMER_NAME, --  �����ͻ�����
		 DELIVERY_CUSTOMER_PHONE, --  �����ͻ��绰
		 ARRV_REGION_CODE, --  Ŀ��վ
		 RECEIVE_CUSTOMER_CODE, --  �ջ��ͻ�����
		 RECEIVE_CUSTOMER_NAME, --  �ջ��ͻ�����
		 TOTAL_FEE, --  ���˷�
		 GOODS_NAME, --  ��������
		 PAYMENT_TYPE, --  �տʽ
		 BAD_AMOUNT, --  ���˽��
		 AUDIT_STATUS, --  ���״̬
		 WORKFLOW_NO, --  ��������
		 CREATE_ORG_CODE, --  �������ű���
		 CREATE_ORG_NAME, --  ������������
		 CREATE_USER_CODE, --  �����˹���
		 CREATE_USER_NAME, --  ����������
		 VERIFY_STATUS, --  �Ƿ����
		 CREATE_TIME, --  ����ʱ��
		 NOTES, --  ��ע
		 CURRENCY_CODE --  ����

		 )
	VALUES
		(SYS_GUID(), --  ID
		 P_BAD_DEBAT_BILL_NO, --  ���˵����
		 P_APPLY_ORG_CODE, --  ���벿�ű���
		 P_APPLY_ORG_NAME, --  ���벿������
		 P_APPLY_USER_CODE, --  �����˹���
		 P_APPLY_USER_NAME, --  ����������
		 P_ERROR_HANDING_NO, --  ����������
		 '', --  ��������
		 P_RECEIVABLE_NO, --  Ӧ�յ���
		 P_WAYBILL_NO, --  �˵���
		 P_SOURCE_BILL_TYPE, --  ��Դ��������
		 P_ORIG_ORG_CODE, --  �������ű���
		 P_ORIG_ORG_NAME, --  ������������
		 P_DEST_ORG_CODE, --  �ջ����ű���
		 P_DEST_ORG_NAME, --  �ջ���������
		 P_FCUSTOMER_CODE, --  �����ͻ�����
		 P_FCUSTOMER_NAME, --  �����ͻ�����
		 '1596284077', --  �����ͻ��绰
		 P_ARRV_REGION_CODE, --  Ŀ��վ
		 P_RECEIVE_CUSTOMER_CODE, --  �ջ��ͻ�����
		 P_RECEIVE_CUSTOMER_NAME, --  �ջ��ͻ�����
		 P_TOTAL_FEE, --  ���˷�
		 'GOODS_NAME', --  ��������
		 P_PAYMENT_TYPE, --  �տʽ
		 P_BAD_AMOUNT, --  ���˽��
		 P_AUDIT_STATUS, --  ���״̬
		 '', --  ��������
		 '', --  �������ű���
		 '', --  ������������
		 '000000', --  �����˹���
		 '��������Ա', --  ����������
		 P_VERIFY_STATUS, --  �Ƿ����
		 SYSDATE, --  ����ʱ��
		 '', --  ��ע
		 'RMB' --  ����

		 );
	COMMIT;

END PROC_TEST_INSERT_BAD_ACCOUNT;
/
