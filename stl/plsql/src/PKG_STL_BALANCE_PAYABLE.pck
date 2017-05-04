CREATE OR REPLACE PACKAGE PKG_STL_BALANCE_PAYABLE IS

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
  --����ÿ�շ������
  ------------------------------------------------------------------
  FUNCTION FUNC_DAILY_DETAIL_PRODUCE(P_ORG_CODE     STRING, --��֯����
                                     P_BEGIN_DATE   DATE, --��ʼ����
                                     P_END_DATE     DATE, --��������
                                     P_PERIOD       STRING, --�����ڼ�
                                     P_BALANCE_TYPE IN STRING -- �������
                                     ) RETURN BOOLEAN;

  ------------------------------------------------------------------
  --����ÿ�պ������
  ------------------------------------------------------------------
  FUNCTION FUNC_DAILY_DETAIL_WRITEOFF(P_ORG_CODE     STRING, --��֯����
                                      P_BEGIN_DATE   DATE, --��ʼ����
                                      P_END_DATE     DATE, --��������
                                      P_PERIOD       STRING, --�����ڼ�
                                      P_BALANCE_TYPE IN STRING -- �������
                                      ) RETURN BOOLEAN;

END PKG_STL_BALANCE_PAYABLE;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_BALANCE_PAYABLE IS

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
  
    --����ÿ�շ�����
    V_RESULT := FUNC_DAILY_DETAIL_PRODUCE(P_ORG_CODE,
                                          P_BEGIN_DATE,
                                          P_END_DATE,
                                          P_PERIOD,
                                          P_BALANCE_TYPE);
  
    --�������󷵻�
    IF V_RESULT = FALSE THEN
      --�����쳣
      RAISE_APPLICATION_ERROR('-20002', ' ����Ӧ��,ÿ�շ�����ʱ�����쳣��');
    END IF;
  
    --����ÿ�պ�����
    V_RESULT := FUNC_DAILY_DETAIL_WRITEOFF(P_ORG_CODE,
                                           P_BEGIN_DATE,
                                           P_END_DATE,
                                           P_PERIOD,
                                           P_BALANCE_TYPE);
  
    --�������󷵻�
    IF V_RESULT = FALSE THEN
      --�����쳣
      RAISE_APPLICATION_ERROR('-20002', ' ����Ӧ��,ÿ�պ���ʱ�����쳣��');
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
                                        'pkg_stl_balance_payable.func_count_balance',
                                        P_ORG_CODE || '-Ӧ����ĩ���˵���');
      --����ʧ�ܱ�־
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
  
    --1) ��������Ӧ�������������ͣ���������ѣ�
    INSERT INTO STV.T_STL_DAILY_BALANCE_TMP
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
       BUSINESS_DATE,
       PAYMENT_TYPE,
       invoice_mark--��Ʊ���    
       )
      SELECT SYS_GUID() AS ID,
             V_PERIOD AS CHECK_OUT_PERIOD,
             NVL(BILL.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             DECODE(BILL.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(BILL.PAYABLE_ORG_CODE,
                           BILL.ORIG_ORG_CODE,
                           BILL.Express_Orig_Org_Code,
                           BILL.DEST_ORG_CODE,
                           BILL.Express_Dest_Org_Code��BILL.PAYABLE_ORG_CODE),
                    BILL.PAYABLE_ORG_CODE) AS PAYABLE_ORG_CODE, --�������Ҫת��
             NVL(BILL.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE,
             0 AS BEGIN_AMOUNT,
             NVL(BILL.AMOUNT, 0) AS PRODUCE_AMOUNT,
             0 AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             -- ƫ�ߴ���ɱ�(���˵�Ϊ׼������ʹ����Դ����)
             case
               when bill_type =
                    PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE then
                NVL(BILL.WAYBILL_NO, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(BILL.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --��Դ����
             NVL(SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE, --��Դ�������
             BILL.CURRENCY_CODE AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BALANCE_TYPE AS STL_TYPE,
             BILL.BILL_TYPE AS BILL_TYPE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             BILL.BUSINESS_DATE AS BUSINESS_DATE,
             PKG_STL_COMMON.NULL_VALUE AS PAYMENT_TYPE,
             bill.invoice_mark --��Ʊ���            
        FROM STL.T_STL_BILL_PAYABLE BILL
       WHERE BILL.ACCOUNT_DATE >= P_BEGIN_DATE
         AND BILL.ACCOUNT_DATE < P_END_DATE
         AND BILL.PAYABLE_ORG_CODE = P_ORG_CODE
         AND BILL.BILL_TYPE IN (PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR, -- ���չ�˾�˷�
                                PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_ORIGINAL, -- ���˳�������Ӧ��
                                PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY, -- ���˵������Ӧ��
                                PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER, -- ��������Ӧ��
                                PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE, -- ƫ�ߴ���ɱ�
                                PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM, -- ����Ӧ��
                                PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND, -- ���˷�Ӧ��
                                PKG_STL_COMMON.PAYABLE_BILL_TYPE_COMPENSATION, -- ���񲹾�Ӧ��
                                PKG_STL_COMMON.PAYABLE_BILL_TYPE_LANDSTOWAGE, --������ⷢ�ɱ�
                                PKG_STL_COMMON.PAYABLE_BILLTYPE_LAND_OTHER, --���������Ӧ��
                                PKG_STL_COMMON.PAYABLE_BILL_TYPE_PO --ƫ������Ӧ��
                                );
    commit;
  
    --2�� �����������˴��ջ���(
    INSERT INTO STV.T_STL_DAILY_BALANCE_TMP
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
       BUSINESS_DATE,
       PAYMENT_TYPE,
       invoice_mark--��Ʊ���   
       )
      SELECT SYS_GUID() AS ID,
             V_PERIOD AS CHECK_OUT_PERIOD,
             NVL(PAY.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PAY.PAYABLE_ORG_CODE,
                           PAY.ORIG_ORG_CODE,
                           PAY.Express_Orig_Org_Code,
                           PAY.DEST_ORG_CODE,
                           PAY.Express_Dest_Org_Code��PAY.PAYABLE_ORG_CODE),
                    PAY.PAYABLE_ORG_CODE) AS PAYABLE_ORG_CODE, --�������Ҫת��
             NVL(PAY.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE,
             0 AS BEGIN_AMOUNT,
             PAY.AMOUNT *
             DECODE(POD.POD_TYPE, PKG_STL_COMMON.POD__POD_TYPE__POD, 1, -1),
             0 AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(PAY.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO, --��Դ����
             NVL(PAY.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE, --��Դ�������
             --�ұ�
             PAY.Currency_Code         AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE     AS ACTIVE,
             P_BALANCE_TYPE            AS STL_TYPE,
             PAY.BILL_TYPE             AS BILL_TYPE,
             P_BEGIN_DATE              AS BALANCE_TIME,
             SYSDATE                   AS CREATE_TIME,
             PAY.BUSINESS_DATE         AS BUSINESS_DATE,
             PKG_STL_COMMON.NULL_VALUE AS PAYMENT_TYPE,
             pay.invoice_mark as invoice_mark--��Ʊ���             
        FROM STL.T_STL_POD POD
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.WAYBILL_NO = POD.WAYBILL_NO
       WHERE POD.POD_TYPE IN
             (PKG_STL_COMMON.POD__POD_TYPE__POD,
              PKG_STL_COMMON.POD__POD_TYPE__UPD)
         AND POD.POD_DATE >= P_BEGIN_DATE
         AND POD.POD_DATE < P_END_DATE
         AND PAY.ACCOUNT_DATE < POD.POD_DATE
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC
         AND PAY.PRODUCT_CODE IN
             (PKG_STL_COMMON.PRODUCT_CODE_AF,
              PKG_STL_COMMON.PRODUCT_CODE_PKG) --�����
         AND PAY.PAYABLE_ORG_CODE = P_ORG_CODE;
    commit;
  
    --3�� ��������ר��Ӧ�����ջ���
    INSERT INTO STV.T_STL_DAILY_BALANCE_TMP
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
       BUSINESS_DATE,
       PAYMENT_TYPE,
       invoice_mark--��Ʊ���     
       )
      SELECT SYS_GUID() AS ID,
             V_PERIOD AS CHECK_OUT_PERIOD,
             NVL(RCV.DELIVERY_CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             RCV.ORIG_ORG_CODE AS PAYABLE_ORG_CODE,
             NVL(RCV.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE,
             0 AS BEGIN_AMOUNT,
             WO.AMOUNT AS PRODUCE_AMOUNT,
             0 AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(RCV.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO, --��Դ����
             NVL(RCV.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE, --��Դ�������
             RCV.CURRENCY_CODE AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BALANCE_TYPE AS STL_TYPE,
             PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC AS BILL_TYPE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             RCV.BUSINESS_DATE AS BUSINESS_DATE,
             PKG_STL_COMMON.NULL_VALUE AS PAYMENT_TYPE,
             rcv.invoice_mark as invoice--��Ʊ���           
        FROM STL.T_STL_BILL_WRITEOFF WO
      --���
       INNER JOIN STL.T_STL_BILL_REPAYMENT RPMT
          ON RPMT.REPAYMENT_NO = WO.BEGIN_NO
         AND RPMT.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      --Ӧ�յ�
       INNER JOIN STL.T_STL_BILL_RECEIVABLE RCV
          ON RCV.RECEIVABLE_NO = WO.END_NO
         AND RCV.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
      --�����Ӧ��
       WHERE WO.WRITEOFF_TYPE = PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RR
         AND RCV.BILL_TYPE = PKG_STL_COMMON.RECEIVABLE_BILL_TYPE_COD
            --��������
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND RCV.PRODUCT_CODE <> PKG_STL_COMMON.PRODUCT_CODE_PKG
            --������֯
         AND wo.org_code = P_ORG_CODE;
  
    COMMIT;
  
    RETURN TRUE;
  
    --��׽�쳣
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'pkg_stl_balance_payable.FUNC_DAILY_DETAIL_PRODUCE',
                                        P_ORG_CODE || '-Ӧ����ĩ���˺��㷢����');
    
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
  
    --1) ��������Ӧ�������������͡�������
    INSERT INTO STV.T_STL_DAILY_BALANCE_TMP
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
       BUSINESS_DATE,
       PAYMENT_TYPE,
       invoice_mark--��Ʊ���        
       )
      SELECT SYS_GUID() AS ID,
             V_PERIOD AS CHECK_OUT_PERIOD,
             NVL(PAY.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             --2013-8-12 ͳһʹ��Ӧ������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PAY.PAYABLE_ORG_CODE,
                           PAY.ORIG_ORG_CODE,
                           PAY.Express_Orig_Org_Code,
                           PAY.DEST_ORG_CODE,
                           PAY.Express_Dest_Org_Code��PAY.PAYABLE_ORG_CODE),
                    PAY.PAYABLE_ORG_CODE) AS CHECK_OUT_ORG_CODE, --�������Ҫת��
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             --�쵥ȡ�����Ǻ쵥ȥ��
             PMTD.PAY_AMOUNT *
             DECODE(PMT.IS_RED_BACK, PKG_STL_COMMON.IS_RED_BACK_YES, -1, 1),
             0 AS END_AMOUNT,
             NVL(PAY.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ����
             -- ƫ�ߴ���ɱ�(���˵�Ϊ׼������ʹ����Դ����)
             case
               when pay.bill_type =
                    PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE then
                NVL(pay.WAYBILL_NO, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(pay.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --��Դ����
             NVL(PAY.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --��������
             PAY.BILL_TYPE AS BILL_TYPE, -- ����������
             PAY.CURRENCY_CODE AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             PAY.BUSINESS_DATE AS BUSINESS_DATE,
             PKG_STL_COMMON.NULL_VALUE AS PAYMENT_TYPE,
             pay.invoice_mark as invoice_mark --��Ʊ���             
      --�������Ӧ����
        FROM STL.T_STL_BILL_PAYMENT PMT
        JOIN STL.T_STL_BILL_PAYMENT_D PMTD
          ON PMTD.PAYMENT_NO = PMT.PAYMENT_NO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = PMTD.SOURCE_BILL_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE PMT.PAYMENT_ORG_CODE = P_ORG_CODE
         AND PMT.ACCOUNT_DATE >= P_BEGIN_DATE --��������
         AND PMT.ACCOUNT_DATE < P_END_DATE
         AND PAY.BILL_TYPE IN (PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR, -- ���չ�˾�˷�
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_ORIGINAL, -- ���˳�������Ӧ��
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY, -- ���˵������Ӧ��
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER, -- ��������Ӧ��
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE, -- ƫ�ߴ���ɱ�
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM, -- ����Ӧ��
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND, -- ���˷�Ӧ��
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_COMPENSATION, --���񲹾�
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_LANDSTOWAGE, --������ⷢ�ɱ�
                               PKG_STL_COMMON.PAYABLE_BILLTYPE_LAND_OTHER, --���������Ӧ��
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_PO --ƫ������Ӧ��
                               );
    commit;
  
    --2) ��������Ӧ�������������͡�������(Ӧ�ճ�Ӧ��,Ԥ����Ӧ��)
    INSERT INTO STV.T_STL_DAILY_BALANCE_TMP
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
       BUSINESS_DATE,
       PAYMENT_TYPE,       
       invoice_mark --��Ʊ���    
       )    
      SELECT SYS_GUID() AS ID,
             V_PERIOD AS CHECK_OUT_PERIOD,
             NVL(PAY.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             --2013-8-12 ͳһʹ��Ӧ������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PAY.PAYABLE_ORG_CODE,
                           PAY.ORIG_ORG_CODE,
                           PAY.Express_Orig_Org_Code,
                           PAY.DEST_ORG_CODE,
                           PAY.Express_Dest_Org_Code��PAY.PAYABLE_ORG_CODE),
                    PAY.PAYABLE_ORG_CODE) AS CHECK_OUT_ORG_CODE, --�������Ҫת��
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             WO.AMOUNT AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(PAY.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ����
             -- ƫ�ߴ���ɱ�(���˵�Ϊ׼������ʹ����Դ����)
             case
               when pay.bill_type =
                    PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE then
                NVL(pay.WAYBILL_NO, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(pay.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --��Դ����
             NVL(PAY.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --��������
             PAY.BILL_TYPE AS BILL_TYPE, -- ����������
             PAY.CURRENCY_CODE AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             PAY.BUSINESS_DATE AS BUSINESS_DATE,
             PKG_STL_COMMON.NULL_VALUE AS PAYMENT_TYPE,
             pay.invoice_mark as invoice_mark--��Ʊ���           
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON PAY.PAYABLE_NO = WO.END_NO --��������
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.ORG_CODE = P_ORG_CODE --��������
         AND WO.WRITEOFF_TYPE IN
             (PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP,
              PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_AP) --Ӧ�ճ�Ӧ����Ԥ����Ӧ��
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE --��������
         AND WO.ACCOUNT_DATE < P_END_DATE
         AND PAY.BILL_TYPE IN (PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR, -- ���չ�˾�˷�
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_ORIGINAL, -- ���˳�������Ӧ��
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_DELIVERY, -- ���˵������Ӧ��
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_AIR_OTHER, -- ��������Ӧ��
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_PARTIAL_LINE, -- ƫ�ߴ���ɱ�
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_CLAIM, -- ����Ӧ��
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_REFUND, -- ���˷�Ӧ��
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_COMPENSATION, --���񲹾�Ӧ��
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_LANDSTOWAGE, --������ⷢ�ɱ�
                               PKG_STL_COMMON.PAYABLE_BILLTYPE_LAND_OTHER, --���������Ӧ��
                               PKG_STL_COMMON.PAYABLE_BILL_TYPE_PO --ƫ������Ӧ��
                               );
  
    commit;
  
    --3) ��������Ӧ�����ջ�����ˡ�ר�ߣ�
    INSERT INTO STV.T_STL_DAILY_BALANCE_TMP
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
       BUSINESS_DATE,
       PAYMENT_TYPE,
       invoice_mark--��Ʊ���    
       )
      SELECT SYS_GUID() AS ID,
             V_PERIOD AS CHECK_OUT_PERIOD,
             NVL(PAY.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             --2013-8-12ͳһʹ��Ӧ������
             DECODE(PAY.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    DECODE(PAY.PAYABLE_ORG_CODE,
                           PAY.ORIG_ORG_CODE,
                           PAY.Express_Orig_Org_Code,
                           PAY.DEST_ORG_CODE,
                           PAY.Express_Dest_Org_Code��PAY.PAYABLE_ORG_CODE),
                    PAY.PAYABLE_ORG_CODE) AS CHECK_OUT_ORG_CODE, --�������Ҫת��
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             WO.AMOUNT AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(PAY.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ����
             NVL(PAY.WAYBILL_NO, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_NO, --��Դ����
             NVL(PAY.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --��������
             PAY.BILL_TYPE AS BILL_TYPE, -- ����������
             PAY.CURRENCY_CODE AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             PAY.BUSINESS_DATE AS BUSINESS_DATE,
             PKG_STL_COMMON.NULL_VALUE AS PAYMENT_TYPE,
             pay.invoice_mark as invoice_mark--��Ʊ���            
        FROM STL.T_STL_BILL_WRITEOFF WO
        JOIN STL.T_STL_BILL_PAYABLE PAY
          ON WO.END_NO = PAY.PAYABLE_NO
         AND PAY.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE WO.WRITEOFF_TYPE IN
             (PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_RP,
              PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_AP,
              PKG_STL_COMMON.WRITEOFF_WRITEOFF_TYPE_PP)
         AND WO.ORG_CODE = P_ORG_CODE
         AND PAY.BILL_TYPE = PKG_STL_COMMON.PAYABLE_BILL_TYPE_APC
         AND WO.ACCOUNT_DATE >= P_BEGIN_DATE
         AND WO.ACCOUNT_DATE < P_END_DATE;
  
    COMMIT;
  
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
                                        'PKG_STL_BALANCE_PAYABLE.FUNC_DAILY_DETAIL_WRITEOFF',
                                        P_ORG_CODE || '-Ӧ����ĩ���˺������������');
      --����ʧ�ܱ�־
      RETURN FALSE;
  END;

END PKG_STL_BALANCE_PAYABLE;
/
