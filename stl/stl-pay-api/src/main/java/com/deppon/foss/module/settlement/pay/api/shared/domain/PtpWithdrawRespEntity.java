/**
 * 
 */
package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.io.Serializable;

/**
 * 合伙人提现-合伙人校验并付款-响应Response实体
 * @author foss-Jiang Xun
 * @date 2016-02-24 下午5:35:00
 */
public class PtpWithdrawRespEntity implements Serializable{
	
	private static final long serialVersionUID = 8169863324233033356L;
	
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
