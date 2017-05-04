package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.io.Serializable;
/**
 * 
 * @author 218392 zhangyongxue
 * @date 2016-05-27 20:28:20
 * VTS外请车费用调整同意之后调用结算重生成整车尾款应付单接口：返回参数
 *
 */
public class ResponseAdjustFeeEntity implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean ifSuccess;
	
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
