package com.deppon.pda.bdm.module.foss.login.shared.exception;

import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.foss.login.shared.domain.ExceptionConstant;


  
/**     
 *      
 * 数据库不存在程序版本信息异常
 * @author chengang       
 * @version 1.0     
 * @created 2012-9-14 下午03:55:45    
 */

public class DatabasePgmVersionException extends PdaBusiException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8795819955980486273L;
	
	/**
	 * 构造方法
	 * @param message 异常消息
	 */
	public DatabasePgmVersionException() {
		super();
	}
	
	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public DatabasePgmVersionException(Throwable cause) {
		super(cause);
	} 
	
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_PGMVER_DATABASE;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "该程序不需要更新!";
	}
	
	@Override
	public Object[] getErrorArguments(){
		return null;
	}
}
