package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.io.Serializable;


public class AppOrderStateDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
    /**
     * 订单状态 
     */
    private String orderSource;
    /**
     * 渠道来源
     */
    private String channel;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 运输性质
     */
    private String transportProperties;
    /**
     * 发货人
     */
    private String sender;
    /**
     * 发货人手机
     */
    private String senderTel;
    /**
     * 司机名称
     */
    private String driverName;
    /**
     * 司机电话
     */
    private String driverTel;
    /**
     * 车牌号
     */
    private String carNumber;
    /**
     * 出发城市
     */
    private String leaveCity;
    /**
     * 接单时间
     */
    private String orderTime;
    /**
     * 快递员工号
     */
    private String courierNumber;
    /**
     * 快递员姓名
     */
    private String courierName;
    /**
     * 快递员电话
     */
    private String courierTel;
    
    
	public String getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getTransportProperties() {
		return transportProperties;
	}
	public void setTransportProperties(String transportProperties) {
		this.transportProperties = transportProperties;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSenderTel() {
		return senderTel;
	}
	public void setSenderTel(String senderTel) {
		this.senderTel = senderTel;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverTel() {
		return driverTel;
	}
	public void setDriverTel(String driverTel) {
		this.driverTel = driverTel;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public String getLeaveCity() {
		return leaveCity;
	}
	public void setLeaveCity(String leaveCity) {
		this.leaveCity = leaveCity;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getCourierNumber() {
		return courierNumber;
	}
	public void setCourierNumber(String courierNumber) {
		this.courierNumber = courierNumber;
	}
	public String getCourierName() {
		return courierName;
	}
	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}
	public String getCourierTel() {
		return courierTel;
	}
	public void setCourierTel(String courierTel) {
		this.courierTel = courierTel;
	}
	
}
