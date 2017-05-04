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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/PlatformDistributeDto.java
 * 
 *  FILE NAME     :PlatformDistributeDto.java
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
 * FILE    NAME: PlatformDistributeDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformDistributeEntity;

/**
 * 月台Dto
 * @author 104306-foss-wangLong
 * @date 2012-11-3 下午5:08:39
 */
public class PlatformDistributeDto implements Serializable {

	private static final long serialVersionUID = 8477787745529973444L;
	
	/** 月台编号  */
	private String platformNo;
	
	/** 月台虚拟编号 */
	private String platformVirtualCode;
	
	/** 车牌号 */
	private String vehicleNo;
	
	/** 使用标示   */
	private String useType;
	
	/** 车型 */
	private String vehicleType;
	
	/** 时间 用于查询条件 */
	private Date useBeginTime;
	
	private Date useEndTime;
	
	/** 显示可用月台  用于查询条件 */
	private boolean showAvailable;
	
	/** 按月台号 区间查询 */
	private String startPlatformCode;
	
	private String endPlatformCode;
	
	private List<String> statusList;
	
	private String hour0100;
	
	private String hour0130;
	
	private String hour0200;
	
	private String hour0230;
	
	private String hour0300;
	
	private String hour0330;
	
	private String hour0400;
	
	private String hour0430;
	
	private String hour0500;

	private String hour0530;
	
	private String hour0600;
	
	private String hour0630;
	
	private String hour0700;
	
	private String hour0730;
	
	private String hour0800;
	
	private String hour0830;
	
	private String hour0900;
	
	private String hour0930;
	
	private String hour1000;
	
	private String hour1030;
	
	private String hour1100;
	
	private String hour1130;
	
	private String hour1200;
	
	private String hour1230;
	
	private String hour1300;
	
	private String hour1330;
	
	private String hour1400;
	
	private String hour1430;
	
	private String hour1500;
	
	private String hour1530;
	
	private String hour1600;
	
	private String hour1630;
	
	private String hour1700;

	private String hour1730;
	
	private String hour1800;
	
	private String hour1830;
	
	private String hour1900;
	
	private String hour1930;
	
	private String hour2000;
	
	private String hour2030;
	
	private String hour2100;
	
	private String hour2130;
	
	private String hour2200;
	
	private String hour2230;
	
	private String hour2300;
	
	private String hour2330;
	
	private String hour0000;
	
	private String hour0030;
	
	private PlatformDistributeEntity platformDistributeEntity;
	
	/**
	 * 默认构造
	 * @author 104306-foss-wangLong
	 * @date 2012-11-8 上午9:52:42
	 */
	public PlatformDistributeDto() {
		this(new PlatformDistributeEntity());
	}

	/**
	 * @author 104306-foss-wangLong
	 * @date 2012-11-8 上午9:52:23
	 * @param 月台实体
	 */
	public PlatformDistributeDto(
			PlatformDistributeEntity platformDistributeEntity) {
		super();
		this.platformDistributeEntity = platformDistributeEntity;
	}

	/**
	 * 获得platformNo
	 * @return the platformNo
	 */
	public String getPlatformNo() {
		return platformNo;
	}

	/**
	 * 设置platformNo
	 * @param platformNo the platformNo to set
	 */
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}

	/**
	 * 获得platformVirtualCode
	 * @return the platformVirtualCode
	 */
	public String getPlatformVirtualCode() {
		return platformVirtualCode;
	}

	/**
	 * 设置platformVirtualCode
	 * @param platformVirtualCode the platformVirtualCode to set
	 */
	public void setPlatformVirtualCode(String platformVirtualCode) {
		this.platformVirtualCode = platformVirtualCode;
	}

	/**
	 * 获得vehicleNo
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * 设置vehicleNo
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获得useType
	 * @return the useType
	 */
	public String getUseType() {
		return useType;
	}

	/**
	 * 设置useType
	 * @param useType the useType to set
	 */
	public void setUseType(String useType) {
		this.useType = useType;
	}

	/**
	 * 获得vehicleType
	 * @return the vehicleType
	 */
	public String getVehicleType() {
		return vehicleType;
	}

	/**
	 * 设置vehicleType
	 * @param vehicleType the vehicleType to set
	 */
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	/**
	 * 获得useBeginTime
	 * @return the useBeginTime
	 */
	public Date getUseBeginTime() {
		return useBeginTime;
	}

	/**
	 * 设置useBeginTime
	 * @param useBeginTime the useBeginTime to set
	 */
	public void setUseBeginTime(Date useBeginTime) {
		this.useBeginTime = useBeginTime;
	}

	/**
	 * 获得useEndTime
	 * @return the useEndTime
	 */
	public Date getUseEndTime() {
		return useEndTime;
	}

	/**
	 * 设置useEndTime
	 * @param useEndTime the useEndTime to set
	 */
	public void setUseEndTime(Date useEndTime) {
		this.useEndTime = useEndTime;
	}

	/**
	 * 获得showAvailable
	 * @return the showAvailable
	 */
	public boolean isShowAvailable() {
		return showAvailable;
	}

	/**
	 * 设置showAvailable
	 * @param showAvailable the showAvailable to set
	 */
	public void setShowAvailable(boolean showAvailable) {
		this.showAvailable = showAvailable;
	}

	/**
	 * 获得startPlatformCode
	 * @return the startPlatformCode
	 */
	public String getStartPlatformCode() {
		return startPlatformCode;
	}

	/**
	 * 设置startPlatformCode
	 * @param startPlatformCode the startPlatformCode to set
	 */
	public void setStartPlatformCode(String startPlatformCode) {
		this.startPlatformCode = startPlatformCode;
	}

	/**
	 * 获得endPlatformCode
	 * @return the endPlatformCode
	 */
	public String getEndPlatformCode() {
		return endPlatformCode;
	}

	/**
	 * 设置endPlatformCode
	 * @param endPlatformCode the endPlatformCode to set
	 */
	public void setEndPlatformCode(String endPlatformCode) {
		this.endPlatformCode = endPlatformCode;
	}

	/**
	 * 获得statusList
	 * @return the statusList
	 */
	public List<String> getStatusList() {
		return statusList;
	}

	/**
	 * 设置statusList
	 * @param statusList the statusList to set
	 */
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}

	/**
	 * 获得hour0100
	 * @return the hour0100
	 */
	public String getHour0100() {
		return hour0100;
	}

	/**
	 * 设置hour0100
	 * @param hour0100 the hour0100 to set
	 */
	public void setHour0100(String hour0100) {
		this.hour0100 = hour0100;
	}

	/**
	 * 获得hour0130
	 * @return the hour0130
	 */
	public String getHour0130() {
		return hour0130;
	}

	/**
	 * 设置hour0130
	 * @param hour0130 the hour0130 to set
	 */
	public void setHour0130(String hour0130) {
		this.hour0130 = hour0130;
	}

	/**
	 * 获得hour0200
	 * @return the hour0200
	 */
	public String getHour0200() {
		return hour0200;
	}

	/**
	 * 设置hour0200
	 * @param hour0200 the hour0200 to set
	 */
	public void setHour0200(String hour0200) {
		this.hour0200 = hour0200;
	}

	/**
	 * 获得hour0230
	 * @return the hour0230
	 */
	public String getHour0230() {
		return hour0230;
	}

	/**
	 * 设置hour0230
	 * @param hour0230 the hour0230 to set
	 */
	public void setHour0230(String hour0230) {
		this.hour0230 = hour0230;
	}

	/**
	 * 获得hour0300
	 * @return the hour0300
	 */
	public String getHour0300() {
		return hour0300;
	}

	/**
	 * 设置hour0300
	 * @param hour0300 the hour0300 to set
	 */
	public void setHour0300(String hour0300) {
		this.hour0300 = hour0300;
	}

	/**
	 * 获得hour0330
	 * @return the hour0330
	 */
	public String getHour0330() {
		return hour0330;
	}

	/**
	 * 设置hour0330
	 * @param hour0330 the hour0330 to set
	 */
	public void setHour0330(String hour0330) {
		this.hour0330 = hour0330;
	}

	/**
	 * 获得hour0400
	 * @return the hour0400
	 */
	public String getHour0400() {
		return hour0400;
	}

	/**
	 * 设置hour0400
	 * @param hour0400 the hour0400 to set
	 */
	public void setHour0400(String hour0400) {
		this.hour0400 = hour0400;
	}

	/**
	 * 获得hour0430
	 * @return the hour0430
	 */
	public String getHour0430() {
		return hour0430;
	}

	/**
	 * 设置hour0430
	 * @param hour0430 the hour0430 to set
	 */
	public void setHour0430(String hour0430) {
		this.hour0430 = hour0430;
	}

	/**
	 * 获得hour0500
	 * @return the hour0500
	 */
	public String getHour0500() {
		return hour0500;
	}

	/**
	 * 设置hour0500
	 * @param hour0500 the hour0500 to set
	 */
	public void setHour0500(String hour0500) {
		this.hour0500 = hour0500;
	}

	/**
	 * 获得hour0530
	 * @return the hour0530
	 */
	public String getHour0530() {
		return hour0530;
	}

	/**
	 * 设置hour0530
	 * @param hour0530 the hour0530 to set
	 */
	public void setHour0530(String hour0530) {
		this.hour0530 = hour0530;
	}

	/**
	 * 获得hour0600
	 * @return the hour0600
	 */
	public String getHour0600() {
		return hour0600;
	}

	/**
	 * 设置hour0600
	 * @param hour0600 the hour0600 to set
	 */
	public void setHour0600(String hour0600) {
		this.hour0600 = hour0600;
	}

	/**
	 * 获得hour0630
	 * @return the hour0630
	 */
	public String getHour0630() {
		return hour0630;
	}

	/**
	 * 设置hour0630
	 * @param hour0630 the hour0630 to set
	 */
	public void setHour0630(String hour0630) {
		this.hour0630 = hour0630;
	}

	/**
	 * 获得hour0700
	 * @return the hour0700
	 */
	public String getHour0700() {
		return hour0700;
	}

	/**
	 * 设置hour0700
	 * @param hour0700 the hour0700 to set
	 */
	public void setHour0700(String hour0700) {
		this.hour0700 = hour0700;
	}

	/**
	 * 获得hour0730
	 * @return the hour0730
	 */
	public String getHour0730() {
		return hour0730;
	}

	/**
	 * 设置hour0730
	 * @param hour0730 the hour0730 to set
	 */
	public void setHour0730(String hour0730) {
		this.hour0730 = hour0730;
	}

	/**
	 * 获得hour0800
	 * @return the hour0800
	 */
	public String getHour0800() {
		return hour0800;
	}

	/**
	 * 设置hour0800
	 * @param hour0800 the hour0800 to set
	 */
	public void setHour0800(String hour0800) {
		this.hour0800 = hour0800;
	}

	/**
	 * 获得hour0830
	 * @return the hour0830
	 */
	public String getHour0830() {
		return hour0830;
	}

	/**
	 * 设置hour0830
	 * @param hour0830 the hour0830 to set
	 */
	public void setHour0830(String hour0830) {
		this.hour0830 = hour0830;
	}

	/**
	 * 获得hour0900
	 * @return the hour0900
	 */
	public String getHour0900() {
		return hour0900;
	}

	/**
	 * 设置hour0900
	 * @param hour0900 the hour0900 to set
	 */
	public void setHour0900(String hour0900) {
		this.hour0900 = hour0900;
	}

	/**
	 * 获得hour0930
	 * @return the hour0930
	 */
	public String getHour0930() {
		return hour0930;
	}

	/**
	 * 设置hour0930
	 * @param hour0930 the hour0930 to set
	 */
	public void setHour0930(String hour0930) {
		this.hour0930 = hour0930;
	}

	/**
	 * 获得hour1000
	 * @return the hour1000
	 */
	public String getHour1000() {
		return hour1000;
	}

	/**
	 * 设置hour1000
	 * @param hour1000 the hour1000 to set
	 */
	public void setHour1000(String hour1000) {
		this.hour1000 = hour1000;
	}

	/**
	 * 获得hour1030
	 * @return the hour1030
	 */
	public String getHour1030() {
		return hour1030;
	}

	/**
	 * 设置hour1030
	 * @param hour1030 the hour1030 to set
	 */
	public void setHour1030(String hour1030) {
		this.hour1030 = hour1030;
	}

	/**
	 * 获得hour1100
	 * @return the hour1100
	 */
	public String getHour1100() {
		return hour1100;
	}

	/**
	 * 设置hour1100
	 * @param hour1100 the hour1100 to set
	 */
	public void setHour1100(String hour1100) {
		this.hour1100 = hour1100;
	}

	/**
	 * 获得hour1130
	 * @return the hour1130
	 */
	public String getHour1130() {
		return hour1130;
	}

	/**
	 * 设置hour1130
	 * @param hour1130 the hour1130 to set
	 */
	public void setHour1130(String hour1130) {
		this.hour1130 = hour1130;
	}

	/**
	 * 获得hour1200
	 * @return the hour1200
	 */
	public String getHour1200() {
		return hour1200;
	}

	/**
	 * 设置hour1200
	 * @param hour1200 the hour1200 to set
	 */
	public void setHour1200(String hour1200) {
		this.hour1200 = hour1200;
	}

	/**
	 * 获得hour1230
	 * @return the hour1230
	 */
	public String getHour1230() {
		return hour1230;
	}

	/**
	 * 设置hour1230
	 * @param hour1230 the hour1230 to set
	 */
	public void setHour1230(String hour1230) {
		this.hour1230 = hour1230;
	}

	/**
	 * 获得hour1300
	 * @return the hour1300
	 */
	public String getHour1300() {
		return hour1300;
	}

	/**
	 * 设置hour1300
	 * @param hour1300 the hour1300 to set
	 */
	public void setHour1300(String hour1300) {
		this.hour1300 = hour1300;
	}

	/**
	 * 获得hour1330
	 * @return the hour1330
	 */
	public String getHour1330() {
		return hour1330;
	}

	/**
	 * 设置hour1330
	 * @param hour1330 the hour1330 to set
	 */
	public void setHour1330(String hour1330) {
		this.hour1330 = hour1330;
	}

	/**
	 * 获得hour1400
	 * @return the hour1400
	 */
	public String getHour1400() {
		return hour1400;
	}

	/**
	 * 设置hour1400
	 * @param hour1400 the hour1400 to set
	 */
	public void setHour1400(String hour1400) {
		this.hour1400 = hour1400;
	}

	/**
	 * 获得hour1430
	 * @return the hour1430
	 */
	public String getHour1430() {
		return hour1430;
	}

	/**
	 * 设置hour1430
	 * @param hour1430 the hour1430 to set
	 */
	public void setHour1430(String hour1430) {
		this.hour1430 = hour1430;
	}

	/**
	 * 获得hour1500
	 * @return the hour1500
	 */
	public String getHour1500() {
		return hour1500;
	}

	/**
	 * 设置hour1500
	 * @param hour1500 the hour1500 to set
	 */
	public void setHour1500(String hour1500) {
		this.hour1500 = hour1500;
	}

	/**
	 * 获得hour1530
	 * @return the hour1530
	 */
	public String getHour1530() {
		return hour1530;
	}

	/**
	 * 设置hour1530
	 * @param hour1530 the hour1530 to set
	 */
	public void setHour1530(String hour1530) {
		this.hour1530 = hour1530;
	}

	/**
	 * 获得hour1600
	 * @return the hour1600
	 */
	public String getHour1600() {
		return hour1600;
	}

	/**
	 * 设置hour1600
	 * @param hour1600 the hour1600 to set
	 */
	public void setHour1600(String hour1600) {
		this.hour1600 = hour1600;
	}

	/**
	 * 获得hour1630
	 * @return the hour1630
	 */
	public String getHour1630() {
		return hour1630;
	}

	/**
	 * 设置hour1630
	 * @param hour1630 the hour1630 to set
	 */
	public void setHour1630(String hour1630) {
		this.hour1630 = hour1630;
	}

	/**
	 * 获得hour1700
	 * @return the hour1700
	 */
	public String getHour1700() {
		return hour1700;
	}

	/**
	 * 设置hour1700
	 * @param hour1700 the hour1700 to set
	 */
	public void setHour1700(String hour1700) {
		this.hour1700 = hour1700;
	}

	/**
	 * 获得hour1730
	 * @return the hour1730
	 */
	public String getHour1730() {
		return hour1730;
	}

	/**
	 * 设置hour1730
	 * @param hour1730 the hour1730 to set
	 */
	public void setHour1730(String hour1730) {
		this.hour1730 = hour1730;
	}

	/**
	 * 获得hour1800
	 * @return the hour1800
	 */
	public String getHour1800() {
		return hour1800;
	}

	/**
	 * 设置hour1800
	 * @param hour1800 the hour1800 to set
	 */
	public void setHour1800(String hour1800) {
		this.hour1800 = hour1800;
	}

	/**
	 * 获得hour1830
	 * @return the hour1830
	 */
	public String getHour1830() {
		return hour1830;
	}

	/**
	 * 设置hour1830
	 * @param hour1830 the hour1830 to set
	 */
	public void setHour1830(String hour1830) {
		this.hour1830 = hour1830;
	}

	/**
	 * 获得hour1900
	 * @return the hour1900
	 */
	public String getHour1900() {
		return hour1900;
	}

	/**
	 * 设置hour1900
	 * @param hour1900 the hour1900 to set
	 */
	public void setHour1900(String hour1900) {
		this.hour1900 = hour1900;
	}

	/**
	 * 获得hour1930
	 * @return the hour1930
	 */
	public String getHour1930() {
		return hour1930;
	}

	/**
	 * 设置hour1930
	 * @param hour1930 the hour1930 to set
	 */
	public void setHour1930(String hour1930) {
		this.hour1930 = hour1930;
	}

	/**
	 * 获得hour2000
	 * @return the hour2000
	 */
	public String getHour2000() {
		return hour2000;
	}

	/**
	 * 设置hour2000
	 * @param hour2000 the hour2000 to set
	 */
	public void setHour2000(String hour2000) {
		this.hour2000 = hour2000;
	}

	/**
	 * 获得hour2030
	 * @return the hour2030
	 */
	public String getHour2030() {
		return hour2030;
	}

	/**
	 * 设置hour2030
	 * @param hour2030 the hour2030 to set
	 */
	public void setHour2030(String hour2030) {
		this.hour2030 = hour2030;
	}

	/**
	 * 获得hour2100
	 * @return the hour2100
	 */
	public String getHour2100() {
		return hour2100;
	}

	/**
	 * 设置hour2100
	 * @param hour2100 the hour2100 to set
	 */
	public void setHour2100(String hour2100) {
		this.hour2100 = hour2100;
	}

	/**
	 * 获得hour2130
	 * @return the hour2130
	 */
	public String getHour2130() {
		return hour2130;
	}

	/**
	 * 设置hour2130
	 * @param hour2130 the hour2130 to set
	 */
	public void setHour2130(String hour2130) {
		this.hour2130 = hour2130;
	}

	/**
	 * 获得hour2200
	 * @return the hour2200
	 */
	public String getHour2200() {
		return hour2200;
	}

	/**
	 * 设置hour2200
	 * @param hour2200 the hour2200 to set
	 */
	public void setHour2200(String hour2200) {
		this.hour2200 = hour2200;
	}

	/**
	 * 获得hour2230
	 * @return the hour2230
	 */
	public String getHour2230() {
		return hour2230;
	}

	/**
	 * 设置hour2230
	 * @param hour2230 the hour2230 to set
	 */
	public void setHour2230(String hour2230) {
		this.hour2230 = hour2230;
	}

	/**
	 * 获得hour2300
	 * @return the hour2300
	 */
	public String getHour2300() {
		return hour2300;
	}

	/**
	 * 设置hour2300
	 * @param hour2300 the hour2300 to set
	 */
	public void setHour2300(String hour2300) {
		this.hour2300 = hour2300;
	}

	/**
	 * 获得hour2330
	 * @return the hour2330
	 */
	public String getHour2330() {
		return hour2330;
	}

	/**
	 * 设置hour2330
	 * @param hour2330 the hour2330 to set
	 */
	public void setHour2330(String hour2330) {
		this.hour2330 = hour2330;
	}

	/**
	 * 获得hour0000
	 * @return the hour0000
	 */
	public String getHour0000() {
		return hour0000;
	}

	/**
	 * 设置hour0000
	 * @param hour0000 the hour0000 to set
	 */
	public void setHour0000(String hour0000) {
		this.hour0000 = hour0000;
	}

	/**
	 * 获得hour0030
	 * @return the hour0030
	 */
	public String getHour0030() {
		return hour0030;
	}

	/**
	 * 设置hour0030
	 * @param hour0030 the hour0030 to set
	 */
	public void setHour0030(String hour0030) {
		this.hour0030 = hour0030;
	}

	/**
	 * 获得platformDistributeEntity
	 * @return the platformDistributeEntity
	 */
	public PlatformDistributeEntity getPlatformDistributeEntity() {
		return platformDistributeEntity;
	}

	/**
	 * 设置platformDistributeEntity
	 * @param platformDistributeEntity the platformDistributeEntity to set
	 */
	public void setPlatformDistributeEntity(
			PlatformDistributeEntity platformDistributeEntity) {
		this.platformDistributeEntity = platformDistributeEntity;
	}

	/** 
	 * toString
	 * @author 104306-foss-wangLong
	 * @date 2013-1-9 下午2:30:09
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "PlatformDistributeDto [" +
					"platformNo=" + platformNo
				+ ", platformVirtualCode=" + platformVirtualCode
				+ ", hour0130=" + hour0130 + ", hour0200=" + hour0200
				+ ", hour0230=" + hour0230 + ", hour0300=" + hour0300
				+ ", hour0330=" + hour0330 + ", hour0400=" + hour0400
				+ ", hour0430=" + hour0430 + ", hour0500=" + hour0500
				+ ", hour0530=" + hour0530 + ", hour0600=" + hour0600
				+ ", hour0630=" + hour0630 + ", hour0700=" + hour0700
				+ ", hour0730=" + hour0730 + ", hour0800=" + hour0800
				+ ", hour0830=" + hour0830 + ", hour0900=" + hour0900
				+ ", hour0930=" + hour0930 + ", hour1000=" + hour1000
				+ ", hour1030=" + hour1030 + ", hour1100=" + hour1100
				+ ", hour1130=" + hour1130 + ", hour1200=" + hour1200
				+ ", hour1230=" + hour1230 + ", hour1300=" + hour1300
				+ ", hour1330=" + hour1330 + ", hour1400=" + hour1400
				+ ", hour1430=" + hour1430 + ", hour1500=" + hour1500
				+ ", hour1530=" + hour1530 + ", hour1600=" + hour1600
				+ ", hour1630=" + hour1630 + ", hour1700=" + hour1700
				+ ", hour1730=" + hour1730 + ", hour1800=" + hour1800
				+ ", hour1830=" + hour1830 + ", hour1900=" + hour1900
				+ ", hour1930=" + hour1930 + ", hour2000=" + hour2000
				+ ", hour2030=" + hour2030 + ", hour2100=" + hour2100
				+ ", hour2130=" + hour2130 + ", hour2200=" + hour2200
				+ ", hour2230=" + hour2230 + ", hour2300=" + hour2300
				+ ", hour2330=" + hour2330 + ", hour0000=" + hour0000
				+ ", hour0030=" + hour0030 + "]";
	}
	
}