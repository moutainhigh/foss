package com.deppon.foss.module.transfer.load.dubbo.api.define;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description 同步给悟空系统的操作人员信息 20160709版
 * @version 1.0
 * @author 328864-foss-xieyang
 * @update 2016年4月28日 上午10:43:33
 */
public class LoadTaskCreateDto implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -7757945819679546473L;
	
	// 装车任务编号 1
	private String loadTaskNo;
	// 车牌号1
	private String vehicleNo;
	// 任务类型
	private String taskType;
	// 月台号 1
	private String platformNo;
	// 装车开始时间
	private Date loadBeginTime;
	// 装车部门编号
	private String origOrgCode;
	// 装车部门名称
	private String loadDeptName;
	// 发车计划编号
	private String truckOutboundPlanNo;
	// 到达部门编号
	private String arrivalDeptNo;
	// 到达部门名称
	private String arrivalDeptName;
	
	/**loaderCodes*/
	private List<LoaderDto> loaders;                      //理货员
	
	private String operatorNo;// 操作人工号
	private String operationOrgCode;// 操作部门编号
	private String operationTime;// 操作时间
	/** 装车类型 快递 1, 零担0, 合单2 **/
	private Integer sendType;
	
	private String operatorName;// 操作人名称
	private String operationOrgName;// 操作部门名称

	private String operationDevice;// 操作设备
	private String operationDeviceCode;// 操作设备编码
	private String dataVersion;// 版本

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

	public String getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
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

	public String getDataVersion() {
		return dataVersion;
	}

	public void setDataVersion(String dataVersion) {
		this.dataVersion = dataVersion;
	}

	public String getLoadTaskNo() {
		return loadTaskNo;
	}

	public void setLoadTaskNo(String loadTaskNo) {
		this.loadTaskNo = loadTaskNo;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getOrigOrgCode() {
		return origOrgCode;
	}

	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	public Date getLoadBeginTime() {
		return loadBeginTime;
	}

	public void setLoadBeginTime(Date loadBeginTime) {
		this.loadBeginTime = loadBeginTime;
	}

	public String getArrivalDeptNo() {
		return arrivalDeptNo;
	}

	public void setArrivalDeptNo(String arrivalDeptNo) {
		this.arrivalDeptNo = arrivalDeptNo;
	}

	public String getLoadDeptName() {
		return loadDeptName;
	}

	public void setLoadDeptName(String loadDeptName) {
		this.loadDeptName = loadDeptName;
	}

	public String getArrivalDeptName() {
		return arrivalDeptName;
	}

	public void setArrivalDeptName(String arrivalDeptName) {
		this.arrivalDeptName = arrivalDeptName;
	}

	public String getPlatformNo() {
		return platformNo;
	}

	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTruckOutboundPlanNo() {
		return truckOutboundPlanNo;
	}

	public void setTruckOutboundPlanNo(String truckOutboundPlanNo) {
		this.truckOutboundPlanNo = truckOutboundPlanNo;
	}

	public Integer getSendType() {
		return sendType;
	}

	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}

	public List<LoaderDto> getLoaders() {
		return loaders;
	}

	public void setLoaders(List<LoaderDto> loaders) {
		this.loaders = loaders;
	}



	
}
