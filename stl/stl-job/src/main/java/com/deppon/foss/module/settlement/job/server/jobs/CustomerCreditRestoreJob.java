package com.deppon.foss.module.settlement.job.server.jobs;

import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.settlement.consumer.server.service.impl.CustomerCreditRestoreService;

/**
 * 客户额度还原服务
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-11-17 下午5:27:16
 */
public class CustomerCreditRestoreJob extends GridJob {

	private static final Logger LOGGER = LogManager
			.getLogger(CustomerCreditRestoreJob.class);

	/**
	 * 
	 * 定时还原
	 * 同步月结客户信息
	 * 同步部门最大临欠额度信息
	 * 
	 * 定时处理部门临时欠款是否超期
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 下午5:24:24
	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void doExecute(JobExecutionContext arg0)
			throws JobExecutionException {

		try {
			CustomerCreditRestoreService customerCreditRestoreService = getBean(
					"customerCreditRestoreService",
					CustomerCreditRestoreService.class);

			//当前日期
			Date current = new Date();
			//上次执行日期
			Date lastDate = customerCreditRestoreService.getLastExecuteTime(current);
			
			try {
				LOGGER.info("开始同步客户信息");

				// 同步客户信息
				customerCreditRestoreService.syncCustomer(lastDate,current);
				
				LOGGER.info("同步客户信息完成");
			} catch (Exception e) {
				LOGGER.error("同步客户信息发生异常，异常信息：" + e.getMessage(), e);
				throw new Exception(e);
			}

			try {
				LOGGER.info("开始同步组织信息");
				// 同步组织信息
				customerCreditRestoreService.syncOrgBusiness();
				LOGGER.info("同步组织信息完成");
			} catch (Exception e) {
				LOGGER.error("同步组织信息发生异常，异常信息：" + e.getMessage(), e);
				throw new Exception(e);
			}
			
			//定时处理部门临时欠款是否超期
			customerCreditRestoreService.restoreCredit(lastDate,current);
			
		} catch (Exception e) {
			LOGGER.error("客户额度还原service初始化失败：" + e.getMessage(), e);
			throw new JobExecutionException("客户额度还原service初始化失败：" + e.getMessage(), e);
		}

	}
}
