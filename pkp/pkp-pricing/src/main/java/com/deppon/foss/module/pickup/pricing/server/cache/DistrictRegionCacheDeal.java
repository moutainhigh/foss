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

import com.deppon.foss.module.pickup.pricing.api.shared.domain.DistrictRegionEntity;

/**
 * 
 * @Description: 行政区域与区域Cache处理
 * DistrictRegionCacheDeal.java Create on 2013-4-15 下午7:13:04
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class DistrictRegionCacheDeal {
	/**
	 * DistrictRegionCache 缓存
	 */
	private DistrictRegionCache districtRegionCache;
	
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
	public DistrictRegionEntity getDistrictRegionByCache(String code) {
		return districtRegionCache.get(code);
	}
	public DistrictRegionCache getDistrictRegionCache() {
		return districtRegionCache;
	}
	public void setDistrictRegionCache(DistrictRegionCache districtRegionCache) {
		this.districtRegionCache = districtRegionCache;
	}
}