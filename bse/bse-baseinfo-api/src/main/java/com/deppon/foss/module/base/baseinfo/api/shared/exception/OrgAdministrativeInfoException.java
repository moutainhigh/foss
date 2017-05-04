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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/OrgAdministrativeInfoException.java
 * 
 * FILE NAME        	: OrgAdministrativeInfoException.java
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
 * 用来处理“行政组织”业务操作异常类类：SUC-785
 * 
 * @date 2012-10-22 下午2:27:40
 * @since
 * @version
 */
public class OrgAdministrativeInfoException extends BusinessException {

    private static final long serialVersionUID = 3964605498450776283L;

    /**
     * "行政组织"的业务异常ERROR_KEY 
     */
    public static final String ORGADMINISTRATIVEINFO_CODE_IS_NULL = "foss.bse.bse-baseinfo.OrgAdministrativeInfoCodeNullException";
    
    public static final String ORGADMINISTRATIVEINFO_SALE_DELIVERY_INVALID = "foss.bse.baseinfo.OrgAdministrativeInfoSaleDeliveryInvalid";

    /**
     * "行政组织"的业务异常ERROR_KEY 
     */
    public static final String ORGADMINISTRATIVEINFO_BIZ_TYPE_IS_EMPTY = "foss.bse.bse-baseinfo.OrgAdministrativeInfoCodeNullException";
    
    
    public OrgAdministrativeInfoException(String code, String msg, Throwable cause) {
	super(code, msg, cause);
    }

    public OrgAdministrativeInfoException(String code, String msg) {
	super(code, msg);
    }
    public OrgAdministrativeInfoException(String msg) {
	super(msg);
    }
}
