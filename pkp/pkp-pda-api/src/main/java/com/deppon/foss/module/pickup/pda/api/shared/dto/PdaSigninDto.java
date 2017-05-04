package com.deppon.foss.module.pickup.pda.api.shared.dto;

import java.util.Date;

/**
 * PDA签到信息
 * @author 038590-foss-wanghui
 * @date 2013-4-27 上午9:17:40
 */
public class PdaSigninDto {
	// 设备号
	private String deviceNo;
	// 司机工号
	private String driverCode;
	// 车牌号
	private String vehicleNo;
	// 签到时间
	private Date signTime;
	// 用户类型
	private String userType;
	// 部门编码
	private String orgCode;

	/**
	 * Gets the orgCode.
	 * 
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the orgCode.
	 * 
	 * @param orgCode the orgCode to see
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Gets the deviceNo.
	 * 
	 * @return the deviceNo
	 */
	public String getDeviceNo() {
		return deviceNo;
	}

	/**
	 * Sets the deviceNo.
	 * 
	 * @param deviceNo the deviceNo to see
	 */
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	/**
	 * Gets the driverCode.
	 * 
	 * @return the driverCode
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * Sets the driverCode.
	 * 
	 * @param driverCode the driverCode to see
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * Gets the vehicleNo.
	 * 
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * Sets the vehicleNo.
	 * 
	 * @param vehicleNo the vehicleNo to see
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * Gets the signTime.
	 * 
	 * @return the signTime
	 */
	public Date getSignTime() {
		return signTime;
	}

	/**
	 * Sets the signTime.
	 * 
	 * @param signTime the signTime to see
	 */
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	/**
	 * Gets the userType.
	 * 
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * Sets the userType.
	 * 
	 * @param userType the userType to see
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
}
