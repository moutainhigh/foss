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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/lmsitf/esb/client/UpdateVehicleStateCallBackProcessor.java
 *  
 *  FILE NAME          :UpdateVehicleStateCallBackProcessor.java
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
 * FILE    NAME: UpdateVehicleStateCallBackProcessor.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.lmsitf.esb.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.inteface.domain.vehicle.CarStateInfoDisposeReult;
import com.deppon.esb.inteface.domain.vehicle.CarStateInfocationResponse;

/**
 * 中转调用LMS更新车辆状态接口的回调处理类
 * @author 046130-foss-xuduowei
 * @date 2012-12-11 上午8:41:29
 */
public class UpdateVehicleStateCallBackProcessor implements ICallBackProcess {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateVehicleStateCallBackProcessor.class);
	//错误标志，用于查找日志
	private static final String LMS_UPDATE_VEHICLE_STATE_CALL_BACK = "LMS_UPDATE_VEHICLE_STATE_CALL_BACK:";
	/** 
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-11 上午8:41:31
	 * @see com.deppon.esb.core.process.ICallBackProcess#callback(java.lang.Object)
	 */
	@Override
	public void callback(Object response) throws ESBException {
		if(response!=null){
			CarStateInfocationResponse carResponse = (CarStateInfocationResponse)response;
			CarStateInfoDisposeReult result = carResponse.getCarStateInfoDisposeReult();
			if(result != null){
				LOGGER.info("车牌号："+result.getCarNumber());
				LOGGER.info("原因："+result.getReason());
				LOGGER.info("是否成功："+result.isResult());
			}else{
				LOGGER.debug("response: null");
			}
		}else{
			//无操作
		}

	}

	/** 
	 * 返回
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-11 上午8:41:31
	 * @see com.deppon.esb.core.process.ICallBackProcess#errorHandler(java.lang.Object)
	 */
	@Override
	public void errorHandler(Object errorResponse) throws ESBException {
		
		LOGGER.error(LMS_UPDATE_VEHICLE_STATE_CALL_BACK,errorResponse.toString());
	}

}