package com.deppon.pda.bdm.module.foss.login.shared.exception;

import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.foss.login.shared.domain.ExceptionConstant;

public class DepartAssemblyDeptException extends PdaBusiException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7252874203115601146L;
	
	/**
	 * 构造方法
	 * @param message 异常消息
	 */
	public DepartAssemblyDeptException() {
		super();
	}
	
	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public DepartAssemblyDeptException(Throwable cause) {
		super(cause);
	} 
	
	public String getErrCode() {
		return ExceptionConstant.DEPARTASSEMBLYDEPT_NOT_EXIST;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "快递员营业部无法获取快递配载外场部门!";
	}
	
	@Override
	public Object[] getErrorArguments(){
		return null;
	}
	
}
