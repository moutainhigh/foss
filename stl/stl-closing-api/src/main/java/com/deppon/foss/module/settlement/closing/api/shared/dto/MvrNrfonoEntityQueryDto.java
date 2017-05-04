package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 始发应收参数Dto
 * 
 * @author ddw
 * @date 2013-11-08
 */
public class MvrNrfonoEntityQueryDto implements Serializable {

	/**
	 * 始发应收参数Dto序列号
	 */
	private static final long serialVersionUID = 5737806093499459538L;

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
	
	

}
