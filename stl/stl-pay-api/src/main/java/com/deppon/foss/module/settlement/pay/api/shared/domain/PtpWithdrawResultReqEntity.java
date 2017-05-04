package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 合伙人提现-报账系统审批结果通知合伙人系统-请求Request实体
 * @author foss-Jiang Xun
 * @date 2016-02-26 下午4:39:00
 */
public class PtpWithdrawResultReqEntity implements Serializable{

	/**
	 * 付款单Vo序列号
	 */
	private static final long serialVersionUID = 5553621584508817719L;
	
	/**
	 * 合伙人部门编码
	 */
	private String partnerOrgCode;

	/**
	 * 合伙人部门名称
	 */
	private String partnerOrgName;	
	
	/**
	 * 对接部门名称
	 */
	private String contractOrgName;
	
	
	/**
	 * 对接部门编码
	 */
	private String contractOrgCode;

	/**
	 * 来源单号
	 */
	private String sourceBillNo;

	
	/**
	 * 业务类别
	 */
	private String sourceBillType;

	/**
	 * 来源系统
	 */
	private String sourceSystem;
	
	/**
	 * 退款类别
	 */
	private String refundType;
	
	/**
	 * 业务日期
	 */
	private Date bizDate;
	
	
	/**
	 * 业务日期
	 */
	private Date accountDate;

	
	/**
	 * 扣款金额
	 */
	private BigDecimal amount;
	
	
	/**
	 * 创建人名称
	 */
	private String createUserName;
	
	
	/**
	 * 修改人编码
	 */
	private String modifyUserCode;
	
	/**
	 * 修改人名称
	 */
	private String modifyUserName;
	
	
	/**
	 * 创建人编码
	 */
	private String createUserCode;
	
	/**
	 * 备注信息
	 */
	private String remark;
	
	/**
     * 付款单单号
     */
    private String paymentBillNo;
    
    /**
     * 工作流号
     */
    private String workFlowNo;
    
    
    /**
     * 工作流审批状态
     */
    private String workFlowApplyStatus;

	public String getPartnerOrgCode() {
		return partnerOrgCode;
	}

	public void setPartnerOrgCode(String partnerOrgCode) {
		this.partnerOrgCode = partnerOrgCode;
	}

	public String getPartnerOrgName() {
		return partnerOrgName;
	}

	public void setPartnerOrgName(String partnerOrgName) {
		this.partnerOrgName = partnerOrgName;
	}

	public String getContractOrgName() {
		return contractOrgName;
	}

	public void setContractOrgName(String contractOrgName) {
		this.contractOrgName = contractOrgName;
	}

	public String getContractOrgCode() {
		return contractOrgCode;
	}

	public void setContractOrgCode(String contractOrgCode) {
		this.contractOrgCode = contractOrgCode;
	}

	public String getSourceBillNo() {
		return sourceBillNo;
	}

	public void setSourceBillNo(String sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
	}

	public String getSourceBillType() {
		return sourceBillType;
	}

	public void setSourceBillType(String sourceBillType) {
		this.sourceBillType = sourceBillType;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}


	public Date getBizDate() {
		return bizDate;
	}

	public void setBizDate(Date bizDate) {
		this.bizDate = bizDate;
	}

	public Date getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPaymentBillNo() {
		return paymentBillNo;
	}

	public void setPaymentBillNo(String paymentBillNo) {
		this.paymentBillNo = paymentBillNo;
	}

	public String getWorkFlowNo() {
		return workFlowNo;
	}

	public void setWorkFlowNo(String workFlowNo) {
		this.workFlowNo = workFlowNo;
	}

	public String getWorkFlowApplyStatus() {
		return workFlowApplyStatus;
	}

	public void setWorkFlowApplyStatus(String workFlowApplyStatus) {
		this.workFlowApplyStatus = workFlowApplyStatus;
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
	
}
