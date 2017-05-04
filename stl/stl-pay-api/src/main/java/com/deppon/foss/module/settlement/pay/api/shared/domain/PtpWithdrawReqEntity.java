package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 合伙人提现-合伙人校验并付款-请求Request实体
 * @author foss-Jiang Xun
 * @date 2016-02-24 下午5:35:00
 *
 */
public class PtpWithdrawReqEntity implements Serializable{

	/**
	 * 付款单Vo序列号
	 */
	private static final long serialVersionUID = 3015713035224671240L;

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
	 * 创建人编码
	 */
	private String createUserCode;
	
	/**
	 * 备注信息
	 */
	private String remark;

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
	
}
