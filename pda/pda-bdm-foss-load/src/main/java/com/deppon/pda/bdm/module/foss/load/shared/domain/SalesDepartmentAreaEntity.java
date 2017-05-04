package com.deppon.pda.bdm.module.foss.load.shared.domain;

import java.io.Serializable;

public class SalesDepartmentAreaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 *2014-10-30
	 *ThroughPackageOrg.java
	 *直达包到达部门
	 *
	 */
	/**部门编码*/
	private String curDeptCode;
	public String getCurDeptCode() {
		return curDeptCode;
	}
	public void setCurDeptCode(String curDeptCode) {
		this.curDeptCode = curDeptCode;
	}

}
