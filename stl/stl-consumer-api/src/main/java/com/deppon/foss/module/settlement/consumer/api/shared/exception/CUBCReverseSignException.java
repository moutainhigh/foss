package com.deppon.foss.module.settlement.consumer.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * CUBC反签收异常类
 * @author 353654
 *
 */
public class CUBCReverseSignException extends BusinessException {

	private static final long serialVersionUID = 1L;
	
	public CUBCReverseSignException(String code) {
		super();
		this.errCode = code;
	}
}
