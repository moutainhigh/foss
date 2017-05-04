package com.deppon.foss.module.settlement.consumer.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 灰名单请求参数
 * 
 * @author 269044
 * @date 2016-04-15
 */
public class GrayCustomerEntity implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * id
	 */
	private String id;
	
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

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getOverDays() {
		return overDays;
	}

	public void setOverDays(int overDays) {
		this.overDays = overDays;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
}
