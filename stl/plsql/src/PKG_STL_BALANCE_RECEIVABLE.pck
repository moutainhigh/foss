CREATE OR REPLACE PACKAGE PKG_STL_BALANCE_RECEIVABLE IS

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

END PKG_STL_BALANCE_RECEIVABLE;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_BALANCE_RECEIVABLE IS

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
      RAISE_APPLICATION_ERROR('-20002', '����Ӧ�գ���������쳣');
    END IF;
  
    --���������
    V_RESULT := FUNC_DAILY_DETAIL_WRITEOFF(P_ORG_CODE,
                                           P_BEGIN_DATE,
                                           P_END_DATE,
                                           P_PERIOD,
                                           P_BALANCE_TYPE);
  
    --�������󷵻�
    IF V_RESULT = FALSE THEN
      RAISE_APPLICATION_ERROR('-20002', '����Ӧ�գ����������쳣');
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
                                        P_ORG_CODE || '-Ӧ����ĩ���˵���');
    
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
  
    --�쳣ǩ�ռ�¼���
    CURSOR CUR_STOCK_EX IS
      SELECT distinct t.waybill_no, t.create_time
        FROM STL.T_STL_OUT_STOCK_EXCEPTION T
       inner join stl.t_stl_bill_receivable bill
          on bill.waybill_no = t.waybill_no
      --ʼ��Ӧ��
       WHERE bill.bill_type = Pkg_Stl_Common.RECEIVABLE_BILL_TYPE_ORIGIN
            --�Ǻ쵥
         and is_red_back = PKG_STL_COMMON.NO
            --Ӧ�ղ���
         and bill.receivable_org_code = P_ORG_CODE
            --����ʱ��
         and t.create_time >= P_BEGIN_DATE
         and t.create_time < P_END_DATE;
  
    V_STOCK_EX_ROW STl.T_STL_OUT_STOCK_EXCEPTION%ROWTYPE;
  
    --ǩ�ռ�¼���  
    CURSOR CUR_POD IS
      SELECT pod.waybill_no, pod.pod_type, pod.pod_date
        FROM Stv.t_Stl_Pod_Rec pod
       WHERE pod.receivable_org_code = P_ORG_CODE;
  
    V_POD_ROW STV.t_Stl_Pod_Rec%ROWTYPE;
  
  BEGIN
    --��ü����ڼ�
    V_PERIOD := P_PERIOD;
  
    --���˵� 
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
      SELECT SYS_GUID() AS ID, --ҵ��ID
             V_PERIOD AS CHECK_OUT_PERIOD, -- �����ڼ�
             NVL(BILL.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE, --�ͻ�����
             DECODE(BILL.PRODUCT_CODE,
              PKG_STL_COMMON.PRODUCT_CODE_PKG,
              DECODE(BILL.receivable_org_code,
                     BILL.ORIG_ORG_CODE,
                     BILL.Express_Orig_Org_Code,
                     BILL.DEST_ORG_CODE,
                     BILL.Express_Dest_Org_Code��BILL.receivable_org_code),
              BILL.receivable_org_code) AS CHECK_OUT_ORG_CODE, -- Ӧ����֯
             NVL(BILL.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ����
             0 AS BEGIN_AMOUNT, --�ڳ����
             BILL.AMOUNT AS PRODUCE_AMOUNT, --������
             0 AS WRITEOFF_AMOUNT, --�������
             0 AS END_AMOUNT, --��ĩ���
             --�����������Ϊƫ��ʱ��ʹ���˵��ţ��������� 
             case
               when BILL.BILL_TYPE =
                    PKG_STL_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(bill.waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(bill.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --��Դ���� 
             SOURCE_BILL_TYPE AS SOURCE_BILL_TYPE, --��Դ�������
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE, --�ұ�
             PKG_STL_COMMON.ACTIVE AS ACTIVE, --��Ч��
             P_BALANCE_TYPE AS STL_TYPE, --��������
             BILL.BILL_TYPE AS BILL_TYPE, --��������
             P_BEGIN_DATE AS BALANCE_TIME, --����ҵ������
             SYSDATE AS CREATE_TIME, --����ʱ��
             bill.account_date as business_date,
             bill.payment_type as payment_type,
             bill.invoice_mark as invoice_mark --��Ʊ���          
        FROM STL.T_STL_BILL_RECEIVABLE BILL
      --Ŀǰ���ռ������� 
       WHERE BILL.Account_Date >= P_BEGIN_DATE
         AND BILL.Account_Date < P_END_DATE
            --Ӧ����֯
         AND BILL.RECEIVABLE_ORG_CODE = P_ORG_CODE
            --�������ͣ���������Ӧ�ա�СƱӦ�� ,���������Ӧ�գ�
         and bill.bill_type in
             (pkg_stl_common.RECEIVABLE_BILL_TYPE_AR,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_REVENUE,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_LR,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_PR --ƫ������Ӧ��
                            );
    commit;
  
    --��Դ����˵�
    --�������ͣ�ʼ��Ӧ��
    --���쳣ǩ��
    --Ҳ�㣺��ǩ�յ�Ӧ��
    FOR V_STOCK_EX_ROW IN CUR_STOCK_EX LOOP
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
         invoice_mark -- ��Ʊ���        
         )
        SELECT SYS_GUID() AS ID,
               V_PERIOD AS CHECK_OUT_PERIOD,
               NVL(CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
               CHECK_OUT_ORG_CODE,
               0 AS BEGIN_AMOUNT,
               --Ӧ���ܽ�� - �ۼƺ������
               --��ǩ�� ȡ�� 
               RCV_AMOUNT - WO_AMOUNT as PRODUCE_AMOUNT,
               0 AS WRITEOFF_AMOUNT,
               0 AS END_AMOUNT,
               NVL(PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ����
               --�����������Ϊƫ��ʱ��ʹ���˵��ţ��������� 
               case
                 when BILL_TYPE = PKG_STL_common.RECEIVABLE_BILL_TYPE_PARTIAL then
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
               ACCOUNT_DATE as business_date,
               payment_type as payment_type,
               invoice_mark as invoice_mark--��Ʊ���             
          FROM (SELECT rcv.CUSTOMER_CODE, --�ͻ�����
                       --ʹ�ó�������
                       DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE��RCV.ORIG_ORG_CODE) as CHECK_OUT_ORG_CODE, --Ӧ����֯
                       RCV.CUSTOMER_TYPE, --�ͻ�����
                       RCV.WAYBILL_NO, --�˵���
                       rcv.SOURCE_BILL_NO, --��Դ����
                       rcv.SOURCE_BILL_TYPE, --��Դ�������
                       RCV.RECEIVABLE_NO, --���ݱ��
                       RCV.ACCOUNT_DATE, --�������
                       RCV.BILL_TYPE, --����������
                       rcv.payment_type,
                       RCV.AMOUNT RCV_AMOUNT, --���
                       NVL((SELECT SUM(WO.AMOUNT)
                             FROM STL.T_STL_BILL_WRITEOFF WO
                            WHERE (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
                                  WO.END_NO = RCV.RECEIVABLE_NO)
                              AND WO.ACCOUNT_DATE <=
                                  V_STOCK_EX_ROW.create_time),
                           0) WO_AMOUNT,
                       RCV.PRODUCT_CODE, --��Ʒ���� 
                       rcv.invoice_mark as invoice_mark--��Ʊ���                  
                  FROM STL.T_STL_BILL_RECEIVABLE RCV
                --��������
                 where rcv.receivable_org_code = p_org_code
                   and RCV.ID IN
                       (SELECT ID
                          FROM (SELECT ID, IS_RED_BACK, BILL_TYPE
                                  FROM (SELECT *
                                          FROM STL.T_STL_BILL_RECEIVABLE T
                                         WHERE T.WAYBILL_NO =
                                               V_STOCK_EX_ROW.WAYBILL_NO
                                           AND T.SOURCE_BILL_TYPE =
                                               PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W
                                           AND T.ACCOUNT_DATE <
                                               V_STOCK_EX_ROW.create_time
                                              --�����˷�
                                           AND T.BILL_TYPE =
                                               pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN
                                         ORDER BY T.ACCOUNT_DATE DESC,
                                                  T.CREATE_TIME  DESC,
                                                  t.IS_RED_BACK  asc)
                                 WHERE ROWNUM <= 1)
                         WHERE IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
                              --�����˷�
                           AND bill_type =
                               pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN))
         WHERE RCV_AMOUNT <> WO_AMOUNT
           AND RCV_AMOUNT <> 0;
      commit;
    end loop;
  
    --�˵���
    --ǩ��δ������Ӧ���ܶ�-�ۼƺ���� ��Ϊ������
    --��ǩ�� δ����  ��Ӧ���ܶ�-�ۼƺ���� �� -1 ��Ϊ������
    --�����˷�
    FOR V_POD_ROW IN CUR_POD LOOP
    
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
               NVL(CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
               CHECK_OUT_ORG_CODE,
               0 AS BEGIN_AMOUNT,
               --Ӧ���ܽ�� - �ۼƺ������
               --��ǩ�� ȡ��
               case
                 when V_POD_ROW.POD_TYPE = Pkg_Stl_Common.POD__POD_TYPE__POD then
                  RCV_AMOUNT - WO_AMOUNT
                 else
                 --��ǩ�� ȡ��
                  (RCV_AMOUNT - WO_AMOUNT) * -1
               end as PRODUCE_AMOUNT,
               
               0 AS WRITEOFF_AMOUNT,
               0 AS END_AMOUNT,
               NVL(PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ����
               --�����������Ϊƫ��ʱ��ʹ���˵��ţ��������� 
               case
                 when BILL_TYPE = PKG_STL_common.RECEIVABLE_BILL_TYPE_PARTIAL then
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
               ACCOUNT_DATE as business_date,
               payment_type as payment_type,
               invoice_mark as invoice_mark--��Ʊ���           
          FROM (SELECT rcv.CUSTOMER_CODE, --�ͻ�����
                       --ʹ�ó�������
                       DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.EXPRESS_ORIG_ORG_CODE��RCV.ORIG_ORG_CODE) as CHECK_OUT_ORG_CODE, --Ӧ����֯
                       RCV.CUSTOMER_TYPE, --�ͻ�����
                       RCV.WAYBILL_NO, --�˵���
                       rcv.SOURCE_BILL_NO, --��Դ����
                       rcv.SOURCE_BILL_TYPE, --��Դ�������
                       RCV.RECEIVABLE_NO, --���ݱ��
                       RCV.ACCOUNT_DATE, --�������
                       RCV.BILL_TYPE, --����������
                       rcv.payment_type,
                       RCV.AMOUNT RCV_AMOUNT, --���
                       NVL((SELECT SUM(WO.AMOUNT)
                             FROM STL.T_STL_BILL_WRITEOFF WO
                            WHERE (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
                                  WO.END_NO = RCV.RECEIVABLE_NO)
                              AND WO.ACCOUNT_DATE <= V_POD_ROW.Pod_Date),
                           0) WO_AMOUNT,
                       RCV.PRODUCT_CODE, --��Ʒ���� 
                       rcv.invoice_mark --��Ʊ���
                  FROM STL.T_STL_BILL_RECEIVABLE RCV
                --��������
                 where rcv.receivable_org_code = p_org_code
                   and RCV.ID IN
                       (SELECT ID
                          FROM (SELECT ID, IS_RED_BACK, BILL_TYPE
                                  FROM (SELECT *
                                          FROM STL.T_STL_BILL_RECEIVABLE T
                                         WHERE T.WAYBILL_NO =
                                               V_POD_ROW.WAYBILL_NO
                                           AND T.SOURCE_BILL_TYPE =
                                               PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W
                                           AND T.ACCOUNT_DATE <
                                               V_POD_ROW.POD_DATE
                                              --�����˷�
                                           AND T.BILL_TYPE =
                                               pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN
                                         ORDER BY T.ACCOUNT_DATE DESC,
                                                  T.CREATE_TIME  DESC,
                                                  t.IS_RED_BACK  asc)
                                 WHERE ROWNUM <= 1)
                         WHERE IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
                              --�����˷�
                           AND bill_type =
                               pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN))
         WHERE RCV_AMOUNT <> WO_AMOUNT
           AND RCV_AMOUNT <> 0;
    
      commit;
    
      --�˵���
      --ǩ��δ������Ӧ���ܶ�-�ۼƺ���� ��Ϊ������
      --��ǩ�� δ����  ��Ӧ���ܶ�-�ۼƺ���� �� -1 ��Ϊ������
      --���� - �����˷�     
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
               --Ӧ���ܽ�� - �ۼƺ������
               --��ǩ�� ȡ��
               case
                 when V_POD_ROW.POD_TYPE = Pkg_Stl_Common.POD__POD_TYPE__POD then
                  RCV_AMOUNT - WO_AMOUNT
                 else
                 --��ǩ�� ȡ��
                  (RCV_AMOUNT - WO_AMOUNT) * -1
               end as PRODUCE_AMOUNT,
               
               0 AS WRITEOFF_AMOUNT,
               0 AS END_AMOUNT,
               NVL(PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ����
               --�����������Ϊƫ��ʱ��ʹ���˵��ţ��������� 
               case
                 when BILL_TYPE =
                      PKG_STL_common.RECEIVABLE_BILL_TYPE_PARTIAL then
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
               ACCOUNT_DATE as business_date,
               payment_type as payment_type,
               invoice_mark as invoice_mark--��Ʊ���             
          FROM (SELECT rcv.CUSTOMER_CODE, --�ͻ�����
                       DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.Express_Dest_Org_Code��RCV.dest_org_code) as CHECK_OUT_ORG_CODE, --Ӧ����֯
                       RCV.CUSTOMER_TYPE, --�ͻ�����
                       RCV.WAYBILL_NO, --�˵���
                       rcv.SOURCE_BILL_NO, --��Դ����
                       rcv.SOURCE_BILL_TYPE, --��Դ�������
                       RCV.RECEIVABLE_NO, --���ݱ��
                       RCV.ACCOUNT_DATE, --�������
                       RCV.BILL_TYPE, --����������
                       rcv.payment_type,
                       RCV.AMOUNT RCV_AMOUNT, --���
                       NVL((SELECT SUM(WO.AMOUNT)
                             FROM STL.T_STL_BILL_WRITEOFF WO
                            WHERE (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
                                  WO.END_NO = RCV.RECEIVABLE_NO)
                              AND WO.ACCOUNT_DATE <= V_POD_ROW.Pod_Date),
                           0) WO_AMOUNT,
                       RCV.PRODUCT_CODE, --��Ʒ���� 
                       rcv.invoice_mark --��Ʊ���
                  FROM STL.T_STL_BILL_RECEIVABLE RCV
                --���ﲿ��
                 where rcv.receivable_org_code = p_org_code
                   and RCV.ID IN
                       (SELECT ID
                          FROM (SELECT ID, IS_RED_BACK, BILL_TYPE
                                  FROM (SELECT *
                                          FROM STL.T_STL_BILL_RECEIVABLE T
                                         WHERE T.WAYBILL_NO =
                                               V_POD_ROW.WAYBILL_NO
                                           AND T.SOURCE_BILL_TYPE =
                                               PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W
                                           AND T.ACCOUNT_DATE <
                                               V_POD_ROW.POD_DATE
                                           AND T.BILL_TYPE in
                                              --�����˷�
                                               (pkg_stl_common.RECEIVABLE_BILL_TYPE_DEST,
                                                pkg_stl_common.RECEIVABLE_BILL_TYPE_AA,
                                                pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL,
                                                pkg_stl_common.RECEIVABLE_BILL_TYPE_DL)
                                         ORDER BY T.ACCOUNT_DATE DESC,
                                                  T.CREATE_TIME  DESC,
                                                  t.IS_RED_BACK  asc)
                                 WHERE ROWNUM <= 1)
                         WHERE IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
                              --�����˷�
                           AND bill_type in
                               (pkg_stl_common.RECEIVABLE_BILL_TYPE_DEST,
                                pkg_stl_common.RECEIVABLE_BILL_TYPE_AA,
                                pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL,
                                pkg_stl_common.RECEIVABLE_BILL_TYPE_DL)))
         WHERE RCV_AMOUNT <> WO_AMOUNT
           AND RCV_AMOUNT <> 0;
    
      commit;
    
      --�˵���
      --ǩ��δ������Ӧ���ܶ�-�ۼƺ���� ��Ϊ������
      --��ǩ�� δ����  ��Ӧ���ܶ�-�ۼƺ���� �� -1 ��Ϊ������
      --���� - ���˴��ջ���     
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
               NVL(CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
               CHECK_OUT_ORG_CODE AS CHECK_OUT_ORG_CODE,
               0 AS BEGIN_AMOUNT,
               --Ӧ���ܽ�� - �ۼƺ������
               --��ǩ�� ȡ��
               case
                 when V_POD_ROW.POD_TYPE = Pkg_Stl_Common.POD__POD_TYPE__POD then
                  RCV_AMOUNT - WO_AMOUNT
                 else
                 --��ǩ�� ȡ��
                  (RCV_AMOUNT - WO_AMOUNT) * -1
               end as PRODUCE_AMOUNT,
               
               0 AS WRITEOFF_AMOUNT,
               0 AS END_AMOUNT,
               NVL(PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ����
               --�����������Ϊƫ��ʱ��ʹ���˵��ţ��������� 
               case
                 when BILL_TYPE =
                      PKG_STL_common.RECEIVABLE_BILL_TYPE_PARTIAL then
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
               ACCOUNT_DATE as business_date,
               payment_type as payment_type,
               invoice_mark as invoice_mark--��Ʊ���              
          FROM (SELECT rcv.CUSTOMER_CODE, --�ͻ�����
                       DECODE(RCV.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,RCV.Express_Dest_Org_Code��RCV.dest_org_code) as CHECK_OUT_ORG_CODE, --Ӧ����֯
                       RCV.CUSTOMER_TYPE, --�ͻ�����
                       RCV.WAYBILL_NO, --�˵���
                       rcv.SOURCE_BILL_NO, --��Դ����
                       rcv.SOURCE_BILL_TYPE, --��Դ�������
                       RCV.RECEIVABLE_NO, --���ݱ��
                       RCV.ACCOUNT_DATE, --�������
                       RCV.BILL_TYPE, --����������
                       rcv.payment_type,
                       RCV.AMOUNT RCV_AMOUNT, --���
                       NVL((SELECT SUM(WO.AMOUNT)
                             FROM STL.T_STL_BILL_WRITEOFF WO
                            WHERE (WO.BEGIN_NO = RCV.RECEIVABLE_NO OR
                                  WO.END_NO = RCV.RECEIVABLE_NO)
                              AND WO.ACCOUNT_DATE <= V_POD_ROW.Pod_Date),
                           0) WO_AMOUNT,
                       RCV.PRODUCT_CODE, --��Ʒ����
                       rcv.invoice_mark --��Ʊ��� 
                  FROM STL.T_STL_BILL_RECEIVABLE RCV
                --�����ﲿ��
                 where rcv.receivable_org_code = p_org_code
                   and RCV.ID IN
                       (SELECT ID
                          FROM (SELECT ID, IS_RED_BACK, BILL_TYPE
                                  FROM (SELECT *
                                          FROM STL.T_STL_BILL_RECEIVABLE T
                                         WHERE T.WAYBILL_NO =
                                               V_POD_ROW.WAYBILL_NO
                                           AND T.SOURCE_BILL_TYPE =
                                               PKG_STL_COMMON.RECEIVABLE_SOURCE_BILL_TYPE_W
                                           AND T.ACCOUNT_DATE <
                                               V_POD_ROW.POD_DATE
                                              --���˴��ջ���
                                           AND T.BILL_TYPE in
                                               (pkg_stl_common.RECEIVABLE_BILL_TYPE_AAC,
                                                pkg_stl_common.RECEIVABLE_BILL_TYPE_COD,
                                                pkg_stl_common.RECEIVABLE_BILL_TYPE_DLC)
                                              --���˲�Ʒ
                                           and t.product_code IN(
                                               pkg_stl_common.PRODUCT_CODE_AF,
                                               pkg_stl_common.PRODUCT_CODE_PKG)
                                         ORDER BY T.ACCOUNT_DATE DESC,
                                                  T.CREATE_TIME  DESC,
                                                  t.IS_RED_BACK  asc)
                                 WHERE ROWNUM <= 1)
                         WHERE IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
                              --���˴��ջ���
                           AND BILL_TYPE in
                               (pkg_stl_common.RECEIVABLE_BILL_TYPE_AAC,
                                pkg_stl_common.RECEIVABLE_BILL_TYPE_COD,
                                pkg_stl_common.RECEIVABLE_BILL_TYPE_DLC)
                              --���˲�Ʒ

                           and product_code in (pkg_stl_common.PRODUCT_CODE_AF,
                                                pkg_stl_common.PRODUCT_CODE_PKG)))
         WHERE RCV_AMOUNT <> WO_AMOUNT
           AND RCV_AMOUNT <> 0;
    
      commit;
    end loop;
  
    RETURN TRUE;
  
    --��׽�쳣
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(V_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE - 1 / (24 * 60 * 60),
                                        'PKG_STL_Balance_RECEIVABLE.func_daily_detail_produce',
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
  
    --���˵�
    --���Ӧ�ճ�Ӧ���ĺ�����¼ 
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
              DECODE(B.PRODUCT_CODE,
                     PKG_STL_COMMON.PRODUCT_CODE_PKG,
              DECODE(B.receivable_org_code,
                     B.ORIG_ORG_CODE,
                     B.Express_Orig_Org_Code,
                     B.DEST_ORG_CODE,
                     B.Express_Dest_Org_Code��B.receivable_org_code),
              B.receivable_org_code) AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SBW.AMOUNT AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(B.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ����
             --�����������Ϊƫ��ʱ��ʹ���˵��ţ��������� 
             case
               when b.BILL_TYPE =
                    PKG_STL_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(b.waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(b.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --��Դ���� 
             NVL(B.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --��������
             B.BILL_TYPE AS BILL_TYPE, -- ����������
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             b.account_date as business_date,
             b.payment_type as payment_type,
             b.invoice_mark as invoice_mark--��Ʊ���           
        FROM STL.T_STL_BILL_WRITEOFF SBW
      --����Ӧ�յ�
       INNER JOIN STL.T_STL_BILL_RECEIVABLE B
          ON B.RECEIVABLE_NO = SBW.BEGIN_No --��ʼӦ�յ���  
            --�Ǻ쵥
         and b.is_red_back = PKG_STL_COMMON.NO
       WHERE SBW.ORG_CODE = P_ORG_CODE --��������
         AND SBW.ACCOUNT_DATE >= P_BEGIN_DATE --��������
         AND SBW.ACCOUNT_DATE < P_END_DATE
            --���˵�
            --�������ͣ���������Ӧ�ա�СƱӦ�� ��
         and b.bill_type in
             (pkg_stl_common.RECEIVABLE_BILL_TYPE_AR,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_REVENUE,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_LR,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_PR --ƫ������Ӧ��
              );
    commit;
  
    --���Ԥ�ճ�Ӧ�ա������Ӧ�ա������Ӧ�յ�Ӧ�յ���Ϊendbill���к���
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
       BILL_TYPE, -- ���㵥������
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
             DECODE(B.PRODUCT_CODE,
                     PKG_STL_COMMON.PRODUCT_CODE_PKG,
              DECODE(B.receivable_org_code,
                     B.ORIG_ORG_CODE,
                     B.Express_Orig_Org_Code,
                     B.DEST_ORG_CODE,
                     B.Express_Dest_Org_Code��B.receivable_org_code),
              B.receivable_org_code) AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SBW.AMOUNT AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(B.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ����
             --�����������Ϊƫ��ʱ��ʹ���˵��ţ��������� 
             case
               when b.BILL_TYPE =
                    PKG_STL_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(b.waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(b.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --��Դ���� 
             NVL(B.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --��������
             B.BILL_TYPE AS BILL_TYPE,
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             b.account_date as business_date,
             b.payment_type as payment_type,
             b.invoice_mark as invoice_mark--��Ʊ���            
        FROM STL.T_STL_BILL_WRITEOFF SBW
      --����Ӧ�յ�
       INNER JOIN STL.T_STL_BILL_RECEIVABLE B
          ON B.RECEIVABLE_NO = SBW.END_No --��ʼӦ�յ��� --�������� 
            --�Ǻ쵥
         and b.is_red_back = PKG_STL_COMMON.NO
       WHERE SBW.ORG_CODE = P_ORG_CODE --��������
         AND SBW.ACCOUNT_DATE >= P_BEGIN_DATE --��������
         AND SBW.ACCOUNT_DATE < P_END_DATE
            --���˵�
            --�������ͣ���������Ӧ�ա�СƱӦ�� ��
         and b.bill_type in
             (pkg_stl_common.RECEIVABLE_BILL_TYPE_AR,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_REVENUE,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_LR,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_PR --ƫ������Ӧ��
              );
    commit;
  
    --�˵���
    --������ǩ�� ��������������
    --����ǩ��
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
             --���ʼ��Ӧ�գ�ʼ����������
             case
               when b.bill_type = pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN then
                DECODE(B.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,B.EXPRESS_ORIG_ORG_CODE��B.ORIG_ORG_CODE)
               else
                DECODE(B.PRODUCT_CODE,
                     PKG_STL_COMMON.PRODUCT_CODE_PKG,
                     DECODE(B.receivable_org_code,
                     B.ORIG_ORG_CODE,
                     B.Express_Orig_Org_Code,
                     B.DEST_ORG_CODE,
                     B.Express_Dest_Org_Code��B.receivable_org_code),
                     B.receivable_org_code)
             end AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SBW.AMOUNT AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(B.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ����
             --�����������Ϊƫ��ʱ��ʹ���˵��ţ��������� 
             case
               when b.BILL_TYPE =
                    PKG_STL_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(b.waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(b.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --��Դ���� 
             NVL(B.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --��������
             B.BILL_TYPE AS BILL_TYPE, -- ����������
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             b.account_date as business_date,
             b.payment_type as payment_type,
             b.invoice_mark as invoice_mark--��Ʊ���            
        FROM STL.T_STL_BILL_WRITEOFF sbw
       inner JOIN STL.T_STL_BILL_RECEIVABLE b
          ON b.RECEIVABLE_NO = sbw.BEGIN_NO
         AND b.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = b.WAYBILL_NO
                 AND T.POD_DATE < sbw.ACCOUNT_DATE) = 1 --��ǩ��
         AND sbw.ACCOUNT_DATE >= P_BEGIN_DATE
         and sbw.ACCOUNT_DATE < P_END_DATE
            --��������֯����
         and SBW.ORG_CODE = p_org_code
            --�������ͣ�ʼ���˷ѡ������˷ѡ����˵�����ƫ�ߵ��������˴��ջ������䣩
         and b.bill_type in
             (pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DEST,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_AA,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_AAC,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DL,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DLC
              );
    commit;
  
    --��Ϊ����
    --����ǩ��
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
             --���ʼ��Ӧ�գ�ʼ����������
             case
               when b.bill_type = pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN then
                DECODE(B.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,B.EXPRESS_ORIG_ORG_CODE��B.ORIG_ORG_CODE)
               else
                DECODE(B.PRODUCT_CODE,
                     PKG_STL_COMMON.PRODUCT_CODE_PKG,
                     DECODE(B.receivable_org_code,
                     B.ORIG_ORG_CODE,
                     B.Express_Orig_Org_Code,
                     B.DEST_ORG_CODE,
                     B.Express_Dest_Org_Code��B.receivable_org_code),
                     B.receivable_org_code)
             end AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SBW.AMOUNT AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(B.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ����
             --�����������Ϊƫ��ʱ��ʹ���˵��ţ��������� 
             case
               when b.BILL_TYPE =
                    PKG_STL_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(b.waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(b.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --��Դ���� 
             NVL(B.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --��������
             B.BILL_TYPE AS BILL_TYPE, -- ����������
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             b.account_date as business_date,
             b.payment_type as payment_type,
             b.invoice_mark as invoice_mark --��Ʊ���            
        FROM STL.T_STL_BILL_WRITEOFF sbw
        JOIN STL.T_STL_BILL_RECEIVABLE b
          ON b.RECEIVABLE_NO = sbw.END_NO
         AND b.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE (SELECT NVL(SUM(DECODE(T.POD_TYPE,
                                    PKG_STL_COMMON.POD__POD_TYPE__POD,
                                    1,
                                    PKG_STL_COMMON.POD__POD_TYPE__UPD,
                                    -1)),
                         0) POD
                FROM STL.T_STL_POD T
               WHERE T.WAYBILL_NO = b.WAYBILL_NO
                 AND T.POD_DATE < sbw.ACCOUNT_DATE) = 1 --��ǩ��
         AND sbw.ACCOUNT_DATE >= P_BEGIN_DATE
         and sbw.ACCOUNT_DATE < P_END_DATE
            --��������֯����
         and SBW.ORG_CODE = p_org_code
            --�������ͣ�ʼ���˷ѡ������˷ѡ����˵�����ƫ�ߵ��������˴��ջ��
         and b.bill_type in
             (pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DEST,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_AA,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_PARTIAL,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_AAC,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DL,
              pkg_stl_common.RECEIVABLE_BILL_TYPE_DLC);
    commit;
  
    --�쳣ǩ���Ѻ���
    --ʼ��Ӧ��
    --�˵���Ϊ����
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
             NVL(b.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             --���ʼ��Ӧ�գ�ʼ����������
             case
               when b.bill_type = pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN then
                DECODE(B.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,B.EXPRESS_ORIG_ORG_CODE��B.ORIG_ORG_CODE)
               else
                DECODE(B.PRODUCT_CODE,
                     PKG_STL_COMMON.PRODUCT_CODE_PKG,
                     DECODE(B.receivable_org_code,
                     B.ORIG_ORG_CODE,
                     B.Express_Orig_Org_Code,
                     B.DEST_ORG_CODE,
                     B.Express_Dest_Org_Code��B.receivable_org_code),
                     B.receivable_org_code)
             end AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SBW.AMOUNT AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(B.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ����
             --�����������Ϊƫ��ʱ��ʹ���˵��ţ��������� 
             case
               when b.BILL_TYPE =
                    PKG_STL_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(b.waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(b.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --��Դ���� 
             NVL(B.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --��������
             B.BILL_TYPE AS BILL_TYPE, -- ����������
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             b.account_date as business_date,
             b.payment_type as payment_type,
             b.invoice_mark as invoice_mark--��Ʊ���            
        FROM STL.T_STL_BILL_WRITEOFF sbw
       inner JOIN STL.T_STL_BILL_RECEIVABLE b
          ON b.RECEIVABLE_NO = sbw.BEGIN_NO
         AND b.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE exists
       (SELECT 1
                FROM STL.t_Stl_Out_Stock_Exception T
               WHERE T.WAYBILL_NO = b.WAYBILL_NO
                 AND T.Create_Time < sbw.ACCOUNT_DATE) --�����쳣ǩ�ռ�¼
         AND sbw.ACCOUNT_DATE >= P_BEGIN_DATE
         and sbw.ACCOUNT_DATE < P_END_DATE
            --��������֯����
         and SBW.ORG_CODE = p_org_code
            --�������ͣ�ʼ���˷ѣ�
         and b.bill_type in (pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN);
    commit;
  
    --�˵���Ϊ����
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
             NVL(b.CUSTOMER_CODE, PKG_STL_COMMON.NULL_VALUE) AS CUSTOMER_CODE,
             --���ʼ��Ӧ�գ�ʼ����������
             case
               when b.bill_type = pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN then
                 DECODE(B.PRODUCT_CODE,PKG_STL_COMMON.PRODUCT_CODE_PKG,B.EXPRESS_ORIG_ORG_CODE��B.ORIG_ORG_CODE)
               else
                DECODE(B.PRODUCT_CODE,
                     PKG_STL_COMMON.PRODUCT_CODE_PKG,
                     DECODE(B.receivable_org_code,
                     B.ORIG_ORG_CODE,
                     B.Express_Orig_Org_Code,
                     B.DEST_ORG_CODE,
                     B.Express_Dest_Org_Code��B.receivable_org_code),
                     B.receivable_org_code)
             end AS CHECK_OUT_ORG_CODE,
             0 AS BEGIN_AMOUNT,
             0 AS PRODUCE_AMOUNT,
             SBW.AMOUNT AS WRITEOFF_AMOUNT,
             0 AS END_AMOUNT,
             NVL(B.PRODUCT_CODE, PKG_STL_COMMON.NULL_VALUE) AS PRODUCT_CODE, --��Ʒ����
             --�����������Ϊƫ��ʱ��ʹ���˵��ţ��������� 
             case
               when b.BILL_TYPE =
                    PKG_STL_common.RECEIVABLE_BILL_TYPE_PARTIAL then
                NVL(b.waybill_no, PKG_STL_COMMON.NULL_VALUE)
               else
                NVL(b.SOURCE_BILL_NO, PKG_STL_COMMON.NULL_VALUE)
             end AS SOURCE_BILL_NO, --��Դ���� 
             NVL(B.SOURCE_BILL_TYPE, PKG_STL_COMMON.NULL_VALUE) AS SOURCE_BILL_TYPE,
             P_BALANCE_TYPE AS STL_TYPE, --��������
             B.BILL_TYPE AS BILL_TYPE, -- ����������
             PKG_STL_COMMON.CURRENCY_CODE_RMB AS CURRENCY_CODE,
             PKG_STL_COMMON.ACTIVE AS ACTIVE,
             P_BEGIN_DATE AS BALANCE_TIME,
             SYSDATE AS CREATE_TIME,
             b.account_date as business_date,
             b.payment_type as payment_type,
             b.invoice_mark as invoice_mark --��Ʊ���          
        FROM STL.T_STL_BILL_WRITEOFF sbw
       inner JOIN STL.T_STL_BILL_RECEIVABLE b
          ON b.RECEIVABLE_NO = sbw.end_no
         AND b.IS_RED_BACK = PKG_STL_COMMON.IS_RED_BACK_NO
       WHERE exists
       (SELECT 1
                FROM STL.t_Stl_Out_Stock_Exception T
               WHERE T.WAYBILL_NO = b.WAYBILL_NO
                 AND T.Create_Time < sbw.ACCOUNT_DATE) --�����쳣ǩ�ռ�¼
         AND sbw.ACCOUNT_DATE >= P_BEGIN_DATE
         and sbw.ACCOUNT_DATE < P_END_DATE
            --��������֯����
         and SBW.ORG_CODE = p_org_code
            --�������ͣ�ʼ���˷ѣ�
         and b.bill_type in (pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN);
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
                                        'PKG_STL_Balance_RECEIVABLE.func_daily_detail_writeoff',
                                        P_ORG_CODE || '-Ӧ����ĩ���˺��������');
      --����ʧ�ܱ�־
      RETURN FALSE;
  END;

END PKG_STL_BALANCE_RECEIVABLE;
/
