package com.deppon.foss.module.transfer.partialline.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class SalesdeptDeliveryprocException extends BusinessException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SalesdeptDeliveryprocException(String errCode) {
		super();
		super.errCode = errCode;
	}

	public SalesdeptDeliveryprocException(String code, String msg) {
		super(code, msg);
	}

	public SalesdeptDeliveryprocException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
