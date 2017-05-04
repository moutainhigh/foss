package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

public class SmallTicketEntity {

	private String smallTicketNum;//小票单号
	
	
	private String wayBillNo;//运单号

	public String getWayBillNo() {
		return wayBillNo;
	}

	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}

	public String getSmallTicketNum() {
		return smallTicketNum;
	}

	public void setSmallTicketNum(String smallTicketNum) {
		this.smallTicketNum = smallTicketNum;
	}
	
}
