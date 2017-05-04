package com.deppon.foss.module.transfer.salesdeptexplost.job;

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
import com.deppon.foss.module.transfer.stock.api.server.service.ISalesDeptExpLostService;

public class SaveStock0amSnapshotJob extends BaseStateFulJob {

	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {

		Date jobStartTime = new Date();

		TfrJobProcessEntity processEntity = new TfrJobProcessEntity();

		ISalesDeptExpLostService salesDeptExpLostService = super.getBean(
				"salesDeptExpLostService", ISalesDeptExpLostService.class);

		ITfrCommonService tfrCommonService = super.getBean("tfrCommonService",
				ITfrCommonService.class);

		int threadNo = 0;
		int threadCount = 0;

		try {
			Date scheduledFireTime = context.getScheduledFireTime();

			processEntity = tfrCommonService.queryJobInfo(
					TfrJobBusinessTypeEnum.SALES_DEPT_EXP_LOST_STOCK,
					scheduledFireTime, threadNo, threadCount);

			Date bizStartTime = new Date();

			salesDeptExpLostService.saveStock0amSnapshot();

			Date bizEndTime = new Date();

			processEntity.setJobStartTime(jobStartTime);

			processEntity.setBizStartTime(bizStartTime);
			processEntity.setBizEndTime(bizEndTime);
			processEntity.setStatus(TransferConstants.JOB_SUCCESS);
		} catch (Exception e) {
			processEntity.setStatus(TransferConstants.JOB_FAILURE);

			TfrJobProcessLogEntity logEntity = new TfrJobProcessLogEntity();
			logEntity.setBizName(TfrJobBusinessTypeEnum.SALES_DEPT_EXP_LOST_STOCK
					.getBizName());
			logEntity.setBizCode(TfrJobBusinessTypeEnum.SALES_DEPT_EXP_LOST_STOCK
					.getBizCode());
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
