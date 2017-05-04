package com.deppon.pda.bdm.module.core.shared.exception.login;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
  
/**     
 * 密码异常     
 *     
 * @author chengang       
 * @version 1.0     
 * @created 2012-9-12 上午11:04:34    
 */

public class PasswordErrorException extends PdaBusiException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8795819955980486273L;
	
	/**
	 * 构造方法
	 * @param message 异常消息
	 */
	public PasswordErrorException() {
		super();
	}
	
	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public PasswordErrorException(Throwable cause) {
		super(cause);
	} 
	
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_PASSWORD_ERR;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "用户名或密码错误!";
	}
	
	@Override
	public Object[] getErrorArguments(){
		return null;
	}
}
