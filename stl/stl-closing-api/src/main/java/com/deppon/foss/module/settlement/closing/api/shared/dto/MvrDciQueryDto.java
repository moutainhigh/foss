package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 折扣报表 queryDto
 */
public class MvrDciQueryDto implements Serializable {

	private static final long serialVersionUID = -9176488876485879273L;

	/**
	 * 期间
	 */
	private String period;

	/**
	 * 业务类型
	 */
	private String productCode;
	
	/**
	 * 业务类型集合
	 */
	private List<String> productCodeList;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 始发网点编码
	 */
	private String origOrgCode;

	/**
	 * 到达网点编码
	 */
	private String destOrgCode;
	
	/** 用户编码-设置用户数据查询权限. */
	private String userCode;

	private int start;
	private int limit;

	/* getter & setter */

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getOrigOrgCode() {
		return origOrgCode;
	}

	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	public String getDestOrgCode() {
		return destOrgCode;
	}

	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * @return userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * @return productCodeList
	 */
	public List<String> getProductCodeList() {
		return productCodeList;
	}

	/**
	 * @param productCodeList
	 */
	public void setProductCodeList(List<String> productCodeList) {
		this.productCodeList = productCodeList;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}


}
