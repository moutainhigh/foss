/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-pickup-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/api/shared/exception/QueryTrackingWaybillException.java
 * 
 * FILE NAME        	: QueryTrackingWaybillException.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 
 * 运单追踪异常类
 * @author ibm-wangfei
 * @date Nov 14, 2012 5:34:33 PM
 */
public class QueryTrackingWaybillException extends BusinessException {
	
	private static final long serialVersionUID = 1L;
	public static final String ERROR = "error";

	public QueryTrackingWaybillException(String errCode) {
		super();
		super.errCode = errCode;
	}

	public QueryTrackingWaybillException(String code, String msg) {

		super(code, msg);
	}

	public QueryTrackingWaybillException(String msg, Throwable cause) {

		super(msg, cause);
	}
}