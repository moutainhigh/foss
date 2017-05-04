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
 * PROJECT NAME	: pkp-pda-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/shared/exception/PdaProcessException.java
 * 
 * FILE NAME        	: PdaProcessException.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pda.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 调度异常类
 * 
 * @author ibm-wangfei
 * @date Dec 5, 2012 8:16:30 PM
 */
public class PdaProcessException extends BusinessException {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2716619840870429159L;
	/**
	 * 有参构造方法
	 * @author foss-meiying
	 * @date 2013-3-11 下午3:22:57
	 * @param errCode
	 */
	public PdaProcessException(String errCode) {
		super();
		this.errCode = errCode;
	}
	/**
	 * 有参构造方法
	 * @author foss-meiying
	 * @date 2013-3-11 下午3:23:08
	 * @param errCode
	 * @param cause
	 */
	public PdaProcessException(String errCode, Throwable cause) {
		super(errCode, cause);
		this.errCode = errCode;
	}

}