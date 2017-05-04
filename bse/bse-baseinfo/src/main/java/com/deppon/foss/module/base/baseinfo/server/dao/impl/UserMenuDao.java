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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/UserMenuDao.java
 * 
 * FILE NAME        	: UserMenuDao.java
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
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.List;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IUserMenuDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserMenuEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:用户菜单数据访问</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1 2012-11-6 钟庭杰    新增
* </div>  
********************************************
 */
public class UserMenuDao extends iBatis3DaoImpl implements
	IUserMenuDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
	    + ".userMenu.";

    /**
     * 插入用户菜单
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserMenuDao#insertUserMenu(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserMenuEntity)
     * insertUserMenu
     * @param entity
     * @return
     * @since:
     */
	@Override
	public UserMenuEntity insertUserMenu(UserMenuEntity entity) {
		// 请求合法性验证：
		if (null == entity) {
		    return entity;
		}
		Date now = new Date();
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateDate(now);
		entity.setModifyDate(now);
		entity.setModifyUser(entity.getCreateUser());
		entity.setActive(FossConstants.ACTIVE);
		int result = getSqlSession().insert(NAMESPACE + "insertUserMenu", entity);
		return result > 0 ? entity : null;
	}

	/**
	 * 根据ID，删除用户菜单数据
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserMenuDao#deleteUserMenuById(java.lang.String)
	 * deleteUserMenuById
	 * @param id
	 * @return
	 * @since:
	 */
	@Override
	public int deleteUserMenuById(String id) {
		return getSqlSession().delete(NAMESPACE + "deleteUserMenuById", id);
	}

	/**
	 * 根据ID集合，批量删除用户菜单数据
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserMenuDao#deleteUserMenuByIds(java.util.List)
	 * deleteUserMenuByIds
	 * @param ids
	 * @return
	 * @since:
	 */
	@Override
	public int deleteUserMenuByIds(List<String> ids) {
		return getSqlSession().delete(NAMESPACE + "deleteUserMenuByIds", ids);
	}

	/**
	 * 根据用户编码删除用户拥有的菜单
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserMenuDao#deleteUserMenuByUserCode(java.lang.String)
	 * deleteUserMenuByUserCode
	 * @param userCode
	 * @return
	 * @since:
	 */
	@Override
	public int deleteUserMenuByUserCode(String userCode) {
		return getSqlSession().delete(NAMESPACE + "deleteUserMenuByUserCode", userCode);
	}

	/**
	 * 根据资源编码删除用户拥有的菜单
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserMenuDao#deleteUserMenuByResourceCode(java.lang.String)
	 * deleteUserMenuByResourceCode
	 * @param resourceCode
	 * @return
	 * @since:
	 */
	@Override
	public int deleteUserMenuByResourceCode(String resourceCode) {
		return getSqlSession().delete(NAMESPACE + "deleteUserMenuByResourceCode", resourceCode);
	}

	/**
	 * 根据用户编码查询用户菜单对象
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserMenuDao#getUserMenuByUserCode(java.lang.String)
	 * getUserMenuByUserCode
	 * @param userCode
	 * @return
	 * @since:
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserMenuEntity> getUserMenuByUserCode(String userCode) {
		return getSqlSession().selectList(NAMESPACE + "getUserMenuByUserCode", userCode);
	}

	/**
	 * 根据资源编码查询用户菜单对象
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserMenuDao#getUserMenuByResourceCode(java.lang.String)
	 * getUserMenuByResourceCode
	 * @param resourceCode
	 * @return
	 * @since:
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserMenuEntity> getUserMenuByResourceCode(String resourceCode) {
		return getSqlSession().selectList(NAMESPACE + "getUserMenuByResourceCode", resourceCode);
	}
}
