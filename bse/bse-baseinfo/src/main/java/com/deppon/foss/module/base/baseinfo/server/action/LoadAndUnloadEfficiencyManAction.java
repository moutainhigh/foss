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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/LoadAndUnloadEfficiencyManAction.java
 * 
 * FILE NAME        	: LoadAndUnloadEfficiencyManAction.java
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
import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadEfficiencyManService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyManEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.LoadAndUnloadEfficiencyManVo;
import com.deppon.foss.module.base.baseinfo.server.action.actionutil.GainEmployee;

/**
 * 新增装卸车标准-车-时间 action
 * 
 * 装卸车人力效率标准
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-18 上午9:48:18
 */
public class LoadAndUnloadEfficiencyManAction extends AbstractAction {

	/**
	 * 新增装卸车标准-车-时间
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-21 上午9:06:41
	 */
	@JSON
	public String addLoadAndUnloadEfficiencyMan() {
		// //校验前台传入的VO是否为空
		if (loadAndUnloadEfficiencyManVo == null
				|| loadAndUnloadEfficiencyManVo
						.getLoadAndUnloadEfficiencyManEntity() == null
				|| StringUtils.isBlank(loadAndUnloadEfficiencyManVo
						.getLoadAndUnloadEfficiencyManEntity().getOrgCode())) {
			// 校验不通过怎返回错误信息
			return returnError("部门编码不能为空");
		}

		try {
			// 获取VO中的实体对象
			LoadAndUnloadEfficiencyManEntity entityView = loadAndUnloadEfficiencyManVo
					.getLoadAndUnloadEfficiencyManEntity();

			// 设置操作用户的用户编码
			String operUserCode = GainEmployee.getOperUserCode();
			// 创建用户编码
			entityView.setCreateUser(operUserCode);
			// 修改用户编码
			entityView.setModifyUser(operUserCode);
			// 执行新增操作
			LoadAndUnloadEfficiencyManEntity entityCondition = loadAndUnloadEfficiencyManService
					.addLoadAndUnloadEfficiencyMan(entityView);
			// 保存失败，则返回错误信息
			if (entityCondition == null) {
				return returnError("保存失败");
			}
			// 发生异常，则返回异常信息
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		} catch (Exception e2) {
			return returnError("新增装卸车人力效率标准失败" + e2.getMessage(), e2);
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
	public String deleteLoadAndUnloadEfficiencyMan() {
		// //校验前台传入的VO是否为空
		if (loadAndUnloadEfficiencyManVo == null
				|| loadAndUnloadEfficiencyManVo
						.getLoadAndUnloadEfficiencyManEntity() == null
				|| StringUtils.isBlank(loadAndUnloadEfficiencyManVo
						.getLoadAndUnloadEfficiencyManEntity().getOrgCode())) {
			// 校验不通过则返回异常信息
			return returnError("部门编码不能为空");
		}
		// 获取前台传入的实体信息
		LoadAndUnloadEfficiencyManEntity entityView = loadAndUnloadEfficiencyManVo
				.getLoadAndUnloadEfficiencyManEntity();

		// 设置操作用户的用户编码
		String operUserCode = GainEmployee.getOperUserCode();
		// 设置创建人
		entityView.setCreateUser(operUserCode);
		// 设置修改人
		entityView.setModifyUser(operUserCode);
		try {
			// 执行删除操作
			LoadAndUnloadEfficiencyManEntity entityDelete = loadAndUnloadEfficiencyManService
					.deleteLoadAndUnloadEfficiencyMan(entityView);
			if (entityDelete == null) {
				return returnError("保存装卸车人力效率标准失败");
			}
		} catch (BusinessException e) {
			LOGGER.error("保存装卸车人力效率标准失败", e);
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
	public String deleteLoadAndUnloadEfficiencyManMore() {
		// 校验前台传入的VO是否为空
		if (loadAndUnloadEfficiencyManVo == null
				|| loadAndUnloadEfficiencyManVo
						.getLoadAndUnloadEfficiencyManEntity() == null
				|| StringUtils.isBlank(loadAndUnloadEfficiencyManVo
						.getLoadAndUnloadEfficiencyManEntity().getOrgCode())) {
			// 校验不通过则返回错误信息
			return returnError("部门编码不能为空");
		}
		// 获取编码数组信息
		String[] codes = loadAndUnloadEfficiencyManVo
				.getLoadAndUnloadEfficiencyManEntity().getOrgCode()
				.split(SymbolConstants.EN_COMMA);

		String operUserCode = GainEmployee.getOperUserCode();
		try {
			// 执行批量删除操作
			LoadAndUnloadEfficiencyManEntity entityDelete = loadAndUnloadEfficiencyManService
					.deleteLoadAndUnloadEfficiencyManMore(codes, operUserCode);
			if (entityDelete == null) {
				return returnError("保存装卸车人力效率标准失败");
			}
		} catch (BusinessException e) {
			LOGGER.error("保存装卸车人力效率标准失败", e);
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
	public String updateLoadAndUnloadEfficiencyMan() {
		// 校验前台传入的VO是否为空
		if (loadAndUnloadEfficiencyManVo == null
				|| loadAndUnloadEfficiencyManVo
						.getLoadAndUnloadEfficiencyManEntity() == null
				|| StringUtils.isBlank(loadAndUnloadEfficiencyManVo
						.getLoadAndUnloadEfficiencyManEntity().getOrgCode())) {
			// 校验不通过则返回错误信息
			return returnError("部门编码不能为空");
		}

		try {
			// 获取前台实体信息
			LoadAndUnloadEfficiencyManEntity entityView = loadAndUnloadEfficiencyManVo
					.getLoadAndUnloadEfficiencyManEntity();
			// 执行查询操作
			LoadAndUnloadEfficiencyManEntity entityQuery = loadAndUnloadEfficiencyManService
					.queryLoadAndUnloadEfficiencyManByOrgCode(entityView
							.getOrgCode());
			// 从数据库中获取最新的数据
			entityQuery = this.transEntity(entityView, entityQuery);
			// 获取员工编码
			String operUserCode = GainEmployee.getOperUserCode();
			entityQuery.setModifyUser(operUserCode);
			// 执行修改操作
			LoadAndUnloadEfficiencyManEntity entityResult = loadAndUnloadEfficiencyManService
					.updateLoadAndUnloadEfficiencyMan(entityQuery);
			// 如果修改失败，则抛出错误信息
			if (entityResult == null) {
				return returnError("修改失败");
			}
			// 发生异常则打印日志信息
		} catch (BusinessException e) {
			LOGGER.error("保存装卸车人力效率标准失败", e);
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
	public String queryLoadAndUnloadEfficiencyManExactByEntity() {
		// 校验前台传入的VO是否为空
		if (loadAndUnloadEfficiencyManVo == null) {
			loadAndUnloadEfficiencyManVo = new LoadAndUnloadEfficiencyManVo();
		}
		// 获取前台查询条件
		LoadAndUnloadEfficiencyManEntity entityCondition = loadAndUnloadEfficiencyManVo
				.getLoadAndUnloadEfficiencyManEntity();

		if (limit == 0) {
			limit = Integer.MAX_VALUE;
		}
		// 返回的结果显示在表格中：
		loadAndUnloadEfficiencyManVo
				.setLoadAndUnloadEfficiencyManList(loadAndUnloadEfficiencyManService
						.queryLoadAndUnloadEfficiencyManExactByEntity(
								entityCondition, start, limit));
		//查询合计信息
		totalCount = loadAndUnloadEfficiencyManService
				.queryLoadAndUnloadEfficiencyManExactByEntityCount(entityCondition);
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
	public LoadAndUnloadEfficiencyManEntity transEntity(
			LoadAndUnloadEfficiencyManEntity entityView,
			LoadAndUnloadEfficiencyManEntity entityQuery) {
		// 平均人天装车吨数
		entityQuery.setLoadWeightStd(entityView.getLoadWeightStd());
		// 平均人天卸车吨数
		entityQuery.setLoadVolumeStd(entityView.getLoadVolumeStd());

		return entityQuery;
	}

	/**
	 * 下面是变量的声明
	 */
	private static final long serialVersionUID = -4387627988772020011L;

	// 用于注入装卸车效率标准业务服务实现类
	private ILoadAndUnloadEfficiencyManService loadAndUnloadEfficiencyManService;

	private LoadAndUnloadEfficiencyManVo loadAndUnloadEfficiencyManVo;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LoadAndUnloadEfficiencyManAction.class);

	/*
	 * =================================================================
	 * 下面是get,set方法：
	 */

	public void setLoadAndUnloadEfficiencyManService(
			ILoadAndUnloadEfficiencyManService loadAndUnloadEfficiencyManService) {
		this.loadAndUnloadEfficiencyManService = loadAndUnloadEfficiencyManService;
	}

	public LoadAndUnloadEfficiencyManVo getLoadAndUnloadEfficiencyManVo() {
		return loadAndUnloadEfficiencyManVo;
	}

	public void setLoadAndUnloadEfficiencyManVo(
			LoadAndUnloadEfficiencyManVo loadAndUnloadEfficiencyManVo) {
		this.loadAndUnloadEfficiencyManVo = loadAndUnloadEfficiencyManVo;
	}

}
