/**
 *  initial comments.
 */
package com.deppon.foss.module.pickup.pricing.server.cache;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionAirDao;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionAirEntity;
import com.google.inject.Inject;

/**
 * 
 * @Description: 空运价格区域Provider
 * PriceRegionCacheProvider.java Create on 2013-2-18 下午5:39:25
 * Company:IBM
 * @author FOSSDP-Administrator
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PriceRegionAirCacheProvider implements ITTLCacheProvider<List<PriceRegionAirEntity>> {

	/**
	 * 
	 */
	private static final Logger log = Logger.getLogger(PriceRegionAirCacheProvider.class);
	/**
	 * 空运区域DAO
	 */
    @Inject
	private IRegionAirDao regionAirDao;

	/**
	 * 设置 空运区域DAO.
	 *
	 * @param regionAirDao the new 空运区域DAO
	 */
	public void setRegionAirDao(IRegionAirDao regionAirDao) {
		this.regionAirDao = regionAirDao;
	}
	/**
     * @Description: 根据主键获取价格区域实体
     * @author FOSSDP-sz
     * @date 2013-1-30 上午11:22:13
     * @return
     * @version V1.0
     */
    @Override
    public List<PriceRegionAirEntity> get(String key) {
    	log.debug("PriceRegionAirCacheProvider cacheKey is :"+ key);
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
    	return regionAirDao.searchRegionByDistrictForCache(provinceCode, cityCode, countyCode, PricingConstants.PRICING_REGION, null);
    }
    
}