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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/AirlinesAccountException.java
 * 
 * FILE NAME        	: AirlinesAccountException.java
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
 * 用来处理“航空公司账户”业务操作异常类：SUC-43
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-3 上午9:32:39</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-3 上午9:32:39
 * @since
 * @version
 */
public class AirlinesAccountException extends BusinessException {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 3484702757604786231L;

    /**
     * "航空公司账户"的业务异常ERROR_KEY
     */
    public static final String AIRLINESACCOUNT_ID_NULL_ERROR_CODE = "foss.bse.bse-baseinfo.AirlinesAccountIdNullException";

    public AirlinesAccountException(String code, String msg, Throwable cause) {
	super(code, msg, cause);
    }

    public AirlinesAccountException(String code, String msg) {
	super(code, msg);
    }
}
