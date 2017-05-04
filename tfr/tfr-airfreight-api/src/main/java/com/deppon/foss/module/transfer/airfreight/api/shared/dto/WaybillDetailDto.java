package com.deppon.foss.module.transfer.airfreight.api.shared.dto;

import java.io.Serializable;

public class WaybillDetailDto implements Serializable{

	/**
	 * 运单明细实体
	 */
	private static final long serialVersionUID = 1L;
	
	//运单号
	private String waybillNo;
	
	//尺寸

	private String goodSize;
	
	//对外备注
	private String outerNotes;
	
	//对内备注
	private String innerNotes;
	
	//储运事项
	private String transportationRemark;
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getGoodSize() {
		return goodSize;
	}
	public void setGoodSize(String goodSize) {
		this.goodSize = goodSize;
	}
	public String getOuterNotes() {
		return outerNotes;
	}
	public void setOuterNotes(String outerNotes) {
		this.outerNotes = outerNotes;
	}
	public String getInnerNotes() {
		return innerNotes;
	}
	public void setInnerNotes(String innerNotes) {
		this.innerNotes = innerNotes;
	}
	public String getTransportationRemark() {
		return transportationRemark;
	}
	public void setTransportationRemark(String transportationRemark) {
		this.transportationRemark = transportationRemark;
	}
	
	
	
}
