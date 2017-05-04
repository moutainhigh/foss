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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/PlanVehicleDto.java
 * 
 *  FILE NAME     :PlanVehicleDto.java
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
 * FILE    NAME: PlanVehicleDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 发车计划车辆信息
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-11-29 下午6:35:26
 */
public class PlanVehicleDto implements java.io.Serializable {

	private static final long serialVersionUID = 6095904215338217131L;

	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 车型
	 */
	private String truckModel;
	/**
	 * 车型值
	 */
	private String truckModelValue;
	/**
	 * 货柜号
	 */
	private String containerNo;
	/**
	 * 最大载重
	 */
	private BigDecimal maxLoadWeight;
	/**
	 * 实际最大载重
	 */
	private BigDecimal actualMaxLoadWeight;
	/**
	 * 车容积
	 */
	private BigDecimal truckVolume;
	/**
	 * 预计装载重量
	 */
	private BigDecimal planLoadWeight;
	/**
	 * 预计装载体积
	 */
	private BigDecimal planLoadVolume;
	/**
	 * 月台使用时间从
	 */
	private Date platformTimeStart;
	/**
	 * 月台使用时间到
	 */
	private Date platformTimeEnd;
	/**
	 * 车辆类型（挂车/厢式车/拖头）
	 */
	private String vehicleType;

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
	 * @param vehicleNo the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 货柜号.
	 *
	 * @return the 货柜号
	 */
	public String getContainerNo() {
		return containerNo;
	}

	/**
	 * 设置 货柜号.
	 *
	 * @param containerNo the new 货柜号
	 */
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
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
	 * @param maxLoadWeight the new 最大载重
	 */
	public void setMaxLoadWeight(BigDecimal maxLoadWeight) {
		this.maxLoadWeight = maxLoadWeight;
	}

	/**
	 * 获取 实际最大载重.
	 *
	 * @return the 实际最大载重
	 */
	public BigDecimal getActualMaxLoadWeight() {
		return actualMaxLoadWeight;
	}

	/**
	 * 设置 实际最大载重.
	 *
	 * @param actualMaxLoadWeight the new 实际最大载重
	 */
	public void setActualMaxLoadWeight(BigDecimal actualMaxLoadWeight) {
		this.actualMaxLoadWeight = actualMaxLoadWeight;
	}

	/**
	 * 获取 车容积.
	 *
	 * @return the 车容积
	 */
	public BigDecimal getTruckVolume() {
		return truckVolume;
	}

	/**
	 * 设置 车容积.
	 *
	 * @param truckVolume the new 车容积
	 */
	public void setTruckVolume(BigDecimal truckVolume) {
		this.truckVolume = truckVolume;
	}

	/**
	 * 获取 预计装载重量.
	 *
	 * @return the 预计装载重量
	 */
	public BigDecimal getPlanLoadWeight() {
		return planLoadWeight;
	}

	/**
	 * 设置 预计装载重量.
	 *
	 * @param planLoadWeight the new 预计装载重量
	 */
	public void setPlanLoadWeight(BigDecimal planLoadWeight) {
		this.planLoadWeight = planLoadWeight;
	}

	/**
	 * 获取 预计装载体积.
	 *
	 * @return the 预计装载体积
	 */
	public BigDecimal getPlanLoadVolume() {
		return planLoadVolume;
	}

	/**
	 * 设置 预计装载体积.
	 *
	 * @param planLoadVolume the new 预计装载体积
	 */
	public void setPlanLoadVolume(BigDecimal planLoadVolume) {
		this.planLoadVolume = planLoadVolume;
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
	 * @param truckModel the new 车型
	 */
	public void setTruckModel(String truckModel) {
		this.truckModel = truckModel;
	}

	/**
	 * 获取 车型值.
	 *
	 * @return the 车型值
	 */
	public String getTruckModelValue() {
		return truckModelValue;
	}

	/**
	 * 设置 车型值.
	 *
	 * @param truckModelValue the new 车型值
	 */
	public void setTruckModelValue(String truckModelValue) {
		this.truckModelValue = truckModelValue;
	}

	/**
	 * 获取 月台使用时间从.
	 *
	 * @return the 月台使用时间从
	 */
	public Date getPlatformTimeStart() {
		return platformTimeStart;
	}

	/**
	 * 设置 月台使用时间从.
	 *
	 * @param platformTimeStart the new 月台使用时间从
	 */
	public void setPlatformTimeStart(Date platformTimeStart) {
		this.platformTimeStart = platformTimeStart;
	}

	/**
	 * 获取 月台使用时间到.
	 *
	 * @return the 月台使用时间到
	 */
	public Date getPlatformTimeEnd() {
		return platformTimeEnd;
	}

	/**
	 * 设置 月台使用时间到.
	 *
	 * @param platformTimeEnd the new 月台使用时间到
	 */
	public void setPlatformTimeEnd(Date platformTimeEnd) {
		this.platformTimeEnd = platformTimeEnd;
	}

	/**
	 * 获取 车辆类型（挂车/厢式车/拖头）.
	 *
	 * @return the 车辆类型（挂车/厢式车/拖头）
	 */
	public String getVehicleType() {
		return vehicleType;
	}

	/**
	 * 设置 车辆类型（挂车/厢式车/拖头）.
	 *
	 * @param vehicleType the new 车辆类型（挂车/厢式车/拖头）
	 */
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

}