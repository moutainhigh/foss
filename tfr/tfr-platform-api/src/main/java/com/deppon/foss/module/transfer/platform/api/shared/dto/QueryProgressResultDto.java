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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/QueryProgressResultDto.java
 *  
 *  FILE NAME          :QueryProgressResultDto.java
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
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.shared.dto
 * FILE    NAME: QueryProgressResultDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.util.Date;

/**
 * 月台功能调用的结果
 * @author 046130-foss-xuduowei
 * @date 2012-11-26 上午8:57:06
 */
public class QueryProgressResultDto {
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 任务类别
	 */
	private String taskType;
	/**
	 * 任务开始时间
	 */
	private Date taskBeginTime;
	/**
	 * 任务结束时间
	 */
	private Date taskEndTime;
	/**
	 * 重量进度
	 */
	private String weightProgress;
	/**
	 * 体积进度
	 */
	private String volumeProgress;

	/**
	 * 获取 任务类别.
	 *
	 * @return the 任务类别
	 */
	public String getTaskType() {
		return taskType;
	}

	/**
	 * 设置 任务类别.
	 *
	 * @param taskType the new 任务类别
	 */
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	/**
	 * 获取 体积进度.
	 *
	 * @return the 体积进度
	 */
	public String getVolumeProgress() {
		return volumeProgress;
	}

	/**
	 * 设置 体积进度.
	 *
	 * @param volumeProgress the new 体积进度
	 */
	public void setVolumeProgress(String volumeProgress) {
		this.volumeProgress = volumeProgress;
	}

	/**
	 * 获取 重量进度.
	 *
	 * @return the 重量进度
	 */
	public String getWeightProgress() {
		return weightProgress;
	}

	/**
	 * 设置 重量进度.
	 *
	 * @param weightProgress the new 重量进度
	 */
	public void setWeightProgress(String weightProgress) {
		this.weightProgress = weightProgress;
	}

	/**
	 * 获取 任务结束时间.
	 *
	 * @return the 任务结束时间
	 */
	public Date getTaskEndTime() {
		return taskEndTime;
	}

	/**
	 * 设置 任务结束时间.
	 *
	 * @param taskEndTime the new 任务结束时间
	 */
	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public Date getTaskBeginTime() {
		return taskBeginTime;
	}

	public void setTaskBeginTime(Date taskBeginTime) {
		this.taskBeginTime = taskBeginTime;
	}
	
	
}