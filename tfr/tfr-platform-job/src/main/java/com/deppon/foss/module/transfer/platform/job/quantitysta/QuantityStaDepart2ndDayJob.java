package com.deppon.foss.module.transfer.platform.job.quantitysta;

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
import com.deppon.foss.module.transfer.platform.api.server.service.IQuantityStaDepartService;
import com.deppon.foss.module.transfer.platform.job.BaseStateFulJob;

public class QuantityStaDepart2ndDayJob extends BaseStateFulJob {

	private static final Logger LOGGER = Logger
			.getLogger(QuantityStaDepart2ndDayJob.class);

	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {

		Date jobStartTime = new Date();

		LOGGER.info("*****货量预测-实际货量-第2天出发货量job于"
				+ String.format("%1$tF %2$tT", jobStartTime, jobStartTime)
				+ "开始执行*****");

		TfrJobProcessEntity processEntity = new TfrJobProcessEntity();

		IQuantityStaDepartService quantityStaDepartService = super.getBean(
				"quantityStaDepartService", IQuantityStaDepartService.class);

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

			LOGGER.info("scheduledFireTime: "
					+ String.format("%1$tF %2$tT", scheduledFireTime,
							scheduledFireTime));

			processEntity = tfrCommonService.queryJobInfo(
					TfrJobBusinessTypeEnum.QUANTITY_STA_DEPART_2ND_DAY,
					scheduledFireTime, threadNo, threadCount);

			Date bizStartTime = new Date();
			LOGGER.info("*****"
					+ String.format("%1$tF %2$tT", bizStartTime, bizStartTime)
					+ "开始生成货量预测-实际货量-第2天出发货量*****");

			quantityStaDepartService.generateDepart2ndDay(jobStartTime,
					threadCount, threadNo);

			Date bizEndTime = new Date();
			LOGGER.info("*****"
					+ String.format("%1$tF %2$tT", bizEndTime, bizEndTime)
					+ "结束生成货量预测-实际货量-第2天出发货量*****");

			processEntity.setJobStartTime(jobStartTime);

			processEntity.setBizStartTime(bizStartTime);
			processEntity.setBizEndTime(bizEndTime);
			processEntity.setStatus(TransferConstants.JOB_SUCCESS);
		} catch (Exception e) {
			LOGGER.info("*****货量预测-实际货量-第2天出发货量job执行失败*****", e);
			processEntity.setStatus(TransferConstants.JOB_FAILURE);

			// 记录失败日志
			TfrJobProcessLogEntity logEntity = new TfrJobProcessLogEntity();
			logEntity
					.setBizName(TfrJobBusinessTypeEnum.QUANTITY_STA_DEPART_2ND_DAY
							.getBizName());
			logEntity
					.setBizCode(TfrJobBusinessTypeEnum.QUANTITY_STA_DEPART_2ND_DAY
							.getBizCode());
			logEntity.setRemark(TransferConstants.JOB_FAILURE);
			logEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			logEntity.setCreateTime(new Date());

			tfrCommonService.addJobProcessLog(logEntity);

		} finally {
			Date jobEndTime = new Date();
			processEntity.setJobEndTime(jobEndTime);

			tfrCommonService.updateExecutedJob(processEntity);

			LOGGER.info("*****货量预测-实际货量-第2天出发货量job于"
					+ String.format("%1$tF %2$tT", jobEndTime, jobEndTime)
					+ "结束执行*****");
		}
	}
}
