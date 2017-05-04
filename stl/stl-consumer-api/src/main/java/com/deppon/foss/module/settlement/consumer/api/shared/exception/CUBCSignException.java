package com.deppon.foss.module.settlement.consumer.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * CUBC签收异常类
 * @author 353654
 *
 */
public class CUBCSignException extends BusinessException{

	private static final long serialVersionUID = 1L;
	
	public CUBCSignException(String code) {
		super();
		this.errCode = code;
	}
}
