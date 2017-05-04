package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;

/**
 * 
 * 欠款额度返回结果
 * 
 * @author dp-huangxb
 * @date 2012-10-19 下午6:37:27
 */
public class DebitDto implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 49998567534921231L;

	/**
	 * 能否欠款信息 国际化后,使用异常编码
	 */
	private String message;

	/**
	 * 能否欠款标记
	 */
	private boolean beBebt;

	/**
	 * 付款方式
	 */
	private String payment;

	/**
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return beBebt
	 */
	public boolean isBeBebt() {
		return beBebt;
	}

	/**
	 * @param beBebt
	 */
	public void setBeBebt(boolean beBebt) {
		this.beBebt = beBebt;
	}

	/**
	 * @return payment
	 */
	public String getPayment() {
		return payment;
	}

	/**
	 * @param payment
	 */
	public void setPayment(String payment) {
		this.payment = payment;
	}

}
