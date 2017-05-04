package com.deppon.pda.bdm.module.core.shared.exception.sys.utilex;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.exception.PdaSysException;

/**
 * Json封装异常
 * @author wanghongling
 * @date 2012-09-10
 * @version 1.0
 *
 */
public class JsonEncapsulateException extends PdaSysException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8795819955980486273L;
	
	/**
	 * 构造方法
	 * @param message 异常消息
	 */
	public JsonEncapsulateException() {
		super();
	}
	
	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public JsonEncapsulateException(Throwable cause) {
		super(cause);
	}
	
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_SYS_JSON_ENCAP_ERR;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "JSON封装异常,原因：%s";
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
