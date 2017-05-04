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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/TruckScheduleGridDto.java
 * 
 *  FILE NAME     :TruckScheduleGridDto.java
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

/**
 * 表格统计Dto
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-25 上午11:07:10
 */
public class TruckScheduleGridDto implements java.io.Serializable {

	private static final long serialVersionUID = -2782726628838149907L;
	/**
	 * id
	 * */
	private String id;
	/**
	 * 司机姓名
	 */
	private String driverName;
	/**
	 * 司机代码
	 */
	private String driverCode;
	/**
	 * 司机电话
	 */
	private String driverPhone;
	/**
	 * 司机所属车队
	 */
	private String driverOrgCode;
	/**
	 * 司机所属车队名称
	 */
	private String driverOrgName;
	/**
	 * 线路名称或区域
	 */
	private String lineNameOrArea;
	/**
	 * 车牌
	 */
	private String vehicleNo;
	/**
	 * 工作类型主要用于查询条件
	 */
	private String planType;
	/**
	 * 计划月份
	 */
	private String yearMonth;

	/**
	 * 1日上班状态
	 */
	private String date1;
	/**
	 * 2日上班状态
	 */
	private String date2;
	/**
	 * 3日上班状态
	 */
	private String date3;
	/**
	 * 4日上班状态
	 */
	private String date4;
	/**
	 * 5日上班状态
	 */
	private String date5;
	/**
	 * 6日上班状态
	 */
	private String date6;
	/**
	 * 7日上班状态
	 */
	private String date7;
	/**
	 * 8日上班状态
	 */
	private String date8;
	/**
	 * 9日上班状态
	 */
	private String date9;
	/**
	 * 10日上班状态
	 */
	private String date10;
	/**
	 * 11日上班状态
	 */
	private String date11;
	/**
	 * 12日上班状态
	 */
	private String date12;
	/**
	 * 13日上班状态
	 */
	private String date13;
	/**
	 * 14日上班状态
	 */
	private String date14;
	/**
	 * 15日上班状态
	 */
	private String date15;
	/**
	 * 16日上班状态
	 */
	private String date16;
	/**
	 * 17日上班状态
	 */
	private String date17;
	/**
	 * 18日上班状态
	 */
	private String date18;
	/**
	 * 19日上班状态
	 */
	private String date19;
	/**
	 * 20日上班状态
	 */
	private String date20;
	/**
	 * 21日上班状态
	 */
	private String date21;
	/**
	 * 22日上班状态
	 */
	private String date22;
	/**
	 * 23日上班状态
	 */
	private String date23;
	/**
	 * 24日上班状态
	 */
	private String date24;
	/**
	 * 25日上班状态
	 */
	private String date25;
	/**
	 * 26日上班状态
	 */
	private String date26;
	/**
	 * 27日上班状态
	 */
	private String date27;
	/**
	 * 28日上班状态
	 */
	private String date28;
	/**
	 * 29日上班状态
	 */
	private String date29;
	/**
	 * 30日上班状态
	 */
	private String date30;
	/**
	 * 31日上班状态
	 */
	private String date31;
	/**
	 * 上班天数
	 */
	private int workTotal;
	/**
	 * 排班类型
	 */
	private String schedulingType;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取 1日上班状态.
	 * 
	 * @return the 1日上班状态
	 */
	public String getDate1() {
		return date1;
	}

	/**
	 * 设置 1日上班状态.
	 * 
	 * @param date1
	 *            the new 1日上班状态
	 */
	public void setDate1(String date1) {
		this.date1 = date1;
	}

	/**
	 * 获取 2日上班状态.
	 * 
	 * @return the 2日上班状态
	 */
	public String getDate2() {
		return date2;
	}

	/**
	 * 设置 2日上班状态.
	 * 
	 * @param date2
	 *            the new 2日上班状态
	 */
	public void setDate2(String date2) {
		this.date2 = date2;
	}

	/**
	 * 获取 3日上班状态.
	 * 
	 * @return the 3日上班状态
	 */
	public String getDate3() {
		return date3;
	}

	/**
	 * 设置 3日上班状态.
	 * 
	 * @param date3
	 *            the new 3日上班状态
	 */
	public void setDate3(String date3) {
		this.date3 = date3;
	}

	/**
	 * 获取 4日上班状态.
	 * 
	 * @return the 4日上班状态
	 */
	public String getDate4() {
		return date4;
	}

	/**
	 * 设置 4日上班状态.
	 * 
	 * @param date4
	 *            the new 4日上班状态
	 */
	public void setDate4(String date4) {
		this.date4 = date4;
	}

	/**
	 * 获取 5日上班状态.
	 * 
	 * @return the 5日上班状态
	 */
	public String getDate5() {
		return date5;
	}

	/**
	 * 设置 5日上班状态.
	 * 
	 * @param date5
	 *            the new 5日上班状态
	 */
	public void setDate5(String date5) {
		this.date5 = date5;
	}

	/**
	 * 获取 6日上班状态.
	 * 
	 * @return the 6日上班状态
	 */
	public String getDate6() {
		return date6;
	}

	/**
	 * 设置 6日上班状态.
	 * 
	 * @param date6
	 *            the new 6日上班状态
	 */
	public void setDate6(String date6) {
		this.date6 = date6;
	}

	/**
	 * 获取 7日上班状态.
	 * 
	 * @return the 7日上班状态
	 */
	public String getDate7() {
		return date7;
	}

	/**
	 * 设置 7日上班状态.
	 * 
	 * @param date7
	 *            the new 7日上班状态
	 */
	public void setDate7(String date7) {
		this.date7 = date7;
	}

	/**
	 * 获取 8日上班状态.
	 * 
	 * @return the 8日上班状态
	 */
	public String getDate8() {
		return date8;
	}

	/**
	 * 设置 8日上班状态.
	 * 
	 * @param date8
	 *            the new 8日上班状态
	 */
	public void setDate8(String date8) {
		this.date8 = date8;
	}

	/**
	 * 获取 9日上班状态.
	 * 
	 * @return the 9日上班状态
	 */
	public String getDate9() {
		return date9;
	}

	/**
	 * 设置 9日上班状态.
	 * 
	 * @param date9
	 *            the new 9日上班状态
	 */
	public void setDate9(String date9) {
		this.date9 = date9;
	}

	/**
	 * 获取 10日上班状态.
	 * 
	 * @return the 10日上班状态
	 */
	public String getDate10() {
		return date10;
	}

	/**
	 * 设置 10日上班状态.
	 * 
	 * @param date10
	 *            the new 10日上班状态
	 */
	public void setDate10(String date10) {
		this.date10 = date10;
	}

	/**
	 * 获取 11日上班状态.
	 * 
	 * @return the 11日上班状态
	 */
	public String getDate11() {
		return date11;
	}

	/**
	 * 设置 11日上班状态.
	 * 
	 * @param date11
	 *            the new 11日上班状态
	 */
	public void setDate11(String date11) {
		this.date11 = date11;
	}

	/**
	 * 获取 12日上班状态.
	 * 
	 * @return the 12日上班状态
	 */
	public String getDate12() {
		return date12;
	}

	/**
	 * 设置 12日上班状态.
	 * 
	 * @param date12
	 *            the new 12日上班状态
	 */
	public void setDate12(String date12) {
		this.date12 = date12;
	}

	/**
	 * 获取 13日上班状态.
	 * 
	 * @return the 13日上班状态
	 */
	public String getDate13() {
		return date13;
	}

	/**
	 * 设置 13日上班状态.
	 * 
	 * @param date13
	 *            the new 13日上班状态
	 */
	public void setDate13(String date13) {
		this.date13 = date13;
	}

	/**
	 * 获取 14日上班状态.
	 * 
	 * @return the 14日上班状态
	 */
	public String getDate14() {
		return date14;
	}

	/**
	 * 设置 14日上班状态.
	 * 
	 * @param date14
	 *            the new 14日上班状态
	 */
	public void setDate14(String date14) {
		this.date14 = date14;
	}

	/**
	 * 获取 15日上班状态.
	 * 
	 * @return the 15日上班状态
	 */
	public String getDate15() {
		return date15;
	}

	/**
	 * 设置 15日上班状态.
	 * 
	 * @param date15
	 *            the new 15日上班状态
	 */
	public void setDate15(String date15) {
		this.date15 = date15;
	}

	/**
	 * 获取 16日上班状态.
	 * 
	 * @return the 16日上班状态
	 */
	public String getDate16() {
		return date16;
	}

	/**
	 * 设置 16日上班状态.
	 * 
	 * @param date16
	 *            the new 16日上班状态
	 */
	public void setDate16(String date16) {
		this.date16 = date16;
	}

	/**
	 * 获取 17日上班状态.
	 * 
	 * @return the 17日上班状态
	 */
	public String getDate17() {
		return date17;
	}

	/**
	 * 设置 17日上班状态.
	 * 
	 * @param date17
	 *            the new 17日上班状态
	 */
	public void setDate17(String date17) {
		this.date17 = date17;
	}

	/**
	 * 获取 18日上班状态.
	 * 
	 * @return the 18日上班状态
	 */
	public String getDate18() {
		return date18;
	}

	/**
	 * 设置 18日上班状态.
	 * 
	 * @param date18
	 *            the new 18日上班状态
	 */
	public void setDate18(String date18) {
		this.date18 = date18;
	}

	/**
	 * 获取 19日上班状态.
	 * 
	 * @return the 19日上班状态
	 */
	public String getDate19() {
		return date19;
	}

	/**
	 * 设置 19日上班状态.
	 * 
	 * @param date19
	 *            the new 19日上班状态
	 */
	public void setDate19(String date19) {
		this.date19 = date19;
	}

	/**
	 * 获取 20日上班状态.
	 * 
	 * @return the 20日上班状态
	 */
	public String getDate20() {
		return date20;
	}

	/**
	 * 设置 20日上班状态.
	 * 
	 * @param date20
	 *            the new 20日上班状态
	 */
	public void setDate20(String date20) {
		this.date20 = date20;
	}

	/**
	 * 获取 21日上班状态.
	 * 
	 * @return the 21日上班状态
	 */
	public String getDate21() {
		return date21;
	}

	/**
	 * 设置 21日上班状态.
	 * 
	 * @param date21
	 *            the new 21日上班状态
	 */
	public void setDate21(String date21) {
		this.date21 = date21;
	}

	/**
	 * 获取 22日上班状态.
	 * 
	 * @return the 22日上班状态
	 */
	public String getDate22() {
		return date22;
	}

	/**
	 * 设置 22日上班状态.
	 * 
	 * @param date22
	 *            the new 22日上班状态
	 */
	public void setDate22(String date22) {
		this.date22 = date22;
	}

	/**
	 * 获取 23日上班状态.
	 * 
	 * @return the 23日上班状态
	 */
	public String getDate23() {
		return date23;
	}

	/**
	 * 设置 23日上班状态.
	 * 
	 * @param date23
	 *            the new 23日上班状态
	 */
	public void setDate23(String date23) {
		this.date23 = date23;
	}

	/**
	 * 获取 24日上班状态.
	 * 
	 * @return the 24日上班状态
	 */
	public String getDate24() {
		return date24;
	}

	/**
	 * 设置 24日上班状态.
	 * 
	 * @param date24
	 *            the new 24日上班状态
	 */
	public void setDate24(String date24) {
		this.date24 = date24;
	}

	/**
	 * 获取 25日上班状态.
	 * 
	 * @return the 25日上班状态
	 */
	public String getDate25() {
		return date25;
	}

	/**
	 * 设置 25日上班状态.
	 * 
	 * @param date25
	 *            the new 25日上班状态
	 */
	public void setDate25(String date25) {
		this.date25 = date25;
	}

	/**
	 * 获取 26日上班状态.
	 * 
	 * @return the 26日上班状态
	 */
	public String getDate26() {
		return date26;
	}

	/**
	 * 设置 26日上班状态.
	 * 
	 * @param date26
	 *            the new 26日上班状态
	 */
	public void setDate26(String date26) {
		this.date26 = date26;
	}

	/**
	 * 获取 27日上班状态.
	 * 
	 * @return the 27日上班状态
	 */
	public String getDate27() {
		return date27;
	}

	/**
	 * 设置 27日上班状态.
	 * 
	 * @param date27
	 *            the new 27日上班状态
	 */
	public void setDate27(String date27) {
		this.date27 = date27;
	}

	/**
	 * 获取 28日上班状态.
	 * 
	 * @return the 28日上班状态
	 */
	public String getDate28() {
		return date28;
	}

	/**
	 * 设置 28日上班状态.
	 * 
	 * @param date28
	 *            the new 28日上班状态
	 */
	public void setDate28(String date28) {
		this.date28 = date28;
	}

	/**
	 * 获取 29日上班状态.
	 * 
	 * @return the 29日上班状态
	 */
	public String getDate29() {
		return date29;
	}

	/**
	 * 设置 29日上班状态.
	 * 
	 * @param date29
	 *            the new 29日上班状态
	 */
	public void setDate29(String date29) {
		this.date29 = date29;
	}

	/**
	 * 获取 30日上班状态.
	 * 
	 * @return the 30日上班状态
	 */
	public String getDate30() {
		return date30;
	}

	/**
	 * 设置 30日上班状态.
	 * 
	 * @param date30
	 *            the new 30日上班状态
	 */
	public void setDate30(String date30) {
		this.date30 = date30;
	}

	/**
	 * 获取 31日上班状态.
	 * 
	 * @return the 31日上班状态
	 */
	public String getDate31() {
		return date31;
	}

	/**
	 * 设置 31日上班状态.
	 * 
	 * @param date31
	 *            the new 31日上班状态
	 */
	public void setDate31(String date31) {
		this.date31 = date31;
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
	 * 获取 计划月份.
	 * 
	 * @return the 计划月份
	 */
	public String getYearMonth() {
		return yearMonth;
	}

	/**
	 * 设置 计划月份.
	 * 
	 * @param yearMonth
	 *            the new 计划月份
	 */
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	/**
	 * 获取 工作类型主要用于查询条件.
	 * 
	 * @return the 工作类型主要用于查询条件
	 */
	public String getPlanType() {
		return planType;
	}

	/**
	 * 设置 工作类型主要用于查询条件.
	 * 
	 * @param planType
	 *            the new 工作类型主要用于查询条件
	 */
	public void setPlanType(String planType) {
		this.planType = planType;
	}

	/**
	 * 获取 上班天数.
	 * 
	 * @return the 上班天数
	 */
	public int getWorkTotal() {
		return workTotal;
	}

	/**
	 * 设置 上班天数.
	 * 
	 * @param workTotal
	 *            the new 上班天数
	 */
	public void setWorkTotal(int workTotal) {
		this.workTotal = workTotal;
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
	 * 获取 线路名称或区域.
	 *
	 * @return the 线路名称或区域
	 */
	public String getLineNameOrArea() {
		return lineNameOrArea;
	}

	/**
	 * 设置 线路名称或区域.
	 *
	 * @param lineNameOrArea the new 线路名称或区域
	 */
	public void setLineNameOrArea(String lineNameOrArea) {
		this.lineNameOrArea = lineNameOrArea;
	}

	/**
	 * 获取 车牌.
	 *
	 * @return the 车牌
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * 设置 车牌.
	 *
	 * @param vehicleNo the new 车牌
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 司机所属车队名称.
	 *
	 * @return the 司机所属车队名称
	 */
	public String getDriverOrgName() {
		return driverOrgName;
	}

	/**
	 * 设置 司机所属车队名称.
	 *
	 * @param driverOrgName the new 司机所属车队名称
	 */
	public void setDriverOrgName(String driverOrgName) {
		this.driverOrgName = driverOrgName;
	}

}