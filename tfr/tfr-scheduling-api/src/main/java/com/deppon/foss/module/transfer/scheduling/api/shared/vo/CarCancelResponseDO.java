package com.deppon.foss.module.transfer.scheduling.api.shared.vo;

import java.io.Serializable;

/**
 * 临时租车取消标记响应实体
 * @author 337470
 *
 */
public class CarCancelResponseDO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//成功失败状态 0 成功 1 失败
	private String status;
	//异常信息
	private String resultMsg;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
	
}
