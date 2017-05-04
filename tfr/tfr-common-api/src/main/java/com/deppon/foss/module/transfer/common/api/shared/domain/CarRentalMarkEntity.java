package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class CarRentalMarkEntity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String quotataionNumber;         //询价编号
	
	private BigDecimal quotePrice;           //报价价格
	
	private String PleaseCarDepartment;      //请车部门

	public String getQuotataionNumber() {
		return quotataionNumber;
	}

	public void setQuotataionNumber(String quotataionNumber) {
		this.quotataionNumber = quotataionNumber;
	}

	public BigDecimal getQuotePrice() {
		return quotePrice;
	}

	public void setQuotePrice(BigDecimal quotePrice) {
		this.quotePrice = quotePrice;
	}

	public String getPleaseCarDepartment() {
		return PleaseCarDepartment;
	}

	public void setPleaseCarDepartment(String pleaseCarDepartment) {
		PleaseCarDepartment = pleaseCarDepartment;
	}
	
	
}
