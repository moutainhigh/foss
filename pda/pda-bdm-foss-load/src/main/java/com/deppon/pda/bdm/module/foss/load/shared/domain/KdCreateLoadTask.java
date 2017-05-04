package com.deppon.pda.bdm.module.foss.load.shared.domain;

import java.util.Date;

/** 
  * @ClassName KdCreateLoadTask 
  * @Description TODO 
  * @author cbb 
  * @date 2013-7-26 下午5:04:09 
*/ 
public class KdCreateLoadTask {

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
	 * 创建部门编码
	 */
	private String createDeptCode;
	/**
	 * 月台号
	 */
	private String platformCode;
	/**
	 * 车牌号
	 */
	private String truckCode;
	/**
	 * 收派员编码
	 */
	private String deliveryCode;
	/**
	 * 
	 * 任务号
	 */
	private String taskCode;
	/**
	 * 
	 * 货物类型
	 */
	private String crgType;
	
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
	public String getCreateDeptCode() {
		return createDeptCode;
	}
	public void setCreateDeptCode(String createDeptCode) {
		this.createDeptCode = createDeptCode;
	}
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
	public String getDeliveryCode() {
		return deliveryCode;
	}
	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getCrgType() {
		return crgType;
	}
	public void setCrgType(String crgType) {
		this.crgType = crgType;
	}
	
}
