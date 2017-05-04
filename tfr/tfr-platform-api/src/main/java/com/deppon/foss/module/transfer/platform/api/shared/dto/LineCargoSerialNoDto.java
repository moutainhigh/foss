package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;

public class LineCargoSerialNoDto implements Serializable {

	private static final long serialVersionUID = -4802471892314630535L;

	private String waybillNo;

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

	@Override
	public String toString() {
		return "LineCargoSerialNoDto [waybillNo=" + waybillNo + ", serialNo="
				+ serialNo + "]";
	}

}
