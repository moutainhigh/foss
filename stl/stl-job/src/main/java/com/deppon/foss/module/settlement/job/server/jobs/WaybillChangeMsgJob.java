package com.deppon.foss.module.settlement.job.server.jobs;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.settlement.closing.api.server.service.IWaybillChangeMsgProcessService;


/**
 * 财务完结消息处理
 * @author ibm-zhuwei
 * @date 2012-12-4 下午5:28:19
 */
public class WaybillChangeMsgJob extends GridJob implements StatefulJob {

	private static final Logger LOGGER = LogManager.getLogger(WaybillChangeMsgJob.class);
	

	
	/** 
	 * 财务完结任务执行
	 * @author ibm-zhuwei
	 * @date 2012-12-21 上午10:14:40
	 * @param context
	 * @throws JobExecutionException
	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		
		//业务完结消息
		IWaybillChangeMsgProcessService waybillChangeMsgProcessService;
		try {
			LOGGER.info("财务完结消息处理开始...");
			
			waybillChangeMsgProcessService = getBean("waybillChangeMsgProcessService",
					IWaybillChangeMsgProcessService.class);
			
			waybillChangeMsgProcessService.processChangeMsg();
			
			LOGGER.info("财务完结消息处理结束...");
		} catch (Exception e) {
			LOGGER.error("业务完结消息处理发生错误:" + e.getMessage(), e);
			throw new JobExecutionException("业务完结消息处理发生错误:" + e.getMessage(), e);
		}
		
	}

}
