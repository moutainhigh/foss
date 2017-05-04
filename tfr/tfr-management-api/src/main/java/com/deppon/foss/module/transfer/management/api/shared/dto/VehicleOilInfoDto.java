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
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/dto/VehicleOilInfoDto.java
 *  
 *  FILE NAME          :VehicleOilInfoDto.java
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
 * PROJECT NAME: tfr-management-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.management.api.shared.dto
 * FILE    NAME: VehicleOilInfoDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.management.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * LMS需要的车辆油耗信息
 * @author 046130-foss-xuduowei
 * @date 2012-12-27 下午2:22:23
 */
public class VehicleOilInfoDto {
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 加油升数
	 */
	private BigDecimal refuelAmount;
	/**
	 * 当前公里数
	 */
	private BigDecimal currentMile;
	/**
	 * 加油时间
	 */
	private Date refuelTime;
	
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
	 * 获取 加油升数.
	 *
	 * @return the 加油升数
	 */
	public BigDecimal getRefuelAmount() {
		return refuelAmount;
	}
	
	/**
	 * 设置 加油升数.
	 *
	 * @param refuelAmount the new 加油升数
	 */
	public void setRefuelAmount(BigDecimal refuelAmount) {
		this.refuelAmount = refuelAmount;
	}
	
	/**
	 * 获取 当前公里数.
	 *
	 * @return the 当前公里数
	 */
	public BigDecimal getCurrentMile() {
		return currentMile;
	}
	
	/**
	 * 设置 当前公里数.
	 *
	 * @param currentMile the new 当前公里数
	 */
	public void setCurrentMile(BigDecimal currentMile) {
		this.currentMile = currentMile;
	}
	
	/**
	 * 获取 加油时间.
	 *
	 * @return the 加油时间
	 */
	public Date getRefuelTime() {
		return refuelTime;
	}
	
	/**
	 * 设置 加油时间.
	 *
	 * @param refuelTime the new 加油时间
	 */
	public void setRefuelTime(Date refuelTime) {
		this.refuelTime = refuelTime;
	}
		
	
}