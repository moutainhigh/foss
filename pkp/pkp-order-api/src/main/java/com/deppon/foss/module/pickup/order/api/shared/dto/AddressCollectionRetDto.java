package com.deppon.foss.module.pickup.order.api.shared.dto;

public class AddressCollectionRetDto {
	/**
	 * 失败编码
	 * */
	private String errCode;
	/**
	 * 错误id
	 * */
	private String errId;
	/**
	 * 错误信息
	 * */
	private String errMessage;
	/**
	 * 成功状态1成功、0失败
	 * */
	private String retStatus;
	/**
	 * 响应结果
	 * */
	private String retValue;
	
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
	public String getErrMessage() {
		return errMessage;
	}
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	public String getRetStatus() {
		return retStatus;
	}
	public void setRetStatus(String retStatus) {
		this.retStatus = retStatus;
	}
	public String getRetValue() {
		return retValue;
	}
	public void setRetValue(String retValue) {
		this.retValue = retValue;
	}
}
