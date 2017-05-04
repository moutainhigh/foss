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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/BargainPreferDto.java
 * 
 * FILE NAME        	: BargainPreferDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;


/**
 * 客户合同-优惠信息DTO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-12-4 下午7:50:22 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-12-4 下午7:50:22
 * @since
 * @version
 */
public class BargainPreferDto extends CusBargainEntity {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 7073719437149722815L;
    
    /**
     * 运费折扣费率.
     */
    private BigDecimal chargeRebate;
    
    /**
     * 代收货款费率.
     */
    private BigDecimal agentGathRate;
    
    /**
     * 保价费率.
     */
    private BigDecimal insureDpriceRate;
    
    /**
     * 接货费率.
     */
    private BigDecimal receivePriceRate;
    
    /**
     * 送货费率.
     */
    private BigDecimal deliveryFeeRate;
    
    /**
     * 适用部门.
     */
    private String applyDepts;
    
    /**
     * 与客户信息是多对一关系.
     */
    private CustomerDto customerDto;
    
    /**
     * 执行起始日期.
     */
    private Date effectiveDate;
    
    /**
     * 执行到期日期.
     */
    private Date expirationDate;
    
    /**
	 * 优惠所属类型(取值LTT或者EXPRESS,分别表示零担和快递)
	*/
	private String ftype;
 
	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}
    
    /**
     * 获取 执行起始日期.
     *
     * @return  the effectiveDate
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    
    /**
     * 设置 执行起始日期.
     *
     * @param effectiveDate the effectiveDate to set
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    
    /**
     * 获取 执行到期日期.
     *
     * @return  the expirationDate
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    
    /**
     * 设置 执行到期日期.
     *
     * @param expirationDate the expirationDate to set
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * 获取 运费折扣费率.
     *
     * @return  the chargeRebate
     */
    public BigDecimal getChargeRebate() {
        return chargeRebate;
    }
    
    /**
     * 设置 运费折扣费率.
     *
     * @param chargeRebate the chargeRebate to set
     */
    public void setChargeRebate(BigDecimal chargeRebate) {
        this.chargeRebate = chargeRebate;
    }
    
    /**
     * 获取 代收货款费率.
     *
     * @return  the agentGathRate
     */
    public BigDecimal getAgentGathRate() {
        return agentGathRate;
    }
    
    /**
     * 设置 代收货款费率.
     *
     * @param agentGathRate the agentGathRate to set
     */
    public void setAgentGathRate(BigDecimal agentGathRate) {
        this.agentGathRate = agentGathRate;
    }
    
    /**
     * 获取 保价费率.
     *
     * @return  the insureDpriceRate
     */
    public BigDecimal getInsureDpriceRate() {
        return insureDpriceRate;
    }
    
    /**
     * 设置 保价费率.
     *
     * @param insureDpriceRate the insureDpriceRate to set
     */
    public void setInsureDpriceRate(BigDecimal insureDpriceRate) {
        this.insureDpriceRate = insureDpriceRate;
    }
    
    /**
     * 获取 接货费率.
     *
     * @return  the receivePriceRate
     */
    public BigDecimal getReceivePriceRate() {
        return receivePriceRate;
    }
    
    /**
     * 设置 接货费率.
     *
     * @param receivePriceRate the receivePriceRate to set
     */
    public void setReceivePriceRate(BigDecimal receivePriceRate) {
        this.receivePriceRate = receivePriceRate;
    }
    
    /**
     * 获取 送货费率.
     *
     * @return  the deliveryFeeRate
     */
    public BigDecimal getDeliveryFeeRate() {
        return deliveryFeeRate;
    }
    
    /**
     * 设置 送货费率.
     *
     * @param deliveryFeeRate the deliveryFeeRate to set
     */
    public void setDeliveryFeeRate(BigDecimal deliveryFeeRate) {
        this.deliveryFeeRate = deliveryFeeRate;
    }
    
    /**
     * 获取 适用部门.
     *
     * @return  the applyDepts
     */
    public String getApplyDepts() {
        return applyDepts;
    }
    
    /**
     * 设置 适用部门.
     *
     * @param applyDepts the applyDepts to set
     */
    public void setApplyDepts(String applyDepts) {
        this.applyDepts = applyDepts;
    }
    
    /**
     * 获取 与客户信息是多对一关系.
     *
     * @return  the customerDto
     */
    public CustomerDto getCustomerDto() {
        return customerDto;
    }
    
    /**
     * 设置 与客户信息是多对一关系.
     *
     * @param customerDto the customerDto to set
     */
    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }
    
    
}
