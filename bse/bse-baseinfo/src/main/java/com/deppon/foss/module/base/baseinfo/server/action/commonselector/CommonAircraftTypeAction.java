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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonAircraftTypeAction.java
 * 
 * FILE NAME        	: CommonAircraftTypeAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.action.commonselector
 * FILE    NAME: CommonAirAgencyCompanyAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAircraftTypeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity;

/**
 * 公共选择器--机型ACTION.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-6 上午10:32:34
 */
public class CommonAircraftTypeAction extends AbstractAction implements
		IQueryAction {
	// service
	/** The common aircraft type service. */
	private ICommonAircraftTypeService commonAircraftTypeService;
	// search condition
	/** The aircraft type entity. */
	private AircraftTypeEntity aircraftTypeEntity = new AircraftTypeEntity();
	// result
	/** The aircraft type entities. */
	private List<AircraftTypeEntity> aircraftTypeEntities;
	
	/** serialVersionUID. */
	private static final long serialVersionUID = -3383631039625626863L;

	/**
	 * 空运代理公司查询.
	 *
	 * @return the string
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-6 上午10:32:34
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		aircraftTypeEntities = commonAircraftTypeService
				.queryAircraftTypeListByCondition(aircraftTypeEntity,start,limit);
		setTotalCount(commonAircraftTypeService
				.countAircraftTypeListByCondition(aircraftTypeEntity));
		return returnSuccess();
	}

	/**
	 * getter.
	 *
	 * @return the aircraft type entities
	 */
	public List<AircraftTypeEntity> getAircraftTypeEntities() {
		return aircraftTypeEntities;
	}

	/**
	 * setter.
	 *
	 * @param commonAircraftTypeService the new common aircraft type service
	 */
	public void setCommonAircraftTypeService(
			ICommonAircraftTypeService commonAircraftTypeService) {
		this.commonAircraftTypeService = commonAircraftTypeService;
	}

	/**
	 * setter.
	 *
	 * @param aircraftTypeEntity the new aircraft type entity
	 */
	public void setAircraftTypeEntity(AircraftTypeEntity aircraftTypeEntity) {
		this.aircraftTypeEntity = aircraftTypeEntity;
	}

	/**
	 * getter.
	 *
	 * @return the aircraft type entity
	 */
	public AircraftTypeEntity getAircraftTypeEntity() {
		return aircraftTypeEntity;
	}
}
