package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

public class PdaOrderWorkerOmsResponseDto implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	// 是否成功
	private String isSuccess;

	// 错误消息
	private String errorMsg;

	/**
	 * 
	 * @return isSuccess
	 */
	public String getIsSuccess() {
		return isSuccess;
	}

	/**
	 * 
	 * @param isSuccess
	 */
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	/**
	 * 
	 * @return errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * 
	 * @param errorMsg
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
