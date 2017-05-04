package com.deppon.foss.module.generalquery.shared.domain;

public class DeptMessageInfoEntity {
	private String deptName;
	private String parent_org_code;
	private String  division;
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getParent_org_code() {
		return parent_org_code;
	}
	public void setParent_org_code(String parent_org_code) {
		this.parent_org_code = parent_org_code;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	
}
