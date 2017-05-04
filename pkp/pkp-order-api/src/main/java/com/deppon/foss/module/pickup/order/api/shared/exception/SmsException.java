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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/exception/SmsException.java
 * 
 * FILE NAME        	: SmsException.java
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
 * 短信异常
 * @author 038590-foss-wanghui
 * @date 2012-12-7 上午9:32:30
 */
public class SmsException extends BusinessException {
	
	private static final long serialVersionUID = -4908792537931743394L;

	/**
	 * Instantiates a new smsNotFoundException.
	 * 
	 * @param errCode the errCode
	 */
	public SmsException(String errCode) {
		super();
		super.errCode = errCode;
	}
	
	/**
	 * Instantiates a new smsNotFoundException.
	 * 
	 * @param errCode the errCode
	 * @param msg the msg
	 */
	public SmsException(String errCode, Throwable cause){
		super(errCode, cause);
		super.errCode = errCode;
	}
}