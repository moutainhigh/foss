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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/server/service/IFOSSToLMSService.java
 *  
 *  FILE NAME          :IFOSSToLMSService.java
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
 *  
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-common-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.common.api.server.service
 * FILE    NAME: IFOSSToLMSService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.common.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.dto.LMSVehicleStateDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.LMSVehicleTravelDataDto;

/**
 * FOSS提供车辆状态数据和行驶数据给LMS
 * @author 046130-foss-xuduowei
 * @date 2012-12-4 下午3:13:27
 */
public interface IFOSSToLMSService extends IService {
	
	/**
	 * 
	 * 更新车辆状态数据
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-4 下午3:14:29
	 */
	void updateVehicleState(LMSVehicleStateDto lmsVehicleDto);
	
	/**
	 * 
	 * 提供行驶数据
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-5 上午10:12:11
	 */
	void updateVehicleTravelData(LMSVehicleTravelDataDto lmsVehicleTravelDataDto);
	
}