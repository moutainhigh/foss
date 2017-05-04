package com.deppon.foss.module.pickup.pricing.server.cache;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnEcGoodsEntity;
import com.deppon.foss.util.common.FossTTLCache;

import java.util.List;

/**
 * 
 * @Description: 价格区域与组织关系Cache
 * @Date 2016.06.29
 * @author 311417 wangfeng
 */
public class PriceRegionEcGoodsOrgCache extends FossTTLCache<List<PriceRegioOrgnEcGoodsEntity>> {

    /**
     * 标记PriceRegionEcOrgCache的UUID
     * @return
     */
    @Override
    public String getUUID() {
    	return PKP_PRICE_REGION_EC_ORG_UUID;
    }
}