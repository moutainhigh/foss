package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;

/**
 * 
 * 
 * @Description: 中转调用cubc验证空运合大票明细


 * @author 328768
 * @date 2017年1月3日 下午8:26:40
 *
 */
public class CubcValidationAirJoinTicketResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6619188605398602942L;
	
	/**
	 * 是否已经核销/已审核
	 */
	private boolean audited = false;
	/**
	 * 是否出现异常
	 */
	private boolean success = false;
	/**
	 * 异常信息
	 */
	private String exceptionMsg;
	
	/**
	 * 原因
	 */
	private String reason;
	
	public boolean isAudited() {
		return audited;
	}
	public void setAudited(boolean audited) {
		this.audited = audited;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getExceptionMsg() {
		return exceptionMsg;
	}
	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
