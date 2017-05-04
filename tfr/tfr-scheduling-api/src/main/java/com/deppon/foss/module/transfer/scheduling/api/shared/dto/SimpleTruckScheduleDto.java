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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/SimpleTruckScheduleDto.java
 * 
 *  FILE NAME     :SimpleTruckScheduleDto.java
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
 * FILE    NAME: TruckScheduleGridDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingZoneEntity;
import com.deppon.foss.util.DateUtils;

/**
 * 排班表简单查询Dto
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-25 上午11:07:10
 */
public class SimpleTruckScheduleDto implements java.io.Serializable {

	private static final long serialVersionUID = 4560661487198727497L;
	/**
	 * 司机姓名
	 */
	private String driverName;
	/**
	 * 司机代码
	 */
	private String driverCode;
	/**
	 * 司机所属车队
	 */
	private String driverOrgCode;
	/**
	 * 司机所属车队
	 */
	private String driverOrgName;
	/**
	 * 定人定区code
	 */
	private String zoneCode;
	/**
	 * 定人定区名
	 */
	private String zoneName;
	/**
	 * 定人定区送货区域code
	 */
	private String deliveryAreaCode;
	/**
	 * 定人定区送货区域名
	 */
	private String deliveryAreaName;
	/**
	 * 工作类型
	 */
	private String planType;
	/**
	 * 日期
	 */
	private Date schedulingDate;
	/**
	 * 年月日（日期的字符串形式）
	 */
	private String ymd;

	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 车型CODE
	 */
	private String truckModel;
	/**
	 * 车型名
	 */
	private String truckModelValue;
	/**
	 * 车辆所属车队
	 */
	private String carOwner;
	/**
	 * 车辆所属车队名称
	 */
	private String carOwnerName;
	/**
	 * 线路
	 */
	private String lineNo;
	/**
	 * 线路名称
	 */
	private String lineName;
	/**
	 * 司机电话
	 */
	private String driverPhone;
	/**
	 * 发车时间
	 */
	private Date departTime;
	/**
	 * 发车时间(字符串形式 0800)
	 */
	private String departTimeShort;
	/**
	 * 班次
	 */
	private String frequencyNo;
	/**
	 * 计划状态
	 */
	private String schedulingStatus;
	/**
	 * 计划类型
	 */
	private String schedulingtype;
	/**
	 * 排班日期从（用于查询）
	 */
	private Date scheduleDateFrom;
	/**
	 * 排班日期到（用于查询）
	 */
	private Date scheduleDateTo;
	/**
	 * 计划ID
	 */
	private String scheduleId;
	/**
	 * 计划任务ID
	 */
	private String scheduleTaskId;
	/**
	 * 任务序号
	 */
	private String taskNo;
	/**
	 * 年月
	 */
	private String yearMonth;
	/**
	 * 工作类别列表,或者用于删除时传递计划任务Id列表
	 */
	private List<String> list;
	/**
	 * 计划ID列表（用于删除时传递）
	 */
	private List<String> scheduleIds;
	/**
	 * 是否本车队小组的车（Y是N否）
	 */
	private String truckFlag;
	/**
	 * 排班部门
	 */
	private String schedulingDepartCode;
	/**
	 * 部门列表
	 */
	private List<String> departmentCodes;
	
	//非工作日接货区域
	private List<TruckSchedulingZoneEntity> schedulingZoneEntity;
	
	//非工作日接货区域拼接名称
	private String pkpRegionalName;
	
	//非工作日送货区域拼接名称
	private String deliveryRegionalName;
	
	//是否带快递货（Y是N否）
	private String isBringExpress;
	
	//预计带货方数（方/F）
	private BigDecimal expectedBringVolume;
	
	//车辆净空
	private BigDecimal selfVolume;
	
	//车辆载重
	private BigDecimal deadLoad;
	
	//是否节假日
	private String isHoliday;
	//出车任务
	private String dispatchVehicleTask;
	//预计出车时间
	private String expectedDispatchVehicleTime; 
	//预计二次出车时间
	private String isTheTwoTripTime; 
	//带货部门code
	private String takeGoodsDepartment;
	//转货部门code
	private String transferGoodsDepartment; 
	//带货部门名称
	private String takeGoodsDepartmentName;
    //转货部门名称
	private String transferGoodsDepartmentName; 
	
	
	public String getTakeGoodsDepartmentName() {
		return takeGoodsDepartmentName;
	}

	public void setTakeGoodsDepartmentName(String takeGoodsDepartmentName) {
		this.takeGoodsDepartmentName = takeGoodsDepartmentName;
	}

	public String getTransferGoodsDepartmentName() {
		return transferGoodsDepartmentName;
	}

	public void setTransferGoodsDepartmentName(String transferGoodsDepartmentName) {
		this.transferGoodsDepartmentName = transferGoodsDepartmentName;
	}

	public String getDispatchVehicleTask() {
		return dispatchVehicleTask;
	}

	public void setDispatchVehicleTask(String dispatchVehicleTask) {
		this.dispatchVehicleTask = dispatchVehicleTask;
	}

	public String getExpectedDispatchVehicleTime() {
		return expectedDispatchVehicleTime;
	}

	public void setExpectedDispatchVehicleTime(String expectedDispatchVehicleTime) {
		this.expectedDispatchVehicleTime = expectedDispatchVehicleTime;
	}

	public String getIsTheTwoTripTime() {
		return isTheTwoTripTime;
	}

	public void setIsTheTwoTripTime(String isTheTwoTripTime) {
		this.isTheTwoTripTime = isTheTwoTripTime;
	}

	public String getTakeGoodsDepartment() {
		return takeGoodsDepartment;
	}

	public void setTakeGoodsDepartment(String takeGoodsDepartment) {
		this.takeGoodsDepartment = takeGoodsDepartment;
	}

	public String getTransferGoodsDepartment() {
		return transferGoodsDepartment;
	}

	public void setTransferGoodsDepartment(String transferGoodsDepartment) {
		this.transferGoodsDepartment = transferGoodsDepartment;
	}

	public String getIsHoliday() {
		return isHoliday;
	}

	public void setIsHoliday(String isHoliday) {
		this.isHoliday = isHoliday;
	}

	public BigDecimal getSelfVolume() {
		return selfVolume;
	}

	public void setSelfVolume(BigDecimal selfVolume) {
		this.selfVolume = selfVolume;
	}

	public BigDecimal getDeadLoad() {
		return deadLoad;
	}

	public void setDeadLoad(BigDecimal deadLoad) {
		this.deadLoad = deadLoad;
	}

	public String getDeliveryAreaCode() {
		return deliveryAreaCode;
	}

	public void setDeliveryAreaCode(String deliveryAreaCode) {
		this.deliveryAreaCode = deliveryAreaCode;
	}

	public String getDeliveryAreaName() {
		return deliveryAreaName;
	}

	public void setDeliveryAreaName(String deliveryAreaName) {
		this.deliveryAreaName = deliveryAreaName;
	}

	/**
	 * 非工作日送货区域拼接名称.
	 * 
	 * @return the 非工作日送货区域拼接名称
	 */
	public String getDeliveryRegionalName() {
		return deliveryRegionalName;
	}

	/**
	 * 设置工作日送货区域拼接名称.
	 * 
	 * @param deliveryRegionalName
	 *            the new 工作日送货区域拼接名称
	 */
	public void setDeliveryRegionalName(String deliveryRegionalName) {
		this.deliveryRegionalName = deliveryRegionalName;
	}

	/**
	 * 是否带快递货.
	 * 
	 * @return the 是否带快递货
	 */
	public String getIsBringExpress() {
		return isBringExpress;
	}

	/**
	 * 设置是否带快递货.
	 * 
	 * @param isBringExpress
	 *            the new 是否带快递货
	 */
	public void setIsBringExpress(String isBringExpress) {
		this.isBringExpress = isBringExpress;
	}

	/**
	 * 预计带货方数（方/F）.
	 * 
	 * @return the 预计带货方数（方/F）
	 */
	public BigDecimal getExpectedBringVolume() {
		return expectedBringVolume;
	}

	/**
	 * 设置预计带货方数（方/F）.
	 * 
	 * @param expectedBringVolume
	 *            the new 预计带货方数（方/F）
	 */
	public void setExpectedBringVolume(BigDecimal expectedBringVolume) {
		this.expectedBringVolume = expectedBringVolume;
	}

	/**
	 * 非工作日接货区域.
	 * 
	 * @return the 非工作日接货区域
	 */
	public List<TruckSchedulingZoneEntity> getSchedulingZoneEntity() {
		return schedulingZoneEntity;
	}
	
	/**
	 * 非工作日接货区域.
	 * 
	 * @param schedulingZoneEntity
	 *            the new 非工作日接货区域
	 */
	public void setSchedulingZoneEntity(
			List<TruckSchedulingZoneEntity> schedulingZoneEntity) {
		this.schedulingZoneEntity = schedulingZoneEntity;
	}

	/**
	 * 非工作日接货区域拼接名称.
	 * 
	 * @return the 非工作日接货区域拼接名称
	 */
	public String getPkpRegionalName() {
		return pkpRegionalName;
	}

	/**
	 * 非工作日接货区域拼接名称.
	 * 
	 * @param pkpRegionalName
	 *            the new 非工作日接货区域拼接名称
	 */
	public void setPkpRegionalName(String pkpRegionalName) {
		this.pkpRegionalName = pkpRegionalName;
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
	 * 获取 司机代码.
	 * 
	 * @return the 司机代码
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * 设置 司机代码.
	 * 
	 * @param driverCode
	 *            the new 司机代码
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * 获取 司机所属车队.
	 * 
	 * @return the 司机所属车队
	 */
	public String getDriverOrgCode() {
		return driverOrgCode;
	}

	/**
	 * 设置 司机所属车队.
	 * 
	 * @param driverOrgCode
	 *            the new 司机所属车队
	 */
	public void setDriverOrgCode(String driverOrgCode) {
		this.driverOrgCode = driverOrgCode;
	}

	/**
	 * 获取 定人定区code.
	 * 
	 * @return the 定人定区code
	 */
	public String getZoneCode() {
		return zoneCode;
	}

	/**
	 * 设置 定人定区code.
	 * 
	 * @param zoneCode
	 *            the new 定人定区code
	 */
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	/**
	 * 获取 工作类型.
	 * 
	 * @return the 工作类型
	 */
	public String getPlanType() {
		return planType;
	}

	/**
	 * 设置 工作类型.
	 * 
	 * @param planType
	 *            the new 工作类型
	 */
	public void setPlanType(String planType) {
		this.planType = planType;
	}

	/**
	 * 获取 日期.
	 * 
	 * @return the 日期
	 */
	public Date getSchedulingDate() {
		return schedulingDate;
	}

	/**
	 * 设置 日期.
	 * 
	 * @param schedulingDate
	 *            the new 日期
	 */
	public void setSchedulingDate(Date schedulingDate) {
		this.schedulingDate = schedulingDate;
	}

	/**
	 * 获取 车牌号.
	 * 
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * 设置 车牌号.
	 * 
	 * @param vehicleNo
	 *            the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 车型CODE.
	 * 
	 * @return the 车型CODE
	 */
	public String getTruckModel() {
		return truckModel;
	}

	/**
	 * 设置 车型CODE.
	 * 
	 * @param truckModel
	 *            the new 车型CODE
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
	 * 获取 线路.
	 * 
	 * @return the 线路
	 */
	public String getLineNo() {
		return lineNo;
	}

	/**
	 * 设置 线路.
	 * 
	 * @param lineNo
	 *            the new 线路
	 */
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	/**
	 * 获取 司机电话.
	 * 
	 * @return the 司机电话
	 */
	public String getDriverPhone() {
		return driverPhone;
	}

	/**
	 * 设置 司机电话.
	 * 
	 * @param driverPhone
	 *            the new 司机电话
	 */
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	/**
	 * 获取 发车时间.
	 * 
	 * @return the 发车时间
	 */
	@DateFormat
	public Date getDepartTime() {
		return departTime;
	}

	/**
	 * 设置 发车时间.
	 * 
	 * @param departTime
	 *            the new 发车时间
	 */
	@DateFormat
	public void setDepartTime(Date departTime) {
		this.departTime = departTime;
	}

	/**
	 * 获取 班次.
	 * 
	 * @return the 班次
	 */
	public String getFrequencyNo() {
		return frequencyNo;
	}

	/**
	 * 设置 班次.
	 * 
	 * @param frequencyNo
	 *            the new 班次
	 */
	public void setFrequencyNo(String frequencyNo) {
		this.frequencyNo = frequencyNo;
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
	 * 获取 计划类型.
	 * 
	 * @return the 计划类型
	 */
	public String getSchedulingtype() {
		return schedulingtype;
	}

	/**
	 * 设置 计划类型.
	 * 
	 * @param schedulingtype
	 *            the new 计划类型
	 */
	public void setSchedulingtype(String schedulingtype) {
		this.schedulingtype = schedulingtype;
	}

	/**
	 * 获取 排班日期从（用于查询）.
	 * 
	 * @return the 排班日期从（用于查询）
	 */
	public Date getScheduleDateFrom() {
		return scheduleDateFrom;
	}

	/**
	 * 设置 排班日期从（用于查询）.
	 * 
	 * @param scheduleDateFrom
	 *            the new 排班日期从（用于查询）
	 */
	public void setScheduleDateFrom(Date scheduleDateFrom) {
		this.scheduleDateFrom = scheduleDateFrom;
	}

	/**
	 * 获取 排班日期到（用于查询）.
	 * 
	 * @return the 排班日期到（用于查询）
	 */
	public Date getScheduleDateTo() {
		return scheduleDateTo;
	}

	/**
	 * 设置 排班日期到（用于查询）.
	 * 
	 * @param scheduleDateTo
	 *            the new 排班日期到（用于查询）
	 */
	public void setScheduleDateTo(Date scheduleDateTo) {
		this.scheduleDateTo = scheduleDateTo;
	}

	/**
	 * 获取 司机所属车队.
	 * 
	 * @return the 司机所属车队
	 */
	public String getDriverOrgName() {
		return driverOrgName;
	}

	/**
	 * 设置 司机所属车队.
	 * 
	 * @param driverOrgName
	 *            the new 司机所属车队
	 */
	public void setDriverOrgName(String driverOrgName) {
		this.driverOrgName = driverOrgName;
	}

	/**
	 * 获取 年月日（日期的字符串形式）.
	 * 
	 * @return the 年月日（日期的字符串形式）
	 */
	public String getYmd() {
		return ymd;
	}

	/**
	 * 设置 年月日（日期的字符串形式）.
	 * 
	 * @param ymd
	 *            the new 年月日（日期的字符串形式）
	 */
	public void setYmd(String ymd) {
		this.ymd = ymd;
	}

	/**
	 * 获取 计划ID.
	 * 
	 * @return the 计划ID
	 */
	public String getScheduleId() {
		return scheduleId;
	}

	/**
	 * 设置 计划ID.
	 * 
	 * @param scheduleId
	 *            the new 计划ID
	 */
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	/**
	 * 获取 计划任务ID.
	 * 
	 * @return the 计划任务ID
	 */
	public String getScheduleTaskId() {
		return scheduleTaskId;
	}

	/**
	 * 设置 计划任务ID.
	 * 
	 * @param scheduleTaskId
	 *            the new 计划任务ID
	 */
	public void setScheduleTaskId(String scheduleTaskId) {
		this.scheduleTaskId = scheduleTaskId;
	}

	/**
	 * 获取 发车时间(字符串形式 0800).
	 * 
	 * @return the 发车时间(字符串形式 0800)
	 */
	public String getDepartTimeShort() {
		if (this.getDepartTime() != null) {
			departTimeShort = DateUtils.convert(this.getDepartTime(), DateUtils.TIME_FORMAT);
		}
		return departTimeShort;
	}

	/**
	 * 设置 发车时间(字符串形式 0800).
	 * 
	 * @param departTimeShort
	 *            the new 发车时间(字符串形式 0800)
	 */
	public void setDepartTimeShort(String departTimeShort) {
		this.departTimeShort = departTimeShort;
	}

	/**
	 * 获取 定人定区名.
	 * 
	 * @return the 定人定区名
	 */
	public String getZoneName() {
		return zoneName;
	}

	/**
	 * 设置 定人定区名.
	 * 
	 * @param zoneName
	 *            the new 定人定区名
	 */
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	/**
	 * 获取 车辆所属车队名称.
	 * 
	 * @return the 车辆所属车队名称
	 */
	public String getCarOwnerName() {
		return carOwnerName;
	}

	/**
	 * 设置 车辆所属车队名称.
	 * 
	 * @param carOwnerName
	 *            the new 车辆所属车队名称
	 */
	public void setCarOwnerName(String carOwnerName) {
		this.carOwnerName = carOwnerName;
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
	 * 获取 年月.
	 * 
	 * @return the 年月
	 */
	public String getYearMonth() {
		return yearMonth;
	}

	/**
	 * 设置 年月.
	 * 
	 * @param yearMonth
	 *            the new 年月
	 */
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	/**
	 * 获取 工作类别列表,或者用于删除时传递计划任务Id列表.
	 * 
	 * @return the 工作类别列表,或者用于删除时传递计划任务Id列表
	 */
	public List<String> getList() {
		return list;
	}

	/**
	 * 设置 工作类别列表,或者用于删除时传递计划任务Id列表.
	 * 
	 * @param list
	 *            the new 工作类别列表,或者用于删除时传递计划任务Id列表
	 */
	public void setList(List<String> list) {
		this.list = list;
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
	 * 获取 计划ID列表（用于删除时传递）.
	 * 
	 * @return the 计划ID列表（用于删除时传递）
	 */
	public List<String> getScheduleIds() {
		return scheduleIds;
	}

	/**
	 * 设置 计划ID列表（用于删除时传递）.
	 * 
	 * @param scheduleIds
	 *            the new 计划ID列表（用于删除时传递）
	 */
	public void setScheduleIds(List<String> scheduleIds) {
		this.scheduleIds = scheduleIds;
	}

	/**
	 * 获取 车型名.
	 * 
	 * @return the 车型名
	 */
	public String getTruckModelValue() {
		return truckModelValue;
	}

	/**
	 * 设置 车型名.
	 * 
	 * @param truckModelValue
	 *            the new 车型名
	 */
	public void setTruckModelValue(String truckModelValue) {
		this.truckModelValue = truckModelValue;
	}

	/**
	 * 获取 是否本车队小组的车（Y是N否）.
	 * 
	 * @return the 是否本车队小组的车（Y是N否）
	 */
	public String getTruckFlag() {
		return truckFlag;
	}

	/**
	 * 设置 是否本车队小组的车（Y是N否）.
	 * 
	 * @param truckFlag
	 *            the new 是否本车队小组的车（Y是N否）
	 */
	public void setTruckFlag(String truckFlag) {
		this.truckFlag = truckFlag;
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
	 * @param schedulingDepartCode
	 *            the new 排班部门
	 */
	public void setSchedulingDepartCode(String schedulingDepartCode) {
		this.schedulingDepartCode = schedulingDepartCode;
	}

	public List<String> getDepartmentCodes() {
		return departmentCodes;
	}

	public void setDepartmentCodes(List<String> departmentCodes) {
		this.departmentCodes = departmentCodes;
	}

	@Override
	public String toString() {
		return "SimpleTruckScheduleDto [driverName=" + driverName + ", driverCode=" + driverCode + ", driverOrgCode="
				+ driverOrgCode + ", zoneCode=" + zoneCode + ", planType=" + planType + ", schedulingDate="
				+ schedulingDate + ", vehicleNo=" + vehicleNo + ", truckModel=" + truckModel + ", carOwner=" + carOwner
				+ ", lineNo=" + lineNo + ", driverPhone=" + driverPhone + ", departTime=" + departTime
				+ ", frequencyNo=" + frequencyNo + ", schedulingStatus=" + schedulingStatus + ", schedulingtype="
				+ schedulingtype + ", scheduleDateFrom=" + scheduleDateFrom + ", scheduleDateTo=" + scheduleDateTo+",pkpRegionalName="+pkpRegionalName
				+ "]";
	}

}