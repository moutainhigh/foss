package com.deppon.foss.module.settlement.closing.api.shared.inteface.domain;

import java.io.Serializable;

public class EcsOutWarehouseRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//运单号
	private String waybillNo;
	
	//异常出库类型（丢货：”LG”，弃货：”GG”，违禁品：”CG”）
	private String expType;
	
	//操作人工号
    private String empCode;
    
    //操作人姓名
    private String empName;
    
    //操作人部门编码
    private String currentDeptCode;
    
    //操作人部门名称
    private String currentDeptName;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getExpType() {
		return expType;
	}

	public void setExpType(String expType) {
		this.expType = expType;
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
