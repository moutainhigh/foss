package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;
/**
 * 仓库预警短信接收岗位基础资料异常处理类
 * @author dujunhui-187862
 * @date 2014-08-08 下午4:45:23
*/
public class TitleBaseInfoException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1490494493068928243L;

	public TitleBaseInfoException(String code, Object... args) {
		super(code, args);
	}

	public TitleBaseInfoException(String msg) {
		super(msg);
	}
	
}
