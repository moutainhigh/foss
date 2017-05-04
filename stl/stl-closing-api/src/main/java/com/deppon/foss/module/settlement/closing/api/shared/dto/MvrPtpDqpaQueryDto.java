package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 合伙人德启代付月报表dto
 * 
 * @author gpz
 * @date 2016年3月21日
 */
public class MvrPtpDqpaQueryDto implements Serializable{

	/**
	 * Dto序列号
	 */
	private static final long serialVersionUID = 8625951815303158294L;

	/**
	 * 期间
	 */
	private String period;

	/**
	 * 业务类型(运输性质)
	 */
	private String productCode;
	
	/**
	 * 业务类型集合
	 */
	private List<String> productCodeList;

	/**
	 * 客户编码(合伙人信息)
	 */
	private String customerCode;

	/**
	 * 始发部门编码
	 */
	private String origOrgCode;

	/**
	 * 到达部门编码
	 */
	private String destOrgCode;
	
	/** 
	 * 用户编码-设置用户数据查询权限. 
	 */
	private String userCode;

	/**
	 * @return the period
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * @param period the period to set
	 */
	public void setPeriod(String period) {
		this.period = period;
	}

	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return the productCodeList
	 */
	public List<String> getProductCodeList() {
		return productCodeList;
	}

	/**
	 * @param productCodeList the productCodeList to set
	 */
	public void setProductCodeList(List<String> productCodeList) {
		this.productCodeList = productCodeList;
	}

	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return the origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**
	 * @param origOrgCode the origOrgCode to set
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	/**
	 * @return the destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * @param destOrgCode the destOrgCode to set
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * @return the userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
}
