package com.deppon.foss.module.pickup.order.api.shared.domain;


import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * @ClassName: OrderReportEntity
 * @Description: 快递订单处理每日统计报表记录
 * @author YANGBIN
 * @date 2014-5-9 下午2:01:48
 * 
 */
public class OrderReportEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	// 订单ID
	private String dispatchOrderId;
	// 订单号
	private String orderNo;
	// 接货地址
	private String pickupAddress;
	// 快递员工号
	private String expressDriverCode;
	// 快递员姓名
	private String expressDriverName;
	// 车牌号
	private String vehicleNo;
	// 快递员手机
	private String expressDriverPhone;
	// 收派小区编码
	private String pickupRegionCode;
	// 收派小区名称
	private String pickupRegionName;
	
	public String getDispatchOrderId() {
		return dispatchOrderId;
	}
	public void setDispatchOrderId(String dispatchOrderId) {
		this.dispatchOrderId = dispatchOrderId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getPickupAddress() {
		return pickupAddress;
	}
	public void setPickupAddress(String pickupAddress) {
		this.pickupAddress = pickupAddress;
	}
	public String getExpressDriverCode() {
		return expressDriverCode;
	}
	public void setExpressDriverCode(String expressDriverCode) {
		this.expressDriverCode = expressDriverCode;
	}
	public String getExpressDriverName() {
		return expressDriverName;
	}
	public void setExpressDriverName(String expressDriverName) {
		this.expressDriverName = expressDriverName;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getExpressDriverPhone() {
		return expressDriverPhone;
	}
	public void setExpressDriverPhone(String expressDriverPhone) {
		this.expressDriverPhone = expressDriverPhone;
	}
	public String getPickupRegionCode() {
		return pickupRegionCode;
	}
	public void setPickupRegionCode(String pickupRegionCode) {
		this.pickupRegionCode = pickupRegionCode;
	}
	public String getPickupRegionName() {
		return pickupRegionName;
	}
	public void setPickupRegionName(String pickupRegionName) {
		this.pickupRegionName = pickupRegionName;
	}

	
}