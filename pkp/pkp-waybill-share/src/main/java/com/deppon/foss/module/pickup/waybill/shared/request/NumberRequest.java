package com.deppon.foss.module.pickup.waybill.shared.request;

import java.io.Serializable;

public class NumberRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 123423423L;

	private String customerMobilePhone;

	public String getCustomerMobilePhone() {
		return customerMobilePhone;
	}

	public void setCustomerMobilePhone(String customerMobilePhone) {
		this.customerMobilePhone = customerMobilePhone;
	}
	
	
}
