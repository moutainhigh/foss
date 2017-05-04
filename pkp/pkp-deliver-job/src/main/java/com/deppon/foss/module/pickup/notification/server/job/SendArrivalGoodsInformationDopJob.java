package com.deppon.foss.module.pickup.notification.server.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IWaybillArriveTempService;


/**
 * FOSS自动给DOP推送到货信息(特殊增值服务类运单)JOB
 * @author 243921-FOSS-zhangtingting
 * @date 2015-12-01 下午02:47:23
 */
public class SendArrivalGoodsInformationDopJob extends GridJob implements StatefulJob{
	private static final Logger LOGGER = LoggerFactory.getLogger(SendArrivalGoodsInformationDopJob.class);

	/**
	 * 
	 * 自动推送到货信息JOB
	 * @param arg0
	 * @throws JobExecutionException
	 * @author 243921-FOSS-zhangtingting
	 * @date 2015-12-01 下午03:07:03
	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			LOGGER.info("SendArrivalGoodsInformationDopJob begin");
			// 最终到达部门创建卸车任务为节点，FOSS自动推送到货信息至DOP
			IWaybillArriveTempService waybillArriveTempService = getBean("waybillArriveTempService", IWaybillArriveTempService.class);
			waybillArriveTempService.autoSendArrivalGoods();
			LOGGER.info("SendArrivalGoodsInformationDopJob end");
		} catch (Exception e) {
			LOGGER.error("自动推送到货信息JOB error", e);
			throw new JobExecutionException("自动推送到货信息JOB error", e);
		}
	}
	
}