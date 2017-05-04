package com.deppon.foss.module.transfer.common.api.shared.dto;

public class CubcVehicleAssembleBillResponse {


	/**
	 * 是否成功 0代码正常 1代表发生异常
	 */
	private String result;
	
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "CubcVehicleAssembleBillResponse [result=" + result + ", reason=" + reason + "]";
	}

}
