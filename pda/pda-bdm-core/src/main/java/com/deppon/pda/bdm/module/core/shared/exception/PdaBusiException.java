package com.deppon.pda.bdm.module.core.shared.exception;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;



/**  
 * 异常类
 * @author chengang       
 * @version 1.0     
 * @created 2012-9-6 下午03:29:40
 */
public class PdaBusiException extends RuntimeException implements IPdaException{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 异常ID
	 */
	private String errId;
	
	public PdaBusiException() {
		super();
		errId = generateErrId();
	}
	
	public PdaBusiException(Throwable cause) {
		super(cause);
		errId = generateErrId();
	}
	
	private String generateErrId(){
		return ErrIdGeneratorUtil.generateID();
	}
	
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI;
	}

	@Override
	public String getNativeMessage() {
		return null;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "业务异常,原因：%s";
	}
	
	@Override
	public Object[] getErrorArguments(){
		Object[] arg = null;
		if(this.getCause()!=null){
			arg = new Object[]{
				this.getCause().getMessage()	
			};
		}
		return arg;
	}
	
	@Override
	public String getMessage(){
		if(getNativeMessage()!=null&&getNativeMessage().length()>0){
			return getNativeMessage();
		}else{
			return String.format(getDefaultMessage(), getErrorArguments());
		}
	}

	public String getErrId() {
		return errId;
	}
}
