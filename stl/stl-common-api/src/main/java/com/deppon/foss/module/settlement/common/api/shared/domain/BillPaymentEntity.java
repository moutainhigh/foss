package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 付款单实体类
 * 
 * @author ibm-qiaolifeng
 * @date 2012-10-11 下午5:15:25
 * @since
 * @version
 */
public class BillPaymentEntity extends BaseEntity {

	/**
	 * 实体序列号
	 */
	private static final long serialVersionUID = 2946952331890171925L;

	/**
	 * 付款单号
	 */
	private String paymentNo;

	/**
	 * 币种
	 */
	private String currencyCode;

	/**
	 * 金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal amount;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 工作流号
	 */
	private String workflowNo;

	/**
	 * 汇款状态
	 */
	private String remitStatus;

	/**
	 * 审核状态
	 */
	private String auditStatus;

	/**
	 * 来源单据单号
	 */
	private String sourceBillNo;

	/**
	 * 来源单据类型
	 */
	private String sourceBillType;

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
	 * 付款方式
	 */
	private List<String> paymentTypes;
	/**
	 * 收款人手机号码
	 */
	private String mobilePhone;

	/**
	 * 银行账号
	 */
	private String accountNo;

	/**
	 * 收款人编码
	 */
	private String payeeCode;
	
	/**
	 * 收款人名称
	 */
	private String payeeName;

	/**
	 * 账户类型
	 */
	private String accountType;

	/**
	 * 省份编码
	 */
	private String provinceCode;

	/**
	 * 省份名称
	 */
	private String provinceName;

	/**
	 * 城市编码
	 */
	private String cityCode;

	/**
	 * 城市名称
	 */
	private String cityName;

	/**
	 * 开户行编码
	 */
	private String bankHqCode;

	/**
	 * 开户行名称
	 */
	private String bankHqName;

	/**
	 * 开户行支行名称
	 */
	private String bankBranchName;

	/**
	 * 支行编码（行号）
	 */
	private String bankBranchCode;

	/**
	 * 审核意见
	 */
	private String auditOpinion;

	/**
	 * 作废意见
	 */
	private String disableOpinion;

	/**
	 * 申请工作流号
	 */
	private String applyWorkFlowNo;

	/**
	 * 备注
	 */
	private String notes;

	/**
	 * 版本号
	 */
	private Short versionNo;

	/**
	 * 生成方式
	 */
	private String createType;
	
	/**
	 * 付款批次号
	 */
	private String batchNo;
	
	/**
	 * 工作流类型
	 */
	private String workFlowType;
	
	/**
	 * 发票抬头编码 （需要传给费控系统）
	 */
	private String invoiceHeadCode;
	
	/**
	 * 发票抬头名称
	 */
	private String invoiceHeadName;
	
	/*=============以下字段给临时租车使用，未存到数据库中===================*/
	/**
	 * 费用承担部门
	 */
	private String costDeptCode;
	
	/**
	 * 费用承担部门编码
	 */
	private String costDeptName;
	
	/**
	 * 是否增值税专用发票
	 */
	private Boolean isTaxinvoice;
	/**
	 * 费用类型
	 */
	private String costType;
	
	/**
	 * 增值税发票号码
	 */
	private String vatInvoice;
	
	/**
	 * 是否转报销/转报销
	 */
	private String reimbursement;
	
	/**
	 * 纳税人识别号
	 */
	private String taxpayerNumber;
	/*=============wwb add at 03/10/2016 发票信息============*/
	/**
	 * 不含税金额
	 */
	private BigDecimal taxfreePrice;
	/**
	 * 税额
	 */
	private BigDecimal taxPrice;

	/**
	 * 合伙人到付运费自动付款推送次数
	 */
	private BigDecimal autoSendCount;
	
	/**
	 * 付款部门标杆编码
	 */
	private String paymentOrgUnifiedCode;
	
/*=====保理业务   未存到数据库中@379106-2016-10-15 ============*/
	
	/**
	 * 是否为保理  默认为'N'
	 */
	private String factoring; 
	
	/**
	 * 保理开始日期   FACTOR_BEGIN_TIME
	 */
	private Date factorBeginTime;
	
	/**
	 * 保理结束日期   FACTOR_END_TIME
	 */
	private Date factorEndTime;
	
	/**
	 * 保理回款帐号  FACTOR_ACCOUNT
	 */
	private String factorAccount;
	
	
	/**
	 * 贷款客户编码
	 */
	private String cusCode;
	
	
	/**
	 * @return paymentNo
	 */
	public String getPaymentNo() {
		return paymentNo;
	}

	/**
	 * @param paymentNo
	 */
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
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
	 * @return remitStatus
	 */
	public String getRemitStatus() {
		return remitStatus;
	}

	/**
	 * @param remitStatus
	 */
	public void setRemitStatus(String remitStatus) {
		this.remitStatus = remitStatus;
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
	 * @return accountType
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
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
	 * @return auditOpinion
	 */
	public String getAuditOpinion() {
		return auditOpinion;
	}

	/**
	 * @param auditOpinion
	 */
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	/**
	 * @return disableOpinion
	 */
	public String getDisableOpinion() {
		return disableOpinion;
	}

	/**
	 * @param disableOpinion
	 */
	public void setDisableOpinion(String disableOpinion) {
		this.disableOpinion = disableOpinion;
	}

	/**
	 * @return applyWorkFlowNo
	 */
	public String getApplyWorkFlowNo() {
		return applyWorkFlowNo;
	}

	/**
	 * @param applyWorkFlowNo
	 */
	public void setApplyWorkFlowNo(String applyWorkFlowNo) {
		this.applyWorkFlowNo = applyWorkFlowNo;
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
	 * @return createType
	 */
	public String getCreateType() {
		return createType;
	}

	/**
	 * @param createType
	 */
	public void setCreateType(String createType) {
		this.createType = createType;
	}

	
	/**
	 * @return  the batchNo
	 */
	public String getBatchNo() {
		return batchNo;
	}

	
	/**
	 * @param batchNo the batchNo to set
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * @return workFlowType
	 */
	public String getWorkFlowType() {
		return workFlowType;
	}

	/**
	 * @param workFlowType
	 */
	public void setWorkFlowType(String workFlowType) {
		this.workFlowType = workFlowType;
	}

	
	/**
	 * @return  the invoiceHeadCode
	 */
	public String getInvoiceHeadCode() {
		return invoiceHeadCode;
	}

	
	/**
	 * @param invoiceHeadCode the invoiceHeadCode to set
	 */
	public void setInvoiceHeadCode(String invoiceHeadCode) {
		this.invoiceHeadCode = invoiceHeadCode;
	}

	
	/**
	 * @return  the invoiceHeadName
	 */
	public String getInvoiceHeadName() {
		return invoiceHeadName;
	}

	
	/**
	 * @param invoiceHeadName the invoiceHeadName to set
	 */
	public void setInvoiceHeadName(String invoiceHeadName) {
		this.invoiceHeadName = invoiceHeadName;
	}

	
	/**
	 * @return  the payeeCode
	 */
	public String getPayeeCode() {
		return payeeCode;
	}

	
	/**
	 * @param payeeCode the payeeCode to set
	 */
	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}

	public String getCostDeptCode() {
		return costDeptCode;
	}

	public void setCostDeptCode(String costDeptCode) {
		this.costDeptCode = costDeptCode;
	}

	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	public String getVatInvoice() {
		return vatInvoice;
	}

	public void setVatInvoice(String vatInvoice) {
		this.vatInvoice = vatInvoice;
	}

	public String getReimbursement() {
		return reimbursement;
	}

	public void setReimbursement(String reimbursement) {
		this.reimbursement = reimbursement;
	}

	public String getCostDeptName() {
		return costDeptName;
	}

	public void setCostDeptName(String costDeptName) {
		this.costDeptName = costDeptName;
	}

	public Boolean getIsTaxinvoice() {
		return isTaxinvoice;
	}

	public void setIsTaxinvoice(Boolean isTaxinvoice) {
		this.isTaxinvoice = isTaxinvoice;
	}

	public String getTaxpayerNumber() {
		return taxpayerNumber;
	}

	public void setTaxpayerNumber(String taxpayerNumber) {
		this.taxpayerNumber = taxpayerNumber;
	}

	public BigDecimal getTaxfreePrice() {
		return taxfreePrice;
	}

	public void setTaxfreePrice(BigDecimal taxfreePrice) {
		this.taxfreePrice = taxfreePrice;
	}

	public BigDecimal getTaxPrice() {
		return taxPrice;
	}

	public void setTaxPrice(BigDecimal taxPrice) {
		this.taxPrice = taxPrice;
	}

	public BigDecimal getAutoSendCount() {
		return autoSendCount;
	}

	public void setAutoSendCount(BigDecimal autoSendCount) {
		this.autoSendCount = autoSendCount;
	}

	public String getPaymentOrgUnifiedCode() {
		return paymentOrgUnifiedCode;
	}

	public void setPaymentOrgUnifiedCode(String paymentOrgUnifiedCode) {
		this.paymentOrgUnifiedCode = paymentOrgUnifiedCode;
	}

	public List<String> getPaymentTypes() {
		return paymentTypes;
	}

	public void setPaymentTypes(List<String> paymentTypes) {
		this.paymentTypes = paymentTypes;
	}
	public String getFactoring() {
		return factoring;
	}

	public void setFactoring(String factoring) {
		this.factoring = factoring;
	}

	public Date getFactorBeginTime() {
		return factorBeginTime;
	}

	public void setFactorBeginTime(Date factorBeginTime) {
		this.factorBeginTime = factorBeginTime;
	}

	public Date getFactorEndTime() {
		return factorEndTime;
	}

	public void setFactorEndTime(Date factorEndTime) {
		this.factorEndTime = factorEndTime;
	}

	public String getFactorAccount() {
		return factorAccount;
	}

	public void setFactorAccount(String factorAccount) {
		this.factorAccount = factorAccount;
	}

	public String getCusCode() {
		return cusCode;
	}

	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}
	
}
