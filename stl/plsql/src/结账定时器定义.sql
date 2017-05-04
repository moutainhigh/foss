
declare
  /**
  结账定时器
  */

  /**
  默认凌晨3点开始
  */
 v_begin_date date := trunc(SYSDATE) + 1 + 1/24*3; 

  --创建定时器
BEGIN
  --存在则删除
  /*DBMS_SCHEDULER.drop_job('JOB_MAPPING_CUSTOMER_INC');*/

  --创建新的定时器
  DBMS_SCHEDULER.create_job(job_name   => 'JOB_STV_BALANCE_AUTO',
                            job_type   => 'PLSQL_BLOCK',
                            job_action => 'begin pkg_stl_balance.proc_balance_call(''JOB'',''BALANCE''); end;',
                            START_DATE => v_begin_date,
                            REPEAT_INTERVAL => 'FREQ=DAILY;INTERVAL=1', 
                            enabled         => TRUE,
                            COMMENTS        => '期末余额自动结账');

END;

