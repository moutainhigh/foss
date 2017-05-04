--添加PKP-JOB服务器实例调度计划

delete from QRTZ_JOB_PLANNINGS where INSTANCE_ID = 'pkp-job';

insert into qrtz_job_plannings (INSTANCE_ID, SCOPE_TYPE, SCOPE_NAME, ACCESS_RULE)
values ('pkp-job', 1, 'pkp-job', 1);

delete from QRTZ_JOB_SCHEDULES where JOB_GROUP = 'pkp-job';
--添加定时获取短信发送结果任务---为示例
--insert into dpap_learn.qrtz_job_schedules
--  (ID,
--   TRIGGER_GROUP,
--   TRIGGER_NAME,
--   JOB_GROUP,
--   JOB_NAME,
--   DESCRIPTION,
--   TRIGGER_TYPE,
--   TRIGGER_EXPRESSION,
--   JOB_CLASS,
--   JOB_DATA)
--values
--  ('30',
--   'DEFAULT',
--   'CronTrigger030',
--   'pkp-job',
--   'syncSmsProcessJob',
--   'syncSmsProcessJob',
--   1,
--   '0 0/3 * * * ?',
--   'com.deppon.foss.module.pickup.notification.server.job.SyncSmsProcessJob',
--   '');