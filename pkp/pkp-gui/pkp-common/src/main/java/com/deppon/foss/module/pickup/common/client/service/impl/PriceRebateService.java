/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/impl/PriceRebateService.java
 * 
 * FILE NAME        	: PriceRebateService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.common.client.service.impl;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.common.client.dao.IPriceRuleDao;
import com.deppon.foss.module.pickup.common.client.dto.PriceRuleDto;
import com.deppon.foss.module.pickup.common.client.service.IPriceRebateService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRuleEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.EffectivePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * 价格服务
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-11-8 上午11:33:34
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-11-8 上午11:33:34
 * @since
 * @version
 */
public class PriceRebateService implements IPriceRebateService {

	@Inject
	private IPriceRuleDao priceRuleDao;
	
	
	@Override
	public PriceRuleEntity queryPriceRuleByCode(String code, Date billTime) {
		PriceRuleDto priceRule = new PriceRuleDto();
		priceRule.setCode(code);
		priceRule.setActive(FossConstants.ACTIVE);
		priceRule.setBillTime(billTime);
		return priceRuleDao.queryByCode(priceRule);
	}

	@Override
	public List<EffectivePlanDto> searchEffectivePlanDetailList(
			String originalOrgCode, String destinationOrgCode,
			String productCode, Date billDate) {
		return null;
	}

	@Override
	public List<ProductPriceDto> searchProductPriceList(
			QueryBillCacilateDto queryDto) {
		return null;
	}
	
}