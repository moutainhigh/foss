package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 空运预付单
 * 
 * @author dp-liqin
 * @date 2012-10-11 下午5:23:00
 */
public class BillAdvancedPaymentEntity extends BaseEntity {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -4669868513402338705L;

	/**
	 * 预付单号
	 */
	private String advancesNo;

	/**
	 * 币种
	 */
	private String currencyCode;

	/**
	 * 金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal amount;

	/**
	 * 已核销金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal verifyAmount;

	/**
	 * 未核销金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal unverifyAmount;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 申请部门编码
	 */
	private String applyOrgCode;

	/**
	 * 申请部门名称
	 */
	private String applyOrgName;

	/**
	 * 申请部门所属子公司编码
	 */
	private String applyCompanyCode;

	/**
	 * 申请部门所属子公司名称
	 */
	private String applyCompanyName;

	/**
	 * 付款部门编码
	 */
	private String paymentOrgCode;

	/**
	 * 付款部门名称
	 */
	private String paymentOrgName;

	/**
	 * 付款部门所属子公司编码
	 */
	private String paymentCompanyCode;

	/**
	 * 付款部门所属子公司名称
	 */
	private String paymentCompanyName;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 是否红单
	 */
	private String isRedBack;

	/**
	 * 制单人工号
	 */
	private String createUserCode;

	/**
	 * 制单人名称
	 */
	private String createUserName;

	/**
	 * 审核人工号
	 */
	private String auditUserCode;

	/**
	 * 审核人名称
	 */
	private String auditUserName;

	/**
	 * 修改人工号
	 */
	private String modifyUserCode;

	/**
	 * 修改人名称
	 */
	private String modifyUserName;

	/**
	 * 做废人工号
	 */
	private String disableUserCode;

	/**
	 * 做废人名称
	 */
	private String disableUserName;

	/**
	 * 业务日期
	 */
	private Date businessDate;

	/**
	 * 记账日期
	 */
	private Date accountDate;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 做废时间
	 */
	private Date disableTime;

	/**
	 * 单据类型
	 */
	private String billType;

	/**
	 * 是否初始化
	 */
	private String isInit;

	/**
	 * 付款方式
	 */
	private String paymentType;

	/**
	 * 收款人手机号码
	 */
	private String mobilePhone;

	/**
	 * 银行账号
	 */
	private String accountNo;

	/**
	 * 开户人姓名
	 */
	private String payeeName;

	/**
	 * 对公对私标志
	 */
	private String publicPrivateFlag;


	/**
	 * 省份编码
	 */
	private String provinceCode;

	/**
	 * 省份名称
	 */
	private String provinceName;

	/**
	 * 开户人编号
	 */
	private String bankHqCode;

	/**
	 * 开户人姓名
	 */
	private String bankHqName;

	/**
	 * 城市编码
	 */
	private String cityCode;

	/**
	 * 城市姓名
	 */
	private String cityName;

	/**
	 * 开户行支行编码
	 */
	private String bankBranchCode;

	/**
	 * 开户行支行名称
	 */
	private String bankBranchName;

	/**
	 * 发票抬头
	 */
	private String invoiceTitle;

	/**
	 * 最迟汇款时间
	 */
	private Date lastpaymentTime;

	/**
	 * 审批状态
	 */
	private String auditStatus;

	/**
	 * 审批通过时间
	 */
	private Date auditTime;
	/**
	 * 工作流号
	 */
	private String workflowNo;

	/**
	 * 对账单号
	 */
	private String statementBillNo;

	/**
	 * 备注
	 */
	private String notes;

	/**
	 * 版本号
	 */
	private Short versionNo;
	
	/**
	 * 发票标记
	 */
	private String invoiceMark;

	/**
	 * @return advancesNo
	 */
	public String getAdvancesNo() {
		return advancesNo;
	}

	/**
	 * @param advancesNo
	 */
	public void setAdvancesNo(String advancesNo) {
		this.advancesNo = advancesNo;
	}

	/**
	 * @return currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
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
	 * @return verifyAmount
	 */
	public BigDecimal getVerifyAmount() {
		return verifyAmount;
	}

	/**
	 * @param verifyAmount
	 */
	public void setVerifyAmount(BigDecimal verifyAmount) {
		this.verifyAmount = verifyAmount;
	}

	/**
	 * @return unverifyAmount
	 */
	public BigDecimal getUnverifyAmount() {
		return unverifyAmount;
	}

	/**
	 * @param unverifyAmount
	 */
	public void setUnverifyAmount(BigDecimal unverifyAmount) {
		this.unverifyAmount = unverifyAmount;
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
	 * @return applyOrgCode
	 */
	public String getApplyOrgCode() {
		return applyOrgCode;
	}

	/**
	 * @param applyOrgCode
	 */
	public void setApplyOrgCode(String applyOrgCode) {
		this.applyOrgCode = applyOrgCode;
	}

	/**
	 * @return applyOrgName
	 */
	public String getApplyOrgName() {
		return applyOrgName;
	}

	/**
	 * @param applyOrgName
	 */
	public void setApplyOrgName(String applyOrgName) {
		this.applyOrgName = applyOrgName;
	}

	/**
	 * @return applyCompanyCode
	 */
	public String getApplyCompanyCode() {
		return applyCompanyCode;
	}

	/**
	 * @param applyCompanyCode
	 */
	public void setApplyCompanyCode(String applyCompanyCode) {
		this.applyCompanyCode = applyCompanyCode;
	}

	/**
	 * @return applyCompanyName
	 */
	public String getApplyCompanyName() {
		return applyCompanyName;
	}

	/**
	 * @param applyCompanyName
	 */
	public void setApplyCompanyName(String applyCompanyName) {
		this.applyCompanyName = applyCompanyName;
	}

	/**
	 * @return paymentOrgCode
	 */
	public String getPaymentOrgCode() {
		return paymentOrgCode;
	}

	/**
	 * @param paymentOrgCode
	 */
	public void setPaymentOrgCode(String paymentOrgCode) {
		this.paymentOrgCode = paymentOrgCode;
	}

	/**
	 * @return paymentOrgName
	 */
	public String getPaymentOrgName() {
		return paymentOrgName;
	}

	/**
	 * @param paymentOrgName
	 */
	public void setPaymentOrgName(String paymentOrgName) {
		this.paymentOrgName = paymentOrgName;
	}

	/**
	 * @return paymentCompanyCode
	 */
	public String getPaymentCompanyCode() {
		return paymentCompanyCode;
	}

	/**
	 * @param paymentCompanyCode
	 */
	public void setPaymentCompanyCode(String paymentCompanyCode) {
		this.paymentCompanyCode = paymentCompanyCode;
	}

	/**
	 * @return paymentCompanyName
	 */
	public String getPaymentCompanyName() {
		return paymentCompanyName;
	}

	/**
	 * @param paymentCompanyName
	 */
	public void setPaymentCompanyName(String paymentCompanyName) {
		this.paymentCompanyName = paymentCompanyName;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return isRedBack
	 */
	public String getIsRedBack() {
		return isRedBack;
	}

	/**
	 * @param isRedBack
	 */
	public void setIsRedBack(String isRedBack) {
		this.isRedBack = isRedBack;
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
	 * @return auditUserCode
	 */
	public String getAuditUserCode() {
		return auditUserCode;
	}

	/**
	 * @param auditUserCode
	 */
	public void setAuditUserCode(String auditUserCode) {
		this.auditUserCode = auditUserCode;
	}

	/**
	 * @return auditUserName
	 */
	public String getAuditUserName() {
		return auditUserName;
	}

	/**
	 * @param auditUserName
	 */
	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
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
	 * @return disableUserCode
	 */
	public String getDisableUserCode() {
		return disableUserCode;
	}

	/**
	 * @param disableUserCode
	 */
	public void setDisableUserCode(String disableUserCode) {
		this.disableUserCode = disableUserCode;
	}

	/**
	 * @return disableUserName
	 */
	public String getDisableUserName() {
		return disableUserName;
	}

	/**
	 * @param disableUserName
	 */
	public void setDisableUserName(String disableUserName) {
		this.disableUserName = disableUserName;
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
	 * @return disableTime
	 */
	public Date getDisableTime() {
		return disableTime;
	}

	/**
	 * @param disableTime
	 */
	public void setDisableTime(Date disableTime) {
		this.disableTime = disableTime;
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
	 * @return isInit
	 */
	public String getIsInit() {
		return isInit;
	}

	/**
	 * @param isInit
	 */
	public void setIsInit(String isInit) {
		this.isInit = isInit;
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
	 * @return mobilePhone
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}

	/**
	 * @param mobilePhone
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	/**
	 * @return accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @param accountNo
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * @return payeeName
	 */
	public String getPayeeName() {
		return payeeName;
	}

	/**
	 * @param payeeName
	 */
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	

	/**
	 * @return provinceCode
	 */
	public String getProvinceCode() {
		return provinceCode;
	}

	/**
	 * @param provinceCode
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	/**
	 * @return provinceName
	 */
	public String getProvinceName() {
		return provinceName;
	}

	/**
	 * @param provinceName
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	/**
	 * @return bankHqCode
	 */
	public String getBankHqCode() {
		return bankHqCode;
	}

	/**
	 * @param bankHqCode
	 */
	public void setBankHqCode(String bankHqCode) {
		this.bankHqCode = bankHqCode;
	}

	/**
	 * @return bankHqName
	 */
	public String getBankHqName() {
		return bankHqName;
	}

	/**
	 * @param bankHqName
	 */
	public void setBankHqName(String bankHqName) {
		this.bankHqName = bankHqName;
	}

	/**
	 * @return cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * @return cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return bankBranchCode
	 */
	public String getBankBranchCode() {
		return bankBranchCode;
	}

	/**
	 * @param bankBranchCode
	 */
	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
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
	 * @return invoiceTitle
	 */
	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	/**
	 * @param invoiceTitle
	 */
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	/**
	 * @return lastpaymentTime
	 */
	public Date getLastpaymentTime() {
		return lastpaymentTime;
	}

	/**
	 * @param lastpaymentTime
	 */
	public void setLastpaymentTime(Date lastpaymentTime) {
		this.lastpaymentTime = lastpaymentTime;
	}

	/**
	 * @return auditStatus
	 */
	public String getAuditStatus() {
		return auditStatus;
	}

	/**
	 * @param auditStatus
	 */
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**
	 *
	 * @return
	 */
	public Date getAuditTime() { return auditTime; }

	/**
	 *
	 * @param auditTime
	 */
	public void setAuditTime(Date auditTime) { this.auditTime = auditTime; }

	/**
	 * @return workflowNo
	 */
	public String getWorkflowNo() {
		return workflowNo;
	}

	/**
	 * @param workflowNo
	 */
	public void setWorkflowNo(String workflowNo) {
		this.workflowNo = workflowNo;
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
	
	/**
	 * @return  publicPrivateFlag
	 */
	public String getPublicPrivateFlag() {
		return publicPrivateFlag;
	}

	
	/**
	 * @param  publicPrivateFlag
	 */
	public void setPublicPrivateFlag(String publicPrivateFlag) {
		this.publicPrivateFlag = publicPrivateFlag;
	}

	/**
	 * @return the invoiceMark
	 */
	public String getInvoiceMark() {
		return invoiceMark;
	}

	/**
	 * @param invoiceMark the invoiceMark to set
	 */
	public void setInvoiceMark(String invoiceMark) {
		this.invoiceMark = invoiceMark;
	}
}
