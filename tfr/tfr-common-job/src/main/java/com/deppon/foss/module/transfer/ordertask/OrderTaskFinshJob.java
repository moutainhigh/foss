package com.deppon.foss.module.transfer.ordertask;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.job.BaseStateFulJob;
import com.deppon.foss.module.transfer.unload.api.server.service.IOrderTaskService;

public class OrderTaskFinshJob extends BaseStateFulJob {

	@Override
	protected void doExecute(JobExecutionContext paramJobExecutionContext)
			throws JobExecutionException {
		LOGGER.trace("************************ 开始执行JOB ************************");
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;
		IOrderTaskService orderTaskService = getBean("orderTaskService", IOrderTaskService.class);
		//提供中转模块中的通用服务 
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);
		try{
			//获取线程号 
			threadNo = Integer.valueOf((String) paramJobExecutionContext.getMergedJobDataMap().get("threadNo")).intValue();
			//获取线程数
			threadCount = Integer.valueOf((String) paramJobExecutionContext.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = paramJobExecutionContext.getScheduledFireTime();
			
			//1、取出上次成功执行JOB后的业务截止时间
			jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.PUSH_ORDERTASKFINISH_QMS_JOB, scheduledFireTime, threadNo, threadCount);
			
			//2、执行业务任务
			orderTaskService.orderTaskFinish();
			//初始化开始时间
			jobStartTime = Calendar.getInstance().getTime();
			/*** 执行丢货找到数据上报业务 ***/
			
			//初始化结束时间
			jobEndTime = new Date();

			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(scheduledFireTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
		}catch(Exception e){

			e.printStackTrace();
			LOGGER.error("任务执行失败", e);
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
			//记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.PUSH_ORDERTASKFINISH_QMS_JOB.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.PUSH_ORDERTASKFINISH_QMS_JOB.getBizCode());
			jobProcessLogEntity.setRemark("任务执行失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		}finally{
			tfrCommonService.updateExecutedJob(jobProcess);
		}
		
		LOGGER.trace("************************ 结束执行JOB ************************");
	}

}
