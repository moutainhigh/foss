package com.deppon.foss.module.settlement.job.server.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.settlement.pay.api.server.service.ISyncIncomeRptCH;

/**
 * 同步现金缴款报表到财务自助
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-7-28 上午11:29:11,content: </p>
 * @author 105762
 * @date 2014-7-28 上午11:29:11
 * @since 1.6
 * @version 1.0
 */
public class SyncIncomeRptCHJob extends GridJob {
	{
		System.out.println("init...");
	}

	/**
	 * 声明日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SyncIncomeRptCHJob.class);

	/** 
	 * <p>同步现金缴款报表到财务自助</p> 
	 * @author 105762
	 * @date 2014-7-28 下午2:18:54
	 * @param context
	 * @throws JobExecutionException 
	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {

		LOGGER.info("同步现金缴款报表到财务自助 job 开始...");

		ISyncIncomeRptCH syncIncomeRptCH;

		// 获取实例
		syncIncomeRptCH = getBean("syncIncomeRptCH", ISyncIncomeRptCH.class);

		// 执行
		try {
			syncIncomeRptCH.process();
		} catch (ESBException e) {
			throw new JobExecutionException(e.getMessage());
		}
		LOGGER.info("同步现金缴款报表到财务自助 job 结束.");
	}
}
