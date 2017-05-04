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
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/server/service/IQueryVehicleOilInfoService.java
 *  
 *  FILE NAME          :IQueryVehicleOilInfoService.java
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
 * PROJECT NAME: tfr-management-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.management.api.server.service
 * FILE    NAME: IQueryVehicleOilInfo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.management.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.management.api.shared.dto.VehicleOilInfoDto;

/**
 * 查询车辆油耗信息，供LMS调用
 * @author 046130-foss-xuduowei
 * @date 2012-12-27 下午2:21:18
 */
public interface IQueryVehicleOilInfoService extends IService {
	/**
	 * 
	 * 查询车辆油耗信息
	 * @param vehicleNo 车牌号
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-27 下午2:33:05
	 */
	List<VehicleOilInfoDto> queryVehicleOilInfo(String vehicleNo,Date beginDate,Date endDate);		
}