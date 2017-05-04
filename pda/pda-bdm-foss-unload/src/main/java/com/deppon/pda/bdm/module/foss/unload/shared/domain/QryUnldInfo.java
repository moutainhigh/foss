package com.deppon.pda.bdm.module.foss.unload.shared.domain;

import java.util.Date;

/**
 * 刷新卸车任务
 * @author gaojia
 * @date Sep 6,2012 14:33:30 PM
 * @version 1.0
 * @since
 */

public class QryUnldInfo {

	/**
	 * 任务号
	 */
	private String taskCode;
	/**
	 * 
	 * 扫描时间
	 */
	private Date scanTime;
	public String getTaskCode(){
		return taskCode;
	}

	public void setTaskCode(String newVal){
		taskCode = newVal;
	}

	public Date getScanTime() {
		return scanTime;
	}

	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	
}