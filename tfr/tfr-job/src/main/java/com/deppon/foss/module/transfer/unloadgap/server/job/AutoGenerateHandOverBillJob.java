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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unloadgap/server/job/AutoGenerateHandOverBillJob.java
 *  
 *  FILE NAME          :AutoGenerateHandOverBillJob.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-unloadgap-job
 * PACKAGE NAME: com.deppon.module.transfer.unloadgap.server.job
 * FILE    NAME: AutoGenerateHandOverBill.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unloadgap.server.job;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.load.api.server.service.IAutoGenerateHandOverBillService;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;

/**
 * 自动生成交接单Job
 * @author dp-duyi
 * @date 2012-11-5 上午9:34:38
 */
public class AutoGenerateHandOverBillJob extends GridJob {
	private static final Logger logger = LoggerFactory.getLogger(AutoGenerateHandOverBillJob.class);
	/** 
	 * 自动生成交接单Job
	 * @author dp-duyi
	 * @date 2012-11-5 上午9:34:57
	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		logger.trace("************************ 开始执行生成交接单JOB ************************");
		IAutoGenerateHandOverBillService autoGenerateHandOverBillService = getBean("autoGenerateHandOverBillService", IAutoGenerateHandOverBillService.class);
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;
		
		try{
			threadNo = Integer.valueOf((String) context.getMergedJobDataMap().get("threadNo")).intValue();
			threadCount = Integer.valueOf((String) context.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = context.getScheduledFireTime();
			jobProcess = tfrCommonService.queryLastExecutedJobInfo(TfrJobBusinessTypeEnum.HANDOVER_BILL, scheduledFireTime, threadNo, threadCount);
			jobStartTime = new Date();
			Date queryBeginTime;
			try{
				queryBeginTime = new Date(jobProcess.getBizStartTime().getTime()-ConstantsNumberSonar.SONAR_NUMBER_5*ConstantsNumberSonar.SONAR_NUMBER_60*ConstantsNumberSonar.SONAR_NUMBER_1000);
			}catch (Exception e) {
				queryBeginTime = jobProcess.getBizStartTime();
			}
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS"); 
			logger.error("生成交接单时间:"+df.format(queryBeginTime)+"-"+df.format(scheduledFireTime));
			autoGenerateHandOverBillService.createHandOverBill(queryBeginTime, scheduledFireTime, threadCount, threadNo);
			jobEndTime = new Date();
			//更新本次job执行后的状态,出现异常时，不更新业务执行起止时间
			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(scheduledFireTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
		}catch(Exception e){
			logger.error("任务执行失败", e);
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
//			记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.HANDOVER_BILL.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.HANDOVER_BILL.getBizCode());
			jobProcessLogEntity.setRemark("任务执行失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		} finally{
			tfrCommonService.updateExecutedJob(jobProcess);
		}
		
		logger.trace("************************ 结束执行生成交接单JOB ************************");
	}

}