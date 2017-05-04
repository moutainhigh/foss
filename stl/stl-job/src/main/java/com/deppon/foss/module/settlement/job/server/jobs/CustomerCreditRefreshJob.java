package com.deppon.foss.module.settlement.job.server.jobs;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeansException;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICustomerCreditRefreshService;

/**
 * 客户额度还原服务
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-11-17 下午5:27:16
 */
public class CustomerCreditRefreshJob extends GridJob {

	private static final Logger LOGGER = LogManager.getLogger(CustomerCreditRefreshJob.class);

	/**
	 * <p>
	 * 定时更新月结额度为零的客户财务刷新标记
	 * </p>
	 * 
	 * @author 105762
	 * @date 2014-3-11 下午2:39:51
	 * @param arg0
	 * @throws JobExecutionException
	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void doExecute(JobExecutionContext arg0) throws JobExecutionException {
		ICustomerCreditRefreshService customerCreditRefreshService;
		try {
			LOGGER.info("更新客户月结额度为零的客户信用额度时间戳 Job 开始。");
			customerCreditRefreshService = getBean("customerCreditRefreshService",ICustomerCreditRefreshService.class);

			customerCreditRefreshService.updateZeroCreditFinanceMark();
			
		} catch (BeansException e) {
			LOGGER.error("更新客户月结额度为零的客户信用额度service初始化失败：" + e.getMessage(), e);
			throw new JobExecutionException("更新客户月结额度为零的客户信用额度service初始化失败：" + e.getMessage(), e);
		} catch (Exception e){
			LOGGER.error("更新客户月结额度为零的客户信用额度 Job 执行失败：" + e.getMessage(), e);
			throw new JobExecutionException("更新客户月结额度为零的客户信用额度 Job 执行失败：" + e.getMessage(), e);
		}
	}
}
