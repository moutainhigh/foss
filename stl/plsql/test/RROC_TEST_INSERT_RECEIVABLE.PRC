CREATE OR REPLACE PROCEDURE RROC_TEST_INSERT_RECEIVABLE(RECEIVABLE_NO       IN VARCHAR2,
																												WAYBILL_NO          VARCHAR2,
																												WAYBILL_ID          VARCHAR2,
																												SOURCE_BILL_NO      VARCHAR2,
																												SOURCE_BILL_TYPE    VARCHAR2,
																												BILL_TYPE           VARCHAR2,
																												CUSTOMER_CODE       VARCHAR2,
																												CUSTOMER_NAME       VARCHAR2,
																												RECEIVABLE_ORG_CODE VARCHAR2,
																												RECEIVABLE_ORG_NAME VARCHAR2,
																												ORIG_ORG_CODE       VARCHAR2,
																												ORIG_ORG_NAME       VARCHAR2,
																												DEST_ORG_CODE       VARCHAR2,
																												DEST_ORG_NAME       VARCHAR2,
																												BUSINESS_DATE       DATE,
																												ACCOUNT_DATE        DATE,
																												ACTIVE              CHAR,
																												IS_RED_BACK         CHAR,
																												AMOUNT              NUMBER,
																												PAYMENT_TYPE        VARCHAR2 --���ʽ
																											 ,
																												PRODUCT_CODE        VARCHAR2,
																												PRODUCT_ID          VARCHAR2,
																												VERSION_NO          NUMBER) IS
BEGIN

	INSERT INTO STL.T_STL_BILL_RECEIVABLE
		(ID,
		 RECEIVABLE_NO,
		 WAYBILL_NO,
		 WAYBILL_ID,
		 CREATE_TYPE,
		 SOURCE_BILL_NO,
		 SOURCE_BILL_TYPE,
		 BILL_TYPE,
		 RECEIVABLE_ORG_CODE,
		 RECEIVABLE_ORG_NAME,
		 ORIG_ORG_CODE,
		 ORIG_ORG_NAME,
		 DEST_ORG_CODE,
		 DEST_ORG_NAME,
		 CUSTOMER_CODE,
		 CUSTOMER_NAME,
		 AMOUNT,
		 VERIFY_AMOUNT,
		 UNVERIFY_AMOUNT,
		 CURRENCY_CODE,
		 BUSINESS_DATE,
		 ACCOUNT_DATE,
		 CONREVEN_DATE,
		 PAYMENT_TYPE,
		 PRODUCT_CODE,
		 PRODUCT_ID,
		 TRANSPORT_FEE,
		 PICKUP_FEE,
		 DELIVERY_GOODS_FEE,
		 PACKAGING_FEE,
		 COD_FEE,
		 INSURANCE_FEE,
		 OTHER_FEE,
		 VALUE_ADD_FEE,
		 PROMOTIONS_FEE,
		 GOODS_NAME,
		 GOODS_VOLUME_TOTAL,
		 BILL_WEIGHT,
		 RECEIVE_METHOD,
		 CUSTOMER_PICKUP_ORG_CODE,
		 GOODS_QTY_TOTAL,
		 TARGET_ORG_CODE,
		 VERSION_NO,
		 ACTIVE,
		 IS_RED_BACK,
		 IS_INIT,
		 CREATE_USER_CODE,
		 CREATE_USER_NAME,
		 CREATE_ORG_CODE,
		 CREATE_ORG_NAME,
		 CREATE_TIME,
		 MODIFY_TIME,
     MODIFY_USER_CODE,
     MODIFY_USER_NAME,
     STATEMENT_BILL_NO,
     UNLOCK_DATE_TIME,
     LOCK_CUSTOMER_CODE,
     LOCK_CUSTOMER_NAME,
     COLLECTION_TYPE,
     COLLECTION_NAME,
     AUDIT_USER_CODE,
     AUDIT_USER_NAME,
     APPROVE_STATUS,
     AUDIT_DATE,
     IS_DISABLE,
     DISABLE_USER_CODE,
     DISABLE_USER_NAME,
     DISABLE_TIME,
     NOTES)
  VALUES
    (SYS_GUID(), --ID
     RECEIVABLE_NO, --Ӧ�յ���
     WAYBILL_NO, --�˵���
     WAYBILL_ID, --�˵�ID
     'A', --ϵͳ������ʽ
     SOURCE_BILL_NO, --��Դ���ݺ�
     SOURCE_BILL_TYPE, --��Դ��������
     BILL_TYPE, --��������
     RECEIVABLE_ORG_CODE, --Ӧ�ղ��ű���
     RECEIVABLE_ORG_NAME, --Ӧ�ղ�������
     ORIG_ORG_CODE, --�������ű���
     ORIG_ORG_NAME, --������������
     DEST_ORG_CODE, --���ﲿ�ű���
     DEST_ORG_NAME, --���ﲿ������
     CUSTOMER_CODE, --�ͻ�����
     CUSTOMER_NAME, --�ͻ�����
     AMOUNT, --���
     0, --�Ѻ������
     AMOUNT, --δ�������
     'RMB',
     BUSINESS_DATE, --ҵ������
     ACCOUNT_DATE, --��������
     NULL, --ȷ����������
     PAYMENT_TYPE, --���ʽ
     PRODUCT_CODE, --��Ʒ����
     PRODUCT_ID, --��ƷID
     42796,
     1,
     2,
     4,
     1,
     1,
     -1,
     8,
     4,
     '',
     NULL,
     NULL,
     '�����ʽ��40',
     '',
     NULL,
     '',
     VERSION_NO, --�汾��
     ACTIVE, --�Ƿ���Ч
     IS_RED_BACK, --�Ƿ�쵥
     'N',
     '000123',
     '����',
     '1',
     '�°�����',
     SYSDATE,
     SYSDATE,
     '000123',
     '����',
     'N/A',
     NULL,
     '',
     '',
     '',
     '',
     '',
     '',
     '',
     NULL,
     '',
     '',
     '',
     NULL,
     '');

END RROC_TEST_INSERT_RECEIVABLE;
/