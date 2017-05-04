CREATE OR REPLACE PACKAGE PKG_STL_BALANCE IS

  -- Author  : 000123-fosss-huangxiaobo
  -- Created : 2012/11/19 11:33:31
  -- Purpose : PKG_BALANCE

  --���˵������
  procedure proc_balance_call(p_source_type  in varchar2 default Pkg_Stl_Balance_Constant.BALANCE_SOURCE_TYPE_JOB, --��Դ���-Ĭ��JOB    
                              p_operate_type in varchar2 default Pkg_Stl_Balance_Constant.BALANCE_OPERATE_TYPE_BALANCE, --�������  -- Ĭ�Ͻ���               
                              p_end_date     in date default trunc(sysdate) - 1 --��������
                              );

  --�жϽ��˳����Ƿ�������
  function func_is_balance_run return boolean;

  --�жϽ��˳����Ƿ�������
  function func_is_sub_job_run return boolean;

  ----------------------------------------------
  --����״̬
  --���������
  -------------------------------------------------
  function func_balance_state return int;

  --��ĩ�������
  PROCEDURE PROC_BALANCE_main(p_end_date in timestamp);

  --------------------------------------------------
  --��ĩ����JOB
  --ʵ�ֶ��߳̽���
  --------------------------------------------------
  PROCEDURE proc_balance_job(p_business_date      in timestamp,
                             p_max_threads        int,
                             p_current_thread_num int);

  --��ĩ������
  PROCEDURE PROC_UNBALANCE(P_BEGIN_DATE IN timestamp -- ��������
                           );

  ------------------------------------------------------------------
  --����Ӧ�����
  ------------------------------------------------------------------
  PROCEDURE PROC_COUNT_BALANCE_RECEIVABLE(P_ORG_CODE   IN VARCHAR2, --ҵ����֯
                                          P_BEGIN_DATE IN DATE, -- ��ʼ����
                                          P_END_DATE   IN DATE -- ��������
                                          );

  ------------------------------------------------------------------
  --����Ӧ�����
  ------------------------------------------------------------------
  PROCEDURE PROC_COUNT_BALANCE_PAYABLE(P_ORG_CODE   IN VARCHAR2, --ҵ����֯
                                       P_BEGIN_DATE IN DATE, -- ��ʼ����
                                       P_END_DATE   IN DATE -- ��������
                                       );

  ------------------------------------------------------------------
  --����Ԥ�����
  ------------------------------------------------------------------
  PROCEDURE PROC_COUNT_BALANCE_DEPOSIT(P_ORG_CODE   IN VARCHAR2, --ҵ����֯
                                       P_BEGIN_DATE IN DATE, -- ��ʼ����
                                       P_END_DATE   IN DATE -- ��������
                                       );

END PKG_STL_BALANCE;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_BALANCE IS
  --author��0000123-foss-huangxiaobo
  --created��2012-11-19
  --��ĩ�����˾���ʵ��

  --���˵������
  --ʹ��˵��:
  --JOB ���� �� p_source_type ��= ��JOB�� ����
  --
  --FOSS������ã�
  --     ���ˣ�
  --       p_source_type ��= ������p_operate_type := ��BALANCE�� ��p_end_date := �������Ľ�������
  --     �����ˣ�
  --     p_source_type ��= ������ p_operate_type := ��UNBALANCE�� ��p_end_date := �������Ľ�������
  procedure proc_balance_call(p_source_type  in varchar2 default Pkg_Stl_Balance_Constant.BALANCE_SOURCE_TYPE_JOB, --��Դ���-Ĭ��JOB
                              p_operate_type in varchar2 default Pkg_Stl_Balance_Constant.BALANCE_OPERATE_TYPE_BALANCE, --�������              
                              p_end_date     in date default trunc(sysdate) - 1 --��������
                              ) is
    --����ʵ���Ƿ�������
    v_is_balance_run boolean;
    --��������
    V_end_date timestamp;
    --job_action
    v_job_action varchar2(200);
    --��Դ���
    v_source_type varchar2(30);
    --�������
    v_operate_type varchar2(30);    
  
  begin
    --ʵ���Ƿ�����
    v_is_balance_run := func_is_balance_run;
    --��������
    V_end_date := p_end_date;
    --��Դ���
    v_source_type := p_source_type;
    --�������
    v_operate_type := p_operate_type;
  
    --�����ԴΪJOB
    if v_is_balance_run and
       v_source_type = Pkg_Stl_Balance_Constant.BALANCE_SOURCE_TYPE_JOB then
      --����
      return;
    elsif v_is_balance_run then
      --�׳��쳣
      RAISE_APPLICATION_ERROR('-20002', 'ϵͳ���ڽ��н���,�����ظ�ִ��!');
      return;
    end if;
  
    --У��ʱ�䷶Χ
    if p_end_date >= trunc(sysdate) /*or
       p_end_date < Pkg_Stl_Balance_Constant.BEGIN_BALANCE_DATE*/ then
      --�����쳣
      RAISE_APPLICATION_ERROR('-20002', '���ڷ�Χ����ȷ��');
    end if;
    
  dbms_output.put_line (v_source_type || ',' || v_operate_type );
  
    --�����ԴJOBΪ�Զ�
    if v_source_type = 'JOB' then
      --���ó������
      PROC_BALANCE_main(p_end_date);
    elsif v_operate_type =
          Pkg_Stl_Balance_Constant.BALANCE_OPERATE_TYPE_BALANCE then
      --����job_action
      v_job_action := 'begin pkg_stl_balance.PROC_BALANCE_main(to_date(''' ||
                      to_char(v_end_date, 'yyyymmdd') ||
                      ''',''yyyymmdd'')); end;';
    
      --��������JOB
      DBMS_SCHEDULER.create_job(job_name   => 'JOB_STL_BALANCE_M',
                                job_type   => 'PLSQL_BLOCK',
                                enabled    => true,
                                job_action => v_job_action,
                                COMMENTS   => '��ĩ����ֹ�����');
    
    elsif v_operate_type =
          Pkg_Stl_Balance_Constant.BALANCE_OPERATE_TYPE_UNBALANCE then
      --����job_action
      v_job_action := 'begin pkg_stl_balance.PROC_UNBALANCE(to_date(''' ||
                      to_char(v_end_date, 'yyyymmdd') ||
                      ''',''yyyymmdd'')); end;';
      --����������JOB
      DBMS_SCHEDULER.create_job(job_name   => 'JOB_STL_UNBALANCE_M',
                                job_type   => 'PLSQL_BLOCK',
                                enabled    => true,
                                job_action => v_job_action,
                                COMMENTS   => '��ĩ����ֹ�������');
    end if;
  
    --��׽�쳣
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(TO_CHAR(p_end_date, 'yyyymm'),
                                        p_end_date,
                                        p_end_date,
                                        'pkg_stl_balance.proc_balance_call',
                                        '��ĩ�����뷴����');
      --�����쳣
      RAISE_APPLICATION_ERROR('-20002', SQLCODE || ',' || SQLERRM);
  end;

  --�жϽ��˳����Ƿ�������
  --�ǣ� ������ ���ܽ��н��˲���
  --��δ����  �ܽ��н��˲���
  function func_is_balance_run return boolean is
    --�����м�
    v_row_counts int := 0;
    --�������Ƿ���������
    v_sub_job_is_running boolean := true;
  begin
  
    --�������Ƿ���������
    v_sub_job_is_running := func_is_sub_job_run;
  
    --�Ƿ������У�
    if v_sub_job_is_running then
      return true;
    else
      --�ж��Զ����˵Ķ�ʱ�����Ƿ�������װ 
      select count(1)
        into v_row_counts
        from user_scheduler_running_jobs
       where JOB_NAME in ('JOB_STV_BALANCE_AUTO',
                          'JOB_STL_BALANCE_M',
                          'JOB_STL_UNBALANCE_M');
      --�Ƿ�����
      return v_row_counts > 0;
    end if;
  end;

  --�жϽ����ӳ����Ƿ�������
  function func_is_sub_job_run return boolean is
    --Ĭ�ϵ���1�����ٷ���
    v_run_thread int := 1;
  begin
  
    select count(1)
      into v_run_thread
      from user_scheduler_running_jobs
     where job_name like 'JOB_STV_BALANCE_TH%';
    --����0 ����Ϊ��������
    return v_run_thread > 0;
  end;

  --����״̬
  --0��ʾδ����
  --1��ʾ������
  function func_balance_state return int is
    --����ʵ���Ƿ�������
    v_is_balance_run boolean;
  begin
    --ʵ���Ƿ�����
    v_is_balance_run := func_is_balance_run;
  
    if v_is_balance_run = true then
      return 1;
    else
      return 0;
    end if;
  end;

  --��ĩ�������
  --ע��:����Ϊ�ַ���
  PROCEDURE PROC_BALANCE_main(p_end_date in timestamp) IS
  
    --�����������
    V_proc_end_date date;
  
    --��ʼ����
    V_BEGIN_DATE DATE;
    --��������
    v_end_date Date;
  
    --�ϴν�������
    V_LAST_BUSINES_DATE DATE;
  
    --�Ƿ�����������Ĭ��false��
    V_LAST_DAY_IS_SUCCESS BOOLEAN := FALSE;
  
    --�Ƿ��һ�ν���(Ĭ��false)
    V_IS_FIRST_BALANCE BOOLEAN := FALSE;
  
    --����״̬��¼��
    V_BALANCE_STATUS_ROWS INT;
  
    --��������
    v_job_max_threads int := Pkg_Stl_Balance_Constant.BALANCE_MAX_THREADS;
  
    --ѭ������
    v_index int := 1;
    --��ʱ������
    v_job_name varchar2(200);
    --ִ�н��
    v_result boolean;
    --�����ڼ�
    v_period varchar2(10);
  
  BEGIN
  
    --�������Ľ�������ת�������ڸ�ʽ
    V_proc_end_date := p_end_date;
  
/*    --��ʵ���˼�¼
    SELECT COUNT(1)
      INTO V_BALANCE_STATUS_ROWS
      FROM STV.T_STL_BALANCE_BATCH
     WHERE ROWNUM = 1
          --ֻ��ѯ��Ч���ڼ�
       and active = Pkg_Stl_Common.YES;*/
  
    --�����һ����
    IF /*V_BALANCE_STATUS_ROWS = 0*/ 
      V_proc_end_date<= PKG_STL_BALANCE_CONSTANT.BEGIN_BALANCE_DATE
      THEN
      --��ʶ��һ�ν���
      V_IS_FIRST_BALANCE := TRUE;
    
    ELSE
      --�ҵ����������ڣ������ж��ϴν����Ƿ�����쳣
      SELECT MAX(BUSINESS_DATE)
        INTO V_LAST_BUSINES_DATE
        FROM STV.T_STL_BALANCE_BATCH
      --ֻ��ѯ��Ч���ڼ�
       where active = Pkg_Stl_Common.YES;
    
      --�쳣���������ϴ�δ��ɵĽ��˼ƻ�������ʱ��      
      SELECT COUNT(1)
        INTO V_BALANCE_STATUS_ROWS
        FROM (SELECT STATUS
                FROM STV.T_STL_BALANCE_BATCH
               WHERE BUSINESS_DATE = V_LAST_BUSINES_DATE
                    --ֻ��ѯ��Ч���ڼ�
                 and active = Pkg_Stl_Common.YES
               GROUP BY STATUS);
    
      --��¼��������1�������쳣
      IF V_BALANCE_STATUS_ROWS != 1 THEN
        --�ϴν��˳ɹ�Ϊfalse
        V_LAST_DAY_IS_SUCCESS := FALSE;
        --���л��� 
        proc_unbalance(V_LAST_BUSINES_DATE);
      else
        --�ϴν��˳ɹ�Ϊ�ɹ�
        V_LAST_DAY_IS_SUCCESS := true;
      END IF;
    END IF;
  
    --ȷ����ʼ����
    if V_IS_FIRST_BALANCE = true then
      --�����趨������
      V_BEGIN_DATE := Pkg_Stl_Balance_Constant.BEGIN_BALANCE_DATE;
      --�ϴ��쳣
    elsif V_LAST_DAY_IS_SUCCESS = false then
      --���ν������� �����ϴν������� 
      V_BEGIN_DATE := V_LAST_BUSINES_DATE;
    else
      --������Ϊ����     
      --���ν������� �����ϴν�������  + 1
      V_BEGIN_DATE := V_LAST_BUSINES_DATE + 1;
    end if;
  
    --�ϴν�ת���� ���ڵ��ڽ��죬���ܼ���
    IF V_BEGIN_DATE >= TRUNC(SYSDATE) or V_proc_end_date >= TRUNC(SYSDATE) THEN
      --�����쳣
      RAISE_APPLICATION_ERROR('-20002', '���ܶԵ�����ҵ����н��ˣ���');
    END IF;
  
    --�����ڽ���ѭ��
    while v_begin_date <= V_proc_end_date loop
      begin
        --��������
        v_end_date := v_begin_date + 1;
      
        --����ǩ��δ������Ӧ�յ�
        delete from stv.t_stl_pod_rec;
        commit;
      
        --ǩ��δ���������� 
        --��ǩ��δ��������
        insert into stv.t_stl_pod_rec
          (waybill_no, pod_date, pod_type, receivable_org_code)
          select pod.waybill_no,
                 pod.pod_date,
                 pod.pod_type,
                 bill.receivable_org_code
            from stl.t_stl_pod pod
           inner join stl.t_stl_bill_receivable bill
              on bill.waybill_no = pod.waybill_no
             and bill.is_red_back = PKG_STL_COMMON.NO
           where POD.POD_TYPE in
                 (PKG_STL_COMMON.POD__POD_TYPE__POD,
                  PKG_STL_COMMON.POD__POD_TYPE__UPD)
             AND POD.POD_DATE >= V_BEGIN_DATE
             AND POD.POD_DATE < v_end_date
             and bill.account_date < POD.POD_DATE
           group by pod.waybill_no,
                    pod.pod_date,
                    pod.pod_type,
                    bill.receivable_org_code;
        --�ύ
        commit;
      
        --������ʼ���α�
        PKG_STL_BALANCE_BATCH.PROC_INIT_BALANCE_BATCH(V_BEGIN_DATE);
      
        --��õ�ǰ�ڼ�
        v_period := PKG_STL_BALANCE_COMMON.FUNC_GET_PERIOD(v_begin_date);
      
        --��ǿ�ʼ
        V_RESULT := PKG_STL_BALANCE_BATCH.FUNC_BEGIN_BALANCE_BATCH(Pkg_Stl_Balance_Constant.BALANCE_GLOBAL_ORG,
                                                                   V_BEGIN_DATE);
        --�������󷵻�
        IF V_RESULT = FALSE THEN
          --�����쳣
          RAISE_APPLICATION_ERROR('-20002',
                                  '�����ǲ����쿪ʼ���ˣ��ڳ������쳣');
        END IF;
      
        --����Ǹ��µĵ�һ�� ���ߵ�һ�ν��˾������ڳ�
        IF V_IS_FIRST_BALANCE or
           PKG_STL_BALANCE_COMMON.FUNC_IS_MONTH_FIRST_DAY(V_BEGIN_DATE) THEN
        
          --ʼ��Ӧ���ڳ�
          V_RESULT := PKG_STL_BALANCE_COMMON.FUNC_DAILY_DETAIL_BEGIN(V_BEGIN_DATE,
                                                                     v_period);
        
          ---���Ϊ�Ѿ����ˣ���ֹ�ظ������ڳ�                                                                     
          V_IS_FIRST_BALANCE := false;
          --�ύ
          commit;
          --�������󷵻�
          IF V_RESULT = FALSE THEN
            --�����쳣
            RAISE_APPLICATION_ERROR('-20002', '�����ڳ������쳣');
          END IF;
        END IF;
      
        --���������Ѿ����������˳���ʼ��һ���������
        --hash ֵ��0��ʼ
        v_index := 0;
        while v_index <= v_job_max_threads loop
          v_job_name := 'JOB_STV_BALANCE_TH' || to_char(v_index);
          --��ʼ����ʱ������
          DBMS_SCHEDULER.create_job(job_name   => v_job_name,
                                    job_type   => 'PLSQL_BLOCK',
                                    enabled    => true,
                                    job_action => 'begin stv.pkg_stl_balance.proc_balance_job(to_date(''' ||
                                                  to_char(v_begin_date,
                                                          'yyyymmdd') ||
                                                  ''',''yyyymmdd''),' ||
                                                  v_job_max_threads || ',' ||
                                                  v_index || '); end;',
                                    COMMENTS   => '��ĩ������֮' || v_job_name);
          ----�ȴ�1�룬  
          dbms_lock.sleep(0.5);
          v_index := v_index + 1;
        end loop;
      
        --�������һֱ�����У���ȴ�
        while func_is_sub_job_run loop
          ----�ȴ�1�룬  
          dbms_lock.sleep(1);
        end loop;
      
        --����ÿ����ϸ������ÿ�ջ���
        V_RESULT := PKG_STL_BALANCE_COMMON.FUNC_DAILY_Detail_END(V_BEGIN_DATE,
                                                                 v_end_date,
                                                                 V_PERIOD);
      
        --�������󷵻�
        IF V_RESULT = FALSE THEN
          RAISE_APPLICATION_ERROR('-20002', '����ÿ�ջ�����ϸ�����쳣');
        END IF;
      
        --�������ĩ�������
        --����Ǹ��µ����һ�죬������ĩ���
        IF PKG_STL_BALANCE_COMMON.FUNC_IS_MONTH_LAST_DAY(v_begin_date) THEN
          --�����ÿ����ϸ���л��ܣ�����ÿ����ϸ���
          V_RESULT := PKG_STL_BALANCE_COMMON.FUNC_MONTH_DETAIL_END_AMOUNT(v_period);
        
          --�������󷵻�
          IF V_RESULT = FALSE THEN
            RAISE_APPLICATION_ERROR('-20002', '����ÿ�������ϸ�����쳣');
          END IF;
        
          --�����¶ȷ�����
          V_RESULT := PKG_STL_BALANCE_COMMON.FUNC_MONTH_CUSTOMER_END_AMOUNT(v_period);
        
          --�������󷵻�
          IF V_RESULT = FALSE THEN
            RAISE_APPLICATION_ERROR('-20002', '����ÿ�������ܷ����쳣');
          END IF;
        
        END IF;
      
        --��ǽ��� 
        V_RESULT := PKG_STL_BALANCE_BATCH.FUNC_END_BALANCE_BATCH(Pkg_Stl_Balance_Constant.BALANCE_GLOBAL_ORG,
                                                                 V_BEGIN_DATE);
        --�������󷵻�
        IF V_RESULT = FALSE THEN
          --�����쳣
          RAISE_APPLICATION_ERROR('-20002',
                                  '�����ǲ�������˽��˷����쳣');
        END IF;
      
        --����ʼ���ڽ����ۼ�
        V_BEGIN_DATE := V_BEGIN_DATE + 1;
      
      end;
    end loop;
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(TO_CHAR(V_BEGIN_DATE, 'yyyymm'),
                                        V_BEGIN_DATE,
                                        V_BEGIN_DATE,
                                        'pkg_stl_balance.PROC_BALANCE',
                                        '��ĩ���˷����쳣,�˳�������ѭ������');
  END;

  --------------------------------------------------
  --��ĩ����JOB
  --ʵ�ֶ��߳̽���
  --------------------------------------------------
  procedure proc_balance_job(p_business_date      in timestamp,
                             p_max_threads        int,
                             p_current_thread_num int) is
  
    --��ʼʱ��
    v_begin_date timestamp;
    --����ʱ��
    v_end_date timestamp;
    --��ǰ��֯
    v_org_code varchar2(200);
    --ִ�н��
    V_RESULT boolean;
  
    V_PLSQL VARCHAR2(4000);
  
    --�����α�
    TYPE TYPE_CURSOR IS REF CURSOR;
    V_CURSOR TYPE_CURSOR;
  begin
    --������ʼʱ��
    V_BEGIN_DATE := p_business_date;
    v_end_date   := V_BEGIN_DATE + 1;
  
    --����sql
    V_PLSQL := q'<SELECT org_code FROM stv.t_stl_balance_batch T
		                       WHERE T.STATUS = :1 AND T.ACTIVE = :2 AND T.business_date = :3
                           and ora_hash(org_code, :4, 1) = :5
                           >';
  
    OPEN V_CURSOR FOR V_PLSQL
      USING Pkg_Stl_Balance_Constant.BALANCE_BATCH_INITED, Pkg_Stl_Common.YES, p_business_date, p_max_threads, p_current_thread_num;
    LOOP
      FETCH V_CURSOR
        INTO V_ORG_CODE;
      EXIT WHEN V_CURSOR%NOTFOUND;
      BEGIN
      
        --��ǿ�ʼ
        V_RESULT := PKG_STL_BALANCE_BATCH.FUNC_BEGIN_BALANCE_BATCH(V_ORG_CODE,
                                                                   V_BEGIN_DATE);
        --��ǳɹ�����������
        IF V_RESULT = false THEN
          --�����쳣
          RAISE_APPLICATION_ERROR('-20002',
                                  '��ǿ�ʼ���ˣ��������񲢷���������ϵ����Ա��');
        end if;
      
        --����Ӧ�ս���
        PROC_COUNT_BALANCE_RECEIVABLE(V_ORG_CODE, V_BEGIN_DATE, V_END_DATE);
        --����Ӧ������
        PROC_COUNT_BALANCE_PAYABLE(V_ORG_CODE, V_BEGIN_DATE, V_END_DATE);
        --����Ԥ�ս���
        PROC_COUNT_BALANCE_DEPOSIT(V_ORG_CODE, V_BEGIN_DATE, V_END_DATE);
      
        --��־����
        V_RESULT := PKG_STL_BALANCE_BATCH.FUNC_END_BALANCE_BATCH(V_ORG_CODE,
                                                                 V_BEGIN_DATE);
        IF V_RESULT = FALSE THEN
          --�����쳣 
          RAISE_APPLICATION_ERROR('-20002',
                                  '�����ɽ��ˣ��������񲢷���������ϵ����Ա��');
        END IF;
        --�ύ
        COMMIT;
      end;
    end loop;
    --�ر��α�
    close V_CURSOR;
    --�����쳣ʱ��¼������false
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(TO_CHAR(V_BEGIN_DATE, 'yyyymm'),
                                        V_BEGIN_DATE,
                                        V_BEGIN_DATE,
                                        'pkg_stl_balance.proc_balance_job',
                                        V_ORG_CODE || '���̵߳���');
  end;

  ---------------------------------------------------------------
  --��ĩ������
  --ʵ�ֽ��������ڵ�������ɾ��
  --ע�⣺ֻ��Ҫɾ����Ч��������Ϊ�����޸�����ʱ���ܻ������Ч������  
  ------------------------------------------------------------------
  PROCEDURE PROC_UNBALANCE(P_BEGIN_DATE IN timestamp -- ��ʼ����
                           ) IS
    --��������
    v_end_date date;
  BEGIN
  
    --���ַ�����ת�������ڸ�ʽ
     v_end_date := trunc(P_BEGIN_DATE);
     
     if v_end_date<=PKG_STL_BALANCE_CONSTANT.BEGIN_BALANCE_DATE then 
     v_end_date := PKG_STL_BALANCE_CONSTANT.BEGIN_BALANCE_DATE;
     end if;   
    
    --ɾ�������ϸ����
    DELETE FROM STV.T_STL_BALANCE_DETAIL
     WHERE BALANCE_TIME >= v_end_date
       and active = Pkg_Stl_Common.YES
       and is_init = pkg_stl_common.NO --ɾ���ǳ�ʼ������ϸ����
       ;
    commit;
  
    --ɾ����������
    DELETE FROM STV.T_STL_BALANCE
     WHERE BALANCE_TIME >= v_end_date
       and active = Pkg_Stl_Common.YES;
    commit;
  
    --ɾ��ÿ���˵���ϸ
    delete from stv.t_stl_daily_balance_tmp
     where BALANCE_TIME >= v_end_date
          
       and active = Pkg_Stl_Common.YES;
    commit;
  
    --ɾ��ÿ���˵�����
    delete from stv.t_stl_daily_balance
     where BALANCE_TIME >= v_end_date
       and active = Pkg_Stl_Common.YES;
    commit;
  
    --ɾ���Ѿ����˵����κ�
    delete from STV.T_STL_BALANCE_BATCH B
     WHERE B.BUSINESS_DATE >= v_end_date;
    --�ύ
    COMMIT;
    --��׽�쳣
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(TO_CHAR(P_BEGIN_DATE, 'yyyymm'),
                                        P_BEGIN_DATE,
                                        P_BEGIN_DATE,
                                        'pkg_stl_balance.PROC_UNBALANCE',
                                        '��ĩ������');
  END;

  ------------------------------------------------------------------
  --Ӧ����ĩ����
  ------------------------------------------------------------------
  PROCEDURE PROC_COUNT_BALANCE_RECEIVABLE(P_ORG_CODE   IN VARCHAR2, --ҵ����֯
                                          P_BEGIN_DATE IN DATE, -- ��ʼ����
                                          P_END_DATE   IN DATE -- ��������
                                          ) IS
    --ִ�н��
    V_RESULT BOOLEAN;
    --�����ڼ�
    V_PERIOD VARCHAR2(10);
    --��������
    V_BALANCE_TYPE VARCHAR2(50);
  
  BEGIN
    --��ʼ�������� Ӧ����ĩ����
    V_BALANCE_TYPE := Pkg_Stl_Balance_Constant.BALANCE_TYPE_RECEIVABLE;
  
    --��ý����ڼ�
    V_PERIOD := PKG_STL_BALANCE_COMMON.FUNC_GET_PERIOD(P_BEGIN_DATE);
  
    --ִ��Ӧ����ĩ����
    V_RESULT := PKG_STL_BALANCE_RECEIVABLE.FUNC_COUNT_BALANCE(P_ORG_CODE,
                                                              P_BEGIN_DATE,
                                                              P_END_DATE,
                                                              V_PERIOD,
                                                              V_BALANCE_TYPE);
    --���ؽ�����ɹ����׳��쳣���ع�
    IF V_RESULT = FALSE THEN
      RAISE_APPLICATION_ERROR('-20002', 'Ӧ�ս��˲��ɹ���');
    END IF;
    --��׽�쳣
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(TO_CHAR(P_BEGIN_DATE, 'yyyymm'),
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'pkg_stl_balance.func_count_balance_receivable',
                                        'Ӧ����ĩ����');
  END;

  ------------------------------------------------------------------
  --Ӧ����ĩ����
  ------------------------------------------------------------------
  PROCEDURE PROC_COUNT_BALANCE_PAYABLE(P_ORG_CODE   IN VARCHAR2, --ҵ����֯
                                       P_BEGIN_DATE IN DATE, -- ��ʼ����
                                       P_END_DATE   IN DATE -- ��������
                                       ) IS
  
    --ִ�н��
    V_RESULT BOOLEAN;
    --�����ڼ�
    V_PERIOD VARCHAR2(10);
    --��������
    V_BALANCE_TYPE VARCHAR2(50);
  
  BEGIN
  
    --��ʼ�������� Ӧ����ĩ����
    V_BALANCE_TYPE := Pkg_Stl_Balance_Constant.BALANCE_TYPE_PAYABLE;
    --��ý����ڼ�
    V_PERIOD := PKG_STL_BALANCE_COMMON.FUNC_GET_PERIOD(P_BEGIN_DATE);
  
    --ִ��Ӧ����ĩ����
    V_RESULT := PKG_STL_BALANCE_PAYABLE.FUNC_COUNT_BALANCE(P_ORG_CODE,
                                                           P_BEGIN_DATE,
                                                           P_END_DATE,
                                                           V_PERIOD,
                                                           V_BALANCE_TYPE);
    --���ؽ�����ɹ����׳��쳣���ع�
    IF V_RESULT = FALSE THEN
      RAISE_APPLICATION_ERROR('-20002', 'Ӧ�����˲��ɹ���');
    END IF;
    --��׽�쳣
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(TO_CHAR(P_BEGIN_DATE, 'yyyymm'),
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'pkg_stl_balance.func_count_balance_payable',
                                        'Ӧ����ĩ����');
  END;

  ------------------------------------------------------------------
  --Ԥ����ĩ����
  ------------------------------------------------------------------
  PROCEDURE PROC_COUNT_BALANCE_DEPOSIT(P_ORG_CODE   IN VARCHAR2, --ҵ����֯
                                       P_BEGIN_DATE IN DATE, -- ��ʼ����
                                       P_END_DATE   IN DATE -- ��������
                                       ) IS
  
    --ִ�н��
    V_RESULT BOOLEAN;
    --�����ڼ�
    V_PERIOD VARCHAR2(10);
    --��������
    V_BALANCE_TYPE VARCHAR2(50);
  
  BEGIN
  
    --��ý����ڼ�
    V_PERIOD := PKG_STL_BALANCE_COMMON.FUNC_GET_PERIOD(P_BEGIN_DATE);
  
    --��ʼ�������� Ԥ����ĩ���� --����
    V_BALANCE_TYPE := Pkg_Stl_Balance_Constant.BALANCE_TYPE_DEPOSIT_BILL;
  
    --ִ��Ӧ����ĩ����--����
    V_RESULT := PKG_STL_BALANCE_DEPOSIT_BILL.FUNC_COUNT_BALANCE(P_ORG_CODE,
                                                                P_BEGIN_DATE,
                                                                P_END_DATE,
                                                                V_PERIOD,
                                                                V_BALANCE_TYPE);
    --���ؽ�����ɹ����׳��쳣���ع�
    IF V_RESULT = FALSE THEN
      RAISE_APPLICATION_ERROR('-20002', 'Ԥ��(����)���˲��ɹ���');
    END IF;
  
    --��ת��Ŀ
    --��ʼ�������� Ԥ����ĩ���� --��ת��Ŀ
    V_BALANCE_TYPE := Pkg_Stl_Balance_Constant.BALANCE_TYPE_DEPOSIT_TF;
  
    --ִ��Ӧ����ĩ����--����
    V_RESULT := PKG_STL_BALANCE_DEPOSIT_TF.FUNC_COUNT_BALANCE(P_ORG_CODE,
                                                              P_BEGIN_DATE,
                                                              P_END_DATE,
                                                              V_PERIOD,
                                                              V_BALANCE_TYPE);
  
    --���ؽ�����ɹ����׳��쳣���ع�
    IF V_RESULT = FALSE THEN
      RAISE_APPLICATION_ERROR('-20002', 'Ԥ��(��ת)���˲��ɹ���');
    END IF;
    --�ֽ���ר��Ŀ
    V_BALANCE_TYPE := Pkg_Stl_Balance_Constant.BALANCE_TYPE_DEPOSIT_TF;
     --ִ��Ӧ����ĩ����--����
    V_RESULT := PKG_STL_BALANCE_cash_tf.FUNC_COUNT_BALANCE(P_ORG_CODE,
                                                              P_BEGIN_DATE,
                                                              P_END_DATE,
                                                              V_PERIOD,
                                                              V_BALANCE_TYPE);
  
    --���ؽ�����ɹ����׳��쳣���ع�
    IF V_RESULT = FALSE THEN
      RAISE_APPLICATION_ERROR('-20002', '�ֽ�(��ת)���˲��ɹ���');
    END IF;
    --��׽�쳣
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --�������LOG��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(TO_CHAR(P_BEGIN_DATE, 'yyyymm'),
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'pkg_stl_balance.func_count_balance_deposit',
                                        'Ԥ����ĩ����');
  END;

END PKG_STL_BALANCE;
/
