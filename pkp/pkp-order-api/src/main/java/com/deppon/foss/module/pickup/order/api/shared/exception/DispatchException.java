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
 * 调度异常类
 * @author 038590-foss-wanghui
 * @date 2012-10-25 下午5:06:36
 */
public class DispatchException extends BusinessException {

	private static final long serialVersionUID = 691797036656592039L;

	/**
	 * Instantiates a new dispatchException.
	 * 
	 * @param errCode the errCode
	 */
	public DispatchException(String errCode) {
		super(errCode);				//zxy 20140710 修改:把code设置到msg中,否则打印出来的都是null
		super.errCode = errCode;
	}
	
	/**
	 * zxy 20140710 AUTO-134 新增
	 * Creates a new instance of DispatchException.
	 *
	 * @param code
	 * @param msg
	 */
	public DispatchException(String code, String msg) {
		super(msg);
		this.errCode = code;
	}
	
	/**
	 * Instantiates a new dispatchException.
	 * 
	 * @param errCode the errCode
	 * @param msg the msg
	 */
	public DispatchException(String errCode, Throwable cause){
		super(errCode, cause);
		super.errCode = errCode;
	}

}