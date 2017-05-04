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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonBigZoneService.java
 * 
 * FILE NAME        	: CommonBigZoneService.java
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
 * FILE    NAME: CommonBigZoneService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliveryBigZoneDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliverySmallZoneDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonBigZoneService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;

/**
 * 公共查询选择器--集中接送货大区service.
 *
 * @author panGuangJun
 * @date 2012-12-3 上午8:52:44
 */
public class CommonBigZoneService implements ICommonBigZoneService {
	
	/** The pickup and delivery big zone dao. */
	private IPickupAndDeliveryBigZoneDao pickupAndDeliveryBigZoneDao;
	
	/** The pickup and delivery small zone dao. */
	private IPickupAndDeliverySmallZoneDao pickupAndDeliverySmallZoneDao;
	
	/**
	 * 根据条件查询接送货大区.
	 *
	 * @param entity the entity
	 * @param limit the limit
	 * @param start the start
	 * @return the list
	 * @author panGuangJun
	 * @date 2012-12-3 上午8:52:44
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonBigZoneService#queryBigZones(com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity, int, int)
	 */
	@Override
	public List<BigZoneEntity> queryBigZones(BigZoneEntity entity, int limit,
			int start) {
		return pickupAndDeliveryBigZoneDao.queryBigZonesByCondition(entity, limit, start);
	}

	/**
	 * 根据条件查询接送货大区总条数.
	 *
	 * @param entity the entity
	 * @return the long
	 * @author panGuangJun
	 * @date 2012-12-3 上午8:52:44
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonBigZoneService#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity)
	 */
	@Override
	public Long queryRecordCount(BigZoneEntity entity) {
		return pickupAndDeliveryBigZoneDao.queryRecordCount(entity);
	}

	/**
	 * Sets the pickup and delivery big zone dao.
	 *
	 * @param pickupAndDeliveryBigZoneDao the new pickup and delivery big zone dao
	 */
	public void setPickupAndDeliveryBigZoneDao(
			IPickupAndDeliveryBigZoneDao pickupAndDeliveryBigZoneDao) {
		this.pickupAndDeliveryBigZoneDao = pickupAndDeliveryBigZoneDao;
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
	 * 根据管理部门查询接送货大区总条数.
	 *
	 * @param entity the entity
	 * @return the long
	 * @author zhuweixing
	 * @date 2015-01-06  上午8:52:44
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonBigZoneService#searchRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity)
	 */
	@Override
	public Long searchRecordCount(BigZoneEntity entity) {
		if(null == entity.getManagement()||"".equals(entity.getManagement())){
			return null;
		}
		entity.setType(DictionaryValueConstants.REGION_TYPE_PK);
		return pickupAndDeliveryBigZoneDao.searchRecordCount(entity);
	}
	
	/**
	 * 根据管理部门查询接送货大区.
	 *
	 * @param entity the entity
	 * @param limit the limit
	 * @param start the start
	 * @return the list
	 * @author zhuweixing
	 * @date 2015-01-06  上午8:52:44
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonBigZoneService#searchBigZones(com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity, int, int)
	 */
	@Override
	public List<BigZoneEntity> searchBigZones(BigZoneEntity entity, int limit,
			int start) {		
		if(null == entity.getManagement()||"".equals(entity.getManagement())){
			return null;
		}
		entity.setType(DictionaryValueConstants.REGION_TYPE_PK);
		return pickupAndDeliveryBigZoneDao.searchBigZonesByManagement(entity, limit, start);
	}
	
	/**
	 * 根据管理部门查询接送货小区总条数.
	 *
	 * @param entity the entity
	 * @return the long
	 * @author zhuweixing
	 * @date 2015-01-06  上午8:52:44
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonBigZoneService#findRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity)
	 */
	@Override
	public Long findRecordCount(SmallZoneEntity entity) {
		if(null == entity.getManagement()||"".equals(entity.getManagement())){
			return null;
		}
		entity.setRegionType(DictionaryValueConstants.REGION_TYPE_PK);
		return pickupAndDeliverySmallZoneDao.findRecordCount(entity);
	}
	
	/**
	 * 根据管理部门查询接送货小区.
	 *
	 * @param entity the entity
	 * @param limit the limit
	 * @param start the start
	 * @return the list
	 * @author zhuweixing
	 * @date 2015-01-06 上午8:52:44
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonBigZoneService#findSmallZones(com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity, int, int)
	 */
	@Override
	public List<SmallZoneEntity> findSmallZones(SmallZoneEntity entity, int limit,
			int start) {
		if(null == entity.getManagement()||"".equals(entity.getManagement())){
			return null;
		}
		entity.setRegionType(DictionaryValueConstants.REGION_TYPE_PK);
		return pickupAndDeliverySmallZoneDao.findSmallZonesByManagement(entity, limit, start);
	}
	
	/**查询送货大区的总数
	 *  @param entity the entity
	 * @return the long
	 * @author wusuhua
	 * @date 2015-06-11  上午8:52:44
	 */
	@Override
	public Long searchDERecordCount(BigZoneEntity entity) {
		// TODO Auto-generated method stub
		/*if(null == entity.getManagement()||"".equals(entity.getManagement())){
			return null;
		}*/
		if(null==entity){
			entity=new BigZoneEntity();
		}
		entity.setType(DictionaryValueConstants.REGION_TYPE_DE);
		return pickupAndDeliveryBigZoneDao.searchRecordCount(entity);
	}

	/**查询送货大区
	 *  @param entity the entity
	 * @return List<BigZoneEntity>
	 * @author wusuhua
	 * @date 2015-06-11  上午8:52:44
	 */
	@Override
	public List<BigZoneEntity> searchBigZonesDE(BigZoneEntity entity,
			int limit, int start) {
		// TODO Auto-generated method stub
		if(null==entity){
			entity=new BigZoneEntity();
		}
		entity.setType(DictionaryValueConstants.REGION_TYPE_DE);
		return pickupAndDeliveryBigZoneDao.searchBigZonesByManagement(entity, limit, start);
	}
	
	
}
