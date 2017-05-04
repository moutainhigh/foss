package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 装车任务异常DTO
 * @author 332209-FOSS-ruilibao
 * @date 2017年3月30日
 */
public class LoadTaskExceptionDto implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 车辆任务ID
	 */
	private String id;
	
	/**
	 * 装车任务号
	 */
	private String loadTaskNo;
	
	/**
	 * 任务状态
	 */
	private String loadTaskStatus;
	
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	
	/**
	 * 提交总票数
	 */
	private String submitTotalCount;
	
	/**
	 * 理货员
	 */
	private String loaderName;
	
	/**
	 * 创建任务时间
	 */
	private Date loadStartTime;
	
	/**
	 * 异常原因
	 */
	private String errorMsg;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the loadTaskStatus
	 */
	public String getLoadTaskStatus() {
		return loadTaskStatus;
	}

	/**
	 * @param loadTaskStatus the loadTaskStatus to set
	 */
	public void setLoadTaskStatus(String loadTaskStatus) {
		this.loadTaskStatus = loadTaskStatus;
	}

	/**
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * @return the submitTotalCount
	 */
	public String getSubmitTotalCount() {
		return submitTotalCount;
	}

	/**
	 * @param submitTotalCount the submitTotalCount to set
	 */
	public void setSubmitTotalCount(String submitTotalCount) {
		this.submitTotalCount = submitTotalCount;
	}

	/**
	 * @return the loaderName
	 */
	public String getLoaderName() {
		return loaderName;
	}

	/**
	 * @param loaderName the loaderName to set
	 */
	public void setLoaderName(String loaderName) {
		this.loaderName = loaderName;
	}

	/**
	 * @return the loadStartTime
	 */
	public Date getLoadStartTime() {
		return loadStartTime;
	}

	/**
	 * @param loadStartTime the loadStartTime to set
	 */
	public void setLoadStartTime(Date loadStartTime) {
		this.loadStartTime = loadStartTime;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
