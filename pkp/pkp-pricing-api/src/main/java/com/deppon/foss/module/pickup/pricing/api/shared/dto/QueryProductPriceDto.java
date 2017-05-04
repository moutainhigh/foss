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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/QueryProductPriceDto.java
 * 
 * FILE NAME        	: QueryProductPriceDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 运费计费查询bean
 * @author DP-Foss-YueHongJie
 * @date 2012-11-8 上午8:08:17
 */
public class QueryProductPriceDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6660437635500589904L;
    /**
     * 产品编码
     */
    private String productCode; 
    /**
     * 货物类型编码
     */
    private String goodsTypeCode;
    /**
     * 始发区域ID
     */
    private String originalOrgId;
    /**
     * 到达区域ID
     */
    private String destinationId;
    /**
     * 营业部收货日期
     */
    private Date receiveDate;
    /**
     * 是否集中接货
     */
    private String isReceiveGoods;
    /**
     * 是否集中送货 2016.07.08新增 首续重价格方案使用
     */
    private String isSendGoods;
    /**
     * 航班号
     */
    private String flightShift;
    /**
     * 币种类型
     */
    private String currencyCode;
    /**
     * 始发价格主方案ID
     */
    private String tSrvPriceRegionId; 
    /**
     * 激活标志
     */
    private String active; 
    /**
     * 规则类型
     */
    private String type; 
    
    
    /**
     * 是否自提
     */
    private String isSelfPickUp;
    
    /**
     * 合票类型Code
     */
    private String combBillTypeCode;  //zxy 20140507 MANA-1253 新增
    /**
     * 客户编码
     */
    private String customerCode;
    
    
    /**
     * 获取 规则类型.
     *
     * @return the 规则类型
     */
    public String getType() {
        return type;
    }
    
    /**
     * 设置 规则类型.
     *
     * @param type the new 规则类型
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * 获取 激活标志.
     *
     * @return the 激活标志
     */
    public String getActive() {
        return active;
    }
    
    /**
     * 设置 激活标志.
     *
     * @param active the new 激活标志
     */
    public void setActive(String active) {
        this.active = active;
    }

    /**
     * 
     *
     * @return 
     */
    public String gettSrvPriceRegionId() {
        return tSrvPriceRegionId;
    }
    
    /**
     * 
     *
     * @param tSrvPriceRegionId 
     */
    public void settSrvPriceRegionId(String tSrvPriceRegionId) {
        this.tSrvPriceRegionId = tSrvPriceRegionId;
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
     * 获取 始发区域ID.
     *
     * @return the 始发区域ID
     */
    public String getOriginalOrgId() {
        return originalOrgId;
    }
    
    /**
     * 设置 始发区域ID.
     *
     * @param originalOrgId the new 始发区域ID
     */
    public void setOriginalOrgId(String originalOrgId) {
        this.originalOrgId = originalOrgId;
    }
    
    
    /**
     * 获取 营业部收货日期.
     *
     * @return the 营业部收货日期
     */
    public Date getReceiveDate() {
        return receiveDate;
    }

    
    /**
     * 设置 营业部收货日期.
     *
     * @param receiveDate the new 营业部收货日期
     */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    /**
     * 获取 是否集中接货.
     *
     * @return the 是否集中接货
     */
    public String getIsReceiveGoods() {
        return isReceiveGoods;
    }
    
    
    /**
     * 获取 到达区域ID.
     *
     * @return the 到达区域ID
     */
    public String getDestinationId() {
        return destinationId;
    }

    
    /**
     * 设置 到达区域ID.
     *
     * @param destinationId the new 到达区域ID
     */
    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    
    /**
     * 获取 币种类型.
     *
     * @return the 币种类型
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    
    /**
     * 设置 币种类型.
     *
     * @param currencyCode the new 币种类型
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * 设置 是否集中接货.
     *
     * @param isReceiveGoods the new 是否集中接货
     */
    public void setIsReceiveGoods(String isReceiveGoods) {
        this.isReceiveGoods = isReceiveGoods;
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

    
    public String getIsSelfPickUp() {
        return isSelfPickUp;
    }

    
    public void setIsSelfPickUp(String isSelfPickUp) {
        this.isSelfPickUp = isSelfPickUp;
    }

	public String getCombBillTypeCode() {
		return combBillTypeCode;
	}

	public void setCombBillTypeCode(String combBillTypeCode) {
		this.combBillTypeCode = combBillTypeCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getIsSendGoods() {
		return isSendGoods;
	}

	public void setIsSendGoods(String isSendGoods) {
		this.isSendGoods = isSendGoods;
	}
    
}