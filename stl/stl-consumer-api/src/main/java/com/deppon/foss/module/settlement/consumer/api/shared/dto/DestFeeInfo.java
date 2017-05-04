package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 到付费用
 * 
 * @author Administrator
 * 
 */
public class DestFeeInfo {

	/**
	 * 到付总额
	 */
	private BigDecimal totalAmount;

	/**
	 * 实收运费
	 */
	private BigDecimal transportAmount;

	/**
	 * 付款方式
	 */
	private String[] paymentTypes;

	/**
	 * 付款日期
	 */
	private Date paymentDate;

	/**
	 * @return the totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount
	 *            the totalAmount to set
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the transportAmount
	 */
	public BigDecimal getTransportAmount() {
		return transportAmount;
	}

	/**
	 * @param transportAmount
	 *            the transportAmount to set
	 */
	public void setTransportAmount(BigDecimal transportAmount) {
		this.transportAmount = transportAmount;
	}

	/**
	 * @return the paymentTypes
	 */
	public String[] getPaymentTypes() {
		return paymentTypes;
	}

	/**
	 * @param paymentTypes
	 *            the paymentTypes to set
	 */
	public void setPaymentTypes(String[] paymentTypes) {
		this.paymentTypes = paymentTypes;
	}

	/**
	 * @return the paymentDate
	 */
	public Date getPaymentDate() {
		return paymentDate;
	}

	/**
	 * @param paymentDate
	 *            the paymentDate to set
	 */
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

}
