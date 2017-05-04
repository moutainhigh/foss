package com.deppon.foss.module.settlement.job.server.jobs;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.settlement.common.api.server.service.IOrgMonthIncomeService;

/**
 * 每月初，定时生成上月部门收入记录Job
 * 
 * @author 099995-foss-wujiangtao
 * @date 2013-2-18 下午5:57:30
 * @since
 * @version
 */
public class OrgMonthInComeJob  extends GridJob implements StatefulJob{
	private static final Logger LOGGER = LogManager.getLogger(OrgMonthInComeJob.class);
	
	/**
	 * 每月初，定时生成上月部门收入记录Job
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-18 下午5:58:45
	 * @param context
	 * @throws JobExecutionException
	 */
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		IOrgMonthIncomeService orgMonthIncomeService;
		try{
			LOGGER.info("开始执行部门收入记录Service");
			orgMonthIncomeService= getBean("orgMonthIncomeService",IOrgMonthIncomeService.class);
			orgMonthIncomeService.sumStilBillToOrgMonthIncome();
			LOGGER.info("END 执行部门收入记录Service");
		}catch(Exception e){
			LOGGER.error("生成上月部门收入发生异常，异常信息：" + e.getMessage(),e);
			throw new JobExecutionException("生成上月部门收入发生异常，异常信息：" + e.getMessage(), e);
		}
	}

}
