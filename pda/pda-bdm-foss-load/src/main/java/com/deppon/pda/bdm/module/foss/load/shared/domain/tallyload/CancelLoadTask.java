package com.deppon.pda.bdm.module.foss.load.shared.domain.tallyload;

import java.util.Date;

/**
 * 撤销装车任务
 * @ClassName CancelLoadTask.java 
 * @Description 
 * @author 245955
 * @date 2015-4-27
 */
public class CancelLoadTask {
	
	/**
	 * PDA编号
	 */
	private String pdaCode;
	/**
	 * 扫描时间
	 */
	private Date scanTime;
	/**
	 * 任务号
	 */
	private String taskCode;
	/**
	 * 员工编号
	 */
	private String userCode;
	
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
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	
}