package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class StockDurationQcDto implements Serializable {

	private static final long serialVersionUID = 1694084873787889710L;

	/**
	 * 外场编码
	 */
	private String tfrCtrCode;

	/**
	 * 统计日期
	 */
	private Date staDate;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 下一部门编码
	 */
	private String nextOrgCode;

	/**
	 * 上一部门编码
	 */
	private String preOrgCode;

	/**
	 * 提货方式编码
	 */
	private String receiveMethod;

	public String getTfrCtrCode() {
		return tfrCtrCode;
	}

	public void setTfrCtrCode(String tfrCtrCode) {
		this.tfrCtrCode = tfrCtrCode;
	}

	public Date getStaDate() {
		return staDate;
	}

	public void setStaDate(Date staDate) {
		this.staDate = staDate;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getNextOrgCode() {
		return nextOrgCode;
	}

	public void setNextOrgCode(String nextOrgCode) {
		this.nextOrgCode = nextOrgCode;
	}

	public String getPreOrgCode() {
		return preOrgCode;
	}

	public void setPreOrgCode(String preOrgCode) {
		this.preOrgCode = preOrgCode;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

}
