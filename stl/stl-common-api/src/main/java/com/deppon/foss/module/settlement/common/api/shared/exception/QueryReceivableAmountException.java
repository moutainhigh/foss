package com.deppon.foss.module.settlement.common.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class QueryReceivableAmountException extends BusinessException{
	
	private static final long serialVersionUID = 1L;

	public QueryReceivableAmountException(String errCode) {
		super();
		super.errCode = errCode;
	}
}
