package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;

/**
 * 付款单编辑使用dto
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-11-26 上午10:23:37
 * @since
 * @version
 */
public class BillPaymentDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 7208314934903793875L;

	/**
	 * ID
	 */
	private String id;

	/**
	 * 还款单号
	 */
	private String repaymentNo;

	/**
	 * 记账日期
	 */
	private Date accountDate;

	/**
	 * 工作流号
	 */
	private String workflowNo;

	/**
	 * 申请工作流号
	 */
	private String applyWorkFlowNo;

	/**
	 * 汇款状态
	 */
	private String remitStatus;

	/**
	 * 审核状态
	 */
	private String auditStatus;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 是否红单
	 */
	private String isRedBack;

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
	 * 修改人姓名
	 */
	private String modifyUserName;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 审核意见
	 */
	private String auditOpinion;

	/**
	 * 作废意见
	 */
	private String disableOpinion;

	/**
	 * 付款单集合
	 */
	private List<BillPaymentEntity> billPayments;

	/**
	 * 审核状态限制条件，审核时设置为未审核/反审核时设置为已审核
	 */
	private String conAuditStatus;
	
	/**
	 * 付款批次号
	 */
	private String batchNo;
	
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
		 * 发票抬头编码 （需要传给费控系统）
		 */
		private String invoiceHeadCode;
		
		/**
		 * 发票抬头名称
		 */
		private String invoiceHeadName;
		
	/**
	 * 备注
	 */
	private String notes;

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
	 * @return repaymentNo
	 */
	public String getRepaymentNo() {
		return repaymentNo;
	}

	/**
	 * @param repaymentNo
	 */
	public void setRepaymentNo(String repaymentNo) {
		this.repaymentNo = repaymentNo;
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
	 * @return billPayments
	 */
	public List<BillPaymentEntity> getBillPayments() {
		return billPayments;
	}

	/**
	 * @param billPayments
	 */
	public void setBillPayments(List<BillPaymentEntity> billPayments) {
		this.billPayments = billPayments;
	}

	/**
	 * @return conAuditStatus
	 */
	public String getConAuditStatus() {
		return conAuditStatus;
	}

	/**
	 * @param conAuditStatus
	 */
	public void setConAuditStatus(String conAuditStatus) {
		this.conAuditStatus = conAuditStatus;
	}

	/**
	 * @return batchNo
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * @param batchNo
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
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
	 * @return payeeCode
	 */
	public String getPayeeCode() {
		return payeeCode;
	}

	/**
	 * @param payeeCode
	 */
	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
