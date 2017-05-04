package com.deppon.foss.module.transfer.load.api.shared.dto;

/** 
 * @className: ComplementResultDto
 * @author: ShiWei shiwei@outlook.com
 * @description: 补码结果提示信息
 * @date: 2013-8-26 下午10:33:50
 * 
 */
public class ComplementResultDto {
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 补码结果
	 */
	private String beSuccess;
	
	/**
	 * 失败原因
	 */
	private String message;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getBeSuccess() {
		return beSuccess;
	}

	public void setBeSuccess(String beSuccess) {
		this.beSuccess = beSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
