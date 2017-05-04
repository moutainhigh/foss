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
 * FILE PATH        	: authorization/src/main/java/com/deppon/foss/module/authorization/client/service/DepartmentService.java
 * 
 * FILE NAME        	: DepartmentService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.authorization.client.service;

import java.util.List;

import org.mybatis.guice.transactional.Transactional;

import com.deppon.foss.module.authorization.client.dao.IDepartmentDao;
import com.deppon.foss.module.organization.shared.domain.Department;
import com.google.inject.Inject;

/**
 * DepartmentService
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:39:03,content:TODO </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:39:03
 * @since
 * @version
 */
public class DepartmentService implements IDepartmentService {

	@Inject
	IDepartmentDao departmentDao;

	@Transactional
	@Override
	public Department getById(String id) {
		// TODO Auto-generated method stub
		return departmentDao.getById(id);
	}

	@Transactional
	@Override
	public void insertDepartment(Department department) {
		// TODO Auto-generated method stub
		departmentDao.insertDepartment(department);
	}

	@Transactional
	public void updateDepartment(Department department) {
		departmentDao.updateDepartment(department);
	}

	@Transactional
	public void saveOrUpdate(Department department) {
		try {
			departmentDao.insertDepartment(department);
		} catch (Exception e) {
			departmentDao.updateDepartment(department);
		}
	}

	@Transactional
	@Override
	public List<Department> getAll() {
		// TODO Auto-generated method stub
		return departmentDao.getAll();
	}

}