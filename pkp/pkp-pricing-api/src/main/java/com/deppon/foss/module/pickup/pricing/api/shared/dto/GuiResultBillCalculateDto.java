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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/PdaResultBillCalculateDto.java
 * 
 * FILE NAME        	: PdaResultBillCalculateDto.java
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
 * 
 * @Description: GUI客户端产品价格计算结果的DTO
 * PdaQueryBillCalculateDto.java Create on 2013-1-14 上午10:11:47
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class GuiResultBillCalculateDto implements Serializable {
    
    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;
	
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
    private String  priceEntryCode;
    
    /**
     *  费用类型名称
     */
    private String priceEntryName;
    
    
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
     * 单价/费率
     */
    private BigDecimal feeRate;

    
    /**
     * 轻货费率（小数）或者单价（分）
     */
    private BigDecimal lightFeeRate;    

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
     * 用于计算签收返单费用
     * 快递首重价格
     */
    private BigDecimal standExpFirstFee;
    
	/**
	 * 快递价格折扣方案
	 */
	private ExpressDiscountDto expressDiscountDto ;




    // 增值服务  

      /**code
         服务子类型:只针对增值服务该栏位有用
               对于代收货款：为退款类型(即日退，三日退，审核退)
             对于签收回单：为返单类型（传真返单，原件返单）
            对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等）     
         其它增值服务：空
     */
    private String subType;
    
    /** name
	服务子类型:只针对增值服务该栏位有用
      	对于代收货款：为退款类型(即日退，三日退，审核退)
    	对于签收回单：为返单类型（传真返单，原件返单）
   	对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等）     
	其它增值服务：空
    */
    private String subTypeName;
    
    
    /**
     * 是否可以修改
     */
    private String canmodify;
    
    /**
     * 是否可以删除
     */
    private String candelete;
    
    
    /**
     * 其他费用归集类别代码
     */
    private String belongToPriceEntityCode;
    /**
     * 其他费用归集类别名称
     */
    private String belongToPriceEntityName;
    
    
     /**
    * 默认保费申明
    */
    private BigDecimal defaultBF;
    
    
    
    /**
     * 采用的折扣方案
     */
    private List<GuiResultDiscountDto> discountPrograms;
    
    
    /**
     * 偏线费率信息
     */
    private ResultOuterPriceCaccilateDto resultOuterPriceCaccilateDto;
    
    /**
     * 最低费率
     */
    private BigDecimal minFeeRate;
    
    /**
     * 最高费率
     */
    private BigDecimal maxFeeRate;
    /**
     * 伙伴开单
     */
    private String partnerBillingLogo;


    /**
     * 是否接货（数据库结果）
     */
    private String centralizePickupResult;

    /**
     * 是否送货（数据库结果）
     */
    private String centralizeDeliveryResult;

    /**
     * 偏线费  352676
     */
    private BigDecimal partialTransportFee;
    
    public String getCentralizePickupResult() {
        return centralizePickupResult;
    }

    public void setCentralizePickupResult(String centralizePickupResult) {
        this.centralizePickupResult = centralizePickupResult;
    }

    public String getCentralizeDeliveryResult() {
        return centralizeDeliveryResult;
    }

    public void setCentralizeDeliveryResult(String centralizeDeliveryResult) {
        this.centralizeDeliveryResult = centralizeDeliveryResult;
    }

    public String getPartnerBillingLogo() {
		return partnerBillingLogo;
	}




	public void setPartnerBillingLogo(String partnerBillingLogo) {
		this.partnerBillingLogo = partnerBillingLogo;
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
     * 获取 priceCriteriaDetailEntity 的id.
     *
     * @return the priceCriteriaDetailEntity 的id
     */
    public String getId() {
        return id;
    }



    
    public ResultOuterPriceCaccilateDto getResultOuterPriceCaccilateDto() {
		return resultOuterPriceCaccilateDto;
	}




	public void setResultOuterPriceCaccilateDto(
			ResultOuterPriceCaccilateDto resultOuterPriceCaccilateDto) {
		this.resultOuterPriceCaccilateDto = resultOuterPriceCaccilateDto;
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
    public String getPriceEntryCode() {
        return priceEntryCode;
    }



    
    /**
     * 设置 费用类型代码.
     *
     * @param priceEntryCode the new 费用类型代码
     */
    public void setPriceEntryCode(String priceEntryCode) {
        this.priceEntryCode = priceEntryCode;
    }



    
    /**
     * 获取 费用类型名称.
     *
     * @return the 费用类型名称
     */
    public String getPriceEntryName() {
        return priceEntryName;
    }



    
    /**
     * 设置 费用类型名称.
     *
     * @param priceEntryName the new 费用类型名称
     */
    public void setPriceEntryName(String priceEntryName) {
        this.priceEntryName = priceEntryName;
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
     * 获取 打折后的费用.
     *
     * @return the 打折后的费用
     */
    public BigDecimal getDiscountFee() {
        return discountFee;
    }



    
    /**
     * 设置 打折后的费用.
     *
     * @param discountFee the new 打折后的费用
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
     * 获取 航班班次.
     *
     * @return the 航班班次
     */
    public String getFlightShiftNo() {
        return flightShiftNo;
    }



    
    /**
     * 设置 航班班次.
     *
     * @param flightShiftNo the new 航班班次
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
     * 获取 code 服务子类型:只针对增值服务该栏位有用 对于代收货款：为退款类型(即日退，三日退，审核退) 对于签收回单：为返单类型（传真返单，原件返单） 对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等） 其它增值服务：空.
     *
     * @return the code 服务子类型:只针对增值服务该栏位有用 对于代收货款：为退款类型(即日退，三日退，审核退) 对于签收回单：为返单类型（传真返单，原件返单） 对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等） 其它增值服务：空
     */
    public String getSubType() {
        return subType;
    }



    
    /**
     * 设置 code 服务子类型:只针对增值服务该栏位有用 对于代收货款：为退款类型(即日退，三日退，审核退) 对于签收回单：为返单类型（传真返单，原件返单） 对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等） 其它增值服务：空.
     *
     * @param subType the new code 服务子类型:只针对增值服务该栏位有用 对于代收货款：为退款类型(即日退，三日退，审核退) 对于签收回单：为返单类型（传真返单，原件返单） 对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等） 其它增值服务：空
     */
    public void setSubType(String subType) {
        this.subType = subType;
    }



    
    /**
     * 获取 name 服务子类型:只针对增值服务该栏位有用 对于代收货款：为退款类型(即日退，三日退，审核退) 对于签收回单：为返单类型（传真返单，原件返单） 对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等） 其它增值服务：空.
     *
     * @return the name 服务子类型:只针对增值服务该栏位有用 对于代收货款：为退款类型(即日退，三日退，审核退) 对于签收回单：为返单类型（传真返单，原件返单） 对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等） 其它增值服务：空
     */
    public String getSubTypeName() {
        return subTypeName;
    }



    
    /**
     * 设置 name 服务子类型:只针对增值服务该栏位有用 对于代收货款：为退款类型(即日退，三日退，审核退) 对于签收回单：为返单类型（传真返单，原件返单） 对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等） 其它增值服务：空.
     *
     * @param subTypeName the new name 服务子类型:只针对增值服务该栏位有用 对于代收货款：为退款类型(即日退，三日退，审核退) 对于签收回单：为返单类型（传真返单，原件返单） 对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等） 其它增值服务：空
     */
    public void setSubTypeName(String subTypeName) {
        this.subTypeName = subTypeName;
    }



    
    /**
     * 获取 是否可以修改.
     *
     * @return the 是否可以修改
     */
    public String getCanmodify() {
        return canmodify;
    }



    
    /**
     * 设置 是否可以修改.
     *
     * @param canmodify the new 是否可以修改
     */
    public void setCanmodify(String canmodify) {
        this.canmodify = canmodify;
    }



    
    /**
     * 获取 是否可以删除.
     *
     * @return the 是否可以删除
     */
    public String getCandelete() {
        return candelete;
    }



    
    /**
     * 设置 是否可以删除.
     *
     * @param candelete the new 是否可以删除
     */
    public void setCandelete(String candelete) {
        this.candelete = candelete;
    }



    
    /**
     * 获取 其他费用归集类别代码.
     *
     * @return the 其他费用归集类别代码
     */
    public String getBelongToPriceEntityCode() {
        return belongToPriceEntityCode;
    }



    
    /**
     * 设置 其他费用归集类别代码.
     *
     * @param belongToPriceEntityCode the new 其他费用归集类别代码
     */
    public void setBelongToPriceEntityCode(String belongToPriceEntityCode) {
        this.belongToPriceEntityCode = belongToPriceEntityCode;
    }



    
    /**
     * 获取 其他费用归集类别名称.
     *
     * @return the 其他费用归集类别名称
     */
    public String getBelongToPriceEntityName() {
        return belongToPriceEntityName;
    }



    
    /**
     * 设置 其他费用归集类别名称.
     *
     * @param belongToPriceEntityName the new 其他费用归集类别名称
     */
    public void setBelongToPriceEntityName(String belongToPriceEntityName) {
        this.belongToPriceEntityName = belongToPriceEntityName;
    }



    
    /**
     * 获取 默认保费申明.
     *
     * @return the 默认保费申明
     */
    public BigDecimal getDefaultBF() {
        return defaultBF;
    }



    
    /**
     * 设置 默认保费申明.
     *
     * @param defaultBF the new 默认保费申明
     */
    public void setDefaultBF(BigDecimal defaultBF) {
        this.defaultBF = defaultBF;
    }



    
    /**
     * 获取 采用的折扣方案.
     *
     * @return the 采用的折扣方案
     */
    public List<GuiResultDiscountDto> getDiscountPrograms() {
        return discountPrograms;
    }



    
    /**
     * 设置 采用的折扣方案.
     *
     * @param discountPrograms the new 采用的折扣方案
     */
    public void setDiscountPrograms(List<GuiResultDiscountDto> discountPrograms) {
        this.discountPrograms = discountPrograms;
    }

    /**
     * 获取最低费率
     * @return
     */
	public BigDecimal getMinFeeRate() {
		return minFeeRate;
	}

	/**
	 * 设置最低费率
	 * @param minFeeRate
	 */
	public void setMinFeeRate(BigDecimal minFeeRate) {
		this.minFeeRate = minFeeRate;
	}

	/**
	 * 获取最高费率
	 * @return
	 */
	public BigDecimal getMaxFeeRate() {
		return maxFeeRate;
	}

	/**
	 * 设置最高费率
	 * @param maxFeeRate
	 */
	public void setMaxFeeRate(BigDecimal maxFeeRate) {
		this.maxFeeRate = maxFeeRate;
	}

	/**
     * 获取快递首重价格
	 * @return standExpFirstFee
	 */
	public BigDecimal getStandExpFirstFee() {
		return standExpFirstFee;
	}

	/**
     * 设置快递首重价格
     * @param standExpFirstFee
	 */
	public void setStandExpFirstFee(BigDecimal standExpFirstFee) {
		this.standExpFirstFee = standExpFirstFee;
	}

	/**
	 * 快递价格折扣方案
	 * @return expressDiscountDto
	 */
	public ExpressDiscountDto getExpressDiscountDto() {
		return expressDiscountDto;
	}

	/**
	 *  快递价格折扣方案
	 * @param expressDiscountDto
	 */
	public void setExpressDiscountDto(ExpressDiscountDto expressDiscountDto) {
		this.expressDiscountDto = expressDiscountDto;
	}

	public BigDecimal getPartialTransportFee() {
		return partialTransportFee;
	}

	public void setPartialTransportFee(BigDecimal partialTransportFee) {
		this.partialTransportFee = partialTransportFee;
	}


    
}