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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonEmpSelectorAction.java
 * 
 * FILE NAME        	: CommonEmpSelectorAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.action
 * FILE    NAME: CommonEmpSelectorAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonEmpService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.EmployeeVo;

/**
 * 公共选择器--人员查询ACTION.
 *
 * @author panGuangJun
 * @date 2012-11-30 上午11:32:01
 */
public class CommonEmpSelectorAction extends AbstractAction implements IQueryAction{
	
	/** The common emp service. */
	private ICommonEmpService commonEmpService;
	
	/** The employee vo. */
	private EmployeeVo employeeVo = new EmployeeVo();
	
	/** The Constant serialVersionUID.  */
	private static final long serialVersionUID = 4648931810985946081L;

	/**
	 * 人员公共组件查询方法.
	 *
	 * @return the string
	 * @author panGuangJun
	 * @date 2012-11-30 上午11:36:29
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@JSON
	@Override
	public String query() {
		 List<EmployeeEntity> emps = commonEmpService.queryEmpByCondition(employeeVo,start,limit);
		 employeeVo.setEmployeeList(emps);
		 totalCount=commonEmpService.countEmpByCondition(employeeVo);
		 return returnSuccess();
	}

	/**
	 * Sets the common emp service.
	 *
	 * @param commonEmpService the new common emp service
	 */
	public void setCommonEmpService(ICommonEmpService commonEmpService) {
		this.commonEmpService = commonEmpService;
	}

	/**
	 * Gets the employee vo.
	 *
	 * @return the employee vo
	 */
	public EmployeeVo getEmployeeVo() {
		return employeeVo;
	}

	/**
	 * Sets the employee vo.
	 *
	 * @param employeeVo the new employee vo
	 */
	public void setEmployeeVo(EmployeeVo employeeVo) {
		this.employeeVo = employeeVo;
	}

}
