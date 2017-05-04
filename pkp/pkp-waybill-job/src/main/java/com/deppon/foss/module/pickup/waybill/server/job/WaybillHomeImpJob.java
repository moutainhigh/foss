package com.deppon.foss.module.pickup.waybill.server.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillHomeImpService;

public class WaybillHomeImpJob extends GridJob implements StatefulJob{

	private static final Logger LOGGER = LoggerFactory.getLogger(WaybillHomeImpJob.class);
	
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			LOGGER.info("FOSS向DOP推送家装运单信息 begin");
			//家装接口
			IWaybillHomeImpService waybillHomeImpService = getBean("waybillHomeImpService", IWaybillHomeImpService.class);
			//执行任务
			waybillHomeImpService.pushWaybillHomeInfo();
			
			LOGGER.info("FOSS向DOP推送推送家装运单信息 end");
		} catch (Exception e) {
			LOGGER.error("error", e);
			throw new JobExecutionException("推送家装运单信息数据异常", e);
		}
	}

}
