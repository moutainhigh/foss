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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/cache/DiscountPriorityCacheProvider.java
 * 
 * FILE NAME        	: DiscountPriorityCacheProvider.java
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

import org.apache.log4j.Logger;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IDiscountPriorityDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.DiscountPriorityEntity;
import com.google.inject.Inject;

/**
 * 
 * @Description: 折扣优先级Provider
 * DiscountPriorityCacheProvider.java Create on 2013-2-18 下午5:26:20
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class DiscountPriorityCacheProvider implements ITTLCacheProvider<List<DiscountPriorityEntity>> {

	/**
	 * 
	 */
	private static final Logger log = Logger.getLogger(DiscountPriorityCacheProvider.class);
	
	/**
	 * 折扣优先级DAO
	 */
	@Inject
    private IDiscountPriorityDao discountPriorityDao;
	
	/**
	 * 设置 折扣优先级DAO.
	 *
	 * @param discountPriorityDao the new 折扣优先级DAO
	 */
	public void setDiscountPriorityDao(IDiscountPriorityDao discountPriorityDao) {
		this.discountPriorityDao = discountPriorityDao;
	}

	/**
     * @Description: 根据主键获取折扣优先级实体
     * @author FOSSDP-sz
     * @date 2013-1-30 上午11:22:13
     * @return
     * @version V1.0
     */
    @Override
    public List<DiscountPriorityEntity> get(String key) {
    	log.debug("DiscountPriorityCacheProvider cacheKey is :"+ key);
    	DiscountPriorityEntity discountPriorityEntity = new DiscountPriorityEntity();
    	discountPriorityEntity.setBeginTime(new Date());
    	return discountPriorityDao.selectByCondition(discountPriorityEntity);
    }

}