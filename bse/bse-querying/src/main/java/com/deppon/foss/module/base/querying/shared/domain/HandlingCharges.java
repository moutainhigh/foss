package com.deppon.foss.module.base.querying.shared.domain;

import java.math.BigDecimal;

/**
 * 2013-7-18 foss-132599-shenweihua
 * 装卸费基类   
 */
public class HandlingCharges {
	/**
	 * 金额
	 */
	private BigDecimal amount;

	/**
	 * 付款状态:未汇款(NT)、汇款中(TG)、已汇款(TD)
	 */
	private String paymentStatus;

	/**
	 * 付款方式
	 */
	private String paymentType;
	
	/**
	 * 得到金额
	 * @return
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	
	/**
	 * 设置金额
	 * @return
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	/**
	 * 得到付款状态
	 * @return
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}
	
	/**
	 * 设置付款状态
	 * @return
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	/**
	 * 得到付款方式
	 * @return
	 */
	public String getPaymentType() {
		return paymentType;
	}
	
	/**
	 * 设置付款方式
	 * @return
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}


}
