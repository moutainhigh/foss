package com.deppon.pda.bdm.module.core.shared.exception.sys.common;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;

public class JsonNotNullException extends PdaBusiException{
	private static final long serialVersionUID = 1L;
	public JsonNotNullException() {
		super();
	} 
	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public JsonNotNullException(Throwable cause) {
		super(cause);
	} 
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_SYS_JSON_NOT_NULL;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}


	@Override
	public String getDefaultMessage() {
		return "亲，HTTP请求异常，请重试";
	}
	
	@Override
	public Object[] getErrorArguments(){
		return null;
	}

}
