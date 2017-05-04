package com.deppon.foss.module.pickup.lostcargo.server.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.pickup.sign.api.server.service.ILostCargoNotifyService;

/***
 * @clasaName:com.deppon.foss.module.pickup.lostcargo.server.job.LostCargoNotifyProcessJob
 * @author: foss-yuting
 * @description: 丢货差错自动上报	 次日上午8点发送打包数据  </br>
 * @date:2014年12月3日 下午2:27:21
 */
public class LostCargoNotifyProcessJob extends GridJob {
		private static final Logger LOGGER = LoggerFactory.getLogger(LostCargoNotifyProcessJob.class);
		@Override
		protected void doExecute(JobExecutionContext context) throws JobExecutionException
		{
			try {
				LOGGER.info("丢货差错自动上报 start");
				ILostCargoNotifyService lostCargoNotifyService = getBean("lostCargoNotifyService", ILostCargoNotifyService.class);
				lostCargoNotifyService.processNotifyLastCargo();
				LOGGER.info("丢货差错自动上报  end");
			} catch (Exception e) {
				LOGGER.error("error", e);
				throw new JobExecutionException("丢货差错自动上报异常",e);
			}
		}
		
	}