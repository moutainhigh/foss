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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unloadgap/server/job/CreateLoaderWorkloadJob.java
 *  
 *  FILE NAME          :CreateLoaderWorkloadJob.java
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
 * PROJECT NAME: tfr-job
 * PACKAGE NAME: com.deppon.foss.module.transfer.unloadgap.server.job
 * FILE    NAME: CreateLoaderWorkloadJob.java
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

import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.job.BaseStateFulJob;
import com.deppon.foss.module.transfer.load.api.server.service.ICreateLoaderWorkloadService;

/**
 * 生成理货员装卸车工作量
 * @author dp-duyi
 * @date 2012-12-25 上午10:06:53
 */
public class CreateLoaderWorkloadJob extends BaseStateFulJob  {
	private final static Logger LOGGER = LoggerFactory.getLogger(CreateLoaderWorkloadJob.class);
	
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.trace("************************ 开始执行生成装卸车工作量JOB ************************");
		ICreateLoaderWorkloadService createLoaderWorkloadService = getBean("createLoaderWorkloadService", ICreateLoaderWorkloadService.class);
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);
		try {
			threadNo = Integer.valueOf((String) context.getMergedJobDataMap().get("threadNo")).intValue();
			threadCount = Integer.valueOf((String) context.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = context.getScheduledFireTime();
			
//			1、取出上次成功执行JOB后的业务截止时间
			jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.LOADER_WORKLOAD, scheduledFireTime, threadNo, threadCount);
			
//			2、执行业务任务
			jobStartTime = Calendar.getInstance().getTime();
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS"); 
			LOGGER.error("生成装卸车工作量时间:"+df.format(jobProcess.getBizStartTime())+"-"+df.format(scheduledFireTime));
			createLoaderWorkloadService.createLoaderWorkLoad(jobProcess.getBizStartTime(), scheduledFireTime, threadNo, threadCount);
			jobEndTime = Calendar.getInstance().getTime();
//			3、更新任务完成状态及时间
			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(scheduledFireTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
		} catch (Exception e) {
			LOGGER.error("CreateLoaderWorkloadJob error", e);
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
//			记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.LOADER_WORKLOAD.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.LOADER_WORKLOAD.getBizCode());
			jobProcessLogEntity.setRemark("任务执行失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		} finally{
			tfrCommonService.updateExecutedJob(jobProcess);
		}
		
		LOGGER.trace("************************ 结束生成装卸车工作量JOB ************************");
	}
}