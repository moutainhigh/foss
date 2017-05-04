package com.deppon.foss.module.transfer.unloadgap.server.job;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.job.BaseStateFulJob;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskSmsService;
/***
 * 
* @ClassName: UnloadTaskSMSJob 
* @Description: 卸车时 根据运单 发短信
* @author 189284-ZhangXu 
* @date 2015-6-18 上午9:43:58 
*
 */
public class UnloadTaskSmsJob extends BaseStateFulJob{
	private final static Logger LOGGER = LoggerFactory.getLogger(UnloadTaskSmsJob.class);
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {

		LOGGER.error("******************开始执行卸车发短信JOB***************************");
		IUnloadTaskSmsService unloadTaskSmsService = getBean("unloadTaskSmsService", IUnloadTaskSmsService.class);
		
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);
		try {
			threadNo = Integer.valueOf((String) context.getMergedJobDataMap().get("threadNo")).intValue();
			threadCount = Integer.valueOf((String) context.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = context.getScheduledFireTime();
			
//			1、取出上次成功执行JOB后的业务截止时间
			jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.UNLOAD_TASK_SMS_JOB, scheduledFireTime, threadNo, threadCount);
			
//			2、执行业务任务
			jobStartTime = Calendar.getInstance().getTime();
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS"); 
			LOGGER.error("卸车任务，发送短信时间:"+df.format(jobProcess.getBizStartTime())+"-"+df.format(scheduledFireTime));
			unloadTaskSmsService.sendUnloadTaskSms();
			jobEndTime = Calendar.getInstance().getTime();
//			3、更新任务完成状态及时间
			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(scheduledFireTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
		} catch (Exception e) {
			LOGGER.error("UnloadTaskSMSJob error", e);
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
//			记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.UNLOAD_TASK_SMS_JOB.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.UNLOAD_TASK_SMS_JOB.getBizCode());
			jobProcessLogEntity.setRemark("任务执行失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		} finally{
			tfrCommonService.updateExecutedJob(jobProcess);
		}
		LOGGER.error("*********************结束卸车发短信JOB*****************************");
		
			
	}
}
