/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class WoodenStatementEntity extends BaseEntity {
	 
	private static final long serialVersionUID = 1L;
	/**
	 * 对账单单号
	 */
	private String statementBillNo;
	/**
	 * 创建部门编码
	 */
	private String createOrgCode;
	/**
	 * 创建部门名称
	 */
	private String createOrgName;
	/**
	 * 公司编码
	 */
	private String companyCode;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 标杆编码
	 */
	private String unifiedCode;
	/**
	 * 客户编码
	 */
	private String customerCode;
	/**
	 * 客户名称
	 */
	private String customerName;
	/**
	 * 单据类型
	 */
	private String billType;
	/**
	 * 发生金额
	 */
	private BigDecimal periodAmount;
	/**
	 * 期间开始日期
	 */
	private Date periodBeginDate;
	/**
	 * 期间结束日期
	 */
	private Date periodEndDate;
	/**
	 * 未核销金额
	 */
	private BigDecimal unpaidAmount;
	/**
	 * 创建人工号
	 */
	private String createUserCode;
	/**
	 * 创建人姓名
	 */
	private String createUserName;
	/**
	 * 业务日期
	 */
	private Date businessDate;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改人工号
	 */
	private String modifyUserCode;
	/**
	 * 修改人姓名
	 */
	private String modifyUserName;
	/**
	 * 确认人工号
	 */
	private String confirmUserCode;
	/**
	 * 确认人姓名
	 */
	private String confirmUserName;
	/**
	 * 确认时间
	 */
	private Date confirmTime;
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	/**
	 * 确认状态
	 */
	private String confirmStatus;
	/**
	 * 备注
	 */
	private String notes;
	/**
	 * 版本号
	 */
	private Short versionNo;

	public String getStatementBillNo() {
		return statementBillNo;
	}

	public void setStatementBillNo(String statementBillNo) {
		this.statementBillNo = statementBillNo;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUnifiedCode() {
		return unifiedCode;
	}

	public void setUnifiedCode(String unifiedCode) {
		this.unifiedCode = unifiedCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public BigDecimal getPeriodAmount() {
		return periodAmount;
	}

	public void setPeriodAmount(BigDecimal periodAmount) {
		this.periodAmount = periodAmount;
	}

	public Date getPeriodBeginDate() {
		return periodBeginDate;
	}

	public void setPeriodBeginDate(Date periodBeginDate) {
		this.periodBeginDate = periodBeginDate;
	}

	public Date getPeriodEndDate() {
		return periodEndDate;
	}

	public void setPeriodEndDate(Date periodEndDate) {
		this.periodEndDate = periodEndDate;
	}

	public BigDecimal getUnpaidAmount() {
		return unpaidAmount;
	}

	public void setUnpaidAmount(BigDecimal unpaidAmount) {
		this.unpaidAmount = unpaidAmount;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getConfirmUserCode() {
		return confirmUserCode;
	}

	public void setConfirmUserCode(String confirmUserCode) {
		this.confirmUserCode = confirmUserCode;
	}

	public String getConfirmUserName() {
		return confirmUserName;
	}

	public void setConfirmUserName(String confirmUserName) {
		this.confirmUserName = confirmUserName;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Short getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Short versionNo) {
		this.versionNo = versionNo;
	}

}
