package com.deppon.foss.module.settlement.job.server.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillAutoPayPtpService;
/***
 * <1>
 * @clasaName:com.deppon.foss.module.pickup.sign.server.job
 * @author: 231438-foss-cjy
 * @description: 1.	合伙人到付运费第一次推送处理失败的重新推送Job。
 *   2.失败的每天1:00、3:00、5:00、7:00重推
 * @date:2014年7月14日 下午2:27:21
 */
public class BillPaybleAutoPayPushAginJob extends GridJob {
	private static final Logger LOGGER = LoggerFactory.getLogger(BillPaybleAutoPayPushAginJob.class);
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		//FOSS到PTP自动付款服务类
		IBillAutoPayPtpService billautoPayPtpService;
		try{
			LOGGER.info("---------------------合伙人到付运费自动付款重推JOB开始：-----------------------------");
			//自动付款推送业务逻辑实现类
			billautoPayPtpService = getBean("billAutoPayPtpService",IBillAutoPayPtpService.class);
			//启动重推业务实现类入口
			billautoPayPtpService.autoPaytoPtpPushAgin();
			LOGGER.info("---------------------合伙人到付运费自动付款重推JOB结束：-----------------------------");
		}catch(Exception e){
			LOGGER.error("error", e.getMessage());
			throw new JobExecutionException("合伙人到付运费自动付款重推异常:"+e.getMessage());
		}

	}

}
