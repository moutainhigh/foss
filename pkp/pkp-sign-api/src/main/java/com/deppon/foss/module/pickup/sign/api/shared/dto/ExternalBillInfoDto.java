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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/ExternalBillInfoDto.java
 * 
 * FILE NAME        	: ExternalBillInfoDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/***
 * 偏线外发单Dto
 * @author foss-meiying
 * @date 2012-11-15 下午4:19:30
 * @since
 * @version
 */
public class ExternalBillInfoDto implements Serializable{
	private static final long serialVersionUID = 3449742091620490225L;
	/***
	 * 运单号
	 */
	private String waybillNo;
	/***
	 * 外发单号
	 */
	private String externalBillNo;
	/***
	 * 外发员
	 */
	private String externalUserName;

	/**
	 * 外发代理费
	 */
	private BigDecimal externalAgencyFee;

	/**
	 * 付送货费
	 */
	private BigDecimal deliveryFee;
	/**
	 * 外发成本总额
	 */
	private BigDecimal costAmount;
	/**
	 * 实收代理费
	 */
	private BigDecimal receiveAgencyFee;
	/**
	 * 实付代理费
	 */
	private BigDecimal payAgencyFee;

	/***
	 * 自动核销申请
	 */
	private String isWriteOff;
	/***
	 * 备注
	 */
	private String notes;
	/***
	 * 中转外发
	 */
	private String transferExternal;
	/**
	 * 币种
	 */
	private String currencyCode;
	/**
	 * 付款方式PAID_METHOD
	 */
	private String paidMethod;
	/**
	 * 到付金额
	 */
	private BigDecimal toPayAmount;

	/**
	 * 到达网点电话
	 */
	private String contactPhone;

	/**
	 * 到达网点地址
	 */
	private String address;
	
	/**
	 * 外发部门
	 */
	private String externalOrgName;
	
	/**
	 * 录入人
	 */
	private String registerUser;
	/**
	 * 录入日期
	 */
	private Date registerTime;
	
	/**
	 * 代理网点名称
	 */
	private String agentDeptName;
	
	/**
	 * 外发代理公司名称
	 */
	private String agentCompanyName;
	
	/*================= 快递增加的属性 ================*/
	/**
	 * 外发代理公司编码
	 */
	private String agentCompanyCode;
	
	/**
	 * 外发代理网点名称
	 */
	private String agentDeptCode;

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo the waybillNo to see
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the externalBillNo
	 */
	public String getExternalBillNo() {
		return externalBillNo;
	}

	/**
	 * @param externalBillNo the externalBillNo to see
	 */
	public void setExternalBillNo(String externalBillNo) {
		this.externalBillNo = externalBillNo;
	}

	/**
	 * @return the externalUserName
	 */
	public String getExternalUserName() {
		return externalUserName;
	}

	/**
	 * @param externalUserName the externalUserName to see
	 */
	public void setExternalUserName(String externalUserName) {
		this.externalUserName = externalUserName;
	}

	/**
	 * @return the externalAgencyFee
	 */
	public BigDecimal getExternalAgencyFee() {
		return externalAgencyFee;
	}

	/**
	 * @param externalAgencyFee the externalAgencyFee to see
	 */
	public void setExternalAgencyFee(BigDecimal externalAgencyFee) {
		this.externalAgencyFee = externalAgencyFee;
	}

	/**
	 * @return the deliveryFee
	 */
	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	/**
	 * @param deliveryFee the deliveryFee to see
	 */
	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	/**
	 * @return the costAmount
	 */
	public BigDecimal getCostAmount() {
		return costAmount;
	}

	/**
	 * @param costAmount the costAmount to see
	 */
	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}

	/**
	 * @return the receiveAgencyFee
	 */
	public BigDecimal getReceiveAgencyFee() {
		return receiveAgencyFee;
	}

	/**
	 * @param receiveAgencyFee the receiveAgencyFee to see
	 */
	public void setReceiveAgencyFee(BigDecimal receiveAgencyFee) {
		this.receiveAgencyFee = receiveAgencyFee;
	}

	/**
	 * @return the payAgencyFee
	 */
	public BigDecimal getPayAgencyFee() {
		return payAgencyFee;
	}

	/**
	 * @param payAgencyFee the payAgencyFee to see
	 */
	public void setPayAgencyFee(BigDecimal payAgencyFee) {
		this.payAgencyFee = payAgencyFee;
	}

	/**
	 * @return the isWriteOff
	 */
	public String getIsWriteOff() {
		return isWriteOff;
	}

	/**
	 * @param isWriteOff the isWriteOff to see
	 */
	public void setIsWriteOff(String isWriteOff) {
		this.isWriteOff = isWriteOff;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to see
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the transferExternal
	 */
	public String getTransferExternal() {
		return transferExternal;
	}

	/**
	 * @param transferExternal the transferExternal to see
	 */
	public void setTransferExternal(String transferExternal) {
		this.transferExternal = transferExternal;
	}

	/**
	 * @return the paidMethod
	 */
	public String getPaidMethod() {
		return paidMethod;
	}

	/**
	 * @param paidMethod the paidMethod to see
	 */
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	/**
	 * @return the toPayAmount
	 */
	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	/**
	 * @param toPayAmount the toPayAmount to see
	 */
	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	/**
	 * @return the agentCompanyName
	 */
	public String getAgentCompanyName() {
		return agentCompanyName;
	}

	/**
	 * @param agentCompanyName the agentCompanyName to see
	 */
	public void setAgentCompanyName(String agentCompanyName) {
		this.agentCompanyName = agentCompanyName;
	}

	/**
	 * @return the agentDeptName
	 */
	public String getAgentDeptName() {
		return agentDeptName;
	}

	/**
	 * @param agentDeptName the agentDeptName to see
	 */
	public void setAgentDeptName(String agentDeptName) {
		this.agentDeptName = agentDeptName;
	}

	/**
	 * @return the contactPhone
	 */
	public String getContactPhone() {
		return contactPhone;
	}

	/**
	 * @param contactPhone the contactPhone to see
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to see
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the externalOrgName
	 */
	public String getExternalOrgName() {
		return externalOrgName;
	}

	/**
	 * @param externalOrgName the externalOrgName to see
	 */
	public void setExternalOrgName(String externalOrgName) {
		this.externalOrgName = externalOrgName;
	}

	/**
	 * @return the registerUser
	 */
	public String getRegisterUser() {
		return registerUser;
	}

	/**
	 * @param registerUser the registerUser to see
	 */
	public void setRegisterUser(String registerUser) {
		this.registerUser = registerUser;
	}

	/**
	 * @return the registerTime
	 */
	public Date getRegisterTime() {
		return registerTime;
	}

	/**
	 * @param registerTime the registerTime to see
	 */
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode the currencyCode to see
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getAgentCompanyCode() {
		return agentCompanyCode;
	}
	
	public void setAgentCompanyCode(String agentCompanyCode) {
		this.agentCompanyCode = agentCompanyCode;
	}
	
	public String getAgentDeptCode() {
		return agentDeptCode;
	}
	
	public void setAgentDeptCode(String agentDeptCode) {
		this.agentDeptCode = agentDeptCode;
	}
}