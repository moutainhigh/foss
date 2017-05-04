package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;

/**
 * 
 * @author 331556 fanjingwei
 * @date 2016-05-13
 */
public class vtsWriteomodifyBillWriteOffDto implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 运单号
	 */
	private String waybillNumber;
	/**
	 * 核销状态
	 */
	private String writeoffStatus;

	/**
	 * 核销备注
	 */
	private String writeOffNote;

	/**
	 * 员工工号
	 */
	private String empCode;

	/**
	 * 员工姓名
	 */
	private String empName;

	/**
	 * 当前登录部门编码
	 */
	private String currentDeptCode;

	/**
	 * 当前登录部门名称
	 */
	private String currentDeptName;

	

	public String getWaybillNumber() {
		return waybillNumber;
	}

	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}

	public String getWriteoffStatus() {
		return writeoffStatus;
	}

	public void setWriteoffStatus(String writeoffStatus) {
		this.writeoffStatus = writeoffStatus;
	}

	public String getWriteOffNote() {
		return writeOffNote;
	}

	public void setWriteOffNote(String writeOffNote) {
		this.writeOffNote = writeOffNote;
	}

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


}
