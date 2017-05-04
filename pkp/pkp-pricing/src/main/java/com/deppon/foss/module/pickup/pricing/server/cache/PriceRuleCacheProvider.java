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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/cache/PriceRuleCacheProvider.java
 * 
 * FILE NAME        	: PriceRuleCacheProvider.java
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
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceRuleDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRuleEntity;
import com.google.inject.Inject;

/**
 * 
 * @Description: 计价规则Provider
 * GoodsTypeCacheProvider.java Create on 2013-1-31 上午10:01:52
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PriceRuleCacheProvider implements ITTLCacheProvider<List<PriceRuleEntity>> {

    /**
     * 日志
     */
    private static final Logger log = Logger.getLogger(PriceRuleCacheProvider.class);
    
    /**
     * 计价规则DAO
     */
    @Inject
    private IPriceRuleDao priceRuleDao;

    /**
     * 设置 计价规则DAO.
     *
     * @param priceRuleDao the new 计价规则DAO
     */
    public void setPriceRuleDao(IPriceRuleDao priceRuleDao) {
	this.priceRuleDao = priceRuleDao;
    }
    
    /**
     * @Description: 根据主键获取计价规则实体
     * @author FOSSDP-sz
     * @date 2013-1-30 上午11:22:13
     * @return
     * @version V1.0
     */
    @Override
    public List<PriceRuleEntity> get(String key) {
    	log.debug("PriceRuleCacheProvider cacheKey is :"+ key);
    	if (StringUtils.isBlank(key)) {
    	    return null;
    	}
    	return priceRuleDao.queryPriceRuleByCode(key);
    }
}