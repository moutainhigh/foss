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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/domain/PublishPriceEntity.java
 * 
 * FILE NAME        	: PublishPriceEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * (公布价对象)
 * @author 岳洪杰
 * @date 2012-10-13 下午2:15:09
 * @since
 * @version
 */

/**
 * @author wangfei
 *
 */
/**
 * @author wangfei
 *
 */
public class PublishPriceExpressEntity extends BaseEntity {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 5159255590827556282L;
	
	/**
	 * 长短途
	 */
	private String longOrShort;
	
    /**
     *  派送承诺需加天数
     */
	private Integer addDay;
	
	/**
     * 承诺最长时间
     */
    private Integer maxTime;

    /**
     * 承诺最长时间单位
     */
    private String maxTimeUnit;

    /**
     * 承诺最短时间
     */
    private Integer minTime;

    /**
     *  承诺最短时间单位
     */
    private String minTimeUnit;
	
    /**
     *  产品CODE 第3级的产品代码
     */
    private String productCode;
    /**
     * 产品name
     */
    private String productName; 
	/**
	 *  派送承诺时间
	 */
    private String deliveryTime;
	/**
	 *  始发区域
	 */
	private String deptRegionName;
	/**
	 * 始发区域code
	 */
	private String deptRegionCode;
	/**
	 * 始发区域id
	 */
	private String deptRegionId;
		
    /** 价格始发区域Id. */
    private String deptPriceRegionId;
    
    /** 价格始发区域code. */
    private String deptPriceRegionCode;
    
    /** 价格始发区域Name. */
    private String deptPriceRegionName;	
	/**
     * 目的区域ID
     */
    private String arrvRegionId;
    /**
     * 目的区域CODE
     */
    private String arrvRegionCode;
    /**
     * 目的站,目的区域
     */
    private String arrvRegionName;
    
    /** 价格到达区域id. */
    private String arrvPriceRegionId;
    
    /** 价格到达区域code. */
    private String arrvPriceRegionCode;
    
    /** 价格到达区域Name. */
    private String arrvPriceRegionName;
    /**
     * 条目编号
     */
    private String productItemCode;
    /**
     * 条目名称
     */
    private String productItemName;
    /**
     * 最小营运时效
     */
    private String minEffectiveValue;
    /**
     * 最大营运时效
     */
    private String maxEffectiveValue;
    /**
	 * 营运时效单位
	 */
	private String effectiveUnit;
    /**
     * 取货时间
     */
    private String pickupTime;
    /**
     * 到达时间,营运时效
     */
    private String arriveTime;

    /**
     * 出发部门
     */
    private String startDept;
    /**
     * 出发部门ID
     */
    private String startDeptId;
    /**
     * 出发部门CODE
     */
    private String startDeptCode;
    /**
     * 目的城市
     */
    private String destinationCity;

    /**
     * 货物类型CODE
     */
    private String goodsTypeCode; 
    
    /**
     * 货物类型name
     */
    private String goodsTypeName; 
    
    /**
     * 是否接货
     */
    private String centralizePickup;
    
	/**
	 * 首重
	 */
	private BigDecimal firstWeight ;
	
	/**
	 * 首重区间-下限
	 */
	private BigDecimal weightLowLimit ;
	
	/**
	 * 首重区间-上限
	 */
	private BigDecimal weightHighLimit ;
	
	/**
	 * 重量下线
	 */
	private BigDecimal weightOffline1 ;
	
	/**
	 * 重量上线
	 */
	private BigDecimal weightOnline1 ;
	
    /**
     * 费率1
     */
    private BigDecimal feeRate1;
    
	/**
	 * 重量下线
	 */
	private BigDecimal weightOffline2 ;
	
	/**
	 * 重量上线
	 */
	private BigDecimal weightOnline2 ;
	
    /**
     * 费率2
     */
    private BigDecimal feeRate2;
    /** 区域类型. */
    private String regionNature;
	/**
	 * 获取 目的区域ID.
	 *
	 * @return the 目的区域ID
	 */
	public String getArrvRegionId() {
		return arrvRegionId;
	}
	
	/**
	 * 设置 目的区域ID.
	 *
	 * @param arrvRegionId the new 目的区域ID
	 */
	public void setArrvRegionId(String arrvRegionId) {
		this.arrvRegionId = arrvRegionId;
	}
	
	/**
	 * 获取 目的区域CODE.
	 *
	 * @return the 目的区域CODE
	 */
	public String getArrvRegionCode() {
		return arrvRegionCode;
	}
	
	/**
	 * 设置 目的区域CODE.
	 *
	 * @param arrvRegionCode the new 目的区域CODE
	 */
	public void setArrvRegionCode(String arrvRegionCode) {
		this.arrvRegionCode = arrvRegionCode;
	}
	/**
	 * 获取 目的站.
	 *
	 * @return the 目的站
	 */
	public String getArrvRegionName() {
		return arrvRegionName;
	}

	/**
	 * 设置 目的站.
	 *
	 * @param arrvRegionName the new 目的站
	 */
	public void setArrvRegionName(String arrvRegionName) {
		this.arrvRegionName = arrvRegionName;
	}
	
	/**
	 * 获取 条目编号.
	 *
	 * @return the 条目编号
	 */
	public String getProductItemCode() {
		return productItemCode;
	}
	
	/**
	 * 设置 条目编号.
	 *
	 * @param productItemCode the new 条目编号
	 */
	public void setProductItemCode(String productItemCode) {
		this.productItemCode = productItemCode;
	}
	
	/**
	 * 获取 条目名称.
	 *
	 * @return the 条目名称
	 */
	public String getProductItemName() {
		return productItemName;
	}
	
	/**
	 * 设置 条目名称.
	 *
	 * @param productItemName the new 条目名称
	 */
	public void setProductItemName(String productItemName) {
		this.productItemName = productItemName;
	}
	
	/**
	 * 获取 最小营运时效.
	 *
	 * @return the 最小营运时效
	 */
	public String getMinEffectiveValue() {
		return minEffectiveValue;
	}
	
	/**
	 * 设置 最小营运时效.
	 *
	 * @param minEffectiveValue the new 最小营运时效
	 */
	public void setMinEffectiveValue(String minEffectiveValue) {
		this.minEffectiveValue = minEffectiveValue;
	}
	
	/**
	 * 获取 最大营运时效.
	 *
	 * @return the 最大营运时效
	 */
	public String getMaxEffectiveValue() {
		return maxEffectiveValue;
	}
	
	/**
	 * 设置 最大营运时效.
	 *
	 * @param maxEffectiveValue the new 最大营运时效
	 */
	public void setMaxEffectiveValue(String maxEffectiveValue) {
		this.maxEffectiveValue = maxEffectiveValue;
	}
	
	/**
	 * 获取 营运时效单位.
	 *
	 * @return the 营运时效单位
	 */
	public String getEffectiveUnit() {
		return effectiveUnit;
	}
	
	/**
	 * 设置 营运时效单位.
	 *
	 * @param effectiveUnit the new 营运时效单位
	 */
	public void setEffectiveUnit(String effectiveUnit) {
		this.effectiveUnit = effectiveUnit;
	}
	
	/**
	 * 获取 取货时间.
	 *
	 * @return the 取货时间
	 */
	public String getPickupTime() {
		return pickupTime;
	}
	
	/**
	 * 设置 取货时间.
	 *
	 * @param pickupTime the new 取货时间
	 */
	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}
	
	/**
	 * 获取 到达时间.
	 *
	 * @return the 到达时间
	 */
	public String getArriveTime() {
		return arriveTime;
	}
	
	/**
	 * 设置 到达时间.
	 *
	 * @param arriveTime the new 到达时间
	 */
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	
	/**
	 * 获取 出发部门.
	 *
	 * @return the 出发部门
	 */
	public String getStartDept() {
		return startDept;
	}
	
	/**
	 * 设置 出发部门.
	 *
	 * @param startDept the new 出发部门
	 */
	public void setStartDept(String startDept) {
		this.startDept = startDept;
	}
	
	/**
	 * 获取 出发部门ID.
	 *
	 * @return the 出发部门ID
	 */
	public String getStartDeptId() {
		return startDeptId;
	}
	
	/**
	 * 设置 出发部门ID.
	 *
	 * @param startDeptId the new 出发部门ID
	 */
	public void setStartDeptId(String startDeptId) {
		this.startDeptId = startDeptId;
	}
	
	/**
	 * 获取 出发部门CODE.
	 *
	 * @return the 出发部门CODE
	 */
	public String getStartDeptCode() {
		return startDeptCode;
	}
	
	/**
	 * 设置 出发部门CODE.
	 *
	 * @param startDeptCode the new 出发部门CODE
	 */
	public void setStartDeptCode(String startDeptCode) {
		this.startDeptCode = startDeptCode;
	}
	
	/**
	 * 获取 目的城市.
	 *
	 * @return the 目的城市
	 */
	public String getDestinationCity() {
		return destinationCity;
	}
	
	/**
	 * 设置 目的城市.
	 *
	 * @param destinationCity the new 目的城市
	 */
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public String getGoodsTypeCode() {
		return goodsTypeCode;
	}

	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
	}

	public String getGoodsTypeName() {
		return goodsTypeName;
	}

	public void setGoodsTypeName(String goodsTypeName) {
		this.goodsTypeName = goodsTypeName;
	}

	public String getCentralizePickup() {
		return centralizePickup;
	}

	public void setCentralizePickup(String centralizePickup) {
		this.centralizePickup = centralizePickup;
	}

	public BigDecimal getFirstWeight() {
		return firstWeight;
	}

	public void setFirstWeight(BigDecimal firstWeight) {
		this.firstWeight = firstWeight;
	}

	public BigDecimal getWeightOnline1() {
		return weightOnline1;
	}

	public void setWeightOnline1(BigDecimal weightOnline1) {
		this.weightOnline1 = weightOnline1;
	}

	public BigDecimal getWeightOffline1() {
		return weightOffline1;
	}

	public void setWeightOffline1(BigDecimal weightOffline1) {
		this.weightOffline1 = weightOffline1;
	}

	public BigDecimal getFeeRate1() {
		return feeRate1;
	}

	public void setFeeRate1(BigDecimal feeRate1) {
		this.feeRate1 = feeRate1;
	}

	public BigDecimal getWeightOnline2() {
		return weightOnline2;
	}

	public void setWeightOnline2(BigDecimal weightOnline2) {
		this.weightOnline2 = weightOnline2;
	}

	public BigDecimal getWeightOffline2() {
		return weightOffline2;
	}

	public void setWeightOffline2(BigDecimal weightOffline2) {
		this.weightOffline2 = weightOffline2;
	}

	public BigDecimal getFeeRate2() {
		return feeRate2;
	}

	public void setFeeRate2(BigDecimal feeRate2) {
		this.feeRate2 = feeRate2;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getDeptRegionName() {
		return deptRegionName;
	}

	public void setDeptRegionName(String deptRegionName) {
		this.deptRegionName = deptRegionName;
	}

	public String getDeptRegionCode() {
		return deptRegionCode;
	}

	public void setDeptRegionCode(String deptRegionCode) {
		this.deptRegionCode = deptRegionCode;
	}

	public String getDeptRegionId() {
		return deptRegionId;
	}

	public void setDeptRegionId(String deptRegionId) {
		this.deptRegionId = deptRegionId;
	}

	public BigDecimal getWeightLowLimit() {
		return weightLowLimit;
	}

	public void setWeightLowLimit(BigDecimal weightLowLimit) {
		this.weightLowLimit = weightLowLimit;
	}

	public BigDecimal getWeightHighLimit() {
		return weightHighLimit;
	}

	public void setWeightHighLimit(BigDecimal weightHighLimit) {
		this.weightHighLimit = weightHighLimit;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getLongOrShort() {
		return longOrShort;
	}

	public void setLongOrShort(String longOrShort) {
		this.longOrShort = longOrShort;
	}

	public Integer getAddDay() {
		return addDay;
	}

	public void setAddDay(Integer addDay) {
		this.addDay = addDay;
	}

	public Integer getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(Integer maxTime) {
		this.maxTime = maxTime;
	}

	public String getMaxTimeUnit() {
		return maxTimeUnit;
	}

	public void setMaxTimeUnit(String maxTimeUnit) {
		this.maxTimeUnit = maxTimeUnit;
	}

	public Integer getMinTime() {
		return minTime;
	}

	public void setMinTime(Integer minTime) {
		this.minTime = minTime;
	}

	public String getMinTimeUnit() {
		return minTimeUnit;
	}

	public void setMinTimeUnit(String minTimeUnit) {
		this.minTimeUnit = minTimeUnit;
	}

	public String getDeptPriceRegionId() {
		return deptPriceRegionId;
	}

	public void setDeptPriceRegionId(String deptPriceRegionId) {
		this.deptPriceRegionId = deptPriceRegionId;
	}

	public String getDeptPriceRegionCode() {
		return deptPriceRegionCode;
	}

	public void setDeptPriceRegionCode(String deptPriceRegionCode) {
		this.deptPriceRegionCode = deptPriceRegionCode;
	}

	public String getDeptPriceRegionName() {
		return deptPriceRegionName;
	}

	public void setDeptPriceRegionName(String deptPriceRegionName) {
		this.deptPriceRegionName = deptPriceRegionName;
	}

	public String getArrvPriceRegionId() {
		return arrvPriceRegionId;
	}

	public void setArrvPriceRegionId(String arrvPriceRegionId) {
		this.arrvPriceRegionId = arrvPriceRegionId;
	}

	public String getArrvPriceRegionCode() {
		return arrvPriceRegionCode;
	}

	public void setArrvPriceRegionCode(String arrvPriceRegionCode) {
		this.arrvPriceRegionCode = arrvPriceRegionCode;
	}

	public String getArrvPriceRegionName() {
		return arrvPriceRegionName;
	}

	public void setArrvPriceRegionName(String arrvPriceRegionName) {
		this.arrvPriceRegionName = arrvPriceRegionName;
	}

	public String getRegionNature() {
		return regionNature;
	}

	public void setRegionNature(String regionNature) {
		this.regionNature = regionNature;
	}
	
	
}