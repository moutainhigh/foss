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
 *  PROJECT NAME  : tfr-arrival-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/arrival/api/shared/dto/VehicleInfoDTO.java
 *  
 *  FILE NAME          :VehicleInfoDTO.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.arrival.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 分配月台中车辆的信息
 * 
 * @author IBM-liubinbin
 * @date 2012-11-5 下午4:11:08
 */
public class VehicleInfoDTO extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;

	public String getVirtualCode() {
		return virtualCode;
	}

	/************* 线路名称 ****************/
	private String lineName;

	/************* 载重体积 ****************/
	private String weight;

	/************* 车型 ****************/
	private BigDecimal vehicleLengthName;

	/************* 运输性质 ****************/
	private String productType;

	/************* 预计卸车时长（小时） ****************/
	private String unloadTime;

	/************* 预计到达时间 ****************/
	private Date planArrivalTime;

	/************* 月台号 ****************/
	private String platformNo;

	/************* 虚拟编码 ****************/
	private String virtualCode;

	/************* 预分配时间（开始） ****************/
	private Date platformUserStartTime;

	/************* 预分配时间（结束） ****************/
	private Date platformUserEndTime;

	/************* 到达ID ****************/
	private String truckArriveId;

	/************* 车牌号 ****************/
	private String vehicleNo;

	/************* 任务车辆明细ID ****************/
	private String truckTaskDetailId;

	/**
	 * 获取 *********** 线路名称***************.
	 * 
	 * @return the *********** 线路名称***************
	 */
	public String getLineName() {
		return lineName;
	}

	/**
	 * 设置 *********** 线路名称***************.
	 * 
	 * @param lineName
	 *            the new *********** 线路名称***************
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	/**
	 * 获取 *********** 载重体积 ***************.
	 * 
	 * @return the *********** 载重体积 ***************
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * 设置 *********** 载重体积 ***************.
	 * 
	 * @param weight
	 *            the new *********** 载重体积 ***************
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}

	/**
	 * 获取 *********** 车型 ***************.
	 * 
	 * @return the *********** 车型 ***************
	 */
	public BigDecimal getVehicleLengthName() {
		return vehicleLengthName;
	}

	/**
	 * 设置 *********** 车型 ***************.
	 * 
	 * @param vehicleLengthName
	 *            the new *********** 车型 ***************
	 */
	public void setVehicleLengthName(BigDecimal vehicleLengthName) {
		this.vehicleLengthName = vehicleLengthName;
	}

	/**
	 * 获取 *********** 运输性质 ***************.
	 * 
	 * @return the *********** 运输性质 ***************
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * 设置 *********** 运输性质 ***************.
	 * 
	 * @param productType
	 *            the new *********** 运输性质 ***************
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * 获取 *********** 预计卸车时长（小时） ***************.
	 * 
	 * @return the *********** 预计卸车时长（小时） ***************
	 */
	public String getUnloadTime() {
		return unloadTime;
	}

	/**
	 * 设置 *********** 预计卸车时长（小时） ***************.
	 * 
	 * @param unloadTime
	 *            the new *********** 预计卸车时长（小时） ***************
	 */
	public void setUnloadTime(String unloadTime) {
		this.unloadTime = unloadTime;
	}

	/**
	 * 获取 *********** 预计到达时间 ***************.
	 * 
	 * @return the *********** 预计到达时间 ***************
	 */
	public Date getPlanArrivalTime() {
		return planArrivalTime;
	}

	/**
	 * 设置 *********** 预计到达时间 ***************.
	 * 
	 * @param planArrivalTime
	 *            the new *********** 预计到达时间 ***************
	 */
	public void setPlanArrivalTime(Date planArrivalTime) {
		this.planArrivalTime = planArrivalTime;
	}

	/**
	 * 获取 *********** 预分配时间（开始） ***************.
	 * 
	 * @return the *********** 预分配时间（开始） ***************
	 */
	public Date getPlatformUserStartTime() {
		return platformUserStartTime;
	}

	/**
	 * 设置 *********** 预分配时间（开始） ***************.
	 * 
	 * @param platformUserStartTime
	 *            the new *********** 预分配时间（开始） ***************
	 */
	public void setPlatformUserStartTime(Date platformUserStartTime) {
		this.platformUserStartTime = platformUserStartTime;
	}

	/**
	 * 获取 *********** 预分配时间（结束） ***************.
	 * 
	 * @return the *********** 预分配时间（结束） ***************
	 */
	public Date getPlatformUserEndTime() {
		return platformUserEndTime;
	}

	/**
	 * 设置 *********** 预分配时间（结束） ***************.
	 * 
	 * @param platformUserEndTime
	 *            the new *********** 预分配时间（结束） ***************
	 */
	public void setPlatformUserEndTime(Date platformUserEndTime) {
		this.platformUserEndTime = platformUserEndTime;
	}

	/**
	 * 获取 *********** 月台号 ***************.
	 * 
	 * @return the *********** 月台号 ***************
	 */
	public String getPlatformNo() {
		return platformNo;
	}

	/**
	 * 设置 *********** 月台号 ***************.
	 * 
	 * @param platformNo
	 *            the new *********** 月台号 ***************
	 */
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}

	/**
	 * 获取 *********** 到达ID ***************.
	 * 
	 * @return the *********** 到达ID ***************
	 */
	public String getTruckArriveId() {
		return truckArriveId;
	}

	/**
	 * 设置 *********** 到达ID ***************.
	 * 
	 * @param truckArriveId
	 *            the new *********** 到达ID ***************
	 */
	public void setTruckArriveId(String truckArriveId) {
		this.truckArriveId = truckArriveId;
	}

	/**
	 * 获取 *********** 车牌号 ***************.
	 * 
	 * @return the *********** 车牌号 ***************
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * 设置 *********** 车牌号 ***************.
	 * 
	 * @param vehicleNo
	 *            the new *********** 车牌号 ***************
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 *********** 任务车辆明细ID ***************.
	 * 
	 * @return the *********** 任务车辆明细ID ***************
	 */
	public String getTruckTaskDetailId() {
		return truckTaskDetailId;
	}

	/**
	 * 设置 *********** 任务车辆明细ID ***************.
	 * 
	 * @param truckTaskDetailId
	 *            the new *********** 任务车辆明细ID ***************
	 */
	public void setTruckTaskDetailId(String truckTaskDetailId) {
		this.truckTaskDetailId = truckTaskDetailId;
	}

	public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
	}

}