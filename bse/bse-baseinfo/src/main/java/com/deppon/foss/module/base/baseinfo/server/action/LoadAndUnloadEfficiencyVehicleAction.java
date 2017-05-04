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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/LoadAndUnloadEfficiencyVehicleAction.java
 * 
 * FILE NAME        	: LoadAndUnloadEfficiencyVehicleAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleTypeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadEfficiencyVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyVehicleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.LoadAndUnloadEfficiencyVehicleVo;
import com.deppon.foss.module.base.baseinfo.server.action.actionutil.GainEmployee;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * 新增装卸车标准-车-时间 action
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-18 上午9:48:45
 */
public class LoadAndUnloadEfficiencyVehicleAction extends AbstractAction {

	/**
	 * 新增装卸车标准-车-时间
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-21 上午9:06:41
	 */
	@JSON
	public String addLoadAndUnloadEfficiencyVehicle() {
		if (loadAndUnloadEfficiencyVehicleVo == null
				|| loadAndUnloadEfficiencyVehicleVo
						.getLoadAndUnloadEfficiencyVehicleEntity() == null
				|| StringUtils
						.isBlank(loadAndUnloadEfficiencyVehicleVo
								.getLoadAndUnloadEfficiencyVehicleEntity()
								.getOrgCode())
				|| StringUtils.isBlank(loadAndUnloadEfficiencyVehicleVo
						.getLoadAndUnloadEfficiencyVehicleEntity()
						.getVehicleTypeLength())) {
			return returnError("部门编码不能为空或者车型长度不能为空");
		}

		try {
			LoadAndUnloadEfficiencyVehicleEntity entityView = loadAndUnloadEfficiencyVehicleVo
					.getLoadAndUnloadEfficiencyVehicleEntity();

			// 设置操作用户的用户编码
			String operUserCode = GainEmployee.getOperUserCode();
			entityView.setCreateUser(operUserCode);
			entityView.setModifyUser(operUserCode);

			LoadAndUnloadEfficiencyVehicleEntity entityCondition = loadAndUnloadEfficiencyVehicleService
					.addLoadAndUnloadEfficiencyVehicle(entityView);
			if (entityCondition == null) {
				return returnError("保存失败");
			}
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}

	/**
	 * 删除装卸车标准-车-时间
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-21 上午9:06:41
	 */
	@JSON
	public String deleteLoadAndUnloadEfficiencyVehicle() {
		if (loadAndUnloadEfficiencyVehicleVo == null
				|| loadAndUnloadEfficiencyVehicleVo
						.getLoadAndUnloadEfficiencyVehicleEntity() == null
				|| StringUtils.isBlank(loadAndUnloadEfficiencyVehicleVo
						.getLoadAndUnloadEfficiencyVehicleEntity()
						.getVirtualCode())) {
			return returnError("虚拟编码不能为空");
		}

		try {
			LoadAndUnloadEfficiencyVehicleEntity entityView = loadAndUnloadEfficiencyVehicleVo
					.getLoadAndUnloadEfficiencyVehicleEntity();
			String operUserCode = GainEmployee.getOperUserCode();
			entityView.setModifyUser(operUserCode);

			LoadAndUnloadEfficiencyVehicleEntity entityDelete = loadAndUnloadEfficiencyVehicleService
					.deleteLoadAndUnloadEfficiencyVehicle(entityView);

			if (entityDelete == null) {
				return returnError("作废装卸车标准时间失败");
			}
		} catch (BusinessException e) {
			LOGGER.error("作废装卸车标准时间失败", e);
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 删除装卸车标准-车-时间
	 * 
	 * 批量删除
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-21 上午9:06:41
	 */
	@JSON
	public String deleteLoadAndUnloadEfficiencyVehicleMore() {
		if (loadAndUnloadEfficiencyVehicleVo == null
				|| loadAndUnloadEfficiencyVehicleVo
						.getLoadAndUnloadEfficiencyVehicleEntity() == null
				|| StringUtils.isBlank(loadAndUnloadEfficiencyVehicleVo
						.getLoadAndUnloadEfficiencyVehicleEntity()
						.getVirtualCode())) {
			return returnError("虚拟编码不能为空");
		}

		String[] codes = loadAndUnloadEfficiencyVehicleVo
				.getLoadAndUnloadEfficiencyVehicleEntity().getVirtualCode()
				.split(SymbolConstants.EN_COMMA);

		try {
			String operUserCode = GainEmployee.getOperUserCode();

			LoadAndUnloadEfficiencyVehicleEntity entityDelete = loadAndUnloadEfficiencyVehicleService
					.deleteLoadAndUnloadEfficiencyVehicleMore(codes,
							operUserCode);

			if (entityDelete == null) {
				return returnError("作废装卸车人力效率标准失败");
			}
		} catch (BusinessException e) {
			LOGGER.error("作废装卸车人力效率标准失败", e);
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 修改 装卸车标准-车-时间
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-21 上午9:06:41
	 */
	@JSON
	public String updateLoadAndUnloadEfficiencyVehicle() {
		if (loadAndUnloadEfficiencyVehicleVo == null
				|| loadAndUnloadEfficiencyVehicleVo
						.getLoadAndUnloadEfficiencyVehicleEntity() == null
				|| StringUtils
						.isBlank(loadAndUnloadEfficiencyVehicleVo
								.getLoadAndUnloadEfficiencyVehicleEntity()
								.getOrgCode())) {
			return returnError("部门编码不能为空");
		}

		LoadAndUnloadEfficiencyVehicleEntity entityView = loadAndUnloadEfficiencyVehicleVo
				.getLoadAndUnloadEfficiencyVehicleEntity();
		LoadAndUnloadEfficiencyVehicleEntity entityQuery = loadAndUnloadEfficiencyVehicleService
				.queryLoadAndUnloadEfficiencyVehicleByVirtualCode(entityView
						.getVirtualCode());
		entityQuery = this.transEntity(entityView, entityQuery);

		try {
			// 设置操作用户的用户编码
			UserEntity emp = FossUserContext.getCurrentUser();
			String operUserCode = null;
			if (emp != null && StringUtils.isNotBlank(emp.getEmpCode())) {
				operUserCode = emp.getEmpCode();
			}
			entityQuery.setModifyUser(operUserCode);

			LoadAndUnloadEfficiencyVehicleEntity entityResult = loadAndUnloadEfficiencyVehicleService
					.updateLoadAndUnloadEfficiencyVehicle(entityQuery);
			if (entityResult == null) {
				return returnError("修改失败");
			}
		} catch (BusinessException e) {

			return returnError(e.getMessage());
		}

		return returnSuccess();
	}

	/**
	 * 动态条件，精确查询
	 * 
	 * 如果属性不为空，则为精确查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-21 上午9:06:41
	 */
	@JSON
	public String queryLoadAndUnloadEfficiencyVehicleExactByEntity() {
		if (loadAndUnloadEfficiencyVehicleVo == null) {
			loadAndUnloadEfficiencyVehicleVo = new LoadAndUnloadEfficiencyVehicleVo();
		}

		LoadAndUnloadEfficiencyVehicleEntity entityCondition = loadAndUnloadEfficiencyVehicleVo
				.getLoadAndUnloadEfficiencyVehicleEntity();

		if (limit == 0) {
			limit = Integer.MAX_VALUE;
		}
		// 返回的结果显示在表格中：
		loadAndUnloadEfficiencyVehicleVo
				.setLoadAndUnloadEfficiencyVehicleList(loadAndUnloadEfficiencyVehicleService
						.queryLoadAndUnloadEfficiencyVehicleExactByEntity(
								entityCondition, start, limit));
		totalCount = loadAndUnloadEfficiencyVehicleService
				.queryLoadAndUnloadEfficiencyVehicleExactByEntityCount(entityCondition);
		return returnSuccess();
	}

	/**
	 * 查询车长列表
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-21 上午9:06:41
	 */
	@JSON
	public String queryLeasedVehicleTypeAll() {
		if (loadAndUnloadEfficiencyVehicleVo == null) {
			loadAndUnloadEfficiencyVehicleVo = new LoadAndUnloadEfficiencyVehicleVo();
		}
		VehicleTypeEntity vehicleType = new VehicleTypeEntity();
		List<VehicleTypeEntity> entityResults = leasedVehicleTypeService
				.queryDistinctLeasedVehicleTypeListBySelectiveCondition(
						vehicleType, NumberConstants.NUMERAL_ZORE,
						Integer.MAX_VALUE);

		// 返回的结果显示在表格中：
		loadAndUnloadEfficiencyVehicleVo
				.setVehicleTypeEntityList(entityResults);
		return returnSuccess();
	}

	/**
	 * 下面是工具方法
	 */

	/**
	 * 将从界面获取的数据与数据库中的源数据整合一下
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-21 上午10:40:15
	 * 
	 * @param entityView
	 *            从界面获取的源数据
	 * @param entityQuery
	 *            从数据库查出来的，要存入数据库的目标数据
	 */
	public LoadAndUnloadEfficiencyVehicleEntity transEntity(
			LoadAndUnloadEfficiencyVehicleEntity entityView,
			LoadAndUnloadEfficiencyVehicleEntity entityQuery) {
		if (entityQuery == null) {
			entityQuery = new LoadAndUnloadEfficiencyVehicleEntity();
		}
		// 带高栏敞篷车装车标准用时
		entityQuery.setGlCpLoadHours(entityView.getGlCpLoadHours());
		// 带高栏敞篷车装车标准用分
		entityQuery.setGlCpLoadMins(entityView.getGlCpLoadMins());
		// 带高栏敞篷车卸车标准用时
		entityQuery.setGlCpUnloadHours(entityView.getGlCpUnloadHours());
		// 带高栏敞篷车卸车标准用分
		entityQuery.setGlCpUnloadMins(entityView.getGlCpUnloadMins());
		// 不带高栏敞篷车装车标准用时
		entityQuery.setNglCpLoadHours(entityView.getNglCpLoadHours());
		// 不带高栏敞篷车装车标准用分
		entityQuery.setNglCpLoadMins(entityView.getNglCpLoadMins());
		// 不带高栏敞篷车卸车标准用时
		entityQuery.setNglCpUnloadHours(entityView.getNglCpUnloadHours());
		// 不带高栏敞篷车卸车标准用分
		entityQuery.setNglCpUnloadMins(entityView.getNglCpUnloadMins());
		// 非敞篷车装车标准用时
		entityQuery.setNcpLoadHours(entityView.getNcpLoadHours());
		// 非敞篷车装车标准用分
		entityQuery.setNcpLoadMins(entityView.getNcpLoadMins());
		// 非敞篷车卸车标准用时
		entityQuery.setNcpUnloadHours(entityView.getNcpUnloadHours());
		// 非敞篷车卸车标准用分
		entityQuery.setNcpUnloadMins(entityView.getNcpUnloadMins());

		return entityQuery;
	}

	/**
	 * 下面是变量的声明
	 */
	private static final long serialVersionUID = -4387627988772020011L;

	// 用于注入装卸车效率标准业务服务实现类
	private ILoadAndUnloadEfficiencyVehicleService loadAndUnloadEfficiencyVehicleService;

	private LoadAndUnloadEfficiencyVehicleVo loadAndUnloadEfficiencyVehicleVo;

	private ILeasedVehicleTypeService leasedVehicleTypeService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LoadAndUnloadEfficiencyVehicleAction.class);

	/*
	 * =================================================================
	 * 下面是get,set方法：
	 */

	public void setLoadAndUnloadEfficiencyVehicleService(
			ILoadAndUnloadEfficiencyVehicleService loadAndUnloadEfficiencyVehicleService) {
		this.loadAndUnloadEfficiencyVehicleService = loadAndUnloadEfficiencyVehicleService;
	}

	public LoadAndUnloadEfficiencyVehicleVo getLoadAndUnloadEfficiencyVehicleVo() {
		return loadAndUnloadEfficiencyVehicleVo;
	}

	public void setLoadAndUnloadEfficiencyVehicleVo(
			LoadAndUnloadEfficiencyVehicleVo loadAndUnloadEfficiencyVehicleVo) {
		this.loadAndUnloadEfficiencyVehicleVo = loadAndUnloadEfficiencyVehicleVo;
	}

	public void setLeasedVehicleTypeService(
			ILeasedVehicleTypeService leasedVehicleTypeService) {
		this.leasedVehicleTypeService = leasedVehicleTypeService;
	}

}
