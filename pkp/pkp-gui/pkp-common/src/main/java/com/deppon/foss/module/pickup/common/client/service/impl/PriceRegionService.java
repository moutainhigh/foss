/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/impl/PriceRegionService.java
 * 
 * FILE NAME        	: PriceRegionService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.common.client.service.impl;

import org.mybatis.guice.transactional.Transactional;

import com.deppon.foss.module.pickup.common.client.dao.IPriceRegionDao;
import com.deppon.foss.module.pickup.common.client.service.IPriceRegionService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity;
import com.google.inject.Inject;

/**
 * 价格方案主信息服务类
 * 
 * @author 105089-foss-yangtong
 * @date 2012-10-24 上午10:50:13
 */
public class PriceRegionService implements IPriceRegionService {

	@Inject
	private IPriceRegionDao priceRegionDao;

	/**
	 * 
	 * 功能：插条记录
	 * 
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	@Override
	public void addPriceRegion(PriceRegionEntity priceRegion) {
		priceRegionDao.addPriceRegion(priceRegion);

	}

	/**
	 * 
	 * 功能：更新条记录
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	@Override
	public void updatePriceRegion(PriceRegionEntity priceRegion) {
		priceRegionDao.updatePriceRegion(priceRegion);

	}

	/**
	 * 
	 * 功能：新增或更新记录
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Override
	public void saveOrUpdate(PriceRegionEntity priceRegion) {
		if(!priceRegionDao.addPriceRegion(priceRegion)){
			priceRegionDao.updatePriceRegion(priceRegion);
		}

	}

}