--娣诲姞PKP-JOB鏈嶅姟鍣ㄥ疄渚嬭皟搴﹁鍒�

delete from QRTZ_JOB_PLANNINGS where INSTANCE_ID = 'pkp-job';

insert into qrtz_job_plannings (INSTANCE_ID, SCOPE_TYPE, SCOPE_NAME, ACCESS_RULE)
values ('pkp-job', 1, 'pkp-job', 1);

delete from QRTZ_JOB_SCHEDULES where JOB_GROUP = 'pkp-job';
--娣诲姞瀹氭椂鑾峰彇鐭俊鍙戦�缁撴灉浠诲姟
insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA)
values
  ('30',
   'DEFAULT',
   'CronTrigger030',
   'pkp-job',
   'syncSmsProcessJob',
   'syncSmsProcessJob',
   1,
   '0 0/3 * * * ?',
   'com.deppon.foss.module.pickup.notification.server.job.SyncSmsProcessJob',
   '');
--娣诲姞瀹氭椂璁＄畻杩愬崟鍒拌揪鏃堕棿銆佸埌杈炬暟閲忎换鍔�
insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA)
values
  ('31',
   'DEFAULT',
   'CronTrigger031',
   'pkp-job',
   'waybillArriveProcessJob',
   'waybillArriveProcessJob',
   1,
   '0 0/5 * * * ?',
   'com.deppon.foss.module.pickup.notification.server.job.WaybillArriveProcessJob',
   '');

delete dpap_learn.qrtz_job_schedules t where t.trigger_name = 'CronTrigger032'

--娣诲姞浠撳偍璐规棩鏈熸墽琛岃〃璁板綍鎿嶄綔JOB
insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA)
values
  ('51',
   'DEFAULT',
   'CronTrigger051',
   'pkp-job',
   'storageExecdateProcessJob',
   'storageExecdateProcessJob',
   1,
   '0 0 1 * * ?',
   'com.deppon.foss.module.pickup.notification.server.job.StorageExecdateProcessJob',
   '');
--鑷姩閫氱煡JOB
insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA)
values
  ('511',
   'DEFAULT',
   'CronTrigger0511',
   'pkp-job',
   'autoArriveNotifyProcessJob',
   'autoArriveNotifyProcessJob',
   1,
   '0 0/5 8-15 * * ?',
   'com.deppon.foss.module.pickup.notification.server.job.AutoArriveNotifyProcessJob',
   '');

--鎵归噺鏂板寰呮墽琛屾槑缁嗚〃璁板綍鐢熸垚JOB
insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA)
values
  ('77',
   'DEFAULT',
   'CronTrigger077',
   'pkp-job',
   'storageExecDetailProcessJob',
   'storageExecDetailProcessJob',
   1,
   '0 0 2 * * ?',
   'com.deppon.foss.module.pickup.notification.server.job.StorageExecdateDetailProcessJob',
   '');

--娣诲姞浠撳偍璐圭敓鎴怞OB
insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA)
values
  ('32',
   'DEFAULT',
   'CronTrigger032',
   'pkp-job',
   'storageChargeProcessJob',
   'storageChargeProcessJob',
   1,
   '0 0/30 * * * ?',
   'com.deppon.foss.module.pickup.notification.server.job.StorageChargeProcessJob',
   '');
   
-- 娣诲姞寮冭揣job
insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA)
values
  ('14',
   'DEFAULT',
   'CronTrigger014',
   'pkp-job',
   'abandonGoodsApplicationJob',
   'abandonGoodsApplicationJob',
   1,
   '0 0 0 * * ?',
   'com.deppon.foss.module.pickup.predeliver.server.job.AbandonGoodsApplicationJob',
   '');

   
--娣诲姞澶勭悊杩愬崟鐨勯泦涓�璐у皬鍖篔ob
insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA)
values
  ('34',
   'DEFAULT',
   'CronTrigger034',
   'pkp-job',
   'deliverRegionCodeProcessJob',
   'deliverRegionCodeProcessJob',
   1,
   '0 0/2 * * * ?',
   'com.deppon.foss.module.pickup.predeliver.server.job.DeliverRegionCodeProcessJob',
   '');
   
-- 娣诲姞棰勫鐞嗗缓璁甹ob
insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA)
values
  ('7',
   'DEFAULT',
   'CronTrigger006',
   'pkp-job',
   'orderPreprocessJob',
   'orderPreprocessJob',
   1,
   '0/5 * * * * ?',
   'com.deppon.foss.module.pickup.orderpreprocess.server.job.OrderPreprocessJob',
   '');
   
-- 娣诲姞瀹氭椂娓呯┖杞﹁浇淇℃伅job
insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA)
values
  ('11',
   'DEFAULT',
   'CronTrigger011',
   'pkp-job',
   'vehicleActualSituationClearJob',
   'vehicleActualSituationClearJob',
   1,
   '0/5 * * * * ?',
   'com.deppon.foss.module.pickup.orderpreprocess.server.job.VehicleActualSituationClearJob',
   '');

--娣诲姞缁撴竻璐ф寮傛璋冪敤浠樻Job
insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA)
values
  ('37',
   'DEFAULT',
   'CronTrigger036',
   'pkp-job',
   'repaymentJob',
   'repaymentJob',
   1,
   '0 0/2 * * * ?',
   'com.deppon.foss.module.pickup.sign.server.job.RepaymentJob',
   '');

--娣诲姞鏇存敼鍗曚唬鍔炶秴鏃舵彁閱扟ob
insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA)
values
  ('1121',
   'DEFAULT',
   'CronTrigger041',
   'pkp-job',
   'waybillRfcTodoJob',
   'waybillRfcTodoJob',
   1,
   '0 0/30 * * * ?',
   'com.deppon.foss.module.pickup.notification.server.job.WaybillRfcTodoJob',
   '');
   
   
   --杩愬崟鍏ュ簱Job
insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA)
values
  ('1122',
   'DEFAULT',
   'CronTrigger042',
   'pkp-job',
   'WaybillStockJob',
   'WaybillStockJob',
   1,
   '0/30 * * * * ?',
   'com.deppon.foss.module.pickup.waybill.server.job.WaybillStockJob',
   '');
   
    --鍙嶇鏀惰皟鐢ㄤ腑杞帴鍙ｅ紓姝OB
   
   insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA)
values
  ('pkp-job01',
   'DEFAULT',
   'CronTrigger081',
   'pkp-job',
   'inStockProcessJob',
   '鍙嶇鏀惰皟鐢ㄤ腑杞帴鍙ｅ紓姝OB',
   1,
   '0 0/2 * * * ?',
   'com.deppon.foss.module.pickup.sign.server.job.InStockProcessJob',
   '');
  --绛炬敹鍑哄簱鏃惰皟鐢ㄤ腑杞帴鍙ｅ紓姝�
   insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA)
values
  ('pkp-job02',
   'DEFAULT',
   'CronTrigger082',
   'pkp-job',
   'outStockProcessJob',
   '绛炬敹鍑哄簱鏃惰皟鐢ㄤ腑杞帴鍙ｅ紓姝OB',
   1,
   '0 0/2 * * * ?',
   'com.deppon.foss.module.pickup.sign.server.job.OutStockProcessJob', 
   '');
   --寰呭鐞嗗彂閫佺煭淇″紓姝�
   insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA)
values
  ('pkp-job007',
   'DEFAULT',
   'CronTrigger083',
   'pkp-job',
   'PendingSendSMSJob',
   'PendingSendSMSJob',
   1,
   '0/30 * * * * ?',
   'com.deppon.foss.module.pickup.waybill.server.job.PendingSendSMSJob',
   '');
   
     --鏇存敼鍗曞紓姝ョ敓鎴愪唬鍔�
   insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA)
values
  ('pkp-job008',
   'DEFAULT',
   'CronTrigger084',
   'pkp-job',
   'PendingTodoJob',
   'PendingTodoJob',
   1,
   '0 0/10 * * * ?',
   'com.deppon.foss.module.pickup.waybill.server.job.PendingTodoJob',
   '');
   
   
   --澶勭悊鍘嗗彶浠ｅ姙
    insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA)
values
  ('pkp-job010',
   'DEFAULT',
   'CronTrigger087',
   'pkp-job',
   'PendToDoHandlerTodoJob',
   'PendToDoHandlerTodoJob',
   1,
   '0 0 0 ? * *',
   'com.deppon.foss.module.pickup.waybill.server.job.PendToDoHandlerTodoJob',
   '');
   
   --妫�祴涓嬭浇浠ょ墝鐘舵�,姣忛殧5鍒嗛挓鍒锋柊涓� zxy 20140410
    insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA)
values
  ('pkp-job011',
   'DEFAULT',
   'CronTrigger088',
   'pkp-job',
   'RefreshDownloadTokenJob',
   '妫�祴涓嬭浇浠ょ墝鐘舵�,姣忛殧5鍒嗛挓鍒锋柊涓�',
   1,
   '0 0/5 * * * ?',
   'com.deppon.foss.module.pickup.syncdownload.server.job.RefreshDownloadTokenJob',
   '');
   
 --璋冩暣璧拌揣璺緞
    insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA)
values
  ('pkp-job021',
   'DEFAULT',
   'CronTrigger021',
   'pkp-job',
   'WaybillPathDetailJob',
   'WaybillPathDetailJob',
   1,
   '0 0 0 ? * *',
   'com.deppon.foss.module.pickup.waybill.server.job.WaybillPathDetailJob',
   '');
   --閲嶆柊鍏ュ簱
    insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA)
values
  ('pkp-job022',
   'DEFAULT',
   'CronTrigger022',
   'pkp-job',
   'WaybillRfcRestockJob',
   'WaybillRfcRestockJob',
   1,
   '0 0 0 ? * *',
   'com.deppon.foss.module.pickup.waybill.server.job.WaybillRfcRestockJob',
   '');
   --蹇�鑷姩璋冨害鏁版嵁娓呯悊浠诲姟
   --zxy 20140707 鏂板
	INS ERT INTO DPAP_LEARN.QRTZ_JOB_SCHEDULES
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA,
   UPDATE_TIME,
   ACTIVE)
VALUES
  ('pkp-job012',
   'DEFAULT',
   'CronTrigger089',
   'pkp-job',
   'ExpressOrderReportJob',
   '蹇�鑷姩璋冨害鏁版嵁娓呯悊浠诲姟',
   1,
   '0 0 3 * * ?',
   'com.deppon.foss.module.pickup.orderpreprocess.server.job.ExpressOrderReportJob',
   '',
   sysdate,
   1);
   
   --蹇�鍝℃瘡澶╂暩鎿氱当瑷堜换鍔�
   --YB 20140707 鏂板
   insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA,
   UPDATE_TIME,
   ACTIVE)
values
  ('pkp-job16',
   'DEFAULT',
   'CronTrigger090',
   'pkp-job',
   'CourierReportJob',
   '鐢熸垚蹇�鍛樼粺璁℃姤琛�姣忓ぉ1鐐规墽琛�',
   1,
   '0 0 1 * * ?',
   'com.deppon.foss.module.pickup.orderpreprocess.server.job.CourierReportJob',
   null,
   sysdate,
   1);
   
   --蹇�棰勫鐞嗕换鍔�
   --zxy 20140715 鏂板
   insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA,
   UPDATE_TIME,
   ACTIVE)
values
  ('pkp-job17',
   'DEFAULT',
   'CronTrigger091',
   'pkp-job',
   'ExpressOrderPreprocessJob',
   '蹇�棰勫鐞嗕换鍔�,
   1,
   '0/5 * * * * ?',
   'com.deppon.foss.module.pickup.orderpreprocess.server.job.ExpressOrderPreprocessJob',
   null,
   sysdate,
   1);
    
--鎵归噺鍙戦�鐭俊鐩稿叧   娆℃棩鐩稿叧 <1>
insert into dpap_learn.qrtz_job_schedules
  	(ID,
  	TRIGGER_GROUP,
  	TRIGGER_NAME,
  	JOB_GROUP,
  	JOB_NAME,
  	DESCRIPTION,
  	TRIGGER_TYPE,
  	TRIGGER_EXPRESSION,
  	JOB_CLASS,
  	JOB_DATA)
values
  ('b8e92345-b3b4-0d09-a183-d2a507ee3dea',
   'DEFAULT',
   'CronTrigger0100',
   'pkp-job',
   'batchSendSignSMSDayJob',
   '娆℃棩鎵归噺鍙戦�鐭俊鐩稿叧(鍙戣揣鍜岀鍗�',
   1,
   '0 0 12 * * ?',
   'com.deppon.foss.module.pickup.sign.server.job.BatchSendSignSMSDayJob',
   '');

insert into dpap_learn.qrtz_job_schedules
	(ID,
	TRIGGER_GROUP,
	TRIGGER_NAME,
	JOB_GROUP,
	JOB_NAME,
	DESCRIPTION,
	TRIGGER_TYPE,
	TRIGGER_EXPRESSION,
	JOB_CLASS,
	JOB_DATA)
values
  ('5ddfc322-bf6e-8876-eb76-5f2302ea1b31',
   'DEFAULT',
   'CronTrigger0101',
   'pkp-job',
   'batchSendSuccSMSDayJob',
   '娆℃棩鎵归噺鍙戦�鐭俊鐩稿叧(绛炬敹鍜屾垚鍔熻繑鍥�',
   1,
   '0 0 12 * * ?',
   'com.deppon.foss.module.pickup.sign.server.job.BatchSendSuccSMSDayJob',
   '');
   
insert into dpap_learn.qrtz_job_schedules
	(ID,
	TRIGGER_GROUP,
	TRIGGER_NAME,
	JOB_GROUP,
	JOB_NAME,
	DESCRIPTION,
	TRIGGER_TYPE,
	TRIGGER_EXPRESSION,
	JOB_CLASS,
	JOB_DATA)
values
  ('e560d855-cb28-95d9-763f-70a6213e3148',
  'DEFAULT',
  'CronTrigger0102',
  'pkp-job',
  'batchSendSMSWeekJob',
  '鍛ㄦ鎵归噺鍙戦�鐭俊鐩稿叧',
  1,
  '0 0 12 ? * MON',
  'com.deppon.foss.module.pickup.sign.server.job.BatchSendSMSWeekJob',
  '');

   
   --蹇�鑷姩鐢熸垚鐢靛瓙杩愬崟
   --BL 20140902 鏂板
   insert into dpap_learn.qrtz_job_schedules
   (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA,
   UPDATE_TIME,
   ACTIVE)
 values
  ('pkp-job34',
   'DEFAULT',
   'CronTrigger092',
   'pkp-job',
   'EWaybillGenerateJob',
   '蹇�鑷姩鐢熸垚鐢靛瓙杩愬崟浠诲姟',
   1,
   '* 0/2 * * * ?',
   'com.deppon.foss.module.pickup.waybill.server.job',
 	null,
 	sysdate,
 	1);
   
   
      insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA,
   UPDATE_TIME,
   ACTIVE)
values
  ('pkp-job161',
   'DEFAULT',
   'CronTrigger161',
   'pkp-job',
   'EWaybillInvalidJob',
   '鍚庡彴鑷姩浣滃簾鐢靛瓙杩愬崟鏁版嵁(姣忓ぉ2鐐规墽琛�',
   1,
   '0 0 2 * * ?',
   'com.deppon.foss.module.pickup.waybill.server.job.EWaybillInvalidJob',
   null,
   sysdate,
   1);
   
   
   --钀藉湴閰嶅鍙慩X澶╂湭绛炬敹鑷姩涓婃姤OA涓㈣揣
   --yangjinheng 20140902 鏂板
	insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA,
   UPDATE_TIME,
   ACTIVE)
values
  ('pkp-job1220',
   'DEFAULT',
   'CronTrigger1220',
   'pkp-job',
   'LdpNotSignReportOaJob',
   '钀藉湴閰嶅鍙慩X澶╂湭绛炬敹鑷姩涓婃姤OA涓㈣揣',
   1,
   '0 0 0/1 * * ?',
   'com.deppon.foss.module.pickup.express.server.job.LdpNotSignReportOaJob',
   null,
   sysdate,
   1);
   
	--闆嗕腑寮�崟鐭俊鍙戦�鍒ゆ柇鏄惁鍙戦�鎴愬姛
    --gaochunling 20141204 鏂板
	insert into dpap_learn.qrtz_job_schedules
  (ID,
   TRIGGER_GROUP,
   TRIGGER_NAME,
   JOB_GROUP,
   JOB_NAME,
   DESCRIPTION,
   TRIGGER_TYPE,
   TRIGGER_EXPRESSION,
   JOB_CLASS,
   JOB_DATA,
   UPDATE_TIME,
   ACTIVE)
values
  ('pkp-job1222',
   'DEFAULT',
   'CronTrigger1222',
   'pkp-job',
   'WaybillPushMessageJob',
   '闆嗕腑寮�崟鐭俊鍙戦�鍒ゆ柇鏄惁鍙戦�鎴愬姛,姣�鍒嗛挓鎵ц涓�',
   1,
   '0 0/1 * * * ?',
   'com.deppon.foss.module.pickup.waybill.server.job.WaybillPushMessageJob',
   null,
   sysdate,
   1);
