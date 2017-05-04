package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.util.List;

public class SendQueryingResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3712624640184669058L;
	/**
	 * 信息
	 */
	private String exMsg;
	/**
	 * 状态(1：成功,0：失败)
	 */
	private int status;
	/**
	 * 结果集合
	 */
	private List<SendingWayBillInfoResponse> data;

	public String getExMsg() {
		return exMsg;
	}

	public void setExMsg(String exMsg) {
		this.exMsg = exMsg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<SendingWayBillInfoResponse> getData() {
		return data;
	}

	public void setData(List<SendingWayBillInfoResponse> data) {
		this.data = data;
	}

}
