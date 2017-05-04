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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/CarInfoPageDto.java
 * 
 *  FILE NAME     :CarInfoPageDto.java
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
 * FILE    NAME: CarInfoPageDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.util.List;

/**
 * 车辆分页信息
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-12-20 下午2:20:58
 */
public class CarInfoPageDto implements java.io.Serializable {

	private static final long serialVersionUID = 2522477761079329540L;

	/**
	 * 总条数
	 */
	private Long totalCount = 0L;
	/**
	 * 车辆列表
	 */
	private List<CarInfoDto> vehicleList;

	/**
	 * 获取 总条数.
	 * 
	 * @return the 总条数
	 */
	public Long getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置 总条数.
	 * 
	 * @param totalCount
	 *            the new 总条数
	 */
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 获取 车辆列表.
	 * 
	 * @return the 车辆列表
	 */
	public List<CarInfoDto> getVehicleList() {
		return vehicleList;
	}

	/**
	 * 设置 车辆列表.
	 * 
	 * @param vehicleList
	 *            the new 车辆列表
	 */
	public void setVehicleList(List<CarInfoDto> vehicleList) {
		this.vehicleList = vehicleList;
	}

}