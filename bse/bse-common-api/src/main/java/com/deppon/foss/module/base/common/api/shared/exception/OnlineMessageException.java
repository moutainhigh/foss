package com.deppon.foss.module.base.common.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 定义一个在线信息的异常类
 * 
 * @author WangPeng
 * @date 2013-07-10 09:28AM
 *
 */
public class OnlineMessageException extends BusinessException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3974743556804238102L;
	
	
	public OnlineMessageException(String errCode) {
		super();
		super.errCode = errCode;
	}

	public OnlineMessageException(String code, String msg) {

		super(code, msg);
	}

}
