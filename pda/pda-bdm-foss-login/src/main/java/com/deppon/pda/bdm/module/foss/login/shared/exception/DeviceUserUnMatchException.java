package com.deppon.pda.bdm.module.foss.login.shared.exception;

import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.foss.login.shared.domain.ExceptionConstant;


/**
 * 
 * 设备所属人不匹配异常
 * 
 * @author lxw
 * @version 1.0
 * @created 2012-09-19
 */

public class DeviceUserUnMatchException extends PdaBusiException {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -6371693499345968894L;

	/**
	 * 构造方法
	 * 
	 * @param message
	 *            异常消息
	 */
	public DeviceUserUnMatchException() {
		super();
	}

	/**
	 * 构造方法
	 * 
	 * @param cause
	 *            原因
	 */
	public DeviceUserUnMatchException(Throwable cause) {
		super(cause);
	}

	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_DEPTUNMATCH_ERR;
	}

	protected String getNativeMessageKey() {
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "设备所属人不匹配!";
	}

	@Override
	public Object[] getErrorArguments() {
		return null;
	}
}
