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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/exception/CalculateOptimalPlatformException.java
 * 
 *  FILE NAME     :CalculateOptimalPlatformException.java
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
 * FILE    NAME: CalculateOptimalPlatformException.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.shared.exception;

import com.deppon.foss.module.transfer.scheduling.api.define.PlatformDispatchConstants;

/**
 * 计算最优月台异常
 * @author 104306-foss-wangLong
 * @date 2012-11-16 上午6:28:31
 */
public class CalculateOptimalPlatformException extends PlatformDispatchException {

	private static final long serialVersionUID = -5075893155105567106L;
	
	/**
	 * CalculateOptimalPlatformException
	 * @author 104306-foss-wangLong
	 * @date 2012-11-14 下午5:33:53
	 */
	public CalculateOptimalPlatformException(String errorCode, Object[] arguments) {
		super(errorCode, arguments);
	}

	/**
	 * CalculateOptimalPlatformException
	 * @author 104306-foss-wangLong
	 * @date 2013-1-11 上午9:19:30
	 * @param message  异常信息
	 */
	public CalculateOptimalPlatformException(String message) {
		super(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{message});
	}
}