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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/cache/EffectiveRegionCache.java
 * 
 * FILE NAME        	: EffectiveRegionCache.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.cache;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionExpressEntity;
import com.deppon.foss.util.common.FossTTLCache;

/**
 * 
 * @Description: 时效区域Cache
 * EffectiveRegionCache.java Create on 2013-2-18 下午5:37:57
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class EffectiveRegionExpressCache extends FossTTLCache<List<PriceRegionExpressEntity>> {
 
	/**
     * 标记EffectiveRegionExpress的UUID
     * @return
     */
    @Override
    public String getUUID() {
    	return PKP_EFFECTIVE_REGION_EXPRESS_UUID;
    }
}