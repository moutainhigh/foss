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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/UserMenuService.java
 * 
 * FILE NAME        	: UserMenuService.java
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
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IUserMenuDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserMenuService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserMenuEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ResourceException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.UserException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:用户菜单业务访问</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1 2012-11-6 钟庭杰    新增
* </div>  
********************************************
 */
public class UserMenuService implements IUserMenuService {
	
	private IUserMenuDao userMenuDao;

	public void setUserMenuDao(IUserMenuDao userMenuDao) {
		this.userMenuDao = userMenuDao;
	}

	/**
	 * 保存用户常用菜单
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IUserMenuService#saveUserMenu(java.lang.String, java.util.List)
	 * saveUserMenu
	 * @param userCode
	 * @param resouceCodes
	 * @throws UserException
	 * @throws ResourceException
	 * @since:
	 */
	@Override
	@Transactional
	public void saveUserMenu(String userCode, List<String> resouceCodes)
			throws UserException, ResourceException {
		if (userCode == null || "".equals(userCode.trim())) {
			throw new UserException(UserException.USER_CODE_NULL);
		}
		if (resouceCodes == null) {
			throw new ResourceException(
					ResourceException.RESOURCE_CODE_LIST_NULL);
		}
		userMenuDao.deleteUserMenuByUserCode(userCode);
		Date now = new Date();
		int displayOrder = 0;
		UserEntity currenUser = (UserEntity) (UserContext.getCurrentUser());
		for (String resourceCode : resouceCodes) {
			UserMenuEntity userMenu = new UserMenuEntity();
			userMenu.setId(UUIDUtils.getUUID());
			userMenu.setDisplayOrder(displayOrder++);
			userMenu.setUserCode(userCode);
			userMenu.setResourceCode(resourceCode);
			userMenu.setCreateDate(now);
			userMenu.setModifyDate(now);
			userMenu.setCreateUser(currenUser.getUserName());
			userMenu.setModifyUser(currenUser.getUserName());
			userMenu.setActive(FossConstants.ACTIVE);
			userMenuDao.insertUserMenu(userMenu);
		}
	}

	/**
	 * 通过用户编码查询用户常用菜单
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IUserMenuService#queryUserMenuByUserCode(java.lang.String)
	 * queryUserMenuByUserCode
	 * @param userCode
	 * @return
	 * @since:
	 */
	@Override
	public List<UserMenuEntity> queryUserMenuByUserCode(String userCode)
			throws UserException {
		if (userCode == null || "".equals(userCode.trim())) {
			throw new UserException(UserException.USER_CODE_NULL);
		}
		List<UserMenuEntity> userMenus = userMenuDao
				.getUserMenuByUserCode(userCode);
		return userMenus;
	}

}
