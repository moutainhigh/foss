package com.deppon.foss.module.settlement.consumer.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class CubcSettleException extends BusinessException {
	
	private static final long serialVersionUID = 378375004L;
	/**
	 * 请求参数为空
	 */
	public static final String PARAMS_IS_NULL = "请求参数或系统信息为空";
	
	public static final String CONNECTIONS_ERROR = "调用CUBC结清服务发生异常";

	public CubcSettleException(String errCode) {
		super();
		super.errCode=errCode;
		
	}
	
	
}
