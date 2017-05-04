package com.deppon.pda.bdm.module.foss.load.shared.exception;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;

public class LoadTaskHasSubmitException extends PdaBusiException{
private static final long serialVersionUID = 1L;
	
	public LoadTaskHasSubmitException(){
		super();
	}
	
	public LoadTaskHasSubmitException(Throwable cause){
		super(cause);
	}
	
	@Override
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_LOAD_LOADTASKHASSMT_ERR;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "该任务已提交!";
	}
}
