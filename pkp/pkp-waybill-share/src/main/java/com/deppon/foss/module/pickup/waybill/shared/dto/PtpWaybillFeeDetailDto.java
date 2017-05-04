package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PtpWaybillFeeDetailDto  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6216106347235070503L;

	//费用类型 eg：超远派送费
	private String feeType;
	//费用金额
	private BigDecimal amount;
	
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	
	
}
