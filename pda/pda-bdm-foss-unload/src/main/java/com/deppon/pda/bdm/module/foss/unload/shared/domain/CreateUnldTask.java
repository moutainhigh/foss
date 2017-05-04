package com.deppon.pda.bdm.module.foss.unload.shared.domain;

import java.util.Date;
import java.util.List;

/**
 * 建立卸车任务
 * @author gaojia
 * @date Sep 6,2012 14:33:30 PM
 * @version 1.0
 * @since
 */
public class CreateUnldTask {
	
	/**
	 * 扫描时间
	 */
	private Date scanTime;
	
	/**
	 * 建立任务员工号
	 */
	
	private String userCode;
	/**
	 * PDA编号
	 */
	private String pdaCode;
	/**
	 * 月台号
	 */
	private String platformCode;
	/**
	 * 交接单号
	 */
	private List<String> receiptCodes;
	/**
	 * 车牌号
	 */
	private String truckCode;
	/**
	 * 员工编号
	 */
	private List<UnloaderModel> userCodes;
	/**
	 * 
	 * 任务号
	 */
	private String taskCode;
	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	public String getPdaCode() {
		return pdaCode;
	}
	public void setPdaCode(String pdaCode) {
		this.pdaCode = pdaCode;
	}
	public String getPlatformCode() {
		return platformCode;
	}
	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}
	public List<String> getReceiptCodes() {
		return receiptCodes;
	}
	public void setReceiptCodes(List<String> receiptCodes) {
		this.receiptCodes = receiptCodes;
	}
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	
	public List<UnloaderModel> getUserCodes() {
		return userCodes;
	}
	public void setUserCodes(List<UnloaderModel> userCodes) {
		this.userCodes = userCodes;
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