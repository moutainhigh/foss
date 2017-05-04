/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/UserRoleUtils.java
 * 
 * FILE NAME        	: UserRoleUtils.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
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
package com.deppon.foss.module.pickup.common.client.utils;

import java.util.Map;

import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.module.login.shared.domain.LoginInfo;

/**
 * 这个类的作用是根据userId 和user在本地的resourcseMap来判断 当前user有没有权限执行该action
 * @author shixw
 *
 */
public abstract class UserRoleUtils {

	/**
	 * 判断当前的userRole是不是有权限执行该action
	 * @param actionClass
	 * @return
	 */
	@SuppressWarnings("rawtypes") 
	public static boolean validateCurrentUserRoleAction(Class actionClass){
		
		LoginInfo loginInfo =(LoginInfo) SessionContext.get("login_info");

		//不存在login info就干脆直接让他可以打开窗口
		if(loginInfo==null){
			return true;
		}
		
		//uri Map
		Map<String, String> uriMap = loginInfo.getUserResourcesCodes();
		
		if(uriMap==null){
			return true;
		}
		
		//有这种权限就返回true
		if(uriMap.containsValue(actionClass.getName())){
			return true;
		}else{
			//没有这种权限就返回false
			return false;
		}
	}
	
}