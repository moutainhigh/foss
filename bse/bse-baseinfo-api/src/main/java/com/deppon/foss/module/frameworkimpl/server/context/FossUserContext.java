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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/frameworkimpl/server/context/FossUserContext.java
 * 
 * FILE NAME        	: FossUserContext.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.frameworkimpl.server.context;

import java.util.List;

import com.deppon.foss.framework.exception.security.UserNotLoginException;
import com.deppon.foss.framework.server.context.SessionContext;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.context.WebApplicationContextHolder;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserDeptDataService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

/**
 * 
 * 
 ******************************************* 
 * <b style="font-family:微软雅黑"><small>Description:FOSS系统当前用户上下文信息类</small></b>
 * </br> <b style="font-family:微软雅黑"><small>HISTORY</small></b></br> <b
 * style="font-family:微软雅黑"><small> ID DATE PERSON REASON</small></b><br>
 ******************************************** 
 * <div style="font-family:微软雅黑,font-size:70%"> 1 2012-11-12 钟庭杰 新增 </div>
 ******************************************** 
 */
public final class FossUserContext {

	private FossUserContext() {
		super();
	}

	/**
	 * 获取当前会话的用户
	 * 
	 * @return UserEntity 当前用户
	 */
	public static UserEntity getCurrentUser() {
		UserEntity user = (UserEntity) (UserContext.getCurrentUser());
		if (user == null) {
			throw new UserNotLoginException();
		}
		return user;
	}

	/**
	 * 获取当前用户设置的当前部门编码 getCurrentDeptCode
	 * 
	 * @return String 当前部门编码
	 * @since:
	 */
	public static String getCurrentDeptCode() {
		return(String) SessionContext.getSession()
				.getObject("FOSS_KEY_CURRENT_DEPTCODE");
	}
	
	/**
	 * 获取当前用户设置的当前部门名称 getCurrentDeptName
	 * 
	 * @return String 当前部门名称
	 * @since:
	 */
	public static String getCurrentDeptName() {
		return(String) SessionContext.getSession()
				.getObject("FOSS_KEY_CURRENT_DEPTNAME");
	}

	/**
	 * 获取当前用户设置的当前部门 getCurrentDept
	 * 
	 * @return OrgAdministrativeInfoEntity 当前部门对象
	 * @since:
	 */
	public static OrgAdministrativeInfoEntity getCurrentDept() {
		IOrgAdministrativeInfoService orgAdministrativeInfoService = (IOrgAdministrativeInfoService) WebApplicationContextHolder
				.getWebApplicationContext().getBean(
						"orgAdministrativeInfoService");
		String currentDeptCode = (String) SessionContext.getSession()
				.getObject("FOSS_KEY_CURRENT_DEPTCODE");
		return orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(currentDeptCode);
	}

	/**
	 * 获得当前用户所属的所有部门编码 getCurrentUserManagerDeptCodes
	 * 
	 * @return List<String> 当前用户所属部门的编码集合
	 * @since:
	 */
	public static List<String> getCurrentUserManagerDeptCodes() {
		IUserDeptDataService userDeptDataService = (IUserDeptDataService) WebApplicationContextHolder
				.getWebApplicationContext().getBean("userDeptDataService");
		UserEntity user = FossUserContext.getCurrentUser();
		return userDeptDataService.queryUserDeptDataByCode(user
				.getUserName());
		 
	}

	/**
	 * 获得当前用户所属的所有部门 getCurrentUserManagerDepts
	 * 
	 * @return List<OrgAdministrativeInfoEntity> 当前用户所属所有部门对象集合
	 * @since:
	 */
	public static List<OrgAdministrativeInfoEntity> getCurrentUserManagerDepts() {
		IUserDeptDataService userDeptDataService = (IUserDeptDataService) WebApplicationContextHolder
				.getWebApplicationContext().getBean("userDeptDataService");
		IOrgAdministrativeInfoService orgAdministrativeInfoService = (IOrgAdministrativeInfoService) WebApplicationContextHolder
				.getWebApplicationContext().getBean(
						"orgAdministrativeInfoService");
		UserEntity user = FossUserContext.getCurrentUser();
		List<String> userOrgCodes = userDeptDataService
				.queryUserDeptDataByCode(user.getEmpCode());
		String[] orgIds = new String[userOrgCodes.size()];
		orgIds = userOrgCodes.toArray(orgIds);
		return orgAdministrativeInfoService
				.queryOrgAdministrativeInfoBatchByCode(orgIds);
	}

	/**
	 * 获得当前系统的当前所有信息 getCurrentInfo
	 * 
	 * @return List<OrgAdministrativeInfoEntity> 当前用户所属所有部门对象集合
	 * @since:
	 */
	public static CurrentInfo getCurrentInfo() {
		UserEntity user = FossUserContext.getCurrentUser();
		return new CurrentInfo(user);
	}
}
