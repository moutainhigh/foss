package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 快递代理公司网点异常类
 * 
 * @author WangPeng
 * @date 2013-07-13 11:19 AM
 * 
 */
public class LdpAgencyDeptException extends BusinessException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public LdpAgencyDeptException(String errCode) {
		super();
		super.errCode = errCode;
	}

	public LdpAgencyDeptException(String code, String msg) {

		super(code, msg);
	}
}
