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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/cache/ProductCache.java
 * 
 * FILE NAME        	: ProductCache.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.cache;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultProductPriceDto;
import com.deppon.foss.util.common.FossTTLCache;

/**
 * 
 * 计算价格运费Cache
 * @author DP-Foss-YueHongJie
 * @date 2013-4-15 下午7:38:05
 * @version 1.0
 */
public class PriceValuationCache extends FossTTLCache<List<List<ResultProductPriceDto>>> {
    /**
     * 标记PriceValuationCache的UUID
     * @return
     */
    @Override
    public String getUUID() {
    	return PKP_PRICE_VALUATION_UUID;
    }
}