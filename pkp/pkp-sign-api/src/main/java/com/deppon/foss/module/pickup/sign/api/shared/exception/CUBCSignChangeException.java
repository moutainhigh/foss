package com.deppon.foss.module.pickup.sign.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class CUBCSignChangeException extends BusinessException{

	private static final long serialVersionUID = 1L;
	
	public CUBCSignChangeException(String code) {
		super();
		this.errCode = code;
	}

}
