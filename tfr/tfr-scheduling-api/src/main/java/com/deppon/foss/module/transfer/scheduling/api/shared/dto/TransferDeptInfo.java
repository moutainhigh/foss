/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.shared.dto
 * FILE    NAME: TransferDeptInfo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

/**
 * 用于查询月台信息界面初始化部门信息
 * @author 046130-foss-xuduowei
 * @date 
 */
public class TransferDeptInfo {
	/**
	 * 部门code
	 */
	private String deptCode;
	/**
	 * 部门名称
	 */
	private String deptName;
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	
}
