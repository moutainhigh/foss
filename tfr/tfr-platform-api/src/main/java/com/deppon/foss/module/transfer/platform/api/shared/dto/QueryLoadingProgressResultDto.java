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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/QueryLoadingProgressResultDto.java
 *  
 *  FILE NAME          :QueryLoadingProgressResultDto.java
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
 * FILE    NAME: QueryLoadingProgressResultDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 查询装车进度返回结果
 * @author 046130-foss-xuduowei
 * @date 2012-11-19 下午4:38:22
 */
public class QueryLoadingProgressResultDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8056555588537036892L;
	
	/**
	 * 任务ID
	 */
	private String id;
	/**
	 * 任务号
	 */
	private String taskNumber;
	/**
	 * 任务建立时间
	 */
	private Date createTaskDate;
	/**
	 * 已装体积
	 */
	private BigDecimal loadedVolume;
	/**
	 * 已装重量
	 */
	private BigDecimal loadedWeight;
	/**
	 * 剩余体积
	 */
	private BigDecimal leftVolume;
	/**
	 * 剩余重量
	 */
	private BigDecimal leftWeight;
	/**
	 * 完成时间
	 */
	private Date completeDate;
	/**
	 * 完成时间
	 */
	private Date planDepartDate;
	/**
	 * 到达时间
	 */
	private Date arrivedDate;
	/**
	 * 线路名称
	 */
	private String lineInfo;
	/**
	 * 任务类型
	 */
	private String taskType;
	/**
	 * 车牌号
	 */
	private String vehicleNumber;
	/**
	 * 装车人数
	 */
	private int tallyMember;
	/**
	 * 额定体积
	 */
	private BigDecimal ratedVolume;
	/**
	 * 额定重量
	 */
	private BigDecimal ratedWeight;
	/**
	 * 到达部门
	 */
	private String arrivedDept;
	/**
	 * 月台号
	 */
	private String platform;
	/**
	 * 理货员
	 */
	private String loadMember;
	/**
	 * 任务状态
	 */
	private String taskState;
	/**
	 * 装车体积进度
	 */
	private String volumeProgress;
	/**
	 * 装车重量进度
	 */
	private String weightProgress;
	
	
	/**
	 * 获取 任务号.
	 *
	 * @return the 任务号
	 */
	public String getTaskNumber() {
		return taskNumber;
	}
	
	/**
	 * 设置 任务号.
	 *
	 * @param taskNumber the new 任务号
	 */
	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}
	
	/**
	 * 获取 任务建立时间.
	 *
	 * @return the 任务建立时间
	 */
	public Date getCreateTaskDate() {
		return createTaskDate;
	}
	
	/**
	 * 设置 任务建立时间.
	 *
	 * @param createTaskDate the new 任务建立时间
	 */
	public void setCreateTaskDate(Date createTaskDate) {
		this.createTaskDate = createTaskDate;
	}
	
	/**
	 * 获取 完成时间.
	 *
	 * @return the 完成时间
	 */
	public Date getCompleteDate() {
		return completeDate;
	}
	
	/**
	 * 设置 完成时间.
	 *
	 * @param completeDate the new 完成时间
	 */
	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}
	
	/**
	 * 获取 到达时间.
	 *
	 * @return the 到达时间
	 */
	public Date getArrivedDate() {
		return arrivedDate;
	}
	
	/**
	 * 设置 到达时间.
	 *
	 * @param arrivedDate the new 到达时间
	 */
	public void setArrivedDate(Date arrivedDate) {
		this.arrivedDate = arrivedDate;
	}
	
	
	/**
	 * 获取 线路名称.
	 *
	 * @return the 线路名称
	 */
	public String getLineInfo() {
		return lineInfo;
	}
	
	/**
	 * 设置 线路名称.
	 *
	 * @param lineInfo the new 线路名称
	 */
	public void setLineInfo(String lineInfo) {
		this.lineInfo = lineInfo;
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
	 * 获取 到达部门.
	 *
	 * @return the 到达部门
	 */
	public String getArrivedDept() {
		return arrivedDept;
	}
	
	/**
	 * 设置 到达部门.
	 *
	 * @param arrivedDept the new 到达部门
	 */
	public void setArrivedDept(String arrivedDept) {
		this.arrivedDept = arrivedDept;
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
	public String getLoadMember() {
		return loadMember;
	}
	
	/**
	 * 设置 理货员.
	 *
	 * @param loadMember the new 理货员
	 */
	public void setLoadMember(String loadMember) {
		this.loadMember = loadMember;
	}
	
	/**
	 * 获取 任务状态.
	 *
	 * @return the 任务状态
	 */
	public String getTaskState() {
		return taskState;
	}
	
	/**
	 * 设置 任务状态.
	 *
	 * @param taskState the new 任务状态
	 */
	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}
	
	/**
	 * 获取 装车体积进度.
	 *
	 * @return the 装车体积进度
	 */
	public String getVolumeProgress() {
		return volumeProgress;
	}
	
	/**
	 * 设置 装车体积进度.
	 *
	 * @param volumeProgress the new 装车体积进度
	 */
	public void setVolumeProgress(String volumeProgress) {
		this.volumeProgress = volumeProgress;
	}
	
	/**
	 * 获取 装车重量进度.
	 *
	 * @return the 装车重量进度
	 */
	public String getWeightProgress() {
		return weightProgress;
	}
	
	/**
	 * 设置 装车重量进度.
	 *
	 * @param weightProgress the new 装车重量进度
	 */
	public void setWeightProgress(String weightProgress) {
		this.weightProgress = weightProgress;
	}
	
	/**
	 * 获取 完成时间.
	 *
	 * @return the 完成时间
	 */
	public Date getPlanDepartDate() {
		return planDepartDate;
	}
	
	/**
	 * 设置 完成时间.
	 *
	 * @param planDepartDate the new 完成时间
	 */
	public void setPlanDepartDate(Date planDepartDate) {
		this.planDepartDate = planDepartDate;
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
	 * 获取 装车人数.
	 *
	 * @return the 装车人数
	 */
	public int getTallyMember() {
		return tallyMember;
	}
	
	/**
	 * 设置 装车人数.
	 *
	 * @param tallyMember the new 装车人数
	 */
	public void setTallyMember(int tallyMember) {
		this.tallyMember = tallyMember;
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
	 * 获取 任务ID.
	 *
	 * @return the 任务ID
	 */
	public String getId() {
		return id;
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
	
	
}