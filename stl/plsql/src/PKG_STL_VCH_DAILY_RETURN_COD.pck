CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_RETURN_COD IS

  -- ==============================================================
  -- Author  : guxinhua
  -- Created : 2013-04-01 16:00:00
  -- Purpose : ÿ��ƾ֤�˴��ջ���
  -- ==============================================================

  -----------------------------------------------------------------
  -- ƾ֤�˴��ջ�������ϸ
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
                                     P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                     P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                     ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

  -----------------------------------------------------------------
  -- ƾ֤�˴��ջ����»��ܣ���ÿ�շ�ʽ��
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_MONTHLY_SUMMARY(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
                                        P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                        P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                        ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

END PKG_STL_VCH_DAILY_RETURN_COD;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_RETURN_COD IS

  -----------------------------------------------------------------
  -- ƾ֤�˴��ջ�������ϸ
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
                                     P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                     P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                     ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
   IS
  BEGIN
  
    --�˴��ջ�����ϸ
    INSERT INTO STV.T_STL_DVD_RETURN_COD
      (ID, -- ID
       PERIOD, -- �ڼ�
       WAYBILL_NO, -- �˵���
       PAYABLE_ORG_CODE, -- Ӧ�����ű���
       PAYABLE_ORG_NAME, -- Ӧ����������
       PAYABLE_COM_CODE, -- Ӧ���ӹ�˾����
       PAYABLE_COM_NAME, -- Ӧ���ӹ�˾����
       COD_TYPE, -- ���ջ�������
       CUSTOMER_CODE, -- �����ͻ�����
       CUSTOMER_TYPE, -- �ͻ�����
       CUSTOMER_NAME, -- �����ͻ�����/��������
       PAYEE_NAME, -- �տ���
       PAYEE_ACCOUNT, -- �տ��������ʺ�
       BANK_HQ_CODE, -- �����б���
       BANK_HQ_NAME, -- ����������
       ACCOUNT_DATE, -- ��������
       BUSINESS_DATE, -- ҵ������
       SIGN_DATE, -- ǩ������
       PAYMENT_DATE, -- ��������
       --RETURN_ORG, -- �˿�ţ��̶�ֵ��
       --RETURN_COM_ORG, -- �˿��ӹ�˾���̶�ֵ��
       REFUND_PATH, -- �˿�·��
       PRODUCT_CODE, -- ��������
       COD_AMOUNT -- Ӧ�˽��
       )
      SELECT SYS_GUID(),
             P_PERIOD,
             PAY.WAYBILL_NO,
             decode(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    NVL(PAY.EXPRESS_ORIG_ORG_CODE,PAY.ORIG_ORG_CODE),
                    PAY.PAYABLE_ORG_CODE), --PAY.PAYABLE_ORG_CODE,
             decode(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    NVL(PAY.EXPRESS_ORIG_ORG_NAME,PAY.ORIG_ORG_NAME),
                    PAY.PAYABLE_ORG_NAME), --PAY.PAYABLE_ORG_NAME,
             PAY.PAYABLE_COM_CODE,
             PAY.PAYABLE_COM_NAME,
             COD.COD_TYPE,
             PAY.CUSTOMER_CODE,
             PAY.CUSTOMER_TYPE,
             PAY.CUSTOMER_NAME,
             PAY.PAYEE_NAME,
             PAY.ACCOUNT_NO,
             COD.BANK_HQ_CODE,
             COD.BANK_HQ_NAME,
             WO.ACCOUNT_DATE,
             COD.BUSINESS_DATE,
             PAY.SIGN_DATE,
             WO.ACCOUNT_DATE,
             --NULL,
             --NULL,
             COD.REFUND_PATH,
             PAY.PRODUCT_CODE,
             WO.AMOUNT
      
        FROM STL.T_STL_BILL_WRITEOFF WO --������
        JOIN STL.T_STL_BILL_PAYMENT PMT
          ON WO.BEGIN_NO = PMT.PAYMENT_NO --���
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO --Ӧ����
         AND PAY.IS_RED_BACK = 'N'
        JOIN STL.T_STL_COD COD
          ON PAY.WAYBILL_ID = COD.WAYBILL_ID
         AND PAY.ACTIVE = COD.ACTIVE
       WHERE PAY.IS_RED_BACK = PMT.IS_RED_BACK
         AND WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_PP --�����Ӧ��
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC --APC
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    RETURN TRUE;
  
  EXCEPTION
    WHEN OTHERS THEN
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        'ƾ֤�˴��ջ�������ϸ' || '�쳣');
    
      --����ʧ�ܱ�־
      RETURN FALSE;
  END;

  -----------------------------------------------------------------
  -- ƾ֤�˴��ջ����»��ܣ���ÿ�շ�ʽ��
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_MONTHLY_SUMMARY(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121212
                                        P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                        P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                        ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
   IS
  BEGIN
  
    INSERT INTO STV.T_STL_DVR_RETURN_COD
      (ID, -- ID
       PAYMENT_DATE, -- ��������
       PERIOD, -- ���������ڼ�
       PAYABLE_ORG_CODE, -- Ӧ�����ű���
       PAYABLE_ORG_NAME, -- Ӧ����������
       UNIFIED_CODE, -- ��֯��˱���
       PAYABLE_COM_CODE, -- Ӧ���ӹ�˾����
       PAYABLE_COM_NAME, -- Ӧ���ӹ�˾����
       --RETURN_ORG, -- �˿�ţ��̶�ֵ��
       --RETURN_COM_ORG, -- �˿��ӹ�˾���̶�ֵ��
       PRODUCT_CODE, -- ҵ������(������/�����˺������)
       BANK_ACCOUNT, -- �����ʺ�
       REFUND_PATH, -- ����;��(����/����)
       COD_AMOUNT -- ���
       )
      (SELECT SYS_GUID(),
              TO_DATE(COD.PERIOD, 'yyyyMMdd'),
              P_PERIOD,
              COD.PAYABLE_ORG_CODE,
              MAX(COD.PAYABLE_ORG_NAME),
              NVL2(COD.PAYABLE_ORG_CODE,
                   PKG_STL_COMMON.FUNC_GET_ORG_UNIFIED_CODE(COD.PAYABLE_ORG_CODE),
                   NULL),
              COD.PAYABLE_COM_CODE,
              MAX(COD.PAYABLE_COM_NAME),
              --NULL,
              --NULL,
              PKG_STL_COMMON.COD_COD_TYPE_RETURN_1_DAY,
              NULL,
              COD.REFUND_PATH,
              SUM(COD_AMOUNT)
         FROM STV.T_STL_DVD_RETURN_COD COD
        WHERE --SUBSTR(COD.PERIOD, 1, 6) = P_PERIOD
        COD.ACCOUNT_DATE >= P_BEGIN_DATE
    AND COD.ACCOUNT_DATE < P_END_DATE -- �ڼ䷶Χ
    AND COD.COD_TYPE = PKG_STL_COMMON.COD_COD_TYPE_RETURN_1_DAY
        GROUP BY COD.PERIOD,
                 --COD.PAYMENT_DATE,
                 COD.PAYABLE_ORG_CODE,
                 COD.PAYABLE_COM_CODE,
                 COD.REFUND_PATH) UNION ALL
      (SELECT SYS_GUID(),
              TO_DATE(COD.PERIOD, 'yyyyMMdd'),
              P_PERIOD,
              COD.PAYABLE_ORG_CODE,
              MAX(COD.PAYABLE_ORG_NAME),
              NVL2(COD.PAYABLE_ORG_CODE,
                   PKG_STL_COMMON.FUNC_GET_ORG_UNIFIED_CODE(COD.PAYABLE_ORG_CODE),
                   NULL),
              COD.PAYABLE_COM_CODE,
              MAX(COD.PAYABLE_COM_NAME),
              --NULL,
              --NULL,
              PKG_STL_COMMON.COD_COD_TYPE_RETURN_3_APPROVE,
              NULL,
              COD.REFUND_PATH,
              SUM(COD_AMOUNT)
         FROM STV.T_STL_DVD_RETURN_COD COD
        WHERE --SUBSTR(COD.PERIOD, 1, 6) = P_PERIOD
        COD.ACCOUNT_DATE >= P_BEGIN_DATE
    AND COD.ACCOUNT_DATE < P_END_DATE -- �ڼ䷶Χ
    AND COD.COD_TYPE IN
        (PKG_STL_COMMON.COD_COD_TYPE_RETURN_3_DAY,
         PKG_STL_COMMON.COD_COD_TYPE_RETURN_APPROVE)
        GROUP BY COD.PERIOD,
                 --COD.PAYMENT_DATE,
                 COD.PAYABLE_ORG_CODE,
                 COD.PAYABLE_COM_CODE,
                 COD.REFUND_PATH
       HAVING SUM(COD_AMOUNT) != 0);
  
    RETURN TRUE;
  
  EXCEPTION
    WHEN OTHERS THEN
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_MONTHLY_SUMMARY',
                                        'ƾ֤�˴��ջ����»��ܣ���ÿ�շ�ʽ��' || '�쳣');
    
      --����ʧ�ܱ�־
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_RETURN_COD;
/
