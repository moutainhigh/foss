package com.deppon.foss.module.pickup.waybill.server.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.pickup.waybill.api.server.service.IEWaybillService;

/**
 * <p>电子运单后台自动删除数据服务类.目前是每天2点定时执行<p>
 * @author Foss-105888-Zhangxingwang
 * @date 2014-9-15 20:03:05
 *
 */
public class EWaybillInvalidJob extends GridJob implements StatefulJob {
	private static final Logger LOGGER = LoggerFactory.getLogger(EWaybillInvalidJob.class);
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		try {
			LOGGER.info("作废电子运单数据 begin");
			//异步生成代办服务
			IEWaybillService ewaybillService = getBean("ewaybillService", IEWaybillService.class);
			//执行任务
			ewaybillService.invalidEWaybillOrverDays();
			LOGGER.info("作废电子运单数据 end");
		} catch (Exception e) {
			LOGGER.error("error", e);
			throw new JobExecutionException("作废电子运单数据异常", e);
		}
	}

}
