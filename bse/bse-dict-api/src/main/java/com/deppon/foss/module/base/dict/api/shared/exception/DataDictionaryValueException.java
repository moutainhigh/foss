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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/api/shared/exception/DataDictionaryValueException.java
 * 
 * FILE NAME        	: DataDictionaryValueException.java
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
 * 数据字典-值 的异常类
 * @author 087584-foss-lijun
 * @date 2012-11-30 上午10:58:50
 */
public class DataDictionaryValueException extends BusinessException {

    private static final long serialVersionUID = 9063168317009336403L;

    /**
     * "数据字典"的业务异常ERROR_KEY
     */
    public static final String DATADICTVALUE__PARAMETER_ERROR__CODE = "foss.bse.bse-baseinfo.DataDictionaryValueException";
    
    public DataDictionaryValueException(String code, String msg, Throwable cause) {
	super(code, msg, cause);
    }

    public DataDictionaryValueException(String code, String msg) {
	super(code, msg);
    }

    public DataDictionaryValueException(String msg) {
	super(msg);
    }
}
