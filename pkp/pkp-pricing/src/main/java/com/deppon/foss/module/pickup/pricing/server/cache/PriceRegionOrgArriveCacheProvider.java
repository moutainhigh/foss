/**
 *  initial comments.
 */
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
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/cache/PriceRegionOrgCacheProvider.java
 * 
 * FILE NAME        	: PriceRegionOrgCacheProvider.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.cache;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionArriveDao;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionOrgArriveEntity;
import com.google.inject.Inject;

/**
 * 
 * @Description: 空运价格区域与组织关系Provider
 * PriceRegionOrgAirCacheProvider.java Create on 2013-3-13 下午3:34:44
 * Company:IBM
 * @author FOSSDP-Administrator
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PriceRegionOrgArriveCacheProvider implements ITTLCacheProvider<List<PriceRegionOrgArriveEntity>> {

	/**
	 * 
	 */
	private static final Logger log = Logger.getLogger(PriceRegionOrgArriveCacheProvider.class);
	/**
	 * 区域DAO
	 */
    @Inject
	private IRegionArriveDao regionArriveDao;
    
	public void setRegionArriveDao(IRegionArriveDao regionArriveDao) {
		this.regionArriveDao = regionArriveDao;
	}

	/**
     * @Description: 根据网点CODE获取价格区域与组织关系实体
     * @author FOSSDP-sz
     * @date 2013-1-30 上午11:22:13
     * @return
     * @version V1.0
     */
    @Override
    public List<PriceRegionOrgArriveEntity> get(String key) {
    	log.debug("PriceRegionOrgAirCacheProvider cacheKey is :"+ key);
    	if (StringUtils.isBlank(key)) {
    	    return null;
    	}
    	return regionArriveDao.searchRegionOrgByDeptNo(key, PricingConstants.ARRIVE_REGION);
    }
}