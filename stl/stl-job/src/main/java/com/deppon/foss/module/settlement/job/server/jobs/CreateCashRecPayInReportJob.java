package com.deppon.foss.module.settlement.job.server.jobs;

import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.settlement.pay.api.server.service.IReportCashRecPayInService;
import com.deppon.foss.util.DateUtils;


/**
 * 生成现金缴款报表
 * @author 088933-foss-zhangjiheng
 * @date 2012-11-19 下午2:29:17
 */
public class CreateCashRecPayInReportJob extends GridJob implements StatefulJob {
    //日志
	private static final Logger LOGGER = LogManager.getLogger(CreateCashRecPayInReportJob.class);
	//截止当前时间的统计日期间隔，-1代表前一天
	private static final int DATE_NUM=-1;
	
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		//现金收入报表服务
		IReportCashRecPayInService reportCashRecPayInService;
		try {
			LOGGER.info("生成缴款报表处理开始...");
			reportCashRecPayInService = getBean("reportCashRecPayInService",
					IReportCashRecPayInService.class);
			//结束日期为当前日期的00:00:00
			Date endDate=DateUtils.getStartDatetime(new Date());
			//开始日期为前一天的00:00:00
			Date beginDate=DateUtils.addDayToDate(endDate, DATE_NUM);
			reportCashRecPayInService.createAllReportCashRecPayIn(beginDate, endDate);
			LOGGER.info("生成缴款报表处理结束...");
		} catch (Exception e) {
			LOGGER.error("生成缴款报表失败：" + e.getMessage(), e);
			throw new JobExecutionException("生成缴款报表失败：" + e.getMessage(), e);
		}
		
	}

}
