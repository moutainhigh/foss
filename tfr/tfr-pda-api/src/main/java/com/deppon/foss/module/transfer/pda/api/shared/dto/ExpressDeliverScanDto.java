/*
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.shared.dto
 * FILE    NAME: ExpressDeliverScanDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 快递扫描Dto
 * @author dp-duyi
 * @date 2013-7-24 下午3:00:08
 */
public class ExpressDeliverScanDto implements Serializable {

	private static final long serialVersionUID = 2075791909076280316L;
	/**任务号*/
	private String taskNo;
	/**运单号*/
	private String wayBillNo;
	/**流水号*/
	private String serialNo;
	/**扫描时间*/
	private Date scanTime;
	/**货物类型:正常、撤销*/
	private String type;
	/**扫描状态：扫描、手输*/
	private String scanState;
	/**设备号*/
	private String deviceNo;
	/**派送单号*/
	private String deliverNo;
	/**到达联件数**/
	private int arrivePieces;
	public String getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getScanState() {
		return scanState;
	}
	public void setScanState(String scanState) {
		this.scanState = scanState;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getDeliverNo() {
		return deliverNo;
	}
	public void setDeliverNo(String deliverNo) {
		this.deliverNo = deliverNo;
	}
	public int getArrivePieces() {
		return arrivePieces;
	}
	public void setArrivePieces(int arrivePieces) {
		this.arrivePieces = arrivePieces;
	}
	
}
