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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/ResourceConflictException.java
 * 
 * FILE NAME        	: ResourceConflictException.java
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
 * 权限互斥异常类
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-30 下午10:02:27
 */
public class ResourceConflictException extends BusinessException {

    private static final long serialVersionUID = 590525254182760551L;

    /**
     * 权限互斥编码不能为空
     */
    public static final String RESOURCE_CONFLICT_CODE_NULL = "foss.authorization.RoleCodeNullException";
    //RESOURCECONFLICT
    public static final String RESOURCECONFLICT_ADD_SUCCESS = "foss.bse.baseinfo.resourceConflictException.addSuccess";
    
    public static final String RESOURCECONFLICT_ADD_FAILURE = "foss.bse.baseinfo.resourceConflictException.addFailure";
    
    public static final String RESOURCECONFLICT_DEL_SUCCESS = "foss.bse.baseinfo.resourceConflictException.delSuccess";
    
    public static final String RESOURCECONFLICT_DEL_FAILURE = "foss.bse.baseinfo.resourceConflictException.delFailure";

    public static final String RESOURCECONFLICT_QUE_FAILURE = "foss.bse.baseinfo.resourceConflictException.queFailure";
    
    
    /**
     * 异常的构造方法
     * 
     * @param errCode
     * @since
     */
    public ResourceConflictException(String errCode) {
	super();
	super.errCode = errCode;
    }

    /**
     * 异常的构造方法
     * 
     * @param errCode
     * @since
     */
    public ResourceConflictException(String code, String msg) {
	super(code, msg);
    }

    /**
     * 异常的构造方法
     * 
     * @param errCode
     * @since
     */
    public ResourceConflictException(String code, String msg, Throwable cause) {
	super(code, msg, cause);
    }
}
