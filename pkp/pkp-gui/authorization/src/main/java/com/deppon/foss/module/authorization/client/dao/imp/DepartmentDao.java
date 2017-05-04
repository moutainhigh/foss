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
 * FILE PATH        	: authorization/src/main/java/com/deppon/foss/module/authorization/client/dao/imp/DepartmentDao.java
 * 
 * FILE NAME        	: DepartmentDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.authorization.client.dao.imp;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.authorization.client.dao.IDepartmentDao;
import com.deppon.foss.module.organization.shared.domain.Department;


/**
 * 部门访问DAO层实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:32:32,content:TODO </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:32:32
 * @since
 * @version
 */
public class DepartmentDao implements IDepartmentDao {

	private SqlSession sqlSession;

	/**
	 * sql工具提供方法
	 */
	@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	/**
	 * 通过ID获取部门信息
	 */
	@Override
	public Department getById(String id) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("foss.department.getById",id);
	}

	/**
	 * 通过ID获取部门信息
	 */
	@Override
	public void insertDepartment(Department department) {
		// TODO Auto-generated method stub
		sqlSession.insert("foss.department.insertDepartment", department);
	}


	public void updateDepartment(Department department) {
		sqlSession.update("foss.department.updateDepartment", department);
	}

	
	public void saveOrUpdate(Department department) {
		sqlSession.insert("foss.department.saveOrUpdate", department);
	}

	@Override
	public List<Department> getAll() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("foss.department.getAll");
	}

}