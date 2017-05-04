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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/EmployeeAction.java
 * 
 * FILE NAME        	: EmployeeAction.java
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
package com.deppon.foss.module.base.baseinfo.server.action;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.EmployeeVo;

/**
 * 人员 action
 * 
 * 只提供查询
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-18 上午9:47:35
 */
public class EmployeeAction extends AbstractAction {
	/**
	 * 根据编码code查询人员
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-17 下午6:15:25
	 */
	@JSON
	public String queryEmployeeByEmpCode() {
		if (employeeVo == null
				|| employeeVo.getEmployeeDetail() == null
				|| StringUtils.isEmpty(employeeVo.getEmployeeDetail()
						.getEmpCode())) {
			return returnError("员工编码为空！，不能执行查询操作！");
		}
		employeeVo.setEmployeeDetail(employeeService
				.queryEmployeeByEmpCode(employeeVo.getEmployeeDetail()
						.getEmpCode()));

		return returnSuccess();
	}

	/**
	 * 动态条件，模糊查询
	 * 
	 * 部门的标杆编码如果不为空，则为精确查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-21 上午9:06:41
	 */
	@JSON
	public String queryEmployeeByEntity() {
		if (employeeVo == null) {
			employeeVo = new EmployeeVo();
		}

		EmployeeEntity entityView = employeeVo.getEmployeeDetail();
		EmployeeEntity entityCondition = new EmployeeEntity();
		entityCondition.setEmpCode(entityView.getEmpCode());
		entityCondition.setEmpName(entityView.getEmpName());
		entityCondition.setTitle(entityView.getTitle());
		entityCondition.setPhone(entityView.getPhone());
		entityCondition.setUnifieldCode(entityView.getUnifieldCode());
		entityCondition.setStatus(entityView.getStatus());

		// 返回的结果显示在表格中：
		employeeVo.setEmployeeList(employeeService.queryEmployeeByEntity(
				entityCondition, start, limit));
		totalCount = employeeService
				.queryEmployeeByEntityCount(entityCondition);
		return returnSuccess();
	}

	/**
	 * 
	 * 用户部门信息功能查询员工信息（只查询有用户信息的员工）
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-2-28 下午7:29:23
	 */
	public String queryEmployeeAndUserByEntity() {
		// 校验查询VO是否为空
		if (employeeVo == null) {
			return returnError("查询参数信息为空，不能执行查询操作！");
		}
		EmployeeEntity entityView = employeeVo.getEmployeeDetail();
		EmployeeEntity entityCondition = new EmployeeEntity();
		// 设置(去除前后端)员工工号
		entityCondition.setEmpCode(entityView.getEmpCode().trim());
		// 设置员工名称
		entityCondition.setEmpName(entityView.getEmpName().trim());
		// 设置员工职位
		entityCondition.setTitle(entityView.getTitle());
		// 设置员工电话
		entityCondition.setPhone(entityView.getPhone().trim());
		// 设置部门标杆编码
		entityCondition.setUnifieldCode(entityView.getUnifieldCode());
		// 设置员工状态
		entityCondition.setStatus(entityView.getStatus());
		// 获取总条数
		totalCount = employeeService
				.queryEmployeeAndUserCountByEntity(entityCondition);
		if (totalCount > 0) {
			// 返回的结果显示在表格中：
			employeeVo
					.setEmployeeList(employeeService
							.queryEmployeeAndUserByEntity(entityCondition,
									start, limit));
		}
		return returnSuccess();
	}

	/**
	 * 下面是变量的声明
	 */
	private static final long serialVersionUID = -4387627988772020011L;

	// 用于注入人员业务服务实现类
	private IEmployeeService employeeService;

	private EmployeeVo employeeVo;

	/*
	 * =================================================================
	 * 下面是get,set方法：
	 */

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public EmployeeVo getEmployeeVo() {
		return employeeVo;
	}

	public void setEmployeeVo(EmployeeVo employeeVo) {
		this.employeeVo = employeeVo;
	}

}
