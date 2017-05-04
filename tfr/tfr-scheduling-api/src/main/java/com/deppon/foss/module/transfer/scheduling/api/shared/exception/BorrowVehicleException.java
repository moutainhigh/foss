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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/exception/BorrowVehicleException.java
 * 
 *  FILE NAME     :BorrowVehicleException.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.shared.exception
 * FILE    NAME: BorrowVehicleException.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.shared.exception;

import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.define.BorrowVehicleConstants;

/**
 * 借车异常
 * @author 104306-foss-wangLong
 * @date 2012-11-14 下午5:32:15
 */
public class BorrowVehicleException extends TfrBusinessException {

	private static final long serialVersionUID = -2352447711127450284L;

	/**
	 * @author 104306-foss-wangLong
	 * @date 2012-12-5 下午4:00:19
	 * @param errorCode
	 * @param arguments
	 */
	public BorrowVehicleException(String errorCode, Object[] arguments) {
		super();
		this.errCode = errorCode;
		setErrorArguments(arguments);
	}
	
	/**
	 * @author 104306-foss-wangLong
	 * @date 2012-12-5 下午4:00:19
	 * @param errorCode
	 * @param message
	 */
	public BorrowVehicleException(String errorCode, String message) {
		this(errorCode, new Object[]{message});
	}
	
	/**
	 * @author 104306-foss-wangLong
	 * @date 2012-12-5 下午4:00:19
	 * @param message
	 */
	public BorrowVehicleException(String message) {
		this(BorrowVehicleConstants.BORROWVEHICLE_PARAMETERERROR_MESSAGE, new Object[]{message});
	}
}