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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/LoadAndUnloadEfficiencyTonException.java
 * 
 * FILE NAME        	: LoadAndUnloadEfficiencyTonException.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;


/**
 * 装卸车效率标准异常类
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-4 上午11:36:16
 */
public class LoadAndUnloadEfficiencyTonException extends BusinessException {

	private static final long serialVersionUID = 590525254182760551L;
	
	/**
	 * 角色编码不能为空
	 */
	public static final String LOADANDUNLOADEFFICIENCY_TON_CODE_NULL = "foss.authorization.RoleCodeNullException";
	
	/**
	  * 异常的构造方法
	  * @param errCode
	  * @since
	 */
	public LoadAndUnloadEfficiencyTonException(String errCode){
		super();
		super.errCode = errCode;
	}

	public LoadAndUnloadEfficiencyTonException(String code, String msg) {
		super(code, msg);
	}
	
	public LoadAndUnloadEfficiencyTonException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}
}
