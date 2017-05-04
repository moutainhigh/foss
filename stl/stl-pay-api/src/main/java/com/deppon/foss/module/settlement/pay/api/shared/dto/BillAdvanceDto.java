package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;

/**
 * 预付单DTO
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-16 下午8:30:15
 */
public class BillAdvanceDto implements Serializable {

	/**
	 * 预付单DTO序列号
	 */
	private static final long serialVersionUID = 8863925702032586045L;
	/**
	 * ID
	 */
	private String id;
	/**
	 * 预付单编号
	 */
	private String advancesNo;
	/**
	 * 金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal amount;
	/**
	 * 是否有效
	 */
	private String active;
	/**
	 * 是否红单
	 */
	private String isRedBack;
	/**
	 * 业务日期
	 */
	private Date businessStartDate;
	/**
	 * 业务结束日期
	 */
	private Date businessEndDate;
	/**
	 * 业务结束日期
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
	 * 开户人姓名
	 */
	private String payeeName;
	/**
	 * 开户人姓名
	 */
	private String payeeCode;
	/**
	 * 省份编码
	 */
	private String provinceCode;
	/**
	 * 省份名称
	 */
	private String provinceName;
	/**
	 * 开户行编码
	 */
	private String bankHqCode;
	/**
	 * 开户行名称
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
	 * 账户类型
	 */
	private String accountType;
	/**
	 * 银行账号
	 */
	private String accountNo;
	/**
	 * 发票编码
	 */
	private String invoiceTitleCode;
	/**
	 * 发票抬头名称
	 */
	private String invoiceTitleName;
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
	 * 工作流号
	 */
	private String workflowNo;
	/**
	 * 版本号
	 */
	private Short versionNo;
	/**
	 * 申请部门编号
	 */
	private String applyOrgCode;
	/**
	 * 客户编号
	 */
	private String customerCode;
	/**
	 * 客户名称
	 */
	private String customerName;
	/**
	 * 预付单列表
	 */
	private List<BillAdvancedPaymentEntity> queryAdvanceList;
	/**
	 * 制单人工号
	 */
	private String createUserCode;
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
	 * 申请部门名称
	 */
	private String applyOrgName;
	/**
	 * 制单人名称
	 */
	private String createUserName;
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
	 * 申请部门所属子公司编码
	 */
	private String applyCompanyCode;
	/**
	 * 备注
	 */
	private String notes;
	/**
	 * 费控返回预付单集合
	 */
	private List<String> advanPayNoList;
	/**
	 * 总条数
	 */
	private Integer countNum;
	/**
	 * 每页大小
	 */
	private Integer pageSize;
	/**
	 * 自定义导出列头
	 */
	private String[] arrayColumns;
	/**
	 * 自定义导出列中文名称
	 */
	private String[] arrayColumnNames;
	/**
	 * 未核销金额
	 */
	private BigDecimal unverifyAmount;
	/**
	 * 付款部门集合
	 */
	private List<String> applyOrgcodeList;

	/**
	 * 职员编号
	 */
	private String empCode;

	/**
	 * Get
	 * @return id
	 */
	
	public String getId() {
		return id;
	}

	/**
	 * Set
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get
	 * @return advancesNo
	 */
	
	public String getAdvancesNo() {
		return advancesNo;
	}

	/**
	 * Set
	 * @param advancesNo
	 */
	public void setAdvancesNo(String advancesNo) {
		this.advancesNo = advancesNo;
	}

	/**
	 * Get
	 * @return amount
	 */
	
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Set
	 * @param amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * Get
	 * @return active
	 */
	
	public String getActive() {
		return active;
	}

	/**
	 * Set
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * Get
	 * @return isRedBack
	 */
	
	public String getIsRedBack() {
		return isRedBack;
	}

	/**
	 * Set
	 * @param isRedBack
	 */
	public void setIsRedBack(String isRedBack) {
		this.isRedBack = isRedBack;
	}

	/**
	 * Get
	 * @return businessStartDate
	 */
	
	public Date getBusinessStartDate() {
		return businessStartDate;
	}

	/**
	 * Set
	 * @param businessStartDate
	 */
	public void setBusinessStartDate(Date businessStartDate) {
		this.businessStartDate = businessStartDate;
	}

	/**
	 * Get
	 * @return businessEndDate
	 */
	
	public Date getBusinessEndDate() {
		return businessEndDate;
	}

	/**
	 * Set
	 * @param businessEndDate
	 */
	public void setBusinessEndDate(Date businessEndDate) {
		this.businessEndDate = businessEndDate;
	}

	/**
	 * Get
	 * @return businessDate
	 */
	
	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * Set
	 * @param businessDate
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * Get
	 * @return accountDate
	 */
	
	public Date getAccountDate() {
		return accountDate;
	}

	/**
	 * Set
	 * @param accountDate
	 */
	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	/**
	 * Get
	 * @return createTime
	 */
	
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Set
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	/**
	 * Get
	 * @return disableTime
	 */
	
	public Date getDisableTime() {
		return disableTime;
	}

	/**
	 * Set
	 * @param disableTime
	 */
	public void setDisableTime(Date disableTime) {
		this.disableTime = disableTime;
	}

	/**
	 * Get
	 * @return billType
	 */
	
	public String getBillType() {
		return billType;
	}

	/**
	 * Set
	 * @param billType
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}

	/**
	 * Get
	 * @return isInit
	 */
	
	public String getIsInit() {
		return isInit;
	}

	/**
	 * Set
	 * @param isInit
	 */
	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}

	/**
	 * Get
	 * @return paymentType
	 */
	
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * Set
	 * @param paymentType
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * Get
	 * @return mobilePhone
	 */
	
	public String getMobilePhone() {
		return mobilePhone;
	}

	/**
	 * Set
	 * @param mobilePhone
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	/**
	 * Get
	 * @return payeeName
	 */
	
	public String getPayeeName() {
		return payeeName;
	}

	/**
	 * Set
	 * @param payeeName
	 */
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	/**
	 * Get
	 * @return payeeCode
	 */
	
	public String getPayeeCode() {
		return payeeCode;
	}

	/**
	 * Set
	 * @param payeeCode
	 */
	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}

	/**
	 * Get
	 * @return provinceCode
	 */
	
	public String getProvinceCode() {
		return provinceCode;
	}

	/**
	 * Set
	 * @param provinceCode
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	/**
	 * Get
	 * @return provinceName
	 */
	
	public String getProvinceName() {
		return provinceName;
	}

	/**
	 * Set
	 * @param provinceName
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	/**
	 * Get
	 * @return bankHqCode
	 */
	
	public String getBankHqCode() {
		return bankHqCode;
	}

	/**
	 * Set
	 * @param bankHqCode
	 */
	public void setBankHqCode(String bankHqCode) {
		this.bankHqCode = bankHqCode;
	}

	/**
	 * Get
	 * @return bankHqName
	 */
	
	public String getBankHqName() {
		return bankHqName;
	}

	/**
	 * Set
	 * @param bankHqName
	 */
	public void setBankHqName(String bankHqName) {
		this.bankHqName = bankHqName;
	}

	/**
	 * Get
	 * @return cityCode
	 */
	
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * Set
	 * @param cityCode
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * Get
	 * @return cityName
	 */
	
	public String getCityName() {
		return cityName;
	}

	/**
	 * Set
	 * @param cityName
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * Get
	 * @return bankBranchCode
	 */
	
	public String getBankBranchCode() {
		return bankBranchCode;
	}

	/**
	 * Set
	 * @param bankBranchCode
	 */
	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	/**
	 * Get
	 * @return bankBranchName
	 */
	
	public String getBankBranchName() {
		return bankBranchName;
	}

	/**
	 * Set
	 * @param bankBranchName
	 */
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	/**
	 * Get
	 * @return accountType
	 */
	
	public String getAccountType() {
		return accountType;
	}

	/**
	 * Set
	 * @param accountType
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * Get
	 * @return accountNo
	 */
	
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * Set
	 * @param accountNo
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * Get
	 * @return invoiceTitleCode
	 */
	
	public String getInvoiceTitleCode() {
		return invoiceTitleCode;
	}

	/**
	 * Set
	 * @param invoiceTitleCode
	 */
	public void setInvoiceTitleCode(String invoiceTitleCode) {
		this.invoiceTitleCode = invoiceTitleCode;
	}

	/**
	 * Get
	 * @return invoiceTitleName
	 */
	
	public String getInvoiceTitleName() {
		return invoiceTitleName;
	}

	/**
	 * Set
	 * @param invoiceTitleName
	 */
	public void setInvoiceTitleName(String invoiceTitleName) {
		this.invoiceTitleName = invoiceTitleName;
	}

	/**
	 * Get
	 * @return invoiceTitle
	 */
	
	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	/**
	 * Set
	 * @param invoiceTitle
	 */
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	/**
	 * Get
	 * @return lastpaymentTime
	 */
	
	public Date getLastpaymentTime() {
		return lastpaymentTime;
	}

	/**
	 * Set
	 * @param lastpaymentTime
	 */
	public void setLastpaymentTime(Date lastpaymentTime) {
		this.lastpaymentTime = lastpaymentTime;
	}

	/**
	 * Get
	 * @return auditStatus
	 */
	
	public String getAuditStatus() {
		return auditStatus;
	}

	/**
	 * Set
	 * @param auditStatus
	 */
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**
	 * Get
	 * @return workflowNo
	 */
	
	public String getWorkflowNo() {
		return workflowNo;
	}

	/**
	 * Set
	 * @param workflowNo
	 */
	public void setWorkflowNo(String workflowNo) {
		this.workflowNo = workflowNo;
	}

	/**
	 * Get
	 * @return versionNo
	 */
	
	public Short getVersionNo() {
		return versionNo;
	}

	/**
	 * Set
	 * @param versionNo
	 */
	public void setVersionNo(Short versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * Get
	 * @return applyOrgCode
	 */
	
	public String getApplyOrgCode() {
		return applyOrgCode;
	}

	/**
	 * Set
	 * @param applyOrgCode
	 */
	public void setApplyOrgCode(String applyOrgCode) {
		this.applyOrgCode = applyOrgCode;
	}

	/**
	 * Get
	 * @return customerCode
	 */
	
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * Set
	 * @param customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * Get
	 * @return customerName
	 */
	
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * Set
	 * @param customerName
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * Get
	 * @return queryAdvanceList
	 */
	
	public List<BillAdvancedPaymentEntity> getQueryAdvanceList() {
		return queryAdvanceList;
	}

	/**
	 * Set
	 * @param queryAdvanceList
	 */
	public void setQueryAdvanceList(List<BillAdvancedPaymentEntity> queryAdvanceList) {
		this.queryAdvanceList = queryAdvanceList;
	}

	/**
	 * Get
	 * @return createUserCode
	 */
	
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * Set
	 * @param createUserCode
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * Get
	 * @return auditUserCode
	 */
	
	public String getAuditUserCode() {
		return auditUserCode;
	}

	/**
	 * Set
	 * @param auditUserCode
	 */
	public void setAuditUserCode(String auditUserCode) {
		this.auditUserCode = auditUserCode;
	}

	/**
	 * Get
	 * @return auditUserName
	 */
	
	public String getAuditUserName() {
		return auditUserName;
	}

	/**
	 * Set
	 * @param auditUserName
	 */
	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}

	/**
	 * Get
	 * @return modifyUserCode
	 */
	
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * Set
	 * @param modifyUserCode
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * Get
	 * @return modifyUserName
	 */
	
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**
	 * Set
	 * @param modifyUserName
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	/**
	 * Get
	 * @return applyOrgName
	 */
	
	public String getApplyOrgName() {
		return applyOrgName;
	}

	/**
	 * Set
	 * @param applyOrgName
	 */
	public void setApplyOrgName(String applyOrgName) {
		this.applyOrgName = applyOrgName;
	}

	/**
	 * Get
	 * @return createUserName
	 */
	
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * Set
	 * @param createUserName
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * Get
	 * @return applyCompanyName
	 */
	
	public String getApplyCompanyName() {
		return applyCompanyName;
	}

	/**
	 * Set
	 * @param applyCompanyName
	 */
	public void setApplyCompanyName(String applyCompanyName) {
		this.applyCompanyName = applyCompanyName;
	}

	/**
	 * Get
	 * @return paymentOrgCode
	 */
	
	public String getPaymentOrgCode() {
		return paymentOrgCode;
	}

	/**
	 * Set
	 * @param paymentOrgCode
	 */
	public void setPaymentOrgCode(String paymentOrgCode) {
		this.paymentOrgCode = paymentOrgCode;
	}

	/**
	 * Get
	 * @return paymentOrgName
	 */
	
	public String getPaymentOrgName() {
		return paymentOrgName;
	}

	/**
	 * Set
	 * @param paymentOrgName
	 */
	public void setPaymentOrgName(String paymentOrgName) {
		this.paymentOrgName = paymentOrgName;
	}

	/**
	 * Get
	 * @return paymentCompanyCode
	 */
	
	public String getPaymentCompanyCode() {
		return paymentCompanyCode;
	}

	/**
	 * Set
	 * @param paymentCompanyCode
	 */
	public void setPaymentCompanyCode(String paymentCompanyCode) {
		this.paymentCompanyCode = paymentCompanyCode;
	}

	/**
	 * Get
	 * @return paymentCompanyName
	 */
	
	public String getPaymentCompanyName() {
		return paymentCompanyName;
	}

	/**
	 * Set
	 * @param paymentCompanyName
	 */
	public void setPaymentCompanyName(String paymentCompanyName) {
		this.paymentCompanyName = paymentCompanyName;
	}

	/**
	 * Get
	 * @return applyCompanyCode
	 */
	
	public String getApplyCompanyCode() {
		return applyCompanyCode;
	}

	/**
	 * Set
	 * @param applyCompanyCode
	 */
	public void setApplyCompanyCode(String applyCompanyCode) {
		this.applyCompanyCode = applyCompanyCode;
	}

	/**
	 * Get
	 * @return notes
	 */
	
	public String getNotes() {
		return notes;
	}

	/**
	 * Set
	 * @param notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * Get
	 * @return advanPayNoList
	 */
	
	public List<String> getAdvanPayNoList() {
		return advanPayNoList;
	}

	/**
	 * Set
	 * @param advanPayNoList
	 */
	public void setAdvanPayNoList(List<String> advanPayNoList) {
		this.advanPayNoList = advanPayNoList;
	}

	/**
	 * Get
	 * @return countNum
	 */
	
	public Integer getCountNum() {
		return countNum;
	}

	/**
	 * Set
	 * @param countNum
	 */
	public void setCountNum(Integer countNum) {
		this.countNum = countNum;
	}

	/**
	 * Get
	 * @return pageSize
	 */
	
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * Set
	 * @param pageSize
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * Get
	 * @return arrayColumns
	 */
	
	public String[] getArrayColumns() {
		return arrayColumns;
	}

	/**
	 * Set
	 * @param arrayColumns
	 */
	public void setArrayColumns(String[] arrayColumns) {
		this.arrayColumns = arrayColumns;
	}

	/**
	 * Get
	 * @return arrayColumnNames
	 */
	
	public String[] getArrayColumnNames() {
		return arrayColumnNames;
	}

	/**
	 * Set
	 * @param arrayColumnNames
	 */
	public void setArrayColumnNames(String[] arrayColumnNames) {
		this.arrayColumnNames = arrayColumnNames;
	}

	/**
	 * Get
	 * @return unverifyAmount
	 */
	
	public BigDecimal getUnverifyAmount() {
		return unverifyAmount;
	}

	/**
	 * Set
	 * @param unverifyAmount
	 */
	public void setUnverifyAmount(BigDecimal unverifyAmount) {
		this.unverifyAmount = unverifyAmount;
	}

	/**
	 * Get
	 * @return applyOrgcodeList
	 */
	
	public List<String> getApplyOrgcodeList() {
		return applyOrgcodeList;
	}

	/**
	 * Set
	 * @param applyOrgcodeList
	 */
	public void setApplyOrgcodeList(List<String> applyOrgcodeList) {
		this.applyOrgcodeList = applyOrgcodeList;
	}

	/**
	 * Get
	 * @return empCode
	 */
	
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * Set
	 * @param empCode
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
}
