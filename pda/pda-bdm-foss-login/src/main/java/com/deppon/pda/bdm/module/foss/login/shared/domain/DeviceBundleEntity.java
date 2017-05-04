package com.deppon.pda.bdm.module.foss.login.shared.domain;

import java.io.Serializable;
import java.util.Date;

public class DeviceBundleEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	/* 设备号 */
	private String dvcCode;
	/* 车牌号 */
	private String truckCode;
	/* 顶级车队 */
	private String topFleet;
	/* 设备状态 */
	private String status;
	/* 绑定时间 */
	private Date bindTime;
	/* 解绑时间 */
	private Date unwrapTime;
	/* 解绑人工号 */
	private String userCode;
	/* 解绑人姓名 */
	private String userName;
	/* 系统类型 */
	private String systemType;

	public String getDvcCode() {
		return dvcCode;
	}

	public void setDvcCode(String dvcCode) {
		this.dvcCode = dvcCode;
	}

	public String getTruckCode() {
		return truckCode;
	}

	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}

	public String getTopFleet() {
		return topFleet;
	}

	public void setTopFleet(String topFleet) {
		this.topFleet = topFleet;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getBindTime() {
		return bindTime;
	}

	public void setBindTime(Date bindTime) {
		this.bindTime = bindTime;
	}

	public Date getUnwrapTime() {
		return unwrapTime;
	}

	public void setUnwrapTime(Date unwrapTime) {
		this.unwrapTime = unwrapTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
