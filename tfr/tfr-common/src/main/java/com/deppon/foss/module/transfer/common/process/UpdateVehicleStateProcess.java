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
 *  PROJECT NAME  : tfr-common
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/process/UpdateVehicleStateProcess.java
 *  
 *  FILE NAME          :UpdateVehicleStateProcess.java
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
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.common.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.inteface.domain.vehicle.CarStateInfocationResponse;

/**
 * @author 038300-foss-pengzhen
 * 更新车辆状态
 */
public class UpdateVehicleStateProcess implements ICallBackProcess{

	/**
	 * 日志
	 */
	protected final static Logger logger = LoggerFactory.getLogger(UpdateVehicleStateProcess.class.getName());
	/**
	 * ESB
	 * 回调
	 */
	@Override
	public void callback(Object arg0) throws ESBException{
		CarStateInfocationResponse carStateInfocationResponse=(CarStateInfocationResponse)arg0;
		logger.info("更新车辆状态正常回调：",carStateInfocationResponse.getCarStateInfoDisposeReult().getCarNumber()+"<>"+carStateInfocationResponse.getCarStateInfoDisposeReult().getReason());
		
	}
	/**
	 * ESB
	 * 回调
	 */
	@Override
	public void errorHandler(Object arg0) throws ESBException{
		CarStateInfocationResponse carStateInfocationResponse=(CarStateInfocationResponse)arg0;
		logger.info("更新车辆状态异常回调：",carStateInfocationResponse.getCarStateInfoDisposeReult().getCarNumber()+"<>"+carStateInfocationResponse.getCarStateInfoDisposeReult().getReason());
		
	}

}