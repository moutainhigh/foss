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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/TruckSchedulingDto.java
 * 
 *  FILE NAME     :TruckSchedulingDto.java
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
 * FILE    NAME: TruckSchedulingDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity;

/**
 * 排班计划Dto
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-11-3 下午4:36:31
 */
public class TruckSchedulingDto extends TruckSchedulingEntity {

	private static final long serialVersionUID = 3154487848257756169L;
	/**
	 * 排班日期yyyy-mm-dd
	 */
	private String ymd;
	/**
	 * 工作类别列表
	 */
	private List<String> list;

	/**
	 * 计划对应的任务
	 */
	private List<TruckSchedulingTaskDto> taskDtos;

	/**
	 * 获取 排班日期yyyy-mm-dd.
	 * 
	 * @return the 排班日期yyyy-mm-dd
	 */
	public String getYmd() {
		return ymd;
	}

	/**
	 * 设置 排班日期yyyy-mm-dd.
	 * 
	 * @param ymd
	 *            the new 排班日期yyyy-mm-dd
	 */
	public void setYmd(String ymd) {
		this.ymd = ymd;
	}

	/**
	 * 获取 计划对应的任务.
	 * 
	 * @return the 计划对应的任务
	 */
	public List<TruckSchedulingTaskDto> getTaskDtos() {
		return taskDtos;
	}

	/**
	 * 设置 计划对应的任务.
	 * 
	 * @param taskDtos
	 *            the new 计划对应的任务
	 */
	public void setTaskDtos(List<TruckSchedulingTaskDto> taskDtos) {
		this.taskDtos = taskDtos;
	}

	/**
	 * 获取 工作类别列表.
	 * 
	 * @return the 工作类别列表
	 */
	public List<String> getList() {
		return list;
	}

	/**
	 * 设置 工作类别列表.
	 * 
	 * @param list
	 *            the new 工作类别列表
	 */
	public void setList(List<String> list) {
		this.list = list;
	}

}