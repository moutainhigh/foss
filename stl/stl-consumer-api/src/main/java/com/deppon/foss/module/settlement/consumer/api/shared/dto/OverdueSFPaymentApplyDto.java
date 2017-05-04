package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.OverdueSFPaymentApplyEntity;

public class OverdueSFPaymentApplyDto extends OverdueSFPaymentApplyEntity implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4978313748650326558L;
	/**
	 * 记账日期
	 */
	private Date accountDate;
	/**
	 * 金额
	 */
	private BigDecimal amount;

	/**
	 * 业务日期
	 */
	private Date businessDate;

	/**
	 * 客户编码
	 */
	private String customerCode;
	/**
	 * 客户联系人编码
	 */
	private String customerContact;
	/**
	 * 客户联系人名称
	 */
	private String customerContactName;
	/**
	 * 客户名称
	 */
	private String customerName;
	/**
	 * 联系电话
	 */
	private String customerPhone;

	/**
	 * 应付部门编码
	 */
	private String payableOrgCode;
	/**
	 * 应付部门名称
	 */
	private String payableOrgName;
	/**
	 * @return  the accountDate
	 */
	public Date getAccountDate() {
		return accountDate;
	}
	/**
	 * @param accountDate the accountDate to set
	 */
	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}
	/**
	 * @return  the amount
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
	 * @return  the businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}
	/**
	 * @param businessDate the businessDate to set
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}
	/**
	 * @return  the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * @return  the customerContact
	 */
	public String getCustomerContact() {
		return customerContact;
	}
	/**
	 * @param customerContact the customerContact to set
	 */
	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
	}
	/**
	 * @return  the customerContactName
	 */
	public String getCustomerContactName() {
		return customerContactName;
	}
	/**
	 * @param customerContactName the customerContactName to set
	 */
	public void setCustomerContactName(String customerContactName) {
		this.customerContactName = customerContactName;
	}
	/**
	 * @return  the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return  the customerPhone
	 */
	public String getCustomerPhone() {
		return customerPhone;
	}
	/**
	 * @param customerPhone the customerPhone to set
	 */
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	/**
	 * @return  the payableOrgCode
	 */
	public String getPayableOrgCode() {
		return payableOrgCode;
	}
	/**
	 * @param payableOrgCode the payableOrgCode to set
	 */
	public void setPayableOrgCode(String payableOrgCode) {
		this.payableOrgCode = payableOrgCode;
	}
	/**
	 * @return  the payableOrgName
	 */
	public String getPayableOrgName() {
		return payableOrgName;
	}
	/**
	 * @param payableOrgName the payableOrgName to set
	 */
	public void setPayableOrgName(String payableOrgName) {
		this.payableOrgName = payableOrgName;
	}

}
