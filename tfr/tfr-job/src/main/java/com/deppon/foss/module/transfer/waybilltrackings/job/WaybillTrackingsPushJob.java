/**
 * 
 */
package com.deppon.foss.module.transfer.waybilltrackings.job;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.trackings.api.server.service.IWaybillTrackingsService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;

/**
 * @author niuly
 * @date 2014-04-30 14:16:30
 * @funciton 推送轨迹给快递100
 */
public class WaybillTrackingsPushJob extends GridJob implements StatefulJob {

	private final static Logger LOGGER = LoggerFactory.getLogger(WaybillTrackingsPushJob.class);
	
	/**
	 * @author niuly
	 * @date 2014-04-30 14:16:30
	 * @funciton 推送轨迹给快递100
	 */
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.info("---推送轨迹给快递100JOB开始---");
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;
		IWaybillTrackingsService waybillTrackingsService = this.getBean("waybillTrackingsService", IWaybillTrackingsService.class);
		ITfrCommonService tfrCommonService = this.getBean("tfrCommonService", ITfrCommonService.class);
		try{
			threadNo = Integer.valueOf((String)context.getMergedJobDataMap().get("threadNo")).intValue();
			threadCount = Integer.valueOf((String)context.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = context.getScheduledFireTime();
			
			jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.PUSH_WAYBILL_TRACKS_JOB, scheduledFireTime, threadNo, threadCount);
			//job开始执行时间
			jobStartTime = Calendar.getInstance().getTime();
			//执行任务
			waybillTrackingsService.pushWaybillTracks();
			//job结束执行时间
			jobEndTime = Calendar.getInstance().getTime();
			
			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(scheduledFireTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
			
		}catch(Exception e) {
			LOGGER.error("WaybillTrackingsPushJob error", e);
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
//			记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.PUSH_WAYBILL_TRACKS_JOB.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.PUSH_WAYBILL_TRACKS_JOB.getBizCode());
			jobProcessLogEntity.setRemark("任务执行失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		}finally {
			tfrCommonService.updateExecutedJob(jobProcess);
		}
		LOGGER.info("---推送轨迹给快递100JOB结束---");
	}
}
