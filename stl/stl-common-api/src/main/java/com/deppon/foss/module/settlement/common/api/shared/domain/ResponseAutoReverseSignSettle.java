package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.io.Serializable;

/**
 * 
 * @author 218392 zhangyongxue
 * @date 2016-06-12 16:11:20
 * VTS自动反签收反结清：返回参数
 *
 */
public class ResponseAutoReverseSignSettle implements Serializable{

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
