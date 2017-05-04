package com.deppon.pda.bdm.module.foss.load.shared.exception;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;


  
/**     
 * 密码异常     
 *     
 * @author chengang       
 * @version 1.0     
 * @created 2012-9-12 上午11:04:34    
 */

public class UserInfoNotWholeException extends PdaBusiException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3054289384782710138L;

	/**
	 * 构造方法
	 * @param message 异常消息
	 */
	public UserInfoNotWholeException() {
		super();
	}
	
	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public UserInfoNotWholeException(Throwable cause) {
		super(cause);
	} 
	
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_USERINFO_NOTWHOLE;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "用户信息不完整，部门不存在";
	}
	
	@Override
	public Object[] getErrorArguments(){
		return null;
	}
}
