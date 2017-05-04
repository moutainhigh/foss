package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;

/**
 * 变更清单响应消息体
 * @author 328768
 *
 */
public class CubcModifyAirChangeResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//结果 失败为0，成功是1
	private int result;
	//原因 失败则必填
	private String reason;
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	

}
