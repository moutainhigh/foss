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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/exception/WaybillQueryForBseException.java
 * 
 * FILE NAME        	: WaybillQueryForBseException.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/***
 * 提供给综合查询的异常类
 * @author ibm-wangfei
 * @date 2012-10-30 上午11:41:41
 * @since
 * @version
 */
public class WaybillQueryForBseException extends BusinessException {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 有参构造函数
	 * @author foss-meiying
	 * @date 2013-3-13 下午2:57:07
	 * @param code
	 */
	public WaybillQueryForBseException(String code) {
		super();
		this.errCode = code;
	}
	
	/**
	 * 有参构造函数
	 * @author foss-meiying
	 * @date 2013-3-13 下午2:57:01
	 * @param code
	 * @param cause
	 */
	public WaybillQueryForBseException(String code, Throwable cause) {
		super(code,cause);
		this.errCode = code;
	}
	
	
}