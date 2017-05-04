/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-job
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/orderpreprocess/server/job/VehicleActualSituationClearJob.java
 * 
 * FILE NAME        	: VehicleActualSituationClearJob.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.orderpreprocess.server.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.pickup.order.api.server.service.IVehicleManageService;


/**
 * 车载信息清空job
 * @author 038590-foss-wanghui
 * @date 2012-12-24 下午5:28:58
 */
public class VehicleActualSituationClearJob extends GridJob {

	private static final Logger LOGGER = LoggerFactory.getLogger(VehicleActualSituationClearJob.class);
	/**
	 * 
	 * 定时清空车载信息（0：00）
	 * @param 
	 * @author 038590-foss-wanghui
	 * @date 2012-11-17 下午5:55:37
	 */
	@Override
	protected void doExecute(JobExecutionContext arg0)
			throws JobExecutionException {
		try {
			// 获得车辆管理的服务
			IVehicleManageService vehicleManageService = getBean("vehicleManageService", IVehicleManageService.class);
			LOGGER.info("清空车载信息Job启动");
			// 调用清空接口清空车载信息
			vehicleManageService.emptyVehicle();
			LOGGER.info("清空车载信息Job结束");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new JobExecutionException(e.getMessage(),e);
		}
	}
	
}