/**
 *  initial comments.
 */
package com.deppon.foss.module.pickup.pricing.server.cache;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionValueAddEntity;
import com.deppon.foss.util.common.FossTTLCache;

/**
 * 
 * @Description: 增值区域Cache
 * PriceRegionCache.java Create on 2013-2-18 下午5:38:47
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PriceRegionValueAddCache extends FossTTLCache<List<PriceRegionValueAddEntity>> {
	/**
     * 标记PriceRegionAirCache的UUID
     * @return
     */
    @Override
    public String getUUID() {
    	return PKP_PRICE_REGION_VALUEADD_UUID;
    }
}