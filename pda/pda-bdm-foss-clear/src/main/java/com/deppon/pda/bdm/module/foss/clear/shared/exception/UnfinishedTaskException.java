package com.deppon.pda.bdm.module.foss.clear.shared.exception;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;



/**
 * 未完成任务异常类
 * @author chenliang       
 * @version 1.0     
 * @created 2012-9-13 下午07:34:51
 */
public class UnfinishedTaskException extends PdaBusiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造方法
	 * @param message 异常消息
	 */
	public UnfinishedTaskException() {
		super();
	}

	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public UnfinishedTaskException(Throwable cause) {
		super(cause);
	}

	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_CLEAR_UNFINISHED_TASK_ERR;
	}

	protected String getNativeMessageKey() {
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "任务未完成，请等待其他用户完成后再提交!";
	}

	@Override
	public Object[] getErrorArguments() {
		return null;
	}

}
