package com.deppon.pda.bdm.module.foss.clear.shared.domain;

import java.util.Date;

/**
 * 提交清仓
 * 
 * @author xujun
 * @date Sep 6,2012 14:33:30 PM
 * @version 1.0
 * @since
 */
public class SubmitClearTask {

	/**
	 * 扫描时间
	 */
	private Date smtTime;
	/**
	 * 任务号
	 */
	private String taskCode;
	
	/**
	 * 是否强制提交
	 */
	private String isForceSmt;
	
	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public Date getSmtTime() {
		return smtTime;
	}

	public void setSmtTime(Date smtTime) {
		this.smtTime = smtTime;
	}

	public String getIsForceSmt() {
		return isForceSmt;
	}

	public void setIsForceSmt(String isForceSmt) {
		this.isForceSmt = isForceSmt;
	}
	
	
}