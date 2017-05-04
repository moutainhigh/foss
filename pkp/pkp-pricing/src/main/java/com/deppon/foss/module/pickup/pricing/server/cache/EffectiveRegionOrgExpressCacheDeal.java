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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/cache/EffectiveRegionOrgCacheDeal.java
 * 
 * FILE NAME        	: EffectiveRegionOrgCacheDeal.java
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

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnExpressEntity;

/**
 * 
 * @Description: 时效区域与组织关系Cache处理
 * EffectiveRegionOrgCacheDeal.java Create on 2013-2-19 下午2:35:44
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class EffectiveRegionOrgExpressCacheDeal {
	/**
	 * 时效区域和部门缓存
	 */
	private EffectiveRegionOrgExpressCache effectiveRegionOrgExpressCache;

	/**
	 * 获取 时效区域和部门缓存.
	 *
	 * @return the 时效区域和部门缓存
	 */
	public EffectiveRegionOrgExpressCache getEffectiveRegionOrgExpressCache() {
		return effectiveRegionOrgExpressCache;
	}
	
	/**
	 * 设置 时效区域和部门缓存.
	 *
	 * @param effectiveRegionOrgExpressCache the new 时效区域和部门缓存
	 */
	public void setEffectiveRegionOrgExpressCache(
			EffectiveRegionOrgExpressCache effectiveRegionOrgExpressCache) {
		this.effectiveRegionOrgExpressCache = effectiveRegionOrgExpressCache;
	}
	

	/**
	 * 
	 * @Description: 根据网点编号以及发生日期从缓存中查询符合条件 时效区域与组织关系实体。
	 * 如缓存中没有查询到相应实体，则直接从数据库中进行查询
	 * @author FOSSDP-sz
	 * @date 2013-1-31 上午10:32:48
	 * @param code
	 * @param date
	 * @return
	 * @version V1.0
	 */
	public PriceRegioOrgnExpressEntity getPriceRegionOrgExpressByCache(String code, Date date) {
		List<PriceRegioOrgnExpressEntity> priceRegioOrgnEntities = effectiveRegionOrgExpressCache.get(code);
		PriceRegioOrgnExpressEntity entity = null;
		if(CollectionUtils.isNotEmpty(priceRegioOrgnEntities)) {
			for (PriceRegioOrgnExpressEntity priceRegioOrgnEntity : priceRegioOrgnEntities) {
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


	/**
	 * 
	 * @Description: 根据组织编号查询是否存在截止时间小于当前时间
	 * @author FOSSDP-xmm
	 * @date 2013-8-23 上午10:32:48
	 * @param code
	 * @param date
	 * @return
	 * @version V1.0
	 */
	public Boolean existLatterEndTimeRegionOrgExpressByCache(String code, Date date) {
		List<PriceRegioOrgnExpressEntity> priceRegioOrgnEntities = effectiveRegionOrgExpressCache.get(code);
		if(CollectionUtils.isNotEmpty(priceRegioOrgnEntities)) {
			for (PriceRegioOrgnExpressEntity priceRegioOrgnEntity : priceRegioOrgnEntities) {
				Date endTime = priceRegioOrgnEntity.getEndTime();
				if(endTime.getTime() < date.getTime()) {
					return true;
				}
			}
		} 
		return false;
	}
}