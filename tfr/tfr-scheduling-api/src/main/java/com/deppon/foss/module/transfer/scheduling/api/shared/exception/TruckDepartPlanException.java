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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/exception/TruckDepartPlanException.java
 * 
 *  FILE NAME     :TruckDepartPlanException.java
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
 * FILE    NAME: TruckDepartPlanException.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 发车计划异常
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-11-21 下午6:12:59
 */
public class TruckDepartPlanException extends BusinessException {

	/**
	 * 发车计划异常
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 发车计划异常
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午6:13:56
	 */
	public TruckDepartPlanException() {
		super();
	}

	/**
	 * 发车计划异常
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午6:13:56
	 */
	public TruckDepartPlanException(String code, String msg, String natvieMsg, Throwable cause) {
		super(code, msg, natvieMsg, cause);
	}

	/**
	 * 发车计划异常
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午6:13:56
	 */
	public TruckDepartPlanException(String code, String msg, String natvieMsg) {
		super(code, msg, natvieMsg);
	}

	/**
	 * 发车计划异常
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午6:13:56
	 */
	public TruckDepartPlanException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	/**
	 * 发车计划异常
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午6:13:56
	 */
	public TruckDepartPlanException(String code, String msg) {
		super(code, msg);
	}

	/**
	 * 发车计划异常
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午6:13:56
	 */
	public TruckDepartPlanException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * 发车计划异常
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午6:13:56
	 */
	public TruckDepartPlanException(String msg) {
		super(msg);
	}

}