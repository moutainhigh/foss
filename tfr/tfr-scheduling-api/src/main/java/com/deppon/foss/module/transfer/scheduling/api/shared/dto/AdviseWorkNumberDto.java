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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/AdviseWorkNumberDto.java
 * 
 *  FILE NAME     :AdviseWorkNumberDto.java
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
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *  计算上班人数DTO
 * @author huyue
 * @date 2012-10-15 下午12:50:34
 */
public class AdviseWorkNumberDto implements Serializable {
	
	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 2503911558468096776L;
	/**
	 * 预测时间
	 */
	private Date statisticsTime;
	/**
	 * 起始时间
	 */
	private Date forecastStartTime;
	/**
	 * 截止时间
	 */
	private Date forecastEndTime;
	/**
	 * 总重量
	 */
	private BigDecimal totalWeight;
	/**
	 * 总体积
	 */
	private BigDecimal totalVolume;
	
	/**
	 * 统计车辆类型和数量DTO List
	 */
	private List<CountVehicleDto> countVehicleList;

	/**
	 * 获取 预测时间.
	 *
	 * @return the 预测时间
	 */
	public Date getStatisticsTime() {
		return statisticsTime;
	}

	/**
	 * 设置 预测时间.
	 *
	 * @param statisticsTime the new 预测时间
	 */
	public void setStatisticsTime(Date statisticsTime) {
		this.statisticsTime = statisticsTime;
	}

	/**
	 * 获取 起始时间.
	 *
	 * @return the 起始时间
	 */
	public Date getForecastStartTime() {
		return forecastStartTime;
	}

	/**
	 * 设置 起始时间.
	 *
	 * @param forecastStartTime the new 起始时间
	 */
	public void setForecastStartTime(Date forecastStartTime) {
		this.forecastStartTime = forecastStartTime;
	}

	/**
	 * 获取 截止时间.
	 *
	 * @return the 截止时间
	 */
	public Date getForecastEndTime() {
		return forecastEndTime;
	}

	/**
	 * 设置 截止时间.
	 *
	 * @param forecastEndTime the new 截止时间
	 */
	public void setForecastEndTime(Date forecastEndTime) {
		this.forecastEndTime = forecastEndTime;
	}

	/**
	 * 获取 总重量.
	 *
	 * @return the 总重量
	 */
	public BigDecimal getTotalWeight() {
		return totalWeight;
	}

	/**
	 * 设置 总重量.
	 *
	 * @param totalWeight the new 总重量
	 */
	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}

	/**
	 * 获取 总体积.
	 *
	 * @return the 总体积
	 */
	public BigDecimal getTotalVolume() {
		return totalVolume;
	}

	/**
	 * 设置 总体积.
	 *
	 * @param totalVolume the new 总体积
	 */
	public void setTotalVolume(BigDecimal totalVolume) {
		this.totalVolume = totalVolume;
	}

	/**
	 * 获取 统计车辆类型和数量DTO List.
	 *
	 * @return the 统计车辆类型和数量DTO List
	 */
	public List<CountVehicleDto> getCountVehicleList() {
		return countVehicleList;
	}

	/**
	 * 设置 统计车辆类型和数量DTO List.
	 *
	 * @param countVehicleList the new 统计车辆类型和数量DTO List
	 */
	public void setCountVehicleList(List<CountVehicleDto> countVehicleList) {
		this.countVehicleList = countVehicleList;
	}
	
}