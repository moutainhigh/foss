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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/cache/PriceRegionOrgCacheDeal.java
 * 
 * FILE NAME        	: PriceRegionOrgCacheDeal.java
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

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnBigGoodsEntity;
/**
 * 
 * @Description: 价格区域与组织关系Cache处理
 * 
 * PriceRegionOrgCacheDeal.java Create on 2014-07-9 下午2:03:53
 * 
 * 
 * @author FOSSDP-潘国仰
 * 
 * @version V1.0
 * 
 */
public class PriceRegionBigOrgCacheDeal {
	/**
	 * priceRegionOrgCache 缓存
	 */
	private PriceRegionBigOrgCache priceRegionBigOrgCache;
	
	/**
	 * 获得PriceRegionBigOrgCache缓存
	 * @return
	 */
	public PriceRegionBigOrgCache getPriceRegionBigOrgCache() {
		return priceRegionBigOrgCache;
	}

	/**
	 * 设置PriceRegionBigOrgCache的缓存
	 * @param priceRegionBigOrgCache
	 */
	public void setPriceRegionBigOrgCache(
			PriceRegionBigOrgCache priceRegionBigOrgCache) {
		this.priceRegionBigOrgCache = priceRegionBigOrgCache;
	}


	/**
	 * 
	 * @Description: 根据网点编号以及发生日期从缓存中查询符合条件 价格区域与组织关系实体。
	 * 如缓存中没有查询到相应实体，则直接从数据库中进行查询
	 * @author FOSS-潘国仰
	 * @date 2014-07-09  上午10:32:48
	 * @param code
	 * @param date
	 * @return
	 * @version V1.0
	 */
	public PriceRegioOrgnBigGoodsEntity getPriceRegionOrgByCache(String code, Date date) {
		List<PriceRegioOrgnBigGoodsEntity> priceRegioOrgnEntities = priceRegionBigOrgCache.get(code);
		PriceRegioOrgnBigGoodsEntity entity = null;
		if(CollectionUtils.isNotEmpty(priceRegioOrgnEntities)) {
			for (PriceRegioOrgnBigGoodsEntity priceRegioOrgnEntity : priceRegioOrgnEntities) {
				Date beginTime = priceRegioOrgnEntity.getBeginTime();
				Date endTime = priceRegioOrgnEntity.getEndTime();
				if(beginTime.getTime() <= date.getTime() && endTime.getTime() > date.getTime()) {
					entity = priceRegioOrgnEntity;
					break;
				}
			}
		} 
		return entity;
	}
}