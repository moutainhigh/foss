/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-job
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/server/job/OrderReportJob.java
 *  
 *  FILE NAME          :OrderReportJob.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.ordertask;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.job.BaseStateFulJob;
import com.deppon.foss.module.transfer.unload.api.server.service.IOrderDifferReportService;

/**
 * 生成点单任务差异报告(包含对应的差异明细)
 * @author foss-189284
 * @Date 2016-1-19 上午8:29:41
 */
public class OrderReportJob extends BaseStateFulJob{
	
	/** 
	 * JOB定时执行生成对应的点单差异报告
	 * 1、通过上次JOB执行状态，确定本次业务起止处理时间段
	 * 2、按业务起止处理时间段，处理此时间段内某切片的点单任务
	 * 3、更新此JOB的执行时间和状态
	 * @author foss-189284
	 * @date 2016-1-19 上午8:29:41
	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.info("************************ 开始执行点单差异报告 JOB ************************");
		
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;
		IOrderDifferReportService orderDifferReportService = getBean("orderDifferReportService", IOrderDifferReportService.class);
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);
		try {
		//	threadNo = Integer.valueOf((String) context.getMergedJobDataMap().get("threadNo")).intValue();
			//threadCount = Integer.valueOf((String) context.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = context.getScheduledFireTime();
			
//			1、取出上次成功执行JOB后的业务截止时间
			jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.ORDER_REPORT, scheduledFireTime, threadNo, threadCount);
			
//			2、执行业务任务
			jobStartTime = Calendar.getInstance().getTime();
//			 2.1、创建点单任务差异报告及差异明细
//			 2.2、执行成功后，若存在明细都已处理完毕的点单差异报告，需更新这些点单报告状态为TransferConstants.STOCK_CHECKING_REPORT_DONE
			orderDifferReportService.addOrederReport(jobProcess.getBizEndTime(), scheduledFireTime, threadNo, threadCount);
			jobEndTime = Calendar.getInstance().getTime();
//			3、更新任务完成状态及时间
			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(scheduledFireTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
		} catch (Exception e) {
			LOGGER.error("OrderReportJob error", e);
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
//			记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.ST_REPORT.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.ST_REPORT.getBizCode());
			jobProcessLogEntity.setRemark("任务执行失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		} finally{
			tfrCommonService.updateExecutedJob(jobProcess);
		}
		
		LOGGER.info("************************ 结束执行点单差异报告 JOB ************************");
	}
}