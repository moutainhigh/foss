CREATE OR REPLACE PACKAGE STV.PKG_STL_BALANCE_COMMON IS

  -- Author  : 000123-foss-huangxiaobo
  -- Created : 2012/11/20 9:37:48
  -- Purpose : ��ĩ�ṫ��ģ�� 

  ------------------------------------------------------------------
  --�����ʼ��������(Ĭ�ϵ�ǰ����ǰһ���00:00:00)
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_BEGIN_DATE(P_CURRENT_DATE DATE --��������
                               ) RETURN DATE;

  ------------------------------------------------------------------
  --��ý�������(Ĭ�ϵ�ǰ���ڵ�00:00:00)
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_END_DATE(P_CURRENT_DATE DATE --��������
                             ) RETURN DATE;

  ------------------------------------------------------------------
  --�������ڻ�õ������ڶ�Ӧ���ڼ�
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_PERIOD(P_DATE1 DATE --��ʼ����
                           ) RETURN STRING;

  ------------------------------------------------------------------
  --�������ڻ�ö�Ӧ�������ڵ��ڼ�
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_LAST_PERIOD(P_PERIOD_DATE DATE --�����Ӧ���ڼ�
                                ) RETURN STRING;

  ------------------------------------------------------------------
  --��÷�ҳ���ҳ��
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_PAGE_SIZE(P_ROW_NUM INT --ҳ����
                              ) RETURN INT;

  ------------------------------------------------------------------
  --�Ƿ�Ϊ��һ�������ڼ�
  ------------------------------------------------------------------
  FUNCTION FUNC_IS_JUNUARY(P_PERIOD STRING --�����ڼ�
                           ) RETURN BOOLEAN;

  ------------------------------------------------------------------
  --�ж��Ƿ�Ϊ�·ݵĵ�һ��
  ------------------------------------------------------------------
  FUNCTION FUNC_IS_MONTH_FIRST_DAY(P_CURRENT_DATE DATE --��������
                                   ) RETURN BOOLEAN;

  ------------------------------------------------------------------
  --�ж��Ƿ�Ϊ�·ݵ����һ��
  ------------------------------------------------------------------
  FUNCTION FUNC_IS_MONTH_LAST_DAY(P_CURRENT_DATE DATE --��������
                                  ) RETURN BOOLEAN;

  ------------------------------------------------------------------
  --�ж��Ƿ��һ�ν���
  ------------------------------------------------------------------
  FUNCTION FUNC_IS_FIRST_BALANCE(P_ORG_CODE     STRING, --��֯����
                                 P_BALANCE_TYPE STRING --��������
                                 ) RETURN BOOLEAN;

  ------------------------------------------------------------------
  --����ÿ���ڳ����
  ------------------------------------------------------------------
  FUNCTION FUNC_DAILY_DETAIL_BEGIN(P_BEGIN_DATE DATE, --��ʼ����                                   
                                   P_PERIOD     STRING --�����ڼ�
                                   ) RETURN BOOLEAN;

  --��ÿ�������ϸ���м��㣬�����µ׶���ϸ���л���
  FUNCTION FUNC_DAILY_Detail_END(P_BEGIN_DATE DATE, --��ʼ����
                                 P_END_DATE   DATE, --��������
                                 P_PERIOD     STRING --�����ڼ�
                                 ) RETURN BOOLEAN;

  ------------------------------------------------------------------
  --������ϸ��ĩ���
  ------------------------------------------------------------------
  FUNCTION FUNC_MONTH_DETAIL_END_AMOUNT(P_PERIOD STRING --�����ڼ�
                                        ) RETURN BOOLEAN;

  --�����¶����
  FUNCTION FUNC_MONTH_CUSTOMER_END_AMOUNT(P_PERIOD STRING --�����ڼ�
                                          ) RETURN BOOLEAN;

END PKG_STL_BALANCE_COMMON;
/
CREATE OR REPLACE PACKAGE BODY STV.PKG_STL_BALANCE_COMMON IS

  ------------------------------------------------------------------
  --�����ʼ��������(Ĭ�ϵ�ǰ���ڵ�00:00:00)
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_BEGIN_DATE(P_CURRENT_DATE DATE --��������
                               ) RETURN DATE IS
    --�������ؽ��
    V_RESULT DATE;
  BEGIN
    --���������Ϊ�գ�����д���
    IF P_CURRENT_DATE IS NOT NULL THEN
      --���������ڵ�ǰһ�죬ȥ��ʱ����
      V_RESULT := TRUNC(P_CURRENT_DATE);
    ELSE
      RAISE_APPLICATION_ERROR(-20100, '����Ϊ��!');
    END IF;
    RETURN V_RESULT;
  END;

  ------------------------------------------------------------------
  --��ý�������(Ĭ�ϵ�ǰ���ڵĵڶ���00:00:00)
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_END_DATE(P_CURRENT_DATE DATE --��������
                             ) RETURN DATE IS
    --�������ؽ��
    V_RESULT DATE;
  BEGIN
    --���������Ϊ�գ�����д���
    IF P_CURRENT_DATE IS NOT NULL THEN
      --���������� + 1��ȥ��ʱ����
      V_RESULT := TRUNC(P_CURRENT_DATE + 1);
    ELSE
      RAISE_APPLICATION_ERROR(-20100, '����Ϊ��!');
    END IF;
    RETURN V_RESULT;
  END;

  ------------------------------------------------------------------
  --��ý����ڼ�
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_PERIOD(P_DATE1 DATE --��ʼ����
                           ) RETURN STRING IS
  BEGIN
  
    --����YYYYMM��ʽ���ڼ�
    RETURN TO_CHAR(P_DATE1, 'yyyymm');
  END;

  ------------------------------------------------------------------
  --�������ڻ�ö�Ӧ�������ڵ��ڼ�
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_LAST_PERIOD(P_PERIOD_DATE DATE --�����Ӧ���ڼ�
                                ) RETURN STRING IS
  BEGIN
    --������һ�·� ���ڼ�
    RETURN TO_CHAR(ADD_MONTHS(P_PERIOD_DATE, -1), 'yyyymm');
  END;

  ------------------------------------------------------------------
  --��÷�ҳ���ҳ��
  ------------------------------------------------------------------
  FUNCTION FUNC_GET_PAGE_SIZE(P_ROW_NUM INT --ҳ����
                              ) RETURN INT IS
    --ҳ���С
    V_PAGE_SIZE INT;
    --ȡ��
    V_MOD_RESULT INT;
  BEGIN
    --ȡ��
    V_MOD_RESULT := MOD(P_ROW_NUM, PKG_STL_COMMON.MAX_PAGE_ROW_SIZE);
    --�쳣����
    IF V_MOD_RESULT = 0 THEN
      V_PAGE_SIZE := P_ROW_NUM / PKG_STL_COMMON.MAX_PAGE_ROW_SIZE;
    ELSE
      V_PAGE_SIZE := P_ROW_NUM / PKG_STL_COMMON.MAX_PAGE_ROW_SIZE + 1;
    END IF;
  
    RETURN V_PAGE_SIZE;
  END;

  ------------------------------------------------------------------
  --�ж��Ƿ�Ϊ�����ڼ�Ϊ1��
  ------------------------------------------------------------------
  FUNCTION FUNC_IS_JUNUARY(P_PERIOD STRING --�����ڼ�
                           ) RETURN BOOLEAN IS
  BEGIN
    --�ӵ�5���ַ���ʼ,��ȡ�·�
    RETURN SUBSTR(P_PERIOD, 5, 2) = '01';
  END;

  ------------------------------------------------------------------
  --�ж��Ƿ�Ϊ�·ݵĵ�һ��
  ------------------------------------------------------------------
  FUNCTION FUNC_IS_MONTH_FIRST_DAY(P_CURRENT_DATE DATE --��������
                                   ) RETURN BOOLEAN IS
  BEGIN
    RETURN TRUNC(P_CURRENT_DATE, 'MM') = TRUNC(P_CURRENT_DATE);
  END;

  ------------------------------------------------------------------
  --�ж��Ƿ�Ϊ�·ݵ����һ��
  ------------------------------------------------------------------
  FUNCTION FUNC_IS_MONTH_LAST_DAY(P_CURRENT_DATE DATE --��������
                                  ) RETURN BOOLEAN IS
  BEGIN
    RETURN TRUNC(LAST_DAY(P_CURRENT_DATE)) = TRUNC(P_CURRENT_DATE);
  END;

  ------------------------------------------------------------------
  --�ж��Ƿ��һ�ν���
  ------------------------------------------------------------------
  FUNCTION FUNC_IS_FIRST_BALANCE(P_ORG_CODE     STRING, --��֯����
                                 P_BALANCE_TYPE STRING --��������
                                 ) RETURN BOOLEAN IS
    --��ĩ�������
    V_BALANCE_ROWNUM INT;
  BEGIN
    SELECT COUNT(1)
      INTO V_BALANCE_ROWNUM
      FROM STV.T_STL_BALANCE
     WHERE
    --����֯
     CHECK_OUT_ORG_CODE = P_ORG_CODE
    --��������
     and STL_TYPE = P_BALANCE_TYPE
    --ֻ��ѯ1��
     AND ROWNUM = 1;
    --���ݷ��ؽ���ж��Ƿ�Ϊ��һ�ν���
    RETURN V_BALANCE_ROWNUM = 0;
  
    --��׽�쳣
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(SYSDATE,
                                        SYSDATE,
                                        SYSDATE,
                                        'pkg_stl_balance_common.FUNC_IS_FIRST_BALANCE',
                                        P_BALANCE_TYPE || '�ж��Ƿ��һ�ν���');
      --����ʧ�ܱ�־
      RETURN FALSE;
  END;

  ------------------------------------------------------------------
  --�����ڳ����
  ------------------------------------------------------------------
  FUNCTION FUNC_DAILY_DETAIL_BEGIN(P_BEGIN_DATE DATE, --��ʼ����
                                   P_PERIOD     STRING --�����ڼ�
                                   ) RETURN BOOLEAN IS
  
    --�Ƿ��һ�ν��в�����
    V_IS_FIRST_OPERATE BOOLEAN := false;
    --�����ڼ�
    V_PERIOD STRING(8);
    --��һ�����ڼ�
    V_LAST_PERIOD STRING(8);
    --��������
    V_BALANCE_STATUS_ROWS int;
  
  BEGIN
    --��ü����ڼ�
    V_PERIOD := P_PERIOD;
  
     /*    SELECT COUNT(1)
      INTO V_BALANCE_STATUS_ROWS
      FROM STV.T_STL_BALANCE_BATCH
     WHERE ROWNUM = 1
          --�Ѿ�����
       and status = pkg_stl_balance_constant.BALANCE_BATCH_END
          --ֻ��ѯ��Ч���ڼ�
       and active = Pkg_Stl_Common.YES;*/
  
    --�����һ����
    IF /*V_BALANCE_STATUS_ROWS = 0*/P_BEGIN_DATE<= PKG_STL_BALANCE_CONSTANT.BEGIN_BALANCE_DATE THEN
      --�ж��Ƿ��һ�ν���
      V_IS_FIRST_OPERATE := true;
    end if;
  
    --����ǣ��ӳ�ʼ���������ԭʼ����
    IF V_IS_FIRST_OPERATE = TRUE THEN
      --�����ʼ������ĩ����0�����
      INSERT INTO STV.T_STL_DAILY_BALANCE_tmp
      --ID,�ڼ�,��֯
        (ID,
         CHECK_OUT_PERIOD,
         CUSTOMER_CODE,
         CHECK_OUT_ORG_CODE,
         SOURCE_BILL_NO, --��Դ����
         SOURCE_BILL_TYPE, --��Դ�������
         STL_TYPE, -- ��������
         BILL_TYPE�� --����������
         PRODUCT_CODE,
         BEGIN_AMOUNT, --�ڳ�
         PRODUCE_AMOUNT, --����
         WRITEOFF_AMOUNT, --����
         END_AMOUNT, --��ĩ
         CURRENCY_CODE,
         ACTIVE,
         BALANCE_TIME, --��������
         CREATE_TIME, --��������
         business_date,
         payment_type,
         invoice_mark --��Ʊ���
         )
        SELECT SYS_GUID() AS ID,
               V_PERIOD AS CHECK_OUT_PERIOD,
               NVL(CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
               CHECK_OUT_ORG_CODE,
               NVL(SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO, --��Դ����
               NVL(SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE, --��Դ�������
               STL_TYPE AS STL_TYPE, -- ��������
               BILL_TYPE AS BILL_TYPE, --����������
               NVL(PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE,
               END_AMOUNT AS BEGIN_AMOUNT, --�����ڳ�
               0 AS PRODUCE_AMOUNT, --���ڷ�����
               0 AS WRITEOFF_AMOUNT, --���ں������
               0 AS END_AMOUNT, --������ĩ���
               PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
               PKG_STL_COMMON.ACTIVE AS ACTIVE,
               P_BEGIN_DATE AS BALANCE_TIME,
               SYSDATE AS CREATE_TIME,
               business_date as business_date,
               payment_type as payment_type,
               p.INVOICE_MARK
          FROM STV.t_Stl_Balance_Invoice_Init P
        --ֻҪ��Ч���ڳ����
         where p.stl_type in
               (PKG_STL_BALANCE_CONSTANT.BALANCE_TYPE_RECEIVABLE,
                Pkg_Stl_Balance_Constant.BALANCE_TYPE_PAYABLE,
                pkg_stl_balance_constant.BALANCE_TYPE_DEPOSIT_BILL,
                pkg_stl_balance_constant.BALANCE_TYPE_DEPOSIT_TF)
              
              --ֻ��Ҫ������0������Ϊ�����ڳ�
           and END_AMOUNT != 0;
      --�ύ
      commit;
    
    ELSE
      --��������һ����ĩ
      V_LAST_PERIOD := PKG_STL_BALANCE_COMMON.FUNC_GET_LAST_PERIOD(P_BEGIN_DATE);
    
      --�������ڴ���0�����
      INSERT INTO STV.T_STL_DAILY_BALANCE_tmp
      --ID,�ڼ�,��֯
        (ID,
         CHECK_OUT_PERIOD,
         CUSTOMER_CODE,
         CHECK_OUT_ORG_CODE,
         SOURCE_BILL_NO, --��Դ����
         SOURCE_BILL_TYPE, --��Դ�������
         STL_TYPE, -- ��������
         BILL_TYPE�� --����������
         PRODUCT_CODE,
         BEGIN_AMOUNT, --�ڳ�
         PRODUCE_AMOUNT, --����
         WRITEOFF_AMOUNT, --����
         END_AMOUNT, --��ĩ
         CURRENCY_CODE,
         ACTIVE,
         BALANCE_TIME, --��������
         CREATE_TIME, --��������
         business_date,
         payment_type,
         invoice_mark--��Ʊ���        
         )
        SELECT SYS_GUID() AS ID,
               V_PERIOD AS CHECK_OUT_PERIOD,
               NVL(CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
               CHECK_OUT_ORG_CODE,
               NVL(SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO, --��Դ����
               NVL(SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE, --��Դ�������
               STL_TYPE AS STL_TYPE, -- ��������
               BILL_TYPE AS BILL_TYPE, --����������
               NVL(PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE,
               END_AMOUNT AS BEGIN_AMOUNT, --�����ڳ�
               0 AS PRODUCE_AMOUNT, --���ڷ�����
               0 AS WRITEOFF_AMOUNT, --���ں������
               0 AS END_AMOUNT, --������ĩ���
               PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
               PKG_STL_COMMON.ACTIVE AS ACTIVE,
               P_BEGIN_DATE AS BALANCE_TIME,
               SYSDATE AS CREATE_TIME,
               business_date as business_date,
               payment_type as payment_type,
               p.invoice_mark as invoice_mark --��Ʊ���           
          FROM STV.T_STL_BALANCE_DETAIL P
        
         WHERE
        --������ĩ
         CHECK_OUT_PERIOD = V_LAST_PERIOD
        
        --ֻ��Ҫ������0������Ϊ�����ڳ�
         AND END_AMOUNT != 0;
    
      --�ύ
      commit;
    END IF;
    --Ĭ�Ϸ��سɹ�
    RETURN TRUE;
    --��׽�쳣
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_PERIOD,
                                        P_BEGIN_DATE,
                                        P_BEGIN_DATE,
                                        'PKG_STL_Balance_RECEIVABLE.func_daily_detail_begin',
                                        '��ĩ���˺����ڳ�ʱ�����쳣');
    
      --���سɹ���־
      RETURN FALSE;
  END;

  --����ͻ�ÿ�����
  FUNCTION FUNC_DAILY_Detail_END(P_BEGIN_DATE DATE, --��ʼ����
                                 P_END_DATE   DATE, --��������
                                 P_PERIOD     STRING --�����ڼ�
                                 ) RETURN BOOLEAN IS
  BEGIN
    INSERT INTO STV.T_STL_DAILY_BALANCE
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
       CHECK_OUT_ORG_CODE,
       STL_TYPE, -- ��������
       BILL_TYPE, --���㵥��������       
       SOURCE_BILL_NO, --��Դ����
       SOURCE_BILL_TYPE, --��Դ�������
       PRODUCT_CODE,       
       BEGIN_AMOUNT, --�����ڳ�
       PRODUCE_AMOUNT, --���ڷ�����
       WRITEOFF_AMOUNT, --���ں������
       END_AMOUNT, --������ĩ���      
       CURRENCY_CODE,
       ACTIVE,
       BALANCE_TIME,
       CREATE_TIME,
       business_date,
       payment_type,
       invoice_mark--��Ʊ���      
       )
      SELECT SYS_GUID() as ID,
             P_PERIOD as CHECK_OUT_PERIOD,
             CUSTOMER_CODE,
             CHECK_OUT_ORG_CODE,
             STL_TYPE,
             BILL_TYPE,
             SOURCE_BILL_NO,
             SOURCE_BILL_TYPE,
             PRODUCT_CODE,
             
             BEGIN_AMOUNT,
             PRODUCE_AMOUNT,
             WRITEOFF_AMOUNT,
             END_AMOUNT,
             
             PKG_STL_COMMON.CURRENCY_CODE_RMB as CURRENCY_CODE, -- �����
             PKG_STL_COMMON.ACTIVE            as ACTIVE,
             P_BEGIN_DATE                     as BALANCE_TIME,
             SYSDATE                          as CREATE_TIME,
             business_date,
             payment_type,
             invoice_mark as invoice_mark --��Ʊ���            
        from (select CUSTOMER_CODE as customer_code,
                     CHECK_OUT_ORG_CODE,
                     STL_TYPE, -- ��������
                     BILL_TYPE, --���㵥��������             
                     SOURCE_BILL_NO, --��Դ����
                     SOURCE_BILL_TYPE, --��Դ�������
                     PRODUCT_CODE,
                     SUM(BEGIN_AMOUNT) AS BEGIN_AMOUNT, --�����ڳ�
                     SUM(PRODUCE_AMOUNT) AS PRODUCE_AMOUNT, --���ڷ�����
                     SUM(WRITEOFF_AMOUNT) AS WRITEOFF_AMOUNT, --���ں������
                     SUM(BEGIN_AMOUNT) + SUM(PRODUCE_AMOUNT) -
                     SUM(WRITEOFF_AMOUNT) AS END_AMOUNT, --������ĩ                    
                     min(business_date) as business_date,
                     payment_type,
                     invoice_mark --��Ʊ���                    
                FROM STV.T_STL_DAILY_BALANCE_tmp
               WHERE CHECK_OUT_PERIOD = P_PERIOD --�����ڼ�
                 AND BALANCE_TIME >= P_BEGIN_DATE
                 AND BALANCE_TIME < P_END_DATE              
               GROUP BY CUSTOMER_CODE,
                        CHECK_OUT_ORG_CODE,
                        PRODUCT_CODE,
                        SOURCE_BILL_NO, --��Դ����
                        SOURCE_BILL_TYPE, --��Դ�������
                        payment_type, --���ʽ
                        STL_TYPE, -- ��������
                        BILL_TYPE,--���㵥��������
                        invoice_mark --��Ʊ���
                        ); 
    --�ύ
    commit;
    RETURN TRUE;
    --��׽�쳣
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_BALANCE_COMMON.FUNC_DAILY_CUSTOMER_END_AMOUNT',
                                        '����ͻ�ÿ�������ϸ');
      --����ʧ�ܱ�־
      RETURN FALSE;
  END;

  ------------------------------------------------------------------
  --����ÿ����ϸ��ĩ���
  ------------------------------------------------------------------
  FUNCTION FUNC_MONTH_DETAIL_END_AMOUNT(P_PERIOD STRING --�����ڼ�
                                        ) RETURN BOOLEAN IS
  
    --�����ڼ�
    V_PERIOD STRING(8);
    --ϵͳʱ��
    V_SYSDATE DATE;
  
    --��������
    V_PERIOD_DATE DATE;
  
    --��ʼ����
    v_begin_date DATE;
  
    --��ʼ����
    v_end_date DATE;
  
  BEGIN
    --��ü����ڼ�
    V_PERIOD := P_PERIOD;
  
    --���ϵͳʱ��
    V_SYSDATE := SYSDATE();
  
    --��ý����ڼ��Ӧ������(���һ��)
    V_PERIOD_DATE := LAST_DAY(TO_DATE(P_PERIOD, 'yyyymm'));
  
    --��ʼ����
    v_begin_date := TO_DATE(P_PERIOD, 'yyyymm');
  
    --��������(���ڼ����һ��)
    v_end_date := TRUNC(LAST_DAY(V_PERIOD_DATE));
  
    --��������
    INSERT INTO STV.T_STL_BALANCE_DETAIL
    --ID,�ڼ�,��֯
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
       customer_name,
       CHECK_OUT_ORG_CODE,
       CHECK_OUT_ORG_name,
       CHECK_OUT_Com_CODE,
       CHECK_OUT_Com_Name,
       SOURCE_BILL_NO, --��Դ����
       SOURCE_BILL_TYPE, --��Դ�������
       STL_TYPE, -- ��������
       BILL_TYPE, --���㵥��������
       PRODUCT_CODE,
       BEGIN_AMOUNT, --�����ڳ�
       PRODUCE_AMOUNT, --���ڷ�����
       WRITEOFF_AMOUNT, --���ں������
       END_AMOUNT, --������ĩ���
       CURRENCY_CODE,
       ACTIVE,
       BALANCE_TIME,
       CREATE_TIME,
       business_date,
       payment_type, --���ʽ
       aging_days, --��������
       invoice_mark,--��Ʊ���
       is_init --�Ƿ��ʼ��
       )
      SELECT SYS_GUID() as ID,
             V_PERIOD as CHECK_OUT_PERIOD,
             CUSTOMER_CODE as CUSTOMER_CODE,
             CUSTOMER.NAME as customer_name,
             CHECK_OUT_ORG_CODE as CHECK_OUT_ORG_CODE,
             DEPT.name as CHECK_OUT_ORG_Name,
             FINORG.CODE as CHECK_OUT_Com_CODE,
             FINORG.name as CHECK_OUT_Com_name,
             SOURCE_BILL_NO as SOURCE_BILL_NO,
             SOURCE_BILL_TYPE as SOURCE_BILL_TYPE,
             STL_TYPE as STL_TYPE, -- ��������
             BILL_TYPE as BILL_TYPE, --���㵥��������
             PRODUCT_CODE as PRODUCT_CODE,
             BEGIN_AMOUNT as BEGIN_AMOUNT,
             PRODUCE_AMOUNT as PRODUCE_AMOUNT,
             WRITEOFF_AMOUNT as WRITEOFF_AMOUNT,
             END_AMOUNT AS END_AMOUNT,
             PKG_STL_COMMON.CURRENCY_CODE_RMB as CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE as ACTIVE,
             V_PERIOD_DATE as BALANCE_TIME, --��ʼ�����ڼ�
             V_SYSDATE as CREATE_TIME,
             business_date as business_date, --ҵ������
             payment_type,
             (trunc(V_PERIOD_DATE) - trunc(business_date)) aging_days, --����(��������-ҵ������)
             invoice_mark as invoice_mark,--��Ʊ���
             pkg_stl_common.NO as is_init --�Ƿ��ʼ��             
        FROM (SELECT CUSTOMER_CODE,
                     CHECK_OUT_ORG_CODE,
                     SOURCE_BILL_NO, --��Դ����
                     SOURCE_BILL_TYPE, --��Դ�������
                     STL_TYPE, -- ��������
                     BILL_TYPE, --���㵥��������
                     PRODUCT_CODE,
                     SUM(BEGIN_AMOUNT) AS BEGIN_AMOUNT, --�����ڳ�
                     SUM(PRODUCE_AMOUNT) AS PRODUCE_AMOUNT, --���ڷ�����
                     SUM(WRITEOFF_AMOUNT) AS WRITEOFF_AMOUNT, --���ں������
                     SUM(END_AMOUNT) AS END_AMOUNT, --���ں������
                     min(business_date) as business_date,
                     payment_type as payment_type,
                     invoice_mark as invoice_mark --��Ʊ���
                FROM STV.T_STL_DAILY_BALANCE
               WHERE CHECK_OUT_PERIOD = P_PERIOD --�����ڼ�              
               GROUP BY CUSTOMER_CODE,
                        CHECK_OUT_ORG_CODE,
                        PRODUCT_CODE,
                        SOURCE_BILL_NO, --��Դ����
                        SOURCE_BILL_TYPE, --��Դ�������
                        STL_TYPE, -- ��������
                        payment_type,
                        BILL_TYPE,
                        invoice_mark --��Ʊ���
                        ) bal --���㵥��������
      --�����ͻ�(�̶��ͻ���ɢ�͡�����)
        LEFT JOIN STV.MV_STV_CUSTOMER CUSTOMER
          ON CUSTOMER.CODE = BAL.CUSTOMER_CODE
      --�������� 
      --��ʱ���ҵ���Ӧ���ű��뼰��˾����Ҫ����һ����֯��һ�����ڻ����޸ĵ�����  
        left JOIN (select CODE,
                          name,
                          SUBSIDIARY_CODE,
                          row_number() over(partition by CODE order by CREATE_TIME desc) as rn
                     from BSE.T_BAS_ORG
                   --���µ�һ��
                    where TRUNC(CREATE_TIME, 'MM') <= v_begin_date
                      AND trunc(MODIFY_TIME) >= v_end_date) DEPT
          ON DEPT.CODE = BAL.CHECK_OUT_ORG_CODE
         and DEPT.rn = 1
      
      --������˾
        LEFT JOIN (select CODE,
                          name,
                          row_number() over(partition by CODE order by CREATE_TIME desc) as rn
                     from BSE.T_BAS_FIN_ORG
                    where TRUNC(CREATE_TIME, 'MM') <= v_begin_date
                      AND trunc(MODIFY_TIME) >= v_end_date) FINORG
          ON FINORG.CODE = DEPT.SUBSIDIARY_CODE
         and FINORG.rn = 1;
  
    --�ύ
    commit;
    --��Ҫ�����ͻ�
    RETURN TRUE;
  
    --��׽�쳣
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_PERIOD,
                                        V_PERIOD_DATE,
                                        V_PERIOD_DATE,
                                        'PKG_STL_BALANCE_COMMON.FUNC_MONTH_DETAIL_END_AMOUNT',
                                        '�����¶���ϸ��ĩ���');
      --����ʧ�ܱ�־
      RETURN FALSE;
    
  END;

  --����ͻ��¶����
  FUNCTION FUNC_MONTH_CUSTOMER_END_AMOUNT(P_PERIOD STRING --�����ڼ�
                                          ) RETURN BOOLEAN IS
    --ϵͳʱ��
    V_SYSDATE DATE;
  
    --�����ڼ�
    V_PERIOD STRING(8);
  
    --�ж��·��Ƿ�Ϊ��һ����
    V_IS_JANUARY BOOLEAN;
  
    --��һ�����ڼ�
    V_LAST_PERIOD STRING(8);
  
    --��ý����ڼ��Ӧ������
    V_PERIOD_DATE DATE;
  
  BEGIN
  
    --���ϵͳʱ��
    V_SYSDATE := SYSDATE();
  
    --��õ�ǰ�ڼ�
    V_PERIOD := P_PERIOD;
  
    --����ڼ��Ӧ������
    V_PERIOD_DATE := TRUNC(LAST_DAY(TO_DATE(P_PERIOD, 'yyyymm')));
  
    --�����һ�����ڼ�
    V_LAST_PERIOD := PKG_STL_BALANCE_COMMON.FUNC_GET_LAST_PERIOD(V_PERIOD_DATE);
  
    --��õ�ǰ�ڼ���·ݣ��Ƿ�Ϊ1��
    V_IS_JANUARY := PKG_STL_BALANCE_COMMON.FUNC_IS_JUNUARY(V_PERIOD);
  
    --�����һ�£���ȷ����� = ���ڷ�����
    IF V_IS_JANUARY THEN
      INSERT INTO STV.T_STL_BALANCE
        (ID,
         BILL_TYPE,
         STL_TYPE,
         CHECK_OUT_PERIOD,
         CHECK_OUT_ORG_CODE,
         CHECK_OUT_ORG_NAME, --�����֯
         CHECK_OUT_COM_CODE, --��˾
         CHECK_OUT_COM_NAME,
         CUSTOMER_CODE,
         CUSTOMER_NAME,
         PRODUCT_CODE,
         BEGIN_AMOUNT,
         PRODUCE_AMOUNT,
         WRITEOFF_AMOUNT,
         END_AMOUNT,
         TOTAL_PRODUCE_AMOUNT,
         TOTAL_WRITEOFF_AMOUNT,
         CREATE_TIME,
         BALANCE_TIME, -- ����ʱ��
         business_date, --ҵ������
         payment_type, --���ʽ
         aging_days, --��������
         ACTIVE, --�Ƿ���Ч
         CURRENCY_CODE, --���Ҵ���
         invoice_mark --��Ʊ���
         )
      --���ӿͻ����ơ���˾���ơ����ű���
        SELECT SYS_GUID() AS ID,
               BAL.BILL_TYPE AS BILL_TYPE,
               BAL.STL_TYPE AS STL_TYPE,
               P_PERIOD AS CHECK_OUT_PERIOD,
               BAL.CHECK_OUT_ORG_CODE AS CHECK_OUT_ORG_CODE,
               bal.CHECK_OUT_ORG_NAME AS CHECK_OUT_ORG_NAME, --������֯����
               bal.CHECK_OUT_COM_CODE AS CHECK_OUT_COM_CODE, --�ӹ�˾����
               bal.CHECK_OUT_COM_Name AS CHECK_OUT_COM_NAME,
               BAL.CUSTOMER_CODE AS CUSTOMER_CODE,
               nvl(BAL.CUSTOMER_Name, PKG_stl_common.NULL_VALUE) AS CUSTOMER_NAME,
               BAL.PRODUCT_CODE AS PRODUCT_CODE,
               BAL.BEGIN_AMOUNT AS BEGIN_AMOUNT,
               BAL.PRODUCE_AMOUNT AS PRODUCE_AMOUNT,
               BAL.WRITEOFF_AMOUNT AS WRITEOFF_AMOUNT,
               BAL.END_AMOUNT AS END_AMOUNT,
               BAL.PRODUCE_AMOUNT AS TOTAL_PRODUCE_AMOUNT, --���ڷ���
               BAL.WRITEOFF_AMOUNT AS TOTAL_WRITEOFF_AMOUNT, --���ں���
               V_SYSDATE AS CREATE_TIME,
               V_PERIOD_DATE AS BALANCE_TIME,
               business_date as business_date, --ҵ������
               payment_type as payment_type, --���ʽ
               AGING_DAYS as AGING_DAYS, --��������
               PKG_STL_COMMON.ACTIVE AS ACTIVE,
               PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE, -- �����
               invoice_mark --��Ʊ���
          FROM (SELECT BILL_TYPE,
                       STL_TYPE,
                       D.CHECK_OUT_ORG_CODE,
                       D.CHECK_OUT_ORG_name,
                       D.CHECK_OUT_Com_CODE,
                       D.CHECK_OUT_Com_Name,
                       CUSTOMER_CODE,
                       CUSTOMER_Name,
                       PRODUCT_CODE,
                       SUM(BEGIN_AMOUNT) AS BEGIN_AMOUNT,
                       SUM(PRODUCE_AMOUNT) AS PRODUCE_AMOUNT,
                       SUM(WRITEOFF_AMOUNT) AS WRITEOFF_AMOUNT,
                       SUM(END_AMOUNT) AS END_AMOUNT,
                       min(business_date) as business_date,
                       max(d.AGING_DAYS) as AGING_DAYS, --��������
                       payment_type,
                       d.invoice_mark as invoice_mark --��Ʊ���
                  FROM STV.T_STL_BALANCE_DETAIL D
                 WHERE D.CHECK_OUT_PERIOD = P_PERIOD --�����ڼ�                
                 GROUP BY D.CHECK_OUT_ORG_CODE,
                          D.CHECK_OUT_ORG_name,
                          D.CHECK_OUT_Com_CODE,
                          D.CHECK_OUT_Com_Name,
                          CUSTOMER_CODE,
                          CUSTOMER_Name,
                          STL_TYPE,
                          BILL_TYPE,
                          payment_type,
                          PRODUCT_CODE,
                          invoice_mark --��Ʊ���
                          ) BAL;
    
      --�ύ
      commit;
    
    ELSE
      --����������ĩ + ���ڷ�����
      INSERT INTO STV.T_STL_BALANCE
        (ID,
         BILL_TYPE,
         STL_TYPE,
         CHECK_OUT_PERIOD,
         
         CHECK_OUT_ORG_CODE,
         CHECK_OUT_ORG_NAME, --�����֯
         CHECK_OUT_COM_CODE, --��˾
         CHECK_OUT_COM_NAME,
         CUSTOMER_CODE,
         CUSTOMER_NAME,
         
         PRODUCT_CODE,
         
         CREATE_TIME,
         BALANCE_TIME, -- ����ʱ��
         business_date, --ҵ������
         payment_type, --���ʽ
         aging_days, --����
         ACTIVE, --�Ƿ���Ч
         CURRENCY_CODE, --���Ҵ���
         
         BEGIN_AMOUNT,
         PRODUCE_AMOUNT,
         WRITEOFF_AMOUNT,
         END_AMOUNT,
         TOTAL_PRODUCE_AMOUNT,
         TOTAL_WRITEOFF_AMOUNT,
         invoice_mark --��Ʊ���
         )
        SELECT SYS_GUID() AS ID,
               BAL.BILL_TYPE,
               BAL.STL_TYPE,
               P_PERIOD,
               
               BAL.CHECK_OUT_ORG_CODE AS CHECK_OUT_ORG_CODE,
               BAL.CHECK_OUT_ORG_Name AS CHECK_OUT_ORG_NAME, --������֯����
               
               BAL.CHECK_OUT_Com_Code AS CHECK_OUT_COM_CODE,
               BAL.CHECK_OUT_Com_Name AS CHECK_OUT_COM_NAME,
               
               BAL.CUSTOMER_CODE AS CUSTOMER_CODE,
               BAL.CUSTOMER_Name AS CUSTOMER_NAME,
               
               BAL.PRODUCT_CODE as PRODUCT_CODE,
               
               V_SYSDATE                        as CREATE_TIME,
               V_PERIOD_DATE                    as BALANCE_TIME,
               BAL.business_date                as business_date, --ҵ������
               BAL.payment_type                 as payment_type, --���ʽ
               BAL.aging_days                   as aging_days, --��������
               PKG_STL_COMMON.ACTIVE            as active,
               PKG_STL_COMMON.CURRENCY_CODE_RMB as CURRENCY_CODE, -- �����
               
               BAL.BEGIN_AMOUNT as BEGIN_AMOUNT,
               BAL.PRODUCE_AMOUNT as PRODUCE_AMOUNT,
               BAL.WRITEOFF_AMOUNT as WRITEOFF_AMOUNT,
               BAL.END_AMOUNT as END_AMOUNT,
               BAL.PRODUCE_AMOUNT + nvl(B.TOTAL_PRODUCE_AMOUNT, 0) as TOTAL_PRODUCE_AMOUNT,
               BAL.WRITEOFF_AMOUNT + nvl(B.TOTAL_WRITEOFF_AMOUNT, 0) as TOTAL_WRITEOFF_AMOUNT,
               BAL.invoice_mark --��Ʊ���
          FROM (SELECT D.BILL_TYPE,
                       D.STL_TYPE,
                       payment_type as payment_type,
                       min(business_date) as business_date,
                       max(aging_days) as aging_days, --��������
                       D.CHECK_OUT_ORG_CODE,
                       D.CHECK_OUT_ORG_name,
                       D.CHECK_OUT_Com_CODE,
                       D.CHECK_OUT_Com_Name,
                       D.CUSTOMER_CODE,
                       D.CUSTOMER_name,
                       D.PRODUCT_CODE,
                       SUM(nvl(D.BEGIN_AMOUNT, 0)) AS BEGIN_AMOUNT,
                       SUM(nvl(D.PRODUCE_AMOUNT, 0)) AS PRODUCE_AMOUNT,
                       SUM(nvl(D.WRITEOFF_AMOUNT, 0)) AS WRITEOFF_AMOUNT,
                       SUM(nvl(D.END_AMOUNT, 0)) AS END_AMOUNT,
                       d.invoice_mark as invoice_mark --��Ʊ���
                  FROM STV.T_STL_BALANCE_DETAIL D
                 WHERE D.CHECK_OUT_PERIOD = P_PERIOD --�����ڼ�                   
                 GROUP BY D.CHECK_OUT_ORG_CODE,
                          D.CHECK_OUT_ORG_name,
                          D.CHECK_OUT_Com_CODE,
                          D.CHECK_OUT_Com_Name,
                          CUSTOMER_CODE,
                          CUSTOMER_Name,
                          STL_TYPE,
                          BILL_TYPE,
                          PRODUCT_CODE,
                          payment_type,
                          invoice_mark --��Ʊ���
                          ) BAL
        --����������ĩ
          left join STV.T_STL_BALANCE B
            on B.CUSTOMER_CODE = BAL.CUSTOMER_CODE --�ͻ�
              --��֯
           AND B.CHECK_OUT_ORG_CODE = BAL.CHECK_OUT_ORG_CODE
              --��������
           AND B.STL_TYPE = BAL.STL_TYPE
              --����������
           AND B.BILL_TYPE = BAL.BILL_TYPE
              --���ʽ��ͬ
           and b.payment_type = BAL.payment_type
              --��Ʒ������ͬ
           and b.product_code = BAL.product_code
              --������ĩ
           AND B.CHECK_OUT_PERIOD = V_LAST_PERIOD
           AND B.INVOICE_MARK = BAL.invoice_mark
           ;
    
      --�ύ
      commit;
    
    END IF;
    RETURN TRUE;
    --��׽�쳣
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_PERIOD,
                                        V_SYSDATE,
                                        V_SYSDATE,
                                        'PKG_STL_BALANCE_COMMON.FUNC_MONTH_CUSTOMER_END_AMOUNT',
                                        '����ͻ��¶���ĩ���');
      --����ʧ�ܱ�־
      RETURN FALSE;
  END;

END PKG_STL_BALANCE_COMMON;
/
