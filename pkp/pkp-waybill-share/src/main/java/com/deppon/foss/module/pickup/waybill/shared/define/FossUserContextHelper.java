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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/define/FossUserContextHelper.java
 * 
 * FILE NAME        	: FossUserContextHelper.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-share
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.shared.define
 * FILE    NAME: FossUserContextHelp.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.shared.define;

import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * 获取session中user相关信息
 * @author 026123-foss-lifengteng
 * @date 2012-12-26 下午5:00:28
 */
public class FossUserContextHelper {


	/**
	 * 当前登录用户empcode
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-26 下午5:01:31
	 */
	public static String getUserCode() {
		return FossUserContext.getCurrentInfo() == null ? "" : FossUserContext.getCurrentInfo().getEmpCode();
	}


	/**
	 *  当前登录用户empName
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-26 下午5:01:40
	 */
	public static String getUserName() {
		return FossUserContext.getCurrentInfo() == null ? "" : FossUserContext.getCurrentInfo().getEmpName();
	}


	/**
	 * 当前登录用户所在部门code
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-26 下午5:01:54
	 */
	public static String getOrgCode() {
		return FossUserContext.getCurrentInfo() == null ? "" : FossUserContext.getCurrentInfo().getCurrentDeptCode();
	}


	/**
	 * 当前登录用户所在部门Name
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-26 下午5:02:15
	 */
	public static String getOrgName() {
		return FossUserContext.getCurrentInfo() == null ? "" : FossUserContext.getCurrentInfo().getCurrentDeptName();
	}
}