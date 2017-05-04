package com.deppon.foss.module.pickup.pricing.server.cache;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionEcGoodsDao;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnEcGoodsEntity;
import com.google.inject.Inject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;

/**
 *
 * @Description: 价格区域与组织关系Provider
 * PriceRegionOrgEcCacheProvider.java
 * @Date 2016.06.29
 * @author 311417 wangfeng
 */
public class PriceRegionEcGoodsOrgCacheProvider implements ITTLCacheProvider<List<PriceRegioOrgnEcGoodsEntity>> {

	private static final Logger log = Logger.getLogger(PriceRegionEcGoodsOrgCacheProvider.class);

	/**
	 * 区域 DAO
	 */
    @Inject
	private IRegionEcGoodsDao regionEcGoodsDao;
    
	public void setRegionEcGoodsDao(IRegionEcGoodsDao regionEcGoodsDao) {
		this.regionEcGoodsDao = regionEcGoodsDao;
	}

	/**
     * @Description: 根据网点CODE获取价格区域与组织关系实体
     * @author 311417 WangFeng
     * @date 2016.06.29
     * @return
     * @version V1.0
     */
    @Override
    public List<PriceRegioOrgnEcGoodsEntity> get(String key) {
    	log.debug("PriceRegionOrgCacheProvider cacheKey is :"+ key);
    	if (StringUtils.isBlank(key)) {
    	    return null;
    	}
    	return regionEcGoodsDao.searchRegionOrgByDeptNo(key, PricingConstants.PRICING_REGION);
    }
}