package com.deppon.pda.bdm.module.foss.load.shared.domain;

import java.io.Serializable;

/**
 * @ClassName HandoverDepts.java 
 * @Description 交接部门实体
 * @author 201638
 * @date 2015-1-30
 */
public class HandoverDept implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 部门编码
	 */
	private String deptCode;
	
	/**
	 * 部门名称
	 */
	private String deptName;
	
	private String cityCode;

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

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	
}
