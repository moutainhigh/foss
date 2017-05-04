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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/cache/ProductItemCacheDeal.java
 * 
 * FILE NAME        	: ProductItemCacheDeal.java
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

import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductItemEntity;

/**
 * 
 * @Description: 产品条目Cache处理
 * ProductItemCacheDeal.java Create on 2013-2-19 上午11:48:49
 * Company:IBM
 * @author FOSSDP-Administrator
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class ProductItemCacheDeal {
	/**
	 * 产品条目缓存
	 */
	private ProductItemCache productItemCache;

	/**
	 * 设置 产品条目DAO.
	 *
	 * @param productItemCache the new 产品条目DAO
	 */
	public void setProductItemCache(ProductItemCache productItemCache) {
		this.productItemCache = productItemCache;
	}

	/**
	 * 获取 产品条目DAO.
	 *
	 * @return the 产品条目DAO
	 */
	public ProductItemCache getProductItemCache() {
		return productItemCache;
	}

	/**
	 * 
	 * @Description: 根据产品条目CODE从缓存获取实体
	 * @author FOSSDP-sz
	 * @date 2013-2-1 下午5:28:35
	 * @param code
	 * @param date
	 * @return
	 * @version V1.0
	 */
	public ProductItemEntity getProductItemEntityByCache(String code, Date date) {
		List<ProductItemEntity> productItemEntities = productItemCache.get(code);
		ProductItemEntity entity = null;
		if(CollectionUtils.isNotEmpty(productItemEntities)) {
			for (ProductItemEntity productItemEntity : productItemEntities) {
				Date beginTime = productItemEntity.getBeginTime();
				Date endTime = productItemEntity.getEndTime();
				if(beginTime.getTime() <= date.getTime() && endTime.getTime() > date.getTime()) {
					entity = productItemEntity;
					break;
				}
			}
		} 
		return entity;
	}

}