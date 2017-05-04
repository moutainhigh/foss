/*
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.shared.dto
 * FILE    NAME: SortingScanDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * 非PDA查询dto
 * @author 200978  xiaobingcheng
 * 2014-8-31
 */
public class SortingScanDto implements Serializable {
	
	private static final long serialVersionUID = -5689869219143712161L;
	/**运单号*/
	private String waybillNo;
	/**流水号*/
	private String serialNo;
	/**扫描时间*/
	private Date scanTime;
	/**扫描类型:上分拣、下分拣*/
	private String scanType;
	/**设备号*/
	private String deviceNo;
	/**操作人编码*/
	private String operatorCode;
	/**操作部门编码*/
	private String orgCode;
	/**开始时间*/
	private Date queryTimeEnd;
	/**截止时间*/
	private Date queryTimeBegin;
	/**发货客户编码*/
	private String deliveryCustomerCode;
	
	
	
	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}
	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	public String getScanType() {
		return scanType;
	}
	public void setScanType(String scanType) {
		this.scanType = scanType;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public Date getQueryTimeEnd() {
		return queryTimeEnd;
	}
	public void setQueryTimeEnd(Date queryTimeEnd) {
		this.queryTimeEnd = queryTimeEnd;
	}
	public Date getQueryTimeBegin() {
		return queryTimeBegin;
	}
	public void setQueryTimeBegin(Date queryTimeBegin) {
		this.queryTimeBegin = queryTimeBegin;
	}
	
}
