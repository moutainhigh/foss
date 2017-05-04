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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/dto/ArriveBillDto.java
 *  
 *  FILE NAME          :ArriveBillDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-unload-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.shared.dto
 * FILE    NAME: BillDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;

/**
 * ArriveBillDto
 * @author dp-duyi
 * @date 2012-10-18 下午2:23:25
 */
public class ArriveBillDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -342418507228708385L;
	/**id*/
	private String id;                               //id
	/**billNo*/
	private String billNo;                           //单据编号
	/**billLevel*/
	private String billLevel;                        //单据级别
	/**origOrg*/
	private String origOrgName;                          //出发部门名称
	/**assignState*/
	private String assignState;                      //分配状态
	/**weightTotal*/
	private double weightTotal;                      //总重量
	/**goodsQtyTotal*/
	private double goodsQtyTotal;                    //总件数
	/**fastWayBillQtyTotal*/
	private double fastWayBillQtyTotal;              //卡货总票数
	/**volumeTotal*/
	private double volumeTotal;                      //总体积
	/**origOrgCode*/
	private String origOrgCode;						 //出发部门编码
	/**destOrgCode*/
	private String destOrgCode;                      //到达部门编码
	private String billType;                         //单据类型
	private String unloadBeginTime;
	private String unloadEndTime;
	private String beCanceled;
	/**新增快递票数字段*/
	private double expressWayBillQty;
	/***/
	//查询使用
	/**arriveVehicleId*/
	private String arriveVehicleId;                  //车辆到达id
	/**vehicleNo*/
	private String vehicleNo;                        //车牌号
	/**arriveTime*/
	private String arriveTime;                       //到达时间
	/**unloadType*/
	private String unloadType;						 //到达类型
	
	//月台号
	private String prePlatformNo;
	//272681 unloadState
	private String unloadState;                      //卸车状态
	private String flightNo;                        //航班号
	
	public String getUnloadBeginTime() {
		return unloadBeginTime;
	}

	public void setUnloadBeginTime(String unloadBeginTime) {
		this.unloadBeginTime = unloadBeginTime;
	}

	public String getUnloadEndTime() {
		return unloadEndTime;
	}

	public void setUnloadEndTime(String unloadEndTime) {
		this.unloadEndTime = unloadEndTime;
	}

	public String getBeCanceled() {
		return beCanceled;
	}

	public void setBeCanceled(String beCanceled) {
		this.beCanceled = beCanceled;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the billNo.
	 *
	 * @return the billNo
	 */
	public String getBillNo() {
		return billNo;
	}
	
	/**
	 * Sets the billNo.
	 *
	 * @param billNo the new billNo
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	
	
	public String getOrigOrgName() {
		return origOrgName;
	}

	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	/**
	 * Gets the weightTotal.
	 *
	 * @return the weightTotal
	 */
	public double getWeightTotal() {
		return weightTotal;
	}
	
	/**
	 * Sets the weightTotal.
	 *
	 * @param weightTotal the new weightTotal
	 */
	public void setWeightTotal(double weightTotal) {
		this.weightTotal = weightTotal;
	}
	
	/**
	 * Gets the goodsQtyTotal.
	 *
	 * @return the goodsQtyTotal
	 */
	public double getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	
	/**
	 * Sets the goodsQtyTotal.
	 *
	 * @param goodsQtyTotal the new goodsQtyTotal
	 */
	public void setGoodsQtyTotal(double goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	
	/**
	 * Gets the fastWayBillQtyTotal.
	 *
	 * @return the fastWayBillQtyTotal
	 */
	public double getFastWayBillQtyTotal() {
		return fastWayBillQtyTotal;
	}
	
	/**
	 * Sets the fastWayBillQtyTotal.
	 *
	 * @param fastWayBillQtyTotal the new fastWayBillQtyTotal
	 */
	public void setFastWayBillQtyTotal(double fastWayBillQtyTotal) {
		this.fastWayBillQtyTotal = fastWayBillQtyTotal;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public String getArriveVehicleId() {
		return arriveVehicleId;
	}
	
	/**
	 * 
	 *
	 * @param arriveVehicleId 
	 */
	public void setArriveVehicleId(String arriveVehicleId) {
		this.arriveVehicleId = arriveVehicleId;
	}
	
	public String getAssignState() {
		return assignState;
	}

	public void setAssignState(String assignState) {
		this.assignState = assignState;
	}

	/**
	 * Gets the volumeTotal.
	 *
	 * @return the volumeTotal
	 */
	public double getVolumeTotal() {
		return volumeTotal;
	}
	
	/**
	 * Sets the volumeTotal.
	 *
	 * @param volumeTotal the new volumeTotal
	 */
	public void setVolumeTotal(double volumeTotal) {
		this.volumeTotal = volumeTotal;
	}
	
	/**
	 * Gets the billLevel.
	 *
	 * @return the billLevel
	 */
	public String getBillLevel() {
		return billLevel;
	}
	
	/**
	 * Sets the billLevel.
	 *
	 * @param billLevel the new billLevel
	 */
	public void setBillLevel(String billLevel) {
		this.billLevel = billLevel;
	}
	
	/**
	 * Gets the vehicleNo.
	 *
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * Sets the vehicleNo.
	 *
	 * @param vehicleNo the new vehicleNo
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * Gets the arriveTime.
	 *
	 * @return the arriveTime
	 */
	public String getArriveTime() {
		return arriveTime;
	}
	
	/**
	 * Sets the arriveTime.
	 *
	 * @param arriveTime the new arriveTime
	 */
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	
	/**
	 * Gets the unloadType.
	 *
	 * @return the unloadType
	 */
	public String getUnloadType() {
		return unloadType;
	}
	
	/**
	 * Sets the unloadType.
	 *
	 * @param unloadType the new unloadType
	 */
	public void setUnloadType(String unloadType) {
		this.unloadType = unloadType;
	}
	
	/**
	 * Gets the origOrgCode.
	 *
	 * @return the origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	
	/**
	 * Sets the origOrgCode.
	 *
	 * @param origOrgCode the new origOrgCode
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	
	/**
	 * Gets the destOrgCode.
	 *
	 * @return the destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}
	
	/**
	 * Sets the destOrgCode.
	 *
	 * @param destOrgCode the new destOrgCode
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * @return the prePlatformNo
	 */
	public String getPrePlatformNo() {
		return prePlatformNo;
	}

	/**
	 * @param prePlatformNo the prePlatformNo to set
	 */
	public void setPrePlatformNo(String prePlatformNo) {
		this.prePlatformNo = prePlatformNo;
	}

	public String getUnloadState() {
		return unloadState;
	}

	public void setUnloadState(String unloadState) {
		this.unloadState = unloadState;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public double getExpressWayBillQty() {
		return expressWayBillQty;
	}

	public void setExpressWayBillQty(double expressWayBillQty) {
		this.expressWayBillQty = expressWayBillQty;
	}

}