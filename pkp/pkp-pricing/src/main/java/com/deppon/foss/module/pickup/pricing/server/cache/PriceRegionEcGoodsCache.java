package com.deppon.foss.module.pickup.pricing.server.cache;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsEntity;
import com.deppon.foss.util.common.FossTTLCache;

import java.util.List;

/**
 * 
 * @Description: 价格区域Cache
 * PriceRegionEcGoodsCache.java Create on 2016.06.29
 * @author 311417 wangfeng
 */
public class PriceRegionEcGoodsCache extends FossTTLCache<List<PriceRegionEcGoodsEntity>> {
	/**
     * 标记PriceRegionCache的UUID
     * @return
     */
    @Override
    public String getUUID() {
    	return PKP_PRICE_REGION_EC_UUID;
    }
}