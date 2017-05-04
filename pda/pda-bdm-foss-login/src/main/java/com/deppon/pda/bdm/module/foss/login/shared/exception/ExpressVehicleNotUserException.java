package com.deppon.pda.bdm.module.foss.login.shared.exception;

import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.foss.login.shared.domain.ExceptionConstant;

public class ExpressVehicleNotUserException extends PdaBusiException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7252874203115601146L;
	
	/**
	 * 构造方法
	 * @param message 异常消息
	 */
	public ExpressVehicleNotUserException() {
		super();
	}
	
	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public ExpressVehicleNotUserException(Throwable cause) {
		super(cause);
	} 
	
	public String getErrCode() {
		return ExceptionConstant.EXPRESS_VEHICLE_NOT_EXIST_USER;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "快递车辆信息中无该用户!";
	}
	
	@Override
	public Object[] getErrorArguments(){
		return null;
	}
	
}
