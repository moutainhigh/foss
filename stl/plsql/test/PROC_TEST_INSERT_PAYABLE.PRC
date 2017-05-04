CREATE OR REPLACE PROCEDURE PROC_TEST_INSERT_PAYABLE(PAYABLE_NO       VARCHAR2, --Ӧ������
                                                     WAYBILL_NO       VARCHAR2, --�˵���
                                                     WAYBILL_ID       VARCHAR2, --�˵�id
                                                     SOURCE_BILL_NO   VARCHAR2, --��Դ���ݱ��
                                                     BILL_TYPE        VARCHAR2, --��������
                                                     SOURCE_BILL_TYPE VARCHAR2, --��Դ��������
                                                     ACCOUNT_DATE     IN DATE, --��������
                                                     BUSINESS_DATE    DATE, --ҵ������
                                                     SIGN_DATE        DATE, --ǩ������
                                                     AMOUNT           NUMBER, --���
                                                     ACTIVE           CHAR, --����״̬
                                                     IS_RED_BACK      CHAR, --�Ƿ�쵥
                                                     CUSTOMER_CODE    VARCHAR2, --�ͻ�����
                                                     CUSTOMER_NAME    VARCHAR2, --�ͻ�����
                                                     ORIG_ORG_CODE    VARCHAR2, --ʼ�����ű���
                                                     ORIG_ORG_NAME    VARCHAR2, --ʼ����������
                                                     DEST_ORG_CODE    VARCHAR2, --���ﲿ�ű���
                                                     DEST_ORG_NAME    VARCHAR2, --���ﲿ������
                                                     PAYABLE_ORG_CODE  VARCHAR2, --Ӧ�����ű���
                                                     PAYABLE_ORG_NAME  VARCHAR2,  --Ӧ����������
                                                     PAYMENT_NO VARCHAR2 default '', --Ĭ��Ϊ��
                                                     PAYMENT_AMOUNT number default null,--������
                                                     PRODUCT_CODE varchar2 default '10'
                                                     ) IS
BEGIN

  --����Ӧ��������
  INSERT INTO STL.T_STL_BILL_PAYABLE
    (ID,
     PAYABLE_NO,
     WAYBILL_NO,
     WAYBILL_ID,
     PAYMENT_NO,
     CREATE_TYPE,
     BILL_TYPE,
     SOURCE_BILL_NO,
     SOURCE_BILL_TYPE,
     PRODUCT_CODE,
     PAYABLE_ORG_CODE,
     PAYABLE_ORG_NAME,
     PAYABLE_COM_CODE,
     PAYABLE_COM_NAME,
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
     PRODUCT_ID,
     ACCOUNT_DATE,
     BUSINESS_DATE,
     SIGN_DATE,
     EFFECTIVE_DATE,
     CREATE_USER_NAME,
     CREATE_USER_CODE,
     CREATE_ORG_CODE,
     CREATE_ORG_NAME,
     ACTIVE,
     IS_RED_BACK,
     IS_INIT,
     VERSION_NO,
     EFFECTIVE_STATUS,
     EFFECTIVE_USER_NAME,
     EFFECTIVE_USER_CODE,
     FROZEN_STATUS,
     FROZEN_TIME,
     FROZEN_USER_NAME,
     FROZEN_USER_CODE,
     PAY_STATUS,
     PAYMENT_STATUS,
     STATEMENT_BILL_NO,
     CUSTOMER_CONTACT,
     CUSTOMER_CONTACT_NAME,
     CUSTOMER_PHONE,
     CREATE_TIME,
     MODIFY_TIME,
     MODIFY_USER_NAME,
     MODIFY_USER_CODE,
     OTHERSIDE_ORG_CODE,
     OTHERSIDE_ORG_NAME,
     WORKFLOW_NO,
     LGDRIVER_CODE,
     LGDRIVER_NAME,
     PAYER_TYPE,
     PAYABLE_TYPE,
     DELIVER_FEE,
     OUTGOING_FEE,
     AUDIT_USER_CODE,
     AUDIT_USER_NAME,
     AUDIT_DATE,
     APPROVE_STATUS,
     IS_DISABLE,
     DISABLE_USER_CODE,
     DISABLE_USER_NAME,
     DISABLE_TIME,
     NOTES,
     COD_TYPE,
     PAYEE_NAME,
     BANK_HQ_CODE,
     BANK_HQ_NAME,
     ACCOUNT_NO,
     PROVINCE_CODE,
     PROVINCE_NAME,
     CITY_CODE,
     CITY_NAME,
     BANK_BRANCH_CODE,
     BANK_BRANCH_NAME,
     PAYMENT_AMOUNT,
     PAYMENT_NOTES)
  VALUES
    (SYS_GUID(),
     PAYABLE_NO, --'YF7' || LPAD(SQ_YF7.NEXTVAL, 7, '0'), --Ӧ������
     WAYBILL_NO, --'YDWUJT' || LPAD(SQ_YF7.NEXTVAL, 7, '0'), --�˵���
     WAYBILL_ID, --'YDWUJT' || LPAD(SQ_YF7.NEXTVAL, 7, '0'), --�˵�id
     PAYMENT_NO,
     'A',
     BILL_TYPE, --��������
     SOURCE_BILL_NO, --'WFDWUJT' || LPAD(SQ_YF7.NEXTVAL, 7, '0'), --ǰ׺ΪWFD��Դ���ݱ��
     SOURCE_BILL_TYPE, --��Դ��������  ƫ���ⷢ��
     PRODUCT_CODE, --��Ʒ���� ����ƫ��
     PAYABLE_ORG_CODE, --Ӧ�����ű���
     PAYABLE_ORG_NAME, --Ӧ����������
     'TODO',
     'TODO1',
     ORIG_ORG_CODE, --ʼ�����ű���
     ORIG_ORG_NAME, --ʼ����������
     DEST_ORG_CODE, --ƫ���ⷢ���ű���
     DEST_ORG_NAME, --�ⷢ��������
     CUSTOMER_CODE, --�ͻ�����/��������
     CUSTOMER_NAME, --�ͻ�����/��������
     AMOUNT, --���
     0, --�Ѻ������
     AMOUNT, --δ�������
     'RMB',
     '',
     ACCOUNT_DATE, -- TO_DATE('11-12-2012 16:08:27', 'dd-mm-yyyy hh24:mi:ss'), --��������
     BUSINESS_DATE, -- TO_DATE('11-12-2012 16:08:27', 'dd-mm-yyyy hh24:mi:ss'), ---ҵ������
     SIGN_DATE, -- TO_DATE('12-12-2012 16:08:27', 'dd-mm-yyyy hh24:mi:ss'), --ǩ������
     NULL, --��Ч����
     '����',
     '000123',
     '1',
     '�°�����',
     ACTIVE,
     IS_RED_BACK,
     'N',
     1,
     'N',
     '',
     '',
     'N',
     NULL,
     '',
     '',
     'N',
     'NP',
     'N/A',
     '',
     '',
     '',
     SYSDATE,
     SYSDATE,
     '����',
     '000123',
     '',
     '',
     '',
     '',
     '',
     '',
     '',
     100000,
     100000,
     '',
     '',
     NULL,
     '',
     '',
     '',
     'N',
     NULL,
     '',
     '',
     '',
     '',
     '',
     '',
     '',
     '',
     '',
     '',
     '',
     '',
     PAYMENT_AMOUNT,
     '');
  COMMIT;
END PROC_TEST_INSERT_PAYABLE;
/