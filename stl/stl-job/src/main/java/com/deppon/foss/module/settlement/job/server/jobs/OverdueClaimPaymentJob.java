package com.deppon.foss.module.settlement.job.server.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.settlement.consumer.api.server.service.IOverdueClaimPaymentService;

/**
 * 发送超时理赔付款到QMS
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-7-28 上午11:29:11,content: </p>
 * @author 105762
 * @date 2014-7-28 上午11:29:11
 * @since 1.6
 * @version 1.0
 */
public class OverdueClaimPaymentJob extends GridJob {

	/**
	 * 声明日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OverdueClaimPaymentJob.class);

	/**
	 * <p>发送超时理赔付款到QMS</p>
	 * @author 105762
	 * @date 2014-7-28 下午2:18:54
	 * @param context
	 * @throws JobExecutionException
	 * @see GridJob#doExecute(JobExecutionContext)
	 */
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {

		LOGGER.info("发送超时理赔付款到QMS job 开始...");

		IOverdueClaimPaymentService overdueClaimPaymentService;

		// 获取实例
		overdueClaimPaymentService = getBean("overdueClaimPaymentService", IOverdueClaimPaymentService.class);

		// 执行
		try {
			overdueClaimPaymentService.process();
		} catch (ESBException e) {
			throw new JobExecutionException(e.getMessage());
		}
		LOGGER.info("发送超时理赔付款到QMS job 结束.");
	}
}
