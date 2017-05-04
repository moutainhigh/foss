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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/cache/ProductCacheProvider.java
 * 
 * FILE NAME        	: ProductCacheProvider.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.cache;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IProductDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;

/**
 * @Description: 产品缓存Provider
 * ProductCacheProvider.java Create on 2013-1-30 下午3:01:52
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class ProductCacheProvider implements ITTLCacheProvider<List<ProductEntity>> {
	/**
	 * 
	 */
	private static final Logger log = Logger.getLogger(ProductCacheProvider.class);
	/**
	 * 产品DAO
	 */
    IProductDao productDao;

    /**
     * 设置 产品DAO.
     *
     * @param productDao the new 产品DAO
     */
    public void setProductDao(IProductDao productDao) {
		this.productDao = productDao;
	}
	/**
     * @Description: 根据主键获取产品实体
     * @author FOSSDP-sz
     * @date 2013-1-30 上午11:22:13
     * @return
     * @version V1.0
     */
    @Override
    public List<ProductEntity> get(String key) {
    	log.debug("ProductCacheProvider cacheKey is :"+ key);
    	if (StringUtils.isBlank(key)) {
    	    return null;
    	}
    	return productDao.queryProductsByCode(key);
    }
}