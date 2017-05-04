package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.math.BigDecimal;

/**
 * 用于网上支付校验的dto
 * @author 239284
 *
 */
public class ArriveOnlineDto {

	public BigDecimal amount;
	public BigDecimal verifyAmount;
	public BigDecimal unverifyAmount;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getVerifyAmount() {
		return verifyAmount;
	}

	public void setVerifyAmount(BigDecimal verifyAmount) {
		this.verifyAmount = verifyAmount;
	}

	public BigDecimal getUnverifyAmount() {
		return unverifyAmount;
	}

	public void setUnverifyAmount(BigDecimal unverifyAmount) {
		this.unverifyAmount = unverifyAmount;
	}
	
}
