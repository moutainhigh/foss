package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.util.Date;

/**
 * @description 完成装车任务 20160709版
 * @version 1.0
 * @author 328864-foss-xieyang
 * @update 2016年5月5日 上午7:53:17
 */
public class SmtWKLoadTaskDto {

	/**
	 * @fields serialVersionUID
	 * @author 328864-foss-xieyang
	 * @update 2016年5月5日 上午7:53:12
	 * @version V1.0
	 */

	private static final long serialVersionUID = 1L;

//	String taskNo,Date loadEndTime,String deviceNo,String loaderCode
	
	/** 装车编号 **/
	private String loadTaskNo;
	/** 装车结束时间*/
	private Date loadEndTime;
	/** 操作部门编号 **/
	private String operationOrgCode;
	/** 操作时间 **/
	private String operationTime;
	/** 操作人工号 **/
	private String operatorNo;
	/** 装车类型 快递 1, 零担0, 合单2 **/
	private Integer sendType;
	private boolean isCancel;
	
	private String loaderCode;
	/** 任务状态 **/
	private String taskStatus;
	
	private String deviceNo;

	/**
	 * @description 获取最后装车时间
	 * @return
	 * @version 1.0
	 * @author 328864-foss-xieyang
	 * @update 2016年5月5日 上午7:56:48
	 */
	public Date getLoadEndTime() {
		if (loadEndTime == null) {
			return null;
		}
		return (Date) loadEndTime.clone();
	}

	/**
	 * @description 设置最后装车时间
	 * @param loadEndTime
	 * @version 1.0
	 * @author 328864-foss-xieyang
	 * @update 2016年5月5日 上午7:56:46
	 */
	public void setLoadEndTime(Date loadEndTime) {
		if (loadEndTime != null) {
			this.loadEndTime = (Date) loadEndTime.clone();
		} else {
			this.loadEndTime = null;
		}
	}

	/**
	 * @description 获取装车人编号
	 * @return
	 * @version 1.0
	 * @author 328864-foss-xieyang
	 * @update 2016年5月5日 上午7:56:43
	 */
	public String getLoaderCode() {
		return loaderCode;
	}

	/**
	 * @description 设置装车人编号
	 * @param loaderCode
	 * @version 1.0
	 * @author 328864-foss-xieyang
	 * @update 2016年5月5日 上午7:56:41
	 */
	public void setLoaderCode(String loaderCode) {
		this.loaderCode = loaderCode;
	}

	/**
	 * @description 获取装车里面的东西是合单,快递还是零担
	 * @return
	 * @version 1.0
	 * @author 328864-foss-xieyang
	 * @update 2016年5月5日 上午7:56:38
	 */
	public Integer getSendType() {
		return sendType;
	}

	/**
	 * @description 设置装车里面的东西是合单,快递还是零担
	 * @param sendType
	 * @version 1.0
	 * @author 328864-foss-xieyang
	 * @update 2016年5月5日 上午7:56:36
	 */
	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}

	public String getLoadTaskNo() {
		return loadTaskNo;
	}

	public void setLoadTaskNo(String loadTaskNo) {
		this.loadTaskNo = loadTaskNo;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getOperationOrgCode() {
		return operationOrgCode;
	}

	public void setOperationOrgCode(String operationOrgCode) {
		this.operationOrgCode = operationOrgCode;
	}

	public String getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}

	public String getOperatorNo() {
		return operatorNo;
	}

	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}

	public boolean isCancel() {
		return isCancel;
	}

	public void setCancel(boolean isCancel) {
		this.isCancel = isCancel;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	@Override
	public String toString() {
		return "SmtWKLoadTaskDto [loadTaskNo=" + loadTaskNo + ", loadEndTime="
				+ loadEndTime + ", operationOrgCode=" + operationOrgCode
				+ ", operationTime=" + operationTime + ", operatorNo="
				+ operatorNo + ", sendType=" + sendType + ", isCancel="
				+ isCancel + ", loaderCode=" + loaderCode + ", taskStatus="
				+ taskStatus + ", deviceNo=" + deviceNo + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
