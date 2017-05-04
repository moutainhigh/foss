package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;
/**
 * 降价发券方案异常处理类
 * @author dujunhui-187862
 * @date 2014-9-27 上午10:31:28
*/
public class PriceCouponException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1490494493068928243L;

	public PriceCouponException(String code, Object... args) {
		super(code, args);
		super.errCode = code;
	}

	public PriceCouponException(String msg) {
		super();
		super.errCode = msg;
	}
	
}
