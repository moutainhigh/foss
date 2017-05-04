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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/exception/BaseInfoInvokeException.java
 * 
 * FILE NAME        	: BaseInfoInvokeException.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 运单提交异常
 * @author 026123-foss-lifengteng
 * @date 2012-11-8 下午3:06:09
 */
public class BaseInfoInvokeException extends BusinessException {

	/**
	 * 序列化版本号（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = -6348817845036184310L;
	
	 //找不到走货线路，请确认是否添加了走货线路：
	public static final String FREIGHTROUTE_NULL="foss.pkp.waybill.waybillManagerService.exception.nullFreightRoute";
    

	/**
	 *  （创建一个新的实例 ）BaseInfoInvokeException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午3:06:10
	 */
	public BaseInfoInvokeException() {
	}

	/**
	 *  （创建一个新的实例 ）BaseInfoInvokeException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午3:06:10
	 */
	public BaseInfoInvokeException(String msg) {
		super(msg);
		this.errCode = msg;
	}

	/**
	 *  （创建一个新的实例 ）BaseInfoInvokeException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午3:06:10
	 */
	public BaseInfoInvokeException(String msg, Throwable cause) {
		super(msg, cause);
		this.errCode = msg;
	}

	/**
	 *  （创建一个新的实例 ）BaseInfoInvokeException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午3:06:10
	 */
	public BaseInfoInvokeException(String code, String msg) {
		super(code, msg);
	}

	/**
	 *  （创建一个新的实例 ）WaybillValidateException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-23 下午4:20:19
	 */
	public BaseInfoInvokeException(String code,Object... args) {
		super(code,args);
		this.errCode = code;
	}
	/**
	 *  （创建一个新的实例 ）BaseInfoInvokeException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午3:06:10
	 */
	public BaseInfoInvokeException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	/**
	 *  （创建一个新的实例 ）BaseInfoInvokeException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午3:06:10
	 */
	public BaseInfoInvokeException(String code, String msg, String natvieMsg) {
		super(code, msg, natvieMsg);
	}

	/**
	 *  （创建一个新的实例 ）BaseInfoInvokeException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午3:06:10
	 */
	public BaseInfoInvokeException(String code, String msg, String natvieMsg, Throwable cause) {
		super(code, msg, natvieMsg, cause);
	}

}