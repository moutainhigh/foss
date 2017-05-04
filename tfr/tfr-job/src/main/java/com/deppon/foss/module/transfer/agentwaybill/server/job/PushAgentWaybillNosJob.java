package com.deppon.foss.module.transfer.agentwaybill.server.job;

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
import com.deppon.foss.module.transfer.partialline.api.server.service.IAgentWaybillTrackService;
/**
 * @author niuly
 * @function 推送绑定代理单号给快递100
 * @date 2015年2月4日下午3:36:29
 */
public class PushAgentWaybillNosJob extends GridJob implements StatefulJob{
	private final static Logger LOGGER = LoggerFactory.getLogger(PushAgentWaybillNosJob.class);

	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.info("---推送绑定快递代理单号给快递100的JOB开始---");
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;
		IAgentWaybillTrackService agentWaybillTrackService = this.getBean("agentWaybillTrackService", IAgentWaybillTrackService.class);
		ITfrCommonService tfrCommonService = this.getBean("tfrCommonService", ITfrCommonService.class);
		try{
			threadNo = Integer.valueOf((String)context.getMergedJobDataMap().get("threadNo")).intValue();
			threadCount = Integer.valueOf((String)context.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = context.getScheduledFireTime();
			
			jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.PUSH_AGENT_WAYBILLNO_JOB, scheduledFireTime, threadNo, threadCount);
			//job开始执行时间
			jobStartTime = Calendar.getInstance().getTime();
			//执行任务
			agentWaybillTrackService.pushAgentWaybillNos();
			//job结束执行时间
			jobEndTime = Calendar.getInstance().getTime();
			
			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(scheduledFireTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
			
		}catch(Exception e) {
			LOGGER.error("PushAgentWaybillNosJob error", e);
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
//			记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.PUSH_AGENT_WAYBILLNO_JOB.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.PUSH_AGENT_WAYBILLNO_JOB.getBizCode());
			jobProcessLogEntity.setRemark("任务执行失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		}finally {
			tfrCommonService.updateExecutedJob(jobProcess);
		}
		LOGGER.info("---推送绑定快递代理单号给快递100的JOB结束---");
	}

}
