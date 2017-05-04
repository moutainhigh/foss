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
 *  PROJECT NAME  : tfr-common-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/shared/dto/LMSVehicleTravelDataDto.java
 *  
 *  FILE NAME          :LMSVehicleTravelDataDto.java
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
 * PROJECT NAME: tfr-common-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.common.api.shared.dto
 * FILE    NAME: LMSVehicleTravelDataDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * FOSS提供给LMS的行驶数据
 * @author 046130-foss-xuduowei
 * @date 2012-12-5 上午10:13:41
 */
public class LMSVehicleTravelDataDto {
	/**
	 * 唯一标示号
	 */
	private String uniqueNo;
	/**
	 * 操作类型
	 */
	private int operatorState;
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 货柜号
	 */
	private String counterNo;
	/**
	 * 原公里数据
	 */
	private BigDecimal originalKilometer;
	/**
	 * 目的公里数
	 */
	private BigDecimal purposeKilometer;
	/**
	 * 车辆使用类型
	 */
	private String vehicleUsage;
	/**
	 * 时间
	 */
	private Date reachDate;
	
	/**
	 * 获取 唯一标示号.
	 *
	 * @return the 唯一标示号
	 */
	public String getUniqueNo() {
		return uniqueNo;
	}
	
	/**
	 * 设置 唯一标示号.
	 *
	 * @param uniqueNo the new 唯一标示号
	 */
	public void setUniqueNo(String uniqueNo) {
		this.uniqueNo = uniqueNo;
	}
	
	/**
	 * 获取 操作类型.
	 *
	 * @return the 操作类型
	 */
	public int getOperatorState() {
		return operatorState;
	}
	
	/**
	 * 设置 操作类型.
	 *
	 * @param operatorState the new 操作类型
	 */
	public void setOperatorState(int operatorState) {
		this.operatorState = operatorState;
	}
	
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
	public String getCounterNo() {
		return counterNo;
	}
	
	/**
	 * 设置 货柜号.
	 *
	 * @param counterNo the new 货柜号
	 */
	public void setCounterNo(String counterNo) {
		this.counterNo = counterNo;
	}
	
	/**
	 * 获取 原公里数据.
	 *
	 * @return the 原公里数据
	 */
	public BigDecimal getOriginalKilometer() {
		return originalKilometer;
	}
	
	/**
	 * 设置 原公里数据.
	 *
	 * @param originalKilometer the new 原公里数据
	 */
	public void setOriginalKilometer(BigDecimal originalKilometer) {
		this.originalKilometer = originalKilometer;
	}
	
	/**
	 * 获取 目的公里数.
	 *
	 * @return the 目的公里数
	 */
	public BigDecimal getPurposeKilometer() {
		return purposeKilometer;
	}
	
	/**
	 * 设置 目的公里数.
	 *
	 * @param purposeKilometer the new 目的公里数
	 */
	public void setPurposeKilometer(BigDecimal purposeKilometer) {
		this.purposeKilometer = purposeKilometer;
	}
	
	/**
	 * 获取 车辆使用类型.
	 *
	 * @return the 车辆使用类型
	 */
	public String getVehicleUsage() {
		return vehicleUsage;
	}
	
	/**
	 * 设置 车辆使用类型.
	 *
	 * @param vehicleUsage the new 车辆使用类型
	 */
	public void setVehicleUsage(String vehicleUsage) {
		this.vehicleUsage = vehicleUsage;
	}
	
	/**
	 * 获取 时间.
	 *
	 * @return the 时间
	 */
	public Date getReachDate() {
		return reachDate;
	}
	
	/**
	 * 设置 时间.
	 *
	 * @param reachDate the new 时间
	 */
	public void setReachDate(Date reachDate) {
		this.reachDate = reachDate;
	}
	
	
}