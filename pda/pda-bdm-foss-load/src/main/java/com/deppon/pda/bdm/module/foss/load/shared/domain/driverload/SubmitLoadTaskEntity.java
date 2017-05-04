package com.deppon.pda.bdm.module.foss.load.shared.domain.driverload;

import java.util.Date;

public class SubmitLoadTaskEntity {
	/**
	 * 扫描时间
	 */
	private Date scanTime;
	/**
	 * 任务号
	 */
	private String taskCode;
	/**
	 * 是否强制提交
	 */
	private String isForceSmt;

	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getIsForceSmt() {
		return isForceSmt;
	}
	public void setIsForceSmt(String isForceSmt) {
		this.isForceSmt = isForceSmt;
	}
	
	
}
