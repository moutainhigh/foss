package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.io.Serializable;


public class QmsErrResponseParam implements Serializable {

	private static final long serialVersionUID = 1L;
	//差错编号
	private String errorID;
	//调用结果
	private String handleCode;
	//错误信息
	private String message;
	//是否已处理
	private String handle;
	//处理结果
	//private ErrorResultEntity result;
	private Object result;
	public String getErrorID() {
		return errorID;
	}
	public void setErrorID(String errorID) {
		this.errorID = errorID;
	}
	public String getHandleCode() {
		return handleCode;
	}
	public void setHandleCode(String handleCode) {
		this.handleCode = handleCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getHandle() {
		return handle;
	}
	public void setHandle(String handle) {
		this.handle = handle;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
}
