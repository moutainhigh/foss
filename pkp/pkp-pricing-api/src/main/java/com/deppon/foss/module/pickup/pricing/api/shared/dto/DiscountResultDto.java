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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/DiscountResultDto.java
 * 
 * FILE NAME        	: DiscountResultDto.java
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
 * @Description: 
 * DiscountResultDto.java Create on 2013-4-8 下午2:40:40
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class DiscountResultDto implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = -64883491102609087L;

	/**
	 * PriceCriteriaDetailEntity 的id
	 */
	private String id;

	/**
	 * 折扣率
	 */
	private BigDecimal discountRate;
	
	//针对快递的最低代收手续费
	private BigDecimal lowestCharge;

	public BigDecimal getLowestCharge() {
		return lowestCharge;
	}

	public void setLowestCharge(BigDecimal lowestCharge) {
		this.lowestCharge = lowestCharge;
	}
	
	//单票手续费（比如代收或者是保价）
	private BigDecimal sinTicketCharge;

	public BigDecimal getSinTicketCharge() {
		return sinTicketCharge;
	}

	public void setSinTicketCharge(BigDecimal sinTicketCharge) {
		this.sinTicketCharge = sinTicketCharge;
	}

	/**
	 * 最低减免
	 */
	private BigDecimal minFee;

	/**
	 * 最高减免
	 */
	private BigDecimal maxFee;

	/**
	 * 原始费用
	 */
	private BigDecimal originnalCost;

	/**
	 * 打折后费用
	 */
	private BigDecimal discountValue;

	/**
	 * 减免方式：直接减免，还是按照比率减免
	 */
	private String type;
	/**
	 * 用户合同类型：普通合同、月发越送
	 */
	private String contractType;
	/**
	 * 折扣方式 : 运费、增值
	 */
	private String discountMode;

	/**
	 * 采用的折扣方案
	 */
	private List<PriceDiscountDto> discountPrograms;
	/**
	 * 续重折扣 供快递运费打折使用
	 */
	private BigDecimal renewalDiscountRate; 

	/**
	 * 获取 priceCriteriaDetailEntity 的id.
	 * 
	 * @return the priceCriteriaDetailEntity 的id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置 priceCriteriaDetailEntity 的id.
	 * 
	 * @param id
	 *            the new priceCriteriaDetailEntity 的id
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @param discountRate
	 *            the new 折扣率
	 */
	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}

	/**
	 * 获取 最低减免.
	 * 
	 * @return the 最低减免
	 */
	public BigDecimal getMinFee() {
		return minFee;
	}

	/**
	 * 设置 最低减免.
	 * 
	 * @param minFee
	 *            the new 最低减免
	 */
	public void setMinFee(BigDecimal minFee) {
		this.minFee = minFee;
	}

	/**
	 * 获取 最高减免.
	 * 
	 * @return the 最高减免
	 */
	public BigDecimal getMaxFee() {
		return maxFee;
	}

	/**
	 * 设置 最高减免.
	 * 
	 * @param maxFee
	 *            the new 最高减免
	 */
	public void setMaxFee(BigDecimal maxFee) {
		this.maxFee = maxFee;
	}

	/**
	 * 获取 减免方式：直接减免，还是按照比率减免.
	 * 
	 * @return the 减免方式：直接减免，还是按照比率减免
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置 减免方式：直接减免，还是按照比率减免.
	 * 
	 * @param type
	 *            the new 减免方式：直接减免，还是按照比率减免
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取 原始费用.
	 * 
	 * @return the 原始费用
	 */
	public BigDecimal getOriginnalCost() {
		return originnalCost;
	}

	/**
	 * 设置 原始费用.
	 * 
	 * @param originnalCost
	 *            the new 原始费用
	 */
	public void setOriginnalCost(BigDecimal originnalCost) {
		this.originnalCost = originnalCost;
	}

	/**
	 * 获取 打折后费用.
	 * 
	 * @return the 打折后费用
	 */
	public BigDecimal getDiscountValue() {
		return discountValue;
	}

	/**
	 * 设置 打折后费用.
	 * 
	 * @param discountValue
	 *            the new 打折后费用
	 */
	public void setDiscountValue(BigDecimal discountValue) {
		this.discountValue = discountValue;
	}

	/**
	 * 获取 折扣方式 : 运费、增值.
	 * 
	 * @return the 折扣方式 : 运费、增值
	 */
	public String getDiscountMode() {
		return discountMode;
	}

	/**
	 * 设置 折扣方式 : 运费、增值.
	 * 
	 * @param discountMode
	 *            the new 折扣方式 : 运费、增值
	 */
	public void setDiscountMode(String discountMode) {
		this.discountMode = discountMode;
	}

	/**
	 * 获取 采用的折扣方案.
	 * 
	 * @return the 采用的折扣方案
	 */
	public List<PriceDiscountDto> getDiscountPrograms() {
		return discountPrograms;
	}

	/**
	 * 设置 采用的折扣方案.
	 * 
	 * @param discountPrograms
	 *            the new 采用的折扣方案
	 */
	public void setDiscountPrograms(List<PriceDiscountDto> discountPrograms) {
		this.discountPrograms = discountPrograms;
	}

	/**
	 * 获取 用户合同类型：普通合同、月发越送.
	 * 
	 * @return the 用户合同类型：普通合同、月发越送
	 */
	public String getContractType() {
		return contractType;
	}

	/**
	 * 设置 用户合同类型：普通合同、月发越送.
	 * 
	 * @param contractType
	 *            the new 用户合同类型：普通合同、月发越送
	 */
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public BigDecimal getRenewalDiscountRate() {
		return renewalDiscountRate;
	}

	public void setRenewalDiscountRate(BigDecimal renewalDiscountRate) {
		this.renewalDiscountRate = renewalDiscountRate;
	}

}