package com.deppon.foss.module.settlement.closing.api.shared.inteface.domain;

import java.math.BigDecimal;

public class LineOfcreditRequest {
	/**
	 * 开单付款方式
	 */
	private String paidMethod;
	/**
	 * 发货客户编码
	 */
	private String deliveryCustomerCode;
	/**
	 * 收货部门(出发部门)
	 */
	private String receiveOrgCode;
	/**
	 * 预付金额
	 */
	private BigDecimal prePayAmount;
	
	public String getPaidMethod() {
		return paidMethod;
	}
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}
	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}
	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}
	public BigDecimal getPrePayAmount() {
		return prePayAmount;
	}
	public void setPrePayAmount(BigDecimal prePayAmount) {
		this.prePayAmount = prePayAmount;
	}
	
	
}
