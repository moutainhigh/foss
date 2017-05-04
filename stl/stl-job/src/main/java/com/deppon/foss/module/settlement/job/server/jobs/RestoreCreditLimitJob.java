package com.deppon.foss.module.settlement.job.server.jobs;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.settlement.common.api.server.service.ICreditMsgService;

/**
 * 恢复客户信用额度Job
 * 
 * @author foss-zhangxiaohui
 * @date Jan 21, 2013 10:57:28 AM
 */
public class RestoreCreditLimitJob extends GridJob {

	/**
	 * 声明日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(RestoreCreditLimitJob.class);
	
	/**
	 * 恢复客户信用额度Job
	 * 
	 * @author foss-zhangxiaohui
	 * @date Jan 21, 2013 10:59:31 AM
	 */
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		//实例化还原信用额度
		ICreditMsgService creditMsgService;
		try{
			//打印Job开始日志
			LOGGER.info("开始还原客户信用额度");

			// 给还原信用额度Service实例赋值
			creditMsgService = getBean("creditMsgService", ICreditMsgService.class);
			
			// 还原信用额度
			creditMsgService.batchUpdateCreditMsg(new Date());

			//打印Job日志
			LOGGER.info("还原信用额度服务完成");
		}
		//捕获异常
		catch (Exception e) {
			//打印错误信息
			LOGGER.error("还原信用额度发生异常，异常信息：" + e.getMessage());
			throw new JobExecutionException("还原信用额度发生异常，异常信息：" + e.getMessage(), e);
		}
	}
}
