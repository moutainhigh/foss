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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/cache/PriceRegionCacheProvider.java
 * 
 * FILE NAME        	: PriceRegionCacheProvider.java
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
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity;
import com.google.inject.Inject;

/**
 * 
 * @Description: 价格区域Provider
 * PriceRegionCacheProvider.java Create on 2013-2-18 下午5:39:25
 * Company:IBM
 * @author FOSSDP-Administrator
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PriceRegionCacheProvider implements ITTLCacheProvider<List<PriceRegionEntity>> {

	/**
	 * 
	 */
	private static final Logger log = Logger.getLogger(PriceRegionCacheProvider.class);
	/**
	 * 区域DAO
	 */
    @Inject
	private IRegionDao regionDao;
    
	/**
	 * 设置 区域DAO.
	 *
	 * @param regionDao the new 区域DAO
	 */
	public void setRegionDao(IRegionDao regionDao) {
		this.regionDao = regionDao;
	}
	/**
     * @Description: 根据主键获取价格区域实体
     * @author FOSSDP-sz
     * @date 2013-1-30 上午11:22:13
     * @return
     * @version V1.0
     */
    @Override
    public List<PriceRegionEntity> get(String key) {
    	log.debug("PriceRegionCacheProvider cacheKey is :"+ key);
    	if (StringUtils.isBlank(key)) {
    	    return null;
    	}
    	String provinceCode = null;
    	String cityCode = null;
    	String countyCode = null;
    	if(StringUtil.isNotBlank(key)) {
    		//将输入参数拆分为省、市、区
    		String[] keys = key.split("#");
    		//省
    		if(StringUtil.isNotBlank(keys[0]) && !StringUtil.equals("key", keys[0])) {
    			provinceCode = keys[0];
    		} 
    		//市
    		if(StringUtil.isNotBlank(keys[1]) && !StringUtil.equals("key", keys[1])) {
    			cityCode = keys[1];
    		} 
    		//区
    		if(StringUtil.isNotBlank(keys[2]) && !StringUtil.equals("key", keys[2])) {
    			countyCode = keys[2];
    		} 
    	}
    	//根据拆分的条件来查询
    	return regionDao.searchRegionByDistrictForCache(provinceCode, cityCode, countyCode, PricingConstants.PRICING_REGION, null);
    }
    
}