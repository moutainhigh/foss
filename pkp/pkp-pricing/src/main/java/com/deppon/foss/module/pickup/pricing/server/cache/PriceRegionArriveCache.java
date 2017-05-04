/**
 *  initial comments.
 */
package com.deppon.foss.module.pickup.pricing.server.cache;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionArriveEntity;
import com.deppon.foss.util.common.FossTTLCache;

/**
 * 
 * @Description: 到达区域Cache
 * PriceRegionCache.java Create on 2013-2-18 下午5:38:47
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PriceRegionArriveCache extends FossTTLCache<List<PriceRegionArriveEntity>> {
	/**
     * 标记PriceRegionAirCache的UUID
     * @return
     */
    @Override
    public String getUUID() {
    	return PKP_PRICE_REGION_ARRIVE_UUID;
    }
}