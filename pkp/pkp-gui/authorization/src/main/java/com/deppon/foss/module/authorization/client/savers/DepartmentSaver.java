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
 * FILE PATH        	: authorization/src/main/java/com/deppon/foss/module/authorization/client/savers/DepartmentSaver.java
 * 
 * FILE NAME        	: DepartmentSaver.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.authorization.client.savers;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.client.component.sync.AbstractSyncDataSaver;
import com.deppon.foss.module.authorization.client.service.DepartmentService;
import com.deppon.foss.module.authorization.client.service.DepartmentServiceFactory;
import com.deppon.foss.module.organization.shared.domain.Department;


/**
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:38:09,content:TODO </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:38:09
 * @since
 * @version
 */
public class DepartmentSaver extends AbstractSyncDataSaver {
	/**
	 * 
	 * 功能：getSaveType
	 * 
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	@Override
	public Class<?> getSaveType() {
		// TODO Auto-generated method stub
		return Department.class;
	}

	/**
	 * 
	 * 功能：saveData
	 * 
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Date saveData(List<?> data) {
		// TODO Auto-generated method stub
		Date date = null;
		List<Department> departments = (List<Department>) data;
		for (Department department : departments) {

			DepartmentService departmentService = DepartmentServiceFactory
					.getDepartmentService();
			departmentService.saveOrUpdate(department);

		}
		return date;
	}

	/**
	 * 
	 * 功能：取区域ID
	 * 
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	@Override
	public String getRegionID() {
		return null;
	}

}