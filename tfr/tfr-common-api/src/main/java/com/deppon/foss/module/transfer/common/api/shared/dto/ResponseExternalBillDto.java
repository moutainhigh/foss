package com.deppon.foss.module.transfer.common.api.shared.dto;

public class ResponseExternalBillDto {

	/**
	 * 处理结果
	 * “0”表示失败。“1”表示成功
	 */
	private String result;
	/**
	 * 异常信息
	 */
	private String reason;
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
