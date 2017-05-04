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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/dto/PDAScanDetailDto.java
 *  
 *  FILE NAME          :PDAScanDetailDto.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.shared.dto
 * FILE    NAME: PDAScanDetailDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * PDA扫描明细
 * @author dp-duyi
 * @date 2012-12-17 下午2:58:19
 */
public class PDAScanDetailDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1434689678874523991L;
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
	
	/**
	 * Gets the 任务编号.
	 *
	 * @return the 任务编号
	 */
	public String getTaskNo() {
		return taskNo;
	}
	
	/**
	 * Sets the 任务编号.
	 *
	 * @param taskNo the new 任务编号
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	
	/**
	 * Gets the 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWayBillNo() {
		return wayBillNo;
	}
	
	/**
	 * Sets the 运单号.
	 *
	 * @param wayBillNo the new 运单号
	 */
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	
	/**
	 * Gets the 流水号.
	 *
	 * @return the 流水号
	 */
	public String getSerialNo() {
		return serialNo;
	}
	
	/**
	 * Sets the 流水号.
	 *
	 * @param serialNo the new 流水号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	/**
	 * Gets the 类型.
	 *
	 * @return the 类型
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Sets the 类型.
	 *
	 * @param type the new 类型
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Gets the 重量.
	 *
	 * @return the 重量
	 */
	public double getWeight() {
		return weight;
	}
	
	/**
	 * Sets the 重量.
	 *
	 * @param weight the new 重量
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	/**
	 * Gets the 体积.
	 *
	 * @return the 体积
	 */
	public double getVolume() {
		return volume;
	}
	
	/**
	 * Sets the 体积.
	 *
	 * @param volume the new 体积
	 */
	public void setVolume(double volume) {
		this.volume = volume;
	}
	
	/**
	 * Gets the 货名.
	 *
	 * @return the 货名
	 */
	public String getGoodsName() {
		return goodsName;
	}
	
	/**
	 * Sets the 货名.
	 *
	 * @param goodsName the new 货名
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	/**
	 * Gets the 设备号.
	 *
	 * @return the 设备号
	 */
	public String getDeviceNo() {
		return deviceNo;
	}
	
	/**
	 * Sets the 设备号.
	 *
	 * @param deviceNo the new 设备号
	 */
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	
	/**
	 * Gets the 扫描状态.
	 *
	 * @return the 扫描状态
	 */
	public String getScanState() {
		return scanState;
	}
	
	/**
	 * Sets the 扫描状态.
	 *
	 * @param scanState the new 扫描状态
	 */
	public void setScanState(String scanState) {
		this.scanState = scanState;
	}
	
	/**
	 * Gets the 扫描时间.
	 *
	 * @return the 扫描时间
	 */
	public Date getScanTime() {
		return scanTime;
	}
	
	/**
	 * Sets the 扫描时间.
	 *
	 * @param scanTime the new 扫描时间
	 */
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	
	/**
	 * Gets the 运输性质.
	 *
	 * @return the 运输性质
	 */
	public String getTransportType() {
		return transportType;
	}
	
	/**
	 * Sets the 运输性质.
	 *
	 * @param transportType the new 运输性质
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	
	/**
	 * Gets the 包装.
	 *
	 * @return the 包装
	 */
	public String getPack() {
		return pack;
	}
	
	/**
	 * Sets the 包装.
	 *
	 * @param pack the new 包装
	 */
	public void setPack(String pack) {
		this.pack = pack;
	}

	/**
	 * 
	 * <p>提货网点编码</p> 
	 * @date 2014-9-22 上午10:16:27
	 * @return
	 * @see
	 */
	public final String getReachOrgCode() {
		return reachOrgCode;
	}

	/**
	 * 
	 * <p>提货网点编码</p> 
	 * @date 2014-9-22 上午10:16:38
	 * @param stationNumber
	 * @see
	 */
	public final void setReachOrgCode(String reachOrgCode) {
		this.reachOrgCode = reachOrgCode;
	}

	/**
	 * Gets the 是否包扫描.
	 *
	 * @return the 是否包扫描
	 */
	public final String getIsPackageScan() {
		return isPackageScan;
	}

	/**
	 * Sets the 是否包扫描.
	 *
	 * @param isPackageScan the new 包装
	 */
	public final void setIsPackageScan(String isPackageScan) {
		this.isPackageScan = isPackageScan;
	}

}