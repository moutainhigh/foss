/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-partialline-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 落地配外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/api/shared/domain/LdpExternalBillEntity.java
 * 
 *  FILE NAME     :LdpExternalBillEntity.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.partialline.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 落地配外发单实体
 * 
 * @author liuzhaowei
 * @date 2013-07-16 上午9:18:36
 */
public class LdpExternalBillEntity extends BaseEntity {

	private static final long serialVersionUID = -8102274338009581810L;
	
	/**
	 * id
	 * 
	 */
	private String id;
	/**
	 * 运单号
	 * 
	 */
	private String waybillNo;
	
	/**
	 * 交接单号
	 */
	private String handoverNo;

	/**
	 * 外发单号
	 * 
	 */
	private String externalBillNo;

	/**
	 * 外发员工号
	 * 
	 */
	private String externalUserCode;

	/**
	 * 外发员姓名
	 * 
	 */
	private String externalUserName;
	
	/**
	 * 外发部门编号
	 * 
	 */
	private String externalOrgCode;
	
	/**
	 * 财务通过自己的所属的外场的权限获取但前帐号的所有的外场编码,并且这边要注意不能大于1000
	 * 
	 */
	private List<String> externalOrgCodeList;

	/**
	 * 外发部门名称
	 * 
	 */
	private String externalOrgName;
	
	/**
	 * 落地配公司编号
	 * 
	 */
	private String agentCompanyCode;

	/**
	 * 落地配公司名称
	 * 
	 */
	private String agentCompanyName;
	
	/**
	 * 落地配公司网点编号
	 * 
	 */
	private String agentOrgCode;

	/**
	 * 落地配公司网点名称
	 * 
	 */
	private String agentOrgName;
	
	/**
	 * 返单类型：原件返回-ORIGINAL
	 * 
	 */
	private String returnType;
	
	/**
	 * 外发运费（德邦付给代理）
	 * 
	 */
	private BigDecimal freightFee;
	
	/**
	 * 代收货款手续费
	 * 
	 */
	private BigDecimal codAgencyFee;
	
	/**
	 * 到付手续费
	 */
	private BigDecimal toPayFee;

	/**
	 * 到付金额
	 * 
	 */
	private BigDecimal payDpFee;
	
	/**
	 * 外发保价费
	 * 
	 */
	private BigDecimal externalInsuranceFee;
	
	/**
	 * 备注
	 * 
	 */
	private String notes;  
	
	/**
	 * 审核状态
	 * 
	 */
	private String auditStatus;  
	
	/**
	 * 录入日期
	 * 
	 */
	private Date registerTime;
	
	/**
	 * 修改日期
	 * 
	 */
	private Date modifyTime;
	
	/**
	 * 修改人编码
	 * 
	 */
	private String modifyUserCode;
	
	/**
	 * 修改人
	 * 
	 */
	private String modifyUserName;
	
	/**
	 * 是否中转外发
	 */
	private String transferExternal;
	
	/**
	 * 发送状态
	 */
	private String sendStatus;
	
	/**
	 * 币制
	 */
	private String currencyCode;
	
	/**
	 * 代收货款
	 * 
	 */
	private BigDecimal codAmount;
	
	/**
	 * 应收费用
	 * 
	 */
	private BigDecimal billReceiveable;
	
	/**
	 * 应付费用
	 * 
	 */
	private BigDecimal billPayable;

	/**
	 * 快递代理返货费
	 * 
	 */
	private BigDecimal agencyReturnFee;
	/**
	 * 代理单号
	 * 
	 */
	private String agencyWaybillNo;
	/**
	 * 流水号
	 * 
	 */
	private String serialNo;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getHandoverNo() {
		return handoverNo;
	}

	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

	public String getExternalBillNo() {
		return externalBillNo;
	}

	public void setExternalBillNo(String externalBillNo) {
		this.externalBillNo = externalBillNo;
	}

	public String getExternalUserCode() {
		return externalUserCode;
	}

	public void setExternalUserCode(String externalUserCode) {
		this.externalUserCode = externalUserCode;
	}

	public String getExternalUserName() {
		return externalUserName;
	}

	public void setExternalUserName(String externalUserName) {
		this.externalUserName = externalUserName;
	}

	public String getExternalOrgCode() {
		return externalOrgCode;
	}

	public void setExternalOrgCode(String externalOrgCode) {
		this.externalOrgCode = externalOrgCode;
	}

	public String getExternalOrgName() {
		return externalOrgName;
	}

	public void setExternalOrgName(String externalOrgName) {
		this.externalOrgName = externalOrgName;
	}

	public String getAgentCompanyCode() {
		return agentCompanyCode;
	}

	public void setAgentCompanyCode(String agentCompanyCode) {
		this.agentCompanyCode = agentCompanyCode;
	}

	public String getAgentCompanyName() {
		return agentCompanyName;
	}

	public void setAgentCompanyName(String agentCompanyName) {
		this.agentCompanyName = agentCompanyName;
	}

	public String getAgentOrgCode() {
		return agentOrgCode;
	}

	public void setAgentOrgCode(String agentOrgCode) {
		this.agentOrgCode = agentOrgCode;
	}

	public String getAgentOrgName() {
		return agentOrgName;
	}

	public void setAgentOrgName(String agentOrgName) {
		this.agentOrgName = agentOrgName;
	}

	public BigDecimal getFreightFee() {
		return freightFee;
	}

	public void setFreightFee(BigDecimal freightFee) {
		this.freightFee = freightFee;
	}

	public BigDecimal getCodAgencyFee() {
		return codAgencyFee;
	}

	public void setCodAgencyFee(BigDecimal codAgencyFee) {
		this.codAgencyFee = codAgencyFee;
	}

	public BigDecimal getPayDpFee() {
		return payDpFee;
	}

	public void setPayDpFee(BigDecimal payDpFee) {
		this.payDpFee = payDpFee;
	}

	public BigDecimal getExternalInsuranceFee() {
		return externalInsuranceFee;
	}

	public void setExternalInsuranceFee(BigDecimal externalInsuranceFee) {
		this.externalInsuranceFee = externalInsuranceFee;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getTransferExternal() {
		return transferExternal;
	}

	public void setTransferExternal(String transferExternal) {
		this.transferExternal = transferExternal;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	public BigDecimal getBillReceiveable() {
		return billReceiveable;
	}

	public void setBillReceiveable(BigDecimal billReceiveable) {
		this.billReceiveable = billReceiveable;
	}

	public BigDecimal getBillPayable() {
		return billPayable;
	}

	public void setBillPayable(BigDecimal billPayable) {
		this.billPayable = billPayable;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public List<String> getExternalOrgCodeList() {
		return externalOrgCodeList;
	}

	public void setExternalOrgCodeList(List<String> externalOrgCodeList) {
		this.externalOrgCodeList = externalOrgCodeList;
	}

	public BigDecimal getAgencyReturnFee() {
		return agencyReturnFee;
	}

	public void setAgencyReturnFee(BigDecimal agencyReturnFee) {
		this.agencyReturnFee = agencyReturnFee;
	}

	public String getAgencyWaybillNo() {
		return agencyWaybillNo;
	}

	public void setAgencyWaybillNo(String agencyWaybillNo) {
		this.agencyWaybillNo = agencyWaybillNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public BigDecimal getToPayFee() {
		return toPayFee;
	}

	public void setToPayFee(BigDecimal toPayFee) {
		this.toPayFee = toPayFee;
	}
	
	
}
	