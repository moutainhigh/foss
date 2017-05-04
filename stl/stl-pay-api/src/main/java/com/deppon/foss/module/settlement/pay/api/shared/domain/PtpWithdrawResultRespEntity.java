/**
 * 
 */
package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.io.Serializable;

/**
 * 合伙人提现-报账系统审批结果通知合伙人系统-响应Response实体
 * @author foss-Jiang Xun
 * @date 2016-02-26 下午4:39:00
 */
public class PtpWithdrawResultRespEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 消息
	 */
	private String message;
	
	/**
	 * 是否成功
	 */
	private boolean result;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
}
