package com.deppon.pda.bdm.module.foss.unload.shared.domain.driverunload;

import java.util.Date;
import java.util.List;

/**
 * 提交接驳卸车任务
 * @ClassName SubmitTranUnloadEntity.java 
 * @Description 
 * @author 245955
 * @date 2015-4-15
 */
public class SubmitTranUnloadEntity {
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
	public List<String> getUserCodes() {
		return userCodes;
	}
	public void setUserCodes(List<String> userCodes) {
		this.userCodes = userCodes;
	}
	public String getIsForceSmt() {
		return isForceSmt;
	}
	public void setIsForceSmt(String isForceSmt) {
		this.isForceSmt = isForceSmt;
	}
}
