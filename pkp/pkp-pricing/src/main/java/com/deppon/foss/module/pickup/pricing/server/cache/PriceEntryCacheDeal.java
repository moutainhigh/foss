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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/cache/PriceEntryCacheDeal.java
 * 
 * FILE NAME        	: PriceEntryCacheDeal.java
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

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;

/**
 * 
 * @Description: 计价条目Cache处理
 * PriceEntryCacheDeal.java Create on 2013-2-19 下午1:36:32
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PriceEntryCacheDeal {
	/**
	 * priceEntryCache缓存
	 */
	private PriceEntryCache priceEntryCache;
	
	/**
	 * 设置 priceEntryCache缓存.
	 *
	 * @param priceEntryCache the new priceEntryCache缓存
	 */
	public void setPriceEntryCache(PriceEntryCache priceEntryCache) {
		this.priceEntryCache = priceEntryCache;
	}

	/**
	 * 获取 priceEntryCache缓存.
	 *
	 * @return the priceEntryCache缓存
	 */
	public PriceEntryCache getPriceEntryCache() {
		return priceEntryCache;
	}
	/**
	 * 
	 * @Description: 根据计价条目编号以及发生日期从缓存中查询符合条件计价条目实体。
	 * 如缓存中没有查询到相应实体，则直接从数据库中进行查询
	 * @author FOSSDP-sz
	 * @date 2013-1-31 上午10:32:48
	 * @param code
	 * @param date
	 * @return
	 * @version V1.0
	 */
	public PriceEntity getPriceEntryByCache(String code, Date date) {
		List<PriceEntity> priceEntities = priceEntryCache.get(code);
		PriceEntity entity = null;
		if(CollectionUtils.isNotEmpty(priceEntities)) {
			for (PriceEntity priceEntity : priceEntities) {
				Date beginTime = priceEntity.getBeginTime();
				Date endTime = priceEntity.getEndTime();
				if(beginTime.getTime() <= date.getTime() && endTime.getTime() > date.getTime()) {
					entity = priceEntity;
					break;
				}
			}
		} 
		return entity;
	}
}