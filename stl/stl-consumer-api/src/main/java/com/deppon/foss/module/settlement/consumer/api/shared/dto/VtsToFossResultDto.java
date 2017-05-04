package com.deppon.foss.module.settlement.consumer.api.shared.dto;

/**
 * VTS返回结果信息
 * @author 331556 fanjingwei
 * @date 2016-05-20
 */
public class VtsToFossResultDto {
	/**
	 * 成功失败标识
	 */
	private boolean isSuccess;
	
	/**
	 * 消息
	 */
	private String msg;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
