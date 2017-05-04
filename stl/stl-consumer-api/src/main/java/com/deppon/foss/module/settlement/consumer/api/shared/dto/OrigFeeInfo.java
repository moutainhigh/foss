package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 始发运费
 * 
 * @author zbw
 * @since 2013-06-26
 * 
 */
public class OrigFeeInfo {

	/**
	 * 付款方式
	 */
	private String paymentType;

	/**
	 * 实收始发运费
	 */
	private BigDecimal receivedAmount;

	/**
	 * 收款日期
	 */
	private Date receivedDate;

	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType
	 *            the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return the receivedAmount
	 */
	public BigDecimal getReceivedAmount() {
		return receivedAmount;
	}

	/**
	 * @param receivedAmount
	 *            the receivedAmount to set
	 */
	public void setReceivedAmount(BigDecimal receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	/**
	 * @return the receivedDate
	 */
	public Date getReceivedDate() {
		return receivedDate;
	}

	/**
	 * @param receivedDate
	 *            the receivedDate to set
	 */
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
}
