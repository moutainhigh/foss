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
 * PROJECT NAME	: pkp-gui
 * 
 * FILE PATH        	: authorization/src/main/java/com/deppon/foss/module/authorization/client/dao/IUserDao.java
 * 
 * FILE NAME        	: IUserDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.authorization.client.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;




/**
 * 用户数据访问接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:35:10,content:TODO </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:35:10
 * @since
 * @version
 */

public interface IUserDao {
	/**
	 * 查询用户对象
	 * @param id  查询条件
	 * @return 用户对象
	 */
	UserEntity getById(String id);
	/**
	 * 查询所有用户信息
	 * @param user  查询条件
	 * @return 用户对象List
	 */
	List<UserEntity> getAll(UserEntity user);

	/**
	 * 更新用户对象
	 * @param user
	 */
	void update(UserEntity user);

	/**
	 * 新增用户对象
	 * @param user
	 */
	void insert(UserEntity user);

	/**
	 * 通过userId删除用户对象
	 * @param userId
	 */
	void deleteById(String userId);



	/**
	 * 通过UserId得到用户所拥有的角色ID与功能编码
	 * @param userId
	 * @return
	 */
	UserEntity getUserWithRoleIdAndFunctionCodeById(String userId);

	

	/**
	 * 通过用户的登录名，得到用户对象
	 * @param loginName
	 * @return
	 */
	UserEntity getByLoginName(String loginName);




}