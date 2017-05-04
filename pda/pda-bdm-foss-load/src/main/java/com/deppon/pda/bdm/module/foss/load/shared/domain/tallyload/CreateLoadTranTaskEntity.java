package com.deppon.pda.bdm.module.foss.load.shared.domain.tallyload;

import java.util.Date;
import java.util.List;

import com.deppon.pda.bdm.module.foss.load.shared.domain.LoaderModel;
/**
 * 创建理货员PDA快递接驳装车
 * @description 
 * @version 1.0
 * @author 245955
 * @update 2015-4-10 上午 
 */
public class CreateLoadTranTaskEntity {

	/**
	 * 扫描时间
	 */
	private Date scanTime;
	/**
	 * PDA编号
	 */
	private String pdaCode;
	/**
	 * 创建员工编号
	 */
	private String userCode;
	/**
	 * 月台号
	 */
	private String platformCode;
	/**
	 * 任务类型
	 */
	private String loadType;
	/**
	 * 车牌号
	 */
	private String truckCode;
	/**
	 * 理货员工工号
	 */
	private List<LoaderModel> userCodes;
	/**
	 * 任务号
	 */
	private String taskCode;
	/**
	 * 到达部门
	 */
	private String deptType;
	/**
	 * 接驳点
	 */
    private List<String> arrDeptCode; 

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

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public String getLoadType() {
		return loadType;
	}

	public void setLoadType(String loadType) {
		this.loadType = loadType;
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

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	public List<String> getArrDeptCode() {
		return arrDeptCode;
	}

	public void setArrDeptCode(List<String> arrDeptCode) {
		this.arrDeptCode = arrDeptCode;
	}

}
