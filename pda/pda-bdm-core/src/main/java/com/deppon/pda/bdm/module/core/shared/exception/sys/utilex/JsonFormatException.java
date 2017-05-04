package com.deppon.pda.bdm.module.core.shared.exception.sys.utilex;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;

/**
 * JSON格式错误
 * @author wanghongling
 * @date 2012-09-10
 * @version 1.0
 *
 */
public class JsonFormatException extends JsonParseException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2930166730643093419L;
	
	/**
	 * 构造方法
	 */
	public JsonFormatException() {
		super();
	}
	
	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public JsonFormatException(Throwable cause) {
		super(cause);
	}
	
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_SYS_JSON_FORMAT_ERR;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "JSON格式错误,原因：%s";
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

}
