package com.deppon.foss.module.settlement.writeoff.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

public class CustomersNotReconciledEntity extends BaseEntity {
	 
	private static final long serialVersionUID = 1L;

	private String largeRegion;

	private String smallRegion;

	private String statementOrgCode;
	
	private String statementOrgName;

	private String customerCode;

	private String customerName;

	private BigDecimal amountOneMonth;

	private BigDecimal amountTwoMonth;

	private BigDecimal amountThreeMonth;

	public String getLargeRegion() {
		return largeRegion;
	}

	public void setLargeRegion(String largeRegion) {
		this.largeRegion = largeRegion;
	}

	public String getSmallRegion() {
		return smallRegion;
	}

	public void setSmallRegion(String smallRegion) {
		this.smallRegion = smallRegion;
	}

	public String getStatementOrgCode() {
		return statementOrgCode;
	}

	public void setStatementOrgCode(String statementOrgCode) {
		this.statementOrgCode = statementOrgCode;
	}

	public String getStatementOrgName() {
		return statementOrgName;
	}

	public void setStatementOrgName(String statementOrgName) {
		this.statementOrgName = statementOrgName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getAmountOneMonth() {
		return amountOneMonth;
	}

	public void setAmountOneMonth(BigDecimal amountOneMonth) {
		this.amountOneMonth = amountOneMonth;
	}

	public BigDecimal getAmountTwoMonth() {
		return amountTwoMonth;
	}

	public void setAmountTwoMonth(BigDecimal amountTwoMonth) {
		this.amountTwoMonth = amountTwoMonth;
	}

	public BigDecimal getAmountThreeMonth() {
		return amountThreeMonth;
	}

	public void setAmountThreeMonth(BigDecimal amountThreeMonth) {
		this.amountThreeMonth = amountThreeMonth;
	}
}
