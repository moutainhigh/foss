package com.deppon.foss.module.transfer.stock.api.shared.dto;

import java.io.Serializable;

public class SalesDeptExpLostWaybillSerialNosDto implements Serializable {

	private static final long serialVersionUID = 6873974952546844060L;

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
		return "SalesDeptExpLostWaybillSerialNosDto [waybillNo=" + waybillNo
				+ ", serialNo=" + serialNo + "]";
	}

}
