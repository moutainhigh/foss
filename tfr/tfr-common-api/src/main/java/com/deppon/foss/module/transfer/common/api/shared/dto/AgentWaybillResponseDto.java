package com.deppon.foss.module.transfer.common.api.shared.dto;

public class AgentWaybillResponseDto {
	/**
	 * 返回结果
	 *  "true"表示成功，"false"表示失败
	 */
	private String result;
	/**
	 * 返回编码
	 * 200: 提交成功
	 *	701: 拒绝订阅的快递公司
	 *	700: 订阅方的订阅数据存在错误（如不支持的快递公司、单号为空、单号超长等）
	 *	600: 您不是合法的订阅者（即授权Key出错）
	 *	500: 服务器错误（即快递100的服务器出理间隙或临时性异常，有时如果因为不按规范提交请求，比如快递公司参数写错等，也会报此错误）
	 *	501:重复订阅
	 */
	private String returnCode;
	/**
	 * 返回消息
	 */
	private String message;
	public String getResult() {
		return result;
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
