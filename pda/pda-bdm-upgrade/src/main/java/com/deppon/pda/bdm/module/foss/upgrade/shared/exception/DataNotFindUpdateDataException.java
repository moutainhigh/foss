package com.deppon.pda.bdm.module.foss.upgrade.shared.exception;


import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;



/**
 * 
 * 用户名异常
 * 
 * @author chengang
 * @version 1.0
 * @created 2012-9-12 上午09:32:29
 */

public class DataNotFindUpdateDataException extends PdaBusiException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8795819955980486273L;
	
	/**
	 * 构造方法
	 * @param message 异常消息
	 */
	public DataNotFindUpdateDataException() {
		super();
	}
	
	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public DataNotFindUpdateDataException(Throwable cause) {
		super(cause);
	} 
	
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_DATA_NOT_FIND;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "数据版本已是最新版本";
	}
	
	@Override
	public Object[] getErrorArguments(){
		return null;
	}
}
