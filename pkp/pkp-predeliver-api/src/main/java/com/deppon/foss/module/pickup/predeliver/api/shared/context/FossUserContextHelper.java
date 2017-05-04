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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/context/FossUserContextHelper.java
 * 
 * FILE NAME        	: FossUserContextHelper.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.context;

import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * 获取session中user相关信息
 * 
 * @author ibm-wangfei
 * @date Nov 14, 2012 4:31:41 PM
 */
public final class FossUserContextHelper {

	/**
	 * 
	 * 当前登录用户empcode
	 * 
	 * @author ibm-wangfei
	 * @date Nov 8, 2012 10:48:41 AM
	 */
	public static String getUserCode() {
		return FossUserContext.getCurrentUser() == null ? "" : FossUserContext.getCurrentUser().getEmployee().getEmpCode();
	}

	/**
	 * 
	 * 当前登录用户empName
	 * 
	 * @author ibm-wangfei
	 * @date Nov 8, 2012 10:48:41 AM
	 */
	public static String getUserName() {
		return FossUserContext.getCurrentUser() == null ? "" : FossUserContext.getCurrentUser().getEmployee().getEmpName();
	}

	/**
	 * 
	 * 当前登录用户所在部门code
	 * 
	 * @author ibm-wangfei
	 * @date Nov 8, 2012 10:48:50 AM
	 */
	public static String getOrgCode() {
		return FossUserContext.getCurrentDeptCode() == null ? "" : FossUserContext.getCurrentDeptCode();
	}

	/**
	 * 
	 * 当前登录用户所在部门Name
	 * 
	 * @author ibm-wangfei
	 * @date Nov 8, 2012 10:48:50 AM
	 */
	public static String getOrgName() {
		return FossUserContext.getCurrentInfo() == null ? "" : FossUserContext.getCurrentInfo().getCurrentDeptName();
	}
}