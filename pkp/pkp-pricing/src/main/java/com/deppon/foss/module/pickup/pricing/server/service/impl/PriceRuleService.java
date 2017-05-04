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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/PriceRuleService.java
 * 
 * FILE NAME        	: PriceRuleService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceRuleDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceRuleService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRuleEntity;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRuleCacheDeal;
import com.google.inject.Inject;

/**
 * 
 * @Description: 计价规则服务
 * PriceRuleService.java Create on 2013-2-2 上午11:27:40
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PriceRuleService implements IPriceRuleService{
	
	/**
	 * 
	 */
	private static final Logger log = Logger.getLogger(PriceRuleService.class);
    /**
     * priceRuleCache 处理类
     */
	@Inject
	private PriceRuleCacheDeal priceRuleCacheDeal;
	/**
     * 计价规则DAO
     */
	@Inject
	private IPriceRuleDao priceRuleDao;
	
	/**
	 * 设置 priceRuleCache 处理类.
	 *
	 * @param priceRuleCacheDeal the new priceRuleCache 处理类
	 */
	public void setPriceRuleCacheDeal(PriceRuleCacheDeal priceRuleCacheDeal) {
		this.priceRuleCacheDeal = priceRuleCacheDeal;
	}
	
	/**
	 * 设置 计价规则DAO.
	 *
	 * @param priceRuleDao the new 计价规则DAO
	 */
	public void setPriceRuleDao(IPriceRuleDao priceRuleDao) {
		this.priceRuleDao = priceRuleDao;
	}

	/**
     * @Description: 根据计价规则entryCode返回计价规则实体
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-2-2 上午11:27:01
     * 
     * @param entryCode
     * 
     * @param billDate
     * 
     * @return
     * 
     * @version V1.0
     */
    public PriceRuleEntity getPriceRuleByCache(String ruleCode, Date billDate) {
    	if(StringUtils.isEmpty(ruleCode)) {
    		return null;
    	}
    	if(billDate == null) {
    		billDate = new Date();
    	}
    	PriceRuleEntity priceRuleEntity = null;
    	try {
    		priceRuleEntity = priceRuleCacheDeal.getPriceRuleEntityByCache(ruleCode, billDate);
    		return priceRuleEntity;
		} catch (Exception e) {
			log.info("无法获取计价规则缓存", e);
		}
    	return priceRuleDao.queryPriceRuleByCode(ruleCode, billDate);
    }
    /**
     * 
     * @Description: 刷新计价规则
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-2-22 上午10:57:25
     * 
     * @param ruleCode
     * 
     * @version V1.0
     */
    public void refreshPriceRuleCache(String ruleCode) {
    	if(StringUtils.isNotBlank(ruleCode)) {
    		try {
    			priceRuleCacheDeal.getPriceRuleCache().invalid(ruleCode);
    		} catch (Exception e) {
    			log.info("无法刷新计价规则缓存", e);
    		}
    	}
    }
}