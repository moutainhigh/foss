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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/PdaResultBillCalculateDto.java
 * 
 * FILE NAME        	: PdaResultBillCalculateDto.java
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
import java.util.List;

/**
 * 
 * @Description: PAD客户端产品价格计算结果的DTO
 * PdaQueryBillCalculateDto.java Create on 2013-1-14 上午10:11:47
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PdaResultBillCalculateDto implements Serializable {
    
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 费用类型代码
     */
    private String  priceEntityCode;
    
    /**
     * 费用类型名称
     */
    private String priceEntityName;
    
    /**
     * 最终实际计算的费率
     */
    private BigDecimal actualFeeRate;
    
    /**
	 * 经过计算后的费用，例如使用传入的 重量* feeRate 得出后的计算值
	 */
	private BigDecimal caculateFee;
    
	/**
	 * 打折后的费用
	 */
	private BigDecimal discountFee;
	
	/**
	 * 价格计算表达式
	 */
	private String caculateExpression;
	
	/**
	 * 是否可以修改
	 */
	private String  canModify;
	
	/**
	 * 是否可以删除
	 */
    private String  canDelete;
    
	/**
	 * 最低费用
	 */
	private BigDecimal minFee;

	/**
	 * 最高费用
	 */
	private BigDecimal maxFee;
	
	/**
	 * 
	 */
    private String  subType;
    /**
     * 
     */
    private String  subTypeName;
    
    /**
     * 是否接货
     */
    private String centralizePickup;
    
	
	public String getCentralizePickup() {
		return centralizePickup;
	}

	public void setCentralizePickup(String centralizePickup) {
		this.centralizePickup = centralizePickup;
	}

	/**
	 * 采用的折扣方案
	 */
	private List<PdaResultDiscountDto> discountPrograms;

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
	 * 获取 最终实际计算的费率.
	 *
	 * @return the 最终实际计算的费率
	 */
	public BigDecimal getActualFeeRate() {
		return actualFeeRate;
	}

	/**
	 * 设置 最终实际计算的费率.
	 *
	 * @param actualFeeRate the new 最终实际计算的费率
	 */
	public void setActualFeeRate(BigDecimal actualFeeRate) {
		this.actualFeeRate = actualFeeRate;
	}

	/**
	 * 获取 经过计算后的费用，例如使用传入的 重量* feeRate 得出后的计算值.
	 *
	 * @return the 经过计算后的费用，例如使用传入的 重量* feeRate 得出后的计算值
	 */
	public BigDecimal getCaculateFee() {
		return caculateFee;
	}

	/**
	 * 设置 经过计算后的费用，例如使用传入的 重量* feeRate 得出后的计算值.
	 *
	 * @param caculateFee the new 经过计算后的费用，例如使用传入的 重量* feeRate 得出后的计算值
	 */
	public void setCaculateFee(BigDecimal caculateFee) {
		this.caculateFee = caculateFee;
	}

	/**
	 * 获取 打折后的费用.
	 *
	 * @return the 打折后的费用
	 */
	public BigDecimal getDiscountFee() {
		return discountFee;
	}

	/**
	 * 设置 打折后的费用.
	 *
	 * @param discountFee the new 打折后的费用
	 */
	public void setDiscountFee(BigDecimal discountFee) {
		this.discountFee = discountFee;
	}

	/**
	 * 获取 价格计算表达式.
	 *
	 * @return the 价格计算表达式
	 */
	public String getCaculateExpression() {
		return caculateExpression;
	}

	/**
	 * 设置 价格计算表达式.
	 *
	 * @param caculateExpression the new 价格计算表达式
	 */
	public void setCaculateExpression(String caculateExpression) {
		this.caculateExpression = caculateExpression;
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
	 * 获取 最高费用.
	 *
	 * @return the 最高费用
	 */
	public BigDecimal getMaxFee() {
		return maxFee;
	}

	/**
	 * 设置 最高费用.
	 *
	 * @param maxFee the new 最高费用
	 */
	public void setMaxFee(BigDecimal maxFee) {
		this.maxFee = maxFee;
	}

	/**
	 * 获取 采用的折扣方案.
	 *
	 * @return the 采用的折扣方案
	 */
	public List<PdaResultDiscountDto> getDiscountPrograms() {
		return discountPrograms;
	}

	/**
	 * 设置 采用的折扣方案.
	 *
	 * @param discountPrograms the new 采用的折扣方案
	 */
	public void setDiscountPrograms(List<PdaResultDiscountDto> discountPrograms) {
		this.discountPrograms = discountPrograms;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getSubTypeName() {
		return subTypeName;
	}

	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}

}