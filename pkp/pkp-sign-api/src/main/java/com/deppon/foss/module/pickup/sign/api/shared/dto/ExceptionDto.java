package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;


/**
 * 异常签收操作DTO
 * @author 159231
 *
 */
public class ExceptionDto implements Serializable  {
	//序列
	private static final long serialVersionUID = -6522770559854851487L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 签收情况
	 */
	private String situation;
	/**
	 * 异常类型
	 */
	private String exceptionType;
	/**
	 * 异常类型描述
	 */
	private String exceptionTypeDesc;
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getSituation() {
		return situation;
	}
	public void setSituation(String situation) {
		this.situation = situation;
	}
	public String getExceptionType() {
		return exceptionType;
	}
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}
	public String getExceptionTypeDesc() {
		return exceptionTypeDesc;
	}
	public void setExceptionTypeDesc(String exceptionTypeDesc) {
		this.exceptionTypeDesc = exceptionTypeDesc;
	}
	
}