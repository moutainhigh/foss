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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/server/job/StReportOAErrorJob.java
 *  
 *  FILE NAME          :StReportOAErrorJob.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.server.job;

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
import com.deppon.foss.module.transfer.stockchecking.api.server.service.IStReportService;

/**
 * 生成差错单，并更新清仓差异报告明细中的差错单单号
 * @author foss-wuyingjie
 * @date 2012-12-05 下午4:16:57
 */
public class StReportOAErrorJob extends BaseStateFulJob{
	/** 
	 * JOB定时执行生成OA差错单，并更新差错单号到清仓差异明细中
	 * 1、通过业务规则中定义的处理时间查询某一时间段内需处理的清仓差异明细记录
	 * 2、上报OA的清仓明细
	 * 3、更新此JOB的执行时间和状态
	 * @author foss-wuyingjie
	 * @date 2012-11-27 下午4:18:25
	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.info("************************ 开始执行OA差错单 JOB ************************");
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;
		IStReportService stReportService = getBean("stReportService", IStReportService.class);
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);
		
		try {
			threadNo = Integer.valueOf((String) context.getMergedJobDataMap().get("threadNo")).intValue();
			threadCount = Integer.valueOf((String) context.getMergedJobDataMap().get("threadCount")).intValue();
			
			Date scheduledFireTime = context.getScheduledFireTime();
			
//			1、取出上次成功执行JOB后的业务截止时间
			jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.ST_OA_ERROR, scheduledFireTime, threadNo, threadCount);
//			2、执行业务任务：创建清仓任务差异报告及差异明细
			jobStartTime = Calendar.getInstance().getTime();
			stReportService.executeStOAErrorTask(scheduledFireTime, threadNo, threadCount);
			jobEndTime = Calendar.getInstance().getTime();
//			3、更新任务完成状态及时间
			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(scheduledFireTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
		} catch (Exception e) {
			LOGGER.error("StReportOAErrorJob error", e);
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
//			记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.ST_OA_ERROR.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.ST_OA_ERROR.getBizCode());
			jobProcessLogEntity.setRemark("任务执行失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		} finally{
			tfrCommonService.updateExecutedJob(jobProcess);
		}
		
		LOGGER.info("************************ 结束执行OA差错单 JOB ************************");
	}
}