package com.deppon.pda.bdm.module.core.shared.exception.sys.common;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;

public class ServiceNotLoadException extends PdaBusiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4227038760243583390L;
	
	
	public ServiceNotLoadException() {
		super();
	}
	
	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public ServiceNotLoadException(Throwable cause) {
		super(cause);
	} 
	
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_SYS_SERVICE_NOT_LOAD;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "服务类类未加载";
	}
	
	@Override
	public Object[] getErrorArguments(){
		return null;
	}

}
