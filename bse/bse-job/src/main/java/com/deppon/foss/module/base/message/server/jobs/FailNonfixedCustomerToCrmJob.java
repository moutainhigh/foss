package com.deppon.foss.module.base.message.server.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFailNonfixedCustomerService;

public class FailNonfixedCustomerToCrmJob extends GridJob implements StatefulJob{

	private static final Logger LOGGER = LoggerFactory.getLogger(FailNonfixedCustomerToCrmJob.class);
	
	/** 
	 * 处理推送失败散客重新推送信息定时任务执行入口
	 * @author 187862-dujunhui
	 * @date 2015-5-8 下午6:33:18
	 * @param context
	 * @throws JobExecutionException
	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		try{
			LOGGER.info("处理推送失败散客重新推送信息任务开始...");
			IFailNonfixedCustomerService failNonfixedCustomerService =getBean("failNonfixedCustomerService", IFailNonfixedCustomerService.class);
			failNonfixedCustomerService.sendInfoToCrm();
			LOGGER.info("处理推送失败散客重新推送信息任务结束...");
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			throw new JobExecutionException(e);
		}
		
	}

}
