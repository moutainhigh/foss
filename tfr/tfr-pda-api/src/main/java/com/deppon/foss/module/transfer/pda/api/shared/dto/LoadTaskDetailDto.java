package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.io.Serializable;
import java.util.Date;





/**
* @description 同步给悟空系统创建交接单详细
* @version 1.0
* @author 328864-foss-xieyang
* @update 2016年4月28日 上午10:42:38
*/
public class LoadTaskDetailDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 装车任务编号 1
	private String loadTaskNo; 
	// 车牌号1
	private String vehicleNo; 
	// 装车部门编号
	private String origOrgCode; 
	// 装车开始时间
	private Date loadBeginTime; 
	// 到达部门编号
	private String arrivalDeptNo;
	// 装车部门名称
	private String loadDeptName;
	// 到达部门名称
	private String arrivalDeptName; 
	// 月台号 1
	private String platformNo; 
	// 任务类型
	private String taskType; 
	// 发车计划编号
	private String truckOutboundPlanNo; 

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


	public Date getLoadBeginTime() {
		if (loadBeginTime == null) {
			return null;
		}
		return (Date) loadBeginTime.clone();
	}

	public void setLoadBeginTime(Date loadBeginTime) {
		if (loadBeginTime == null) {
			this.loadBeginTime = null;
		} else {
			this.loadBeginTime = (Date) loadBeginTime.clone();
		}
	}



}
