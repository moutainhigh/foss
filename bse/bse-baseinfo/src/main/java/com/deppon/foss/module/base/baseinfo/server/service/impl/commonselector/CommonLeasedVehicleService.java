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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonLeasedVehicleService.java
 * 
 * FILE NAME        	: CommonLeasedVehicleService.java
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
 * FILE    NAME: CommonLeasedVehicleService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonLeasedVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity;

/**
 * 公共选择器--外请车查询service.
 *
 * @author panGuangJun
 * @date 2012-12-1 上午9:33:20
 */
public class CommonLeasedVehicleService implements ICommonLeasedVehicleService {
	
	/** The leased vehicle dao. */
	private ILeasedVehicleDao leasedVehicleDao;
	
	/**
	 * 根据实体条件分页查询外请车.
	 *
	 * @param leasedTruck the leased truck
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author panGuangJun
	 * @date 2012-12-1 上午9:33:20
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonLeasedVehicleService#queryLeasedVehicleListCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity, int, int)
	 */
	@Override
	public List<LeasedTruckEntity> queryLeasedVehicleListCondition(
			LeasedTruckEntity leasedTruck, int start, int limit) {
		return leasedVehicleDao.queryLeasedVehicleListBySelectiveCondition(leasedTruck, start, limit);
	}

	/**
	 * 根据实体条件查询外请车总数.
	 *
	 * @param leasedTruck the leased truck
	 * @return the long
	 * @author panGuangJun
	 * @date 2012-12-1 上午9:33:20
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonLeasedVehicleService#queryLeasedVehicleRecordCountBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity)
	 */
	@Override
	public long queryLeasedVehicleRecordCountBySelectiveCondition(
			LeasedTruckEntity leasedTruck) {
		return leasedVehicleDao.queryLeasedVehicleRecordCountBySelectiveCondition(leasedTruck);
	}

	/**
	 * Sets the leased vehicle dao.
	 *
	 * @param leasedVehicleDao the new leased vehicle dao
	 */
	public void setLeasedVehicleDao(ILeasedVehicleDao leasedVehicleDao) {
		this.leasedVehicleDao = leasedVehicleDao;
	}

}
