package com.deppon.foss.module.settlement.job.server.jobs;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeansException;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.settlement.consumer.api.server.service.IGrayCustomerService;

/**
 * 灰名单：有超期应收单未还款的月结客户
 * 
 *  @author 269044-zhurongrong
 *  @date 2016-04-12
 */
public class GrayCustomerJob extends GridJob{
	
	/**
	 * 记录日志
	 */
	private static final Logger LOGGER = LogManager.getLogger(GrayCustomerJob.class);
	
    /**
     * 定时更新有超期应收单未还款的月结客户
     * 
     *  @author 269044-zhurongrong
     *  @date 2016-04-12
     */
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		IGrayCustomerService grayCustomerService;
		try{
			LOGGER.info("更新灰名单Job 开始。");
			grayCustomerService = getBean("grayCustomerService",IGrayCustomerService.class);
			grayCustomerService.SyncAllGrayCustomersToECS(); 
			LOGGER.info("更新灰名单Job 结束。");
		} catch (BeansException e) {
			LOGGER.error("更新灰名单service初始化失败：" + e.getMessage(), e);
			throw new JobExecutionException("更新灰名单service初始化失败：" + e.getMessage(), e);
		} catch (Exception e){
			LOGGER.error("更新灰名单 Job 执行失败：" + e.getMessage(), e);
			throw new JobExecutionException("更新灰名单 Job 执行失败：" + e.getMessage(), e);
		}
		
	}

}
