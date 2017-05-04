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
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PopPublicPriceDto;

/**
 * 
 * (公布价对象)
 * @author 岳洪杰
 * @date 2012-10-13 下午2:15:09
 * @since
 * @version
 */

/**
 * @author 219413
 *
 */
public class PublishPriceEntity extends BaseEntity {
    
    /**
     * 
     */
    private static final long serialVersionUID = 2270905008796184963L;
    /**
     * 目的区域ID
     */
    private String arrvRegionId;
    /**
     * 目的区域CODE
     */
    private String arrvRegionCode;
    /**
     * 目的站
     */
    private String arrvRegionName;
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
     * 到达时间
     */
    private String arriveTime;
    /**
     * 长短途 
     */
    private String longOrShort;
    /**
     * 最低一票
     */
    private BigDecimal minFee;
    /**
     * 重货价格
     */
    private BigDecimal heavyPrice;
    /**
     * 轻货价格
     */
    private BigDecimal lightPrice;
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
     * 航班班次
     */
    private String flightShiftNo;
    
    /**
     * 是否接货
     */
    private String centralizePickup;
	
    /**
     * 送货起步价
     */
    private BigDecimal deliveryCharges; 
    
    /**
     * 合票类型名称
     */
    private String combBillTypeName;  //zxy 20140428 MANA-1253 新增
    
    /**
     * 合票类型Code
     */
    private String combBillTypeCode;  //zxy 20140428 MANA-1253 新增
    
    /**
     * 轻货费率（小数）或者单价（分）
     */
     
    private String lightFeeRateStr;// 新增
    
    /**
     * 重货费率（小数）或者单价（分）
     */
    private String heavyFeeRateStr;// 新增
    
    /**
     * 计价分段明细list
     * @author 219413-Luomengxiang  
     */
    private List<PopPublicPriceDto> popPublicPriceDtoList;
    
    /**
   	 * 公布价是否显示偏线价格方案 ，此属性是为了在查询偏线价格方案是，只显示汽运和偏线的总价，而不显示单独的偏线价格方案。
        * @author 335673
   	 */
   	private Boolean isPxFlag;
       
   	public Boolean getIsPxFlag() {
   		return isPxFlag;
   	}
   	public void setIsPxFlag(Boolean isPxFlag) {
   		this.isPxFlag = isPxFlag;
   	}
	/**
     * 获得计价分段明细list
     * @return popPublicPriceDtoList
     */ 
    public List<PopPublicPriceDto> getPopPublicPriceDtoList() {
		return popPublicPriceDtoList;
	}
    /**
     * 设置计价分段明细list
     * @param  popPublicPriceDtoList
     */ 
	public void setPopPublicPriceDtoList(
			List<PopPublicPriceDto> popPublicPriceDtoList) {
		this.popPublicPriceDtoList = popPublicPriceDtoList;
	}

	public String getLightFeeRateStr() {
		return lightFeeRateStr;
	}

	public void setLightFeeRateStr(String lightFeeRateStr) {
		this.lightFeeRateStr = lightFeeRateStr;
	}

	public String getHeavyFeeRateStr() {
		return heavyFeeRateStr;
	}

	public void setHeavyFeeRateStr(String heavyFeeRateStr) {
		this.heavyFeeRateStr = heavyFeeRateStr;
	}

	public BigDecimal getDeliveryCharges() {
		return deliveryCharges;
	}

	public void setDeliveryCharges(BigDecimal deliveryCharges) {
		this.deliveryCharges = deliveryCharges;
	}

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
	 * 获取 长短途.
	 *
	 * @return the 长短途
	 */
	public String getLongOrShort() {
		return longOrShort;
	}
	
	/**
	 * 设置 长短途.
	 *
	 * @param longOrShort the new 长短途
	 */
	public void setLongOrShort(String longOrShort) {
		this.longOrShort = longOrShort;
	}
	
	/**
	 * 获取 最低一票.
	 *
	 * @return the 最低一票
	 */
	public BigDecimal getMinFee() {
		return minFee;
	}
	
	/**
	 * 设置 最低一票.
	 *
	 * @param minFee the new 最低一票
	 */
	public void setMinFee(BigDecimal minFee) {
		this.minFee = minFee;
	}
	
	/**
	 * 获取 重货价格.
	 *
	 * @return the 重货价格
	 */
	public BigDecimal getHeavyPrice() {
		return heavyPrice;
	}
	
	/**
	 * 设置 重货价格.
	 *
	 * @param heavyPrice the new 重货价格
	 */
	public void setHeavyPrice(BigDecimal heavyPrice) {
		this.heavyPrice = heavyPrice;
	}
	
	/**
	 * 获取 轻货价格.
	 *
	 * @return the 轻货价格
	 */
	public BigDecimal getLightPrice() {
		return lightPrice;
	}
	
	/**
	 * 设置 轻货价格.
	 *
	 * @param lightPrice the new 轻货价格
	 */
	public void setLightPrice(BigDecimal lightPrice) {
		this.lightPrice = lightPrice;
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

	public String getFlightShiftNo() {
		return flightShiftNo;
	}

	public void setFlightShiftNo(String flightShiftNo) {
		this.flightShiftNo = flightShiftNo;
	}

	public String getCentralizePickup() {
		return centralizePickup;
	}

	public void setCentralizePickup(String centralizePickup) {
		this.centralizePickup = centralizePickup;
	}

	public String getCombBillTypeName() {
		return combBillTypeName;
	}

	public void setCombBillTypeName(String combBillTypeName) {
		this.combBillTypeName = combBillTypeName;
	}

	public String getCombBillTypeCode() {
		return combBillTypeCode;
	}

	public void setCombBillTypeCode(String combBillTypeCode) {
		this.combBillTypeCode = combBillTypeCode;
	}
    
}