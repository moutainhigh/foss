
declare
  /**
  ���˶�ʱ��
  */

  /**
  Ĭ���賿3�㿪ʼ
  */
 v_begin_date date := trunc(SYSDATE) + 1 + 1/24*3; 

  --������ʱ��
BEGIN
  --������ɾ��
  /*DBMS_SCHEDULER.drop_job('JOB_MAPPING_CUSTOMER_INC');*/

  --�����µĶ�ʱ��
  DBMS_SCHEDULER.create_job(job_name   => 'JOB_STV_BALANCE_AUTO',
                            job_type   => 'PLSQL_BLOCK',
                            job_action => 'begin pkg_stl_balance.proc_balance_call(''JOB'',''BALANCE''); end;',
                            START_DATE => v_begin_date,
                            REPEAT_INTERVAL => 'FREQ=DAILY;INTERVAL=1', 
                            enabled         => TRUE,
                            COMMENTS        => '��ĩ����Զ�����');

END;

