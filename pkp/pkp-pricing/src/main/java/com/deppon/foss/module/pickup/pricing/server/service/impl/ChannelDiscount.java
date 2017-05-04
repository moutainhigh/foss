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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/ChannelDiscount.java
 * 
 * FILE NAME        	: ChannelDiscount.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.pricing.api.server.service.DiscountTypeInterface;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceDiscountService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IValueAddDiscountService;
import com.deppon.foss.module.pickup.pricing.api.server.util.PriceUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.DiscountTypeConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.DiscountParmDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.DiscountResultDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;
import com.deppon.foss.util.CollectionUtils;
import com.google.inject.Inject;
/**
 * 
 * @Description: 
 * ChannelDiscount.java Create on 2013-3-17 下午4:47:51
 * Company:IBM
 * @author FOSSDP-Administrator
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class ChannelDiscount implements DiscountTypeInterface {
	/**
	 *  折扣服务 SERVICE
	 */
	@Inject
    private IPriceDiscountService priceDiscountService; 
	/**
	 *  增值优惠 SERVICE
	 */
	@Inject
    private IValueAddDiscountService valueAddDiscountService; 
	/**
   	 * 数据字典SERVICE
   	 */ 
    @Inject
    private IDataDictionaryValueService dataDictionaryValueService;

	/**
	 * 设置 折扣服务 SERVICE.
	 *
	 * @param priceDiscountService the new 折扣服务 SERVICE
	 */
	public void setPriceDiscountService(IPriceDiscountService priceDiscountService) {
		this.priceDiscountService = priceDiscountService;
	}
	
	/**
	 * 设置 增值优惠 SERVICE.
	 *
	 * @param valueAddDiscountService the new 增值优惠 SERVICE
	 */
	public void setValueAddDiscountService(
			IValueAddDiscountService valueAddDiscountService) {
		this.valueAddDiscountService = valueAddDiscountService;
	}
	
	/**
	 * 设置 数据字典SERVICE.
	 *
	 * @param dataDictionaryValueService the new 数据字典SERVICE
	 */
	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}
	/**
	 * 
	 * @Description: 获取渠道折扣操作类
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-3-17 下午4:33:57
	 * 
	 * @param discountParmDto
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	@Override
	public DiscountResultDto doDiscount(DiscountParmDto parm) {
		DiscountResultDto discountResultDto = null;
		if(StringUtil.isNotBlank(parm.getSaleChannelCode())) {
			String discountType = PriceUtil.getFirstLevelEntryCode(parm.getPricingEntryCode());
			//增值优惠
			if (StringUtil.equals(discountType, PriceEntityConstants.PRICING_CODE_VALUEADDED)){
				List<PriceDiscountDto> priceDiscountDtos = valueAddDiscountService.calculateChannelDiscount(parm);
				if(priceDiscountDtos != null && priceDiscountDtos.size() > 0) {
					discountResultDto = PriceUtil.compareValueAddDiscount(priceDiscountDtos, parm);
				}
				//运费
			} else if (StringUtil.equals(discountType, PriceEntityConstants.PRICING_CODE_FRT)){
				List<PriceDiscountDto> priceDiscountDtos = priceDiscountService.calculateChannelDiscount(parm);
				if(priceDiscountDtos != null && priceDiscountDtos.size() > 0) {
					discountResultDto = PriceUtil.comparePriceDiscount(priceDiscountDtos, parm);
				}
			}
			//封装优惠相应名称信息
			if(discountResultDto != null && CollectionUtils.isNotEmpty(discountResultDto.getDiscountPrograms())) {
				for (int i = 0; i < discountResultDto.getDiscountPrograms().size(); i++) {
					PriceDiscountDto priceDiscountDto = discountResultDto.getDiscountPrograms().get(i);
					//设置计费ID
					priceDiscountDto.setChargeDetailId(parm.getCriteriaDetailId());
					//设置打折类型CODE
					priceDiscountDto.setType(DiscountTypeConstants.DISCOUNT_TYPE__CHANEL);
					//设置打折类型NAME
					priceDiscountDto.setTypeName(DiscountTypeConstants.DISCOUNT_TYPE__CHANEL_NAME);
					//设置打折ID
					priceDiscountDto.setDiscountId(priceDiscountDto.getPriceCriteriaId());
					//从数据字典查询折扣渠道
					DataDictionaryValueEntity dictionaryValueEntityForChannel = dataDictionaryValueService
							.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.PKP_PRICE_CHANNAL, priceDiscountDto.getSaleChannelCode());
					//设置打折活动名称
					priceDiscountDto.setPriceEntryCode(parm.getPricingEntryCode());
					priceDiscountDto.setPriceEntryName(parm.getPricingEntryName());
					//
					priceDiscountDto.setMarketName(parm.getPricingEntryName());
					if(dictionaryValueEntityForChannel != null && StringUtils.isNotBlank(dictionaryValueEntityForChannel.getValueName())) {
						//设置打折渠道名称
						priceDiscountDto.setSaleChannelName(dictionaryValueEntityForChannel.getValueName());
					} else {
						priceDiscountDto.setSaleChannelName("渠道折扣");
					}
				}
			}
			return discountResultDto;
		} else {
			return null;
		}
	}

	@Override
	public DiscountResultDto doExpressDiscount(DiscountParmDto parm) {
		DiscountResultDto discountResultDto = null;
		if(StringUtil.isNotBlank(parm.getSaleChannelCode())) {
			String discountType = PriceUtil.getFirstLevelEntryCode(parm.getPricingEntryCode());
			//增值优惠
			if (StringUtil.equals(discountType, PriceEntityConstants.PRICING_CODE_VALUEADDED)){
				List<PriceDiscountDto> priceDiscountDtos = valueAddDiscountService.calculateChannelDiscount(parm);
				if(priceDiscountDtos != null && priceDiscountDtos.size() > 0) {
					discountResultDto = PriceUtil.compareValueAddDiscount(priceDiscountDtos, parm);
				}
				//运费
			} else if (StringUtil.equals(discountType, PriceEntityConstants.PRICING_CODE_FRT)){
				List<PriceDiscountDto> priceDiscountDtos = priceDiscountService.calculateChannelDiscount(parm);
				if(priceDiscountDtos != null && priceDiscountDtos.size() > 0) {
					discountResultDto = PriceUtil.comparePriceDiscount(priceDiscountDtos, parm);
				}
			}
			//封装优惠相应名称信息
			if(discountResultDto != null && CollectionUtils.isNotEmpty(discountResultDto.getDiscountPrograms())) {
				for (int i = 0; i < discountResultDto.getDiscountPrograms().size(); i++) {
					PriceDiscountDto priceDiscountDto = discountResultDto.getDiscountPrograms().get(i);
					//设置计费ID
					priceDiscountDto.setChargeDetailId(parm.getCriteriaDetailId());
					//设置打折类型CODE
					priceDiscountDto.setType(DiscountTypeConstants.DISCOUNT_TYPE__CHANEL);
					//设置打折类型NAME
					priceDiscountDto.setTypeName(DiscountTypeConstants.DISCOUNT_TYPE__CHANEL_NAME);
					//设置打折ID
					priceDiscountDto.setDiscountId(priceDiscountDto.getPriceCriteriaId());
					//从数据字典查询折扣渠道
					DataDictionaryValueEntity dictionaryValueEntityForChannel = dataDictionaryValueService
							.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.PKP_PRICE_CHANNAL, priceDiscountDto.getSaleChannelCode());
					//设置打折活动名称
					priceDiscountDto.setPriceEntryCode(parm.getPricingEntryCode());
					priceDiscountDto.setPriceEntryName(parm.getPricingEntryName());
					//
					priceDiscountDto.setMarketName(parm.getPricingEntryName());
					if(dictionaryValueEntityForChannel != null && StringUtils.isNotBlank(dictionaryValueEntityForChannel.getValueName())) {
						//设置打折渠道名称
						priceDiscountDto.setSaleChannelName(dictionaryValueEntityForChannel.getValueName());
					} else {
						priceDiscountDto.setSaleChannelName("渠道折扣");
					}
				}
			}
			return discountResultDto;
		} else {
			return null;
		}
	} 
	
}