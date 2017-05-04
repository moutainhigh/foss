package com.deppon.foss.module.transfer.unload.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

public class OrderSerialNoDetailEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	//流水号
	private String serialNo;
	
	//交接单号
	private String handoverNo;
	
	//运单号
	private String waybillNo;
	
	//点单差异类型
	private String orderReportType;
	
	//点单任务明细id
	private String orderTaskDetailId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getHandoverNo() {
		return handoverNo;
	}

	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getOrderReportType() {
		return orderReportType;
	}

	public void setOrderReportType(String orderReportType) {
		this.orderReportType = orderReportType;
	}

	public String getOrderTaskDetailId() {
		return orderTaskDetailId;
	}

	public void setOrderTaskDetailId(String orderTaskDetailId) {
		this.orderTaskDetailId = orderTaskDetailId;
	}

	
}
