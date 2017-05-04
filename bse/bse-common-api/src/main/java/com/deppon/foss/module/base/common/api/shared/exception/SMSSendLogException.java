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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/exception/SMSSendLogException.java
 * 
 * FILE NAME        	: SMSSendLogException.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;
/**
 * 用来处理“短信信息”业务操作异常类：无SUC
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-19 下午7:48:35</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-19 下午7:48:35
 * @since
 * @version
 */
public class SMSSendLogException extends BusinessException {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 8359733132749548760L;

    /**
     * "短信信息"的业务异常ERROR_KEY
     */
    public static final String SMSSENDLOG_ID_NULL_ERROR_CODE = "foss.bse.bse-baseinfo.SMSSendLogIdNullException";

    public SMSSendLogException(String code, String msg, Throwable cause) {
	super(code, msg, cause);
    }

    public SMSSendLogException(String code, String msg) {
	super(code, msg);
    }
}
