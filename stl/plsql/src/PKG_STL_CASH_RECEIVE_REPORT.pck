CREATE OR REPLACE PACKAGE PKG_STL_CASH_RECEIVE_REPORT IS

  -- Author  : 088933-foss-zhangjiheng

  -- Created : 2012-12-10 19:27:16

  -- Purpose : �����ֽ����뱨��

  ------------------------------------------------------------------

  --�����ֽ����뱨������ÿ�춨ʱ������ȫ��˾������������뱨��

  ------------------------------------------------------------------

  PROCEDURE PROC_STL_CASH_RECEIVE_RPT(P_BEGIN_DATE IN TIMESTAMP, --��ʼ����

                                      P_END_DATE IN TIMESTAMP --��������

                                      );

  ------------------------------------------------------------------

  --�����ֽ����뱨�����ݵ�������������������뱨��

  ------------------------------------------------------------------

  PROCEDURE PROC_STL_CASH_RECEIVE_RPT_ORG(P_BEGIN_DATE IN TIMESTAMP, --��ʼ����

                                          P_END_DATE IN TIMESTAMP, --��������

                                          P_ORG_CODE IN VARCHAR2, --�������

                                          P_CREATE_USER_CODE IN VARCHAR2, --�����˹���

                                          P_CREATE_USER_NAME IN VARCHAR2, --����������

                                          P_CURRENT_TIME IN TIMESTAMP --��������

                                          );

END PKG_STL_CASH_RECEIVE_REPORT;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_CASH_RECEIVE_REPORT IS

  -- Author  : 088933-foss-zhangjiheng
  -- Created : 2012-12-10 19:27:16
  -- Purpose : ���ɽɿ��

  ------------------------------------------------------------------
  --���ɽɿ������ÿ�춨ʱ������ȫ��˾������������뱨��
  ------------------------------------------------------------------
  PROCEDURE PROC_STL_CASH_RECEIVE_RPT(P_BEGIN_DATE IN TIMESTAMP, --��ʼ����
                                      P_END_DATE   IN TIMESTAMP --��������
                                      ) IS
    V_USER_CODE    VARCHAR2(50); --�����˹���
    V_USER_NAME    VARCHAR2(50); --����������
    V_CURRENT_TIME DATE; --��������
    --�����α���������Ч����֯
    CURSOR ORG_CURSOR IS
      SELECT CODE
        FROM BSE.T_BAS_SALES_DEPARTMENT SALEORGS
      --��Ч����֯
       WHERE SALEORGS.ACTIVE = STV.PKG_STL_COMMON.ACTIVE;
    --�����α�����
    ORG_CURSOR_ROW ORG_CURSOR%ROWTYPE;
  BEGIN
    V_USER_CODE    := 'SYSTEM';
    V_USER_NAME    := 'ϵͳ';
    V_CURRENT_TIME := SYSDATE;
    FOR ORG_CURSOR_ROW IN ORG_CURSOR LOOP
      --���ô���ҵ��洢����

      PROC_STL_CASH_RECEIVE_RPT_ORG(P_BEGIN_DATE,
                                    P_END_DATE,
                                    ORG_CURSOR_ROW.CODE,
                                    V_USER_CODE,
                                    V_USER_NAME,
                                    V_CURRENT_TIME);

    END LOOP;
  END;
  ------------------------------------------------------------------
  --�����ֽ����뱨�����ݵ�������������������뱨��
  ------------------------------------------------------------------
  PROCEDURE PROC_STL_CASH_RECEIVE_RPT_ORG(P_BEGIN_DATE       IN TIMESTAMP, --��ʼ����
                                          P_END_DATE         IN TIMESTAMP, --��������
                                          P_ORG_CODE         IN VARCHAR2, --�������
                                          P_CREATE_USER_CODE IN VARCHAR2, --�����˹���
                                          P_CREATE_USER_NAME IN VARCHAR2, --����������
                                          P_CURRENT_TIME     IN TIMESTAMP --��������
                                          ) IS
    V_REPORT_NO               VARCHAR2(50); --�ֽ����뱨����
    V_REPAYMENT_DATE          TIMESTAMP; --������˿�ʼ����
    V_CASH_COLLECTION_DATE    TIMESTAMP; --�ֽ��տ���˿�ʼ����
    V_DEPOSIT_RECEIVABLE_DATE TIMESTAMP; --Ԥ�յ����˿�ʼ����
    V_COUNT_NUM               INT; --ͳ���ֽ�ɿ����ϸ��¼������

  BEGIN
    V_REPORT_NO := SYS_GUID();

    /*�����ʼ���ڡ��������ڡ�������벻Ϊ���ҽ������ڴ��ڵ��ڿ�ʼ���ڣ���ִ�����ɱ���ҵ�񣬷��������־��Ϣ*/

    IF P_BEGIN_DATE IS NOT NULL AND P_END_DATE IS NOT NULL AND
       P_ORG_CODE IS NOT NULL AND P_END_DATE > P_BEGIN_DATE THEN

        /*��ѯ��ʷͳ�Ƽ�¼��ȡ������ֽ��տ��Ԥ�յ��ļ��˿�ʼ����*/
        /*����Ŀ�ʼ����ֻ���ڵ�һ��ͳ��ʱ���ã�������ʷ��¼ʱ����ʼ����ȡ��ʷ��¼��������ͳ������ */
        BEGIN
          SELECT REPAYMENT_DATE,
                 CASH_COLLECTION_DATE,
                 DEPOSIT_RECEIVABLE_DATE
            INTO V_REPAYMENT_DATE,
                 V_CASH_COLLECTION_DATE,
                 V_DEPOSIT_RECEIVABLE_DATE
            FROM STL.T_STL_CASH_COLLECTION_COUNT_H CCCH
           WHERE CCCH.ORG_CODE = P_ORG_CODE;
        EXCEPTION
          WHEN NO_DATA_FOUND THEN
            V_REPAYMENT_DATE          := P_BEGIN_DATE;
            V_CASH_COLLECTION_DATE    := P_BEGIN_DATE;
            V_DEPOSIT_RECEIVABLE_DATE := P_BEGIN_DATE;
          WHEN TOO_MANY_ROWS THEN
            --�������LOG��
            STV.PKG_STL_COMMON.PROC_STL_ERROR_LOG(NULL, --����
                                              P_BEGIN_DATE, --��ʼʱ��
                                              P_END_DATE, --��ֹʱ��
                                              '��ѯ��ʷͳ�Ƽ�¼��һ�����������ͬ�ļ�¼', --��������
                                              'PROC_STL_CASH_RECEIVE_RPT_ORG', --��ע
                                              'STL_USER_CODE', --������
                                              SQLCODE, --������
                                              SQLERRM, --������Ϣ
                                              SYSDATE);
          WHEN OTHERS THEN
            --�������LOG��
            stv.PKG_STL_COMMON.PROC_STL_ERROR_LOG(NULL, --����
                                              P_BEGIN_DATE, --��ʼʱ��
                                              P_END_DATE, --��ֹʱ��
                                              '���ɽɿ����ϸ', --��������
                                              'PROC_STL_CASH_RECEIVE_RPT_ORG', --��ע
                                              'STL_USER_CODE', --������
                                              SQLCODE, --������
                                              SQLERRM, --������Ϣ
                                              SYSDATE);

        END;

        BEGIN
          --�����ֽ�������ϸ����
          INSERT INTO STL.T_STL_CASH_COLLECTION_RPT_D
            (ID, --ID
             REPORT_NO, --������
             SOURCE_BILL_NO, --�˵���
             WAYBILL_NO,
             SOURCE_BILL_TYPE, --��Դ��������
             COLLECTION_ORG_CODE, --�տ�ű���
             COLLECTION_ORG_NAME, --�տ������
             CUSTOMER_CODE, --�ͻ�����
             CUSTOMER_NAME, --�ͻ�����
             /*Ӧ�ɿ���*/
             AMOUNT,
             /*δ�ɿ���*/
             OVERDUE_AMOUNT,
             /*�ѽɿ���*/
             PAID_AMOUNT,
             /*Ӫҵ����*/
             CLERKS_AMOUNT,
             /*��Ӫҵ����*/
             UNCLERKS_AMOUNT,
             /*Ԥ�տ���*/
             PRECOLLECTED_AMOUNT,
             CREATE_TIME,
             ACCOUNT_DATE,
             BUSINESS_DATE,
             PAYMENT_TYPE,
             CUSTOMER_TYPE,
             MODIFY_TIME)

            SELECT SYS_GUID(),
                   V_REPORT_NO,
                   SOURCE_BILL_NO,
                   WAYBILL_NO,
                   SOURCE_BILL_TYPE,
                   COLLECTION_ORG_CODE,
                   COLLECTION_ORG_NAME,
                   CUSTOMER_CODE,
                   CUSTOMER_NAME,
                   AMOUNT,
                   OVERDUE_AMOUNT,
                   PAID_AMOUNT,
                   CLERKS_AMOUNT,
                   UNCLERKS_AMOUNT,
                   PRECOLLECTED_AMOUNT,
                   P_CURRENT_TIME,
                   ACCOUNT_DATE,
                   BUSINESS_DATE,
                   PAYMENT_TYPE,
                   CUSTOMER_TYPE,
                   SYSDATE
              FROM (
                    --��ѯ�ֽ��տ
                    SELECT MAX(TBC.CASH_COLLECTION_NO) AS SOURCE_BILL_NO,
                            MAX(TBC.WAYBILL_NO) AS WAYBILL_NO,
                            MAX(STV.PKG_STL_COMMON.CASH_RPT_D_SOURCE_BILL_TYPE_XS) AS SOURCE_BILL_TYPE,
                            TBC.CUSTOMER_CODE AS CUSTOMER_CODE,
                            MAX(TBC.CUSTOMER_NAME) AS CUSTOMER_NAME,
                            MAX(TBC.COLLECTION_ORG_CODE) AS COLLECTION_ORG_CODE,
                            MAX(TBC.COLLECTION_ORG_NAME) AS COLLECTION_ORG_NAME,
                            SUM(TBC.AMOUNT) AS AMOUNT,
                            SUM(0) AS PAID_AMOUNT,
                            SUM(TBC.AMOUNT) AS OVERDUE_AMOUNT,
                            SUM(TBC.AMOUNT) AS CLERKS_AMOUNT,
                            SUM(0) AS UNCLERKS_AMOUNT,
                            SUM(0) AS PRECOLLECTED_AMOUNT,
                            MAX(TBC.ACCOUNT_DATE) AS ACCOUNT_DATE,
                            MAX(TBC.BUSINESS_DATE) AS BUSINESS_DATE,
                            MAX(TBC.PAYMENT_TYPE) AS PAYMENT_TYPE,
                            MAX(TBC.CUSTOMER_TYPE) AS CUSTOMER_TYPE
                      FROM STL.T_STL_BILL_CASH_COLLECTION TBC
                     WHERE TBC.ACCOUNT_DATE > V_CASH_COLLECTION_DATE
                       AND TBC.ACCOUNT_DATE < P_END_DATE
                       AND TBC.COLLECTION_ORG_CODE = P_ORG_CODE
                       AND TBC.PAYMENT_TYPE IN
                           (stv.PKG_STL_COMMON.PAYMENT_TYPE_CASH,
                            STV.PKG_STL_COMMON.PAYMENT_TYPE_CARD,
                            STV.PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
                            STV.PKG_STL_COMMON.PAYMENT_TYPE_NOTE,
                            STV.PKG_STL_COMMON.PAYMENT_TYPE_ONLINE)
                     GROUP BY TBC.SOURCE_BILL_NO, TBC.CUSTOMER_CODE
                    UNION ALL
                    --��ѯԤ�յ�
                    SELECT MAX(TSD.DEPOSIT_RECEIVED_NO) AS SOURCE_BILL_NO,
                           MAX(NULL) AS WAYBILL_NO,
                           MAX(STV.PKG_STL_COMMON.CASH_RPT_D_SOURCE_BILL_TYPE_US) AS SOURCE_BILL_TYPE,
                           MAX(TSD.CUSTOMER_CODE) AS CUSTOMER_CODE,
                           MAX(TSD.CUSTOMER_NAME) AS CUSTOMER_NAME,
                           MAX(TSD.COLLECTION_ORG_CODE) AS COLLECTION_ORG_CODE,
                           MAX(TSD.COLLECTION_ORG_NAME) AS COLLECTION_ORG_NAME,
                           SUM(TSD.AMOUNT) AS AMOUNT,
                           SUM(0) AS PAID_AMOUNT,
                           SUM(TSD.AMOUNT) AS OVERDUE_AMOUNT,
                           SUM(0) AS CLERKS_AMOUNT,
                           SUM(0) AS UNCLERKS_AMOUNT,
                           SUM(TSD.AMOUNT) AS PRECOLLECTED_AMOUNT,
                           MAX(TSD.ACCOUNT_DATE) AS ACCOUNT_DATE,
                           MAX(TSD.BUSINESS_DATE) AS BUSINESS_DATE,
                           MAX(TSD.PAYMENT_TYPE) AS PAYMENT_TYPE,
                           MAX(TSD.CUSTOMER_TYPE) AS CUSTOMER_TYPE
                      FROM STL.T_STL_BILL_DEPOSIT_RECEIVED TSD
                     WHERE TSD.ACCOUNT_DATE > V_DEPOSIT_RECEIVABLE_DATE
                       AND TSD.ACCOUNT_DATE < P_END_DATE
                       AND TSD.COLLECTION_ORG_CODE = P_ORG_CODE
                       AND TSD.PAYMENT_TYPE IN
                           (STV.PKG_STL_COMMON.PAYMENT_TYPE_CASH,
                            STV.PKG_STL_COMMON.PAYMENT_TYPE_CARD,
                            STV.PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
                            STV.PKG_STL_COMMON.PAYMENT_TYPE_NOTE,
                            STV.PKG_STL_COMMON.PAYMENT_TYPE_ONLINE)
                     GROUP BY TSD.DEPOSIT_RECEIVED_NO
                    UNION ALL
                    --��ѯ���
                    SELECT MAX(TBR.REPAYMENT_NO) AS SOURCE_BILL_NO,
                           MAX(NULL) AS WAYBILL_NO,
                           MAX(STV.PKG_STL_COMMON.CASH_RPT_D_SOURCE_BILL_TYPE_HK) AS SOURCE_BILL_TYPE,
                           MAX(TBR.CUSTOMER_CODE) AS CUSTOMER_CODE,
                           MAX(TBR.CUSTOMER_NAME) AS CUSTOMER_NAME,
                           MAX(TBR.COLLECTION_ORG_CODE) AS COLLECTION_ORG_CODE,
                           MAX(TBR.COLLECTION_ORG_NAME) AS COLLECTION_ORG_NAME,
                           SUM(TBR.AMOUNT) AS AMOUNT,
                           SUM(0) AS PAID_AMOUNT,
                           SUM(TBR.AMOUNT) AS OVERDUE_AMOUNT,
                           SUM(TBR.AMOUNT) CLERKS_AMOUNT,
                           SUM(0) UNCLERKS_AMOUNT,
                           SUM(0) AS PRECOLLECTED_AMOUNT,
                           MAX(TBR.ACCOUNT_DATE) AS ACCOUNT_DATE,
                           MAX(TBR.BUSINESS_DATE) AS BUSINESS_DATE,
                           MAX(TBR.PAYMENT_TYPE) AS PAYMENT_TYPE,
                           MAX(TBR.CUSTOMER_TYPE) AS CUSTOMER_TYPE
                      FROM STL.T_STL_BILL_REPAYMENT TBR
                     WHERE TBR.ACCOUNT_DATE > V_REPAYMENT_DATE
                       AND TBR.ACCOUNT_DATE < P_END_DATE
                       AND TBR.COLLECTION_ORG_CODE = P_ORG_CODE
                       AND TBR.PAYMENT_TYPE IN
                           (STV.PKG_STL_COMMON.PAYMENT_TYPE_CASH,
                            STV.PKG_STL_COMMON.PAYMENT_TYPE_CARD,
                            STV.PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
                            STV.PKG_STL_COMMON.PAYMENT_TYPE_NOTE,
                            STV.PKG_STL_COMMON.PAYMENT_TYPE_ONLINE)
                     GROUP BY TBR.REPAYMENT_NO);
        EXCEPTION
          WHEN OTHERS THEN
            ROLLBACK;
            --�������LOG��
            STV.PKG_STL_COMMON.PROC_STL_ERROR_LOG(NULL, --����
                                              P_BEGIN_DATE, --��ʼʱ��
                                              P_END_DATE, --��ֹʱ��
                                              '���ɽɿ����ϸ', --��������
                                              'PROC_STL_CASH_RECEIVE_RPT_ORG', --��ע
                                              'STL_USER_CODE', --������
                                              SQLCODE, --������
                                              SQLERRM, --������Ϣ
                                              SYSDATE);
        END;

        /***��ȡ�ֽ��տ�������Ԥ�յ����������� ���Ϊ�ռ�¼��־�����׳��쳣****/
        BEGIN
          SELECT MAX(US_ACCOUNT_DATE) AS V_DEPOSIT_RECEIVABLE_DATE,
                 MAX(XS_ACCOUNT_DATE) AS V_CASH_COLLECTION_DATE,
                 MAX(HK_ACCOUNT_DATE) AS V_REPAYMENT_DATE

            INTO V_DEPOSIT_RECEIVABLE_DATE,
                 V_CASH_COLLECTION_DATE,
                 V_REPAYMENT_DATE

            FROM (SELECT CASE SOURCE_BILL_TYPE
                           WHEN 'US' THEN
                            TEMP.ACCOUNT_DATE
                         END AS US_ACCOUNT_DATE,
                         CASE SOURCE_BILL_TYPE
                           WHEN 'XS' THEN
                            TEMP.ACCOUNT_DATE
                         END AS XS_ACCOUNT_DATE,
                         CASE SOURCE_BILL_TYPE
                           WHEN 'HK' THEN
                            TEMP.ACCOUNT_DATE
                         END AS HK_ACCOUNT_DATE

                    FROM (
                          /***��ѯĳһʱ�����ֽ��տ��Ԥ�յ���������������***/
                          SELECT MAX(ACCOUNT_DATE) ACCOUNT_DATE,
                                  MAX(SOURCE_BILL_TYPE) SOURCE_BILL_TYPE
                            FROM STL.T_STL_CASH_COLLECTION_RPT_D TD
                           WHERE COLLECTION_ORG_CODE = P_ORG_CODE
                             AND REPORT_NO = V_REPORT_NO
                           GROUP BY SOURCE_BILL_TYPE) TEMP);
        END;

        --�����жϸ���������ʷ��¼�����Ƿ���ڣ�������ִ��insert��������ִ��UPDATE��
        BEGIN
          MERGE INTO STL.T_STL_CASH_COLLECTION_COUNT_H CCCH
          USING (SELECT COUNT(*) COU, MAX(W.ORG_CODE) CODE
                   FROM STL.T_STL_CASH_COLLECTION_COUNT_H W
                  WHERE W.ORG_CODE = P_ORG_CODE) T
          ON (T.CODE = CCCH.ORG_CODE AND T.COU > 0)
          WHEN MATCHED THEN
            UPDATE
               SET
                   CCCH.REPAYMENT_DATE          = DECODE(V_REPAYMENT_DATE,NULL,CCCH.REPAYMENT_DATE,V_REPAYMENT_DATE),
                   CCCH.DEPOSIT_RECEIVABLE_DATE = DECODE(V_DEPOSIT_RECEIVABLE_DATE,NULL,CCCH.DEPOSIT_RECEIVABLE_DATE,V_DEPOSIT_RECEIVABLE_DATE),
                   CCCH.CASH_COLLECTION_DATE    = DECODE(V_CASH_COLLECTION_DATE,NULL,CCCH.CASH_COLLECTION_DATE,V_CASH_COLLECTION_DATE),

                   CCCH.MODIFY_DATE             = SYSDATE
             WHERE CCCH.ORG_CODE = P_ORG_CODE

          WHEN NOT MATCHED THEN
            INSERT
              (ID,
               ORG_CODE,
               REPAYMENT_DATE,
               CASH_COLLECTION_DATE,
               DEPOSIT_RECEIVABLE_DATE,
               MODIFY_DATE)
            VALUES
              (SYS_GUID(),
               P_ORG_CODE,
               DECODE(V_REPAYMENT_DATE,NULL,P_BEGIN_DATE,V_REPAYMENT_DATE),
               DECODE(V_CASH_COLLECTION_DATE,NULL,P_BEGIN_DATE,V_CASH_COLLECTION_DATE),
               DECODE(V_DEPOSIT_RECEIVABLE_DATE,NULL,P_BEGIN_DATE,V_DEPOSIT_RECEIVABLE_DATE),
               SYSDATE);
        EXCEPTION
          WHEN OTHERS THEN
            --�������LOG��
            STV.PKG_STL_COMMON.PROC_STL_ERROR_LOG(NULL, --����
                                              P_BEGIN_DATE, --��ʼʱ��
                                              P_END_DATE, --��ֹʱ��
                                              '�޸Ļ��������ɿ����ʷͳ�Ƽ�¼��Ϣ', --��������
                                              'PROC_STL_CASH_RECEIVE_RPT_ORG', --��ע
                                              'STL_USER_CODE', --������
                                              SQLCODE, --������
                                              SQLERRM, --������Ϣ
                                              SYSDATE);
        END;




       SELECT COUNT(*)
         INTO V_COUNT_NUM
         FROM STL. T_STL_CASH_COLLECTION_RPT_D TCRD
        WHERE TCRD.REPORT_NO = V_REPORT_NO
          AND TCRD.ACCOUNT_DATE > P_BEGIN_DATE
          AND TCRD.ACCOUNT_DATE < P_END_DATE
          AND TCRD.COLLECTION_ORG_CODE = P_ORG_CODE;

         --���ɽɿ����ϸ�����㣬�����ɽɿ��
        IF V_COUNT_NUM>0 THEN
          BEGIN
            INSERT INTO STL.T_STL_CASH_COLLECTION_RPT
              (ID,
               REPORT_NO,
               ORG_CODE,
               ORG_NAME,
               BUSINESS_DATE,
               DUES_AMOUNT,
               PAID_AMOUNT,
               OVERDUE_AMOUNT,
               CLERKS_AMOUNT,
               UNCLERKS_AMOUNT,
               PRECOLLECTED_AMOUNT,
               CREATE_USER_CODE,
               CREATE_USER_NAME,
               CREATE_TIME,
               MODIFY_TIME,
               VERSION_NO,
               CURRENCY_CODE)
              SELECT SYS_GUID(),
                     V_REPORT_NO,
                     P_ORG_CODE,
                     P_ORG_CODE,
                     P_CURRENT_TIME, --ҵ������
                                                --NVL �滻�����Ϊ��ʱ������0
                     NVL(SUM(NVL(DECODE(TCRD.PAYMENT_TYPE,STV.PKG_STL_COMMON.PAYMENT_TYPE_CASH,TCRD.AMOUNT,0),0)),0) AS DUES_AMOUNT,  --�ֽ��ܽ��
                     NVL(SUM(0),0)AS PAID_AMOUNT,   --�ѽ���
                     NVL(SUM(NVL(DECODE(TCRD.PAYMENT_TYPE,STV.PKG_STL_COMMON.PAYMENT_TYPE_CASH,TCRD.AMOUNT,0) ,0)),0) AS OVERDUE_AMOUNT,  --�ֽ�δ�ɿ���
                     NVL(SUM(NVL(CLERKS_AMOUNT,0)),0) AS CLERKS_AMOUNT,
                     NVL(SUM(NVL(UNCLERKS_AMOUNT,0)),0) AS UNCLERKS_AMOUNT,
                     NVL(SUM(NVL(PRECOLLECTED_AMOUNT,0)),0) AS PRECOLLECTED_AMOUNT,

                     P_CREATE_USER_CODE,
                     P_CREATE_USER_NAME,
                     P_CURRENT_TIME,
                     SYSDATE,
                     '1',
                     STV.PKG_STL_COMMON.CURRENCY_CODE_RMB

                FROM STL. T_STL_CASH_COLLECTION_RPT_D TCRD
               WHERE TCRD.REPORT_NO = V_REPORT_NO
                 AND TCRD.COLLECTION_ORG_CODE = P_ORG_CODE;
          EXCEPTION
            WHEN OTHERS THEN
              ROLLBACK;
              --�������LOG��
              STV.PKG_STL_COMMON.PROC_STL_ERROR_LOG(NULL, --����
                                                P_BEGIN_DATE, --��ʼʱ��
                                                P_END_DATE, --��ֹʱ��
                                                '���ɽɿ��', --��������
                                                'PROC_STL_CASH_RECEIVE_RPT_ORG', --��ע
                                                'STL_USER_CODE', --������
                                                SQLCODE, --������
                                                SQLERRM, --������Ϣ
                                                SYSDATE);
          END;
       END IF;

    ELSE

      --�������LOG��
      STV.PKG_STL_COMMON.PROC_STL_ERROR_LOG(NULL, --����
                                        P_BEGIN_DATE, --��ʼʱ��
                                        P_END_DATE, --��ֹʱ��
                                        '�����ֽ����뱨����������ڻ�������ϢΪ�գ��������Ϸ�', --��������
                                        'PROC_STL_CASH_RECEIVE_RPT_ORG', --��ע
                                        'STL_USER_CODE', --������
                                        NULL, --������
                                        '������룺' || P_ORG_CODE ||
                                        '��������ڻ�������ϢΪ�գ��������Ϸ�', --������Ϣ
                                        SYSDATE);
    END IF;

    COMMIT;
  END;
END PKG_STL_CASH_RECEIVE_REPORT;
/
