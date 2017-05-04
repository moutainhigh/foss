/**
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *      
 *   http://www.deppon.com
 */
package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 这里用一句话描述这个类的作用
 * @version 1.0
 * @author 328768-foss-gaojianfu
 * @update 2016-5-12 下午9:37:35
 */
public class SyncPDAUnloadTaskDataToWkDto implements Serializable {

	/**
	 * @fields serialVersionUID
	 * @author 328768-foss-gaojianfu
	 * @update 2016-5-12 下午9:37:35
	 * @version V1.0
	 */

	private static final long serialVersionUID = 1L;

	/** 卸车任务编号 */
	private String unloadTaskNo;
	/** 卸车结束时间 */
	private Date unloadEndTime;
	/** 操作设备 */
	private String operationDevice;
	/** 操作设备编码 */
	private String operationDeviceCode;
	/** 离开任务时间*/
	private Date leaveTaskTime;
	/** 操作码*/ 
	private String operationCode;
	/** 辅助操作码*/
	private String operationAssistCode;
	/**操作人工号*/
	private String operatorNo;
	/**操作人名称*/
	private String operatorName;
	/**操作部门编号*/
	private String operationOrgCode;
	/**操作部门名称*/
	private String operationOrgName;
	
	private String notes;
	
	private String beException;

	public String getUnloadTaskNo() {
		return unloadTaskNo;
	}

	public void setUnloadTaskNo(String unloadTaskNo) {
		this.unloadTaskNo = unloadTaskNo;
	}

	
	public Date getUnloadEndTime() {
		return unloadEndTime;
	}

	public void setUnloadEndTime(Date unloadEndTime) {
		this.unloadEndTime = unloadEndTime;
	}

	public String getOperationDevice() {
		return operationDevice;
	}

	public void setOperationDevice(String operationDevice) {
		this.operationDevice = operationDevice;
	}

	public String getOperationDeviceCode() {
		return operationDeviceCode;
	}

	public void setOperationDeviceCode(String operationDeviceCode) {
		this.operationDeviceCode = operationDeviceCode;
	}

	public Date getLeaveTaskTime() {
		return leaveTaskTime;
	}

	public void setLeaveTaskTime(Date leaveTaskTime) {
		this.leaveTaskTime = leaveTaskTime;
	}

	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	public String getOperationAssistCode() {
		return operationAssistCode;
	}

	public void setOperationAssistCode(String operationAssistCode) {
		this.operationAssistCode = operationAssistCode;
	}

	public String getOperatorNo() {
		return operatorNo;
	}

	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperationOrgCode() {
		return operationOrgCode;
	}

	public void setOperationOrgCode(String operationOrgCode) {
		this.operationOrgCode = operationOrgCode;
	}

	public String getOperationOrgName() {
		return operationOrgName;
	}

	public void setOperationOrgName(String operationOrgName) {
		this.operationOrgName = operationOrgName;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getBeException() {
		return beException;
	}

	public void setBeException(String beException) {
		this.beException = beException;
	}

}
