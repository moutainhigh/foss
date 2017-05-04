package com.deppon.pda.bdm.module.foss.login.shared.exception;

import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.foss.login.shared.domain.ExceptionConstant;


  
/**     
 *      
 * 部门异常
 *     
 * @author chengang       
 * @version 1.0     
 * @created 2012-9-12 上午11:08:23    
 */

public class UserDeptUnMatchException extends PdaBusiException{    
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 构造方法
	 * @param message 异常消息
	 */
	public UserDeptUnMatchException() {
		super();
	}
	
	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public UserDeptUnMatchException(Throwable cause) {
		super(cause);
	} 
	
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_DEPT_ERR;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "部门不匹配!";
	}
	
	@Override
	public Object[] getErrorArguments(){
		return null;
	}
	
}
