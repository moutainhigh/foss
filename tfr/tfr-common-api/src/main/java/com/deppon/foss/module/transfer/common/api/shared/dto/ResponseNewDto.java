package com.deppon.foss.module.transfer.common.api.shared.dto;

public class ResponseNewDto {
//	{"code":"","message":"货物已确认","succee":1,"wayBillNo":""}
	private String code;
	private String message;
	private String succee;
	private String wayBillNo;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSuccee() {
		return succee;
	}
	public void setSuccee(String succee) {
		this.succee = succee;
	}
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	
	
}