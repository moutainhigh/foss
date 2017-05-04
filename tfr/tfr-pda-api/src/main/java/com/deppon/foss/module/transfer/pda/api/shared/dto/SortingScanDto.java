/*
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.shared.dto
 * FILE    NAME: SortingScanDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 分拣扫描Dto
 * @author dp-duyi
 * @date 2013-7-26 上午11:12:16
 */
public class SortingScanDto implements Serializable {
	
	private static final long serialVersionUID = -5689869219143712161L;
	/**运单号*/
	private String wayBillNo;
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
	/**发货客户编码 218427-hongwy*/
	private String deliveryCustomerCode;
	
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
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
	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}
	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}
	
}
