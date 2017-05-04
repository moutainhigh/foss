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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonMotorcadeSalesAreaAction.java
 * 
 * FILE NAME        	: CommonMotorcadeSalesAreaAction.java
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
 * FILE    NAME: CommonMotorcadeSalesAreaAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonMotorcadeSalesAreaService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeSalesAreaEntity;

/**
 * 公共选择器--车队对应营业区ACTION.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-5 下午3:41:50
 */
public class CommonMotorcadeSalesAreaAction extends AbstractAction implements
		IQueryAction {

	/** serialVersionUID. */
	private static final long serialVersionUID = 3120542751897649798L;
	// search condition
	/** The area entity. */
	private MotorcadeServeSalesAreaEntity areaEntity = new MotorcadeServeSalesAreaEntity();
	// result
	/** The area entities. */
	private List<MotorcadeServeSalesAreaEntity> areaEntities;
	// service
	/** The common motorcade sales area service. */
	private ICommonMotorcadeSalesAreaService commonMotorcadeSalesAreaService;

	/**
	 * 车队对应营业区查询.
	 *
	 * @return the string
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-5 下午3:41:51
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		areaEntities = commonMotorcadeSalesAreaService
				.queryMotorcadeServeSalesAreaByCondtion(areaEntity, start,
						limit);
		setTotalCount(commonMotorcadeSalesAreaService
				.queryMotorcadeServeSalesAreaByCondtionCount(areaEntity));
		return returnSuccess();
	}

	/**
	 * getter.
	 *
	 * @return the area entities
	 */
	public List<MotorcadeServeSalesAreaEntity> getAreaEntities() {
		return areaEntities;
	}

	/**
	 * setter.
	 *
	 * @param areaEntity the new area entity
	 */
	public void setAreaEntity(MotorcadeServeSalesAreaEntity areaEntity) {
		this.areaEntity = areaEntity;
	}

	/**
	 * setter.
	 *
	 * @param commonMotorcadeSalesAreaService the new common motorcade sales area service
	 */
	public void setCommonMotorcadeSalesAreaService(
			ICommonMotorcadeSalesAreaService commonMotorcadeSalesAreaService) {
		this.commonMotorcadeSalesAreaService = commonMotorcadeSalesAreaService;
	}

	/**
	 * getter.
	 *
	 * @return the area entity
	 */
	public MotorcadeServeSalesAreaEntity getAreaEntity() {
		return areaEntity;
	}

}
