package com.deppon.pda.bdm.module.foss.login.shared.exception;

import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.foss.login.shared.domain.ExceptionConstant;


  
/**     
 *      
 * 设备所属部门不匹配异常  
 * @author chengang       
 * @version 1.0     
 * @created 2012-9-14 下午02:10:10    
 */

public class DeviceDeptUnMatchException extends PdaBusiException{

	
	private static final long serialVersionUID = 1L;

	/**
	 * 构造方法
	 * @param message 异常消息
	 */
	public DeviceDeptUnMatchException() {
		super();
	}
	
	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public DeviceDeptUnMatchException(Throwable cause) {
		super(cause);
	} 
	
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_DEPTUNMATCH_ERR;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "设备所属部门不匹配!";
	}
	
	@Override
	public Object[] getErrorArguments(){
		return null;
	}
}
