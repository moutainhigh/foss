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

import java.util.Date;
import java.util.List;

/**
 * 
 */
public class PublishPriceExpressDto {

    /**
     * 价格规则ID
     */
    private String pricingValuationId;
    /**
     *  产品CODE 第3级的产品代码
     */
    private String productCode;
    /**
     * 产品name
     */
    private String productName; 
    /**
     * 货物类型CODE
     */
    private String goodsTypeCode; 
    /**
     * 货物类型name
     */
    private String goodsTypeName; 
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
     * 接收日期
     */
    private Date receiveDate;
    
    /**
     * 价格明细
     */
    private List<PublishPriceExpressDetailDto> publishPriceExpressDetail;

    /**
     *  EffectivePlanDetailEntity的 id 
     */
    private String id;
    
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
     * 激活
     */
    private String active; 
    
    /**
     * 规则类型
     */
    private String valuationType;
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
    
    public Date getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
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

    
    public String getValuationType() {
		return valuationType;
	}
	public void setValuationType(String valuationType) {
		this.valuationType = valuationType;
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
	public List<PublishPriceExpressDetailDto> getPublishPriceExpressDetail() {
		return publishPriceExpressDetail;
	}
	public void setPublishPriceExpressDetail(
			List<PublishPriceExpressDetailDto> publishPriceExpressDetail) {
		this.publishPriceExpressDetail = publishPriceExpressDetail;
	}    

}