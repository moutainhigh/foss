package com.deppon.esb.pojo.domain.foss2sms;

import java.util.List;

/**
 * The Class SmsSendRequest.
 */
public class SmsSendRequest {
	
	/** The sms authority. */
	private SmsAuthorityInfo smsAuthority;
	
	/** The sms infos. */
	private List<SmsInfo> smsInfos;
	
	
	/**
	 * Gets the sms authority.
	 * 
	 * @return the sms authority
	 */
	public SmsAuthorityInfo getSmsAuthority() {
		return smsAuthority;
	}
	
	/**
	 * Sets the sms authority.
	 * 
	 * @param smsAuthority
	 *            the new sms authority
	 */
	public void setSmsAuthority(SmsAuthorityInfo smsAuthority) {
		this.smsAuthority = smsAuthority;
	}
	
	/**
	 * Gets the sms infos.
	 * 
	 * @return the sms infos
	 */
	public List<SmsInfo> getSmsInfos() {
		return smsInfos;
	}
	
	/**
	 * Sets the sms infos.
	 * 
	 * @param smsInfos
	 *            the new sms infos
	 */
	public void setSmsInfos(List<SmsInfo> smsInfos) {
		this.smsInfos = smsInfos;
	}
}
