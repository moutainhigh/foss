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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/exception/FossSSORemotingException.java
 * 
 * FILE NAME        	: FossSSORemotingException.java
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

import com.deppon.foss.framework.exception.GeneralException;

/**
 * Hession单点登陆异常类
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-12-25 上午9:48:48
 */
public class FossSSORemotingException extends GeneralException {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = -770454893545274873L;

	/**
	 * 
	 * 单点登陆异常方法
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:48:32
	 */
	public FossSSORemotingException() {
		super();
	}

	/**
	 * 
	 * 单点登陆异常方法
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:48:32
	 */
	public FossSSORemotingException(String errCode, String message, Throwable cause, Object... arguments) {
		super(errCode, message, cause, arguments);
	}

	/**
	 * 
	 * 单点登陆异常方法
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:48:32
	 */
	public FossSSORemotingException(String errCode, String message, Throwable cause, String nativeMsg, Object... arguments) {
		super(errCode, message, cause, nativeMsg, arguments);
	}

	/**
	 * 
	 * 单点登陆异常方法
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:48:32
	 */
	public FossSSORemotingException(String msg, String nativeMsg) {
		super(msg, nativeMsg);
	}

	/**
	 * 
	 * 单点登陆异常方法
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:48:32
	 */
	public FossSSORemotingException(String message, Throwable cause, String nativeMsg) {
		super(message, cause, nativeMsg);
	}

	/**
	 * 
	 * 单点登陆异常方法
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:48:32
	 */
	public FossSSORemotingException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 
	 * 单点登陆异常方法
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:48:32
	 */
	public FossSSORemotingException(String msg) {
		super(msg);
	}

	/**
	 * 
	 * 单点登陆异常方法
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:48:32
	 */
	public FossSSORemotingException(Throwable cause, String nativeMsg) {
		super(cause, nativeMsg);
	}

	/**
	 * 
	 * 单点登陆异常方法
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:48:32
	 */
	public FossSSORemotingException(Throwable cause) {
		super(cause);
	}

}