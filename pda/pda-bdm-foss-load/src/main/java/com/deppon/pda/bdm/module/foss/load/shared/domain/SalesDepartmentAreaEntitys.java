package com.deppon.pda.bdm.module.foss.load.shared.domain;

import java.io.Serializable;

public class SalesDepartmentAreaEntitys implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 *2014-10-30
	 *ThroughPackageOrg.java
	 *直达包到达部门
	 *
	 */
	/**部门编码*/
	private String orgCode;
	
	/**部门名称*/
	private String orgName;

	/**
	 * @return 直达包部门编码
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return 直达包部门名称
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	

}
