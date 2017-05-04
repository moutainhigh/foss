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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/CRMPromotionsDto.java
 * 
 * FILE NAME        	: CRMPromotionsDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.dto;



public class CRMPromotionsDto {

    
     
    
    
    /**
     * 优惠针对的费用类型代码
     */
    private String  priceEntityCode;
    
    /**
     *  优惠针对的费用类型名称
     */
    private String priceEntityName;
    
        
     
    /**
     * 低扣费用
     */
    private Long fixedDiscountFee;



    
    public String getPriceEntityCode() {
        return priceEntityCode;
    }



    
    public void setPriceEntityCode(String priceEntityCode) {
        this.priceEntityCode = priceEntityCode;
    }



    
    public String getPriceEntityName() {
        return priceEntityName;
    }



    
    public void setPriceEntityName(String priceEntityName) {
        this.priceEntityName = priceEntityName;
    }



    
    public Long getFixedDiscountFee() {
        return fixedDiscountFee;
    }



    
    public void setFixedDiscountFee(Long fixedDiscountFee) {
        this.fixedDiscountFee = fixedDiscountFee;
    }
 
    
    
    

      

}