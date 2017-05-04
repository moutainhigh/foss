package com.deppon.foss.module.settlement.common.api.shared.dto;

public class FossPayableToVtsTail {

	private boolean isSuccess;
	
	
	private String msg;

	

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
