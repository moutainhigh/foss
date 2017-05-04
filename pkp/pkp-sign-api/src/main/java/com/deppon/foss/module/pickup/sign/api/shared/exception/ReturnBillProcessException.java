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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/exception/ReturnBillProcessException.java
 * 
 * FILE NAME        	: ReturnBillProcessException.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;
/**
 * 
 *ReturnBillProcess异常类
 * @author 
 *		foss-meiying
 * @date 
 *      2013-3-13 下午2:56:08
 * @since
 * @version
 */
public class ReturnBillProcessException extends BusinessException {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	public static final String CONFIRM_SUCESS = "确认成功";
	public static final String CONFIRM_NO = "确认失败";
	/**
	 * 有一个参数的构造方法
	 * @author foss-meiying
	 * @date 2013-3-13 下午2:55:48
	 * @param code
	 */
	public ReturnBillProcessException(String code) {
		super();
		this.errCode = code;
	}
	/**
	 * 有两个参数的构造方法
	 * @author foss-meiying
	 * @date 2013-3-13 下午2:55:34
	 * @param code
	 * @param e
	 */
	public ReturnBillProcessException(String code, Throwable e) {
		super(code,e);
		this.errCode = code;
	}
}