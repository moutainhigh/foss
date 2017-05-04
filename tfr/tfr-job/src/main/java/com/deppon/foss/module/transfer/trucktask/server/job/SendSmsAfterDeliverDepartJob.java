/**  
 * Project Name:tfr-job  
 * File Name:SendSmsAfterDeliverDepartJob.java  
 * Package Name:com.deppon.foss.module.transfer.trucktask.server.job  
 * Date:2015年5月24日下午5:32:53  
 * Copyright (c) 2015, shiwei@outlook.com All Rights Reserved.  
 *  
*/  
  
package com.deppon.foss.module.transfer.trucktask.server.job;  

import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.departure.api.server.service.ISendSmsAfterDeliverDepartJobService;
import com.deppon.foss.module.transfer.job.BaseStateFulJob;

/**  
 * ClassName:SendSmsAfterDeliverDepartJob <br/>  
 * Reason:   派送放行后，给车载运单的收货客户发送短信. <br/>  
 * Date:     2015年5月24日 下午5:32:53 <br/>  
 * @author   shiwei-045923 shiwei@outlook.com  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
public class SendSmsAfterDeliverDepartJob extends BaseStateFulJob {

	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {

		LOGGER.error("************************ 开始***派送放行后发送短信JOB ************************");
		
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;
		ISendSmsAfterDeliverDepartJobService sendSmsAfterDeliverDepartJobService = getBean("sendSmsAfterDeliverDepartJobService", ISendSmsAfterDeliverDepartJobService.class);
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);
		
		try {
			threadNo = Integer.valueOf((String) context.getMergedJobDataMap().get("threadNo")).intValue();
			threadCount = Integer.valueOf((String) context.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = context.getScheduledFireTime();
			
			//1、取出上次成功执行JOB后的业务截止时间
			jobProcess = tfrCommonService.queryLastExecutedJobInfo(TfrJobBusinessTypeEnum.SEND_VEHICLESTATUS_2TPS_JOB, scheduledFireTime, threadNo, threadCount);
			
			//2、执行业务任务，读取运单、发送短信
			jobStartTime = Calendar.getInstance().getTime();
			sendSmsAfterDeliverDepartJobService.SendSmsAfterDeliverDepart();
			jobEndTime = Calendar.getInstance().getTime();
			//3、更新任务完成状态及时间
			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(scheduledFireTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
		} catch (Exception e) {
			//已在ISendSmsAfterDeliverDepartJobService中针对运单记录异常日志，此处不再记录日志，只更新jobStatus
			LOGGER.error(e.getMessage());
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
		} finally{
			tfrCommonService.updateExecutedJob(jobProcess);
		}
		
		LOGGER.error("************************ 结束***派送放行后发送短信JOB ************************");
	}

}
  
