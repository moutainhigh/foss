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
 * FILE PATH        	: authorization/src/main/java/com/deppon/foss/module/authorization/client/dao/IAuthorizeDao.java
 * 
 * FILE NAME        	: IAuthorizeDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.authorization.client.dao;

import java.util.List;

import com.deppon.foss.module.authorization.shared.domain.Role;
 

/**
 * 授权用户角色与部门数据访问层
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:33:22,content:TODO </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:33:22
 * @since
 * @version
 */
public interface IAuthorizeDao {
	
	/**
	 * 通过用户ID查询得到待授权的角色信息集合
	 * @param userId
	 * @return
	 */
	List<Role> getAllChooesRoleByUserId(String userId);

	/**
	 * 通过用户ID查询得到已授权的角色信息集合
	 * @param userId
	 * @return
	 */
	List<Role> getAllAuthorizedRoleByUserId(String userId);

	/**
	 * 通过用户ID查询得到已授权的部门ID集合
	 * @param userId
	 * @return
	 */
	List<String> getAllDepartmentIdByUserId(String userId);

	/**
	 * 保存用户部门数据权限
	 * @param userId
	 * @param deptId
	 */
	void insertUserDeptAuth(String userId, String deptId);

	/**
	 * 保存用户授权角色
	 * @param userId
	 * @param roleId
	 */
	void insertUserRoleAuth(String userId, String roleId);

	/**
	 * 通过用户ID删除用户部门数据授权数据
	 * @param userId
	 */
	void deleteUserDeptAuthByUserId(String userId);

	/**
	 * 通过用户ID删除用户角色授权数据
	 * @param userId
	 */
	void deleteUserRoleAuthByUserId(String userId);
	
}