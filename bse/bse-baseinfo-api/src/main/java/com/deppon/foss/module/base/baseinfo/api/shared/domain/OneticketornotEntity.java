package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

public class OneticketornotEntity extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 客户编码
     */
	private String code;
	
	 /**
     * 是否一票多件
     */
	private String isoneticketornot;

	 /**
     * 一票多件申请事由
     */
    
	private String ticketdescription;

	 /**
     * 有效性
     */
    private String active;
    
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIsoneticketornot() {
		return isoneticketornot;
	}

	public void setIsoneticketornot(String isoneticketornot) {
		this.isoneticketornot = isoneticketornot;
	}

	public String getTicketdescription() {
		return ticketdescription;
	}

	public void setTicketdescription(String ticketdescription) {
		this.ticketdescription = ticketdescription;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
}
