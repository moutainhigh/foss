package com.deppon.foss.module.settlement.job.server.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.settlement.consumer.api.server.service.IDebtCreditLimitInfoQueryService;

/**
 * 发送最早欠款及已用信用额度的客户信息Job
 * 
 * @author foss-zhangxiaohui
 * @date Jan 15, 2013 2:12:29 PM
 */
public class SendDebtCreditLimitInfoJob extends GridJob implements StatefulJob {

	/**
	 * 声明日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SendDebtCreditLimitInfoJob.class);
	
	/**
	 * 发送欠款客户已用额度信息到CRM外围系统
	 * 
	 * @author foss-zhangxiaohui
	 * @date Jan 15, 2013 2:14:00 PM
	 */
	@Override
	protected void doExecute(JobExecutionContext arg0)
			throws JobExecutionException {
		// 实例化查询最早欠款客户已用额度信息Service
		IDebtCreditLimitInfoQueryService debtCreditLimitInfoQueryService;
		try {
			//打印Job开始日志
			LOGGER.info("开始查询和发送最早欠款客户已用额度信息");
			
			//赋值给声明的Service实例
			debtCreditLimitInfoQueryService = getBean("debtCreditLimitInfoQueryService", IDebtCreditLimitInfoQueryService.class);
			
			//开始查询和发送最早欠款客户已用额度信息
			debtCreditLimitInfoQueryService.process();

			//打印Job日志
			LOGGER.info("查询和发送最早欠款客户已用额度信息完成");
		} 
		//捕获异常
		catch (Exception e) {
			//打印错误信息
			LOGGER.error("查询和发送最早欠款客户已用额度信息发生异常，异常信息：" + e.getMessage());
			throw new JobExecutionException("查询和发送最早欠款客户已用额度信息发生异常，异常信息：" + e.getMessage(), e);
		}
	}
}
