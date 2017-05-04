package com.deppon.foss.module.transfer.common.api.shared.dto;

public class TaobaoTrackingsResponseDto {
	/**
	 * 返回结果
	 *  "true"表示成功，"false"表示失败
	private String result;
	 */
	/**
	 * 返回消息
	
	private String message;
	//运单号
	private String waybillNo;
	//流水号
	private String serialNo;
	 */
	
	/**
	 * dop的规则 是否成功(必填) "true"表示成功，"false"表示失败
	* @fields success
	* @author 14022-foss-songjie
	* @update 2015年5月28日 上午10:03:06
	* @version V1.0
	private String success;
	*/
	/**
	 * dop的规则 失败原因
	* @fields reason
	* @author 14022-foss-songjie
	* @update 2015年5月28日 上午10:03:08
	* @version V1.0
	private String reason;
	*/
	
	/**
	 * 必选  Y N 代表成功失败
	 */
	private String isSuccess;

	//可选	失败原因
	private String error;

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	
}
