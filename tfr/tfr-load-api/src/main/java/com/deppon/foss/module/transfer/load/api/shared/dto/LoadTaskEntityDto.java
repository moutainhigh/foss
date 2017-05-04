package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.util.Date;

/**
 * 装车任务异常DTO
 * @author 332209-FOSS-ruilibao
 * @date 2017年3月30日
 */
public class LoadTaskEntityDto extends LoadTaskExceptionDto{

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;

	private Date loadEndTime;
	private String origOrgCode;
	private String destOrgCode;
	private String loadTaskType;
	private String loaderCode;
	private int sendType;
	private String deviceNo;
	
	public int getSendType() {
		return sendType;
	}
	public void setSendType(int sendType) {
		this.sendType = sendType;
	}
	public String getLoaderCode() {
		return loaderCode;
	}
	public void setLoaderCode(String loaderCode) {
		this.loaderCode = loaderCode;
	}
	public Date getLoadEndTime() {
		return loadEndTime;
	}
	public void setLoadEndTime(Date loadEndTime) {
		this.loadEndTime = loadEndTime;
	}
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	public String getDestOrgCode() {
		return destOrgCode;
	}
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	public String getLoadTaskType() {
		return loadTaskType;
	}
	public void setLoadTaskType(String loadTaskType) {
		this.loadTaskType = loadTaskType;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
}
