package com.deppon.pda.bdm.module.core.shared.exception.sys.utilex;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.exception.PdaSysException;

/**
 * JSON解析异常
 * @author wanghongling
 * @date 2012-09-10
 * @version 1.0
 *
 */
public class JsonParseException extends PdaSysException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3814234633001625633L;
	
	/**
	 * 构造方法
	 */
	public JsonParseException() {
		super();
	}
	
	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public JsonParseException(Throwable cause) {
		super(cause);
	}
	
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_SYS_JSON_PARSE_ERR;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "JSON解析异常,原因：%s";
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
