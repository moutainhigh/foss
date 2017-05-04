package com.deppon.pda.bdm.module.core.shared.exception;

/**
 * 异常接口
 * @author wanghongling
 * @date 2012-09-10
 * @version 1.0
 *
 */
public interface IPdaException {
	/**
	 * 返回异常编码
	 * @return
	 */
	public String getErrCode();
	/**
	 * 返回异常ID
	 * @return
	 */
	public String getErrId();
	/**
	 * 返回异常消息
	 * @return
	 */
	public String getMessage();
	
	/**
	 * 返回默认的消息
	 * @return
	 */
	public String getDefaultMessage();
	
	/**
	 * 返回本地消息
	 * @return
	 */
	public String getNativeMessage();
	
	/**
	 * 返回异常参数
	 * @return
	 */
	public Object[] getErrorArguments();
	
}
