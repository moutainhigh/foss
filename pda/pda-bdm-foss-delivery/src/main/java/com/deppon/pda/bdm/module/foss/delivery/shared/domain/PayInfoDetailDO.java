package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 打款信息明细实体
 */
public class PayInfoDetailDO extends PayCenterBaseDO implements Serializable {

	private static final long serialVersionUID = -6785775806587541379L;
	/**
	 * 打款编码
	 */
	private String payCode;

	/**
	 * 关联类型
	 */
	private String relatedType;

	/**
	 * 关联对象
	 */
	private String relatedTarget;

	/**
	 * 来源单据类型
	 */
	private String sourceBillType;
	
	/**
	 * 来源单据单号 (运单号)
	 */
	private String sourceBillNo;
	
	/**
	 * 对象金额
	 */
	private BigDecimal amount;

	/**
	 * 对象名称
	 */
	private String customerName;

	/**
	 * 对象编码
	 */
	private String customerCode;

	/**
	 * 对象类型
	 */
	private String customerType;

	/**
	 * 业务日期
	 */
	private Date businessDate;

	/******** getter/setter *********/

	public String getPayCode() {
		return payCode;
	}
	
	public String getSourceBillType() {
		return sourceBillType;
	}

	public void setSourceBillType(String sourceBillType) {
		this.sourceBillType = sourceBillType;
	}

	public String getSourceBillNo() {
		return sourceBillNo;
	}

	public void setSourceBillNo(String sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
	}
	
	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public String getRelatedType() {
		return relatedType;
	}

	public void setRelatedType(String relatedType) {
		this.relatedType = relatedType;
	}

	public String getRelatedTarget() {
		return relatedTarget;
	}

	public void setRelatedTarget(String relatedTarget) {
		this.relatedTarget = relatedTarget;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
}
