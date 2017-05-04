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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/PriceValuationService.java
 * 
 * FILE NAME        	: PriceValuationService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressPriceValuationDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IExpressPriceValuationService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultProductPriceDto;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceValuationCacheDeal;
import com.deppon.foss.util.CollectionUtils;
import com.google.inject.Inject;

/**
 * 
 * 提供价格方案详情服务查询
 * @author DP-Foss-YueHongJie
 * @date 2013-4-15 下午7:27:49
 * @version 1.0
 */
public class ExpressPriceValuationService implements IExpressPriceValuationService{
    
    /**
     * 日志
     */
    private static final Logger log = Logger.getLogger(ExpressPriceValuationService.class);
	
    /** 
     * 计费规则dao
     */
    @Inject
    IExpressPriceValuationDao expressPriceValuationDao;
    
    /**
     * 价格详情CacheDeal
     */
    PriceValuationCacheDeal priceValuationCacheDeal;
    
    /**
     * 
     * <p>获得价格详情CacheDetail</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-16 上午8:40:18
     * @param priceValuationCacheDeal
     * @see
     */
    public void setPriceValuationCacheDeal(
    	PriceValuationCacheDeal priceValuationCacheDeal) {
        this.priceValuationCacheDeal = priceValuationCacheDeal;
    }

    /**
     * 设置 计费规则dao.
     *
     * @param priceValuationDao the new 计费规则dao
     */
    public void setExpressPriceValuationDao(
			IExpressPriceValuationDao expressPriceValuationDao) {
		this.expressPriceValuationDao = expressPriceValuationDao;
	}
    /**
     * 
     * <p>根据查询实体查询价格详细计算信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-15 下午7:29:33
     * @param dto
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPriceValuationService#queryPriceValuationByCalculaCondition(com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryProductPriceDto)
     */
    @Override
    public List<ResultProductPriceDto> queryPriceValuationByCalculaCondition(QueryProductPriceDto dto) {
    	return expressPriceValuationDao.queryPriceValuationByCalculaCondition(dto);
    }
    
   

	/**
     * 
     * <p>根据查询实体在缓存中查询价格详细计算信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-15 下午7:28:30
     * @param queryProductPriceDto
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPriceValuationService#queryPriceValuationByCalculaCach(com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryProductPriceDto)
     */
    @Override
    public List<ResultProductPriceDto> queryPriceValuationByCalculaCach(
	  QueryProductPriceDto queryProductPriceDto) {
        try {
            Date billDate = queryProductPriceDto.getReceiveDate();
            if (billDate == null) {
                billDate = new Date();
            }
            String originalOrgId = queryProductPriceDto.getOriginalOrgId();
            String destinationId = queryProductPriceDto.getDestinationId();
            String productCode = queryProductPriceDto.getProductCode();
            String currencyCode = queryProductPriceDto.getCurrencyCode();
            String goodsTypeCode = queryProductPriceDto.getGoodsTypeCode();
            String isReceiveGoods = queryProductPriceDto.getIsReceiveGoods();
            String flightShift = null;
            if(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20004.equals(productCode)){
                flightShift = queryProductPriceDto.getFlightShift();
                if(StringUtils.isEmpty(flightShift)){
            	 log.error("航班类型不能为空");
                }
            }
            if(StringUtils.isEmpty(originalOrgId)){
    	    	log.error("出发区域ID不能为空");
    	    }else if(StringUtils.isEmpty(destinationId)){
    		log.error("到达区域ID不能为空");
    	    }else if(StringUtils.isEmpty(productCode)){
    		log.error("产品编码不能为空");
    	    }else if(StringUtils.isEmpty(goodsTypeCode)){
    		log.error("货物编码不能为空");
    	    }else if(StringUtils.isEmpty(currencyCode)){
    		log.error("币种不能为空");
    	    }else if(StringUtils.isEmpty(isReceiveGoods)){
    		log.error("是否上门接货不能为空");
    	    } 
           
            StringBuilder keyBuffer = new StringBuilder();
            keyBuffer.append(originalOrgId);
            keyBuffer.append("#");
            keyBuffer.append(destinationId);
            keyBuffer.append("#");
            keyBuffer.append(currencyCode);
            keyBuffer.append("#");
            keyBuffer.append(productCode);
            keyBuffer.append("#");
            keyBuffer.append(goodsTypeCode);
            keyBuffer.append("#");
            keyBuffer.append(isReceiveGoods);
            String key = keyBuffer.toString();
            
	    List<ResultProductPriceDto>  resultProductPriceDtos = priceValuationCacheDeal.getPriceValuationByCache(key, billDate);
	    if(CollectionUtils.isNotEmpty(resultProductPriceDtos)){
		return resultProductPriceDtos;	
	    }else{
		return expressPriceValuationDao.queryPriceValuationByCalculaCondition(queryProductPriceDto);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    log.info("获取运费缓存时,出现错误",e);
	}
        return null;
    }

}