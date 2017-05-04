/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-consumer-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/api/shared/domain/InvoiceEntity.java
 * 
 * FILE NAME        	: InvoiceEntity.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 标记发票已开记录
 * 
 * @author dp-wujiangtao
 * @date 2012-10-11 下午5:53:34
 * @since
 * @version
 */
public class InvoiceEntity extends BaseEntity {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -8641837192987408053L;

	/**
	 * 来源单号
	 */
	private String sourceBillNo;

	/**
	 * 来源单据类型
	 */
	private String sourceBillType;

	/**
	 * 发票类型
	 */
	private String invoiceType;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 总金额 
	 * BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal totalAmount;

	/**
	 * 已开金额 
	 * BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal alreadyOpenAmount;

	/**
	 * 业务日期
	 */
	private Date businessDate;

	/**
	 * 申请人名称
	 */
	private String applyUserName;

	/**
	 * 申请人编码
	 */
	private String applyUserCode;

	/**
	 * 部门编码
	 */
	private String orgCode;
	
	/**
	 * 部门名称
	 */
	private String orgName;
	
	/**
	 * 是否有效
	 */
	private String active;
	
	/**
	 * 币种
	 */
	private String currencyCode;
	
	/**
	 * 版本号
	 */
	private Short versionNo;


	/**
	 * @return sourceBillNo
	 */
	public String getSourceBillNo() {
		return sourceBillNo;
	}

	/**
	 * @param  sourceBillNo  
	 */
	public void setSourceBillNo(String sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
	}

	/**
	 * @return sourceBillType
	 */
	public String getSourceBillType() {
		return sourceBillType;
	}

	/**
	 * @param  sourceBillType  
	 */
	public void setSourceBillType(String sourceBillType) {
		this.sourceBillType = sourceBillType;
	}

	/**
	 * @return invoiceType
	 */
	public String getInvoiceType() {
		return invoiceType;
	}

	/**
	 * @param  invoiceType  
	 */
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	/**
	 * @return customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param  customerCode  
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param  customerName  
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param  totalAmount  
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return alreadyOpenAmount
	 */
	public BigDecimal getAlreadyOpenAmount() {
		return alreadyOpenAmount;
	}

	/**
	 * @param  alreadyOpenAmount  
	 */
	public void setAlreadyOpenAmount(BigDecimal alreadyOpenAmount) {
		this.alreadyOpenAmount = alreadyOpenAmount;
	}

	/**
	 * @return businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param  businessDate  
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * @return applyUserName
	 */
	public String getApplyUserName() {
		return applyUserName;
	}

	/**
	 * @param  applyUserName  
	 */
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	/**
	 * @return applyUserCode
	 */
	public String getApplyUserCode() {
		return applyUserCode;
	}

	/**
	 * @param  applyUserCode  
	 */
	public void setApplyUserCode(String applyUserCode) {
		this.applyUserCode = applyUserCode;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param  active  
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param  currencyCode  
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return versionNo
	 */
	public Short getVersionNo() {
		return versionNo;
	}

	/**
	 * @param  versionNo  
	 */
	public void setVersionNo(Short versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * @return orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param  orgCode  
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param  orgName  
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
}
