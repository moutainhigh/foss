package com.deppon.pda.bdm.module.foss.load.shared.domain;

import java.util.List;

/** 
  * @ClassName KdGetLoadTask 
  * @Description TODO 
  * @author cbb 
  * @date 2013-7-26 下午5:04:16 
*/ 
public class KdGetLoadTask {
	/**
	 * 月台号
	 */
	private String platformCode;
	/**
	 * 车牌号
	 */
	private String truckCode;
	/**
	 * 派送单号
	 */
	private String deryListCode;
	/**
	 * 装车任务状态
	 */
	private String status;
	/**
	 * 任务号
	 */
	private String taskCode;
	/**
	 * 装车类型
	 */
	private String loadType;
	/**
	 * 目的站编码
	 */
	private List<String> arrDeptCode;
	/**
	 * 目的站名称
	 */
	private List<String> arrDeptName;
	public String getPlatformCode() {
		return platformCode;
	}
	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	public String getDeryListCode() {
		return deryListCode;
	}
	public void setDeryListCode(String deryListCode) {
		this.deryListCode = deryListCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getLoadType() {
		return loadType;
	}
	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}
	public List<String> getArrDeptCode() {
		return arrDeptCode;
	}
	public void setArrDeptCode(List<String> arrDeptCode) {
		this.arrDeptCode = arrDeptCode;
	}
	public List<String> getArrDeptName() {
		return arrDeptName;
	}
	public void setArrDeptName(List<String> arrDeptName) {
		this.arrDeptName = arrDeptName;
	}
}
