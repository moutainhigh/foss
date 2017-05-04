package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;

/**
 * No.347069 从QMS获取异常货处置审批结果
 * 
 * @author zhang
 * @date 347069
 */
public class AbnormalBillApprovalDto implements Serializable {
	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 6753085545775301560L;
	/**
	 * 处置状态
	 */
	private String handleStatus;
	/**
	 * 信息
	 */
	private String msg;
	/**
	 * 小票单号
	 */
	private String paycode;
	/**
	 * 状态业务含义
	 */
	private String statusMeanin;

	public String getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPaycode() {
		return paycode;
	}

	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}

	public String getStatusMeanin() {
		return statusMeanin;
	}

	public void setStatusMeanin(String statusMeanin) {
		this.statusMeanin = statusMeanin;
	}
}
