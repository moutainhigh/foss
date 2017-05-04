package com.deppon.foss.module.pickup.sign.server.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.pickup.sign.api.server.service.IPtpSignService;

public class PtpSignTimeProcessJob extends GridJob{
	private static final Logger LOGGER = LoggerFactory.getLogger(PtpSignTimeProcessJob.class);
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException
	{
		try {
			LOGGER.info("PtpSignTimeProcessJob begin");
			// 付款service
			IPtpSignService ptpSignService = getBean("ptpSignService", IPtpSignService.class);
			// 执行轮询
			ptpSignService.processptpSignjob();
			LOGGER.info("PtpSignTimeProcessJob end");
		} catch (Exception e) {
			LOGGER.error("error", e);
			throw new JobExecutionException("同步签收时间出错",e);
		}
	}
}
