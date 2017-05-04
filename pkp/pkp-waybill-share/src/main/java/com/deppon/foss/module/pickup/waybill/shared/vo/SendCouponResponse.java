package com.deppon.foss.module.pickup.waybill.shared.vo;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SendCouponResponse")
public class SendCouponResponse {
	// 优惠券编码
	protected Map<String, String> couponCodes;
	// 是否成功
	protected String ifSuccess;
	// 异常信息
	protected String errorMsg;

	public Map<String, String> getCouponCodes() {
		return couponCodes;
	}

	public void setCouponCodes(Map<String, String> couponCodes) {
		this.couponCodes = couponCodes;
	}

	/**
	 * @return the ifSuccess
	 */
	public String getIfSuccess() {
		return ifSuccess;
	}

	/**
	 * @param ifSuccess
	 *            the ifSuccess to set
	 */
	public void setIfSuccess(String ifSuccess) {
		this.ifSuccess = ifSuccess;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg
	 *            the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
