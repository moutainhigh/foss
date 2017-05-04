package com.deppon.foss.module.settlement.job.server.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.settlement.consumer.api.server.service.IEcsFossErrorLogJobService;

/**
 * 
 * @author: 326181
 * @description: 每30分钟调用 - FOSS推送第三方付款数据到财务自助JOB
 */
public class FossToFinsRemittanceJob extends GridJob {
	private static final Logger LOGGER = LoggerFactory.getLogger(FossToFinsRemittanceJob.class);
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.info("---------------------调用FOSS推送第三方付款数据到财务自助JOB开始：-----------------------------");
		//快递对接FOSS,JOB定时执行service
		IEcsFossErrorLogJobService ecsFossErrorLogJobService = getBean("ecsFossErrorLogJobService", IEcsFossErrorLogJobService.class);
		//启动业务实现类入口
		ecsFossErrorLogJobService.doExecuteFossToFinsRemittanceJob();
		LOGGER.info("---------------------调用FOSS推送第三方付款数据到财务自助JOB结束：-----------------------------");
	}

}
