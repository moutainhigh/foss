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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/cache/GoodsTypeCacheDeal.java
 * 
 * FILE NAME        	: GoodsTypeCacheDeal.java
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

import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;

/**
 * 
 * @Description: 货物类型Cache处理
 * GoodsTypeCacheDeal.java Create on 2013-2-19 上午11:46:57
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class GoodsTypeCacheDeal {
	/**
	 * goodsTypeCache 缓存
	 */
	private GoodsTypeCache goodsTypeCache;

	/**
	 * 
	 * @Description: 根据货物类型编号以及发生日期从缓存中查询符合条件货物类型实体。
	 * 如缓存中没有查询到相应实体，则直接从数据库中进行查询
	 * @author FOSSDP-sz
	 * @date 2013-1-31 上午10:32:48
	 * @param code
	 * @param date
	 * @return
	 * @version V1.0
	 */
	public GoodsTypeEntity getGoodsTypeEntityByCache(String code, Date date) {
		List<GoodsTypeEntity> goodsTypeEntities = goodsTypeCache.get(code);
		GoodsTypeEntity entity = null;
		if(CollectionUtils.isNotEmpty(goodsTypeEntities)) {
			for (GoodsTypeEntity goodsTypeEntity : goodsTypeEntities) {
				Date beginTime = goodsTypeEntity.getBeginTime();
				Date endTime = goodsTypeEntity.getEndTime();
				if(beginTime.getTime() <= date.getTime() && endTime.getTime() > date.getTime()) {
					entity = goodsTypeEntity;
					break;
				}
			}
		} 
		return entity;
	}

	/**
	 * 获取 goodsTypeCache 缓存.
	 *
	 * @return the goodsTypeCache 缓存
	 */
	public GoodsTypeCache getGoodsTypeCache() {
		return goodsTypeCache;
	}

	/**
	 * 设置 goodsTypeCache 缓存.
	 *
	 * @param goodsTypeCache the new goodsTypeCache 缓存
	 */
	public void setGoodsTypeCache(GoodsTypeCache goodsTypeCache) {
		this.goodsTypeCache = goodsTypeCache;
	}

}