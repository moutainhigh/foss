package com.deppon.pda.bdm.module.foss.packagemanager.shared.domain;

import java.util.Date;
/**
 * 
  * @ClassName CancelPackageTask 
  * @Description TODO 提交建包任务
  * @author mt 
  * @date 2013-7-29 下午3:43:47
 */
public class SubmitPackageTask {
	//包号
	String packageCode;
	//设备号
	String deviceNo;
	//员工号	
	String userCode;
	//扫描时间
	Date scanTime;
	public String getPackageCode() {
		return packageCode;
	}
	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
}
