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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/IPriceRebateService.java
 * 
 * FILE NAME        	: IPriceRebateService.java
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
package com.deppon.foss.module.pickup.common.client.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRuleEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.EffectivePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;

/**
 * 
 * 价格服务接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-29 上午10:34:41,
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-10-29 上午10:34:41
 * @since
 * @version
 */
public interface IPriceRebateService extends IService {

    /**
     * 
     * <p>
     * 通过价格规则编码查询价格规则
     * </p>
     * 
     * @author foss-sunrui
     * @date 2012-11-8 上午11:26:54
     * @param code
     * @return
     * @see
     */
     PriceRuleEntity queryPriceRuleByCode(String code, Date billTime);

    /**
     * 
     * <p>
     * Description: 查询产品时效 <br />
     * </p>
     * 
     * @author DP-Foss-YueHongJie
     * @version 0.1 2012-10-25
     * @param originalOrgCode
     *            出发部门
     * @param destinationOrgCode
     *            到达部门
     * @param productCode
     *            产品code
     * @parm billDate 开单日期 可空 ，默认为当前时间
     * @return List<EffectivePlanDto>
     */
     List<EffectivePlanDto> searchEffectivePlanDetailList(
	    String originalOrgCode, String destinationOrgCode,
	    String productCode, Date billDate);

    /**
     * <p>
     * 计算产品费用
     * </p>
     * 
     * @author DP-Foss-YueHongJie
     * @date 2012-11-12
     * @param queryDto 输入产品价格计算条件dto
     * @return ProductPriceDto 输出产品价格计算后的dto
     * @see
     */
     List<ProductPriceDto> searchProductPriceList(
	    QueryBillCacilateDto queryDto);

}