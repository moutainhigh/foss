package com.deppon.foss.module.settlement.job.server.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.settlement.pay.api.server.service.IPartnerStatementOfAwardAutoDeductService;
import com.deppon.foss.module.settlement.pay.api.server.service.IPartnerStatementOfAwardReDeductService;

/**
 * <1>
 * @clasaName:com.deppon.foss.module.settlement.job.server.jobs.PartnerStatementOfAwardReDeductJob.java
 * @author: 367752
 * @description: 1.奖罚对账单扣款失败后，对生成的对账单进行修复，让后重推对账单。
 * 2.重推时间是每天的2:00,下午2:00。
 * @date:2016年10月14日 
 */
public class PartnerStatementOfAwardReDeductJob extends GridJob {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PartnerStatementOfAwardReDeductJob.class);
	
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		
		IPartnerStatementOfAwardReDeductService partnerStatementOfAwardReDeductService;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//打印Job开始日志
			LOGGER.info("合伙人奖罚扣款对账单重推开始,开始时间是：" + sdf.format(new Date()));
			
			partnerStatementOfAwardReDeductService = getBean("partnerStatementOfAwardReDeductService",IPartnerStatementOfAwardReDeductService.class);
			partnerStatementOfAwardReDeductService.reDeductPartnerStatementOfAward();
			
			LOGGER.info("合伙人奖罚扣款对账单重推结束,结束时间是："+ sdf.format(new Date()));
		}catch(Exception e){
			throw new JobExecutionException(e.getMessage());
		}

	}

}
