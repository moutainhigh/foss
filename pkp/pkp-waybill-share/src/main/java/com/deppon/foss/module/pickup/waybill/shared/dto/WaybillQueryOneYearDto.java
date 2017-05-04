package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.util.Date;

public class WaybillQueryOneYearDto {
	
	/**
	 *  状态
	 */
	private String state;

	/**
	 *  发货客户编码
	 */
	private String deliveryCustomerCode;
	
	/**
	 * 开始查询时间
	 */
	private Date startTime;
	
	/**
	 * 结束查询时间
	 */
	private Date endTime;
	
	/**
	 * 是否有效
	 */
	private String active;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	
	

}
