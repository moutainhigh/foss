package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class EffectiveSaveDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * 生效日期
	 */
	private Date effectiveDate;
	/**
	 * 
	 * 生效状态
	 */
	private String effectiveStatus;
	
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date date) {
		this.effectiveDate = date;
	}
	public String getEffectiveStatus() {
		return effectiveStatus;
	}
	public void setEffectiveStatus(String effectiveStatus) {
		this.effectiveStatus = effectiveStatus;
	}
}
