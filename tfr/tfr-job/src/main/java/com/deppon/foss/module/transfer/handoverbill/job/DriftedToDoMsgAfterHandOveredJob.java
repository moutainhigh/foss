package com.deppon.foss.module.transfer.handoverbill.job;

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
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.unloadgap.server.job.UnloadDiffReportJob;

/** 
 * @className: DriftedToDoMsgAfterHandOveredJob
 * @author: ShiWei shiwei@outlook.com
 * @description: 交接单保存后，将运单的待办漂移至下一部门
 * @date: 2013-6-5 下午9:35:42
 * 
 */
public class DriftedToDoMsgAfterHandOveredJob extends BaseStateFulJob {

private static final Logger LOGGER = LoggerFactory.getLogger(UnloadDiffReportJob.class);
	
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.trace("************************ 开始执行交接单待办漂移JOB ************************");

		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;
		IHandOverBillService handOverBillService = getBean("handOverBillService", IHandOverBillService.class);
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);

		try{
			threadNo = Integer.valueOf((String) context.getMergedJobDataMap().get("threadNo")).intValue();
			threadCount = Integer.valueOf((String) context.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = context.getScheduledFireTime();
			
//			1、取出上次成功执行JOB后的业务截止时间
			jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.HANDOVERBILL_TODO, scheduledFireTime, threadNo, threadCount);
			
//			2、执行业务任务
			jobStartTime = Calendar.getInstance().getTime();
			handOverBillService.driftedToDoMsgAfterHandOvered();
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
//			记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.HANDOVERBILL_TODO.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.HANDOVERBILL_TODO.getBizCode());
			jobProcessLogEntity.setRemark("任务执行失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		} finally{
			tfrCommonService.updateExecutedJob(jobProcess);
		}

		LOGGER.trace("************************ 结束交接单待办漂移JOB ************************");
	}

}
