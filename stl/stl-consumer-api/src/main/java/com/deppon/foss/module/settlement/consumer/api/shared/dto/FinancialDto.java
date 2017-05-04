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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/FinancialDto.java
 * 
 * FILE NAME        	: FinancialDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/***
 * 财务信息
 * @author foss-meiying
 * @date 2012-10-30 下午2:22:24
 * @since
 * @version
 */
public class FinancialDto implements Serializable {
	private static final long serialVersionUID = 1632253893695788915L;
	/** 
	 * 收款总额
	 */
	private BigDecimal toPayAmount;
	/**
	 *  代收货款
	 */
	private BigDecimal codAmount;
	/**
	 *  实付运费
	 */
	private BigDecimal pocketShipping;
	/** 
	 * 开单付款方式
	 */
	private String paymentType;
	/**
	 *  应收代收款
	 */
	private BigDecimal receiveableAmount;
	/**
	 *  已收代收款
	 */
	private BigDecimal receivedAmount;
	/**
	 *  应收到付款
	 */
	private BigDecimal receiveablePayAmoout;
	/**
	 *  已收到付款
	 */
	private BigDecimal receivedPayAmount;
	/** 
	 * 是否整车运单
	 */
	private String isWholeVehicle;
	/**
	 *  运输性质
	 */
	private String productCode;
	/**
	 * 客户编码
	 */
	private String consigneeCode;
	/**
	 * 客户名称
	 */
	private String consigneeName;
	/**
	 * 款项认领编号
	 */
	private String claimNo;
	/**
	 * 派送单编号
	 */
	private String deliverbillNo;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 收货人(收货客户名称)
	 */
	private String receiveCustomerName;
	/**
	 * 收货客户联系人
	 */
	private String receiveCustomerContact;
	
	/**
	 *  收货人是否大客户.
	 */
	private String receiveBigCustomer; 
	
	/**
	 *  关联单号应收到付款.
	 */
	private BigDecimal oldReceiveablePayAmoout;
	
	/**
	 *  是否存在绑定的关联单号.
	 */
	private boolean isExistOldWaybillNo;
	/**张新2015-2-7
	 *  关联单号费用.
	 */
	private BigDecimal connetnumCost; 
	/**张新2015-2-7
	 *  总收款金额.
	 */
	private BigDecimal totalPayment; 
	/**
	 *  总金额.
	 */
	private BigDecimal totalMoney;

	/**
	 * 返单类型
	 */
	private String returnbillType;

	public String getReturnbillType() {
		return returnbillType;
	}

	public void setReturnbillType(String returnbillType) {
		this.returnbillType = returnbillType;
	}


	/**
	 * Gets the 收货人是否大客户.
	 *
	 * @return the 收货人是否大客户.
	 */
	public String getReceiveBigCustomer() {
		return receiveBigCustomer;
	}

	/**
	 * Sets the 收货人是否大客户.
	 *
	 * @param receiveBigCustomer the 收货人是否大客户.
	 */
	public void setReceiveBigCustomer(String receiveBigCustomer) {
		this.receiveBigCustomer = receiveBigCustomer;
	}

	/**
	 * Gets the 到付金额(实付运费).
	 *
	 * @return the 到付金额(实付运费)
	 */
	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	/**
	 * Sets the 到付金额(实付运费).
	 *
	 * @param toPayAmount the new 到付金额(实付运费)
	 */
	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	/**
	 * Gets the 代收货款.
	 *
	 * @return the 代收货款
	 */
	public BigDecimal getCodAmount() {
		return codAmount;
	}

	/**
	 * Sets the 代收货款.
	 *
	 * @param codAmount the new 代收货款
	 */
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}


	/**
	 * Gets the 开单付款方式.
	 *
	 * @return the 开单付款方式
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * Sets the 开单付款方式.
	 *
	 * @param paymentType the new 开单付款方式
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * Gets the 应收代收款.
	 *
	 * @return the 应收代收款
	 */
	public BigDecimal getReceiveableAmount() {
		return receiveableAmount;
	}

	/**
	 * Sets the 应收代收款.
	 *
	 * @param receiveableAmount the new 应收代收款
	 */
	public void setReceiveableAmount(BigDecimal receiveableAmount) {
		this.receiveableAmount = receiveableAmount;
	}

	/**
	 * Gets the 已收代收款.
	 *
	 * @return the 已收代收款
	 */
	public BigDecimal getReceivedAmount() {
		return receivedAmount;
	}

	/**
	 * Sets the 已收代收款.
	 *
	 * @param receivedAmount the new 已收代收款
	 */
	public void setReceivedAmount(BigDecimal receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	/**
	 * Gets the 应收到付款.
	 *
	 * @return the 应收到付款
	 */
	public BigDecimal getReceiveablePayAmoout() {
		return receiveablePayAmoout;
	}

	/**
	 * Sets the 应收到付款.
	 *
	 * @param receiveablePayAmoout the new 应收到付款
	 */
	public void setReceiveablePayAmoout(BigDecimal receiveablePayAmoout) {
		this.receiveablePayAmoout = receiveablePayAmoout;
	}

	/**
	 * Gets the 已收到付款.
	 *
	 * @return the 已收到付款
	 */
	public BigDecimal getReceivedPayAmount() {
		return receivedPayAmount;
	}

	/**
	 * Sets the 已收到付款.
	 *
	 * @param receivedPayAmount the new 已收到付款
	 */
	public void setReceivedPayAmount(BigDecimal receivedPayAmount) {
		this.receivedPayAmount = receivedPayAmount;
	}

	/**
	 * Gets the 是否整车运单.
	 *
	 * @return the 是否整车运单
	 */
	public String getIsWholeVehicle() {
		return isWholeVehicle;
	}

	/**
	 * Sets the 是否整车运单.
	 *
	 * @param isWholeVehicle the new 是否整车运单
	 */
	public void setIsWholeVehicle(String isWholeVehicle) {
		this.isWholeVehicle = isWholeVehicle;
	}

	/**
	 * Gets the 运输性质.
	 *
	 * @return the 运输性质
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * Sets the 运输性质.
	 *
	 * @param productCode the new 运输性质
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * Gets the 客户编码.
	 *
	 * @return the 客户编码
	 */
	public String getConsigneeCode() {
		return consigneeCode;
	}

	/**
	 * Sets the 客户编码.
	 *
	 * @param consigneeCode the new 客户编码
	 */
	public void setConsigneeCode(String consigneeCode) {
		this.consigneeCode = consigneeCode;
	}

	/**
	 * Gets the 客户名称.
	 *
	 * @return the 客户名称
	 */
	public String getConsigneeName() {
		return consigneeName;
	}

	/**
	 * Sets the 客户名称.
	 *
	 * @param consigneeName the new 客户名称
	 */
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	/**
	 * Gets the 款项认领编号.
	 *
	 * @return the 款项认领编号
	 */
	public String getClaimNo() {
		return claimNo;
	}

	/**
	 * Sets the 款项认领编号.
	 *
	 * @param claimNo the new 款项认领编号
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	/**
	 * Gets the 派送单编号.
	 *
	 * @return the 派送单编号
	 */
	public String getDeliverbillNo() {
		return deliverbillNo;
	}

	/**
	 * Sets the 派送单编号.
	 *
	 * @param deliverbillNo the new 派送单编号
	 */
	public void setDeliverbillNo(String deliverbillNo) {
		this.deliverbillNo = deliverbillNo;
	}

	/**
	 * Gets the 订单号.
	 *
	 * @return the 订单号
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * Sets the 订单号.
	 *
	 * @param orderNo the new 订单号
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getPocketShipping() {
		return pocketShipping;
	}

	public void setPocketShipping(BigDecimal pocketShipping) {
		this.pocketShipping = pocketShipping;
	}

	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	public BigDecimal getOldReceiveablePayAmoout() {
		return oldReceiveablePayAmoout;
	}

	public void setOldReceiveablePayAmoout(BigDecimal oldReceiveablePayAmoout) {
		this.oldReceiveablePayAmoout = oldReceiveablePayAmoout;
	}

	public boolean isExistOldWaybillNo() {
		return isExistOldWaybillNo;
	}

	public void setIsExistOldWaybillNo(boolean isExistOldWaybillNo) {
		this.isExistOldWaybillNo = isExistOldWaybillNo;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public BigDecimal getConnetnumCost() {
		return connetnumCost;
	}

	public void setConnetnumCost(BigDecimal connetnumCost) {
		this.connetnumCost = connetnumCost;
	}

	public BigDecimal getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(BigDecimal totalPayment) {
		this.totalPayment = totalPayment;
	}


}