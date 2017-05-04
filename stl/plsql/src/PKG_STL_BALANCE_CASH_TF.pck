CREATE OR REPLACE PACKAGE STV.PKG_STL_BALANCE_CASH_TF IS
  --�ֽ���ĩ�������
  FUNCTION FUNC_COUNT_BALANCE(P_ORG_CODE     STRING, --��֯����
                              P_BEGIN_DATE   DATE, --��ʼ����
                              P_END_DATE     DATE, --��������
                              P_PERIOD       STRING, --�����ڼ�
                              P_BALANCE_TYPE STRING --��������
                              ) RETURN BOOLEAN;
  --�ֽ���ĩ���˷���
  FUNCTION FUNC_DAILY_DETAIL_PRODUCE(P_ORG_CODE     STRING, --��֯����
                                     P_BEGIN_DATE   DATE, --��ʼ����
                                     P_END_DATE     DATE, --��������
                                     P_PERIOD       STRING, --�����ڼ�
                                     P_BALANCE_TYPE IN STRING -- �������
                                     ) RETURN BOOLEAN;
  --�ֽ���ĩ���˺���
  FUNCTION FUNC_DAILY_DETAIL_WRITEOFF(P_ORG_CODE     STRING, --��֯����
                                      P_BEGIN_DATE   DATE, --��ʼ����
                                      P_END_DATE     DATE, --��������
                                      P_PERIOD       STRING, --�ڼ�
                                      P_BALANCE_TYPE IN STRING -- �������
                                      ) RETURN BOOLEAN;
END;
/
CREATE OR REPLACE PACKAGE BODY STV.PKG_STL_BALANCE_CASH_TF IS
  --�ֽ���ĩ�������
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
      RAISE_APPLICATION_ERROR('-20002',
                              '����Ԥ��-��ת--�ֽ𣬷�������쳣');
    END IF;

    V_RESULT := FUNC_DAILY_DETAIL_WRITEOFF(P_ORG_CODE,
                                           P_BEGIN_DATE,
                                           P_END_DATE,
                                           P_PERIOD,
                                           P_BALANCE_TYPE);
    --�������󷵻�
    IF V_RESULT = FALSE THEN
      RAISE_APPLICATION_ERROR('-20002',
                              '����Ԥ��-��ת--�ֽ𣬺�������쳣');
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
                                        'pkg_stl_balance_cash_tf.func_count_balance',
                                        P_ORG_CODE || '-�ֽ�-��ת��ĩ���˵���');

      --���سɹ���־
      RETURN FALSE;
  END;

  --�ֽ���ĩ���˷���
  FUNCTION FUNC_DAILY_DETAIL_PRODUCE(P_ORG_CODE     STRING, --��֯����
                                     P_BEGIN_DATE   DATE, --��ʼ����
                                     P_END_DATE     DATE, --��������
                                     P_PERIOD       STRING, --�����ڼ�
                                     P_BALANCE_TYPE IN STRING -- �������
                                     ) RETURN BOOLEAN IS
  BEGIN
    --���㷢����
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
       INVOICE_MARK --��Ʊ���
       )
      SELECT SYS_GUID() AS ID,
             P_PERIOD AS CHECK_OUT_PERIOD,
             CA.CUSTOMER_CODE AS CUSTOMER_CODE,
             DECODE(CA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    NVL(CA.EXPRESS_ORIG_ORG_CODE, CA.GENERATING_ORG_CODE),
                    CA.GENERATING_ORG_CODE) AS CHECK_OUT_ORG_CODE, --���벿��
             CA.PRODUCT_CODE AS PRODUCT_CODE,
             0 AS BEGIN_AMOUNT,
             CA.AMOUNT AS PRODUCE_AMOUNT,
             0 AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(CA.SOURCE_BILL_NO, CA.WAYBILL_NO) AS SOURE_BILL_NO,
             CA.SOURCE_BILL_TYPE AS SOURE_BILL_TYPE,
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BALANCE_TYPE AS STL_TYPE,
             CA.BILL_TYPE AS BILL_TYPE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             CA.ACCOUNT_DATE AS BUSSINESS_DATE,
             CA.PAYMENT_TYPE AS PAYMENT_TYPE,
             CA.INVOICE_MARK AS INVOICE_MARK --��Ʊ���
        FROM STL.T_STL_BILL_CASH_COLLECTION CA
       WHERE CA.ACCOUNT_DATE >= P_BEGIN_DATE
         AND CA.ACCOUNT_DATE < P_END_DATE
         AND CA.GENERATING_ORG_CODE = P_ORG_CODE
         AND CA.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.CASH_COLL_SOURCE_BILL_TYPE_W;
    COMMIT;
    RETURN TRUE;
    --��׽�쳣
  EXCEPTION

    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE - 1 / (24 * 60 * 60),
                                        'pkg_stl_balance_cash_tf.FUNC_DAILY_DETAIL_PRODUCE',
                                        P_ORG_CODE || '-�ֽ�-��ת��ĩ���˺��㷢����');

      --����ʧ�ܱ�־
      RETURN FALSE;

  END;
  --�ֽ���ĩ���˺���
  FUNCTION FUNC_DAILY_DETAIL_WRITEOFF(P_ORG_CODE     STRING, --��֯����
                                      P_BEGIN_DATE   DATE, --��ʼ����
                                      P_END_DATE     DATE, --��������
                                      P_PERIOD       STRING, --�ڼ�
                                      P_BALANCE_TYPE IN STRING -- �������
                                      ) RETURN BOOLEAN IS
  --��ŵ���ǩ������ֽ��տ
  CURSOR POD_CORSOR IS SELECT DISTINCT CA.SOURCE_BILL_NO FROM STL.T_STL_BILL_CASH_COLLECTION CA
  INNER JOIN STL.T_STL_POD POD ON POD.WAYBILL_NO = CA.SOURCE_BILL_NO
  WHERE POD.POD_TYPE IN (Pkg_Stl_Common.POD__POD_TYPE__POD,PKG_STL_COMMON.POD__POD_TYPE__UPD)
  AND POD.POD_DATE>= P_BEGIN_DATE
  AND POD.POD_DATE < P_END_DATE
  AND CA.SOURCE_BILL_TYPE = PKG_STL_COMMON.CASH_COLL_SOURCE_BILL_TYPE_W;
  --�쳣ǩ���α�
  CURSOR EX_CORSOR IS SELECT DISTINCT CA.SOURCE_BILL_NO FROM STL.T_STL_BILL_CASH_COLLECTION CA
  INNER JOIN STL.T_STL_OUT_STOCK_EXCEPTION EX ON EX.WAYBILL_NO = CA.SOURCE_BILL_NO
  WHERE EX.CREATE_TIME >= P_BEGIN_DATE
  AND EX.CREATE_TIME < P_END_DATE
  AND CA.SOURCE_BILL_TYPE = PKG_STL_COMMON.CASH_COLL_SOURCE_BILL_TYPE_W;

  CASH_COLLECTION_ROW STL.T_STL_BILL_CASH_COLLECTION%ROWTYPE;
  BEGIN
    FOR CASH_COLLECTION_ROW IN POD_CORSOR LOOP
    --����������(ǩ�շ�ǩ��)
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
       INVOICE_MARK --��Ʊ���
       )
      SELECT SYS_GUID() AS ID,
             P_PERIOD AS CHECK_OUT_PERIOD,
             CA.CUSTOMER_CODE AS CUSTOMER_CODE,
             DECODE(CA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    NVL(CA.EXPRESS_ORIG_ORG_CODE, CA.GENERATING_ORG_CODE),
                    CA.GENERATING_ORG_CODE) AS CHECK_OUT_ORG_CODE, --���벿��
             CA.PRODUCT_CODE AS PRODUCT_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
              CA.AMOUNT * (CASE
               WHEN SPOD.RS > 0 THEN
                1
               WHEN SPOD.RS <0 THEN
               -1
               ELSE
                0
             END) AS WRITEOFF_AMOUNT, --ǩ��ȡ��������ǩ��ȡ����
             0 AS END_AMOUNT,
             NVL(CA.SOURCE_BILL_NO, CA.WAYBILL_NO) AS SOURE_BILL_NO,
             CA.SOURCE_BILL_TYPE AS SOURE_BILL_TYPE,
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BALANCE_TYPE AS STL_TYPE,
             CA.BILL_TYPE AS BILL_TYPE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             CA.ACCOUNT_DATE AS BUSSINESS_DATE,
             CA.PAYMENT_TYPE AS PAYMENT_TYPE,
             CA.INVOICE_MARK --��Ʊ���
        FROM STL.T_STL_BILL_CASH_COLLECTION CA
       INNER JOIN (SELECT SUM(DECODE(PP.POD_TYPE,
                                     PKG_STL_COMMON.POD__POD_TYPE__POD,
                                     1,
                                     PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                     -1,
                                     0)) RS,
                          PP.WAYBILL_NO
                     FROM STL.T_STL_POD PP
                    WHERE PP.POD_DATE < P_END_DATE
                    AND PP.POD_DATE>= P_BEGIN_DATE
                    AND PP.POD_TYPE IN (Pkg_Stl_Common.POD__POD_TYPE__POD,PKG_STL_COMMON.POD__POD_TYPE__UPD)
                    GROUP BY PP.WAYBILL_NO) SPOD
          ON CA.WAYBILL_NO = SPOD.WAYBILL_NO
       WHERE CA.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.CASH_COLL_SOURCE_BILL_TYPE_W
         AND CA.GENERATING_ORG_CODE = P_ORG_CODE
         AND CA.ID IN (
         SELECT ID FROM (SELECT ID,CO.IS_RED_BACK FROM STL.T_STL_BILL_CASH_COLLECTION CO WHERE
         CO.SOURCE_BILL_NO = CASH_COLLECTION_ROW.SOURCE_BILL_NO
         AND CO.ACCOUNT_DATE <= P_END_DATE
         AND CO.SOURCE_BILL_TYPE = PKG_STL_COMMON.CASH_COLL_SOURCE_BILL_TYPE_W
         ORDER BY CO.ACCOUNT_DATE DESC,CO.CREATE_TIME DESC
          ) WHERE ROWNUM <=1 AND IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         );
     END LOOP;
    COMMIT;

    FOR CASH_COLLECTION_ROW IN EX_CORSOR LOOP
    --���������������������Υ��Ʒ��
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
       INVOICE_MARK --��Ʊ���
       )
      SELECT SYS_GUID() AS ID,
             P_PERIOD AS CHECK_OUT_PERIOD,
             CA.CUSTOMER_CODE AS CUSTOMER_CODE,
             DECODE(CA.PRODUCT_CODE,
                    PKG_STL_COMMON.PRODUCT_CODE_PKG,
                    NVL(CA.EXPRESS_ORIG_ORG_CODE, CA.GENERATING_ORG_CODE),
                    CA.GENERATING_ORG_CODE) AS CHECK_OUT_ORG_CODE, --���벿��
             CA.PRODUCT_CODE AS PRODUCT_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             CA.AMOUNT AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(CA.SOURCE_BILL_NO, CA.WAYBILL_NO) AS SOURE_BILL_NO,
             CA.SOURCE_BILL_TYPE AS SOURE_BILL_TYPE,
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BALANCE_TYPE AS STL_TYPE,
             CA.BILL_TYPE AS BILL_TYPE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             CA.ACCOUNT_DATE AS BUSSINESS_DATE,
             CA.PAYMENT_TYPE AS PAYMENT_TYPE,
             CA.INVOICE_MARK AS INVOICE_MARK --��Ʊ���
        FROM STL.T_STL_BILL_CASH_COLLECTION CA
       INNER JOIN STL.T_STL_OUT_STOCK_EXCEPTION EX
          ON EX.WAYBILL_NO = CA.WAYBILL_NO
       WHERE  CA.GENERATING_ORG_CODE = P_ORG_CODE
         AND CA.SOURCE_BILL_TYPE =
             PKG_STL_COMMON.CASH_COLL_SOURCE_BILL_TYPE_W
         AND EX.CREATE_TIME >= P_BEGIN_DATE
         AND EX.CREATE_TIME <P_END_DATE
         --ȡ���µ�һ���ֽ��տ�Ľ��
         AND CA.ID IN (
          SELECT ID FROM (SELECT ID,CO.IS_RED_BACK FROM STL.T_STL_BILL_CASH_COLLECTION CO WHERE
         CO.SOURCE_BILL_NO = CASH_COLLECTION_ROW.SOURCE_BILL_NO
         AND CO.ACCOUNT_DATE <= P_END_DATE
         AND CO.SOURCE_BILL_TYPE = PKG_STL_COMMON.CASH_COLL_SOURCE_BILL_TYPE_W
         ORDER BY CO.ACCOUNT_DATE DESC,CO.CREATE_TIME DESC
          ) WHERE ROWNUM <=1 AND IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
         );
    END LOOP;
    COMMIT;
    RETURN TRUE;
    --��׽�쳣
  EXCEPTION

    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE - 1 / (24 * 60 * 60),
                                        'pkg_stl_balance_cash_tf.FUNC_DAILY_DETAIL_WRITEOFF',
                                        P_ORG_CODE || '-�ֽ�-��ת��ĩ���˺˺�������');

      --����ʧ�ܱ�־
      RETURN FALSE;
  END;
END;
/
