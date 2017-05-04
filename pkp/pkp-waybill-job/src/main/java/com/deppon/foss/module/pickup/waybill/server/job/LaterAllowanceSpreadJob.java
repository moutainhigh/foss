package com.deppon.foss.module.pickup.waybill.server.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICompensateSpreadService;

/**
 * 
* @Description: 晚到补差价定时任务 每两分钟执行一次
* @author hbhk 
* @date 2015-7-2 下午2:27:57
 */
public class LaterAllowanceSpreadJob extends GridJob implements StatefulJob {
	private static final Logger LOGGER = LoggerFactory.getLogger(LaterAllowanceSpreadJob.class);
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		try {
			LOGGER.info("晚到补差价定时任务 begin");
			ICompensateSpreadService compensateSpreadService = getBean("compensateSpreadService", ICompensateSpreadService.class);
			//执行任务
			compensateSpreadService.autoSendSMSAndGetCoupon();
			LOGGER.info("晚到补差价定时任务 end");
		} catch (Exception e) {
			LOGGER.error("晚到补差价定时任务error", e);
		}
	}
}
