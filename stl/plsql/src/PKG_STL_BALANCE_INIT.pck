CREATE OR REPLACE PACKAGE PKG_STL_BALANCE_INIT IS
  --���˳�ʼ������
  --���ߣ���С��
  --����ʱ�䣺2013-06-21
  --��ʼ���ڳ�

  --��ʼ��Ԥ��
  function FUNC_INIT_DEPOSIT_BILL(P_ORG_CODE     STRING, --��֯����
                                  P_BEGIN_DATE   DATE, --��ʼ����
                                  P_PERIOD       STRING, --�����ڼ�
                                  P_BALANCE_TYPE STRING -- ��������
                                  ) return boolean;

  --��ʼ��Ӧ��δǩ�գ�Ԥ����ת��
  function FUNC_INIT_DEPOSIT_TF(P_ORG_CODE     STRING, --��֯����
                                P_BEGIN_DATE   DATE, --��ʼ����
                                P_PERIOD       STRING, --�����ڼ�
                                P_BALANCE_TYPE STRING -- ��������
                                ) return boolean;

  --��ʼ��Ӧ����ǩ��
  function FUNC_INIT_receivable(P_ORG_CODE     STRING, --��֯����
                                P_BEGIN_DATE   DATE, --��ʼ����
                                P_PERIOD       STRING, --�����ڼ�
                                P_BALANCE_TYPE STRING -- ��������
                                ) return boolean;

  --��ʼ��Ӧ��
  function FUNC_INIT_payable(P_ORG_CODE     STRING, --��֯����
                             P_BEGIN_DATE   DATE, --��ʼ����
                             P_PERIOD       STRING, --�����ڼ�
                             P_BALANCE_TYPE STRING -- ��������
                             ) return boolean;

end PKG_STL_BALANCE_INIT;
/
CREATE OR REPLACE PACKAGE body PKG_STL_BALANCE_INIT IS
  --���˳�ʼ������
  --���ߣ���С��
  --����ʱ�䣺2013-06-21
  --��ʼ���ڳ�

  --��ʼ��Ԥ��
  function FUNC_INIT_DEPOSIT_BILL(P_ORG_CODE     STRING, --��֯����
                                  P_BEGIN_DATE   DATE, --��ʼ����
                                  P_PERIOD       STRING, --�����ڼ�
                                  P_BALANCE_TYPE STRING -- ��������
                                  ) return boolean is
  begin
    --���ɳ�ʼ����Ԥ��
    INSERT INTO STV.t_stl_balance_Detail_INIT_Tmp B
    --ID,�ڼ�,��֯
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
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
       payment_type)
    
    --���ڷ������
      SELECT SYS_GUID() AS ID,
             P_PERIOD AS CHECK_OUT_PERIOD,
             NVL(BILL.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             BILL.Generating_Org_Code,
             --DDW 2013-06-18 ��Ԥ����ϸ�������Դ����
             BILL.DEPOSIT_RECEIVED_NO  AS SOURCE_BILL_NO, --��Դ����
             PKG_STL_COMMON.NULL_VALUE AS SOURCE_BILL_TYPE, --��Դ�������
             --��������
             P_BALANCE_TYPE as STL_TYPE,
             BILL.BILL_TYPE, --����������
             --DDW 2013-06-18 ��Ԥ����ϸ����Ӳ�Ʒ����
             BILL.TRANSPORT_TYPE AS PRODUCT_CODE,
             0 AS BEGIN_AMOUNT, --�����ڳ�
             SUM(AMOUNT) AS PRODUCE_AMOUNT, --���ڷ�����
             0 AS WRITEOFF_AMOUNT, --���ں������
             0 AS END_AMOUNT, --������ĩ���
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE, -- �����
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME, --��ʼ������Ϊ����
             SYSDATE AS CREATE_TIME,
             min(business_date) as business_date,
             payment_type as payment_type
        FROM STL.T_STL_BILL_DEPOSIT_RECEIVED BILL
       WHERE
      --��ѯ��ʼ����Ԥ��
       BILL.IS_INIT = PKG_STL_COMMON.YES
      --Ӧ����֯
       AND BILL.generating_org_Code = P_ORG_CODE
       GROUP BY CUSTOMER_CODE,
                bill.generating_org_Code,
                BILL.DEPOSIT_RECEIVED_NO,
                BILL.TRANSPORT_TYPE,
                BILL_TYPE, -- ��������
                payment_type --���ʽ
      HAVING SUM(AMOUNT) != 0;
  
    --���ɳ�ʼ���ĺ���(��Ϊ����)
    INSERT INTO STV.t_stl_balance_Detail_INIT_Tmp
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
       BILL_TYPE, -- ���㵥������
       CURRENCY_CODE, --�ұ�
       ACTIVE, --�Ƿ���Ч
       BALANCE_TIME, --��������
       CREATE_TIME,
       business_date,
       payment_type)
      SELECT SYS_GUID() AS ID,
             p_PERIOD AS CHECK_OUT_PERIOD,
             NVL(SBW.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             SBW.ORG_CODE AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SUM(SBW.AMOUNT) AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             --DDW 2013-06-18 ��Ԥ�ս�����Ӳ�Ʒ���ͺ���Դ����
             B.TRANSPORT_TYPE AS PRODUCT_CODE, --��Ʒ����
             B.DEPOSIT_RECEIVED_NO AS SOURCE_BILL_NO,
             PKG_STL_COMMON.NULL_VALUE AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --��������
             B.BILL_TYPE AS BILL_TYPE,
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             min(b.business_date) as business_date,
             b.payment_type as payment_type
        FROM STL.T_STL_BILL_WRITEOFF SBW
      --����Ӧ�յ�
       INNER JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED B
          ON B.id = SBW.BEGIN_Id --��������
       WHERE sbw.ORG_CODE = B.Generating_Org_Code
         and b.generating_org_code = P_ORG_CODE
            --��ʼ���ĵ���
         AND b.IS_INIT = PKG_STL_COMMON.YES
       GROUP BY SBW.CUSTOMER_CODE,
                SBW.ORG_CODE,
                b.payment_type,
                b.DEPOSIT_RECEIVED_NO,
                b.TRANSPORT_TYPE,
                B.BILL_TYPE -- Ӧ�յ�����
      HAVING SUM(SBW.AMOUNT) != 0;
  
    --Ԥ��(��Ϊ������)
    INSERT INTO STV.t_stl_balance_Detail_INIT_Tmp
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
       BILL_TYPE, -- ���㵥������
       CURRENCY_CODE, --�ұ�
       ACTIVE, --�Ƿ���Ч
       BALANCE_TIME, --��������
       CREATE_TIME,
       business_date,
       payment_type)
      SELECT SYS_GUID() AS ID,
             p_PERIOD AS CHECK_OUT_PERIOD,
             NVL(SBW.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             SBW.ORG_CODE AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SUM(SBW.AMOUNT) AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             --DDW 2013-06-18 ��Ԥ�ս�����Ӳ�Ʒ���ͺ���Դ����
             B.TRANSPORT_TYPE AS PRODUCT_CODE, --��Ʒ����
             B.DEPOSIT_RECEIVED_NO AS SOURCE_BILL_NO,
             PKG_STL_COMMON.NULL_VALUE AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --��������
             B.BILL_TYPE AS BILL_TYPE,
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             min(b.business_date) as business_date,
             b.payment_type as payment_type
        FROM STL.T_STL_BILL_WRITEOFF SBW
      --����Ӧ�յ�
       INNER JOIN STL.T_STL_BILL_DEPOSIT_RECEIVED B
          ON B.id = SBW.END_Id --��������
       WHERE sbw.ORG_CODE = B.Generating_Org_Code
         and b.generating_org_code = P_ORG_CODE
            --��ʼ���ĵ���
         AND b.IS_INIT = PKG_STL_COMMON.YES
       GROUP BY SBW.CUSTOMER_CODE,
                SBW.ORG_CODE,
                b.payment_type,
                b.DEPOSIT_RECEIVED_NO,
                b.TRANSPORT_TYPE,
                B.BILL_TYPE -- Ӧ�յ�����
      HAVING SUM(SBW.AMOUNT) != 0;
  
    --���ɳ�ʼ������ĩ
    INSERT INTO STV.t_stl_balance_Detail_INIT
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
       payment_type)
      SELECT SYS_GUID(),
             P_PERIOD,
             CUSTOMER_CODE,
             CHECK_OUT_ORG_CODE,
             STL_TYPE, -- ��������
             BILL_TYPE, --���㵥��������
             SOURCE_BILL_NO, --��Դ����
             SOURCE_BILL_TYPE, --��Դ�������
             PRODUCT_CODE,
             0 AS BEGIN_AMOUNT, --�����ڳ�
             SUM(PRODUCE_AMOUNT) AS PRODUCE_AMOUNT, --���ڷ�����
             SUM(WRITEOFF_AMOUNT) AS WRITEOFF_AMOUNT, --���ں������
             SUM(PRODUCE_AMOUNT) - SUM(WRITEOFF_AMOUNT) AS END_AMOUNT, --������ĩ
             PKG_STL_COMMON.CURRENCY_CODE_RMB as CURRENCY_CODE, -- �����
             PKG_STL_COMMON.ACTIVE as ACTIVE,
             P_BEGIN_DATE as BALANCE_TIME,
             SYSDATE as CREATE_TIME,
             min(business_date) as business_date,
             payment_type
        FROM STV.t_stl_balance_Detail_INIT_Tmp
       WHERE STL_TYPE = P_BALANCE_TYPE --��������
         AND CHECK_OUT_ORG_CODE = P_ORG_CODE --������֯
       GROUP BY CUSTOMER_CODE,
                CHECK_OUT_ORG_CODE,
                PRODUCT_CODE,
                SOURCE_BILL_NO, --��Դ����
                SOURCE_BILL_TYPE, --��Դ�������
                payment_type, --���ʽ
                STL_TYPE, -- ��������
                BILL_TYPE; --���㵥��������
    --Ĭ�Ϸ���true
    return true;
    --��׽�쳣
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_BEGIN_DATE,
                                        'pkg_stl_banlance_deposit.FUNC_daily_detail_INIT',
                                        P_ORG_CODE || '-Ԥ����ĩ���˺����ʼ�����');
    
      --���سɹ���־
      RETURN FALSE;
  end;

  --��ʼ��Ӧ��δǩ�գ�Ԥ����ת��
  function FUNC_INIT_DEPOSIT_TF(P_ORG_CODE     STRING, --��֯����
                                P_BEGIN_DATE   DATE, --��ʼ����
                                P_PERIOD       STRING, --�����ڼ�
                                P_BALANCE_TYPE STRING -- ��������
                                ) return boolean is
  
  begin
  
    --���ɳ�ʼ����Ӧ��
    INSERT INTO STV.t_stl_balance_Detail_INIT_Tmp B
    --ID,�ڼ�,��֯
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
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
       payment_type)
    
    --���ڷ������
      SELECT SYS_GUID() AS ID,
             P_PERIOD AS CHECK_OUT_PERIOD,
             NVL(BILL.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             BILL.RECEIVABLE_ORG_CODE,
             NVL(SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO, --��Դ����
             NVL(SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE, --��Դ������� 
             --��������
             P_BALANCE_TYPE as STL_TYPE,
             BILL.BILL_TYPE, --����������
             NVL(BILL.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE,
             0 AS BEGIN_AMOUNT, --�����ڳ�               
             SUM(AMOUNT) AS PRODUCE_AMOUNT, --���ڷ�����
             0 AS WRITEOFF_AMOUNT, --���ں������
             0 AS END_AMOUNT, --������ĩ���
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE, -- �����
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME, --��ʼ������Ϊ����
             SYSDATE AS CREATE_TIME,
             min(business_date) as business_date,
             payment_type as payment_type
        FROM STL.T_STL_BILL_RECEIVABLE BILL
      --Ŀǰ���ռ�������
       WHERE
      --��ѯ��ʼ����Ӧ�յ�
       BILL.IS_INIT = PKG_STL_COMMON.YES
      --Ӧ����֯
       AND BILL.RECEIVABLE_ORG_CODE = P_ORG_CODE
       GROUP BY CUSTOMER_CODE,
                RECEIVABLE_ORG_CODE,
                PRODUCT_CODE,
                BILL_TYPE, -- ��������
                payment_type, --���ʽ
                SOURCE_BILL_NO, --��Դ����
                SOURCE_BILL_TYPE --��Դ�������
      HAVING SUM(AMOUNT) != 0;
    --���ɳ�ʼ���ĺ�������Ϊ������
    INSERT INTO STV.t_stl_balance_Detail_INIT_Tmp
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
       BILL_TYPE, -- ���㵥������
       CURRENCY_CODE, --�ұ�
       ACTIVE, --�Ƿ���Ч
       BALANCE_TIME, --��������
       CREATE_TIME,
       business_date,
       payment_type)
      SELECT SYS_GUID() AS ID,
             p_PERIOD AS CHECK_OUT_PERIOD,
             NVL(SBW.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             SBW.ORG_CODE AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SUM(SBW.AMOUNT) AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(B.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ����
             NVL(B.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO,
             NVL(B.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --��������
             B.BILL_TYPE AS BILL_TYPE,
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             min(b.business_date) as business_date,
             b.payment_type as payment_type
        FROM STL.T_STL_BILL_WRITEOFF SBW
      --����Ӧ�յ�
       INNER JOIN STL.T_STL_BILL_RECEIVABLE B
          ON B.id = SBW.BEGIN_Id --��������
       WHERE sbw.ORG_CODE = B.RECEIVABLE_ORG_CODE
         and b.RECEIVABLE_ORG_CODE = P_ORG_CODE
            --��ʼ���ĵ���
         AND b.IS_INIT = PKG_STL_COMMON.YES
       GROUP BY SBW.CUSTOMER_CODE,
                SBW.ORG_CODE,
                B.PRODUCT_CODE,
                B.SOURCE_BILL_NO,
                B.SOURCE_BILL_TYPE,
                b.payment_type,
                B.BILL_TYPE -- Ӧ�յ�����
      HAVING SUM(SBW.AMOUNT) != 0;
    --��Ϊ������
    INSERT INTO STV.t_stl_balance_Detail_INIT_Tmp
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
       BILL_TYPE, -- ���㵥������
       CURRENCY_CODE, --�ұ�
       ACTIVE, --�Ƿ���Ч
       BALANCE_TIME, --��������
       CREATE_TIME,
       business_date,
       payment_type)
      SELECT SYS_GUID() AS ID,
             p_PERIOD AS CHECK_OUT_PERIOD,
             NVL(SBW.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             SBW.ORG_CODE AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SUM(SBW.AMOUNT) AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(B.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ����
             NVL(B.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO,
             NVL(B.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --��������
             B.BILL_TYPE AS BILL_TYPE,
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             min(b.business_date) as business_date,
             b.payment_type as payment_type
        FROM STL.T_STL_BILL_WRITEOFF SBW
      --����Ӧ�յ�
       INNER JOIN STL.T_STL_BILL_RECEIVABLE B
          ON B.id = SBW.END_Id --��������
       WHERE sbw.ORG_CODE = B.RECEIVABLE_ORG_CODE
         and b.RECEIVABLE_ORG_CODE = P_ORG_CODE
            --��ʼ���ĵ���
         AND b.IS_INIT = PKG_STL_COMMON.YES
       GROUP BY SBW.CUSTOMER_CODE,
                SBW.ORG_CODE,
                B.PRODUCT_CODE,
                B.SOURCE_BILL_NO,
                B.SOURCE_BILL_TYPE,
                b.payment_type,
                B.BILL_TYPE -- Ӧ�յ�����
      HAVING SUM(SBW.AMOUNT) != 0;
  
    --���ɳ�ʼ������ĩ
    INSERT INTO STV.t_stl_balance_Detail_INIT
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
       payment_type)
      SELECT SYS_GUID(),
             P_PERIOD,
             CUSTOMER_CODE,
             CHECK_OUT_ORG_CODE,
             STL_TYPE, -- ��������
             BILL_TYPE, --���㵥��������             
             SOURCE_BILL_NO, --��Դ����
             SOURCE_BILL_TYPE, --��Դ�������
             PRODUCT_CODE,
             0 AS BEGIN_AMOUNT, --�����ڳ�
             SUM(PRODUCE_AMOUNT) AS PRODUCE_AMOUNT, --���ڷ�����
             SUM(WRITEOFF_AMOUNT) AS WRITEOFF_AMOUNT, --���ں������
             SUM(PRODUCE_AMOUNT) - SUM(WRITEOFF_AMOUNT) AS END_AMOUNT, --������ĩ
             PKG_STL_COMMON.CURRENCY_CODE_RMB as CURRENCY_CODE, -- �����
             PKG_STL_COMMON.ACTIVE as ACTIVE,
             P_BEGIN_DATE as BALANCE_TIME,
             SYSDATE as CREATE_TIME,
             min(business_date) as business_date,
             payment_type
        FROM STV.t_stl_balance_Detail_INIT_Tmp
       WHERE STL_TYPE = P_BALANCE_TYPE --��������
         AND CHECK_OUT_ORG_CODE = P_ORG_CODE --������֯ 
       GROUP BY CUSTOMER_CODE,
                CHECK_OUT_ORG_CODE,
                PRODUCT_CODE,
                SOURCE_BILL_NO, --��Դ����
                SOURCE_BILL_TYPE, --��Դ�������
                payment_type, --���ʽ
                STL_TYPE, -- ��������
                BILL_TYPE; --���㵥�������� 
    --Ĭ�Ϸ���true
    return true;
    --��׽�쳣
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(p_PERIOD,
                                        P_BEGIN_DATE,
                                        P_BEGIN_DATE,
                                        'pkg_stl_balance_payable.FUNC_daily_detail_INIT',
                                        P_ORG_CODE || '-ӦԤ����ĩ���˺����ʼ���ڳ�');
    
      --���سɹ���־
      RETURN FALSE;
  end;

  --��ʼ��Ӧ��
  function FUNC_INIT_receivable(P_ORG_CODE     STRING, --��֯����
                                P_BEGIN_DATE   DATE, --��ʼ����
                                P_PERIOD       STRING, --�����ڼ�
                                P_BALANCE_TYPE STRING -- ��������
                                ) return boolean is
  
  begin
  
    --���ɳ�ʼ����Ӧ��
    INSERT INTO STV.t_stl_balance_Detail_INIT_Tmp B
    --ID,�ڼ�,��֯
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
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
       payment_type)
    
    --���ڷ������
      SELECT SYS_GUID() AS ID,
             P_PERIOD AS CHECK_OUT_PERIOD,
             NVL(BILL.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             BILL.RECEIVABLE_ORG_CODE,
             NVL(SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO, --��Դ����
             NVL(SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE, --��Դ������� 
             --��������
             P_BALANCE_TYPE as STL_TYPE,
             BILL.BILL_TYPE, --����������
             NVL(BILL.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE,
             0 AS BEGIN_AMOUNT, --�����ڳ�               
             SUM(AMOUNT) AS PRODUCE_AMOUNT, --���ڷ�����
             0 AS WRITEOFF_AMOUNT, --���ں������
             0 AS END_AMOUNT, --������ĩ���
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE, -- �����
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME, --��ʼ������Ϊ����
             SYSDATE AS CREATE_TIME,
             business_date as business_date,
             payment_type as payment_type
        FROM STL.T_STL_BILL_RECEIVABLE BILL
      --Ŀǰ���ռ�������
       WHERE
      --��ѯ��ʼ����Ӧ�յ�
       BILL.IS_INIT = PKG_STL_COMMON.YES
      --Ӧ����֯
       AND BILL.RECEIVABLE_ORG_CODE = P_ORG_CODE
       GROUP BY CUSTOMER_CODE,
                RECEIVABLE_ORG_CODE,
                PRODUCT_CODE,
                BILL_TYPE, -- ��������
                payment_type, --���ʽ
                SOURCE_BILL_NO, --��Դ����
                SOURCE_BILL_TYPE --��Դ�������
      HAVING SUM(AMOUNT) != 0;
    --���ɳ�ʼ���ĺ���
    --��Ϊ��������
    INSERT INTO STV.t_stl_balance_Detail_INIT_Tmp
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
       BILL_TYPE, -- ���㵥������
       CURRENCY_CODE, --�ұ�
       ACTIVE, --�Ƿ���Ч
       BALANCE_TIME, --��������
       CREATE_TIME,
       business_date,
       payment_type)
      SELECT SYS_GUID() AS ID,
             p_PERIOD AS CHECK_OUT_PERIOD,
             NVL(SBW.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             SBW.ORG_CODE AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SUM(SBW.AMOUNT) AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(B.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ����
             NVL(B.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO,
             NVL(B.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --��������
             B.BILL_TYPE AS BILL_TYPE,
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             b.business_date as business_date,
             b.payment_type as payment_type
        FROM STL.T_STL_BILL_WRITEOFF SBW
      --����Ӧ�յ�
       INNER JOIN STL.T_STL_BILL_RECEIVABLE B
          ON B.Id = SBW.Begin_Id --��������
       WHERE sbw.ORG_CODE = B.RECEIVABLE_ORG_CODE
         and b.RECEIVABLE_ORG_CODE = P_ORG_CODE
            --��ʼ���ĵ���
         AND b.IS_INIT = PKG_STL_COMMON.YES
       GROUP BY SBW.CUSTOMER_CODE,
                SBW.ORG_CODE,
                B.PRODUCT_CODE,
                B.SOURCE_BILL_NO,
                B.SOURCE_BILL_TYPE,
                b.payment_type,
                B.BILL_TYPE -- Ӧ�յ�����
      HAVING SUM(SBW.AMOUNT) != 0;
    --��Ϊ����������
    INSERT INTO STV.t_stl_balance_Detail_INIT_Tmp
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
       BILL_TYPE, -- ���㵥������
       CURRENCY_CODE, --�ұ�
       ACTIVE, --�Ƿ���Ч
       BALANCE_TIME, --��������
       CREATE_TIME,
       business_date,
       payment_type)
      SELECT SYS_GUID() AS ID,
             p_PERIOD AS CHECK_OUT_PERIOD,
             NVL(SBW.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             SBW.ORG_CODE AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SUM(SBW.AMOUNT) AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(B.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ����
             NVL(B.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO,
             NVL(B.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --��������
             B.BILL_TYPE AS BILL_TYPE,
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             b.business_date as business_date,
             b.payment_type as payment_type
        FROM STL.T_STL_BILL_WRITEOFF SBW
      --����Ӧ�յ�
       INNER JOIN STL.T_STL_BILL_RECEIVABLE B
          ON B.id = SBW.End_Id --��������
       WHERE sbw.ORG_CODE = B.RECEIVABLE_ORG_CODE
         and b.RECEIVABLE_ORG_CODE = P_ORG_CODE
            --��ʼ���ĵ���
         AND b.IS_INIT = PKG_STL_COMMON.YES
       GROUP BY SBW.CUSTOMER_CODE,
                SBW.ORG_CODE,
                B.PRODUCT_CODE,
                B.SOURCE_BILL_NO,
                B.SOURCE_BILL_TYPE,
                b.payment_type,
                B.BILL_TYPE -- Ӧ�յ�����
      HAVING SUM(SBW.AMOUNT) != 0;
  
    --���ɳ�ʼ������ĩ
    INSERT INTO STV.t_stl_balance_Detail_INIT
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
       payment_type)
      SELECT SYS_GUID(),
             P_PERIOD,
             CUSTOMER_CODE,
             CHECK_OUT_ORG_CODE,
             STL_TYPE, -- ��������
             BILL_TYPE, --���㵥��������             
             SOURCE_BILL_NO, --��Դ����
             SOURCE_BILL_TYPE, --��Դ�������
             PRODUCT_CODE,
             0 AS BEGIN_AMOUNT, --�����ڳ�
             SUM(PRODUCE_AMOUNT) AS PRODUCE_AMOUNT, --���ڷ�����
             SUM(WRITEOFF_AMOUNT) AS WRITEOFF_AMOUNT, --���ں������
             SUM(PRODUCE_AMOUNT) - SUM(WRITEOFF_AMOUNT) AS END_AMOUNT, --������ĩ
             PKG_STL_COMMON.CURRENCY_CODE_RMB as CURRENCY_CODE, -- �����
             PKG_STL_COMMON.ACTIVE as ACTIVE,
             P_BEGIN_DATE as BALANCE_TIME,
             SYSDATE as CREATE_TIME,
             business_date as business_date,
             payment_type
        FROM STV.t_stl_balance_Detail_INIT_Tmp
       WHERE STL_TYPE = P_BALANCE_TYPE --��������
         AND CHECK_OUT_ORG_CODE = P_ORG_CODE --������֯ 
       GROUP BY CUSTOMER_CODE,
                CHECK_OUT_ORG_CODE,
                PRODUCT_CODE,
                SOURCE_BILL_NO, --��Դ����
                SOURCE_BILL_TYPE, --��Դ�������
                payment_type, --���ʽ
                STL_TYPE, -- ��������
                BILL_TYPE; --���㵥�������� 
    --Ĭ�Ϸ���true
    return true;
    --��׽�쳣
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(p_PERIOD,
                                        P_BEGIN_DATE,
                                        P_BEGIN_DATE,
                                        'pkg_stl_balance_payable.FUNC_daily_detail_INIT',
                                        P_ORG_CODE || '-Ӧ����ĩ���˺����ʼ���ڳ�');
    
      --���سɹ���־
      RETURN FALSE;
  end;

  --��ʼ��Ӧ��
  function FUNC_INIT_payable(P_ORG_CODE     STRING, --��֯����
                             P_BEGIN_DATE   DATE, --��ʼ����
                             P_PERIOD       STRING, --�����ڼ�
                             P_BALANCE_TYPE STRING -- ��������
                             ) return boolean is
  
  begin
  
    --���ɳ�ʼ����Ӧ��
    INSERT INTO STV.t_stl_balance_Detail_INIT_Tmp B
    --ID,�ڼ�,��֯
      (ID,
       CHECK_OUT_PERIOD,
       CUSTOMER_CODE,
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
       payment_type)
    
    --���ڷ������
      SELECT SYS_GUID() AS ID,
             P_PERIOD AS CHECK_OUT_PERIOD,
             NVL(BILL.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             BILL.payable_org_code,
             NVL(SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO, --��Դ����
             NVL(SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE, --��Դ������� 
             --��������
             P_BALANCE_TYPE as STL_TYPE,
             BILL.BILL_TYPE, --����������
             NVL(BILL.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE,
             0 AS BEGIN_AMOUNT, --�����ڳ�               
             SUM(AMOUNT) AS PRODUCE_AMOUNT, --���ڷ�����
             0 AS WRITEOFF_AMOUNT, --���ں������
             0 AS END_AMOUNT, --������ĩ���
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE, -- �����
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME, --��ʼ������Ϊ����
             SYSDATE AS CREATE_TIME,
             min(business_date) as business_date,
             PKG_STL_COMMON.NULL_VALUE as payment_type
        FROM STL.T_STL_BILL_Payable BILL
      --Ŀǰ���ռ�������
       WHERE
      --��ѯ��ʼ����Ӧ����
       BILL.IS_INIT = PKG_STL_COMMON.YES
      --Ӧ����֯
       AND BILL.payable_org_code = P_ORG_CODE
       GROUP BY CUSTOMER_CODE,
                payable_ORG_CODE,
                PRODUCT_CODE,
                BILL_TYPE, -- ��������
                SOURCE_BILL_NO, --��Դ����
                SOURCE_BILL_TYPE; --��Դ�������
    --���ɳ�ʼ���ĺ���(��Ϊ����)
    INSERT INTO STV.t_stl_balance_Detail_INIT_Tmp
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
       BILL_TYPE, -- ���㵥������
       CURRENCY_CODE, --�ұ�
       ACTIVE, --�Ƿ���Ч
       BALANCE_TIME, --��������
       CREATE_TIME,
       business_date,
       payment_type)
      SELECT SYS_GUID() AS ID,
             p_PERIOD AS CHECK_OUT_PERIOD,
             NVL(SBW.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             SBW.ORG_CODE AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SUM(SBW.AMOUNT) AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(B.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ����
             NVL(B.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO,
             NVL(B.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --��������
             B.BILL_TYPE AS BILL_TYPE,
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             min(b.business_date) as business_date,
             PKG_STL_COMMON.NULL_VALUE as payment_type
        FROM STL.T_STL_BILL_WRITEOFF SBW
      --����Ӧ����
       INNER JOIN STL.T_STL_BILL_Payable B
          ON B.Payable_No = SBW.BEGIN_No --��������
       WHERE sbw.ORG_CODE = B.Payable_ORG_CODE
         and b.Payable_ORG_CODE = P_ORG_CODE
            --��ʼ���ĵ���
         AND b.IS_INIT = PKG_STL_COMMON.YES
       GROUP BY SBW.CUSTOMER_CODE,
                SBW.ORG_CODE,
                B.PRODUCT_CODE,
                B.SOURCE_BILL_NO,
                B.SOURCE_BILL_TYPE,
                B.BILL_TYPE; -- ��������
  
    --����(��Ϊ������)
    INSERT INTO STV.t_stl_balance_Detail_INIT_Tmp
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
       BILL_TYPE, -- ���㵥������
       CURRENCY_CODE, --�ұ�
       ACTIVE, --�Ƿ���Ч
       BALANCE_TIME, --��������
       CREATE_TIME,
       business_date,
       payment_type)
      SELECT SYS_GUID() AS ID,
             p_PERIOD AS CHECK_OUT_PERIOD,
             NVL(SBW.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             SBW.ORG_CODE AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SUM(SBW.AMOUNT) AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(B.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ����
             NVL(B.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO,
             NVL(B.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --��������
             B.BILL_TYPE AS BILL_TYPE,
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             min(b.business_date) as business_date,
             PKG_STL_COMMON.NULL_VALUE as payment_type
        FROM STL.T_STL_BILL_WRITEOFF SBW
      --����Ӧ����
       INNER JOIN STL.T_STL_BILL_Payable B
          ON B.Payable_No = SBW.END_No --��������
       WHERE sbw.ORG_CODE = B.Payable_ORG_CODE
         and b.Payable_ORG_CODE = P_ORG_CODE
            --��ʼ���ĵ���
         AND b.IS_INIT = PKG_STL_COMMON.YES
       GROUP BY SBW.CUSTOMER_CODE,
                SBW.ORG_CODE,
                B.PRODUCT_CODE,
                B.SOURCE_BILL_NO,
                B.SOURCE_BILL_TYPE,
                B.BILL_TYPE; -- ��������
  
    --���ɺ������
    INSERT INTO STV.t_stl_balance_Detail_INIT
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
       payment_type)
      SELECT SYS_GUID(),
             P_PERIOD,
             CUSTOMER_CODE,
             CHECK_OUT_ORG_CODE,
             STL_TYPE, -- ��������
             BILL_TYPE, --���㵥��������             
             SOURCE_BILL_NO, --��Դ����
             SOURCE_BILL_TYPE, --��Դ�������
             PRODUCT_CODE,
             0 AS BEGIN_AMOUNT, --�����ڳ�
             SUM(PRODUCE_AMOUNT) AS PRODUCE_AMOUNT, --���ڷ�����
             SUM(WRITEOFF_AMOUNT) AS WRITEOFF_AMOUNT, --���ں������
             SUM(PRODUCE_AMOUNT) - SUM(WRITEOFF_AMOUNT) AS END_AMOUNT, --������ĩ
             PKG_STL_COMMON.CURRENCY_CODE_RMB as CURRENCY_CODE, -- �����
             PKG_STL_COMMON.ACTIVE as ACTIVE,
             P_BEGIN_DATE as BALANCE_TIME,
             SYSDATE as CREATE_TIME,
             min(business_date) as business_date,
             payment_type
        FROM STV.t_stl_balance_Detail_INIT_Tmp
       WHERE STL_TYPE = P_BALANCE_TYPE --��������
         AND CHECK_OUT_ORG_CODE = P_ORG_CODE --������֯ 
       GROUP BY CUSTOMER_CODE,
                CHECK_OUT_ORG_CODE,
                PRODUCT_CODE,
                SOURCE_BILL_NO, --��Դ����
                SOURCE_BILL_TYPE, --��Դ�������
                payment_type, --���ʽ
                STL_TYPE, -- ��������
                BILL_TYPE; --���㵥�������� 
    --Ĭ�Ϸ���true
    return true;
    --��׽�쳣
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(p_PERIOD,
                                        P_BEGIN_DATE,
                                        P_BEGIN_DATE,
                                        'pkg_stl_balance_payable.FUNC_daily_detail_INIT',
                                        P_ORG_CODE || '-Ӧ����ĩ���˺����ʼ���ڳ�');
    
      --���سɹ���־
      RETURN FALSE;
  end;

end PKG_STL_BALANCE_INIT;
/
