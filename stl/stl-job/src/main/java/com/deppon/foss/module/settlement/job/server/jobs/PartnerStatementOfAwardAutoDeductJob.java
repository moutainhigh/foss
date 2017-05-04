package com.deppon.foss.module.settlement.job.server.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.settlement.pay.api.server.service.IPartnerStatementOfAwardAutoDeductService;

/**
 * <1>
 * @clasaName:com.deppon.foss.module.settlement.job.server.jobs.PartnerStatementOfAwardAutoDeductJob.java
 * @author: 367752
 * @description: 1.	系统每天24:00点筛选抓取记账日期：前三天（含当天）在00:00~23:59生成且有效
 * 的“合伙人奖罚应收单、合伙人差错应收单、合伙人培训会务应收单、合伙人奖励应付单、合伙人快递差错应付单”单据
 *   2.抓取数据后，系统自动制作对账单。将相同的“部门名称”和 “合伙人编码”的财务单据：应收单、应付单，进行应收应付单自动核销（应收冲应付）；
 *   3.应收应付单自动核销后，当单据的应收金额大于应付金额时，则自动触发批量扣款
 * @date:2016年9月1日 下午2:27:21
 */
public class PartnerStatementOfAwardAutoDeductJob extends GridJob {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PartnerStatementOfAwardAutoDeductJob.class);
	
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		
		IPartnerStatementOfAwardAutoDeductService partnerStatementOfAwardAutoDeductService;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			//打印Job开始日志
			LOGGER.info("合伙人奖罚自动扣款开始,开始时间是：" + sdf.format(new Date()));
			
			partnerStatementOfAwardAutoDeductService = getBean("partnerStatementOfAwardAutoDeductService",IPartnerStatementOfAwardAutoDeductService.class);
			partnerStatementOfAwardAutoDeductService.autoDeductPartnerStatementOfAward();
			
			LOGGER.info("合伙人奖罚自动扣款结束,结束时间是："+ sdf.format(new Date()));
		}catch(Exception e){
			throw new JobExecutionException(e.getMessage());
		}

	}

}
