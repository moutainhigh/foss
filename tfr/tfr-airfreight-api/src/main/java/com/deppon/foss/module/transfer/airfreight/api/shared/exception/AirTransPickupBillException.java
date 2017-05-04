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
 *  FILE PATH          :/AirTransPickupBillException.java
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

public class AirTransPickupBillException extends TfrBusinessException {

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 6961830207820935893L;

	public AirTransPickupBillException(String errorCode) {
		this.errCode = errorCode;
	}
	
	public AirTransPickupBillException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AirTransPickupBillException(String errCode, String msg,
			String natvieMsg, Throwable cause) {
		super(errCode, msg, natvieMsg, cause);
		// TODO Auto-generated constructor stub
	}

	public AirTransPickupBillException(String errCode, String msg,
			String natvieMsg) {
		super(errCode, msg, natvieMsg);
		// TODO Auto-generated constructor stub
	}

	public AirTransPickupBillException(String errCode, String msg,
			Throwable cause) {
		super(errCode, msg, cause);
		// TODO Auto-generated constructor stub
	}

	public AirTransPickupBillException(String errCode, String msg) {
		super(errCode, msg);
		// TODO Auto-generated constructor stub
	}

	public AirTransPickupBillException(String msg, Throwable cause) {
		super(msg, cause);
		// TODO Auto-generated constructor stub
	}
}