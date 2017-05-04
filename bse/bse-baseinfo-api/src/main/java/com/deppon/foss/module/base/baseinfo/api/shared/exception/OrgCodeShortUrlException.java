package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 同步短url接口的异常类
 * @author 232608
 *
 */
public class OrgCodeShortUrlException extends BusinessException{
	private static final long serialVersionUID = 616329425L;

	public OrgCodeShortUrlException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	public OrgCodeShortUrlException(String code, String msg) {
		super(code, msg);
	}

	public OrgCodeShortUrlException(String msg) {
		super(msg);
	}
}
