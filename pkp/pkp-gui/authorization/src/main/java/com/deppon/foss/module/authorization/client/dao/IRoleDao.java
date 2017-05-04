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
 * FILE PATH        	: authorization/src/main/java/com/deppon/foss/module/authorization/client/dao/IRoleDao.java
 * 
 * FILE NAME        	: IRoleDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.authorization.client.dao;


import java.util.Date;
import java.util.List;

import com.deppon.foss.module.authorization.shared.domain.Role;


/**
 * 角色数据处理方法
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:34:42,content:TODO </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:34:42
 * @since
 * @version
 */
public interface IRoleDao {

	/**
	 * 查询所有角色
	 * @return
	 */
	List<Role> getAll();
	
	/**
	 * 提供缓存加载所有的角色对象与角色所对应的功能ID
	 * @return
	 */
	List<Role> getAllRole();
	
	/**
	 * 查询所有角色
	 * @param role 查询条件信息类
	 * @return 角色对象List
	 */
	List<Role> getAll(Role role);
	

	/**
	 * 分页查询所有角色
	 * @param role 查询条件信息类
	 * @param limit 查询条数
	 * @param start 查询的起始位置
	 * @return 角色对象List
	 */
	List<Role> getAll(Role role, int limit, int start);
	
	/**
	 * 通过一些角色ID得到一个角色对象的集合
	 * @param roleIds
	 * @return
	 */
	List<Role> getByIds(List<String> roleIds);
	
	/**
	 * 通过ID，得到功能对象
	 * @param id
	 * @return
	 */
	Role getById(String id);

	/**
	 * 更新角色信息
	 * @param role 角色信息
	 */
	void update(Role role);

	/**
	 * 保存角色信息
	 * @param role 角色信息
	 */
	void insert(Role role);
	
	/**
	 * 保存角色权限信息
	 * @param roleId
	 * @param functionId
	 */
	void insert(String roleId, String functionId);

	/**
	 * 通过ID删除角色信息
	 * @param id
	 */
	void deleteById(String id);
	
	/**
	 * 根据角色ID，删除权限角色表中的数据
	 * @param id
	 */
	void deleteFunctionRoleById(String id);
	
	/**
	 * 根据角色ID，删除授权用户角色表中的数据
	 * @param roleId
	 */
	void deleteUserAuthRoleById(String roleId);

	/**
	 * 统计角色的条数
	 * @param function
	 * @return
	 */
	Long count(Role role);
	
	/**
	 * 查询最后更新时间
	 * @return
	 */
	Date getLastModifyTime();




	/**
	 * 根据角色名称，得到角色的ID
	 * @param roleName
	 * @return
	 */
	List<String> getIdByName(String roleName);


}