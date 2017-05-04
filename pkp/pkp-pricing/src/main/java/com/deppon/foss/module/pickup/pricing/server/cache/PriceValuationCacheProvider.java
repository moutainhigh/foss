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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/cache/PriceRuleCacheProvider.java
 * 
 * FILE NAME        	: PriceRuleCacheProvider.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.cache;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceValuationDao;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultProductPriceDto;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * 运费价格 cachProvider 
 * @author DP-Foss-YueHongJie
 * @date 2013-4-16 上午11:47:45
 * @version 1.0
 */
public class PriceValuationCacheProvider implements ITTLCacheProvider<List<List<ResultProductPriceDto>>> {
	
	private static final int THREE = 3;
	
	private static final int FOUR = 4;
	
	private static final int FIVE =5;
	
	private static final int SEVEN = 7;
    /**
     * 序列化
     */
    private static final Logger log = Logger.getLogger(PriceValuationCacheProvider.class);
    
    
    /** 
     * 计费规则dao
     */
    @Inject
    IPriceValuationDao priceValuationDao;
    
    public void setPriceValuationDao(IPriceValuationDao priceValuationDao) {
        this.priceValuationDao = priceValuationDao;
    }

    /**
     * 
     * <p>根据主键获取计价规则实体</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-16 上午11:48:51
     * @param key
     * @return 
     * @see com.deppon.foss.framework.cache.provider.ITTLCacheProvider#get(java.lang.String)
     */
    @Override
    public List<List<ResultProductPriceDto>> get(String key) {
    	log.debug("PriceValuationCacheProvider cacheKey is :"+ key);
    	this.checkInputParameters(key);
    	return null;//priceValuationDao.queryPriceValuationByCalculaCondition(queryProductPriceDto);
    }
    
    /**
     * 
     * <p>检查获取运费缓存key值参数是否存在</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-19 下午2:10:31
     * @return
     * @see
     */
    private QueryProductPriceDto checkInputParameters(String key){
	
	if (StringUtils.isBlank(key)) {
    	    return null;
    	}
	
	//解析条件
    	String[] keys = key.split("#");
	String originalOrgId = keys[0]; 
	String destinationId = keys[1]; 
	String currencyCode = keys[2]; 
	String productCode = keys[THREE]; 
	String goodsTypeCode = keys[FOUR]; 
	String isReceiveGoods = keys[FIVE]; 
	
	//如果是空运产品则累加航班类型
	String flightShift = null;
	if(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productCode)){
	    flightShift = keys[SEVEN];
	}
	
	//运费查询Bean
	QueryProductPriceDto queryProductPriceDto = new QueryProductPriceDto();
	queryProductPriceDto.setOriginalOrgId(originalOrgId);
	queryProductPriceDto.setDestinationId(destinationId);
	queryProductPriceDto.setCurrencyCode(currencyCode);
	queryProductPriceDto.setProductCode(productCode);
	queryProductPriceDto.setGoodsTypeCode(goodsTypeCode);
	queryProductPriceDto.setIsReceiveGoods(isReceiveGoods);
	queryProductPriceDto.setCurrencyCode(currencyCode);
	queryProductPriceDto.setType(PricingConstants.VALUATION_TYPE_PRICING); 
	queryProductPriceDto.setActive(FossConstants.ACTIVE); 
	queryProductPriceDto.setFlightShift(flightShift);
	return queryProductPriceDto;
    }
}