  truncate table  STV.T_STL_BALANCE_DETAIL;
  truncate table STV.T_STL_BALANCE;
--��������
    INSERT INTO STV.T_STL_BALANCE_DETAIL
    --ID,�ڼ�,��֯
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
       customer_name,
       CHECK_OUT_ORG_CODE,
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
       aging_days --��������
       )
      SELECT SYS_GUID() as ID,
             '201306' as CHECK_OUT_PERIOD,
             CUSTOMER_CODE as CUSTOMER_CODE,
             CUSTOMER.NAME as customer_name,
             CHECK_OUT_ORG_CODE as CHECK_OUT_ORG_CODE,
             SOURCE_BILL_NO as SOURCE_BILL_NO,
             SOURCE_BILL_TYPE as SOURCE_BILL_TYPE,
             STL_TYPE as STL_TYPE, -- ��������
             BILL_TYPE as BILL_TYPE, --���㵥��������
             PRODUCT_CODE as PRODUCT_CODE,
             BEGIN_AMOUNT as BEGIN_AMOUNT,
             PRODUCE_AMOUNT as PRODUCE_AMOUNT,
             WRITEOFF_AMOUNT as WRITEOFF_AMOUNT,
             BEGIN_AMOUNT + PRODUCE_AMOUNT - WRITEOFF_AMOUNT AS END_AMOUNT,
             'RMB' as CURRENCY_CODE,
             'Y' as ACTIVE,
             to_date('20130630','yyyymmdd') as BALANCE_TIME, --��ʼ�����ڼ�
             sysdate as CREATE_TIME,
             business_date as business_date, --ҵ������
             payment_type,
             (trunc(to_date('20130630','yyyymmdd')) - trunc(business_date)) aging_days --����(��������-ҵ������)             
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
                     payment_type as payment_type
                FROM STV.T_STL_DAILY_BALANCE
               WHERE CHECK_OUT_PERIOD = '201306' --�����ڼ�
               GROUP BY CUSTOMER_CODE,
                        CHECK_OUT_ORG_CODE,
                        PRODUCT_CODE,
                        SOURCE_BILL_NO, --��Դ����
                        SOURCE_BILL_TYPE, --��Դ�������
                        STL_TYPE, -- ��������
                        payment_type,
                        BILL_TYPE) bal --���㵥��������
      --�����ͻ�(�̶��ͻ���ɢ�͡�����)
        LEFT JOIN STV.MV_STV_CUSTOMER CUSTOMER
          ON CUSTOMER.CODE = BAL.CUSTOMER_CODE;
          
          commit;
          
          
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
         TOTAL_WRITEOFF_AMOUNT)
        SELECT SYS_GUID() AS ID,
               BAL.BILL_TYPE,
               BAL.STL_TYPE,
               '201306',
               
               BAL.CHECK_OUT_ORG_CODE AS CHECK_OUT_ORG_CODE,
               DEPT.NAME              AS CHECK_OUT_ORG_NAME, --������֯����
               
               FINORG.CODE AS CHECK_OUT_COM_CODE,
               FINORG.NAME AS CHECK_OUT_COM_NAME,
               
               BAL.CUSTOMER_CODE AS CUSTOMER_CODE,
               BAL.CUSTOMER_Name AS CUSTOMER_NAME,
               
               BAL.PRODUCT_CODE as PRODUCT_CODE,
               
               sysdate                        as CREATE_TIME,
               tO_date('20130630','yyyymmdd')                    as BALANCE_TIME,
               BAL.business_date                as business_date, --ҵ������
               BAL.payment_type                 as payment_type, --���ʽ
               BAL.aging_days                   as aging_days, --��������
               'Y'            as active,
               'RMB' as CURRENCY_CODE, -- �����
               
               BAL.BEGIN_AMOUNT as BEGIN_AMOUNT,
               BAL.PRODUCE_AMOUNT as PRODUCE_AMOUNT,
               BAL.WRITEOFF_AMOUNT as WRITEOFF_AMOUNT,
               BAL.END_AMOUNT as END_AMOUNT,
               BAL.PRODUCE_AMOUNT + nvl(B.TOTAL_PRODUCE_AMOUNT, 0) as TOTAL_PRODUCE_AMOUNT,
               BAL.WRITEOFF_AMOUNT + nvl(B.TOTAL_WRITEOFF_AMOUNT, 0) as TOTAL_WRITEOFF_AMOUNT
          FROM (SELECT D.BILL_TYPE,
                       D.STL_TYPE,
                       payment_type as payment_type,
                       min(business_date) as business_date,
                       max(aging_days) as aging_days, --��������
                       D.CHECK_OUT_ORG_CODE,
                       D.CUSTOMER_CODE,
                       D.CUSTOMER_name,
                       D.PRODUCT_CODE,
                       SUM(nvl(D.BEGIN_AMOUNT, 0)) AS BEGIN_AMOUNT,
                       SUM(nvl(D.PRODUCE_AMOUNT, 0)) AS PRODUCE_AMOUNT,
                       SUM(nvl(D.WRITEOFF_AMOUNT, 0)) AS WRITEOFF_AMOUNT,
                       SUM(nvl(D.END_AMOUNT, 0)) AS END_AMOUNT
                  FROM STV.T_STL_BALANCE_DETAIL D
                 WHERE D.CHECK_OUT_PERIOD = '201306' --�����ڼ�
                 GROUP BY CHECK_OUT_ORG_CODE,
                          CUSTOMER_CODE,
                          CUSTOMER_Name,
                          STL_TYPE,
                          BILL_TYPE,
                          PRODUCT_CODE,
                          payment_type) BAL
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
           AND B.CHECK_OUT_PERIOD = '201305'
        --�������� 
        --��ʱ���ҵ���Ӧ���ű��뼰��˾����Ҫ����һ����֯��һ�����ڻ����޸ĵ�����
          left JOIN (select CODE,
                            name,
                            SUBSIDIARY_CODE,
                            row_number() over(partition by CODE order by CREATE_TIME desc) as rn
                       from BSE.T_BAS_ORG
                      where  TRUNC(CREATE_TIME, 'MM') <= to_date('20130601','yyyymmdd')
                        AND trunc(MODIFY_TIME) >= to_date('20130630','yyyymmdd')) DEPT
            ON DEPT.CODE = BAL.CHECK_OUT_ORG_CODE
           and rn = 1
        
        --������˾
          LEFT JOIN (select CODE,
                            name,
                            row_number() over(partition by CODE order by CREATE_TIME desc) as rn
                       from BSE.T_BAS_FIN_ORG
                      where  TRUNC(CREATE_TIME, 'MM') <= to_date('20130601','yyyymmdd')
                        AND trunc(MODIFY_TIME) >= to_date('20130630','yyyymmdd')) FINORG
            ON FINORG.CODE = DEPT.SUBSIDIARY_CODE
           and DEPT.rn = 1;
           commit;
