package com.deppon.foss.module.settlement.writeoff.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class CustomerStatementEntity extends BaseEntity {
	 
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private String id;

	/**
	 * 对账单号
	 */
	private String statementBillNo;

	/**
	 * 制作部门编码
	 */
	private String createOrgCode;

	/**
	 * 制作部门名称
	 */
	private String createOrgName;

	/**
	 * 所属子公司编码
	 */
	private String companyCode;

	/**
	 * 所属子公司名称
	 */
	private String companyName;

	/**
	 * 制作部门标杆编码
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
	 * 对账单类型
	 */
	private String billType;

	/**
	 * 本期发生金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal periodAmount;
	
	private BigDecimal paidAmount;

	/**
	 * 本期应收金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal periodRecAmount;

	/**
	 * 本期应付金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal periodPayAmount;

	/**
	 * 本期剩余应收金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal periodUnverifyRecAmount;

	/**
	 * 本期剩余应付金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal periodUnverifyPayAmount;

	/**
	 * 账期开始日期
	 */
	private Date periodBeginDate;

	/**
	 * 账期结束日期
	 */
	private Date periodEndDate;

	/**
	 * 未还金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal unpaidAmount;

	/**
	 * 结账次数
	 */
	private Short settleNum;

	/**
	 * 制单人工号
	 */
	private String createUserCode;

	/**
	 * 制单人名称
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
	 * 修改人名称
	 */
	private String modifyUserName;

	/**
	 * 确认人工号
	 */
	private String confirmUserCode;

	/**
	 * 确认人名称
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
	 * 解锁时间
	 */
	private Date unlockTime;

	/**
	 * 确认状态
	 */
	private String confirmStatus;

	/**
	 * 子公司账号
	 */
	private String companyAccountBankNo;

	/**
	 * 开户名
	 */
	private String accountUserName;

	/**
	 * 支行名称
	 */
	private String bankBranchName;
	
	/**
	 * 备注
	 */
	private String notes;
	
	/**
	 * 发票申请状态
	 */
	private String invoiceStatus;
	
	/**
	 * 是否申请发票
	 */
	private String applyInvoice;
	
	/**
	 * 版本号
	 */
	private Short versionNo;

	/**
	 * 是否统一结算
	 */
	private String unifiedSettlement;
	
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public BigDecimal getPeriodRecAmount() {
		return periodRecAmount;
	}

	public void setPeriodRecAmount(BigDecimal periodRecAmount) {
		this.periodRecAmount = periodRecAmount;
	}

	public BigDecimal getPeriodPayAmount() {
		return periodPayAmount;
	}

	public void setPeriodPayAmount(BigDecimal periodPayAmount) {
		this.periodPayAmount = periodPayAmount;
	}

	public BigDecimal getPeriodUnverifyRecAmount() {
		return periodUnverifyRecAmount;
	}

	public void setPeriodUnverifyRecAmount(BigDecimal periodUnverifyRecAmount) {
		this.periodUnverifyRecAmount = periodUnverifyRecAmount;
	}

	public BigDecimal getPeriodUnverifyPayAmount() {
		return periodUnverifyPayAmount;
	}

	public void setPeriodUnverifyPayAmount(BigDecimal periodUnverifyPayAmount) {
		this.periodUnverifyPayAmount = periodUnverifyPayAmount;
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

	public Short getSettleNum() {
		return settleNum;
	}

	public void setSettleNum(Short settleNum) {
		this.settleNum = settleNum;
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

	public Date getUnlockTime() {
		return unlockTime;
	}

	public void setUnlockTime(Date unlockTime) {
		this.unlockTime = unlockTime;
	}

	public String getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public String getCompanyAccountBankNo() {
		return companyAccountBankNo;
	}

	public void setCompanyAccountBankNo(String companyAccountBankNo) {
		this.companyAccountBankNo = companyAccountBankNo;
	}

	public String getAccountUserName() {
		return accountUserName;
	}

	public void setAccountUserName(String accountUserName) {
		this.accountUserName = accountUserName;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getApplyInvoice() {
		return applyInvoice;
	}

	public void setApplyInvoice(String applyInvoice) {
		this.applyInvoice = applyInvoice;
	}

	public Short getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Short versionNo) {
		this.versionNo = versionNo;
	}

	public String getUnifiedSettlement() {
		return unifiedSettlement;
	}

	public void setUnifiedSettlement(String unifiedSettlement) {
		this.unifiedSettlement = unifiedSettlement;
	}

}
