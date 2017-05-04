package com.deppon.foss.module.transfer.unload.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

public class BCMachWaybillSortScanEntity {
	private String id;
	private String waybillNo;
	private Date scanTime;
	private Date lastScanTime;
	private BigDecimal weight;
	private BigDecimal volumn;
	private int piece;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	public Date getLastScanTime() {
		return lastScanTime;
	}
	public void setLastScanTime(Date lastScanTime) {
		this.lastScanTime = lastScanTime;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public BigDecimal getVolumn() {
		return volumn;
	}
	public void setVolumn(BigDecimal volumn) {
		this.volumn = volumn;
	}
	public int getPiece() {
		return piece;
	}
	public void setPiece(int piece) {
		this.piece = piece;
	}
	
	
	
}
