/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 038590-foss-wanghui
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
 * PROJECT NAME	: pkp-order-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/exception/DispatchException.java
 * 
 * FILE NAME        	: DispatchException.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;


/**
 * 异常类
 * @author caohuibin
 * @date 2015.9.11
 */
public class BigCustomeException extends BusinessException {

	public static final String BIGCUSTOMENULL="pkp.order.BigCustome.Exception.null";

	public static final String BIGCUSTOMESUMMARYNULL="pkp.order.BigCustomeSummary.Exception.null";

	public static final String ERROR = "error";
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 0;

	/**
	 *
	 * 有参构造
	 * @author caohuibin
	 * @date
	 * @param code
	 */
	public BigCustomeException(String code)
	{
		super();
		this.errCode = code;
	}

	/**
	 *
	 * 有参构造
	 */
	public BigCustomeException(String code,  Throwable cause){
		super(code, cause);
		this.errCode = code;
	}

}