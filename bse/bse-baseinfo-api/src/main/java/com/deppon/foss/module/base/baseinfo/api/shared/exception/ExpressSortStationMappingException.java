package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

@SuppressWarnings("serial")
public class ExpressSortStationMappingException extends BusinessException {

	public ExpressSortStationMappingException() {
		super();
	}

	public ExpressSortStationMappingException(String code, String msg) {
		super(code, msg);
	}

	public ExpressSortStationMappingException(String msg) {
		super(msg);
	}
	
}
