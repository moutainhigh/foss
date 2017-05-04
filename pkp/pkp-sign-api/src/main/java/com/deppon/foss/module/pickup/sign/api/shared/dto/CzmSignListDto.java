package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;

public class CzmSignListDto implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	//子母件运单编号
	private String waybillNo;
	//子母件流水号
	private String serialNo;
	
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
}
