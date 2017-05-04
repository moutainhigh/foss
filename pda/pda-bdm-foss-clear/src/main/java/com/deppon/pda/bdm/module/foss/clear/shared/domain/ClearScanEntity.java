package com.deppon.pda.bdm.module.foss.clear.shared.domain;

import java.util.Date;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

public class ClearScanEntity extends ScanMsgEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String wblCode;
	private String labelCode;
	private String scanFlag;
	private Date scanTime;
	private String scanStatus;
	private String taskCode;
	private String isMore;
	// 2013-08-14 xujun 新增库位属性
	private String position;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWblCode() {
		return wblCode;
	}
	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}
	public String getLabelCode() {
		return labelCode;
	}
	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}
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
	public String getScanStatus() {
		return scanStatus;
	}
	public void setScanStatus(String scanStatus) {
		this.scanStatus = scanStatus;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getIsMore() {
		return isMore;
	}
	public void setIsMore(String isMore) {
		this.isMore = isMore;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}

}
