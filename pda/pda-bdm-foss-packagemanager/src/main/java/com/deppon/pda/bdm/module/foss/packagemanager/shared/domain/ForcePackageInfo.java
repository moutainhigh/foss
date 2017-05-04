package com.deppon.pda.bdm.module.foss.packagemanager.shared.domain;

import java.util.Date;
/**
 * 
  * @ClassName ForcePackageInfo 
  * @Description TODO 强扫接口
  * @author mt 
  * @date 2013-7-30 下午2:21:23
 */
public class ForcePackageInfo {
	//包号
	String packageCode;
	//运单号
	String wayBillNo;
	//流水号
	String serialNo;
	//设备号
	String deviceNo;
	//扫描时间
	Date scanTime;
	//扫描状态
	String scanState;
	public String getPackageCode() {
		return packageCode;
	}
	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	public String getScanState() {
		return scanState;
	}
	public void setScanState(String scanState) {
		this.scanState = scanState;
	}
}
