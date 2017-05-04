package com.deppon.pda.bdm.module.core.shared.domain;
/**
 * PDA请求响应结果
 * @author gaojia
 * @date Sep 6,2012 14:33:30 PM
 * @version 1.0
 * @since
 */
public class Result {
	
	/**
	 * 失败
	 */
	public static final int ERROR = 0;
	
	/**
	 * 成功
	 */
	public static final int SUCCESS = 1;

	/**
	 * 错误编号
	 */
	private String errCode;
	/**
	 * 错误ID
	 */
	private String errId;
	/**
	 * 错误信息
	 */
	private String errMsg;
	/**
	 * 响应状态（成功或者失败）
	 */
	private int retStatus;
	/**
	 * 响应结果
	 */
	private Object retValue;
	
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrId() {
		return errId;
	}
	public void setErrId(String errId) {
		this.errId = errId;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public int getRetStatus() {
		return retStatus;
	}
	public void setRetStatus(int retStatus) {
		this.retStatus = retStatus;
	}
	public Object getRetValue() {
		return retValue;
	}
	public void setRetValue(Object retValue) {
		this.retValue = retValue;
	}

	
}