package com.deppon.foss.module.pickup.waybill.shared.vo;


public class RewardFineDetailVo {
	
	//登陆员工工号
	private String empCode;
	//起始时间
	private Long startTime;
	//结束时间
	private Long endTime;
	
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	

}
