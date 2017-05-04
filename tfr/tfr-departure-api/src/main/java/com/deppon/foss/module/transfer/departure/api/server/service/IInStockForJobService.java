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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/server/service/IGpsService.java
 *  
 *  FILE NAME          :IGpsService.java
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

import java.util.List;

import com.deppon.foss.module.transfer.departure.api.shared.dto.WayBillRefershDTO;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;

/**
 * 卸车入库service封装类
 * @author 046130-foss-liubinbin
 * @date 2012-11-16 下午6:12:39
 */
public interface IInStockForJobService{

	/**
	 * 卸车入库
	 * @date 2012-11-16 下午6:12:39
	 * @see com.deppon.foss.inteface.gps.FossGpsService#notifyArrivaltime(com.deppon.esb.inteface.domain.gps.NotifyArrivaltimeRequestType)
	 */
	void refreshInStorage(List<WayBillRefershDTO> waybilllist,LoaderParticipationEntity loaderParticipationEntity);

}