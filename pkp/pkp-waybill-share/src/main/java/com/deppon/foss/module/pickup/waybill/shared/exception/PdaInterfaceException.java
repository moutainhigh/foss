package com.deppon.foss.module.pickup.waybill.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class PdaInterfaceException extends BusinessException  {

	/**
	 * 序列
	 */
	private static final long serialVersionUID = -6292189387014206774L;

	/**
	 * Instantiates a new pdaInterfaceException.
	 * 
	 * @param errCode the errCode
	 */
	public PdaInterfaceException(String errCode) {
		super();
		super.errCode = errCode;
	}
	
	/**
	 * Instantiates a new pdaInterfaceException.
	 * 
	 * @param errCode the errCode
	 * @param cause the cause
	 */
	public PdaInterfaceException(String errCode, Throwable cause){
		super(errCode, cause);
		super.errCode = errCode;
	}
}
