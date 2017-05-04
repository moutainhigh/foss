package com.deppon.pda.bdm.module.foss.login.shared.exception;

import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.foss.login.shared.domain.ExceptionConstant;

public class SourceDeptException extends PdaBusiException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7252874203115601146L;
	
	/**
	 * 构造方法
	 * @param message 异常消息
	 */
	public SourceDeptException() {
		super();
	}
	
	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public SourceDeptException(Throwable cause) {
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
		return "该快递员没有对应的出发营业部!";
	}
	
	@Override
	public Object[] getErrorArguments(){
		return null;
	}
	
}
