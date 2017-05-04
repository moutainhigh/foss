package com.deppon.pda.bdm.module.foss.upgrade.shared.exception;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;

/**
 * 
  * @ClassName DLLVerNotFoundException 
  * @Description TODO 未找到程序版本信息异常
  * @author mt 
  * @date 2013-8-24 上午8:42:22
 */
public class DLLVerNotFoundException extends PdaBusiException {
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
	public DLLVerNotFoundException() {
		super();
	}

	/**
	 * 构造方法
	 * 
	 * @param cause
	 *            原因
	 */
	public DLLVerNotFoundException(Throwable cause) {
		super(cause);
	}

	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_DLL_PGMVER_NOTFOUND_ERR;
	}

	protected String getNativeMessageKey() {
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "未找到DLL程序版本升级包!";
	}

	@Override
	public Object[] getErrorArguments() {
		return null;
	}
}
