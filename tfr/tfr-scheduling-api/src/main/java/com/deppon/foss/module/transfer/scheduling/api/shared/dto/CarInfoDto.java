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
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/CarInfoDto.java
 * 
 *  FILE NAME     :CarInfoDto.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.shared.dto
 * FILE    NAME: CarInfoDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.math.BigDecimal;

/**
 * 车辆信息
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-11-26 下午4:49:13
 */
public class CarInfoDto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 车辆所属车队
	 */
	private String carOwnerName;
	/**
	 * 车队小组
	 */
	private String carOwnerGroupName;
	/**
	 * 车辆所属车队code
	 */
	private String carOwnerCode;
	/**
	 * 车辆装调
	 */
	private String carStatus;
	/**
	 * 车辆状态描述
	 */
	private String carStatusDesc;
	/**
	 * 车型
	 */
	private String truckModel;
	/**
	 * 车型名
	 */
	private String truckModelValue;
	/**
	 * 最大载重
	 */
	private BigDecimal maxLoadWeight;
	/**
	 * 车辆净空
	 */
	private BigDecimal truckVolume;
	/**
	 * 司机所属车队小组
	 */
	private String driverOrgName;
	/**
	 * 司机所属车队小组code
	 */
	private String driverOrgCode;
	/**
	 * 司机名
	 */
	private String driverName;
	/**
	 * 司机code
	 */
	private String driverCode;
	/**
	 * 计划日期
	 */
	private String planDate;
	/**
	 * 状态(Y,N)
	 */
	private String status;

	/**
	 * 获取 车牌号.
	 * 
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * 设置 车牌号.
	 * 
	 * @param vehicleNo
	 *            the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 车辆所属车队.
	 * 
	 * @return the 车辆所属车队
	 */
	public String getCarOwnerName() {
		return carOwnerName;
	}

	/**
	 * 设置 车辆所属车队.
	 * 
	 * @param carOwnerName
	 *            the new 车辆所属车队
	 */
	public void setCarOwnerName(String carOwnerName) {
		this.carOwnerName = carOwnerName;
	}

	/**
	 * 获取 车辆所属车队code.
	 * 
	 * @return the 车辆所属车队code
	 */
	public String getCarOwnerCode() {
		return carOwnerCode;
	}

	/**
	 * 设置 车辆所属车队code.
	 * 
	 * @param carOwnerCode
	 *            the new 车辆所属车队code
	 */
	public void setCarOwnerCode(String carOwnerCode) {
		this.carOwnerCode = carOwnerCode;
	}

	/**
	 * 获取 车辆装调.
	 * 
	 * @return the 车辆装调
	 */
	public String getCarStatus() {
		return carStatus;
	}

	/**
	 * 设置 车辆装调.
	 * 
	 * @param carStatus
	 *            the new 车辆装调
	 */
	public void setCarStatus(String carStatus) {
		this.carStatus = carStatus;
	}

	/**
	 * 获取 车型.
	 * 
	 * @return the 车型
	 */
	public String getTruckModel() {
		return truckModel;
	}

	/**
	 * 设置 车型.
	 * 
	 * @param truckModel
	 *            the new 车型
	 */
	public void setTruckModel(String truckModel) {
		this.truckModel = truckModel;
	}

	/**
	 * 获取 最大载重.
	 * 
	 * @return the 最大载重
	 */
	public BigDecimal getMaxLoadWeight() {
		return maxLoadWeight;
	}

	/**
	 * 设置 最大载重.
	 * 
	 * @param maxLoadWeight
	 *            the new 最大载重
	 */
	public void setMaxLoadWeight(BigDecimal maxLoadWeight) {
		this.maxLoadWeight = maxLoadWeight;
	}

	/**
	 * 获取 车辆净空.
	 * 
	 * @return the 车辆净空
	 */
	public BigDecimal getTruckVolume() {
		return truckVolume;
	}

	/**
	 * 设置 车辆净空.
	 * 
	 * @param truckVolume
	 *            the new 车辆净空
	 */
	public void setTruckVolume(BigDecimal truckVolume) {
		this.truckVolume = truckVolume;
	}

	/**
	 * 获取 司机所属车队小组.
	 * 
	 * @return the 司机所属车队小组
	 */
	public String getDriverOrgName() {
		return driverOrgName;
	}

	/**
	 * 设置 司机所属车队小组.
	 * 
	 * @param driverOrgName
	 *            the new 司机所属车队小组
	 */
	public void setDriverOrgName(String driverOrgName) {
		this.driverOrgName = driverOrgName;
	}

	/**
	 * 获取 司机所属车队小组code.
	 * 
	 * @return the 司机所属车队小组code
	 */
	public String getDriverOrgCode() {
		return driverOrgCode;
	}

	/**
	 * 设置 司机所属车队小组code.
	 * 
	 * @param driverOrgCode
	 *            the new 司机所属车队小组code
	 */
	public void setDriverOrgCode(String driverOrgCode) {
		this.driverOrgCode = driverOrgCode;
	}

	/**
	 * 获取 司机名.
	 * 
	 * @return the 司机名
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * 设置 司机名.
	 * 
	 * @param driverName
	 *            the new 司机名
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * 获取 司机code.
	 * 
	 * @return the 司机code
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * 设置 司机code.
	 * 
	 * @param driverCode
	 *            the new 司机code
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * 获取 车型名.
	 * 
	 * @return the 车型名
	 */
	public String getTruckModelValue() {
		return truckModelValue;
	}

	/**
	 * 设置 车型名.
	 * 
	 * @param truckModelValue
	 *            the new 车型名
	 */
	public void setTruckModelValue(String truckModelValue) {
		this.truckModelValue = truckModelValue;
	}

	/**
	 * 获取 车辆状态描述.
	 * 
	 * @return the 车辆状态描述
	 */
	public String getCarStatusDesc() {
		return carStatusDesc;
	}

	/**
	 * 设置 车辆状态描述.
	 * 
	 * @param carStatusDesc
	 *            the new 车辆状态描述
	 */
	public void setCarStatusDesc(String carStatusDesc) {
		this.carStatusDesc = carStatusDesc;
	}

	/**
	 * 获取 计划日期.
	 * 
	 * @return the 计划日期
	 */
	public String getPlanDate() {
		return planDate;
	}

	/**
	 * 设置 计划日期.
	 * 
	 * @param planDate
	 *            the new 计划日期
	 */
	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}

	/**
	 * 获取 状态(Y,N).
	 * 
	 * @return the 状态(Y,N)
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置 状态(Y,N).
	 * 
	 * @param status
	 *            the new 状态(Y,N)
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取 车队小组.
	 * 
	 * @return the 车队小组
	 */
	public String getCarOwnerGroupName() {
		return carOwnerGroupName;
	}

	/**
	 * 设置 车队小组.
	 * 
	 * @param carOwnerGroupName
	 *            the new 车队小组
	 */
	public void setCarOwnerGroupName(String carOwnerGroupName) {
		this.carOwnerGroupName = carOwnerGroupName;
	}

}