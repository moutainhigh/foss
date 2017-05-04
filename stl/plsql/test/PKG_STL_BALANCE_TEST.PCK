CREATE OR REPLACE PACKAGE PKG_STL_BALANCE_TEST IS

  -- Author  : 00123-foss-huangxiaobo
  -- Created : 2012/12/10 18:48:16
  -- Purpose : ��ĩ������

  FUNCTION FUNC_TEST_BALANCE RETURN BOOLEAN;

END PKG_STL_BALANCE_TEST;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_BALANCE_TEST IS

  -- Author  : 00123-foss-huangxiaobo
  -- Created : 2012/12/10 18:48:16
  -- Purpose : ��ĩ������

  FUNCTION FUNC_TEST_BALANCE RETURN BOOLEAN IS
    --������֯
    V_ORG_CODE VARCHAR2(40);
    --����ͻ�
    V_CUSTOMER_CODE VARCHAR2(40);
    --��������
    V_ACCOUNT_DATE DATE;
    --Ӧ�յ�����
    V_RECEIVABLE_NO VARCHAR2(20);
    --Ӧ��������
    V_PAYABLE_NO VARCHAR2(20);
    --Ԥ�յ�����
    V_DEPOSIT_NO VARCHAR2(20);
    --��������
    V_WRITEOFF_NO VARCHAR2(20);
    --���ؽ��
    V_RESULT BOOLEAN;
  
  BEGIN
  
    --�Բ������г�ʼ��
    V_ORG_CODE      := 'my_org_code';
    V_CUSTOMER_CODE := 'my_customer_code';
    V_ACCOUNT_DATE  := TO_DATE('2012-11-21', 'yyyy-mm-dd');
    V_RECEIVABLE_NO := 'BR001';
    V_PAYABLE_NO    := 'BP001';
    V_DEPOSIT_NO    := 'BD001';
    V_WRITEOFF_NO   := 'BW001';
  
    --ɾ�����ڵ�����
    DELETE FROM STV.T_STL_BALANCE;
    COMMIT;
    DELETE FROM STV.T_STL_BALANCE_DETAIL;
    COMMIT;
    DELETE FROM STV.T_STL_DAILY_BALANCE_DETAIL;
    COMMIT;
    DELETE FROM STV.T_STL_DAILY_BALANCE;
    COMMIT;
    --ɾ��Ӧ�յ�
    DELETE FROM STL.T_STL_BILL_RECEIVABLE R
     WHERE R.RECEIVABLE_NO = V_RECEIVABLE_NO;
    COMMIT;
    --ɾ��Ӧ����
    DELETE FROM STL.T_STL_BILL_PAYABLE R WHERE R.PAYABLE_NO = V_PAYABLE_NO;
    COMMIT;
    --ɾ��Ԥ�յ�
    DELETE FROM STL.T_STL_BILL_DEPOSIT_RECEIVED R
     WHERE R.DEPOSIT_RECEIVED_NO = V_DEPOSIT_NO;
    COMMIT;
    DELETE FROM STL.T_STL_BILL_WRITEOFF R
     WHERE R.WRITEOFF_BILL_NO = V_WRITEOFF_NO;
    COMMIT;
    
    --��ʼ
  
    --�����µ����� Ӧ�յ�    
    INSERT INTO STL.T_STL_BILL_RECEIVABLE
      (ID,
       RECEIVABLE_NO,
       WAYBILL_NO,
       SOURCE_BILL_NO,
       SOURCE_BILL_TYPE,
       BILL_TYPE,
       RECEIVABLE_ORG_CODE,
       CUSTOMER_CODE,
       AMOUNT,
       VERIFY_AMOUNT,
       UNVERIFY_AMOUNT,
       CURRENCY_CODE,
       BUSINESS_DATE,
       ACCOUNT_DATE,
       PRODUCT_CODE,
       BILL_WEIGHT,
       RECEIVE_METHOD,
       VERSION_NO,
       ACTIVE,
       IS_RED_BACK,
       IS_INIT,
       CREATE_TYPE)
    VALUES
      (SYS_GUID(),
       V_RECEIVABLE_NO,
       '2012008',
       '2012008',
       'W',
       'SS',
       V_ORG_CODE,
       V_CUSTOMER_CODE,
       10000,
       3393900,
       6606100,
       'RMB',
       V_ACCOUNT_DATE,
       V_ACCOUNT_DATE,
       'O1',
       10.000,
       '����',
       20,
       'Y',
       'N',
       'N',
       'A');
    COMMIT;
  
    --����Ӧ����
    INSERT INTO STL.T_STL_BILL_PAYABLE P
      (ID,
       PAYABLE_NO,
       WAYBILL_NO,
       SOURCE_BILL_NO,
       SOURCE_BILL_TYPE,
       BILL_TYPE,
       PAYABLE_ORG_CODE,
       CUSTOMER_CODE,
       AMOUNT,
       VERIFY_AMOUNT,
       UNVERIFY_AMOUNT,
       CURRENCY_CODE,
       BUSINESS_DATE,
       ACCOUNT_DATE,
       PRODUCT_CODE,
       VERSION_NO,
       ACTIVE,
       IS_RED_BACK,
       IS_INIT,
       CREATE_TYPE)
    VALUES
      (SYS_GUID(),
       V_PAYABLE_NO,
       '2012008',
       '2012008',
       'W',
       'SS',
       V_ORG_CODE,
       V_CUSTOMER_CODE,
       10000,
       3393900,
       6606100,
       'RMB',
       V_ACCOUNT_DATE,
       V_ACCOUNT_DATE,
       'O1',
       20,
       'Y',
       'N',
       'N',
       'A');
    COMMIT;
    --Ԥ�յ�  
    INSERT INTO STL.T_STL_BILL_DEPOSIT_RECEIVED P
      (ID,
       DEPOSIT_RECEIVED_NO,
       BILL_TYPE,
       GENERATING_ORG_CODE,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       AMOUNT,
       VERIFY_AMOUNT,
       UNVERIFY_AMOUNT,
       CURRENCY_CODE,
       BUSINESS_DATE,
       ACCOUNT_DATE,
       VERSION_NO,
       ACTIVE,
       IS_RED_BACK,
       IS_INIT,
       PAYMENT_TYPE,
       CREATE_USER_CODE,
       CREATE_TIME)
    VALUES
      (SYS_GUID(),
       V_DEPOSIT_NO,
       'SS',
       V_ORG_CODE,
       V_CUSTOMER_CODE,
       'customer_name',
       10000,
       3393900,
       6606100,
       'RMB',
       V_ACCOUNT_DATE,
       V_ACCOUNT_DATE,
       20,
       'Y',
       'N',
       'N',
       'C',
       'test',
       SYSDATE);
    COMMIT;
  
    --����Ӧ����ĩ����
    V_RESULT := PKG_STL_BALANCE_RECEIVABLE.FUNC_COUNT_BALANCE(V_ORG_CODE,
                                                              V_ACCOUNT_DATE,
                                                              TRUNC(V_ACCOUNT_DATE + 1),
                                                              TO_CHAR(V_ACCOUNT_DATE,
                                                                      'yyyymm'),
                                                              PKG_STL_BALANCE_COMMON.BALANCE_TYPE_RECEIVABLE);
    IF V_RESULT = FALSE THEN
      RETURN FALSE;
    END IF;
  
    --����Ӧ����ĩ����
    V_RESULT := PKG_STL_BALANCE_PAYABLE.FUNC_COUNT_BALANCE(V_ORG_CODE,
                                                           V_ACCOUNT_DATE,
                                                           TRUNC(V_ACCOUNT_DATE + 1),
                                                           TO_CHAR(V_ACCOUNT_DATE,
                                                                   'yyyymm'),
                                                           PKG_STL_BALANCE_COMMON.BALANCE_TYPE_PAYABLE);
    IF V_RESULT = FALSE THEN
      RETURN FALSE;
    END IF;
  
    --����Ԥ�յ�
    V_RESULT := PKG_STL_BALANCE_DEPOSIT.FUNC_COUNT_BALANCE(V_ORG_CODE,
                                                           V_ACCOUNT_DATE,
                                                           TRUNC(V_ACCOUNT_DATE + 1),
                                                           TO_CHAR(V_ACCOUNT_DATE,
                                                                   'yyyymm'),
                                                           PKG_STL_BALANCE_COMMON.BALANCE_TYPE_DEPOSIT);
    IF V_RESULT = FALSE THEN
      RETURN FALSE;
    END IF;
  
    --����9����ĩ
    V_ACCOUNT_DATE := TO_DATE('2012-11-30', 'yyyy-mm-dd');
    --����Ӧ����ĩ����
    V_RESULT := PKG_STL_BALANCE_RECEIVABLE.FUNC_COUNT_BALANCE(V_ORG_CODE,
                                                              V_ACCOUNT_DATE,
                                                              TRUNC(V_ACCOUNT_DATE + 1),
                                                              TO_CHAR(V_ACCOUNT_DATE,
                                                                      'yyyymm'),
                                                              PKG_STL_BALANCE_COMMON.BALANCE_TYPE_RECEIVABLE);
    IF V_RESULT = FALSE THEN
      RETURN FALSE;
    END IF;
  
    --����Ӧ����ĩ����
    V_RESULT := PKG_STL_BALANCE_PAYABLE.FUNC_COUNT_BALANCE(V_ORG_CODE,
                                                           V_ACCOUNT_DATE,
                                                           TRUNC(V_ACCOUNT_DATE + 1),
                                                           TO_CHAR(V_ACCOUNT_DATE,
                                                                   'yyyymm'),
                                                           PKG_STL_BALANCE_COMMON.BALANCE_TYPE_PAYABLE);
    IF V_RESULT = FALSE THEN
      RETURN FALSE;
    END IF;
  
    --����Ԥ�յ�
    V_RESULT := PKG_STL_BALANCE_DEPOSIT.FUNC_COUNT_BALANCE(V_ORG_CODE,
                                                           V_ACCOUNT_DATE,
                                                           TRUNC(V_ACCOUNT_DATE + 1),
                                                           TO_CHAR(V_ACCOUNT_DATE,
                                                                   'yyyymm'),
                                                           PKG_STL_BALANCE_COMMON.BALANCE_TYPE_DEPOSIT);
    IF V_RESULT = FALSE THEN
      RETURN FALSE;
    END IF;
  
    --���ú�������
    V_ACCOUNT_DATE := TO_DATE('2012-12-1', 'yyyy-mm-dd');
  
    --���������¼(Ӧ�ճ�Ӧ��)
    INSERT INTO STL.T_STL_BILL_WRITEOFF
      (ID,
       WRITEOFF_BATCH_NO,
       WRITEOFF_BILL_NO,
       BEGIN_NO,
       END_NO,
       CREATE_TYPE,
       WRITEOFF_TYPE,
       ORG_CODE,
       ORG_NAME,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       AMOUNT,
       ACTIVE,
       IS_RED_BACK,
       WRITEOFF_TIME,
       ACCOUNT_DATE,
       IS_INIT,
       VERSION_NO)
    VALUES
      (SYS_GUID(),
       V_WRITEOFF_NO,
       V_WRITEOFF_NO,
       V_RECEIVABLE_NO,
       V_PAYABLE_NO,
       'M',
       'AP',
       V_ORG_CODE,
       'Ӧ���Ϻ�Ӫҵ��',
       V_CUSTOMER_CODE,
       '�ż̺�',
       100,
       'Y',
       'N',
       V_ACCOUNT_DATE,
       V_ACCOUNT_DATE,
       'N',
       1);
    COMMIT;
  
    --���������¼(�����Ӧ��)
    INSERT INTO STL.T_STL_BILL_WRITEOFF
      (ID,
       WRITEOFF_BATCH_NO,
       WRITEOFF_BILL_NO,
       BEGIN_NO,
       END_NO,
       CREATE_TYPE,
       WRITEOFF_TYPE,
       ORG_CODE,
       ORG_NAME,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       AMOUNT,
       ACTIVE,
       IS_RED_BACK,
       WRITEOFF_TIME,
       ACCOUNT_DATE,
       IS_INIT,
       VERSION_NO)
    VALUES
      (SYS_GUID(),
       V_WRITEOFF_NO,
       V_WRITEOFF_NO,
       'paymentBIll',
       V_RECEIVABLE_NO,
       'M',
       'AP',
       V_ORG_CODE,
       'Ӧ���Ϻ�Ӫҵ��',
       V_CUSTOMER_CODE,
       '�ż̺�',
       100,
       'Y',
       'N',
       V_ACCOUNT_DATE,
       V_ACCOUNT_DATE,
       'N',
       1);
    COMMIT;
  
    --���������¼(�����Ӧ��)
    INSERT INTO STL.T_STL_BILL_WRITEOFF
      (ID,
       WRITEOFF_BATCH_NO,
       WRITEOFF_BILL_NO,
       BEGIN_NO,
       END_NO,
       CREATE_TYPE,
       WRITEOFF_TYPE,
       ORG_CODE,
       ORG_NAME,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       AMOUNT,
       ACTIVE,
       IS_RED_BACK,
       WRITEOFF_TIME,
       ACCOUNT_DATE,
       IS_INIT,
       VERSION_NO)
    VALUES
      (SYS_GUID(),
       V_WRITEOFF_NO,
       V_WRITEOFF_NO,
       'paymentBIll',
       V_PAYABLE_NO,
       'M',
       'AP',
       V_ORG_CODE,
       'Ӧ���Ϻ�Ӫҵ��',
       V_CUSTOMER_CODE,
       '�ż̺�',
       100,
       'Y',
       'N',
       V_ACCOUNT_DATE,
       V_ACCOUNT_DATE,
       'N',
       1);
    COMMIT;
  
    --���������¼(Ԥ�ճ�Ӧ��)
    INSERT INTO STL.T_STL_BILL_WRITEOFF
      (ID,
       WRITEOFF_BATCH_NO,
       WRITEOFF_BILL_NO,
       BEGIN_NO,
       END_NO,
       CREATE_TYPE,
       WRITEOFF_TYPE,
       ORG_CODE,
       ORG_NAME,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       AMOUNT,
       ACTIVE,
       IS_RED_BACK,
       WRITEOFF_TIME,
       ACCOUNT_DATE,
       IS_INIT,
       VERSION_NO)
    VALUES
      (SYS_GUID(),
       V_WRITEOFF_NO,
       V_WRITEOFF_NO,
       V_DEPOSIT_NO,
       V_RECEIVABLE_NO,
       'M',
       'AP',
       V_ORG_CODE,
       'Ӧ���Ϻ�Ӫҵ��',
       V_CUSTOMER_CODE,
       '�ż̺�',
       100,
       'Y',
       'N',
       V_ACCOUNT_DATE,
       V_ACCOUNT_DATE,
       'N',
       1);
    COMMIT;
  
    --���������¼(�����Ԥ��)
    INSERT INTO STL.T_STL_BILL_WRITEOFF
      (ID,
       WRITEOFF_BATCH_NO,
       WRITEOFF_BILL_NO,
       BEGIN_NO,
       END_NO,
       CREATE_TYPE,
       WRITEOFF_TYPE,
       ORG_CODE,
       ORG_NAME,
       CUSTOMER_CODE,
       CUSTOMER_NAME,
       AMOUNT,
       ACTIVE,
       IS_RED_BACK,
       WRITEOFF_TIME,
       ACCOUNT_DATE,
       IS_INIT,
       VERSION_NO)
    VALUES
      (SYS_GUID(),
       V_WRITEOFF_NO,
       V_WRITEOFF_NO,
       'paymentBill',
       V_DEPOSIT_NO,
       'M',
       'AP',
       V_ORG_CODE,
       'Ӧ���Ϻ�Ӫҵ��',
       V_CUSTOMER_CODE,
       '�ż̺�',
       100,
       'Y',
       'N',
       V_ACCOUNT_DATE,
       V_ACCOUNT_DATE,
       'N',
       1);
    COMMIT;
  
    --�����ڳ�
    --���ú�������
    V_ACCOUNT_DATE := TO_DATE('2012-12-1', 'yyyy-mm-dd');
    --����Ӧ����ĩ����
    V_RESULT := PKG_STL_BALANCE_RECEIVABLE.FUNC_COUNT_BALANCE(V_ORG_CODE,
                                                              V_ACCOUNT_DATE,
                                                              TRUNC(V_ACCOUNT_DATE + 1),
                                                              TO_CHAR(V_ACCOUNT_DATE,
                                                                      'yyyymm'),
                                                              PKG_STL_BALANCE_COMMON.BALANCE_TYPE_RECEIVABLE);
    IF V_RESULT = FALSE THEN
      RETURN FALSE;
    END IF;
  
    --����Ӧ����ĩ����
    V_RESULT := PKG_STL_BALANCE_PAYABLE.FUNC_COUNT_BALANCE(V_ORG_CODE,
                                                           V_ACCOUNT_DATE,
                                                           TRUNC(V_ACCOUNT_DATE + 1),
                                                           TO_CHAR(V_ACCOUNT_DATE,
                                                                   'yyyymm'),
                                                           PKG_STL_BALANCE_COMMON.BALANCE_TYPE_PAYABLE);
    IF V_RESULT = FALSE THEN
      RETURN FALSE;
    END IF;
  
    --����Ԥ�յ�
    V_RESULT := PKG_STL_BALANCE_DEPOSIT.FUNC_COUNT_BALANCE(V_ORG_CODE,
                                                           V_ACCOUNT_DATE,
                                                           TRUNC(V_ACCOUNT_DATE + 1),
                                                           TO_CHAR(V_ACCOUNT_DATE,
                                                                   'yyyymm'),
                                                           PKG_STL_BALANCE_COMMON.BALANCE_TYPE_DEPOSIT);
    IF V_RESULT = FALSE THEN
      RETURN FALSE;
    END IF;
  
    COMMIT;
  
    --������ĩ
    --���ú�������
    V_ACCOUNT_DATE := TO_DATE('2012-12-31', 'yyyy-mm-dd');
    --����Ӧ����ĩ����
    V_RESULT := PKG_STL_BALANCE_RECEIVABLE.FUNC_COUNT_BALANCE(V_ORG_CODE,
                                                              V_ACCOUNT_DATE,
                                                              TRUNC(V_ACCOUNT_DATE + 1),
                                                              TO_CHAR(V_ACCOUNT_DATE,
                                                                      'yyyymm'),
                                                              PKG_STL_BALANCE_COMMON.BALANCE_TYPE_RECEIVABLE);
    IF V_RESULT = FALSE THEN
      RETURN FALSE;
    END IF;
  
    --����Ӧ����ĩ����
    V_RESULT := PKG_STL_BALANCE_PAYABLE.FUNC_COUNT_BALANCE(V_ORG_CODE,
                                                           V_ACCOUNT_DATE,
                                                           TRUNC(V_ACCOUNT_DATE + 1),
                                                           TO_CHAR(V_ACCOUNT_DATE,
                                                                   'yyyymm'),
                                                           PKG_STL_BALANCE_COMMON.BALANCE_TYPE_PAYABLE);
    IF V_RESULT = FALSE THEN
      RETURN FALSE;
    END IF;
  
    --����Ԥ�յ�
    V_RESULT := PKG_STL_BALANCE_DEPOSIT.FUNC_COUNT_BALANCE(V_ORG_CODE,
                                                           V_ACCOUNT_DATE,
                                                           TRUNC(V_ACCOUNT_DATE + 1),
                                                           TO_CHAR(V_ACCOUNT_DATE,
                                                                   'yyyymm'),
                                                           PKG_STL_BALANCE_COMMON.BALANCE_TYPE_DEPOSIT);
    IF V_RESULT = FALSE THEN
      RETURN FALSE;
    END IF;
  
    COMMIT;
  
    --���ú�������(���㵽�ڳ�)
    V_ACCOUNT_DATE := TO_DATE('2013-1-1', 'yyyy-mm-dd');
    --����Ӧ����ĩ����
    V_RESULT := PKG_STL_BALANCE_RECEIVABLE.FUNC_COUNT_BALANCE(V_ORG_CODE,
                                                              V_ACCOUNT_DATE,
                                                              TRUNC(V_ACCOUNT_DATE + 1),
                                                              TO_CHAR(V_ACCOUNT_DATE,
                                                                      'yyyymm'),
                                                              PKG_STL_BALANCE_COMMON.BALANCE_TYPE_RECEIVABLE);
    IF V_RESULT = FALSE THEN
      RETURN FALSE;
    END IF;
  
    --����Ӧ����ĩ����
    V_RESULT := PKG_STL_BALANCE_PAYABLE.FUNC_COUNT_BALANCE(V_ORG_CODE,
                                                           V_ACCOUNT_DATE,
                                                           TRUNC(V_ACCOUNT_DATE + 1),
                                                           TO_CHAR(V_ACCOUNT_DATE,
                                                                   'yyyymm'),
                                                           PKG_STL_BALANCE_COMMON.BALANCE_TYPE_PAYABLE);
    IF V_RESULT = FALSE THEN
      RETURN FALSE;
    END IF;
  
    --����Ԥ�յ�
    V_RESULT := PKG_STL_BALANCE_DEPOSIT.FUNC_COUNT_BALANCE(V_ORG_CODE,
                                                           V_ACCOUNT_DATE,
                                                           TRUNC(V_ACCOUNT_DATE + 1),
                                                           TO_CHAR(V_ACCOUNT_DATE,
                                                                   'yyyymm'),
                                                           PKG_STL_BALANCE_COMMON.BALANCE_TYPE_DEPOSIT);
    IF V_RESULT = FALSE THEN
      RETURN FALSE;
    END IF;
  
    COMMIT;
    --���ú�������(���㵽�ڳ�)
    V_ACCOUNT_DATE := TO_DATE('2013-1-31', 'yyyy-mm-dd');
    --����Ӧ����ĩ����
    V_RESULT := PKG_STL_BALANCE_RECEIVABLE.FUNC_COUNT_BALANCE(V_ORG_CODE,
                                                              V_ACCOUNT_DATE,
                                                              TRUNC(V_ACCOUNT_DATE + 1),
                                                              TO_CHAR(V_ACCOUNT_DATE,
                                                                      'yyyymm'),
                                                              PKG_STL_BALANCE_COMMON.BALANCE_TYPE_RECEIVABLE);
    IF V_RESULT = FALSE THEN
      RETURN FALSE;
    END IF;
  
    --����Ӧ����ĩ����
    V_RESULT := PKG_STL_BALANCE_PAYABLE.FUNC_COUNT_BALANCE(V_ORG_CODE,
                                                           V_ACCOUNT_DATE,
                                                           TRUNC(V_ACCOUNT_DATE + 1),
                                                           TO_CHAR(V_ACCOUNT_DATE,
                                                                   'yyyymm'),
                                                           PKG_STL_BALANCE_COMMON.BALANCE_TYPE_PAYABLE);
    IF V_RESULT = FALSE THEN
      RETURN FALSE;
    END IF;
  
    --����Ԥ�յ�
    V_RESULT := PKG_STL_BALANCE_DEPOSIT.FUNC_COUNT_BALANCE(V_ORG_CODE,
                                                           V_ACCOUNT_DATE,
                                                           TRUNC(V_ACCOUNT_DATE + 1),
                                                           TO_CHAR(V_ACCOUNT_DATE,
                                                                   'yyyymm'),
                                                           PKG_STL_BALANCE_COMMON.BALANCE_TYPE_DEPOSIT);
  
    COMMIT;
    RETURN V_RESULT;
  END;

END PKG_STL_BALANCE_TEST;
/