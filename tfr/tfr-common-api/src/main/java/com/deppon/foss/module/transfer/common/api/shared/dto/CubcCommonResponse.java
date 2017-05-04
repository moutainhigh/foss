package com.deppon.foss.module.transfer.common.api.shared.dto;

public class CubcCommonResponse {


	/**
	 * 1表示成功，0表示失败
	 */
	private String result;
	/**
	 * 返回数据
	 */
	private String data;
	
	/**
	 * 异常信息
	 */
	private String reason;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	


}
