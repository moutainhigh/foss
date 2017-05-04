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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/shared/dto/QueryMoneyDto.java
 * 
 * FILE NAME        	: QueryMoneyDto.java
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
package com.deppon.foss.module.pickup.waybill.api.shared.dto;

import java.math.BigDecimal;


/**
 * 运单发到货金额DTO
 * @author 038590-foss-wanghui
 * @date 2012-12-24 下午7:54:01
 */
public class QueryMoneyDto {

	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 是否发货客户
	 */
	private boolean isSender ;
	/**
	 * 客户类型
	 */
	private String custType;
	/**
	 * 预付金额
	 */
	private BigDecimal prePayment;
	/**
	 * 到付金额
	 */
	private BigDecimal arrivePayment;
	/**
	 * 装卸费
	 */
	private BigDecimal serviceFee;
	/**
	 * 代收货款
	 */
	private BigDecimal refund;
	
	/**
	 * Gets the waybill no.
	 *
	 * @return the waybill no
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	
	/**
	 * Sets the waybill no.
	 *
	 * @param waybillNo the new waybill no to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	/**
	 * Checks if is sender.
	 *
	 * @return true, if is sender
	 */
	public boolean isIsSender() {
		return isSender;
	}
	
	/**
	 * Sets the sender.
	 *
	 * @param isSender the new sender to set
	 */
	public void setSender(boolean isSender) {
		this.isSender = isSender;
	}
	
	/**
	 * Gets the cust type.
	 *
	 * @return the cust type
	 */
	public String getCustType() {
		return custType;
	}
	
	/**
	 * Sets the cust type.
	 *
	 * @param custType the new cust type to set
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}
	
	/**
	 * Gets the pre payment.
	 *
	 * @return the pre payment
	 */
	public BigDecimal getPrePayment() {
		return prePayment;
	}
	
	/**
	 * Sets the pre payment.
	 *
	 * @param prePayment the new pre payment to set
	 */
	public void setPrePayment(BigDecimal prePayment) {
		this.prePayment = prePayment;
	}
	
	/**
	 * Gets the arrive payment.
	 *
	 * @return the arrive payment
	 */
	public BigDecimal getArrivePayment() {
		return arrivePayment;
	}
	
	/**
	 * Sets the arrive payment.
	 *
	 * @param arrivePayment the new arrive payment to set
	 */
	public void setArrivePayment(BigDecimal arrivePayment) {
		this.arrivePayment = arrivePayment;
	}
	
	/**
	 * Gets the service fee.
	 *
	 * @return the service fee
	 */
	public BigDecimal getServiceFee() {
		return serviceFee;
	}
	
	/**
	 * Sets the service fee.
	 *
	 * @param serviceFee the new service fee to set
	 */
	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}
	
	/**
	 * Gets the refund.
	 *
	 * @return the refund
	 */
	public BigDecimal getRefund() {
		return refund;
	}
	
	/**
	 * Sets the refund.
	 *
	 * @param refund the new refund to set
	 */
	public void setRefund(BigDecimal refund) {
		this.refund = refund;
	}
	
	
}