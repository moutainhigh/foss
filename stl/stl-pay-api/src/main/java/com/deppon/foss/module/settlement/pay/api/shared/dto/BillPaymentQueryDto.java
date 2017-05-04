package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 付款单参数Dto
 * 
 * @author foss-qiaolifeng
 * @date 2012-11-19 上午10:34:06
 */
public class BillPaymentQueryDto implements Serializable {

	/**
	 * 付款单参数Dto序列号
	 */
	private static final long serialVersionUID = -4193663505303028149L;
	
	/**
	 * id
	 */
	private String id;
	
	/**
	 * 付款单记账开始时间
	 */
	private Date startAccountDate;

	/**
	 * 付款单记账结束时间
	 */
	private Date endAccountDate;

	/**
	 * 付款方式
	 */
	private String paymentType;

	/**
	 * 汇款状态
	 */
	private String remitStatus;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 单号集合
	 */
	private String[] billNoArray;

	/**
	 * 来源单号集合
	 */
	private String[] sourceBillNoArray;

	/**
	 * 付款单号
	 */
	private List<String> paymentNos;

	/**
	 * 运单号
	 */
	private List<String> waybillNos;

	/**
	 * 来源单号
	 */
	private List<String> sourceBillNos;

	/**
	 * 付款部门编码
	 */
	private String paymentOrgCode;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 是否红单
	 */
	private String isRedBack;

	/**
	 * 界面勾选的付款单编号
	 */
	private String selectBillPaymentNos;

	/**
	 * 操作意见
	 */
	private String opinionNotes;

	/**
	 * 银行账号
	 */
	private String payBillBankNo;

	/**
	 * 收款方姓名
	 */
	private String payBillPayeeName;

	/**
	 * 收款方code
	 */
	private String payBillPayeeCode;

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
	 * 发票抬头编码 （需要传给费控系统）
	 */
	private String invoiceHeadCode;

	/**
	 * 发票抬头名称
	 */
	private String invoiceHeadName;

	/**
	 * 当前登录人编码
	 */
	private String empCode;

	/**
	 * 是否初始化
	 */
	private String isInit;
	
	/**
	 * 是否自动冲借支
	 */
	private String isAutoAbatementLoan;
	
	/**
	 * 工作流号
	 */
	private String workFlowNo;
	
	/**
	 * 工作流号数组
	 */
	private String[] workFlowNosArray;
	/**
	 * 工作流号
	 */
	private List<String> workFlowNos;
	/**
	 * 备注
	 */
	private String notes;
	
	private String batchNo;
	
	/**
	 * 是否保理
	 */
	private String factoring;
	
	
	/**
	 * 保理开始日期   
	 */
	private Date factorBeginTime;
	
	/**
	 * 保理结束日期   
	 */
	private Date factorEndTime;
	
	/**
	 * 保理回款帐号  
	 */
	private String factorAccount;
		
	/**
	 * 贷款客户编码
	 */
	private String cusCode;
	
	
	public String getFactoring() {
		return factoring;
	}

	public void setFactoring(String factoring) {
		this.factoring = factoring;
	}
	
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}



	/**
	 * @get
	 * @return workFlowNosArray
	 */
	public String[] getWorkFlowNosArray() {
		/*
		 * @get
		 * @return workFlowNosArray
		 */
		return workFlowNosArray;
	}


	
	/**
	 * @set
	 * @param workFlowNosArray
	 */
	public void setWorkFlowNosArray(String[] workFlowNosArray) {
		/*
		 *@set
		 *@this.workFlowNosArray = workFlowNosArray
		 */
		this.workFlowNosArray = workFlowNosArray;
	}


	
	/**
	 * @get
	 * @return workFlowNos
	 */
	public List<String> getWorkFlowNos() {
		/*
		 * @get
		 * @return workFlowNos
		 */
		return workFlowNos;
	}


	
	/**
	 * @set
	 * @param workFlowNos
	 */
	public void setWorkFlowNos(List<String> workFlowNos) {
		/*
		 *@set
		 *@this.workFlowNos = workFlowNos
		 */
		this.workFlowNos = workFlowNos;
	}


	/**
	 * @get
	 * @return workFlowNo
	 */
	public String getWorkFlowNo() {
		/*
		 * @get
		 * @return workFlowNo
		 */
		return workFlowNo;
	}

	
	/**
	 * @set
	 * @param workFlowNo
	 */
	public void setWorkFlowNo(String workFlowNo) {
		/*
		 *@set
		 *@this.workFlowNo = workFlowNo
		 */
		this.workFlowNo = workFlowNo;
	}

	/**
	 * @get
	 * @return isInit
	 */
	public String getIsInit() {
		/*
		 * @get
		 * 
		 * @return isInit
		 */
		return isInit;
	}

	/**
	 * @set
	 * @param isInit
	 */
	public void setIsInit(String isInit) {
		/*
		 * @set
		 * 
		 * @this.isInit = isInit
		 */
		this.isInit = isInit;
	}

	/**
	 * @return payBillPayeeCode
	 */
	public String getPayBillPayeeCode() {
		return payBillPayeeCode;
	}

	/**
	 * @param payBillPayeeCode
	 */
	public void setPayBillPayeeCode(String payBillPayeeCode) {
		this.payBillPayeeCode = payBillPayeeCode;
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
	 * @return invoiceHeadCode
	 */
	public String getInvoiceHeadCode() {
		return invoiceHeadCode;
	}

	/**
	 * @param invoiceHeadCode
	 */
	public void setInvoiceHeadCode(String invoiceHeadCode) {
		this.invoiceHeadCode = invoiceHeadCode;
	}

	/**
	 * @return invoiceHeadName
	 */
	public String getInvoiceHeadName() {
		return invoiceHeadName;
	}

	/**
	 * @param invoiceHeadName
	 */
	public void setInvoiceHeadName(String invoiceHeadName) {
		this.invoiceHeadName = invoiceHeadName;
	}

	/**
	 * 联系方式
	 */
	private String mobilePhone;

	/**
	 * 申请备用金额
	 */
	private BigDecimal applyAmount;

	/**
	 * @return startAccountDate
	 */
	public Date getStartAccountDate() {
		return startAccountDate;
	}

	/**
	 * @param startAccountDate
	 */
	public void setStartAccountDate(Date startAccountDate) {
		this.startAccountDate = startAccountDate;
	}

	/**
	 * @return endAccountDate
	 */
	public Date getEndAccountDate() {
		return endAccountDate;
	}

	/**
	 * @param endAccountDate
	 */
	public void setEndAccountDate(Date endAccountDate) {
		this.endAccountDate = endAccountDate;
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
	 * @return billNoArray
	 */
	public String[] getBillNoArray() {
		return billNoArray;
	}

	/**
	 * @param billNoArray
	 */
	public void setBillNoArray(String[] billNoArray) {
		this.billNoArray = billNoArray;
	}

	/**
	 * @return paymentNos
	 */
	public List<String> getPaymentNos() {
		return paymentNos;
	}

	/**
	 * @param paymentNos
	 */
	public void setPaymentNos(List<String> paymentNos) {
		this.paymentNos = paymentNos;
	}

	/**
	 * @return waybillNos
	 */
	public List<String> getWaybillNos() {
		return waybillNos;
	}

	/**
	 * @param waybillNos
	 */
	public void setWaybillNos(List<String> waybillNos) {
		this.waybillNos = waybillNos;
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
	 * @return selectBillPaymentNos
	 */
	public String getSelectBillPaymentNos() {
		return selectBillPaymentNos;
	}

	/**
	 * @param selectBillPaymentNos
	 */
	public void setSelectBillPaymentNos(String selectBillPaymentNos) {
		this.selectBillPaymentNos = selectBillPaymentNos;
	}

	/**
	 * @return opinionNotes
	 */
	public String getOpinionNotes() {
		return opinionNotes;
	}

	/**
	 * @param opinionNotes
	 */
	public void setOpinionNotes(String opinionNotes) {
		this.opinionNotes = opinionNotes;
	}

	/**
	 * @return payBillBankNo
	 */
	public String getPayBillBankNo() {
		return payBillBankNo;
	}

	/**
	 * @param payBillBankNo
	 */
	public void setPayBillBankNo(String payBillBankNo) {
		this.payBillBankNo = payBillBankNo;
	}

	/**
	 * @return payBillPayeeName
	 */
	public String getPayBillPayeeName() {
		return payBillPayeeName;
	}

	/**
	 * @param payBillPayeeName
	 */
	public void setPayBillPayeeName(String payBillPayeeName) {
		this.payBillPayeeName = payBillPayeeName;
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
	 * @return applyAmount
	 */
	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	/**
	 * @param applyAmount
	 */
	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	/**
	 * @return sourceBillNoArray
	 */
	public String[] getSourceBillNoArray() {
		return sourceBillNoArray;
	}

	/**
	 * @param sourceBillNoArray
	 */
	public void setSourceBillNoArray(String[] sourceBillNoArray) {
		this.sourceBillNoArray = sourceBillNoArray;
	}

	/**
	 * @return sourceBillNos
	 */
	public List<String> getSourceBillNos() {
		return sourceBillNos;
	}

	/**
	 * @param sourceBillNos
	 */
	public void setSourceBillNos(List<String> sourceBillNos) {
		this.sourceBillNos = sourceBillNos;
	}

	/**
	 * @return empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * @param empCode
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * @GET
	 * @return isAutoAbatementLoan
	 */
	public String getIsAutoAbatementLoan() {
		/*
		 *@get
		 *@ return isAutoAbatementLoan
		 */
		return isAutoAbatementLoan;
	}

	/**
	 * @SET
	 * @param isAutoAbatementLoan
	 */
	public void setIsAutoAbatementLoan(String isAutoAbatementLoan) {
		/*
		 *@set
		 *@this.isAutoAbatementLoan = isAutoAbatementLoan
		 */
		this.isAutoAbatementLoan = isAutoAbatementLoan;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
