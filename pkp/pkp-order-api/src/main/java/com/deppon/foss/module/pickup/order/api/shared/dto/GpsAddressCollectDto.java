package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.util.Date;

public class GpsAddressCollectDto {
	/**
	 * 运单号
	 * */
	private String billNo;
	/**
	 * 地址类型
	 * */
	private String addressType;
	/**
	 * 经度
	 * */
	private String  gpsLongitude;
	/**
	 * 纬度
	 * */
	private String  gpsLatitude;
	/**
	 * 采集时间
	 * */
	private Date  collectTime;
	/**
	 * 采集司机编号
	 * */
	private String driverCode;
	/**
	 * 采集人所在部门
	 * */
	private String driverDept;
	
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getGpsLongitude() {
		return gpsLongitude;
	}
	public void setGpsLongitude(String gpsLongitude) {
		this.gpsLongitude = gpsLongitude;
	}
	public String getGpsLatitude() {
		return gpsLatitude;
	}
	public void setGpsLatitude(String gpsLatitude) {
		this.gpsLatitude = gpsLatitude;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	public String getDriverDept() {
		return driverDept;
	}
	public void setDriverDept(String driverDept) {
		this.driverDept = driverDept;
	}
	

}
