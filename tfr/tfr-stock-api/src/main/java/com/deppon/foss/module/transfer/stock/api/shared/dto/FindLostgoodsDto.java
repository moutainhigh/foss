package com.deppon.foss.module.transfer.stock.api.shared.dto;

public class FindLostgoodsDto {
	/**
	 * 创建所属部门';
	 */
	private String orgCode;
	
	/**
	 * 任务创建时间（上报时间）';
	 */
	private String taskCreateDate;
	/**
	 * 任务创建结束时间';
	 */
	private String taskEndDate;
	
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getTaskCreateDate() {
		return taskCreateDate;
	}
	public void setTaskCreateDate(String taskCreateDate) {
		this.taskCreateDate = taskCreateDate;
	}
	public String getTaskEndDate() {
		return taskEndDate;
	}
	public void setTaskEndDate(String taskEndDate) {
		this.taskEndDate = taskEndDate;
	}
	

}
