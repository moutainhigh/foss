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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/LoadAndUnloadEfficiencyTonAction.java
 * 
 * FILE NAME        	: LoadAndUnloadEfficiencyTonAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.base.baseinfo.server.action;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadEfficiencyTonService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyTonEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.LoadAndUnloadEfficiencyTonVo;
import com.deppon.foss.module.base.baseinfo.server.action.actionutil.GainEmployee;

/**
 * 新增装卸车标准-吨-时间 action
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-18 上午9:48:32
 */
public class LoadAndUnloadEfficiencyTonAction extends AbstractAction {

	/**
	 * 新增装卸车标准-吨-时间
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-21 上午9:06:41
	 */
	@JSON
	public String addLoadAndUnloadEfficiencyTon() {
		// 判断前台传入参数是否为空
		if (loadAndUnloadEfficiencyTonVo == null
				|| loadAndUnloadEfficiencyTonVo
						.getLoadAndUnloadEfficiencyTonEntity() == null
				|| StringUtils.isBlank(loadAndUnloadEfficiencyTonVo
						.getLoadAndUnloadEfficiencyTonEntity().getOrgCode())) {
			//校验失败则返回错误信息
			return returnError("部门编码不能为空");
		}

		try {
			//获取实体信息
			LoadAndUnloadEfficiencyTonEntity entityView = loadAndUnloadEfficiencyTonVo
					.getLoadAndUnloadEfficiencyTonEntity();

			// 设置操作用户的用户编码
			String operUserCode = GainEmployee.getOperUserCode();
			entityView.setCreateUser(operUserCode);
			entityView.setModifyUser(operUserCode);
            //执行新增操作
			LoadAndUnloadEfficiencyTonEntity entityCondition = loadAndUnloadEfficiencyTonService
					.addLoadAndUnloadEfficiencyTon(entityView);
			//新增失败，返回错误信息
			if (entityCondition == null) {
				return returnError("新增装卸车效率标准失败");
			}
			//发生异常则返回异常信息
		} catch (BusinessException e) {
			return returnError("新增装卸车效率标准失败");
		} catch (Exception e2) {
			return returnError("新增装卸车效率标准失败" + e2.getMessage(), e2);
		}
		return returnSuccess();
	}

	/**
	 * 删除装卸车标准-吨-时间
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-21 上午9:06:41
	 */
	@JSON
	public String deleteLoadAndUnloadEfficiencyTon() {
		// 判断前台传入参数是否为空
		if (loadAndUnloadEfficiencyTonVo == null
				|| loadAndUnloadEfficiencyTonVo
						.getLoadAndUnloadEfficiencyTonEntity() == null
				|| StringUtils.isBlank(loadAndUnloadEfficiencyTonVo
						.getLoadAndUnloadEfficiencyTonEntity().getOrgCode())) {
			//校验失败则返回错误信息
			return returnError("部门编码不能为空");
		}

		try {
			//获取实体信息
			LoadAndUnloadEfficiencyTonEntity entityView = loadAndUnloadEfficiencyTonVo
					.getLoadAndUnloadEfficiencyTonEntity();
			String operUserCode = GainEmployee.getOperUserCode();
			entityView.setModifyUser(operUserCode);
            //执行删除操作
			LoadAndUnloadEfficiencyTonEntity entityDelete = loadAndUnloadEfficiencyTonService
					.deleteLoadAndUnloadEfficiencyTon(entityView);
            //删除失败，则返回错误信息
			if (entityDelete == null) {
				return returnError("作废装卸车人力效率标准失败");
			}
		} catch (BusinessException e) {
			LOGGER.error("作废装卸车人力效率标准失败", e);
			return returnError(e);
		} catch (Exception e2) {
			return returnError("作废装卸车人力效率标准失败" + e2.getMessage(), e2);
		}
		return returnSuccess();
	}

	/**
	 * 删除装卸车标准-吨-时间
	 * 
	 * 批量删除
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-21 上午9:06:41
	 */
	@JSON
	public String deleteLoadAndUnloadEfficiencyTonMore() {
		// 判断前台传入参数是否为空
		if (loadAndUnloadEfficiencyTonVo == null
				|| loadAndUnloadEfficiencyTonVo
						.getLoadAndUnloadEfficiencyTonEntity() == null
				|| StringUtils.isBlank(loadAndUnloadEfficiencyTonVo
						.getLoadAndUnloadEfficiencyTonEntity().getOrgCode())) {
			//校验失败则返回错误信息
			return returnError("部门编码不能为空");
		}
       //获取多个行政区域编码
		String[] codes = loadAndUnloadEfficiencyTonVo
				.getLoadAndUnloadEfficiencyTonEntity().getOrgCode()
				.split(SymbolConstants.EN_COMMA);
		try {
			String operUserCode = GainEmployee.getOperUserCode();
            //执行删除操作
			LoadAndUnloadEfficiencyTonEntity entityDelete = loadAndUnloadEfficiencyTonService
					.deleteLoadAndUnloadEfficiencyTonMore(codes, operUserCode);
            //删除失败，返回错误信息
			if (entityDelete == null) {
				return returnError("作废装卸车人力效率标准失败");
			}
		} catch (BusinessException e) {
			LOGGER.error("作废装卸车人力效率标准失败", e);
			return returnError(e);
		} catch (Exception e2) {
			return returnError("作废装卸车人力效率标准失败" + e2.getMessage(), e2);
		}
		return returnSuccess();
	}

	/**
	 * 修改 装卸车标准-吨-时间
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-21 上午9:06:41
	 */
	@JSON
	public String updateLoadAndUnloadEfficiencyTon() {
		// 判断前台传入参数是否为空
		if (loadAndUnloadEfficiencyTonVo == null
				|| loadAndUnloadEfficiencyTonVo
						.getLoadAndUnloadEfficiencyTonEntity() == null
				|| StringUtils.isBlank(loadAndUnloadEfficiencyTonVo
						.getLoadAndUnloadEfficiencyTonEntity().getOrgCode())) {
			//校验失败则返回错误信息
			return returnError("部门编码不能为空");
		}
        //获取实体信息
		LoadAndUnloadEfficiencyTonEntity entityView = loadAndUnloadEfficiencyTonVo
				.getLoadAndUnloadEfficiencyTonEntity();
		//执行查询操作
		LoadAndUnloadEfficiencyTonEntity entityQuery = loadAndUnloadEfficiencyTonService
				.queryLoadAndUnloadEfficiencyTonByOrgCode(entityView
						.getOrgCode());
		//将需要被修改的数据置换为数据库数据
		entityQuery = this.transEntity(entityView, entityQuery);

		try {
			String operUserCode = GainEmployee.getOperUserCode();
			entityQuery.setModifyUser(operUserCode);
            //执行修改操作
			LoadAndUnloadEfficiencyTonEntity entityResult = loadAndUnloadEfficiencyTonService
					.updateLoadAndUnloadEfficiencyTon(entityQuery);
			//修改失败返回异常信息
			if (entityResult == null) {
				return returnError("修改装卸车人力效率标准失败");
			}

		} catch (BusinessException e) {
			LOGGER.error("修改装卸车人力效率标准失败", e);
			return returnError(e.getMessage());
		} catch (Exception e2) {
			return returnError("修改装卸车人力效率标准失败" + e2.getMessage(), e2);
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
	public String queryLoadAndUnloadEfficiencyTonExactByEntity() {
		// 判断前台传入参数是否为空
		if (loadAndUnloadEfficiencyTonVo == null) {
			loadAndUnloadEfficiencyTonVo = new LoadAndUnloadEfficiencyTonVo();
		}
		// 获取实体信息是否为空
		LoadAndUnloadEfficiencyTonEntity entityCondition = loadAndUnloadEfficiencyTonVo
				.getLoadAndUnloadEfficiencyTonEntity();

		if (limit == 0) {
			limit = Integer.MAX_VALUE;
		}
		// 返回的结果显示在表格中：
		loadAndUnloadEfficiencyTonVo
				.setLoadAndUnloadEfficiencyTonList(loadAndUnloadEfficiencyTonService
						.queryLoadAndUnloadEfficiencyTonExactByEntity(
								entityCondition, start, limit));
		//统计合计信息
		totalCount = loadAndUnloadEfficiencyTonService
				.queryLoadAndUnloadEfficiencyTonExactByEntityCount(entityCondition);
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
	public LoadAndUnloadEfficiencyTonEntity transEntity(
			LoadAndUnloadEfficiencyTonEntity entityView,
			LoadAndUnloadEfficiencyTonEntity entityQuery) {
		if (entityQuery == null) {
			entityQuery = new LoadAndUnloadEfficiencyTonEntity();
		}

		entityQuery.setOrgName(entityView.getOrgName());
		// 装车重量标准
		entityQuery.setLoadWeightStd(entityView.getLoadWeightStd());
		// 装车体积标准
		entityQuery.setLoadVolumeStd(entityView.getLoadVolumeStd());
		// 卸车重量标准
		entityQuery.setUnloadWeightStd(entityView.getUnloadWeightStd());
		// 卸车体积标准
		entityQuery.setUnloadVolumeStd(entityView.getUnloadVolumeStd());

		return entityQuery;
	}

	/**
	 * 下面是变量的声明
	 */
	private static final long serialVersionUID = -4387627988772020011L;

	// 用于注入装卸车效率标准业务服务实现类
	private ILoadAndUnloadEfficiencyTonService loadAndUnloadEfficiencyTonService;

	private LoadAndUnloadEfficiencyTonVo loadAndUnloadEfficiencyTonVo;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LoadAndUnloadEfficiencyTonAction.class);

	/*
	 * =================================================================
	 * 下面是get,set方法：
	 */

	public void setLoadAndUnloadEfficiencyTonService(
			ILoadAndUnloadEfficiencyTonService loadAndUnloadEfficiencyTonService) {
		this.loadAndUnloadEfficiencyTonService = loadAndUnloadEfficiencyTonService;
	}

	public LoadAndUnloadEfficiencyTonVo getLoadAndUnloadEfficiencyTonVo() {
		return loadAndUnloadEfficiencyTonVo;
	}

	public void setLoadAndUnloadEfficiencyTonVo(
			LoadAndUnloadEfficiencyTonVo loadAndUnloadEfficiencyTonVo) {
		this.loadAndUnloadEfficiencyTonVo = loadAndUnloadEfficiencyTonVo;
	}

}
