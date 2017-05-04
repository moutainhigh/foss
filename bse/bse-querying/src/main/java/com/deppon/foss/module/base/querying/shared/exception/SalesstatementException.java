package com.deppon.foss.module.base.querying.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;
/**
 * 营业报表清单异常
 * @author Administrator
 *
 */
public class SalesstatementException extends BusinessException {

	private static final long serialVersionUID = -5158349371215256757L;

	
	public SalesstatementException(String errCode) {
		super();
		super.errCode = errCode;
	}
	public SalesstatementException(String code, String msg) {
		super(code, msg);
	}
	public SalesstatementException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
