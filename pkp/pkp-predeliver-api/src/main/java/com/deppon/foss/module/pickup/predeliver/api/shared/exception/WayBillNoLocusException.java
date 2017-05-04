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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/exception/WayBillNoLocusException.java
 * 
 * FILE NAME        	: WayBillNoLocusException.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 
 * 异常类
 * 
 * @author ibm-wangfei
 * @date Jan 8, 2013 5:34:08 PM
 */
public class WayBillNoLocusException extends BusinessException{
	
	private static final long serialVersionUID = 1L;

	public WayBillNoLocusException(String code){
		  super();
		  this.errCode = code;
	}
	//有两个参数的构造函数
	public WayBillNoLocusException(String code, Throwable cause) {
		super(code,cause);
		this.errCode = code;
	}
	//有两个参数的构造函数
	public WayBillNoLocusException(String code, String msg) {
		super(code,msg);
		this.errCode = code;
	}
}