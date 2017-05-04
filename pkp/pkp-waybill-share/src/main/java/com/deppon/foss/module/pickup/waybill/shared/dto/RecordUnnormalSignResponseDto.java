package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

public class RecordUnnormalSignResponseDto implements Serializable{

	/**
	 * 零担异常签收自动上报回参
	 */
	private static final long serialVersionUID = -4123546338050801932L;

	// 1 成功 0 失败(发生异常) 2重复上报
	private String result;
	// 具体信息
	private String resultMsg;

	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

}