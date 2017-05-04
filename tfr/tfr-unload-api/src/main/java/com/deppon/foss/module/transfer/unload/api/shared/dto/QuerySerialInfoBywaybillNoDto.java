package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;

public class QuerySerialInfoBywaybillNoDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String serialNo;
	
	private String waybillNo;
	
	private String transportType;
	
	private String createBillQty;

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getCreateBillQty() {
		return createBillQty;
	}

	public void setCreateBillQty(String createBillQty) {
		this.createBillQty = createBillQty;
	}
	

}
