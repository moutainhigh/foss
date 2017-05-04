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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/ScheduleExcelDto.java
 * 
 *  FILE NAME     :ScheduleExcelDto.java
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
 * FILE    NAME: ExcelDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.util.Date;

/**
 * 排班导入DTO
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-11-2 下午5:35:56
 */
public class ScheduleExcelDto implements java.io.Serializable {

	private static final long serialVersionUID = -4621080766396629788L;

	/**
	 * 司机所属部门DRIVER_ORG_CODE
	 */
	private String driverOrgCode;
	/**
	 * 司机所属部门名称
	 */
	private String driverOrgName;
	/**
	 * 司机电话DRIVER_PHONE
	 */
	private String driverPhone;
	/**
	 * 排班类型（TFR短途排班PKP接送货排班 SCHEDULING_TYPE
	 */
	private String schedulingType;
	/**
	 * 司机代码DRIVER_CODE
	 */
	private String driverCode;
	/**
	 * 司机姓名
	 */
	private String driverName;
	/**
	 * 日期SCHEDULING_DATE
	 */
	private Date schedulingDate;
	/**
	 * 工作类别PLAN_TYPE
	 */
	private String planType;
	/**
	 * 工作类别PLAN_TYPE
	 */
	private String planTypeName;
	/**
	 * 日期数字DATE_NUM
	 */
	private int dateNum;

	/**
	 * 年月YEAR_MONTH
	 */
	private String yearMonth;

	// 排班任务字段
	/**
	 * 任务序号
	 */
	private String taskNo;
	/**
	 * 短途排班表ID T_OPT_TRUCK_SCHEDULING_ID
	 */
	private String schedulingId;
	/**
	 * 排班任务Id
	 */
	private String scheduleTaskId;

	/**
	 * 车牌号VEHICLE_NO
	 */
	private String vehicleNo;

	/**
	 * 车型TRUCK_MODEL
	 */
	private String truckModel;

	/**
	 * 车辆所属车队
	 */
	private String carOwner;

	/**
	 * 线路LINE_NO
	 */
	private String lineNo;
	/**
	 * 线路名称
	 */
	private String lineName;
	/**
	 * 线路简码
	 */
	private String lineNoCode;

	/**
	 * 班次FREQUENCY_NO
	 */
	private String frequencyNo;

	/**
	 * 发车时间DEPART_TIME
	 */
	private Date departTime;

	/**
	 * 定人定区ZONE_CODE
	 */
	private String zoneCode;
	/**
	 * 行号
	 */
	private int rowNum;

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
	 * 获取 司机电话DRIVER_PHONE.
	 * 
	 * @return the 司机电话DRIVER_PHONE
	 */
	public String getDriverPhone() {
		return driverPhone;
	}

	/**
	 * 设置 司机电话DRIVER_PHONE.
	 * 
	 * @param driverPhone
	 *            the new 司机电话DRIVER_PHONE
	 */
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	/**
	 * 获取 排班类型（TFR短途排班PKP接送货排班 SCHEDULING_TYPE.
	 * 
	 * @return the 排班类型（TFR短途排班PKP接送货排班 SCHEDULING_TYPE
	 */
	public String getSchedulingType() {
		return schedulingType;
	}

	/**
	 * 设置 排班类型（TFR短途排班PKP接送货排班 SCHEDULING_TYPE.
	 * 
	 * @param schedulingType
	 *            the new 排班类型（TFR短途排班PKP接送货排班 SCHEDULING_TYPE
	 */
	public void setSchedulingType(String schedulingType) {
		this.schedulingType = schedulingType;
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
	 * 获取 司机姓名.
	 * 
	 * @return the 司机姓名
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * 设置 司机姓名.
	 * 
	 * @param driverName
	 *            the new 司机姓名
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * 获取 日期SCHEDULING_DATE.
	 * 
	 * @return the 日期SCHEDULING_DATE
	 */
	public Date getSchedulingDate() {
		return schedulingDate;
	}

	/**
	 * 设置 日期SCHEDULING_DATE.
	 * 
	 * @param schedulingDate
	 *            the new 日期SCHEDULING_DATE
	 */
	public void setSchedulingDate(Date schedulingDate) {
		this.schedulingDate = schedulingDate;
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
	 * 获取 工作类别PLAN_TYPE.
	 * 
	 * @return the 工作类别PLAN_TYPE
	 */
	public String getPlanTypeName() {
		return planTypeName;
	}

	/**
	 * 设置 工作类别PLAN_TYPE.
	 * 
	 * @param planTypeName
	 *            the new 工作类别PLAN_TYPE
	 */
	public void setPlanTypeName(String planTypeName) {
		this.planTypeName = planTypeName;
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
	 * 获取 任务序号.
	 * 
	 * @return the 任务序号
	 */
	public String getTaskNo() {
		return taskNo;
	}

	/**
	 * 设置 任务序号.
	 * 
	 * @param taskNo
	 *            the new 任务序号
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	/**
	 * 获取 短途排班表ID T_OPT_TRUCK_SCHEDULING_ID.
	 * 
	 * @return the 短途排班表ID T_OPT_TRUCK_SCHEDULING_ID
	 */
	public String getSchedulingId() {
		return schedulingId;
	}

	/**
	 * 设置 短途排班表ID T_OPT_TRUCK_SCHEDULING_ID.
	 * 
	 * @param schedulingId
	 *            the new 短途排班表ID T_OPT_TRUCK_SCHEDULING_ID
	 */
	public void setSchedulingId(String schedulingId) {
		this.schedulingId = schedulingId;
	}

	/**
	 * 获取 车牌号VEHICLE_NO.
	 * 
	 * @return the 车牌号VEHICLE_NO
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * 设置 车牌号VEHICLE_NO.
	 * 
	 * @param vehicleNo
	 *            the new 车牌号VEHICLE_NO
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 车型TRUCK_MODEL.
	 * 
	 * @return the 车型TRUCK_MODEL
	 */
	public String getTruckModel() {
		return truckModel;
	}

	/**
	 * 设置 车型TRUCK_MODEL.
	 * 
	 * @param truckModel
	 *            the new 车型TRUCK_MODEL
	 */
	public void setTruckModel(String truckModel) {
		this.truckModel = truckModel;
	}

	/**
	 * 获取 车辆所属车队.
	 * 
	 * @return the 车辆所属车队
	 */
	public String getCarOwner() {
		return carOwner;
	}

	/**
	 * 设置 车辆所属车队.
	 * 
	 * @param carOwner
	 *            the new 车辆所属车队
	 */
	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
	}

	/**
	 * 获取 线路LINE_NO.
	 * 
	 * @return the 线路LINE_NO
	 */
	public String getLineNo() {
		return lineNo;
	}

	/**
	 * 设置 线路LINE_NO.
	 * 
	 * @param lineNo
	 *            the new 线路LINE_NO
	 */
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	/**
	 * 获取 班次FREQUENCY_NO.
	 * 
	 * @return the 班次FREQUENCY_NO
	 */
	public String getFrequencyNo() {
		return frequencyNo;
	}

	/**
	 * 设置 班次FREQUENCY_NO.
	 * 
	 * @param frequencyNo
	 *            the new 班次FREQUENCY_NO
	 */
	public void setFrequencyNo(String frequencyNo) {
		this.frequencyNo = frequencyNo;
	}

	/**
	 * 获取 发车时间DEPART_TIME.
	 * 
	 * @return the 发车时间DEPART_TIME
	 */
	public Date getDepartTime() {
		return departTime;
	}

	/**
	 * 设置 发车时间DEPART_TIME.
	 * 
	 * @param departTime
	 *            the new 发车时间DEPART_TIME
	 */
	public void setDepartTime(Date departTime) {
		this.departTime = departTime;
	}

	/**
	 * 获取 定人定区ZONE_CODE.
	 * 
	 * @return the 定人定区ZONE_CODE
	 */
	public String getZoneCode() {
		return zoneCode;
	}

	/**
	 * 设置 定人定区ZONE_CODE.
	 * 
	 * @param zoneCode
	 *            the new 定人定区ZONE_CODE
	 */
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	/**
	 * 获取 线路简码.
	 * 
	 * @return the 线路简码
	 */
	public String getLineNoCode() {
		return lineNoCode;
	}

	/**
	 * 设置 线路简码.
	 * 
	 * @param lineNoCode
	 *            the new 线路简码
	 */
	public void setLineNoCode(String lineNoCode) {
		this.lineNoCode = lineNoCode;
	}

	/**
	 * 获取 线路名称.
	 * 
	 * @return the 线路名称
	 */
	public String getLineName() {
		return lineName;
	}

	/**
	 * 设置 线路名称.
	 * 
	 * @param lineName
	 *            the new 线路名称
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	/**
	 * 获取 行号.
	 * 
	 * @return the 行号
	 */
	public int getRowNum() {
		return rowNum;
	}

	/**
	 * 设置 行号.
	 * 
	 * @param rowNum
	 *            the new 行号
	 */
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	/**
	 * 获取 司机所属部门名称.
	 * 
	 * @return the 司机所属部门名称
	 */
	public String getDriverOrgName() {
		return driverOrgName;
	}

	/**
	 * 设置 司机所属部门名称.
	 * 
	 * @param driverOrgName
	 *            the new 司机所属部门名称
	 */
	public void setDriverOrgName(String driverOrgName) {
		this.driverOrgName = driverOrgName;
	}

	/**
	 * 获取 排班任务Id.
	 * 
	 * @return the 排班任务Id
	 */
	public String getScheduleTaskId() {
		return scheduleTaskId;
	}

	/**
	 * 设置 排班任务Id.
	 * 
	 * @param scheduleTaskId
	 *            the new 排班任务Id
	 */
	public void setScheduleTaskId(String scheduleTaskId) {
		this.scheduleTaskId = scheduleTaskId;
	}

	@Override
	public String toString() {
		return "ScheduleExcelDto [driverOrgCode=" + driverOrgCode + ", driverOrgName=" + driverOrgName
				+ ", driverPhone=" + driverPhone + ", schedulingType=" + schedulingType + ", driverCode=" + driverCode
				+ ", driverName=" + driverName + ", schedulingDate=" + schedulingDate + ", planType=" + planType
				+ ", planTypeName=" + planTypeName + ", dateNum=" + dateNum + ", yearMonth=" + yearMonth + ", taskNo="
				+ taskNo + ", schedulingId=" + schedulingId + ", vehicleNo=" + vehicleNo + ", truckModel=" + truckModel
				+ ", carOwner=" + carOwner + ", lineNo=" + lineNo + ", lineName=" + lineName + ", lineNoCode="
				+ lineNoCode + ", frequencyNo=" + frequencyNo + ", departTime=" + departTime + ", zoneCode=" + zoneCode
				+ ", rowNum=" + rowNum + "]";
	}

}