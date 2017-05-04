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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/cache/PriceRegionCacheDeal.java
 * 
 * FILE NAME        	: PriceRegionCacheDeal.java
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
 * 
 * @Description: 价格区域Cache处理
 * PriceRegionCacheDeal.java Create on 2013-2-18 下午5:39:59
 * Company:IBM
 * @author FOSSDP-Administrator
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PriceRegionCacheDeal {
	/**
	 * priceRegionCache 缓存
	 */
	private PriceRegionCache priceRegionCache;
	
	/**
	 * 设置 priceRegionCache 缓存.
	 *
	 * @param priceRegionCache the new priceRegionCache 缓存
	 */
	public void setPriceRegionCache(PriceRegionCache priceRegionCache) {
		this.priceRegionCache = priceRegionCache;
	}

	/**
	 * 获取 priceRegionCache 缓存.
	 *
	 * @return the priceRegionCache 缓存
	 */
	public PriceRegionCache getPriceRegionCache() {
		return priceRegionCache;
	}
	/**
	 * 
	 * @Description: 根据价格区域编号以及发生日期从缓存中查询符合条件价格区域实体。
	 * 如缓存中没有查询到相应实体，则直接从数据库中进行查询
	 * @author FOSSDP-sz
	 * @date 2013-1-31 上午10:32:48
	 * @param code
	 * @param date
	 * @return
	 * @version V1.0
	 */
	public List<PriceRegionEntity> getPriceRegionByCache(String code, Date date) {
		List<PriceRegionEntity> priceRegionEntities = priceRegionCache.get(code);
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