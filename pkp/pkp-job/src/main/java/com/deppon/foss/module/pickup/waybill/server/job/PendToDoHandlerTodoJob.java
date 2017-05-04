/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.server.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcTodoJobService;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class PendToDoHandlerTodoJob  extends GridJob implements StatefulJob {

	private static final Logger LOGGER = LoggerFactory.getLogger(PendToDoHandlerTodoJob.class);
	
	@Override
	protected void doExecute(JobExecutionContext context)
		throws JobExecutionException {
		try {
			//异步生成代办服务
			IWaybillRfcTodoJobService waybillRfcTodoJobService = getBean("waybillRfcTodoJobService", IWaybillRfcTodoJobService.class);
			//执行任务
			waybillRfcTodoJobService.handlerTodoJob();
		} catch (Exception e) {
			LOGGER.error("error", e);
		}
	}

}
