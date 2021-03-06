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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/cache/ProductCacheDeal.java
 * 
 * FILE NAME        	: ProductCacheDeal.java
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

import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;

/**
 * 
 * @Description: 产品Cache处理
 * ProductCacheDeal.java Create on 2013-1-30 上午10:47:40
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class ProductCacheDeal {
	/**
	 * productCache 缓存
	 */
	private ProductCache productCache; 
 
	/**
	 * @Description: 根据产品CODE从缓存获取实体
	 * @author FOSSDP-sz
	 * @date 2013-2-1 上午11:08:40
	 * @param code
	 * @param billDate
	 * @return
	 * @version V1.0
	 */
	public ProductEntity getProductEntityByCache(String code, Date billDate) {
		List<ProductEntity> productEntities = productCache.get(code);
		ProductEntity entity = null;
		if(CollectionUtils.isNotEmpty(productEntities)) {
			for (ProductEntity productEntity : productEntities) {
				Date beginTime = productEntity.getBeginTime();
				Date endTime = productEntity.getEndTime();
				if(beginTime.getTime() <= billDate.getTime() && endTime.getTime() > billDate.getTime()) {
					entity = productEntity;
					break;
				}
			}
		} 
		return entity;
	}
	/**
	 * 设置 productCache 缓存.
	 *
	 * @param productCache the new productCache 缓存
	 */
	public void setProductCache(ProductCache productCache) {
		this.productCache = productCache;
	}

	/**
	 * 获取 productCache 缓存.
	 *
	 * @return the productCache 缓存
	 */
	public ProductCache getProductCache() {
		return productCache;
	}

	
}