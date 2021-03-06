package com.deppon.foss.module.pickup.waybill.server.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcTodoJobService;

public class WaybillRfcRestockJob extends GridJob implements StatefulJob {
	private static final Logger LOGGER = LoggerFactory.getLogger(WaybillRfcRestockJob.class);
	
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			LOGGER.info("处理待办优化 begin");
			//异步生成代办服务
			IWaybillRfcTodoJobService waybillRfcTodoJobService = getBean("waybillRfcTodoJobService", IWaybillRfcTodoJobService.class);
			//执行任务
			waybillRfcTodoJobService.handleRestockTodo();
			LOGGER.info("处理待办优化 end");
		} catch (Exception e) {
			LOGGER.error("error", e);
			throw new JobExecutionException("处理待办优化异常", e);
		}
	}

}
