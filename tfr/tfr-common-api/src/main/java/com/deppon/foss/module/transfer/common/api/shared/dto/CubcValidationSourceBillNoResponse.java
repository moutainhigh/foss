package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;

/**
 * 
 * 
 * @Description: 中转调用cubc验证空运合大票明细
 * @author 328768
 * @date 2017年1月3日 下午8:45:24
 *
 */
public class CubcValidationSourceBillNoResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7336363974249977807L;
	
	/**
	 * 是否成功
	 */
	private boolean success = false;
	/**
	 * 异常信息
	 */
	private String exceptionMsg;
	/**
	 * 是否已审核，核销,已付款
	 */
	private boolean audited = false;
	
	/**
	 * 原因
	 */
	private String reason;
	
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
	public boolean isAudited() {
		return audited;
	}
	public void setAudited(boolean audited) {
		this.audited = audited;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

}
