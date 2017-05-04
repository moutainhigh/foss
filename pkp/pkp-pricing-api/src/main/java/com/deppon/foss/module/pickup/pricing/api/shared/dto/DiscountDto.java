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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/DiscountDto.java
 * 
 * FILE NAME        	: DiscountDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.math.BigDecimal;


/**
 * 
 */
public class DiscountDto {

    
    /**
     *PriceCriteriaDetailEntity 的id
     */
    private String id;

    /**
     *  产品CODE 第三级的产品代码
     */
    private String productCode;
    
    /**
     * 产品name
     */
    private String productName; 
    
    
    /**
     * 打折针对的费用类型代码
     */
    private String  priceEntityCode;
    
    /**
     *  打折针对的费用类型名称
     */
    private String priceEntityName;
    
    
    /**
     * 打折针对的货物类型CODE
     */
    private String goodsTypeCode; 
    
    
    /**
     * 打折针对的货物类型name
     */
    private String goodsTypeName; 
    
     
     
    /**
     * 折扣率
     */
     
    private BigDecimal discountRate;
    
     
     
    /**
     * 固定折扣费用
     */
    private Long fixedDiscountFee;
 
        
    /**
     * 价格计算表达式
     */
    private String caculateExpression;


    
    
    /**
     * 获取 价格计算表达式.
     *
     * @return the 价格计算表达式
     */
    public String getCaculateExpression() {
        return caculateExpression;
    }



    
    /**
     * 设置 价格计算表达式.
     *
     * @param caculateExpression the new 价格计算表达式
     */
    public void setCaculateExpression(String caculateExpression) {
        this.caculateExpression = caculateExpression;
    }



    /**
     * 获取 priceCriteriaDetailEntity 的id.
     *
     * @return the priceCriteriaDetailEntity 的id
     */
    public String getId() {
        return id;
    }


    
    /**
     * 设置 priceCriteriaDetailEntity 的id.
     *
     * @param id the new priceCriteriaDetailEntity 的id
     */
    public void setId(String id) {
        this.id = id;
    }


    
    /**
     * 获取 产品CODE 第三级的产品代码.
     *
     * @return the 产品CODE 第三级的产品代码
     */
    public String getProductCode() {
        return productCode;
    }


    
    /**
     * 设置 产品CODE 第三级的产品代码.
     *
     * @param productCode the new 产品CODE 第三级的产品代码
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }


    
    /**
     * 获取 产品name.
     *
     * @return the 产品name
     */
    public String getProductName() {
        return productName;
    }


    
    /**
     * 设置 产品name.
     *
     * @param productName the new 产品name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }


    
    /**
     * 获取 打折针对的费用类型代码.
     *
     * @return the 打折针对的费用类型代码
     */
    public String getPriceEntityCode() {
        return priceEntityCode;
    }


    
    /**
     * 设置 打折针对的费用类型代码.
     *
     * @param priceEntityCode the new 打折针对的费用类型代码
     */
    public void setPriceEntityCode(String priceEntityCode) {
        this.priceEntityCode = priceEntityCode;
    }


    
    /**
     * 获取 打折针对的费用类型名称.
     *
     * @return the 打折针对的费用类型名称
     */
    public String getPriceEntityName() {
        return priceEntityName;
    }


    
    /**
     * 设置 打折针对的费用类型名称.
     *
     * @param priceEntityName the new 打折针对的费用类型名称
     */
    public void setPriceEntityName(String priceEntityName) {
        this.priceEntityName = priceEntityName;
    }


    
    /**
     * 获取 打折针对的货物类型CODE.
     *
     * @return the 打折针对的货物类型CODE
     */
    public String getGoodsTypeCode() {
        return goodsTypeCode;
    }


    
    /**
     * 设置 打折针对的货物类型CODE.
     *
     * @param goodsTypeCode the new 打折针对的货物类型CODE
     */
    public void setGoodsTypeCode(String goodsTypeCode) {
        this.goodsTypeCode = goodsTypeCode;
    }


    
    /**
     * 获取 打折针对的货物类型name.
     *
     * @return the 打折针对的货物类型name
     */
    public String getGoodsTypeName() {
        return goodsTypeName;
    }


    
    /**
     * 设置 打折针对的货物类型name.
     *
     * @param goodsTypeName the new 打折针对的货物类型name
     */
    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }




    
    /**
     * 获取 折扣率.
     *
     * @return the 折扣率
     */
    public BigDecimal getDiscountRate() {
        return discountRate;
    }




    
    /**
     * 设置 折扣率.
     *
     * @param discountRate the new 折扣率
     */
    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }




    
    /**
     * 获取 固定折扣费用.
     *
     * @return the 固定折扣费用
     */
    public Long getFixedDiscountFee() {
        return fixedDiscountFee;
    }




    
    /**
     * 设置 固定折扣费用.
     *
     * @param fixedDiscountFee the new 固定折扣费用
     */
    public void setFixedDiscountFee(Long fixedDiscountFee) {
        this.fixedDiscountFee = fixedDiscountFee;
    }


   
    
    

      

}