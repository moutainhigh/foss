package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.util.Date;

public class PackagePrintLogDto {
	
	/**id主键**/
	private String id;
	/**包号**/
	private String packageNo;
	/**打印时间**/
	private Date printTime;
	/**打印人编码**/
	private String printPersonCode;
	/**登录人编码**/
	private String empCode;
	/**登录部门编码**/
	private String deptCode;
	/**登录部门名称**/
	private String deptName;
	public String getPackageNo() {
		return packageNo;
	}
	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
	public Date getPrintTime() {
		return printTime;
	}
	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}

	public String getPrintPersonCode() {
		return printPersonCode;
	}
	public void setPrintPersonCode(String printPersonCode) {
		this.printPersonCode = printPersonCode;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
