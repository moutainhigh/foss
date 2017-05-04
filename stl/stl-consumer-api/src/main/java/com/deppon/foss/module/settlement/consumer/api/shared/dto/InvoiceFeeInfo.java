package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.math.BigDecimal;

/**
 * 发票费用信息
 * 
 * @author Administrator
 * 
 */
public class InvoiceFeeInfo {

	/**
	 * 发票金额
	 */
	private BigDecimal amount;

	/**
	 * 开发票部门
	 */
	private String orgCode;

	/**
	 * 开发票部门名称
	 */
	private String orgName;

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode
	 *            the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName
	 *            the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

}
