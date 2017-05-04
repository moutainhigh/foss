package com.deppon.foss.module.settlement.job.server.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillAutoPayPtpService;

/**
 * @clasaName:com.deppon.foss.module.pickup.sign.server.jobs.PtpRewardAutoPayJob
 * @author 355019
 * @description:1.FOSS系统每天01：00和12:00点筛选抓取记账日期：前三天（不含当天）
 * 				在00:00~23:59生成且有效应付单单据：合伙人奖励应付、合伙人快递差错应付
 * 				1）	合伙人奖励应付：将抓取后的应付单，按应付单相同的"部门编码"和"合伙人编码"进行打包，系统生成一条付款单；
 *				2）	合伙人快递差错应付：将抓取后的应付单，按应付单相同的"部门编码"和"合伙人编码"进行打包，系统生成一条付款单；
 *              2.抓取失败单据,系统在每天01：00和12:00点,重新抓取,打包生成付款单；
 * @date:2017年3月10日 下午15:00:21
 */
public class PtpRewardAutoPayJob extends GridJob {
	private static final Logger LOGGER = LoggerFactory.getLogger(PtpRewardAutoPayJob.class);
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		//FOSS到PTP自动付款服务类
		IBillAutoPayPtpService billautoPayPtpService;
		try{
			LOGGER.info("---------------------合伙人奖励自动返JOB开始：-----------------------------");
			//TODO  自动付款推送业务逻辑实现类
			billautoPayPtpService = getBean("billAutoPayPtpService",IBillAutoPayPtpService.class);
			//启动业务实现类入口
			billautoPayPtpService.autoPaytoPtpReward();
			LOGGER.info("---------------------合伙人奖励自动返JOB结束：-----------------------------");
		}catch(Exception e){
			LOGGER.error("error", e.getMessage());
			throw new JobExecutionException("合伙人奖励自动返异常:"+e.getMessage());
		}
	}

}
