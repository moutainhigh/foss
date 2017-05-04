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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonVehicleTypeService.java
 * 
 * FILE NAME        	: CommonVehicleTypeService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector
 * FILE    NAME: CommonVehicleTypeService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonVehicleTypeDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonVehicleTypeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity;

/**
 * 公共选择器--车辆车型service实现.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-10 上午9:20:44
 */
public class CommonVehicleTypeService implements ICommonVehicleTypeService {
	
	/** The common vehicle type dao. */
	private ICommonVehicleTypeDao commonVehicleTypeDao;

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“车辆车型”实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>.
	 *
	 * @param vehicleType the vehicle type
	 * @param offset the offset
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-10 上午9:20:44
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonVehicleTypeService#queryVehicleTypesByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity,
	 * int, int)
	 */
	@Override
	public List<VehicleTypeEntity> queryVehicleTypesByCondition(
			VehicleTypeEntity vehicleType, int offset, int limit) {
		return commonVehicleTypeDao.queryVehicleTypesByCondition(vehicleType,
				offset, limit);
	}

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“车辆车型”实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>.
	 *
	 * @param vehicleType the vehicle type
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-10 上午9:20:44
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonVehicleTypeService#countVehicleTypeByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity)
	 */
	@Override
	public Long countVehicleTypeByCondition(VehicleTypeEntity vehicleType) {
		return commonVehicleTypeDao.countVehicleTypeByCondition(vehicleType);
	}

	/**
	 * setter.
	 *
	 * @param commonVehicleTypeDao the new common vehicle type dao
	 */
	public void setCommonVehicleTypeDao(
			ICommonVehicleTypeDao commonVehicleTypeDao) {
		this.commonVehicleTypeDao = commonVehicleTypeDao;
	}

}
