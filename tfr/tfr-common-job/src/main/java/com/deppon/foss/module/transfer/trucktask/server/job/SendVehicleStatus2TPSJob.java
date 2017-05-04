package com.deppon.foss.module.transfer.trucktask.server.job;

import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.arrival.api.server.service.ISendVehicleStatus2TPSJobService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.job.BaseStateFulJob;

public class SendVehicleStatus2TPSJob extends BaseStateFulJob {
	private static final Logger LOGGER = LoggerFactory.getLogger(SendVehicleStatus2TPSJob.class);
	
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		
		LOGGER.error("************************ 开始***执行推送车辆状态到TPS系统JOB ************************");
		
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;
		ISendVehicleStatus2TPSJobService sendVehicleStatus2TPSJobService = getBean("sendVehicleStatus2TPSJobService", ISendVehicleStatus2TPSJobService.class);
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);
		
		try {
			threadNo = Integer.valueOf((String) context.getMergedJobDataMap().get("threadNo")).intValue();
			threadCount = Integer.valueOf((String) context.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = context.getScheduledFireTime();
			
			//1、取出上次成功执行JOB后的业务截止时间
			jobProcess = tfrCommonService.queryLastExecutedJobInfo(TfrJobBusinessTypeEnum.SEND_VEHICLESTATUS_2TPS_JOB, scheduledFireTime, threadNo, threadCount);
			
			//2、执行业务任务，推送车辆状态到TPS系统
			jobStartTime = Calendar.getInstance().getTime();
			sendVehicleStatus2TPSJobService.sendVehicleStatus2TPS();
			jobEndTime = Calendar.getInstance().getTime();
			//3、更新任务完成状态及时间
			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(scheduledFireTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
			//记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.SEND_VEHICLESTATUS_2TPS_JOB.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.SEND_VEHICLESTATUS_2TPS_JOB.getBizCode());
			jobProcessLogEntity.setRemark("任务执行失败！");
			jobProcessLogEntity.setExceptionInfo(e.getMessage());
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		} finally{
			tfrCommonService.updateExecutedJob(jobProcess);
		}
		
		LOGGER.error("************************ 结束***执行推送车辆状态到TPS系统JOB ************************");
	}

}
