package com.deppon.foss.module.transfer.platform.job.stockduration;

import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.platform.api.server.service.IStockDurationService;
import com.deppon.foss.module.transfer.platform.job.BaseStateFulJob;

public class StockDurationExpJob extends BaseStateFulJob {

	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {

		Date jobStartTime = new Date();

		TfrJobProcessEntity processEntity = new TfrJobProcessEntity();

		IStockDurationService stockDurationService = super.getBean(
				"stockDurationService", IStockDurationService.class);

		ITfrCommonService tfrCommonService = super.getBean("tfrCommonService",
				ITfrCommonService.class);

		int threadNo = 0;
		int threadCount = 0;

		try {
			threadNo = Integer.valueOf(
					(String) context.getMergedJobDataMap().get("threadNo"))
					.intValue();
			threadCount = Integer.valueOf(
					(String) context.getMergedJobDataMap().get("threadCount"))
					.intValue();

			Date scheduledFireTime = context.getScheduledFireTime();

			processEntity = tfrCommonService.queryJobInfo(
					TfrJobBusinessTypeEnum.STOCK_DURATION_EXP, scheduledFireTime,
					threadNo, threadCount);

			Date bizStartTime = new Date();

			stockDurationService.generateExp(threadCount, threadNo);

			Date bizEndTime = new Date();

			processEntity.setJobStartTime(jobStartTime);

			processEntity.setBizStartTime(bizStartTime);
			processEntity.setBizEndTime(bizEndTime);
			processEntity.setStatus(TransferConstants.JOB_SUCCESS);
		} catch (Exception e) {
			processEntity.setStatus(TransferConstants.JOB_FAILURE);

			TfrJobProcessLogEntity logEntity = new TfrJobProcessLogEntity();
			logEntity
					.setBizName(TfrJobBusinessTypeEnum.STOCK_DURATION_EXP.getBizName());
			logEntity
					.setBizCode(TfrJobBusinessTypeEnum.STOCK_DURATION_EXP.getBizCode());
			logEntity.setRemark(TransferConstants.JOB_FAILURE);
			logEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			logEntity.setCreateTime(new Date());

			tfrCommonService.addJobProcessLog(logEntity);

		} finally {
			Date jobEndTime = new Date();
			processEntity.setJobEndTime(jobEndTime);

			tfrCommonService.updateExecutedJob(processEntity);
		}
	}

}
