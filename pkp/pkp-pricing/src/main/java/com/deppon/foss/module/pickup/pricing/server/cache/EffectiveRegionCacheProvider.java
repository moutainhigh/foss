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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/cache/EffectiveRegionCacheProvider.java
 * 
 * FILE NAME        	: EffectiveRegionCacheProvider.java
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
 * @Description: 时效区域Provider
 * EffectiveRegionCacheProvider.java Create on 2013-2-18 下午4:40:04
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class EffectiveRegionCacheProvider implements ITTLCacheProvider<List<PriceRegionEntity>> {

	/**
	 * 
	 */
	private static final Logger log = Logger.getLogger(EffectiveRegionCacheProvider.class);
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
     * @Description: 根据主键获取时效区域实体
     * @author FOSSDP-sz
     * @date 2013-1-30 上午11:22:13
     * @return
     * @version V1.0
     */
    @Override
    public List<PriceRegionEntity> get(String key) {
    	log.debug("EffectiveRegionCacheProvider cacheKey is :"+ key);
    	if (StringUtils.isBlank(key)) {
    	    return null;
    	}
    	String provinceCode = null;
    	String cityCode = null;
    	String countyCode = null;
    	//将输入参数分解为省、市、区
    	if(StringUtil.isNotBlank(key)) {
    		String[] keys = key.split("#");
    		if(StringUtil.isNotBlank(keys[0]) && !StringUtil.equals("key", keys[0])) {
    			provinceCode = keys[0];
    		} 
    		
    		if(StringUtil.isNotBlank(keys[1]) && !StringUtil.equals("key", keys[1])) {
    			cityCode = keys[1];
    		} 
    		
    		if(StringUtil.isNotBlank(keys[2]) && !StringUtil.equals("key", keys[2])) {
    			countyCode = keys[2];
    		} 
    	}
    	//查询相应数据
    	return regionDao.searchRegionByDistrictForCache(provinceCode, cityCode, countyCode, PricingConstants.PRESCRIPTION_REGION, null);
    }
}