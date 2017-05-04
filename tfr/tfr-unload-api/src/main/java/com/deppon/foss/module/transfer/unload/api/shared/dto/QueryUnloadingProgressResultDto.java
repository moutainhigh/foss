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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/dto/QueryUnloadingProgressResultDto.java
 *  
 *  FILE NAME          :QueryUnloadingProgressResultDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-unload-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.shared.dto
 * FILE    NAME: QueryUnloadingProgressResultDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;

/**
 * 查询卸车进度返回结果
 * @author 046130-foss-xuduowei
 * @date 2012-12-12 上午10:47:07
 */
public class QueryUnloadingProgressResultDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1758664797981898571L;
	/**
	 * 任务ID
	 */
	private String id;
	/**
	 * 任务号
	 */
	private String taskNo;
	/**
	 * 单据编号
	 */
	private String billNumber;
	/**
	 * 车辆到达时间
	 */
	private Date arrivedTime;
	/**
	 * 分配时间
	 */
	private Date distributeTime;
	/**
	 * 任务建立时间
	 */
	private Date taskBeginTime;
	/**
	 * 任务完成时间
	 */
	private Date taskCompleteTime;
	/**
	 * 预计完成时间
	 */
	private Date predictCompleteTime;
	/**
	 * 预计完成分数
	 */
	private BigDecimal predictCompleteMinutes;
	/**
	 * 计划完成时间
	 */
	private Date taskPlanTime;
	/**
	 * 车牌号
	 */
	private String vehicleNumber;
	/**
	 * 是否超时
	 */
	private String isTimeout;
	/**
	 * 任务类型
	 */
	private String taskType;
	/**
	 * 任务状态
	 */
	private String taskState;
	/**
	 * 总体积
	 */
	private BigDecimal totalVolume;
	/**
	 * 总重量
	 */
	private BigDecimal totalWeight;
	/**
	 * 总件数
	 */
	private int totalPieces;
	/**
	 * 剩余体积
	 */
	private BigDecimal leftVolume;
	/**
	 * 剩余重量
	 */
	private BigDecimal leftWeight;
	/**
	 * 剩余件数
	 */
	private int leftPieces;
	/**
	 * 剩余体积
	 */
	private BigDecimal unloadedVolume;
	/**
	 * 剩余重量
	 */
	private BigDecimal unloadedWeight;
	/**
	 * 剩余件数
	 */
	private int unloadedPieces;
	/**
	 * 月台号
	 */
	private String platform;
	/**
	 * 理货员
	 */
	private String unLoadMember;
	/**
	 * 出发部门
	 */
	private String leaveDept;
	/**
	 * 卸车体积进度字符标示
	 */
	private String unLoadingVolumnProgress;
	/**
	 * 卸车重量进度字符标示
	 */
	private String unLoadingWeightProgress;
	/**
	 * 卸车体积进度百分比标示
	 */
	private String volumeProgress;
	/**
	 * 卸车重量进度百分比标示
	 */
	private String weightProgress;
	
	/**
	 * 获取 任务ID.
	 *
	 * @return the 任务ID
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Gets the 任务状态.
	 *
	 * @return the 任务状态
	 */
	public String getTaskState() {
		return taskState;
	}

	/**
	 * Sets the 任务状态.
	 *
	 * @param taskState the new 任务状态
	 */
	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

	/**
	 * 设置 任务ID.
	 *
	 * @param id the new 任务ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 获取 单据编号.
	 *
	 * @return the 单据编号
	 */
	public String getBillNumber() {
		return billNumber;
	}
	
	/**
	 * 设置 单据编号.
	 *
	 * @param billNumber the new 单据编号
	 */
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	
	/**
	 * 获取 车辆到达时间.
	 *
	 * @return the 车辆到达时间
	 */
	@DateFormat
	public Date getArrivedTime() {
		return arrivedTime;
	}
	
	/**
	 * 设置 车辆到达时间.
	 *
	 * @param arrivedTime the new 车辆到达时间
	 */
	public void setArrivedTime(Date arrivedTime) {
		this.arrivedTime = arrivedTime;
	}
	
	/**
	 * 获取 分配时间.
	 *
	 * @return the 分配时间
	 */
	public Date getDistributeTime() {
		return distributeTime;
	}
	
	/**
	 * 设置 分配时间.
	 *
	 * @param distributeTime the new 分配时间
	 */
	public void setDistributeTime(Date distributeTime) {
		this.distributeTime = distributeTime;
	}
	
	/**
	 * 获取 任务建立时间.
	 *
	 * @return the 任务建立时间
	 */
	public Date getTaskBeginTime() {
		return taskBeginTime;
	}
	
	/**
	 * 设置 任务建立时间.
	 *
	 * @param taskBeginTime the new 任务建立时间
	 */
	public void setTaskBeginTime(Date taskBeginTime) {
		this.taskBeginTime = taskBeginTime;
	}
	
	/**
	 * 获取 任务完成时间.
	 *
	 * @return the 任务完成时间
	 */
	public Date getTaskCompleteTime() {
		return taskCompleteTime;
	}
	
	/**
	 * 设置 任务完成时间.
	 *
	 * @param taskCompleteTime the new 任务完成时间
	 */
	public void setTaskCompleteTime(Date taskCompleteTime) {
		this.taskCompleteTime = taskCompleteTime;
	}
	
	/**
	 * 获取 计划完成时间.
	 *
	 * @return the 计划完成时间
	 */
	public Date getTaskPlanTime() {
		return taskPlanTime;
	}
	
	/**
	 * 设置 计划完成时间.
	 *
	 * @param taskPlanTime the new 计划完成时间
	 */
	public void setTaskPlanTime(Date taskPlanTime) {
		this.taskPlanTime = taskPlanTime;
	}
	
	/**
	 * 获取 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	
	/**
	 * 设置 车牌号.
	 *
	 * @param vehicleNumber the new 车牌号
	 */
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	
	/**
	 * 获取 是否超时.
	 *
	 * @return the 是否超时
	 */
	public String getIsTimeout() {
		return isTimeout;
	}
	
	/**
	 * 设置 是否超时.
	 *
	 * @param isTimeout the new 是否超时
	 */
	public void setIsTimeout(String isTimeout) {
		this.isTimeout = isTimeout;
	}
	
	/**
	 * 获取 任务类型.
	 *
	 * @return the 任务类型
	 */
	public String getTaskType() {
		return taskType;
	}
	
	/**
	 * 设置 任务类型.
	 *
	 * @param taskType the new 任务类型
	 */
	public void setTaskType(String taskType) {
		this.taskType = taskType;
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
		this.totalVolume = totalVolume.setScale(UnloadConstants.SONAR_NUMBER_3,BigDecimal.ROUND_HALF_UP);
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
		this.totalWeight = totalWeight.setScale(UnloadConstants.SONAR_NUMBER_3,BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 获取 总件数.
	 *
	 * @return the 总件数
	 */
	public int getTotalPieces() {
		return totalPieces;
	}
	
	/**
	 * 设置 总件数.
	 *
	 * @param totalPieces the new 总件数
	 */
	public void setTotalPieces(int totalPieces) {
		this.totalPieces = totalPieces;
	}
	
	/**
	 * 获取 剩余体积.
	 *
	 * @return the 剩余体积
	 */
	public BigDecimal getLeftVolume() {
		return leftVolume;
	}
	
	/**
	 * 设置 剩余体积.
	 *
	 * @param leftVolume the new 剩余体积
	 */
	public void setLeftVolume(BigDecimal leftVolume) {
		this.leftVolume = leftVolume;
	}
	
	/**
	 * 获取 剩余重量.
	 *
	 * @return the 剩余重量
	 */
	public BigDecimal getLeftWeight() {
		return leftWeight;
	}
	
	/**
	 * 设置 剩余重量.
	 *
	 * @param leftWeight the new 剩余重量
	 */
	public void setLeftWeight(BigDecimal leftWeight) {
		this.leftWeight = leftWeight;
	}
	
	/**
	 * 获取 剩余件数.
	 *
	 * @return the 剩余件数
	 */
	public int getLeftPieces() {
		return leftPieces;
	}
	
	/**
	 * 设置 剩余件数.
	 *
	 * @param leftPieces the new 剩余件数
	 */
	public void setLeftPieces(int leftPieces) {
		this.leftPieces = leftPieces;
	}
	
	/**
	 * 获取 月台号.
	 *
	 * @return the 月台号
	 */
	public String getPlatform() {
		return platform;
	}
	
	/**
	 * 设置 月台号.
	 *
	 * @param platform the new 月台号
	 */
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	/**
	 * 获取 理货员.
	 *
	 * @return the 理货员
	 */
	public String getUnLoadMember() {
		return unLoadMember;
	}
	
	/**
	 * 设置 理货员.
	 *
	 * @param unLoadMember the new 理货员
	 */
	public void setUnLoadMember(String unLoadMember) {
		this.unLoadMember = unLoadMember;
	}
	
	/**
	 * 获取 出发部门.
	 *
	 * @return the 出发部门
	 */
	public String getLeaveDept() {
		return leaveDept;
	}
	
	/**
	 * 设置 出发部门.
	 *
	 * @param leaveDept the new 出发部门
	 */
	public void setLeaveDept(String leaveDept) {
		this.leaveDept = leaveDept;
	}
	
	/**
	 * 获取 卸车体积进度字符标示.
	 *
	 * @return the 卸车体积进度字符标示
	 */
	public String getUnLoadingVolumnProgress() {
		return unLoadingVolumnProgress;
	}
	
	/**
	 * 设置 卸车体积进度字符标示.
	 *
	 * @param unLoadingVolumnProgress the new 卸车体积进度字符标示
	 */
	public void setUnLoadingVolumnProgress(String unLoadingVolumnProgress) {
		this.unLoadingVolumnProgress = unLoadingVolumnProgress;
	}
	
	/**
	 * 获取 卸车重量进度字符标示.
	 *
	 * @return the 卸车重量进度字符标示
	 */
	public String getUnLoadingWeightProgress() {
		return unLoadingWeightProgress;
	}
	
	/**
	 * 设置 卸车重量进度字符标示.
	 *
	 * @param unLoadingWeightProgress the new 卸车重量进度字符标示
	 */
	public void setUnLoadingWeightProgress(String unLoadingWeightProgress) {
		this.unLoadingWeightProgress = unLoadingWeightProgress;
	}
	
	/**
	 * 获取 剩余体积.
	 *
	 * @return the 剩余体积
	 */
	public BigDecimal getUnloadedVolume() {
		return unloadedVolume;
	}
	
	/**
	 * 设置 剩余体积.
	 *
	 * @param unloadedVolume the new 剩余体积
	 */
	public void setUnloadedVolume(BigDecimal unloadedVolume) {
		this.unloadedVolume = unloadedVolume.setScale(UnloadConstants.SONAR_NUMBER_3,BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 获取 剩余重量.
	 *
	 * @return the 剩余重量
	 */
	public BigDecimal getUnloadedWeight() {
		return unloadedWeight;
	}
	
	/**
	 * 设置 剩余重量.
	 *
	 * @param unloadedWeight the new 剩余重量
	 */
	public void setUnloadedWeight(BigDecimal unloadedWeight) {
		this.unloadedWeight = unloadedWeight.setScale(UnloadConstants.SONAR_NUMBER_3,BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 获取 剩余件数.
	 *
	 * @return the 剩余件数
	 */
	public int getUnloadedPieces() {
		return unloadedPieces;
	}
	
	/**
	 * 设置 剩余件数.
	 *
	 * @param unloadedPieces the new 剩余件数
	 */
	public void setUnloadedPieces(int unloadedPieces) {
		this.unloadedPieces = unloadedPieces;
	}
	
	/**
	 * 获取 卸车体积进度百分比标示.
	 *
	 * @return the 卸车体积进度百分比标示
	 */
	public String getVolumeProgress() {
		return volumeProgress;
	}
	
	/**
	 * 设置 卸车体积进度百分比标示.
	 *
	 * @param volumeProgress the new 卸车体积进度百分比标示
	 */
	public void setVolumeProgress(String volumeProgress) {
		this.volumeProgress = volumeProgress;
	}
	
	/**
	 * 获取 卸车重量进度百分比标示.
	 *
	 * @return the 卸车重量进度百分比标示
	 */
	public String getWeightProgress() {
		return weightProgress;
	}
	
	/**
	 * 设置 卸车重量进度百分比标示.
	 *
	 * @param weightProgress the new 卸车重量进度百分比标示
	 */
	public void setWeightProgress(String weightProgress) {
		this.weightProgress = weightProgress;
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
	 * 获取 预计完成时间.
	 *
	 * @return the 预计完成时间
	 */
	public Date getPredictCompleteTime() {
		return predictCompleteTime;
	}
	
	/**
	 * 设置 预计完成时间.
	 *
	 * @param predictCompleteTime the new 预计完成时间
	 */
	public void setPredictCompleteTime(Date predictCompleteTime) {
		this.predictCompleteTime = predictCompleteTime;
	}
	
	/**
	 * 获取 预计完成分数.
	 *
	 * @return the 预计完成分数
	 */
	public BigDecimal getPredictCompleteMinutes() {
		return predictCompleteMinutes;
	}
	
	/**
	 * 设置 预计完成分数.
	 *
	 * @param predictCompleteMinutes the new 预计完成分数
	 */
	public void setPredictCompleteMinutes(BigDecimal predictCompleteMinutes) {
		this.predictCompleteMinutes = predictCompleteMinutes.setScale(UnloadConstants.SONAR_NUMBER_5,BigDecimal.ROUND_HALF_UP);
	}
		
		
		
}