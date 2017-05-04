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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/ProductPriceDto.java
 * 
 * FILE NAME        	: ProductPriceDto.java
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
import java.util.List;


/**
 *  运价dto
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:zhangdongping,date:2012-10-25 下午5:43:34</p>
 * @author zhangdongping
 * @date 2012-10-25 下午5:43:34
 * @since
 * @version
 */
public class ProductPriceDto implements Serializable{
    
    
    /**
     * 
     */
    private static final long serialVersionUID = -3241944185914344502L;

    /**
     *PriceCriteriaDetailEntity 的id
     */
    private String id;
    
    
    /**
     * 始发区域ID
     */
    private String startRegionId;
    
    /**
     * 到达区域ID
     */
    private String arrvRegionId;

    /**
     *  产品CODE 第二级的产品代码
     */
    private String productCode;
    
    /**
     * 产品name
     */
    private String productName; 
    
    
    /**
     * 费用类型代码
     */
    private String  priceEntityCode;
    
    /**
     *  费用类型名称
     */
    private String priceEntityName;
    
    
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
	 * 是否送货
	 */
	private String centralizeDelivery;



	/**
     * 单价/费率
     */
    private BigDecimal feeRate;

    
    /**
     * 轻货费率（小数）或者单价（分）
     */
    private BigDecimal lightFeeRate;    
	/**
     * 单价/费率
     */
    private String feeRateStr;

    /**
     * 重货费率（小数）或者单价（分）
     */
    private BigDecimal heavyFeeRate;
    
    
    /**
     * 接送货轻货费率（小数）或者单价（分）
     */
     
    private BigDecimal lightFeeRatePickUpYes;
    
    /**
     * 接送货重货费率（小数）或者单价（分）
     */
    private BigDecimal heavyFeeRatePickUpYes;
    
    /**
     * 非接送货轻货费率（小数）或者单价（分）
     */
    private BigDecimal lightFeeRatePickUpNo;
    
    /**
     * 非接送货重货费率（小数）或者单价（分）
     */
    private BigDecimal heavyFeeRatePickUpNo;
    /**
     * 接送货最低一票
     */
    private BigDecimal minFeePickUpYes;
    /**
     * 非接送货最低一票
     */
    private BigDecimal minFeePickUpNo;
    
    /**
     * 最终实际计算的费率
     */
    private BigDecimal actualFeeRate;


	/**
     * 最低费用
     */
    private BigDecimal minFee;
    
    
    /**
     * 最高费用
     */
    private BigDecimal maxFee;
    
     
    /**
     * 固定费用
     */
    private BigDecimal fee;
    
    /**
     * 计费类别:WEIGHT，按重量计费，单位为公斤，VOLUME 按体积计费，单位为立方；
     */
    private String caculateType;
    
    
    /**
     * 经过计算后的费用，例如使用传入的 重量* feeRate 得出后的计算值
     */
    private BigDecimal caculateFee;
    
    /**
     * 打折后的费用
     */
    private BigDecimal discountFee;
    
    
    /**
     * 价格计算表达式
     */
    private String caculateExpression;
    
    /**
     * 航班班次
     */
    private String flightShiftNo;
    
    /**
     * 长途或短途
     */
    private String longOrShort;
    
    /**
     * 体积重 空运中使用
     */
    private  BigDecimal volumeWeight;
    
    
    /**
     * 是否最低一票
     */
    private String isMinFee;

	 /**

     * 合票类型Code
     */
    private String combBillTypeCode;  //zxy 20140428 MANA-1253 新增
    
    /**
     * 合票类型名称
     */
    private String combBillTypeName;  //zxy 20140428 MANA-1253 新增
    
    /**
    
    /**
     * FOSS折扣前费率
     */
    private BigDecimal initFeeRate;    
    
    /**
     * 计价分段明细List
     * @author 219413-Luomengxiang  2014-11-25
     */
	private List<PopPublicPriceDto> popPublicPriceDtoList;
    
	/**
	 * 获得计价分段明细List
	 * @return
	 */
    public List<PopPublicPriceDto> getPopPublicPriceDtoList() {
		return popPublicPriceDtoList;
	}
    
    /**
	 * 设置计价分段明细List
	 * @return
	 */
	public void setPopPublicPriceDtoList(
			List<PopPublicPriceDto> popPublicPriceDtoList) {
		this.popPublicPriceDtoList = popPublicPriceDtoList;
	}

	public BigDecimal getInitFeeRate() {
		return initFeeRate;
	}

	public void setInitFeeRate(BigDecimal initFeeRate) {
		this.initFeeRate = initFeeRate;
	}
	 /**
     * 轻货费率（小数）或者单价（分）
     */
     
    private String lightFeeRateStr;//yangkang 20140708 新增
    
    /**
     * 重货费率（小数）或者单价（分）
     */
    private String heavyFeeRateStr;//yangkang 20140708 新增
    
    /**
     * 用于计算快递运费
     * 首重价格
     */
    private BigDecimal firstRateFee;
    /**
     * 续重区间1重量
     */
    private BigDecimal firstWeight;
    /**
     * 续重区间2重量
     */
    private BigDecimal secondWeight;
    /**
     * 续重区间1费率
     */
    private BigDecimal firstTempRate;
    /**
     * 续重区间2费率
     */
    private BigDecimal secondTempRate;

	/**
     * 是否最低一票
     */
    public String getIsMinFee() {
		return isMinFee;
	}


    /**
     * 是否最低一票
     */
	public void setIsMinFee(String isMinFee) {
		this.isMinFee = isMinFee;
	}

	/**
     * 纯运费
     */
    private BigDecimal purefreight;
	public BigDecimal getPurefreight() {
		return purefreight;
	}

	public void setPurefreight(BigDecimal purefreight) {
		this.purefreight = purefreight;
	}
	/**
     * 获取 体积重 空运中使用.
     *
     * @return the 体积重 空运中使用
     */
    public BigDecimal getVolumeWeight() {
        return volumeWeight;
    }

    
    /**
     * 设置 体积重 空运中使用.
     *
     * @param volumeWeight the new 体积重 空运中使用
     */
    public void setVolumeWeight(BigDecimal volumeWeight) {
        this.volumeWeight = volumeWeight;
    }


    /**
     * 采用的折扣方案
     */
    private List<PriceDiscountDto> discountPrograms;
    
    /**
     * 计算偏线费率实体信息 - 改对象只针对当前计算汽运偏线的产品
     */
    private ResultOuterPriceCaccilateDto  resultOuterPriceCaccilateDto;
    /**
     * 偏线费  352676
     */
    private BigDecimal partialTransportFee;
    
    /**
     * 计算偏线费率实体信息 - 改对象只针对当前计算汽运偏线的产品
     */
	public ResultOuterPriceCaccilateDto getResultOuterPriceCaccilateDto() {
		return resultOuterPriceCaccilateDto;
	}

	  /**
     * 计算偏线费率实体信息 - 改对象只针对当前计算汽运偏线的产品
     */
	public void setResultOuterPriceCaccilateDto(
			ResultOuterPriceCaccilateDto resultOuterPriceCaccilateDto) {
		this.resultOuterPriceCaccilateDto = resultOuterPriceCaccilateDto;
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
	 * 获取 产品CODE 第二级的产品代码.
	 *
	 * @return the 产品CODE 第二级的产品代码
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * 设置 产品CODE 第二级的产品代码.
	 *
	 * @param productCode the new 产品CODE 第二级的产品代码
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
	 * 获取 费用类型代码.
	 *
	 * @return the 费用类型代码
	 */
	public String getPriceEntityCode() {
		return priceEntityCode;
	}

	/**
	 * 设置 费用类型代码.
	 *
	 * @param priceEntityCode the new 费用类型代码
	 */
	public void setPriceEntityCode(String priceEntityCode) {
		this.priceEntityCode = priceEntityCode;
	}

	/**
	 * 获取 费用类型名称.
	 *
	 * @return the 费用类型名称
	 */
	public String getPriceEntityName() {
		return priceEntityName;
	}

	/**
	 * 设置 费用类型名称.
	 *
	 * @param priceEntityName the new 费用类型名称
	 */
	public void setPriceEntityName(String priceEntityName) {
		this.priceEntityName = priceEntityName;
	}

	/**
	 * 获取 货物类型CODE.
	 *
	 * @return the 货物类型CODE
	 */
	public String getGoodsTypeCode() {
		return goodsTypeCode;
	}

	/**
	 * 设置 货物类型CODE.
	 *
	 * @param goodsTypeCode the new 货物类型CODE
	 */
	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
	}

	/**
	 * 获取 货物类型name.
	 *
	 * @return the 货物类型name
	 */
	public String getGoodsTypeName() {
		return goodsTypeName;
	}

	/**
	 * 设置 货物类型name.
	 *
	 * @param goodsTypeName the new 货物类型name
	 */
	public void setGoodsTypeName(String goodsTypeName) {
		this.goodsTypeName = goodsTypeName;
	}

	/**
	 * 获取 是否接货.
	 *
	 * @return the 是否接货
	 */
	public String getCentralizePickup() {
		return centralizePickup;
	}

	/**
	 * 设置 是否接货.
	 *
	 * @param centralizePickup the new 是否接货
	 */
	public void setCentralizePickup(String centralizePickup) {
		this.centralizePickup = centralizePickup;
	}

	/**
	 * 获取 是否送货.
	 *
	 * @return  是否送货
	 */
	public String getCentralizeDelivery() {
		return centralizeDelivery;
	}


	/**
	 * 设置 是否送货
	 *
	 * @param centralizeDelivery  是否送货
	 */
	public void setCentralizeDelivery(String centralizeDelivery) {
		this.centralizeDelivery = centralizeDelivery;
	}

	/**
	 * 获取 单价/费率.
	 *
	 * @return the 单价/费率
	 */
	public BigDecimal getFeeRate() {
		return feeRate;
	}

	/**
	 * 设置 单价/费率.
	 *
	 * @param feeRate the new 单价/费率
	 */
	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}

	/**
	 * 获取 轻货费率（小数）或者单价（分）.
	 *
	 * @return the 轻货费率（小数）或者单价（分）
	 */
	public BigDecimal getLightFeeRate() {
		return lightFeeRate;
	}

	/**
	 * 设置 轻货费率（小数）或者单价（分）.
	 *
	 * @param lightFeeRate the new 轻货费率（小数）或者单价（分）
	 */
	public void setLightFeeRate(BigDecimal lightFeeRate) {
		this.lightFeeRate = lightFeeRate;
	}

	/**
	 * 获取 重货费率（小数）或者单价（分）.
	 *
	 * @return the 重货费率（小数）或者单价（分）
	 */
	public BigDecimal getHeavyFeeRate() {
		return heavyFeeRate;
	}

	/**
	 * 设置 重货费率（小数）或者单价（分）.
	 *
	 * @param heavyFeeRate the new 重货费率（小数）或者单价（分）
	 */
	public void setHeavyFeeRate(BigDecimal heavyFeeRate) {
		this.heavyFeeRate = heavyFeeRate;
	}

	/**
	 * 获取 最终实际计算的费率.
	 *
	 * @return the 最终实际计算的费率
	 */
	public BigDecimal getActualFeeRate() {
		return actualFeeRate;
	}

	/**
	 * 设置 最终实际计算的费率.
	 *
	 * @param actualFeeRate the new 最终实际计算的费率
	 */
	public void setActualFeeRate(BigDecimal actualFeeRate) {
		this.actualFeeRate = actualFeeRate;
	}

	/**
	 * 获取 最低费用.
	 *
	 * @return the 最低费用
	 */
	public BigDecimal getMinFee() {
		return minFee;
	}

	/**
	 * 设置 最低费用.
	 *
	 * @param minFee the new 最低费用
	 */
	public void setMinFee(BigDecimal minFee) {
		this.minFee = minFee;
	}

	/**
	 * 获取 最高费用.
	 *
	 * @return the 最高费用
	 */
	public BigDecimal getMaxFee() {
		return maxFee;
	}

	/**
	 * 设置 最高费用.
	 *
	 * @param maxFee the new 最高费用
	 */
	public void setMaxFee(BigDecimal maxFee) {
		this.maxFee = maxFee;
	}

	/**
	 * 获取 固定费用.
	 *
	 * @return the 固定费用
	 */
	public BigDecimal getFee() {
		return fee;
	}

	/**
	 * 设置 固定费用.
	 *
	 * @param fee the new 固定费用
	 */
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	/**
	 * 获取 计费类别:WEIGHT，按重量计费，单位为公斤，VOLUME 按体积计费，单位为立方；.
	 *
	 * @return the 计费类别:WEIGHT，按重量计费，单位为公斤，VOLUME 按体积计费，单位为立方；
	 */
	public String getCaculateType() {
		return caculateType;
	}

	/**
	 * 设置 计费类别:WEIGHT，按重量计费，单位为公斤，VOLUME 按体积计费，单位为立方；.
	 *
	 * @param caculateType the new 计费类别:WEIGHT，按重量计费，单位为公斤，VOLUME 按体积计费，单位为立方；
	 */
	public void setCaculateType(String caculateType) {
		this.caculateType = caculateType;
	}

	/**
	 * 获取 经过计算后的费用，例如使用传入的 重量* feeRate 得出后的计算值.
	 *
	 * @return the 经过计算后的费用，例如使用传入的 重量* feeRate 得出后的计算值
	 */
	public BigDecimal getCaculateFee() {
		return caculateFee;
	}

	/**
	 * 设置 经过计算后的费用，例如使用传入的 重量* feeRate 得出后的计算值.
	 *
	 * @param caculateFee the new 经过计算后的费用，例如使用传入的 重量* feeRate 得出后的计算值
	 */
	public void setCaculateFee(BigDecimal caculateFee) {
		this.caculateFee = caculateFee;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public BigDecimal getDiscountFee() {
		return discountFee;
	}

	/**
	 * 
	 *
	 * @param discountFee 
	 */
	public void setDiscountFee(BigDecimal discountFee) {
	      this.discountFee = discountFee;
	}

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
	 * 获取 航班号.
	 *
	 * @return the 航班号
	 */
	public String getFlightShiftNo() {
		return flightShiftNo;
	}

	/**
	 * 设置 航班号.
	 *
	 * @param flightShiftNo the new 航班号
	 */
	public void setFlightShiftNo(String flightShiftNo) {
		this.flightShiftNo = flightShiftNo;
	}

	/**
	 * 获取 长途或短途.
	 *
	 * @return the 长途或短途
	 */
	public String getLongOrShort() {
		return longOrShort;
	}

	/**
	 * 设置 长途或短途.
	 *
	 * @param longOrShort the new 长途或短途
	 */
	public void setLongOrShort(String longOrShort) {
		this.longOrShort = longOrShort;
	}

	/**
	 * 获取 采用的折扣方案.
	 *
	 * @return the 采用的折扣方案
	 */
	public List<PriceDiscountDto> getDiscountPrograms() {
		return discountPrograms;
	}

	/**
	 * 设置 采用的折扣方案.
	 *
	 * @param discountPrograms the new 采用的折扣方案
	 */
	public void setDiscountPrograms(List<PriceDiscountDto> discountPrograms) {
		this.discountPrograms = discountPrograms;
	}
    
	/**
	 * 获取 接送货轻货费率（小数）或者单价（分）.
	 *
	 * @return the 接送货轻货费率（小数）或者单价（分）
	 */
	public BigDecimal getLightFeeRatePickUpYes() {
		return lightFeeRatePickUpYes;
	}


	/**
	 * 设置 接送货轻货费率（小数）或者单价（分）.
	 *
	 * @param lightFeeRatePickUpYes the new 接送货轻货费率（小数）或者单价（分）
	 */
	public void setLightFeeRatePickUpYes(BigDecimal lightFeeRatePickUpYes) {
		this.lightFeeRatePickUpYes = lightFeeRatePickUpYes;
	}


	/**
	 * 获取 接送货重货费率（小数）或者单价（分）.
	 *
	 * @return the 接送货重货费率（小数）或者单价（分）
	 */
	public BigDecimal getHeavyFeeRatePickUpYes() {
		return heavyFeeRatePickUpYes;
	}


	/**
	 * 设置 接送货重货费率（小数）或者单价（分）.
	 *
	 * @param heavyFeeRatePickUpYes the new 接送货重货费率（小数）或者单价（分）
	 */
	public void setHeavyFeeRatePickUpYes(BigDecimal heavyFeeRatePickUpYes) {
		this.heavyFeeRatePickUpYes = heavyFeeRatePickUpYes;
	}


	/**
	 * 获取 非接送货轻货费率（小数）或者单价（分）.
	 *
	 * @return the 非接送货轻货费率（小数）或者单价（分）
	 */
	public BigDecimal getLightFeeRatePickUpNo() {
		return lightFeeRatePickUpNo;
	}


	/**
	 * 设置 非接送货轻货费率（小数）或者单价（分）.
	 *
	 * @param lightFeeRatePickUpNo the new 非接送货轻货费率（小数）或者单价（分）
	 */
	public void setLightFeeRatePickUpNo(BigDecimal lightFeeRatePickUpNo) {
		this.lightFeeRatePickUpNo = lightFeeRatePickUpNo;
	}


	/**
	 * 获取 非接送货重货费率（小数）或者单价（分）.
	 *
	 * @return the 非接送货重货费率（小数）或者单价（分）
	 */
	public BigDecimal getHeavyFeeRatePickUpNo() {
		return heavyFeeRatePickUpNo;
	}


	/**
	 * 设置 非接送货重货费率（小数）或者单价（分）.
	 *
	 * @param heavyFeeRatePickUpNo the new 非接送货重货费率（小数）或者单价（分）
	 */
	public void setHeavyFeeRatePickUpNo(BigDecimal heavyFeeRatePickUpNo) {
		this.heavyFeeRatePickUpNo = heavyFeeRatePickUpNo;
	}


	/**
	 * 获取 接送货最低一票.
	 *
	 * @return the 接送货最低一票
	 */
	public BigDecimal getMinFeePickUpYes() {
		return minFeePickUpYes;
	}


	/**
	 * 设置 接送货最低一票.
	 *
	 * @param minFeePickUpYes the new 接送货最低一票
	 */
	public void setMinFeePickUpYes(BigDecimal minFeePickUpYes) {
		this.minFeePickUpYes = minFeePickUpYes;
	}


	/**
	 * 获取 非接送货最低一票.
	 *
	 * @return the 非接送货最低一票
	 */
	public BigDecimal getMinFeePickUpNo() {
		return minFeePickUpNo;
	}


	/**
	 * 设置 非接送货最低一票.
	 *
	 * @param minFeePickUpNo the new 非接送货最低一票
	 */
	public void setMinFeePickUpNo(BigDecimal minFeePickUpNo) {
		this.minFeePickUpNo = minFeePickUpNo;
	}


	/**
	 * 获取 始发区域ID.
	 *
	 * @return the 始发区域ID
	 */
	public String getStartRegionId() {
		return startRegionId;
	}


	/**
	 * 设置 始发区域ID.
	 *
	 * @param startRegionId the new 始发区域ID
	 */
	public void setStartRegionId(String startRegionId) {
		this.startRegionId = startRegionId;
	}


	/**
	 * 获取 到达区域ID.
	 *
	 * @return the 到达区域ID
	 */
	public String getArrvRegionId() {
		return arrvRegionId;
	}


	/**
	 * 设置 到达区域ID.
	 *
	 * @param arrvRegionId the new 到达区域ID
	 */
	public void setArrvRegionId(String arrvRegionId) {
		this.arrvRegionId = arrvRegionId;
	}
	public String getCombBillTypeCode() {
		return combBillTypeCode;
	}


	public void setCombBillTypeCode(String combBillTypeCode) {
		this.combBillTypeCode = combBillTypeCode;
	}


	public String getCombBillTypeName() {
		return combBillTypeName;
	}


	public void setCombBillTypeName(String combBillTypeName) {
		this.combBillTypeName = combBillTypeName;
	}
	
	public String getFeeRateStr() {
		return feeRateStr;
	}


	public void setFeeRateStr(String feeRateStr) {
		this.feeRateStr = feeRateStr;
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

	public BigDecimal getFirstRateFee() {
		return firstRateFee;
	}

	public void setFirstRateFee(BigDecimal firstRateFee) {
		this.firstRateFee = firstRateFee;
	}

	public BigDecimal getFirstWeight() {
		return firstWeight;
	}

	public void setFirstWeight(BigDecimal firstWeight) {
		this.firstWeight = firstWeight;
	}

	public BigDecimal getSecondWeight() {
		return secondWeight;
	}

	public void setSecondWeight(BigDecimal secondWeight) {
		this.secondWeight = secondWeight;
	}

	public BigDecimal getFirstTempRate() {
		return firstTempRate;
	}

	public void setFirstTempRate(BigDecimal firstTempRate) {
		this.firstTempRate = firstTempRate;
	}

	public BigDecimal getSecondTempRate() {
		return secondTempRate;
	}

	public void setSecondTempRate(BigDecimal secondTempRate) {
		this.secondTempRate = secondTempRate;
	}
	
	 /**
     * 用于计算签收返单费用
     * 快递首重价格
     * */
    private BigDecimal standardExpFirstFee;
	
	/**
	 * 快递价格折扣方案
	 */
	private ExpressDiscountDto expressDiscountDto ;
	
	/**
	 * 标准 公布价运费（打折前）
	 */
	private BigDecimal standardTransportFee ;

	public BigDecimal getStandardExpFirstFee() {
		return standardExpFirstFee;
	}

	public void setStandardExpFirstFee(BigDecimal standardExpFirstFee) {
		this.standardExpFirstFee = standardExpFirstFee;
	}

	public ExpressDiscountDto getExpressDiscountDto() {
		return expressDiscountDto;
	}

	public void setExpressDiscountDto(ExpressDiscountDto expressDiscountDto) {
		this.expressDiscountDto = expressDiscountDto;
	}

	public BigDecimal getStandardTransportFee() {
		return standardTransportFee;
	}

	public void setStandardTransportFee(BigDecimal standardTransportFee) {
		this.standardTransportFee = standardTransportFee;
	}

	public BigDecimal getPartialTransportFee() {
		return partialTransportFee;
	}

	public void setPartialTransportFee(BigDecimal partialTransportFee) {
		this.partialTransportFee = partialTransportFee;
	}


	
	
}