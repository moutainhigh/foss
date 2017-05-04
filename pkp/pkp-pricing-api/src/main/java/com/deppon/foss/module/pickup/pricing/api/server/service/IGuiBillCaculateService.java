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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IPdaBillCaculateService.java
 * 
 * FILE NAME        	: IPdaBillCaculateService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;

public interface IGuiBillCaculateService extends IService {
	
    	 /**
	  * 
	  * <p>计算运费</p> 
	  * @author DP-Foss-YueHongJie
	  * @date 2013-4-20 上午11:17:15
	  * @param billCalculateDto
	  * @return
	  * @see
	  */
	 List<GuiResultBillCalculateDto>  queryGuiTransportBillPrice(GuiQueryBillCalculateDto billCalculateDto);
	
	 /**
	  * 
	  * <p>计算增值服务</p> 
	  * @author DP-Foss-YueHongJie
	  * @date 2013-4-20 上午11:18:40
	  * @param billCalculateDto
	  * @return
	  * @see
	  */
	 List<GuiResultBillCalculateDto> queryGuiValueAddBillPrice(GuiQueryBillCalculateDto billCalculateDto);
 
	 /**
	  * 
	  * <p>计算物流费用</p> 
	  * @author DP-Foss-YueHongJie
	  * @date 2013-4-20 上午11:17:15
	  * @param billCalculateDto
	  * @return
	  * @see
	  */
	 List<GuiResultBillCalculateDto>  queryGuiBillPrice(GuiQueryBillCalculateDto billCalculateDto);
	 
	 /**
	  * 获取整车的保费相关信息
	  * @param billCalculateDto
	  * @return
	  */
	 public GuiResultBillCalculateDto getProductPriceDtoOfWVHAndBF(GuiQueryBillCalculateDto billCalculateDto);
	 
	  /**
	  * 
	  * <p>计算快递物流费用</p> 
	  * @author DP-Foss-YueHongJie
	  * @date 2013-4-20 上午11:17:15
	  * @param billCalculateDto
	  * @return
	  * @see
	  */
	 List<GuiResultBillCalculateDto>  queryGuiExpressBillPrice(GuiQueryBillCalculateDto billCalculateDto);
	 
}