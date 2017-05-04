package com.deppon.foss.module.pickup.waybill.api.shared.dto;

public class WaybillHomeInfoResponseDto {
	private String id; // id
	private String mailNo; // 订单号
	private String resultCode; // 成功或失败的标识（0-失败 1-成功）
	private String errorMessage; // 失败原因（如果处理失败，此字段为必填）
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMailNo() {
		return mailNo;
	}
	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
