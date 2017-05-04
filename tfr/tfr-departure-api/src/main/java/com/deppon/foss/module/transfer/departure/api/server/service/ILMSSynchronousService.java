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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/server/service/ILMSSynchronousService.java
 *  
 *  FILE NAME          :ILMSSynchronousService.java
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
 * PROJECT NAME: tfr-gps-itf
 * PACKAGE NAME: com.deppon.foss.module.transfer.gps.server.ws
 * FILE    NAME: GPSToFOSSServiceImpl.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.departure.api.server.service;

import java.util.Date;


/**
 * Lms同步接口
 * 
 * @author 046130-foss-xuduowei
 * @date 2012-11-16 下午6:12:39
 */
public interface ILMSSynchronousService{

	/**
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-16 下午6:12:39
	 * @see com.deppon.foss.inteface.gps.FossGpsService#updateVehicleTrack(com.deppon.esb.inteface.domain.gps.VehiclePositionNotificationType)
	 */
	void lmsSynchronous(String vehicleNo, Date startDate,Date endDate,String departPlanType);

}