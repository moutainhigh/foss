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
 * PROJECT NAME	: bse-orgsync-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/orgsync/api/shared/exception/OrgAdministrativeInfoRemindException.java
 * 
 * FILE NAME        	: OrgAdministrativeInfoRemindException.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.orgsync.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;


public class OrgAdministrativeInfoRemindException extends BusinessException {

    private static final long serialVersionUID = 3964605498450776283L;

    /**
     * "行政组织"的业务异常ERROR_KEY 
     */
    public static final String ORGADMINISTRATIVEINFO_CODE_IS_NULL = "foss.bse.bse-baseinfo.OrgAdministrativeInfoRemindCodeNullException";
    

    /**
     * "行政组织"的业务异常ERROR_KEY 
     */
    public static final String ORGADMINISTRATIVEINFO_BIZ_TYPE_IS_EMPTY = "foss.bse.bse-baseinfo.OrgAdministrativeInfoRemindCodeNullException";
    
    
    public OrgAdministrativeInfoRemindException(String code, String msg, Throwable cause) {
	super(code, msg, cause);
    }

    public OrgAdministrativeInfoRemindException(String code, String msg) {
	super(code, msg);
    }
    public OrgAdministrativeInfoRemindException(String msg) {
	super(msg);
    }
}

