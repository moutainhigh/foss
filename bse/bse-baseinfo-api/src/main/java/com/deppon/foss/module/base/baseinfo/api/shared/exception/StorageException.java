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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/exception/StorageException.java
 * 
 * FILE NAME        	: StorageException.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;


/**
 * 库位异常类
 * @author foss-zhujunyong
 * @date Jan 14, 2013 5:24:33 PM
 * @version 1.0
 */
public class StorageException extends BusinessException{

    /**
     * 
     */
    private static final long serialVersionUID = -7098221492858620391L;

    /**
     *同一外场同一库区下库位编码不能重复
     */
    public static final String STORAGE_CODE_EXIST = "foss.bse.baseinfo.storage.storageCodeExist";
    
    public StorageException(String errCode){
	super();
	super.errCode = errCode;
    }
    
    public StorageException(String code,String msg){
	super(code,msg);
    }
    
}
