/**
 *  initial comments.
 */
package com.deppon.foss.module.pickup.pricing.server.cache;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionBigGoodsArriveDao;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionBigGoodsArriveEntity;

/**
 * 
 * @Description: 空运价格区域Provider
 * PriceRegionCacheProvider.java Create on 2013-2-18 下午5:39:25
 * Company:IBM
 * @author FOSSDP-Administrator
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PriceRegionBigGoodsArriveCacheProvider implements ITTLCacheProvider<List<PriceRegionBigGoodsArriveEntity>> {

	/**
	 * 
	 */
	private static final Logger log = Logger.getLogger(PriceRegionBigGoodsArriveCacheProvider.class);
	/**
	 * 空运区域DAO
	 */
	private IRegionBigGoodsArriveDao regionBigGoodsArriveDao;

	public void setRegionBigGoodsArriveDao(
			IRegionBigGoodsArriveDao regionBigGoodsArriveDao) {
		this.regionBigGoodsArriveDao = regionBigGoodsArriveDao;
	}



	/**
     * @Description: 根据主键获取价格区域实体
     * @author FOSS-潘国仰
     * @date 2014-07-09 上午11:22:13
     * @return
     * @version V1.0
     */
    @Override
    public List<PriceRegionBigGoodsArriveEntity> get(String key) {
    	log.debug("PriceRegionArriveCacheProvider cacheKey is :"+ key);
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
    	if(StringUtil.isEmpty(provinceCode)
    	   &&
    	   StringUtil.isEmpty(cityCode)
    	   && 
    	   StringUtil.isEmpty(countyCode)){
    		return null;
    	}
    	return regionBigGoodsArriveDao.searchRegionByDistrictForCache(provinceCode, cityCode, countyCode, PricingConstants.ARRIVE_REGION, null);
    }
    
}