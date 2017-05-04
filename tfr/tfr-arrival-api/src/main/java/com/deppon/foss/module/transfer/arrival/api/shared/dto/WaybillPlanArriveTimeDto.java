package com.deppon.foss.module.transfer.arrival.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

//晚到补差价Dto
public class WaybillPlanArriveTimeDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 预计到达时间
	 */
	private Date preArriveTime;
	
	/**
	 * 同步时间
	 */
	private Date syncTime;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public Date getPreArriveTime() {
		return preArriveTime;
	}

	public void setPreArriveTime(Date preArriveTime) {
		this.preArriveTime = preArriveTime;
	}

	public Date getSyncTime() {
		return syncTime;
	}

	public void setSyncTime(Date syncTime) {
		this.syncTime = syncTime;
	}
}
