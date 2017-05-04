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
 *  FILE PATH          :/AirHandOverBillException.java
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

public class AirHandOverBillException extends TfrBusinessException {

	private static final long serialVersionUID = 3445971650549377487L;
	
	public AirHandOverBillException() {
		super();
	}

	public AirHandOverBillException(String code, String msg, String natvieMsg,
			Throwable cause) {
		super(code, msg, natvieMsg, cause);
	}

	public AirHandOverBillException(String code, String msg, String natvieMsg) {
		super(code, msg, natvieMsg);
	}

	public AirHandOverBillException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	public AirHandOverBillException(String code, String msg) {
		super(code, msg);
	}

	public AirHandOverBillException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public AirHandOverBillException(String msg) {
		super(msg);
	}
	
}