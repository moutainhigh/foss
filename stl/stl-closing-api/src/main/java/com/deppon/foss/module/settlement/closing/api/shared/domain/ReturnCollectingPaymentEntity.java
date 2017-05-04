package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.io.Serializable;
/**
 * @author 218392 zhangyongxue
 * @date   2015-09-07 09:35:40
 * 代收货款清单查询返回结果实体entity
 */
public class ReturnCollectingPaymentEntity implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 日期(运单表里对应FOSS的开单时间，精确到日)
	 */
	private String billTime;
	
	/**
	 * 运单号---(代收货款表)
	 */
	private String waybillNo;
	
	/**
	 * 收货公司---(运单表中收货客户名称)
	 */
	private String receiveCompany;
	
	/**
	 * 收货人固定电话----(运单表中电话)
	 */
	private String receiverHomePhone;
	/**
	 * 收货人个人手机----(运单表中手机号)
	 */
	private String receiverPersonTel;
	
	/**
	 * 代收货款金额---(代收货款表中金额)
	 */
	private double codAmount;
	
	/**
	 * 手续费---(运单表中代收货款手续费)
	 */
	private double codFee;
	
	/**
	 * 发货人---(运单表中发货客户联系人)
	 */
	private String deliverCustomer;
	
	/**
	 * 退款类型---(运单表中字段‘退款类型’)
	 */
	private String refundType;
	
	/**
	 * 付款状态--(代收货款表中status代收货款状态,也是‘代收货款综合查询中的付款状态字段’)
	 */
	private String paymentStatus;
	
	/**
	 * 应付金额---(代收货款金额 减去 手续费)
	 */
	private double payableAmount;
	
	/**
	 * 收货人电话或手机供 CRM使用的----(优先去手机号，没有再用电话)
	 */
	private String receiverPhone;
	
	/**
	 * (省)
	 */
	private String customerProvName;
	
	/**
	 * (市)
	 */
	private String customerCityName;
	
	/**
	 * (区)
	 */
	private String customerDistName;
	
	/**
	 * 收货人街道,不是CRM需要的，需要的在下面receiveArea(运单表中具体的收货具体地址，有的只是街道)
	 */
	private String customerAddress;
	
	/**
	 * 收货地区 CRM使用的---(省+市+区+街道)
	 */
	private String receiveArea;
	
	
	public String getBillTime() {
		return billTime;
	}

	public void setBillTime(String billTime) {
		this.billTime = billTime;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getCustomerProvName() {
		return customerProvName;
	}

	public void setCustomerProvName(String customerProvName) {
		this.customerProvName = customerProvName;
	}

	public String getCustomerCityName() {
		return customerCityName;
	}

	public void setCustomerCityName(String customerCityName) {
		this.customerCityName = customerCityName;
	}

	public String getCustomerDistName() {
		return customerDistName;
	}

	public void setCustomerDistName(String customerDistName) {
		this.customerDistName = customerDistName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getReceiveArea() {
		return receiveArea;
	}

	public void setReceiveArea(String receiveArea) {
		this.receiveArea = receiveArea;
	}

	public String getReceiveCompany() {
		return receiveCompany;
	}

	public void setReceiveCompany(String receiveCompany) {
		this.receiveCompany = receiveCompany;
	}

	public String getReceiverHomePhone() {
		return receiverHomePhone;
	}

	public void setReceiverHomePhone(String receiverHomePhone) {
		this.receiverHomePhone = receiverHomePhone;
	}

	public String getReceiverPersonTel() {
		return receiverPersonTel;
	}

	public void setReceiverPersonTel(String receiverPersonTel) {
		this.receiverPersonTel = receiverPersonTel;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public double getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(double codAmount) {
		this.codAmount = codAmount;
	}

	public double getCodFee() {
		return codFee;
	}

	public void setCodFee(double codFee) {
		this.codFee = codFee;
	}

	public double getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(double payableAmount) {
		this.payableAmount = payableAmount;
	}

	public String getDeliverCustomer() {
		return deliverCustomer;
	}

	public void setDeliverCustomer(String deliverCustomer) {
		this.deliverCustomer = deliverCustomer;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}


}
