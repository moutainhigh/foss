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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/ResultPricePlanDetailBean.java
 * 
 * FILE NAME        	: ResultPricePlanDetailBean.java
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
 * 
 * 价格方案明细结果bean
 * ResultPricePlanDetailBean
 * DP-Foss-YueHongJie
 * 2012-12-3 下午12:23:39
 * 
 * @version 1.0.0
 *
 */
public class ResultPricePlanDetailBean implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = -4569504102312042632L;

    /**
     * 价格方案ID
     */
    private String pricePlanId;
    
    
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
     * 到达区域name
     */
    private String arrvRegionName;

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
     * 是否送货  2016.07.11 新增  首续重价格方案使用
     */
    private String centralizeDelivery;
    
    /**
     * 轻货费率（小数）或者单价（分）
     */
     
    private BigDecimal lightFeeRate;
    
    /**
     * 重货费率（小数）或者单价（分）
     */
    private BigDecimal heavyFeeRate;

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
     * 航班类型CODE
     */
    private String flightShift;

    
    /**
     * 备注信息
     */
    private String remark;
    
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
    private BigDecimal leftRange ;
    /**
     * 结束范围
     */
    private BigDecimal rightRange;
    
    /**
     * 固定费用
     */
    private BigDecimal fixedCosts;
    
    /**
     * 大票货价格
     */
    private BigDecimal prices ;
    
    /**
     * 获取 航班类型CODE.
     *
     * @return the 航班类型CODE
     */
    public String getFlightShift() {
        return flightShift;
    }



    
    /**
     * 设置 航班类型CODE.
     *
     * @param flightShift the new 航班类型CODE
     */
    public void setFlightShift(String flightShift) {
        this.flightShift = flightShift;
    }

    
    /**
     * 获取 备注信息.
     *
     * @return the 备注信息
     */
    public String getRemark() {
        return remark;
    }


    
    /**
     * 设置 备注信息.
     *
     * @param remark the new 备注信息
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
     * 获取 费率.
     *
     * @return the 费率
     */
    public BigDecimal getFeeRate() {
		return feeRate;
	}


	/**
	 * 设置 费率.
	 *
	 * @param feeRate the new 费率
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
     * 获取 到达区域name.
     *
     * @return the 到达区域name
     */
    public String getArrvRegionName() {
        return arrvRegionName;
    }


    
    /**
     * 设置 到达区域name.
     *
     * @param arrvRegionName the new 到达区域name
     */
    public void setArrvRegionName(String arrvRegionName) {
        this.arrvRegionName = arrvRegionName;
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




	public BigDecimal getFixedCosts() {
		return fixedCosts;
	}




	public void setFixedCosts(BigDecimal fixedCosts) {
		this.fixedCosts = fixedCosts;
	}




	public BigDecimal getPrices() {
		return prices;
	}




	public void setPrices(BigDecimal prices) {
		this.prices = prices;
	}




	public String getCentralizeDelivery() {
		return centralizeDelivery;
	}




	public void setCentralizeDelivery(String centralizeDelivery) {
		this.centralizeDelivery = centralizeDelivery;
	}
	
	


}