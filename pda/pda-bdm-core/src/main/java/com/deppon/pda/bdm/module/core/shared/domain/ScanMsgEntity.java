package com.deppon.pda.bdm.module.core.shared.domain;

import java.util.Date;

/**
 * 扫描信息实体类
 * 
 * @author gaojia
 * @date Sep 6,2012 10:00:30 AM
 * @version 1.0
 * @since
 */
public class ScanMsgEntity extends DomainEntity {

	/**
	 * @description
	 */

	private static final long serialVersionUID = -8053509850182238482L;

	/**
	 * 运单号
	 */
	private String wblCode;

	/**
	 * 扫描人工号
	 */
	private String scanUser;
	/**
	 * pda编号
	 */
	private String pdaCode;
	/**
	 * 扫描部门
	 */
	private String deptCode;
	/**
	 * 标签号
	 */
	private String labelCode;
	/**
	 * 扫描标识
	 */
	private String scanFlag;
	/**
	 * 扫描时间
	 */
	private Date scanTime;
	/**
	 * 扫描类型
	 */
	private String scanType;
	/**
	 * 同步状态
	 */
	private int syncStatus;
	/**
	 * 上传时间
	 */
	private Date uploadTime;

	/**
	 * 任务号
	 */
	private String taskCode;
	/**
	 * 扫描状态
	 */
	private String scanStatus;
	/**
	 * 件数
	 */
	private int pieces;
	
	/**
	 * 重发次数
	 */
	private int count;
	
	/**
	 * 设备型号
	 */
	private String pdaType;

	public String getWblCode() {
		return wblCode;
	}

	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}

	public String getScanUser() {
		return scanUser;
	}

	public void setScanUser(String scanUser) {
		this.scanUser = scanUser;
	}

	public String getPdaCode() {
		return pdaCode;
	}

	public void setPdaCode(String pdaCode) {
		this.pdaCode = pdaCode;
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

	public String getScanType() {
		return scanType;
	}

	public void setScanType(String scanType) {
		this.scanType = scanType;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getScanStatus() {
		return scanStatus;
	}

	public void setScanStatus(String scanStatus) {
		this.scanStatus = scanStatus;
	}

	public int getPieces() {
		return pieces;
	}

	public void setPieces(int pieces) {
		this.pieces = pieces;
	}


	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(int syncStatus) {
		this.syncStatus = syncStatus;
	}

	public String getPdaType() {
		return pdaType;
	}

	public void setPdaType(String pdaType) {
		this.pdaType = pdaType;
	}
	
	
	
}