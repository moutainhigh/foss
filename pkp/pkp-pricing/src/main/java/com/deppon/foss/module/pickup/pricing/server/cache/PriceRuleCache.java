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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/cache/PriceRuleCache.java
 * 
 * FILE NAME        	: PriceRuleCache.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.cache;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRuleEntity;
import com.deppon.foss.util.common.FossTTLCache;

/**
 * 
 * @Description: 计价规则Cache
 * GoodsTypeCache.java Create on 2013-1-31 上午9:55:40
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PriceRuleCache  extends FossTTLCache<List<PriceRuleEntity>> {
	/**
     * 标记PriceRuleCache的UUID
     * @return
     */
    @Override
    public String getUUID() {
    	return PKP_PRICE_RULE_UUID;
    }
}