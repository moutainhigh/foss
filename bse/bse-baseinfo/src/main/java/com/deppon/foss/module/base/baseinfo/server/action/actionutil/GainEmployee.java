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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/actionutil/GainEmployee.java
 * 
 * FILE NAME        	: GainEmployee.java
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
package com.deppon.foss.module.base.baseinfo.server.action.actionutil;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * 
 * @date Mar 11, 2013 2:48:18 PM
 * @version 1.0
 */
public class GainEmployee {
    
    /**
     * 获得操作人员编码（工号）
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-29 下午3:30:13
     */
    public static String getOperUserCode(){
	// 设置操作用户的用户编码
	UserEntity userOfCache = FossUserContext.getCurrentUser();
	String operUserCode = null;
	if (userOfCache != null
		&& userOfCache.getEmployee() != null
		&& StringUtils.isNotBlank(userOfCache.getEmployee()
			.getEmpCode())) {
	    operUserCode = userOfCache.getEmployee().getEmpCode();
	}
	return operUserCode;
    }
}
