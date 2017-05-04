package com.deppon.pda.bdm.module.core.shared.exception;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;

/**
 * PDA系统异常基类
 * @author wanghongling
 * @date 2012-09-10
 * @version 1.0
 *
 */
public class PdaSysException extends RuntimeException implements IPdaException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1430866168492550403L;
	/**
	 * 异常ID
	 */
	private String errId;
	
	/**
	 * 构造方法
	 */
	public PdaSysException() {
		super();
		errId = ErrIdGeneratorUtil.generateID();
	}
	
	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public PdaSysException(Throwable cause) {
		super(cause);
		errId = ErrIdGeneratorUtil.generateID();
	}
	
	@Override
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_SYS;
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
		return "系统异常,原因：%s";
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

	@Override
	public String getErrId() {
		return errId;
	}

}
