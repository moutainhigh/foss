package com.deppon.foss.module.settlement.consumer.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;


public class CubcReverseSettleException extends BusinessException {
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 378375005L;
	
	public static final String PARAMS_IS_NULL ="请求的参数或系统信息为空";

	public static final String CONNECTIONS_ERROR ="调用CUBC反结清服务发生异常";
	public CubcReverseSettleException(String errCode) {
		super();
		super.errCode=errCode;
	}

	
	
	
	
}
