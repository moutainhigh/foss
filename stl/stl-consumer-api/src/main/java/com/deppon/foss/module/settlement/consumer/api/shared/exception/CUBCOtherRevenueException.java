package com.deppon.foss.module.settlement.consumer.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class CUBCOtherRevenueException extends BusinessException{
	
	private static final long serialVersionUID = 378375011L;

	public CUBCOtherRevenueException(String errCode) {
		super();
		super.errCode=errCode;
	}
	
}
