package com.deppon.foss.module.transfer.pda.api.shared.domain;

import java.util.List;

public class WaybillPathDetailEntity {
	//运单号
	private String waybillNo ;
	//流水号
	private String serialNo;
	//重量
	private float weight;
	//体积
	private float size;
	//走货路由信息
	private List<ExpressPathDetailEntiy> pathdetails;
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public List<ExpressPathDetailEntiy> getPathdetails() {
		return pathdetails;
	}
	public void setPathdetails(List<ExpressPathDetailEntiy> pathdetails) {
		this.pathdetails = pathdetails;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public float getSize() {
		return size;
	}
	public void setSize(float size) {
		this.size = size;
	}
	
	
	
}
