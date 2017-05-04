package com.deppon.pda.bdm.module.foss.unload.shared.domain;

import java.util.Date;
import java.util.List;

/**
 * 提交卸车任务
 * @author gaojia
 * @date Sep 6,2012 14:33:30 PM
 * @version 1.0
 * @since
 */
public class SubmitUnldTask {
	
	/**
	 * PDA编号
	 */
	private String pdaCode;
	/**
	 * 扫描时间
	 */
	private Date scanTime;
	/**
	 * 提交任务员工号
	 */
	private String userCode;
	/**
	 * 卸车任务编号
	 */
	private String taskCode;
	/**
	 * 员工工号
	 */
	private List<String> userCodes;
	/**
	 * 是否强制提交
	 */
	private String isForceSmt;
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
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public List<String> getUserCodes() {
		return userCodes;
	}
	public void setUserCodes(List<String> userCodes) {
		this.userCodes = userCodes;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getIsForceSmt() {
		return isForceSmt;
	}
	public void setIsForceSmt(String isForceSmt) {
		this.isForceSmt = isForceSmt;
	}
	
	
	
}