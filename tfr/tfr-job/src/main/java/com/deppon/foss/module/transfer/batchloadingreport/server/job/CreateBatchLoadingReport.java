/**
 * @author foss 257200
 * 2015-6-26
 * 257220
 */
package com.deppon.foss.module.transfer.batchloadingreport.server.job;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DateUtils;
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
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.unload.api.server.service.IBatchLoadingReportService;

/**
 * @author 257220
 *
 */
public class CreateBatchLoadingReport extends BaseStateFulJob{

	private static Logger LOGGER = LoggerFactory.getLogger(CreateBatchLoadingReport.class);
	private static String EXCEPTION_REMARK = "任务执行失败！";

	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		LOGGER.info("---生成分批配载上报数据JOB开始---");
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		Date bizStartTime = null; //业务开始时间
		Date bizEndTime = null;//业务结束时间
		int threadNo = 0;
		int threadCount = 1;
		IBatchLoadingReportService batchLoadingReportService = this.getBean("batchLoadingReportService", IBatchLoadingReportService.class);
		ITfrCommonService tfrCommonService = this.getBean("tfrCommonService", ITfrCommonService.class);
		try{
			threadNo = Integer.valueOf((String)context.getMergedJobDataMap().get("threadNo")).intValue();
			threadCount = Integer.valueOf((String)context.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = context.getScheduledFireTime();
			//1、取出上次成功执行JOB后的业务截止时间
			jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.CREATE_BATCHLOADING_REPORT_JOB, scheduledFireTime, threadNo, threadCount);
			bizStartTime = jobProcess.getBizEndTime();//上一次业务截至时间作为这一次的开始时间
			int amount = ConstantsNumberSonar.SONAR_NUMBER_30;//默认分钟
			bizEndTime = DateUtils.addMinutes(bizStartTime, amount);//业务截止时间
			Date now = new Date();
			//如果设置的截止时间大于现在时间，则为现在时间
			if(bizEndTime.after(new Date())){
				bizEndTime = now;
			}
			//job开始执行时间
			jobStartTime = Calendar.getInstance().getTime();
			//执行任务  生成bizStartTime到bizEndTime时间段之内的分批配载数据
			batchLoadingReportService.createBatchLoadingReport(bizStartTime,bizEndTime);
			//job结束执行时间
			jobEndTime = Calendar.getInstance().getTime();
			
			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(bizStartTime);
			jobProcess.setBizEndTime(bizEndTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
			
		}catch(Exception e){
			LOGGER.error("CreateBatchLoadingReport error", e);
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
//			记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.CREATE_BATCHLOADING_REPORT_JOB.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.CREATE_BATCHLOADING_REPORT_JOB.getBizCode());
			jobProcessLogEntity.setRemark(EXCEPTION_REMARK);
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		}finally{
			tfrCommonService.updateExecutedJob(jobProcess);
		}
	}
}
