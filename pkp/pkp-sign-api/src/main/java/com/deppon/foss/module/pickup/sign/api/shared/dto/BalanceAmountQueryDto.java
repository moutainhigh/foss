package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;

public class BalanceAmountQueryDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String consigneeCode;

	public String getConsigneeCode() {
		return consigneeCode;
	}

	public void setConsigneeCode(String consigneeCode) {
		this.consigneeCode = consigneeCode;
	}

	public BalanceAmountQueryDto(String consigneeCode) {
		super();
		this.consigneeCode = consigneeCode;
	}
	
}
