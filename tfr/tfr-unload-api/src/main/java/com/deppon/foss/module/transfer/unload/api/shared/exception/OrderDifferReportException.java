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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/exception/SealException.java
 *  
 *  FILE NAME          :SealException.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 
 * 绑定与校验装车封签Exception
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:zyx,date:2012-10-11 下午3:15:52</p>
 * @author zyx
 * @date 2012-10-11 下午3:15:52
 * @since
 * @version
 */
public class OrderDifferReportException extends BusinessException {
	private static final long serialVersionUID = 5759198406172095887L;

	public OrderDifferReportException() {
		super();
	}

	public OrderDifferReportException(String errCode, String msg, String natvieMsg, Throwable cause) {
		super(errCode, msg, natvieMsg, cause);
	}

	public OrderDifferReportException(String errCode, String msg, String natvieMsg) {
		super(errCode, msg, natvieMsg);
	}

	public OrderDifferReportException(String errCode, String msg, Throwable cause) {
		super(errCode, msg, cause);
	}

	public OrderDifferReportException(String errCode, String msg) {
		super(errCode, msg);
	}

	public OrderDifferReportException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public OrderDifferReportException(String msg) {
		super(msg);
		this.errCode = msg;
	}
	
	public OrderDifferReportException(String code,Object... args) {
		super(code,args);
	}
	
	public OrderDifferReportException(String code,String msg, Object... args) {
		super(code,msg,args);
	}
}