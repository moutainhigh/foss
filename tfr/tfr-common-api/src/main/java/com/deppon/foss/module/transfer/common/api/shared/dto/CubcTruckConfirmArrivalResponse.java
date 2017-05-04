package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;

/**
 * FOSS空运模块响应实体
 * @author 328768
 *
 */
public class CubcTruckConfirmArrivalResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	//结果 整数类型，0、失败；1、成功
	private int result;
	//失败原因
	private String reason;

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
