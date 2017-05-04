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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/domain/AssignUnloadTaskEntity.java
 *  
 *  FILE NAME          :AssignUnloadTaskEntity.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.shared.domain
 * FILE    NAME: AssignUnloadTaskEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 分配卸车任务实体
 * @author dp-duyi
 * @date 2012-12-18 上午11:30:35
 */
public class AssignUnloadTaskEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5410587505267098968L;
	/**id*/
	private String id;
	/**vehicleNo*/
	private String vehicleNo;
	/**billNo*/
	private String billNo;
	/**vehicleType*/
	private String vehicleType;
	/**unloadType*/
	private String unloadType;
	/**arriveTime*/
	private Date arriveTime;
	/**weightTotal*/
	private double weightTotal;
	/**volumeTotal*/
	private double volumeTotal;
	/**goodsQtyTotal*/
	private int goodsQtyTotal;
	/**fastWayBillQtyTotal*/
	private int fastWayBillQtyTotal;
	/**platformId*/
	private String platformId;
	/**platformNo*/
	private String platformNo;
	/**line*/
	private String line;
	/**loaderName*/
	private String loaderName;
	/**loaderCode*/
	private String loaderCode;
	/**loadOrgCode*/
	private String loadOrgCode;
	/**loadOrgName*/
	private String loadOrgName;
	/**beCanceled*/
	private String  beCanceled;
	/**assignTime*/
	private Date assignTime;
	/**unloadStartTime*/
	private Date unloadStartTime;
	/**unloadEndTime*/
	private Date unloadEndTime;
	/**state*/
	private String state;
	/**assignUserName*/
	private String assignUserName;
	/**assignUserCode*/
	private String assignUserCode;
	/**modifyTime*/
	private Date modifyTime;
	/**modifyUserName*/
	private String modifyUserName;
	/**modifyUserCode*/
	private String modifyUserCode;
	/**assignOrgCode*/
	private String assignOrgCode;
	/**assignOrgName*/
	private String assignOrgName;
	
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
	
	/**
	 * Gets the vehicleType.
	 *
	 * @return the vehicleType
	 */
	public String getVehicleType() {
		return vehicleType;
	}
	
	/**
	 * Sets the vehicleType.
	 *
	 * @param vehicleType the new vehicleType
	 */
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
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
	 * Gets the arriveTime.
	 *
	 * @return the arriveTime
	 */
	public Date getArriveTime() {
		return arriveTime;
	}
	
	/**
	 * Sets the arriveTime.
	 *
	 * @param arriveTime the new arriveTime
	 */
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
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
	 * Gets the goodsQtyTotal.
	 *
	 * @return the goodsQtyTotal
	 */
	public int getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	
	/**
	 * Sets the goodsQtyTotal.
	 *
	 * @param goodsQtyTotal the new goodsQtyTotal
	 */
	public void setGoodsQtyTotal(int goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	
	/**
	 * Gets the fastWayBillQtyTotal.
	 *
	 * @return the fastWayBillQtyTotal
	 */
	public int getFastWayBillQtyTotal() {
		return fastWayBillQtyTotal;
	}
	
	/**
	 * Sets the fastWayBillQtyTotal.
	 *
	 * @param fastWayBillQtyTotal the new fastWayBillQtyTotal
	 */
	public void setFastWayBillQtyTotal(int fastWayBillQtyTotal) {
		this.fastWayBillQtyTotal = fastWayBillQtyTotal;
	}
	
	/**
	 * Gets the platformId.
	 *
	 * @return the platformId
	 */
	public String getPlatformId() {
		return platformId;
	}
	
	/**
	 * Sets the platformId.
	 *
	 * @param platformId the new platformId
	 */
	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}
	
	/**
	 * Gets the platformNo.
	 *
	 * @return the platformNo
	 */
	public String getPlatformNo() {
		return platformNo;
	}
	
	/**
	 * Sets the platformNo.
	 *
	 * @param platformNo the new platformNo
	 */
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	
	/**
	 * Gets the line.
	 *
	 * @return the line
	 */
	public String getLine() {
		return line;
	}
	
	/**
	 * Sets the line.
	 *
	 * @param line the new line
	 */
	public void setLine(String line) {
		this.line = line;
	}
	
	/**
	 * Gets the loaderName.
	 *
	 * @return the loaderName
	 */
	public String getLoaderName() {
		return loaderName;
	}
	
	/**
	 * Sets the loaderName.
	 *
	 * @param loaderName the new loaderName
	 */
	public void setLoaderName(String loaderName) {
		this.loaderName = loaderName;
	}
	
	/**
	 * Gets the loaderCode.
	 *
	 * @return the loaderCode
	 */
	public String getLoaderCode() {
		return loaderCode;
	}
	
	/**
	 * Sets the loaderCode.
	 *
	 * @param loaderCode the new loaderCode
	 */
	public void setLoaderCode(String loaderCode) {
		this.loaderCode = loaderCode;
	}
	
	/**
	 * Gets the loadOrgCode.
	 *
	 * @return the loadOrgCode
	 */
	public String getLoadOrgCode() {
		return loadOrgCode;
	}
	
	/**
	 * Sets the loadOrgCode.
	 *
	 * @param loadOrgCode the new loadOrgCode
	 */
	public void setLoadOrgCode(String loadOrgCode) {
		this.loadOrgCode = loadOrgCode;
	}
	
	/**
	 * Gets the loadOrgName.
	 *
	 * @return the loadOrgName
	 */
	public String getLoadOrgName() {
		return loadOrgName;
	}
	
	/**
	 * Sets the loadOrgName.
	 *
	 * @param loadOrgName the new loadOrgName
	 */
	public void setLoadOrgName(String loadOrgName) {
		this.loadOrgName = loadOrgName;
	}
	
	/**
	 * Gets the beCanceled.
	 *
	 * @return the beCanceled
	 */
	public String getBeCanceled() {
		return beCanceled;
	}
	
	/**
	 * Sets the beCanceled.
	 *
	 * @param beCanceled the new beCanceled
	 */
	public void setBeCanceled(String beCanceled) {
		this.beCanceled = beCanceled;
	}
	
	/**
	 * Gets the assignTime.
	 *
	 * @return the assignTime
	 */
	public Date getAssignTime() {
		return assignTime;
	}
	
	/**
	 * Sets the assignTime.
	 *
	 * @param assignTime the new assignTime
	 */
	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}
	
	/**
	 * Gets the unloadStartTime.
	 *
	 * @return the unloadStartTime
	 */
	public Date getUnloadStartTime() {
		return unloadStartTime;
	}
	
	/**
	 * Sets the unloadStartTime.
	 *
	 * @param unloadStartTime the new unloadStartTime
	 */
	public void setUnloadStartTime(Date unloadStartTime) {
		this.unloadStartTime = unloadStartTime;
	}
	
	/**
	 * Gets the unloadEndTime.
	 *
	 * @return the unloadEndTime
	 */
	public Date getUnloadEndTime() {
		return unloadEndTime;
	}
	
	/**
	 * Sets the unloadEndTime.
	 *
	 * @param unloadEndTime the new unloadEndTime
	 */
	public void setUnloadEndTime(Date unloadEndTime) {
		this.unloadEndTime = unloadEndTime;
	}
	
	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * Gets the assignUserName.
	 *
	 * @return the assignUserName
	 */
	public String getAssignUserName() {
		return assignUserName;
	}
	
	/**
	 * Sets the assignUserName.
	 *
	 * @param assignUserName the new assignUserName
	 */
	public void setAssignUserName(String assignUserName) {
		this.assignUserName = assignUserName;
	}
	
	/**
	 * Gets the assignUserCode.
	 *
	 * @return the assignUserCode
	 */
	public String getAssignUserCode() {
		return assignUserCode;
	}
	
	/**
	 * Sets the assignUserCode.
	 *
	 * @param assignUserCode the new assignUserCode
	 */
	public void setAssignUserCode(String assignUserCode) {
		this.assignUserCode = assignUserCode;
	}
	
	/**
	 * Gets the modifyTime.
	 *
	 * @return the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	
	/**
	 * Sets the modifyTime.
	 *
	 * @param modifyTime the new modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	/**
	 * Gets the modifyUserName.
	 *
	 * @return the modifyUserName
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}
	
	/**
	 * Sets the modifyUserName.
	 *
	 * @param modifyUserName the new modifyUserName
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	
	/**
	 * Gets the modifyUserCode.
	 *
	 * @return the modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	
	/**
	 * Sets the modifyUserCode.
	 *
	 * @param modifyUserCode the new modifyUserCode
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	
	/**
	 * Gets the assignOrgCode.
	 *
	 * @return the assignOrgCode
	 */
	public String getAssignOrgCode() {
		return assignOrgCode;
	}
	
	/**
	 * Sets the assignOrgCode.
	 *
	 * @param assignOrgCode the new assignOrgCode
	 */
	public void setAssignOrgCode(String assignOrgCode) {
		this.assignOrgCode = assignOrgCode;
	}
	
	/**
	 * Gets the assignOrgName.
	 *
	 * @return the assignOrgName
	 */
	public String getAssignOrgName() {
		return assignOrgName;
	}
	
	/**
	 * Sets the assignOrgName.
	 *
	 * @param assignOrgName the new assignOrgName
	 */
	public void setAssignOrgName(String assignOrgName) {
		this.assignOrgName = assignOrgName;
	}
}