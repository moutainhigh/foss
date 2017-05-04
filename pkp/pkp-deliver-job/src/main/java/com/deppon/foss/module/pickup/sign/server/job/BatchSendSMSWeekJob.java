package com.deppon.foss.module.pickup.sign.server.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.pickup.sign.api.server.service.IBatchSendSMSJobService;

/***
 * <3>
 * @clasaName:com.deppon.foss.module.pickup.sign.server.job
 * @author: yuting@163.com
 * @description: (快递订单调度受理短信)、（快递签收发件人短信）、（签收单返单短信），次日向客户发送批量打包短信<br>
 * 				 是否批量发送  取决于crm端 发送的状态数据   [0,1]	<br>  
 * 				每周发送一次，发送时间为每周一12:00
 * @date:2014年7月15日 下午5:21:21
 */
public class BatchSendSMSWeekJob extends GridJob {
	private static final Logger LOGGER = LoggerFactory.getLogger(BatchSendSMSWeekJob.class);
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException
	{
		try {
			LOGGER.info("3>周次批量发送短信 start");
			IBatchSendSMSJobService batchSendExpressJobService = getBean("batchSendSMSWeekJobService", IBatchSendSMSJobService.class);
			batchSendExpressJobService.processBatchSendExpress();
			LOGGER.info("3>周次批量发送短信  end");
		} catch (Exception e) {
			LOGGER.error("error", e);
			throw new JobExecutionException("周次批量发送短信异常",e);
		}
	}

}
