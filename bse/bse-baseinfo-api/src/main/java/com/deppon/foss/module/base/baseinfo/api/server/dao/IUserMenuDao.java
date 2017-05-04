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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IUserMenuDao.java
 * 
 * FILE NAME        	: IUserMenuDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserMenuEntity;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:用户菜单数据访问接口</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1 2012-11-6 钟庭杰    新增
* </div>  
********************************************
 */
public interface IUserMenuDao {
	
	/**
	 * 插入用户菜单
	 * insertUserResource
	 * @param entity
	 * @return
	 * @return UserMenuEntity
	 * @since:
	 */
	UserMenuEntity insertUserMenu(UserMenuEntity entity);

    /**
     * 根据ID，删除用户菜单数据
     * deleteUserResourceById
     * @param id
     * @return
     * @return int
     * @since:
     */
    int deleteUserMenuById(String id);

    /**
     * 根据ID集合，批量删除用户菜单数据
     * deleteUserMenuByIds
     * @param ids
     * @return
     * @return int
     * @since:
     */
    int deleteUserMenuByIds(List<String> ids);
	
    /**
     * 根据用户编码删除用户拥有的菜单
     * deleteUserMenuByUserCode
     * @param userCode
     * @return
     * @return int
     * @since:
     */
    int deleteUserMenuByUserCode(String userCode);
	
    /**
     * 根据资源编码删除用户拥有的菜单
     * deleteUserMenuByResourceCode
     * @param resourceCode
     * @return
     * @return int
     * @since:
     */
    int deleteUserMenuByResourceCode(String resourceCode);
	
    /**
     * 根据用户编码查询用户菜单对象
     * getUserMenuByUserCode
     * @param userCode
     * @return
     * @return List<UserMenuEntity>
     * @since:
     */
    List<UserMenuEntity> getUserMenuByUserCode(String userCode);	
	
	
    /**
     * 根据资源编码查询用户菜单对象
     * getUserMenuByResourceCode
     * @param resourceCode
     * @return
     * @return List<UserMenuEntity>
     * @since:
     */
    List<UserMenuEntity> getUserMenuByResourceCode(String resourceCode);
}
