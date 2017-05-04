package com.deppon.foss.module.generalquery.shared.domain;

import java.util.Date;
/**
 * 货物状态明细
 * @author mt
 * @date 2013-01-11 16:58
 * @version 1.0
 * @since
 */
public class TraceInfoEntity {
	/**
	 * 货物状态 
	 */
	private String crgStatus;
	/**
	 * 时间 
	 */
	private Date operTime;
	public String getCrgStatus() {
		return crgStatus;
	}
	public void setCrgStatus(String crgStatus) {
		this.crgStatus = crgStatus;
	}
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	
}
