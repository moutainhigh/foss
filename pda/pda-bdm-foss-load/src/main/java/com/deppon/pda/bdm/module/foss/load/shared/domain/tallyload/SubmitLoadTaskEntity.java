package com.deppon.pda.bdm.module.foss.load.shared.domain.tallyload;

import java.util.Date;

/**
 * 接驳-提交装车任务
 * @ClassName TranSubmitLoadTaskEntity.java 
 * @Description 
 * @author 245955
 * @date 2015-4-14
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
	 * 任务号
	 */
	private String taskCode;
	/**
	 * 是否强制提交
	 */
	private String isForceSmt;
	/**
	 * 车牌号
	 */
	private String truckCode;

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

	public String getIsForceSmt() {
		return isForceSmt;
	}

	public void setIsForceSmt(String isForceSmt) {
		this.isForceSmt = isForceSmt;
	}

	public String getTruckCode() {
		return truckCode;
	}

	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}

}
