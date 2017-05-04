package com.deppon.foss.module.transfer.pda.api.shared.domain;

import java.util.Date;

public class ExpressIntelligentSortDetailEntity {
	//运单号
	private String waybillNo;
	//流水号  
	private String serialNo;
	//设备号（格口号）
	private String deviceNo;
	//运单号
	private Date scanTime;
	
	//投递是否正确的标识
	private int  scanCount;
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public int getScanCount() {
		return scanCount;
	}
	public void setScanCount(int scanCount) {
		this.scanCount = scanCount;
	}
	
	
}
