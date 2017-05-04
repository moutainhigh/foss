package com.deppon.pda.bdm.module.foss.login.shared.exception;

import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.foss.login.shared.domain.ExceptionConstant;




/**
 * 
 * 用户类型异常
 * 
 * @author chengang
 * @version 1.0
 * @created 2012-9-12 上午09:32:29
 */

public class UserTypeUnMatchException extends PdaBusiException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8795819955980486273L;
	
	/**
	 * 构造方法
	 * @param message 异常消息
	 */
	public UserTypeUnMatchException() {
		super();
	}
	
	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public UserTypeUnMatchException(Throwable cause) {
		super(cause);
	} 
	
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_LOGIN_USERTYPE;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "该用户类型不匹配!";
	}
	
	@Override
	public Object[] getErrorArguments(){
		return null;
	}
}
