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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonSmallZoneService.java
 * 
 * FILE NAME        	: CommonSmallZoneService.java
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
 * FILE    NAME: CommonSmallZoneService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliverySmallZoneDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonServiceZoneDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonSmallZoneService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ServiceZoneDto;

/**
 * 公共选择器--集中接送货小区service.
 *
 * @author panGuangJun
 * @date 2012-12-3 上午8:53:41
 */
public class CommonSmallZoneService implements ICommonSmallZoneService {
	//集中接送货小区dao
	/** The pickup and delivery small zone dao. */
	private IPickupAndDeliverySmallZoneDao pickupAndDeliverySmallZoneDao;
	//集中接送货dao
	/** The common service zone dao. */
	private ICommonServiceZoneDao commonServiceZoneDao;
	
	/**
	 * 根据条件分页查询接送货小区.
	 *
	 * @param entity the entity
	 * @param limit the limit
	 * @param start the start
	 * @return the list
	 * @author panGuangJun
	 * @date 2012-12-3 上午8:53:41
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonSmallZoneService#querySmallZones(com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity, int, int)
	 */
	@Override
	public List<SmallZoneEntity> querySmallZones(SmallZoneEntity entity,
			int limit, int start) {
		return pickupAndDeliverySmallZoneDao.queryPickupAndDeliverySmallZones(entity, limit, start);
	}

	/**
	 * 根据条件查询接送货小区总数.
	 *
	 * @param entity the entity
	 * @return the long
	 * @author panGuangJun
	 * @date 2012-12-3 上午8:53:41
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonSmallZoneService#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity)
	 */
	@Override
	public Long queryRecordCount(SmallZoneEntity entity) {
		return pickupAndDeliverySmallZoneDao.queryRecordCount(entity);
	}

	/**
	 * Sets the pickup and delivery small zone dao.
	 *
	 * @param pickupAndDeliverySmallZoneDao the new pickup and delivery small zone dao
	 */
	public void setPickupAndDeliverySmallZoneDao(
			IPickupAndDeliverySmallZoneDao pickupAndDeliverySmallZoneDao) {
		this.pickupAndDeliverySmallZoneDao = pickupAndDeliverySmallZoneDao;
	}

	/**
	 * 查询集中接送货大小区.
	 *
	 * @param entity the entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-28 上午11:04:41
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonSmallZoneService#queryServiceZones(com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity, int, int)
	 */
	@Override
	public List<ServiceZoneDto> queryServiceZones(SmallZoneEntity entity,
			int start, int limit) {
		return commonServiceZoneDao.queryServiceZoneListByCondition(entity, start, limit);
	}

	/**
	 * 查询集中接送货大小区总数.
	 *
	 * @param entity the entity
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-28 上午11:04:41
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonSmallZoneService#queryServiceRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity)
	 */
	@Override
	public Long queryServiceRecordCount(SmallZoneEntity entity) {
		return  commonServiceZoneDao.countServiceZoneListByCondition(entity);
	}

	/**
	 * setter.
	 *
	 * @param commonServiceZoneDao the new common service zone dao
	 */
	public void setCommonServiceZoneDao(ICommonServiceZoneDao commonServiceZoneDao) {
		this.commonServiceZoneDao = commonServiceZoneDao;
	}
}
