CREATE OR REPLACE PACKAGE PKG_STL_BALANCE_BATCH IS

  -- Author  : 000123-foss-huangxiaobo
  -- Created : 2012/12/10 11:07:34
  -- Purpose : �������β���
  ------------------------------------------------------------------
  --��ǿ�ʼ����
  ------------------------------------------------------------------
  FUNCTION FUNC_BEGIN_BALANCE_BATCH(P_ORG_CODE   IN VARCHAR2, --������֯
                                    P_BEGIN_DATE IN DATE -- ��ʼ����
                                    ) RETURN BOOLEAN;

  ------------------------------------------------------------------
  --��ǽ������
  ------------------------------------------------------------------
  FUNCTION FUNC_END_BALANCE_BATCH(P_ORG_CODE   IN VARCHAR2, --������֯
                                  P_BEGIN_DATE IN DATE -- ��ʼ����
                                  ) RETURN BOOLEAN;
  ------------------------------------------------------------------
  --��ʼ����������
  ------------------------------------------------------------------
  PROCEDURE PROC_INIT_BALANCE_BATCH(P_BEGIN_DATE IN DATE);

END PKG_STL_BALANCE_BATCH;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_BALANCE_BATCH IS

  ------------------------------------------------------------------
  --��ǿ�ʼ����
  ------------------------------------------------------------------
  FUNCTION FUNC_BEGIN_BALANCE_BATCH(P_ORG_CODE   IN VARCHAR2, --������֯
                                    P_BEGIN_DATE IN DATE) RETURN BOOLEAN -- ��ʼ����
   IS
    --��������
    V_UPDATE_ROWS INT;
    --��ǰ�ڼ�
    v_period varchar2(10);
  BEGIN
    --��õ�ǰ�ڼ�
    v_period := pkg_stl_balance_common.FUNC_GET_PERIOD(P_DATE1 => P_BEGIN_DATE);
  
    UPDATE STV.T_STL_BALANCE_BATCH
    --��״̬����Ϊ׼������
       SET STATUS     = Pkg_Stl_Balance_Constant.BALANCE_BATCH_BEGIN,
           begin_time = sysdate
     WHERE ORG_CODE = P_ORG_CODE --��֯����
       AND BUSINESS_DATE = P_BEGIN_DATE --�������ڷ�Χ
          --ֻ������Ч������״̬
       and active = PKG_STL_Common.ACTIVE
          --ָ���ڼ�
       and period = v_period
          --����״̬���ڳ�ʼ��
       AND STATUS = Pkg_Stl_Balance_Constant.BALANCE_BATCH_INITED;
  
    --�жϸ�������
    V_UPDATE_ROWS := SQL%ROWCOUNT;
  
    --�ύ
    commit;
  
    RETURN V_UPDATE_ROWS = 1;
  
    --��׽�쳣
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(TO_CHAR(P_BEGIN_DATE, 'yyyymm'),
                                        P_BEGIN_DATE,
                                        P_BEGIN_DATE,
                                        'pkg_stl_balance_batch.FUNC_BEGIN_BALANCE_BATCH',
                                        P_ORG_CODE || '��ǿ�ʼ����');
      --����ʧ�ܱ�־
      RETURN FALSE;
  END;

  ------------------------------------------------------------------
  --��ǽ������
  ------------------------------------------------------------------
  FUNCTION FUNC_END_BALANCE_BATCH(P_ORG_CODE   IN VARCHAR2, --������֯
                                  P_BEGIN_DATE IN DATE) RETURN BOOLEAN -- ��ʼ����
   IS
    --��������
    V_UPDATE_ROWS INT;
    --��ǰ�ڼ�
    v_period varchar2(10);
  BEGIN
    --��õ�ǰ�ڼ�
    v_period := pkg_stl_balance_common.FUNC_GET_PERIOD(P_DATE1 => P_BEGIN_DATE);
  
    UPDATE STV.T_STL_BALANCE_BATCH
    --��״̬����Ϊ׼������
       SET STATUS   = Pkg_Stl_Balance_Constant.BALANCE_BATCH_END,
           end_time = sysdate
     WHERE ORG_CODE = P_ORG_CODE --��֯����
       AND BUSINESS_DATE = P_BEGIN_DATE --�������ڷ�Χ
          --ֻ������Ч������״̬
       and active = PKG_STL_Common.ACTIVE
          --ָ���ڼ�
       and period = v_period
          --����״̬���� ��ʼ����
       AND STATUS = Pkg_Stl_Balance_Constant.BALANCE_BATCH_BEGIN;
    RETURN TRUE;
    --�жϸ�������
    V_UPDATE_ROWS := SQL%ROWCOUNT;
  
    --�����ύ
    COMMIT;
    RETURN V_UPDATE_ROWS = 1;
  
    --��׽�쳣
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(TO_CHAR(P_BEGIN_DATE, 'yyyymm'),
                                        P_BEGIN_DATE,
                                        P_BEGIN_DATE,
                                        'pkg_stl_balance_batch.FUNC_END_BALANCE_BATCH',
                                        P_ORG_CODE || '��ǽ������');
      --����ʧ�ܱ�־
      RETURN FALSE;
  END;

  ------------------------------------------------------------------
  --��ʼ����������
  ------------------------------------------------------------------
  PROCEDURE PROC_INIT_BALANCE_BATCH(P_BEGIN_DATE IN DATE) IS
    --��ʼ����
    V_BEGIN_DATE DATE;
    --��������
    V_END_DATE DATE;
    --�����ڼ�
    V_PERIOD CHAR(6);
  BEGIN
    --��õ�ǰ����
    V_BEGIN_DATE := TRUNC(P_BEGIN_DATE);
    V_END_DATE   := V_BEGIN_DATE + 1;
    V_PERIOD     := TO_CHAR(V_BEGIN_DATE, 'yyyymm');
  
    --��ʼ�������ڼ䣨����֯��
    INSERT INTO STV.T_STL_BALANCE_BATCH
      (ID, PERIOD, BUSINESS_DATE, ORG_CODE, ACTIVE, STATUS, create_time)
      SELECT SYS_GUID(),
             V_PERIOD,
             V_BEGIN_DATE AS BUSINESS_DATE,
             ORG_CODE,
             PKG_STL_COMMON.YES AS ACTIVE,
             Pkg_Stl_Balance_Constant.BALANCE_BATCH_INITED AS STATUS,
             sysdate as create_time
        FROM (select org_code
                from (
                      --Ԥ��
                      select b3.collection_org_code as org_code
                        from stl.t_stl_bill_deposit_received b3
                       where account_date >= V_BEGIN_DATE
                         and account_date < V_END_DATE
                      --ǩ�ռ�¼(����Ӧ����֯)
                      union all
                      select bill.receivable_org_code as org_code
                        from stl.t_stl_bill_receivable bill
                       WHERE bill.is_red_back = Pkg_Stl_Common.NO
                         and bill.source_bill_type =
                             Pkg_Stl_Common.REPAYMENT_SOURCE_BILL_TYPE_W
                         and bill.waybill_no in
                             (select waybill_no
                                from stl.t_stl_pod
                               where pod_date >= V_BEGIN_DATE
                                 and pod_date < V_END_DATE
                                 and pod_type in
                                     (PKG_STL_COMMOn.POD__POD_TYPE__UPD,
                                      PKG_STL_COMMON.POD__POD_TYPE__POD))
                      
						 --�쳣ǩ�ռ�¼(����Ӧ����֯)
                      --����ʼ��Ӧ��
                      union all
                      select bill.receivable_org_code as org_code
                        from stl.t_stl_bill_receivable bill
                       WHERE bill.is_red_back = Pkg_Stl_Common.NO
                         and bill.source_bill_type =
                             Pkg_Stl_Common.RECEIVABLE_SOURCE_BILL_TYPE_W
                         and bill.bill_type =
                             pkg_stl_common.RECEIVABLE_BILL_TYPE_ORIGIN
                         and bill.waybill_no in
                             (select waybill_no
                                from stl.t_stl_out_stock_exception
                               where create_time >= V_BEGIN_DATE
                                 and create_time < V_END_DATE)
                      --ǩ�ռ�¼(�������ջ���Ӧ����֯)
                      union all
                      select bill.payable_org_code as org_code
                        from stl.t_stl_bill_payable bill
                       WHERE bill.is_red_back = Pkg_Stl_Common.NO
                         and bill.source_bill_type =
                             Pkg_Stl_Common.REPAYMENT_SOURCE_BILL_TYPE_W
                         and bill.bill_type =
                             PKG_STL_Common.PAYABLE_BILL_TYPE_APC
                         and bill.waybill_no in
                             (select waybill_no
                                from stl.t_stl_pod
                               where pod_date >= V_BEGIN_DATE
                                 and pod_date < V_END_DATE
                                 and pod_type in
                                     (PKG_STL_COMMOn.POD__POD_TYPE__UPD,
                                      PKG_STL_COMMON.POD__POD_TYPE__POD))
                      union all
                      --Ӧ��
                      select b.receivable_org_code as org_code
                        from stl.t_stl_bill_receivable b
                       where account_date >= V_BEGIN_DATE
                         and account_date < V_END_DATE
                      --����Ӧ��
                      union all
                      --Ӧ��
                      select b1.payable_org_code as org_code
                        from stl.t_stl_bill_payable b1
                       where account_date >= V_BEGIN_DATE
                         and account_date < V_END_DATE
                      --������֯
                      union all
                      select PAYMENT_ORG_CODE as org_code
                        from stl.T_STL_BILL_PAYMENT
                       where account_date >= V_BEGIN_DATE
                         and account_date < V_END_DATE
                      --����
                      union all
                      select org_code
                        from stl.t_stl_bill_writeoff
                       where account_date >= V_BEGIN_DATE
                         and account_date < V_END_DATE)
               group by org_code);
    commit;
  
    --����һ��ȫ����֯�����ڱ�֤����������
    INSERT INTO STV.T_STL_BALANCE_BATCH
      (ID, PERIOD, BUSINESS_DATE, ORG_CODE, ACTIVE, STATUS, create_time)
    values
      (SYS_GUID(),
       V_PERIOD,
       V_BEGIN_DATE,
       Pkg_Stl_Balance_Constant.BALANCE_GLOBAL_ORG,
       PKG_STL_COMMON.YES,
       Pkg_Stl_Balance_Constant.BALANCE_BATCH_INITED,
       sysdate);
    --�����ύ
    COMMIT;
  
    --��׽�쳣
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(TO_CHAR(P_BEGIN_DATE, 'yyyymm'),
                                        V_BEGIN_DATE,
                                        V_BEGIN_DATE,
                                        'pkg_stl_balance_batch.PROC_INIT_BALANCE_BATCH',
                                        '�������α��ʼ���쳣');
  END;

END PKG_STL_BALANCE_BATCH;
/
