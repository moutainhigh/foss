package com.deppon.pda.bdm.module.foss.packagemanager.shared.domain;

/**
 * 建包增加扫描生成目的站-- 返回目的站信息
 * @author 245955
 *
 */
public class CreatePackageSiteScanResult {

	private String deptCode;
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
