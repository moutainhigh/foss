package com.deppon.foss.module.trackings.api.shared.dto;

public class ResponseDto {
	//msgId 运单号和路由id组成,格式："单号:watchStatus,id0,id1,..."
	private String msgId;
	//返回结果：true或false
	private String result;
	private String returnCode;
	private String message;
	public String getResult() {
		return result;
	}
	
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public void setResult(String result) {
		this.result = result;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
