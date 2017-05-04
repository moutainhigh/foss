package com.deppon.pda.bdm.module.foss.login.shared.domain;

import java.util.Date;

/**
 * PDA登出信息
 * @author gaojia
 * @date Sep 6,2012 14:33:30 PM
 * @version 1.0
 * @since
 */
public class PdaLoginOutInfo {
	/**
	 * 员工工号
	 */
	private String scanUser;
	/**
	 * 车牌号
	 */
	private String truckCode;
	/**
	 * PDA编号
	 */
	private String pdaCode;
	/**
	 * 登出时间
	 */
	private Date scanTime;
	/**
	 * 用户类型
	 */
	private String userType;
	public String getScanUser() {
		return scanUser;
	}
	public void setScanUser(String scanUser) {
		this.scanUser = scanUser;
	}
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	public String getPdaCode() {
		return pdaCode;
	}
	public void setPdaCode(String pdaCode) {
		this.pdaCode = pdaCode;
	}
	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	
}
