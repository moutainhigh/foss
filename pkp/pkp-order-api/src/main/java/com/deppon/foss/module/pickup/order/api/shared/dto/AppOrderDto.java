package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.io.Serializable;

public class AppOrderDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * APP订单
	 */
	private String orderNo;
	private String shippingNo;
	private String posterMobilePhone;
	private String receiverMobilePhone;
	private String driverCode;
	private String productCode;
	private String status;
	//add by 329757
	private String vehicleNo;
	
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getShippingNo() {
		return shippingNo;
	}
	public void setShippingNo(String shippingNo) {
		this.shippingNo = shippingNo;
	}
	public String getPosterMobilePhone() {
		return posterMobilePhone;
	}
	public void setPosterMobilePhone(String posterMobilePhone) {
		this.posterMobilePhone = posterMobilePhone;
	}
	public String getReceiverMobilePhone() {
		return receiverMobilePhone;
	}
	public void setReceiverMobilePhone(String receiverMobilePhone) {
		this.receiverMobilePhone = receiverMobilePhone;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
