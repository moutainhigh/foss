package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class AirQueryFlightArriveSerialEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2874897456215332L;
    /**
     * 空运到达运单明细ID
     */
	private String airFlightArriveDetailId;
	/**
	 * 流水号
	 */
	private String serialNo;
	/**
	 * 到达时间
	 */
	private Date arriveTime;
	/**
	 * 备注
	 */
	private String note;
	
	public String getAirFlightArriveDetailId() {
		return airFlightArriveDetailId;
	}
	public void setAirFlightArriveDetailId(String airFlightArriveDetailId) {
		this.airFlightArriveDetailId = airFlightArriveDetailId;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public Date getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
