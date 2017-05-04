package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.math.BigDecimal;

/**
 * 代收货款费用信息
 * 
 * @author Administrator
 * 
 */
public class CODFeeInfo {

	/**
	 * 实收代收货款
	 */
	private BigDecimal codAmount;

	/**
	 * 付款方式
	 */
	private String paymentType;

	/**
	 * 所有付款方式
	 */
	private String[] paymentTypes;

	/**
	 * 冲应收金额
	 */
	private BigDecimal verifyRcvAmount;

	/**
	 * 应退金额
	 */
	private BigDecimal returnableAmount;

	/**
	 * 付款状态
	 */
	private String paymentStatus;

	/**
	 * @return the codAmount
	 */
	public BigDecimal getCodAmount() {
		return codAmount;
	}

	/**
	 * @param codAmount
	 *            the codAmount to set
	 */
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
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

}
