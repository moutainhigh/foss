package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;

public class CODPayableDto extends CODEntity implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7979700876765122548L;
	
	private String payableId;
	
	/**
	 * 记账日期
	 */
	private Date payableAccountDate;
	
	/**
	 * 版本号
	 */
	private Short payableVersionNo;
	
	/**
	 * 生效状态
	 */
	private String effectiveStatus;
	
	/**
	 * 签收时间
	 */
	private Date signDate;
	
	/**
	 * 应付子公司编码
	 */
	private String payableComCode;
	
	/**
	 * 应付子公司名称
	 */
	private String payableComName;
	
	/**
	 * 运单号
	 */
	private String payableWaybillNo;
	
	/**
	 * 未核销金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal unverifyAmount;
	
	/**
	 * 应付部门编码
	 */
	private String payablePayableOrgCode;

	
	/**
	 * 应付单部门
	 */
	private String payablePayableOrgName;
	
	/**
	 * 应付金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal amount;
	
	/**
	 * 应付单号
	 */
	private String payableNo;
	
	/**
	 * 来源单据单号
	 */
	private String sourceBillNo;
	
	/**
	 * 客户名称
	 */
	private String payableCustomerName;

	/**
	 * 客户编码
	 */
	private String payableCustomerCode;

	/**
	 * 业务日期
	 */
	private Date payableBusinessDate;
	
	/**
	 * 已核销金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal verifyAmount;
	
	/**
	 * 审批状态
	 */
	private String approveStatus;
	
	/**
	 * 应付单单据类型
	 */
	private String billType;
	
	/**
	 * 应付单是否红单
	 */
	private String isRedBack;
	
	/**
	 * @return payableId
	 */
	public String getPayableId() {
		return payableId;
	}

	/**
	 * @param payableId
	 */
	public void setPayableId(String payableId) {
		this.payableId = payableId;
	}

	/**
	 * @return payableAccountDate
	 */
	public Date getPayableAccountDate() {
		return payableAccountDate;
	}

	/**
	 * @param payableAccountDate
	 */
	public void setPayableAccountDate(Date payableAccountDate) {
		this.payableAccountDate = payableAccountDate;
	}

	/**
	 * @return payableVersionNo
	 */
	public Short getPayableVersionNo() {
		return payableVersionNo;
	}

	/**
	 * @param payableVersionNo
	 */
	public void setPayableVersionNo(Short payableVersionNo) {
		this.payableVersionNo = payableVersionNo;
	}

	/**
	 * @return effectiveStatus
	 */
	public String getEffectiveStatus() {
		return effectiveStatus;
	}

	/**
	 * @param effectiveStatus
	 */
	public void setEffectiveStatus(String effectiveStatus) {
		this.effectiveStatus = effectiveStatus;
	}

	/**
	 * @return signDate
	 */
	public Date getSignDate() {
		return signDate;
	}

	/**
	 * @param signDate
	 */
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	/**
	 * @return payableComName
	 */
	public String getPayableComName() {
		return payableComName;
	}

	/**
	 * @param payableComName
	 */
	public void setPayableComName(String payableComName) {
		this.payableComName = payableComName;
	}

	/**
	 * @return payableWaybillNo
	 */
	public String getPayableWaybillNo() {
		return payableWaybillNo;
	}

	/**
	 * @param payableWaybillNo
	 */
	public void setPayableWaybillNo(String payableWaybillNo) {
		this.payableWaybillNo = payableWaybillNo;
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
	 * @return payablePayableOrgName
	 */
	public String getPayablePayableOrgName() {
		return payablePayableOrgName;
	}

	/**
	 * @param payablePayableOrgName
	 */
	public void setPayablePayableOrgName(String payablePayableOrgName) {
		this.payablePayableOrgName = payablePayableOrgName;
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
	 * @return payableComCode
	 */
	public String getPayableComCode() {
		return payableComCode;
	}

	/**
	 * @param payableComCode
	 */
	public void setPayableComCode(String payableComCode) {
		this.payableComCode = payableComCode;
	}

	/**
	 * @return payablePayableOrgCode
	 */
	public String getPayablePayableOrgCode() {
		return payablePayableOrgCode;
	}

	/**
	 * @param payablePayableOrgCode
	 */
	public void setPayablePayableOrgCode(String payablePayableOrgCode) {
		this.payablePayableOrgCode = payablePayableOrgCode;
	}

	/**
	 * @return payableNo
	 */
	public String getPayableNo() {
		return payableNo;
	}

	/**
	 * @param payableNo
	 */
	public void setPayableNo(String payableNo) {
		this.payableNo = payableNo;
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
	 * @return payableCustomerName
	 */
	public String getPayableCustomerName() {
		return payableCustomerName;
	}

	/**
	 * @param payableCustomerName
	 */
	public void setPayableCustomerName(String payableCustomerName) {
		this.payableCustomerName = payableCustomerName;
	}

	/**
	 * @return payableCustomerCode
	 */
	public String getPayableCustomerCode() {
		return payableCustomerCode;
	}

	/**
	 * @param payableCustomerCode
	 */
	public void setPayableCustomerCode(String payableCustomerCode) {
		this.payableCustomerCode = payableCustomerCode;
	}

	/**
	 * @return payableBusinessDate
	 */
	public Date getPayableBusinessDate() {
		return payableBusinessDate;
	}

	/**
	 * @param payableBusinessDate
	 */
	public void setPayableBusinessDate(Date payableBusinessDate) {
		this.payableBusinessDate = payableBusinessDate;
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
	 * @return approveStatus
	 */
	public String getApproveStatus() {
		return approveStatus;
	}

	/**
	 * @param approveStatus
	 */
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
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
	
}
