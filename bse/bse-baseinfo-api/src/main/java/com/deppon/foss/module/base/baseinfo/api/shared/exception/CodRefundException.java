package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * (CodRefund的exception类)
 * @author 187862-dujunhui
 * @date 2014-7-16 下午2:19:13
 * @since
 * @version v1.0
 */
public class CodRefundException extends BusinessException {

	/**
	 * (序列化)
	 */
	private static final long serialVersionUID = 1490494493068928243L;

	public CodRefundException(String code, Object... args) {
		super(code, args);
	}

	public CodRefundException(String msg) {
		super(msg);
	}
	
}
