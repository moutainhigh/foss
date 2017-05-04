package com.deppon.foss.module.transfer.common.api.shared.dto;

/**
 * 同步各类信息至CUBC返回信息接收类
 * 
 * @author 316759-foss-RuipengWang
 *
 */
public class ResponseToCubcCallBack {
	
	/**
	 * 请求ID
	 */
	private String id;

	/**
	 * 处理结果
	 * “0”表示失败。“1”表示成功
	 */
	private String result;
	
	/**
	 * 返回处理信息
	 */
	private String reason;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "ResponseToCubcCallBack [id=" + id + ", result=" + result + ", reason=" + reason + "]";
	}
	
}
