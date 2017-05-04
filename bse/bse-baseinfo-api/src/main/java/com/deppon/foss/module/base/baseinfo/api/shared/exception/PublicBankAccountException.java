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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/PublicBankAccountException.java
 * 
 * FILE NAME        	: PublicBankAccountException.java
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
 * 用来处理“对公银行账号”业务操作异常类类，无SUC
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-11 上午8:56:48
 */
public class PublicBankAccountException extends BusinessException {

    private static final long serialVersionUID = 3964605498450776283L;

    /**
     * "对公银行账号"的业务异常ERROR_KEY 
     */
    public static final String ORGADMINISTRATIVEINFO_CODE_IS_NULL = "foss.bse.bse-baseinfo.OrgAdministrativeInfoCodeNullException";
    

    /**
     * "对公银行账号"的业务异常ERROR_KEY 
     */
    public static final String ORGADMINISTRATIVEINFO_BIZ_TYPE_IS_EMPTY = "foss.bse.bse-baseinfo.OrgAdministrativeInfoCodeNullException";
    
    
    public PublicBankAccountException(String code, String msg, Throwable cause) {
	super(code, msg, cause);
    }

    public PublicBankAccountException(String code, String msg) {
	super(code, msg);
    }
    public PublicBankAccountException(String msg) {
	super(msg);
    }
}
