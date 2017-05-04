package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
/**
 * 外请车往来月报表查询明细
 * @author 073615
 *
 */
public class MvrOrcciQueryDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 期间
	 */
	private String period;
	/**
	 * 始发/到达类型
	 */
	private String orgType;
	/**
	 * 部门编码
	 */
	private String deptCode;
	
	public String getPeriod() {
		return period;
	}
	
	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	

}
