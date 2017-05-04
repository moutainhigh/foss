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
 * PROJECT NAME	: bse-dict-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/api/shared/exception/ConfigurationParamsException.java
 * 
 * FILE NAME        	: ConfigurationParamsException.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;


/**
 * 系统配置参数 异常类
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-30 上午10:59:22
 */
public class ConfigurationParamsException extends BusinessException {

    private static final long serialVersionUID = 3063168317009336403L;

    /**
     * "系统配置参数"的业务异常ERROR_KEY
     */
    public static final String CONFIG__PARAMETER_ERROR__CODE = "foss.bse.bse-baseinfo.DataDictionaryValueException";
    
    public ConfigurationParamsException(String code, String msg, Throwable cause) {
	super(code, msg, cause);
    }

    public ConfigurationParamsException(String code, String msg) {
	super(code, msg);
    }

    public ConfigurationParamsException(String msg) {
	super(msg);
    }
}
