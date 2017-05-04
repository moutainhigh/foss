package com.deppon.foss.module.pickup.waybill.server.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPendingSendCouponService;

/**
 * 定价优化项目 降价返券需求
 * 
 * @author Foss-206860
 * **/             
public class PendingSendCouponJob extends GridJob implements StatefulJob {

	private static final Logger LOGGER = LoggerFactory.getLogger(PendingSendCouponJob.class);
	
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			LOGGER.info("PendingSendCouponJob begin");
			//待返券服务
			IPendingSendCouponService pendingSendCouponService = getBean("pendingSendCouponService", IPendingSendCouponService.class);
			//执行任务
			pendingSendCouponService.batchjobs();
			LOGGER.info("PendingSendCouponJob end");
		} catch (Exception e) {
			LOGGER.error("error", e);
			throw new JobExecutionException("执行待返券服务异常", e);
		}
	}

}
