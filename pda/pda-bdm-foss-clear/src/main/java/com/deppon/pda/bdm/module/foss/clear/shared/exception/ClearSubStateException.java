package com.deppon.pda.bdm.module.foss.clear.shared.exception;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;

public class ClearSubStateException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errCode;
	
	public ClearSubStateException(){
		super();
		errCode = this.getErrCode();
	}
	public ClearSubStateException(String str){
		super(str);
		errCode = this.getErrCode();
	}
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	
}
