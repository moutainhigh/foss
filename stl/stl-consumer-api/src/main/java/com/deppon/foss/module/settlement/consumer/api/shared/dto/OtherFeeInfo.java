package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.math.BigDecimal;

/**
 * 其它费用信息
 * 
 * @author Administrator
 * 
 */
public class OtherFeeInfo {

	/**
	 * 应付单据类型:装卸费(SF)、服务补救(CP)、退运费(R)
	 */
	private String payableBillType;

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
	 * 冲应收金额
	 */
	private BigDecimal verifyRcvAmount;

	/**
	 * 应退金额
	 */
	private BigDecimal returnableAmount;

	/**
	 * @return the payableBillType
	 */
	public String getPayableBillType() {
		return payableBillType;
	}

	/**
	 * @param payableBillType
	 *            the payableBillType to set
	 */
	public void setPayableBillType(String payableBillType) {
		this.payableBillType = payableBillType;
	}

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
	 * @return the paymentStatus
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @param paymentStatus
	 *            the paymentStatus to set
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

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
	 * @return the verifyRcvAmount
	 */
	public BigDecimal getVerifyRcvAmount() {
		return verifyRcvAmount;
	}

	/**
	 * @param verifyRcvAmount
	 *            the verifyRcvAmount to set
	 */
	public void setVerifyRcvAmount(BigDecimal verifyRcvAmount) {
		this.verifyRcvAmount = verifyRcvAmount;
	}

	/**
	 * @return the returnableAmount
	 */
	public BigDecimal getReturnableAmount() {
		return returnableAmount;
	}

	/**
	 * @param returnableAmount
	 *            the returnableAmount to set
	 */
	public void setReturnableAmount(BigDecimal returnableAmount) {
		this.returnableAmount = returnableAmount;
	}

}
