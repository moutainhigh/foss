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
 *  PROJECT NAME  : tfr-partialline-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 偏线外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/api/shared/exception/ExternalBillException.java
 * 
 *  FILE NAME     :ExternalBillException.java
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
package com.deppon.foss.module.transfer.partialline.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 偏线异常
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-12 上午9:07:12
 */
public class ExternalBillException extends BusinessException {

	private static final long serialVersionUID = 197828712662398468L;

	public ExternalBillException(String errCode) {
		super();
		super.errCode = errCode;
	}

	/**
	 * 外发单异常
	 * 
	 * @author dp-zhongyubing
	 * @date 2012-11-30 上午9:50:27
	 */
	public ExternalBillException() {
		super();

	}

	/**
	 * 外发单异常
	 * 
	 * @author dp-zhongyubing
	 * @date 2012-11-30 上午9:50:27
	 */
	public ExternalBillException(String code, String msg, String natvieMsg, Throwable cause) {
		super(code, msg, natvieMsg, cause);
	}

	/**
	 * 外发单异常
	 * 
	 * @author dp-zhongyubing
	 * @date 2012-11-30 上午9:50:27
	 */
	public ExternalBillException(String code, String msg, String natvieMsg) {
		super(code, msg, natvieMsg);
	}

	/**
	 * 外发单异常
	 * 
	 * @author dp-zhongyubing
	 * @date 2012-11-30 上午9:50:27
	 */
	public ExternalBillException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	/**
	 * 外发单异常
	 * 
	 * @author dp-zhongyubing
	 * @date 2012-11-30 上午9:50:27
	 */
	public ExternalBillException(String code, String msg) {
		super(code, msg);
	}

	/**
	 * 外发单异常
	 * 
	 * @author dp-zhongyubing
	 * @date 2012-11-30 上午9:50:27
	 */
	public ExternalBillException(String msg, Throwable cause) {
		super(msg, cause);
	}

}