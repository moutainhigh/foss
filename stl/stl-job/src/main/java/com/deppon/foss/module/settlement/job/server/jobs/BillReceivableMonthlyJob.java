/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.settlement.job.server.jobs;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillReceivableMonthlyQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillReceivableDto;

/**
 * 每月1号0:00:00定时生成上月一整个月的月结提成金额
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:273272,date:2016-2-17 上午8:18:26,</p>
 * @author 273272 唐俊
 * @date 2016-2-17 上午8:18:26
 * @since
 * @version
 */
public class BillReceivableMonthlyJob extends GridJob implements StatefulJob{

	//日志
	private static final Logger LOG = LogManager.getLogger(BillReceivableMonthlyJob.class);
	/** 
	 * <p>每月1号0:00:00定时生成上月一整个月的月结提成金额</p> 
	 * @author 273272 唐俊
	 * @date 2016-2-17 上午8:18:48
	 * @param context
	 * @throws JobExecutionException 
	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		IBillReceivableMonthlyQueryService billReceivableMonthlyQueryService;
		LOG.info("合伙人月结报表的数据生成开始.........");
		billReceivableMonthlyQueryService = getBean("billReceivableMonthlyQueryService",
				IBillReceivableMonthlyQueryService.class);
		BillReceivableDto billReceivableDto = new BillReceivableDto();
		//获取当前时间设置为查询的结束时间
		Date date = new Date();
		billReceivableDto.setEndDate(date);
		//将当前时间减一个月设置为查询的开始时间
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		billReceivableDto.setStartDate(cal.getTime());
		LOG.info("合伙人月结报表的数据查询开始.........");
		List<BillReceivableEntity> list = billReceivableMonthlyQueryService.queryBillReceivableByData(billReceivableDto);
		LOG.info("合伙人月结报表的数据查询结束.........");
		//批量插入数据库
		LOG.info("合伙人月结报表的数据插入开始.........");
		billReceivableMonthlyQueryService.insertBillReceivable(list);
		LOG.info("合伙人月结报表的数据插入结束.........");
		LOG.info("合伙人月结报表的数据生成结束.........");
	}

}
