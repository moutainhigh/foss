package com.deppon.pda.bdm.module.core.shared.exception.sys.common;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;

public class ExternalInterfaceException extends PdaBusiException{
	private static final long serialVersionUID = 1L;
	private String errorCode;
	/**
	 * 构造方法
	 * @param message 异常消息
	 */
	public ExternalInterfaceException(Throwable cause,String errorCode) {
		super(cause);
		this.errorCode = errorCode;
	}
	
	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public ExternalInterfaceException(Throwable cause) {
		super(cause);
	} 
	public String getErrCode() {
		return ExceptionConstant.ERROR_SYS_EXTERNAL_FAIL;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "%s";
	}
	
	@Override
	public Object[] getErrorArguments(){
		Object[] arg = new Object[]{
				this.getErrorCode()
		};
		return arg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	
}
