package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;

public class CUBCOtherRevenueResultDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String otherRevenueNo;
	
	private String meg;

	public String getOtherRevenueNo() {
		return otherRevenueNo;
	}

	public void setOtherRevenueNo(String otherRevenueNo) {
		this.otherRevenueNo = otherRevenueNo;
	}

	public String getMeg() {
		return meg;
	}

	public void setMeg(String meg) {
		this.meg = meg;
	}
	
	
}
