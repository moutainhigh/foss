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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/cache/UserCacheProvider.java
 * 
 * FILE NAME        	: UserCacheProvider.java
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
package com.deppon.foss.module.base.baseinfo.server.cache;

import java.util.Date;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.framework.entity.IUser;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.UserException;

/**
 * 
 * 
 ******************************************* 
 * <b style="font-family:微软雅黑"><small>Description:用户对象数据缓存提供者</small></b> </br>
 * <b style="font-family:微软雅黑"><small>HISTORY</small></b></br> <b
 * style="font-family:微软雅黑"><small> ID DATE PERSON REASON</small></b><br>
 ******************************************** 
 * <div style="font-family:微软雅黑,font-size:70%"> 1 2012-08-30 钟庭杰 新增 </div>
 ******************************************** 
 */
public class UserCacheProvider implements ITTLCacheProvider<IUser> {

	private IUserDao userDao;

	public Date getLastModifyTime() {
		return userDao.getLastModifyTime();
	}

	/**
	 * 从数据库加载数据到缓存中
	 * 
	 * 如果要在加入缓存之前附加一些数据，在此方法中操作
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-28 上午9:06:55
	 * @see com.deppon.foss.framework.cache.provider.ILazyCacheProvider#get(java.lang.Object)
	 */
	public IUser get(String key) {
		UserEntity user = userDao.getUserWithRoleIdAndOrgIdById(key);
		if (user != null) {
			EmployeeEntity emp = userDao.getUserEmployeeInfoByCode(user.getUserName());
			user.setEmployee(emp);
			if (emp != null) {
				OrgAdministrativeInfoEntity org = emp.getDepartment();
				if (org != null) {
					// 给用户的“人员”的部门的“财务组织”加上名称
					orgAdministrativeInfoService.attachSubsidiaryName(org);
				}
			}else{
				// 设置用户直属部门为默认部门
				if (emp == null) {
					throw new UserException(UserException.CURRENT_USER_EMP_NULL);
				}
				@SuppressWarnings("unused")
				OrgAdministrativeInfoEntity dept = emp.getDepartment();
				if (dept == null) {
					throw new UserException(UserException.CURRENT_USER_EMP_ORG_NULL);
				}
			}
		}else{
			throw new UserException(UserException.CURRENT_USER_NO_ROLE);
		}
		return user;
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

}
