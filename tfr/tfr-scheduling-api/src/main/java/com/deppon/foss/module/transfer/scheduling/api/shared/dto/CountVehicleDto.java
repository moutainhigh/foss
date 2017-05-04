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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/CountVehicleDto.java
 * 
 *  FILE NAME     :CountVehicleDto.java
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

/**
 *  统计车辆类型和数量DTO
 * @author huyue
 * @date 2012-10-15 下午12:50:34
 */
public class CountVehicleDto implements Serializable {
	
	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 2503911558468096776L;
	/**
	 * 车辆型号
	 */
	private String vehicleType;
	/**
	 * 车辆台数
	 */
	private int count;

	/**
	 * 获取 车辆型号.
	 *
	 * @return the 车辆型号
	 */
	public String getVehicleType() {
		return vehicleType;
	}

	/**
	 * 设置 车辆型号.
	 *
	 * @param vehicleType the new 车辆型号
	 */
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	/**
	 * 获取 车辆台数.
	 *
	 * @return the 车辆台数
	 */
	public int getCount() {
		return count;
	}

	/**
	 * 设置 车辆台数.
	 *
	 * @param count the new 车辆台数
	 */
	public void setCount(int count) {
		this.count = count;
	}
	
}