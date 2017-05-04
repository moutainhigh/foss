package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
public class CenterBillOrgDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4614231921953200396L;
	/**
	 * 开单组编码
	 */
	private  String billOrgCode;
	/**
	 * 开单量
	 */
	private Integer billCount;
	/**
	 * 差量
	 */
	private Integer subBillCount;

	public String getBillOrgCode() {
		return billOrgCode;
	}

	public void setBillOrgCode(String billOrgCode) {
		this.billOrgCode = billOrgCode;
	}

	public Integer getBillCount() {
		return billCount;
	}

	public void setBillCount(Integer billCount) {
		this.billCount = billCount;
	}

	public Integer getSubBillCount() {
		return subBillCount;
	}

	public void setSubBillCount(Integer subBillCount) {
		this.subBillCount = subBillCount;
	}

}
