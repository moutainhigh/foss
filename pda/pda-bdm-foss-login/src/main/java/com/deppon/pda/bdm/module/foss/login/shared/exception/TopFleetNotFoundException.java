package com.deppon.pda.bdm.module.foss.login.shared.exception;

import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.foss.login.shared.domain.ExceptionConstant;

public class TopFleetNotFoundException extends PdaBusiException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7252874203115601146L;
	
	/**
	 * 构造方法
	 * @param message 异常消息
	 */
	public TopFleetNotFoundException() {
		super();
	}
	
	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public TopFleetNotFoundException(Throwable cause) {
		super(cause);
	} 
	
	public String getErrCode() {
		return ExceptionConstant.SOURCEDEPT_NOT_EXIST;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "司机所在车队没有对应的顶级车队!";
	}
	
	@Override
	public Object[] getErrorArguments(){
		return null;
	}
	
}
