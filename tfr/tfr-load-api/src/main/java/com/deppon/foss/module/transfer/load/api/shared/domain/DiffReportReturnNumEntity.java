package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class DiffReportReturnNumEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//退回次数
	private int returnNum;
	//少货次数
	private int loseNum;
	//运单号
	private String waybillNo;
	//交单时间
	private Date surrenderTime;
	public int getReturnNum() {
		return returnNum;
	}
	public void setReturnNum(int returnNum) {
		this.returnNum = returnNum;
	}
	public int getLoseNum() {
		return loseNum;
	}
	public void setLoseNum(int loseNum) {
		this.loseNum = loseNum;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public Date getSurrenderTime() {
		return surrenderTime;
	}
	public void setSurrenderTime(Date surrenderTime) {
		this.surrenderTime = surrenderTime;
	}

	
}
