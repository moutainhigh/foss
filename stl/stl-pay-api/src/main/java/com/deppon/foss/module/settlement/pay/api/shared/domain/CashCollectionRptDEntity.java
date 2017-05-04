/*
 * PROJECT NAME: stl-pay-api
 * PACKAGE NAME: com.deppon.foss.module.
 * 				settlement.pay.api.shared.domain
 * FILE    NAME: CashCollectionRptDEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/* 
 * @author ibm-pengzhen
 * @date 2012-10-10 下午12:00:42
 * @since
 * @version
 */
public class CashCollectionRptDEntity extends BaseEntity {

	/**
	 * 实体序列号
	 */
	private static final long serialVersionUID = -7814035853062336917L;

	/**
	 * id
	 */
	private String id;

	/**
	 * 报表编码
	 */
	private String reportNo;

	/**
	 * 来源单据单号
	 */
	private String sourceBillNo;

	/**
	 * 来源单据类型
	 */
	private String sourceBillType;

	/**
	 * 支付方式
	 */
	private String paymentType;

	/**
	 * 业务日期
	 */
	private Date businessDate;

	/**
	 * 记账日期
	 */
	private Date accountDate;

	/**
	 * 收款部门编码
	 */
	private String collectionOrgCode;

	/**
	 * 收款部门名称
	 */
	private String collectionOrgName;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal amount;

	/**
	 * 未缴款 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal overdueAmount;

	/**
	 * 单据状态
	 */
	private String status;

	/**
	 * 已缴款 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal paidAmount;

	/**
	 * 营业款金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal clerksAmount;

	/**
	 * 非营业款金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal unclerksAmount;

	/**
	 * 预收款金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal precollectedAmount;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 收入部门名称
	 */
	private String generatingOrgName;

	/**
	 * 收入部门编码
	 */
	private String generatingOrgCode;

	/**
	 * 收银确认时间
	 */
	private Date cashConfirmTime;

	/**
	 * 收银确认人工号
	 */
	private String cashConfirmUserCode;

	/**
	 * 收银确认人名称
	 */
	private String cashConfirmUserName;
	
	
	/**
	 * 客户类型
	 */
	private String customerType;
	
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	
	/**
	 * 版本号
	 */
	private String versionNo;
	
	
	/**
	 * @return the versionNo
	 */
	public String getVersionNo() {
		return versionNo;
	}

	/**
	 * @param versionNo the versionNo to set
	 */
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return reportNo
	 */
	public String getReportNo() {
		return reportNo;
	}

	/**
	 * @param reportNo
	 */
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	/**
	 * @return sourceBillNo
	 */
	public String getSourceBillNo() {
		return sourceBillNo;
	}

	/**
	 * @param sourceBillNo
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
	 * @param sourceBillType
	 */
	public void setSourceBillType(String sourceBillType) {
		this.sourceBillType = sourceBillType;
	}

	/**
	 * @return paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param businessDate
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * @return accountDate
	 */
	public Date getAccountDate() {
		return accountDate;
	}

	/**
	 * @param accountDate
	 */
	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	/**
	 * @return collectionOrgCode
	 */
	public String getCollectionOrgCode() {
		return collectionOrgCode;
	}

	/**
	 * @param collectionOrgCode
	 */
	public void setCollectionOrgCode(String collectionOrgCode) {
		this.collectionOrgCode = collectionOrgCode;
	}

	/**
	 * @return collectionOrgName
	 */
	public String getCollectionOrgName() {
		return collectionOrgName;
	}

	/**
	 * @param collectionOrgName
	 */
	public void setCollectionOrgName(String collectionOrgName) {
		this.collectionOrgName = collectionOrgName;
	}

	/**
	 * @return customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode
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
	 * @param customerName
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return overdueAmount
	 */
	public BigDecimal getOverdueAmount() {
		return overdueAmount;
	}

	/**
	 * @param overdueAmount
	 */
	public void setOverdueAmount(BigDecimal overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return paidAmount
	 */
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	/**
	 * @param paidAmount
	 */
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	/**
	 * @return clerksAmount
	 */
	public BigDecimal getClerksAmount() {
		return clerksAmount;
	}

	/**
	 * @param clerksAmount
	 */
	public void setClerksAmount(BigDecimal clerksAmount) {
		this.clerksAmount = clerksAmount;
	}

	/**
	 * @return unclerksAmount
	 */
	public BigDecimal getUnclerksAmount() {
		return unclerksAmount;
	}

	/**
	 * @param unclerksAmount
	 */
	public void setUnclerksAmount(BigDecimal unclerksAmount) {
		this.unclerksAmount = unclerksAmount;
	}

	/**
	 * @return precollectedAmount
	 */
	public BigDecimal getPrecollectedAmount() {
		return precollectedAmount;
	}

	/**
	 * @param precollectedAmount
	 */
	public void setPrecollectedAmount(BigDecimal precollectedAmount) {
		this.precollectedAmount = precollectedAmount;
	}

	/**
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return generatingOrgName
	 */
	public String getGeneratingOrgName() {
		return generatingOrgName;
	}

	/**
	 * @param generatingOrgName
	 */
	public void setGeneratingOrgName(String generatingOrgName) {
		this.generatingOrgName = generatingOrgName;
	}

	/**
	 * @return generatingOrgCode
	 */
	public String getGeneratingOrgCode() {
		return generatingOrgCode;
	}

	/**
	 * @param generatingOrgCode
	 */
	public void setGeneratingOrgCode(String generatingOrgCode) {
		this.generatingOrgCode = generatingOrgCode;
	}

	/**
	 * @return cashConfirmTime
	 */
	public Date getCashConfirmTime() {
		return cashConfirmTime;
	}

	/**
	 * @param cashConfirmTime
	 */
	public void setCashConfirmTime(Date cashConfirmTime) {
		this.cashConfirmTime = cashConfirmTime;
	}

	/**
	 * @return cashConfirmUserCode
	 */
	public String getCashConfirmUserCode() {
		return cashConfirmUserCode;
	}

	/**
	 * @param cashConfirmUserCode
	 */
	public void setCashConfirmUserCode(String cashConfirmUserCode) {
		this.cashConfirmUserCode = cashConfirmUserCode;
	}

	/**
	 * @return cashConfirmUserName
	 */
	public String getCashConfirmUserName() {
		return cashConfirmUserName;
	}

	/**
	 * @param cashConfirmUserName
	 */
	public void setCashConfirmUserName(String cashConfirmUserName) {
		this.cashConfirmUserName = cashConfirmUserName;
	}

	/**
	 * Get
	 * @return customerType
	 */
	
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * Set
	 * @param customerType
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * Get
	 * @return waybillNo
	 */
	
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * Set
	 * @param waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * Get
	 * @return modifyTime
	 */
	
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * Set
	 * @param modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}