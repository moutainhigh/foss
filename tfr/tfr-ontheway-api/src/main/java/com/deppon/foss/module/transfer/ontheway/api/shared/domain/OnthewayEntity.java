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
 *  PROJECT NAME  : tfr-ontheway-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/ontheway/api/shared/domain/OnthewayEntity.java
 *  
 *  FILE NAME          :OnthewayEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.ontheway.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * 在途界面展示数据
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-25 上午9:13:16
 */
public class OnthewayEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;

	/**********车牌号************/
	private String vehicleNo;  		//车牌号
	
	/********** 当前状态 ************/
	private String currentStatus;	//当前状态
	
	/********** 线路************/
	private String lineName;		//线路
	
	/********** 出发部门 Code************/
	private String origOrgCode;

	/********** 出发部门 ************/
	private String origOrgName;		//出发部门
	
	/********** 到达部门 Code************/
	private String destOrgCode;
	
	/********** 到达部门 ************/
	private String destOrgName;		//到达部门
	
	/**********预计到达时间************/
	private Date planArriveTime;	//预计到达时间
	
	/********** 当前车辆所处路段 ************/
	private String currentPlace;	//当前车辆所处路段
	
	/********** 最后一次跟踪时间 ************/
	private Date trackingTime;	//最后一次跟踪时间
	
	/********** 车辆归属部门名称 ************/
	private String vehicleOrgName;	//车辆归属部门名称
	
	/********** 跟踪方式 ************/
	private String trackingType;	//跟踪方式
	
	/********** 跟踪人 ************/
	private String trackingUserName;//跟踪人
	
	/********** 备注***********/
	private String notes;			//备注
	
	/********** 备注***********/
	private String truckTaskDetailId;
	
	/********** 运行状态的名称***********/
	private String currentStatusName;//运行状态的名称
	
	/********** 运行状态的名称***********/
	private String currentSpeed;//运行状态的名称
	
	/********** 实际出发时间***********/
	private Date actualDepartTime;//实际出发时间

	/********** 状态***********/
	private String vehicleStatus;
	
	/**车辆业务类型(长短途)**/
	private String businessType;
	
	/**车辆归属类型**/
	private String vehicleOwnerType;
	
	private BigDecimal currentSppend; 
	
	/**预计到达状态**/
	private String planArrivalStatus;
	
	
	
	/**
	 * 获取 ********车牌号***********.
	 *
	 * @return the ********车牌号***********
	 */
	public String getVehicleNo()
	{
		return vehicleNo;
	}

	/**
	 * 设置 ********车牌号***********.
	 *
	 * @param vehicleNo the new ********车牌号***********
	 */
	public void setVehicleNo(String vehicleNo)
	{
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 ******** 当前状态 ***********.
	 *
	 * @return the ******** 当前状态 ***********
	 */
	public String getCurrentStatus()
	{
		return currentStatus;
	}

	/**
	 * 设置 ******** 当前状态 ***********.
	 *
	 * @param currentStatus the new ******** 当前状态 ***********
	 */
	public void setCurrentStatus(String currentStatus)
	{
		this.currentStatus = currentStatus;
	}

	/**
	 * 获取 ******** 线路***********.
	 *
	 * @return the ******** 线路***********
	 */
	public String getLineName()
	{
		return lineName;
	}

	/**
	 * 设置 ******** 线路***********.
	 *
	 * @param lineName the new ******** 线路***********
	 */
	public void setLineName(String lineName)
	{
		this.lineName = lineName;
	}

	/**
	 * 获取 ******** 出发部门 ***********.
	 *
	 * @return the ******** 出发部门 ***********
	 */
	public String getOrigOrgName()
	{
		return origOrgName;
	}

	/**
	 * 设置 ******** 出发部门 ***********.
	 *
	 * @param origOrgName the new ******** 出发部门 ***********
	 */
	public void setOrigOrgName(String origOrgName)
	{
		this.origOrgName = origOrgName;
	}

	/**
	 * 获取 ******** 到达部门 ***********.
	 *
	 * @return the ******** 到达部门 ***********
	 */
	public String getDestOrgName()
	{
		return destOrgName;
	}

	/**
	 * 设置 ******** 到达部门 ***********.
	 *
	 * @param destOrgName the new ******** 到达部门 ***********
	 */
	public void setDestOrgName(String destOrgName)
	{
		this.destOrgName = destOrgName;
	}

	/**
	 * 获取 ********预计到达时间***********.
	 *
	 * @return the ********预计到达时间***********
	 */
	public Date getPlanArriveTime()
	{
		return planArriveTime;
	}

	/**
	 * 设置 ********预计到达时间***********.
	 *
	 * @param planArriveTime the new ********预计到达时间***********
	 */
	public void setPlanArriveTime(Date planArriveTime)
	{
		this.planArriveTime = planArriveTime;
	}

	/**
	 * 获取 ******** 当前车辆所处路段 ***********.
	 *
	 * @return the ******** 当前车辆所处路段 ***********
	 */
	public String getCurrentPlace()
	{
		return currentPlace;
	}

	/**
	 * 设置 ******** 当前车辆所处路段 ***********.
	 *
	 * @param currentPlace the new ******** 当前车辆所处路段 ***********
	 */
	public void setCurrentPlace(String currentPlace)
	{
		this.currentPlace = currentPlace;
	}

	/**
	 * 获取 ******** 最后一次跟踪时间 ***********.
	 *
	 * @return the ******** 最后一次跟踪时间 ***********
	 */
	public Date getTrackingTime()
	{
		return trackingTime;
	}

	/**
	 * 设置 ******** 最后一次跟踪时间 ***********.
	 *
	 * @param trackingTime the new ******** 最后一次跟踪时间 ***********
	 */
	public void setTrackingTime(Date trackingTime)
	{
		this.trackingTime = trackingTime;
	}

	/**
	 * 获取 ******** 车辆归属部门名称 ***********.
	 *
	 * @return the ******** 车辆归属部门名称 ***********
	 */
	public String getVehicleOrgName()
	{
		return vehicleOrgName;
	}

	/**
	 * 设置 ******** 车辆归属部门名称 ***********.
	 *
	 * @param vehicleOrgName the new ******** 车辆归属部门名称 ***********
	 */
	public void setVehicleOrgName(String vehicleOrgName)
	{
		this.vehicleOrgName = vehicleOrgName;
	}

	/**
	 * 获取 ******** 跟踪方式 ***********.
	 *
	 * @return the ******** 跟踪方式 ***********
	 */
	public String getTrackingType()
	{
		return trackingType;
	}

	/**
	 * 设置 ******** 跟踪方式 ***********.
	 *
	 * @param trackingType the new ******** 跟踪方式 ***********
	 */
	public void setTrackingType(String trackingType)
	{
		this.trackingType = trackingType;
	}

	/**
	 * 获取 ******** 跟踪人 ***********.
	 *
	 * @return the ******** 跟踪人 ***********
	 */
	public String getTrackingUserName()
	{
		return trackingUserName;
	}

	/**
	 * 设置 ******** 跟踪人 ***********.
	 *
	 * @param trackingUserName the new ******** 跟踪人 ***********
	 */
	public void setTrackingUserName(String trackingUserName)
	{
		this.trackingUserName = trackingUserName;
	}

	/**
	 * 获取 ******** 备注**********.
	 *
	 * @return the ******** 备注**********
	 */
	public String getNotes()
	{
		return notes;
	}

	/**
	 * 设置 ******** 备注**********.
	 *
	 * @param notes the new ******** 备注**********
	 */
	public void setNotes(String notes)
	{
		this.notes = notes;
	}

	/**
	 * 获取 ******** 备注**********.
	 *
	 * @return the ******** 备注**********
	 */
	public String getTruckTaskDetailId()
	{
		return truckTaskDetailId;
	}

	/**
	 * 设置 ******** 备注**********.
	 *
	 * @param truckTaskDetailId the new ******** 备注**********
	 */
	public void setTruckTaskDetailId(String truckTaskDetailId)
	{
		this.truckTaskDetailId = truckTaskDetailId;
	}

	/**
	 * 获取 ******** 运行状态的名称**********.
	 *
	 * @return the ******** 运行状态的名称**********
	 */
	public String getCurrentStatusName()
	{
		return currentStatusName;
	}

	/**
	 * 设置 ******** 运行状态的名称**********.
	 *
	 * @param currentStatusName the new ******** 运行状态的名称**********
	 */
	public void setCurrentStatusName(String currentStatusName)
	{
		this.currentStatusName = currentStatusName;
	}

	/**
	 * 获取 ******** 运行状态的名称**********.
	 *
	 * @return the ******** 运行状态的名称**********
	 */
	public String getCurrentSpeed()
	{
		return currentSpeed;
	}

	/**
	 * 设置 ******** 运行状态的名称**********.
	 *
	 * @param currentSpeed the new ******** 运行状态的名称**********
	 */
	public void setCurrentSpeed(String currentSpeed)
	{
		this.currentSpeed = currentSpeed;
	}

	/**
	 * 获取 ******** 实际出发时间**********.
	 *
	 * @return the ******** 实际出发时间**********
	 */
	public Date getActualDepartTime()
	{
		return actualDepartTime;
	}

	/**
	 * 设置 ******** 实际出发时间**********.
	 *
	 * @param actualDepartTime the new ******** 实际出发时间**********
	 */
	public void setActualDepartTime(Date actualDepartTime)
	{
		this.actualDepartTime = actualDepartTime;
	}

	/**
	 * 获取 ******** 状态**********.
	 *
	 * @return the ******** 状态**********
	 */
	public String getVehicleStatus()
	{
		return vehicleStatus;
	}

	/**
	 * 设置 ******** 状态**********.
	 *
	 * @param vehicleStatus the new ******** 状态**********
	 */
	public void setVehicleStatus(String vehicleStatus)
	{
		this.vehicleStatus = vehicleStatus;
	}

	public BigDecimal getCurrentSppend() {
		return currentSppend;
	}

	public void setCurrentSppend(BigDecimal currentSppend) {
		this.currentSppend = currentSppend;
	}

	public String getPlanArrivalStatus() {
		return planArrivalStatus;
	}

	public void setPlanArrivalStatus(String planArrivalStatus) {
		this.planArrivalStatus = planArrivalStatus;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getVehicleOwnerType() {
		return vehicleOwnerType;
	}

	public void setVehicleOwnerType(String vehicleOwnerType) {
		this.vehicleOwnerType = vehicleOwnerType;
	}

	public String getOrigOrgCode() {
		return origOrgCode;
	}

	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	public String getDestOrgCode() {
		return destOrgCode;
	}

	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
}