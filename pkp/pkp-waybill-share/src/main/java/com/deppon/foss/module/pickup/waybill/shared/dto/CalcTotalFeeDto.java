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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/CalcTotalFeeDto.java
 * 
 * FILE NAME        	: CalcTotalFeeDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
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
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**   
 * <p>
 * 营业清单结果汇总<br />
 * </p>
 * @title CalcTotalFeeDto.java
 * @package com.deppon.foss.module.pickup.waybill.shared.dto 
 * @author suyujun
 * @version 0.1 2012-12-27
 */

public class CalcTotalFeeDto implements Serializable {
	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 1605398646504861809L;
	/**
	 * 总票数
	 */
	private Integer pageCount;
	/**
	 * 总件数
	 */
	private Integer goodsQtyTotal;
	/**
	 * 总重量
	 */
	private BigDecimal  goodsWeightTotal;
	/**
	 * 总体积
	 */
	private BigDecimal goodsVolumeTotal;
	/**
	 * 总预付金额
	 */
	private BigDecimal prePayAmountTotal;
	/**
	 * 到付金额
	 */
	private BigDecimal toPayAmountTotal;
	/**
	 * 总代收货款
	 */
	private BigDecimal codAmountTotal;
	/**
	 * 总包装费
	 */
	private BigDecimal packageFeeTotal;
	/**
	 * 收入总额
	 */
	private BigDecimal amountTotal;
	/**
	 * 现金总额
	 */	
	private BigDecimal cashAmountTotal;
	/**
	 * 临时欠款总额
	 */
	private BigDecimal arrearAmountTotal;
	/**
	 * 到付总额
	 */
	private BigDecimal toPaybillAmountTotal;
	/**
	 * 月结
	 */
	private BigDecimal monthlyAmountTotal;
	/**
	 * 其他
	 */
	private BigDecimal otherAmountTotal;
	/**
	 * 开单金额
	 */
	private BigDecimal billAmountTotal;
	/**
	 * 保价费 
	 */
	private BigDecimal insuranceFee;
	/**
	 * 公布价运费
	 */
	private BigDecimal transportFee;
	/**
	 * 增值服务费
	 */
	private BigDecimal valueAddFee;
	/**
	 * 优惠费用
	 */
	private BigDecimal promotionsFee;	

	
	public BigDecimal getPromotionsFee() {
		return promotionsFee;
	}


	
	public void setPromotionsFee(BigDecimal promotionsFee) {
		this.promotionsFee = promotionsFee;
	}


	public BigDecimal getTransportFee() {
		return transportFee;
	}

	
	public void setTransportFee(BigDecimal transportFee) {
		this.transportFee = transportFee;
	}

	
	public BigDecimal getValueAddFee() {
		return valueAddFee;
	}

	
	public void setValueAddFee(BigDecimal valueAddFee) {
		this.valueAddFee = valueAddFee;
	}

	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}
	
	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}
	/**
	 * @return pageCount : set the property pageCount.
	 */
	public Integer getPageCount() {
		return pageCount;
	}
	/**
	 * @param pageCount : return the property pageCount.
	 */
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	/**
	 * @return goodsQtyTotal : set the property goodsQtyTotal.
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	/**
	 * @param goodsQtyTotal : return the property goodsQtyTotal.
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	/**
	 * @return goodsWeightTotal : set the property goodsWeightTotal.
	 */
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}
	/**
	 * @param goodsWeightTotal : return the property goodsWeightTotal.
	 */
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}
	/**
	 * @return goodsVolumeTotal : set the property goodsVolumeTotal.
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}
	/**
	 * @param goodsVolumeTotal : return the property goodsVolumeTotal.
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}
	/**
	 * @return prePayAmountTotal : set the property prePayAmountTotal.
	 */
	public BigDecimal getPrePayAmountTotal() {
		return prePayAmountTotal;
	}
	/**
	 * @param prePayAmountTotal : return the property prePayAmountTotal.
	 */
	public void setPrePayAmountTotal(BigDecimal prePayAmountTotal) {
		this.prePayAmountTotal = prePayAmountTotal;
	}
	/**
	 * @return toPayAmountTotal : set the property toPayAmountTotal.
	 */
	public BigDecimal getToPayAmountTotal() {
		return toPayAmountTotal;
	}
	/**
	 * @param toPayAmountTotal : return the property toPayAmountTotal.
	 */
	public void setToPayAmountTotal(BigDecimal toPayAmountTotal) {
		this.toPayAmountTotal = toPayAmountTotal;
	}
	/**
	 * @return codAmountTotal : set the property codAmountTotal.
	 */
	public BigDecimal getCodAmountTotal() {
		return codAmountTotal;
	}
	/**
	 * @param codAmountTotal : return the property codAmountTotal.
	 */
	public void setCodAmountTotal(BigDecimal codAmountTotal) {
		this.codAmountTotal = codAmountTotal;
	}
	/**
	 * @return packageFeeTotal : set the property packageFeeTotal.
	 */
	public BigDecimal getPackageFeeTotal() {
		return packageFeeTotal;
	}
	/**
	 * @param packageFeeTotal : return the property packageFeeTotal.
	 */
	public void setPackageFeeTotal(BigDecimal packageFeeTotal) {
		this.packageFeeTotal = packageFeeTotal;
	}
	/**
	 * @return amountTotal : set the property amountTotal.
	 */
	public BigDecimal getAmountTotal() {
		return amountTotal;
	}
	/**
	 * @param amountTotal : return the property amountTotal.
	 */
	public void setAmountTotal(BigDecimal amountTotal) {
		this.amountTotal = amountTotal;
	}
	/**
	 * @return the cashAmountTotal
	 */
	public BigDecimal getCashAmountTotal() {
		return cashAmountTotal;
	}
	/**
	 * @param cashAmountTotal the cashAmountTotal to set
	 */
	public void setCashAmountTotal(BigDecimal cashAmountTotal) {
		this.cashAmountTotal = cashAmountTotal;
	}
	/**
	 * @return the arrearAmountTotal
	 */
	public BigDecimal getArrearAmountTotal() {
		return arrearAmountTotal;
	}
	/**
	 * @param arrearAmountTotal the arrearAmountTotal to set
	 */
	public void setArrearAmountTotal(BigDecimal arrearAmountTotal) {
		this.arrearAmountTotal = arrearAmountTotal;
	}
	/**
	 * @return the toPaybillAmountTotal
	 */
	public BigDecimal getToPaybillAmountTotal() {
		return toPaybillAmountTotal;
	}
	/**
	 * @param toPaybillAmountTotal the toPaybillAmountTotal to set
	 */
	public void setToPaybillAmountTotal(BigDecimal toPaybillAmountTotal) {
		this.toPaybillAmountTotal = toPaybillAmountTotal;
	}
	/**
	 * @return the monthlyAmountTotal
	 */
	public BigDecimal getMonthlyAmountTotal() {
		return monthlyAmountTotal;
	}
	/**
	 * @param monthlyAmountTotal the monthlyAmountTotal to set
	 */
	public void setMonthlyAmountTotal(BigDecimal monthlyAmountTotal) {
		this.monthlyAmountTotal = monthlyAmountTotal;
	}
	/**
	 * @return the otherAmountTotal
	 */
	public BigDecimal getOtherAmountTotal() {
		return otherAmountTotal;
	}
	/**
	 * @param otherAmountTotal the otherAmountTotal to set
	 */
	public void setOtherAmountTotal(BigDecimal otherAmountTotal) {
		this.otherAmountTotal = otherAmountTotal;
	}
	/**
	 * @return the billAmountTotal
	 */
	public BigDecimal getBillAmountTotal() {
		return billAmountTotal;
	}
	/**
	 * @param billAmountTotal the billAmountTotal to set
	 */
	public void setBillAmountTotal(BigDecimal billAmountTotal) {
		this.billAmountTotal = billAmountTotal;
	}
	
	
}