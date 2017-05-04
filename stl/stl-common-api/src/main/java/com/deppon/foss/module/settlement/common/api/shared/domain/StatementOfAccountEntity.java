package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 对账单(一定期间内多组应收单、应付单、预收单、预付单的统计信息，用于和客户或者代理进行账款核对与结算)
 * 
 * @author dp_zhangjiheng
 * @date 2012-10-11 下午5:03:57
 */
public class StatementOfAccountEntity extends BaseEntity {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -7168405843861679964L;

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
	 * 期初发生金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal beginPeriodAmount;

	/**
	 * 期初应收金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal beginPeriodRecAmount;

	/**
	 * 期初应付金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal beginPeriodPayAmount;

	/**
	 * 期初预收金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal beginPeriodPreAmount;

	/**
	 * 期初预付金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal beginPeriodAdvAmount;

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
	 * 本期预收金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal periodPreAmount;

	/**
	 * 本期预付金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal periodAdvAmount;

	/**
	 * 本期剩余应收金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal periodUnverifyRecAmount;

	/**
	 * 本期剩余应付金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal periodUnverifyPayAmount;

	/**
	 * 本期剩余预收金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal periodUnverifyPreAmount;

	/**
	 * 本期剩余预付金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal periodUnverifyAdvAmount;

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

	private String invoiceStatus;
	
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
	 * @return statementBillNo
	 */
	public String getStatementBillNo() {
		return statementBillNo;
	}

	/**
	 * @param statementBillNo
	 */
	public void setStatementBillNo(String statementBillNo) {
		this.statementBillNo = statementBillNo;
	}

	/**
	 * @return createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * @param createOrgCode
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * @return createOrgName
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}

	/**
	 * @param createOrgName
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	/**
	 * @return companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return unifiedCode
	 */
	public String getUnifiedCode() {
		return unifiedCode;
	}

	/**
	 * @param unifiedCode
	 */
	public void setUnifiedCode(String unifiedCode) {
		this.unifiedCode = unifiedCode;
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
	 * @return billType
	 */
	public String getBillType() {
		return billType;
	}

	/**
	 * @param billType
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}

	/**
	 * @return beginPeriodAmount
	 */
	public BigDecimal getBeginPeriodAmount() {
		return beginPeriodAmount;
	}

	/**
	 * @param beginPeriodAmount
	 */
	public void setBeginPeriodAmount(BigDecimal beginPeriodAmount) {
		this.beginPeriodAmount = beginPeriodAmount;
	}

	/**
	 * @return beginPeriodRecAmount
	 */
	public BigDecimal getBeginPeriodRecAmount() {
		return beginPeriodRecAmount;
	}

	/**
	 * @param beginPeriodRecAmount
	 */
	public void setBeginPeriodRecAmount(BigDecimal beginPeriodRecAmount) {
		this.beginPeriodRecAmount = beginPeriodRecAmount;
	}

	/**
	 * @return beginPeriodPayAmount
	 */
	public BigDecimal getBeginPeriodPayAmount() {
		return beginPeriodPayAmount;
	}

	/**
	 * @param beginPeriodPayAmount
	 */
	public void setBeginPeriodPayAmount(BigDecimal beginPeriodPayAmount) {
		this.beginPeriodPayAmount = beginPeriodPayAmount;
	}

	/**
	 * @return beginPeriodPreAmount
	 */
	public BigDecimal getBeginPeriodPreAmount() {
		return beginPeriodPreAmount;
	}

	/**
	 * @param beginPeriodPreAmount
	 */
	public void setBeginPeriodPreAmount(BigDecimal beginPeriodPreAmount) {
		this.beginPeriodPreAmount = beginPeriodPreAmount;
	}

	/**
	 * @return beginPeriodAdvAmount
	 */
	public BigDecimal getBeginPeriodAdvAmount() {
		return beginPeriodAdvAmount;
	}

	/**
	 * @param beginPeriodAdvAmount
	 */
	public void setBeginPeriodAdvAmount(BigDecimal beginPeriodAdvAmount) {
		this.beginPeriodAdvAmount = beginPeriodAdvAmount;
	}

	/**
	 * @return periodAmount
	 */
	public BigDecimal getPeriodAmount() {
		return periodAmount;
	}

	/**
	 * @param periodAmount
	 */
	public void setPeriodAmount(BigDecimal periodAmount) {
		this.periodAmount = periodAmount;
	}

	/**
	 * @return periodRecAmount
	 */
	public BigDecimal getPeriodRecAmount() {
		return periodRecAmount;
	}

	/**
	 * @param periodRecAmount
	 */
	public void setPeriodRecAmount(BigDecimal periodRecAmount) {
		this.periodRecAmount = periodRecAmount;
	}

	/**
	 * @return periodPayAmount
	 */
	public BigDecimal getPeriodPayAmount() {
		return periodPayAmount;
	}

	/**
	 * @param periodPayAmount
	 */
	public void setPeriodPayAmount(BigDecimal periodPayAmount) {
		this.periodPayAmount = periodPayAmount;
	}

	/**
	 * @return periodPreAmount
	 */
	public BigDecimal getPeriodPreAmount() {
		return periodPreAmount;
	}

	/**
	 * @param periodPreAmount
	 */
	public void setPeriodPreAmount(BigDecimal periodPreAmount) {
		this.periodPreAmount = periodPreAmount;
	}

	/**
	 * @return periodAdvAmount
	 */
	public BigDecimal getPeriodAdvAmount() {
		return periodAdvAmount;
	}

	/**
	 * @param periodAdvAmount
	 */
	public void setPeriodAdvAmount(BigDecimal periodAdvAmount) {
		this.periodAdvAmount = periodAdvAmount;
	}

	/**
	 * @return periodUnverifyRecAmount
	 */
	public BigDecimal getPeriodUnverifyRecAmount() {
		return periodUnverifyRecAmount;
	}

	/**
	 * @param periodUnverifyRecAmount
	 */
	public void setPeriodUnverifyRecAmount(BigDecimal periodUnverifyRecAmount) {
		this.periodUnverifyRecAmount = periodUnverifyRecAmount;
	}

	/**
	 * @return periodUnverifyPayAmount
	 */
	public BigDecimal getPeriodUnverifyPayAmount() {
		return periodUnverifyPayAmount;
	}

	/**
	 * @param periodUnverifyPayAmount
	 */
	public void setPeriodUnverifyPayAmount(BigDecimal periodUnverifyPayAmount) {
		this.periodUnverifyPayAmount = periodUnverifyPayAmount;
	}

	/**
	 * @return periodUnverifyPreAmount
	 */
	public BigDecimal getPeriodUnverifyPreAmount() {
		return periodUnverifyPreAmount;
	}

	/**
	 * @param periodUnverifyPreAmount
	 */
	public void setPeriodUnverifyPreAmount(BigDecimal periodUnverifyPreAmount) {
		this.periodUnverifyPreAmount = periodUnverifyPreAmount;
	}

	/**
	 * @return periodUnverifyAdvAmount
	 */
	public BigDecimal getPeriodUnverifyAdvAmount() {
		return periodUnverifyAdvAmount;
	}

	/**
	 * @param periodUnverifyAdvAmount
	 */
	public void setPeriodUnverifyAdvAmount(BigDecimal periodUnverifyAdvAmount) {
		this.periodUnverifyAdvAmount = periodUnverifyAdvAmount;
	}

	/**
	 * @return periodBeginDate
	 */
	public Date getPeriodBeginDate() {
		return periodBeginDate;
	}

	/**
	 * @param periodBeginDate
	 */
	public void setPeriodBeginDate(Date periodBeginDate) {
		this.periodBeginDate = periodBeginDate;
	}

	/**
	 * @return periodEndDate
	 */
	public Date getPeriodEndDate() {
		return periodEndDate;
	}

	/**
	 * @param periodEndDate
	 */
	public void setPeriodEndDate(Date periodEndDate) {
		this.periodEndDate = periodEndDate;
	}

	/**
	 * @return unpaidAmount
	 */
	public BigDecimal getUnpaidAmount() {
		return unpaidAmount;
	}

	/**
	 * @param unpaidAmount
	 */
	public void setUnpaidAmount(BigDecimal unpaidAmount) {
		this.unpaidAmount = unpaidAmount;
	}

	/**
	 * @return settleNum
	 */
	public Short getSettleNum() {
		return settleNum;
	}

	/**
	 * @param settleNum
	 */
	public void setSettleNum(Short settleNum) {
		this.settleNum = settleNum;
	}

	/**
	 * @return createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * @param createUserCode
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * @return createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @param createUserName
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
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
	 * @return modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * @param modifyUserCode
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * @return modifyUserName
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**
	 * @param modifyUserName
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	/**
	 * @return confirmUserCode
	 */
	public String getConfirmUserCode() {
		return confirmUserCode;
	}

	/**
	 * @param confirmUserCode
	 */
	public void setConfirmUserCode(String confirmUserCode) {
		this.confirmUserCode = confirmUserCode;
	}

	/**
	 * @return confirmUserName
	 */
	public String getConfirmUserName() {
		return confirmUserName;
	}

	/**
	 * @param confirmUserName
	 */
	public void setConfirmUserName(String confirmUserName) {
		this.confirmUserName = confirmUserName;
	}

	/**
	 * @return confirmTime
	 */
	public Date getConfirmTime() {
		return confirmTime;
	}

	/**
	 * @param confirmTime
	 */
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	/**
	 * @return modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return unlockTime
	 */
	public Date getUnlockTime() {
		return unlockTime;
	}

	/**
	 * @param unlockTime
	 */
	public void setUnlockTime(Date unlockTime) {
		this.unlockTime = unlockTime;
	}

	/**
	 * @return confirmStatus
	 */
	public String getConfirmStatus() {
		return confirmStatus;
	}

	/**
	 * @param confirmStatus
	 */
	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	/**
	 * @return companyAccountBankNo
	 */
	public String getCompanyAccountBankNo() {
		return companyAccountBankNo;
	}

	/**
	 * @param companyAccountBankNo
	 */
	public void setCompanyAccountBankNo(String companyAccountBankNo) {
		this.companyAccountBankNo = companyAccountBankNo;
	}

	/**
	 * @return accountUserName
	 */
	public String getAccountUserName() {
		return accountUserName;
	}

	/**
	 * @param accountUserName
	 */
	public void setAccountUserName(String accountUserName) {
		this.accountUserName = accountUserName;
	}

	/**
	 * @return bankBranchName
	 */
	public String getBankBranchName() {
		return bankBranchName;
	}

	/**
	 * @param bankBranchName
	 */
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	/**
	 * @return notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return versionNo
	 */
	public Short getVersionNo() {
		return versionNo;
	}

	/**
	 * @param versionNo
	 */
	public void setVersionNo(Short versionNo) {
		this.versionNo = versionNo;
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

	public String getUnifiedSettlement() {
		return unifiedSettlement;
	}

	public void setUnifiedSettlement(String unifiedSettlement) {
		this.unifiedSettlement = unifiedSettlement;
	}

}
