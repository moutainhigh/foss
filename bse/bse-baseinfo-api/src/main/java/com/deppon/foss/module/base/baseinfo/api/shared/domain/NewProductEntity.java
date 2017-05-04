package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;



public class NewProductEntity extends BaseEntity {
   
	private static final long serialVersionUID = 8851063892850222868L;

	private String customerCode;
	
	private Date startTime;
	
	private Date endTime;
	
	private String isNewProCus;

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getIsNewProCus() {
		return isNewProCus;
	}

	public void setIsNewProCus(String isNewProCus) {
		this.isNewProCus = isNewProCus;
	}

}
