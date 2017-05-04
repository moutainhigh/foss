package com.deppon.pda.bdm.module.foss.clear.shared.domain;

import java.util.Date;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

public class FnshClrTaskScanEntity extends ScanMsgEntity{

	/**
	 * 扫描标识
	 */
	private String scanFlag;
	/**
	 * 扫描时间
	 */
	private Date scanTime;
	/**
	 * 清仓任务编号
	 */
	private String taskCode;
	
	public String getScanFlag() {
		return scanFlag;
	}
	public void setScanFlag(String scanFlag) {
		this.scanFlag = scanFlag;
	}
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

	
	
}
