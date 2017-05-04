package com.deppon.foss.module.settlement.job.server.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillAutoPayPtpService;

/**
 * <1>
 * @clasaName:com.deppon.foss.module.pickup.sign.server.job
 * @author: 231438-foss-cjy
 * @description: 1.	筛选前一天【签收时间】在00:00-23:59之间已生效且未支付的“合伙人到付运费应付”单据，
 *   符合条件的 foss系统自动发起生成对应的“付款单”，推送给ptp充值、生成预收单，充值成功的核销付款单。
 *   2.失败的每天1:00、3:00、5:00、7:00重推
 * @date:2014年7月14日 下午2:27:21
 */
public class BillPayableAutoPayJob extends GridJob {
	private static final Logger LOGGER = LoggerFactory.getLogger(BillPayableAutoPayJob.class);
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		//FOSS到PTP自动付款服务类
		IBillAutoPayPtpService billautoPayPtpService;
		try{
			LOGGER.info("---------------------合伙人到付运费自动付款JOB开始：-----------------------------");
			//TODO  自动付款推送业务逻辑实现类
			billautoPayPtpService = getBean("billAutoPayPtpService",IBillAutoPayPtpService.class);
			//启动业务实现类入口
			billautoPayPtpService.autoPaytoPtp();
			LOGGER.info("---------------------合伙人到付运费自动付款JOB结束：-----------------------------");
		}catch(Exception e){
			LOGGER.error("error", e.getMessage());
			throw new JobExecutionException("合伙人到付运费自动付款异常:"+e.getMessage());
		}
		try{			
			LOGGER.info("---------------------合伙人委托派费自动付款JOB开始：-----------------------------");
			//TODO  自动付款推送业务逻辑实现类
			billautoPayPtpService = getBean("billAutoPayPtpService",IBillAutoPayPtpService.class);
			//启动业务实现类入口
			billautoPayPtpService.autoPaytoPtpPDDF();
			LOGGER.info("---------------------合伙人委托派费自动付款JOB结束：-----------------------------");
		}catch(Exception e){
			LOGGER.error("error", e.getMessage());
			throw new JobExecutionException("合伙人委托派费自动付款异常:"+e.getMessage());
		}
	
	}

}
