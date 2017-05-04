package com.deppon.pda.bdm.module.foss.dprtarr.shared.domain;

import java.util.Date;

/**
 * 车辆出发信息
 * @author gaojia
 * @date Sep 6,2012 14:33:30 PM
 * @version 1.0
 * @since
 */
public class DprtTruckInfo {

	/**
	 * 司机姓名 --
	 */
	private String driverName;
	/**
	 * 司机联系方式 --
	 */
	private String driverPhone;
	/**
	 * 车牌号  --
	 */
	private String truckCode;
	/**
	 * 放行时间 --
	 */
	private Date relseTime;
	/**
	 * 放行人工号  --
	 */
	private String relseUserCode;
	/**
	 * 放行类型  --
	 */
	private String relseType;
	/**
	 * 交接单号  --
	 */
	private String receiptCodes;
	/**
	 * 封签号  --
	 */
	private String sealsCodes;
	/**
	 * 车辆状态  --
	 */
	private String truckStatus;
	/**
	 * 放行理由明细  --
	 */
	private String relseCause;
	/**
	 * 放行状态
	 */
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverPhone() {
		return driverPhone;
	}
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public String getRelseCause() {
		return relseCause;
	}
	public void setRelseCause(String relseCause) {
		this.relseCause = relseCause;
	}
	public Date getRelseTime() {
		return relseTime;
	}
	public void setRelseTime(Date relseTime) {
		this.relseTime = relseTime;
	}
	public String getRelseType() {
		return relseType;
	}
	public void setRelseType(String relseType) {
		this.relseType = relseType;
	}
	public String getRelseUserCode() {
		return relseUserCode;
	}
	public void setRelseUserCode(String relseUserCode) {
		this.relseUserCode = relseUserCode;
	}

	public String getReceiptCodes() {
		return receiptCodes;
	}
	public void setReceiptCodes(String receiptCodes) {
		this.receiptCodes = receiptCodes;
	}
	public String getSealsCodes() {
		return sealsCodes;
	}
	public void setSealsCodes(String sealsCodes) {
		this.sealsCodes = sealsCodes;
	}
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	public String getTruckStatus() {
		return truckStatus;
	}
	public void setTruckStatus(String truckStatus) {
		this.truckStatus = truckStatus;
	}
	
}