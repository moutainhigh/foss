package com.deppon.pda.bdm.module.foss.load.shared.exception;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;

public class LoadTaskHasCreateException extends PdaBusiException{
private static final long serialVersionUID = 1L;
	
	public LoadTaskHasCreateException(){
		super();
	}
	
	public LoadTaskHasCreateException(Throwable cause){
		super(cause);
	}
	
	@Override
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_LOAD_LOADTASKHASCRT_ERR;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "该任务已建立!";
	}
}
