/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-pda-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/dto/LoadScanDetailDto.java
 *  
 *  FILE NAME          :LoadScanDetailDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.server.service
 * FILE    NAME: PDALoadScanDetail.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.dubbo.api.define;

import java.io.Serializable;
import java.util.Date;

/**
 * pda装车扫描记录明细
 * @author 332209-FOSS-ruilibao
 * @date 2017年4月18日
 */
public class LoadScanDetailDubboDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7701873652579759939L;
	
	/**是否合车*/
	private String beJoinCar;
	/**库存件数*/
	private int stockQty;
	/**收货部门名称*/
    private String receiveOrgName;	
    /**到达部门名称*/
    private String reachOrgName;	
    /**备注*/
    private String notes;
    
    /**任务编号*/
	private String taskNo;
	/**运单号*/
	private String wayBillNo;
	/**流水号*/
	private String serialNo;
	/**类型*/
	private String type;
	/**重量*/
	private double weight;
	/**体积*/
	private double volume;
	/**货名*/
	private String goodsName;
	/**设备号*/
	private String deviceNo;
	/**扫描状态*/
	private String scanState;
	/**扫描时间*/
	private Date scanTime;
	/**运输性质名称*/
    private String transportType;	
    /**包装*/
    private String pack;
    /**提货网点编码**/
	private String reachOrgCode;
	/**是否包扫描**/
	private String isPackageScan;
	
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public String getScanState() {
		return scanState;
	}

	public void setScanState(String scanState) {
		this.scanState = scanState;
	}

	public Date getScanTime() {
		return scanTime;
	}

	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public String getReachOrgCode() {
		return reachOrgCode;
	}

	public void setReachOrgCode(String reachOrgCode) {
		this.reachOrgCode = reachOrgCode;
	}

	public String getIsPackageScan() {
		return isPackageScan;
	}

	public void setIsPackageScan(String isPackageScan) {
		this.isPackageScan = isPackageScan;
	}

	/**
	 * Gets the 是否合车.
	 *
	 * @return the 是否合车
	 */
	public String getBeJoinCar() {
		return beJoinCar;
	}
	
	/**
	 * Sets the 是否合车.
	 *
	 * @param beJoinCar the new 是否合车
	 */
	public void setBeJoinCar(String beJoinCar) {
		this.beJoinCar = beJoinCar;
	}
	
	/**
	 * Gets the 库存件数.
	 *
	 * @return the 库存件数
	 */
	public int getStockQty() {
		return stockQty;
	}
	
	/**
	 * Sets the 库存件数.
	 *
	 * @param stockQty the new 库存件数
	 */
	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
	}
	
	/**
	 * Gets the 收货部门名称.
	 *
	 * @return the 收货部门名称
	 */
	public String getReceiveOrgName() {
		return receiveOrgName;
	}
	
	/**
	 * Sets the 收货部门名称.
	 *
	 * @param receiveOrgName the new 收货部门名称
	 */
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}
	
	/**
	 * Gets the 到达部门名称.
	 *
	 * @return the 到达部门名称
	 */
	public String getReachOrgName() {
		return reachOrgName;
	}
	
	/**
	 * Sets the 到达部门名称.
	 *
	 * @param reachOrgName the new 到达部门名称
	 */
	public void setReachOrgName(String reachOrgName) {
		this.reachOrgName = reachOrgName;
	}
	
	/**
	 * Gets the 备注.
	 *
	 * @return the 备注
	 */
	public String getNotes() {
		return notes;
	}
	
	/**
	 * Sets the 备注.
	 *
	 * @param notes the new 备注
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "LoadScanDetailDto [beJoinCar=" + beJoinCar + ", stockQty=" + stockQty + ", receiveOrgName="
				+ receiveOrgName + ", reachOrgName=" + reachOrgName + ", notes=" + notes + ", getTaskNo()="
				+ getTaskNo() + ", getWayBillNo()=" + getWayBillNo() + ", getSerialNo()=" + getSerialNo()
				+ ", getType()=" + getType() + ", getWeight()=" + getWeight() + ", getVolume()=" + getVolume()
				+ ", getGoodsName()=" + getGoodsName() + ", getDeviceNo()=" + getDeviceNo() + ", getScanState()="
				+ getScanState() + ", getScanTime()=" + getScanTime() + ", getTransportType()=" + getTransportType()
				+ ", getPack()=" + getPack() + ", getReachOrgCode()=" + getReachOrgCode() + ", getIsPackageScan()="
				+ getIsPackageScan() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}