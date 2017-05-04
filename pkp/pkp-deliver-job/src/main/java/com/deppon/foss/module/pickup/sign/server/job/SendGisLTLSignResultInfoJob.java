package com.deppon.foss.module.pickup.sign.server.job;

import com.deppon.foss.module.pickup.sign.api.server.service.IBatchSendSMSJobService;
import com.deppon.foss.module.pickup.sign.api.server.service.IGisLTLSignResultService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.login.server.downloadtoken.DownloadTokenManager;
import com.deppon.foss.module.login.server.downloadtoken.DownloadTokenManagerFactory;

/**
 * chengjing 306566 传输时间为：每日凌晨4点传给GIS。（与原有时间点保持一致）
 *
 */
public class SendGisLTLSignResultInfoJob extends GridJob{
	/**
	 * 日志
	 */
	protected final static Logger LOGGER = LoggerFactory.getLogger(SendGisLTLSignResultInfoJob.class);
	
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {

		try {
			LOGGER.info("签收运单的目的站匹配地址信息每日凌晨4点传给GIS  start");
			IGisLTLSignResultService gisLTLSignResultService = getBean("gisLTLSignResultService", IGisLTLSignResultService.class);
			gisLTLSignResultService.sendGisLTLSignResultInfo();
			LOGGER.info("签收运单的相关目的站匹配地址信息每日凌晨4点传给GIS  end");
		} catch (Exception e) {
			LOGGER.error("error", e);
			throw new JobExecutionException("签收运单的相关目的站匹配地址信息每日凌晨4点传给GIS的JOB出现异常",e);
		}
	}
}
