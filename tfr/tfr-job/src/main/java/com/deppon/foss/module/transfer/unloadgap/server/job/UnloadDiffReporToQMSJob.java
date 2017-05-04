package com.deppon.foss.module.transfer.unloadgap.server.job;
/**
 * 卸车少货进行上报OA后，将保存上报reportid。便于此job直接获取数据上报QMS
 * 数据部分封装与上报OA数据保持一致
 * @author 283244
 * **/
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
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadDiffReportService;

public class UnloadDiffReporToQMSJob extends BaseStateFulJob{
	private static final Logger LOGGER = LoggerFactory.getLogger(UnloadDiffReporToQMSJob.class);
	
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.trace("************************ 开始执行卸车丢货数据上报QMS###job************************");

		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;
		IUnloadDiffReportService unloadDiffReportService = getBean("unloadDiffReportService", IUnloadDiffReportService.class);
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);

		try{
			threadNo = Integer.valueOf((String) context.getMergedJobDataMap().get("threadNo")).intValue();
			threadCount = Integer.valueOf((String) context.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = context.getScheduledFireTime();
			
//			1、取出上次成功执行JOB后的业务截止时间
			jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.UNLOAD_DIFF_REPORT_QMS, scheduledFireTime, threadNo, threadCount);
			LOGGER.trace("************************ 开始执行卸车丢货数据上报QMS###job************************");
			jobStartTime = Calendar.getInstance().getTime();
			unloadDiffReportService.executeUnloadDiffReportToQMSTask();
			LOGGER.trace("************************ 结束执行卸车丢货数据上报QMS###job************************");
			jobEndTime = new Date();
			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(scheduledFireTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error("上报QMS任务执行失败", e);
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
//			记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.UNLOAD_DIFF_REPORT_QMS.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.UNLOAD_DIFF_REPORT_QMS.getBizCode());
			jobProcessLogEntity.setRemark("任务执行失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		} finally{
			tfrCommonService.updateExecutedJob(jobProcess);
		}

		
	}

}
