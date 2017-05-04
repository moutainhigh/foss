package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 合同客户VO
 * @author WangQianJin
 * @date 2013-7-30 下午8:34:49
 */
public class CusBargainVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 客户编码
	 */
	private String customerCode;
	/**
	 * 付款类型
	 */
	private String chargeType;
	/*
	 * 快递付款类型
	 */
	private String expayway;
	
	public String getExpayway() {
		return expayway;
	}

	public void setExpayway(String expayway) {
		this.expayway = expayway;
	}

	/**
	 * 开单时间
	 */
	private Date billDate;
	/**
	 * 开单部门
	 */
	private String billOrgCode;
	
	public String getBillOrgCode() {
		return billOrgCode;
	}

	
	public void setBillOrgCode(String billOrgCode) {
		this.billOrgCode = billOrgCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}
	
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	public String getChargeType() {
		return chargeType;
	}
	
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	
	public Date getBillDate() {
		return billDate;
	}
	
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
}
