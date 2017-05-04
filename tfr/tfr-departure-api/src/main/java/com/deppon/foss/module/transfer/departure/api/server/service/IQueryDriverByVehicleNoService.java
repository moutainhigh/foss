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
 *  PROJECT NAME  : tfr-departure-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/server/service/IQueryDriverByVehicleNoService.java
 *  
 *  FILE NAME          :IQueryDriverByVehicleNoService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.departure.api.shared.domain.RelationInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.dto.VehicleNoInfoDTO;
/**
 * 
 * 制定一些共用的处理业务的方法，包括车辆任务绑定表的操作，定时任务
 * @author foss-liubinbin(for IBM)
 * @date 2012-11-2 上午10:23:10
 */
public interface IQueryDriverByVehicleNoService extends IService{
	
	/**
	 * 
	 * 通过车牌号查找司机信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-24 上午11:04:20
	 */
	RelationInfoEntity  queryDriverInfoByVehicleNo(VehicleNoInfoDTO vehicleNoInfoDTO);
	/**
	 * 
	 * 通过车牌号查找司机信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-24 上午11:04:20
	 */
	RelationInfoEntity  queryDriverInfoByVehicleNo(String vehicleNo);

	/**
	 * 
	 * 通过车牌号查找司机信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-24 上午11:04:20
	 */
	RelationInfoEntity  queryDriverInfoByVehicleNo(String vehicleNo, String id);
	
	
}