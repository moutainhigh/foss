package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;


public class SendInfoSearchRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9167660175513981691L;
	/**
	 * 交接单号
	 */
	private String handoverBillNo;
	/**
	 * 出发开始时间
	 */
	private String  startDepartureTime;
	/**
	 * 出发结束时间
	 */
	private String  endDepartureTime;
	/**
	 * 预计到达开始时间
	 */
	private String  startArrivalTime;
	/**
	 * 预计到达结束时间
	 */
	private String  endArrivalTime;
	/**
	 * 提货网点
	 */
	private String pkpOrgCode;
	/**
	 * 出发部门编码 departOrgCode
	 */
	private String departOrgCode;
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 目的外场
	 */
	private String arrivalOrgCode;
	
	public String getArrivalOrgCode() {
		return arrivalOrgCode;
	}
	public void setArrivalOrgCode(String arrivalOrgCode) {
		this.arrivalOrgCode = arrivalOrgCode;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getHandoverBillNo() {
		return handoverBillNo;
	}
	public void setHandoverBillNo(String handoverBillNo) {
		this.handoverBillNo = handoverBillNo;
	}
	public String getStartDepartureTime() {
		return startDepartureTime;
	}
	public void setStartDepartureTime(String startDepartureTime) {
		this.startDepartureTime = startDepartureTime;
	}
	public String getEndDepartureTime() {
		return endDepartureTime;
	}
	public void setEndDepartureTime(String endDepartureTime) {
		this.endDepartureTime = endDepartureTime;
	}
	public String getStartArrivalTime() {
		return startArrivalTime;
	}
	public void setStartArrivalTime(String startArrivalTime) {
		this.startArrivalTime = startArrivalTime;
	}
	public String getEndArrivalTime() {
		return endArrivalTime;
	}
	public void setEndArrivalTime(String endArrivalTime) {
		this.endArrivalTime = endArrivalTime;
	}

	public String getPkpOrgCode() {
		return pkpOrgCode;
	}
	public void setPkpOrgCode(String pkpOrgCode) {
		this.pkpOrgCode = pkpOrgCode;
	}
	public String getDepartOrgCode() {
		return departOrgCode;
	}
	public void setDepartOrgCode(String departOrgCode) {
		this.departOrgCode = departOrgCode;
	}
	
	
	
}
