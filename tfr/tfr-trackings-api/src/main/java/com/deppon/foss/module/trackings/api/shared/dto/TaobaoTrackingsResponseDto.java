package com.deppon.foss.module.trackings.api.shared.dto;

public class TaobaoTrackingsResponseDto {
	/**
	 * 返回结果
	 *  "true"表示成功，"false"表示失败
	 */
	private String result;
	
	/**
	 * 返回消息
	 */
	private String message;
	//运单号
	private String waybillNo;
	//流水号
	private String serialNo;
	
	
	/**
	 * dop的规则 是否成功(必填) "true"表示成功，"false"表示失败
	* @fields success
	* @author 14022-foss-songjie
	* @update 2015年5月28日 上午10:03:06
	* @version V1.0
	*/
	private String success;
	
	/**
	 * dop的规则 失败原因
	* @fields reason
	* @author 14022-foss-songjie
	* @update 2015年5月28日 上午10:03:08
	* @version V1.0
	*/
	private String reason;
	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
}
