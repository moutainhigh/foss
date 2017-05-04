package com.deppon.foss.module.transfer.pda.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class QueryTrayScanTaskEntity extends BaseEntity {

	private static final long serialVersionUID = 4035577588006216124L;
	
	/**
	 * 运单号
	 * **/
	private String waybillNo;
	
	/**包号**/
	private String packageNo;
	
	/**
	 * 
	 * 流水号
	 * 
	 * */
	private String serialNo;
	
	/***
	 * 
	 * 当前部门
	 * 
	 * **/
	private String orgCode;
	
	/**
	 * 工号
	 * */
	private String bindingCode;
	
	/**
	 * 时间
	 * **/
	private Date queryTime;
	
	/**
	 * 
	 * 托盘任务号
	 * */
	private String taskNo;
	
	

	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public String getBindingCode() {
		return bindingCode;
	}

	public void setBindingCode(String bindingCode) {
		this.bindingCode = bindingCode;
	}

	public Date getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(Date queryTime) {
		this.queryTime = queryTime;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}

}
