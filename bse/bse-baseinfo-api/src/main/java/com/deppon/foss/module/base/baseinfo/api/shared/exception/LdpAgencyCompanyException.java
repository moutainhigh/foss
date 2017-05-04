package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 快递代理公司异常类
 * 
 * @author WangPeng
 * @date 2013-07-13 11:19 AM
 * 
 */
public class LdpAgencyCompanyException extends BusinessException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public LdpAgencyCompanyException(String errCode) {
		super();
		super.errCode = errCode;
	}

	public LdpAgencyCompanyException(String code, String msg) {

		super(code, msg);
	}
}
