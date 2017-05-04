package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.io.Serializable;

public class ExpressWorkerBillCountDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 快递员工号
	 * */
	private String employeeCode;
	/**
	 * 应完成票数
	 * */
	private Integer receiveCount;
	/**
	 * 完成票数
	 * */
	private Integer completeCount;
	/**
	 * 其他票数
	 * */
	private Integer otherCount;
	
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public Integer getReceiveCount() {
		return receiveCount;
	}
	public void setReceiveCount(Integer receiveCount) {
		this.receiveCount = receiveCount;
	}
	public Integer getCompleteCount() {
		return completeCount;
	}
	public void setCompleteCount(Integer completeCount) {
		this.completeCount = completeCount;
	}
	public Integer getOtherCount() {
		return otherCount;
	}
	public void setOtherCount(Integer otherCount) {
		this.otherCount = otherCount;
	}

	
	
}
