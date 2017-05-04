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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/TruckSchedulingTaskDto.java
 * 
 *  FILE NAME     :TruckSchedulingTaskDto.java
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
 * FILE    NAME: TruckSchedulingTaskDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingTaskEntity;

/**
 * 排班任务列表
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-11-3 下午3:01:18
 */
public class TruckSchedulingTaskDto extends TruckSchedulingTaskEntity {

	private static final long serialVersionUID = -1399445408858128598L;

	/**
	 * 日期SCHEDULING_DATE
	 */
	private String schedulingDate;

	/**
	 * 司机代码DRIVER_CODE
	 */
	private String driverCode;

	/**
	 * 工作类别PLAN_TYPE
	 */
	private String planType;
	/**
	 * 排班类型
	 */
	private String schedulingType;

	/**
	 * 司机所属部门DRIVER_ORG_CODE
	 */
	private String driverOrgCode;
	/**
	 * 日期数字DATE_NUM
	 */
	private int dateNum;

	/**
	 * 年月YEAR_MONTH
	 */
	private String yearMonth;
	/**
	 * 计划状态
	 */
	private String schedulingStatus;
	/**
	 * 线路简码
	 */
	private String lineCode;
	/**
	 * 查询车辆状态Y的状态的
	 */
	private String active;
	/**
	 * 排班部门
	 */
	private String schedulingDepartCode;

	/**
	 * 获取 日期SCHEDULING_DATE.
	 * 
	 * @return the 日期SCHEDULING_DATE
	 */
	public String getSchedulingDate() {
		return schedulingDate;
	}

	/**
	 * 设置 日期SCHEDULING_DATE.
	 * 
	 * @param schedulingDate
	 *            the new 日期SCHEDULING_DATE
	 */
	public void setSchedulingDate(String schedulingDate) {
		this.schedulingDate = schedulingDate;
	}

	/**
	 * 获取 司机代码DRIVER_CODE.
	 * 
	 * @return the 司机代码DRIVER_CODE
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * 设置 司机代码DRIVER_CODE.
	 * 
	 * @param driverCode
	 *            the new 司机代码DRIVER_CODE
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * 获取 工作类别PLAN_TYPE.
	 * 
	 * @return the 工作类别PLAN_TYPE
	 */
	public String getPlanType() {
		return planType;
	}

	/**
	 * 设置 工作类别PLAN_TYPE.
	 * 
	 * @param planType
	 *            the new 工作类别PLAN_TYPE
	 */
	public void setPlanType(String planType) {
		this.planType = planType;
	}

	/**
	 * 获取 司机所属部门DRIVER_ORG_CODE.
	 * 
	 * @return the 司机所属部门DRIVER_ORG_CODE
	 */
	public String getDriverOrgCode() {
		return driverOrgCode;
	}

	/**
	 * 设置 司机所属部门DRIVER_ORG_CODE.
	 * 
	 * @param driverOrgCode
	 *            the new 司机所属部门DRIVER_ORG_CODE
	 */
	public void setDriverOrgCode(String driverOrgCode) {
		this.driverOrgCode = driverOrgCode;
	}

	/**
	 * 获取 日期数字DATE_NUM.
	 * 
	 * @return the 日期数字DATE_NUM
	 */
	public int getDateNum() {
		return dateNum;
	}

	/**
	 * 设置 日期数字DATE_NUM.
	 * 
	 * @param dateNum
	 *            the new 日期数字DATE_NUM
	 */
	public void setDateNum(int dateNum) {
		this.dateNum = dateNum;
	}

	/**
	 * 获取 年月YEAR_MONTH.
	 * 
	 * @return the 年月YEAR_MONTH
	 */
	public String getYearMonth() {
		return yearMonth;
	}

	/**
	 * 设置 年月YEAR_MONTH.
	 * 
	 * @param yearMonth
	 *            the new 年月YEAR_MONTH
	 */
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	/**
	 * 获取 计划状态.
	 * 
	 * @return the 计划状态
	 */
	public String getSchedulingStatus() {
		return schedulingStatus;
	}

	/**
	 * 设置 计划状态.
	 * 
	 * @param schedulingStatus
	 *            the new 计划状态
	 */
	public void setSchedulingStatus(String schedulingStatus) {
		this.schedulingStatus = schedulingStatus;
	}

	/**
	 * 获取 线路简码.
	 * 
	 * @return the 线路简码
	 */
	public String getLineCode() {
		return lineCode;
	}

	/**
	 * 设置 线路简码.
	 * 
	 * @param lineCode
	 *            the new 线路简码
	 */
	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}

	/**
	 * 获取 查询车辆状态Y的状态的.
	 * 
	 * @return the 查询车辆状态Y的状态的
	 */
	public String getActive() {
		return active;
	}

	/**
	 * 设置 查询车辆状态Y的状态的.
	 * 
	 * @param active
	 *            the new 查询车辆状态Y的状态的
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * 获取 排班类型.
	 * 
	 * @return the 排班类型
	 */
	public String getSchedulingType() {
		return schedulingType;
	}

	/**
	 * 设置 排班类型.
	 * 
	 * @param schedulingType
	 *            the new 排班类型
	 */
	public void setSchedulingType(String schedulingType) {
		this.schedulingType = schedulingType;
	}

	/**
	 * 获取 排班部门.
	 *
	 * @return the 排班部门
	 */
	public String getSchedulingDepartCode() {
		return schedulingDepartCode;
	}

	/**
	 * 设置 排班部门.
	 *
	 * @param schedulingDepartCode the new 排班部门
	 */
	public void setSchedulingDepartCode(String schedulingDepartCode) {
		this.schedulingDepartCode = schedulingDepartCode;
	}

}