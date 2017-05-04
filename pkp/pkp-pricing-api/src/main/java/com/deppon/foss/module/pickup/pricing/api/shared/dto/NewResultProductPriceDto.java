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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/ResultProductPriceDto.java
 * 
 * FILE NAME        	: ResultProductPriceDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 *****************************************************************************
 */
package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @Description: 
 * ResultProductPriceDto.java Create on 2013-3-17 上午10:25:54
 * Company:IBM
 * @author FOSSDP-Administrator
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
 
public class NewResultProductPriceDto implements Serializable{
    
    /** 
     * The Constant serialVersionUID. 
     */
    private static final long serialVersionUID = 1627828191943242023L;

    /** 
     * 计费规则ID.   
     */
    private String pricingValuationId;
    
    /** 
     * 计费明细ID.   
     */
    private String pricingCriteriaDetailId;
    
    /**
     *  价格方案名称.   
     */
    private String pricePlanName;  
    
    /** 
     * 价格方案ID.   
     */
    private String pricePlanId; 

    /**
     *  产品CODE.   
     */
    private String productCode; 
    
    /** 
     * 产品名称.   
     */
    private String productName; 
    
    /** 
     * 货物编码.   
     */
    private String goodsTypeCode;  
    
    /**
     *  货物名称.   
     */
    private String goodsTypeName; 
    
    /** 
     * 是否集中接货.   
     */
    private String centralizePickup;
    
    /** 
     * 规则类型.   
     */
    private String valuationType; 
    
    /** 
     * 航班号.   
     */
    private String flightShift; 
    
    /** 
     * 币种.   
     */
    private String currencyCode; 
    
    /** 
     * 费用类型名称.   
     */
    private String priceEntityName; 
    
    /** 
     * 费用类型代码.   
     */
    private String priceEntityCode; 
    
    /** 
     * 计费类别.   
     */
    private String caculateType;
    
    /**
     *  费用单价.   
     */
    private BigDecimal feeRate; 
    
    /**
     *  固定费用.   
     */
    private Long fee;
    
    /** 
     * 最低费用.   
     */
    private Long minFee;
    
    /** 
     * 最高费用.   
     */
    private Long maxFee;
    
    /** 
     * 计价条目CODE.   
     */
    private String pricingEntryCode;
    
    /** 
     * 计价名称.   
     */
    private String pricingEntryName;
    
    /** 
     * 是否可以修改.   
     */
    private String  canModify; 
    
    /** 
     * 是否可以删除.   
     */
    private String  canDelete; 
    
    /** 
     * 出发区域ID.   
     */
    private String  deptRegionId; 
    
    /**
     *  目的区域ID.   
     */
    private String  arrvRegionId;
    
    /** 
     * 折扣率.   
     */
    private BigDecimal discountRate; 
    
    /** 
     * 服务子类型.   
     */
    private String subType;
    
    /** 
     * 计价左区间.   
     */
    private double leftrange;
    
    /** 
     * 计价右区间.   
     */
    private double rightrange;
    
    /** 
     * 计价参数1.   
     */
    private Long parm1;
    
    /** 
     * 计价参数2.   
     */
    private Long parm2;
    
    /** 
     * 计价参数3.   
     */
    private Long parm3;
    
    /** 
     * 计价参数4.   
     */
    private Long parm4;
    
    /** 
     * 计价参数5.   
     */
    private Long parm5;
    
    /** 
     * 规则表达式ID.   
     */

    private String tSrvPriceRuleId; 
    
    /** 
     * 规则表达式.   
     */
    private String experssion; 
    
    
    /** 
     * 长途还是短途.   
     */
    private String longOrShort;

    /**方案开始时间**/
    private Date beginTime;
    
    /**
     * 最低费率
     */
    private BigDecimal minFeeRate;
    
    /**
     * 最高费率
     */
    private BigDecimal maxFeeRate;
    
    /**
     * 步进量纲
     */
    private double dimension; 
        
    /**
     * 费用条目描述 
     */
    private String description;
    
    
    /**
     * 临界值
     * 
     */
    private long criticalValue;
    
    /**
     * 计费明细ID
     */
    private String id;
    
    /**
     * 分段数
     */
    private String sectionId;
    
    
    public String getPricingValuationId() {
		return pricingValuationId;
	}



	public void setPricingValuationId(String pricingValuationId) {
		this.pricingValuationId = pricingValuationId;
	}



	public String getSectionId() {
		return sectionId;
	}



	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public long getCriticalValue() {
		return criticalValue;
	}



	public void setCriticalValue(long criticalValue) {
		this.criticalValue = criticalValue;
	}



	/**
     * 获取 方案开始时间*.
     *
     * @return the 方案开始时间*
     */
    public Date getBeginTime() {
        return beginTime;
    }


    
    /**
     * 设置 方案开始时间*.
     *
     * @param beginTime the new 方案开始时间*
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }


    
    /**
     * 获取 方案结束时间*.
     *
     * @return the 方案结束时间*
     */
    public Date getEndTime() {
        return endTime;
    }


    
    /**
     * 设置 方案结束时间*.
     *
     * @param endTime the new 方案结束时间*
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    /**方案结束时间**/
    private Date endTime;
	/**
	 * 获取 计费明细ID.
	 *
	 * @return the 计费明细ID
	 */
	public String getPricingCriteriaDetailId() {
		return pricingCriteriaDetailId;
	}


	/**
	 * 设置 计费明细ID.
	 *
	 * @param pricingCriteriaDetailId the new 计费明细ID
	 */
	public void setPricingCriteriaDetailId(String pricingCriteriaDetailId) {
		this.pricingCriteriaDetailId = pricingCriteriaDetailId;
	}


	/**
	 * 获取 价格方案名称.
	 *
	 * @return the 价格方案名称
	 */
	public String getPricePlanName() {
		return pricePlanName;
	}


	/**
	 * 设置 价格方案名称.
	 *
	 * @param pricePlanName the new 价格方案名称
	 */
	public void setPricePlanName(String pricePlanName) {
		this.pricePlanName = pricePlanName;
	}


	/**
	 * 获取 价格方案ID.
	 *
	 * @return the 价格方案ID
	 */
	public String getPricePlanId() {
		return pricePlanId;
	}


	/**
	 * 设置 价格方案ID.
	 *
	 * @param pricePlanId the new 价格方案ID
	 */
	public void setPricePlanId(String pricePlanId) {
		this.pricePlanId = pricePlanId;
	}


	/**
	 * 获取 产品CODE.
	 *
	 * @return the 产品CODE
	 */
	public String getProductCode() {
		return productCode;
	}


	/**
	 * 设置 产品CODE.
	 *
	 * @param productCode the new 产品CODE
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}


	/**
	 * 获取 产品名称.
	 *
	 * @return the 产品名称
	 */
	public String getProductName() {
		return productName;
	}


	/**
	 * 设置 产品名称.
	 *
	 * @param productName the new 产品名称
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}


	/**
	 * 获取 货物编码.
	 *
	 * @return the 货物编码
	 */
	public String getGoodsTypeCode() {
		return goodsTypeCode;
	}


	/**
	 * 设置 货物编码.
	 *
	 * @param goodsTypeCode the new 货物编码
	 */
	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
	}


	/**
	 * 获取 货物名称.
	 *
	 * @return the 货物名称
	 */
	public String getGoodsTypeName() {
		return goodsTypeName;
	}


	/**
	 * 设置 货物名称.
	 *
	 * @param goodsTypeName the new 货物名称
	 */
	public void setGoodsTypeName(String goodsTypeName) {
		this.goodsTypeName = goodsTypeName;
	}


	/**
	 * 获取 是否集中接货.
	 *
	 * @return the 是否集中接货
	 */
	public String getCentralizePickup() {
		return centralizePickup;
	}


	/**
	 * 设置 是否集中接货.
	 *
	 * @param centralizePickup the new 是否集中接货
	 */
	public void setCentralizePickup(String centralizePickup) {
		this.centralizePickup = centralizePickup;
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
	 * 获取 航班号.
	 *
	 * @return the 航班号
	 */
	public String getFlightShift() {
		return flightShift;
	}


	/**
	 * 设置 航班号.
	 *
	 * @param flightShift the new 航班号
	 */
	public void setFlightShift(String flightShift) {
		this.flightShift = flightShift;
	}


	/**
	 * 获取 币种.
	 *
	 * @return the 币种
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}


	/**
	 * 设置 币种.
	 *
	 * @param currencyCode the new 币种
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
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
	 * 获取 计费类别.
	 *
	 * @return the 计费类别
	 */
	public String getCaculateType() {
		return caculateType;
	}


	/**
	 * 设置 计费类别.
	 *
	 * @param caculateType the new 计费类别
	 */
	public void setCaculateType(String caculateType) {
		this.caculateType = caculateType;
	}


	/**
	 * 获取 费用单价.
	 *
	 * @return the 费用单价
	 */
	public BigDecimal getFeeRate() {
		return feeRate;
	}


	/**
	 * 设置 费用单价.
	 *
	 * @param feeRate the new 费用单价
	 */
	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}


	/**
	 * 获取 固定费用.
	 *
	 * @return the 固定费用
	 */
	public Long getFee() {
		return fee;
	}


	/**
	 * 设置 固定费用.
	 *
	 * @param fee the new 固定费用
	 */
	public void setFee(Long fee) {
		this.fee = fee;
	}


	/**
	 * 获取 最低费用.
	 *
	 * @return the 最低费用
	 */
	public Long getMinFee() {
		return minFee;
	}


	/**
	 * 设置 最低费用.
	 *
	 * @param minFee the new 最低费用
	 */
	public void setMinFee(Long minFee) {
		this.minFee = minFee;
	}


	/**
	 * 获取 最高费用.
	 *
	 * @return the 最高费用
	 */
	public Long getMaxFee() {
		return maxFee;
	}


	/**
	 * 设置 最高费用.
	 *
	 * @param maxFee the new 最高费用
	 */
	public void setMaxFee(Long maxFee) {
		this.maxFee = maxFee;
	}


	/**
	 * 获取 计价条目CODE.
	 *
	 * @return the 计价条目CODE
	 */
	public String getPricingEntryCode() {
		return pricingEntryCode;
	}


	/**
	 * 设置 计价条目CODE.
	 *
	 * @param pricingEntryCode the new 计价条目CODE
	 */
	public void setPricingEntryCode(String pricingEntryCode) {
		this.pricingEntryCode = pricingEntryCode;
	}


	/**
	 * 获取 计价名称.
	 *
	 * @return the 计价名称
	 */
	public String getPricingEntryName() {
		return pricingEntryName;
	}


	/**
	 * 设置 计价名称.
	 *
	 * @param pricingEntryName the new 计价名称
	 */
	public void setPricingEntryName(String pricingEntryName) {
		this.pricingEntryName = pricingEntryName;
	}


	/**
	 * 获取 是否可以修改.
	 *
	 * @return the 是否可以修改
	 */
	public String getCanModify() {
		return canModify;
	}


	/**
	 * 设置 是否可以修改.
	 *
	 * @param canModify the new 是否可以修改
	 */
	public void setCanModify(String canModify) {
		this.canModify = canModify;
	}


	/**
	 * 获取 是否可以删除.
	 *
	 * @return the 是否可以删除
	 */
	public String getCanDelete() {
		return canDelete;
	}


	/**
	 * 设置 是否可以删除.
	 *
	 * @param canDelete the new 是否可以删除
	 */
	public void setCanDelete(String canDelete) {
		this.canDelete = canDelete;
	}


	/**
	 * 获取 出发区域ID.
	 *
	 * @return the 出发区域ID
	 */
	public String getDeptRegionId() {
		return deptRegionId;
	}


	/**
	 * 设置 出发区域ID.
	 *
	 * @param deptRegionId the new 出发区域ID
	 */
	public void setDeptRegionId(String deptRegionId) {
		this.deptRegionId = deptRegionId;
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
	 * 获取 服务子类型.
	 *
	 * @return the 服务子类型
	 */
	public String getSubType() {
		return subType;
	}


	/**
	 * 设置 服务子类型.
	 *
	 * @param subType the new 服务子类型
	 */
	public void setSubType(String subType) {
		this.subType = subType;
	}


	/**
	 * 获取 计价左区间.
	 *
	 * @return the 计价左区间
	 */
	public double getLeftrange() {
		return leftrange;
	}


	/**
	 * 设置 计价左区间.
	 *
	 * @param leftrange the new 计价左区间
	 */
	public void setLeftrange(double leftrange) {
		this.leftrange = leftrange;
	}


	/**
	 * 获取 计价右区间.
	 *
	 * @return the 计价右区间
	 */
	public double getRightrange() {
		return rightrange;
	}


	/**
	 * 设置 计价右区间.
	 *
	 * @param rightrange the new 计价右区间
	 */
	public void setRightrange(double rightrange) {
		this.rightrange = rightrange;
	}


	/**
	 * 获取 计价参数1.
	 *
	 * @return the 计价参数1
	 */
	public Long getParm1() {
		return parm1;
	}


	/**
	 * 设置 计价参数1.
	 *
	 * @param parm1 the new 计价参数1
	 */
	public void setParm1(Long parm1) {
		this.parm1 = parm1;
	}


	/**
	 * 获取 计价参数2.
	 *
	 * @return the 计价参数2
	 */
	public Long getParm2() {
		return parm2;
	}


	/**
	 * 设置 计价参数2.
	 *
	 * @param parm2 the new 计价参数2
	 */
	public void setParm2(Long parm2) {
		this.parm2 = parm2;
	}


	/**
	 * 获取 计价参数3.
	 *
	 * @return the 计价参数3
	 */
	public Long getParm3() {
		return parm3;
	}


	/**
	 * 设置 计价参数3.
	 *
	 * @param parm3 the new 计价参数3
	 */
	public void setParm3(Long parm3) {
		this.parm3 = parm3;
	}


	/**
	 * 获取 计价参数4.
	 *
	 * @return the 计价参数4
	 */
	public Long getParm4() {
		return parm4;
	}


	/**
	 * 设置 计价参数4.
	 *
	 * @param parm4 the new 计价参数4
	 */
	public void setParm4(Long parm4) {
		this.parm4 = parm4;
	}


	/**
	 * 获取 计价参数5.
	 *
	 * @return the 计价参数5
	 */
	public Long getParm5() {
		return parm5;
	}


	/**
	 * 设置 计价参数5.
	 *
	 * @param parm5 the new 计价参数5
	 */
	public void setParm5(Long parm5) {
		this.parm5 = parm5;
	}


	/**
	 * 
	 *
	 * @return 
	 */
	public String gettSrvPriceRuleId() {
		return tSrvPriceRuleId;
	}


	/**
	 * 
	 *
	 * @param tSrvPriceRuleId 
	 */
	public void settSrvPriceRuleId(String tSrvPriceRuleId) {
		this.tSrvPriceRuleId = tSrvPriceRuleId;
	}


	/**
	 * 获取 规则表达式.
	 *
	 * @return the 规则表达式
	 */
	public String getExperssion() {
		return experssion;
	}


	/**
	 * 设置 规则表达式.
	 *
	 * @param experssion the new 规则表达式
	 */
	public void setExperssion(String experssion) {
		this.experssion = experssion;
	}


	/**
	 * 获取 长途还是短途.
	 *
	 * @return the 长途还是短途
	 */
	public String getLongOrShort() {
		return longOrShort;
	}


	/**
	 * 设置 长途还是短途.
	 *
	 * @param longOrShort the new 长途还是短途
	 */
	public void setLongOrShort(String longOrShort) {
		this.longOrShort = longOrShort;
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
	
	public double getDimension() {
	    if(BigDecimal.ZERO.compareTo(BigDecimal.valueOf(dimension))==0)
	    {
		dimension=1;
	    }
	    return dimension;
	}

	public void setDimension(double dimension) {
	    this.dimension = dimension;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}    
}