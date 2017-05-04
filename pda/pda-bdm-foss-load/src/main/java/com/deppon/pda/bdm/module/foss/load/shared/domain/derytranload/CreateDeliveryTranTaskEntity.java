package com.deppon.pda.bdm.module.foss.load.shared.domain.derytranload;

import java.util.Date;
import java.util.List;


/**
 * 创建派件交接任务
 * @ClassName CreateDeliveryTranTaskEntity.java 
 * @Description 
 * @author 245955
 * @date 2015-4-16
 */
public class CreateDeliveryTranTaskEntity {	
	/**
	 * 扫描时间
	 */
	private Date scanTime;
	/**
	 * PDA编号
	 */
	private String pdaCode;
	/**
	 * 收派员编号
	 */
	private String userCode;
	/**
	 * 车牌号
	 */
	private String truckCode;
	/**
	 * 理货员工工号
	 */
	private List<LoaderModel> userCodes;
	/**
	 *交接员工工号
	 */
	private String tallyerCode;
	/**
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

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getTruckCode() {
		return truckCode;
	}

	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}

	public List<LoaderModel> getUserCodes() {
		return userCodes;
	}

	public void setUserCodes(List<LoaderModel> userCodes) {
		this.userCodes = userCodes;
	}
	public String getTallyerCode() {
		return tallyerCode;
	}

	public void setTallyerCode(String tallyerCode) {
		this.tallyerCode = tallyerCode;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	
}
