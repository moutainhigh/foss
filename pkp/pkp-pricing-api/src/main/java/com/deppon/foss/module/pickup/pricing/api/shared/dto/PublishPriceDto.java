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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/PublishPriceDto.java
 * 
 * FILE NAME        	: PublishPriceDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 */
public class PublishPriceDto {
    
    /**
     *  EffectivePlanDetailEntity的 id 
     */
    private String id;

    /**
     *  产品CODE 第3级的产品代码
     */
    private String productCode;
    
    /**
     * 产品name
     */
    private String productName; 
    
    /**
     * 始发区域Id
     */
    private String deptRegionId;
    /**
     * 始发区域code
     */
    private String deptRegionCode;
    /**
     * 到达区域id
     */
    private String arrvRegionId;
    /**
     * 到达区域code
     */
    private String arrvRegionCode;

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
     * 承诺到达营业部时间
     */
    private String arriveTime;

    /**
     *  派送承诺需加天数
     */
    private Integer addDay;

    /**
     *  派送承诺时间
     */
    private String deliveryTime;

    /**
     * 是否有驻地部门
     */
    private String hasSalesDept;

    /**
     * 长短途
     */
    private String longOrShort;
    
    
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
     * 最低费用
     */
    private BigDecimal minFee;
    
    /**
     * 费率
     */
    private BigDecimal feeRate;
    
    /**
     * 费用类别
     */
    private String caculateType;
     
    /**
     * 接收日期
     */
    private Date receiveDate;
    
    /**
     * 规则类型
     */
    private String valuationType;
    
    /**
     * 激活
     */
    private String active;
    
    /**
     * 价格规则ID
     */
    private String pricingValuationId;
    
    /**
     * 航班班次
     */
    private String flightShiftNo;
    
    /**
     * 到达区域ID
     */
    private List<String> arrvRegionIds;
    
    /**
     * 合票类型Code
     */
    private String combBillTypeCode;  //zxy 20140428 MANA-1253 新增
    
    /**
     * 合票类型名称
     */
    private String combBillTypeName;  //zxy 20140428 MANA-1253 新增
    /**
     * 开始范围
     */
    private BigDecimal leftRange;//yangkang 20140708 新增
    
    /**
     * 结束范围
     */
    private BigDecimal rightRange;//yangkang 20140708 新增
    
    
    /**
     * 轻货费率（小数）或者单价（分）
     */
     
    private String lightFeeRateStr;//yangkang 20140708 新增
    
    /**
     * 重货费率（小数）或者单价（分）
     */
    private String heavyFeeRateStr;//yangkang 20140708 新增
    
    /**
     * 固定费用
     */
    private BigDecimal fixedCosts;
    
    /**
     * 计价分段明细list
     * @author 219413-Luomengxiang   2014-11-24
     */
    private List<PopPublicPriceDto> popPublishPriceDtoList;
    
    /**
     * 获取计价分段明细List
     * @return popPublishPriceDtoList
     */
	public List<PopPublicPriceDto> getPopPublishPriceDtoList() {
		return popPublishPriceDtoList;
	}
	 /**
     * 设置计价分段明细List
     * @return popPublishPriceDtoList
     */
	public void setPopPublishPriceDtoList(
			List<PopPublicPriceDto> popPublishPriceDtoList) {
		this.popPublishPriceDtoList = popPublishPriceDtoList;
	}

	public BigDecimal getFixedCosts() {
		return fixedCosts;
	}


	public void setFixedCosts(BigDecimal fixedCosts) {
		this.fixedCosts = fixedCosts;
	}


	/**
     * 获取 effectivePlanDetailEntity的 id.
     *
     * @return the effectivePlanDetailEntity的 id
     */
    public String getId() {
        return id;
    }

    
    /**
     * 设置 effectivePlanDetailEntity的 id.
     *
     * @param id the new effectivePlanDetailEntity的 id
     */
    public void setId(String id) {
        this.id = id;
    }

    
    /**
     * 获取 产品CODE 第3级的产品代码.
     *
     * @return the 产品CODE 第3级的产品代码
     */
    public String getProductCode() {
        return productCode;
    }

    
    /**
     * 设置 产品CODE 第3级的产品代码.
     *
     * @param productCode the new 产品CODE 第3级的产品代码
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
     * 获取 始发区域Id.
     *
     * @return the 始发区域Id
     */
    public String getDeptRegionId() {
        return deptRegionId;
    }

    
    /**
     * 设置 始发区域Id.
     *
     * @param deptRegionId the new 始发区域Id
     */
    public void setDeptRegionId(String deptRegionId) {
        this.deptRegionId = deptRegionId;
    }

    
    /**
     * 获取 始发区域code.
     *
     * @return the 始发区域code
     */
    public String getDeptRegionCode() {
        return deptRegionCode;
    }

    
    /**
     * 设置 始发区域code.
     *
     * @param deptRegionCode the new 始发区域code
     */
    public void setDeptRegionCode(String deptRegionCode) {
        this.deptRegionCode = deptRegionCode;
    }

    
    /**
     * 获取 到达区域id.
     *
     * @return the 到达区域id
     */
    public String getArrvRegionId() {
        return arrvRegionId;
    }

    
    /**
     * 设置 到达区域id.
     *
     * @param arrvRegionId the new 到达区域id
     */
    public void setArrvRegionId(String arrvRegionId) {
        this.arrvRegionId = arrvRegionId;
    }

    
    /**
     * 获取 到达区域code.
     *
     * @return the 到达区域code
     */
    public String getArrvRegionCode() {
        return arrvRegionCode;
    }

    
    /**
     * 设置 到达区域code.
     *
     * @param arrvRegionCode the new 到达区域code
     */
    public void setArrvRegionCode(String arrvRegionCode) {
        this.arrvRegionCode = arrvRegionCode;
    }

    
    /**
     * 获取 承诺最长时间.
     *
     * @return the 承诺最长时间
     */
    public Integer getMaxTime() {
        return maxTime;
    }

    
    /**
     * 设置 承诺最长时间.
     *
     * @param maxTime the new 承诺最长时间
     */
    public void setMaxTime(Integer maxTime) {
        this.maxTime = maxTime;
    }

    
    /**
     * 获取 承诺最长时间单位.
     *
     * @return the 承诺最长时间单位
     */
    public String getMaxTimeUnit() {
        return maxTimeUnit;
    }

    
    /**
     * 设置 承诺最长时间单位.
     *
     * @param maxTimeUnit the new 承诺最长时间单位
     */
    public void setMaxTimeUnit(String maxTimeUnit) {
        this.maxTimeUnit = maxTimeUnit;
    }

    
    /**
     * 获取 承诺最短时间.
     *
     * @return the 承诺最短时间
     */
    public Integer getMinTime() {
        return minTime;
    }

    
    /**
     * 设置 承诺最短时间.
     *
     * @param minTime the new 承诺最短时间
     */
    public void setMinTime(Integer minTime) {
        this.minTime = minTime;
    }

    
    /**
     * 获取 承诺最短时间单位.
     *
     * @return the 承诺最短时间单位
     */
    public String getMinTimeUnit() {
        return minTimeUnit;
    }

    
    /**
     * 设置 承诺最短时间单位.
     *
     * @param minTimeUnit the new 承诺最短时间单位
     */
    public void setMinTimeUnit(String minTimeUnit) {
        this.minTimeUnit = minTimeUnit;
    }

    
    /**
     * 获取 承诺到达营业部时间.
     *
     * @return the 承诺到达营业部时间
     */
    public String getArriveTime() {
        return arriveTime;
    }

    
    /**
     * 设置 承诺到达营业部时间.
     *
     * @param arriveTime the new 承诺到达营业部时间
     */
    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    
    /**
     * 获取 派送承诺需加天数.
     *
     * @return the 派送承诺需加天数
     */
    public Integer getAddDay() {
        return addDay;
    }

    
    /**
     * 设置 派送承诺需加天数.
     *
     * @param addDay the new 派送承诺需加天数
     */
    public void setAddDay(Integer addDay) {
        this.addDay = addDay;
    }

    
    /**
     * 获取 派送承诺时间.
     *
     * @return the 派送承诺时间
     */
    public String getDeliveryTime() {
        return deliveryTime;
    }

    
    /**
     * 设置 派送承诺时间.
     *
     * @param deliveryTime the new 派送承诺时间
     */
    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    
    /**
     * 获取 是否有驻地部门.
     *
     * @return the 是否有驻地部门
     */
    public String getHasSalesDept() {
        return hasSalesDept;
    }

    
    /**
     * 设置 是否有驻地部门.
     *
     * @param hasSalesDept the new 是否有驻地部门
     */
    public void setHasSalesDept(String hasSalesDept) {
        this.hasSalesDept = hasSalesDept;
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
	 * 
	 *
	 * @return 
	 */
	public BigDecimal getFeeRate() {
		return feeRate;
	}


	/**
	 * 
	 *
	 * @param feeRate 
	 */
	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}


	/**
	 * 获取 费用类别.
	 *
	 * @return the 费用类别
	 */
	public String getCaculateType() {
		return caculateType;
	}


	/**
	 * 设置 费用类别.
	 *
	 * @param caculateType the new 费用类别
	 */
	public void setCaculateType(String caculateType) {
		this.caculateType = caculateType;
	}


	/**
	 * 获取 接收日期.
	 *
	 * @return the 接收日期
	 */
	public Date getReceiveDate() {
		return receiveDate;
	}


	/**
	 * 设置 接收日期.
	 *
	 * @param receiveDate the new 接收日期
	 */
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}


	/**
	 * 获取 规则类型.
	 *
	 * @return the 规则类型
	 */
	public String getValuationType() {
		return valuationType;
	}


	/**
	 * 设置 规则类型.
	 *
	 * @param valuationType the new 规则类型
	 */
	public void setValuationType(String valuationType) {
		this.valuationType = valuationType;
	}


	/**
	 * 获取 激活.
	 *
	 * @return the 激活
	 */
	public String getActive() {
		return active;
	}


	/**
	 * 设置 激活.
	 *
	 * @param active the new 激活
	 */
	public void setActive(String active) {
		this.active = active;
	}


	/**
	 * 获取 价格规则ID.
	 *
	 * @return the 价格规则ID
	 */
	public String getPricingValuationId() {
		return pricingValuationId;
	}


	/**
	 * 设置 价格规则ID.
	 *
	 * @param pricingValuationId the new 价格规则ID
	 */
	public void setPricingValuationId(String pricingValuationId) {
		this.pricingValuationId = pricingValuationId;
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
	 * 获取 到达区域ID.
	 *
	 * @return the 到达区域ID
	 */
	public List<String> getArrvRegionIds() {
		return arrvRegionIds;
	}


	/**
	 * 设置 到达区域ID.
	 *
	 * @param arrvRegionIds the new 到达区域ID
	 */
	public void setArrvRegionIds(List<String> arrvRegionIds) {
		this.arrvRegionIds = arrvRegionIds;
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


	public BigDecimal getLeftRange() {
		return leftRange;
	}


	public void setLeftRange(BigDecimal leftRange) {
		this.leftRange = leftRange;
	}


	public BigDecimal getRightRange() {
		return rightRange;
	}


	public void setRightRange(BigDecimal rightRange) {
		this.rightRange = rightRange;
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

}