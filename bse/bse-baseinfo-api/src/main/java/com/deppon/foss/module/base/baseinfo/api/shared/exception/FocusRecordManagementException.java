package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class FocusRecordManagementException extends BusinessException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9033163481315712345L;
	
	public FocusRecordManagementException(String code, String msg) {
		super(code, msg);
	}

	public FocusRecordManagementException(String msg) {
		super(msg);
	
	}
}
