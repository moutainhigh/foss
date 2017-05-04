package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class GrayCustomerDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 客户编码
	 */
	private String customerCode;
	
	/**
	 * 最久欠款应收单的业务日期
	 */
	private Date minDate;

	/**
	 * 最大欠款天数
	 */
	private int overDays;
	
	/**
	 * 状态：0--进入灰名单  1--移除灰名单
	 */
	private String status;
	
	/**
	 * 是否有效
	 */
	private String active;

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Date getMinDate() {
		return minDate;
	}

	public int getOverDays() {
		return overDays;
	}

	public void setOverDays(int overDays) {
		this.overDays = overDays;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	
}
