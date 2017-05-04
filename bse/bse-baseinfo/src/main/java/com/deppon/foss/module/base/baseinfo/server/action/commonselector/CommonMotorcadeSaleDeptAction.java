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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonMotorcadeSaleDeptAction.java
 * 
 * FILE NAME        	: CommonMotorcadeSaleDeptAction.java
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
 * FILE    NAME: CommonMotorcadeSaleDeptAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonMotorcadeSaleDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity;

/**
 * 公共查询选择器--车队对应的营业部ACTINO.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-5 下午3:35:01
 */
public class CommonMotorcadeSaleDeptAction extends AbstractAction implements
		IQueryAction {
	// vo
	/** The common motorcade sale dept service. */
	private ICommonMotorcadeSaleDeptService commonMotorcadeSaleDeptService;
	// search condition
	/** The sales motorcade entity. */
	private SalesMotorcadeEntity salesMotorcadeEntity;
	// result
	/** The sales motorcade entities. */
	private List<SalesMotorcadeEntity> salesMotorcadeEntities;
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 3712074387178521944L;

	/**
	 * 查询车队对应营业部.
	 *
	 * @return the string
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-5 下午3:35:40
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		salesMotorcadeEntities = commonMotorcadeSaleDeptService
				.queryMotorcadeSalesDeptByCondition(salesMotorcadeEntity,
						start, limit);
		setTotalCount(commonMotorcadeSaleDeptService
				.queryMotorcadeSalesDeptByConditionCount(salesMotorcadeEntity));
		return returnSuccess();
	}

	/**
	 * getter.
	 *
	 * @return the sales motorcade entities
	 */
	public List<SalesMotorcadeEntity> getSalesMotorcadeEntities() {
		return salesMotorcadeEntities;
	}

	/**
	 * setter.
	 *
	 * @param salesMotorcadeEntity the new sales motorcade entity
	 */
	public void setSalesMotorcadeEntity(
			SalesMotorcadeEntity salesMotorcadeEntity) {
		this.salesMotorcadeEntity = salesMotorcadeEntity;
	}

	/**
	 * setter.
	 *
	 * @param commonMotorcadeSaleDeptService the new common motorcade sale dept service
	 */
	public void setCommonMotorcadeSaleDeptService(
			ICommonMotorcadeSaleDeptService commonMotorcadeSaleDeptService) {
		this.commonMotorcadeSaleDeptService = commonMotorcadeSaleDeptService;
	}

	/**
	 * getter.
	 *
	 * @return the sales motorcade entity
	 */
	public SalesMotorcadeEntity getSalesMotorcadeEntity() {
		return salesMotorcadeEntity;
	}

}
