package com.deppon.foss.module.transfer.common.api.shared.dto;
/**
 * @author niuly
 * @function 上报无标签多货QMS返回结果
 */
public class ResponseFromQmsDto {
	//1—成功 0-失败
	private String callResult;
	//返回结果说明
	private String errmsg;
	
	private String isExpress;
	
	public String getCallResult() {
		return callResult;
	}
	public void setCallResult(String callResult) {
		this.callResult = callResult;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getIsExpress() {
		return isExpress;
	}
	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}
}
