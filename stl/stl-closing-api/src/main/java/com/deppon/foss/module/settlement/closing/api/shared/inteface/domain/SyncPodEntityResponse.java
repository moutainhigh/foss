package com.deppon.foss.module.settlement.closing.api.shared.inteface.domain;

import java.io.Serializable;

/**
 * 同步cubc的财务单据完结信息-响应
 * 
 * @author 269044
 * @date 2017-04-21
 */
public class SyncPodEntityResponse implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	// 返回結果 0 失败 1成功
	private int result;
	// 異常信息
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
