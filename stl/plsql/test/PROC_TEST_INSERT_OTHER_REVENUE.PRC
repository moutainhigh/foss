CREATE OR REPLACE PROCEDURE PROC_TEST_INSERT_OTHER_REVENUE(P_OTHER_REVENUE_NO    VARCHAR2, --  СƱ����
																													 P_WAYBILL_NO          VARCHAR2, --  �˵���
																													 P_GENERATING_ORG_CODE VARCHAR2, --  ���벿�ű���
																													 P_GENERATING_ORG_NAME VARCHAR2, --  ���벿������
																													 P_GENERATING_COM_CODE VARCHAR2, --  �����ӹ�˾����
																													 P_GENERATING_COM_NAME VARCHAR2, --  �����ӹ�˾����
																													 P_CUSTOMER_CODE       VARCHAR2, --  �ͻ����
																													 P_CUSTOMER_NAME       VARCHAR2, --  �ͻ�����
																													 P_CUSTOMER_TYPE       VARCHAR2, --  �ͻ�����
																													 P_PAYMENT_TYPE        VARCHAR2, --  �տʽ
																													 P_INCOME_CATEGORIES   VARCHAR2, --  �������
																													 P_AMOUNT              NUMBER default 10000, --  ���
																													 P_BUSINESS_DATE       DATE, --  ҵ������
																													 P_ACTIVE              CHAR, --  �Ƿ���Ч
																													 P_IS_RED_BACK         CHAR, --  �Ƿ�쵥
																													 P_IS_DISABLE        CHAR --  �Ƿ�����
																													 ) IS
BEGIN

	INSERT INTO STL.T_STL_OTHER_REVENUE
		(ID, --  ID
		 OTHER_REVENUE_NO, --  СƱ����
		 WAYBILL_NO, --  �˵���
		 GENERATING_ORG_CODE, --  ���벿�ű���
		 GENERATING_ORG_NAME, --  ���벿������
		 GENERATING_COM_CODE, --  �����ӹ�˾����
		 GENERATING_COM_NAME, --  �����ӹ�˾����
		 CUSTOMER_CODE, --  �ͻ����
		 CUSTOMER_NAME, --  �ͻ�����
		 CUSTOMER_PHONE, --  �ͻ��绰
		 CUSTOMER_TYPE, --  �ͻ�����
		 PAYMENT_TYPE, --  �տʽ
		 INCOME_CATEGORIES, --  �������
		 AMOUNT, --  ���
		 BUSINESS_DATE, --  ҵ������
		 VERSION_NO, --  �汾��
		 ACTIVE, --  �Ƿ���Ч
		 IS_RED_BACK, --  �Ƿ�쵥
		 CREATE_USER_CODE, --  �Ƶ��˱���
		 CREATE_USER_NAME, --  �Ƶ�������
		 CREATE_ORG_CODE, --  �Ƶ����ű���
		 CREATE_ORG_NAME, --  �Ƶ���������
		 CREATE_TIME, --  ����ʱ��
		 MODIFY_TIME, --  �޸�ʱ��
		 MODIFY_USER_CODE, --  �޸��˱���
		 MODIFY_USER_NAME, --  �޸���
		 IS_DISABLE, --  �Ƿ�����
		 DISABLE_USER_CODE, --  �����˱���
		 DISABLE_USER_NAME, --  ������
		 DISABLE_TIME, --  ����ʱ��
		 NOTES, --  ��ע
		 CURRENCY_CODE --  ����
		 )
	VALUES
		(SYS_GUID(), --  ID
		 P_OTHER_REVENUE_NO, --  СƱ����
		 P_WAYBILL_NO, --  �˵���
		 P_GENERATING_ORG_CODE, --  ���벿�ű���
		 P_GENERATING_ORG_NAME, --  ���벿������
		 P_GENERATING_COM_CODE, --  �����ӹ�˾����
		 P_GENERATING_COM_NAME, --  �����ӹ�˾����
		 P_CUSTOMER_CODE, --  �ͻ����
		 P_CUSTOMER_NAME, --  �ͻ�����
		 '15962840777', --  �ͻ��绰
		 P_CUSTOMER_TYPE, --  �ͻ�����
		 P_PAYMENT_TYPE, --  �տʽ
		 P_INCOME_CATEGORIES, --  �������
		 P_AMOUNT, --  ���
		 P_BUSINESS_DATE, --  ҵ������
		 1, --  �汾��
		 P_ACTIVE, --  �Ƿ���Ч
		 P_IS_RED_BACK, --  �Ƿ�쵥
		 '000000',
		 '��������Ա',
		 '', --  �Ƶ����ű���
		 '', --  �Ƶ���������
		 SYSDATE, --  ����ʱ��
		 SYSDATE, --  �޸�ʱ��
		 '', --  �޸��˱���
		 '', --  �޸���
		 P_IS_DISABLE, --  �Ƿ�����
		 '000000',
		 '��������Ա',
		 SYSDATE, --  ����ʱ��
		 '', --  ��ע
		 'RMB' --  ����
		 );
	COMMIT;

END PROC_TEST_INSERT_OTHER_REVENUE;
/
