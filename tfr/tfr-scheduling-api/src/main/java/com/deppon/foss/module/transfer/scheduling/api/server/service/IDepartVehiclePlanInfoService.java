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
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/service/IInviteVehicleService.java
 * 
 *  FILE NAME     :IInviteVehicleService.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.server.service
 * FILE    NAME: IInviteVehicleService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.DepartVehiclePlanInfoQueryParmEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.DepartPlanInfoDetailDto;

/**
 * 
* @description 获取计划发车信息Service
* @version 1.0
* @author 332209-foss-ruilibao
* @update 2016-5-6 下午2:48:06
 */
public interface IDepartVehiclePlanInfoService extends IService {
	
	/**
	 * 
	* @description 获取计划发车信息接口定义
	* @param entity 获取发车计划信息查询参数实体
	* @return 发车计划信息实体
	* @version 1.0
	* @author 332209-foss-ruilibao
	* @update 2016-5-6 下午2:48:31
	 */
	DepartPlanInfoDetailDto queryDepartVehiclePlanInfo(DepartVehiclePlanInfoQueryParmEntity entity);
}