CREATE OR REPLACE PACKAGE PKG_STL_BALANCE_DEPOSIT_BILL IS

  -- Author  : 000123-foss-huangxiaobo
  -- Created : 2012/12/10 14:36:51
  -- Purpose : Ԥ����ĩ����

  ------------------------------------------------------------------
  --Ԥ����ĩ���������
  ------------------------------------------------------------------
  FUNCTION FUNC_COUNT_BALANCE(P_ORG_CODE     STRING, --��֯����
                              P_BEGIN_DATE   DATE, --��ʼ����
                              P_END_DATE     DATE, --��������
                              P_PERIOD       STRING, --�����ڼ�
                              P_BALANCE_TYPE STRING --��������
                              ) RETURN BOOLEAN;

  ------------------------------------------------------------------
  --���㱾�ڷ������
  ------------------------------------------------------------------
  FUNCTION FUNC_DAILY_DETAIL_PRODUCE(P_ORG_CODE     STRING, --��֯����
                                     P_BEGIN_DATE   DATE, --��ʼ����
                                     P_END_DATE     DATE, --��������
                                     P_PERIOD       STRING, --�����ڼ�
                                     P_BALANCE_TYPE IN STRING -- �������
                                     ) RETURN BOOLEAN;

  ------------------------------------------------------------------
  --���㱾�κ������
  ------------------------------------------------------------------
  FUNCTION FUNC_DAILY_DETAIL_WRITEOFF(P_ORG_CODE     STRING, --��֯����
                                      P_BEGIN_DATE   DATE, --��ʼ����
                                      P_END_DATE     DATE, --��������
                                      P_PERIOD       STRING, --�����ڼ�
                                      P_BALANCE_TYPE IN STRING -- �������
                                      ) RETURN BOOLEAN;

END PKG_STL_BALANCE_DEPOSIT_BILL;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_BALANCE_DEPOSIT_BILL IS

  ------------------------------------------------------------------
  --Ԥ����ĩ���ˣ�Ԥ����ĩ�������-Ԥ�յ��ݣ�
  ------------------------------------------------------------------
  FUNCTION FUNC_COUNT_BALANCE(P_ORG_CODE     STRING, --��֯����
                              P_BEGIN_DATE   DATE, --��ʼ����
                              P_END_DATE     DATE, --��������
                              P_PERIOD       STRING, --�����ڼ�
                              P_BALANCE_TYPE STRING --��������
                              ) RETURN BOOLEAN IS
    --ִ�н��
    V_RESULT BOOLEAN;
  
  BEGIN
  
    --���㷢����
    V_RESULT := FUNC_DAILY_DETAIL_PRODUCE(P_ORG_CODE,
                                          P_BEGIN_DATE,
                                          P_END_DATE,
                                          P_PERIOD,
                                          P_BALANCE_TYPE);
  
    --�������󷵻�
    IF V_RESULT = FALSE THEN
      RAISE_APPLICATION_ERROR('-20002', '����Ԥ��,��������쳣');
    END IF;
  
    --���������
    V_RESULT := FUNC_DAILY_DETAIL_WRITEOFF(P_ORG_CODE,
                                           P_BEGIN_DATE,
                                           P_END_DATE,
                                           P_PERIOD,
                                           P_BALANCE_TYPE);
  
    --�������󷵻�
    IF V_RESULT = FALSE THEN
      RAISE_APPLICATION_ERROR('-20002', '����Ԥ��,���������쳣');
    END IF;
  
    RETURN V_RESULT;
    --��׽�쳣
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'pkg_stl_balance_deposit.func_count_balance',
                                        P_ORG_CODE || 'Ԥ����ĩ���˵���');
      --����ʧ�ܱ�־
      RETURN FALSE;
  END;

  ------------------------------------------------------------------
  --���㱾�ڷ������
  ------------------------------------------------------------------
  FUNCTION FUNC_DAILY_DETAIL_PRODUCE(P_ORG_CODE     STRING, --��֯����
                                     P_BEGIN_DATE   DATE, --��ʼ����
                                     P_END_DATE     DATE, --��������
                                     P_PERIOD       STRING, --�����ڼ�
                                     P_BALANCE_TYPE IN STRING -- �������
                                     ) RETURN BOOLEAN IS
  
    --�����ڼ�
    V_PERIOD STRING(8);
  BEGIN
    --��ü����ڼ�
    V_PERIOD := P_PERIOD;
  
    --�����ڷ�����д����ʱ��
    INSERT INTO STV.T_STL_DAILY_BALANCE_tmp
    --ID,�ڼ�,��֯
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
       CHECK_OUT_ORG_CODE,
       PRODUCT_CODE,
       BEGIN_AMOUNT, --�ڳ�
       PRODUCE_AMOUNT, --���ڷ�����
       WRITEOFF_AMOUNT, --�������
       END_AMOUNT, --��ĩ���
       SOURCE_BILL_NO, --��Դ����
       SOURCE_BILL_TYPE, --��Դ�������
       CURRENCY_CODE, --�ұ�
       ACTIVE, --�Ƿ���Ч
       STL_TYPE, -- ��������
       BILL_TYPE, --����������
       BALANCE_TIME, --��������
       CREATE_TIME,
       business_date,
       payment_type,
       invoice_mark --��Ʊ���      
       )
      SELECT SYS_GUID() AS ID,
             V_PERIOD AS CHECK_OUT_PERIOD,
             NVL(BILL.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             BILL.Collection_Org_Code AS CHECK_OUT_ORG_CODE,
             --DDW 2013-06-18 ��Ԥ�ս�����Ӳ�Ʒ����
             BILL.TRANSPORT_TYPE AS PRODUCT_CODE,
             0                   AS BEGIN_AMOUNT,
             BILL.AMOUNT         AS PRODUCE_AMOUNT,
             0                   AS WRITEOFF_AMOUNT,
             0                   AS END_AMOUNT,
             --DDW 2013-06-18 ��Ԥ�ս��������Դ����
             BILL.DEPOSIT_RECEIVED_NO  AS SOURCE_BILL_NO, --��Դ����(��ֵ)
             PKG_STL_COMMON.NULL_VALUE AS SOURCE_BILL_TYPE, --��Դ�������(��ֵ)
             BILL.CURRENCY_CODE        AS CURRENCY_CODE, --�ұ�
             PKG_STL_COMMON.ACTIVE     AS ACTIVE,
             P_BALANCE_TYPE            AS STL_TYPE,
             BILL.BILL_TYPE            AS BILL_TYPE,
             P_BEGIN_DATE              AS BALANCE_TIME,
             SYSDATE                   AS CREATE_TIME,
             business_date             as business_date, --ҵ������
             bill.payment_type,
             bill.invoice_mark as invoice_mark --��Ʊ���           
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED BILL
      --Ŀǰ���ռ�������
       WHERE BILL.ACCOUNT_DATE >= P_BEGIN_DATE
         AND BILL.ACCOUNT_DATE < P_END_DATE
            --Ӧ����֯
         AND BILL.Collection_Org_Code = P_ORG_CODE; -- �������� 
    commit;
    RETURN TRUE;
  
    --��׽�쳣
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'pkg_stl_balance_deposit.func_daily_produce',
                                        P_ORG_CODE || '-Ԥ����ĩ���˺��㷢����');
    
      --����ʧ�ܱ�־
      RETURN FALSE;
    
  END;

  ------------------------------------------------------------------
  --���㱾�κ������
  ------------------------------------------------------------------
  FUNCTION FUNC_DAILY_DETAIL_WRITEOFF(P_ORG_CODE     STRING, --��֯����
                                      P_BEGIN_DATE   DATE, --��ʼ����
                                      P_END_DATE     DATE, --��������
                                      P_PERIOD       STRING, --�ڼ�
                                      P_BALANCE_TYPE IN STRING -- �������
                                      ) RETURN BOOLEAN IS
    --�����ڼ�
    V_PERIOD STRING(8);
  
  BEGIN
    --��ü����ڼ�
    V_PERIOD := P_PERIOD;

    --Ԥ�ճ�Ӧ��  �Ժ���ʱ��Ϊ׼
  
    --���Ԥ�ճ�Ӧ�յĺ������,��������ʱ��
    INSERT INTO STV.T_STL_DAILY_BALANCE_tmp
    --ID,�ڼ�,��֯
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
       CHECK_OUT_ORG_CODE,
       BEGIN_AMOUNT,
       PRODUCE_AMOUNT,
       WRITEOFF_AMOUNT,
       END_AMOUNT,
       PRODUCT_CODE,
       SOURCE_BILL_NO,
       SOURCE_BILL_TYPE,
       STL_TYPE, --��������
       BILL_TYPE, --��������
       CURRENCY_CODE, --�ұ�
       ACTIVE, --�Ƿ���Ч
       BALANCE_TIME, --��������
       CREATE_TIME,
       business_date,
       payment_type,
       invoice_mark --��Ʊ���    
       )
      SELECT SYS_GUID() AS ID,
             V_PERIOD AS CHECK_OUT_PERIOD,
             NVL(b.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
              B.Collection_Org_Code AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SBW.AMOUNT AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             --DDW 2013-06-18 ��Ԥ�ս�����Ӳ�Ʒ���ͺ���Դ����
             B.TRANSPORT_TYPE          AS PRODUCT_CODE, --��Ʒ����
             B.DEPOSIT_RECEIVED_NO     AS SOURCE_BILL_NO,
             PKG_STL_COMMON.NULL_VALUE AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE            AS STL_TYPE, --��������
             B.BILL_TYPE               AS BILL_TYPE, -- ����������
             B.CURRENCY_CODE           AS CURRENCY_CODE, --�ұ�
             PKG_STL_COMMON.ACTIVE     AS ACTIVE,
             P_BEGIN_DATE              AS BALANCE_TIME,
             SYSDATE                   AS CREATE_TIME,
             b.business_date           as business_date,
             b.payment_type,
             b.invoice_mark as invoice_mark--��Ʊ���             
        FROM STL.T_STL_BILL_WRITEOFF SBW
      --����Ԥ�յ�
       INNER JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED B
          ON B.Deposit_Received_No = SBW.Begin_No --��ʼӦ�յ���
           --�Ǻ쵥
         and b.is_red_back = PKG_STL_COMMON.NO
       WHERE 
             SBW.Writeoff_Type =  PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_DR
         AND SBW.ORG_CODE = P_ORG_CODE --��������
        -- AND SBW.ORG_CODE = B.Collection_Org_Code
         AND SBW.ACCOUNT_DATE >= P_BEGIN_DATE --��������
         AND SBW.ACCOUNT_DATE < P_END_DATE; 
    commit;
      
   --��ø����Ԥ�ս��,��������ʱ��
    INSERT INTO STV.T_STL_DAILY_BALANCE_tmp
    --ID,�ڼ�,��֯
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
       CHECK_OUT_ORG_CODE,
       BEGIN_AMOUNT,
       PRODUCE_AMOUNT,
       WRITEOFF_AMOUNT,
       END_AMOUNT,
       PRODUCT_CODE,
       SOURCE_BILL_NO,
       SOURCE_BILL_TYPE,
       STL_TYPE, --��������
       BILL_TYPE, --��������
       CURRENCY_CODE, --�ұ�
       ACTIVE, --�Ƿ���Ч
       BALANCE_TIME, --��������
       CREATE_TIME,
       business_date,
       payment_type,
       invoice_mark--��Ʊ���      
       )
     --�����Ԥ��
 select SYS_GUID() AS ID,
        V_PERIOD AS CHECK_OUT_PERIOD,
        NVL(b.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
        B.Collection_Org_Code AS CHECK_OUT_ORG_CODE,
        0 AS BEGIN_AMOUNT,
        0 AS PRODUCE_AMOUNT,
        PAY_D.PAY_AMOUNT * DECODE(PAY.IS_RED_BACK,
                                  PKG_STL_COMMON.IS_RED_BACK_YES,
                                  -1,
                                  PKG_STL_COMMON.IS_RED_BACK_NO,
                                  1) AS WRITEOFF_AMOUNT,
        0 AS END_AMOUNT,
        B.TRANSPORT_TYPE AS PRODUCT_CODE, --��Ʒ����
        B.DEPOSIT_RECEIVED_NO AS SOURCE_BILL_NO,
        PKG_STL_COMMON.NULL_VALUE AS SOURCE_BILL_TYPE,
        P_BALANCE_TYPE AS STL_TYPE, --��������
        B.BILL_TYPE AS BILL_TYPE, -- ����������
        B.CURRENCY_CODE AS CURRENCY_CODE, --�ұ�
        PKG_STL_COMMON.ACTIVE AS ACTIVE,
        P_BEGIN_DATE AS BALANCE_TIME,
        SYSDATE AS CREATE_TIME,
        b.business_date as business_date,
        b.payment_type,
        b.invoice_mark as invoice_mark--��Ʊ���      
   FROM STL.T_STL_BILL_PAYMENT PAY
   JOIN STL.T_STL_BILL_PAYMENT_D PAY_D ON PAY.PAYMENT_NO = PAY_D.PAYMENT_NO
   JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED B ON B.Deposit_Received_No =
                                             PAY_D.SOURCE_BILL_NO
                                         AND B.IS_RED_BACK =
                                             PKG_STL_COMMON.IS_RED_BACK_NO
  WHERE PAY.ACCOUNT_DATE >= P_BEGIN_DATE
    AND PAY.ACCOUNT_DATE < P_END_DATE
    and PAY.Payment_Org_Code = B.Collection_Org_Code
    AND B.Collection_Org_Code = P_ORG_CODE;
      
    commit;
    --Ĭ�Ϸ��سɹ���
    RETURN TRUE;
  
    --��׽�쳣
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_BALANCE_DEPOSIT',
                                        P_ORG_CODE || '-Ԥ����ĩ���˺�����ĩ���');
      --����ʧ�ܱ�־
      RETURN FALSE;
  END;

END PKG_STL_BALANCE_DEPOSIT_BILL;
/
