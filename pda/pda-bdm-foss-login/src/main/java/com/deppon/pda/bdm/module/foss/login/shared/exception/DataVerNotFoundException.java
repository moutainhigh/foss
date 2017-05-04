package com.deppon.pda.bdm.module.foss.login.shared.exception;

import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.foss.login.shared.domain.ExceptionConstant;

  
/**     
 *      
 * 未找到数据版本信息异常  
 * @author chengang       
 * @version 1.0     
 * @created 2012-9-19 上午11:04:15    
 */

public class DataVerNotFoundException extends PdaBusiException {
	
	private static final long serialVersionUID = -8795819955980486273L;
	
	/**
	 * 构造方法
	 * @param message 异常消息
	 */
	public DataVerNotFoundException() {
		super();
	}
	
	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public DataVerNotFoundException(Throwable cause) {
		super(cause);
	} 
	
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_DATAVER_NOTFOUND_ERR;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "未找到数据版本信息!";
	}
	
	@Override
	public Object[] getErrorArguments(){
		return null;
	}
}
