package com.deppon.pda.bdm.module.foss.unload.shared.domain.driverunload;


import java.util.Date;

/**
 * 下拉接驳卸车任务
 * @ClassName DownUnloadDetailEntity.java 
 * @Description 
 * @author 245955
 * @date 2015-4-14
 */
public class DownUnloadTaskEntity {

	/**
	 * 任务号
	 */
	private String taskCode;
	/**
	 * 
	 * 扫描时间
	 */
	private Date scanTime;
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	
}
