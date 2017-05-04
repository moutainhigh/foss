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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/PdaQueryBillCalculateSubDto.java
 * 
 * FILE NAME        	: PdaQueryBillCalculateSubDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.settlement.dubbo.api.define;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @Description: 提供PAD客户端开单服务-查询产品价格计算的子集合DTO
 * PdaQueryBillCalculateSubDto.java Create on 2013-1-14 下午3:14:27
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class GuiQueryBillCalculateSubDto implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 费用类型代码
     */
    private String  priceEntityCode;
    
    /**
     * 原始费用（增值服务具备）
     */
    private BigDecimal originnalCost;
	
    /**
     * 增值服务子类型（增值服务具备）
     */
    private String subType;
    
    /**
     * 木架体积
     */
    private BigDecimal woodenVolume;
    
    /**
     * 保险声明价值
     */
    private BigDecimal insuranceAmount;
    
    /**
     * 最低费率--目前只有保费使用
     */
    private BigDecimal minFeeRate;
    
    /**
     * 最高费率--目前只有保费使用
     */
    private BigDecimal maxFeeRate;

    /**
     * 保价费率
     */
    private BigDecimal insuranceRate;
    
    
	public BigDecimal getMinFeeRate() {
		return minFeeRate;
	}

	public void setMinFeeRate(BigDecimal minFeeRate) {
		this.minFeeRate = minFeeRate;
	}

	public BigDecimal getMaxFeeRate() {
		return maxFeeRate;
	}

	public void setMaxFeeRate(BigDecimal maxFeeRate) {
		this.maxFeeRate = maxFeeRate;
	}

	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public BigDecimal getInsuranceRate() {
		return insuranceRate;
	}

	public void setInsuranceRate(BigDecimal insuranceRate) {
		this.insuranceRate = insuranceRate;
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
	 * 获取 原始费用（增值服务具备）.
	 *
	 * @return the 原始费用（增值服务具备）
	 */
	public BigDecimal getOriginnalCost() {
		return originnalCost;
	}

	/**
	 * 设置 原始费用（增值服务具备）.
	 *
	 * @param originnalCost the new 原始费用（增值服务具备）
	 */
	public void setOriginnalCost(BigDecimal originnalCost) {
		this.originnalCost = originnalCost;
	}

	/**
	 * 获取 增值服务子类型（增值服务具备）.
	 *
	 * @return the 增值服务子类型（增值服务具备）
	 */
	public String getSubType() {
		return subType;
	}

	/**
	 * 设置 增值服务子类型（增值服务具备）.
	 *
	 * @param subType the new 增值服务子类型（增值服务具备）
	 */
	public void setSubType(String subType) {
		this.subType = subType;
	}

	/**
	 * 获取 木架体积.
	 *
	 * @return the 木架体积
	 */
	public BigDecimal getWoodenVolume() {
		return woodenVolume;
	}

	/**
	 * 设置 木架体积.
	 *
	 * @param woodenVolume the new 木架体积
	 */
	public void setWoodenVolume(BigDecimal woodenVolume) {
		this.woodenVolume = woodenVolume;
	}
    
}