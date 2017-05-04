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
package com.deppon.foss.module.transfer.vehicleseal.server.job;

import java.util.Calendar;
import java.util.Date;

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
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleSealService;

/**
 * 封签差错上报, 更新封签差错中的查错号
 * @author zhangyixin
 * @date 2013-05-16 下午4:16:57
 */
public class VehicleSealReportOAErrorJob extends BaseStateFulJob{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VehicleSealReportOAErrorJob.class);
	private static final String JOB_FAILURE_REMARK = "任务执行失败!";

	/** 
	 * JOB定时执行生成OA差错单，并更新差错单号到封签中
	 * 1、通过业务规则中定义的处理时间查询某一时间段内需处理的封签差异明细记录
	 * 2、上报OA的封签差错明细
	 * 3、更新此JOB的执行时间和状态
	 * @author zhangyixin
	 * @date 2013-05-16 下午4:16:57
	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.info("************************ 开始执行OA封签差错单 JOB ************************");

		//任务执行实体
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		//开始时间
		Date jobStartTime = null;
		//结束时间
		Date jobEndTime = null;
		//线程号
		int threadNo = 0;
		//线程数
		int threadCount = 1;
		//车辆封签
		IVehicleSealService vehicleSealService = getBean("vehicleSealService", IVehicleSealService.class);
		//提供中转模块中的通用服务
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);

		try {
			//获取线程号
			threadNo = Integer.valueOf((String) context.getMergedJobDataMap().get("threadNo")).intValue();
			//获取线程数
			threadCount = Integer.valueOf((String) context.getMergedJobDataMap().get("threadCount")).intValue();
			//获取
			Date scheduledFireTime = context.getScheduledFireTime();

			//取出上次成功执行JOB后的业务截止时间
			jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.LOAD_VEHICLE_SEAL_DATA_JOB, scheduledFireTime, threadNo, threadCount);

			//初始化开始时间
			jobStartTime = Calendar.getInstance().getTime();
			//OA封签差错单
			vehicleSealService.autoReportSlipError();
			//初始化结束时间
			jobEndTime = Calendar.getInstance().getTime();
			// 更新任务完成状态及时间
			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(scheduledFireTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
			// 记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.LOAD_VEHICLE_SEAL_DATA_JOB.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.LOAD_VEHICLE_SEAL_DATA_JOB.getBizCode());
			jobProcessLogEntity.setRemark(JOB_FAILURE_REMARK);
			jobProcessLogEntity.setExceptionInfo(e.getMessage());
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());

			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		} finally {
			tfrCommonService.updateExecutedJob(jobProcess);
		}
		
		LOGGER.info("************************ 结束执行OA封签差错单 JOB ************************");
	}
}