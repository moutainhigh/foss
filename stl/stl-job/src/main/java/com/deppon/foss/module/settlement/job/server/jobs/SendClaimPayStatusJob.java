package com.deppon.foss.module.settlement.job.server.jobs;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.settlement.closing.api.server.service.IClaimStatusMsgProcessService;

/**
 * 
 * 发送理赔应付单支付状态到CRM定时任务
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-12-4 下午3:03:20
 */
public class SendClaimPayStatusJob extends GridJob {

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LogManager.getLogger(SendClaimPayStatusJob.class);
	

	/**
	 * 
	 * JOB执行任务
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-17 下午7:24:58
	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {

		LOGGER.info("开始发送理赔应付单付款状态到CRM.");
		
		// 理赔支付消息处理服务
		IClaimStatusMsgProcessService claimStatusMsgProcessService;

		try {

			// 获取理赔支付消息处理服务
			claimStatusMsgProcessService = getBean(
					"claimStatusMsgProcessService",
					IClaimStatusMsgProcessService.class);

			// 发送支付状态信息
			claimStatusMsgProcessService.sendPaymentMsg();

		} catch (Exception e) {

			LOGGER.error("发送坏账消息出现异常：" + e.getMessage(), e);
			throw new JobExecutionException("发送坏账消息出现异常：" + e.getMessage(), e);

		}

		LOGGER.info("结束发送理赔应付单付款状态到CRM.");
	}
}
