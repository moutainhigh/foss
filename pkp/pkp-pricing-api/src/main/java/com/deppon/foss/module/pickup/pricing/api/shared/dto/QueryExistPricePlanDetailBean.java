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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/QueryExistPricePlanDetailBean.java
 * 
 * FILE NAME        	: QueryExistPricePlanDetailBean.java
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
 * 查询价格方案下生效日期、出发地、目的地、产品、货物、是否接货、航班号
 * @author DP-Foss-YueHongJie
 * @date 2012-12-17 下午5:22:26
 * @version 1.0
 */
public class QueryExistPricePlanDetailBean implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 6923651476654380417L;
    
    /**
     * 生效日期
     */
    private Date beginTime;
    
    /**
     * 中止日期
     */
    private Date endTime;
    
    /**
     * 始发区域ID
     */
    private String priceRegionId;
    /**
     * 目的地区域ID
     */
    private String arrvRegionId;
    /**
     * 是否接货
     */
    private String isCentralizePickup;
    
    /**
     * 是否送货
     */
    private String isCentralizeDelivery;
    /**
     * 产品编码
     */
    private String productCode;
    /**
     * 货物类型编码
     */
    private String goodsTypeCode;
    /**
     * 激活状态
     */
    private String active;
    /**
     * 航班类型
     */
    private String flightShift;
    /**
     * 价格方案ID
     */
    private String pricePlanId;
    
    /**
     * 计费规则
     */
    private String valuationId;
    
    /**
     * 合票类型Code
     */
    private String combBillTypeCode;  //zxy 20140428 MANA-1253 新增
    
    
    /**
     * 开始范围
     */
    private BigDecimal leftRange ;
    /**
     * 结束范围
     */
    private BigDecimal rightRange;
    
    /**
     * 计费类别
     */
    private String caculateType;
    
    
    
    public String getCaculateType() {
		return caculateType;
	}

	public void setCaculateType(String caculateType) {
		this.caculateType = caculateType;
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

	/**
     * 获取 计费规则.
     *
     * @return the 计费规则
     */
    public String getValuationId() {
        return valuationId;
    }
    
    /**
     * 设置 计费规则.
     *
     * @param valuationId the new 计费规则
     */
    public void setValuationId(String valuationId) {
        this.valuationId = valuationId;
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
     * 获取 航班类型.
     *
     * @return the 航班类型
     */
    public String getFlightShift() {
        return flightShift;
    }

    
    /**
     * 设置 航班类型.
     *
     * @param flightShift the new 航班类型
     */
    public void setFlightShift(String flightShift) {
        this.flightShift = flightShift;
    }

    /**
     * 获取 生效日期.
     *
     * @return the 生效日期
     */
    public Date getBeginTime() {
        return beginTime;
    }
    
    /**
     * 设置 生效日期.
     *
     * @param beginTime the new 生效日期
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }
    
    /**
     * 获取 始发区域ID.
     *
     * @return the 始发区域ID
     */
    public String getPriceRegionId() {
        return priceRegionId;
    }
    
    /**
     * 设置 始发区域ID.
     *
     * @param priceRegionId the new 始发区域ID
     */
    public void setPriceRegionId(String priceRegionId) {
        this.priceRegionId = priceRegionId;
    }
    
    /**
     * 获取 目的地区域ID.
     *
     * @return the 目的地区域ID
     */
    public String getArrvRegionId() {
        return arrvRegionId;
    }
    
    /**
     * 设置 目的地区域ID.
     *
     * @param arrvRegionId the new 目的地区域ID
     */
    public void setArrvRegionId(String arrvRegionId) {
        this.arrvRegionId = arrvRegionId;
    }
    
    /**
     * 获取 是否接货.
     *
     * @return the 是否接货
     */
    public String getIsCentralizePickup() {
        return isCentralizePickup;
    }
    
    /**
     * 设置 是否接货.
     *
     * @param isCentralizePickup the new 是否接货
     */
    public void setIsCentralizePickup(String isCentralizePickup) {
        this.isCentralizePickup = isCentralizePickup;
    }
    
    /**
     * 获取 产品编码.
     *
     * @return the 产品编码
     */
    public String getProductCode() {
        return productCode;
    }
    
    /**
     * 设置 产品编码.
     *
     * @param productCode the new 产品编码
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    
    /**
     * 获取 货物类型编码.
     *
     * @return the 货物类型编码
     */
    public String getGoodsTypeCode() {
        return goodsTypeCode;
    }
    
    /**
     * 设置 货物类型编码.
     *
     * @param goodsTypeCode the new 货物类型编码
     */
    public void setGoodsTypeCode(String goodsTypeCode) {
        this.goodsTypeCode = goodsTypeCode;
    }
    
    /**
     * 获取 激活状态.
     *
     * @return the 激活状态
     */
    public String getActive() {
        return active;
    }
    
    /**
     * 设置 激活状态.
     *
     * @param active the new 激活状态
     */
    public void setActive(String active) {
        this.active = active;
    }

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCombBillTypeCode() {
		return combBillTypeCode;
	}

	public void setCombBillTypeCode(String combBillTypeCode) {
		this.combBillTypeCode = combBillTypeCode;
	}

	public String getIsCentralizeDelivery() {
		return isCentralizeDelivery;
	}

	public void setIsCentralizeDelivery(String isCentralizeDelivery) {
		this.isCentralizeDelivery = isCentralizeDelivery;
	}
    
}