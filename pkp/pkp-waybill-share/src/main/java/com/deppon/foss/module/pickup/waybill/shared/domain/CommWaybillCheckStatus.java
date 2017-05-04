package com.deppon.foss.module.pickup.waybill.shared.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CommWaybillCheckStatus")
public class CommWaybillCheckStatus {
	// 运单号
	private String waybillNo;
	// 返单号
	private String originalWaybillNo;
	// 返单类型
	private String returnType;
	
	//判断是否是原单号查询
	private String hasReturnType;
	
	//报错信息
	private String information;

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public String getHasReturnType() {
		return hasReturnType;
	}

	public void setHasReturnType(String hasReturnType) {
		this.hasReturnType = hasReturnType;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getOriginalWaybillNo() {
		return originalWaybillNo;
	}

	public void setOriginalWaybillNo(String originalWaybillNo) {
		this.originalWaybillNo = originalWaybillNo;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

}
