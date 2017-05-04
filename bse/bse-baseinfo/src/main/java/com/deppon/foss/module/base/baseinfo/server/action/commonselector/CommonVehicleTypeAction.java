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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonVehicleTypeAction.java
 * 
 * FILE NAME        	: CommonVehicleTypeAction.java
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
 * FILE    NAME: CommonVehicleTypeAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonVehicleTypeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity;

/**
 * 车辆类型查询Action.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-10 上午9:26:00
 */
public class CommonVehicleTypeAction extends AbstractAction implements
		IQueryAction {
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 8836668513850945340L;
	// service
	/** The common vehicle type service. */
	private ICommonVehicleTypeService commonVehicleTypeService;
	// search condition
	/** The vehicle type entity. */
	private VehicleTypeEntity vehicleTypeEntity = new VehicleTypeEntity();
	// result
	/** The vehicle type entities. */
	private List<VehicleTypeEntity> vehicleTypeEntities;

	/**
	 * 车辆查询.
	 *
	 * @return the string
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-10 上午9:26:00
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		Long totalCount=commonVehicleTypeService.countVehicleTypeByCondition(vehicleTypeEntity);
		if(totalCount>0){
			vehicleTypeEntities = commonVehicleTypeService.queryVehicleTypesByCondition(vehicleTypeEntity, start, limit);
			this.setTotalCount(totalCount);
		}
		return returnSuccess();
	}

	/**
	 * getter.
	 *
	 * @return the vehicle type entities
	 */
	public List<VehicleTypeEntity> getVehicleTypeEntities() {
		return vehicleTypeEntities;
	}

	/**
	 * setter.
	 *
	 * @param commonVehicleTypeService the new common vehicle type service
	 */
	public void setCommonVehicleTypeService(
			ICommonVehicleTypeService commonVehicleTypeService) {
		this.commonVehicleTypeService = commonVehicleTypeService;
	}

	/**
	 * setter.
	 *
	 * @param vehicleTypeEntity the new vehicle type entity
	 */
	public void setVehicleTypeEntity(VehicleTypeEntity vehicleTypeEntity) {
		this.vehicleTypeEntity = vehicleTypeEntity;
	}

	/**
	 * getter.
	 *
	 * @return the vehicle type entity
	 */
	public VehicleTypeEntity getVehicleTypeEntity() {
		return vehicleTypeEntity;
	}

}
