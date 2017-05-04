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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/QueryLoadingProgressConditionDto.java
 *  
 *  FILE NAME          :QueryLoadingProgressConditionDto.java
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
 * FILE    NAME: QueryLoadingProgressConditionDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询装车进度条件
 * @author 046130-foss-xuduowei
 * @date 2012-11-19 上午8:19:34
 */
public class QueryLoadingProgressConditionDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4950930321988735982L;
	/**
	 * 部门code
	 */
	private String deptCode;
	/**
	 * 装车任务编号
	 */
	private String taskNumber;
	/**
	 * 到达部门
	 */
	private String arrivedDept;
	/**
	 * 车牌号
	 */
	private String vehicleNumber;
	/**
	 * 任务状态
	 */
	private String taskState;
	/**
	 * 任务类型
	 */
	private String taskType;
	/**
	 * 是否超时
	 */
	private String timeOver;
	/**
	 * 任务建立开始时间
	 */
	private Date createTaskDateFrom;
	/**
	 * 任务建立结束时间
	 */
	private Date createTaskDateTo;
	/**
	 * 排序方向
	 */
	private String sequence;
	/**
	 * 排序字段
	 */
	private String sequenceType;
	/**
	 * 排序规则
	 */
	private String sortRule;
	/**
	 * 当前时间
	 */
	private Date currentDate;
	
	/**
	 * 获取 装车任务编号.
	 *
	 * @return the 装车任务编号
	 */
	public String getTaskNumber() {
		return taskNumber;
	}

	/**
	 * 设置 装车任务编号.
	 *
	 * @param taskNumber the new 装车任务编号
	 */
	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
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
	 * 获取 是否超时.
	 *
	 * @return the 是否超时
	 */
	public String getTimeOver() {
		return timeOver;
	}

	/**
	 * 设置 是否超时.
	 *
	 * @param timeOver the new 是否超时
	 */
	public void setTimeOver(String timeOver) {
		this.timeOver = timeOver;
	}

	/**
	 * 获取 任务建立开始时间.
	 *
	 * @return the 任务建立开始时间
	 */
	public Date getCreateTaskDateFrom() {
		return createTaskDateFrom;
	}

	/**
	 * 设置 任务建立开始时间.
	 *
	 * @param createTaskDateFrom the new 任务建立开始时间
	 */
	public void setCreateTaskDateFrom(Date createTaskDateFrom) {
		this.createTaskDateFrom = createTaskDateFrom;
	}

	/**
	 * 获取 任务建立结束时间.
	 *
	 * @return the 任务建立结束时间
	 */
	public Date getCreateTaskDateTo() {
		return createTaskDateTo;
	}

	/**
	 * 设置 任务建立结束时间.
	 *
	 * @param createTaskDateTo the new 任务建立结束时间
	 */
	public void setCreateTaskDateTo(Date createTaskDateTo) {
		this.createTaskDateTo = createTaskDateTo;
	}

	/**
	 * 获取 排序方向.
	 *
	 * @return the 排序方向
	 */
	public String getSequence() {
		return sequence;
	}

	/**
	 * 设置 排序方向.
	 *
	 * @param sequence the new 排序方向
	 */
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	/**
	 * 获取 排序字段.
	 *
	 * @return the 排序字段
	 */
	public String getSequenceType() {
		return sequenceType;
	}

	/**
	 * 设置 排序字段.
	 *
	 * @param sequenceType the new 排序字段
	 */
	public void setSequenceType(String sequenceType) {
		this.sequenceType = sequenceType;
	}

	/**
	 * 获取 排序规则.
	 *
	 * @return the 排序规则
	 */
	public String getSortRule() {
		return sortRule;
	}

	/**
	 * 设置 排序规则.
	 *
	 * @param sortRule the new 排序规则
	 */
	public void setSortRule(String sortRule) {
		this.sortRule = sortRule;
	}

	/**
	 * 获取 部门code.
	 *
	 * @return the 部门code
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * 设置 部门code.
	 *
	 * @param deptCode the new 部门code
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * 获取 当前时间.
	 *
	 * @return the 当前时间
	 */
	public Date getCurrentDate() {
		return currentDate;
	}

	/**
	 * 设置 当前时间.
	 *
	 * @param currentDate the new 当前时间
	 */
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	
	
	
	
}