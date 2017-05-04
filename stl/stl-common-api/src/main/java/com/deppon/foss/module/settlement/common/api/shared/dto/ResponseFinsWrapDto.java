package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;

/**
 * @author 218392 zhangyongxue
 * @date 2016-02-16 15:14:26
 * 客户端接收 FINS服务端的响应信息实体DTO
 */
public class ResponseFinsWrapDto implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 是否成功（1：成功 ，0：失败）
	 */
	private String isSuccess;
	/**
	 * 失败原因
	 */
	private String failReason;
	/**
	 * 编号
	 */
	private String billNum;
	
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public String getBillNum() {
		return billNum;
	}
	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}
	
	
}
