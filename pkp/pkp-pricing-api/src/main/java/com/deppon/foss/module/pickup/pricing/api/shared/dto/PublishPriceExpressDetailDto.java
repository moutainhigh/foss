package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.math.BigDecimal;

public class PublishPriceExpressDetailDto {
	/**
	 * 首重
	 */
	private BigDecimal firstWeight ;
	/**
	 * 重量上线
	 */
	private BigDecimal weightOnline ;
	/**
	 * 重量下线
	 */
	private BigDecimal weightOffline ;
    /**
     * 费率
     */
    private BigDecimal feeRate;
	public BigDecimal getFirstWeight() {
		return firstWeight;
	}
	public void setFirstWeight(BigDecimal firstWeight) {
		this.firstWeight = firstWeight;
	}
	public BigDecimal getWeightOnline() {
		return weightOnline;
	}
	public void setWeightOnline(BigDecimal weightOnline) {
		this.weightOnline = weightOnline;
	}
	public BigDecimal getWeightOffline() {
		return weightOffline;
	}
	public void setWeightOffline(BigDecimal weightOffline) {
		this.weightOffline = weightOffline;
	}
	public BigDecimal getFeeRate() {
		return feeRate;
	}
	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}
    
}
