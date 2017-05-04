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
 * FILE PATH        	: authorization/src/main/java/com/deppon/foss/module/authorization/client/dao/IFunctionDao.java
 * 
 * FILE NAME        	: IFunctionDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.authorization.client.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.authorization.shared.domain.Function;

/**
 * T_ORG_FUNCTION信息DAO层接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:34:03,content:TODO </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:34:03
 * @since
 * @version
 */
public interface IFunctionDao {
	
	/**
	 * 查询所有功能
	 * @return
	 */
	List<Function> getAll();
	
	/**
	 * 查询所有功能
	 * @param function 查询条件信息类
	 * @return 功能对象List
	 */
	List<Function> getAll(Function function);
	
	/**
	 * 分页查询所有功能
	 * @param function 查询条件信息类
	 * @param limit 查询条数
	 * @param start 查询的起始位置
	 * @return 功能对象List
	 */
	List<Function> getAll(Function function, int limit, int start);
	
	/**
	 * 通过一些功能ID得到一个功能对象的集合
	 * @param functionIds
	 * @return
	 */
	List<Function> getByIds(List<String> functionIds);
	
	/**
	 * 通过功能编码得到功能对象
	 * @param functionCode
	 * @return
	 */
	Function getByCode(String functionCode);
	
	/**
	 * 查找属于该功能的子功能
	 * @param function 功能查找信息
	 * @return
	 */
	List<Function> getDirectChildFunctions(Function function);
	
	/**
	 * 更新功能信息
	 * @param function 功能信息
	 */
	void update(Function function);
	
	/**
	 * 保存功能信息
	 * @param function 功能信息
	 */
	void insert(Function function);

	/**
	 * 通过ID，得到功能对象
	 * @param id
	 * @return
	 */
	Function getById(String id);
	
	/**
	 * 得到功能树的根节点
	 */
	Function getRoot();
	
	/**
	 * 通过ID获得当前ID所对应的所有的子节点
	 * @param id
	 * @return
	 */
	Collection<Function> getAllChildFunctionById(String id);
	
	/**
	 * 统计功能的条数
	 * @param function
	 * @return
	 */
	Long count(Function function);
	
	/**
	 * 查询最后更新时间
	 * @return
	 */
	Date getLastModifyTime();

	/**
	 * 通过URI得到当前URL定位的功能树下的所有功能
	 * @param URI
	 * @return
	 */
	Collection<Function> getAllChildFunctionByURI(String uri);
	
	/**
	 * 通过功能编码得到当前功能编码定位的功能下的所有功能
	 * @param functionCode
	 * @return
	 */
	Collection<Function> getAllChildFunctionByCode(String functionCode);

	/**
	 * 通过ID删除功能信息
	 * @param id
	 */
	void deleteById(String id);
	
	/**
	 * 通过功能对像的ID集合，删除满足条件的功能信息
	 * @param functionIds
	 */
	void deleteByIds(List<String> functionIds);
	

	/**
	 * 删除功能信息集合中的所有功能信息
	 * @param functionList 功能信息集合
	 */
	void deleteFunctions(List<Function> functionList);

	/**
	 * 通过父功能ID删除父功能下的所有子功能
	 * @param parentId
	 */
	void deleteByParentId(String parentId);

	/**
	 * 通过自增长序列得到序列的值
	 * @return newId
	 */
	String getNewId();

	/**
	 * 通过父功能编码得到所有子功能中编码值最大的功能对象
	 * @param parentCode 父节点编码
	 */
	Function getMaxCodeFunctionByParentCode(String parentCode);

	/**
	 * 通过角色ID，得到功能对象集合
	 * @param roleId
	 * @return
	 */
	List<Function> getAllByRoleId(String roleId);

	/**
	 * 通过角色ID，得到功能对象ID集合
	 * @param roleId
	 * @return
	 */
	List<String> getAllIdByRoleId(String roleId);

	
	/**
	 * 更新该页面的页面元素的状态
	 * @param functionCode 功能编号
	 */
	void updateChild(Function functionChild);
}