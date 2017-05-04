package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;

/**
 * 
 * @author 243921 zhangtingting
 * @date 2017-03-02 15:23:20
 * VTS自动受理反签收反结清：返回的参数
 *
 */
public class TPSReverseSignSettleResponseDto implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 是否成功
	 */
	private boolean ifSuccess;
	
	/**
	 * 错误原因
	 */
	private String errorMsg;

	public boolean isIfSuccess() {
		return ifSuccess;
	}

	public void setIfSuccess(boolean ifSuccess) {
		this.ifSuccess = ifSuccess;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	

}
