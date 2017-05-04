package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;

public class BackPictureWaybillEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -613634879757302641L;
	//类型
	private String type;
	//运单号
	private String waybillNo;
	//订单号
	private String orderNo;
	//消息
	private String message;
	
	private String createTime;
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
