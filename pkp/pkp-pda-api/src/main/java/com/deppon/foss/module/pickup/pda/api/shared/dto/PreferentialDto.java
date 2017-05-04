/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-pda-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/shared/dto/PreferentialDto.java
 * 
 * FILE NAME        	: PreferentialDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pda.api.shared.dto;

import java.math.BigDecimal;

/**
 * 客户优惠信息实体类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-22 上午10:04:32 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-22 上午10:04:32
 * @since
 * @version
 */
public class PreferentialDto {
     
    /**
     * 是否启用
     */
    private String active;
    /**
     * 运费折扣费率
     */
    private BigDecimal chargeRebate;
    /**
     * 代收货款费率
     */
    private BigDecimal agentGathRate;
    /**
     * 保价费率
     */
    private BigDecimal insureDpriceRate;
    /**
     * 接货费率
     */
    private BigDecimal receivePriceRate;
    /**
     * 送货费率
     */
    private BigDecimal deliveryFeeRate;
    /**
     * 优惠信息对应的合同ID
     */
    private BigDecimal cusBargainId;
    /**
     * 在CRM中fid
     */
    private BigDecimal crmId; 
    /**
     * 付款方式.   NOT_MONTH_END 非月结          MONTH_END 月结
     */
    private String chargeType;
    
    /**
     * 申请欠款额度 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）.
     */
    private BigDecimal arrearsAmount;
    
    
    /**
     * 获取 在CRM中fid.
     *
     * @return the 在CRM中fid
     */
    public BigDecimal getCrmId() {
        return crmId;
    }
    
    /**
     * 设置 在CRM中fid.
     *
     * @param crmId the new 在CRM中fid
     */
    public void setCrmId(BigDecimal crmId) {
        this.crmId = crmId;
    }
    /**
     * @return  the active 是否启用
     */
    public String getActive() {
        return active;
    }
    
    /**
     * @param active the active to set 是否启用
     */
    public void setActive(String active) {
        this.active = active;
    }
    
    /**
     * @return  the chargeRebate 运费折扣费率
     */
    public BigDecimal getChargeRebate() {
        return chargeRebate;
    }
    
    /**
     * @param chargeRebate the chargeRebate to set 运费折扣费率
     */
    public void setChargeRebate(BigDecimal chargeRebate) {
        this.chargeRebate = chargeRebate;
    }
    
    /**
     * @return  the agentGathRate 代收货款费率
     */
    public BigDecimal getAgentGathRate() {
        return agentGathRate;
    }
    
    /**
     * @param agentGathRate the agentGathRate to set 代收货款费率
     */
    public void setAgentGathRate(BigDecimal agentGathRate) {
        this.agentGathRate = agentGathRate;
    }
    
    /**
     * @return  the insureDpriceRate 代收货款费率
     */
    public BigDecimal getInsureDpriceRate() {
        return insureDpriceRate;
    }
    
    /**
     * @param insureDpriceRate the insureDpriceRate to set 代收货款费率
     */
    public void setInsureDpriceRate(BigDecimal insureDpriceRate) {
        this.insureDpriceRate = insureDpriceRate;
    }
    
    /**
     * @return  the receivePriceRate 接货费率
     */
    public BigDecimal getReceivePriceRate() {
        return receivePriceRate;
    }
    
    /**
     * @param receivePriceRate the receivePriceRate to set 接货费率
     */
    public void setReceivePriceRate(BigDecimal receivePriceRate) {
        this.receivePriceRate = receivePriceRate;
    }
    
    /**
     * @return  the deliveryFeeRate 送货费率
     */
    public BigDecimal getDeliveryFeeRate() {
        return deliveryFeeRate;
    }
    
    /**
     * @param deliveryFeeRate the deliveryFeeRate to set 送货费率
     */
    public void setDeliveryFeeRate(BigDecimal deliveryFeeRate) {
        this.deliveryFeeRate = deliveryFeeRate;
    }
    
    /**
     * 获取 优惠信息对应的合同ID.
     *
     * @return the 优惠信息对应的合同ID
     */
    public BigDecimal getCusBargainId() {
        return cusBargainId;
    }
    
    /**
     * 设置 优惠信息对应的合同ID.
     *
     * @param cusBargainId the new 优惠信息对应的合同ID
     */
    public void setCusBargainId(BigDecimal cusBargainId) {
        this.cusBargainId = cusBargainId;
    }

	/**
	 * 获取 付款方式.
	 *
	 * @return the 付款方式
	 */
	public String getChargeType() {
		return chargeType;
	}

	/**
	 * 设置 付款方式.
	 *
	 * @param chargeType the new 付款方式
	 */
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	/**
	 * 获取 申请欠款额度 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）.
	 *
	 * @return the 申请欠款额度 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	public BigDecimal getArrearsAmount() {
		return arrearsAmount;
	}

	/**
	 * 设置 申请欠款额度 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）.
	 *
	 * @param arrearsAmount the new 申请欠款额度 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	public void setArrearsAmount(BigDecimal arrearsAmount) {
		this.arrearsAmount = arrearsAmount;
	}
}