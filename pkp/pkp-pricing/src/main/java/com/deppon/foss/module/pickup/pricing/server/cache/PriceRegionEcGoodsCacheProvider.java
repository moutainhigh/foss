package com.deppon.foss.module.pickup.pricing.server.cache;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionEcGoodsDao;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsEntity;
import com.google.inject.Inject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 
 * @Description: 价格区域Provider
 * PriceRegionEcCacheProvider.java Create on 2016.06.29
 * @author 311417 WangFeng
 */
public class PriceRegionEcGoodsCacheProvider implements ITTLCacheProvider<List<PriceRegionEcGoodsEntity>> {

	private static final Logger log = Logger.getLogger(PriceRegionEcGoodsCacheProvider.class);
	/**
	 * 区域DAO
	 */
    @Inject
	private IRegionEcGoodsDao regionEcGoodsDao;
    
	public void setRegionEcGoodsDao(IRegionEcGoodsDao regionEcGoodsDao) {
		this.regionEcGoodsDao = regionEcGoodsDao;
	}


	/**
     * @Description: 根据主键获取价格区域实体
     * @author 311417 WangFeng
     * @date 2016.06.29
     * @return
     */
    @Override
    public List<PriceRegionEcGoodsEntity> get(String key) {
    	log.debug("PriceRegionCacheProvider cacheKey is :"+ key);
    	if (StringUtils.isBlank(key)) {
    	    return null;
    	}
    	String provinceCode = null;
    	String cityCode = null;
    	String countyCode = null;
    	if(StringUtil.isNotBlank(key)) {
    		//将输入参数拆分为省、市、区
    		String[] keys = key.split("#");
    		//省
    		if(StringUtil.isNotBlank(keys[0]) && !StringUtil.equals("key", keys[0])) {
    			provinceCode = keys[0];
    		} 
    		//市
    		if(StringUtil.isNotBlank(keys[1]) && !StringUtil.equals("key", keys[1])) {
    			cityCode = keys[1];
    		} 
    		//区
    		if(StringUtil.isNotBlank(keys[2]) && !StringUtil.equals("key", keys[2])) {
    			countyCode = keys[2];
    		} 
    	}
    	//根据拆分的条件来查询
    	return regionEcGoodsDao.searchRegionByDistrictForCache(provinceCode, cityCode, countyCode, PricingConstants.PRICING_REGION, null);
    }
    
}