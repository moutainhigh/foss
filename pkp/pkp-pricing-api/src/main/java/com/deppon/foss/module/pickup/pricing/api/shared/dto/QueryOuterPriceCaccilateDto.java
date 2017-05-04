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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/QueryBillCacilateDto.java
 * 
 * FILE NAME        	: QueryBillCacilateDto.java
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

// TODO: Auto-generated Javadoc
/**
 * 查询偏线中转费DTO.
 *
 * @author DP-Foss-YueHongJie
 */
public class QueryOuterPriceCaccilateDto implements Serializable {
    
    /** 序列化ID. */
	private static final long serialVersionUID = -1357556085572118218L;
	
	/** 产品. */
    private String productCode;
    
    /** 货物类型. */
    private String goodsCode;
    
    /** 营业部收货日期（可选，无则表示当前日期）,即开单日期 *. */
    private Date receiveDate;
    
    /** 重量. */
    private BigDecimal weight;
    
    /** 体积. */
    private BigDecimal volume;
    
    /** 币种. */
    private String currencyCdoe;
     
    /**
     * Gets the product code.
     *
     * @return the product code
     */
    public String getProductCode() {
		return productCode;
	}

	/**
	 * Sets the product code.
	 *
	 * @param productCode the new product code
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * Gets the goods code.
	 *
	 * @return the goods code
	 */
	public String getGoodsCode() {
		return goodsCode;
	}

	/**
	 * Sets the goods code.
	 *
	 * @param goodsCode the new goods code
	 */
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	/**
	 * Gets the receive date.
	 *
	 * @return the receive date
	 */
	public Date getReceiveDate() {
		return receiveDate;
	}

	/**
	 * Sets the receive date.
	 *
	 * @param receiveDate the new receive date
	 */
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	/**
	 * Gets the weight.
	 *
	 * @return the weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * Sets the weight.
	 *
	 * @param weight the new weight
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * Gets the volume.
	 *
	 * @return the volume
	 */
	public BigDecimal getVolume() {
		return volume;
	}

	/**
	 * Sets the volume.
	 *
	 * @param volume the new volume
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	/**
	 * Gets the currency cdoe.
	 *
	 * @return the currency cdoe
	 */
	public String getCurrencyCdoe() {
		return currencyCdoe;
	}

	/**
	 * Sets the currency cdoe.
	 *
	 * @param currencyCdoe the new currency cdoe
	 */
	public void setCurrencyCdoe(String currencyCdoe) {
		this.currencyCdoe = currencyCdoe;
	}

	public String getPartialLineCode() {
		return partialLineCode;
	}

	public void setPartialLineCode(String partialLineCode) {
		this.partialLineCode = partialLineCode;
	}

	public String getOutFieldCode() {
		return outFieldCode;
	}

	public void setOutFieldCode(String outFieldCode) {
		this.outFieldCode = outFieldCode;
	}

	/** 偏线编码. */
    private String partialLineCode;
    
    /** 外场编码. */
    private String outFieldCode;
}