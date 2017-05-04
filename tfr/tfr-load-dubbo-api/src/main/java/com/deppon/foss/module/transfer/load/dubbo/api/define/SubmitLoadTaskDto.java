package com.deppon.foss.module.transfer.load.dubbo.api.define;

import java.io.Serializable;
import java.util.Date;

/**
 * 装车提交DTO
 * @author 332209-FOSS-ruilibao
 * @date 2017年3月29日
 */
public class SubmitLoadTaskDto implements Serializable{

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 8370702305795717018L;
	
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
	
	/** 车牌号 **/
	private String deviceNo;
	
	/** 异常信息 **/
	private String exceptionMsg;
	
	/** 提交总票数 **/
	private String submitTotalTicketCount;
	
	private String isPageFlag;
	
	/**
	 * @return the isPageFlag
	 */
	public String getIsPageFlag() {
		return isPageFlag;
	}

	/**
	 * @param isPageFlag the isPageFlag to set
	 */
	public void setIsPageFlag(String isPageFlag) {
		this.isPageFlag = isPageFlag;
	}

	/**
	 * @return the loadTaskNo
	 */
	public String getLoadTaskNo() {
		return loadTaskNo;
	}

	/**
	 * @param loadTaskNo the loadTaskNo to set
	 */
	public void setLoadTaskNo(String loadTaskNo) {
		this.loadTaskNo = loadTaskNo;
	}

	/**
	 * @return the loadEndTime
	 */
	public Date getLoadEndTime() {
		return loadEndTime;
	}

	/**
	 * @param loadEndTime the loadEndTime to set
	 */
	public void setLoadEndTime(Date loadEndTime) {
		this.loadEndTime = loadEndTime;
	}

	/**
	 * @return the operationOrgCode
	 */
	public String getOperationOrgCode() {
		return operationOrgCode;
	}

	/**
	 * @param operationOrgCode the operationOrgCode to set
	 */
	public void setOperationOrgCode(String operationOrgCode) {
		this.operationOrgCode = operationOrgCode;
	}

	/**
	 * @return the operationTime
	 */
	public String getOperationTime() {
		return operationTime;
	}

	/**
	 * @param operationTime the operationTime to set
	 */
	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}

	/**
	 * @return the operatorNo
	 */
	public String getOperatorNo() {
		return operatorNo;
	}

	/**
	 * @param operatorNo the operatorNo to set
	 */
	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}

	/**
	 * @return the sendType
	 */
	public Integer getSendType() {
		return sendType;
	}

	/**
	 * @param sendType the sendType to set
	 */
	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}

	/**
	 * @return the isCancel
	 */
	public boolean isCancel() {
		return isCancel;
	}

	/**
	 * @param isCancel the isCancel to set
	 */
	public void setCancel(boolean isCancel) {
		this.isCancel = isCancel;
	}

	/**
	 * @return the loaderCode
	 */
	public String getLoaderCode() {
		return loaderCode;
	}

	/**
	 * @param loaderCode the loaderCode to set
	 */
	public void setLoaderCode(String loaderCode) {
		this.loaderCode = loaderCode;
	}

	/**
	 * @return the taskStatus
	 */
	public String getTaskStatus() {
		return taskStatus;
	}

	/**
	 * @param taskStatus the taskStatus to set
	 */
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	/**
	 * @return the deviceNo
	 */
	public String getDeviceNo() {
		return deviceNo;
	}

	/**
	 * @param deviceNo the deviceNo to set
	 */
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	/**
	 * @return the exceptionMsg
	 */
	public String getExceptionMsg() {
		return exceptionMsg;
	}

	/**
	 * @param exceptionMsg the exceptionMsg to set
	 */
	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	/**
	 * @return the submitTotalTicketCount
	 */
	public String getSubmitTotalTicketCount() {
		return submitTotalTicketCount;
	}

	/**
	 * @param submitTotalTicketCount the submitTotalTicketCount to set
	 */
	public void setSubmitTotalTicketCount(String submitTotalTicketCount) {
		this.submitTotalTicketCount = submitTotalTicketCount;
	}
}
