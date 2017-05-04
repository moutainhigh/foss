package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

public class RentCarCubcResponse {

	/**
	 * 是否成功 0代码正常 1代表发生异常
	 */
	private String isException;

	/**
	 * 异常信息
	 */
	private String message;

	public String getIsException() {
		return isException;
	}

	public void setIsException(String isException) {
		this.isException = isException;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
