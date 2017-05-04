/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-common-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/exception/MessageException.java
 * 
 * FILE NAME        	: MessageException.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
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
package com.deppon.foss.module.base.common.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 站内消息异常
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-12-12 下午7:17:30
 */
public class MessageException extends BusinessException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1532233389528280343L;

	public MessageException(String errCode) {
		super();
		super.errCode = errCode;
	}

	public MessageException(String code, String msg) {

		super(code, msg);
	}

}
