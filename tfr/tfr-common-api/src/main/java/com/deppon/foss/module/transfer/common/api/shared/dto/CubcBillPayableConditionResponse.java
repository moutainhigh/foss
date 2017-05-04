package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.util.List;

public class CubcBillPayableConditionResponse {

	/**
	 * 车次号
	 */
	private List<String> sourceBillNo;

	/**
	 * 异常信息
	 */
	private String message;

	public List<String> getSourceBillNo() {
		return sourceBillNo;
	}

	public void setSourceBillNo(List<String> sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
