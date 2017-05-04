package com.deppon.foss.module.pickup.pricing.api.shared.dto;

/**
 * 
 * FOSS同步到PTP返回的参数
 * 
 * @author 265475	 DP-Foss-zoushengli
 * 
 * @date 2016-1-18 下午11:35:08
 * 
 */
public class WaybillRegionInfoResponseDto {
	private String resultCode; // 成功或失败的标识（0-失败 1-成功）
	private String errorMessage; // 失败原因（如果处理失败，此字段为必填）
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
