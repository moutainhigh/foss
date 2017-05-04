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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/cache/PriceRuleCacheDeal.java
 * 
 * FILE NAME        	: PriceRuleCacheDeal.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.cache;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRuleEntity;

/**
 * 
 * @Description: 计价规则Cache处理
 * ProductCacheDeal.java Create on 2013-1-30 上午10:47:40
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PriceRuleCacheDeal {
	/**
	 * priceRuleCache 缓存
	 */
	private PriceRuleCache priceRuleCache;
	/**
	 * 设置 priceRuleCache 缓存.
	 *
	 * @param priceRuleCache the new priceRuleCache 缓存
	 */
	public void setPriceRuleCache(PriceRuleCache priceRuleCache) {
		this.priceRuleCache = priceRuleCache;
	}
	/**
	 * 获取 priceRuleCache 缓存.
	 *
	 * @return the priceRuleCache 缓存
	 */
	public PriceRuleCache getPriceRuleCache() {
		return priceRuleCache;
	}
	/**
	 * 
	 * @Description: 根据货物类型编以及发生日期从缓存中查询符合条件货物类型实体。
	 * 如缓存中没有查询到相应实体，则直接从数据库中进行查询
	 * @author FOSSDP-sz
	 * @date 2013-1-31 上午10:32:48
	 * @param code
	 * @param date
	 * @return
	 * @version V1.0
	 */
	public PriceRuleEntity getPriceRuleEntityByCache(String code, Date date) {
		List<PriceRuleEntity> priceRuleEntities = priceRuleCache.get(code);
		PriceRuleEntity entity = null;
		if(CollectionUtils.isNotEmpty(priceRuleEntities)) {
			for (PriceRuleEntity priceRuleEntity : priceRuleEntities) {
				Date beginTime = priceRuleEntity.getBeginTime();
				Date endTime = priceRuleEntity.getEndTime();
				if(beginTime.getTime() <= date.getTime() && endTime.getTime() > date.getTime()) {
					entity = priceRuleEntity;
					break;
				}
			}
		} 
		return entity;
	}

}