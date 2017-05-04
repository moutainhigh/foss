package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;

/**
 * 出发到达往来报表dto.
 *
 * @author 045738-foss-maojianqiang
 * @date 2013-3-7 下午2:10:43
 */
public class MvrRfiDto implements Serializable{
	
	/** 序列化id. */
	private static final long serialVersionUID = -6670665957040497275L;
	
	/** 客户编码. */
	private String customerCode;
	
	/** 期间. */
	private String period;
	
	/** 部门编码. */
	private String deptCode;
	
	/** 查询类型. */
	private String orgType;
	
	/**
	 * Gets the customer code.
	 *
	 * @return customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	
	/**
	 * Sets the customer code.
	 *
	 * @param customerCode the new customer code
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	/**
	 * Gets the period.
	 *
	 * @return period
	 */
	public String getPeriod() {
		return period;
	}
	
	/**
	 * Sets the period.
	 *
	 * @param period the new period
	 */
	public void setPeriod(String period) {
		this.period = period;
	}
	
	/**
	 * Gets the dept code.
	 *
	 * @return deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}
	
	/**
	 * Sets the dept code.
	 *
	 * @param deptCode the new dept code
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	/**
	 * Gets the org type.
	 *
	 * @return orgType
	 */
	public String getOrgType() {
		return orgType;
	}
	
	/**
	 * Sets the org type.
	 *
	 * @param orgType the new org type
	 */
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	
	
}
