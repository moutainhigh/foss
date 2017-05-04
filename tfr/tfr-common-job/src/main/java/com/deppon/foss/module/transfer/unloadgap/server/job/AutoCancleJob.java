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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unloadgap/server/job/AutoCancleJob.java
 *  
 *  FILE NAME          :AutoCancleJob.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unloadgap.server.job;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.departure.api.server.service.ISharedService;

/**
 * “待放行”状态的车辆n分钟（默认30分钟可配置）内放行有效，超过时间值则变为已失效状态
 * @author foss-liubinbin
 * @date 2012-11-27 下午2:57:25
 * 
 * @updated foss-wuyingjie
 * @date 2013-1-24 下午3:50:04
 */
public class AutoCancleJob extends GridJob implements StatefulJob{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(AutoCancleJob.class);
	
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.trace("************************ 开始执行车辆放行 JOB ************************");
		
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;
		ISharedService sharedService = getBean("sharedService",	ISharedService.class);
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);
		
		try {
			Date scheduledFireTime = context.getScheduledFireTime();
			
//			1、取出上次成功执行JOB后的业务截止时间，此业务无需分片
			jobProcess = tfrCommonService.queryLastExecutedJobInfo(TfrJobBusinessTypeEnum.CANCEL_DEPARTURE, scheduledFireTime, threadNo, threadCount);
			
//			2、执行业务任务
			jobStartTime = Calendar.getInstance().getTime();
			sharedService.autoCancle();
			jobEndTime = Calendar.getInstance().getTime();
//			3、更新任务完成状态及时间
			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(scheduledFireTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
		} catch (Exception e) {
			LOGGER.error("AutoCancelJob error", e);
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
//			记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.CANCEL_DEPARTURE.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.CANCEL_DEPARTURE.getBizCode());
			jobProcessLogEntity.setRemark("任务执行失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		} finally{
			tfrCommonService.updateExecutedJob(jobProcess);
		}
		
		LOGGER.trace("************************ 结束执行车辆放行 JOB ************************");
	}
}