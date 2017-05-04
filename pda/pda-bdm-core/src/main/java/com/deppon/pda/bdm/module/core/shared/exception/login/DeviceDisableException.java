package com.deppon.pda.bdm.module.core.shared.exception.login;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;

  
/**     
 *      
 * PDA禁用异常   
 * @author chengang       
 * @version 1.0     
 * @created 2012-9-14 下午01:44:03    
 */

public class DeviceDisableException extends PdaBusiException{
	private static final long serialVersionUID = 1L;

	/**
	 * 构造方法
	 * @param message 异常消息
	 */
	public DeviceDisableException() {
		super();
	}
	
	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public DeviceDisableException(Throwable cause) {
		super(cause);
	} 
	
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_DIS_DEVICE_ERR;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "设备已被禁用!";
	}
	
	@Override
	public Object[] getErrorArguments(){
		return null;
	}
}
