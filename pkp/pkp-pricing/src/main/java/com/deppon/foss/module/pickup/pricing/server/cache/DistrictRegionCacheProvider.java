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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/cache/GoodsTypeCacheProvider.java
 * 
 * FILE NAME        	: GoodsTypeCacheProvider.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.cache;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IDistrictRegionDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.DistrictRegionEntity;

/**
 * 
 * @Description: 时效区域Provider
 * DistrictRegionCacheProvider.java Create on 2013-4-15 下午6:21:53
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class DistrictRegionCacheProvider implements ITTLCacheProvider<DistrictRegionEntity> {
	/**
	 * 
	 */
	private static final Logger log = Logger.getLogger(DistrictRegionCacheProvider.class);
	/**
     * 行政区域与时效、汽运、空运价格区域关系表DAO
     */
    private IDistrictRegionDao districtRegionDao;

	public IDistrictRegionDao getDistrictRegionDao() {
		return districtRegionDao;
	}
	
	public void setDistrictRegionDao(IDistrictRegionDao districtRegionDao) {
		this.districtRegionDao = districtRegionDao;
	}
	/**
     * @Description: 根据主键获取产品实体
     * @author FOSSDP-sz
     * @date 2013-1-30 上午11:22:13
     * @return
     * @version V1.0
     */
    @Override
    public DistrictRegionEntity get(String key) {
    	log.debug("DistrictRegionCacheProvider cacheKey is :"+ key);
    	if (StringUtils.isBlank(key)) {
    	    return null;
    	}
    	return districtRegionDao.selectByDistrictCode(key);
    }
}