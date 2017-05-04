package com.deppon.foss.module.transfer.common.api.shared.vo;

import com.deppon.foss.framework.entity.BaseEntity;

public class AgentWaybillExceptionDto extends BaseEntity{
	private static final long serialVersionUID = 1L;
	/**
	 * 单号
	 */
	private String waybillNo;
	/**
	 * 异常编码
	 */
	private String code;
	/**
	 * 异常信息
	 */
	private String message;
	/**
	 * 异常类型
	 */
	private String type;
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
