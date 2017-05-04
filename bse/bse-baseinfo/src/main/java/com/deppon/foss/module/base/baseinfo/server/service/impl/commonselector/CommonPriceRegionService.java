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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonPriceRegionService.java
 * 
 * FILE NAME        	: CommonPriceRegionService.java
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
 * FILE    NAME: CommonPriceRegionService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPriceRegionDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPriceRegionService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity;

/**
 * 公共组件查询-价格区域查询实现.
 *
 * @author panGuangJun
 * @date 2012-12-4 上午10:53:02
 */
public class CommonPriceRegionService implements
		ICommonPriceRegionService {
	
	/** The common price region dao. */
	private ICommonPriceRegionDao commonPriceRegionDao;
	
	/**
	 * 实体查询方法.
	 *
	 * @param regionEntity the region entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author panGuangJun
	 * @date 2012-12-4 上午10:53:02
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPriceRegionService#searchRegionByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity, int, int)
	 */
	@Override
	public List<PriceRegionEntity> searchRegionByCondition(
			PriceRegionEntity regionEntity, int start, int limit) {
		return commonPriceRegionDao.searchRegionByCondition(
				regionEntity, start, limit);
	}

	/**
	 * 查询条数.
	 *
	 * @param regionEntity the region entity
	 * @return the long
	 * @author panGuangJun
	 * @date 2012-12-4 上午10:53:02
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPriceRegionService#countRegionByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity)
	 */
	@Override
	public Long countRegionByCondition(PriceRegionEntity regionEntity) {
		return commonPriceRegionDao.countRegionByCondition(regionEntity);
	}

	/**
	 * Sets the common price region dao.
	 *
	 * @param commonPriceRegionDao the new common price region dao
	 */
	public void setCommonPriceRegionDao(ICommonPriceRegionDao commonPriceRegionDao) {
		this.commonPriceRegionDao = commonPriceRegionDao;
	}

}
