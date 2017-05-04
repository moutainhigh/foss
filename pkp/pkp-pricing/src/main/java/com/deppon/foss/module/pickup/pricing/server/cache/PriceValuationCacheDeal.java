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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/cache/ProductCacheDeal.java
 * 
 * FILE NAME        	: ProductCacheDeal.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.cache;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultProductPriceDto;

/**
 * 
 * 计算价格运费cach处理
 * @author DP-Foss-YueHongJie
 * @date 2013-4-15 下午7:36:46
 * @version 1.0
 */
public class PriceValuationCacheDeal {
    	
    
	/**
	 * priceValuationCache 缓存
	 */
	private PriceValuationCache priceValuationCache; 
 
	/**
	 * 
	 * <p>根据key参数获得重轻货缓存</p>
	 * @author DP-Foss-YueHongJie
	 * @date 2013-4-19 下午2:32:02
	 * @param 汽运： key ="出发区域ID#到达区域ID#币种#产品#货物类型#是否上门"
	 * 	    空运 ：key ="出发区域ID#到达区域ID#币种#产品#货物类型#是否上门#航班类型"
	 * @param billDate
	 * @return
	 * @see
	 */
	public List<ResultProductPriceDto> getPriceValuationByCache(String key, Date billDate) {
	    List<List<ResultProductPriceDto>>  resultProductPriceDtosList = priceValuationCache.get(key);
	    if(!CollectionUtils.isEmpty(resultProductPriceDtosList)){
		for (List<ResultProductPriceDto> resultProductPriceDtos : resultProductPriceDtosList) {
		    Date beginTime = resultProductPriceDtos.get(0).getBeginTime();
		    Date endTime = resultProductPriceDtos.get(0).getEndTime();
		    if(beginTime.getTime() <= billDate.getTime() && endTime.getTime() > billDate.getTime()) {
			return resultProductPriceDtos;
		    }
		}
	    }
	    return null;
	}

	
	/**
	 * 获取 priceValuationCache 缓存.
	 *
	 * @return the priceValuationCache 缓存
	 */
	public PriceValuationCache getPriceValuationCache() {
	    return priceValuationCache;
	}

	
	/**
	 * 设置 priceValuationCache 缓存.
	 *
	 * @param priceValuationCache the new priceValuationCache 缓存
	 */
	public void setPriceValuationCache(PriceValuationCache priceValuationCache) {
	    this.priceValuationCache = priceValuationCache;
	}
}