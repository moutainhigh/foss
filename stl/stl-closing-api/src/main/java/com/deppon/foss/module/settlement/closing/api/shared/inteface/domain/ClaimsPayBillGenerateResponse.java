package com.deppon.foss.module.settlement.closing.api.shared.inteface.domain;

public class ClaimsPayBillGenerateResponse {
	// 返回結果 0 失败 1成功
	protected int result;
	// 異常信息
	protected String reason;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
