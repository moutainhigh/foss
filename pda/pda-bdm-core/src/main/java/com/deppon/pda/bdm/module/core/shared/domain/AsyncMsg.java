package com.deppon.pda.bdm.module.core.shared.domain;

import java.util.Date;

import com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage;

public class AsyncMsg  extends QueueMessage{
	
//	private String id;

	/**
	 * 操作类型
	 */
	private String operType;
	/**
	 * PDA编号
	 */
	private String pdaCode;
	/**
	 * 站点编号
	 */
	private String deptCode;
	/**
	 * 员工编号
	 */
	private String userCode;
	/**
	 * 设备型号
	 */
	private String pdaType;
	/**
	 * 设备版本号
	 */
	private String pgmVer;
	/**
	 * 请求内容
	 */
	private String content;
	/**
	 * 上传时间
	 */
	private Date uploadTime;
	/**
	 * 同步状态
	 */
	private int asyncStatus;
	
	/**
	 * 同步次数
	 */
	private int syncCount;
	
	/**
	 * 用户类型
	 */
	private String userType;
	
	private String taskCode;
	

//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getPdaCode() {
		return pdaCode;
	}

	public void setPdaCode(String pdaCode) {
		this.pdaCode = pdaCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPdaType() {
		return pdaType;
	}

	public void setPdaType(String pdaType) {
		this.pdaType = pdaType;
	}

	public String getPgmVer() {
		return pgmVer;
	}

	public void setPgmVer(String pgmVer) {
		this.pgmVer = pgmVer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public int getAsyncStatus() {
		return asyncStatus;
	}

	public void setAsyncStatus(int asyncStatus) {
		this.asyncStatus = asyncStatus;
	}

	public int getSyncCount() {
		return syncCount;
	}

	public void setSyncCount(int syncCount) {
		this.syncCount = syncCount;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
}
