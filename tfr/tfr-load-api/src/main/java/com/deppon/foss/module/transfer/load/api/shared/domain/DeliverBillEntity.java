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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/DeliverBillEntity.java
 *  
 *  FILE NAME          :DeliverBillEntity.java
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
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 分配派送单：派送单实体
 * 
 * @author Administrator
 * @date 2012-10-11 下午12:56:49
 * @since
 * @version
 */
public class DeliverBillEntity extends BaseEntity{

	
	private static final long serialVersionUID = 832865583095100906L;
	private String id; 
	private String billNo; 
	private String vehicleNo; 
	private String driver; 
	private String state;
	private int wayBillQtyTotal; 
	private int fastWayBillQtyTotal; 
	private int goodsQtyTotal; 
	private double volumeTotal; 
	private double weightTotal; 
	private double arriveFeeTotal; 
	private String createTime;

	/**
	 * 派送单最后更改时间
	 */
	private Date operateTime;
	
	/**
	 * 派送单修改时的时间
	 */
	private Date modifyTime;
	
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public int getWayBillQtyTotal() {
		return wayBillQtyTotal;
	}
	public void setWayBillQtyTotal(int wayBillQtyTotal) {
		this.wayBillQtyTotal = wayBillQtyTotal;
	}
	public int getFastWayBillQtyTotal() {
		return fastWayBillQtyTotal;
	}
	public void setFastWayBillQtyTotal(int fastWayBillQtyTotal) {
		this.fastWayBillQtyTotal = fastWayBillQtyTotal;
	}
	public int getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	public void setGoodsQtyTotal(int goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	public double getVolumeTotal() {
		return volumeTotal;
	}
	public void setVolumeTotal(double volumeTotal) {
		this.volumeTotal = volumeTotal;
	}
	public double getWeightTotal() {
		return weightTotal;
	}
	public void setWeightTotal(double weightTotal) {
		this.weightTotal = weightTotal;
	}
	public double getArriveFeeTotal() {
		return arriveFeeTotal;
	}
	public void setArriveFeeTotal(double arriveFeeTotal) {
		this.arriveFeeTotal = arriveFeeTotal;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}