package com.deppon.foss.module.transfer.pda.api.shared.domain;

import java.io.Serializable;

public class PDAOrderSerialNoDetailEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//流水号
	private String serialNo;

	//点单差异类型
	private String orderReportType;

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getOrderReportType() {
		return orderReportType;
	}

	public void setOrderReportType(String orderReportType) {
		this.orderReportType = orderReportType;
	}
}
