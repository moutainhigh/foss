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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/CaculateBillFeePackage.java
 * 
 * FILE NAME        	: CaculateBillFeePackage.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.util.List;


 


public class CaculateBillFeePackage {
    
    /**
     * 时效列表，目前情况下只有一个
     */
    private List<EffectivePlanDto> effectivePlanDtoList;
    
    /**
     *  运费列表，一般情况下两条，一条重货价格，一条清货价格
     */
    private List<ProductPriceDto> productPriceDtoList;
    
    /**
     * 在系统中，用户享受的折扣列表信息，针对运费来说，目前业务一般只有一条不会折上折情况。
     * 用户享受的优惠列表信息。针对增值服务，一般多条数据，一种增值服务最多一条。数据有可能来自于CRM
     */
    private List<DiscountDto>  discountDtoList;
    
    
   
    /**
     *  crm系统提供的优惠券打折情况，包括打折率和打折费用类型等信息，目前一种费用优惠信息最多只有一条
     */
    private List<CRMPromotionsDto> crmPromotionList;



    
    public List<EffectivePlanDto> getEffectivePlanDtoList() {
        return effectivePlanDtoList;
    }



    
    public void setEffectivePlanDtoList(List<EffectivePlanDto> effectivePlanDtoList) {
        this.effectivePlanDtoList = effectivePlanDtoList;
    }



    
    public List<ProductPriceDto> getProductPriceDtoList() {
        return productPriceDtoList;
    }



    
    public void setProductPriceDtoList(List<ProductPriceDto> productPriceDtoList) {
        this.productPriceDtoList = productPriceDtoList;
    }



    
    public List<DiscountDto> getDiscountDtoList() {
        return discountDtoList;
    }



    
    public void setDiscountDtoList(List<DiscountDto> discountDtoList) {
        this.discountDtoList = discountDtoList;
    }



    
    public List<CRMPromotionsDto> getCrmPromotionList() {
        return crmPromotionList;
    }



    
    public void setCrmPromotionList(List<CRMPromotionsDto> crmPromotionList) {
        this.crmPromotionList = crmPromotionList;
    }
    
    

}