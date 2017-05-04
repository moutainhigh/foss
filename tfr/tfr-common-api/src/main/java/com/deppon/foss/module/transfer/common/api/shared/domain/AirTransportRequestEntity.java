package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 空运分单合票接口请求Entity
 *@version 1.0
 *@author 268220-chen min
 *@date  2016-4-29 下午2:55:43
 */
public class AirTransportRequestEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 开单方式
	 */
	
	private String billWay;
	
	/**
	 * 开单开始时间
	 */
	private Date billBeginTime;
	/**
	 * 开单结束时间
	 */
	private Date BillEndTime;
	/**
	 * 下一外场
	 */
	private String NextOrgCode;
	/**
	 * 空运配载员所在外场
	 */
	private String currentOrgCode;
	
	/**
	 * 目的站
	 */
	private String destinationStation;
	
	
	/**
	 * 库存状态
	 */
	private String stockState;
	
	/**
	 * 运输性质
	 */
	private String transportProperties;
	
	
	/**
	 * 入库开始时间 
	 */
	private Date storageBeginInTime;
	/**
	 * 入库截止时间
	 */
	private Date storageEndInTime;
	
	
	public Date getStorageBeginInTime() {
		return storageBeginInTime;
	}
	public void setStorageBeginInTime(Date storageBeginInTime) {
		this.storageBeginInTime = storageBeginInTime;
	}
	public Date getStorageEndInTime() {
		return storageEndInTime;
	}
	public void setStorageEndInTime(Date storageEndInTime) {
		this.storageEndInTime = storageEndInTime;
	}

	public String getBillWay() {
		return billWay;
	}

	public void setBillWay(String billWay) {
		this.billWay = billWay;
	}

	public Date getBillBeginTime() {
		return billBeginTime;
	}

	public void setBillBeginTime(Date billBeginTime) {
		this.billBeginTime = billBeginTime;
	}

	public Date getBillEndTime() {
		return BillEndTime;
	}

	public void setBillEndTime(Date billEndTime) {
		BillEndTime = billEndTime;
	}

	public String getNextOrgCode() {
		return NextOrgCode;
	}

	public void setNextOrgCode(String nextOrgCode) {
		NextOrgCode = nextOrgCode;
	}

	public String getCurrentOrgCode() {
		return currentOrgCode;
	}

	public void setCurrentOrgCode(String currentOrgCode) {
		this.currentOrgCode = currentOrgCode;
	}

	public String getDestinationStation() {
		return destinationStation;
	}

	public void setDestinationStation(String destinationStation) {
		this.destinationStation = destinationStation;
	}

	public String getStockState() {
		return stockState;
	}

	public void setStockState(String stockState) {
		this.stockState = stockState;
	}

	public String getTransportProperties() {
		return transportProperties;
	}

	public void setTransportProperties(String transportProperties) {
		this.transportProperties = transportProperties;
	}
	
    

	
}
