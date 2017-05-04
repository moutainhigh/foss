package com.deppon.pda.bdm.module.foss.load.shared.domain;

import java.util.Date;

/**
 * 提交装车任务
 * 
 * @author 335284
 * @date 2016年7月20日 16:11:53
 * @version 2.0
 * @since 2016年7月15日
 */
public class SubmitLoadTask {

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
	 * 0 零担 1 快递 2 合车
	 */
	private Integer sendType;
	/**
	 * 车牌号
	 */
	private String truckCode;

	/**
	 * 操作部门
	 */
	private String deptCode;

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getTruckCode() {
		return truckCode;
	}

	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}

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

	public String getIsForceSmt() {
		return isForceSmt;
	}

	public void setIsForceSmt(String isForceSmt) {
		this.isForceSmt = isForceSmt;
	}

	/**
	 * @return the sendType
	 */
	public Integer getSendType() {
		return sendType;
	}

	/**
	 * @param sendType
	 *            the sendType to set
	 */
	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}

}