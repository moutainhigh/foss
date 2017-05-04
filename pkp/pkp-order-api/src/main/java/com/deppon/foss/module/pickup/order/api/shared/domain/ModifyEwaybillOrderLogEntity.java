package com.deppon.foss.module.pickup.order.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

public class ModifyEwaybillOrderLogEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String orderNo;
	private String waybillNo;
	private String operateType;
	private String isSuccess;
	private String operateRecord;
	private Date createTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getOperateRecord() {
		return operateRecord;
	}
	public void setOperateRecord(String operateRecord) {
		this.operateRecord = operateRecord;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
		
}
