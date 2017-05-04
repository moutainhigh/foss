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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/exception/InviteVehicleException.java
 * 
 *  FILE NAME     :InviteVehicleException.java
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
 * FILE    NAME: InviteVehicleException.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.shared.exception;

import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.define.InviteVehicleConstants;

/**
 * 外请车异常
 * @author 104306-foss-wangLong
 * @date 2012-12-15 上午11:08:37
 */
public class InviteVehicleException extends TfrBusinessException {

	private static final long serialVersionUID = -4142727157293047534L;

	/**
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午2:29:01
	 * @param errorCode 
	 * @param arguments
	 */
	public InviteVehicleException(String errorCode, Object[] arguments) {
		super();
		this.errCode = errorCode;
		setErrorArguments(arguments);
	}
	
	/**
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 上午11:08:37
	 * @param errorCode
	 * @param message
	 */
	public InviteVehicleException(String errorCode, String message) {
		this(errorCode, new Object[]{message});
	}
	
	/**
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 上午11:08:37
	 * @param message
	 */
	public InviteVehicleException(String message) {
		this(InviteVehicleConstants.INVITEVEHICLE_PARAMETERERROR_MESSAGE, new Object[]{message});
	}
}