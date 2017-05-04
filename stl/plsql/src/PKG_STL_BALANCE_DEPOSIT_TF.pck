CREATE OR REPLACE PACKAGE PKG_STL_BALANCE_DEPOSIT_TF IS

  -- Author  : 000123-foss-huangxiaobo
  -- Created : 2012/11/19 14:36:51
  -- Purpose : Ӧ����ĩ����

  ------------------------------------------------------------------
  --Ӧ����ĩ���������
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

END PKG_STL_BALANCE_DEPOSIT_TF;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_BALANCE_DEPOSIT_TF IS

  --Author:000123-foss-huangxiaobo
  --Created:2012-11-19

  ------------------------------------------------------------------
  --Ӧ����ĩ���ˣ�Ӧ����ĩ��������ڣ�
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
      RAISE_APPLICATION_ERROR('-20002', '����Ԥ��-��ת����������쳣');
    END IF;
  
    --���������
    V_RESULT := FUNC_DAILY_DETAIL_WRITEOFF(P_ORG_CODE,
                                           P_BEGIN_DATE,
                                           P_END_DATE,
                                           P_PERIOD,
                                           P_BALANCE_TYPE);
  
    --�������󷵻�
    IF V_RESULT = FALSE THEN
      RAISE_APPLICATION_ERROR('-20002', '����Ԥ��-��ת�����������쳣');
    END IF;
  
    RETURN V_RESULT;
    --��׽�쳣
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE - 1 / (24 * 60 * 60),
                                        'PKG_STL_Balance_RECEIVABLE.func_count_balance',
                                        P_ORG_CODE || '-Ԥ��-��ת��ĩ���˵���');
    
      --���سɹ���־
      RETURN FALSE;
    
  END;
  ------------------------------------------------------------------
  --�����ƶ��ڼ�ķ������
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
  
    --��Ϊ����
    --����δǩ��
    --��������д�뷢����
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
       invoice_mark--��Ʊ���    
       )
      SELECT SYS_GUID() AS ID, --ҵ��ID
             V_PERIOD AS CHECK_OUT_PERIOD, -- �����ڼ�
             NVL(RCV.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE, --�ͻ�����
             --���Ϊ����Ӧ�գ����˲���Ϊ��������
             case
               when rcv.bill_type =
                    Pkg_Stl_Common.RECEIVABLE_BILL_TYPE_ORIGIN then
                 DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE��RCV.ORIG_ORG_CODE)
               else
                DECODE(RCV.PRODUCT_CODE,
                     PKG_STL_COMMON.PRODUCT_CODE_PKG,
                     DECODE(RCV.receivable_org_code,
                     RCV.ORIG_ORG_CODE,
                     RCV.Express_Orig_Org_Code,
                     RCV.DEST_ORG_CODE,
                     RCV.Express_Dest_Org_Code��RCV.receivable_org_code),
                     RCV.receivable_org_code)
             end AS CHECK_OUT_ORG_CODE, -- Ӧ����֯
             NVL(RCV.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ����
             0 AS BEGIN_AMOUNT, --�ڳ����
             WO.Amount AS PRODUCE_AMOUNT, --������
             0 AS WRITEOFF_AMOUNT, --�������
             0 AS END_AMOUNT, --��ĩ���
             --ƫ�ߣ����˵�Ϊ׼
             case
               when bill_type = pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --��Դ����            
             RCV.SOURCE_BILL_TYPE AS SOURCE_BILL_TYPE, --��Դ�������
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE, --�ұ�
             PKG_STL_COMMON.ACTIVE AS ACTIVE, --��Ч��
             P_BALANCE_TYPE AS STL_TYPE, --��������
             RCV.BILL_TYPE AS BILL_TYPE, --��������
             P_BEGIN_DATE AS BALANCE_TIME, --����ҵ������
             SYSDATE AS CREATE_TIME, --����ʱ��
             --��ȷ��������Ϊҵ������
             RCV.Account_Date as business_date,
             RCV.payment_type as payment_type,
             RCV.Invoice_Mark as invoice_mark--��Ʊ���      
        FROM STL.T_STL_BILL_WRITEOFF WO
       INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV
      --��Ϊ����
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      
       WHERE RCV.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --��Դ���˵�
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE <= WO.ACCOUNT_DATE
                 and T.POD_TYPE in
                     (PKG_STL_COMMON.POD__POD_TYPE__POD,
                      PKG_STL_COMMON.POD__POD_TYPE__UPD)) = 0
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
            --ָ������
         and wo.ORG_CODE = p_org_code
            --�������ͣ�ʼ���˷ѡ������˷ѡ����˵�����ƫ�ߵ��������˴��ջ���,����䣩
         and RCV.bill_type in
             (pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DEST,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_AA,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_AAC,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DL,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DLC);

    commit;
  
    --��Ϊ����
    --����δǩ��
    --��������д�뷢����
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
       invoice_mark--��Ʊ���    
        )
      SELECT SYS_GUID() AS ID, --ҵ��ID
             V_PERIOD AS CHECK_OUT_PERIOD, -- �����ڼ�
             NVL(RCV.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE, --�ͻ�����
             --���Ϊ����Ӧ�գ����˲���Ϊ��������
             case
               when rcv.bill_type =
                    Pkg_Stl_Common.RECEIVABLE_BILL_TYPE_ORIGIN then
                 DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE��RCV.ORIG_ORG_CODE)
               else
                DECODE(RCV.PRODUCT_CODE,
                     PKG_STL_COMMON.PRODUCT_CODE_PKG,
                     DECODE(RCV.receivable_org_code,
                     RCV.ORIG_ORG_CODE,
                     RCV.Express_Orig_Org_Code,
                     RCV.DEST_ORG_CODE,
                     RCV.Express_Dest_Org_Code��RCV.receivable_org_code),
                     RCV.receivable_org_code)
             end AS CHECK_OUT_ORG_CODE, -- Ӧ����֯
             NVL(RCV.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ����
             0 AS BEGIN_AMOUNT, --�ڳ����
             WO.Amount AS PRODUCE_AMOUNT, --������
             0 AS WRITEOFF_AMOUNT, --�������
             0 AS END_AMOUNT, --��ĩ���
             --ƫ�ߣ����˵�Ϊ׼
             case
               when bill_type = pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --��Դ����            
             RCV.SOURCE_BILL_TYPE AS SOURCE_BILL_TYPE, --��Դ�������
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE, --�ұ�
             PKG_STL_COMMON.ACTIVE AS ACTIVE, --��Ч��
             P_BALANCE_TYPE AS STL_TYPE, --��������
             RCV.BILL_TYPE AS BILL_TYPE, --��������
             P_BEGIN_DATE AS BALANCE_TIME, --����ҵ������
             SYSDATE AS CREATE_TIME, --����ʱ��
             --��ȷ��������Ϊҵ������
             RCV.Account_Date as business_date,
             RCV.payment_type as payment_type,
             rcv.invoice_mark as invoice_mark--��Ʊ���          
        FROM STL.T_STL_BILL_WRITEOFF WO
       INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV
      --��Ϊ����
          ON (RCV.RECEIVABLE_NO = WO.END_NO)
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      
       WHERE RCV.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --��Դ���˵�
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                 AND T.POD_DATE <= WO.ACCOUNT_DATE
                 and T.POD_TYPE in
                     (PKG_STL_COMMON.POD__POD_TYPE__POD,
                      PKG_STL_COMMON.POD__POD_TYPE__UPD)) = 0
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
            --ָ������
         and wo.ORG_CODE = p_org_code
            --�������ͣ�ʼ���˷ѡ������˷ѡ����˵�����ƫ�ߵ��������˴��ջ���,����䣩
         and RCV.bill_type in
             (pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DEST,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_AA,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_AAC,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DL,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DLC);

    commit;
  
    --��Ϊ����
    --����ͬʱǩ��
    --��������д�뷢����
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
       invoice_mark--��Ʊ���
       )
      SELECT SYS_GUID() AS ID, --ҵ��ID
             V_PERIOD AS CHECK_OUT_PERIOD, -- �����ڼ�
             NVL(RCV.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE, --�ͻ�����
             --���Ϊ����Ӧ�գ����˲���Ϊ��������
             case
               when rcv.bill_type =
                    Pkg_Stl_Common.RECEIVABLE_BILL_TYPE_ORIGIN then
                    DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE��RCV.ORIG_ORG_CODE)
               else
                     DECODE(RCV.PRODUCT_CODE,
                     PKG_STL_COMMON.PRODUCT_CODE_PKG,
                     DECODE(RCV.receivable_org_code,
                     RCV.ORIG_ORG_CODE,
                     RCV.Express_Orig_Org_Code,
                     RCV.DEST_ORG_CODE,
                     RCV.Express_Dest_Org_Code��RCV.receivable_org_code),
                     RCV.receivable_org_code)
             end AS CHECK_OUT_ORG_CODE, -- Ӧ����֯
             NVL(RCV.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ����
             0 AS BEGIN_AMOUNT, --�ڳ����
             WO.Amount AS PRODUCE_AMOUNT, --������
             0 AS WRITEOFF_AMOUNT, --�������
             0 AS END_AMOUNT, --��ĩ���
             --ƫ�ߣ����˵�Ϊ׼
             case
               when bill_type = pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --��Դ����            
             RCV.SOURCE_BILL_TYPE AS SOURCE_BILL_TYPE, --��Դ�������
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE, --�ұ�
             PKG_STL_COMMON.ACTIVE AS ACTIVE, --��Ч��
             P_BALANCE_TYPE AS STL_TYPE, --��������
             RCV.BILL_TYPE AS BILL_TYPE, --��������
             P_BEGIN_DATE AS BALANCE_TIME, --����ҵ������
             SYSDATE AS CREATE_TIME, --����ʱ��
             --��ȷ��������Ϊҵ������
             RCV.Account_Date as business_date,
             RCV.payment_type as payment_type,
             rcv.invoice_mark as invoice_mark--��Ʊ���            
        FROM STL.T_STL_BILL_WRITEOFF WO
       INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV
      --��Ϊ����
          ON RCV.RECEIVABLE_NO = WO.BEGIN_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      
       WHERE RCV.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --��Դ���˵�
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                    --ǩ��ʱ�������ʱ����ͬ
                 AND T.POD_DATE = WO.ACCOUNT_DATE
                 and T.POD_TYPE in
                     (PKG_STL_COMMON.POD__POD_TYPE__POD,
                      --ǩ��
                      PKG_STL_COMMON.POD__POD_TYPE__UPD)) = 1
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
            --ָ������
         and wo.ORG_CODE = p_org_code
            --�������ͣ�ʼ���˷ѡ������˷ѡ����˵�����ƫ�ߵ��������˴��ջ��
         and RCV.bill_type in
             (pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DEST,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_AA,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_AAC,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DL,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DLC);

    commit;
  
    --��Ϊ����
    --����ͬʱǩ��
    --��������д�뷢����
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
       invoice_mark--��Ʊ���      
       )
      SELECT SYS_GUID() AS ID, --ҵ��ID
             V_PERIOD AS CHECK_OUT_PERIOD, -- �����ڼ�
             NVL(RCV.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE, --�ͻ�����
             --���Ϊ����Ӧ�գ����˲���Ϊ��������
             case
               when rcv.bill_type =
                    Pkg_Stl_Common.RECEIVABLE_BILL_TYPE_ORIGIN then
                    DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE��RCV.ORIG_ORG_CODE)
               else
                     DECODE(RCV.PRODUCT_CODE,
                     PKG_STL_COMMON.PRODUCT_CODE_PKG,
                     DECODE(RCV.receivable_org_code,
                     RCV.ORIG_ORG_CODE,
                     RCV.Express_Orig_Org_Code,
                     RCV.DEST_ORG_CODE,
                     RCV.Express_Dest_Org_Code��RCV.receivable_org_code),
                     RCV.receivable_org_code)
             end AS CHECK_OUT_ORG_CODE, -- Ӧ����֯
             NVL(RCV.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ����
             0 AS BEGIN_AMOUNT, --�ڳ����
             WO.Amount AS PRODUCE_AMOUNT, --������
             0 AS WRITEOFF_AMOUNT, --�������
             0 AS END_AMOUNT, --��ĩ���
             --ƫ�ߣ����˵�Ϊ׼
             case
               when bill_type = pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --��Դ����            
             RCV.SOURCE_BILL_TYPE AS SOURCE_BILL_TYPE, --��Դ�������
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE, --�ұ�
             PKG_STL_COMMON.ACTIVE AS ACTIVE, --��Ч��
             P_BALANCE_TYPE AS STL_TYPE, --��������
             RCV.BILL_TYPE AS BILL_TYPE, --��������
             P_BEGIN_DATE AS BALANCE_TIME, --����ҵ������
             SYSDATE AS CREATE_TIME, --����ʱ��
             --��ȷ��������Ϊҵ������
             RCV.Account_Date as business_date,
             RCV.payment_type as payment_type,
             rcv.invoice_mark as invoice_mark--��Ʊ���           
        FROM STL.T_STL_BILL_WRITEOFF WO
       INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV
      --��Ϊ����
          ON (RCV.RECEIVABLE_NO = WO.END_NO)
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      
       WHERE RCV.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --��Դ���˵�
         AND (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = RCV.WAYBILL_NO
                    --ǩ��ʱ�������ʱ����ͬ
                 AND T.POD_DATE = WO.ACCOUNT_DATE
                 and T.POD_TYPE in
                     (PKG_STL_COMMON.POD__POD_TYPE__POD,
                      --����ǩ��
                      PKG_STL_COMMON.POD__POD_TYPE__UPD)) = 1
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
            --ָ������
         and wo.ORG_CODE = p_org_code
            --�������ͣ�ʼ���˷ѡ������˷ѡ����˵�����ƫ�ߵ��������˴��ջ��
         and RCV.bill_type in
             (pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DEST,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_AA,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_AAC,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DL,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DLC);
    commit;
  
    RETURN TRUE;
  
    --��׽�쳣
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE - 1 / (24 * 60 * 60),
                                        'PKG_STL_BALANCE_DEPOSIT_TF.func_daily_detail_produce',
                                        P_ORG_CODE || '-Ԥ��-��ת��ĩ���˺��㷢����');
    
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
  
    --�˵���
    --��Ϊ����
    --ǩ���Ѻ�������������� 
    --��ǩ���Ѻ��� ��ȡ�����������    
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
      SELECT SYS_GUID() AS ID,
             V_PERIOD AS CHECK_OUT_PERIOD,
             NVL(CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             CHECK_OUT_ORG_CODE AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             --ǩ���ѻ���
             case
               when pod_Type = Pkg_Stl_Common.POD__POD_TYPE__POD then
                WO_AMOUNT
               else
                WO_AMOUNT * -1
             end AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ���� 
             --ƫ�ߣ����˵�Ϊ׼
             case
               when bill_type = pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --��Դ���� 
             NVL(SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --��������
             BILL_TYPE AS BILL_TYPE, -- ����������
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             --��ȷ��������Ϊҵ������ 
             account_date as business_date,
             payment_type as payment_type,
             invoice_mark as invoice_mark--��Ʊ���          
        FROM (SELECT RCV.CUSTOMER_CODE, --�ͻ�����  
                     --���Ϊ����Ӧ�գ����˲���Ϊ��������
                     case
                       when rcv.bill_type =
                            Pkg_Stl_Common.RECEIVABLE_BILL_TYPE_ORIGIN then
                       DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE��RCV.ORIG_ORG_CODE)
               else
                     DECODE(RCV.PRODUCT_CODE,
                     PKG_STL_COMMON.PRODUCT_CODE_PKG,
                     DECODE(RCV.receivable_org_code,
                     RCV.ORIG_ORG_CODE,
                     RCV.Express_Orig_Org_Code,
                     RCV.DEST_ORG_CODE,
                     RCV.Express_Dest_Org_Code��RCV.receivable_org_code),
                     RCV.receivable_org_code)
                     end AS CHECK_OUT_ORG_CODE, -- Ӧ����֯
                     RCV.WAYBILL_NO, --�˵��� 
                     MIN(RCV.ACCOUNT_DATE) as ACCOUNT_DATE, --������� 
                     RCV.BILL_TYPE, --����������
                     MIN(RCV.AMOUNT) RCV_AMOUNT, --Ӧ�ն�  
                     pod.pod_type as pod_type, --ǩ������              
                     SUM(WO.AMOUNT) as WO_AMOUNT, --������
                     rcv.payment_type as payment_type, --���ʽ
                     rcv.SOURCE_BILL_TYPE as SOURCE_BILL_TYPE, --��������
                     rcv.SOURCE_BILL_NO as SOURCE_BILL_NO, --��Դ����
                     RCV.PRODUCT_CODE, --��Ʒ����
                     rcv.invoice_mark as invoice_mark --��Ʊ���
                FROM STL.T_STL_POD POD
               inner JOIN STL.T_STL_BILL_RECEIVABLE RCV
                  ON RCV.WAYBILL_NO = POD.WAYBILL_NO
                 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
               inner JOIN STL.T_STL_BILL_WRITEOFF WO
              --��Ϊ����
                  ON (WO.BEGIN_NO = RCV.RECEIVABLE_NO)
               WHERE RCV.SOURCE_BILL_TYPE =
                     PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --��Դ���˵�
                    --ָ����������
                    --�������ͣ�ʼ���˷ѡ������˷ѡ����˵�����ƫ�ߵ��������˴��ջ��
                 and RCV.bill_type in
                     (pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN,
                      pkg_stl_common.RECEIVABLE_BILL_TYPE_DEST,
                      pkg_stl_common.RECEIVABLE_BILL_TYPE_AA,
                      pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL,
                      pkg_stl_common.RECEIVABLE_BILL_TYPE_AAC,
                      pkg_stl_common.RECEIVABLE_BILL_TYPE_DL,
                      pkg_stl_common.RECEIVABLE_BILL_TYPE_DLC)
                 AND WO.ACCOUNT_DATE <= POD.POD_DATE --����ʱ����С��ǩ��ʱ��
                 AND POD.POD_DATE >= P_BEGIN_DATE
                 AND POD.POD_DATE < P_END_DATE
                    --ֻҪǩ���뷴ǩ�յ�����
                 and pod.pod_Type in
                     (PKG_STL_COMMON.POD__POD_TYPE__POD,
                      PKG_STL_COMMON.POD__POD_TYPE__UPD)
                    --ָ������
                 and wo.ORG_CODE = p_org_code
               GROUP BY RCV.CUSTOMER_CODE, --�ͻ�����
                        DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE��RCV.ORIG_ORG_CODE),
                        DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,
                             DECODE(RCV.receivable_org_code,RCV.ORIG_ORG_CODE, RCV.Express_Orig_Org_Code,
                                                            RCV.DEST_ORG_CODE,RCV.Express_Dest_Org_Code��
                                                            RCV.receivable_org_code),RCV.receivable_org_code),
                        RCV.SOURCE_BILL_TYPE,
                        RCV.WAYBILL_NO, --�˵���
                        rcv.SOURCE_BILL_NO,
                        RCV.payment_type,
                        pod.pod_Type,
                        RCV.BILL_TYPE, --����������
                        RCV.PRODUCT_CODE, --��Ʒ����      
                        rcv.invoice_mark --��Ʊ���       
              )
       WHERE WO_AMOUNT != 0
         AND RCV_AMOUNT != 0;
    commit;
  
    --�˵���
    --��Ϊ��Ϊ����
    --ǩ���Ѻ�������������� 
    --��ǩ���Ѻ��� ��ȡ�����������    
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
      SELECT SYS_GUID() AS ID,
             V_PERIOD AS CHECK_OUT_PERIOD,
             NVL(CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             CHECK_OUT_ORG_CODE AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             --ǩ���ѻ���
             case
               when pod_Type = Pkg_Stl_Common.POD__POD_TYPE__POD then
                WO_AMOUNT
               else
                WO_AMOUNT * -1
             end AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ���� 
             --ƫ�ߣ����˵�Ϊ׼
             case
               when bill_type = pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --��Դ���� 
             NVL(SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --��������
             BILL_TYPE AS BILL_TYPE, -- ����������
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             --��ȷ��������Ϊҵ������ 
             account_date as business_date,
             payment_type as payment_type,
             invoice_mark as invoice_mark--��Ʊ���           
        FROM (SELECT RCV.CUSTOMER_CODE, --�ͻ�����  
                     --���Ϊ����Ӧ�գ����˲���Ϊ��������
                     case
                       when rcv.bill_type =
                            Pkg_Stl_Common.RECEIVABLE_BILL_TYPE_ORIGIN then
                        DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE��RCV.ORIG_ORG_CODE)
               else
                     DECODE(RCV.PRODUCT_CODE,
                     PKG_STL_COMMON.PRODUCT_CODE_PKG,
                     DECODE(RCV.receivable_org_code,
                     RCV.ORIG_ORG_CODE,
                     RCV.Express_Orig_Org_Code,
                     RCV.DEST_ORG_CODE,
                     RCV.Express_Dest_Org_Code��RCV.receivable_org_code),
                     RCV.receivable_org_code)
                     end AS CHECK_OUT_ORG_CODE, -- Ӧ����֯
                     RCV.WAYBILL_NO, --�˵��� 
                     MIN(RCV.ACCOUNT_DATE) as ACCOUNT_DATE, --������� 
                     RCV.BILL_TYPE, --����������
                     MIN(RCV.AMOUNT) RCV_AMOUNT, --Ӧ�ն�  
                     pod.pod_type as pod_type, --ǩ������              
                     SUM(WO.AMOUNT) as WO_AMOUNT, --������
                     rcv.payment_type as payment_type, --���ʽ
                     rcv.SOURCE_BILL_TYPE as SOURCE_BILL_TYPE, --��������
                     rcv.SOURCE_BILL_NO as SOURCE_BILL_NO, --��Դ����
                     RCV.PRODUCT_CODE, --��Ʒ����
                     rcv.invoice_mark --��Ʊ���
                FROM STL.T_STL_POD POD
               inner JOIN STL.T_STL_BILL_RECEIVABLE RCV
                  ON RCV.WAYBILL_NO = POD.WAYBILL_NO
                 AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
               inner JOIN STL.T_STL_BILL_WRITEOFF WO
              --��Ϊ����
                  ON (WO.END_NO = RCV.RECEIVABLE_NO)
               WHERE RCV.SOURCE_BILL_TYPE =
                     PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W --��Դ���˵�
                    --ָ����������
                    --�������ͣ�ʼ���˷ѡ������˷ѡ����˵�����ƫ�ߵ��������˴��ջ��
                 and RCV.bill_type in
                     (pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN,
                      pkg_stl_common.RECEIVABLE_BILL_TYPE_DEST,
                      pkg_stl_common.RECEIVABLE_BILL_TYPE_AA,
                      pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL,
                      pkg_stl_common.RECEIVABLE_BILL_TYPE_AAC,
                      pkg_stl_common.RECEIVABLE_BILL_TYPE_DL,
                      pkg_stl_common.RECEIVABLE_BILL_TYPE_DLC)
                 AND WO.ACCOUNT_DATE <= POD.POD_DATE --����ʱ����С��ǩ��ʱ��
                 AND POD.POD_DATE >= P_BEGIN_DATE
                 AND POD.POD_DATE < P_END_DATE
                    --ֻҪǩ���뷴ǩ�յ�����
                 and pod.pod_Type in
                     (PKG_STL_COMMON.POD__POD_TYPE__POD,
                      PKG_STL_COMMON.POD__POD_TYPE__UPD)
                    --ָ������
                 and wo.ORG_CODE = p_org_code
               GROUP BY RCV.CUSTOMER_CODE, --�ͻ�����
                        DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE��RCV.ORIG_ORG_CODE),
                        DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,
                             DECODE(RCV.receivable_org_code,RCV.ORIG_ORG_CODE, RCV.Express_Orig_Org_Code,
                                                            RCV.DEST_ORG_CODE,RCV.Express_Dest_Org_Code��
                                                            RCV.receivable_org_code),RCV.receivable_org_code),
                        RCV.SOURCE_BILL_TYPE,
                        RCV.WAYBILL_NO, --�˵���
                        rcv.SOURCE_BILL_NO,
                        RCV.payment_type,
                        pod.pod_Type,
                        RCV.BILL_TYPE, --����������
                        RCV.PRODUCT_CODE, --��Ʒ����  
                        rcv.invoice_mark --��Ʊ���          
              )
       WHERE WO_AMOUNT != 0
         AND RCV_AMOUNT != 0;
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
                                        P_END_DATE - 1 / (24 * 60 * 60),
                                        'PKG_STL_BALANCE_DEPOSIT_TF.func_daily_detail_writeoff',
                                        P_ORG_CODE || '-Ԥ��-��ת��ĩ���˺������');
      --����ʧ�ܱ�־
      RETURN FALSE;
  END;

END PKG_STL_BALANCE_DEPOSIT_TF;
/
