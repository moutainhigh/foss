package com.deppon.foss.module.transfer.platform.job.pullbackRate;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.platform.api.server.service.IPullbackRateService;


/**
* @description 拉回率的定时任务
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年3月8日 下午5:11:28
*/
public class PullbackRateJob extends GridJob implements StatefulJob {
	private final static Logger LOGGER = LoggerFactory.getLogger(PullbackRateJob.class);
	
	/**
	* @description 拉回率的定时任务
	* (non-Javadoc)
	* @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:01:16
	* @version V1.0
	*/
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		LOGGER.info("---拉回率的定时任务JOB开始---");
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 0;
		IPullbackRateService pullbackRateService = this.getBean("pullbackRateService", IPullbackRateService.class);
		ITfrCommonService tfrCommonService = this.getBean("tfrCommonService", ITfrCommonService.class);
		try{
			threadNo = Integer.valueOf((String)context.getMergedJobDataMap().get("threadNo")).intValue();
			threadCount = Integer.valueOf((String)context.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = context.getScheduledFireTime();
			
			jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.PULLBACK_RATE_JOB, scheduledFireTime, threadNo, threadCount);
			//job开始执行时间
			jobStartTime = Calendar.getInstance().getTime();
			//执行任务(6点采集数据)
			pullbackRateService.doPullbackRateJobList(scheduledFireTime, threadNo, threadCount);
			//job结束执行时间
			jobEndTime = Calendar.getInstance().getTime();
			
			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(scheduledFireTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
			
		}catch(Exception e) {
			LOGGER.error("PullbackRateJob error", e);
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
//			记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.PULLBACK_RATE_JOB.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.PULLBACK_RATE_JOB.getBizCode());
			jobProcessLogEntity.setRemark("任务执行失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		}finally {
			tfrCommonService.updateExecutedJob(jobProcess);
		}
		LOGGER.info("---拉回率的定时任务JOB结束---");

	}

}
