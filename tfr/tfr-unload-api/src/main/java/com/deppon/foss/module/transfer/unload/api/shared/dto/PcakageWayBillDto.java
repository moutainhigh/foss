package com.deppon.foss.module.transfer.unload.api.shared.dto;

public class PcakageWayBillDto {
	/**运单号**/
	private String wayBillNo;
	/**运单流水号**/
	private String serialNo;
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
}
