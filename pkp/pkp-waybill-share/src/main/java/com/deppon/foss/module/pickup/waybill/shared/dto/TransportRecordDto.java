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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/TransportRecordDto.java
 * 
 * FILE NAME        	: TransportRecordDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 转货、返货记录
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-31 下午2:30:27
 */
public class TransportRecordDto implements Serializable{

	private static final long serialVersionUID = -6536338345635842565L;

	/**
	 * 变更类型
	 */
	private String rfcType;

	/**
	 * 目的站
	 */
	private String targetOrgCode;

	/**
	 * 提货方式
	 */
	private String receiveMethod;

	/**
	 * 提货网点
	 */
	private String customerPickupOrgCode;

	/**
	 * 运输性质
	 */
	private String productCode;

	/**
	 * 计费类型
	 */
	private String billingType;

	/**
	 * 费率
	 */
	private BigDecimal unitPrice;
	
	/**
	 * 航班时间
	 */
	private String flightShift;
	
	/**
	 * 预配航班
	 */
	private String flightNumberType;
	
	/**
	 * 合票方式
	 */
	private String freightMethod;

	/**
	 * 运费
	 */
	private BigDecimal transportFee;

	
	/**
	 * @return the rfcType
	 */
	public String getRfcType() {
		return rfcType;
	}

	
	/**
	 * @param rfcType the rfcType to set
	 */
	public void setRfcType(String rfcType) {
		this.rfcType = rfcType;
	}

	
	/**
	 * @return the targetOrgCode
	 */
	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	
	/**
	 * @param targetOrgCode the targetOrgCode to set
	 */
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	
	/**
	 * @return the receiveMethod
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}

	
	/**
	 * @param receiveMethod the receiveMethod to set
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	
	/**
	 * @return the customerPickupOrgCode
	 */
	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	
	/**
	 * @param customerPickupOrgCode the customerPickupOrgCode to set
	 */
	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	
	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	
	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	
	/**
	 * @return the billingType
	 */
	public String getBillingType() {
		return billingType;
	}

	
	/**
	 * @param billingType the billingType to set
	 */
	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}

	
	/**
	 * @return the unitPrice
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	
	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	
	/**
	 * @return the flightShift
	 */
	public String getFlightShift() {
		return flightShift;
	}

	
	/**
	 * @param flightShift the flightShift to set
	 */
	public void setFlightShift(String flightShift) {
		this.flightShift = flightShift;
	}

	
	/**
	 * @return the transportFee
	 */
	public BigDecimal getTransportFee() {
		return transportFee;
	}

	
	/**
	 * @param transportFee the transportFee to set
	 */
	public void setTransportFee(BigDecimal transportFee) {
		this.transportFee = transportFee;
	}


	
	/**
	 * @return the flightNumberType
	 */
	public String getFlightNumberType() {
		return flightNumberType;
	}


	
	/**
	 * @param flightNumberType the flightNumberType to set
	 */
	public void setFlightNumberType(String flightNumberType) {
		this.flightNumberType = flightNumberType;
	}


	
	/**
	 * @return the freightMethod
	 */
	public String getFreightMethod() {
		return freightMethod;
	}


	
	/**
	 * @param freightMethod the freightMethod to set
	 */
	public void setFreightMethod(String freightMethod) {
		this.freightMethod = freightMethod;
	}


}