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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/PricePlanDetailDto.java
 * 
 * FILE NAME        	: PricePlanDetailDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 快递价格方案计价明细DTO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-8-5 下午3:38:17 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-8-5 下午3:38:17
 * @since
 * @version
 */
public class ExpressPricePlanDetailDto implements Serializable{
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 7226110345036214751L;
    
    /**
     * 始发区域ID.
     */
    private String origRegionId;
    
    /** 计费规则ID. */
    private String valuationId;
    
    /** 价格方案ID. */
    private String pricePlanId;
    
    /** 目的区域ID. */
    private String arrvRegionId;
    
    /** 目的区域NAME. */
    private String arrvRegionName;
    
    /** 创建时间. */
    private Date createTime;
    
    /** 产品条目ID. */
    private String productItemId;
    
    /** 产品条目CODE. */
    private String productItemCode;
    
    /** 产品条目名称. */
    private String productItemName;
    
    /**
     * 是否自提（Y/N）.
     */
    private String selfPickUp;
    
    /**
     * 计价明细ID(首重).
     */
    private String priceDetailId;
    
    /**
     * 重量上线(首重).
     */
    private BigDecimal weightOnline;
    
    /**
     * 重量下线（首重）.
     */
    private BigDecimal weightDownline;
    
    /**
     * 价格（首重）.
     */
    private BigDecimal firstFee;
    
    /**
     * 计价明细ID(续重1).
     */
    private String oneDetailId;
    
    /**
     * 重量上线(续重1).
     */
    private BigDecimal weightOnlineOne;
    
    /**
     * 重量下线(续重1).
     */
    private BigDecimal weightDownlineOne;
    
    /**
     * 计价明细ID(续重2).
     */
    private String twoDetailId;
    
    /**
     * 费率(续重1).
     */
    private BigDecimal feeRateOne;
    
    /**
     * 重量上线(续重2).
     */
    private BigDecimal weightOnlineTwo;
    
    /**
     * 重量下线(续重2).
     */
    private BigDecimal weightDownlineTwo;
    
    /**
     * 费率(续重2).
     */
    private BigDecimal feeRateTwo;
    
    /** 数据状态. */
    private String active;
    
    /** 备注. */
    private String remark;

    /**
     * 获取 始发区域ID.
     *
     * @return  the origRegionId
     */
    public String getOrigRegionId() {
        return origRegionId;
    }


    
    /**
     * 设置 始发区域ID.
     *
     * @param origRegionId the origRegionId to set
     */
    public void setOrigRegionId(String origRegionId) {
        this.origRegionId = origRegionId;
    }


    /**
     * 获取 计费规则ID.
     *
     * @return  the valuationId
     */
    public String getValuationId() {
        return valuationId;
    }

    
    /**
     * 设置 计费规则ID.
     *
     * @param valuationId the valuationId to set
     */
    public void setValuationId(String valuationId) {
        this.valuationId = valuationId;
    }

    
    /**
     * 获取 价格方案ID.
     *
     * @return  the pricePlanId
     */
    public String getPricePlanId() {
        return pricePlanId;
    }

    
    /**
     * 设置 价格方案ID.
     *
     * @param pricePlanId the pricePlanId to set
     */
    public void setPricePlanId(String pricePlanId) {
        this.pricePlanId = pricePlanId;
    }

    
    /**
     * 获取 目的区域ID.
     *
     * @return  the arrvRegionId
     */
    public String getArrvRegionId() {
        return arrvRegionId;
    }

    
    /**
     * 设置 目的区域ID.
     *
     * @param arrvRegionId the arrvRegionId to set
     */
    public void setArrvRegionId(String arrvRegionId) {
        this.arrvRegionId = arrvRegionId;
    }

    
    /**
     * 获取 目的区域NAME.
     *
     * @return  the arrvRegionName
     */
    public String getArrvRegionName() {
        return arrvRegionName;
    }

    
    /**
     * 设置 目的区域NAME.
     *
     * @param arrvRegionName the arrvRegionName to set
     */
    public void setArrvRegionName(String arrvRegionName) {
        this.arrvRegionName = arrvRegionName;
    }

    
    /**
     * 获取 创建时间.
     *
     * @return  the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    
    /**
     * 设置 创建时间.
     *
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    
    /**
     * 获取 产品条目ID.
     *
     * @return  the productItemId
     */
    public String getProductItemId() {
        return productItemId;
    }

    
    /**
     * 设置 产品条目ID.
     *
     * @param productItemId the productItemId to set
     */
    public void setProductItemId(String productItemId) {
        this.productItemId = productItemId;
    }

    
    /**
     * 获取 产品条目CODE.
     *
     * @return  the productItemCode
     */
    public String getProductItemCode() {
        return productItemCode;
    }

    
    /**
     * 设置 产品条目CODE.
     *
     * @param productItemCode the productItemCode to set
     */
    public void setProductItemCode(String productItemCode) {
        this.productItemCode = productItemCode;
    }

    
    /**
     * 获取 产品条目名称.
     *
     * @return  the productItemName
     */
    public String getProductItemName() {
        return productItemName;
    }

    
    /**
     * 设置 产品条目名称.
     *
     * @param productItemName the productItemName to set
     */
    public void setProductItemName(String productItemName) {
        this.productItemName = productItemName;
    }

    
    /**
     * 获取 是否自提（Y/N）.
     *
     * @return  the selfPickUp
     */
    public String getSelfPickUp() {
        return selfPickUp;
    }

    
    /**
     * 设置 是否自提（Y/N）.
     *
     * @param selfPickUp the selfPickUp to set
     */
    public void setSelfPickUp(String selfPickUp) {
        this.selfPickUp = selfPickUp;
    }

    
    /**
     * 获取 计价明细ID(首重).
     *
     * @return  the priceDetailId
     */
    public String getPriceDetailId() {
        return priceDetailId;
    }

    
    /**
     * 设置 计价明细ID(首重).
     *
     * @param priceDetailId the priceDetailId to set
     */
    public void setPriceDetailId(String priceDetailId) {
        this.priceDetailId = priceDetailId;
    }

    
    /**
     * 获取 重量上线(首重).
     *
     * @return  the weightOnline
     */
    public BigDecimal getWeightOnline() {
        return weightOnline;
    }

    
    /**
     * 设置 重量上线(首重).
     *
     * @param weightOnline the weightOnline to set
     */
    public void setWeightOnline(BigDecimal weightOnline) {
        this.weightOnline = weightOnline;
    }

    
    /**
     * 获取 重量下线（首重）.
     *
     * @return  the weightDownline
     */
    public BigDecimal getWeightDownline() {
        return weightDownline;
    }

    
    /**
     * 设置 重量下线（首重）.
     *
     * @param weightDownline the weightDownline to set
     */
    public void setWeightDownline(BigDecimal weightDownline) {
        this.weightDownline = weightDownline;
    }

    
    /**
     * 获取 价格（首重）.
     *
     * @return  the firstFee
     */
    public BigDecimal getFirstFee() {
        return firstFee;
    }

    
    /**
     * 设置 价格（首重）.
     *
     * @param firstFee the firstFee to set
     */
    public void setFirstFee(BigDecimal firstFee) {
        this.firstFee = firstFee;
    }

    
    /**
     * 获取 计价明细ID(续重1).
     *
     * @return  the oneDetailId
     */
    public String getOneDetailId() {
        return oneDetailId;
    }

    
    /**
     * 设置 计价明细ID(续重1).
     *
     * @param oneDetailId the oneDetailId to set
     */
    public void setOneDetailId(String oneDetailId) {
        this.oneDetailId = oneDetailId;
    }

    
    /**
     * 获取 重量上线(续重1).
     *
     * @return  the weightOnlineOne
     */
    public BigDecimal getWeightOnlineOne() {
        return weightOnlineOne;
    }

    
    /**
     * 设置 重量上线(续重1).
     *
     * @param weightOnlineOne the weightOnlineOne to set
     */
    public void setWeightOnlineOne(BigDecimal weightOnlineOne) {
        this.weightOnlineOne = weightOnlineOne;
    }

    
    /**
     * 获取 重量下线(续重1).
     *
     * @return  the weightDownlineOne
     */
    public BigDecimal getWeightDownlineOne() {
        return weightDownlineOne;
    }

    
    /**
     * 设置 重量下线(续重1).
     *
     * @param weightDownlineOne the weightDownlineOne to set
     */
    public void setWeightDownlineOne(BigDecimal weightDownlineOne) {
        this.weightDownlineOne = weightDownlineOne;
    }

    
    /**
     * 获取 计价明细ID(续重2).
     *
     * @return  the twoDetailId
     */
    public String getTwoDetailId() {
        return twoDetailId;
    }

    
    /**
     * 设置 计价明细ID(续重2).
     *
     * @param twoDetailId the twoDetailId to set
     */
    public void setTwoDetailId(String twoDetailId) {
        this.twoDetailId = twoDetailId;
    }

    
    /**
     * 获取 费率(续重1).
     *
     * @return  the feeRateOne
     */
    public BigDecimal getFeeRateOne() {
        return feeRateOne;
    }

    
    /**
     * 设置 费率(续重1).
     *
     * @param feeRateOne the feeRateOne to set
     */
    public void setFeeRateOne(BigDecimal feeRateOne) {
        this.feeRateOne = feeRateOne;
    }

    
    /**
     * 获取 重量上线(续重2).
     *
     * @return  the weightOnlineTwo
     */
    public BigDecimal getWeightOnlineTwo() {
        return weightOnlineTwo;
    }

    
    /**
     * 设置 重量上线(续重2).
     *
     * @param weightOnlineTwo the weightOnlineTwo to set
     */
    public void setWeightOnlineTwo(BigDecimal weightOnlineTwo) {
        this.weightOnlineTwo = weightOnlineTwo;
    }

    
    /**
     * 获取 重量下线(续重2).
     *
     * @return  the weightDownlineTwo
     */
    public BigDecimal getWeightDownlineTwo() {
        return weightDownlineTwo;
    }

    
    /**
     * 设置 重量下线(续重2).
     *
     * @param weightDownlineTwo the weightDownlineTwo to set
     */
    public void setWeightDownlineTwo(BigDecimal weightDownlineTwo) {
        this.weightDownlineTwo = weightDownlineTwo;
    }

    
    /**
     * 获取 费率(续重2).
     *
     * @return  the feeRateTwo
     */
    public BigDecimal getFeeRateTwo() {
        return feeRateTwo;
    }

    
    /**
     * 设置 费率(续重2).
     *
     * @param feeRateTwo the feeRateTwo to set
     */
    public void setFeeRateTwo(BigDecimal feeRateTwo) {
        this.feeRateTwo = feeRateTwo;
    }

    
    /**
     * 获取 数据状态.
     *
     * @return  the active
     */
    public String getActive() {
        return active;
    }

    
    /**
     * 设置 数据状态.
     *
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }

    
    /**
     * 获取 备注.
     *
     * @return  the remark
     */
    public String getRemark() {
        return remark;
    }

    
    /**
     * 设置 备注.
     *
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
}