package com.deppon.pda.bdm.module.core.shared.exception.login;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;

  
/**     
 *      
 * 设备未找到异常
 * @author chengang       
 * @version 1.0     
 * @created 2012-9-13 下午06:06:15    
 */

public class DeviceNotFoundException extends PdaBusiException {
	 
	private static final long serialVersionUID = -8795819955980486273L;
	
	/**
	 * 构造方法
	 * @param message 异常消息
	 */
	public DeviceNotFoundException() {
		super();
	}
	
	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public DeviceNotFoundException(Throwable cause) {
		super(cause);
	} 
	
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_DEVICE_ERR;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "设备信息不存在!";
	}
	
	@Override
	public Object[] getErrorArguments(){
		return null;
	}
}
