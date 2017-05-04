package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 直达包扫描运单entity
 * */
public class ThroughPackageScanWaybillEntity  implements Serializable {
  
  /**
	 * 
	 */
 private static final long serialVersionUID = 1L;
 
  private String id;//id
  
  private String  packageNo ;//包号
  
  private String  waybillNo ;//运单号
  
  private  String serialNo;//流水号
  
  private String origCode;//部门code
  
  private String deviceNo ;//设备号
  
  private Date scanTime;//扫描时间
  
  
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getPackageNo() {
	return packageNo;
}
public void setPackageNo(String packageNo) {
	this.packageNo = packageNo;
}
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
public String getOrigCode() {
	return origCode;
}
public void setOrigCode(String origCode) {
	this.origCode = origCode;
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
	
	
}
