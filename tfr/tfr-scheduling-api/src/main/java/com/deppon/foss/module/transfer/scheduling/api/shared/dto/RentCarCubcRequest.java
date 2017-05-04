package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TempRentalMarkDetailEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TempRentalMarkEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TemprentalMarkSmticksEntity;

public class RentCarCubcRequest {

	
	private List<RentCarCubcDto> rentCarCubcList;
	
    private List<TempRentalMarkDetailEntity> tempRentalMarkDetailEntityList;
    
    private List<TempRentalMarkEntity> tempRentalMarkEntityList;
    
    private List<TemprentalMarkSmticksEntity> temprentalMarkSmticksEntityList;
       

	/******************************当前登录人*************************/
	//员工工号
	private String empCode;
	
	//员工姓名
	private String empName;
	
	//当前登录部门编码
	private String currentDeptCode;
		
	//当前登录部门名称
	private String currentDeptName;
	

	//费用承担部门
	private String bearFeesDept;
	
	//费用承担部门编码
	private String bearFeesDeptCode;
	
	

	public String getBearFeesDept() {
		return bearFeesDept;
	}

	public void setBearFeesDept(String bearFeesDept) {
		this.bearFeesDept = bearFeesDept;
	}

	public String getBearFeesDeptCode() {
		return bearFeesDeptCode;
	}

	public void setBearFeesDeptCode(String bearFeesDeptCode) {
		this.bearFeesDeptCode = bearFeesDeptCode;
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

	public List<TempRentalMarkDetailEntity> getTempRentalMarkDetailEntityList() {
		return tempRentalMarkDetailEntityList;
	}

	public void setTempRentalMarkDetailEntityList(
			List<TempRentalMarkDetailEntity> tempRentalMarkDetailEntityList) {
		this.tempRentalMarkDetailEntityList = tempRentalMarkDetailEntityList;
	}

	public List<TempRentalMarkEntity> getTempRentalMarkEntityList() {
		return tempRentalMarkEntityList;
	}

	public void setTempRentalMarkEntityList(
			List<TempRentalMarkEntity> tempRentalMarkEntityList) {
		this.tempRentalMarkEntityList = tempRentalMarkEntityList;
	}

	public List<TemprentalMarkSmticksEntity> getTemprentalMarkSmticksEntityList() {
		return temprentalMarkSmticksEntityList;
	}

	public void setTemprentalMarkSmticksEntityList(
			List<TemprentalMarkSmticksEntity> temprentalMarkSmticksEntityList) {
		this.temprentalMarkSmticksEntityList = temprentalMarkSmticksEntityList;
	}

	public List<RentCarCubcDto> getRentCarCubcList() {
		return rentCarCubcList;
	}

	public void setRentCarCubcList(List<RentCarCubcDto> rentCarCubcList) {
		this.rentCarCubcList = rentCarCubcList;
	}
	
}
