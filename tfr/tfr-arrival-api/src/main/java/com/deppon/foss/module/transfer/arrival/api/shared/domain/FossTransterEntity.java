package com.deppon.foss.module.transfer.arrival.api.shared.domain;

import java.math.BigDecimal;

public class FossTransterEntity{
		private String chargingAssmebleNo;
		private BigDecimal unverifyAmount;
		private BigDecimal amountTotal;
		
		public String getChargingAssmebleNo() {
			return chargingAssmebleNo;
		}
		
		public void setChargingAssmebleNo(String chargingAssmebleNo) {
			this.chargingAssmebleNo = chargingAssmebleNo;
		}
		
		public BigDecimal getUnverifyAmount() {
			return unverifyAmount;
		}
		
		public void setUnverifyAmount(BigDecimal unverifyAmount) {
			this.unverifyAmount = unverifyAmount;
		}
		
		public BigDecimal getAmountTotal() {
			return amountTotal;
		}
		
		public void setAmountTotal(BigDecimal amountTotal) {
			this.amountTotal = amountTotal;
		}
	}