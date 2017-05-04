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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/cache/EffectiveRegionCacheDeal.java
 * 
 * FILE NAME        	: EffectiveRegionCacheDeal.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity;

/**
 * @Description: 价格区域Cache处理
 * EffectiveRegionCacheDeal.java Create on 2013-2-18 下午4:40:47
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class EffectiveRegionCacheDeal {

	/**
	 * EffectiveRegionCache 缓存
	 */
	private EffectiveRegionCache effectiveRegionCache;
	
	/**
	 * 设置 effectiveRegionCache 缓存.
	 *
	 * @param effectiveRegionCache the new effectiveRegionCache 缓存
	 */
	public void setEffectiveRegionCache(EffectiveRegionCache effectiveRegionCache) {
		this.effectiveRegionCache = effectiveRegionCache;
	}

	/**
	 * 获取 effectiveRegionCache 缓存.
	 *
	 * @return the effectiveRegionCache 缓存
	 */
	public EffectiveRegionCache getEffectiveRegionCache() {
		return effectiveRegionCache;
	}

	/**
	 * 
	 * @Description: 根据时效区域编号以及发生日期从缓存中查询符合条件时效区域实体。
	 * 如缓存中没有查询到相应实体，则直接从数据库中进行查询
	 * @author FOSSDP-sz
	 * @date 2013-1-31 上午10:32:48
	 * @param code
	 * @param date
	 * @return
	 * @version V1.0
	 */
	public List<PriceRegionEntity> getPriceRegionByCache(String code, Date date) {
		List<PriceRegionEntity> priceRegionEntities = effectiveRegionCache.get(code);
		List<PriceRegionEntity> regionEntities = new ArrayList<PriceRegionEntity>();
		if(CollectionUtils.isNotEmpty(priceRegionEntities)) {
			for (PriceRegionEntity priceRegionEntity : priceRegionEntities) {
				Date beginTime = priceRegionEntity.getBeginTime();
				Date endTime = priceRegionEntity.getEndTime();
				if(beginTime.getTime() <= date.getTime() && endTime.getTime() > date.getTime()) {
					regionEntities.add(priceRegionEntity);
				}
			}
		} 
		return regionEntities;
	}

}