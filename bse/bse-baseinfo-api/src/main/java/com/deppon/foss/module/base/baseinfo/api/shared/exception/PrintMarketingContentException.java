package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;
/**
 * 打印营销内容基础资料异常处理类
 * @author dujunhui-187862
 * @date 2014-08-26 上午8:51:23
*/
public class PrintMarketingContentException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1490494493068928243L;

	public PrintMarketingContentException(String code, Object... args) {
		super(code, args);
	}

	public PrintMarketingContentException(String msg) {
		super(msg);
	}
	
}
