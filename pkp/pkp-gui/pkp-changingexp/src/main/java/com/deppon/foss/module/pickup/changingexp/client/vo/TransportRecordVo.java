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
 * PROJECT NAME	: pkp-changingexp
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/vo/TransportRecordVo.java
 * 
 * FILE NAME        	: TransportRecordVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.changingexp.client.vo;

import java.math.BigDecimal;

import com.deppon.foss.module.pickup.common.client.vo.BranchVo;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;

/**
 * 
 * 转货、返货记录
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-31 下午2:30:27
 */
public class TransportRecordVo {

	/**
	 * 目的站
	 */
	private String targetOrgCode;
	
	/**
	 * 目的站名称
	 */
	private String targetOrgName;

	/**
	 * 提货方式
	 */
	private DataDictionaryValueVo receiveMethod;
	
	/**
	 * 预配航班
	 */
	private DataDictionaryValueVo flightNumberType;
	
	/**
	 * 合票方式
	 */
	private DataDictionaryValueVo freightMethod;

	/**
	 * 提货网点
	 */
	private BranchVo customerPickupOrgCode;
	
	/**
	 * 提货网点名称
	 */
	private String customerPickupOrgName;
	
	/**
	 * 航班时间
	 */
	private String flightShift;

	/**
	 * 运输性质
	 */
	private ProductEntityVo productCode;

	/**
	 * 计费类型
	 */
	private DataDictionaryValueVo billingType;

	/**
	 * 费率
	 */
	private BigDecimal unitPrice;
	
	/**
	 * 运费
	 */
	private BigDecimal transportFee;

	
	
	
	
	/**
	 * @return the targetOrgName
	 */
	public String getTargetOrgName() {
		return targetOrgName;
	}


	/**
	 * @param targetOrgName the targetOrgName to set
	 */
	public void setTargetOrgName(String targetOrgName) {
		this.targetOrgName = targetOrgName;
	}


	/**
	 * @return the customerPickupOrgName
	 */
	public String getCustomerPickupOrgName() {
		return customerPickupOrgName;
	}


	/**
	 * @param customerPickupOrgName the customerPickupOrgName to set
	 */
	public void setCustomerPickupOrgName(String customerPickupOrgName) {
		this.customerPickupOrgName = customerPickupOrgName;
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
	public DataDictionaryValueVo getReceiveMethod() {
		return receiveMethod;
	}

	
	/**
	 * @param receiveMethod the receiveMethod to set
	 */
	public void setReceiveMethod(DataDictionaryValueVo receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	
	/**
	 * @return the customerPickupOrgCode
	 */
	public BranchVo getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	
	/**
	 * @param customerPickupOrgCode the customerPickupOrgCode to set
	 */
	public void setCustomerPickupOrgCode(BranchVo customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
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
	 * @return the productCode
	 */
	public ProductEntityVo getProductCode() {
		return productCode;
	}

	
	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(ProductEntityVo productCode) {
		this.productCode = productCode;
	}

	
	/**
	 * @return the billingType
	 */
	public DataDictionaryValueVo getBillingType() {
		return billingType;
	}

	
	/**
	 * @param billingType the billingType to set
	 */
	public void setBillingType(DataDictionaryValueVo billingType) {
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
	public DataDictionaryValueVo getFlightNumberType() {
		return flightNumberType;
	}


	
	/**
	 * @param flightNumberType the flightNumberType to set
	 */
	public void setFlightNumberType(DataDictionaryValueVo flightNumberType) {
		this.flightNumberType = flightNumberType;
	}


	
	/**
	 * @return the freightMethod
	 */
	public DataDictionaryValueVo getFreightMethod() {
		return freightMethod;
	}


	
	/**
	 * @param freightMethod the freightMethod to set
	 */
	public void setFreightMethod(DataDictionaryValueVo freightMethod) {
		this.freightMethod = freightMethod;
	}


}