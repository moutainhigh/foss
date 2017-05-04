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
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/AirSpaceException.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.exception;

import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;

public class AirSpaceException extends TfrBusinessException {

	private static final long serialVersionUID = -2602037693068059191L;
	
	/**已经存在该日期内同一目的站的舱位信息*/
	public static final String AIRFREIGHT_EXCEPTION_EXISTAIRSPACE = "foss.airfreight.exception.existAirSpace";
	
	/**至少录入一条舱位信息*/
	public static final String AIRFREIGHT_EXCEPTION_NEEDINPUTONESPACE = "foss.airfreight.exception.needInputOneSpace";
	
	/**只能删除当前日期下一天的数据*/
	public static final String AIRFREIGHT_EXCEPTION_ONLYDELETENEXTDAYSPACE = "foss.airfreight.exception.onlyDeleteNextDaySpace";
	
	/**生成待办事项失败*/
	public static final String AIRFREIGHT_EXCEPTION_ADDBACKLOGSCHEDULEFAILURE = "foss.airfreight.exception.addBacklogScheduleFailure";
	
	
	public AirSpaceException() {
		super();
	}

	public AirSpaceException(String code, String msg, String natvieMsg,
			Throwable cause) {
		super(code, msg, natvieMsg, cause);
	}

	public AirSpaceException(String code, String msg, String natvieMsg) {
		super(code, msg, natvieMsg);
	}

	public AirSpaceException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	public AirSpaceException(String code, String msg) {
		super(code, msg);
	}

	public AirSpaceException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public AirSpaceException(String msg) {
		super(msg);
	}
	
}