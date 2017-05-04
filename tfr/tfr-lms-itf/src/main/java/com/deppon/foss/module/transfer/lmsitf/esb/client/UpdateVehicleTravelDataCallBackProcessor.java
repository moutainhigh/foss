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
 *  PROJECT NAME  : tfr-lms-itf
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/lmsitf/esb/client/UpdateVehicleTravelDataCallBackProcessor.java
 *  
 *  FILE NAME          :UpdateVehicleTravelDataCallBackProcessor.java
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
 * PROJECT NAME: tfr-lms-itf
 * PACKAGE NAME: com.deppon.foss.module.transfer.lmsitf.esb.client
 * FILE    NAME: UpdateVehicleTravelDataCallBackProcessor.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.lmsitf.esb.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.inteface.domain.vehicle.CarRunDisposeResult;
import com.deppon.esb.inteface.domain.vehicle.CarRuncationResponse;

/**
 * 中转调用LMS更新行驶数据接口的回调处理类
 * @author 046130-foss-xuduowei
 * @date 2012-12-11 上午9:16:34
 */
public class UpdateVehicleTravelDataCallBackProcessor implements
		ICallBackProcess {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateVehicleTravelDataCallBackProcessor.class);
	//错误标志，用于查找日志
	private static final String LMS_UPDATE_VEHICLE_TRAVEL_CALL_BACK = "LMS_UPDATE_VEHICLE_TRAVEL_CALL_BACK:";
	/** 
	 * 回调处理
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-11 上午9:16:34
	 * @see com.deppon.esb.core.process.ICallBackProcess#callback(java.lang.Object)
	 */
	@Override
	public void callback(Object response) throws ESBException {
		if(response!=null){
			CarRuncationResponse carResponse = (CarRuncationResponse)response;
			CarRunDisposeResult carResult = carResponse.getCarRunResult();
			LOGGER.info("唯一识别号："+carResult.getSoleNumber());
			LOGGER.info("原因："+carResult.getReason());
			LOGGER.info("是否成功："+carResult.isResult());
		}else{
			LOGGER.debug("response: null");
		}

	}

	/** 
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-11 上午9:16:34
	 * @see com.deppon.esb.core.process.ICallBackProcess#errorHandler(java.lang.Object)
	 */
	@Override
	public void errorHandler(Object errorResponse) throws ESBException {
		// 无操作
		LOGGER.error(LMS_UPDATE_VEHICLE_TRAVEL_CALL_BACK,errorResponse.toString());
	}

}