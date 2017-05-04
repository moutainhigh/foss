package com.deppon.pda.bdm.module.foss.load.shared.domain.derytranload;

import java.util.Date;

/**
 * 提交快递派送装车任务
 * @ClassName KdSubmitLoadTaskEntity.java 
 * @Description 
 * @author 245955
 * @date 2015-4-20
 */
public class SubmitLoadTaskEntity {
	/**
	 * PDA编号
	 */
	private String pdaCode;
	/**
	 * 扫描时间
	 */
	private Date scanTime;
	/**
	 * 提交任务员工编号
	 */
	private String userCode;	
	/**
	 * 派送装车任务号
	 */
	private String taskCode;

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
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	} 
}
