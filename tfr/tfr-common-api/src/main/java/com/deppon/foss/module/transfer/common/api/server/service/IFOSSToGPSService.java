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
 *  PROJECT NAME  : tfr-common-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/server/service/IFOSSToGPSService.java
 *  
 *  FILE NAME          :IFOSSToGPSService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-gps-itf
 * PACKAGE NAME: com.deppon.foss.module.transfer.gps.server.service
 * FILE    NAME: FOSSToGPSService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.common.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.dto.TaskVehicleDto;

/**
 * 上传任务车辆信息至GPS
 * @author 046130-foss-xuduowei
 * @date 2012-11-16 下午5:29:46
 */
public interface IFOSSToGPSService extends IService{
	/**
	 * 
	 * 上传任务车辆信息
	 * @param taskVehicleDto 任务车辆信息
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-16 下午6:00:52
	 */
	boolean notifyTaskVehicleInfo(TaskVehicleDto taskVehicleDto);
	
	/**
	 * 
	 * 同步车辆任务给GPS
	 * @author alfred
	 * @date 2014-3-6 上午8:55:42
	 * @param taskVehicleDto
	 * @return
	 * @see
	 */
	boolean synchronousTaskVehicleInfo(TaskVehicleDto taskVehicleDto);
}