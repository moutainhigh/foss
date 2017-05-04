DECLARE

BEGIN

	SYS.DBMS_SCHEDULER.CREATE_JOB(JOB_NAME        => 'STV.VCH_DAILY_JOB',
																JOB_TYPE        => 'STORED_PROCEDURE',
																JOB_ACTION      => 'PKG_STL_VCH_JOBS.PROC_VOUCHER_JOBS_DAILY',
																START_DATE      => TRUNC(SYSDATE) + 1,
																REPEAT_INTERVAL => 'Freq=Daily;Interval=1',
																END_DATE        => TO_DATE(NULL),
																JOB_CLASS       => 'DEFAULT_JOB_CLASS',
																ENABLED         => TRUE,
																AUTO_DROP       => FALSE,
																COMMENTS        => '报表凭证日生成程序');

	SYS.DBMS_SCHEDULER.CREATE_JOB(JOB_NAME        => 'STV.VCH_MONTHLY_JOB',
																JOB_TYPE        => 'STORED_PROCEDURE',
																JOB_ACTION      => 'PKG_STL_VCH_JOBS.PROC_VOUCHER_JOBS_MONTHLY',
																START_DATE      => TRUNC(SYSDATE) + 4,
																REPEAT_INTERVAL => 'Freq=Daily;Interval=1',
																END_DATE        => TO_DATE(NULL),
																JOB_CLASS       => 'DEFAULT_JOB_CLASS',
																ENABLED         => TRUE,
																AUTO_DROP       => FALSE,
																COMMENTS        => '报表凭证月生成程序');

END;
