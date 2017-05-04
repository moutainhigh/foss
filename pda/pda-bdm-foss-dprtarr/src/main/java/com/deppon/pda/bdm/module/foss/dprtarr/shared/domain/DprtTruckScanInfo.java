package com.deppon.pda.bdm.module.foss.dprtarr.shared.domain;

import java.util.Date;

/**
 * 
 * @author Administrator   出发扫描实体
 *
 */
public class DprtTruckScanInfo {
	
	/**
	 * 放行条码
	 */
	private String relseCode;
	/**
	 * 扫描时间
	 */
	private Date scanTime;
	/**
	 * 车牌号
	 */
	private String truckCode;
	
	public String getRelseCode() {
		return relseCode;
	}
	public void setRelseCode(String relseCode) {
		this.relseCode = relseCode;
	}
	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	
	
}
