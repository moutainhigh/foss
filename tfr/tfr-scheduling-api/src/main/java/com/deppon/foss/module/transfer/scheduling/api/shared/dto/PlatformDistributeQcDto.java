package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class PlatformDistributeQcDto implements Serializable {

	private static final long serialVersionUID = -7449153742750850790L;

	/**
	 * 到达部门code
	 */
	private String destDeptCode;

	/**
	 * 预计还有都少分钟到达
	 */
	private int minEstimatedArrive;

	/**
	 * 最早预计到达时间(即当前时间)
	 */
	private Date beginEstimatedArriveTime;

	/**
	 * 最晚预计到达时间(查询的当前时间+minEstimatedArrive)
	 */
	private Date endEstimatedArriveTime;

	/**
	 * 交接编号(交接单或配载单)
	 */
	private String handoverNo;

	/**
	 * 车牌号
	 */
	private String vehicleNo;

	/**
	 * 出发部门code
	 */
	private String origDeptCode;

	/**
	 * 状态:在途、到达
	 */
	private String status;

	/**
	 * 车辆所属类型
	 */
	private String vehicleOwnerType;

	/**
	 * 月台的使用开始时间，用于查询到达未分配
	 */
	private Date useStartTime;

	public Date getUseStartTime() {
		return useStartTime;
	}

	public void setUseStartTime(Date useStartTime) {
		this.useStartTime = useStartTime;
	}

	public int getMinEstimatedArrive() {
		return minEstimatedArrive;
	}

	public void setMinEstimatedArrive(int minEstimatedArrive) {
		this.minEstimatedArrive = minEstimatedArrive;
	}

	public Date getBeginEstimatedArriveTime() {
		return beginEstimatedArriveTime;
	}

	public void setBeginEstimatedArriveTime(Date beginEstimatedArriveTime) {
		this.beginEstimatedArriveTime = beginEstimatedArriveTime;
	}

	public Date getEndEstimatedArriveTime() {
		return endEstimatedArriveTime;
	}

	public void setEndEstimatedArriveTime(Date endEstimatedArriveTime) {
		this.endEstimatedArriveTime = endEstimatedArriveTime;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getOrigDeptCode() {
		return origDeptCode;
	}

	public void setOrigDeptCode(String origDeptCode) {
		this.origDeptCode = origDeptCode;
	}

	public String getDestDeptCode() {
		return destDeptCode;
	}

	public void setDestDeptCode(String destDeptCode) {
		this.destDeptCode = destDeptCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVehicleOwnerType() {
		return vehicleOwnerType;
	}

	public void setVehicleOwnerType(String vehicleOwnerType) {
		this.vehicleOwnerType = vehicleOwnerType;
	}

	public String getHandoverNo() {
		return handoverNo;
	}

	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

}
