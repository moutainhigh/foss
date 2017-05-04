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
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionBigGoodsDao;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnBigGoodsEntity;
import com.google.inject.Inject;

/**
 * 
 * @Description: 价格区域与组织关系Provider
 * PriceRegionOrgCacheProvider.java Create on 2013-2-19 上午10:26:39
 * Company:IBM
 * @author FOSSDP-Administrator
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PriceRegionBigGoodsOrgCacheProvider implements ITTLCacheProvider<List<PriceRegioOrgnBigGoodsEntity>> {

	/**
	 * 
	 */
	private static final Logger log = Logger.getLogger(PriceRegionBigGoodsOrgCacheProvider.class);
	/**
	 * 区域 DAO
	 */
    @Inject
	private IRegionBigGoodsDao regionBigGoodsDao;
    


	public void setRegionBigGoodsDao(IRegionBigGoodsDao regionBigGoodsDao) {
		this.regionBigGoodsDao = regionBigGoodsDao;
	}




	/**
     * @Description: 根据网点CODE获取价格区域与组织关系实体
     * @author FOSS-潘国仰
     * @date 2014-07-09 上午11:22:13
     * @return
     * @version V1.0
     */
    @Override
    public List<PriceRegioOrgnBigGoodsEntity> get(String key) {
    	log.debug("PriceRegionOrgCacheProvider cacheKey is :"+ key);
    	if (StringUtils.isBlank(key)) {
    	    return null;
    	}
    	return regionBigGoodsDao.searchRegionOrgByDeptNo(key, PricingConstants.PRICING_REGION);
    }
}