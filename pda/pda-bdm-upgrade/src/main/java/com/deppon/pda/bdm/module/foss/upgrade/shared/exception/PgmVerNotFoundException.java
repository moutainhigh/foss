package com.deppon.pda.bdm.module.foss.upgrade.shared.exception;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;

/**
 * 
 * 未找到程序版本信息异常
 * 
 * @author chengang
 * @version 1.0
 * @created 2012-9-14 下午03:55:45
 */

public class PgmVerNotFoundException extends PdaBusiException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8795819955980486273L;

	/**
	 * 构造方法
	 * 
	 * @param message
	 *            异常消息
	 */
	public PgmVerNotFoundException() {
		super();
	}

	/**
	 * 构造方法
	 * 
	 * @param cause
	 *            原因
	 */
	public PgmVerNotFoundException(Throwable cause) {
		super(cause);
	}

	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_PGMVER_NOTFOUND_ERR;
	}

	protected String getNativeMessageKey() {
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "未找到程序版本升级包!";
	}

	@Override
	public Object[] getErrorArguments() {
		return null;
	}
}
