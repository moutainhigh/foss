package com.deppon.pda.bdm.module.foss.login.shared.exception;

import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.foss.login.shared.domain.ExceptionConstant;


/**     
 *      
 * 存在未完成的任务
 * @author chengang       
 * @version 1.0     
 * @created 2012-9-19 上午11:04:15    
 */
public class HasTaskNoFnshException extends PdaBusiException{

	private static final long serialVersionUID = -8795819955980486273L;
	
	private String taskType;
	/**
	 * 构造方法
	 * @param message 异常消息
	 */
	public HasTaskNoFnshException(String taskType) {
		super();
		this.taskType = taskType;
	}
	
	/**
	 * 构造方法
	 * @param cause 原因
	 */
	public HasTaskNoFnshException(Throwable cause) {
		super(cause);
	} 
	
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_LOGIN_TASKNOFNSH;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "存在未完成的%s任务!";
	}
	
	@Override
	public Object[] getErrorArguments(){
		Object[] arg = new Object[]{
				this.getTaskType()
		};
		return arg;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	
}
