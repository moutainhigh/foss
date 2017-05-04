package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * 
 * @author foss结算-306579-guoxinru 
 * @date 2016-3-5 下午1:56:35    
 */
public class PartnerStatementOfAwardDeductDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4803689293496583439L;

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
	 * 来源单号--对账单号
	 */
	private String sourceBillNo;
	
	/**
	 * 业务类别--奖罚扣款
	 */
	private String sourceBillType;

	/**
	 * 来源系统
	 */
	private String sourceSystem;
	
	
	/**
	 * 业务日期
	 */
	private Date bizDate;

	
	/**
	 * 扣款金额
	 */
	private BigDecimal amount;
	
	
	/**
	 * 操作人姓名
	 */
	private String operatorName;
	
	
	/**
	 * 操作人编码
	 */
	private String operatorCode;
	
	/**
	 * 备注信息
	 */
	private String remark;

	/**
	 * 流水类型
	 */
	private String flowType;
	
	/**
	 * @return the partnerOrgCode
	 */
	public String getPartnerOrgCode() {
		return partnerOrgCode;
	}


	/**
	 * @param partnerOrgCode the partnerOrgCode to set
	 */
	public void setPartnerOrgCode(String partnerOrgCode) {
		this.partnerOrgCode = partnerOrgCode;
	}


	/**
	 * @return the partnerOrgName
	 */
	public String getPartnerOrgName() {
		return partnerOrgName;
	}


	/**
	 * @param partnerOrgName the partnerOrgName to set
	 */
	public void setPartnerOrgName(String partnerOrgName) {
		this.partnerOrgName = partnerOrgName;
	}


	/**
	 * @return the sourceBillNo
	 */
	public String getSourceBillNo() {
		return sourceBillNo;
	}


	/**
	 * @param sourceBillNo the sourceBillNo to set
	 */
	public void setSourceBillNo(String sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
	}


	/**
	 * @return the sourceBillType
	 */
	public String getSourceBillType() {
		return sourceBillType;
	}


	/**
	 * @param sourceBillType the sourceBillType to set
	 */
	public void setSourceBillType(String sourceBillType) {
		this.sourceBillType = sourceBillType;
	}


	/**
	 * @return the sourceSystem
	 */
	public String getSourceSystem() {
		return sourceSystem;
	}


	/**
	 * @param sourceSystem the sourceSystem to set
	 */
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}


	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}


	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	/**
	 * @return the contractOrgName
	 */
	public String getContractOrgName() {
		return contractOrgName;
	}


	/**
	 * @param contractOrgName the contractOrgName to set
	 */
	public void setContractOrgName(String contractOrgName) {
		this.contractOrgName = contractOrgName;
	}


	/**
	 * @return the contractOrgCode
	 */
	public String getContractOrgCode() {
		return contractOrgCode;
	}


	/**
	 * @param contractOrgCode the contractOrgCode to set
	 */
	public void setContractOrgCode(String contractOrgCode) {
		this.contractOrgCode = contractOrgCode;
	}


	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}


	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Date getBizDate() {
		return bizDate;
	}


	public void setBizDate(Date bizDate) {
		this.bizDate = bizDate;
	}

	public String getFlowType() {
		return flowType;
	}


	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}


	public String getOperatorName() {
		return operatorName;
	}


	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}


	public String getOperatorCode() {
		return operatorCode;
	}


	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	
	
	
}
