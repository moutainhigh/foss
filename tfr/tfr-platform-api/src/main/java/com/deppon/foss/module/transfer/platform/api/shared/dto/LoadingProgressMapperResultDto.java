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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/QueryProgressMapperResultDto.java
 *  
 *  FILE NAME          :QueryProgressMapperResultDto.java
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
 * FILE    NAME: QueryProgressMapperResultDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 月台功能查询结果映射结果
 * @author 046130-foss-xuduowei
 * @date 2012-11-26 上午9:35:30
 */
public class LoadingProgressMapperResultDto {
	/**
	 * 任务号
	 */
	private String taskNo;
	/**
	 * 任务类别
	 */
	private String taskType;
	/**
	 * 完成时间
	 */
	private Date taskEndTime;
	/**
	 * 额定体积
	 */
	private BigDecimal ratedVolume;
	/**
	 * 额定重量
	 */
	private BigDecimal ratedWeight;
	/**
	 * 已装体积
	 */
	private BigDecimal loadedVolume;
	/**
	 * 已装重量
	 */
	private BigDecimal loadedWeight;
	
	
	
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
	 * 获取 额定体积.
	 *
	 * @return the 额定体积
	 */
	public BigDecimal getRatedVolume() {
		return ratedVolume;
	}
	
	/**
	 * 设置 额定体积.
	 *
	 * @param ratedVolume the new 额定体积
	 */
	public void setRatedVolume(BigDecimal ratedVolume) {
		this.ratedVolume = ratedVolume.setScale(2,BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 获取 额定重量.
	 *
	 * @return the 额定重量
	 */
	public BigDecimal getRatedWeight() {
		return ratedWeight;
	}
	
	/**
	 * 设置 额定重量.
	 *
	 * @param ratedWeight the new 额定重量
	 */
	public void setRatedWeight(BigDecimal ratedWeight) {
		this.ratedWeight = ratedWeight.setScale(2,BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 获取 已装体积.
	 *
	 * @return the 已装体积
	 */
	public BigDecimal getLoadedVolume() {
		return loadedVolume;
	}
	
	/**
	 * 设置 已装体积.
	 *
	 * @param loadedVolume the new 已装体积
	 */
	public void setLoadedVolume(BigDecimal loadedVolume) {
		this.loadedVolume = loadedVolume.setScale(2,BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 获取 已装重量.
	 *
	 * @return the 已装重量
	 */
	public BigDecimal getLoadedWeight() {
		return loadedWeight;
	}
	
	/**
	 * 设置 已装重量.
	 *
	 * @param loadedWeight the new 已装重量
	 */
	public void setLoadedWeight(BigDecimal loadedWeight) {
		this.loadedWeight = loadedWeight.setScale(2,BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 获取 任务号.
	 *
	 * @return the 任务号
	 */
	public String getTaskNo() {
		return taskNo;
	}
	
	/**
	 * 设置 任务号.
	 *
	 * @param taskNo the new 任务号
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	
	/**
	 * 获取 完成时间.
	 *
	 * @return the 完成时间
	 */
	public Date getTaskEndTime() {
		return taskEndTime;
	}
	
	/**
	 * 设置 完成时间.
	 *
	 * @param taskEndTime the new 完成时间
	 */
	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
	}
	
	
	
}