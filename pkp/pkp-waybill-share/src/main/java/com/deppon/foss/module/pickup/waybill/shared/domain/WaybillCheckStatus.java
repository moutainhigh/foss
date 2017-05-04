package com.deppon.foss.module.pickup.waybill.shared.domain;






public class WaybillCheckStatus{
	//运单号
	 private String waybillNo;
	//流水号
	private String srialNo;
	 //运单号状态编码
    private String waybillStatusCode;
    //流水号是否在特殊库存组织
    private String srialNoIsspecialStock;
    //流水号状态是否有效
    private String srialNoStatusCode;
    
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getSrialNo() {
		return srialNo;
	}
	public void setSrialNo(String srialNo) {
		this.srialNo = srialNo;
	}
	public String getWaybillStatusCode() {
		return waybillStatusCode;
	}
	public void setWaybillStatusCode(String waybillStatusCode) {
		this.waybillStatusCode = waybillStatusCode;
	}
	public String getSrialNoIsspecialStock() {
		return srialNoIsspecialStock;
	}
	public void setSrialNoIsspecialStock(String srialNoIsspecialStock) {
		this.srialNoIsspecialStock = srialNoIsspecialStock;
	}
	public String getSrialNoStatusCode() {
		return srialNoStatusCode;
	}
	public void setSrialNoStatusCode(String srialNoStatusCode) {
		this.srialNoStatusCode = srialNoStatusCode;
	}
}
