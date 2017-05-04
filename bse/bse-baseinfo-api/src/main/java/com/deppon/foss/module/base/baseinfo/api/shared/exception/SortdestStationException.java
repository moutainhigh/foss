package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class SortdestStationException extends BusinessException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 147399755398503127L;

	public SortdestStationException(String errCode) {
		super();
		super.errCode = errCode;
	}

	public SortdestStationException(String code, String msg) {

		super(code, msg);
	}
}
