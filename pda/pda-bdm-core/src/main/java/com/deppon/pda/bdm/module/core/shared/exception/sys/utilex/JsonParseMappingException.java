package com.deppon.pda.bdm.module.core.shared.exception.sys.utilex;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;

/**
 * JSON Bean映射异常
 * @author wanghongling
 * @date 2012-09-10
 * @version 1.0
 *
 */
public class JsonParseMappingException extends JsonParseException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4586356101541880562L;
	
	/**
	 * 构造方法
	 */
	public JsonParseMappingException() {
		super();
	}
	
	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public JsonParseMappingException(Throwable cause) {
		super(cause);
	}
	
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_SYS_JSON_MAPPING_ERR;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "JSON解析映射异常,原因：%s";
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
