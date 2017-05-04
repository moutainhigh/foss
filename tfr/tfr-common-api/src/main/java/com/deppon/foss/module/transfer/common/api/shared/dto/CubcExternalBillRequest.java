package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.util.List;


public class CubcExternalBillRequest {

	// 传给结算的实体
	private CubcExternalBillDto cubcExternalBillDto;

	// 作废，审核，反审核时的集合
	private List<CubcExternalBillDto> cubcExternalList;
	// 1审核2反审核,0作废
	private String status;

	/****************************** 当前登录人 *************************/
	// 员工工号
	private String empCode;

	// 员工姓名
	private String empName;

	// 当前登录部门编码
	private String currentDeptCode;

	// 当前登录部门名称
	private String currentDeptName;

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}

	public String getCurrentDeptName() {
		return currentDeptName;
	}

	public void setCurrentDeptName(String currentDeptName) {
		this.currentDeptName = currentDeptName;
	}

	public CubcExternalBillDto getCubcExternalBillDto() {
		return cubcExternalBillDto;
	}

	public void setCubcExternalBillDto(CubcExternalBillDto cubcExternalBillDto) {
		this.cubcExternalBillDto = cubcExternalBillDto;
	}

	public List<CubcExternalBillDto> getCubcExternalList() {
		return cubcExternalList;
	}

	public void setCubcExternalList(List<CubcExternalBillDto> cubcExternalList) {
		this.cubcExternalList = cubcExternalList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
