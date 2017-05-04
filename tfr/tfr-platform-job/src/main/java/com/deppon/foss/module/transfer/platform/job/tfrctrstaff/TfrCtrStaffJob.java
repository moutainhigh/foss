package com.deppon.foss.module.transfer.platform.job.tfrctrstaff;

import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.platform.api.server.service.ITfrCtrStaffService;
import com.deppon.foss.module.transfer.platform.job.BaseStateFulJob;
import com.deppon.foss.module.transfer.platform.job.goodsareadensity.GoodsAreaDensityJob;

public class TfrCtrStaffJob extends BaseStateFulJob {

	private static final Logger LOGGER = Logger
			.getLogger(GoodsAreaDensityJob.class);

	private static final int threadNo = 0;
	private static final int threadCount = 1;

	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		Date jobStartTime = new Date();

		LOGGER.info("*****外场人员情况job于"
				+ String.format("%1$tF %2$tT", jobStartTime, jobStartTime)
				+ "开始执行*****");

		TfrJobProcessEntity processEntity = new TfrJobProcessEntity();

		ITfrCtrStaffService tfrCtrStaffService = super.getBean(
				"tfrCtrStaffService", ITfrCtrStaffService.class);

		ITfrCommonService tfrCommonService = super.getBean("tfrCommonService",
				ITfrCommonService.class);

		try {
			Date scheduledFireTime = context.getScheduledFireTime();

			processEntity = tfrCommonService.queryJobInfo(
					TfrJobBusinessTypeEnum.TFR_CTR_STAFF,
					scheduledFireTime, threadNo, threadCount);

			Date bizStartTime = new Date();
			LOGGER.info("*****"
					+ String.format("%1$tF %2$tT", bizStartTime, bizStartTime)
					+ "开始生成外场人员情况*****");

			tfrCtrStaffService.insertTfrCtrStaff();

			Date bizEndTime = new Date();
			LOGGER.info("*****"
					+ String.format("%1$tF %2$tT", bizEndTime, bizEndTime)
					+ "结束生成外场人员情况*****");

			processEntity.setJobStartTime(jobStartTime);

			processEntity.setBizStartTime(bizStartTime);
			processEntity.setBizEndTime(bizEndTime);
			processEntity.setStatus(TransferConstants.JOB_SUCCESS);
		} catch (Exception e) {
			LOGGER.info("*****外场人员情况job执行失败*****", e);
			processEntity.setStatus(TransferConstants.JOB_FAILURE);

			// 记录失败日志
			TfrJobProcessLogEntity logEntity = new TfrJobProcessLogEntity();
			logEntity.setBizName(TfrJobBusinessTypeEnum.TFR_CTR_STAFF
					.getBizName());
			logEntity.setBizCode(TfrJobBusinessTypeEnum.TFR_CTR_STAFF
					.getBizCode());
			logEntity.setRemark(TransferConstants.JOB_FAILURE);
			logEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			logEntity.setCreateTime(new Date());

			tfrCommonService.addJobProcessLog(logEntity);

		} finally {
			Date jobEndTime = new Date();
			processEntity.setJobEndTime(jobEndTime);

			tfrCommonService.updateExecutedJob(processEntity);

			LOGGER.info("*****外场人员情况job于"
					+ String.format("%1$tF %2$tT", jobEndTime, jobEndTime)
					+ "结束执行*****");
		}
	}

}
