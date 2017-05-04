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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IPriceValuationService.java
 * 
 * FILE NAME        	: IPriceValuationService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.service;import java.util.List;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.NewResultProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultProductPriceDto;

/**
 * 
 * 提供查询价格方案详情信息，提供查询与缓存服务
 * @author DP-Foss-YueHongJie
 * @date 2013-4-15 下午7:25:43
 * @version 1.0
 */
public interface IPriceValuationService extends IService {
	/**
	 * 
	 * 查询价格与计费规则以及计费明细信息
	 * @author DP-Foss-YueHongJie
	 * @date 2012-11-8 上午9:50:08
	 */
	 List<ResultProductPriceDto> queryPriceValuationByCalculaCondition(QueryProductPriceDto queryProductPriceDto);
	 
	 
	 /**
	  * 零担汽运查询运费计算信息
	  * @param queryProductPriceDto
	  * @return
	  */
	 List<NewResultProductPriceDto> queryPriceValuationByCodition(QueryProductPriceDto queryProductPriceDto);
	 /**
	  * 
	  * <p>
	  * 缓存获取价格计费规则以及计费明细信息
	  * </p> 
	  * @author DP-Foss-YueHongJie
	  * @date 2013-4-15 下午7:20:22
	  * @param queryProductPriceDto
	  * @return
	  * @see
	  */
	 List<ResultProductPriceDto> queryPriceValuationByCalculaCach(QueryProductPriceDto queryProductPriceDto);
	
}