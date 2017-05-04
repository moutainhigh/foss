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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/exception/BusinessMonitorException.java
 * 
 * FILE NAME        	: BusinessMonitorException.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-common-api
 * PACKAGE NAME: com.deppon.foss.module.base.common.api.shared.exception
 * FILE    NAME: BusinessMonitorException.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;


/**
 * 业务监控异常
 * @author ibm-zhuwei
 * @date 2013-1-31 上午9:40:21
 */
public class BusinessMonitorException extends BusinessException {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 5886506364540034899L;

	public BusinessMonitorException(String errCode) {
		super();
		super.errCode = errCode;
	}

	public BusinessMonitorException(String code, String msg) {

		super(code, msg);
	}

}
